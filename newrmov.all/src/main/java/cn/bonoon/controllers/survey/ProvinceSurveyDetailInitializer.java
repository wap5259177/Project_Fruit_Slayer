package cn.bonoon.controllers.survey;

import java.text.SimpleDateFormat;

import cn.bonoon.entities.SurveySummaryProvinceEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class ProvinceSurveyDetailInitializer implements DialogFormInitializer<SurveySummaryProvinceEntity>{

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@Override
	public Object init(IService<SurveySummaryProvinceEntity> service,
			DialogModel model, DialogEvent event, Long id, Object form) {
		ProvinceSurveyDetail psd = (ProvinceSurveyDetail)form;
		model.addObject("title", "广东省新农村建设摸底调查汇总表");
		model.addObject("deadline", sdf.format(psd.getDeadline()));
		model.addObject("urgable", psd.getStatus() == 0);
		return form;
	}

}
