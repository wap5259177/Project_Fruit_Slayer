package cn.bonoon.controllers.town.finish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.town.AbstractTownApplicantController;
import cn.bonoon.controllers.town.TownApplicantDetail;
import cn.bonoon.core.TownApplicantService;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;

@Controller
@RequestMapping("s/cl/town/finish")
public class ReportingFinishTownApplicantController extends AbstractTownApplicantController{

	@Autowired
	public ReportingFinishTownApplicantController(TownApplicantService service) {
		super(service);
	}

	@GridStandardDefinition(detailClass = TownApplicantDetail.class, deleteOperation = false, autoOperation = false)
	@QueryExpression("x.status=1")
	@Override
	protected TownApplicantService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return service;
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;//严格限制在域范围内
	}
}
