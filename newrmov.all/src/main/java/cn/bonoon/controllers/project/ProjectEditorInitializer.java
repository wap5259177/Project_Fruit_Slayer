package cn.bonoon.controllers.project;

import java.util.List;

import cn.bonoon.core.ProjectService;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class ProjectEditorInitializer implements DialogFormInitializer<ProjectEntity>{

	@Override
	public Object init(IService<ProjectEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		ProjectService projectService = (ProjectService)service;
		//核心村
		List<Object[]> nrs = projectService.newRurals(event);
		StringBuilder selPlace = new StringBuilder();
		boolean hasma = false;
		selPlace.append("<select id='newRural' style='width:98%;' name='newRural'>");
		if(!nrs.isEmpty()){
			selPlace.append("<optgroup label='主体村'>");
			for(Object[] it : nrs){
				selPlace.append("<option value='").append(it[0]);
				selPlace.append("'>").append(it[2]).append(' ').append(it[1]).append(' ');
				if(null != it[3]){
					selPlace.append(it[3]);
				}
				selPlace.append("</option>");
//				selPlace.append("<option value='").append(it[0])
//					.append("'>").append(it[1])
//					.append('[').append(it[2]).append("]</option>");
			}
			selPlace.append("</optgroup>");
			hasma = true;
		}
		//非主体村
		List<Object[]> prs = projectService.peripheralRurals(event);
		if(!prs.isEmpty()){
			selPlace.append("<optgroup label='非主体村'>");
			for(Object[] it : prs){
				selPlace.append("<option value='").append(it[0]);
				selPlace.append("'>").append(it[2]).append(' ').append(it[1]).append(' ');
				if(null != it[3]){
					selPlace.append(it[3]);
				}
				selPlace.append("</option>");
//				selPlace.append("<option value='").append(it[0])
//					.append("'>").append(it[1])
//					.append('[').append(it[2]).append("]</option>");
			}
			selPlace.append("</optgroup>");
			hasma = true;
		}

		selPlace.append("</select>");
		model.addObject("newRural", selPlace);
		if(!hasma){
			model.addObject("warningMessage", "警告！新农村示范片区未创建！！");
		}
		
		
//		ProjectEditor _form = (ProjectEditor)form;
//		if(_form!=null){
//			if(_form.getStatus()==4){
//				//添加这句话只是为了只显示村子的名称，不能修改项目所在村
//				model.addObject("noUpdateRural", "noUpdateRural");
//			}
//		}
		return form;
	}
}
