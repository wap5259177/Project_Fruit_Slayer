package cn.bonoon.controllers.information;

import java.text.SimpleDateFormat;

import cn.bonoon.entities.informatioin.ProvinceCapitalInvestmentInformationEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class PCInvestDetailInitializer implements DialogFormInitializer<ProvinceCapitalInvestmentInformationEntity>{
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@Override
	public Object init(IService<ProvinceCapitalInvestmentInformationEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		model.addObject("readonly", "readonly='readonly'")
			.addObject("disabled", "disabled='true'");
////		
//		FVReportService fvservice = (FVReportService)service;
//		FVReportEntity report = fvservice.get(id);
//		model.addObject("report", report);
		PCInvestDetail _form = (PCInvestDetail)form;
//		String start = sdf.format(_form.getStartTime());
		String deadline = sdf.format(_form.getDeadline());
		model.addObject("deadline", deadline);
		model.addObject("title", "广东省新农村建设资金投入情况统计表");
//		model.addObject("titleTime", "统计起止时间"+start+"至"+deadline);
		return form;
	}
}
