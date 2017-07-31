package cn.bonoon.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.FelicityCountyService;
import cn.bonoon.core.IFelicityCountyInserter;
import cn.bonoon.entities.FelicityCountyEntity;
import cn.bonoon.entities.FelicityVillageEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.services.AbstractService;

/**
 * 幸福村居
 * @author jackson
 *
 */
@Service
@Transactional(readOnly = true)
public class FelicityCountyServiceImpl extends AbstractService<FelicityCountyEntity> implements FelicityCountyService{

	@Override
	protected FelicityCountyEntity __save(OperateEvent event, FelicityCountyEntity entity) {
		boolean isCreate;
		if(event.is("applicant-create")){
			entity.setStatus(0);
			isCreate = true;
		}else{
			entity.setStatus(-1);
			isCreate = false;
		}
		entity.setCreateAt(event.now());
		entity.setCreatorId(event.getId());
		entity.setCreatorName(event.getUsername());
		entity.setOwnerId(event.getOwnerId());
		UnitEntity ue = entityManager.find(UnitEntity.class, event.getOwnerId());
		PlaceEntity place = ue.getPlace(), city = place.getParent();
		entity.setUnit(ue);
		entity.setPlace(place);
		entity.setName(place.getName());
		if(null != city){
			entity.setCityId(city.getId());
			entity.setCityName(city.getName());
		}
		
		super.__save(event, entity);
		
		boolean canCreate = __readVillages(event, entity);

		if(isCreate && !canCreate){
			throw new RuntimeException("请填写幸福村居对应的村信息！");
		}
		return entity;
	}

	private boolean __readVillages(OperateEvent event, FelicityCountyEntity entity) {
		String[] ordinals = ((IFelicityCountyInserter)event.getSource()).getOrdinals();
		boolean canCreate = false;
		if(null != ordinals){
			for(String or : ordinals){
				Long vid = event.getLong("id_" + or);
				if(null == vid) continue;
				PlaceEntity pe = entityManager.find(PlaceEntity.class, vid);
				if(null == pe) continue;
				FelicityVillageEntity fve = new FelicityVillageEntity();
				fve.setCreateAt(event.now());
				fve.setCreatorId(event.getId());
				fve.setCreatorName(event.getUsername());
				fve.setOwnerId(event.getOwnerId());
				fve.setPlace(pe);
				fve.setCounty(entity);
				
				fve.setName(pe.getName());
				fve.setTownName(pe.getParent().getName());
				
				fve.setNaturalVillage(event.getString("nn_" + or));
				fve.setVillageType(event.getString("vt_" + or));
				fve.setConstructionType(event.getString("ct_" + or));
				entityManager.persist(fve);
				canCreate = true;
			}
		}
		return canCreate;
	}
	
	@Override
	protected FelicityCountyEntity __update(OperateEvent event, FelicityCountyEntity entity) {
		boolean canCreate = false;
		String ql = "select x from FelicityVillageEntity x where x.county.id=?";
		for(FelicityVillageEntity fv : __list(FelicityVillageEntity.class, ql, entity.getId())){
			Long fid = fv.getId();
			if(fid.equals(event.getLong("sid_" + fid))){
				fv.setNaturalVillage(event.getString("snn_" + fid));
				fv.setVillageType(event.getString("svt_" + fid));
				fv.setConstructionType(event.getString("sct_" + fid));
				fv.setDeleted(false);
				entityManager.merge(fv);
				canCreate = true;
			}else{
				entityManager.remove(fv);
			}
		}

		canCreate = __readVillages(event, entity) || canCreate;
		
		if(event.is("applicant-create")){
			if(!canCreate){
				throw new RuntimeException("请填写幸福村居对应的村信息！");
			}
			entity.setStatus(0);
		}else{
			entity.setStatus(-1);
		}
		
		return super.__update(event, entity);
	}

	@Override
	public List<String> summary(Long oid) {
		String ql = "select x.name from FelicityVillageEntity x where x.deleted=false and x.county.deleted=false and x.ownerId=? and x.county.status>=0";
		return __list(String.class, ql, oid);
	}
	
	@Override
	public UnitEntity getUnit(Long uid) {
		return entityManager.find(UnitEntity.class, uid);
	}

	@Override
	public FelicityCountyEntity getByOwner(Long uid) {
		String ql = "select x from FelicityCountyEntity x where x.ownerId=?";
		return __first(FelicityCountyEntity.class, ql, uid);
	}

	@Override
	public List<Object[]> getVillages(Long id) {
		String ql = "select x.id,x.townName,x.name,x.naturalVillage,x.villageType,x.constructionType from FelicityVillageEntity x where x.county.id=?";
		return __list(Object[].class, ql, id);
	}
}
