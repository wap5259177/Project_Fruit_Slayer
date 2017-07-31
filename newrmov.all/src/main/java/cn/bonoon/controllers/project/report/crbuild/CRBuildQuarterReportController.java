package cn.bonoon.controllers.project.report.crbuild;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.CRBuildQuarterReportService;
import cn.bonoon.core.QuarterReportStatus;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterItem;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;
/**
 * 季度报表填报(省级新农村示范片主体村建设情况统计表)
 * 	
 * @author xiaowu
 *
 */
@Controller
@RequestMapping("s/lls/sms/report/crbuild")
public class CRBuildQuarterReportController extends AbstractGridController<ModelAreaCRBuildQuarterItem, CRBuildQuarterItem>{

	private CRBuildQuarterReportService crBuildQuarterReportService;
	@Autowired
	public CRBuildQuarterReportController(CRBuildQuarterReportService crBuildQuarterReportService) {
		super(crBuildQuarterReportService);
		this.crBuildQuarterReportService = crBuildQuarterReportService;
	}

	@Override
	@GridStandardDefinition(
		deleteOperation = false,
		autoOperation = false
	)
//	@QueryExpression("x.modelArea.ownerId={USER owner} and x.batch.quarter.startAt<={USER now} ")
//	@QueryExpression("x.modelArea.ownerId={USER owner} and x.batch.quarter.startAt<={USER now} and x.disabled=false")
	//x.modelArea.status  0:未提交  1：提交  -1省级退回（要重新上报备案）
	@QueryExpression("x.modelArea.ownerId={USER owner} and x.batch.quarter.startAt<={USER now} and x.disabled=false and x.modelArea.status<>-1")
	protected CRBuildQuarterReportService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("开始上报", "index.start", ButtonEventType.GET, ButtonRefreshType.FINISH).status(QuarterReportStatus.NOTSTART);
		
		register.button("填报", "index.report?v=true", ButtonEventType.DIALOG).status(QuarterReportStatus.EDIT);
		register.button("补报", "index.report?v=true", ButtonEventType.DIALOG).status(QuarterReportStatus.REJECT);
		register.button("提交并完成上报", "index.finish", ButtonEventType.GET, ButtonRefreshType.FINISH).status(QuarterReportStatus.EDIT,QuarterReportStatus.REJECT);
//		register.button("刷新", "index.refresh", ButtonEventType.GET, ButtonRefreshType.FINISH).status(QuarterReportStatus.EDIT,QuarterReportStatus.REJECT);
//		register.button("同步资金", "funds.refresh", ButtonEventType.GET, ButtonRefreshType.FINISH).status(QuarterReportStatus.EDIT,QuarterReportStatus.REJECT);
		
		register.button("查看", "index.report", ButtonEventType.DIALOG).status(QuarterReportStatus.FINISH,QuarterReportStatus.AUDIT,100);
		
//		register.button("打印", "index.print", ButtonEventType.JUMP).status(QuarterReportStatus.FINISH,QuarterReportStatus.EDIT,QuarterReportStatus.REJECT,QuarterReportStatus.AUDIT).ordinal(50);
		return crBuildQuarterReportService;
	}
	
	
	
	/**
	 * 打印
	 * @param request
	 * @param id
	 * @param gridid
	 * @param model
	 * @return
	 */
	@RequestMapping("!{key}/index.print")
	public ModelAndView print(HttpServletRequest request, Long id, String gridid, ModelAndView model) {
		_set(id, null, model);
		model.setViewName("report/quarter-report-county-view");
		return model;
	}
	

	/**
	 * 开始上报功能:
	 * 显示按钮:[填报] [提交并完成上报] [刷新]
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/index.start", method = GET)
	public JsonResult toReport(Long id){
		try{
			crBuildQuarterReportService.toReport(getUser(), id);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	
//	/**
//	 * 刷新功能:
//	 * 		点击刷新:重新从数据库中查询行政村和自然村,并生成相应的季度
//	 * @param id
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "!{key}/index.refresh", method = GET)
//	public JsonResult refreshRural(Long id){
//		try{
//			localQuarterReportService.refreshRural(getUser(), id);
//			return JsonResult.result();
//		}catch(Exception ex){
//			return JsonResult.error(ex);
//		}
//	}
//	
	

	/**
	 * 填报功能:
	 * 		点击填报按钮弹出框让用户填报
	 */
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@RequestMapping("!{key}/index.report")
	public ModelAndView report(HttpServletRequest request, Long id, String gridid, String v) {
		DialogModel model = new DialogModel("c_s_" + id, request);
		model.addForm(id);
		_set(id, v, model);
		return model.execute("report/crbuild/crbuild-quarter-report");
	}

	private void _set(Long id, String v, ModelAndView model) {
		ModelAreaCRBuildQuarterItem item = crBuildQuarterReportService.get(id);
		model.addObject("title", "省级新农村示范片主体村建设情况统计表");
		model.addObject("deadline", sdf.format(item.getBatch().getQuarter().getDeadline()));
		model.addObject("id", id);
		model.addObject("item", item);
		if("true".equals(v)){
			model.addObject("reportable", true);
		}else{
			model.addObject("readonly", "readonly='readonly'");
		}
	}
	
	/**
	 * 暂存
	 * @param request
	 * @param itemId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="!{key}/report.save", method = POST)
	public JsonResult save(HttpServletRequest request,CRBuildQuarterDetail detail){
		try{
			//累加数据,//更新到数据库
			crBuildQuarterReportService.updateItem(detail);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	
	/**
	 * 提交上报功能,提交上去以后待市级审核
	 * @param id
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/index.finish", method = GET)
	public JsonResult submit(Long id){
		try{
			crBuildQuarterReportService.toFinish(id);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	
	
	
	/**
	 * 导出excel功能
	 */
//	@ResponseBody
//	@RequestMapping(value = "!{key}/index.excel", method = GET)
//	public View excel(HttpServletRequest request, HttpServletResponse response, Long id){
//		ModelAreaQuarterItem item = localQuarterReportService.get(id);
//		return new CountyQuarterReportExcelView(item);
////		return new CountyQuarterReportExcelView(item);
//	}
}
