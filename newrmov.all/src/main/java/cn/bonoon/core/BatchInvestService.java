package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.informatioin.BatchCapitalInvestmentInformationEntity;
import cn.bonoon.entities.informatioin.CityCapitalInvestmentInformationEntity;
import cn.bonoon.kernel.support.services.GenericService;

public interface BatchInvestService extends GenericService<BatchCapitalInvestmentInformationEntity>{

	void saveBatchs(List<BatchCapitalInvestmentInformationEntity> batchs,CityCapitalInvestmentInformationEntity cityReport);

	/**
	 * 市报下面所有的batchinvest
	 * @param id
	 * @return
	 */
	List<BatchCapitalInvestmentInformationEntity> allBatchInvestByCReportId(
			Long id);

	


}
