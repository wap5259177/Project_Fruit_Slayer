package cn.bonoon.controllers.project.report;
import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import cn.bonoon.core.LocalQuarterReportService;
import cn.bonoon.core.QuarterReportStatus;
import cn.bonoon.entities.ModelAreaQuarterAdministrationRural;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.ModelAreaQuarterNaturalRural;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;
/**
 * 季度报表填报
 * @author xiaowu
 *
 */
@Controller
@RequestMapping("s/lls/sms/report")
public class LocalQuarterReportController extends AbstractGridController<ModelAreaQuarterItem, LocalQuarterItem>{

	private LocalQuarterReportService localQuarterReportService;
	@Autowired
	public LocalQuarterReportController(LocalQuarterReportService localQuarterReportService) {
		super(localQuarterReportService);
		this.localQuarterReportService = localQuarterReportService;
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
	protected LocalQuarterReportService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("开始上报", "index.start", ButtonEventType.GET, ButtonRefreshType.FINISH).status(QuarterReportStatus.NOTSTART);
		
		register.button("填报", "index.report?v=true", ButtonEventType.DIALOG).status(QuarterReportStatus.EDIT);
		register.button("补报", "index.report?v=true", ButtonEventType.DIALOG).status(QuarterReportStatus.REJECT);
		register.button("提交并完成上报", "index.finish", ButtonEventType.GET, ButtonRefreshType.FINISH).status(QuarterReportStatus.EDIT,QuarterReportStatus.REJECT);
		register.button("刷新", "index.refresh", ButtonEventType.GET, ButtonRefreshType.FINISH).status(QuarterReportStatus.EDIT,QuarterReportStatus.REJECT);
//		register.button("同步资金", "funds.refresh", ButtonEventType.GET, ButtonRefreshType.FINISH).status(QuarterReportStatus.EDIT,QuarterReportStatus.REJECT);
		register.button("同步数据", "funds.refresh", ButtonEventType.GET, ButtonRefreshType.FINISH).status(QuarterReportStatus.EDIT,QuarterReportStatus.REJECT);
		
		register.button("查看", "index.report", ButtonEventType.DIALOG).status(QuarterReportStatus.FINISH,QuarterReportStatus.AUDIT,100);
		
		register.button("打印", "index.print", ButtonEventType.JUMP).status(QuarterReportStatus.FINISH,QuarterReportStatus.EDIT,QuarterReportStatus.REJECT,QuarterReportStatus.AUDIT).ordinal(50);
		return localQuarterReportService;
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
	 * 		点击开始上报按钮,
	 * 			a.系统生成需要需要上报的片区数据项:行政村/自然村(创建行政村季度和自然村季度)
	 * 			b.显示按钮:[填报] [提交并完成上报] [刷新]
	 * 		
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/index.start", method = GET)
	public JsonResult toReport(Long id){
		try{
			localQuarterReportService.toReport(getUser(), id);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	
	/**
	 * 刷新功能:
	 * 		点击刷新:重新从数据库中查询行政村和自然村,并生成相应的季度
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/index.refresh", method = GET)
	public JsonResult refreshRural(Long id){
		try{
			localQuarterReportService.refreshRural(getUser(), id);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	

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
		return model.execute("report/quarter-report");
	}

	private void _set(Long id, String v, ModelAndView model) {
		ModelAreaQuarterItem item = localQuarterReportService.get(id);
		System.out.println(item.getModelArea().getId());
		//localQuarterReportService.synchronousMoney(id);
		model.addObject("title", "广东省" + item.getCityName()+item.getModelArea().getName() + "连片示范建设工程进展情况统计表");
		model.addObject("deadline", sdf.format(item.getBatch().getQuarter().getDeadline()));
		model.addObject("itemId", id);
		model.addObject("item", item);
//		model.addObject("srart", item.getStartProject());
//		model.addObject("finish", item.getFinishProject());
		
		
		if(null!=item.getStartProject()){
			model.addObject("srart", item.getStartProject());
		}else{
			model.addObject("srart", 0);
		}
		if(null!=item.getFinishProject()){
			model.addObject("finish", item.getFinishProject());
		}else{
			model.addObject("finish", 0);
		}
		if("true".equalsIgnoreCase(v)){
			model.addObject("reportable", true);
		}else{
			model.addObject("reportable", false);
		}
		
		double totalfunds = 0.0;
		totalfunds  = add(totalfunds,item.getInvestment().getStateFunds());
		totalfunds  = add(totalfunds,item.getInvestment().getProvinceFunds());
		totalfunds  = add(totalfunds,item.getInvestment().getSpecialFunds());
		totalfunds  = add(totalfunds,item.getInvestment().getSocialFunds());
		totalfunds  = add(totalfunds,item.getInvestment().getCityFunds());
		totalfunds  = add(totalfunds,item.getInvestment().getCountyFunds());
		totalfunds  = add(totalfunds,item.getInvestment().getMassFunds());
		totalfunds  = add(totalfunds,item.getInvestment().getOtherFunds());
		
		model.addObject("totalfunds",totalfunds);
		
		List<ModelAreaQuarterAdministrationRural> qars = item.getAdminRurals();
		model.addObject("qars", qars);
	}
	
	/**
	 * 暂存
	 * @param request
	 * @param itemId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="!{key}/report.save", method = POST)
	public JsonResult save(HttpServletRequest request,/* Long[] qnrids,*/Long itemId){
		try{
			ModelAreaQuarterItem item = localQuarterReportService.get(itemId);
			for(ModelAreaQuarterAdministrationRural qar : item.getAdminRurals()){
				String arNf = "qar_" + qar.getId() + "_fp";
				Integer arValue = StringHelper.toInteger(request.getParameter(arNf));
				if(arValue!=null &&(arValue==2 || arValue==0)){
					qar.setArFinishPlan(arValue);
				}
				for(ModelAreaQuarterNaturalRural qnr : qar.getNaturaRurals()){
					for(int i=1;i<=9;i++){
						String nf = "qnr_" + qnr.getId() + "_"+i;	
						Integer value = StringHelper.toInteger(request.getParameter(nf));
						
//						/**为了不影响下面的代码,这里加多一句如果是空就是没有勾选中的     2016-12-28      **/
//						if(value==null)value=0;
//						/***********************************************/
						
						if(value!=null && (value == 2 || value == 0)){
							switch (i) {
							case 1:
								qnr.getNeedFinish().setNeedFinish1(value);
								break;
							case 2:
								qnr.getNeedFinish().setNeedFinish2(value);
								break;
							case 3:
								qnr.getNeedFinish().setNeedFinish3(value);
								break;
							case 4:
								qnr.getNeedFinish().setNeedFinish4(value);
								break;
							case 5:
								qnr.getNeedFinish().setNeedFinish5(value);
								break;
							case 6:
								qnr.getNeedFinish().setNeedFinish6(value);
								break;
							case 7:
								qnr.getNeedFinish().setNeedFinish7(value);
								break;
							case 8:
								qnr.getNeedFinish().setNeedFinish8(value);
								break;
							case 9:
								qnr.getNeedFinish().setNeedFinish9(value);
								break;
							default:
								break;
							}
						}else{
							
						}//没勾选的不处理
					}
				}
			}
			//累加数据,//更新到数据库
			localQuarterReportService.updateItem(item);
			return JsonResult.result();
		}catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
	}
	
	
	/**
	 * 提交上报功能
	 * @param id
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/index.finish", method = GET)
	public JsonResult submit(Long id){
		try{
			localQuarterReportService.toFinish(id);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	/**
	 * 同步资金功能
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/funds.refresh", method = GET)
	public JsonResult refresh6Funds(Long id){
		try{
			localQuarterReportService.synchronousMoney(id);
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		} 
	}
	
	
	
	/**
	 * 导出excel功能
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/index.excel", method = GET)
	public View excel(HttpServletRequest request, HttpServletResponse response, Long id){
		ModelAreaQuarterItem item = localQuarterReportService.get(id);
		return new CountyQuarterReportExcelView(item);
//		return new CountyQuarterReportExcelView(item);
	} 
	
	
}
