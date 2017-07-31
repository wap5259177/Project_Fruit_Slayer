package cn.bonoon.core;

import java.util.Date;
import java.util.List;

import cn.bonoon.entities.ModelAreaQuarterBatch;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.services.GenericService;

public interface CityQuarterReportService extends
		GenericService<ModelAreaQuarterItem> {
	void auditPass(OperateEvent event, Long id, String auditContent,
			String auditName, Date auditAt);

	void auditReject(OperateEvent event, Long id, String auditContent,
			String auditName, Date auditAt);

	// List<Object[]> getQuarterReport(Long ownerId, String batchName,int
	// period,int annual);
	// List<Object[]> getQuarterBatch(Long ownerId, String batchName, int
	// period,int annual);

	// String getCityN(Long ownerId);
	ModelAreaQuarterItem getItem(Long ownerId, int annual, String batchName,
			int period);

	// List<Object[]> getQuarterReport(String batchName, int period, int
	// annual);
	List<ModelAreaQuarterItem> getQuarterReport(String batchName, int period,
			int annual);

	ModelAreaQuarterItem getItem(String batchName, int annual, int period);

	ModelAreaQuarterBatch getBatch(String batchName, int annual, int period);

	/**
	 * 某个片区某个季度的工程进展季报
	 * 
	 * @param batchName
	 * @param annual
	 * @param period
	 * @param modelAreaId
	 */
	ModelAreaQuarterItem getItem(String batchName, int annual, int period,
			Long modelAreaId);

	/**
	 * 某个片区的工程进展季报
	 * 
	 * @param modelAreaId
	 * @param period
	 * @param annual
	 * @param batchName
	 * @return
	 */
	List<ModelAreaQuarterItem> getItems(Long modelAreaId, int period, int annual);

	/**
	 * bug
	 * 
	 * @param item
	 *            判断传入的ModelAreaQuarterItem item 行政村数 自然村数 户数 人数 是否有为0的情况
	 *            有的賦值為上一季度状态不是未提交状态的而且那個季度的對應行政村数 自然村数 户数 人数的值
	 */
	void updateItem4ParamToLast(List<ModelAreaQuarterItem> items);
}
