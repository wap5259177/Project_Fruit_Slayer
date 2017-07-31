package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.informatioin.CityNaturalVillageInformationEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.GenericService;

public interface CityVillageService extends GenericService<CityNaturalVillageInformationEntity>{

	List<CityNaturalVillageInformationEntity> allCountyReport(LogonUser user);

	void submitReport(Long id);


	


}
