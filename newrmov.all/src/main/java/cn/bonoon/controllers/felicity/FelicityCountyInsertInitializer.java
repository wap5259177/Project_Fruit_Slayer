package cn.bonoon.controllers.felicity;

import cn.bonoon.core.FelicityCountyService;
import cn.bonoon.entities.FelicityCountyEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class FelicityCountyInsertInitializer implements DialogFormInitializer<FelicityCountyEntity>{

	@Override
	public Object init(IService<FelicityCountyEntity> service,
			DialogModel model, DialogEvent event, Long id, Object form) {
		FelicityCountyService felicityCountyService = (FelicityCountyService) service;
		//单位的ID
		Long uid = event.getOwnerId(); 
		
		FelicityCountyEntity fce = felicityCountyService.getByOwner(uid);
		PlaceEntity place;
		if(null != fce && fce.getStatus() == -1){
			FelicityCountyInserter fci = new FelicityCountyInserter();
			fci.setId(fce.getId());
			place = fce.getPlace();
			form = fci;
			
			model.addObject("nvs", felicityCountyService.getVillages(fci.getId()));
		}else{
			UnitEntity unit = felicityCountyService.getUnit(uid);
			//place是县单位，下面是镇，再到村
			place = unit.getPlace();
		}
		
		StringBuilder villages = new StringBuilder();
		for(PlaceEntity town : place.getChildren()){
			String tname = town.getName();
			villages.append("<div class='item-close item-open' onclick=\"jQuery(this).toggleClass('item-open').next().slideToggle();\"><b>");
			villages.append(tname);
			villages.append("</b></div><div style='display:none;padding-left:20px;'>");
			for(PlaceEntity vil : town.getChildren()){
				Long cId=vil.getId();
				String vname = vil.getName();
				villages.append("<div><a href='#' onclick=\"getVillage(this, '");
				villages.append(tname).append("', '");
				villages.append(cId).append("', '");
				villages.append(vname).append("')\" id=''>");
				villages.append(vname).append("</a></div>");
			}
			villages.append("</div>");
		}
		model.addObject("villages", villages);

		return form;
	}

}
