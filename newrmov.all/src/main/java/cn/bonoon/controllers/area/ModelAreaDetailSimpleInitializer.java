package cn.bonoon.controllers.area;

import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class ModelAreaDetailSimpleInitializer implements DialogFormInitializer<ModelAreaEntity> {

	
	@Override
	public Object init(IService<ModelAreaEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		model.addObject("readonly", "readonly='readonly'")
		.addObject("disabled", "disabled='true'");
		return form;
	}
}
