package cn.bonoon.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.CityInvestService;
import cn.bonoon.entities.informatioin.CityCapitalInvestmentInformationEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class CityInvestServiceImpl extends AbstractService<CityCapitalInvestmentInformationEntity> implements CityInvestService{

	@Override
	public List<CityCapitalInvestmentInformationEntity> allCountyReport(LogonUser user) {
		String pql = "select x.place.id from UnitEntity x where x.id=?";
		Long pid = __first(Long.class, pql,user.getOwnerId());
		String ql = "select x from CityCapitalInvestmentInformationEntity x where x.place.id=? and x.deleted=false";
		return __list(CityCapitalInvestmentInformationEntity.class, ql,pid);
	}

	@Override
	@Transactional
	public void submitReport(Long id) {
		CityCapitalInvestmentInformationEntity entity = __get(id);
		entity.setStatus(4);//4:待审核
		entityManager.merge(entity);
	}

	

	
	
	

}
