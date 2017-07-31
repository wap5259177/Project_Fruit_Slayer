package cn.bonoon.controllers.statistics.crbuild;

import java.text.SimpleDateFormat;

import cn.bonoon.core.StatisticsCRBuildService;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class ProvinceStatisticsCRBuildDetailInitializer implements DialogFormInitializer<ModelAreaCRBuildQuarterEntity> {

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@Override
	public Object init(IService<ModelAreaCRBuildQuarterEntity> service,
			DialogModel model, DialogEvent event, Long id, Object form) {
		ProvinceStatisticsCRBuildDetail psd = (ProvinceStatisticsCRBuildDetail)form;
		model.addObject("title", "省级新农村示范片主体村建设情况统计表");
		model.addObject("deadline", sdf.format(psd.getDeadline()));
		model.addObject("urgable", psd.getStatus() == 0);
		
		StatisticsCRBuildService cqService = (StatisticsCRBuildService)service;
		ModelAreaCRBuildQuarterEntity item = cqService.get(id);
		model.addObject("islock",item.isLock());
		return form;
	}
}
