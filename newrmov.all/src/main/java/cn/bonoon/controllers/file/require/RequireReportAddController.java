package cn.bonoon.controllers.file.require;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.controllers.file.reportitem.RequireReportItemItem;
import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.support.models.Page;
import cn.bonoon.kernel.support.searcher.Finder;
import cn.bonoon.kernel.web.controllers.AbstractController;
import cn.bonoon.kernel.web.models.JsonResult;

/**
 * 添加补报
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("/s/require/report_ended")
public class RequireReportAddController extends AbstractController {
	@Autowired
	private RequireReportItemService requireReportItemService;

	// 查找可补报的记录（即已结束的记录）
	@ResponseBody
	@RequestMapping(value = "!{mid}/search", method = { RequestMethod.GET, RequestMethod.POST })
	public Page search(@PathVariable("mid") String mid, Long id, HttpServletRequest request) {
		try {
			ReadEvent event = new ReadEvent(applicationContext, request, getUser());
			event.setScope(VisibleScope.GLOBAL);
			Finder fr = requireReportItemService.finder(event, RequireReportItemItem.class);
			fr.and(" requireReport.deleted=0 and deleted=0 and status=0 and x.requireReport.id=?", id);
			return fr.getPage();
		} catch (Exception e) {
			e.printStackTrace();
			return Page.EMPTY;
		}
	}

	@ResponseBody
	@RequestMapping(value = "!{mid}/save_supp", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult save_supp(@PathVariable("mid") String mid, HttpServletRequest request, Long id) {
		try {
			RequireReportItemEntity item = requireReportItemService.get(id);
			item.setStatus(1);
			requireReportItemService.update(item);
			return JsonResult.result();
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.result(e);
		}
	}

}
