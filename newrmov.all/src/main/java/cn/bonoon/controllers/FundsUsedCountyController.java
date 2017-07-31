package cn.bonoon.controllers;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.ProjectReportService;
import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ProjectReportEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;

/**
 * 资金使用报表，项目各项资金使用情况统计
 * @author jackson
 *
 */
@Controller
@RequestMapping("s/cl/project/report/allfunds/used")
public class FundsUsedCountyController extends AbstractPanelController{

	@Autowired
	private ProjectReportService projectReportService;
	
	private final String monthArray[] = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		event.setVmPath("fundsused-county");
		//12个月

		//
		Long id = event.getUser().getOwnerId();
		int[] am = projectReportService.lastMonthly(id);
		if(null != am){
			int lastyear = am[0], lastmonth = am[1];
			Calendar cal = Calendar.getInstance();
			cal.set(lastyear, lastmonth, 1);
			cal.add(Calendar.YEAR, -1);
			int startyear = cal.get(Calendar.YEAR);
			int startMonth = cal.get(Calendar.MONTH);
			
			List<ProjectReportEntity> list = projectReportService.getFunds(id, startyear, startMonth, lastyear, lastmonth);
			
			
//			HashMap<Object, CountyFundsInfo> maps = new HashMap<>();
//			for(Object[] ds : list){
//				Object cid = ds[0];
//				CountyFundsInfo fi = maps.get(cid);
//				if(null == fi){
//					fi = new CountyFundsInfo();
//					maps.put(cid, fi);
//				}
//				fi.add(ds);
//			}
			
			StringBuilder fundsData = new StringBuilder();
			StringBuilder monthBuilder = new StringBuilder();
			boolean ny = true;
			String l1 = "";
			String l2 = "";
			String l3 = "";
			String l4 = "";
			String l5 = "";
			String l6 = "";
			String l7 = "";
			String l8 = "";
			int yy = startyear;
			for(int i = 0; i < 12; i++){
				
				if(i == 0){
					monthBuilder.append("'").append(yy).append("年");
					
				}else{
					monthBuilder.append(",'");
				}
				int mm = startMonth + i;
				if(mm >= 12){
					if(ny){
						yy++;
						monthBuilder.append(yy).append("年");
						
						ny = false;
					}
					mm -= 12;
				}
				monthBuilder.append(monthArray[mm]).append("'");
				ProjectReportEntity pr = __find(list, yy, mm);
				if(null == pr){
					l1 += ",0";
					l2 += ",0";
					l3 += ",0";
					l4 += ",0";
					l5 += ",0";
					l6 += ",0";
					l7 += ",0";
					l8 += ",0";
				}else{
					InvestmentInfo inv = pr.getInvestment();
					l1+= "," + inv.getStateFunds();
					l2+= "," + inv.getSpecialFunds();
					l3+= "," + inv.getProvinceFunds();
					l4+= "," + inv.getCityFunds();
					l5+= "," + inv.getCountyFunds();
					l6+= "," + inv.getSocialFunds();
					l7+= "," + inv.getMassFunds();
					l8+= "," + inv.getOtherFunds();
				}
			}
			
			fundsData.append("{name:'中央',type:'line',data:[").append(l1.substring(1)).append("]},");
			fundsData.append("{name:'省级新农村连片示范工程建设补助资金',type:'line',data:[").append(l2.substring(1)).append("]},");
			fundsData.append("{name:'其他省级财政资金',type:'line',data:[").append(l3.substring(1)).append("]},");
			fundsData.append("{name:'市级财政资金',type:'line',data:[").append(l4.substring(1)).append("]},");
			fundsData.append("{name:'县级财政资金',type:'line',data:[").append(l5.substring(1)).append("]},");
			fundsData.append("{name:'社会',type:'line',data:[").append(l6.substring(1)).append("]},");
			fundsData.append("{name:'群众自筹',type:'line',data:[").append(l7.substring(1)).append("]},");
			fundsData.append("{name:'其他',type:'line',data:[").append(l8.substring(1)).append("]}");
			
			StringBuilder jsContent = new StringBuilder();
			jsContent.append("var myChart = echarts.init(document.getElementById('hc_line'));");
			jsContent.append("var option = {title:{text:'各项资金总投入情况:'},tooltip : {'trigger':'axis'},calculable : true,");
			jsContent.append("legend: {x:'center',data:['中央','省级新农村连片示范工程建设补助资金','其他省级财政资金','市级财政资金','县级财政资金','社会','群众自筹','其他']},xAxis : [{type : 'category',splitLine : {show : false},");
			jsContent.append("data : [").append(monthBuilder).append("]}],yAxis : [{type : 'value'}],series:[")
			.append(fundsData).append("]};myChart.setOption(option);");
			
			
//			model.addAttribute("names", countyNames.substring(1));
//			model.addAttribute("countydata", countyData);
//			model.addAttribute("keyset", maps.keySet());
			event.getModel().addObject("jsContent", jsContent);
			
		}else{
			event.getModel().addObject("aaa",0);
		}
		//
	}
	
//	private class CountyFundsInfo{
//		Map<Integer, Double> data = new HashMap<>();
//		void add(Object[] ds){
//			
//			Integer key = (int)ds[1] * 100 + (int)ds[2];
//			Double d = data.get(key);
//			if(null != d){
//				d += (double)ds[3];
//			}else{
//				d = (double)ds[3];
//			}
//			data.put(key, d);
//		}
//		//生成统计表的数据
//		Double statistics(StringBuilder cd, int y, int m, int offset){
//			int mm = m + offset;
//			if(mm >= 12){
//				y++;
//				mm -= 12;
//			}
//			Integer key = y * 100 + mm;
//			Double d = data.get(key);
//			if(null == d){
//				d = 0d;
//			}
//			if(offset > 0){
//				cd.append(',');
//			}
//			
//			return d;
//		}
//	}
//	
//	@RequestMapping(value = "!{mid}/countyfunds.statistics", method =  RequestMethod.POST)
//	public String statistics(Model model, Long id){
//		model.addAttribute("layout", "layout-empty.vm");
//		
//			
//		
//		return "fundsused-county-content";
//	}
	private ProjectReportEntity __find(List<ProjectReportEntity> list, int y, int m){
		for(ProjectReportEntity pr : list){
			if(pr.getAnnual() == y && pr.getPeriod() == m) return pr;
		}
		return null;
	}
}
