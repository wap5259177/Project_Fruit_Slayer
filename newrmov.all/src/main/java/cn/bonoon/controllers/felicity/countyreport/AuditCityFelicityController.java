package cn.bonoon.controllers.felicity.countyreport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.FelicityCountyReportService;
import cn.bonoon.entities.FelicityCountyReportEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.controllers.BaseButtonResolver;
import cn.bonoon.kernel.web.handlers.DialogUpdateHandler;

@Controller
@RequestMapping("s/ml/fv/audit")
public class AuditCityFelicityController extends AbstractGridController<FelicityCountyReportEntity,FelicityCountyReportItem>{
	
	private FelicityCountyReportService felicityCountyReportService;
	
	@Autowired
	protected AuditCityFelicityController(FelicityCountyReportService felicityCountyReportService) {
		super(felicityCountyReportService);
		this.felicityCountyReportService = felicityCountyReportService;
	}
	
	@Override
	@GridStandardDefinition(autoOperation = false, deleteOperation = false, detailClass =FelicityCountyReportDetail.class)
	@QueryExpression("x.status>=0 and x.county.cityId=(select y.place.id from UnitEntity y where y.id={USER owner})")
	protected FelicityCountyReportService initLayoutGrid(LayoutGridRegister register) throws Exception {
		FelicityCountyReportAuditHandler fcrhandler = new FelicityCountyReportAuditHandler(0, felicityCountyReportService, autoManager, FelicityCountyReportAuditor.class);
		DialogUpdateHandler<FelicityCountyReportEntity> dh = new DialogUpdateHandler<>(register, fcrhandler);
		BaseButtonResolver br = dh.register(register.button().status(2));
		br.setName("审核");
		return felicityCountyReportService;
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.GLOBAL;
	}
}
