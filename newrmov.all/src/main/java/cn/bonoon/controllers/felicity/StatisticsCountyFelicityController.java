package cn.bonoon.controllers.felicity;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.FelicityCountyReportService;
import cn.bonoon.entities.FelicityCountyEntity;
import cn.bonoon.kernel.security.LogonUser;

@Controller
@RequestMapping("s/cl/fv/statistics")
public class StatisticsCountyFelicityController extends StatisticsFelicityController{

	@Override
	protected List<FelicityCountyEntity> counties(FelicityCountyReportService fcrService, LogonUser user) {
		return fcrService.byCounty(user);
	}

}
