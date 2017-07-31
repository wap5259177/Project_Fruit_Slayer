package cn.bonoon.controllers.survey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.SurveySummaryCityService;
import cn.bonoon.core.SurveySummaryProvinceService;
import cn.bonoon.entities.SurveySummaryCityEntity;
import cn.bonoon.entities.SurveySummaryCountyEntity;
import cn.bonoon.entities.SurveySummaryProvinceEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractLayoutController;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/pl/ss/summary")
public class ProvinceSurveySummaryController extends AbstractLayoutController{
	
	
	@Autowired
	private SurveySummaryProvinceService surveySummaryProvinceService;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		List<SurveySummaryProvinceEntity> items = surveySummaryProvinceService.get(getUser(), 10);
		String data = "", select = "";
		for(SurveySummaryProvinceEntity ssc : items){
			Long id = ssc.getId();
			select += "<option value='_" + id + "'>" + ssc.getAnnual() + "</option>";
			data += ",_" + id + ":{id:" + id 
					+ ",deadline:'" + sdf.format(ssc.getDeadline()) + "'}";
		}
		PanelModel model = event.getModel();
		model.addObject("select", select);
		if(!data.isEmpty()){
			model.addObject("data", data.substring(1));
		}
		event.setVmPath("survey/summary-province");
	}
	@ResponseBody
	@RequestMapping("!{key}/data.node")
	public List<SurveyNode> nodes(Long pid, Long id){
		if(null != pid && pid > 0){
			List<SurveyNode> sns = new ArrayList<>();
			if(null != id && id > 0){
				List<SurveySummaryCountyEntity> sscs = surveySummaryProvinceService.countySurveies(pid, id);
				for(SurveySummaryCountyEntity ssc : sscs){
					sns.add(new SurveyNode(ssc));
				}
			}else{
				sns.add(new SurveyNode(surveySummaryProvinceService.get(pid)));
				List<SurveySummaryCityEntity> sscs = surveySummaryProvinceService.citySurveies(pid);
				for(SurveySummaryCityEntity ssc : sscs){
					if(ssc.getStatus() != SurveySummaryCityService.exclude){
						sns.add(new SurveyNode(ssc));
					}
				}
			}
			return sns;
		}
		return Collections.emptyList();
	}

}
