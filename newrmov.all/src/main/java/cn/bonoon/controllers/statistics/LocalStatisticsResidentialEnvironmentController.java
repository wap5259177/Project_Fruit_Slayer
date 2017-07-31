package cn.bonoon.controllers.statistics;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.kernel.support.IOperator;

@Controller
@RequestMapping("s/lls/sre")
public class LocalStatisticsResidentialEnvironmentController extends StatisticsResidentialEnvironmentController{

	@Override
	protected List<Object[]> getItems(IOperator opt, String batch) {
		return statisticsService.statisticsLocal(opt, batch);
	}

}
