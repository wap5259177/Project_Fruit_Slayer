package cn.bonoon.controllers.file.require;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.web.controllers.AbstractController;
import cn.bonoon.kernel.web.models.JsonResult;

/**
 * 结束补报
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("/s/require/report_supplement")
public class RequireReportEndController extends AbstractController {

	@Autowired
	private RequireReportItemService requireReportItemService;

	@ResponseBody
	@RequestMapping(value = "!{mid}/end_supp", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult end_supp(@PathVariable("mid") String mid, HttpServletRequest request, Long id, String gridid) {
		try {
			RequireReportItemEntity item = requireReportItemService.get(id);
			item.setStatus(0);
			requireReportItemService.update(item);
			return JsonResult.result();
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.result(e);
		}
	}
}
