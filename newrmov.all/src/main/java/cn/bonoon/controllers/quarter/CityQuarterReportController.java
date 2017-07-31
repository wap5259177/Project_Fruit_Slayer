package cn.bonoon.controllers.quarter;

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.core.CityQuarterReportService;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;
@Controller
@RequestMapping("s/cls/sms")
public class CityQuarterReportController extends AbstractPanelController {

	@Autowired
	protected CityQuarterReportService cityQuarterReportService;

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		Long ownerId = event.getUser().getOwnerId();
		event.setVmPath("statistics/area-schedule-city");
		event.getModel().addObject("ownerId", ownerId);
		
		//获取当前市名
//		String cityN = cityQuarterReportService.getCityN(ownerId);
//		ModelAreaQuarterItem item = cityQuarterReportService.get(ownerId);
//		
		PanelModel model = event.getModel();
//		String deadline = sdf.format(item.getBatch().getQuarter().getDeadline());
//		model.addObject("title", "广东省" + cityN+ "市级建设进展汇统计表");
//		model.addObject("deadline",sdf.format(item.getBatch().getQuarter().getDeadline() );
		
		//年度下拉
		StringBuilder selyear = new StringBuilder();
		int year = Calendar.getInstance().get(Calendar.YEAR)+5;
		selyear.append("<option value=''>请选择</option>");
//		for(int i = 1992; i <= year; i++){
//			selyear.append("<option value='").append(i).append("'>").append(i).append("</option>");
//		}
		for(int i = year; i >= 2010; i--){
			selyear.append("<option value='").append(i).append("'>").append(i).append("</option>");
		}
		model.addObject("selyear", selyear);
		
	}
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	
	@RequestMapping(value = "!{mid}/quarter.statistics", method = RequestMethod.POST)
	public String statistics(Model model, int batchName, Long ownerId,
			int period, int annual,String name ,String time) {
		String bName = BatchHelper.getValue(batchName);
//		item.getBatch().getQuarter().getDeadline();
//		StringBuilder deadline = new StringBuilder();
		
		
//		batchName = StringHelper.get(batchName, "一");
		//获取统计截止时间
		ModelAreaQuarterItem item = cityQuarterReportService.getItem(ownerId,annual,bName,period);

		StringBuilder batchName1 = new StringBuilder();
		batchName1.append(bName);
		model.addAttribute("batchName1", bName);
		
		StringBuilder deadline = new StringBuilder();
		
		StringBuilder jsContent = new StringBuilder();
		
		int start = 0;
		int  finish = 0;
		
		if(null!=item){
			
			deadline.append(sdf.format(item.getBatch().getQuarter().getDeadline()));			
			model.addAttribute("deadline", deadline);
					
			if(null!=item.getStartProject()){
				start = item.getStartProject().intValue();
			}
			
			if(null!=item.getFinishProject()){
				finish = item.getFinishProject().intValue();
			}
			
			double totalfunds = 0.0;
			totalfunds  = add(totalfunds,item.getInvestment().getStateFunds());
			totalfunds  = add(totalfunds,item.getInvestment().getProvinceFunds());
			totalfunds  = add(totalfunds,item.getInvestment().getSpecialFunds());
			totalfunds  = add(totalfunds,item.getInvestment().getSocialFunds());
			totalfunds  = add(totalfunds,item.getInvestment().getCityFunds());
			totalfunds  = add(totalfunds,item.getInvestment().getCountyFunds());
			totalfunds  = add(totalfunds,item.getInvestment().getMassFunds());
			totalfunds  = add(totalfunds,item.getInvestment().getOtherFunds());
			
			
	
			jsContent.append("<tr><td>").append(item.getModelArea().getCounty()).append("</td>");
			jsContent.append("<td>").append(item.getArCount()).append("</td>");
			jsContent.append("<td>").append(item.getNrCount()).append("</td>");
			jsContent.append("<td>").append(item.getHouseholdCount()).append("</td>");
			jsContent.append("<td>").append(item.getPopulationCount()).append("</td>");
			jsContent.append("<td>").append(item.getArFinishPlan()).append("</td>");
			jsContent.append("<td>").append(item.getNeedFinish().getNeedFinish1()).append("</td>");
			jsContent.append("<td>").append(item.getNeedFinish().getNeedFinish2()).append("</td>");
			jsContent.append("<td>").append(item.getNeedFinish().getNeedFinish3()).append("</td>");
			jsContent.append("<td>").append(item.getNeedFinish().getNeedFinish4()).append("</td>");
			jsContent.append("<td>").append(item.getNeedFinish().getNeedFinish5()).append("</td>");
			jsContent.append("<td>").append(item.getNeedFinish().getNeedFinish6()).append("</td>");
			jsContent.append("<td>").append(item.getNeedFinish().getNeedFinish7()).append("</td>");
			jsContent.append("<td>").append(item.getNeedFinish().getNeedFinish8()).append("</td>");
			jsContent.append("<td>").append(start).append("</td>");
			jsContent.append("<td>").append(finish).append("</td>");
			
			jsContent.append("<td>").append(totalfunds).append("</td>");
			jsContent.append("<td>").append(item.getInvestment().getStateFunds()).append("</td>");
			jsContent.append("<td>").append(item.getInvestment().getSpecialFunds()).append("</td>");
			jsContent.append("<td>").append(item.getInvestment().getProvinceFunds()).append("</td>");
			jsContent.append("<td>").append(item.getInvestment().getCityFunds()).append("</td>");
			jsContent.append("<td>").append(item.getInvestment().getCountyFunds()).append("</td>");
			jsContent.append("<td>").append(item.getInvestment().getSocialFunds()).append("</td>");
			jsContent.append("<td>").append(item.getInvestment().getMassFunds()).append("</td>");
			jsContent.append("<td>").append(item.getInvestment().getOtherFunds()).append("</td>");
//					jsContent.append("<td>").append(item.getInvestment().getSocialFunds()+item.getInvestment().getMassFunds()).append("</td>");
			jsContent.append("<td>").append(item.getNeedFinish().getNeedFinish9()).append("</td>");
			
					

					jsContent.append("</tr>");

					model.addAttribute("jsContent", jsContent);
		}else{
			model.addAttribute("aaa", 0);
		}

		return "statistics/area-schedule-city-content";
	}
	@RequestMapping(value = "index.excel", method = GET)
	public View excel(HttpServletRequest request, HttpServletResponse response, int batchName, 
			Long ownerId,int period,int annual){
		//batchName = StringHelper.get(batchName, "一");
		//获取统计截止时间
		String bName = BatchHelper.getValue(batchName);
		ModelAreaQuarterItem item = cityQuarterReportService.getItem(ownerId,annual,bName,period);
		return new CityQuarterReportStatExcelView(item);
	}
}
