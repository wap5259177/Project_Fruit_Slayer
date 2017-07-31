package cn.bonoon.controllers.area.pendingaudit;

import java.util.List;

import cn.bonoon.core.ModelAreaService;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ResidentialEnvironmentEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class ModelAreaAuditorInitializer implements DialogFormInitializer<ModelAreaEntity> {

	@Override
	public Object init(IService<ModelAreaEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		ModelAreaService modelAreaService = (ModelAreaService)service;

		//2.核心村
//		List<Object[]> nrs = modelAreaService.getNewRurals(id);
//		model.addObject("nrs", nrs);
//		Map<Object, Object[]> maps = new HashMap<>();
//		for(Object[] it : modelAreaService.getNewRurals(id)){
//			Object vid = it[3];
//			Object[] vs = maps.get(vid);
//			if(null == vs){
//				vs = new Object[3];
//				vs[0] = vid;
//				vs[1] = it[2];
//				vs[2] = new ArrayList<Object>();
//				maps.put(vid, vs);
//			}
//			((List<Object>)vs[2]).add(it);
//		}
		//List<Object[]> nrs = modelAreaService.getNewRurals(id);
		model.addObject("nrs", modelAreaService.getNewRurals(id));
		
		//3.非主体村
		model.addObject("prs", modelAreaService.getPeripheralRurals(id));
		
		//4.产业发展情况
		List<Object[]> ias = modelAreaService.getIndustries(id);
		model.addObject("ias", ias);
		
		//5.工程项目
		List<Object[]> pjs = modelAreaService.getProjects(id);
		model.addObject("pjs", pjs);
		
		//6.综合整治
		List<ResidentialEnvironmentEntity> listRes = modelAreaService.getResidentialEnvironments(id);
		model.addObject("listRes", listRes)
			.addObject("readonly", "readonly='readonly'")
			.addObject("disabled", "disabled='true'");
		return form;
	}
}
