package cn.bonoon.controllers;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.ProjectReportService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.DialogModel;

/**
 * 资金使用报表，省级专项资金使用情况统计
 * @author jackson
 *
 */
@Controller
@RequestMapping("s/report/funds/used")
public class FundsUsedController extends AbstractPanelController { 

	
	@Autowired
	private ProjectReportService projectReportService;
	
	private final String monthArray[] = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		event.setVmPath("funds-used");
		
//		PanelModel model = event.getModel();
//		StringBuilder countyNames = new StringBuilder("[");
//		StringBuilder countyData = new StringBuilder();
//		StringBuilder jsContent = new StringBuilder();
//		StringBuilder monthBuilder = new StringBuilder();
//		HashMap<String, FundUsedInfo> maps = new HashMap<>();
//		List<?> list = projectReportService.getAllLocalFunds();
//		for (Object object : list) {
//			Object[] objs = (Object[]) object;
//			String name = (String)objs[0];
//			double funs = (double)objs[1];
//			int annual = (int)objs[2];
//			int month = (int)objs[3];
//			if(!maps.containsKey(name)){
//				FundUsedInfo info = new FundUsedInfo(name, funs, annual, month);
//				maps.put(name, info);
//			}else{
//				FundUsedInfo info = maps.get(name);
//				info.setFunds(month, annual, funs);
//			}
//		}
//		int i = 0;
//		for (String name : maps.keySet()) {
//			if(i==0)
//				countyNames.append("'").append(name).append("'");
//			else
//				countyNames.append(",'").append(name).append("'");
//			i++;
//		}
//		countyNames.append("]");
//		for (FundUsedInfo info : maps.values()) {
//			countyData.append(",{name:'").append(info.getName()).append("',type:'line',tooltip : {trigger: 'item'}, data: [")
//						.append(info.getFundContent());
////			for (int j = 0; j < info.getLocalFunds().length; j++) {
////				if(j == 0)
////					countyData.append(info.getLocalFunds()[j]);
////				else
////					countyData.append(",").append(info.getLocalFunds()[j]);
////					
////			};
//			countyData.append("]}");
//		}
//		Calendar cal = Calendar.getInstance();
//		int monthIndex = cal.get(Calendar.MONTH) + 1;
//		for (int j = 0; j < 12; j++) {
//			monthBuilder.append(",'");
//			if(monthIndex > 11){
//				monthBuilder.append(monthArray[0]);
//				monthIndex = 0;
//			}else{
//				monthBuilder.append(monthArray[monthIndex]);
//			}
//			monthBuilder.append("'");
//			monthIndex++;
//		}
//		jsContent.append("var myChart = echarts.init(document.getElementById('hc_line'));" +
//						"var option = {title:{text:'投入情况:',subtext:'省专项资金：'},tooltip : {'trigger':'axis'}," +
//						"legend: {x:'right',data:").append(countyNames).append(",},xAxis : [{type : 'category',splitLine : {show : false}," +
//						"data : [").append(monthBuilder.substring(1)).append("]}],yAxis : [{type : 'value'}],series:[")
//				.append(countyData.substring(1))
//				.append("]};myChart.setOption(option);");		
//		model.addObject("names", countyNames);
//		model.addObject("countydata", countyData);
//		model.addObject("keyset", maps.keySet());
//		model.addScript(jsContent);
	}
	

	private class $FundsInfo{
		Map<Integer, Double> data = new HashMap<>();
		String name;//片区名
		void add(Object[] ds){
			if(null == name){
				name = ds[1].toString();
			}
			Integer key = (int)ds[2] * 100 + (int)ds[3];
			Double d = data.get(key);
			if(null != d){
				d += (double)ds[4];
			}else{
				d = (double)ds[4];
			}
			data.put(key, d);
		}
		//生成统计表的数据
		Double statistics(StringBuilder cd, StringBuilder ct, int y, int m, int offset, Double sum){
			int mm = m + offset;
			if(mm >= 12){
				y++;
				mm -= 12;
			}
			Integer key = y * 100 + mm;
			Double d = data.get(key);
			if(null == d){
				d = 0d;
			}
			if(offset > 0){
				cd.append(',');
			}
			double resu = add(sum,d);
			cd.append(resu);
			ct.append("<td>").append(resu).append("</td>");
			return d;
		}
		private double add(double d0, double d1) {
			return BigDecimal.valueOf(d0).add(BigDecimal.valueOf(d1)).doubleValue();
		}
	}
	
	@RequestMapping(value = "!{mid}/funds.statistics", method =  RequestMethod.POST)
	public String statistics(Model model, String batch, String calType){
		model.addAttribute("layout", "layout-empty.vm");
		batch = BatchHelper.get(batch);
		
		int[] am = projectReportService.lastMonthly(batch);
		if(null != am){
			int lastyear = am[0], lastmonth = am[1];
			Calendar cal = Calendar.getInstance();
			cal.set(lastyear, lastmonth, 1);
			cal.add(Calendar.YEAR, -1);
			int startyear = cal.get(Calendar.YEAR);
			int startMonth = cal.get(Calendar.MONTH);
			List<Object[]> list = projectReportService.getFunds(batch, startyear, startMonth, lastyear, lastmonth);

			HashMap<Object, $FundsInfo> maps = new HashMap<>();
			for(Object[] ds : list){
				Object cid = ds[0];
				$FundsInfo fi = maps.get(cid);
				if(null == fi){
					fi = new $FundsInfo();
					maps.put(cid, fi);
				}
				fi.add(ds);
			}
			//取名称
			StringBuilder countyNames = new StringBuilder();
			StringBuilder countyData = new StringBuilder();
			StringBuilder countyTable = new StringBuilder();

			StringBuilder monthBuilder = new StringBuilder();
			countyTable.append("<thead><tr><th></th>");
			boolean ny = true;
			for(int i = 0; i < 12; i++){
				countyTable.append("<th style='width:");
				if(i == 0){
					monthBuilder.append("'").append(startyear).append("年");
					countyTable.append("100px;'>").append(startyear).append("年");
				}else{
					monthBuilder.append(",'");
				}
				int mm = startMonth + i;
				if(mm >= 12){
					if(ny){
						int yy = startyear + 1;
						monthBuilder.append(yy).append("年");
						countyTable.append("100px;'>").append(yy).append("年");
						ny = false;
					}else{
						countyTable.append("70px;'>");
					}
					mm -= 12;
				}else if(i > 0){
					countyTable.append("70px;'>");
				}
				monthBuilder.append(monthArray[mm]).append("'");
				countyTable.append(monthArray[mm]).append("</th>");
			}
			countyTable.append("</tr></thead>");
			
			boolean up = "1".equals(calType);
			
			for ($FundsInfo f : maps.values()) {
				countyNames.append(",'").append(f.name).append("'");

				countyData.append(",{name:'").append(f.name).append("',type:'line',tooltip : {trigger: 'item'}, data: [");
				
				countyTable.append("<tr><td>").append(f.name).append("</td>");
				Double sum = 0D, cur = 0D;
				for(int i = 0; i < 12; i++){
					cur = f.statistics(countyData, countyTable, startyear, startMonth, i, sum);
					if(up){
						sum += cur;
					}
				}

				countyData.append("]}");
				countyTable.append("</tr>");
			}
//			countyNames.append("]");
			StringBuilder jsContent = new StringBuilder();
			jsContent.append("var myChart = echarts.init(document.getElementById('hc_line'));");
			jsContent.append("var option = {title:{text:'投入情况:',subtext:'省专项资金：'},tooltip : {'trigger':'axis'},");
			jsContent.append("legend: {x:'right',data:[").append(countyNames.substring(1)).append("],},xAxis : [{type : 'category',splitLine : {show : false},");
			jsContent.append("data : [").append(monthBuilder).append("]}],yAxis : [{type : 'value'}],series:[")
			.append(countyData.substring(1)).append("]};myChart.setOption(option);");
			
			
//			model.addAttribute("names", countyNames.substring(1));
//			model.addAttribute("countydata", countyData);
//			model.addAttribute("keyset", maps.keySet());
			model.addAttribute("jsContent", jsContent);
			model.addAttribute("countyTable", countyTable);
		}else{
			//
			model.addAttribute("aaa", 0);
		}
		
//		HashMap<String, FundUsedInfo> maps = new HashMap<>();
//		List<?> list = projectReportService.getAllLocalFunds(batch, "1".equals(calType));
//		if(!list.isEmpty()){
//		for (Object object : list) {
//			Object[] objs = (Object[]) object;
//			String name = (String)objs[0];
//			double funs = (double)objs[1];
//			int annual = (int)objs[2];
//			int month = (int)objs[3];
//			if(!maps.containsKey(name)){
//				FundUsedInfo info = new FundUsedInfo(name, funs, annual, month);
//				maps.put(name, info);
//			}else{
//				FundUsedInfo info = maps.get(name);
//				info.setFunds(month, annual, funs);
//			}
//		}
//		int i = 0;
//		for (String name : maps.keySet()) {
//			if(i==0)
//				countyNames.append("'").append(name).append("'");
//			else
//				countyNames.append(",'").append(name).append("'");
//			i++;
//		}
//		countyNames.append("]");
//		for (FundUsedInfo info : maps.values()) {
//			countyData.append(",{name:'").append(info.getName()).append("',type:'line',tooltip : {trigger: 'item'}, data: [")
//						.append(info.getFundContent());
////			for (int j = 0; j < info.getLocalFunds().length; j++) {
////				if(j == 0)
////					countyData.append(info.getLocalFunds()[j]);
////				else
////					countyData.append(",").append(info.getLocalFunds()[j]);
////					
////			};
//			countyData.append("]}");
//		}
//		Calendar cal = Calendar.getInstance();
//		int monthIndex = cal.get(Calendar.MONTH) + 1;
//		for (int j = 0; j < 12; j++) {
//			monthBuilder.append(",'");
//			if(monthIndex > 11){
//				monthBuilder.append(monthArray[0]);
//				monthIndex = 0;
//			}else{
//				monthBuilder.append(monthArray[monthIndex]);
//			}
//			monthBuilder.append("'");
//			monthIndex++;
//		}
//		jsContent.append("var myChart = echarts.init(document.getElementById('hc_line'));" +
//						"var option = {title:{text:'投入情况:',subtext:'省专项资金：'},tooltip : {'trigger':'axis'}," +
//						"legend: {x:'right',data:[").append(countyNames).append("],},xAxis : [{type : 'category',splitLine : {show : false}," +
//						"data : [").append(monthBuilder.substring(1)).append("]}],yAxis : [{type : 'value'}],series:[")
//				.append(countyData.substring(1))
//				.append("]};myChart.setOption(option);");		
//		model.addAttribute("names", countyNames);
//		model.addAttribute("countydata", countyData);
//		model.addAttribute("keyset", maps.keySet());
//		model.addAttribute("jsContent", jsContent);
			
//		}else{
//			JOptionPane.showMessageDialog(null,"没有数据");
//		}
		return "funds-used-content";
	}

	@RequestMapping(value = "!{mid}/piechart/dialog", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView piechart(HttpServletRequest request, @PathVariable("mid") String mid, Long id) {
		DialogModel model = new DialogModel(mid, request);
		return model.execute("funds-used-piechart-dlg");
	}

}
