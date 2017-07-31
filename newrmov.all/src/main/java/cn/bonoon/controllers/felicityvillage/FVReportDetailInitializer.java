package cn.bonoon.controllers.felicityvillage;

import java.text.SimpleDateFormat;

import cn.bonoon.entities.felicityVillage.FVReportEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class FVReportDetailInitializer implements DialogFormInitializer<FVReportEntity>{
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@Override
	public Object init(IService<FVReportEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
//		model.addObject("readonly", "readonly='readonly'")
//			.addObject("disabled", "disabled='true'");
////		
//		FVReportService fvservice = (FVReportService)service;
//		FVReportEntity report = fvservice.get(id);
//		model.addObject("report", report);
		FVReportDetail _form = (FVReportDetail)form;
		String start = sdf.format(_form.getStartTime());
		String deadline = sdf.format(_form.getDeadline());
		model.addObject("title", "幸福村居示范片项目库");
		model.addObject("titleTime", "统计起止时间"+start+"至"+deadline);
		return form;
	}
}
