package cn.bonoon.controllers.village.finish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.village.AbstractVillageApplicantController;
import cn.bonoon.controllers.village.VillageApplicantDetail;
import cn.bonoon.core.VillageApplicantService;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;

@Controller
@RequestMapping("s/cl/village/finish")
public class ReportingFinishVillageApplicantController extends AbstractVillageApplicantController{

	@Autowired
	public ReportingFinishVillageApplicantController(VillageApplicantService service) {
		super(service);
	}

	@GridStandardDefinition(detailClass = VillageApplicantDetail.class, deleteOperation = false, autoOperation = false)
	@QueryExpression("x.status=1")
	@Override
	protected VillageApplicantService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return service;
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;//严格限制在域范围内
	}
}
