package cn.bonoon.controllers.statistics;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.kernel.support.IOperator;

@Controller
@RequestMapping("s/cls/sma")
public class CityStatisticsModelAreaController extends StatisticsModelAreaController{

	@Override
	protected List<Object[]> getItems(IOperator opt, String batch) {
		return statisticsService.statisticsCity(opt, batch);
	}
}

