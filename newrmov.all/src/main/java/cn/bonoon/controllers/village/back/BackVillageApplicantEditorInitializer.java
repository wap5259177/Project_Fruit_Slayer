package cn.bonoon.controllers.village.back;

import java.util.List;

import cn.bonoon.controllers.PictureHelper;
import cn.bonoon.controllers.ProjectHelper;
import cn.bonoon.controllers.village.VillageApplicantHelper;
import cn.bonoon.core.VillageApplicantService;
import cn.bonoon.entities.VillageApplicantEntity;
import cn.bonoon.entities.VillageEvaluatePointEntity;
import cn.bonoon.entities.VillagePictureEntity;
import cn.bonoon.entities.VillageProjectEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class BackVillageApplicantEditorInitializer implements DialogFormInitializer<VillageApplicantEntity>{

	@Override
	public Object init(IService<VillageApplicantEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		VillageApplicantService vaService = (VillageApplicantService)service;
		List<VillageEvaluatePointEntity> eps = vaService.evaluatePoints(id);
		VillageApplicantHelper.edit(model, eps);
		List<VillageProjectEntity> tps = vaService.projects(id);
		ProjectHelper.edit(model, tps);
		VillageApplicantHelper.reject(vaService, model, event, form);
		
		List<VillagePictureEntity> vps = vaService.picutres(id);
		PictureHelper.edit(id, model, vps, "/s/tvmis/picture/village/");
		return form;
	}

}
