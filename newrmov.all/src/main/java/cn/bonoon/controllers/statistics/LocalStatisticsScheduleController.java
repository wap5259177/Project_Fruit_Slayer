package cn.bonoon.controllers.statistics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;


@Controller
@RequestMapping("s/lls/sms")
public class LocalStatisticsScheduleController extends AbstractPanelController{
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		event.setVmPath("statistics/area-schedule");
	}
}
