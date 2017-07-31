package cn.bonoon.controllers.file.management;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.account.AccountCondition;
import cn.bonoon.controllers.file.DirectoryNode;
import cn.bonoon.controllers.file.FileHelper;
import cn.bonoon.controllers.file.share.FileShareItem;
import cn.bonoon.core.DirectoryService;
import cn.bonoon.core.FileService;
import cn.bonoon.core.FileStatus;
import cn.bonoon.core.FileType;
import cn.bonoon.core.UserGroupService;
import cn.bonoon.core.plugins.AccountService;
import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.entities.FileHistoryEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.support.models.Node;
import cn.bonoon.kernel.support.models.Page;
import cn.bonoon.kernel.support.searcher.Finder;
import cn.bonoon.kernel.support.searcher.FinderHandler;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

/**
 * <pre>
 * 文件的管理,文件类似于文章,应该属于某一个目录,如果没有目录的,则属于默认的目录,即根目录.
 * 文件是通过其它过程上传上来的,一般只有比较重要的文件才会放到这个文件表里来.
 * 这里可以添加,修改和删除文件.属于管理员功能,需要慎用.
 * </pre>
 */
@Controller
@RequestMapping("s/file/managemant")
public class FileController extends AbstractPanelController {

	@Autowired
	private FileService fileService;
	@Autowired
	private DirectoryService directoryService;
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserGroupService userGroupService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		event.getModel().addObject("currentTimeMillis", System.currentTimeMillis());
		event.setVmPath("file/manager");
	}

	@RequestMapping("!{key}/file.privileges")
	public ModelAndView privileges(HttpServletRequest request, Long id, String gridid) {
		DialogModel model = new DialogModel(request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		return model.execute("file/share-editor");
	}
	
	@RequestMapping("!{key}/file.usergroupshare")
	public ModelAndView userGroupShare(HttpServletRequest request, Long id, String gridid) {
		DialogModel model = new DialogModel(request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		return model.execute("file/user-group-share");
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

	// ---------------------------------------------------- Directory ----------------------------------------------------

	@ResponseBody
	@RequestMapping(value = "!{mid}/directory.node", method = { RequestMethod.GET, RequestMethod.POST })
	public List<DirectoryNode> directory(Long dirId) {
		List<DirectoryEntity> dirs = directoryService.personalRoot(getUser());
		DirectoryNode per_node = new DirectoryNode(rootName);
		add_children(per_node, dirs, null);
		return Collections.singletonList(per_node);
	}

	private void add_children(DirectoryNode node, List<DirectoryEntity> children, Long parentId) {
		if (null == children || children.isEmpty()) return;

		List<Node> nodes = new ArrayList<Node>();
		for (DirectoryEntity dir : children) {
			if (dir.isDeleted()) continue;

			//Long dir_id = dir.getId();
//			String nm = dir.getName();
//			if(dir.getShare() == 1){
//				nm += "<font color='blue' style='font-size:12px;'>[共享]</font>";
//			}
			DirectoryNode cn = new DirectoryNode(dir, parentId);
			nodes.add(cn);
			
			add_children(cn, dir.getChildren(), dir.getId());
		}
		node.setChildren(nodes);
	}

	@ResponseBody
	@RequestMapping(value = "!{mid}/directory.rename", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult rename(@PathVariable("mid") String mid, Long node_id, String node_text) {
		directoryService.rename(getUser(), node_id, node_text);
		return JsonResult.result();
	}
	
	private final String rootName = "根目录";

	@RequestMapping(value = "!{mid}/directory.editor", method = { POST, GET })
	public ModelAndView node_dialog(HttpServletRequest request, @PathVariable("mid") String mid, Long id, Long parent_id, String parent_name) {
		DialogModel model = new DialogModel(mid, request);
		if (null != id) { // update
			DirectoryEntity entity = directoryService.get(id);
			if (null != entity) {

				if(entity.getStatus() == -1){
					model.addObject("cantEdit", true);
				}else{
					DirectoryEntity parent = entity.getParent();
					if (null == parent) { // root
						model.addObject("parent_id", 0);
						model.addObject("parent_name", rootName);
					} else {
						model.addObject("parent_id", entity.getParent().getId());
						model.addObject("parent_name", entity.getParent().getName());
					}
					model.addObject("id", id);
					model.addObject("name", entity.getName());
					model.addObject("maxCount", entity.getMaxCount());
					model.addObject("maxLength", entity.getMaxLength());
					model.addObject("share", entity.getShare());
					model.addObject("status", entity.getStatus());
					model.addObject("remark", entity.getRemark());
				}
			}
		} else { // insert
			model.addObject("parent_id", parent_id);
			model.addObject("parent_name", parent_name);
		}
		return model.execute("file/directory-editor");
	}

	@ResponseBody
	@RequestMapping(value = "!{mid}/directory.submit", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult directorySubmit(@PathVariable("mid") String mid, Long id, Long parent_id, String name, Integer maxCount, Long maxLength,
			Integer share, Integer status, String remark) {
		maxCount = null == maxCount ? 0 : maxCount;
		maxLength = null == maxLength ? 0 : maxLength;
		if (null != id && null != directoryService.get(id)) { // update
			directoryService.update(getUser(), id, name, maxCount, maxLength, share, status, remark);
		} else { // insert
			directoryService.append(getUser(), parent_id, name, maxCount, maxLength, share, status, remark);
		}
		return JsonResult.result();
	}

	@ResponseBody
	@RequestMapping(value = "!{mid}/directory.remove", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult remove(@PathVariable("mid") String mid, HttpServletRequest request, Long id) {
		DirectoryEntity entity;
		if (null == id || id < 1 || null == (entity = directoryService.get(id))) {
			return JsonResult.error("数据异常！");
		}
//		List<FileEntity> file = entity.getFile();
//		List<FileEntity> file = fileService.getFile(entity.getId());
//		if (!directoryService.isLeaf(entity) || null != file && !file.isEmpty()) {
//			return JsonResult.error("该目录下存在子目录或文件，不允许删除！");
//		}
		for(DirectoryEntity dir : entity.getChildren()){
			if(!dir.isDeleted())
				return JsonResult.error("该目录下存在子目录或文件，!.不允许删除！");
		}
		
//		if(entity.getChildren().isEmpty()){       //entity.getFile().isEmpty()
//			directoryService.delete(entity.getId());
//		}else{
//			return JsonResult.error("该目录下存在子目录或文件，!.不允许删除！");
//		}
		directoryService.delete(entity.getId());
		return JsonResult.result();
	}

	@ResponseBody
	@RequestMapping(value = "!{mid}/directory.move", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult move(@PathVariable("mid") String mid, HttpServletRequest request, Long node_id, Long pnode_id) {
		directoryService.move(getUser(), node_id, pnode_id);
		return JsonResult.result();
	}
	
	// ---------------------------------------------------- File ----------------------------------------------------
	@RequestMapping(value = "!{mid}/file.editor", method = { POST, GET })
	public ModelAndView fileEditor(HttpServletRequest request, Long id, Long directory_id) {
		return FileHelper.helper.edit(request, fileService, id, directory_id);
	}

	@RequestMapping(value = "!{mid}/file.detail", method = GET)
	public ModelAndView detail(HttpServletRequest request, final Long id) {
		return FileHelper.helper.detail(request, fileService, id);
	}

	@ResponseBody
	@RequestMapping(value = "!{mid}/file.delete", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult fileDelete(HttpServletRequest request, String ids, Long node_id) {
		fileService.delete(getUser(), StringHelper.toLongArray(ids), node_id);
		return JsonResult.result();
	}
	
	@RequestMapping(value = "!{mid}/file.version", method = { POST, GET })
	public ModelAndView fileVersion(HttpServletRequest request, final Long id) {
		DialogModel model = new DialogModel(request);
		if (null != id) {
			List<FileHistoryEntity> fhs = fileService.getFileHistory(id);
			model.addObject("fhs", fhs);
		}
		model.addObject("id", id);
		return model.execute("file/file-version");
	}

	@ResponseBody
	@RequestMapping(value = "!{mid}/file.search", method = { RequestMethod.GET, RequestMethod.POST })
	public Page search(@PathVariable("mid") String mid, HttpServletRequest request, @ModelAttribute("form") final FileCondition form) {
		try {
			ReadEvent event = new ReadEvent(applicationContext, request, getUser());
			Long did = form.getSearchDirectory();
			if(null != did && did > 0){
				event.setScope(VisibleScope.GLOBAL);
			}
			Integer type = form.getSearchType();
			Finder fr = fileService.finder(form, event, FileItem.class);
			fr.and("x.status<>?", FileStatus.STATUS_DELETED);
			if (type != null) {
				if (type >= 0) {
					fr.and("x.type=?", FileType.values()[type]);
				} else if (type == -2) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, -7);
					fr.and("x.updateAt > ?", cal.getTime()); // 7天内修改过的
					fr.order("updateAt", "desc");
				}
			}
			return fr.getPage();
		} catch (Exception e) {
			e.printStackTrace();
			return Page.EMPTY;
		}
	}
	
	@RequestMapping(value = "!{mid}/file.operator", method = POST)
	public ModelAndView file_operator(HttpServletRequest request, @PathVariable("mid") String mid, String ids, Long type, Long source_node_id) {
		DialogModel model = new DialogModel(request);
		model.addObject("ids", ids);
		model.addObject("type", type); // 0:移动  1:复制
		model.addObject("source_node_id", source_node_id);
		return model.execute("file/file-operator");
	}
	
	@ResponseBody
	@RequestMapping(value = "!{mid}/file/operator.submit", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult file_operator_submit(@PathVariable("mid") String mid, HttpServletRequest request, String ids, Long type, Long source_node_id, Long target_node_id) {
		try {
			fileService.fileOperator(getUser(), StringHelper.toLongArray(ids), type, source_node_id, target_node_id);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error(e);
		}
		return JsonResult.result();
	}
	//------------------------------用户组分享-------------------------------------
	@ResponseBody
	@RequestMapping(value = "!{key}/usergroup.search", method = { RequestMethod.GET, RequestMethod.POST })
	public Page searchUserGroup(HttpServletRequest request, @ModelAttribute("form") final AccountCondition form, String hasPrivileges, final Long id) {
		try {
			ReadEvent event = new ReadEvent(applicationContext, request, getUser());
			event.setScope(VisibleScope.GLOBAL);
			Finder fr = userGroupService.finder(form, event, FileShareItem.class);
//			if (StringHelper.isNotEmpty(hasPrivileges)) {
//				fr.and((hasPrivileges.equals("1") ? "x.id in" : "x.id not in") + "(select fs.account.id from FileShareEntity fs where actions<>0 and file.id=?)", id);
//			}
			fr.setHandler(new FinderHandler() {
				@Override
				public Object doHandler(Object source, Object obj) throws Exception {
					FileShareItem fsi = (FileShareItem) obj;
//					UserGroupMangeItem ugmi = (UserGroupMangeItem) obj;
//					Long gid = ugmi.getId();
					Long uid = fsi.getId();
					Long actions = fileService.getGroupActions(id, uid);
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
	@ResponseBody
	@RequestMapping(value = "!{key}/usergroup.save", method = POST)
	public final JsonResult groupShare(final Long id, String actions) {
		//actions :11_4,7_3
//		Long groupId = (long)4;
		
		try {
			
			fileService.saveGroupActions(getUser(), id, actions);
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
//		return null;
	}
}
