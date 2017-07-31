package cn.bonoon.controllers.file.recyclebin;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.file.FileHelper;
import cn.bonoon.controllers.file.management.FileItem;
import cn.bonoon.core.FileService;
import cn.bonoon.core.FileStatus;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.JsonResult;

/**
 * 回收站
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("s/file/recyclebin")
public class FileRecyclebinController extends AbstractGridController<FileEntity, FileItem> {

	private final FileService fileService;

	@Autowired
	protected FileRecyclebinController(FileService fileService) {
		super(fileService);
		this.fileService = fileService;
	}

	@Override
	@QueryExpression("x.status=0")
	@GridStandardDefinition(deleteOperation = false, autoOperation = false)
	protected FileService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("查看", "file.detail", ButtonEventType.DIALOG).ordinal(21);
		register.button("恢复", "file.resume", ButtonEventType.GET, ButtonRefreshType.FINISH).ordinal(22);
		register.button("彻底删除", "file.delete", ButtonEventType.GET, ButtonRefreshType.FINISH).ordinal(23);
		return fileService;
	}
	
	@RequestMapping(value = "!{key}/file.detail", method = GET)
	public ModelAndView detail(HttpServletRequest request, final Long id) {
		return FileHelper.helper.detail(request, fileService, id);
	}
	
	@Override
	protected Class<FileItem> itemClass() {
		return FileItem.class;
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/file.resume", method = GET)
	public final JsonResult resume(final Long id) {
		try {
			FileEntity entity = fileService.get(id);
			entity.setStatus(FileStatus.STATUS_NORMAL);
			fileService.update(entity);
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/file.delete", method = GET)
	public final JsonResult completely_delete(final Long id) {
		try {
			fileService.completelyDelete(getUser(), new Long[] { id });
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}
}
