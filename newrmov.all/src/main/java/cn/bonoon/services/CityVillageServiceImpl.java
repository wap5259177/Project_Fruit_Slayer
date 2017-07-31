package cn.bonoon.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.CityVillageService;
import cn.bonoon.entities.informatioin.CityNaturalVillageInformationEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class CityVillageServiceImpl extends AbstractService<CityNaturalVillageInformationEntity> implements CityVillageService{

	@Override
	public List<CityNaturalVillageInformationEntity> allCountyReport(LogonUser user) {
		String pql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, pql,user.getOwnerId());
		String ql = "select x from CityNaturalVillageInformationEntity x where x.place.id=? and x.deleted=false";
		return __list(CityNaturalVillageInformationEntity.class, ql,pid);
	}

	@Override
	@Transactional
	public void submitReport(Long id) {
		CityNaturalVillageInformationEntity entity = __get(id);
		entity.setStatus(4);
		entityManager.merge(entity);
	}
}
