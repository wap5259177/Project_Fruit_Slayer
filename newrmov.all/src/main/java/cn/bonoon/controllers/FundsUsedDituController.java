//package cn.bonoon.controllers;
//
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.swing.JOptionPane;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import cn.bonoon.core.ProjectReportService;
//import cn.bonoon.kernel.events.PanelEvent;
//import cn.bonoon.kernel.web.controllers.AbstractPanelController;
//
//@Controller
//@RequestMapping("s/report/funds-used/ditu")
//public class FundsUsedDituController extends AbstractPanelController{
//
//	@Autowired
//	private ProjectReportService projectReportService;
//	
//	@Override
//	protected void onLoad(PanelEvent event) throws Exception {
//		event.setVmPath("funds-used-ditu");	
//	}
//
//	private class $FundsInfo{
//		Map<Integer, Double> data = new HashMap<>();
//		String name;//片区名
//		void add(Object[] ds){
//			if(null == name){
//				name = ds[1].toString();
//			}
//			Integer key = (int)ds[2] * 100 + (int)ds[3];
//			Double d = data.get(key);
//			if(null != d){
//				d += (double)ds[4];
//			}else{
//				d = (double)ds[4];
//			}
//			data.put(key, d);
//		}
//		//生成统计表的数据
//		Double statistics(StringBuilder cd, int y, int m, int offset, Double sum){
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
//			d += sum;
//			cd.append(d);
//			return d;
//		}
//	}
//	
//	@RequestMapping(value = "funds.statistics", method =  RequestMethod.POST)
//	public String statistics(Model model, Long id){
//		model.addAttribute("layout", "layout-empty.vm");
////		batch = StringHelper.get(batch, "一");
//		
//		int[] am = projectReportService.lastMonthly("一");
//		if(null != am){
//			int lastyear = am[0], lastmonth = am[1];
//			Calendar cal = Calendar.getInstance();
//			cal.set(lastyear, lastmonth, 1);
//			cal.add(Calendar.YEAR, -1);
//			int startyear = cal.get(Calendar.YEAR);
//			int startMonth = cal.get(Calendar.MONTH);
//			List<Object[]> list = projectReportService.getFundsByModelId(id, startyear, startMonth, lastyear, lastmonth);
//
//			Object[] ds =  list.get(0);
//			$FundsInfo fi = new $FundsInfo();
//			fi.add(ds);
//			//取名称
//			StringBuilder countyData = new StringBuilder();
//			countyData.append(",{name:['").append(fi.name).append("'],type:'line',tooltip : {trigger: 'item'}, data: [");
////			boolean up = "1".equals(calType);
//			Double sum = 0D, cur = 0D;
//			for(int i = 0; i < 12; i++){
//				cur = fi.statistics(countyData, startyear, startMonth, i, sum);
////				if(up){
////					sum += cur;
////				}
//			}
//			countyData.append("]}");
//
//			StringBuilder monthBuilder = new StringBuilder();
////			countyNames.append("]");
//			StringBuilder jsContent = new StringBuilder();
//			jsContent.append("var myChart = echarts.init(document.getElementById('hc_line'));");
//			jsContent.append("var option = {title:{text:'投入情况:',subtext:'省专项资金：'},tooltip : {'trigger':'axis'},");
//			jsContent.append("legend: {x:'right',data:'").append(fi.name).append("',},xAxis : [{type : 'category',splitLine : {show : false},");
//			jsContent.append("data : [").append(monthBuilder).append("]}],yAxis : [{type : 'value'}],series:[")
//			.append(countyData.substring(1)).append("]};myChart.setOption(option);");
//			
//			
////			model.addAttribute("names", countyNames.substring(1));
////			model.addAttribute("countydata", countyData);
////			model.addAttribute("keyset", maps.keySet());
//			model.addAttribute("jsContent", jsContent);
////			model.addAttribute("countyTable", countyTable);
//		}else{
//			JOptionPane.showMessageDialog(null,"没有数据");
//		}
//		return "funds-used-ditu";
//	}
//}
