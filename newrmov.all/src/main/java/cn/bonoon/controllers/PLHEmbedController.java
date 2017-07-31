package cn.bonoon.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.ApplicantStatus;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.PeripheraRuralService;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.AdministrationUncoreRuralEntity;
import cn.bonoon.entities.IndustryAreaEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.kernel.web.controllers.AbstractController;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/pl")
public class PLHEmbedController extends AbstractController{

	@Autowired
	private ModelAreaService modelAreaService;
	
	class ProgressInfo implements Comparable<ProgressInfo>{
		private final String batch;
		int UNSTART;
		int DRAFT;
		int FINISH;
		int CLOSED;
		int REJECT;
		int WAIT_AUDIT;
		
		@Override
		public int compareTo(ProgressInfo o) {
			return ord - o.ord;
		}
		int ord;
		ProgressInfo(String batch){
			this.batch = batch;
			ord = BatchHelper.indexOf(batch);
		}
		private int total;
		void set(Object[] da){
			total ++;
			int s = ((Number)da[1]).intValue();
			switch (s) {
			case ApplicantStatus.UNSTART:
				UNSTART++;
				break;
			case ApplicantStatus.DRAFT:
				DRAFT++;
				break;
			case ApplicantStatus.FINISH:
				FINISH++;
				break;
			case ApplicantStatus.CLOSED:
				CLOSED++;
				break;
			case ApplicantStatus.REJECT:
				REJECT++;
				break;
			case ApplicantStatus.WAIT_AUDIT:
				WAIT_AUDIT++;
				break;
			}
		}
		
		@Override
		public String toString() {
			return "第" + batch + 
					"批：总" + total +
					"个，未开始" + UNSTART +
					"个、草稿" + DRAFT +
					"个、等审核" + WAIT_AUDIT +
					"个、审核驳回" + REJECT +
					"个、完成" + FINISH +
					"个。";
		}
	}
	
	//private final static String batchs = "一二三";
	
	@RequestMapping("host.embed")
	public String get(Model model){
		List<Object[]> items = modelAreaService.progress();
		Map<String, ProgressInfo> map = new HashMap<>();
		String not = "";
		int i = 1;
		for(Object[] os : items){
			String b = (String)os[0];
			if(BatchHelper.contains(b)){
				ProgressInfo pi = map.get(b);
				if(null == pi){
					pi = new ProgressInfo(b);
					map.put(b, pi);
				}
				pi.set(os);
			}else{
				not += "<div>" + i++ + ". " + os[2] + "[" + os[3] + "]</div>";
			}
		}
		
		List<ProgressInfo> pis = new ArrayList<>(map.values());
		Collections.sort(pis);
		String html = "";
		for(ProgressInfo pi : pis){
			html += pi.toString() + "<br/>";
		}
		if(!not.isEmpty()){
			html += "未明确批次：<div style='padding-left:30px;'>" + not + "</div>";
		}
		
		//主体村的所有行政村和所有自然村的情况
		html += "<br/>统计 主体村：行政村" 
				+ modelAreaService.countBy(AdministrationCoreRuralEntity.class) + "个、自然村" 
				+ modelAreaService.countBy(NewRuralEntity.class) + "个。<br/>";

		html += "统计 非主体村：行政村" 
				+ modelAreaService.countBy(AdministrationUncoreRuralEntity.class) + "个、自然村" 
				+ modelAreaService.countBy(PeripheralRuralEntity.class) + "个。<br/>";

		html += "统计： 产业发展" 
				+ modelAreaService.countBy(IndustryAreaEntity.class) + "个、项目" 
				+ modelAreaService.countBy(ProjectEntity.class) + "个。<br/>";
		
		model.addAttribute("progress", html);
		return "pl-host";
	}
	@Autowired
	private PeripheraRuralService peripheraRuralService;
	
	@ResponseBody
	@RequestMapping("extract.json")
	public JsonResult extract(){
		try{
			peripheraRuralService.extract();
			return JsonResult.result();
		}catch(Exception ex){
			ex.printStackTrace();
			return JsonResult.error(ex);
		}
	}
}
