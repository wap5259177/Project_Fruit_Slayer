package cn.bonoon.controllers.newrural;

import java.util.Calendar;
import java.util.List;

import cn.bonoon.core.AdministrationCoreRuralService;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class AdministrationRuralEditorInitializer implements
		DialogFormInitializer<AdministrationCoreRuralEntity> {

	@Override
	public Object init(IService<AdministrationCoreRuralEntity> service, DialogModel model,
			DialogEvent event, Long id, Object form) {
		AdministrationRuralEditor ared;
		if(null == form){
			ared = new AdministrationRuralEditor();
		}else{
			ared = (AdministrationRuralEditor)form;
		}
			
		
		AdministrationCoreRuralService administrationRuralService = (AdministrationCoreRuralService) service;
		
		AdministrationCoreRuralEntity are = administrationRuralService.getAdministrationByAdminRuralId( id);
		
		List<RuralWorkGroupEntity> wgs;
		List<RuralUnitEntity> rus;
		List<RuralExpertGroupEntity> egs;
		
//		if(are !=null){
			ared.reset(are);
			wgs = administrationRuralService.workGroupsByAdminRural(id);
			rus = administrationRuralService.ruralUnitsByAdminRural(id);
			egs = administrationRuralService.experGroupByAdminRural(id);
//		}else{
//			wgs = newRuralService.workGroups(id);
//			rus = newRuralService.ruralUnits(id);
//			egs = newRuralService.expertGroups(id);
//		}
		//List<RuralExpertGroupEntity> egs = newRuralService.expertGroups(id);
		model.addObject("wgs", wgs).addObject("egs", egs).addObject("rus", rus);
		
		//通过Id查出该行政村下的自然村，及个数
		List<String> natureVillages = administrationRuralService.getNaturalVillage(id);
		if(natureVillages!=null&&natureVillages.size()>0){
			StringBuffer sb = new StringBuffer("");
			for(int i=0;i<natureVillages.size();i++){
				if(i==natureVillages.size()-1){
					sb.append(natureVillages.get(i)).append("");
				}else{
					sb.append(natureVillages.get(i)).append(",");
				}
			}
			model.addObject("natureList", sb);
			model.addObject("natralruralNum", natureVillages.size());
			model.addObject("natureVillages", natureVillages);
		}
		
		
		StringBuilder selyear = new StringBuilder();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		selyear.append("<option value=''>请选择</option>");
//		for(int i = 1992; i <= year; i++){
//			selyear.append("<option value='").append(i).append("'>").append(i).append("</option>");
//		}
		for(int i = year; i >= 1990; i--){
			selyear.append("<option value='").append(i).append("'>").append(i).append("</option>");
		}
		model.addObject("selyear", selyear);
//		NewRuralService newRuralService = (NewRuralService) service;
//		if (null != id && id > 0) {
//			NewRuralEditor baseVillageInfo = new NewRuralEditor();
//			NewRuralEntity newRuralEntity = newRuralService.get(id);
//			baseVillageInfo.set(newRuralEntity);
//			model.addObject("baseVillageInfo", baseVillageInfo);
////			model.addObject("tId", newRuralEntity.getTown());
////			model.addObject("vId",newRuralEntity.getName());
//		}
//		model.addObject("town");
//		model.addObject("username", event.getUsername());
//		model.addObject("id", id);
		// NewRuralEditor ne=new NewRuralEditor();
		// 镇
//		List<PlaceEntity> places = newRuralService.towns(event.getOwnerId());
//		StringBuilder selPlace = new StringBuilder();
//		selPlace.append("<select id='town' style='width:120px;' name='town'>");
//		for (PlaceEntity place : places) {
//			if (place.getChildren().isEmpty()) {
//				continue;
//			}
//			selPlace.append("<optgroup label='").append(place.getName()).append("'>");
//			for (PlaceEntity pe : place.getChildren()) {
////				Long pid = pe.getId();
//				selPlace.append("<option value='").append(pe.getName());
//				selPlace.append("'>").append(pe.getName());
//				selPlace.append("</option>");
//			}
//			selPlace.append("</optgroup>");
//		}
//		selPlace.append("</select>");
//		model.addObject("town", selPlace);
		//村委会
//		List<PlaceEntity> villages = newRuralService.village(event.getOwnerId());
//		StringBuilder selVillage = new StringBuilder();
//		selVillage.append("<select id='village' style='width:120px;' name='name'>");
//		for (PlaceEntity place : villages) {
//			if (place.getChildren().isEmpty()) {
//				continue;
//			}
//			selVillage.append("<optgroup label='").append(place.getName())
//					.append("'>");
//			for (PlaceEntity pe : place.getChildren()) {
////				Long pid = pe.getId();
//				selVillage.append("<option value='").append(pe.getName());
//				selVillage.append("'>").append(pe.getName());
//				selVillage.append("</option>");
//			}
//			selVillage.append("</optgroup>");
//		}
//		selVillage.append("</select>");
//		model.addObject("village", selVillage);
		/*
		 * 条件为false 说明检查不通过，已经关闭了填报功能。
		 */
		if(!administrationRuralService.check(id,event)){
			//打开编辑界面的时候提示下填报已经关闭
			model.addObject("warningMessage", "警告！新农村示范片区基础台账的填报已经关闭！！");
		}
		
		return form;
	}
}
