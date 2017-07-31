package cn.bonoon.controllers.newrural;

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

public class AdministrationRuralDetailInitializer implements DialogFormInitializer<AdministrationCoreRuralEntity>{

	@Override
	public Object init(IService<AdministrationCoreRuralEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {

		AdministrationRuralDetail ard;
		if(null == form){
			ard = new AdministrationRuralDetail();
//			return new NewRuralEditor();
		}else{
			ard = (AdministrationRuralDetail)form;
		}
		
		AdministrationCoreRuralService administrationRuralService = (AdministrationCoreRuralService) service;
		//通过id查找出行政村
		AdministrationCoreRuralEntity are = administrationRuralService.getAdministrationByAdminRuralId(id);

		// 通过Id 查找出改行政村下的自然村，及统计自然村的个数
//		List<String> natureVillages = administrationRuralService.getNaturalVillage(id);
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
		
		
		
		
		
		List<RuralWorkGroupEntity> wgs;
		List<RuralUnitEntity> rus;
		List<RuralExpertGroupEntity> egs;
//		if(are !=null){//如果行政村不为空
			ard.reset(are);
			wgs = administrationRuralService.workGroupsByAdminRural(id);
			rus = administrationRuralService.ruralUnitsByAdminRural(id);
			egs = administrationRuralService.experGroupByAdminRural(id);
//		}else{
//			wgs = newRuralService.workGroups(id);
//			rus = newRuralService.ruralUnits(id);
//			egs = newRuralService.expertGroups(id);
//		}
		model.addObject("wgs", wgs).addObject("egs", egs).addObject("rus", rus);
		
		model.addObject("readonly", "readonly='readonly'")
			.addObject("disabled", "disabled='true'");
		return form;
	}

}
