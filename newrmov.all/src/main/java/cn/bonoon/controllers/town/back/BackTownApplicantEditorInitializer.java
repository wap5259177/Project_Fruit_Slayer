package cn.bonoon.controllers.town.back;

import java.util.List;

import cn.bonoon.controllers.PictureHelper;
import cn.bonoon.controllers.ProjectHelper;
import cn.bonoon.controllers.town.TownApplicantHelper;
import cn.bonoon.core.TownApplicantService;
import cn.bonoon.entities.TownApplicantEntity;
import cn.bonoon.entities.TownEvaluatePointEntity;
import cn.bonoon.entities.TownPictureEntity;
import cn.bonoon.entities.TownProjectEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class BackTownApplicantEditorInitializer implements DialogFormInitializer<TownApplicantEntity>{

	@Override
	public Object init(IService<TownApplicantEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		TownApplicantService taService = (TownApplicantService)service;
		List<TownEvaluatePointEntity> eps = taService.evaluatePoints(id);
		TownApplicantHelper.edit(model, eps);
		List<TownProjectEntity> tps = taService.projects(id);
		ProjectHelper.edit(model, tps);
		TownApplicantHelper.reject(taService, model, event, form);
		
		List<TownPictureEntity> vps = taService.picutres(id);
		PictureHelper.edit(id, model, vps, "/s/tvmis/picture/town/");
		return form;
	}

}
