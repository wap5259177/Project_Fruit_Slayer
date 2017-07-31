package cn.bonoon.controllers.felicityvillage;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.FVCountyReportService;
import cn.bonoon.core.FVModelAreaReportService;
import cn.bonoon.core.FVReportService;
import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.entities.felicityVillage.FVReportEntity;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.controllers.GridButtonStatus;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/pl/felicity/report")
public class FVReportController extends AbstractGridController<FVReportEntity,FVReportItem> {

	private FVReportService fvReportService;
	@Autowired
	public FVReportController(FVReportService fvReportService) {
		super(fvReportService);
		this.fvReportService = fvReportService;
	}
	
	@Autowired
	FVCountyReportService fvCountyReportService;
	@Autowired
	FVModelAreaReportService fvModelAreaReportService;
	

	@Override
	@GridStandardDefinition(
		detailClass = FVReportDetail.class,
		updateClass = FVReportUpdater.class,
		insertClass = FVReportInserter.class,
		editStatus = {0}
	)
	protected FVReportService initLayoutGrid(LayoutGridRegister register) throws Exception {
		GridButtonStatus gbs = new GridButtonStatus(0);
//		GridButtonStatus gbs2 = new GridButtonStatus(2);
		register.button("提交", "report.start", ButtonEventType.GET, ButtonRefreshType.FINISH).status(gbs).ordinal(100);
//		register.button("退回", "report.reject", ButtonEventType.GET, ButtonRefreshType.FINISH).status(gbs2).ordinal(100);
//		register.button("完成", "report.finish", ButtonEventType.GET, ButtonRefreshType.FINISH).status(gbs2).ordinal(100);
		return fvReportService;
	}

	
	
	
	
	
	
	@ResponseBody
	@RequestMapping("!{key}/{pid}!data.node")
	public List<FVStatisticsNode> nodes(@PathVariable("pid") Long pid, Long id){//pid :reportId  id:cReportId
		List<FVStatisticsNode> ss = new ArrayList<>();
		if(null != id && id < 0){
			id = -id;
			List<FVModelAreaReportEntity> mas = fvModelAreaReportService.allMasByCounty(id);
			for(FVModelAreaReportEntity ma:mas){
				ss.add(new FVStatisticsNode(ma));
			}
		}else{
			List<FVMACountyReportEntity> cReports =  fvCountyReportService.allCountyReportByReport(pid);
			for(FVMACountyReportEntity cr:cReports){
				ss.add(new FVStatisticsNode(cr));
			}
		}
		return ss;
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
			fvReportService.startReport(id);
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
			fvReportService.rejectReport(id);
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
	@RequestMapping("!{key}/{id}!report.pase")
	public JsonResult pase(@PathVariable("id") Long id){
		try{
			fvReportService.paseReport(id);
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
