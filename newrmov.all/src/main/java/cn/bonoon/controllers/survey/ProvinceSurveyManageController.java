package cn.bonoon.controllers.survey;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.SurveySummaryProvinceService;
import cn.bonoon.entities.SurveySummaryCityEntity;
import cn.bonoon.entities.SurveySummaryCountyEntity;
import cn.bonoon.entities.SurveySummaryProvinceEntity;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/pl/ss/manage")
public class ProvinceSurveyManageController extends AbstractGridController<SurveySummaryProvinceEntity, ProvinceSurveyItem>{

	
	private SurveySummaryProvinceService surveySummaryProvinceService;
	@Autowired
	public ProvinceSurveyManageController(SurveySummaryProvinceService surveySummaryProvinceService) {
		super(surveySummaryProvinceService);
		this.surveySummaryProvinceService = surveySummaryProvinceService;
	}

	@Override
	@GridStandardDefinition(
		deleteOperation = false,//去除删除按钮
	    //insertClass = ModelAreaEditor.class,//添加,修改按钮 
		detailClass = ProvinceSurveyDetail.class,
		updateClass = ProvinceSurveyEditor.class,
		insertClass = ProvinceSurveyEditor.class,
		editStatus = 0
	)
	
	protected SurveySummaryProvinceService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("完成", "index.finish", ButtonEventType.GET, ButtonRefreshType.FINISH).status(0);
		
		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(50);
		return surveySummaryProvinceService;
	}
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid, Model model) {
		SurveySummaryProvinceEntity entity = surveySummaryProvinceService.get(id);
		model.addAttribute("deadline", sdf.format(entity.getDeadline()));
		List<SurveySummaryCityEntity> sscs = surveySummaryProvinceService.citySurveies(id);
		List<SurveySummaryCityEntity> orss = _sortCity(sscs);
		StringBuffer sb = new StringBuffer();
		for(SurveySummaryCityEntity c:orss){
			sb.append("<tr style='background-color:#7cd7f8;color:#333333;'>");
			sb.append("<td><font style='font-size:11px;'>"+c.getCityName()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getAgriculturalHousehold()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getAgriculturalPopulation()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getVillageCommittee()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getNaturalVillage()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getVillagePlanning()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getUnifiedStyle()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getHardBottom()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getTapWater()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getSpcvgb()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getVillageRenovation()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getCleaningTeam()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getRainSewageDiversion()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getSewageTreatment()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getLivestockConcentratedCaptive()+"</td>");
			sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+c.getVillagerCouncil()+"</td>");
			sb.append("</tr>");
			List<SurveySummaryCountyEntity> cos = surveySummaryProvinceService.countySurveies(null, c.getId());
			for(SurveySummaryCountyEntity co:cos){
				sb.append("<tr> ");
				sb.append("<td>&nbsp;&nbsp;<font style='font-size:11px;'>"+co.getCounty().getName()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getAgriculturalHousehold()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getAgriculturalPopulation()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getVillageCommittee()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getNaturalVillage()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getVillagePlanning()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getUnifiedStyle()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getHardBottom()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getTapWater()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getSpcvgb()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getVillageRenovation()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getCleaningTeam()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getRainSewageDiversion()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getSewageTreatment()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getLivestockConcentratedCaptive()+"</font></td>");
				sb.append("<td style='text-align:center'><font style='font-size:11px;'>"+co.getVillagerCouncil()+"</font></td>");
				sb.append("</tr>");
			}
		}
		model.addAttribute("content", sb);
		model.addAttribute("title","广东省新农村建设摸底调查汇总表  ");
		return "survey/view-view";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.finish", method = GET)
	public JsonResult finish(Long id){
		try{
			surveySummaryProvinceService.finish(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.restat", method = GET)
	public JsonResult restat(Long id){
		try{
			surveySummaryProvinceService.restat(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.back", method = GET)
	public JsonResult back(Long id){
		try{
			surveySummaryProvinceService.back(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.reject", method = GET)
	public JsonResult reject(Long id){
		try{
			surveySummaryProvinceService.reject(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping("!{key}/{id}!report.urge")
	public JsonResult urge(@PathVariable("id") Long id){
		try{
			surveySummaryProvinceService.urge(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
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
			List<SurveySummaryCityEntity> sscs = surveySummaryProvinceService.citySurveies(pid);
			
			List<SurveySummaryCityEntity> orss = _sortCity(sscs);
			for(SurveySummaryCityEntity ssc : orss){
				sns.add(new SurveyNode(ssc));
			}
//			for(SurveySummaryCityEntity ssc : sscs){
//				//if(ssc.getStatus() != SurveySummaryCityService.exclude){
//				sns.add(new SurveyNode(ssc));
//				//}
//			}
		}
		return sns;
	}

	private List<SurveySummaryCityEntity> _sortCity(
			List<SurveySummaryCityEntity> sscs) {
		List<SurveySummaryCityEntity> orss = new ArrayList<>();
		//这20个市（没有深圳市）规定顺序的顺序：广州市-->珠海市-->汕头市-->....
		String[] cities = {"广州市","珠海市","汕头市","佛山市","韶关市","河源市","梅州市","惠州市","汕尾市","东莞市"
				,"中山市","江门市","阳江市","湛江市","茂名市","肇庆市","清远市","潮州市","揭阳市","云浮市"};
		for(String city:cities ){
			for(SurveySummaryCityEntity c:sscs){
				if(city.equals(c.getCityName())){
					orss.add(c);
					break;
				}
			}
		}
		return orss;
	}
	//排除
	@ResponseBody
	@RequestMapping("!{key}/{id}!report.exclude")
	public JsonResult exclude(@PathVariable("id") Long id){
		try{
			surveySummaryProvinceService.exclude(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	//恢复
	@ResponseBody
	@RequestMapping("!{key}/{id}!report.recover")
	public JsonResult recover(@PathVariable("id") Long id){
		try{
			surveySummaryProvinceService.recover(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
}
