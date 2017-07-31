package cn.bonoon.controllers.file.require;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bonoon.core.RequireReportService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

/**
 * 统计上报情况
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("/s/report/statistics")
public class RequireReportStatisticsController extends AbstractPanelController {
	@Autowired
	private RequireReportService requireReportService;

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		StringBuilder year = new StringBuilder("<select id='year' style='width:80px;'>");
		for (int i = 2008, current = Calendar.getInstance().get(Calendar.YEAR); i <= current; i++) {
			year.append("<option value='").append(i).append("'>").append(i).append("</option>");
		}
		year.append("</select>");
		model.addObject("year", year);
		event.setVmPath("report/statistics");
	}

	
	//@ResponseBody
	@RequestMapping(value = "!{mid}/statistics.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String statistics(Model model, @PathVariable("mid") String mid, Long statistics_type, Long time_type, Integer year, Integer month, Integer season) {
		try {
			String title = null;
			List<Object[]> reports = null;
			if (statistics_type == 0) {
				reports = requireReportService.statistics_year(time_type, year);//通过时间类型,年份查询出要求上报的记录
				title = year + "年";
			} else if (statistics_type == 1) {
				reports = requireReportService.statistics_season(time_type, year, season);//时间类型,年份,季度
				String[] arr_season = new String[] { "一", "二", "三", "四" };
				title = year+"年,"+"第" + arr_season[season - 1] + "季度";
			} else if (statistics_type == 2) {
				reports = requireReportService.statistics_month(time_type, year, month);//时间类型,年份,月份
				title = year + "年" + month + "月";
			}
//			return JsonResult.result(_table(reports, title += "上报统计"));
			String result = _table(reports);
			
			model.addAttribute("title", title);
			model.addAttribute("statisticsResult", result);
		} catch (Exception e) {
			e.printStackTrace();
//			return JsonResult.result(e);
		}
		return "report/statistics_content";
	}

	/*
	 * 动态显示统计内容:市级:单位数,未上报数,文档数,县级:单位数,未上报数,文档数,合计文档数
	 * Object[]arr    arr[0]:名称  arr[1]:级别(2:市级  3:县级)     arr[2]:文档数   
	 * 
	 */
	private String _table(List<Object[]> reports) {
		if(null!=reports && reports.size()>0){
			StringBuffer str = new StringBuffer();
			HashMap<String,ReportItem> map = new HashMap<String,ReportItem>();
			for(Object[] arr :reports){
				
				String name = (String)arr[0];
				ReportItem ri = map.get(name);
				if(null == ri){
					ri = new ReportItem();
					map.put(name, ri);
				}
				int doccount = ((Number)arr[2]).intValue();
				if(arr[1].equals(2)){
					//city
					ri.city_unitCount++;
					if(doccount > 0){
						ri.city_documentCount+=doccount;
					}else{
						ri.city_unReportCont++;
					}
				}else if(arr[1].equals(3)){
					ri.county_unitCount++;
					if(doccount > 0){
						ri.county_documentCount+=doccount;
					}else{
						ri.county_unReportCont++;
					}
				}
//				
//				if(arr[1]==Integer.valueOf(3)){//市级
//					//将名称放到map里面,如果是名称不相同单位数++
//					if(map.containsKey(arr[0])){
//						ReportItem item = map.get(arr[0]);//拿到名称对应的reportItem
//						item.setCity_unitCount(item.getCity_unitCount()+1);
//						
//						/*
//						 * 如果文档数>0说明已经上报,然后求出文档数的和.
//						 * 如果文档数<0,说明没有上报.将未上报数++	
//						 */
//						if(Integer.valueOf(arr[2].toString()) > 0){
//							item.setCity_documentCount(item.getCity_documentCount()+Integer.parseInt(arr[2].toString()));
//						}else{
//							item.setCity_unReportCont(item.getCity_unReportCont()+1);
//						}
//						map.put(arr[0].toString(), item);
//						
//					}else{//如果map中没有这个名称,则创建一个reportItem
//						ReportItem item = new ReportItem();
//						item.setCity_unitCount(1);
//						
//						
//						if(Integer.valueOf(arr[2].toString()) > 0){
//							item.setCity_documentCount(Integer.parseInt(arr[2].toString()));
//						}else{
//							item.setCity_unReportCont(1);
//						}
//						map.put(arr[0].toString(),item );
//					}
//
//				}else if(arr[1]==Integer.valueOf(4)){//县级
//					//求单位数
//					if(map.containsKey(arr[0])){
//						ReportItem item = map.get(arr[0]);
//						item.setCounty_unitCount(item.getCounty_unitCount()+1);
//						if(Integer.valueOf(arr[2].toString()) > 0){
//							item.setCounty_documentCount(item.getCounty_documentCount()+Integer.parseInt(arr[2].toString()));
//						}else{
//							item.setCounty_unReportCont(item.getCounty_unReportCont()+1);
//						}
//						map.put(arr[0].toString(), item);
//					}else{
//						ReportItem item = new ReportItem();
//						item.setCounty_unitCount(1);
//						if(Integer.valueOf(arr[2].toString()) > 0){
//							item.setCounty_documentCount(Integer.parseInt(arr[2].toString()));
//						}else{
//							item.setCounty_unReportCont(1);
//						}
//						map.put(arr[0].toString(), item);
//					}
//				}
				
			}
			
//			Iterator<String> it = map.keySet().iterator();
//			while(it.hasNext()){
//				String temp = it.next();
//				str.append("<tr>" +
//						"<td>"+temp+"</td>" +
//						"<td>"+map.get(temp).getCity_unitCount()+"</td>" +
//						"<td>"+map.get(temp).getCity_unReportCont()+"</td>" +
//						"<td>"+map.get(temp).getCity_documentCount()+"</td>" +
//						"<td>"+map.get(temp).getCounty_unitCount()+"</td>" +
//						"<td>"+map.get(temp).getCounty_unReportCont()+"</td>" +
//						"<td>"+map.get(temp).getCounty_documentCount()+"</td>" +
//						"<td>"+(map.get(temp).getCity_documentCount()+map.get(temp).getCounty_documentCount())+"</td>"+
//						"</tr>");	
//			}
			
			for(Map.Entry<String, ReportItem> ri : map.entrySet()){
				ReportItem it = ri.getValue();
//				str.append("<tr><td>" +
//						""+ri.getKey()+"</td>" +
//						"<td>"+it.getCity_unitCount()+"</td>" +
//						"<td>"+it.getCity_unReportCont()+"</td>" +
//						"<td>"+it.getCity_documentCount()+"</td>" +
//						"<td>"+it.getCounty_unitCount()+"</td>" +
//						"<td>"+it.getCounty_unReportCont()+"</td>" +
//						"<td>"+it.getCounty_documentCount()+"</td>" +
//						"<td>"+(it.getCity_documentCount()+it.getCounty_documentCount())+"</td>"+
//						"</tr>");
				
				str.append("<tr><td>").append(ri.getKey()).append("</td>");
				str.append("<td>").append(it.getCity_unitCount()).append("</td>");
				str.append("<td>").append(it.getCity_unReportCont()).append("</td>");
				str.append("<td>").append(it.getCity_documentCount()).append("</td>");
				str.append("<td>").append(it.getCounty_unitCount()).append("</td>");
				str.append("<td>").append(it.getCounty_unReportCont()).append("</td>");
				str.append("<td>").append(it.getCounty_documentCount()).append("</td>");
				str.append("<td>").append((it.getCity_documentCount()+it.getCounty_documentCount())).append("</td>").append("</tr>");
			}
			
			str.append("</table>");
			return str.toString();
		}else{
			return "</table><h1 style='color:red;' align='center'>没有要统计的数据!</h1>";
		}
		// 统计内容：要求上报的单位数量，已上报的单位数量，已上报的市级单位数量，已上报的县级单位数量，上报文档总数
//		int item_count = 0, finish_count = 0, finish_city = 0, finish_county = 0, doc_count = 0;
//		StringBuilder str = new StringBuilder("<table style='border-collapse:collapse;border:solid 0px #000000;'>");
//		str.append("<tr><td colspan='6' style='border-width:0px;font-size:18px;text-align:center;'>").append(title).append("</td></tr>");
//		str.append("<tr><td style='border:solid 1px #000000;width:200px;'>名称</td><td style='border:solid 1px #000000;width:130px;'>要求上报的单位数量</td>");
//		str.append("<td style='border:solid 1px #000000;width:130px;'>已上报的单位数量</td><td style='border:solid 1px #000000;width:130px;'>已上报的市级单位数量</td>");
//		str.append("<td style='border:solid 1px #000000;width:130px;'>已上报的县级单位数量</td><td style='border:solid 1px #000000;width:130px;'>上报文档总数</td></tr>");
		
//		if (null != reports && reports.size() != 0) {
//			for (RequireReportEntity report : reports) {
//				int _item_count = report.getItemCount(), _finish_count = report.getFinishCount(), _finish_city = 0, _finish_county = 0, _doc_count = 0;
//				str.append("<tr><td style='border:solid 1px #000000;'>").append(report.getName()).append("</td>");
//				str.append("<td style='border:solid 1px #000000;'>").append(_item_count).append("</td>");
//				str.append("<td style='border:solid 1px #000000;'>").append(_finish_count).append("</td>");
//				List<RequireReportItemEntity> items = requireReportService.findItem(report.getId());
//				if (null != items && items.size() != 0) {
//					for (RequireReportItemEntity item : items) {
//						int documentCount = item.getDocumentCount();
//						_doc_count += documentCount;
//						if (documentCount > 0) {
//							if (item.getUnit().isCity() == 1)
//								_finish_city++;
//							else
//								_finish_county++;
//						}
//					}
//				}
//				str.append("<td style='border:solid 1px #000000;'>").append(_finish_city).append("</td>");
//				str.append("<td style='border:solid 1px #000000;'>").append(_finish_county).append("</td>");
//				str.append("<td style='border:solid 1px #000000;'>").append(_doc_count).append("</td>");
//				item_count += _item_count;
//				finish_count += _finish_count;
//				finish_city += _finish_city;
//				finish_county += finish_county;
//				doc_count += _doc_count;
//			}
//		}
//		str.append("<tr><td style='border:solid 1px #000000;'>合计</td><td style='border:solid 1px #000000;'>").append(item_count);
//		str.append("</td><td style='border:solid 1px #000000;'>").append(finish_count).append("</td><td style='border:solid 1px #000000;'>");
//		str.append(finish_city).append("</td><td style='border:solid 1px #000000;'>").append(finish_county);
//		str.append("</td><td style='border:solid 1px #000000;'>").append(doc_count).append("</td></tr>");
//		str.append("</tr>");
		
//		int item_count = 0, finish_count = 0, finish_city = 0, finish_county = 0, doc_count = 0;
//		StringBuffer str = new  StringBuffer();
//		if (null != reports && reports.size() != 0) {
//			for (Object[] report : reports) {
//				int _item_count = report.getItemCount(), _finish_count = report.getFinishCount(), _finish_city = 0, _finish_county = 0, _doc_count = 0;
//				str.append("<tr><td style='border:solid 1px #000000;'>").append(report.getName()).append("</td>");//名称
//				str.append("<td style='border:solid 1px #000000;'>").append(_item_count).append("</td>");//要求上报的单位数
//				str.append("<td style='border:solid 1px #000000;'>").append(_finish_count).append("</td>");//已上报的单位数
//				
//				List<RequireReportItemEntity> items = requireReportService.findItem(report.getId());
//				
//				if (null != items && items.size() != 0) {
//					for (RequireReportItemEntity item : items) {
//						int documentCount = item.getDocumentCount();
//						_doc_count += documentCount;//文档总数
//						if (documentCount > 0) {
//							if (item.getUnit().isCity() == 1)//判断是市级文档还是县级文档
//								//让对应的单位数加1
//								_finish_city++;
//							else
//								_finish_county++;
//						}
//					}
//				}
//				str.append("<td style='border:solid 1px #000000;'>").append(_finish_city).append("</td>");//已上报的市级单位数量
//				str.append("<td style='border:solid 1px #000000;'>").append(_finish_county).append("</td>");//已上报的县级单位数量
//				str.append("<td style='border:solid 1px #000000;'>").append(_doc_count).append("</td>");//文档总数
////				str.append("<td style='border:solid 1px #000000;'>").append("22").append("</td>");
////				str.append("<td style='border:solid 1px #000000;'>").append("22").append("</td>");
//				
//				item_count += _item_count;
//				finish_count += _finish_count;
//				finish_city += _finish_city;
//				finish_county += finish_county;
//				doc_count += _doc_count;
//			}
//		}
//		str.append("<tr><td style='border:solid 1px #000000;'>合计</td><td style='border:solid 1px #000000;'>").append(item_count);
//		str.append("</td><td style='border:solid 1px #000000;'>").append(finish_count).append("</td><td style='border:solid 1px #000000;'>");
//		str.append(finish_city).append("</td><td style='border:solid 1px #000000;'>").append(finish_county);
//		str.append("<td style='border:solid 1px #000000;'>").append("22").append("</td>");
//		str.append("<td style='border:solid 1px #000000;'>").append("22").append("</td>");
//		str.append("</td><td style='border:solid 1px #000000;'>").append(doc_count).append("</td></tr>");
//		str.append("</tr>");
//		return str.toString();
////		return str.append("</table>").toString();
	}

	
	/**
	 * 名称所对应的要统计的各个数据
	 * @author Administrator
	 *
	 */
	class ReportItem {
		private int  city_unitCount = 0;//市级的单位数
		private int city_unReportCont = 0;//市级的未上报数
		private int city_documentCount = 0;//市级的文档数
		private int county_unitCount = 0 ;//县级单位数
		private int county_unReportCont = 0;//县级未上报数
		private int county_documentCount = 0 ;//县级文档数
		
		
		public int getCity_unitCount() {
			return city_unitCount;
		}
		public void setCity_unitCount(int city_unitCount) {
			this.city_unitCount = city_unitCount;
		}
		public int getCity_unReportCont() {
			return city_unReportCont;
		}
		public void setCity_unReportCont(int city_unReportCont) {
			this.city_unReportCont = city_unReportCont;
		}
		public int getCity_documentCount() {
			return city_documentCount;
		}
		public void setCity_documentCount(int city_documentCount) {
			this.city_documentCount = city_documentCount;
		}
		public int getCounty_unitCount() {
			return county_unitCount;
		}
		public void setCounty_unitCount(int county_unitCount) {
			this.county_unitCount = county_unitCount;
		}
		public int getCounty_unReportCont() {
			return county_unReportCont;
		}
		public void setCounty_unReportCont(int county_unReportCont) {
			this.county_unReportCont = county_unReportCont;
		}
		public int getCounty_documentCount() {
			return county_documentCount;
		}
		public void setCounty_documentCount(int county_documentCount) {
			this.county_documentCount = county_documentCount;
		}
	}
	
	
}
