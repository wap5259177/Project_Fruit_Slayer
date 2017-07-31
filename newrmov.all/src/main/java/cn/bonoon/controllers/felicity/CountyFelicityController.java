package cn.bonoon.controllers.felicity;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.controllers.felicity.countyreport.FelicityReportAnnualInfo;
import cn.bonoon.core.FelicityCountyReportService;
import cn.bonoon.core.ParamenterValue;
import cn.bonoon.entities.FelicityCountyReportEntity;
import cn.bonoon.entities.FelicityVillageEntity;
import cn.bonoon.entities.FelicityVillageReportEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.JsonResult;
import cn.bonoon.kernel.web.models.PanelModel;

/**
 * 县里的幸福村居
 * @author jackson
 *s/cl/fv/report
 */
public class CountyFelicityController extends AbstractPanelController{

	@Autowired
	private FelicityCountyReportService fcrService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		//还要判断一下当前的月份是否正在上报，如果正在上报，并且还没有完成上报的，则继续上报
		cal.add(Calendar.MONTH, -1);//上个月
		int selectedYear = cal.get(Calendar.YEAR);
		int lastMonth = cal.get(Calendar.MONTH);

		model.addObject("selectedYear", selectedYear);
		model.addObject("lastMonth", lastMonth);
		model.addObject("showMonth", lastMonth + 1);
		
		LogonUser user = getUser();
		List<FelicityCountyReportEntity> reports = fcrService.reports(user, selectedYear);

		FelicityCountyReportEntity report = __annual(model.getModelMap(), reports, selectedYear, year, month, selectedYear, lastMonth);

		StringBuilder bodyContent = new StringBuilder();
		if(null == report){
			//上个月还没有开始报
			//还没开始，那提示下可以创建吧
			bodyContent.append("<tr><td class='reportMessage' colspan='11'>请先 <a href='report!");
			bodyContent.append(selectedYear).append('!').append(lastMonth).append(".start' onclick=\"return startReport(this, '");
			bodyContent.append(selectedYear * 100 + lastMonth).append("');\"> 确定 </a> 开始上报项目的月度报表</td></tr>");
		}else{
			__parseContent(model.getModelMap(), bodyContent, report);
		}
		model.addObject("bodyContent", bodyContent);
		event.setVmPath("felicity/cl-report");
	}
	@RequestMapping(value = "!{key}/annual!{currentYear}.goto")
	public String moveAnnual(HttpServletRequest request, Model model, 
			@PathVariable("currentYear") int currentYear, int selectedYear, int selectedMonth){
		model.addAttribute("layout", "layout-empty.vm");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		LogonUser user = getUser();
		List<FelicityCountyReportEntity> reports = fcrService.reports(user, currentYear);
		 __annual(model.asMap(), reports, currentYear, year, month, selectedYear, selectedMonth);
		
		return "felicity/annual-selector";
	}
	private FelicityCountyReportEntity __annual(Map<String, Object> model, List<FelicityCountyReportEntity> reports, 
			int currentYear, int nowYear, int nowMonth, int selectedYear, int selectedMonth){

		model.put("preYear", currentYear - 1);
		model.put("nextYear", currentYear + 1);
		model.put("currentYear", currentYear);
		
		int nowMonthly = nowYear * 100 + nowMonth;
		int selectedMonthly = selectedYear * 100 + selectedMonth;
		FelicityCountyReportEntity current = null;
		List<FelicityReportAnnualInfo> items = new ArrayList<>();
		for(int i = 0; i < 12; i++){
			FelicityReportAnnualInfo pra = new FelicityReportAnnualInfo(reports, currentYear, i, nowMonthly, selectedMonthly);
			items.add(pra);
			if(pra.isSelected()){
				current = pra.getReport();
			}
		}
		model.put("items", items);
		return current;
	}
	
//	private void __append(StringBuilder content, Long pid, Object temp, String code){
//		content.append("<th><input type='text' name='").append(code).append("-").append(pid).append("' class='fund' value='").append(temp).append("'/></th>");
//	}

//	private final String[] codes = {"vi","co"};
	
	private void __parseContent(Map<String, Object> model, StringBuilder content, FelicityCountyReportEntity report){
		int i = 0, status;
		boolean editing = false;
		status = report.getStatus();
		model.put("report", report);
		
		if(status == 0 || status == 3){
			editing = true;
			//重新再处理一下该月份的项目是否有增加的情况
		}
		Long rid=report.getId();
		if(editing){
		content.append("<tr><th colspan='4' style='font-weight:bold;font-size:14px;'>").append(report.getCounty().getCityName()).append(report.getCounty().getPlace().getName()).append("幸福村居示范片小计</th>");
		content.append("<th>-</th><th>-</th><th>-</th><th>-</th><th>-</th>");
		content.append("<th><input type='text' class='_fundg' name='gpp-").append(rid).append("'value='").append(report.getProjectProgress()).append("'</th>");
		content.append("<th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th>");
		content.append("<th><input type='text' class='_fundg' name='gpr-").append(rid).append("' value='").append(report.getPlanningProgress()).append("'</th><th></th>");
		content.append("<th><input type='text' class='_fundg' name='gbp-").append(rid).append("' value='").append(report.getBiddingProgress()).append("'</th>");
		content.append("<th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th>");
		content.append("</tr>");
		}else{
			content.append("<tr><th colspan='4' style='font-weight:bold;font-size:14px;'>").append(report.getCounty().getCityName()).append(report.getCounty().getPlace().getName()).append("幸福村居示范片小计</th>");
			content.append("<th>-</th><th>-</th><th>-</th><th>-</th><th>-</th>");
			content.append("<th>").append(report.getProjectProgress()).append("</th>");
			content.append("<th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th>");
			content.append("<th>").append(report.getPlanningProgress()).append("</th><th></th>");
			content.append("<th>").append(report.getBiddingProgress()).append("</th>");
			content.append("<th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th><th>-</th>");
			content.append("</tr>");
		}
		for(FelicityVillageReportEntity pre : report.getItems()){
			//FelicityCountyReportEntity fec = pre.getCountyReport();
			FelicityVillageEntity fve=pre.getVillage();
			//String next=pre.getNextStagePlanning();
			//if(fec.getStatus() != 1){continue;}//只有在正常状态下才可以上报月度报表
			i++;
			content.append("<tr");
			if(editing){
				content.append(" editable='true'");
			}
			content.append("><th style='width:40px' ordinal='").append(i).append("'>").append(i);
			content.append("</th><th style='width:200px'>").append(fve.getTownName()).append(fve.getName()).append(fve.getNaturalVillage()).append("</th>");
			content.append("<th style='width:100px;'>").append(fve.getConstructionType()).append("</th>");
			content.append("<th style='width:75px'>");
			if(null!=fve.getVillageType()){
				content.append(fve.getVillageType());
			}
			content.append("</th>");
			if(editing){
			  Long pid = pre.getId();
			  content.append("<th style='width:80px'><input type='text' name='co-").append(pid).append("' class='m-fund' value='").append(pre.getConstructionArea()).append("'/></th>");
			  content.append("<th style='width:80px'><input type='text' name='ho-").append(pid).append("' class='i-fund' value='").append(pre.getHouseholdCount()).append("'/></th>");
			  content.append("<th style='width:80px'><input type='text' name='po-").append(pid).append("' class='i-fund' value='").append(pre.getPopulation()).append("'/></th>");
			
			  content.append("<th style='width:80px'><select class='_sele' style='width:80px;' name='con-").append(pid).append("'>");
			  content.append("<option value='人文历史'>人文历史</option><option value='乡村旅游'>乡村旅游</option><option value='自然生态'>自然生态</option>");
			  content.append("<option value='特色产业'>特色产业</option><option value='民居风貌'>民居风貌</option><option value='渔业渔港'>渔业渔港</option>");
			  content.append("<option value='其它'>其它</option></select></th>");
			  content.append("<th style='width:80px'><input type='text' name='pr-").append(pid).append("' class='i-fund' value='").append(pre.getProjectCount()).append("'/></th>");
			  content.append("<th style='width:80px'><input type='text' name='pro-").append(pid).append("' class='m-fund' value='").append(pre.getProjectProgress()).append("'/></th>");
			  content.append("<th style='width:80px'><input type='text' name='in-").append(pid).append("' class='fund' value='").append(pre.getInvestmentBudget()).append("'/></th>");
			  content.append("<th style='width:80px'><input type='text' name='inv-").append(pid).append("' class='fund' value='").append(pre.getInvestmentCompleted()).append("'/></th>");
			  
			  content.append("<th style='width:80px'><input type='text' name='fu-").append(pid).append("' class='t-fund' readonly='readonly' value='").append(pre.getFundsTotal()).append("'/></th>");//资金来源（万元）
			  content.append("<th style='width:80px'><input type='text' name='fun-").append(pid).append("' class='c-fund' value='").append(pre.getFundsProvince()).append("'/></th>");
			  content.append("<th style='width:80px'><input type='text' name='funs-").append(pid).append("' class='c-fund' value='").append(pre.getFundsCity()).append("'/></th>");
			  content.append("<th style='width:80px'><input type='text' name='funt-").append(pid).append("' class='c-fund' value='").append(pre.getFundsCounty()).append("'/></th>");
			  content.append("<th style='width:80px'><input type='text' name='funv-").append(pid).append("' class='c-fund' value='").append(pre.getFundsTown()).append("'/></th>");
			  content.append("<th style='width:80px'><input type='text' name='fundv-").append(pid).append("' class='c-fund' value='").append(pre.getFundsVillage()).append("'/></th>");
			  content.append("<th style='width:80px'><input type='text' name='funm-").append(pid).append("' class='c-fund' value='").append(pre.getFundsMasses()).append("'/></th>");
			  content.append("<th style='width:80px'><input type='text' name='funs-").append(pid).append("' class='c-fund' value='").append(pre.getFundsSociety()).append("'/></th>");
			  content.append("<th style='width:80px'><input type='text' name='funo-").append(pid).append("' class='c-fund' value='").append(pre.getFundsOther()).append("'/></th>"); 
			  content.append("<th style='width:70px'><input type='checkbox' style='width:35px'  name='pc-").append(pid).append("'id='pc-").append(pid).append("' value='true' ");//.append(?").append("'/>是</th>");
					  if(pre.isPlanningCompleted()){
						  content.append(" checked='checked'");
					  }
					  content.append("/><label style='width:35px' for='pc-").append(pid).append("'>是</label></th>");
			  content.append("<th style='width:70px'><input type='text' name='pp-").append(pid).append("' class='m-fund' value='").append(pre.getPlanningProgress()).append("'/></th>");
		
			  content.append("<th style='width:70px'><input type='checkbox' style='width:35px' name='bc-").append(pid).append("'id='bc-").append(pid).append("' value='true' ");
			         if(pre.isBiddingCompleted()){
				          content.append(" checked='checked'");
			         }
			         content.append("/><label style='width:35px' for='bc-").append(pid).append("'>是</label></th>");
			  content.append("<th style='width:70px'><input type='text' name='bp-").append(pid).append("' class='m-fund' value='").append(pre.getBiddingProgress()).append("'/></th>");
			  
			  content.append("<th style='width:70px'><input type='checkbox' style='width:35px' name='er-").append(pid).append("'id='er-").append(pid).append("' value='true' ");
		         if(pre.isEffectRemediation()){
			          content.append(" checked='checked'");
		         }
		         content.append("/><label style='width:35px' for='er-").append(pid).append("'>是</label></th>");
			
			  content.append("<th style='width:70px'><input type='checkbox' style='width:35px' name='eu-").append(pid).append("' id='eu-").append(pid).append("' value='true' ");
		         if(pre.isEffectUniformStyle()){
			          content.append(" checked='checked'");
		         }
		         content.append("/><label style='width:35px' for='eu-").append(pid).append("'>是</label></th>");
			 
			  content.append("<th style='width:70px'><input type='checkbox' style='width:35px' name='es-").append(pid).append("'  id='es-").append(pid).append("' value='true' ");
		         if(pre.isEffectSolveGarbage()){
			          content.append(" checked='checked'");
		         }
		         content.append("/><label style='width:35px' for='es-").append(pid).append("'>是</label></th>");
			 
			  content.append("<th style='width:70px'><input type='checkbox' style='width:35px' name='et-").append(pid).append("'  id='et-").append(pid).append("' value='true' ");
		         if(pre.isEffectSewageTreatment()){
			          content.append(" checked='checked'");
		         }
		         content.append("/><label style='width:35px' for='et-").append(pid).append("'>是</label></th>");
			  content.append("<th style='width:70px'><input type='checkbox' style='width:35px' name='ec-").append(pid).append("' id='ec-").append(pid).append("' value='true' ");
		         if(pre.isEffectCleaningTeam()){
			          content.append(" checked='checked'");
		         }
		         content.append("/><label style='width:35px' for='ec-").append(pid).append("'>是</label></th>");
			  content.append("<th style='width:70px'><input type='checkbox' style='width:35px' name='efc-").append(pid).append("' id='efc-").append(pid).append("' value='true' ");
		         if(pre.isEffectCouncil()){
			          content.append(" checked='checked'");
		         }
		         content.append("/><label style='width:35px' for='efc-").append(pid).append("'>是</label></th>");
			 
			  content.append("<th style='width:70px'><input type='checkbox' style='width:35px' name='eh-").append(pid).append("'  id='eh-").append(pid).append("' value='true' ");
		         if(pre.isEffectHardBottom()){
			          content.append(" checked='checked'");
		         }
		         content.append("/><label style='width:35px' for='eh-").append(pid).append("'>是</label></th>");
			 
			  content.append("<th style='width:70px'><input type='checkbox' style='width:35px' name='etw-").append(pid).append("' id='etw-").append(pid).append("' value='true' ");
		         if(pre.isEffectThroughWater()){
			          content.append(" checked='checked'");
		         }
		         content.append("/><label style='width:35px' for='etw-").append(pid).append("'>是</label></th>");
			  content.append("<th style='width:70px'><input type='text' name='eo-").append(pid).append("' class='s-fund' value='");
			  if(null!=pre.getEffectOther()){
				  content.append(pre.getEffectOther());
			  }
			  content.append("'/></th>");
			  content.append("<th style='width:200px'><input type='text' name='ds-").append(pid).append("'style='width:95%;' value='");
			  if(null!=pre.getNextStagePlanning()){
				  content.append(pre.getNextStagePlanning());
			  }
			  content.append("'/></th></tr>");
			}else{
			  content.append("<th style='width:80px'>").append(pre.getConstructionArea()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getHouseholdCount()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getPopulation()).append("</th>");
			  content.append("<th style='width:80px'>");
			  if(null!=pre.getConstructionCharacteristic()){
				  content.append(pre.getConstructionCharacteristic());  
			  }
			  content.append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getProjectCount()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getProjectProgress()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getInvestmentBudget()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getInvestmentCompleted()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getFundsTotal()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getFundsProvince()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getFundsCity()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getFundsCounty()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getFundsTown()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getFundsVillage()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getFundsMasses()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getFundsSociety()).append("</th>");
			  content.append("<th style='width:80px'>").append(pre.getFundsOther()).append("</th>");
			  content.append("<th style='width:70px'>").append(pre.isPlanningCompleted()?"是":"否").append("</th>");
			  content.append("<th style='width:70px'>").append(pre.getPlanningProgress()).append("</th>");
			  content.append("<th style='width:70px'>").append(pre.isBiddingCompleted()?"是":"否").append("</th>");
			  content.append("<th style='width:70px'>").append(pre.getBiddingProgress()).append("</th>");
			  content.append("<th style='width:70px'>").append(pre.isEffectRemediation()?"是":"否").append("</th>");
			  content.append("<th style='width:70px'>").append(pre.isEffectUniformStyle()?"是":"否").append("</th>");
			  content.append("<th style='width:70px'>").append(pre.isEffectSolveGarbage()?"是":"否").append("</th>");
			  content.append("<th style='width:70px'>").append(pre.isEffectSewageTreatment()?"是":"否").append("</th>");
			  content.append("<th style='width:70px'>").append(pre.isEffectCleaningTeam()?"是":"否").append("</th>");
			  content.append("<th style='width:70px'>").append(pre.isEffectCouncil()?"是":"否").append("</th>");
			  content.append("<th style='width:70px'>").append(pre.isEffectHardBottom()?"是":"否").append("</th>");
			  content.append("<th style='width:70px'>").append(pre.isEffectThroughWater()?"是":"否").append("</th>");
			  content.append("<th style='width:70px'>");
			  if(null != pre.getEffectOther()){
				  content.append(pre.getEffectOther());
			  }
			  content.append("</th>");
			  content.append("<th style='width:200px'>");
			  if(null != pre.getNextStagePlanning()){
				  content.append(pre.getNextStagePlanning());
			  }
			  content.append("</th></tr>");
			}
		}
		if(editing){
		content.append("<tr style='height:30px;'><th style='text-align:left;' colspan='4'><span style='font-weight:bold;'>填报人:</span><input type='text' style='width:100px;' name='im-").append(rid).append("' value='");
		if(null != report.getInformant()){
		  content.append(report.getInformant());
		}
		content.append("'/></th>");
		content.append("<th style='text-align:left;' colspan='5'><span style='font-weight:bold;'>联系电话:</span><input type='text' style='width:100px;' name='ct-").append(rid).append("' value='");
		if(null !=report.getContact()){
			content.append(report.getContact());
		}
		content.append("'/></th>");
		content.append("<th style='text-align:left;' colspan='27'><span style='font-weight:bold;'>填报时间:</span><input type='text' id='_reportAt' editable='false' style='width:100px;' name='at-").append(rid).append("' value='");
		if(null!=report.getReportingAt()){
			content.append(report.getReportingAt());
		}
		content.append("'/>");
		content.append("</th></tr>");
		}else{
			content.append("<tr style='height:30px;'><th style='text-align:left;' colspan='4'><span style='font-weight:bold;'>填报人:</span>");
			if(null!=report.getInformant()){
				content.append(report.getInformant());
			}
			content.append("</th><th style='text-align:left;' colspan='4'><span style='font-weight:bold;'>联系电话:</span>");
			if(null!=report.getReportingAt()){
		    content.append(report.getContact());
			}
			content.append("</th><th style='text-align:left;' colspan='27'><span style='font-weight:bold;'>填报时间:</span>");
			if(null!=report.getReportingAt()){
				content.append(report.getReportingAt());
			}
			content.append("</th></tr>");
		}
		if(report.getItems().size() > 10){
			model.put("searchable", true);
		}
		model.put("editable", editing);
	}
	@RequestMapping(value = "!{key}/report!{year}!{month}.start")
	public String start(HttpServletRequest request, Model model, Long id,
			@PathVariable("year") int year, @PathVariable("month") int month){
		StringBuilder content = new StringBuilder();
		try{
			LogonUser user = getUser();
			FelicityCountyReportEntity report = null;
			if(null != id){
				report = fcrService.report(user, id);
			}
			int ed = year * 100 + month;
			if(null == report){
				Calendar cal = Calendar.getInstance();
				int cyear = cal.get(Calendar.YEAR);
				int cmonth = cal.get(Calendar.MONTH);
				
				int nd = cyear * 100 + cmonth;
				if(ed > nd){
					throw new Exception("只允许上报本月及之前的项目月度汇总表！");
				}
				report = fcrService.start(user, year, month);
			}
			
			model.addAttribute("currentMonthly", ed);
			model.addAttribute("selectedYear", year);
			model.addAttribute("lastMonth", month);
			model.addAttribute("showMonth", month + 1);
			
			if(null == report){
				content.append("<tr><td class='reportMessage' colspan='11'>").append(year)
					.append("年").append(month + 1).append("月暂时没有任何项目需要上报月度报表！</td></tr>");
			}else{
				__parseContent(model.asMap(), content, report);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			content.append("<tr><td class='reportMessage' colspan='11'>").append(ex.getMessage()).append("</td></tr>");
		}
		model.addAttribute("reports", content);
		return "felicity/reporting-item";
	}
	@ResponseBody
	@RequestMapping(value = "!{key}/report.save", method = POST)
	public JsonResult save(final HttpServletRequest request, Long rid, String toSubmit){
		try{
			if(null == rid){
				throw new Exception("没有需要保存的项目月度报表");
			}
			fcrService.save(rid, new ParamenterValue(){

				@Override
				public double getDouble(Long id, String pre) {
					String val = request.getParameter(pre + id);
					return StringHelper.todouble(val);
				}

				@Override
				public int getInt(Long id, String pre) {
					return StringHelper.toint(request.getParameter(pre + id));
				}

				@Override
				public boolean getboolean(Long id, String pre) {
					
					return StringHelper.toboolean(request.getParameter(pre + id));
				}

				@Override
				public String getString(Long id, String pre) {
					String val = request.getParameter(pre + id);
					return val;
				}

				@Override
				public Date getDate(Long id, String pre) {
					
					return  StringHelper.toDate(request.getParameter(pre + id));
				}

				@Override
				public String[] getStrings(Long id, String pre) {
					return request.getParameterValues(pre + id);
				}
				
			}, "true".equals(toSubmit));
			return JsonResult.result();
		}catch(Exception ex){
			ex.printStackTrace();
			return JsonResult.error(ex);
		}
	}
}
