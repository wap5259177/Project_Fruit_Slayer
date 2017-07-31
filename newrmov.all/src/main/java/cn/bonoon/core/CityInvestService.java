package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.informatioin.CityCapitalInvestmentInformationEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.GenericService;

public interface CityInvestService extends GenericService<CityCapitalInvestmentInformationEntity>{

	
	List<CityCapitalInvestmentInformationEntity>  allCountyReport(LogonUser user);

	void submitReport(Long id);

	

	


}
