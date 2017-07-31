package cn.bonoon.controllers.file.requirereportapply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.RequireReportApplyService;
import cn.bonoon.entities.RequireReportApplyEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;


@Controller
@RequestMapping("/s/document/apply/supplement/search")
public class RequireReportApplySearchController extends AbstractGridController<RequireReportApplyEntity, RequireReportApplyItem>{

	private RequireReportApplyService requireReportApplyService;

	@Autowired
	protected RequireReportApplySearchController(RequireReportApplyService requireReportApplyService) {
		super(requireReportApplyService);
		this.requireReportApplyService = requireReportApplyService;
	}
	
	@Override
	@GridStandardDefinition(
			detailClass = RequireReportApplyDetail.class,
			deleteOperation = false, 
			autoOperation = false
		)
	@QueryExpression("x.status=1")
	protected RequireReportApplyService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return requireReportApplyService;
	}

	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;
	}
}
