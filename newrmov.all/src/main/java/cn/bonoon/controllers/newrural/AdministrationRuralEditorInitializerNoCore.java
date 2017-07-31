package cn.bonoon.controllers.newrural;

import java.util.Calendar;
import java.util.List;

import cn.bonoon.core.AdministrationUncoreRuralService;
import cn.bonoon.entities.AdministrationUncoreRuralEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class AdministrationRuralEditorInitializerNoCore implements
		DialogFormInitializer<AdministrationUncoreRuralEntity> {

	@Override
	public Object init(IService<AdministrationUncoreRuralEntity> service, DialogModel model,
			DialogEvent event, Long id, Object form) {
//		AdministrationRuralEditorNoCore ared;
//		if(null == form){
//			ared = new AdministrationRuralEditorNoCore();
//		}else{
//			ared = (AdministrationRuralEditorNoCore)form;
//		}
			
		
		AdministrationUncoreRuralService administrationUncoreRuralService = (AdministrationUncoreRuralService) service;
		
//		AdministrationUncoreRuralEntity are = administrationUncoreRuralService.getAdministrationByAdminRuralId( id);
		// 通过Id 查找出改行政村下的自然村，及统计自然村的个数
		List<String> natureVillages = administrationUncoreRuralService.getNaturalVillage(id);
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
		for(int i = year; i >= 1990; i--){
			selyear.append("<option value='").append(i).append("'>").append(i).append("</option>");
		}
		model.addObject("selyear", selyear);
		
		
		if(!administrationUncoreRuralService.check(id,event)){
			//打开编辑界面的时候提示下填报已经关闭
			model.addObject("warningMessage", "警告！新农村示范片区基础台账的填报已经关闭！！");
		}
		return form;
	}
}
