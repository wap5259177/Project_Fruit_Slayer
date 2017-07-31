package cn.bonoon.controllers.project.finish;

import static cn.bonoon.util.DoubleHelper.add;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.project.AbstractProjectController;
import cn.bonoon.controllers.project.ProjectDetail;
import cn.bonoon.core.ProjectService;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.ProjectReportItem;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.models.DialogModel;

@Controller
@RequestMapping("s/cl/project/finish")
public class ProjectFinishController extends AbstractProjectController{

//	private ProjectService projectService;
	@Autowired
	protected ProjectFinishController(ProjectService projectService) {
		super(projectService);
//		this.projectService = projectService;
	}

	@Override
	@QueryExpression("x.status in (2,3)")
	@GridStandardDefinition(
			detailClass = ProjectDetail.class,
			deleteOperation = false, 
			autoOperation = false
	)
	protected ProjectService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button(dialogName, dialog, ButtonEventType.DIALOG);
		register.button("查看月报", "index.project", ButtonEventType.DIALOG);
		return projectService;
	}
	
//	@Autowired
//	private  ProjectReportItemManageService projectReportItemManagerService;
	@RequestMapping("!{key}/index.project")
	public ModelAndView report(HttpServletRequest request, Long id, String gridid, String v) {
		DialogModel model = new DialogModel("c_s_" + id, request);
		model.addForm(id);
	
		List<ProjectReportItem> items = projectService.getProjectReport(id);
		ProjectEntity item = projectService.get(id);
		model.addObject("itemId", id);
		model.addObject("item", item);
		model.addObject("items", items);
		model.addObject("title", item.getName()+ "项目月度报表");
		
		Object[] sumItem = projectService.getSumItem(id);
		double totalfunds = 0.0;
		for(int i=1;i<9;i++){
			totalfunds = add(totalfunds,sumItem[i]);
		}
		StringBuilder content = new StringBuilder();
		if(null!=sumItem[0]){
			content.append("<tr><td colspan='2'>").append("累计完成月报情况");
			content.append("<td>").append(sumItem[1]).append("</td>");
			content.append("<td>").append(sumItem[2]).append("</td>");
			content.append("<td>").append(sumItem[3]).append("</td>");
			content.append("<td>").append(sumItem[4]).append("</td>");
			content.append("<td>").append(sumItem[5]).append("</td>");
			content.append("<td>").append(sumItem[6]).append("</td>");
			content.append("<td>").append(sumItem[7]).append("</td>");
			content.append("<td>").append(sumItem[8]).append("</td>");
			content.append("<td>").append(totalfunds).append("</td>");
			content.append("<td>").append(sumItem[9]).append("</td>");
			content.append("<td>").append(sumItem[10]).append("</td>");
			
			content.append("</td></tr>");
			
		}
		model.addObject("content",content);
		
		return model.execute("project/project-report");
	}
}
