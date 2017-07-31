package cn.bonoon.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bonoon.core.project.MaProjectInfo;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.ProjectReportItem;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.GenericService;

public interface ProjectService extends GenericService<ProjectEntity> {

	// @Deprecated
	// List<ProjectEntity> getProject(Long newRural);

	List<Object[]> newRurals(IOperator opt);

	List<Object[]> peripheralRurals(IOperator opt);

	// 通过片区的id查找村
	List<Object[]> newRurals(Long id);

	List<Object[]> peripheralRurals(Long id);

	// DirectoryEntity dir_building(Long id, int extattr); // extattr
	// 33：建设前，34：建设中，35，建设后

	/**
	 * <pre>
	 * 片区名、月份、总数、已提交数
	 * 
	 * 按片区名和月份进行排序
	 * </pre>
	 * 
	 * @param owner
	 * @param year
	 * @return
	 */
	List<Object[]> getStatus(Long owner, int year);

	Collection<Object[]> statistics(String batch);

	Collection<Object[]> statisticsLocal(IOperator opt, String batch);

	Collection<Object[]> statisticsCity(IOperator opt, String batch);

	// DirectoryEntity dir_buildingOrCreate(IOperator opt, Long id, int
	// intValue);

	List<FileEntity> medias(Long id, int code, String buildType);

	// List<FileEntity> annexes(Long id, long apid, String code);
	void saveMedia(ProjectEntity pe, FileEntity _file);

	void reportDetail(LogonUser user, Long id);

	void back(Long id, LogonUser user);

	List<ProjectReportItem> getProjectReport(Long id);

	/**
	 * 删除指定项目的初始累计资金
	 * 
	 * @param id
	 * @throws Exception
	 */
	void clear(Long id, LogonUser user) throws Exception;

	Object[] getSumItem(Long id);

	/**
	 * 重新统计一个项目的所有月报累计上来的资金
	 * 
	 * @param id
	 */
	void restatistic(Long id, LogonUser user);

	/**
	 * 拿到项目的所有图片
	 * 
	 * @param id
	 * @return
	 */
	List<FileEntity> getPic(Long id);

	/**
	 * 退回修改，将ProjectEntity的status=4
	 * 
	 * @param id
	 */
	void backToUpdate(Long id, LogonUser user);

	/**
	 * 修改项目所在村
	 * 
	 * @param id
	 */
	void updatePjRural(Long id, Long ruralId, LogonUser user);

	/**
	 * 修改项目的状态
	 * 
	 * @param id
	 * @param status
	 */
	void updatePjStatus(Long id, Integer status, LogonUser user);

	/**
	 * 拿到所有项目
	 */
	List<ProjectEntity> allProjects();

	/**
	 * 根据条件拿到项目
	 * 
	 * @param batch
	 * @param cityName
	 * @param maName
	 * @param status
	 * @return
	 */
	List<MaProjectInfo> allProInfosBycondition(String batch, String cityName,
			String maName, int status);

	/**
	 * 导出示范片项目一览表
	 * 
	 * @param batch
	 * @param cityName
	 * @param maName
	 * @param status
	 */
	void exportMaPros(HttpServletRequest request, HttpServletResponse response,
			String batch, String cityName, String maName, int status);

	/**
	 * 完成项目修改
	 * 
	 * @param id
	 */
	void pjUpdated(Long id);

	String deleteProject(Long id);

	/**
	 * 在某个片区下按项目名项目编号找项目
	 * 
	 * @param projectCode
	 * @param projectName
	 * @param modelAreaId
	 * 
	 * @param perriodSet
	 *            所有月度报表的月度
	 * @return 项目以及未有
	 */
	TreeMap<ProjectEntity, TreeSet<Integer[]>> getProjectAndProNoItemPerriod(
			String projectCode, String projectName, Long modelAreaId,
			TreeSet<Integer[]> perriodSet);

	/**
	 * 查询指定月度没有月报的项目
	 * 
	 * @return
	 */
	List<ProjectReportItem> notSetReoprt(Long projectId, int whitchYear,
			int whitchMonth, Long modelAreaId);

	/**
	 * 更新某个月报所属月度报表
	 * 
	 * @param itemId
	 * @param reportId
	 * @return
	 */
	String setProRepItemReportParam(Long mid,Long projectId, Integer reportPerriod);
	
	
	/**
	 * 获取某个片区项目创建时最大年月 最小年月
	 *
	 */
	List<int[]> getProjectTime(Long mid);
}

