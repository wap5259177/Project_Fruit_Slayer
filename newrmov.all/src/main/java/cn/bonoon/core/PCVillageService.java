package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.informatioin.CityNaturalVillageInformationEntity;
import cn.bonoon.entities.informatioin.ProvinceNaturalVillageInformationEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface PCVillageService extends GenericService<ProvinceNaturalVillageInformationEntity>{

	void startReport(Long id,IOperator user);

	void rejectReport(Long id,IOperator user);

	List<ProvinceNaturalVillageInformationEntity> allReport();

	void toReport(LogonUser user, Long reportId);

	List<CityNaturalVillageInformationEntity> allCityReportByReport(Long pid);

	void passReport(Long id,IOperator user);

	void DeletedReport(Long id,IOperator user);


	


}
