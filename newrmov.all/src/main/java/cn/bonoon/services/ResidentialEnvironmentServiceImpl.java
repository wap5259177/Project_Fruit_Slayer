package cn.bonoon.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.core.ResidentialEnvironmentService;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.ResidentialEnvironmentEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true) 
public class ResidentialEnvironmentServiceImpl extends AbstractService<ResidentialEnvironmentEntity> implements
		ResidentialEnvironmentService {

	@Override
	public List<ResidentialEnvironmentEntity> getResidentialEnvironment(Long oId) {
		String ql = "select x from ResidentialEnvironmentEntity x where x.ownerId=?";
		return __list(ResidentialEnvironmentEntity.class, ql, oId);
	}

	@Override
	public BaseRuralEntity getRural(Long rid) {
		return entityManager.find(BaseRuralEntity.class, rid);
	}

	@Transactional
	@Override
	public void save(List<ResidentialEnvironmentEntity> items) {
		if(null != items){
			for(ResidentialEnvironmentEntity re : items){
				entityManager.persist(re);
			}
		}
	}
	
	@Override
	public List<BaseRuralEntity> getRurals(Long uid) {
		return __list(BaseRuralEntity.class, "select x from BaseRuralEntity x where x.ownerId=?", uid);//and x.deleted=false
	}
	
	private final String statistics_ql = "select x.batch,x.reportAnnual,x.cityName,x.county,x.name,x.themeName"
			+ ",(select count(x14) from ResidentialEnvironmentEntity x14 where x14.annual=2014 and x14.rural.modelArea.id=x.id)"
			+ ",(select count(x15) from ResidentialEnvironmentEntity x15 where x15.annual=2015 and x15.rural.modelArea.id=x.id)"
			+ ",(select count(x16) from ResidentialEnvironmentEntity x16 where x16.annual=2016 and x16.rural.modelArea.id=x.id)"
			+ ",(select count(x17) from ResidentialEnvironmentEntity x17 where x17.annual=2017 and x17.rural.modelArea.id=x.id)"
			+ " from ModelAreaEntity x where x.deleted=false";// and x.status>0

	@Override
	public List<Object[]> statistics(String batch) {
		batch  = BatchHelper.get(batch);
		String ql = statistics_ql + " and x.batch=?";
		return __list(Object[].class, ql, batch);
	}

	@Override
	public List<Object[]> statisticsLocal(IOperator opt, String batch) {
		batch  = BatchHelper.get(batch);
		String ql = statistics_ql + " and x.ownerId=?";
		return __list(Object[].class, ql, opt.getOwnerId());
	}

	@Override
	public List<Object[]> statisticsCity(IOperator opt, String batch) {
		batch  = BatchHelper.get(batch);
		String ql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, ql, opt.getOwnerId());
		String ql0 = statistics_ql + " and x.cityId=?";
		return __list(Object[].class, ql0, pid);
	}

	@Override
	public ResidentialEnvironmentEntity findRes(Long id, int annual) {
		String ql="select r from ResidentialEnvironmentEntity r where r.rural.id=? and r.annual=?";
		return __first(ResidentialEnvironmentEntity.class,ql,id,annual);
	}
}
