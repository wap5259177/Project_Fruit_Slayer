package cn.bonoon.controllers.file.require;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.file.reportitem.RequireReportItemDetail;
import cn.bonoon.controllers.file.reportitem.RequireReportItemItem;
import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

/**
 * 补报（省级用户操作）
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("/s/require/report_supplement")
public class RequireReportSupplementController extends AbstractGridController<RequireReportItemEntity, RequireReportItemItem> {

	private RequireReportItemService requireReportItemService;

	@Autowired
	protected RequireReportSupplementController(RequireReportItemService requireReportItemService) {
		super(requireReportItemService);
		this.requireReportItemService = requireReportItemService;
	}

	@Override
	@GridStandardDefinition(detailClass = RequireReportItemDetail.class, deleteOperation = false, autoOperation = false)
	@QueryExpression("x.requireReport.deleted=0 and x.deleted=0 and x.status=1")
	protected RequireReportItemService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("结束", "end_supp", ButtonEventType.GET).setRefreshType(ButtonRefreshType.FINISH);
		return requireReportItemService;
	}

}
