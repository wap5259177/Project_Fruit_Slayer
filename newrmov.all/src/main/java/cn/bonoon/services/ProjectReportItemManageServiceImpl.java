//package cn.bonoon.services;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import cn.bonoon.core.ProjectReportItemManageService;
//import cn.bonoon.entities.ProjectReportItem;
//import cn.bonoon.kernel.support.services.AbstractService;
//@Service
//@Transactional(readOnly = true)
//public class ProjectReportItemManageServiceImpl extends AbstractService<ProjectReportItem> implements ProjectReportItemManageService{
//	@Override
//	public List<ProjectReportItem> getProjectReport(Long id){
//		String sql = "select x from ProjectReportItem x where x.project.id=?";
//		return __list(ProjectReportItem.class,sql,id);
//	}
//}
