package cn.bonoon.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.entities.felicityVillage.FVReportEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.services.GenericService;

public interface FVReportService extends GenericService<FVReportEntity>{

	/**
	 * 提交给下面的县填报
	 * @param id
	 */
	void startReport(Long id);

	/**
	 * 所有的report
	 */
	List<FVReportEntity> allReport();

	/**
	 * 县级工作人员,点击开始上报
	 * 	创建县报
	 * @param reportId
	 */
	FVMACountyReportEntity toReport(Long reportId,LogonUser user);

	/**
	 * 数据表格的数据源
	 * @param id
	 * @param user
	 * @return
	 */
	List<Object> table1Items(Long reportId, LogonUser user);
	List<Object> table2Items(Long reportId, LogonUser user) ;
	List<Object> table3Items(Long reportId, LogonUser user) ;

	/**
	 * 根据表的类型导出表
	 * @param request
	 * @param response
	 * @param reportId
	 * @param tableType
	 */
	void exportTable(HttpServletRequest request, HttpServletResponse response,
			Long reportId, Integer tableType,LogonUser user);

	/**
	 * 退回
	 * @param id
	 */
	void rejectReport(Long id);
//	/**
//	 * 完成
//	 * @param id
//	 */
//	void finishReport(Long id);

	/**
	 * 通过
	 * @param id
	 */
	void paseReport(Long id);


}
