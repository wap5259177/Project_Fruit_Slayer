package cn.bonoon.controllers.project.reportManager;

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.ProjectReportService;
import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ProjectReportEntity;
import cn.bonoon.entities.ProjectReportItem;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/pl/ma/project/report")
public class ProjectReportManagerController extends AbstractGridController<ProjectReportEntity, ReportManagerItem>{

//	private ProjectService projectService;
	@Autowired
	private ProjectReportService projectReportService;
	//private  ProjectReportService projectReportManagerService;
	@Autowired
	protected ProjectReportManagerController(ProjectReportService projectReportService) {
		super(projectReportService);
		//this.projectReportManagerService = projectReportManagerService;
		this.projectReportService = projectReportService;
	}


	@Override
	@GridStandardDefinition(
		deleteOperation=false,//去除删除按钮
	    //insertClass = ModelAreaEditor.class,//添加,修改按钮 
		autoOperation = false
		//updateClass = ModelAreaUpdate.class,	
	)
	protected ProjectReportService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("退回", "index.back", ButtonEventType.GET, ButtonRefreshType.FINISH).status(1).ordinal(33);
		register.button("提交", "index.submit", ButtonEventType.GET, ButtonRefreshType.FINISH).status(0).ordinal(23);
		register.button("统计资金", "index.capital", ButtonEventType.GET, ButtonRefreshType.FINISH).ordinal(20);
		register.button("查看", "index.detail", ButtonEventType.DIALOG).ordinal(10);
		
//		register.button("资金错位", "has.error", ButtonEventType.GET, ButtonRefreshType.FINISH).ordinal(50);
//		register.button("资金改正", "error.correct", ButtonEventType.GET, ButtonRefreshType.FINISH).ordinal(51);
//		register.button("改正操作", "error.recorrect", ButtonEventType.GET, ButtonRefreshType.FINISH).ordinal(52);
//		register.button("改正操作", "update.correct", ButtonEventType.GET, ButtonRefreshType.FINISH).ordinal(52);
		return projectReportService;

	}
	
	//---------------------
	@ResponseBody
	@RequestMapping(value = "!{key}/has.error", method = GET)
	public JsonResult hasError(@RequestParam("id") Long id){
		try{
			projectReportService.backToReset(id);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	@ResponseBody
	@RequestMapping(value = "!{key}/error.correct", method = GET)
	public JsonResult correct(@RequestParam("id") Long id){
		try{
			projectReportService.resetProject(id);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "!{key}/update.correct", method = GET)
	public JsonResult updateCorrect(@RequestParam("id") Long id){
		try{
			projectReportService.reUpdate(id);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	@ResponseBody
	@RequestMapping(value = "!{key}/error.recorrect", method = GET)
	public JsonResult reCorrect(@RequestParam("id") Long id){
		try{
			projectReportService.reCorrectt(id);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.back", method = GET)
	public JsonResult back(@RequestParam("id") Long id){
		try{
			projectReportService.back(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	@ResponseBody
	@RequestMapping(value = "!{key}/index.submit", method = GET)
	public JsonResult submit(@RequestParam("id") Long id){
		try{
			projectReportService.submit(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.capital", method = GET)
	public JsonResult capital(@RequestParam("id") Long id){
		try{
			projectReportService.capital(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	
	@RequestMapping(value = "!{key}/index.detail")
	public ModelAndView detail(HttpServletRequest request, 
			@RequestParam("id") Long id, 
			@RequestParam("gridid") String gridid){
		
		DialogModel model = new DialogModel("p_s_" + id, request);
		model.addForm(id);
		List<ProjectReportItem> item = projectReportService.reportItem(id);
		ProjectReportEntity pro = projectReportService.get(id);
		int year = pro.getAnnual();
		int month = pro.getPeriod();
//		ModelAreaEntity modelAreaEntity = pro.getModelArea();
//		Long modelAreaId  = pro.getModelArea().getId();
		//累计截止到目前月报累计情况
		double spend=0.0;
		int labour=0;
		
		List<ProjectReportEntity> sumRepot = projectReportService.getSumRepot(pro.getModelArea(),year, month);
		InvestmentInfo invs =new InvestmentInfo(); 
		for(ProjectReportEntity pre:sumRepot){
			 InvestmentInfo inv =pre.getInvestment();
			 invs.sum(inv);
			 labour+=pre.getLabourCount();
			 spend=add(spend,pre.getSpend());
			 
		 }
		
		//当月月报累计情况
		ProjectReportEntity current  = projectReportService.getCurrent(pro.getModelArea(), year, month);
		StringBuilder content = new StringBuilder();
		content.append("<tr><td colspan='2'>").append("累计完成月报情况").append("</td>");
		if(0!=sumRepot.size()){
			content.append("<td>").append(invs.getStateFunds()).append("</td>");
			content.append("<td>").append(invs.getSpecialFunds()).append("</td>");
			content.append("<td>").append(invs.getProvinceFunds()).append("</td>");
			content.append("<td>").append(invs.getCityFunds()).append("</td>");
			content.append("<td>").append(invs.getCountyFunds()).append("</td>");
			content.append("<td>").append(invs.getSocialFunds()).append("</td>");
			content.append("<td>").append(invs.getMassFunds()).append("</td>");
			content.append("<td>").append(invs.getOtherFunds()).append("</td>");
			content.append("<td>").append(invs.getTotalFunds()).append("</td>");
			content.append("<td>").append(labour).append("</td>");
			
			content.append("<td>");
			content.append("<td>");
		}else{
//			content.append("<tr><td colspan='2'>").append("累计完成月报情况").append("</td>");
			for(int j=0;j<12;j++){
				content.append("<td>——</td>");
			}
			content.append("<td>");
		}
		
		
		content.append("</td></tr>");
		
		if(null!=current){
			InvestmentInfo invest = current.getInvestment();
			content.append("<tr><td colspan='2'>").append("本月完成月报情况");
			content.append("<td>").append(invest.getStateFunds()).append("</td>");
			content.append("<td>").append(invest.getSpecialFunds()).append("</td>");
			content.append("<td>").append(invest.getProvinceFunds()).append("</td>");
			content.append("<td>").append(invest.getCityFunds()).append("</td>");
			content.append("<td>").append(invest.getCountyFunds()).append("</td>");
			content.append("<td>").append(invest.getSocialFunds()).append("</td>");
			content.append("<td>").append(invest.getMassFunds()).append("</td>");
			content.append("<td>").append(invest.getOtherFunds()).append("</td>");
			content.append("<td>").append(invest.getTotalFunds()).append("</td>");
			content.append("<td>").append(current.getLabourCount()).append("</td>");
			content.append("<td>").append(current.getSpend()).append("</td>");
			content.append("<td>");
			content.append("</td></tr>");
		}
		
		
//		ProjectEntity project = projectService.get(id);
//		model.addObject("projectName",project.getName());
//		model.addObject("projectCode",project.getCode());
		model.addObject("title", pro.getModelArea().getCityName()+pro.getModelArea().getName()+pro.getAnnual()+"年");
		model.addObject("itemId", id);
		model.addObject("item", item);
		model.addObject("pro", pro);
		model.addObject("content",content);
//		List<ProjectReportItem> reports = item.getReports();
		return model.execute("project/project-report-detail");
		
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.GLOBAL;
	}
}
