package cn.bonoon.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.PCVillageService;
import cn.bonoon.entities.informatioin.CityNaturalVillageInformationEntity;
import cn.bonoon.entities.informatioin.CountyNaturalVillageInformationEntity;
import cn.bonoon.entities.informatioin.ProvinceNaturalVillageInformationEntity;
import cn.bonoon.entities.logs.HandleLogEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class PCVillageServiceImpl extends AbstractService<ProvinceNaturalVillageInformationEntity> implements PCVillageService{

	@Override
	protected ProvinceNaturalVillageInformationEntity __save(OperateEvent event, ProvinceNaturalVillageInformationEntity entity) {
		return super.__save(event, entity);
	}

	@Override
	@Transactional
	public void startReport(Long id,IOperator user) {
		ProvinceNaturalVillageInformationEntity entity = __get(id);
		entity.setStatus(1);
		entityManager.merge(entity);
		/**记录自然村提交报表的操作****/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(entity.getId());
		hlog.setTargetType(ProvinceNaturalVillageInformationEntity.class.getName());
		hlog.setContent("提交自然村报表");
		entityManager.persist(hlog);
	}

	//退回
	@Override
	@Transactional
	public void rejectReport(Long id,IOperator user) {
		CityNaturalVillageInformationEntity entity = entityManager.find(CityNaturalVillageInformationEntity.class, id);
		entity.setStatus(3);
		entityManager.merge(entity);
		/**记录自然村退回报表的操作****/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(entity.getId());
		hlog.setTargetType(CityNaturalVillageInformationEntity.class.getName());
		hlog.setContent("退回"+entity.getName()+"自然村报表");
		entityManager.persist(hlog);
	}

	@Override
	public List<ProvinceNaturalVillageInformationEntity> allReport() {
		String ql = "select x from ProvinceNaturalVillageInformationEntity x where x.status in (1,2,3) and x.deleted=false";
		return __list(ProvinceNaturalVillageInformationEntity.class, ql);
	}

	@Override
	@Transactional
	public void toReport(LogonUser user, Long reportId) {
//		String ql = "select x from PlaceEntity x where x.id=? and x.deleted=false";
//		PlaceEntity place = __first(PlaceEntity.class, ql,user.getOwnerId());
		
		String pql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, pql,user.getOwnerId());
		PlaceEntity place = entityManager.find(PlaceEntity.class, pid);
		ProvinceNaturalVillageInformationEntity entity = __get(reportId);
		
		CityNaturalVillageInformationEntity cnii = new CityNaturalVillageInformationEntity();
		cnii.setPnvInformation(entity);
		cnii.setPlace(place);
		
		
		cnii.setCreateAt(new Date());//这个是不能为空的
		cnii.setCreatorId(user.getId());
		cnii.setCreatorName(user.getUsername());
		
		cnii.setName(place.getName());
		cnii.setStatus(2);//0:未开始 1:完成  2:正在填报
		entityManager.persist(cnii);
		
		/*
		 * 这个市有多少个县就创建多少条:
		 * CountyNaturalVillageInformationEntity
		 */
		List<PlaceEntity> countys = cnii.getPlace().getChildren();
		for(PlaceEntity p:countys){
			CountyNaturalVillageInformationEntity coii = new CountyNaturalVillageInformationEntity();
			coii.setCnvInformation(cnii);
			coii.setPlace(place);
			coii.setName(p.getName());
			entityManager.persist(coii);
		}
		
	}

	@Override
	public List<CityNaturalVillageInformationEntity> allCityReportByReport(
			Long pid) {
		String ql = "select x from CityNaturalVillageInformationEntity x where x.pnvInformation.id=?and x.deleted=false";
		return __list(CityNaturalVillageInformationEntity.class, ql,pid);
	}

	@Override
	@Transactional
	public void passReport(Long id,IOperator user) {
		CityNaturalVillageInformationEntity entity = entityManager.find(CityNaturalVillageInformationEntity.class, id);
		entity.setStatus(1);
		entityManager.merge(entity);
		/**记录自然村通过报表的操作****/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(entity.getId());
		hlog.setTargetType(CityNaturalVillageInformationEntity.class.getName());
		hlog.setContent("通过"+entity.getName()+"自然村报表");
		entityManager.persist(hlog);
		
	}

	@Override
	@Transactional
	public void DeletedReport(Long id,IOperator user) {
		CityNaturalVillageInformationEntity entity = entityManager.find(CityNaturalVillageInformationEntity.class, id);
		entity.setDeleted(true);
		entityManager.merge(entity);
		/**记录自然村退回填报的操作****/
		HandleLogEntity hlog = new HandleLogEntity();
		hlog.currentUser(user);
		hlog.setTargetId(entity.getId());
		hlog.setTargetType(CityNaturalVillageInformationEntity.class.getName());
		hlog.setContent("删除"+entity.getName()+"自然村报表");
		entityManager.persist(hlog);
	}


	
	
	

}
