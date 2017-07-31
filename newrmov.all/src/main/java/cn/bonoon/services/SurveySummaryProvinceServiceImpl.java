package cn.bonoon.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.SurveySummaryCityService;
import cn.bonoon.core.SurveySummaryProvinceService;
import cn.bonoon.entities.SurveySummaryCityEntity;
import cn.bonoon.entities.SurveySummaryCountyEntity;
import cn.bonoon.entities.SurveySummaryProvinceEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.entities.logs.HandleLogEntity;
import cn.bonoon.entities.logs.surveycomparer.SurveySummaryComparer;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class SurveySummaryProvinceServiceImpl extends AbstractService<SurveySummaryProvinceEntity> implements SurveySummaryProvinceService {

	@Override
	protected SurveySummaryProvinceEntity __save(OperateEvent event, SurveySummaryProvinceEntity entity) {
		/*
		 * 1.先判断这个年度的记录是否存在，如果存在，则不能再提交了
		 * 2.创建所有市级的上报记录
		 */
		int annual = entity.getAnnual();
		String ql = "select count(x) from SurveySummaryProvinceEntity x where x.annual=?";
		if(__exsit(ql, annual)){
			throw new RuntimeException("已经存在" + annual + "年度的汇总记录！");
		}
		ql = "select x from UnitEntity x where x.place.level=2";
		List<UnitEntity> units = __list(UnitEntity.class, ql);
		entity.setNeedReport(units.size());
		super.__save(event, entity);
		for(UnitEntity unit : units){
			SurveySummaryCityEntity ssc = new SurveySummaryCityEntity();
			ssc.setProvince(entity);
			ssc.setUnit(unit);
			PlaceEntity city = unit.getPlace();
			ssc.setCityId(city.getId());
			ssc.setCityName(city.getName());
			entityManager.persist(ssc);
		}
		
		return entity;
	}

	@Override
	public List<SurveySummaryCountyEntity> countySurveies(Long pid, Long id) {
		String ql = "select x from SurveySummaryCountyEntity x where x.city.id=?";
		return __list(SurveySummaryCountyEntity.class, ql, id);
	}

	@Override
	public List<SurveySummaryCityEntity> citySurveies(Long pid) {
		String ql = "select x from SurveySummaryCityEntity x where x.province.id=?";
		return __list(SurveySummaryCityEntity.class, ql, pid);
	}

	@Override
	@Transactional
	public void urge(Long id,IOperator user) {
		SurveySummaryCityEntity ssc = entityManager.find(SurveySummaryCityEntity.class, id);
		ssc.setUrge(ssc.getUrge() + 1);
		entityManager.merge(ssc);
		
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(ssc.getId());
		hlog.setTargetType(SurveySummaryCityEntity.class.getName());
		hlog.setContent(ssc.getProvince().getAnnual()+"年"+ssc.getCityName()+"催报摸底调查");
		entityManager.persist(hlog);
	}
	
	@Override
	public List<SurveySummaryProvinceEntity> get(IOperator user, int count) {
//		String ql = "select x from SurveySummaryProvinceEntity x where x.status=1 order by x.annual desc";
		String ql = "select x from SurveySummaryProvinceEntity x order by x.annual desc";
		return __top(count, SurveySummaryProvinceEntity.class, ql);
	}

	@Override
	@Transactional
	public void back(Long id,IOperator user) {
		SurveySummaryCityEntity ssc = entityManager.find(SurveySummaryCityEntity.class, id);
		if(ssc.getStatus() != 2) throw new RuntimeException("非已经开始的状态，无法退回！");
		ssc.setStatus(0);
		__refresh(ssc);
		
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(ssc.getId());
		hlog.setTargetType(SurveySummaryCityEntity.class.getName());
		hlog.setContent(ssc.getProvince().getAnnual()+"年"+ssc.getCityName()+"退回摸底调查");
		entityManager.persist(hlog);
	}
	
	private void __refresh(SurveySummaryCityEntity ssc){
		ssc.setUrge(0);
		//重新统计一下
		entityManager.merge(ssc);
		String ql = "select count(x) from SurveySummaryCityEntity x where x.province.id=? and x.status=?";
		SurveySummaryProvinceEntity province = ssc.getProvince();
		int sr = __first(Number.class, ql, province.getId(), 2).intValue();
		int fr = __first(Number.class, ql, province.getId(), 1).intValue();
		province.setStartReport(sr);
		province.setFinishReport(fr);
		entityManager.merge(province);
		
		
		
	}
	
	@Override
	@Transactional
	public void finish(Long id,IOperator user) {
		SurveySummaryProvinceEntity ssp = __get(id);
		String ql = "select x from SurveySummaryCityEntity x where x.province.id=?";
		ssp.clear();
		String error = "";
		for(SurveySummaryCityEntity ssc : __list(SurveySummaryCityEntity.class, ql, id)){
			if(ssc.getStatus() == SurveySummaryCityService.exclude) continue;
			
			if(ssc.getStatus() != 1){ 
				error += ","+ssc.getCityName(); 
			}else{
				ssp.sum(ssc);
			}
		}
		if(error.isEmpty()){
			ssp.setStatus(1);
			entityManager.merge(ssp);
			
			/****记录摸底调查完成的操作***/
			HandleLogEntity hlog = new HandleLogEntity();
			hlog.currentUser(user);
			hlog.setTargetId(ssp.getId());
			hlog.setTargetType(SurveySummaryProvinceEntity.class.getName());
			hlog.setContent(ssp.getAnnual()+"年完成了摸底调查");
			entityManager.persist(hlog);
			
		}else{
			throw new RuntimeException("未完成的城市：" + error.substring(1));
		}
	}
	
	@Override
	@Transactional
	public void reject(Long id,IOperator user) {
		SurveySummaryCityEntity ssc = entityManager.find(SurveySummaryCityEntity.class, id);
		if(ssc.getStatus() != 1) throw new RuntimeException("非已经完成的状态，无法驳回！");
		ssc.setStatus(2);
		__refresh(ssc);
		
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(ssc.getId());
		hlog.setTargetType(SurveySummaryCityEntity.class.getName());
		hlog.setContent(ssc.getProvince().getAnnual()+"年"+ssc.getCityName()+"驳回摸底调查");
		entityManager.persist(hlog);
	}
	
	@Override
	@Transactional
	public void restat(Long id,IOperator user) {
		SurveySummaryCityEntity ssc = entityManager.find(SurveySummaryCityEntity.class, id);
		
		if(ssc.getStatus() != 1) throw new RuntimeException("非已经完成的状态，无法重新统计！");
		
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(ssc .getId());
		hlog.setTargetType(SurveySummaryCityEntity.class.getName());
		hlog.setContent("刷新"+ssc.getCityName()+"的摸底调查表数据");
		entityManager.persist(hlog);
		
		SurveySummaryComparer ssCom = new SurveySummaryComparer();
		ssCom.getSscCompare().setSource(ssc);
		ssCom.setLog(hlog);
		
		
		//把所有县的累加上来
		String ql = "select x from SurveySummaryCountyEntity x where x.city.id=?";
		ssc.clear();
		for(SurveySummaryCountyEntity ss : __list(SurveySummaryCountyEntity.class, ql, id)){
			ssc.sum(ss);
		}
		
		ssCom.setName("[城市]"+ssc.getCityName());
		ssCom.getSscCompare().setTarget(ssc);
		ssCom.setTargetType(SurveySummaryCityEntity.class.getName());
		ssCom.setTargetId(ssc.getId());
		entityManager.merge(ssc);
		entityManager.persist(ssCom);

	}

	@Override
	@Transactional
	public void exclude(Long id,IOperator user) {
		SurveySummaryCityEntity ssc = entityManager.find(SurveySummaryCityEntity.class, id);
		ssc.setStatus(3);
		entityManager.merge(ssc);
		
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(ssc.getId());
		hlog.setTargetType(SurveySummaryCityEntity.class.getName());
		hlog.setContent(ssc.getProvince().getAnnual()+"年"+ssc.getCityName()+"排除摸底调查");
		entityManager.persist(hlog);
	}

	@Override
	@Transactional
	public void recover(Long id,IOperator user) {
		SurveySummaryCityEntity ssc = entityManager.find(SurveySummaryCityEntity.class, id);
		ssc.setStatus(0);
		entityManager.merge(ssc);
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(ssc.getId());
		hlog.setTargetType(SurveySummaryCityEntity.class.getName());
		hlog.setContent(ssc.getProvince().getAnnual()+"年"+ssc.getCityName()+"恢复摸底调查");
		entityManager.persist(hlog);
		
	}
}
