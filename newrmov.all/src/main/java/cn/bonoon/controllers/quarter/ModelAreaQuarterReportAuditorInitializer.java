package cn.bonoon.controllers.quarter;

import static cn.bonoon.util.DoubleHelper.add;

import java.text.SimpleDateFormat;

import cn.bonoon.core.CityQuarterReportService;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class ModelAreaQuarterReportAuditorInitializer implements DialogFormInitializer<ModelAreaQuarterItem>{
//	private CityQuarterReportService cityQuarterReportService;
//	private HttpServletRequest request;
	@Override
	public Object init(IService<ModelAreaQuarterItem> service,
			DialogModel model, DialogEvent event, Long id, Object form) {
		model.addObject("readonly", "readonly='readonly'")
			.addObject("disabled", "disabled='true'")
			.addObject("auditing", true);
		model.addObject("c_s_" + id);
		model.addForm(id);
		CityQuarterReportService cqService = (CityQuarterReportService)service;
		ModelAreaQuarterItem item = cqService.get(id);
		if(null!=item){
			
		}
		model.addObject("title", "广东省" + item.getCityName()+item.getModelArea().getName() + "连片示范建设工程进展情况统计表 ");
		model.addObject("deadline", sdf.format(item.getBatch().getQuarter().getDeadline()));
		model.addObject("itemId", id);
		model.addObject("item", item);
		
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
		if(null!=item.getStartProject()){
			model.addObject("srart", item.getStartProject());
		}
		else{
			model.addObject("srart", 0);
		}
		if(null!=item.getFinishProject()){
			model.addObject("finish", item.getFinishProject());
		}
		else{
			model.addObject("finish", 0);
		}
		
		model.execute("report/quarter-audit");
		return form;
	}
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//	@RequestMapping("!{key}/index.report")
//	public ModelAndView report(HttpServletRequest request, Long id, String gridid, String v) {
//		DialogModel model = new DialogModel("c_s_" + id, request);
//		model.addForm(id);
//		ModelAreaQuarterItem item = cityQuarterReportService.get(id);
//		if(null!=item){
//			
//		}
//		model.addObject("title", "广东省" + item.getCityName()+item.getModelArea().getName() + "规划进展汇总统计表");
//		model.addObject("deadline", sdf.format(item.getBatch().getQuarter().getDeadline()));
//		model.addObject("itemId", id);
//		model.addObject("item", item);
//		return model.execute("report/quarter-report");
//	}
	
}
