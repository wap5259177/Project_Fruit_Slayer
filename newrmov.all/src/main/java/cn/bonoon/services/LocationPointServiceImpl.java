package cn.bonoon.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.LocationPointService;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaPointEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.RuralPanoramaEntity;
import cn.bonoon.entities.RuralPointEntity;
import cn.bonoon.kernel.support.services.ServiceSupport;

@Service
@Transactional(readOnly = true)
public class LocationPointServiceImpl extends ServiceSupport implements LocationPointService{

	//查出片区位置
	@Override
	public ModelAreaPointEntity findModelAreaPointByCurrUser(Long oid) {
		String ql = "from ModelAreaPointEntity x where x.modelArea.deleted=false and x.modelArea.ownerId=?";
		return __first(ModelAreaPointEntity.class, ql, oid);
	}

	//示范片信息
	@Override
	public ModelAreaEntity findModelAreaByCurrUser(Long oid) {
		String ql = "from ModelAreaEntity x where x.deleted=false and x.ownerId=?";
		return __first(ModelAreaEntity.class, ql, oid);
	}

	
	
	//创建并保存片区地理位置信息
	@Override
	@Transactional
	public void commitModelAreaPoint(ModelAreaPointEntity modelAreaPoint) {
		entityManager.persist(modelAreaPoint);
		
	}

	//通过id查找modelArea
	@Override
	public ModelAreaEntity findModelAreaById(Long id) {
		String ql = "from ModelAreaEntity x where x.deleted=false and x.id=?";
		return __single(ModelAreaEntity.class, ql, id);
	}

	//通过id查找modelAreaPoint
	@Override
	public ModelAreaPointEntity findModelAreaPointById(Long id) {
		String ql = "from ModelAreaPointEntity x where x.id=?";
		return __single(ModelAreaPointEntity.class, ql, id);
	}
	

	//片区编辑
	@Override
	@Transactional
	public void updateModelAreaPoint(ModelAreaPointEntity modelAreaPoint) {
		entityManager.merge(modelAreaPoint);
	}
	
	

	//通过片区id查找他下面的所有的村子包括核心村和非主体村
	@Override
	public List<NewRuralEntity> findAllNewRuralBymodelAreaId(Long id) {
//		String  ql = "from NewRuralEntity x where x.modelArea.id=?";
		String  ql = "from NewRuralEntity x where x.modelArea.id=? and x.deleted=false";
		return __list(NewRuralEntity.class, ql, id);
	}
	
	@Override
	public List<PeripheralRuralEntity> findAllPeripheralRuralBymodelAreaId(
			Long id) {
//		String  ql = "from PeripheralRuralEntity x where x.modelArea.id=?";
		String  ql = "from PeripheralRuralEntity x where x.modelArea.id=? and x.deleted=false";
		return __list(PeripheralRuralEntity.class, ql, id);
	}

	
	@Override
	public BaseRuralEntity findRuralById(Long id) {
		String ql = "from BaseRuralEntity x where x.deleted=false and x.id=?";
		List<BaseRuralEntity> rurals = entityManager.createQuery(ql,BaseRuralEntity.class).setParameter(1, id).getResultList();
		if(rurals.size()>0){
			return rurals.get(0);
		}else{
			return null;
		}
	}

	/*
	 * 保存,创建村子信息,定位点
	 */
	@Override
	@Transactional
	public void commitRuralPoint(RuralPointEntity ruralPoint) {
		entityManager.persist(ruralPoint);
	}
	
	@Override
	@Transactional
	public void updateRuralPoint(RuralPointEntity ruralPoint) {
		entityManager.merge(ruralPoint);
	}
	
	@Override
	public RuralPointEntity findRuralPointByRuralId(Long id) {
		String ql =  "select x from RuralPointEntity x where x.rural.id=?";
		List<RuralPointEntity> ls =  entityManager.createQuery(ql, RuralPointEntity.class).setParameter(1, id).getResultList();
		if(ls.size()>0){
			return ls.get(0);
		}else{
			return null;
		}
	}

	/*
	 *示范片区下的所有村子 位置
	 */
	@Override
	public List<RuralPointEntity> findRuralPointByMapointId(Long id) {
		String ql  = "select x from RuralPointEntity x where x.modelAreaPoint.id=? and x.modelArea.deleted=false";
		return __list(RuralPointEntity.class, ql, id);
	}
	

	@Override
	public List<ModelAreaPointEntity> getAll() {
		String ql = "select x from ModelAreaPointEntity x where x.modelArea.deleted=false";
		return __list(ModelAreaPointEntity.class, ql);
	}

	//所有片区下的所有村子
	@Override
	public List<BaseRuralEntity> findAllRural() {
		String ql = "select x from BaseRuralEntity x where x.modelArea.deleted=false";
		return __list(BaseRuralEntity.class, ql);
	}

	//所有片区点下的所有村子点
	@Override
	public List<RuralPointEntity> findAllRuralPoint() {
		String ql = "select x from RuralPointEntity x where x.modelArea.deleted=false";
		return __list(RuralPointEntity.class, ql);
	}

	
	//保存全景点
	@Override
	@Transactional
	public void savePanoramaPoint(RuralPanoramaEntity ruralPano) {
		entityManager.persist(ruralPano);
	}

	@Override
	public RuralPanoramaEntity findRuralPanoById(Long id) {
		String ql = "select x from RuralPanoramaEntity x where x.modelArea.deleted=false and x.id=?";
		return __first(RuralPanoramaEntity.class, ql, id);
		
	}

	@Override
	public List<RuralPanoramaEntity> findAllRuralPano() {
		String ql = "select x from RuralPanoramaEntity x where x.modelArea.deleted=false";
		return __list(RuralPanoramaEntity.class, ql);
	}

	@Override
	public List<RuralPanoramaEntity> findRuralPanoByRuralId(Long id) {
		String ql = "select x from RuralPanoramaEntity x where x.modelArea.deleted=false and x.rural.id=?";
		return __list(RuralPanoramaEntity.class, ql, id);
	}
	
	
}
