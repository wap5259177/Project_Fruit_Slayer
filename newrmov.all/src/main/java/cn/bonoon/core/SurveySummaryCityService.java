package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.SurveySummaryCityEntity;
import cn.bonoon.entities.SurveySummaryCountyEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.SearchService;

public interface SurveySummaryCityService extends
		SearchService<SurveySummaryCityEntity> {

	/**
	 * 未接收的状态
	 */
	int notReceived = 0;

	int editing = 2;

	int finish = 1;

	/**
	 * 把该市排除在外
	 */
	int exclude = 3;

	void toReport(IOperator opt, Long id);

	void toFinish(Long id);

	List<SurveySummaryCountyEntity> countyReports(Long id);

	SurveySummaryCountyEntity county(Long id);

	void save(SurveySummaryCountyEntity ssc);

	List<SurveySummaryCityEntity> get(IOperator user);

	List<SurveySummaryCityEntity> getUrges(IOperator user);

	List<SurveySummaryCityEntity> get(IOperator user, int count);

	SurveySummaryCityEntity getCity(Long id);

	void refresh(IOperator opt, Long id);

	public List<Integer> getAllAnnual(String cityName);

	List<SurveySummaryCountyEntity> getLastYearSurveySummaryCityEntityList(
			String cityName, int lastAnnual);

}
