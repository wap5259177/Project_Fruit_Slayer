package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.informatioin.CityCapitalInvestmentInformationEntity;
import cn.bonoon.entities.informatioin.ProvinceCapitalInvestmentInformationEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface PCInvestService extends GenericService<ProvinceCapitalInvestmentInformationEntity>{

	void startReport(Long id,IOperator user);

	void rejectReport(Long id,IOperator user);

	List<ProvinceCapitalInvestmentInformationEntity> allReport();

	/**
	 * 开始填报
	 * @param user
	 * @param reportId
	 */
	void toReport(LogonUser user, Long reportId);

	List<CityCapitalInvestmentInformationEntity> allCityReportByReport(Long pid);

	void passReport(Long id,IOperator user);

	void DeletedReport(Long id,IOperator user);

	/**
	 * double类型数字相加是有错误的，重新刷新一遍让他变成正确的
	 * 一条一条刷新
	 * @param id
	 */
	void refreshMn(Long id,IOperator user);

	/**double类型数字相加是有错误的，重新刷新一遍让他变成正确的
	 * 刷新这个填报下的所有市
	 * @param id
	 */
	void refresh(Long id,IOperator user);

	


}
