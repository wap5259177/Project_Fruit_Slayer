package cn.bonoon.controllers.file.share;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.account.AccountCondition;
import cn.bonoon.controllers.file.FileHelper;
import cn.bonoon.controllers.file.management.FileItem;
import cn.bonoon.core.FileService;
import cn.bonoon.core.UnitService;
import cn.bonoon.core.plugins.AccountService;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.models.Page;
import cn.bonoon.kernel.support.searcher.Finder;
import cn.bonoon.kernel.support.searcher.FinderHandler;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

/**
 * 我的分享
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("s/file/share")
public class FileShareController extends AbstractGridController<FileEntity, FileItem> {

	private final FileService fileService;

	@Autowired
	private AccountService accountService;
	@Autowired
	private UnitService unitService;
	
	
	
	@Autowired
	public FileShareController(FileService fileService) {
		super(fileService);
		this.fileService = fileService;
	}

	@Override
	@QueryExpression("x.deleted=0 and x.status<>0 and x.shared=1 and x.creatorId={USER id}")
	@GridStandardDefinition(deleteOperation = false, autoOperation = false)
	protected FileService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("查看", "file.detail", ButtonEventType.DIALOG).ordinal(21);
		register.button("分享", "file.privileges", ButtonEventType.DIALOG, ButtonRefreshType.ONCLOSE).ordinal(22);
		return fileService;
	}

	@Override
	protected Class<FileItem> itemClass() {
		return FileItem.class;
	}
	
	@RequestMapping(value = "!{key}/file.detail", method = GET)
	public ModelAndView detail(HttpServletRequest request, final Long id) {
		return FileHelper.helper.detail(request, fileService, id);
	}

	@RequestMapping("!{key}/file.privileges")
	public ModelAndView privileges(HttpServletRequest request, Long id, String gridid) {
		DialogModel model = new DialogModel(request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		return model.execute("user/file/user-file-share-dlg");
	}
	@ResponseBody
	@RequestMapping(value = "!{key}/privileges.save", method = POST)
	public final JsonResult share(final Long id, String actions) {
		try {
			fileService.saveActions(getUser(), id, actions);
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}
	@ResponseBody
	@RequestMapping(value = "!{key}/privileges.search", method = { RequestMethod.GET, RequestMethod.POST })
	public Page search(HttpServletRequest request, @ModelAttribute("form") final AccountCondition form, String hasPrivileges, final Long id) {
		try {
			ReadEvent event = new ReadEvent(applicationContext, request, getUser());
			event.setScope(VisibleScope.GLOBAL);
			Finder fr = accountService.finder(form, event, FileShareItem.class);
			LogonUser user = getUser();
			PlaceEntity place = unitService.getPlace(user);
			 //2:市	 3：县 
			if(place!=null){
				int level = place.getLevel();
				if(level==2){
					//列出市下面的所有县
					fr.and("x.ownerId in (select u.id from UnitEntity u where u.place.parent.id=?)",user.getOwnerId());
				}
				if(level==3){
					//所有市
//				fr.and("x.ownerId in(select u.id from UnitEntity u where u.isCity=1)");
					//列出该县所属的市,以及市下面的所有县
					fr.and("x.ownerId=(select u.place.parent.id from UnitEntity u where u.id=?)",user.getOwnerId());
					fr.or("x.ownerId in (select u.id from UnitEntity u where u.place.parent.id=? and u.id<>?)",place.getParent().getId(),user.getOwnerId());
				}
			}
			
			if (StringHelper.isNotEmpty(hasPrivileges)) {
				fr.and((hasPrivileges.equals("1") ? "x.id in" : "x.id not in") + "(select fs.account.id from FileShareEntity fs where actions<>0 and file.id=?)", id);
			}
			fr.setHandler(new FinderHandler() {
				@Override
				public Object doHandler(Object source, Object obj) throws Exception {
					FileShareItem fsi = (FileShareItem) obj;
					Long uid = fsi.getId();
					Long actions = fileService.getActions(id, uid);
					if (null == actions) {
						actions = 0L;
					}
					String name = "fs_" + uid;
					String input = "<input type='hidden' class='fs_actions' name='" + name + "' value='" + actions.toString() + "_" + uid + "' />";
					fsi.setOptDetail("<input type='checkbox' class='fs_detail' name='" + name + ((actions & 1) != 0 ? "' checked='checked' />" : "' />"));
					fsi.setOptDownload("<input type='checkbox' class='fs_download' name='" + name + ((actions & 2) != 0 ? "' checked='checked' />" : "' />"));
					fsi.setOptEditor("<input type='checkbox' class='fs_editor' name='" + name + ((actions & 4) != 0 ? "' checked='checked' />" : "' />"));
					fsi.setOptDelete("<input type='checkbox' class='fs_delete' name='" + name + ((actions & 8) != 0 ? "' checked='checked' />" : "' />") + input);
					return fsi;
				}
			});
			return fr.getPage();
		} catch (Exception e) {
			e.printStackTrace();
			return Page.EMPTY;
		}
	}

}
