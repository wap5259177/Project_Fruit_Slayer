package cn.bonoon.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bonoon.core.ProjectReportService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;

/**
 * 示范片区建设进度统计
 * 
 * @author jackson
 * 
 */
@Controller
@RequestMapping("s/report/schedule/job")
public class ScheduleJobController extends AbstractPanelController {

	
	@Autowired
	private ProjectReportService projectReportService;

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		event.setVmPath("schedule-job");

	}

//	private class $ScheduleInfo {
//		double data;
//		String name;// 片区名
//
//		void add(Object[] ds) {
//			if (null == name) {
//				name = ds[1].toString();
//			}
//			data = ((double) ds[2] + (double) ds[3] + (double) ds[4]
//					+ (double) ds[5] + (double) ds[6] + (double) ds[7])
//					/ (double) ds[8] * 100;
//		}
//
//		Double statistics(StringBuilder cd, int offset, Double sum) {
//			Double d = data;
//			if (null == d) {
//				d = 0d;
//			}
//			if (offset > 0) {
//				cd.append(',');
//			}
//			cd.append(d + sum);
//			return d;
//		}
//	}

	@RequestMapping(value = "!{mid}/investment.statistics", method = RequestMethod.POST)
	public String statistics(Model model, String batch) {
		model.addAttribute("layout", "layout-empty.vm");
		batch = BatchHelper.get(batch);

		double schedule = 0;
		List<Object[]> list = projectReportService.getInvestment(schedule,
				batch);
		HashMap<String, Double> maps = new HashMap<>();
		if (0 != list.size()) {
			for (Object[] ds : list) {
				String modelName = (String) ds[1];
				// if(modelName.equals("金平区示范片区")){
				// System.err.println("123");
				// }
				Double data = maps.get(modelName);
				if (null == data) {
					double temp = (double) ((double) ds[2] == 0d ? 1d : ds[2]);
					data = (double) ds[3]/ temp * 100;
					if(data>=100){
						data = 100d;
					}
					BigDecimal bigD = new BigDecimal(data);
					double d1  = bigD.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					maps.put(modelName, d1);
				}
			}
			StringBuilder countyNames = new StringBuilder();
			StringBuilder countyData = new StringBuilder();

			for (String name : maps.keySet()) {
				countyNames.append(",'").append(name).append("'");

				countyData.append(",").append(maps.get(name).doubleValue());

			}

			StringBuilder jsContent = new StringBuilder();
			jsContent.append("var myChart = echarts.init(document.getElementById('hc_line'));");
			jsContent.append("var option = {title:{text:'项目工程进度:',subtext:''},tooltip : {trigger : 'axis'},");
			jsContent.append("xAxis : [{max:'100',type : 'value',boundaryGap : [0, 0.01],splitLine : {show : false}}],yAxis : [{type : 'category',data:[").append(countyNames.substring(1)).append("],axisLabel :{show:true,interval:'auto',rotate:-30}}],");
			jsContent.append("series:[{type:'bar',data:[").append(countyData.substring(1)).append("]}]};myChart.setOption(option);");

			model.addAttribute("jsContent", jsContent);
		} else {
			model.addAttribute("aaa", 0);
		}

		return "schedule-job-content";
 
	}

//	@RequestMapping(value = "!{mid}/piechart/dialog", method = {
//			RequestMethod.GET, RequestMethod.POST })
//	public ModelAndView piechart(HttpServletRequest request,
//			@PathVariable("mid") String mid, Long id) {
//		DialogModel model = new DialogModel(mid, request);
//		return model.execute("schedule-job-piechart-dlg");
//	}

}
