package cn.bonoon.controllers.felicity;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.FelicityCountyReportService;
import cn.bonoon.entities.FelicityCountyEntity;
import cn.bonoon.kernel.security.LogonUser;

@Controller
@RequestMapping("s/pl/fv/statistics")
public class StatisticsProvinceFelicityController extends StatisticsFelicityController{

	@Override
	protected List<FelicityCountyEntity> counties(FelicityCountyReportService fcrService, LogonUser user) {
		return fcrService.byProvince(user);
	}

	@Override
	protected void parseTotalItem(StringBuilder html, SFTInfo total) {
		total.html("全省幸福村居示范片合计", html);
	}
	protected int parseTotalItem(int i, SFTInfo total,HSSFSheet sheet){ 
		total.exce(i++,"全省幸福村居示范片合计", sheet);
		return i;
	}
}
