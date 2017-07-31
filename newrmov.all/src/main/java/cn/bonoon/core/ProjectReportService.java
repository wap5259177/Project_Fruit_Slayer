package cn.bonoon.core;

import java.util.List;
import java.util.TreeSet;

import cn.bonoon.core.json.ProjectReportSaveInfo;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ProjectReportEntity;
import cn.bonoon.entities.ProjectReportItem;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface ProjectReportService extends
		GenericService<ProjectReportEntity> {

	// @Deprecated
	// List<ProjectReportItem> getReport(Integer year, Integer month);

	// void newInvestments(Integer y, Integer m, Long[] pids, Long[] ids,
	// Double[] stateFunds, Double[] provinceFunds, Double[] localFunds,
	// Double[] socialFunds, Double[] otherFunds);

	List<ProjectReportEntity> reports(IOperator user, int year);

	ProjectReportEntity start(IOperator user, int year, int month);

	ProjectReportEntity report(IOperator user, Long id);

	/**
	 * 保存或提交一个项目的月报数据
	 * 
	 * @param ope
	 * @param id
	 * @param pv
	 * @param submit
	 */
	void save(IOperator ope, Long id, ParamenterValue pv, boolean submit);

	List<?> getAllLocalFunds(String batch, boolean cal);

	/**
	 * 已经上报的月度数据中最后一个月度 下标：0 - 年度、1 - 月度
	 * 
	 * @return
	 */
	int[] lastMonthly(String batch);

	/**
	 * <pre>
	 * 结果：
	 * 0 - 市ID
	 * 1 - 片区名
	 * 2 - 年度
	 * 3 - 月份
	 * 4 - 资金
	 * </pre>
	 * 
	 * @param batch
	 * @param startyear
	 * @param startMonth
	 * @param lastyear
	 * @param lastmonth
	 * @return
	 */
	List<Object[]> getFunds(String batch, int startyear, int startMonth,
			int lastyear, int lastmonth);

	int[] lastMonthly(Long id);

	/**
	 * <pre>
	 * 结果：
	 * 0 - 市ID
	 * 1 - 片区名
	 * 2 - 年度
	 * 3 - 月份
	 * 4 - 资金:专项资金
	 * 5 - 资金:中央
	 * 6 - 资金：省
	 * 7 - 资金：市
	 * 8 - 资金
	 * 9 - 资金
	 * 10 - 资金
	 * 11 - 资金
	 * </pre>
	 * 
	 * @param batch
	 * @param startyear
	 * @param startMonth
	 * @param lastyear
	 * @param lastmonth
	 * @return
	 */
	List<Object[]> getFundsByModelId(Long id, int startyear, int startMonth,
			int lastyear, int lastmonth);

	/**
	 * <pre>
	 * 结果：
	 * 0 - 市ID
	 * 1 - 片区名
	 * 2 - 片区实际投入总资金
	 * 3 - 片区预计投入总资金
	 * </pre>
	 * 
	 * @param batch
	 * @param schedule
	 * @param
	 * @param
	 * @param
	 * @return
	 */
	List<Object[]> getInvestment(double schedule, String batch);

	// ----------------------------------------
	/**
	 * <pre>
	 * 结果:
	 * 	县级下的:
	 * 0 - 资金:计划总投入
	 * 1 - 资金:国家资金(中央)
	 * 2 - 资金:省投入,其他省级资金
	 * 3 - 资金:当地政府投入
	 * 4 - 资金:省级补助资金
	 * 5 - 资金:计社会资金
	 * 6 - 资金:其他
	 * 7 - 资金:市级投入
	 * 8 - 资金:县级投入
	 * 9 - 资金:群众自筹
	 * </pre>
	 * 
	 * @param maid
	 * @return
	 */
	List<Object[]> getAllKindsOfFoundTotal(Long ownerId);// 计划总投入的

	List<Object[]> getFinishedAllKindsOfFoundTotal(Long ownerId);// 实际完成投入的

	/**
	 * <pre>
	 * 结果：
	 * 0 - 县ID
	 * 1 - 年度
	 * 2 - 月份
	 * 3 - 资金
	 * </pre>
	 * 
	 * @param batch
	 * @param startyear
	 * @param startMonth
	 * @param lastyear
	 * @param lastmonth
	 * @return
	 */
	List<ProjectReportEntity> getFunds(Long ownerId, int startyear,
			int startMonth, int lastyear, int lastmonth);

	List<ProjectReportEntity> getCityFundsUsed(Long placeId, String batch,
			int startyear, int startMonth, int lastyear, int lastmonth);

	/**
	 * <pre>
	 * 结果:
	 * 	市级下的:
	 * 0 - 资金:计划总投入
	 * 1 - 资金:国家资金(中央)
	 * 2 - 资金:省投入,其他省级资金
	 * 3 - 资金:当地政府投入
	 * 4 - 资金:省级补助资金
	 * 5 - 资金:计社会资金
	 * 6 - 资金:其他
	 * 7 - 资金:市级投入
	 * 8 - 资金:县级投入
	 * 9 - 资金:群众自筹
	 * </pre>
	 * 
	 * @param ownerId
	 * @param batch
	 * @return
	 */
	List<Object[]> getCityPredictFundTotal(Long ownerId, String batch);

	List<Object[]> getCityFinishedFundTotal(Long ownerId, String batch);

	List<ProjectReportEntity> getRepot(Long ownerId);

	List<ProjectReportEntity> getSumRepot(ModelAreaEntity modelAreaEntity,
			int year, int month);

	ProjectReportEntity getCurrent(ModelAreaEntity modelAreaEntity, int year,
			int month);

	Object[] getReportSummary(ModelAreaEntity modelAreaEntity);

	/**
	 * <pre>
	 * 由省办管理员退回一个项目月报，退回后县办的人员可以编辑
	 * 
	 * 退回编辑的是不允许编辑项目状态的，只允许修改一些数据，防止项目的状态出错
	 * </pre>
	 * 
	 * @param id
	 */
	void back(Long id, LogonUser user);

	/**
	 * 重新统计一个项目月报里的资金
	 * 
	 * @param id
	 */
	void capital(Long id, IOperator user);

	List<ProjectReportItem> reportItem(Long id);

	/**
	 * 由省办工作人员帮某一个县提交某个月份的项目月报
	 * 
	 * @param id
	 */
	void submit(Long id, IOperator user);

	/**
	 * 在第一次提交的时候，错把project.investment当作totalInvestment值进行处理；
	 * 所以有些项目的月报受到了影响，现在需要把受影响的月报调整过来。
	 * 
	 * @param id
	 */
	void resetProject(Long id);

	/**
	 * 首先，要进行处理之前需要有个退回操作，这样可以防止误操作
	 * 
	 * @param id
	 */
	void backToReset(Long id);

	void reCorrectt(Long id);

	void reUpdate(Long id);

	/**
	 * 这个月报的所有月报项
	 * 
	 * @param id
	 * @return
	 */
	List<ProjectReportItem> allItems(Long id);

	/**
	 * 保存月报
	 * 
	 * @param pr
	 */
	void saveReport(ProjectReportSaveInfo pr, Boolean submit, IOperator user);

	/**
	 * 获得该片区未提交或者是提交了退回的等待提交的月度报表所有月份
	 */
	TreeSet<Integer[]> getPeriod(Long mid);
}