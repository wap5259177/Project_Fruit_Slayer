package cn.bonoon.controllers.project.reportManager;

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.EmpowerVerification;
import cn.bonoon.controllers.project.ProjectDetail;
import cn.bonoon.core.ProjectService;
import cn.bonoon.core.plugins.LoginService;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.ProjectReportItem;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/pl/ma/project")
public class ProjectManagerController extends AbstractGridController<ProjectEntity, ProjectManagetItem>{
	
	private ProjectService projectService;
	
	@Autowired
	protected ProjectManagerController(ProjectService projectService) {
		super(projectService);
		this.projectService = projectService;
	}
	
	
	protected String dialog, dialogName = "项目图片";
	@Override
	public void afterPropertiesSet() throws Exception {
		dialog = toUrl("/s/ma/media/1-manager.image");
		super.afterPropertiesSet();
	}
	
	@Override
	@QueryExpression("x.status >=0")
	@GridStandardDefinition(
			detailClass = ProjectDetail.class,
			deleteOperation = false, 
			autoOperation = false
	)
	protected ProjectService initLayoutGrid(LayoutGridRegister register) throws Exception {
//		register.button("项目图片", dialog, ButtonEventType.DIALOG);
		register.button(dialogName, dialog, ButtonEventType.DIALOG).status(1,2,3).ordinal(5);
		register.button("删除初始累计资金", "index.clearView",  ButtonEventType.DIALOG).ordinal(31);
		//这里的退回是设置状态为0，退回可修改项目所有的信息
		register.button("退回", "index.back", ButtonEventType.GET, ButtonRefreshType.FINISH).status(1,2,3).ordinal(30);
		register.button("查看月报", "index.project", ButtonEventType.DIALOG).status(1,2,3).ordinal(10);
		register.button("同步资金", "index.restat", ButtonEventType.GET).status(1,2,3).ordinal(20);
		register.button("删除项目", "index.deleteView", ButtonEventType.DIALOG).ordinal(80);
		register.button("修改基本信息", "index.backToUpdate", ButtonEventType.GET, ButtonRefreshType.FINISH).status(1,2,3).ordinal(40);
		register.button("修改项目所在村", "index.updatePjRural", ButtonEventType.DIALOG).status(1,2,3).ordinal(50);
		register.button("修改项目状态", "index.updatePjStatus", ButtonEventType.DIALOG).status(1,2,3).ordinal(60);
		return projectService;
	}
	@ResponseBody
	@RequestMapping(value = "!{key}/index.restat", method = GET)
	public JsonResult recal(@RequestParam("id") Long id){
		try{
			projectService.restatistic(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.clearView", method = GET)
	public ModelAndView clearView(HttpServletRequest request, @RequestParam("id") Long id,
			@RequestParam(value = "gridid", required = false) String gridid){
			
			DialogModel model = new DialogModel("t_a_" + id, request);
			model.setTitle("删除初始累计资金");
			model.setSize(280, 140);
			model.addObject("gridid", gridid);
			model.addForm(id);
			model.addObject("name",projectService.get(id).getName());
			return model.execute("project/project-sourceInv-empower");
		
	}
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@ResponseBody
	@RequestMapping(value = "!{key}/index.clear")
	public JsonResult clear(@RequestParam("id") Long id,String un,String up){
		try{
			if(!EmpowerVerification.empowerVerification(un, up, loginService, passwordEncoder))
				throw new Exception("授权失败!");
			projectService.clear(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.back", method = GET)
	public JsonResult back(@RequestParam("id") Long id){
		try{
			projectService.back(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.backToUpdate", method = GET)
	public JsonResult backToUpdate(@RequestParam("id") Long id){
		try{
			projectService.backToUpdate(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	@RequestMapping("!{key}/index.project")
	public ModelAndView report(HttpServletRequest request, Long id, String gridid, String v) {
		DialogModel model = new DialogModel("c_s_" + id, request);
		model.addForm(id);
	
		List<ProjectReportItem> items = projectService.getProjectReport(id);
//		ProjectReportItem item = projectReportManagerService.get(id);
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
			content.append("<td>").append(sumItem[10]).append("</td>");
			content.append("<td>").append(sumItem[9]).append("</td>");
			
			content.append("</td></tr>");
			
		}
		model.addObject("content",content);
		
		return model.execute("project/project-report");
	}
	
	
	/**
	 * 点击修改项目所在村
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("!{key}/index.updatePjRural")
	public ModelAndView updatePjRural(HttpServletRequest request, Long id,String gridid) {
		DialogModel model = new DialogModel("uppjr_" + id, request);
		model.addForm(id);
	
		model.addObject("gridid", gridid);
		ProjectEntity project = projectService.get(id);
		model.addObject("form",project);
		try{
//			LogonUser user = getUser();
//			ReadEvent event = new ReadEvent(applicationContext, request, user);
//			event.setScope(VisibleScope.GLOBAL);
//			ProjectEditor form = projectService.get(event, id, ProjectEditor.class);
//			model.addObject("form", form);
			model.addObject("title", "修改项目所在村");
			//核心村
			List<Object[]> nrs = projectService.newRurals(project.getModelArea().getId());
			StringBuilder selPlace = new StringBuilder();
			selPlace.append("<select id='newRural' style='width:98%;' name='newRural'>");
			if(!nrs.isEmpty()){
				selPlace.append("<optgroup label='主体村'>");
				for(Object[] it : nrs){
					selPlace.append("<option ");
					if(project.getRural().getId().equals(it[0])){
						selPlace.append("selected='selected'");
					}
					selPlace.append("value='").append(it[0]);
					
					selPlace.append("'>").append(it[2]).append(' ').append(it[1]).append(' ');
					if(null != it[3]){
						selPlace.append(it[3]);
					}
					selPlace.append("</option>");
				}
				selPlace.append("</optgroup>");
			}
			//非主体村
			List<Object[]> prs = projectService.peripheralRurals(project.getModelArea().getId());
			if(!prs.isEmpty()){
				selPlace.append("<optgroup label='非主体村'>");
				//selected='selected'
				for(Object[] it : prs){
					selPlace.append("<option ");
					if(project.getRural().getId().equals(it[0])){
						selPlace.append("selected='selected'");
					}
					selPlace.append("value='").append(it[0]);
					
					selPlace.append("'>").append(it[2]).append(' ').append(it[1]).append(' ');
					if(null != it[3]){
						selPlace.append(it[3]);
					}
					selPlace.append("</option>");
				}
				selPlace.append("</optgroup>");
			}

			selPlace.append("</select>");
			model.addObject("newRural", selPlace);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model.execute("project/project-update-rural");
	}
	
	/**
	 * 点击提交，修改项目所在村
	 * @param id
	 * @param ruralId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/pjRural.update")
	public JsonResult updateRural(@RequestParam("id") Long id,Long ruralId){
		try{
			projectService.updatePjRural(id,ruralId,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	/**
	 * 点击修改项目状态
	 * /**
	 * 0-未开始
	 * 1-进行中
	 * 2-竣工
	 * 3-终止
	 * 
	 * 4-退回  ：退回修改
	 *
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("!{key}/index.updatePjStatus")
	public ModelAndView updatePjStatus(HttpServletRequest request, Long id,String gridid) {
		DialogModel model = new DialogModel("uppjr_" + id, request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		ProjectEntity project = projectService.get(id);
		model.addObject("form",project);
		model.addObject("title", "修改项目状态");
		return model.execute("project/project-update-status");
	}
	
	/**
	 * 点击提交，修改项目状态
	 * @param id
	 * @param ruralId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/pjStatus.update")
	public JsonResult updateStatus(@RequestParam("id") Long id,Integer status){
		try{
			projectService.updatePjStatus(id,status,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			ex.printStackTrace();
			return JsonResult.error(ex);
		}
	}
	

	
	@RequestMapping(value = "!{key}/index.deleteView", method = GET)
	public ModelAndView deleteView(@PathVariable("key") String key, 
			HttpServletRequest request, 
			@RequestParam(value = "id") Long id, 
			@RequestParam(value = "gridid", required = false) String gridid){
	ProjectEntity pro=	projectService.get(id);
		DialogModel model = new DialogModel("t_a_" + id, request);
		model.setTitle("项目删除操作");
		model.setSize(280, 140);
		model.addObject("gridid", gridid);
		model.addObject("projectName",	pro.getName());
		model.addObject("raralName",	pro.getRural().getNaturalVillage());
		model.addObject("id",	pro.getId());
		model.addForm(id);
		return model.execute("project/project-delete");
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.delete")
	public JsonResult delete(
			@RequestParam("id") Long id,
			@RequestParam(value = "un") String un,
			@RequestParam(value = "up") String up){
		try{

//			if(!EmpowerVerification.empowerVerification(un, up, loginService, passwordEncoder))
//				return JsonResult.error("授权失败!!!");
			
			return JsonResult.result(0,projectService.deleteProject(id));
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.GLOBAL;
	}
}
