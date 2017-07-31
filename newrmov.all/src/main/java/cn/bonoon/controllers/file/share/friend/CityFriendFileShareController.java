package cn.bonoon.controllers.file.share.friend;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.account.AccountCondition;
import cn.bonoon.controllers.file.FileHelper;
import cn.bonoon.controllers.file.share.FileShareItem;
import cn.bonoon.core.FileService;
import cn.bonoon.core.plugins.AccountService;
import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.FileShareEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.models.Page;
import cn.bonoon.kernel.support.searcher.Finder;
import cn.bonoon.kernel.support.searcher.FinderHandler;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/file/city_friend_share")
public class CityFriendFileShareController extends AbstractPanelController{

	@Autowired
	private FileService fileService;
	
	@Autowired
	private AccountService accountService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		event.setVmPath("file/city-friend-share");
		
	}

	@RequestMapping(value = "!{mid}/share.content", method = { POST, GET })
	public String content(Model model, Long did){
		model.addAttribute("layout", "layout-empty.vm");
		if(null != did && did > 0){
			List<FileEntity> fils = fileService.getShareFiles(did,getUser());
			List<DirectoryEntity> dirs = fileService.getShareDirectories(did);
			model.addAttribute("fils", fils);
			model.addAttribute("dirs", dirs);
			if(fils.isEmpty() && dirs.isEmpty()){
				model.addAttribute("notFiles", true);
			}
		}else{
			LogonUser user = getUser();
			List<FileShareEntity> shas = fileService.getShareFiles(user);
			List<DirectoryEntity> dirs = fileService.getShareDirectories(user);
			model.addAttribute("shas", shas);
			model.addAttribute("dirs", dirs);
			if(shas.isEmpty() && dirs.isEmpty()){
				model.addAttribute("notFiles", true);
			}
		}
		model.addAttribute("fh", FileHelper.helper);
		model.addAttribute("did", did);
		model.addAttribute("user", getUser());
		return "file/city-friend-share-content";
	}
	
	@RequestMapping(value = "!{mid}/file.editor", method = { POST, GET })
	public ModelAndView file_dialog(HttpServletRequest request, Long id, Long directory_id) {
		return FileHelper.helper.edit(request, fileService, id, directory_id);
	}

	@RequestMapping(value = "!{mid}/file.detail", method = GET)
	public ModelAndView detail(HttpServletRequest request, final Long id) {
		return FileHelper.helper.detail(request, fileService, id);
	}

	@ResponseBody
	@RequestMapping(value = "!{mid}/file.delete", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult file_delete(HttpServletRequest request, String ids, Long node_id) {
		fileService.delete(getUser(), StringHelper.toLongArray(ids), node_id);
		return JsonResult.result();
	}
	
	//市级共享
	@RequestMapping(value = "!{key}/file.share", method = {  GET})
	public ModelAndView file_share(HttpServletRequest request,Long id,String gridid) {
		DialogModel model = new DialogModel(request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		return model.execute("file/city-file-share-dlg");
		
	}
	//保存
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
	//列出该市下的县
	@ResponseBody
	@RequestMapping(value = "!{key}/privileges.search", method = { RequestMethod.GET, RequestMethod.POST })
	public Page search(HttpServletRequest request, @ModelAttribute("form") final AccountCondition form, String hasPrivileges, final Long id) {
		try {
			ReadEvent event = new ReadEvent(applicationContext, request, getUser());
			event.setScope(VisibleScope.GLOBAL);

			Long ownerId = event.getOwnerId();
//			//找出市下面所有的县、区
//			List<UnitEntity> county = fileService.getConuty(ownerId );
			Finder fr = accountService.finder(form, event, FileShareItem.class);
			fr.and("x.ownerId in (select u.id from UnitEntity u where u.place.parent.id=?)",ownerId);
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
