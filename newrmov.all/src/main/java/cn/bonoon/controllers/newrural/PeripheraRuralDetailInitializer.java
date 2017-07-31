package cn.bonoon.controllers.newrural;

import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class PeripheraRuralDetailInitializer implements DialogFormInitializer<PeripheralRuralEntity>{

	@Override
	public Object init(IService<PeripheralRuralEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		model.addObject("readonly", "readonly='readonly'")
			.addObject("disabled", "disabled='true'");
		return form;
	}

}
