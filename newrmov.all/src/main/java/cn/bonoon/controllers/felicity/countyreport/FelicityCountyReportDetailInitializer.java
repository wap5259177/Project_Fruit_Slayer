package cn.bonoon.controllers.felicity.countyreport;

import cn.bonoon.entities.FelicityCountyReportEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class FelicityCountyReportDetailInitializer implements DialogFormInitializer<FelicityCountyReportEntity>{
	@Override
	public Object init(IService<FelicityCountyReportEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		model.addObject("readonly", "readonly='readonly'")
			.addObject("disabled", "disabled='true'");
		return form;
	}
}
