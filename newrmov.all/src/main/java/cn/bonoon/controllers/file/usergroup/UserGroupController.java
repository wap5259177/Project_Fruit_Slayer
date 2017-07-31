package cn.bonoon.controllers.file.usergroup;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.account.AccountCondition;
import cn.bonoon.core.UserGroupService;
import cn.bonoon.core.plugins.AccountService;
import cn.bonoon.entities.ShareUserGroupEntity;
import cn.bonoon.entities.plugins.AccountEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.support.models.Page;
import cn.bonoon.kernel.support.searcher.Finder;
import cn.bonoon.kernel.support.searcher.FinderHandler;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/file/usergroup")
public class UserGroupController extends AbstractGridController<ShareUserGroupEntity, UserGroupItem>{

//	@Autowired
//	private FileService fileService;

	@Autowired
	private AccountService accountService;
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	protected UserGroupController(UserGroupService userGroupService) {
		
		super(userGroupService);
		this.userGroupService = userGroupService;
		
	}
	
	@Override
	@GridStandardDefinition(
//			detailClass = UserGroupDetail.class,
			insertClass = UserGroupEditor.class, 
			updateClass = UserGroupEditor.class
			)
	protected UserGroupService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("用户管理","index.usergroup?v=true", ButtonEventType.DIALOG).ordinal(40);
		register.button("查看","index.detail", ButtonEventType.DIALOG).ordinal(0);
		return userGroupService;
	}
	


	@RequestMapping(value = "!{key}/index.usergroup", method = GET)
	public ModelAndView userGroup(HttpServletRequest request, Long id, String gridid,String v) {
		DialogModel model = new DialogModel(request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		
		
		if("true".equalsIgnoreCase(v)){
			model.addObject("reportable", true);
		}else{
			model.addObject("reportable", false);
			
			ShareUserGroupEntity sug = userGroupService.get(id);
			model.addObject("groupname", sug.getName());
			model.addObject("remark", sug.getRemark());
			
		}
		return model.execute("file/user-manage");
	}
	
	@RequestMapping(value = "!{key}/index.detail", method = GET)
	public ModelAndView userGroupDetail(HttpServletRequest request, Long id, String gridid,String v) {
		DialogModel model = new DialogModel(request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		
		
		if("true".equalsIgnoreCase(v)){
			model.addObject("reportable", true);
		}else{
			model.addObject("reportable", false);
			
			ShareUserGroupEntity sug = userGroupService.get(id);
			model.addObject("groupname", sug.getName());
			model.addObject("remark", sug.getRemark());
//			model.addObject("account",sug.getAccounts());
			List<AccountEntity> accounts = sug.getAccounts();
			model.addObject("accounts",accounts);
			
		}
		return model.execute("file/user-group-detail");
	}
//	@RequestMapping(value = "!{key}/index.detail", method = GET)
//	public ModelAndView userGroupDetail(HttpServletRequest request, Long id, String gridid) {
//		DialogModel model = new DialogModel(request);
//		model.addForm(id);
//		model.addObject("gridid", gridid);
//		model.addObject("id", id);
//		return model.execute("file/user-group-detail");
//	}
	
	
	@ResponseBody
	@RequestMapping(value = "!{key}/privileges.search", method = { RequestMethod.GET, RequestMethod.POST })
	public Page search(HttpServletRequest request, @ModelAttribute("form") final AccountCondition form, String hasPrivileges, final Long id) {
		try {
			ShareUserGroupEntity sug = userGroupService.get(id);
			ReadEvent event = new ReadEvent(applicationContext, request, getUser());
			event.setScope(VisibleScope.GLOBAL);
			Finder fr = accountService.finder(form, event, UserGroupMangeItem.class);
//			if (StringHelper.isNotEmpty(hasPrivileges)) {
//				fr.and((hasPrivileges.equals("1") ? "x.id in" : "x.id not in") + "(select fs.account.id from FileShareEntity fs where actions<>0 and file.id=?)", id);
//			}
			
			
			final List<Long> selids = new ArrayList<>();
			for(AccountEntity a : sug.getAccounts()){
				selids.add(a.getId());
			}
			fr.setHandler(new FinderHandler() {
				@Override
				public Object doHandler(Object source, Object obj) throws Exception {
					UserGroupMangeItem ugmi = (UserGroupMangeItem) obj;
					ugmi.setChec(selids.remove(ugmi.getId()));
//					Long uid = ugmi.getId();
//					Long actions = userGroupService.getActions(id, uid);
//					if (null == actions) {
//						actions = 0L;
//					}
//					String name = "fs_" + uid;
//					String input = "<input type='hidden' class='fs_actions' name='" + name + "' value='" + actions.toString() + "_" + uid + "' />";
//					ugmi.setChec(true);
//					fsi.setOptDetail("<input type='checkbox' class='fs_detail' name='" + name + ((actions & 1) != 0 ? "' checked='checked' />" : "' />"));
//					fsi.setOptDownload("<input type='checkbox' class='fs_download' name='" + name + ((actions & 2) != 0 ? "' checked='checked' />" : "' />"));
//					fsi.setOptEditor("<input type='checkbox' class='fs_editor' name='" + name + ((actions & 4) != 0 ? "' checked='checked' />" : "' />"));
//					fsi.setOptDelete("<input type='checkbox' class='fs_delete' name='" + name + ((actions & 8) != 0 ? "' checked='checked' />" : "' />") + input);
					
					
					
					return ugmi;
				}
			});
			
			return fr.getPage();
		} catch (Exception e) {
			e.printStackTrace();
			return Page.EMPTY;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/privileges.save", method = POST)
	public final JsonResult share(final Long id, String actions) {
//		System.out.println(",,");
		try {
			userGroupService.saveActions(getUser(), id, actions);
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.GLOBAL;
	}
}
