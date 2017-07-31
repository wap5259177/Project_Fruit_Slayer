package cn.bonoon.controllers.industry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bonoon.core.IndustryAreaService;
import cn.bonoon.entities.IndustryAreaEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class IndustryAreaEditorInitializer implements DialogFormInitializer<IndustryAreaEntity> {

	@Override
	public Object init(IService<IndustryAreaEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		IndustryAreaService industryAreaService = (IndustryAreaService) service;
		
		IndustryAreaEditor iae;
		if(null == form){
			iae = new IndustryAreaEditor();
		}else{
			iae = (IndustryAreaEditor)form;
		}
		//地区，这里应该过滤掉哪些非非主体村或核心村的
		boolean selpr = "非主体村".equals(iae.getVillageFea()), hasma = false;
		
		List<Object[]> nrs = industryAreaService.rurals(event);
		StringBuilder selPlace = new StringBuilder();//行政村
		Map<Object, String> vmap = new HashMap<Object, String>();
		boolean nr = null != iae.getRural() && iae.getRural() > 0;//自然村
		selPlace.append("<select  onchange=\"if(jQuery(this).val() == -1){jQuery(this).hide().val('');jQuery('#placeId').show();}\" style='width:98%;");
		if(!nr){
			selPlace.append("display:none;");
		}
		selPlace.append("' id='rural' name='rural'><option value=''>-- 请选择自然村 --</option><option value='-1'>切换到行政村...</option>");
		if(!nrs.isEmpty()){
			selPlace.append("<optgroup id='sel-nr-og' label='主体村'");
			if(selpr){
				selPlace.append(" style='display:none;'");
			}
			selPlace.append(">");
			for(Object[] it : nrs){
				vmap.put(it[5], it[4] + " " + it[1]);
				
				selPlace.append("<option value='").append(it[0])
				.append("'>").append(it[4]).append(' ').append(it[1]).append(' ');
				if(null != it[3]){
					selPlace.append(it[3]);
				}
				selPlace.append("</option>");
			}
			selPlace.append("</optgroup>");
			hasma = true;
		}
		//非主体村
		List<Object[]> prs = industryAreaService.rurals2(event);
		if(!prs.isEmpty()){
			selPlace.append("<optgroup id='sel-pr-og' label='非主体村'");
			if(!selpr){
				selPlace.append(" style='display:none;'");
			}
			selPlace.append(">");
			for(Object[] it : prs){
				vmap.put(it[5], it[4] + " " + it[1]);
				
				selPlace.append("<option value='").append(it[0])
				.append("'>").append(it[4]).append(' ').append(it[1]).append(' ');
				if(null != it[3]){
					selPlace.append(it[3]);
				}
				selPlace.append("</option>");
			}
			selPlace.append("</optgroup>");
			hasma = true;
		}
		
		selPlace.append("</select><select id='placeId' style='width:98%;");
		if(nr){
			selPlace.append("display:none;");
		}
		selPlace.append("' onchange=\"if(jQuery(this).val() == -1){jQuery(this).hide().val('');jQuery('#rural').show();}\"");
		selPlace.append(" name='placeId'><option value=''>-- 请选择行政村 --</option><option value='-1'>切换到自然村...</option>");

		selPlace.append("<optgroup id='sel-pr-og' label='行政村'>");
		for(Map.Entry<Object, String> ma : vmap.entrySet()){
			//Object aa = ma.getKey();
			selPlace.append("<option value='").append(ma.getKey()).append("'>").append(ma.getValue()).append("</option>");
		}
		selPlace.append("</optgroup></select>");
		if(!hasma){
			model.addObject("warningMessage", "警告！新农村示范片区未创建或已经提交！！");
		}else if(!industryAreaService.check(event)){//条件为false 表示检查不通过，说明已经关闭了填报功能
			//打开编辑界面的时候提示下填报已经关闭
			model.addObject("warningMessage", "警告！新农村示范片区基础台账的填报已经关闭！！");
		}
		model.addObject("rural", selPlace);
		return iae;
	}
}
