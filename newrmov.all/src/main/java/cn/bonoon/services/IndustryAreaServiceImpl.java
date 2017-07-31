package cn.bonoon.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.core.IndustryAreaService;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.IndustryAreaEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.util.DoubleHelper;

@Service
@Transactional(readOnly = true)
public class IndustryAreaServiceImpl extends ConfigurableModelAreaService<IndustryAreaEntity> implements IndustryAreaService {

	@Override
	protected IndustryAreaEntity __save(OperateEvent event, IndustryAreaEntity entity) {
		__doPre(entity, event);
		return super.__save(event, entity);
	}
	
	@Override
	protected IndustryAreaEntity __update(OperateEvent event, IndustryAreaEntity entity) {
		__doPre(entity, event);
		return super.__update(event, entity);
	}
	
	private void __doPre(IndustryAreaEntity entity, OperateEvent event){
//		BaseRuralEntity rural = entity.getRural();
//		if(null == rural){
//			throw new RuntimeException("必须选择产业发展所在的村！");
//		}
//		ModelAreaEntity ma = rural.getModelArea();
		//TODO 提交的时候检查是否允许提交
//		checkAndThrowError(ma, event);
		
//		Long pid = entity.getPlaceId();
//		if(null != pid){
//			PlaceEntity place = entityManager.find(PlaceEntity.class, pid);
//			if(null != place){
//				entity.setVillageName(place.getName());
//				entity.setTown(place.getParent().getName());
//			}
//		}
		BaseRuralEntity rural = entity.getRural();
		
		if(null == rural){
		
			//
			Long pid = entity.getPlaceId();
			if(null == pid || pid == 0){
				throw new RuntimeException("必须选择产业发展所在的村(行政村或自然村)！");
			}
			PlaceEntity pe = entityManager.find(PlaceEntity.class, pid);
			if(null == pe){
				throw new RuntimeException("必须选择产业发展所在的村(行政村或自然村)！");
			}
			
			
			
			Long oid = event.getOwnerId();
			String ql = "select x from ModelAreaEntity x where x.ownerId=?";
			ModelAreaEntity mae = __first(ModelAreaEntity.class, ql, oid);
			if(null == mae){
				throw new RuntimeException("未创建片区台账！");
			}

			checkAndThrowError(mae, event);
			
			entity.setModelArea(mae);
			entity.setVillageName(pe.getName());
			entity.setTown(pe.getParent().getName());
		}else{
		
			ModelAreaEntity ma = rural.getModelArea();
			checkAndThrowError(ma, event);
			int mas = rural.getModelArea().getStatus();
			if(!(mas == 0 || mas == 3)){
				throw new RuntimeException("新农村示范片区已经提交！");
			}
			entity.setModelArea(rural.getModelArea());
			entity.setPlaceId(rural.getPlaceId());
			entity.setVillageName(rural.getName());
			entity.setTown(rural.getTown());
		}
		if("true".equalsIgnoreCase(event.getString("applicant-submit"))){
			if(StringHelper.isEmpty(entity.getCoopName())){
				throw new RuntimeException("专业合作社名称不允许为空！");
			}
			if(null == entity.getReportDate()){
				throw new RuntimeException("请填写填报日期!！");
			}
			entity.setStatus(1);
		}else{
			entity.setStatus(0);
		}
	}

	@Override
	public List<PlaceEntity> getTowns(Long oid) {
		UnitEntity unit = entityManager.find(UnitEntity.class, oid);
		return unit.getPlace().getChildren();
	}
	
	@Override
	public Map<Long, String> rurals(Long oid) {
		String ql1 = "select distinct x.placeId,x.modelArea.name from NewRuralEntity x where x.ownerId=? and x.deleted=false and x.placeId is not null";
		List<Object[]> its1 = __list(Object[].class, ql1, oid);
		String ql2 = "select distinct x.placeId,x.modelArea.name from PeripheralRuralEntity x where x.ownerId=? and x.deleted=false and x.placeId is not null";
		List<Object[]> its2 = __list(Object[].class, ql2, oid);
		Map<Long, String> map = new HashMap<>();
		for(Object[] it1 : its1){
			map.put((Long)it1[0], "主体村：" + it1[1]);
		}
		for(Object[] it2 : its2){
			map.put((Long)it2[0], "非主体村：" + it2[1]);
		}
		return map;
	}

	@Override
	public List<Object[]> rurals(IOperator opt) {
		String ql = "select distinct x.id,x.name,x.modelArea.name,x.naturalVillage,x.town,x.placeId from NewRuralEntity x where x.ownerId=? and x.deleted=false and x.name is not null and x.modelArea.status in(0, 3)";
		return __list(Object[].class, ql, opt.getOwnerId());
	}

	@Override
	public List<Object[]> rurals2(IOperator opt) {
		String ql = "select distinct x.id,x.name,x.modelArea.name,x.naturalVillage,x.town,x.placeId from PeripheralRuralEntity x where x.ownerId=? and x.deleted=false and x.name is not null and x.modelArea.status in(0, 3)";
		return __list(Object[].class, ql, opt.getOwnerId());
	}
	
	@Override
	public boolean check(IOperator opt) {
		String ql = "select x.modelArea.batch from NewRuralEntity x where x.ownerId=? and x.deleted=false";
		return __config().check(__first(String.class, ql, opt.getOwnerId()), opt, false);
	}

	@Override
	public Collection<Object[]> statistics(String batch) {
		batch  = BatchHelper.get(batch);                                     //and x.modelArea.status>0
		String ql = "select x from IndustryAreaEntity x where x.modelArea.deleted=false and x.modelArea.batch=?";
		List<IndustryAreaEntity> items = __list(IndustryAreaEntity.class, ql, batch);
		return __statistics(items);
	}
	
	private Collection<Object[]> __statistics(List<IndustryAreaEntity> items){
		Map<Long, Object[]> sts = new HashMap<>();
		for(IndustryAreaEntity iae : items){
			Object[] its = sts.get(iae.getModelArea().getId());
			if(null == its){
				ModelAreaEntity ma = iae.getModelArea();
				its = new Object[23];
				sts.put(iae.getModelArea().getId(), its);
				//0~5
				its[0] = ma.getBatch();
				its[1] = ma.getReportAnnual();
				its[2] = ma.getCityName();
				its[3] = ma.getCounty();
				its[4] = ma.getName();
				its[5] = ma.getThemeName();
				
				its[6] = 1;
				if("主体村".equals(iae.getVillageFea())){
					its[7] = 1;
					its[8] = 0;
				}else{
					its[7] = 0;
					its[8] = 1;
				}
				
				its[9] = iae.getMemberHous();
				its[10] = iae.getNonMemberHous();
				its[11] = iae.getRegisterFunds();
				its[12] = 0;
				its[13] = 0;
				its[14] = 0;
				its[15] = 0;
				its[16] = 0;
				its[17] = 0;
				its[18] = 0;
				its[19] = iae.getRegiTradeMark();
				its[20] = iae.getAgriculPros();
				its[21] = iae.getFreePollution();
				its[22] = iae.getSpecialProduct();
				if(null == its[22]){
					its[22] = "";
				}
			}else{
				its[6] = (Integer)its[6] + 1;
				if("主体村".equals(iae.getVillageFea())){
					its[7] = (Integer)its[7] + 1;
				}else{
					its[8] = (Integer)its[8] + 1;
				}
				__int(its, 9, iae.getMemberHous());
				__int(its, 10, iae.getNonMemberHous());
				__dou(its, 11, iae.getRegisterFunds());
				__int(its, 19, iae.getRegiTradeMark());
				__int(its, 20, iae.getAgriculPros());
				__int(its, 21, iae.getFreePollution());
				if(StringHelper.isNotEmpty(iae.getSpecialProduct())){
					its[22] = its[22] + "\n" + iae.getSpecialProduct();
				}
			}
			String businessScope = iae.getBusinessScope();
			if(null != businessScope){
			__str(its, 12, businessScope, "种植业");
			__str(its, 13, businessScope, "畜牧业");
			__str(its, 14, businessScope, "渔业");
			__str(its, 15, businessScope, "林业");
			__str(its, 16, businessScope, "服务业");
			__str(its, 17, businessScope, "手工业");
			__str(its, 18, businessScope, "其它");
			}
		}
		return sts.values();
	}
	private void __str(Object[] its, int i, String s, String v){
		if(s.contains(v)){
			its[i] = (Integer)its[i] + 1;
		}
	}
	private void __int(Object[] its, int i, int v){
		its[i] = (Integer)its[i] + v;
	}
	private void __dou(Object[] its, int i, double v){
		its[i] = DoubleHelper.add(v, its[i]);
	}
	
	@Override
	public Collection<Object[]> statisticsLocal(IOperator opt, String batch) {        //and x.modelArea.status>0
		String ql = "select x from IndustryAreaEntity x where x.modelArea.deleted=false and x.modelArea.status>0 and x.modelArea.ownerId=?";
		List<IndustryAreaEntity> items = __list(IndustryAreaEntity.class, ql, opt.getOwnerId());
		return __statistics(items);
	}

	@Override
	public Collection<Object[]> statisticsCity(IOperator opt, String batch) {
		String ql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql, opt.getOwnerId());                  //and x.modelArea.status>0
		ql = "select x from IndustryAreaEntity x where x.modelArea.deleted=false and x.modelArea.status>0 and x.modelArea.cityId=?";
		List<IndustryAreaEntity> items = __list(IndustryAreaEntity.class, ql, pid);
		return __statistics(items);
	}
}
