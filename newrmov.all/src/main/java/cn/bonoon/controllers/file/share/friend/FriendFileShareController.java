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
import cn.bonoon.core.UnitService;
import cn.bonoon.core.plugins.AccountService;
import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.FileShareEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
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

/**
 * 好友分享
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("s/file/friend_share")
public class FriendFileShareController extends AbstractPanelController {

	@Autowired
	private FileService fileService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UnitService unitService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
//		LogonUser user = getUser();
//		List<FileShareEntity> fss = fileService.getFriendFileShare(user);
//		List<DirectoryEntity> fds = fileService.getShareDirectory(user);
//		PanelModel model = event.getModel();
//		model.addObject("fils", fss);
//		model.addObject("dirs", fds);
//		model.addObject("fh", FileHelper.helper);
//		StringBuilder sb = new StringBuilder();
//		for (FileShareEntity fs : fss) {
//			FileEntity file = fs.getFile();
//			Long fid = file.getId();
//			long actions = fs.getActions();
//			String href = (actions & 1) != 0 ? "file.detail?id=" + fid : "javascript:void(0)";
//			sb.append("<div class='file_friend_share' style='float:left;margin:10px;'><input type='hidden' class='file_id' value='").append(fid).append("' />");
//			sb.append("<div style='text-align:center;'><a class='show_dialog' href='").append(href);
//			sb.append("'><img width='48px' onerror=\"this.onerror=null;this.src='/res/images/file.png'\" src='/res/images/file.png'/></a></div>");
//			sb.append("<div style='text-align:center;padding-top:7px;'><a class='show_dialog file_name' href='");
//			sb.append(href).append("'>").append(file.getName()).append(".").append(file.getExt()).append("</a></div>");
//			sb.append("<div style='text-align:center;padding-top:5px;color:#ababab;'>");
//			long len = file.getLength();
//			if(len < K){
//				sb.append(len).append(" B</div>");
//			}else if(len < M){
//				sb.append(len / K).append(" KB</div>");
//			}else if(len < G){
//				sb.append(len / M).append(" MB</div>");
//			}else{
//				sb.append(len / G).append(" GB</div>");
//			}
//			sb.append("<div style='display:none;'><div id='toolbar_").append(fid).append("'>");
//			if ((actions & 1) != 0) {
//				sb.append("<a class='show_dialog lbtn' href='file.detail?id=").append(fid).append("'>查看</a>");
//			}
//			if ((actions & 2) != 0) {
//				sb.append("&nbsp;<a class='lbtn' target='_blank' href='/files/download?id=").append(fid).append("'>下载</a>");
//			}
//			if ((actions & 4) != 0) {
//				sb.append("&nbsp;<a class='show_dialog lbtn' href='file.editor?id=").append(fid).append("'>修改</a>");
//			}
//			if ((actions & 8) != 0) {
//				sb.append("&nbsp;<a class='entity_delete lbtn' href='file.delete?ids=").append(fid).append("'>删除</a>");
//			}
//			sb.append("</div></div></div>");
//		}
//		model.addObject("share_files", sb);
		//目录共享
		event.setVmPath("file/friend-share");
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
		return "file/friend-share-content";
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
	
	
	
	
	
	
	/**
	 * 点击分享弹出框,选择分享的对象
	 * @param request
	 * @param id
	 * @param gridid
	 * @return
	 */
	@RequestMapping("!{key}/file.privileges")
	public ModelAndView privileges(HttpServletRequest request, Long id) {
		DialogModel model = new DialogModel(request);
		model.addForm(id);
		model.addObject("id", id);
		return model.execute("file/share-editor");
	}
	
	/**
	 * 提交分享
	 * @param id
	 * @param actions
	 * @return
	 */
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
			if(user.getOwnerId()!=0){//ownerId==0的是省级账号
				PlaceEntity place = unitService.getPlace(user);
				if(place.getLevel()==3){
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
