package cn.bonoon.controllers.project.report;

import static cn.bonoon.util.DoubleHelper.add;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import cn.bonoon.core.LocalQuarterReportService;
import cn.bonoon.entities.ModelAreaQuarterAdministrationRural;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.ModelAreaQuarterNaturalRural;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractLayoutController;
@Controller
@RequestMapping("s/lls/sms/summary")
public class LocalQuarterSummaryController extends AbstractLayoutController{

	@Autowired
	private LocalQuarterReportService localQuarterReportService;
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		Calendar date = Calendar.getInstance();
		int year = date.get(Calendar.YEAR);
		String yearSelect = "";
		int startAnnual = year-10;
		for(int i=startAnnual;i<=year;i++){
			yearSelect += "<option value='" + i + "'>" + i+"年" + "</option>";
		}
		event.getModel().addObject("yearSelect", yearSelect).addObject("ownerId", event.getUser().getOwnerId());
		event.setVmPath("report/summary-quarter");
	}
	
	/**
	 * 查找
	 * @param ownerId
	 * @param annual
	 * @param period
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/report.search")
	public Map<String,Object> search(Long ownerId,int annual,int period){
		ModelAreaQuarterItem item = localQuarterReportService.getMaqitem(ownerId, annual, period);
		Map<String,Object>map = new HashMap<>();
		StringBuffer sb = new StringBuffer("");
		if(item==null){
			map.put("hasItem", false);
			return map;
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
		
		sb.append("<tr height='20'>");
		sb.append("<td>").append(item.getModelArea().getCityName()).append(item.getModelArea().getCounty()).append("</td>");
		
		
		sb.append("<td>").append(item.getArCount()).append("</td>");
		sb.append("<td>").append(item.getNrCount()).append("</td>");
		sb.append("<td>").append(item.getHouseholdCount()).append("</td>");
		sb.append("<td>").append(item.getPopulationCount()).append("</td>");
		
		sb.append("<td>").append(item.getArFinishPlan()).append("</td>");
		sb.append("<td>").append(item.getNeedFinish().getNeedFinish1()).append("</td>");
		sb.append("<td>").append(item.getNeedFinish().getNeedFinish2()).append("</td>");
		sb.append("<td>").append(item.getNeedFinish().getNeedFinish3()).append("</td>");
		sb.append("<td>").append(item.getNeedFinish().getNeedFinish4()).append("</td>");
		sb.append("<td>").append(item.getNeedFinish().getNeedFinish5()).append("</td>");
		sb.append("<td>").append(item.getNeedFinish().getNeedFinish6()).append("</td>");
		sb.append("<td>").append(item.getNeedFinish().getNeedFinish7()).append("</td>");
		sb.append("<td>").append(item.getNeedFinish().getNeedFinish8()).append("</td>");
		if(null!=item.getStartProject()){
			sb.append("<td>").append(item.getStartProject()).append("</td>");
		}else{
			sb.append("<td>0</td>");
		}
		if(null!=item.getFinishProject()){
			sb.append("<td>").append(item.getFinishProject()).append("</td>");
		}else{
			sb.append("<td>0</td>");
		}
		
		
		
		
		sb.append("<td>").append(totalfunds).append("</td>");
		sb.append("<td>").append(item.getInvestment().getStateFunds()).append("</td>");
		sb.append("<td>").append(item.getInvestment().getSpecialFunds()).append("</td>");
		sb.append("<td>").append(item.getInvestment().getProvinceFunds()).append("</td>");
		sb.append("<td>").append(item.getInvestment().getCityFunds()).append("</td>");
		sb.append("<td>").append(item.getInvestment().getCountyFunds()).append("</td>");
		sb.append("<td>").append(item.getInvestment().getSocialFunds()).append("</td>");//<!--社会投入-->
		sb.append("<td>").append(item.getInvestment().getMassFunds()).append("</td>");//<!--群众自筹资金-->
		sb.append("<td>").append(item.getInvestment().getOtherFunds()).append("</td>");//<!--其他方面资金-->
		
		sb.append("<td>").append(item.getNeedFinish().getNeedFinish9()).append("</td>");
		sb.append("</tr>");
		
		for(ModelAreaQuarterAdministrationRural qar:item.getAdminRurals()){
			sb.append("<tr>");
			sb.append("<td colspan='5' >");
			sb.append("<h3  href='#' class='item-open2 item-close2' onclick=\"jQuery(this).toggleClass('item-close2');").append("jQuery('.c-").append(qar.getId()).append("').toggle();").append("\">").append(qar.getAdminRural().getName()).append("</h3>");
			sb.append("</td>");
			sb.append("<td>");
			if(qar.getArFinishPlan()==1){
				sb.append("<a class='buptip' title='行政村有编制规划设计村数(个)' ><input type='checkbox' disabled='true' checked='checked' style='width:20px;padding:0;margin:0;'/></a>");
			}else if(qar.getArFinishPlan()==2){
				sb.append("<a class='buptip' title='行政村有编制规划设计村数(个)' ><input    type='checkbox' checked='checked' style='width:20px;padding:0;margin:0;' disabled='true'/></a>");
			}else{
				sb.append("<a class='buptip' title='行政村有编制规划设计村数(个)' ><input    type='checkbox' style='width:20'x;padding:0;margin:0;' disabled='true' /></a>");
			}
			sb.append("</td>");
			sb.append("<td colspan='20'></td>");
			sb.append("</tr>");
			
			
			for(ModelAreaQuarterNaturalRural qnr:qar.getNaturaRurals()){
				sb.append("<tr class='c-").append(qar.getId()).append("'>");
				sb.append("<td colspan='5' style='padding-left:50px;'>").append(qnr.getNewRural().getNaturalVillage()).append("</td>");
				sb.append("<td></td>");
				if(qnr.getNeedFinish().getNeedFinish1() == 1){
					sb.append("<td><a class='buptip' title='自然村有编制规划设计村数(个)' ><input type='checkbox' disabled='true' checked='checked' style='width:20px;padding:0;margin:0;'/></a></td>");
				}else if(qnr.getNeedFinish().getNeedFinish1() ==2){
					sb.append("<td><a class='buptip' title='自然村有编制规划设计村数(个)' ><input   type='checkbox' checked='checked' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}else{
					sb.append("<td><a class='buptip' title='自然村有编制规划设计村数(个)' ><input   type='checkbox' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}
				
				if(qnr.getNeedFinish().getNeedFinish2() == 1){
					sb.append("<td><a class='buptip' title='通自来水自然村数(个)' ><input type='checkbox' disabled='true' checked='checked' style='width:20px;padding:0;margin:0;'/></a></td>");
				}else if(qnr.getNeedFinish().getNeedFinish2() ==2){
					sb.append("<td><a class='buptip' title='通自来水自然村数(个)' ><input   type='checkbox' checked='checked' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}else{
					sb.append("<td><a class='buptip' title='通自来水自然村数(个)' ><input   type='checkbox' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}
				
				if(qnr.getNeedFinish().getNeedFinish3() == 1){
					sb.append("<td><a class='buptip' title='完成卫生改厕所自然村数(个)' ><input type='checkbox' disabled='true' checked='checked' style='width:20px;padding:0;margin:0;'/></a></td>");
				}else if(qnr.getNeedFinish().getNeedFinish3() ==2){
					sb.append("<td><a class='buptip' title='完成卫生改厕所自然村数(个)' ><input   type='checkbox' checked='checked' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}else{
					sb.append("<td><a class='buptip' title='完成卫生改厕所自然村数(个)' ><input   type='checkbox' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}
				
				if(qnr.getNeedFinish().getNeedFinish4() == 1){
					sb.append("<td><a class='buptip' title=' 完成道路硬化建设自然村数(个)' ><input type='checkbox' disabled='true' checked='checked' style='width:20px;padding:0;margin:0;'/></a></td>");
				}else if(qnr.getNeedFinish().getNeedFinish4() ==2){
					sb.append("<td><a class='buptip' title=' 完成道路硬化建设自然村数(个) ' ><input   type='checkbox' checked='checked' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}else{
					sb.append("<td><a class='buptip' title=' 完成道路硬化建设自然村数(个) ' ><input   type='checkbox' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}
				
				if(qnr.getNeedFinish().getNeedFinish5() == 1){
					sb.append("<td><a class='buptip' title='完成民居外立面特色改造的自然村数(个)' ><input type='checkbox' disabled='true' checked='checked' style='width:20px;padding:0;margin:0;'/></a></td>");
				}else if(qnr.getNeedFinish().getNeedFinish5() ==2){
					sb.append("<td><a class='buptip' title='完成民居外立面特色改造的自然村数(个)' ><input   type='checkbox' checked='checked' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}else{
					sb.append("<td><a class='buptip' title='完成民居外立面特色改造的自然村数(个)' ><input   type='checkbox' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}
				
				if(qnr.getNeedFinish().getNeedFinish6() == 1){
					sb.append("<td><a class='buptip' title='完成卫生整治及建立保洁队和长效机制的自然村数(个)' ><input type='checkbox' disabled='true' checked='checked' style='width:20px;padding:0;margin:0;'/></a></td>");
				}else if(qnr.getNeedFinish().getNeedFinish6() ==2){
					sb.append("<td><a class='buptip' title='完成卫生整治及建立保洁队和长效机制的自然村数(个)' ><input   type='checkbox' checked='checked' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}else{
					sb.append("<td><a class='buptip' title='完成卫生整治及建立保洁队和长效机制的自然村数(个)' ><input   type='checkbox' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}
				
				if(qnr.getNeedFinish().getNeedFinish7() == 1){
					sb.append("<td><a class='buptip' title='实行雨污分流并建有污水处理设施自然村数(个)' ><input type='checkbox' disabled='true' checked='checked' style='width:20px;padding:0;margin:0;'/></a></td>");
				}else if(qnr.getNeedFinish().getNeedFinish7() ==2){
					sb.append("<td><a class='buptip' title='实行雨污分流并建有污水处理设施自然村数(个)' ><input   type='checkbox' checked='checked' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}else{
					sb.append("<td><a class='buptip' title='实行雨污分流并建有污水处理设施自然村数(个)' ><input   type='checkbox' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}
				
				if(qnr.getNeedFinish().getNeedFinish8() == 1){
					sb.append("<td><a class='buptip' title='实行人畜分离(集中圈养)的自然村数(个)' ><input type='checkbox' disabled='true' checked='checked' style='width:20px;padding:0;margin:0;'/></a></td>");
				}else if(qnr.getNeedFinish().getNeedFinish8() ==2){
					sb.append("<td><a class='buptip' title='实行人畜分离(集中圈养)的自然村数(个)' ><input   type='checkbox' checked='checked' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}else{
					sb.append("<td><a class='buptip' title='实行人畜分离(集中圈养)的自然村数(个)' ><input   type='checkbox' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}
				
				sb.append("<td colspan='11'></td>");
				
				if(qnr.getNeedFinish().getNeedFinish9() == 1){
					sb.append("<td><a class='buptip' title='已成立村民理事会并制定了村规民约和章程的自然村数(个)' ><input type='checkbox' disabled='true' checked='checked' style='width:20px;padding:0;margin:0;'/></a></td>");
				}else if(qnr.getNeedFinish().getNeedFinish9() ==2){
					sb.append("<td><a class='buptip' title='已成立村民理事会并制定了村规民约和章程的自然村数(个)' ><input   type='checkbox' checked='checked' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}else{
					sb.append("<td><a class='buptip' title='已成立村民理事会并制定了村规民约和章程的自然村数(个)' ><input   type='checkbox' style='width:20px;padding:0;margin:0;' disabled='true'/></a></td>");
				}
				sb.append("</tr>");
			}
		}
		String titleName = item.getCityName()+item.getModelArea().getName();
		map.put("deadline", item.getBatch().getQuarter().getDeadline());
		map.put("content", sb);
		map.put("name", titleName);
		map.put("hasItem", true);
		map.put("itemId", item.getId());
		return map;
	}
	
	/**
	 * 导出EXCEL
	 * @param itemId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/report.excel")
	public View excel(Long itemId){
		ModelAreaQuarterItem item = localQuarterReportService.get(itemId);
		return new CountyQuarterReportExcelView(item);
	}
	
	
	/**
	 * 打印
	 */
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@RequestMapping("!{key}/index.print")
	public ModelAndView print(HttpServletRequest request, int annual,int period, String gridid, ModelAndView model) {
		ModelAreaQuarterItem item = localQuarterReportService.getMaqitem(getUser().getOwnerId(), annual, period);
		model.addObject("title", "广东省" + item.getCityName()+item.getModelArea().getName() + "连片示范建设工程进展情况统计表");
		model.addObject("deadline", sdf.format(item.getBatch().getQuarter().getDeadline()));
		model.addObject("item", item);
		if(null!=item.getStartProject()){
			model.addObject("srart", item.getStartProject());
		}else{
			model.addObject("srart", 0);
		}
		if(null!=item.getFinishProject()){
			model.addObject("finish", item.getFinishProject());
		}else{
			model.addObject("finish", 0);
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
		
		model.addObject("totalfunds",totalfunds);
		List<ModelAreaQuarterAdministrationRural> qars = item.getAdminRurals();
		model.addObject("qars", qars);
		model.setViewName("report/quarter-report-county-view");
		return model;
	}
	
	
	
	

}
