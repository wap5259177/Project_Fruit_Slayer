//package cn.bonoon.controllers.file.share;
//
//import static org.springframework.web.bind.annotation.RequestMethod.POST;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import cn.bonoon.controllers.account.AccountCondition;
//import cn.bonoon.core.FileService;
//import cn.bonoon.core.plugins.AccountService;
//import cn.bonoon.kernel.VisibleScope;
//import cn.bonoon.kernel.events.ReadEvent;
//import cn.bonoon.kernel.support.models.Page;
//import cn.bonoon.kernel.support.searcher.Finder;
//import cn.bonoon.kernel.support.searcher.FinderHandler;
//import cn.bonoon.kernel.util.StringHelper;
//import cn.bonoon.kernel.web.controllers.AbstractController;
//import cn.bonoon.kernel.web.models.JsonResult;
//
///**
// * 我的分享
// * 
// * @author ocean~
// */
//@Controller
//@RequestMapping("s/file/share")
//public class FileShareControllerHandlerController extends AbstractController {
//
//	@Autowired
//	private FileService fileService;
//	@Autowired
//	private AccountService accountService;
////
////	@RequestMapping(value = "privileges.do", method = { POST, GET })
////	public ModelAndView get(HttpServletRequest request, Long id, String gridid) {
////		DialogModel model = new DialogModel(request);
////		model.addForm(id);
////		model.addObject("gridid", gridid);
////		model.addObject("id", id);
////		return model.execute("user/file/user-file-share-dlg");
////	}
////
////	@ResponseBody
////	@RequestMapping(value = "user/privileges/search", method = { RequestMethod.GET, RequestMethod.POST })
////	public Page search(HttpServletRequest request, @ModelAttribute("form") final AccountCondition form, String hasPrivileges, final Long id) {
////		try {
////			ReadEvent event = new ReadEvent(applicationContext, request, getUser());
////			event.setScope(VisibleScope.GLOBAL);
////			Finder fr = accountService.finder(form, event, FileShareItem.class);
////			if (StringHelper.isNotEmpty(hasPrivileges)) {
////				fr.and((hasPrivileges.equals("1") ? "x.id in" : "x.id not in") + "(select fs.account.id from FileShareEntity fs where actions<>0 and file.id=?)", id);
////			}
////			fr.setHandler(new FinderHandler() {
////				@Override
////				public Object doHandler(Object source, Object obj) throws Exception {
////					FileShareItem fsi = (FileShareItem) obj;
////					Long uid = fsi.getId();
////					Long actions = fileService.getActions(id, uid);
////					if (null == actions) {
////						actions = 0L;
////					}
////					String name = "fs_" + uid;
////					String input = "<input type='hidden' class='fs_actions' name='" + name + "' value='" + actions.toString() + "_" + uid + "' />";
////					fsi.setOptDetail("<input type='checkbox' class='fs_detail' name='" + name + ((actions & 1) != 0 ? "' checked='checked' />" : "' />"));
////					fsi.setOptDownload("<input type='checkbox' class='fs_download' name='" + name + ((actions & 2) != 0 ? "' checked='checked' />" : "' />"));
////					fsi.setOptEditor("<input type='checkbox' class='fs_editor' name='" + name + ((actions & 4) != 0 ? "' checked='checked' />" : "' />"));
////					fsi.setOptDelete("<input type='checkbox' class='fs_delete' name='" + name + ((actions & 8) != 0 ? "' checked='checked' />" : "' />") + input);
////					return fsi;
////				}
////			});
////			return fr.getPage();
////		} catch (Exception e) {
////			e.printStackTrace();
////			return Page.EMPTY;
////		}
////	}
//
//	@ResponseBody
//	@RequestMapping(value = "privileges!save.b", method = POST)
//	public final JsonResult share(final Long id, String actions) {
//		try {
//			fileService.saveActions(getUser(), id, actions);
//			return JsonResult.result();
//		} catch (Exception ex) {
//			return JsonResult.error(ex);
//		}
//	}
//}
