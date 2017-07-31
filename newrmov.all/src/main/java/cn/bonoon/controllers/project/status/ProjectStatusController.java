package cn.bonoon.controllers.project.status;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.inofs.BaseProjectInfo;
import cn.bonoon.controllers.project.AbstractProjectController;
import cn.bonoon.controllers.project.ProjectEditor;
import cn.bonoon.core.ProjectService;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;

@Controller
@RequestMapping("s/cl/project/status")
public class ProjectStatusController extends AbstractProjectController{

//	private ProjectService projectService;
	@Autowired
	protected ProjectStatusController(ProjectService projectService) {
		super(projectService);
//		this.projectService = projectService;
	}

	@Override
	@GridStandardDefinition(
			//detailClass = ProjectDetail.class,
			insertClass = ProjectEditor.class, 
			updateClass = ProjectEditor.class)
	@QueryExpression("x.status=0")
	protected ProjectService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button(dialogName, dialog, ButtonEventType.DIALOG);
		
		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(50);
		return projectService;
	}
	
	
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid, Model model) {
		try{
			ReadEvent event = new ReadEvent(applicationContext, request, getUser());
			event.setScope(VisibleScope.GLOBAL);
			model.addAttribute("form", projectService.get(event, id, BaseProjectInfo.class));
		}catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("readonly", "readonly='readonly'").addAttribute("disabled", "disabled='true'");
		model.addAttribute("title","连片示范建设工程项目库 ");
		return "applicant/areaproject-view-view";
	}
}
