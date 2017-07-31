package cn.bonoon.controllers.felicity.countyreport;

import cn.bonoon.core.FelicityCountyReportService;
import cn.bonoon.entities.FelicityCountyReportEntity;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

public class AbstractFelicityCountyReportController extends AbstractGridController<FelicityCountyReportEntity,FelicityCountyReportItem>{
	protected final FelicityCountyReportService felicityCountyReportService;
	public AbstractFelicityCountyReportController(FelicityCountyReportService felicityCountyReportService) {
		super(felicityCountyReportService);
		this.felicityCountyReportService = felicityCountyReportService;
	}
}
