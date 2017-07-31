package cn.bonoon.core;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.bonoon.controllers.project.reportManager.ProjectImageShowEntity;
import cn.bonoon.core.configs.ModelAreaConfig;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.AdministrationUncoreRuralEntity;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.CommissionerEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.IndustryAreaEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaPointEntity;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.ResidentialEnvironmentEntity;
import cn.bonoon.entities.RuralPointEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;
import cn.bonoon.kernel.web.models.JsonResult;

public interface ModelAreaService extends GenericService<ModelAreaEntity>,
		ApplicantStatus {
	
	
	void auditPass(OperateEvent event, Long id);

	void auditReject(OperateEvent event, Long id);

	UnitEntity getUnit(Long uid);

	void submit(Long id, IOperator ope);

	/**
	 * 摘要的统计信息 Object[] = {'id', 'name', 'status'}
	 * 
	 * @return
	 */
	List<Object[]> summary(Long uid);

	/**
	 * 示范片区下的核心村的情况 Object[] = {'status', 'count'}
	 * 
	 * @param id
	 * @return
	 */
	List<Object[]> nrurals(Long id);

	/**
	 * 
	 * 示范片区下的非主体村的情况 Object[] = {'status', 'count'}
	 * 
	 * @param id
	 * @return
	 */
	List<Object[]> prurals(Long id);

	/**
	 * 示范片区下的产业发展情况 Object[] = {'status', 'count'}
	 * 
	 * @param id
	 * @return
	 */
	List<Object[]> iarea(Long id);

	List<Object[]> newRurals(IOperator opt);

	List<Object[]> peripheralRurals(IOperator opt);

	// void createDirViedo(OperateEvent event, NewRuralEntity newRural);

	// 片区ID，片区名、年度
	List<Object[]> cityModelArea(Long owner);

	List<ModelAreaPointEntity> cityModelAreaPoint(Long owner);

	List<RuralPointEntity> cityRuralPoint(Long owner);

	List<BaseRuralEntity> getRurals(Long id);

	// 村ID，村名
	Collection<Object[]> getNewRurals(Long id);

	List<NewRuralEntity> getNewRurals1(Long id);

	Collection<Object[]> getPeripheralRurals(Long id);

	List<Object[]> getIndustries(Long id);

	List<IndustryAreaEntity> getIndustries_(Long id);

	List<Object[]> getProjects(Long id);

	List<Object[]> getProjects_type(Long id);

	List<ResidentialEnvironmentEntity> getResidentialEnvironments(Long id);

	ModelAreaEntity getNeedAudit(Long owner);

	void pass(Long id, String content, String name, Date date);

	void reject(Long id, String content, String name, Date date);

	/**
	 * <pre>
	 * 返回的是map，字段：x.batch,x.id,x.name,x.ordinalModel,x.county
	 * </pre>
	 * 
	 * @return
	 */
	Map<Object, List<Object[]>> items();

	List<Object[]> statistics(String batch);

	List<Object[]> statisticsLocal(IOperator opt, String batch);

	List<Object[]> statisticsCity(IOperator opt, String batch);

	List<Object[]> getplace(String batch);

	String getPlaceName(Long id);

	String getNewRural(Long id);

	String getPeripheral(Long id);

	List<ModelAreaEntity[]> getplace1(String batch);

	String getKey(Long uid);

	ModelAreaEntity getByOnwer(Long uid);

	List<Object[]> newRurals(Long maid);

	List<Object[]> peripheralRurals(Long maid);

	CommissionerEntity getByUnit(Long uid);

	Long getNewRuralFirstId(Long id);

	Long getNewRuralLastId(Long id);

	Long getPeripheralFirstId(Long id);

	Long getPeripheralLastId(Long id);

	void back(Long id, IOperator user);

	void ordinal(Long[] ids, Map<?, ?> map, IOperator user);

	void returnMA(Long id, IOperator user);

	ModelAreaConfig getConfig();

	// 验证是该批次是否关闭填报功能
	boolean check(Long id, IOperator opt);

	void optimize(IOperator opt, OperateEvent event, Long id);

	/**
	 * <pre>
	 * 当前的进度：
	 * 0 - 批次
	 * 1 - 状态
	 * 2 - 片区名
	 * 3 - 县名
	 * </pre>
	 * 
	 * @return
	 */
	List<Object[]> progress();

	int countBy(Class<?> clsName);

	/**
	 * 提取功能，即自然村转为行政村的一些字段，自动从自然村提取到相应的行政村
	 * 
	 * @param id
	 */
	void extract(Long id);

	void refreshing(Long id, IOperator user);

	Object[] getSum(Long ModelAreaId);

	Object[] getPSum(Long ModelAreaId);

	void extractHouse(Long ModelAreaId, IOperator user);

	CommissionerEntity getByUser(IOperator user);

	// -------------------------------------------优化系统----------------------------------------------------------
	/**
	 * 
	 * @param batch
	 * @return
	 */
	List<Object[]> getModelAreaByBatch(String batch);

	List<ModelAreaEntity> getModelArea(String batch);

	Map<String, Object> getMa_title_show(Long maid);// 进入到片区后,显示一些重要的信息

	/**
	 * 删除项目的初始的累计资金
	 * 
	 * @param id
	 */
	void clearProjectFunds(Long id, IOperator user);

	/**
	 * 删除某一个月份的项目月报
	 * 
	 * @param id
	 * @param py
	 * @param pm
	 */
	void removeProjectMonthly(Long id, int py, int pm, IOperator user);

	/**
	 * 可以改变指定片区的批次
	 * 
	 * @param id
	 * @param batch
	 */
	void changeBacth(Long id, String batch, IOperator user);

	List<Object[]> basicStatistics(String batch);

	List<Object[]> totalStatistics(String batch);

	List<Object[]> buildStatistics(String batch);

	/**
	 * 获取行政村
	 * 
	 * @param id
	 * @return
	 */
	List<AdministrationCoreRuralEntity> getAdminRural(Long id);

	List<AdministrationUncoreRuralEntity> getUCAdminRural(Long id);

	/**
	 * 获取片区的季度报表
	 * 
	 * @return
	 */
	List<ModelAreaQuarterItem> getAreaQuarterItems(Long id);

	/**
	 * 分组获取,示范片完成综合整治年度
	 * 
	 * @param id
	 * @return
	 */
	List<Integer> getResidentialEnvironmentsGroupByAnnual(Long id);

	// 通过年度获取整治名单
	List<ResidentialEnvironmentEntity> getResidentialEnvironmentsByAnnual(
			Long id, Integer annual);

	/**
	 * 省下的所有片区
	 * 
	 * @return
	 */
	List<ModelAreaEntity> allModelArea();

	/**
	 * 统计主体村、非主体村项目投入资金
	 * 
	 * @param ma
	 * @return
	 */
	ModelAreaStatisInfo statisticsInvest(ModelAreaEntity ma);

	List<Object[]> totalStatisticsNew(String batch);

	List<Object[]> buildStatisticsNew(String batch);

	/**
	 * 按时间段查询某个片区的项目
	 * 
	 * @param id
	 * @param year
	 * @param month
	 *            某个月份
	 * @param monthInterval
	 *            月份间隔 eg：默认为0即查询的是当前month指定的月份， monthInterval=1则为
	 *            [month,month+1]该区间
	 * @return
	 */
	List<ProjectImageShowEntity> recentAddProjectByModelArea(Long id, int year, int month,
			int monthInterval,int imageManageViewWidth,int imageMannageViewHeight,String projectName) ;
	
	/**
	 * 示范片图片
	 */
	void saveMedia(ModelAreaEntity ma, FileEntity _file); 
}
