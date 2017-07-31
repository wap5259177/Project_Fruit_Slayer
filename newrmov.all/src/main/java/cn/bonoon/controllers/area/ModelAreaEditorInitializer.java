package cn.bonoon.controllers.area;

import cn.bonoon.core.ModelAreaService;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class ModelAreaEditorInitializer implements DialogFormInitializer<ModelAreaEntity> {

	
	@Override
	public Object init(IService<ModelAreaEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		ModelAreaService modelAreaService = (ModelAreaService) service;
//		ModelAreaConfig config = modelAreaService.getConfig();
////		model.addObject("username", event.getUsername());
////		model.addObject("id", id);
//		List<RuralWorkGroupEntity> wgs = modelAreaService.workGroups(id);
//		List<RuralExpertGroupEntity> egs = modelAreaService.expertGroups(id);
//		List<RuralUnitEntity> rus = modelAreaService.ruralUnits(id);
//		model.addObject("wgs", wgs).addObject("egs", egs).addObject("rus", rus);
		
		Object[]  adminNatual = modelAreaService.getSum(id);
		Object[]  adminPSum = modelAreaService.getPSum(id);
		
		model.addObject("mainSumHouse",adminNatual[0]);
		model.addObject("mainSumP",adminNatual[1]);

		model.addObject("aroundSumHouse",adminPSum[0]);
		model.addObject("aroundSumP",adminPSum[1]);
		
		if(!modelAreaService.check(id,event)){
			//打开编辑界面的时候提示下填报已经关闭
			model.addObject("warningMessage", "警告！新农村示范片区基础台账的填报已经关闭！！");
		}
		return form;
	}
}
