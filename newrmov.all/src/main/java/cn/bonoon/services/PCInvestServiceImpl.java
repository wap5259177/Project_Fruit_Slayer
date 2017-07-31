package cn.bonoon.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.PCInvestService;
import cn.bonoon.entities.informatioin.BatchCapitalInvestmentInformationEntity;
import cn.bonoon.entities.informatioin.CityCapitalInvestmentInformationEntity;
import cn.bonoon.entities.informatioin.ProvinceCapitalInvestmentInformationEntity;
import cn.bonoon.entities.logs.HandleLogEntity;
import cn.bonoon.entities.logs.capitalcomparer.CapitalInfoComparer;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractService;
import cn.bonoon.util.DoubleHelper;

@Service
@Transactional(readOnly = true)
public class PCInvestServiceImpl extends AbstractService<ProvinceCapitalInvestmentInformationEntity> implements PCInvestService{

	@Override
	protected ProvinceCapitalInvestmentInformationEntity __save(OperateEvent event, ProvinceCapitalInvestmentInformationEntity entity) {
		return super.__save(event, entity);
	}

	@Override
	@Transactional
	public void startReport(Long id,IOperator user) {
		ProvinceCapitalInvestmentInformationEntity entity = __get(id);
		entity.setStatus(1);
		entityManager.merge(entity);
		/****记录资金投入情况提交的操作***/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(entity .getId());
		hlog.setTargetType(ProvinceCapitalInvestmentInformationEntity.class.getName());
		hlog.setContent("提交了资金投入情况表");
		entityManager.persist(hlog);
	}

	//退回
	@Override
	@Transactional
	public void rejectReport(Long id,IOperator user) {
		CityCapitalInvestmentInformationEntity entity = entityManager.find(CityCapitalInvestmentInformationEntity.class, id);
		entity.setStatus(3);
		entityManager.merge(entity);
		/****记录资金投入情况退回的操作***/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(entity.getId());
		hlog.setTargetType(CityCapitalInvestmentInformationEntity.class.getName());
		hlog.setContent("退回"+entity.getName()+"资金投入报表");
		entityManager.persist(hlog);
	}

	//查出所有省创建的记录
	@Override
	public List<ProvinceCapitalInvestmentInformationEntity> allReport() {
		String ql = "select x from ProvinceCapitalInvestmentInformationEntity x where x.status in (1,2,3) and x.deleted=false";
		return __list(ProvinceCapitalInvestmentInformationEntity.class, ql);
		
		
	}

	//开始填报
	@Override
	@Transactional
	public void toReport(LogonUser user, Long reportId) {
//		String ql = "select x from PlaceEntity x where x.id=? and x.deleted=false";
//		PlaceEntity place = __first(PlaceEntity.class, ql,user.getOwnerId());
		String pql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, pql,user.getOwnerId());
		PlaceEntity place = entityManager.find(PlaceEntity.class, pid);
		ProvinceCapitalInvestmentInformationEntity entity = __get(reportId);
		
		CityCapitalInvestmentInformationEntity ccii = new CityCapitalInvestmentInformationEntity();
		ccii.setPciInformation(entity);
		ccii.setPlace(place);
		
		
		ccii.setCreateAt(new Date());//这个是不能为空的
		ccii.setCreatorId(user.getId());
		ccii.setCreatorName(user.getUsername());
		
		ccii.setName(place.getName());
		ccii.setStatus(2);//0:未开始 1:完成  2:正在填报 3:驳回 4:待审核
		entityManager.persist(ccii);
		
//		/*创建固定的三条记录batchcap...*/
//		for(int i=0;i<3;i++){
//			BatchCapitalInvestmentInformationEntity batch = new BatchCapitalInvestmentInformationEntity();
//			batch.setCciInformation(ccii);
//			String name = i==0?"第一批":i==1?"第二批":"第三批";
//			batch.setName(name);
//			entityManager.persist(batch);
//		}
		
		/*
		 * 这个市有多少个县就创建多少条:
		 * CountyNaturalVillageInformationEntity
		 */
		List<PlaceEntity> countys = ccii.getPlace().getChildren();
		for(PlaceEntity p:countys){
			BatchCapitalInvestmentInformationEntity batch = new BatchCapitalInvestmentInformationEntity();
			batch.setCciInformation(ccii);
			batch.setPlace(place);
			batch.setName(p.getName());
			entityManager.persist(batch);
		}
	}

	/*某条省记录下面所有的市报*/
	@Override
	public List<CityCapitalInvestmentInformationEntity> allCityReportByReport(
			Long pid) {
		String ql = "select x from CityCapitalInvestmentInformationEntity x where x.pciInformation.id=?and x.deleted=false";
		return __list(CityCapitalInvestmentInformationEntity.class, ql,pid);
	}

	//通过
	@Override
	@Transactional
	public void passReport(Long id,IOperator user) {
		CityCapitalInvestmentInformationEntity entity = entityManager.find(CityCapitalInvestmentInformationEntity.class, id);
		entity.setStatus(1);
		entityManager.merge(entity);
		
		/****记录资金投入情况通过的操作***/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(entity.getId());
		hlog.setTargetType(CityCapitalInvestmentInformationEntity.class.getName());
		hlog.setContent("通过"+entity.getName()+"资金投入报表");
		entityManager.persist(hlog);
	}

	//退回填报那里
	@Override
	@Transactional
	public void DeletedReport(Long id,IOperator user) {
		CityCapitalInvestmentInformationEntity entity = entityManager.find(CityCapitalInvestmentInformationEntity.class, id);
		entity.setDeleted(true);
		entityManager.merge(entity);
		
		/****记录资金投入情况退回填报的操作***/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(entity.getId());
		hlog.setTargetType(CityCapitalInvestmentInformationEntity.class.getName());
		hlog.setContent("删除"+entity.getName()+"资金投入报表");
		entityManager.persist(hlog);
	}

	//刷新一个市资金
	@Override
	@Transactional
	public void refreshMn(Long id,IOperator user) {
		CityCapitalInvestmentInformationEntity entity = entityManager.find(CityCapitalInvestmentInformationEntity.class, id);
		List<BatchCapitalInvestmentInformationEntity> batchs = entity.getItems();
		double d[] = new double[8];
		for(BatchCapitalInvestmentInformationEntity batch:batchs){
			d[6] = DoubleHelper.add(d[6], batch.getTotalFunds());
			d[0] = DoubleHelper.add(d[0], batch.getFunds0());
			d[1] = DoubleHelper.add(d[1], batch.getFunds1());
			d[2] = DoubleHelper.add(d[2], batch.getFunds2());
			d[3] = DoubleHelper.add(d[3], batch.getFunds3());
			d[4] = DoubleHelper.add(d[4], batch.getFunds4());
			d[5] = DoubleHelper.add(d[5], batch.getFunds5());
		}
		entity.setTotalFunds(d[6]);
		entity.setFunds0(d[0]);
		entity.setFunds1(d[1]);
		entity.setFunds2(d[2]);
		entity.setFunds3(d[3]);
		entity.setFunds4(d[4]);
		entity.setFunds5(d[5]);
		entityManager.merge(entity);
	}

	//这个填报下的所有市
	@Override
	@Transactional
	public void refresh(Long id,IOperator user) {
		String ql = "select x from  CityCapitalInvestmentInformationEntity x where x.pciInformation.id=? and x.deleted=false ";
		List<CityCapitalInvestmentInformationEntity> ccis = __list(CityCapitalInvestmentInformationEntity.class, ql, id);
		
		
		for(CityCapitalInvestmentInformationEntity entity:ccis){
			List<BatchCapitalInvestmentInformationEntity> batchs = entity.getItems();
			double d[] = new double[8];
			
			/****记录资金投入情况刷新资金的操作***/
			HandleLogEntity hlog = new HandleLogEntity();
			hlog.currentUser(user);
			hlog.setTargetId(entity.getId());
			hlog.setTargetType(CityCapitalInvestmentInformationEntity.class.getName());
			hlog.setContent("刷新"+entity.getName()+"资金投入的情况");
			entityManager.persist(hlog);
			
			/****记录资金投入情况刷新资金的比较结果***/
			CapitalInfoComparer cap = new CapitalInfoComparer();
			cap.setLog(hlog);
			cap.setTargetId(entity.getId());
			cap.setTargetType(CityCapitalInvestmentInformationEntity.class.getName());
			cap.setName("[城市]"+entity.getName());
			
			cap.getRnfCompare().setSource(entity);
			
			for(BatchCapitalInvestmentInformationEntity batch:batchs){
				d[6] = DoubleHelper.add(d[6], batch.getTotalFunds());
				d[0] = DoubleHelper.add(d[0], batch.getFunds0());
				d[1] = DoubleHelper.add(d[1], batch.getFunds1());
				d[2] = DoubleHelper.add(d[2], batch.getFunds2());
				d[3] = DoubleHelper.add(d[3], batch.getFunds3());
				d[4] = DoubleHelper.add(d[4], batch.getFunds4());
				d[5] = DoubleHelper.add(d[5], batch.getFunds5());
			}
			entity.setTotalFunds(d[6]);
			entity.setFunds0(d[0]);
			entity.setFunds1(d[1]);
			entity.setFunds2(d[2]);
			entity.setFunds3(d[3]);
			entity.setFunds4(d[4]);
			entity.setFunds5(d[5]);
			
			cap.getRnfCompare().setTarget(entity);
			entityManager.merge(entity);	
			entityManager.persist(cap);
		}
	}


}
