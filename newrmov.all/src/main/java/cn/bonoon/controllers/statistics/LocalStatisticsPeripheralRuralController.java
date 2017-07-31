package cn.bonoon.controllers.statistics;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.kernel.support.IOperator;

@Controller
@RequestMapping("s/lls/spr")
public class LocalStatisticsPeripheralRuralController extends StatisticsPeripheralRuralController{

	@Override
	protected Collection<Object[]> getItems(IOperator opt, String batch) {
		return statisticsService.statisticsLocal(opt, batch);
	}

	
}
