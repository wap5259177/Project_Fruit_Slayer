package cn.bonoon.controllers.quarter;

import cn.bonoon.core.CityQuarterReportService;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

public class AbstractModelAreaCityQuarterReportController extends AbstractGridController<ModelAreaQuarterItem,ModelAreaQuarterAuditItem>{
	protected final CityQuarterReportService cityQuarterReportService;
	public AbstractModelAreaCityQuarterReportController(CityQuarterReportService cityQuarterReportService) {
		super(cityQuarterReportService);
		this.cityQuarterReportService = cityQuarterReportService;
	}
}
