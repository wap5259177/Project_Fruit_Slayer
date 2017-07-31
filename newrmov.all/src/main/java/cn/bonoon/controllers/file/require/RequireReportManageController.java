package cn.bonoon.controllers.file.require;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.controllers.file.reportitem.RequireReportItemDetail;
import cn.bonoon.controllers.file.reportitem.RequireReportManageItem;
import cn.bonoon.core.RequireDetailStatus;
import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/pl/require/report")
public class RequireReportManageController extends AbstractGridController<RequireReportItemEntity,RequireReportManageItem> {
	
	private RequireReportItemService requireReportItemService;

	@Autowired
	protected RequireReportManageController(RequireReportItemService requireReportItemService) {
		super(requireReportItemService);
		this.requireReportItemService = requireReportItemService;
	}

	@Override
	@GridStandardDefinition(
			deleteOperation=false,//去除删除按钮
			detailClass = RequireReportItemDetail.class
	)
	@QueryExpression("x.requireReport.deleted=0 and x.status in(1,3) and x.deleted=0 ")
	protected RequireReportItemService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("退回", "index.back", ButtonEventType.GET, ButtonRefreshType.FINISH).ordinal(10);
		register.button("查收", "index.signin", ButtonEventType.GET, ButtonRefreshType.FINISH).status(RequireDetailStatus.NOTFING).ordinal(20);
		return requireReportItemService;
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.back", method = GET)
	public JsonResult sendback(Long id) {
		try{
			requireReportItemService.sendback(id,getUser());
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
		return JsonResult.result();
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.signin", method = GET)
	public JsonResult sign(Long id) {
		try{
			requireReportItemService.sign(id,getUser());
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
		return JsonResult.result();
	}
	
}
