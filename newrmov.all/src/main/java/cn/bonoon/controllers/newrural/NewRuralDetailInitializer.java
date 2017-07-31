package cn.bonoon.controllers.newrural;

import java.util.List;

import cn.bonoon.core.NewRuralService;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class NewRuralDetailInitializer implements DialogFormInitializer<NewRuralEntity>{

	@Override
	public Object init(IService<NewRuralEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {

		NewRuralDetail nrd;
		if(null == form){
			nrd = new NewRuralDetail();
//			return new NewRuralEditor();
		}else{
			nrd = (NewRuralDetail)form;
		}
		
		NewRuralService newRuralService = (NewRuralService) service;
		AdministrationCoreRuralEntity are = newRuralService.getAdministrationRural(id);
		

		List<RuralWorkGroupEntity> wgs;
		List<RuralUnitEntity> rus;
		List<RuralExpertGroupEntity> egs;
		if(are !=null){
			nrd.reset(are);//重设领导,规划进展
			wgs = newRuralService.workGroupsByAdminRural(are.getId());
			rus = newRuralService.ruralUnitsByAdminRural(are.getId());
			egs = newRuralService.experGroupByAdminRural(are.getId());
		}else{
			wgs = newRuralService.workGroups(id);
			rus = newRuralService.ruralUnits(id);
			egs = newRuralService.expertGroups(id);
		}
		model.addObject("wgs", wgs).addObject("egs", egs).addObject("rus", rus);
		
		model.addObject("readonly", "readonly='readonly'")
			.addObject("disabled", "disabled='true'");
		return form;
	}

}
