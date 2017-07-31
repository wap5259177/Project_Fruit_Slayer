package cn.bonoon.controllers.newrural;

import cn.bonoon.entities.AdministrationUncoreRuralEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class AdministrationRuralDetailInitializerNoCore implements DialogFormInitializer<AdministrationUncoreRuralEntity>{

	@Override
	public Object init(IService<AdministrationUncoreRuralEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {

//		AdministrationRuralDetailNoCore ard;
//		if(null == form){
//			ard = new AdministrationRuralDetailNoCore();
//		}else{
//			ard = (AdministrationRuralDetailNoCore)form;
//		}
		
		//AdministrationUncoreRuralService administrationUncoreRuralService = (AdministrationUncoreRuralService) service;
		//通过id查找出行政村
		//AdministrationUncoreRuralEntity are = administrationUncoreRuralService.getAdministrationByAdminRuralId(id);
		
		// 通过Id 查找出改行政村下的自然村，及统计自然村的个数
//		List<String> natureVillages = administrationUncoreRuralService.getNaturalVillage(id);
//		if(natureVillages!=null&&natureVillages.size()>0){
//			StringBuffer sb = new StringBuffer("");
//			for(int i=0;i<natureVillages.size();i++){
//				if(i==natureVillages.size()-1){
//					sb.append(natureVillages.get(i)).append("");
//				}else{
//					sb.append(natureVillages.get(i)).append(",");
//				}
//			}
//			model.addObject("natureList", sb);
//			model.addObject("natralruralNum", natureVillages.size());
//			model.addObject("natureVillages", natureVillages);
//		}
		
		model.addObject("readonly", "readonly='readonly'")
			.addObject("disabled", "disabled='true'");
		return form;
	}

}
