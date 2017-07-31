package cn.bonoon.controllers.project.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.ProjectReportService;
import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ProjectReportEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/cl/project/report/result")
public class ReportResultController extends AbstractPanelController{

//	@Autowired
//	private ProjectService projectService;
	private ProjectReportService projectReportService;
	
	@Autowired
	protected ReportResultController(ProjectReportService projectReportService/*, ProjectService projectService, NewRuralService newRuralService*/) {
		this.projectReportService = projectReportService;
	}
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		Long ownerId = event.getUser().getOwnerId();
		event.setVmPath("project/report-result");
		event.getModel().addObject("ownerId", ownerId);
		PanelModel model = event.getModel();
		_result(ownerId, model);
	}

	private void _result(Long ownerId, ModelAndView model) {
		List<ProjectReportEntity> pro =  projectReportService.getRepot(ownerId);
		StringBuilder reports  = new StringBuilder(), totalReports  = new StringBuilder();
		//Long modelAreaId;
		//Object[] prore = projectReportService.getReportSummary(modelAreaId);
		//double totalfunds = 0.0;
//		for(int j=1;j<prore.length;j++){
//			totalfunds = add(totalfunds,prore[j]);
//		}
		

		int i = 1;
		
		String[] months = {"一月", "二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
		InvestmentInfo total = new InvestmentInfo();	
		for(ProjectReportEntity project : pro){
			InvestmentInfo inv = project.getInvestment();
			total.sum(inv);
			int period = project.getPeriod();
			model.addObject("modelare",project.getModelArea().getPlace().getName());
			reports.append("<tr>");
			reports.append("<td>").append(i++).append("</td>");
			reports.append("<td>").append(project.getAnnual()).append("</td>");
			reports.append("<td>").append(months[period]).append("</td>");
			
//			reports.append("<td>").append(project.getPeriod()).append("</td>");
			reports.append("<td>").append(inv.getStateFunds()).append("</td>");
			reports.append("<td>").append(inv.getSpecialFunds()).append("</td>");
			reports.append("<td>").append(inv.getProvinceFunds()).append("</td>");
			reports.append("<td>").append(inv.getCityFunds()).append("</td>");
			reports.append("<td>").append(inv.getCountyFunds()).append("</td>");
			reports.append("<td>").append(inv.getSocialFunds()).append("</td>");
			reports.append("<td>").append(inv.getMassFunds()).append("</td>");
			reports.append("<td>").append(inv.getOtherFunds()).append("</td>");
			reports.append("<td>").append(inv.getTotalFunds());
			
			reports.append("</td></tr>");
		}
		totalReports.append("<tr><td colspan='3'>").append("月报累计").append("</td>");
		totalReports.append("<td>").append(total.getStateFunds()).append("</td>");
		totalReports.append("<td>").append(total.getSpecialFunds()).append("</td>");
		totalReports.append("<td>").append(total.getProvinceFunds()).append("</td>");
		totalReports.append("<td>").append(total.getCityFunds()).append("</td>");
		totalReports.append("<td>").append(total.getCountyFunds()).append("</td>");
		totalReports.append("<td>").append(total.getSocialFunds()).append("</td>");
		totalReports.append("<td>").append(total.getMassFunds()).append("</td>");
		totalReports.append("<td>").append(total.getOtherFunds()).append("</td>");
		totalReports.append("<td>").append(total.getTotalFunds());
		
//		totalReports.append("<td>").append(prore[1]).append("</td>");
//		totalReports.append("<td>").append(prore[2]).append("</td>");
//		totalReports.append("<td>").append(prore[3]).append("</td>");
//		totalReports.append("<td>").append(prore[4]).append("</td>");
//		totalReports.append("<td>").append(prore[5]).append("</td>");
//		totalReports.append("<td>").append(prore[6]).append("</td>");
//		totalReports.append("<td>").append(prore[7]).append("</td>");
//		totalReports.append("<td>").append(prore[8]).append("</td>");
//		totalReports.append("<td>").append(totalfunds).append("</td>");
		totalReports.append("</td></tr>");
		
		model.addObject("pro", pro);
		model.addObject("reports",totalReports.append(reports));
	}
	
	
//	@RequestMapping("!{key}/index.print")
//	public String print(HttpServletRequest request, Long id, String gridid, ModelAndView model) {
//		_result(getUser().getOwnerId(), model);
//		return "project/report-result-view";
//	}
}
