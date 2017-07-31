package cn.bonoon.controllers.information;

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

import cn.bonoon.core.CountyVillageService;
import cn.bonoon.core.PCVillageService;
import cn.bonoon.entities.informatioin.CityNaturalVillageInformationEntity;
import cn.bonoon.entities.informatioin.CountyNaturalVillageInformationEntity;
import cn.bonoon.entities.informatioin.ProvinceNaturalVillageInformationEntity;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.controllers.GridButtonStatus;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/pl/information/village")
public class PCVillageController extends AbstractGridController<ProvinceNaturalVillageInformationEntity,ProvinceInformationItem> {

	private PCVillageService pcVillageService;
	@Autowired
	public PCVillageController(PCVillageService pcVillageService) {
		super(pcVillageService);
		this.pcVillageService = pcVillageService;
	}
	
	@Autowired
	private CountyVillageService countyVillageService;
	

	@Override
	@GridStandardDefinition(
		detailClass = PCVillageDetail.class,
		updateClass = ProvinceInformationUpdater.class,
		insertClass = ProvinceInformationInserter.class,
		editStatus = {0}
	)
	protected PCVillageService initLayoutGrid(LayoutGridRegister register) throws Exception {
		GridButtonStatus gbs = new GridButtonStatus(0);
//		GridButtonStatus gbs2 = new GridButtonStatus(2);
		register.button("提交", "report.start", ButtonEventType.GET, ButtonRefreshType.FINISH).status(gbs).ordinal(100);
//		register.button("退回", "report.reject", ButtonEventType.GET, ButtonRefreshType.FINISH).status(gbs2).ordinal(100);
//		register.button("完成", "report.finish", ButtonEventType.GET, ButtonRefreshType.FINISH).status(gbs2).ordinal(100);
		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(50);
		return pcVillageService;
	}
	
	
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid, Model model) {
		ProvinceNaturalVillageInformationEntity entity = pcVillageService.get(id);
		model.addAttribute("form",entity);
		model.addAttribute("deadline", sdf.format(entity.getDeadline()));
		List<CityNaturalVillageInformationEntity> cReports = pcVillageService.allCityReportByReport(id);
		cReports = _sortCity(cReports);
		model.addAttribute("cReports", cReports);
		model.addAttribute("title","广东省自然村户数情况表 ");
		model.addAttribute("readonly", "readonly='readonly'").addAttribute("disabled", "disabled='true'");
		return "information/pv-village-view-view";
	}

	
	
	@ResponseBody
	@RequestMapping("!{key}/{pid}!data.node")
	public List<PCVillageNode> nodes(@PathVariable("pid") Long pid, Long id){//pid :reportId  id:cReportId
		List<PCVillageNode> ss = new ArrayList<>();
		if(null != id && id < 0){
			id = -id;
			List<CountyNaturalVillageInformationEntity> batchs = countyVillageService.allVillagestByCReportId(id);
			for(CountyNaturalVillageInformationEntity b:batchs){
				ss.add(new PCVillageNode(b));
			}
		}else{
			//无序的list
			List<CityNaturalVillageInformationEntity> cReports =  pcVillageService.allCityReportByReport(pid);
			List<CityNaturalVillageInformationEntity> orss = _sortCity(cReports);
			for(CityNaturalVillageInformationEntity cr:orss){
				ss.add(new PCVillageNode(cr));
			}
		}
		return ss;
	}



	private List<CityNaturalVillageInformationEntity> _sortCity(
			List<CityNaturalVillageInformationEntity> cReports) {
		//有顺序的list
		List<CityNaturalVillageInformationEntity> orss = new ArrayList<>();
		//这20个市（没有深圳市）规定顺序的顺序：广州市-->珠海市-->汕头市-->....
		String[] cities = {"广州市","珠海市","汕头市","佛山市","韶关市","河源市","梅州市","惠州市","汕尾市","东莞市"
				,"中山市","江门市","阳江市","湛江市","茂名市","肇庆市","清远市","潮州市","揭阳市","云浮市"};
		for(String city:cities ){
			for(CityNaturalVillageInformationEntity c:cReports){
				String pName = c.getPlace().getName();
				if(city.equals(pName)){
					orss.add(c);
					break;
				}
			}
		}
		return orss;
	}
	
	/**
	 * 提交
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/report.start", method = GET)
	public JsonResult startReport(Long id){
		try{
			pcVillageService.startReport(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	/**
	 * 退回重新开始填报
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("!{key}/{id}!report.backDeleted")
	public JsonResult backDeleted(@PathVariable("id") Long id){
		try{
			pcVillageService.DeletedReport(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	/**
	 * 退回
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("!{key}/{id}!report.back")
	public JsonResult back(@PathVariable("id") Long id){
		try{
			pcVillageService.rejectReport(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	
	/**
	 * 通过
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("!{key}/{id}!report.pass")
	public JsonResult pass(@PathVariable("id") Long id){
		try{
			pcVillageService.passReport(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
//	
//	/**
//	 * 完成
//	 * @param id
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "!{key}/report.finish", method = GET)
//	public JsonResult finishReport(Long id){
//		try{
//			fvReportService.finishReport(id);
//			return JsonResult.result();
//		}catch(Exception ex){
//			return JsonResult.error(ex);
//		}
//	}
	
	
}
