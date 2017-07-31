package cn.bonoon.controllers.file.search;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.file.FileHelper;
import cn.bonoon.core.FileService;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.JsonResult;

/**
 * 文件搜索
 * 
 * @author ocean~
 * 
 */
@Controller
@RequestMapping("s/file/search")
public class FileSearchController extends AbstractPanelController {

	private FileService fileService;
//	private DirectoryService directoryService;

	@Autowired
	public FileSearchController(FileService fileService/*, DirectoryService directoryService*/) {
		this.fileService = fileService;
//		this.directoryService = directoryService;
	}

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		event.setVmPath("user/file/user-file-search");
	}
	
	@ResponseBody
	@RequestMapping(value = "!{mid}/keyword_search", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult keyword_search(@PathVariable("mid") String mid, HttpServletRequest request, String keyword) {
		// TODO 解析文件、查找（权限判断，自己创建的，或别人分享的） 红色显示关键字
		StringBuilder sb = new StringBuilder("");
		StringBuilder sb_page = new StringBuilder("");
		try {
			List<FileEntity> files = fileService.getFile(getUser());
			for (FileEntity file : files) {
				sb.append("<div style='height:120px;width:500px;margin:0 0 17px 0;overflow:hidden;'>");
				sb.append("<div style='color:#1a0dab;height:21px;overflow:hidden;font-size:18px;line-height:1.54;'>");
				sb.append("<a class='show_dialog' href='file.detail?id=").append(file.getId()).append("'>").append(file.getName()).append("</a></div>");
				sb.append("<div style='color:#545454;height:90px;overflow:hidden;font-size:small;line-height:1.54;'>").append(file.getRemark()).append("</div>");
				sb.append("</div>");
			}
			sb_page.append("<a href='#' style='cursor:pointer;font-size:small;'>上一页</a>");
			for (int i = 0; i < 9; i++) {
				sb_page.append("<a href='#' style='margin-left:15px;cursor:pointer;font-size:small;'>").append(i + 1).append("</a>"); // color:#222;
			}
			sb_page.append("<a href='#' style='margin-left:15px;cursor:pointer;font-size:small;'>下一页</a>");
		} catch (Exception e) {
			JsonResult.error(e);
		}
		Object[] obj = {sb, sb_page};
		return JsonResult.result((Object)obj);
	}
	
	@RequestMapping(value = "!{mid}/file.detail", method = POST)
	public ModelAndView detail(HttpServletRequest request, final Long id) {
		return FileHelper.helper.detail(request, fileService, id);
	}
}
