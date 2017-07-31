package cn.bonoon.controllers.statistics;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.kernel.support.IOperator;

@Controller
@RequestMapping("s/cls/scr")
public class CityStatisticsNewRuralController extends StatisticsNewRuralController{

	@Override
	protected Collection<Object[]> getItems(IOperator opt, String batch) {
		return statisticsService.statisticsCity(opt, batch);
	}

}
