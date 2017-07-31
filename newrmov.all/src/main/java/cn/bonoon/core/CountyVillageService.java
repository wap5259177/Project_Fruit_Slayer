package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.informatioin.CityNaturalVillageInformationEntity;
import cn.bonoon.entities.informatioin.CountyNaturalVillageInformationEntity;
import cn.bonoon.kernel.support.services.GenericService;

public interface CountyVillageService extends GenericService<CountyNaturalVillageInformationEntity>{

	void saveCountys(List<CountyNaturalVillageInformationEntity> counties,CityNaturalVillageInformationEntity cityReport);

	List<CountyNaturalVillageInformationEntity> allVillagestByCReportId(Long id);

	/**
	 * 市报下面所有的countyVillage
	 * @param id
	 * @return
	 */
//	List<CountyNaturalVillageInformationEntity> allCountyVillagesByCReportId(
//			Long id);


	


}
