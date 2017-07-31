package cn.bonoon.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.AdministrationUncoreRuralService;
import cn.bonoon.entities.AdministrationUncoreRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.util.StringHelper;

@Service
@Transactional(readOnly = true)
//public class AdministrationUncoreRuralServiceImpl extends AbstractService<AdministrationUncoreRuralEntity> implements AdministrationUncoreRuralService {
public class AdministrationUncoreRuralServiceImpl extends ConfigurableModelAreaService<AdministrationUncoreRuralEntity> implements AdministrationUncoreRuralService {

	//getAdministration
	@Override
	public AdministrationUncoreRuralEntity getAdministrationByAdminRuralId(Long id) {
		AdministrationUncoreRuralEntity are = entityManager.find(AdministrationUncoreRuralEntity.class, id);
		return are;
	}
	
	/*
	 * 重写__update
	 * 
	 * 		点击确认前验证
	 * 
	 */
	@Override
	protected AdministrationUncoreRuralEntity __update(OperateEvent event, AdministrationUncoreRuralEntity entity) {
		//检查是否允许提交
		checkAndThrowError(entity.getModelArea(),event);
		boolean submit = "true".equalsIgnoreCase(event.getString("applicant-submit"));
		PlaceEntity pl = entity.getPlace();
		entity.setTown(pl.getParent().getName());
		if(submit){//保存
			String errmsg = "";
			int i = 1;
			if(StringHelper.isEmpty(entity.getCode())){
				errmsg += i++ + ". 请填写序号！\n\r\t";
			}
			
//			if(entity.getNatrualruralNum()<=0){
//				errmsg += i++ +".自然村个数非合理数字!\n\r\t";
//			}
			
			if(entity.getRuralArea() < entity.getArableLand()){
				errmsg += i++ + ". 总面积必须大于耕地面积！\n\r\t";
			}
			
			if (entity.getPopulation() <= 0) {
				errmsg += i++ + ". 人口数非合理数字！\n\r\t";
			}
			if (entity.getLabor() <= 0) {
				errmsg += i++ + ". 劳动人口数非合理数字！\n\r\t";
			}
			if (entity.getPopulation() < entity.getLabor()) {
				errmsg += i++ + ". 劳动人口不能超过村总人口！\n\r\t";
			}
			if(entity.getHouseHold() > entity.getPopulation()){
				errmsg += i++ + ". 户数必须小于人口数！\n\r\t";
			}
			
			if("是".equals(entity.getPoorVillage()) && StringHelper.isEmpty(entity.getHelpUnit())){
				errmsg += i++ + ". 请填写帮扶单位名称！\n\r\t";
			}
			
			if("是".equals(entity.getPublicService()) && StringHelper.isEmpty(entity.getPsAnnual())){
				errmsg += i++ + ". 请选择建立统一的村级公共服务管理平台年度！\n\r\t";
			}
			
			if(!errmsg.isEmpty()){
				throw new RuntimeException(errmsg);
			}
			
			entity.setStatus(1);
		}else{//暂存
			entity.setStatus(0);
		}
		entityManager.merge(entity);
		return super.__update(event, entity);
		
		
	}

	@Override
	public List<String> getNaturalVillage(Long adminId) {
		//加过滤and x.deleted=false
		String ql = "select x.naturalVillage from PeripheralRuralEntity x  where  x.adminRural.id=? and x.deleted=false";
		return __list(String.class, ql, adminId);
	}

	@Override
	public boolean check(Long id, IOperator opt) {
		String ql = "select x.modelArea.batch from AdministrationUncoreRuralEntity x where id=? and x.deleted=false";
		return __config().check(__first(String.class, ql, id), opt, false);
	}

	@Override
	public List<PeripheralRuralEntity> getRuralByAdminRuralId(Long id) {
		String ql = "select x from PeripheralRuralEntity x where x.adminRural.id=? and x.deleted=false";
		return __list(PeripheralRuralEntity.class, ql, id);
	}

}
