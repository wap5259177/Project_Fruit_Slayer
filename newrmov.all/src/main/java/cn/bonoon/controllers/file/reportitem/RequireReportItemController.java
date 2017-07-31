package cn.bonoon.controllers.file.reportitem;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.io.FileManager;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.JsonResult;

/**
 * 上报文档（市、县用户操作）
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("/s/document/report")
public class RequireReportItemController extends AbstractGridController<RequireReportItemEntity, RequireReportItemItem> {

	private RequireReportItemService requireReportItemService;

	@Autowired
	protected RequireReportItemController(RequireReportItemService requireReportItemService) {
		super(requireReportItemService);
		this.requireReportItemService = requireReportItemService;
	}
	
	

	@Override
	@GridStandardDefinition(detailClass = RequireReportItemDetail.class, deleteOperation = false, autoOperation = false) 
	//@QueryExpression("x.requireReport.deleted=0 and x.status=0 and x.deleted=0 and x.unit.id={USER owner} and x.requireReport.statusIssue=1 and x.requireReport.status=0 or x.status=2")
	@QueryExpression("x.requireReport.deleted=0 and x.status in(0,2) and x.deleted=0 and x.unit.id={USER owner} and x.requireReport.statusIssue=1 and x.requireReport.status=0")
	protected RequireReportItemService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("签收", "index.sign", ButtonEventType.GET, ButtonRefreshType.FINISH).status(0).ordinal(40);
		register.button("上报", "upload.do", ButtonEventType.DIALOG).status(2).ordinal(50);
		register.button("提交", "index.submit", ButtonEventType.GET, ButtonRefreshType.FINISH).status(2).ordinal(60);
		return requireReportItemService;
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.sign", method = GET)
	public JsonResult signin(Long id){
		try{
			requireReportItemService.signin(getUser(),id);
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
		return JsonResult.result();
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.submit", method = GET)//key=mid
	public JsonResult issue(Long id){
		try{
			requireReportItemService.submit(id);
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
		return JsonResult.result();
	}

	@Autowired
	private FileManager fileManager;
	
	@RequestMapping(value = "!{key}/index.download", method = GET)
	public void download(
			final HttpServletResponse response, 
			final HttpServletRequest request, 
			final Long id){
		try{
			RequireReportItemEntity it = requireReportItemService.get(id);
			String path = it.getRequireReport().getAnnex();
			String name = it.getRequireReport().getAnnexName();
			//FileEntity file = it.getRequireReport().getAnnexFile();
			
			fileManager.loadFile(request, response, path, name, null);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
