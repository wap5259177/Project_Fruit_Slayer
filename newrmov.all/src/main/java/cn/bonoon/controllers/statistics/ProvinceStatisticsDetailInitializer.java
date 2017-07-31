package cn.bonoon.controllers.statistics;

import java.text.SimpleDateFormat;

import cn.bonoon.core.StatisticsScheduleService;
import cn.bonoon.entities.ModelAreaQuarterEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class ProvinceStatisticsDetailInitializer implements DialogFormInitializer<ModelAreaQuarterEntity> {

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	@Override
	public Object init(IService<ModelAreaQuarterEntity> service,
			DialogModel model, DialogEvent event, Long id, Object form) {
		ProvinceStatisticsDetail psd = (ProvinceStatisticsDetail)form;
		model.addObject("title", "省级新农村连片示范建设工程进展情况统计表");
		model.addObject("deadline", sdf.format(psd.getDeadline()));
		model.addObject("urgable", psd.getStatus() == 0);
		
		StatisticsScheduleService cqService = (StatisticsScheduleService)service;
		ModelAreaQuarterEntity item = cqService.get(id);
		model.addObject("islock",item.isLock());
		return form;
	}
}
