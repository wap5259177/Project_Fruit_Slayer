package cn.bonoon.controllers.project.report.crbuild;

import java.text.SimpleDateFormat;

import cn.bonoon.core.CityCRBuildQuarterReportService;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterItem;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class ModelAreaCRBuildQuarterReportAuditorInitializer implements DialogFormInitializer<ModelAreaCRBuildQuarterItem>{
//	private CityQuarterReportService cityQuarterReportService;
//	private HttpServletRequest request;
	@Override
	public Object init(IService<ModelAreaCRBuildQuarterItem> service,
			DialogModel model, DialogEvent event, Long id, Object form) {
		model.addObject("readonly", "readonly='readonly'")
			.addObject("disabled", "disabled='true'")
			.addObject("auditing", true);
		model.addObject("c_s_" + id);
		model.addForm(id);
		CityCRBuildQuarterReportService cqService = (CityCRBuildQuarterReportService)service;
		ModelAreaCRBuildQuarterItem item = cqService.get(id);
		if(null!=item){
			
		}
		model.addObject("title", "省级新农村示范片主体村建设情况统计表 ");
		model.addObject("deadline", sdf.format(item.getBatch().getQuarter().getDeadline()));
		model.addObject("id", id);
		model.addObject("item", item);
		
		
		
		model.execute("report/crbuild/quarter-audit");
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
