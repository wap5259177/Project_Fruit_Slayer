package cn.bonoon.controllers.project.running;

import static cn.bonoon.util.DoubleHelper.add;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.inofs.BaseProjectInfo;
import cn.bonoon.controllers.project.AbstractProjectController;
import cn.bonoon.controllers.project.ProjectDetail;
import cn.bonoon.core.ProjectService;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.ProjectReportItem;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.models.DialogModel;

@Controller
@RequestMapping("s/cl/project/running")
public class ProjectRunningController extends AbstractProjectController {

	// private ProjectService projectService;
	//
	// private String dialog;
	// @Override
	// public void afterPropertiesSet() throws Exception {
	// super.afterPropertiesSet();
	// dialog = toUrl("/s/ma/media/1-manager.image");
	// }

	@Autowired
	protected ProjectRunningController(ProjectService projectService) {
		super(projectService);
		// this.projectService = projectService;
	}

	@Override
	@QueryExpression("x.status=1")
	@GridStandardDefinition(detailClass = ProjectDetail.class, deleteOperation = false, autoOperation = false)
	protected ProjectService initLayoutGrid(LayoutGridRegister register)
			throws Exception {
		register.button(dialogName, dialog, ButtonEventType.DIALOG);
		register.button("查看月报", "index.project", ButtonEventType.DIALOG);
		register.button("打印月报", "index.print", ButtonEventType.JUMP)
				.ordinal(50);
//		register.button("细分项目", "index.changeManyProjectView",
//				ButtonEventType.DIALOG);
		return projectService;
	}

//	@RequestMapping("!{key}/index.changeManyProjectView")
//	public ModelAndView changeManyProjectView(HttpServletRequest request,
//			Long id) {
//		DialogModel model = new DialogModel("c_s_" + id, request);
//		model.setTitle("项目细分");
//		model.setSize(700, 500);
//		model.addObject("proId", id);
//		ProjectEntity pro = projectService.get(id);
//		model.addObject("proName", pro.getName());
//
//		return model.execute("project/changeManyProjectView");
//	}

	@ResponseBody
	@RequestMapping("!{key}/index.changeManyProject")
	public String changeManyProject(HttpServletRequest request, Long id,
			int changeProHowMany) {
		ProjectEntity pro = projectService.get(id);
		pro.setDeleted(true);
		
		return "";
	}

	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid,
			Model model) {
		List<ProjectReportItem> items = projectService.getProjectReport(id);
		// ProjectReportItem item = projectReportManagerService.get(id);
		ProjectEntity item = projectService.get(id);
		model.addAttribute("itemId", id);
		model.addAttribute("item", item);
		model.addAttribute("items", items);
		model.addAttribute("title", item.getName() + "项目月度报表");

		Object[] sumItem = projectService.getSumItem(id);
		double totalfunds = 0.0;
		for (int i = 1; i < 9; i++) {
			totalfunds = add(totalfunds, sumItem[i]);
		}
		StringBuilder currentSB = new StringBuilder();
		if (null != sumItem[0]) {
			currentSB
					.append("<td style='text-align:center' colspan='2'><font style='font-size:12px;'>")
					.append("累计完成月报情况").append("</font></td>");
			currentSB
					.append("<td style='text-align:center'><font style='font-size:12px;'>")
					.append(sumItem[1]).append("</font></td>");
			currentSB
					.append("<td style='text-align:center'><font style='font-size:12px;'>")
					.append(sumItem[2]).append("</font></td>");
			currentSB
					.append("<td style='text-align:center'><font style='font-size:12px;'>")
					.append(sumItem[3]).append("</font></td>");
			currentSB
					.append("<td style='text-align:center'><font style='font-size:12px;'>")
					.append(sumItem[4]).append("</font></td>");
			currentSB
					.append("<td style='text-align:center'><font style='font-size:12px;'>")
					.append(sumItem[5]).append("</font></td>");
			currentSB
					.append("<td style='text-align:center'><font style='font-size:12px;'>")
					.append(sumItem[6]).append("</font></td>");
			currentSB
					.append("<td style='text-align:center'><font style='font-size:12px;'>")
					.append(sumItem[7]).append("</font></td>");
			currentSB
					.append("<td style='text-align:center'><font style='font-size:12px;'>")
					.append(sumItem[8]).append("</font></td>");
			currentSB
					.append("<td style='text-align:center'><font style='font-size:12px;'>")
					.append(totalfunds).append("</font></td>");
			currentSB
					.append("<td style='text-align:center'><font style='font-size:12px;'>")
					.append(sumItem[10]).append("</font></td>");
			currentSB
					.append("<td style='text-align:center'><font style='font-size:12px;'>")
					.append(sumItem[9]).append("</font></td>");
		}
		model.addAttribute("currentSB", currentSB);
		return "project/project-report-running-view";
	}

	// @Autowired
	// private ProjectReportItemManageService projectReportItemManagerService;
	@RequestMapping("!{key}/index.project")
	public ModelAndView report(HttpServletRequest request, Long id,
			String gridid, String v) {
		DialogModel model = new DialogModel("c_s_" + id, request);
		model.addForm(id);

		List<ProjectReportItem> items = projectService.getProjectReport(id);
		// ProjectReportItem item = projectReportManagerService.get(id);
		ProjectEntity item = projectService.get(id);
		model.addObject("itemId", id);
		model.addObject("item", item);
		model.addObject("items", items);
		model.addObject("title", item.getName() + "项目月度报表");

		Object[] sumItem = projectService.getSumItem(id);
		double totalfunds = 0.0;
		for (int i = 1; i < 9; i++) {
			totalfunds = add(totalfunds, sumItem[i]);
		}
		StringBuilder content = new StringBuilder();
		if (null != sumItem[0]) {
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
			content.append("<td>").append(sumItem[10]).append("</td>");
			content.append("<td>").append(sumItem[9]).append("</td>");
			content.append("</td></tr>");

		}
		model.addObject("content", content);

		return model.execute("project/project-report");
	}
}
