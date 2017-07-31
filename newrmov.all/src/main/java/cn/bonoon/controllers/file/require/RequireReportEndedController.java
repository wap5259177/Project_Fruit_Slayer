package cn.bonoon.controllers.file.require;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.RequireReportService;
import cn.bonoon.entities.RequireReportEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.JsonResult;

/**
 * 查看已结束的上报记录（省级用户操作）
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("/s/require/report_ended")
public class RequireReportEndedController extends AbstractGridController<RequireReportEntity, RequireReportEndedItem> {

	private final RequireReportService requireReportService;

	@Autowired
	protected RequireReportEndedController(RequireReportService requireReportService) {
		super(requireReportService);
		this.requireReportService = requireReportService;
	}

	@Override
	@GridStandardDefinition(detailClass = RequireReportDetail.class, deleteOperation = false, autoOperation = false)
	@QueryExpression("x.status=1") // 已结束（MySql）
	protected RequireReportService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("退回", "index.toback", ButtonEventType.GET, ButtonRefreshType.FINISH).ordinal(22);
//		DialogDetailHandler<RequireReportEntity> dh = new DialogDetailHandler<RequireReportEntity>(register, requireReportService,  RequireReportAddSuppEditor.class);
//		BaseButtonResolver br = dh.register(register.button());
//		br.setName("补报");
//		br.ordinal(0);
		return requireReportService;
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.toback", method = GET)
	public JsonResult toback(Long id){
		try{
			requireReportService.toback(id);
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
		return JsonResult.result();
	}

	
}



