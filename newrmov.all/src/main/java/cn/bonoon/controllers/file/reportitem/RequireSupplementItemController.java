package cn.bonoon.controllers.file.reportitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

/**
 * 补报文档（市、县用户操作）
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("/s/document/supplement")
public class RequireSupplementItemController extends AbstractGridController<RequireReportItemEntity, RequireSupplementItemItem> {

	private RequireReportItemService requireReportItemService;

	@Autowired
	protected RequireSupplementItemController(RequireReportItemService requireReportItemService) {
		super(requireReportItemService);
		this.requireReportItemService = requireReportItemService;
	}

	@Override
	@GridStandardDefinition(detailClass = RequireReportItemDetail.class, deleteOperation = false, autoOperation = false)
	@QueryExpression("x.requireReport.deleted=0 and x.status=1 and x.deleted=0 and x.unit.id={USER owner}")
	protected RequireReportItemService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("补报", "supplement.do", ButtonEventType.DIALOG);
		return requireReportItemService;
	}

}
