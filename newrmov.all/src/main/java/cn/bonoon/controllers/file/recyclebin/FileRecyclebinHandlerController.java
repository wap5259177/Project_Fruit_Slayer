//package cn.bonoon.controllers.file.recyclebin;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import cn.bonoon.core.FileStatus;
//import cn.bonoon.core.FileService;
//import cn.bonoon.entities.FileEntity;
//import cn.bonoon.kernel.web.controllers.AbstractController;
//import cn.bonoon.kernel.web.models.JsonResult;
//
///**
// * 回收站
// * 
// * @author ocean~
// */
//@Controller
//@RequestMapping("s/file/recyclebin")
//public class FileRecyclebinHandlerController extends AbstractController {
//	@Autowired
//	private FileService fileService;
//
//	@ResponseBody
//	@RequestMapping(value = "resume", method = { RequestMethod.GET, RequestMethod.POST })
//	public final JsonResult resume(final Long id) {
//		try {
//			FileEntity entity = fileService.get(id);
//			entity.setStatus(FileStatus.NORMAL);
//			fileService.update(entity);
//			return JsonResult.result();
//		} catch (Exception ex) {
//			return JsonResult.error(ex);
//		}
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "completely_delete", method = { RequestMethod.GET, RequestMethod.POST })
//	public final JsonResult completely_delete(final Long id) {
//		try {
//			fileService.completelyDelete(getUser(), new Long[] { id });
//			return JsonResult.result();
//		} catch (Exception ex) {
//			return JsonResult.error(ex);
//		}
//	}
//}
