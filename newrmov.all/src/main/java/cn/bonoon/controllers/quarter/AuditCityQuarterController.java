package cn.bonoon.controllers.quarter;

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import cn.bonoon.core.CityQuarterReportService;
import cn.bonoon.core.QuarterReportStatus;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.controllers.BaseButtonResolver;
import cn.bonoon.kernel.web.handlers.DialogUpdateHandler;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.views.TemplateView;
import cn.bonoon.util.ControllerUtil;
import cn.bonoon.util.FrontStyleCodeUtil;

@Controller
@RequestMapping("s/cls/macqa")
public class AuditCityQuarterController extends
		AbstractGridController<ModelAreaQuarterItem, ModelAreaQuarterAuditItem> {

	private CityQuarterReportService cityQuarterReportService;

	@Autowired
	protected AuditCityQuarterController(
			CityQuarterReportService cityQuarterReportService) {
		super(cityQuarterReportService);
		this.cityQuarterReportService = cityQuarterReportService;

	}

	@Override
	@GridStandardDefinition(
	// detailClass = CityQuarterReportDetail.class,
	autoOperation = false, deleteOperation = false)
	@QueryExpression("x.status>=0 and x.modelArea.cityId=(select y.place.id from UnitEntity y where y.id={USER owner})")
	protected CityQuarterReportService initLayoutGrid(
			LayoutGridRegister register) throws Exception {
		CityQuarterAuditHandler cqahandler = new CityQuarterAuditHandler(0,
				cityQuarterReportService, autoManager,
				ModelAreaQuarterReportAuditor.class);
		DialogUpdateHandler<ModelAreaQuarterItem> dh = new DialogUpdateHandler<>(
				register, cqahandler);
//		BaseButtonResolver br = dh.register(register.button("审核", "",
//				ButtonEventType.DIALOG).status(QuarterReportStatus.AUDIT));
//		br.setName("审核");
		register.button("审核", "index.review", ButtonEventType.JUMP).status(4)
				.ordinal(0);
		register.button("查看", "index.report", ButtonEventType.DIALOG);

		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(50);
		return cityQuarterReportService;
	}

	@RequestMapping("{key}/index.review")
	public String review(Long id, Model model) {
		ModelAreaQuarterItem maqi = cityQuarterReportService.get(id);
		FrontStyleCodeUtil fscu = FrontStyleCodeUtil
				.getFrontStyleCodeUtilEntity();
		model.addAttribute(
				"FrontStyle",
				FrontStyleCodeUtil.quoteStyle(
						fscu.getDateTimePicker_Bootstrap_bootstrap_min_css(),
						fscu.getDateTimePicker_Bootstrap_bootstrap_datetimepicker_min_css(),
//						fscu.getJquery_min_js(),
						fscu.getBootstrap_min_js(),
						fscu.getDateTimePicker_Bootstrap_bootstrap_datetimepicker_fr_js(),
						fscu.getDateTimePicker_Bootstrap_bootstrap_datetimepicker_js(),
						fscu.getBootstrap_datetimepicker_zh_CN_js()));
		model.addAttribute("display",
				"<style>#btnSubmit{display:none;}</style>");
		model.addAttribute("title", "<h3>广东省" + maqi.getCityName()
				+ maqi.getModelArea().getName() + "连片示范建设工程进展情况"
				+ maqi.getBatch().getQuarter().getAnnual() + "年第"
				+ (maqi.getBatch().getQuarter().getPeriod() + 1)
				+ "季度审核表 </h3>");
		model.addAttribute("deadline",
				sdf.format(maqi.getBatch().getQuarter().getDeadline()));
		model.addAttribute("item", maqi);
		if (null != maqi.getStartProject()) {
			model.addAttribute("srart", maqi.getStartProject());
		} else {
			model.addAttribute("srart", 0);
		}
		if (null != maqi.getFinishProject()) {
			model.addAttribute("finish", maqi.getFinishProject());
		} else {
			model.addAttribute("finish", 0);
		}

		return "report/quarter-audit2";
	}

	// 驳回
	@ResponseBody
	@RequestMapping(value = "{key}/index.auditReject", method = RequestMethod.POST)
	public String auditReject(Long id, String auditContent, String auditName,
			Date auditAt) {
		try {
			cityQuarterReportService.auditReject(null, id, auditContent,
					auditName, auditAt);
			Map<String, String> map = new HashMap<String, String>();
			map.put("msg", "驳回成功!");
			return ControllerUtil.getJSONString(map);
		} catch (Exception e) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("msg", e.getMessage().toString());
			// System.out.println(ControllerUtil.getJSONString(map));
			return ControllerUtil.getJSONString(map);
		}

	}

	// 通过审核
	@ResponseBody
	@RequestMapping(value = "{key}/index.auditPass", method = RequestMethod.POST)
	public String auditPass(Long id, String auditContent, String auditName,
			Date auditAt) {
		try {
			cityQuarterReportService.auditPass(null, id, auditContent,
					auditName, auditAt);
			Map<String, String> map = new HashMap<String, String>();
			map.put("msg", "审核通过");
			return ControllerUtil.getJSONString(map);
		} catch (Exception e) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("msg", e.getMessage().toString());
			// System.out.println(ControllerUtil.getJSONString(map));
			return ControllerUtil.getJSONString(map);
		}
	}

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

	@RequestMapping("!{key}/index.report")
	public ModelAndView report(HttpServletRequest request, Long id,
			String gridid, String v) {
		DialogModel model = new DialogModel("c_s_" + id, request);
		model.addForm(id);
		ModelAreaQuarterItem item = cityQuarterReportService.get(id);
		if (null != item) {

		}
		model.addObject("title", "广东省" + item.getCityName()
				+ item.getModelArea().getName() + "连片示范建设工程进展情况统计表");
		model.addObject("deadline",
				sdf.format(item.getBatch().getQuarter().getDeadline()));
		model.addObject("itemId", id);
		model.addObject("item", item);
		model.addObject("status", item.getStatus());
		model.addObject("auditat", item.getAuditAt());
		model.addObject("auditName", item.getAuditName());
		model.addObject("auditContent", item.getAuditContent());

		if (null != item.getStartProject()) {
			model.addObject("srart", item.getStartProject());
		} else {
			model.addObject("srart", 0);
		}
		if (null != item.getFinishProject()) {
			model.addObject("finish", item.getFinishProject());
		} else {
			model.addObject("finish", 0);
		}

		double totalfunds = 0.0;
		totalfunds = add(totalfunds, item.getInvestment().getStateFunds());
		totalfunds = add(totalfunds, item.getInvestment().getProvinceFunds());
		totalfunds = add(totalfunds, item.getInvestment().getSpecialFunds());
		totalfunds = add(totalfunds, item.getInvestment().getSocialFunds());
		totalfunds = add(totalfunds, item.getInvestment().getCityFunds());
		totalfunds = add(totalfunds, item.getInvestment().getCountyFunds());
		totalfunds = add(totalfunds, item.getInvestment().getMassFunds());
		totalfunds = add(totalfunds, item.getInvestment().getOtherFunds());

		model.addObject("totalfunds", totalfunds);

		// if("true".equalsIgnoreCase(v)){
		// model.addObject("reportable", true);
		// }else{
		// model.addObject("reportable", false);
		// }
		// List<ModelAreaQuarterAdministrationRural> qars =
		// item.getAdminRurals();
		// model.addObject("qars", qars);
		return model.execute("report/quarter-report");
	}

	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.GLOBAL;
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.excel", method = GET)
	public View excel(HttpServletRequest request, HttpServletResponse response,
			Long id) {

		ModelAreaQuarterItem item = cityQuarterReportService.get(id);
		return new CityQuarterStatExcelView(item);
	}

	// 打印20160413
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid,
			Model model) {
		ModelAreaQuarterItem item = cityQuarterReportService.get(id);
		if (null != item) {

		}
		model.addAttribute("title", "广东省" + item.getCityName()
				+ item.getModelArea().getName() + "连片示范建设工程进展情况统计表");
		model.addAttribute("deadline",
				sdf.format(item.getBatch().getQuarter().getDeadline()));
		model.addAttribute("itemId", id);
		model.addAttribute("item", item);
		model.addAttribute("status", item.getStatus());
		model.addAttribute("auditat", item.getAuditAt());
		model.addAttribute("auditName", item.getAuditName());
		model.addAttribute("auditContent", item.getAuditContent());

		if (null != item.getStartProject()) {
			model.addAttribute("srart", item.getStartProject());
		} else {
			model.addAttribute("srart", 0);
		}
		if (null != item.getFinishProject()) {
			model.addAttribute("finish", item.getFinishProject());
		} else {
			model.addAttribute("finish", 0);
		}

		double totalfunds = 0.0;
		totalfunds = add(totalfunds, item.getInvestment().getStateFunds());
		totalfunds = add(totalfunds, item.getInvestment().getProvinceFunds());
		totalfunds = add(totalfunds, item.getInvestment().getSpecialFunds());
		totalfunds = add(totalfunds, item.getInvestment().getSocialFunds());
		totalfunds = add(totalfunds, item.getInvestment().getCityFunds());
		totalfunds = add(totalfunds, item.getInvestment().getCountyFunds());
		totalfunds = add(totalfunds, item.getInvestment().getMassFunds());
		totalfunds = add(totalfunds, item.getInvestment().getOtherFunds());

		model.addAttribute("totalfunds", totalfunds);
		return "report/quarter-report-view";
	}
}
