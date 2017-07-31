package cn.bonoon.controllers.project.reportManager;

import static cn.bonoon.util.DoubleHelper.add;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.ProjectReportService;
import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ProjectReportEntity;
import cn.bonoon.entities.ProjectReportItem;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.DialogModel;

@Controller
@RequestMapping("s/cls/project")
public class CityProjectReportController extends AbstractGridController<ProjectReportEntity, ReportManagerItem>{

	@Autowired
	private ProjectReportService projectReportService;
	
	@Autowired
	protected CityProjectReportController(ProjectReportService projectReportService) {
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
	@QueryExpression("x.status>=0 and x.modelArea.cityId=(select y.place.id from UnitEntity y where y.id={USER owner})")
	protected ProjectReportService initLayoutGrid(LayoutGridRegister register) throws Exception {
//		register.button("退回", "index.back", ButtonEventType.GET, ButtonRefreshType.FINISH).status(1).ordinal(33);
//		register.button("提交", "index.submit", ButtonEventType.GET, ButtonRefreshType.FINISH).status(0).ordinal(23);
//		register.button("统计资金", "index.capital", ButtonEventType.GET, ButtonRefreshType.FINISH).ordinal(20);
		register.button("查看", "index.detail", ButtonEventType.DIALOG).status(1).ordinal(10);

		register.button("打印", "index.print", ButtonEventType.JUMP).status(1).ordinal(50);
		
		return projectReportService;

	}
	
	
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid, Model model) {
		List<ProjectReportItem> items = projectReportService.reportItem(id);
		
		//按项目编号排序（升序）
		Comparator<ProjectReportItem> comparator = new Comparator<ProjectReportItem>() {
            public int compare(ProjectReportItem it1, ProjectReportItem it2) {
                return it1.getProject().getCode().compareTo( it2.getProject().getCode());
            }
        };
        Collections.sort(items,comparator);
		
		ProjectReportEntity pro = projectReportService.get(id);
		int year = pro.getAnnual();
		int month = pro.getPeriod();
		double spend=0.0;
		int labour=0;
		List<ProjectReportEntity> sumRepot = projectReportService.getSumRepot(pro.getModelArea()  , year, month);
		InvestmentInfo invs =new InvestmentInfo();
		for(ProjectReportEntity pre:sumRepot){
			 InvestmentInfo inv =pre.getInvestment();
			 invs.sum(inv);
			 labour+=pre.getLabourCount();
			 spend=add(spend,pre.getSpend());
		 }
		StringBuffer sumSb = new StringBuffer(); 
		if(0!=sumRepot.size()){
			sumSb.append("<td colspan='2'><font style='font-size:12px;'>").append("累计完成月报情况").append("</font></td>");
			sumSb.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invs.getStateFunds()).append("</font></td>");
			sumSb.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invs.getSpecialFunds()).append("</font></td>");
			sumSb.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invs.getProvinceFunds()).append("</font></td>");
			sumSb.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invs.getCityFunds()).append("</font></td>");
			sumSb.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invs.getCountyFunds()).append("</font></td>");
			sumSb.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invs.getSocialFunds()).append("</font></td>");
			sumSb.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invs.getMassFunds()).append("</font></td>");
			sumSb.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invs.getOtherFunds()).append("</font></td>");
			sumSb.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invs.getTotalFunds()).append("</font></td>");
			sumSb.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(spend).append("</font></td>");
			sumSb.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(labour).append("</td>");
			sumSb.append("<td style='text-align:center;'>").append("</td>");
			
		}else{
//			content.append("<tr><td colspan='2'>").append("累计完成月报情况").append("</td>");
			for(int j=0;j<12;j++){
				sumSb.append("<td style='text-align:center;'>——</td>");
			}
		}
		//当月月报累计情况
		ProjectReportEntity current  = projectReportService.getCurrent(pro.getModelArea(), year, month);
		StringBuffer currentSB = new StringBuffer();
		if(null!=current){
			InvestmentInfo invest = current.getInvestment();
			currentSB.append("<td colspan='2'><font style='font-size:12px;'>").append("本月完成月报情况").append("</font></td>");
			currentSB.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invest.getStateFunds()).append("</font></td>");
			currentSB.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invest.getSpecialFunds()).append("</font></td>");
			currentSB.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invest.getProvinceFunds()).append("</font></td>");
			currentSB.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invest.getCityFunds()).append("</font></td>");
			currentSB.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invest.getCountyFunds()).append("</font></td>");
			currentSB.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invest.getSocialFunds()).append("</font></td>");
			currentSB.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invest.getMassFunds()).append("</font></td>");
			currentSB.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invest.getOtherFunds()).append("</font></td>");
			currentSB.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(invest.getTotalFunds()).append("</font></td>");
			currentSB.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(current.getSpend()).append("</font></td>");
			currentSB.append("<td style='text-align:center;'><font style='font-size:12px;'>").append(current.getLabourCount()).append("</font></td>");
			currentSB.append("<td style='text-align:center;'><font style='font-size:12px;'>").append("</font></td>");
		}
		int peri = pro.getPeriod()+1;
		String title = pro.getModelArea().getCityName()+pro.getModelArea().getName()+year+"年"+peri+"月报信息";
		model.addAttribute("title", title);
		model.addAttribute("items", items);
		model.addAttribute("sumSb", sumSb);
		model.addAttribute("currentSB", currentSB);
		return "project/project-report-view";
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
		
		
		
		List<ProjectReportEntity> sumRepot = projectReportService.getSumRepot(pro.getModelArea()  , year, month);
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
//		content.append("<tr><td colspan='2'>").append("累计完成月报情况").append("</td>");
		if(0!=sumRepot.size()){
			content.append("<tr><td colspan='2'>").append("累计完成月报情况").append("</td>");
			content.append("<td>").append(invs.getStateFunds()).append("</td>");
			content.append("<td>").append(invs.getSpecialFunds()).append("</td>");
			content.append("<td>").append(invs.getProvinceFunds()).append("</td>");
			content.append("<td>").append(invs.getCityFunds()).append("</td>");
			content.append("<td>").append(invs.getCountyFunds()).append("</td>");
			content.append("<td>").append(invs.getSocialFunds()).append("</td>");
			content.append("<td>").append(invs.getMassFunds()).append("</td>");
			content.append("<td>").append(invs.getOtherFunds()).append("</td>");
			content.append("<td>").append(invs.getTotalFunds()).append("</td>");
			content.append("<td>").append(spend).append("</td>");
			content.append("<td>").append(labour).append("</td>");
			
//			content.append("<td>");
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
			content.append("<td>").append(current.getSpend()).append("</td>");
			content.append("<td>").append(current.getLabourCount()).append("</td>");
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
		return VisibleScope.DOMAIN;
	}
	
}
