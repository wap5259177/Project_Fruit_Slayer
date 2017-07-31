package cn.bonoon.controllers.area.manage;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import cn.bonoon.controllers.EmpowerVerification;
import cn.bonoon.controllers.area.ModelAreaDetailSimple;
import cn.bonoon.controllers.area.ModelAreaItem;
import cn.bonoon.controllers.area.uncommitted.ModelAraeInsertInitializer;
import cn.bonoon.core.ApplicantStatus;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.adjusts.ModelAreaManageOptimizeRecordAdRuralStatus;
import cn.bonoon.core.adjusts.ModelAreaManageOptimizeRecordNewRuralStatus;
import cn.bonoon.core.plugins.LoginService;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.plugins.AccountEntity;
import cn.bonoon.entities.plugins.FlagType;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/pl/ma/manage")
public class ModelAreaManageController extends
		AbstractGridController<ModelAreaEntity, ModelAreaItem> {

	private ModelAreaService modelAreaService;

	// private DelPorjectSourceInvComparerService
	// delPorjectSourceInvComparerService;
	// ,DelPorjectSourceInvComparerService delPorjectSourceInvComparerService
	@Autowired
	protected ModelAreaManageController(
			ModelAreaService modelAreaService,
			ModelAreaManageOptimizeRecordNewRuralStatus modelAreaManageOptimizeRecordNewRuralStatus,
			ModelAreaManageOptimizeRecordAdRuralStatus modelAreaManageOptimizeRecordAdRuralStatus) {
		super(modelAreaService);
		this.modelAreaService = modelAreaService;

		// this.delPorjectSourceInvComparerService=delPorjectSourceInvComparerService;
	}

	@Override
	@GridStandardDefinition(deleteOperation = false,// 去除删除按钮
	// insertClass = ModelAreaEditor.class,//添加,修改按钮
	detailClass = ModelAreaDetailSimple.class
	// updateClass = ModelAreaUpdate.class,

	)
	protected ModelAreaService initLayoutGrid(LayoutGridRegister register)
			throws Exception {
		register.button("退回", "index.back", ButtonEventType.GET,
				ButtonRefreshType.FINISH).status(ApplicantStatus.DRAFT)
				.ordinal(26);
		register.button("返回", "index.return", ButtonEventType.GET,
				ButtonRefreshType.FINISH)
				.status(ApplicantStatus.FINISH, ApplicantStatus.WAIT_AUDIT)
				.ordinal(25);
		register.button("优化", "index.optimizeEmpowerView",
				ButtonEventType.DIALOG).ordinal(22);
		register.button("提取", "index.extract", ButtonEventType.GET,
				ButtonRefreshType.FINISH).status(ApplicantStatus.DRAFT)
				.ordinal(28);
		register.button("刷新行政村", "index.refreshing", ButtonEventType.GET,
				ButtonRefreshType.FINISH).ordinal(30);
		register.button("提取人口户数", "index.extracthouse", ButtonEventType.GET,
				ButtonRefreshType.FINISH).ordinal(40);
		register.button("项目管理", "index.project", ButtonEventType.DIALOG);
		register.button("更改名称", "index.changeNameView", ButtonEventType.DIALOG)
				.ordinal(41);
		register.button("图片管理", "/s/ma/media/2-manager.image", ButtonEventType.DIALOG);
		return modelAreaService;
	}

	@RequestMapping(value = "!{key}/index.project", method = GET)
	public ModelAndView project(@PathVariable("key") String key,
			HttpServletRequest request, @RequestParam(value = "id") Long id,
			@RequestParam(value = "gridid", required = false) String gridid) {
		DialogModel model = new DialogModel("t_a_" + id, request);
		model.setTitle("片区项目管理");
		model.setSize(500, 250);
		model.addObject("gridid", gridid);
		model.addObject("name", modelAreaService.get(id).getName());
		model.addForm(id);
		return model.execute("project/project-manager");
	}

	@Autowired
	private LoginService loginService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@ResponseBody
	@RequestMapping("!{key}/project.clear")
	public JsonResult projectClear(@RequestParam("id") Long id,
			@RequestParam(value = "un") String un,
			@RequestParam(value = "up") String up) {
		try {

			if (!EmpowerVerification.empowerVerification(un, up, loginService,
					passwordEncoder))
				throw new Exception("授权失败!");
			modelAreaService.clearProjectFunds(id, getUser());
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping("!{key}/project.month")
	public JsonResult projectMonth(@RequestParam("id") Long id,
			@RequestParam(value = "un") String un,
			@RequestParam(value = "up") String up,
			@RequestParam(value = "py") int py,
			@RequestParam(value = "pm") int pm) {
		try {
			AccountEntity user = loginService.loadByLoginName(un);

			// LogonUser cur = getUser();
			// String pwd = RSAManager.rsaManager.decryptJSString(up);
			if (user == null
					|| user.getFlag() != FlagType.SUPER
					|| !passwordEncoder.isPasswordValid(user.getLoginPwd(), up,
							null)) {
				throw new Exception("授权失败！");
			}

			modelAreaService.removeProjectMonthly(id, py, pm, getUser());
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.refreshing", method = GET)
	public JsonResult refreshing(HttpServletRequest request,
			@RequestParam("id") Long id) {
		try {
			modelAreaService.refreshing(id, getUser());
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.extract", method = GET)
	public JsonResult extract(HttpServletRequest request,
			@RequestParam("id") Long id) {
		try {
			modelAreaService.extract(id);
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.changeNameView", method = GET)
	public ModelAndView changeNameView(HttpServletRequest request,
			@RequestParam("id") Long id,
			@RequestParam(value = "gridid", required = false) String gridid) {
		DialogModel model = new DialogModel("t_a_" + id, request);
		model.setTitle("更改名称");
		model.setSize(600, 270);
		model.addObject("gridid", gridid);
		ModelAreaEntity ma = modelAreaService.get(id);

		model.addObject("cityName", ma.getCityName());
		model.addObject("countryName", ma.getCounty());
		model.addObject("modelAreaName", ma.getName());
		model.addForm(id);
		return model.execute("optimize/area/changeName");

	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.changeName")
	public JsonResult changeName(HttpServletRequest request,
			@RequestParam("id") Long id, String un, String un2, String un3) {
		String errorMsg = "";
		ModelAreaEntity ma = modelAreaService.get(id);
		if ((un == null || un.trim().equals(""))
				&& (un2 == null || un2.trim().equals(""))
				&& (un3 == null || un3.trim().equals(""))) {
			return JsonResult.error("您没有输入任何信息,修改名称失败!");
		}

		if (un != null && un.trim().length() == 3) {
			ma.setCityName(un);
			errorMsg += "市名修改成功!";
		} else if (un != null && un.length() > 3 || un.length() < 3) {
			errorMsg += "市名修改失败,市名字符长度必须为3!";
		}
		if (un2 != null && un2.trim().length() == 3) {
			ma.setCounty(un2);
			errorMsg += "县名修改成功!";
		} else if (un2 != null && un2.length() > 3 || un2.length() < 3) {
			errorMsg += "县名修改失败,县名字符长度必须为3!";
		}
		if (un3 != null && !un3.trim().equals("")&&un3.trim().length() < 15) {
			ma.setName(un3.trim());
			errorMsg += "片区名修改成功!";
		} else {
			errorMsg += "片区名修改失败,片区名不能填空,字符长度必须小于15!";
		}
		modelAreaService.update(ma);
		return JsonResult.result(1, errorMsg);

	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.optimizeEmpowerView", method = GET)
	public ModelAndView optimizeEmpowerView(HttpServletRequest request,
			@RequestParam("id") Long id,
			@RequestParam(value = "gridid", required = false) String gridid) {

		DialogModel model = new DialogModel("t_a_" + id, request);
		model.setTitle("优化");
		model.setSize(280, 140);
		model.addObject("gridid", gridid);
		model.addForm(id);
		return model.execute("optimize/area/modelArea-optimize-empower");

		// return model.execute("project/project-manager");
		// try{
		// OperateEvent event = new OperateEvent(applicationContext, request,
		// null, getUser());
		// modelAreaService.optimize(getUser(),event, id);
		// return JsonResult.result();
		// }catch(Exception ex){
		// return JsonResult.error(ex);
		// }
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.optimize")
	public JsonResult optimize(HttpServletRequest request,
			@RequestParam("id") Long id, String un, String up) {
		try {
			if (!EmpowerVerification.empowerVerification(un, up, loginService,
					passwordEncoder))
				throw new Exception("授权失败!");
			OperateEvent event = new OperateEvent(applicationContext, request,
					null, getUser());
			modelAreaService.optimize(getUser(), event, id);
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.back", method = GET)
	public JsonResult back(@RequestParam("id") Long id) {
		try {
			modelAreaService.back(id, getUser());
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.return", method = GET)
	public JsonResult returnMA(@RequestParam("id") Long id) {
		try {
			modelAreaService.returnMA(id, getUser());
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.extracthouse", method = GET)
	public JsonResult extractHouse(HttpServletRequest request,
			@RequestParam("id") Long id) {
		try {
			modelAreaService.extractHouse(id, getUser());
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
