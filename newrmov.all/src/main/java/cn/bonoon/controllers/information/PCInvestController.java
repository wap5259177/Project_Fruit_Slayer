package cn.bonoon.controllers.information;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

import cn.bonoon.core.BatchInvestService;
import cn.bonoon.core.PCInvestService;
import cn.bonoon.entities.informatioin.BatchCapitalInvestmentInformationEntity;
import cn.bonoon.entities.informatioin.CityCapitalInvestmentInformationEntity;
import cn.bonoon.entities.informatioin.ProvinceCapitalInvestmentInformationEntity;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.controllers.GridButtonStatus;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/pl/information/invest")
public class PCInvestController extends AbstractGridController<ProvinceCapitalInvestmentInformationEntity,ProvinceInformationItem> {

	private PCInvestService pcInvestService;
	@Autowired
	public PCInvestController(PCInvestService pcInvestService) {
		super(pcInvestService);
		this.pcInvestService = pcInvestService;
	}
	
	@Autowired
	private BatchInvestService batchInvestService;
	
	

	@Override
	@GridStandardDefinition(
		detailClass = PCInvestDetail.class,
		updateClass = ProvinceInformationUpdater.class,
		insertClass = ProvinceInformationInserter.class,
		editStatus = {0}
	)
	protected PCInvestService initLayoutGrid(LayoutGridRegister register) throws Exception {
		GridButtonStatus gbs = new GridButtonStatus(0);
//		GridButtonStatus gbs2 = new GridButtonStatus(2);
		register.button("提交", "report.start", ButtonEventType.GET, ButtonRefreshType.FINISH).status(gbs).ordinal(100);
//		register.button("退回", "report.reject", ButtonEventType.GET, ButtonRefreshType.FINISH).status(gbs2).ordinal(100);
//		register.button("完成", "report.finish", ButtonEventType.GET, ButtonRefreshType.FINISH).status(gbs2).ordinal(100);
		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(50);
		
		register.button("刷新资金", "report.refresh", ButtonEventType.GET, ButtonRefreshType.FINISH).status(1).ordinal(49);
		return pcInvestService;
	}
	
	
	
	
	
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid, Model model) {
		ProvinceCapitalInvestmentInformationEntity entity = pcInvestService.get(id);
		model.addAttribute("form",entity);
		model.addAttribute("deadline", sdf.format(entity.getDeadline()));
		List<CityCapitalInvestmentInformationEntity> cReports =  pcInvestService.allCityReportByReport(id);
		cReports = _sortCity(cReports);
		model.addAttribute("cReports", cReports);
		model.addAttribute("title","广东省新农村建设资金投入情况统计表 ");
		model.addAttribute("readonly", "readonly='readonly'").addAttribute("disabled", "disabled='true'");
		return "information/pv-invest-view-view";
	}
	

	
	
	@ResponseBody
	@RequestMapping("!{key}/{pid}!data.node")
	public List<PCInvestNode> nodes(@PathVariable("pid") Long pid, Long id){//pid :reportId  id:cReportId
		List<PCInvestNode> ss = new ArrayList<>();
		if(null != id && id < 0){//第一次展开某个市的时候会进来这个判断里面
			id = -id;
			//之后变成填市下面的县
			List<BatchCapitalInvestmentInformationEntity> batchs = batchInvestService.allBatchInvestByCReportId(id);
			for(BatchCapitalInvestmentInformationEntity b:batchs){
				ss.add(new PCInvestNode(b));//这里设置的id是正数，在vm那里就可以根据id的正负判断是父节点还是子节点
			}
		}else{
			//点查看的时候，第一次进来
			List<CityCapitalInvestmentInformationEntity> cReports =  pcInvestService.allCityReportByReport(pid);
			List<CityCapitalInvestmentInformationEntity> orss = _sortCity(cReports);
			for(CityCapitalInvestmentInformationEntity cr:orss){
				ss.add(new PCInvestNode(cr));//这里设置了外层id为负数
			}
		}
		return ss;
	}




	private List<CityCapitalInvestmentInformationEntity> _sortCity(
			List<CityCapitalInvestmentInformationEntity> cReports) {
		List<CityCapitalInvestmentInformationEntity> orss = new ArrayList<>();
		//这20个市（没有深圳市）规定顺序的顺序：广州市-->珠海市-->汕头市-->....
		String[] cities = {"广州市","珠海市","汕头市","佛山市","韶关市","河源市","梅州市","惠州市","汕尾市","东莞市"
				,"中山市","江门市","阳江市","湛江市","茂名市","肇庆市","清远市","潮州市","揭阳市","云浮市"};
		for(String city:cities ){
			for(CityCapitalInvestmentInformationEntity c:cReports){
				String pName = c.getPlace().getName();
				if(city.equals(pName)){
					orss.add(c);
					break;
				}
			}
		}
		return orss;
	}
	
	
	
	
	
//	@ResponseBody
//	@RequestMapping("!{key}/{pid}!data.node")
//	public List<FVStatisticsNode> nodes(@PathVariable("pid") Long pid, Long id){//pid :reportId  id:cReportId
//		List<FVStatisticsNode> ss = new ArrayList<>();
//		if(null != id && id < 0){
//			id = -id;
//			List<FVModelAreaReportEntity> mas = fvModelAreaReportService.allMasByCounty(id);
//			for(FVModelAreaReportEntity ma:mas){
//				ss.add(new FVStatisticsNode(ma));
//			}
//		}else{
//			List<FVMACountyReportEntity> cReports =  fvCountyReportService.allCountyReportByReport(pid);
//			for(FVMACountyReportEntity cr:cReports){
//				ss.add(new FVStatisticsNode(cr));
//			}
//		}
//		return ss;
//	}
	
	
	
	
	
	
	
	/**
	 * 提交
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/report.start", method = {GET,POST})
	public JsonResult startReport(Long id){
		try{
			pcInvestService.startReport(id,getUser());
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
			pcInvestService.DeletedReport(id,getUser());
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
			pcInvestService.rejectReport(id,getUser());
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
			pcInvestService.passReport(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	/**
	 * 刷新一个市
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("!{key}/{id}!report.refreshMn")
	public JsonResult refreshMn(@PathVariable("id") Long id){
		try{
			pcInvestService.refreshMn(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	
	/**
	 * 刷新资金(double类型数字直接相加出错的)
	 * 整个
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/report.refresh", method = GET)
	public JsonResult refreshReport(Long id){
		try{
			pcInvestService.refresh(id,getUser());
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
