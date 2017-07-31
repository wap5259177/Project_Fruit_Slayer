package cn.bonoon.controllers.statistics;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.controllers.area.back.ModelAreaBackEditor;
import cn.bonoon.core.CityQuarterReportService;
import cn.bonoon.core.LocalQuarterReportService;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.StatisticsScheduleService;
import cn.bonoon.entities.ModelAreaQuarterBatch;
import cn.bonoon.entities.ModelAreaQuarterEntity;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.JsonResult;
import cn.bonoon.util.ControllerUtil;
import cn.bonoon.util.FrontStyleCodeUtil;

@Controller
@RequestMapping("s/pls/sms")
public class ProvinceStatisticsScheduleController extends
		AbstractGridController<ModelAreaQuarterEntity, ProvinceStatisticsItem> {

	@Autowired
	protected CityQuarterReportService cityQuarterReportService;

	private StatisticsScheduleService statisticsScheduleService;

	@Autowired
	public ProvinceStatisticsScheduleController(
			StatisticsScheduleService statisticsScheduleService) {
		super(statisticsScheduleService);
		this.statisticsScheduleService = statisticsScheduleService;
	}

	@Override
	@GridStandardDefinition(deleteOperation = false,// 去除删除按钮
	// detailClass = ProvinceStatisticsDetail.class,
	updateClass = ProvinceStatisticsEditor.class, insertClass = ProvinceStatisticsEditor.class, editStatus = 0)
	protected StatisticsScheduleService initLayoutGrid(
			LayoutGridRegister register) throws Exception {
		register.button("查看", "index.engineeringProgressTable",
				ButtonEventType.JUMP).ordinal(1);
		register.button("刷新", "index.refresh", ButtonEventType.GET,
				ButtonRefreshType.FINISH).status(0).ordinal(22);
		register.button("结束", "index.finish", ButtonEventType.GET,
				ButtonRefreshType.FINISH).status(0).ordinal(23);
		register.button("锁定/解锁", "index.lock", ButtonEventType.GET,
				ButtonRefreshType.FINISH).status(0, 100).ordinal(24);

		register.button("刷新9项指标", "index.refreshRural", ButtonEventType.GET,
				ButtonRefreshType.FINISH).status(0, 100).ordinal(25);

		register.button("打印", "index.print", ButtonEventType.JUMP).ordinal(50);
		register.button("导出指标", "percent.excel", ButtonEventType.LOAD,
				ButtonRefreshType.NONE).ordinal(60);
		return statisticsScheduleService;
	}

	private TreeMap<String, Integer> getBatchsTM(
			List<ModelAreaQuarterBatch> batchL) {
		TreeMap<String, Integer> sb2 = new TreeMap<String, Integer>();

		for (ModelAreaQuarterBatch maqb : batchL) {
			if (maqb.getBatchName() == null)
				continue;
			sb2.put(maqb.getBatchName(),
					new Integer(BatchHelper.indexOf(maqb.getBatchName())));
		}
		return sb2;
	}

	@RequestMapping("{key}/index.engineeringProgressTable")
	public String engineeringProgressTable(Long id, Model model) {
		ModelAreaQuarterEntity maqe = statisticsScheduleService.get(id);

		FrontStyleCodeUtil fscu = FrontStyleCodeUtil
				.getFrontStyleCodeUtilEntity();
		model.addAttribute(
				"FrontStyle",
				FrontStyleCodeUtil.quoteStyle(fscu.getBootstrap_min_css()
						+ fscu.getBootstrap_theme_min_css()
						+ fscu.getJquery_min_js() + fscu.getBootstrap_min_js()));
		model.addAttribute("title", "省级新农村连片示范建设工程进展情况统计表");
		model.addAttribute("showPeriod",
				maqe.getAnnual() + "年" + (maqe.getPeriod() + 1) + "季度");
		model.addAttribute("deadline", sdf.format(maqe.getDeadline()));
		model.addAttribute("showVarList", setShowWhat(maqe, "一"));
		model.addAttribute("display",
				"<style>#btnSubmit{display:none;}</style>");
		StringBuffer sb = new StringBuffer();

		sb.append("<div class='btn-group'><button type='button' id='showSelectWhichBatch' "
				+ "' class='btn btn-primary dropdown-toggle' data-toggle='dropdown'>第一批<span class='caret'></span></button><ul class='dropdown-menu' role='menu'>");

		List<ModelAreaQuarterBatch> maqbL = maqe.getBatchs();
		if (maqbL != null && maqbL.size() > 0) {
			for (ModelAreaQuarterBatch maqb : maqbL) {

				sb.append("<li><a href='#' bId='" + maqb.getId()
						+ "' onclick='selectBatch(this)'>"
						+ maqb.getBatchName() + "</a></li> ");
			}
		}
		sb.append("</ul></div>");
		model.addAttribute("selectBatch", sb.toString());
		model.addAttribute("changeBatch", getBatchsTM(maqe.getBatchs()));

		return "statistics/engineeringProgressTable";
	}

	@RequestMapping("{key}/engineeringProgressTableAjax")
	@ResponseBody
	public String engineeringProgressTableAjax(Long bId) {
		List<ModelAreaQuarterItem> mqiL = statisticsScheduleService
				.itemSurveies(bId);
		if (mqiL == null || mqiL.size() < 1)
			return "";
		TreeSet<ShowProvinceStatisticsSchedulemMsg> set = new TreeSet<ShowProvinceStatisticsSchedulemMsg>(
				new Comparator<ShowProvinceStatisticsSchedulemMsg>() {

					@Override
					public int compare(ShowProvinceStatisticsSchedulemMsg o1,
							ShowProvinceStatisticsSchedulemMsg o2) {
						if (o1.getOrdinalModel() > o2.getOrdinalModel()) {
							return 1;
						} else if (o1.getOrdinalModel() < o2.getOrdinalModel()) {
							return -1;
						}
						// 因为排序的序号有时还是相同的
						return -1;
					}
				});
		for (ModelAreaQuarterItem i : mqiL) {
			ShowProvinceStatisticsSchedulemMsg var = new ShowProvinceStatisticsSchedulemMsg();
			var.setMsg(i);
			StringBuffer sb = new StringBuffer();
			sb.append("<p class='popover-options'>");

			String statusS = "";

			if (var.getStatus() == -1) {
				statusS = "-1";
			} else if (var.getStatus() == 0) {
				statusS = "未开始";
			} else if (var.getStatus() == 1) {
				statusS = "已提交";
			} else if (var.getStatus() == 2) {
				statusS = "正在上报";
			} else if (var.getStatus() == 3) {
				statusS = "驳回";
			} else if (var.getStatus() == 4) {
				statusS = "待审核";
			}

			sb.append("<a  type='button' onclick='PopUpBox();return false;' class='btn btn-success popover-destroy needHidden' title='<h2>"
					+ var.getSssm().getVar1() + "(" + statusS + ")</h2>'");
			sb.append("data-container='body' data-toggle='popover'  data-placement='left' data-content=\" ");
			sb.append("<h4>");

			if (var.getIsLock() && var.getStatus() != -1) {
				sb.append("<button type='button' class='btn btn-default btn-sm' onclick='unlock("
						+ var.getId() + ");return false;'>");
				sb.append("<span class='glyphicon glyphicon-wrench'></span> 解锁");
				sb.append("</button>");
			} else {

				if (var.getStatus() == 0 || var.getStatus() == 2
						|| var.getStatus() == 3) {
					sb.append("<button type='button' class='btn btn-default btn-sm' onclick='c("
							+ var.getId() + ");return false;'>");
					sb.append("<span class='glyphicon glyphicon-wrench'></span> 催报");
					sb.append("</button>");
				} else if (var.getStatus() == 1 || var.getStatus() == 4) {
					sb.append("<button type='button' class='btn btn-default btn-sm' onclick='cc("
							+ var.getId() + ");return false'>");
					sb.append("<span class='glyphicon glyphicon-wrench'></span> 退回");
					sb.append("</button>");
				}
				if (var.getStatus() != -1) {
					if (var.isDisabled() == false) {
						sb.append("<button type='button' class='btn btn-default btn-sm' onclick='ban("
								+ var.getId() + ");return fasle;'>");
						sb.append("<span class='glyphicon glyphicon-wrench'></span> 禁止");
						sb.append("</button>");
					} else {
						sb.append("<button type='button' class='btn btn-default btn-sm' onclick='regain("
								+ var.getId() + ");return fasle;'>");
						sb.append("<span class='glyphicon glyphicon-wrench'></span> 恢复");
						sb.append("</button>");

					}

				}
			}

			if (var.getStatus() != 0) {
				sb.append("<button type='button' class='btn btn-default btn-sm' onclick='changeQuarter("
						+ var.getId()
						+ ");return false;' title='注意：本功能是针对行政村有编制规划设计村数（个）和9项指标数据出错时用的，将更新至最新数据信息'>");
				sb.append("<span class='glyphicon glyphicon-wrench'></span> 矫正");
				sb.append("</button>");

			}
			if (i.getBatch().getQuarter() != null) {
				sb.append("<div class='btn-group'><button type='button' class='btn btn-primary dropdown-toggle btn-sm' id='dropdownMenu1' data-toggle='dropdown'>批次修改<span class='caret'></span></button>");
				sb.append("<ul class='dropdown-menu' role='menu'>");

				TreeMap<String, Integer> tm = getBatchsTM(i.getBatch()
						.getQuarter().getBatchs());

				for (Map.Entry<String, Integer> entrySet : tm.entrySet()) {

					sb.append("<li role='presentation'>");
					sb.append("<a role='menuitem' tabindex='-1' href='#' onclick='");
					sb.append("changeBatch(" + var.getId() + "," + var.getqId()
							+ "," + entrySet.getValue() + ");return false;'>"
							+ entrySet.getKey() + "</a>");
					sb.append("</li>");
				}
				sb.append("</ul></div>");
			}

			sb.append("</h4> \">操作");
			sb.append("</a></p>");
			sb.append("<button type='button' id='" + var.getId() + "'");
			sb.append("onclick='showMoney(" + var.getId() + ");'");
			sb.append("class='btn btn-default btn-sm' >");
			sb.append("<span class='glyphicon glyphicon-chevron-down'></span> 资金");
			sb.append("</button>");
			var.getSssm().setOperation(sb.toString());
			set.add(var);
		}

		return ControllerUtil.getJSONString(set);
	}

	private TreeSet<ModelAreaQuarterItem> setShowWhat(
			ModelAreaQuarterEntity maqe, String batchName) {

		List<ModelAreaQuarterBatch> maqbList = maqe.getBatchs();
		if (maqbList == null)
			return null;
		ModelAreaQuarterBatch getMaqb = null;
		for (ModelAreaQuarterBatch maqb : maqbList) {
			if (maqb.getBatchName().equals(batchName)) {
				getMaqb = maqb;
			}
		}
		TreeSet<ModelAreaQuarterItem> ts = new TreeSet<ModelAreaQuarterItem>(
				new Comparator<ModelAreaQuarterItem>() {

					@Override
					public int compare(ModelAreaQuarterItem o1,
							ModelAreaQuarterItem o2) {
						if (o1.getModelArea().getOrdinalModel() > o2
								.getModelArea().getOrdinalModel()) {
							return 1;
						} else if (o1.getModelArea().getOrdinalModel() < o2
								.getModelArea().getOrdinalModel()) {
							return -1;
						}
						// 因为排序的序号有时还是相同的
						return -1;
					}
				});
		for (ModelAreaQuarterItem item : getMaqb.getItems()) {
			ts.add(item);
		}

		return ts;

	}

	// 打印
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid,
			Model model) {
		ModelAreaQuarterEntity entity = statisticsScheduleService.get(id);
		model.addAttribute("deadline", sdf.format(entity.getDeadline()));
		List<ModelAreaQuarterBatch> bcs = statisticsScheduleService
				.batchSurveies(id);
		List<ModelAreaQuarterBatch> batchs = _sortBatch(bcs);// 批次排序
		for (ModelAreaQuarterBatch b : batchs) {// 片区排序
			List<ModelAreaQuarterItem> items = statisticsScheduleService
					.itemSurveies(b.getId());
			b.setItems(items);
		}
		model.addAttribute("batchs", batchs);
		model.addAttribute("title", "省级新农村连片示范建设工程进展情况统计表 ");
		model.addAttribute("readonly", "readonly='readonly'").addAttribute(
				"disabled", "disabled='true'");
		return "statistics/view-view";
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.refresh", method = GET)
	public JsonResult refreshItem(Long id) {
		try {
			statisticsScheduleService.refreshItem(getUser(), id);
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.finish", method = GET)
	public JsonResult finishEntity(Long id) {
		try {
			statisticsScheduleService.finishEntity(getUser(), id);
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping("!{key}/{id}!report.urge")
	public String urge(@PathVariable("id") Long id) {
		Map<String, String> map = new HashMap<>();

		try {
			statisticsScheduleService.urge(id, getUser());
			map.put("msg", "已催报");
			return ControllerUtil.getJSONString(map);
		} catch (Exception ex) {
			map.put("msg", "催报失败,原因：" + ex.getMessage().toString());
			return ControllerUtil.getJSONString(map);
		}
	}

	@ResponseBody
	@RequestMapping("!{key}/{id}!report.back")
	public String back(@PathVariable("id") Long id) {
		Map<String, String> map = new HashMap<>();
		try {
			statisticsScheduleService.back(id, getUser());
			map.put("msg", "已退回");
			return ControllerUtil.getJSONString(map);
		} catch (Exception ex) {
			map.put("msg", "退回失败,原因：" + ex.getMessage().toString());
			return ControllerUtil.getJSONString(map);
		}
	}

	@ResponseBody
	@RequestMapping("!{key}/{id}!report.ban")
	public String ban(@PathVariable("id") Long id) {
		Map<String, String> map = new HashMap<>();
		try {
			statisticsScheduleService.ban(id, getUser());
			map.put("msg", "已禁止");
			return ControllerUtil.getJSONString(map);
		} catch (Exception ex) {
			map.put("msg", "禁止失败,原因：" + ex.getMessage().toString());
			return ControllerUtil.getJSONString(map);
		}
	}

	@ResponseBody
	@RequestMapping("!{key}/{id}!report.regain")
	public String regain(@PathVariable("id") Long id) {
		Map<String, String> map = new HashMap<>();
		try {
			statisticsScheduleService.regain(id, getUser());
			map.put("msg", "已恢复");
			return ControllerUtil.getJSONString(map);
		} catch (Exception ex) {
			map.put("msg", "恢复失败,原因：" + ex.getMessage().toString());
			return ControllerUtil.getJSONString(map);
		}
	}

	@ResponseBody
	@RequestMapping("!{key}/{pid}!data.node")
	public List<StatisticsNode> nodes(@PathVariable("pid") Long pid, Long id,
			Model model) {
		List<StatisticsNode> sns = new ArrayList<>();
		if (null != id && id < 0) {
			id = -id;
			List<ModelAreaQuarterItem> sscs = statisticsScheduleService
					.itemSurveies(id);
			localQuarterReportService.show9ParamByNoStart(sscs);
			cityQuarterReportService.updateItem4ParamToLast(sscs);
			for (ModelAreaQuarterItem ssc : sscs) {
				sns.add(new StatisticsNode(ssc));
			}
			TreeSet<Integer> ts = new TreeSet<>();
			if (sscs != null && sscs.size() > 0) {
				for (ModelAreaQuarterBatch maqb : sscs.get(0).getBatch()
						.getQuarter().getBatchs()) {
					ts.add(BatchHelper.indexOf(maqb.getBatchName()));

				}
				model.addAttribute("first", ts.first());
				model.addAttribute("last", ts.last());

			}
		} else {
			List<ModelAreaQuarterBatch> sscs = statisticsScheduleService
					.batchSurveies(pid);
			// 重新排下序，按批次第一批，第二批，第三批，珠三角..
			List<ModelAreaQuarterBatch> orcs = _sortBatch(sscs);
			for (ModelAreaQuarterBatch ssc : orcs) {
				sns.add(new StatisticsNode(ssc));
			}
		}
		return sns;
	}

	private List<ModelAreaQuarterBatch> _sortBatch(
			List<ModelAreaQuarterBatch> sscs) {
		List<ModelAreaQuarterBatch> orcs = new ArrayList<>();
		// 省级季度报表，点击查看的时候，批次的顺序。
		String[] orBcs = { "一", "二", "三", "珠三角" };
		for (String bc : orBcs) {
			for (ModelAreaQuarterBatch b : sscs) {
				if (bc.equals(b.getBatchName())) {
					orcs.add(b);
					break;
				}
			}
		}
		return orcs;
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.lock", method = GET)
	public JsonResult lockItem(Long id) {
		try {
			statisticsScheduleService.lockItem(getUser(), id, getUser());
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.refreshRural", method = GET)
	public JsonResult refreshRural(Long id) {
		try {
			statisticsScheduleService.refreshRural(getUser(), id);
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping("!{key}/{id}!report.unlock")
	public String unlock(@PathVariable("id") Long id) {
		Map<String, String> map = new HashMap<>();
		try {
			statisticsScheduleService.unlock(id);
			map.put("msg", "解锁成功");
			return ControllerUtil.getJSONString(map);
		} catch (Exception ex) {
			map.put("msg", "解锁失败,原因:" + ex.getMessage().toString());
			return ControllerUtil.getJSONString(map);
		}
	}

	// 导出table
	@RequestMapping(value = "!{key}/percent.excel", method = { POST, GET })
	@ResponseBody
	public void exportTable(HttpServletRequest request,
			HttpServletResponse response, Model model, Long id) {
		try {
			statisticsScheduleService.exportNfPercent(request, response, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @ResponseBody
	// @RequestMapping("!{key}/{id}/index.changeBatch")
	// public JsonResult changeBatch(@PathVariable("itemId") Long
	// itemId,@PathVariable("batch") int batch,@PathVariable("quarterId")int
	// quarterId,Long id){
	// //获取到要修改的itemID和改为哪个批次Batch的Id
	// //在页面处保留下ModelAreaQuarterEntity的Id
	// // statisticsScheduleService.upadateBatch(itemId, batch,quarterId);
	//
	// try{
	//
	// return JsonResult.result();
	// }catch(Exception ex){
	// return JsonResult.error(ex);
	// }
	// }
	@ResponseBody
	// @RequestMapping("!{key}/{id}!report.ban")
	@RequestMapping("!{key}/{time}/{id}/{quarterId}/{batch}!index.changeBatch")
	public JsonResult changeBatch(@PathVariable("id") Long id,
			@PathVariable("quarterId") Long quarterId,
			@PathVariable("batch") int batch) {

		String msg = null;
		try {
			ModelAreaQuarterEntity m = statisticsScheduleService.get(quarterId);
			boolean batchIsExist = false;
			for (ModelAreaQuarterBatch b : m.getBatchs()) {
				if (b.getBatchName().equals(BatchHelper.getValue(batch))) {
					msg = statisticsScheduleService.upadateBatch(id, batch,
							quarterId, getUser());
					batchIsExist = true;
				}
				;
			}
			if (batchIsExist != true) {
				throw new Exception("批次:" + BatchHelper.getValue(batch)
						+ "不存在,修改失败!");
			}
			// quarterId
			if (msg != null)
				return JsonResult.result(null, msg);
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	// @RequestMapping("!{key}/index.changeBatch2")
	// public String changeBatch2(){
	// //获取到要修改的itemID和改为哪个批次Batch的Id
	// //在页面处保留下ModelAreaQuarterEntity的Id
	// // statisticsScheduleService.upadateBatch(itemId, batch,quarterId);
	//
	// return null;
	// }
	@Autowired
	private LocalQuarterReportService localQuarterReportService;

	/**
	 * 9项指标修改为上一季度
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/index.changeQuarterReport", method = GET)
	public String changeQuarterReport(@RequestParam Long id) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("msg", localQuarterReportService.updateQuarter9Parm(id));
		return ControllerUtil.getJSONString(map);

	}

}
