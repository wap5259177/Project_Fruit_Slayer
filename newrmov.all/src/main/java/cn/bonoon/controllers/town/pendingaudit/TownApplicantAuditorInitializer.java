package cn.bonoon.controllers.town.pendingaudit;

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

public class TownApplicantAuditorInitializer implements DialogFormInitializer<TownApplicantEntity>{

	@Override
	public Object init(IService<TownApplicantEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		model.addObject("audit-editor", true);
		model.addObject("applicant-view", true);
		TownApplicantService taService = (TownApplicantService)service;
		List<TownEvaluatePointEntity> eps = taService.evaluatePoints(id);
		TownApplicantHelper.view(model, eps);
		List<TownProjectEntity> tps = taService.projects(id);
		ProjectHelper.view(model, tps);
		TownApplicantHelper.view(taService, model, event, form, true);
		
		List<TownPictureEntity> vps = taService.picutres(id);
		PictureHelper.view(model, vps);
		return form;
	}

}
