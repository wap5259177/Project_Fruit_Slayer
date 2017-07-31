//package cn.bonoon.core;
//
//import java.util.List;
//
//import cn.bonoon.entities.ProjectReportEntity;
//import cn.bonoon.entities.ProjectReportItem;
//import cn.bonoon.kernel.support.services.GenericService;
//
//
//public interface ProjectReportManagerService extends GenericService<ProjectReportEntity>{
//
//	void back(Long id);
//
//	/**
//	 * 重新统计一个项目月报里的资金
//	 * @param id
//	 */
//	void capital(Long id);
//
//	List<ProjectReportItem> reportItem(Long id);
//
//	/**
//	 * 由省办工作人员帮某一个县提交某个月份的项目月报
//	 * @param id
//	 */
//	void submit(Long id);
//
//}
