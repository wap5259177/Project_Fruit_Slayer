package cn.bonoon.controllers.felicity;

import cn.bonoon.entities.FelicityVillageEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class FelicityVillageDetailInitializer implements DialogFormInitializer<FelicityVillageEntity>{
	@Override
	public Object init(IService<FelicityVillageEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		model.addObject("readonly", "readonly='readonly'")
			.addObject("disabled", "disabled='true'");
		return form;
	}
}
