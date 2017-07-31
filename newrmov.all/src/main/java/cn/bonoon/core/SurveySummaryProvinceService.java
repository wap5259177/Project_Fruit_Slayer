package cn.bonoon.core;

import java.util.List;

import cn.bonoon.entities.SurveySummaryCityEntity;
import cn.bonoon.entities.SurveySummaryCountyEntity;
import cn.bonoon.entities.SurveySummaryProvinceEntity;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface SurveySummaryProvinceService extends GenericService<SurveySummaryProvinceEntity>{

	List<SurveySummaryCountyEntity> countySurveies(Long pid, Long id);

	List<SurveySummaryCityEntity> citySurveies(Long pid);

	/**
	 * 催报某一市
	 * @param id
	 */
	void urge(Long id,IOperator user);

	List<SurveySummaryProvinceEntity> get(IOperator user, int count);

	/**
	 * 退回某一市
	 * @param id
	 */
	void back(Long id,IOperator user);
	
	/**
	 * 驳回某一市
	 * @param id
	 */
	void reject(Long id,IOperator user);

	/**
	 * 完成某一年度的全省
	 * @param id
	 */
	void finish(Long id,IOperator user);

	/**
	 * 驳回某一市
	 * 重新统计
	 * @param id
	 */
	void restat(Long id,IOperator user);

	void exclude(Long id,IOperator user);

	void recover(Long id,IOperator user);

}
