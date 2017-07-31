package cn.bonoon.controllers.file.reportitem.handler;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.web.controllers.AbstractController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

/**
 * 目录文件中的文件上报处理
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("/s/document/report")
public class DirectoryFileReportController extends AbstractController {
	@Autowired
	private RequireReportItemService requireReportItemService;

	// 文件管理->目录文档->上报
	@RequestMapping(value = "/dirfile.do", method = { POST, GET })
	public ModelAndView get(HttpServletRequest request, Long id, String gridid) {
		DialogModel model = new DialogModel(request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		StringBuffer str = new StringBuffer();
		str.append("<select id='report_item'><option value=''>------ 请选择 ------</option>");
		List<RequireReportItemEntity> requireReportItem = requireReportItemService.get(getUser());
		if (null != requireReportItem && requireReportItem.size() != 0) {
			for (RequireReportItemEntity item : requireReportItem) {
				str.append("<option value='").append(item.getId()).append("'>").append(item.getRequireReport().getName()).append("</option>");
			}
		}
		str.append("</select>");
		model.addObject("str", str);
		return model.execute("report/dir_file_report");
	}

	// 文件管理->目录文档->上报->提交
	@ResponseBody
	@RequestMapping(value = "/dirfile!save.b", method = POST)
	public final JsonResult save(Long id, Long item_id) {
		try {
			requireReportItemService.save_from_file(getUser(), item_id, new Long[] { id });
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}
}
