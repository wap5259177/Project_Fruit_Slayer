package cn.bonoon.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaQuarterBatch;
import cn.bonoon.entities.ModelAreaQuarterEntity;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface StatisticsScheduleService extends
		GenericService<ModelAreaQuarterEntity> {

	List<ModelAreaQuarterItem> itemSurveies(Long id);

	List<ModelAreaQuarterBatch> batchSurveies(Long pid);

	void urge(Long id, IOperator opt);

	void back(Long id, IOperator opt);

	void ban(Long id, IOperator opt);

	void regain(Long id, IOperator opt);

	List<ModelAreaQuarterEntity> get(IOperator user, int count);

	void refreshItem(LogonUser user, Long id);

	void finishEntity(LogonUser user, Long id);

	void lockItem(LogonUser user, Long id, IOperator opt);

	// 20160126解锁
	void unlock(Long id);

	List<ModelAreaQuarterBatch> allBatchs();

	/**
	 * 导出主体村项目资金
	 * 
	 * @param request
	 * @param response
	 */
	void exportCoreRuralPjInvest(HttpServletRequest request,
			HttpServletResponse response, List<ModelAreaStatisInfo> items);

	/**
	 * 导出非主体村项目资金
	 * 
	 * @param request
	 * @param response
	 * @param items
	 */
	void exportUnCoreRuralPjInvest(HttpServletRequest request,
			HttpServletResponse response, List<ModelAreaStatisInfo> items);

	/**
	 * 通过以下条件查询项目的资金投入情况
	 */
	ModelAreaStatisInfo statisProInvest(ModelAreaEntity ma, String batch,
			int ruralType, int year, int period, int status);

	/**
	 * 导出
	 */
	void exportRuralPjInvest(HttpServletRequest request,
			HttpServletResponse response, List<ModelAreaStatisInfo> items,
			int ruralType);

	/**
	 * 重新给原始的自然村的9个指标赋值 目的：已完成的指标在下一季度就不要给用户勾选
	 */
	void refreshRural(LogonUser user, Long id);

	/**
	 * 导出第一二批完成指标的百分比
	 */
	void exportNfPercent(HttpServletRequest request,
			HttpServletResponse response, Long id);

	// void exportNfPercent(HttpServletRequest request,
	// HttpServletResponse response, Long id);

	String upadateBatch(Long itemId, int batch, Long quarterId, IOperator opt);

	/**
	 * 获取所有季度并排序：从最新季度降序排序
	 * 
	 * @return
	 */
	List<Object[]> periodSet();
}
