package cn.bonoon.controllers.optimize;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.controllers.AbstractModelAreaViewController;
import cn.bonoon.controllers.optimize.province.survey.SurveyReport;
import cn.bonoon.controllers.survey.SurveyNode;
import cn.bonoon.core.SurveySummaryProvinceService;
import cn.bonoon.entities.SurveySummaryCityEntity;
import cn.bonoon.entities.SurveySummaryCountyEntity;
import cn.bonoon.entities.SurveySummaryProvinceEntity;
import cn.bonoon.kernel.events.PanelEvent;


/**
 * 点击"全省新农村建设" 频道  在一个大的空白页面  列出各个年度的摸底调查
 * 				点击某一个年度查看,弹出一个最大化的框,操作一栏去掉只显示查看内容
 * @author Administrator
 *
 */

@Controller
@RequestMapping("s/pl/all/newrural/area")
public class NewRuralConstuctViewController extends AbstractModelAreaViewController{
	@Autowired
	private SurveySummaryProvinceService surveySummaryProvinceService;
	
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
//		PanelModel model = event.getModel();
		event.setVmPath("optimize2/newruralpro/newruralpro");
	}
	
	
	//列出各个上报的年度list  数据表格
	@ResponseBody
	@RequestMapping("!{key}/survey.json")
	public List<SurveyReport> survey(){
		List<SurveyReport> sr = new ArrayList<>();
		List<SurveySummaryProvinceEntity> ssp = surveySummaryProvinceService.find();
		for(SurveySummaryProvinceEntity s:ssp){
			sr.add(new SurveyReport(s));
		}
		return sr;
	}
	
	
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@RequestMapping("!{key}/survey/survey_detial")
	public String detail(Model model,Long id){
		SurveySummaryProvinceEntity form  = surveySummaryProvinceService.get(id);
		model.addAttribute("form", form);
		
		model.addAttribute("title", "广东省新农村建设摸底调查汇总表");
		model.addAttribute("deadline", sdf.format(form.getDeadline()));
		
		
		model.addAttribute("layout", "layout-empty.vm");
		return "optimize2/newruralpro/newrural-view";
	}

	
	@ResponseBody
	@RequestMapping("!{key}/{pid}!data.node")
	public List<SurveyNode> nodes(@PathVariable("pid") Long pid, Long id){
		List<SurveyNode> sns = new ArrayList<>();
		if(null != id && id > 0){
			List<SurveySummaryCountyEntity> sscs = surveySummaryProvinceService.countySurveies(pid, id);
			for(SurveySummaryCountyEntity ssc : sscs){
				sns.add(new SurveyNode(ssc));
			}
		}else{
			//市级的调查表
			List<SurveySummaryCityEntity> sscs = surveySummaryProvinceService.citySurveies(pid);
			for(SurveySummaryCityEntity ssc : sscs){
				//if(ssc.getStatus() != SurveySummaryCityService.exclude){
				sns.add(new SurveyNode(ssc));
				//}
			}
		}
		return sns;
	}
	
	
}


