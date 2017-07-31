package cn.bonoon.controllers.file.reportitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

/**
 * 上报文档（市、县用户操作）
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("/s/document/report/finish")
public class RequireReportItemFinishController extends AbstractGridController<RequireReportItemEntity, RequireReportItemFinishItem> {

	private RequireReportItemService requireReportItemService;

	@Autowired
	protected RequireReportItemFinishController(RequireReportItemService requireReportItemService) {
		super(requireReportItemService);
		this.requireReportItemService = requireReportItemService;
	}

	@Override
	@GridStandardDefinition(detailClass = RequireReportItemDetail.class, deleteOperation = false, autoOperation = false)
	@QueryExpression("x.requireReport.deleted=0 and x.status in(1,3) and x.deleted=0 and x.unit.id={USER owner}")
	protected RequireReportItemService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return requireReportItemService;
	}

}
