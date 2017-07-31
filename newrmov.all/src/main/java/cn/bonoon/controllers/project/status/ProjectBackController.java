package cn.bonoon.controllers.project.status;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.controllers.inofs.BaseProjectInfo;
import cn.bonoon.controllers.project.AbstractProjectController;
import cn.bonoon.controllers.project.ProjectEditor;
import cn.bonoon.core.ProjectService;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/cl/project/back")
public class ProjectBackController extends AbstractProjectController{

//	private ProjectService projectService;
	@Autowired
	protected ProjectBackController(ProjectService projectService) {
		super(projectService);
//		this.projectService = projectService;
	}

	@Override
	@GridStandardDefinition(
			//detailClass = ProjectDetail.class,
//			insertClass = ProjectEditor.class, 
			deleteOperation = false,
			updateClass = ProjectUpdateBaseInfoEditor.class)
//	@QueryExpression("x.status=4")
	@QueryExpression("x.isUpdate is not null and isUpdate=true")
	protected ProjectService initLayoutGrid(LayoutGridRegister register) throws Exception {
//		register.button(dialogName, dialog, ButtonEventType.DIALOG);//项目图片
		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(40);
		register.button("完成修改", "index.pjUpdated" ,ButtonEventType.GET, ButtonRefreshType.FINISH).ordinal(50);
		return projectService;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.pjUpdated", method = GET)
	public JsonResult clear(@RequestParam("id") Long id){
		try{
			projectService.pjUpdated(id);//完成修改
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
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
