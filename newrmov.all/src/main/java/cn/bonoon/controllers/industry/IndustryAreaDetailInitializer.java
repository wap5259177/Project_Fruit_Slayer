package cn.bonoon.controllers.industry;

import cn.bonoon.entities.IndustryAreaEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class IndustryAreaDetailInitializer implements DialogFormInitializer<IndustryAreaEntity>{

	@Override
	public Object init(IService<IndustryAreaEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		model.addObject("readonly", "readonly='readonly'")
			.addObject("disabled", "disabled='true'");
		return form;
	}

}
