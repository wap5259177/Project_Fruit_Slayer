package cn.bonoon.controllers.area.uncommitted;

import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.plugins.PlaceService;
import cn.bonoon.entities.CommissionerEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class ModelAraeInsertInitializer implements DialogFormInitializer<ModelAreaEntity>{

	@Override
	public Object init(IService<ModelAreaEntity> service, DialogModel model,
			DialogEvent event, Long id, Object form) {
		ModelAreaService modelAreaService = (ModelAreaService) service;
		
		
		
		//单位的ID
		Long uid = event.getOwnerId(); 
		ModelAreaEntity ma = modelAreaService.getByOnwer(uid);
		PlaceEntity place;
		if(null != ma && ma.getStatus() == -1){//片区已经创建，但是被退回    status=-1 退回状态
			place = ma.getPlace();
			Long maid = ma.getId();
			ModelAreaEditor mae = new ModelAreaEditor();
			mae.setContactName(ma.getContactName());
			mae.setContactPhone(ma.getContactPhone());
			mae.setContactPhone2(ma.getContactPhone2());
			mae.setContactJob(ma.getContactJob());
			mae.setId(maid);
			mae.setApplicatAt(ma.getApplicatAt());
			mae.setName(ma.getName());
			mae.setRemark(ma.getRemark());
			form = mae;
			model.addObject("cores", modelAreaService.newRurals(maid))
				.addObject("peris", modelAreaService.peripheralRurals(maid));
		}else{
			UnitEntity unit = modelAreaService.getUnit(uid);
			place = unit.getPlace();
			//尝试读取信息专员的信息
			CommissionerEntity comm = modelAreaService.getByUnit(uid);
			if(null != comm){
				ModelAreaEditor mae = new ModelAreaEditor();
				mae.setContactName(comm.getName());
				mae.setContactPhone(comm.getPhone1());
				mae.setContactPhone2(comm.getPhone2());
				mae.setContactJob(comm.getJob());
				form = mae;
			}
		}
		
		//place是县单位，下面是镇，再到村
		//TODO 这里有可能是县级的镇，支持东莞、中山等情况
		StringBuilder coreRurals = new StringBuilder(), 
				peripheralRurals = new StringBuilder();
		
		if(place.getLevel() == PlaceService.LEVEL_COUNTY){
			for(PlaceEntity town : place.getChildren()){
				if(town.isDeleted()) continue;
				String tname = town.getName();
				
				coreRurals.append("<div class='item-close item-open' onclick=\"jQuery(this).toggleClass('item-open').next().slideToggle();\"><b>");
				coreRurals.append(tname);
				coreRurals.append("</b></div><div style='display:none;padding-left:20px;'>");
				
				peripheralRurals.append("<div class='item-close item-open' onclick=\"jQuery(this).toggleClass('item-open').next().slideToggle();\"><b>");
				peripheralRurals.append(tname);
				peripheralRurals.append("</b></div><div style='display:none;padding-left:20px;'>");
				
				for(PlaceEntity vil : town.getChildren()){
					if(vil.isDeleted()) continue;
					Long cId=vil.getId();
					String vname = vil.getName();
					
					coreRurals.append("<div><a href='#' onclick=\"getCore(this, '");
					coreRurals.append(tname).append("', '");
					coreRurals.append(cId).append("', '");
					coreRurals.append(vname).append("')\" id=''>");
					coreRurals.append(vname).append("</a></div>");
					
					peripheralRurals.append("<div><a href='#' onclick=\"getPeripheral(this, '");
					peripheralRurals.append(tname).append("', '");
					peripheralRurals.append(cId).append("', '");
					peripheralRurals.append(vname).append("')\" id=''>");
					peripheralRurals.append(vname).append("</a></div>");
				}
				coreRurals.append("</div>");
				peripheralRurals.append("</div>");
			}
		}else if(place.getLevel() == PlaceService.LEVEL_TOWN){
			//对镇级县的支持
			for(PlaceEntity vil : place.getChildren()){
				if(vil.isDeleted()) continue;
				Long cId=vil.getId();
				String vname = vil.getName();
				
				coreRurals.append("<div><a href='#' onclick=\"getCore(this, '', '");
				coreRurals.append(cId).append("', '");
				coreRurals.append(vname).append("')\" id=''>");
				coreRurals.append(vname).append("</a></div>");
				
				peripheralRurals.append("<div><a href='#' onclick=\"getPeripheral(this, '', '");
				peripheralRurals.append(cId).append("', '");
				peripheralRurals.append(vname).append("')\" id=''>");
				peripheralRurals.append(vname).append("</a></div>");
			}
		}
		model.addObject("peripheralRurals", peripheralRurals)
			 .addObject("coreRurals", coreRurals);
		return form;
	}

}
