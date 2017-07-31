package cn.bonoon.controllers.project.report;

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.controllers.inofs.BaseProjectInfo;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.ProjectReportService;
import cn.bonoon.core.ProjectService;
import cn.bonoon.core.json.ProjectReportSaveInfo;
import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.ProjectReportEntity;
import cn.bonoon.entities.ProjectReportItem;
import cn.bonoon.entities.ProjectStatusChangeEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.JsonResult;
import cn.bonoon.kernel.web.models.PanelModel;
import cn.bonoon.util.ControllerUtil;
import cn.bonoon.util.FrontStyleCodeUtil;

/**
 * 月度跟踪汇报
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("s/cl/project/report")
public class ProjectReportController extends AbstractPanelController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private ModelAreaService modelAreaService;
	private ProjectReportService projectReportService;

	@Autowired
	protected ProjectReportController(ProjectReportService projectReportService) {
		this.projectReportService = projectReportService;
	}

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		// 还要判断一下当前的月份是否正在上报，如果正在上报，并且还没有完成上报的，则继续上报
		cal.add(Calendar.MONTH, -1);// 上个月
		int selectedYear = cal.get(Calendar.YEAR);
		int lastMonth = cal.get(Calendar.MONTH);

		LogonUser user = getUser();
		// 某个片区下的某一年度的所有项目月报
		List<ProjectReportEntity> reports = projectReportService.reports(user,
				selectedYear);

		ProjectReportEntity report = __annual(model.getModelMap(), reports,
				selectedYear, year, month, selectedYear, lastMonth);

		model.addObject("selectedYear", selectedYear);
		model.addObject("lastMonth", lastMonth);
		model.addObject("showMonth", lastMonth + 1);

		StringBuilder bodyContent = new StringBuilder();
		if (null == report) {
			// 上个月还没有开始报
			// 还没开始，那提示下可以创建吧
			model.addObject("editable", false);
			bodyContent
					.append("<div style='padding-top:10px;'><tr><td class='reportMessage' colspan='11'>请先 <a href='report!");
			bodyContent.append(selectedYear).append('!').append(lastMonth)
					.append(".start' onclick=\"return startReport(this, '");
			bodyContent.append(selectedYear * 100 + lastMonth).append(
					"');\"> 确定 </a> 开始上报项目的月度报表</td></tr></div>");
		} else {
			__parseContent(model.getModelMap(), bodyContent, report);
		}
		model.addObject("bodyContent", bodyContent);
		Long modelAreaId = -1L;
		if (report != null)
			modelAreaId = report.getModelArea().getId();
		model.addObject("updateReportItem", "<a href='needChangeProject?id="
				+ modelAreaId + "' title='对先创建月度报表导致后续项目月报丢失的修复' >项目月报修复</a>");
		event.setVmPath("project/reporting");
	}

	/**
	 * 对没有先新建月报再创建该月度报表的错误操作导致的该月度报表没有该项目月报进行修复
	 * 
	 * @return
	 */
	@RequestMapping("!{key}/needChangeProject")
	public String updateReportItem(Long id, String projectCode,
			String projectName, HttpServletRequest request, Model model) {
		FrontStyleCodeUtil fsc=FrontStyleCodeUtil.getFrontStyleCodeUtilEntity();
		model.addAttribute("FrontStyle", FrontStyleCodeUtil.quoteStyle(fsc.getBootstrap_min_css(),fsc.getBootstrap_theme_min_css(),fsc.getBootstrap_min_js(),fsc.getJquery_min_js()));
		ModelAreaEntity ma = modelAreaService
				.getByOnwer(getUser().getOwnerId());
		if (id == null || id == -1)
			id = ma.getId();
		model.addAttribute("mid", id);
		model.addAttribute("mName", ma.getName());
		System.out.println(ma.getName());
		// model.addAttribute("itemForNotSetProReport", );
		// 下面是显示某个月度报表月报不存在的项目并附带显示月度(项目没有月报的那些月度)
		TreeMap<ProjectEntity, TreeSet<Integer[]>> proTm = projectService
				.getProjectAndProNoItemPerriod(projectCode, projectName, id,
						projectReportService.getPeriod(id));
		// for (ProjectEntity pe : proList)
		// System.out.println(pe.getName());
		model.addAttribute("needChangeProject", proTm);

		// 隐藏掉提交按钮
		model.addAttribute("display",
				"<style>#btnSubmit{display:none;}</style>");
		return "project/againSetProRepItem";
	}

	public static void main(String[] args) {

	}

	@ResponseBody
	@RequestMapping(value = "{key}/selectPro")
	public String selectPro(Long id, String projectCode, String projectName) {
		try {
			TreeMap<ProjectEntity, TreeSet<Integer[]>> proTm = projectService
					.getProjectAndProNoItemPerriod(projectCode, projectName,
							id, projectReportService.getPeriod(id));
			// 存放要显示到界面的每条记录

			// List<List<Map>> allList=new ArrayList<>();
			// for (Entry<ProjectEntity, TreeSet<Integer[]>> e :
			// proTm.entrySet()) {
			//
			// List<Map> iteml= new ArrayList<Map>();
			// allList.add(iteml);
			// //键 id 值e.getKey().getId().toString()
			// Map<String ,String> idVar = new HashMap<String ,String>();
			// iteml.add(idVar);
			// idVar.put("id", e.getKey().getId().toString());
			// //创建一个map作为一个大的键值对用来包含proMsg perriodMsg
			// Map<String ,List<Map<String ,?>>> value = new HashMap<String
			// ,List<Map<String ,?>>>();
			// iteml.add(value);
			// //valuelist数组包含俩个,项目信息和月报
			// List<Map<String ,?>> valuelist=new ArrayList<Map<String ,?>>();
			// value.put("value", valuelist);
			// //项目信息
			// Map<String ,List<String>> proMsg = new HashMap<String
			// ,List<String>>();
			// valuelist.add(proMsg);
			// List<String> oneProMsg=new ArrayList<String>();
			// oneProMsg.add(e.getKey().getName());
			// oneProMsg.add(e.getKey().getCode());
			// oneProMsg.add(e.getKey().getCreateAt().toString());
			// proMsg.put("proMsg", oneProMsg);
			//
			// //所有月度
			// Map<String ,TreeSet<Integer[]>> perriodMsg = new HashMap<String
			// ,TreeSet<Integer[]>>();
			// valuelist.add(perriodMsg);
			// perriodMsg.put("perriodMsg", e.getValue());
			// }
			// String res = ControllerUtil.toJson(allList);
			// System.out.println(res);
			// return res;
			List<ShowProPerriodMsg> list = new ArrayList<ShowProPerriodMsg>();
			for (Entry<ProjectEntity, TreeSet<Integer[]>> e : proTm.entrySet()) {
				ShowProPerriodMsg sppm = new ShowProPerriodMsg();
				ProjectEntity pe = e.getKey();
				sppm.setId(pe.getId());
				sppm.setName(pe.getName());
				sppm.setCode(pe.getCode());
				sppm.setStatus(pe.getStatus());
				sppm.setCreateAt(pe.getCreateAt().toString());
				sppm.setTs(e.getValue());
				list.add(sppm);

			}
			String res = ControllerUtil.toJson(list);
			System.out.println(res);
			return res;
		} catch (Exception e) {
			TreeMap<String, String> tm = new TreeMap<String, String>();
			tm.put("error", e.toString());
			try {
				String res = ControllerUtil.toJson(tm);
				return res;
			} catch (JsonMappingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JsonGenerationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		return "";
	}

//	@ResponseBody
//	@RequestMapping(value = "{key}/checkItemRep")
//	public String checkItemRep(Long mid, Long projectId, int whitchYear,
//			int whitchMonth) {
//		Map<String, String> map = new HashMap<String, String>();
//		List<ProjectReportItem> itemList = projectService.notSetReoprt(
//				projectId, whitchYear, whitchMonth, mid);
//
//		// return ControllerUtil.getJSONString(proList);
//		return "";
//	}

	@ResponseBody
	@RequestMapping(value = "{key}/updateItem")
	public String updateItem(Long mid, Long projectId, Integer reportPerriod) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", projectService.setProRepItemReportParam(mid, projectId,
				reportPerriod));
		return ControllerUtil.getJSONString(map);
	}

	/**
	 * 生成界面显示的一年每个月的月度报表显示实体
	 * 通过selectedYear+selectedMonth（选择具体哪个月报）=currentYear+currentMonth判断选择了哪个月报
	 * @param model
	 * @param reports
	 * @param currentYear
	 *            鼠标选中的年
	 * @param nowYear
	 *            此时的年
	 * @param nowMonth
	 *            此时的月
	 * @param selectedYear
	 *            当前页面显示的年
	 * @param selectedMonth
	 *            当前页面显示的月
	 * @return
	 */
	private ProjectReportEntity __annual(Map<String, Object> model,
			List<ProjectReportEntity> reports, int currentYear, int nowYear,
			int nowMonth, int selectedYear, int selectedMonth) {

		model.put("preYear", currentYear - 1);
		model.put("nextYear", currentYear + 1);
		model.put("currentYear", currentYear);

		int nowMonthly = nowYear * 100 + nowMonth;
		int selectedMonthly = selectedYear * 100 + selectedMonth;
		ProjectReportEntity current = null;
		List<ProjectReportAnnualInfo> items = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			ProjectReportAnnualInfo pra = new ProjectReportAnnualInfo(reports,
					currentYear, i, nowMonthly, selectedMonthly);
			items.add(pra);
			if (pra.isSelected()) {
				current = pra.getReport();
			}
		}
		model.put("items", items);
		return current;
	}

	@RequestMapping(value = "!{key}/annual!{currentYear}.goto")
	public String moveAnnual(HttpServletRequest request, Model model,
			@PathVariable("currentYear") int currentYear, int selectedYear,
			int selectedMonth) {
		model.addAttribute("layout", "layout-empty.vm");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);

		LogonUser user = getUser();
		List<ProjectReportEntity> reports = projectReportService.reports(user,
				currentYear);

		__annual(model.asMap(), reports, currentYear, year, month,
				selectedYear, selectedMonth);

		return "project/annual-selector";
	}

	@RequestMapping(value = "!{key}/project!{id}.detail")
	public String viewDetail(HttpServletRequest request,
			@PathVariable("id") Long id, Model model) {
		model.addAttribute("layout", "simple-dialog.vm");
		model.addAttribute("id", "dlg_" + id).addAttribute("attrs",
				"title='项目详细信息' style='width:720px;'");
		model.addAttribute("readonly", "readonly='readonly'").addAttribute(
				"disabled", "disabled='true'");

		LogonUser user = getUser();
		try {
			ReadEvent event = new ReadEvent(applicationContext, request, user);
			event.setScope(VisibleScope.GLOBAL);
			model.addAttribute("form",
					projectService.get(event, id, BaseProjectInfo.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "applicant/areaproject-view";
	}

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hh:mm:ss");

	private void __parseContent(Map<String, Object> model,
			StringBuilder content, ProjectReportEntity report) {
		int i = 0, status;
		boolean editing = false;
		status = report.getStatus();
		int year = report.getAnnual();
		int month = report.getPeriod();
		model.put("report", report);

		if (report.getBack() == Integer.valueOf(1)) {
			// content.append("backstatus='false'");
			model.put("backstatus", true);
		} else {
			model.put("backstatus", false);
		}

		if (status == 0) {
			editing = true;
			// 重新再处理一下该月份的项目是否有增加的情况
		}
		// Long modelAreaId = report.getModelArea().getId();
		// 累计截止到当月的月报资金
		double spend = 0.0;
		int labour = 0;

		List<ProjectReportEntity> sumRepot = projectReportService.getSumRepot(
				report.getModelArea(), year, month);
		InvestmentInfo invs = new InvestmentInfo();
		for (ProjectReportEntity pre : sumRepot) {
			InvestmentInfo inv = pre.getInvestment();
			invs.sum(inv);
			labour += pre.getLabourCount();
			spend = add(spend, pre.getSpend());

		}

		// for(int i1=1;i1<9;i1++){
		// totalfunds = add(totalfunds,sumRepot[i1]);
		// }

		content.append("<table id='table_content' class='line' style='width:1400px'>");
		if (0 != sumRepot.size()) {
			content.append("<tr class='tr-sum-cls'><td colspan='3'>")
					.append("累计完成月报情况").append("</td>");
			content.append("<td>").append(invs.getStateFunds()).append("</td>");
			content.append("<td>").append(invs.getSpecialFunds())
					.append("</td>");
			content.append("<td>").append(invs.getProvinceFunds())
					.append("</td>");
			content.append("<td>").append(invs.getCityFunds()).append("</td>");
			content.append("<td>").append(invs.getCountyFunds())
					.append("</td>");
			content.append("<td>").append(invs.getSocialFunds())
					.append("</td>");
			content.append("<td>").append(invs.getMassFunds()).append("</td>");
			content.append("<td>").append(invs.getOtherFunds()).append("</td>");
			content.append("<td>").append(invs.getTotalFunds()).append("</td>");
			content.append("<td>").append(labour).append("</td>");
			content.append("<td>").append(spend).append("</td>");
			content.append("<td>");
		} else {
			content.append("<tr><td colspan='3'>").append("累计完成月报情况")
					.append("</td>");
			for (int j = 0; j < 11; j++) {
				content.append("<td>——</td>");
			}

			content.append("<td>");
		}

		content.append("</td></tr>");
		InvestmentInfo invest = report.getInvestment();
		// ProjectReportEntity current =
		// projectReportService.getCurrent(report.getModelArea().getOwnerId(),
		// year, month);
		if (!editing) {
			content.append("<tr class='tr-sum-cls'><td colspan='3'>").append(
					"本月完成月报情况");
			content.append("<td>").append(invest.getStateFunds())
					.append("</td>");
			content.append("<td>").append(invest.getSpecialFunds())
					.append("</td>");
			content.append("<td>").append(invest.getProvinceFunds())
					.append("</td>");
			content.append("<td>").append(invest.getCityFunds())
					.append("</td>");
			content.append("<td>").append(invest.getCountyFunds())
					.append("</td>");
			content.append("<td>").append(invest.getSocialFunds())
					.append("</td>");
			content.append("<td>").append(invest.getMassFunds())
					.append("</td>");
			content.append("<td>").append(invest.getOtherFunds())
					.append("</td>");
			content.append("<td>").append(invest.getTotalFunds())
					.append("</td>");
			content.append("<td>").append(report.getLabourCount())
					.append("</td>");
			content.append("<td>").append(report.getSpend()).append("</td>");
			content.append("<td>");
			content.append("</td></tr>");
		}
		if (editing) {
			// :本月完成月报情况
			InvestmentInfo rivn = report.getInvestment();
			content.append("<tr ><td colspan='3'>")
					.append("<h1 style='color:red;'>本月完成月报情况</h1>")
					.append("</td>");
			content.append("<td><span style='color:red;' id='total_sta' class='rep-row-total'>"
					+ rivn.getStateFunds() + "</span></td>");
			content.append("<td><span style='color:red;' id='total_spc' class='rep-row-total'>"
					+ rivn.getSpecialFunds() + "</span></td>");
			content.append("<td><span style='color:red;' id='total_pro' class='rep-row-total'>"
					+ rivn.getProvinceFunds() + "</span></td>");
			content.append("<td><span style='color:red;' id='total_cit' class='rep-row-total'>"
					+ rivn.getCityFunds() + "</span></td>");
			content.append("<td><span style='color:red;' id='total_cou' class='rep-row-total'>"
					+ rivn.getCountyFunds() + "</span></td>");
			content.append("<td><span style='color:red;' id='total_soc' class='rep-row-total'>"
					+ rivn.getSocialFunds() + "</span></td>");
			content.append("<td><span style='color:red;' id='total_mas' class='rep-row-total'>"
					+ rivn.getMassFunds() + "</span></td>");
			content.append("<td><span style='color:red;' id='total_oth' class='rep-row-total'>"
					+ rivn.getOtherFunds() + "</span></td>");
			content.append("<td><span style='color:red;' id='total_tot' >"
					+ rivn.getTotalFunds() + "</span></td>");
			content.append("<td><span style='color:red;' id='total_lab'>"
					+ report.getLabourCount() + "</span></td>");
			content.append("<td><span style='color:red;' id='total_spe'>"
					+ report.getSpend() + "</span></td>");
			content.append("<td>");
		}

		// 点击某个月报的时候，列出的月报项按照 project.code排序
		List<ProjectReportItem> pjrpItems = projectReportService
				.allItems(report.getId());
		for (ProjectReportItem pre : /* report.getReports() */pjrpItems) {
			ProjectEntity pro = pre.getProject();
			ProjectStatusChangeEntity change = pre.getStatusChange();
			if (editing) {
				// 编辑状态只显示进行中的项目item
				if (pro.getStatus() != 1) {
					continue;
				}// 只有在正常状态下才可以上报月度报表（凡是项目状态不等于1的都不列出来）
			} else {
				// 非编辑状态就应该显示出所有的item
			}
			i++;
			InvestmentInfo inv = pre.getInvestment();
			content.append("<tr class='tr-cls'");
			if (editing) {
				content.append(" editable='true'");
			}
			content.append("><td style='width:40px' ordinal='").append(i)
					.append("'>").append(i);
			// content.append("</td><td onclick=\"jQuery(this).toggleClass('item-open').parent().next().slideToggle();\" class='item-close item-open' style='text-align:left;background-position-y:bottom;'>").append(pro.getName());
			content.append(
					"</td><td style='text-align:left;width:198px;word-break:break-all;over-flow:hidden;'><a href='project!")
					.append(pro.getId());
			content.append(
					".detail' onclick=\"openDialog(this);return false;\">")
					.append(pro.getName());
			content.append(
					"</a></td><td style='width:80px;word-break:break-all;over-flow:hidden;'>")
					.append(pro.getCode());
			int ps = pre.getProjectStatus();
			if (editing) {
				Long pid = pre.getId();
				content.append("<input class='pro-pid' type='hidden' value='")
						.append(pid).append("'/>");
				content.append(
						"</td><td style='width:80px;'><input type='text' tcls='sta'  class='fund bottom sta' value='")
						.append(inv.getStateFunds());
				content.append(
						"'/></td><td style='width:80px;'><input type='text' tcls='spc'  class='fund bottom spc' value='")
						.append(inv.getSpecialFunds());
				content.append(
						"'/></td><td style='width:80px;'><input type='text' tcls='pro'  class='fund bottom pro' value='")
						.append(inv.getProvinceFunds());
				content.append(
						"'/></td><td style='width:80px;'><input type='text' tcls='cit'  class='fund bottom cit' value='")
						.append(inv.getCityFunds());
				content.append(
						"'/></td><td style='width:80px;'><input type='text' tcls='cou'  class='fund bottom cou' value='")
						.append(inv.getCountyFunds());
				content.append(
						"'/></td><td style='width:80px;'><input type='text' tcls='soc'  class='fund bottom soc' value='")
						.append(inv.getSocialFunds());
				content.append(
						"'/></td><td style='width:80px;'><input type='text' tcls='mas'  class='fund bottom mas' value='")
						.append(inv.getMassFunds());
				content.append(
						"'/></td><td style='width:80px;'><input type='text' tcls='oth'  class='fund bottom oth' value='")
						.append(inv.getOtherFunds());
				content.append(
						"'/></td><td style='width:80px;'><input type='text' tcls='tot'  class='t-fund bottom tot' readonly='readonly' value='")
						.append(inv.getTotalFunds());
				content.append(
						"'/></td><td style='width:80px;'><input type='text' tcls='lab'  class='i-fund bottom lab' value='")
						.append(pre.getLabourCount());
				content.append(
						"'/></td><td style='width:80px;'><input type='text' tcls='spe'  class='i-fund bottom spe' value='")
						.append(pre.getSpend());
				// onchange的使用

				Integer back = report.getBack();
				if (null == back || back.intValue() != 1) {
					// content.append("' precision='4'/></td><td style='width:102px;'><select name='fi-").append(pid).append("' onchange=\"if(jQuery(this).val()==2){jQuery(this).next().show();}else{jQuery(this).next().hide();}\"><option value='0'");
					content.append("' precision='4'/></td><td style='width:102px;'><select class='pro-status'  onchange=\"if(jQuery(this).val()==2){jQuery(this).next().show();}else{jQuery(this).next().hide();}\"><option value='0'");
					if (ps == 0) {
						content.append(" selected='selected'");
					}
					content.append(">进行中</option><option value='1'");
					if (ps == 1) {
						content.append(" selected='selected'");
					}
					content.append(">竣工</option><option value='2'");
					if (ps == 2) {
						content.append(" selected='selected' onclick='jQuery('#cleaners')'");
					}
					content.append(">终止</option>");
					if (ps == 0 || ps == 1) {
						content.append(
								"</select><input class='pro-reason' placeholder='原因' title='原因' type='text' style='display:none'name='er-")
								.append(pid)
								.append("' class='reason bottom' value='")
								.append("'/>");
					} else {
						content.append(
								"</select><input class='pro-reason' placeholder='原因' title='原因' type='text' name='er-")
								.append(pid)
								.append("' class='reason bottom' value='")
								.append(pre.getExitReason()).append("'/>");
					}
				} else {
					// 省办 退回月报的情况 退回月报后县级市不能够修改项目的状态：
					content.append("'/></td><td style='width:102px'>");
					content.append(
							"<input type='hidden' class='pro-status' value='")
							.append(ps).append("'/>");
					if (pre.getExitReason() == null) {
						content.append(
								"<input type='hidden' class='pro-reason' placeholder='原因' title='原因' type='text' name='er-")
								.append(pid)
								.append("' class='reason bottom' value='")
								.append("'/>");
					} else {
						content.append(
								"<input type='hidden' class='pro-reason' placeholder='原因' title='原因' type='text' name='er-")
								.append(pid)
								.append("' class='reason bottom' value='")
								.append(pre.getExitReason()).append("'/>");
					}
					switch (ps) {
					case 1:
						if (change != null) {
							// 加删除线
							content.append(
									"<div id='status_tip'  class='buptip' title='")
									.append(change.getCreatorName())
									.append("在")
									.append(sdf.format(change.getCreateAt()))
									.append("由状态[");
							content.append(textStatus(change.getPrestatus()))
									.append("]改为[")
									.append(textStatus(change.getStatus()))
									.append("]' style='text-decoration:line-through ;color:#808000;'>竣工</div>");
						} else {
							content.append("竣工");
						}
						break;
					case 2:
						if (change != null) {
							content.append(
									"<div id='status_tip'  class='buptip' title='")
									.append(change.getCreatorName())
									.append("在")
									.append(sdf.format(change.getCreateAt()))
									.append("由状态[");
							content.append(textStatus(change.getPrestatus()))
									.append("]改为[")
									.append(textStatus(change.getStatus()))
									.append("]' style='text-decoration:line-through ;color:#808000;'>终止</div>");
						} else {
							content.append("终止");
						}
						break;
					default:
						content.append("进行中");
						break;
					}
				}

				// content.append("</select><input type='hidden' name='reportIds' value='").append(pid);
				// if(ps==2){
				// content.append("'/><input type='text' name='er-").append(pid).append("' class='reason bottom' value='").append(pre.getExitReason());
				// }
				// else{
				// content.append("'/><input type='text' style='display:none' name='er-").append(pid).append("' class='reason bottom' value='").append(pre.getExitReason());
				// }
				// if(ps == 2){
				// content.append("'/></td><td style='width:120px;'><input type='text' name='er-").append(pid).append("' class='reason bottom' value='").append(pre.getExitReason());
				// }
				// else{
				// content.append("'/></td><td style='width:120px;'><input style='display:none' type='text' name='er-").append(pid).append("' class='reason bottom' readonly='readonly' value='").append(pre.getExitReason());
				// }
				// content.append("'/>");
			} else {
				/* 非editing得情况： */
				content.append("</td><td style='width:80px'>").append(
						inv.getStateFunds());
				content.append("</td><td style='width:80px'>").append(
						inv.getSpecialFunds());
				content.append("</td><td style='width:80px'>").append(
						inv.getProvinceFunds());

				// content.append("</td><td style='width:80px'>").append(inv.getLocalFunds());

				content.append("</td><td style='width:80px'>").append(
						inv.getCityFunds());
				content.append("</td><td style='width:80px'>").append(
						inv.getCountyFunds());

				content.append("</td><td style='width:80px'>").append(
						inv.getSocialFunds());
				content.append("</td><td style='width:80px'>").append(
						inv.getMassFunds());
				content.append("</td><td style='width:80px'>").append(
						inv.getOtherFunds());
				content.append("</td><td style='width:80px'>").append(
						inv.getTotalFunds());

				content.append("</td><td style='width:80px'>").append(
						pre.getLabourCount());
				content.append("</td><td style='width:80px'>").append(
						pre.getSpend());

				content.append("</td><td style='width:102px'>");
				switch (ps) {
				case 1:
					if (change != null) {
						// 加删除线
						content.append(
								"<div id='status_tip'  class='buptip' title='")
								.append(change.getCreatorName()).append("在")
								.append(sdf.format(change.getCreateAt()))
								.append("由状态[");
						content.append(textStatus(change.getPrestatus()))
								.append("]改为[")
								.append(textStatus(change.getStatus()))
								.append("]' style='text-decoration:line-through ;color:#808000;'>竣工</div>");
					} else {
						content.append("<div>竣工</div>");
					}
					break;
				case 2:
					if (change != null) {
						content.append(
								"<div id='status_tip'  class='buptip' title='")
								.append(change.getCreatorName()).append("在")
								.append(sdf.format(change.getCreateAt()))
								.append("由状态[");
						content.append(textStatus(change.getPrestatus()))
								.append("]改为[")
								.append(textStatus(change.getStatus()))
								.append("]' style='text-decoration:line-through ;color:#808000;'>终止</div>");
					} else {
						content.append("<div>终止</div>");
					}
					break;
				default:
					if (change != null) {
						content.append(
								"<div id='status_tip'  class='buptip' title='")
								.append(change.getCreatorName()).append("在")
								.append(sdf.format(change.getCreateAt()))
								.append("由状态[");
						content.append(textStatus(change.getPrestatus()))
								.append("]改为[")
								.append(textStatus(change.getStatus()))
								.append("]' style='text-decoration:line-through ;'>进行中</div>");
					} else {
						content.append("<div>进行中</div>");
					}
					break;
				}
				// 终止项目的原因
				if (null != pre.getExitReason()
						&& !pre.getExitReason().isEmpty()) {
					content.append(pre.getExitReason());
				}
			}
			content.append("</td></tr>");
		}
		content.append("</table>");
		if (report.getReports().size() > 10) {
			model.put("searchable", true);
		}
		model.put("editable", editing);
	}

	public String textStatus(int status) {
		switch (status) {
		case 0:
			return "未提交";
		case 1:
			return "进行中";
		case 2:
			return "竣工";
		case 3:
			return "终止";
		default:
			return "";
		}
	}

	/**
	 * 点击某个月份，开始上报月报
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @param year
	 * @param month
	 * @return
	 */
	@RequestMapping(value = "!{key}/report!{year}!{month}.start")
	public String start(HttpServletRequest request, Model model, Long id,
			@PathVariable("year") int year, @PathVariable("month") int month) {
		StringBuilder content = new StringBuilder();
		try {
			LogonUser user = getUser();
			ProjectReportEntity report = null;
			if (null != id) {
				report = projectReportService.report(user, id);
			}
			int ed = year * 100 + month;
			if (null == report) {
				Calendar cal = Calendar.getInstance();
				int cyear = cal.get(Calendar.YEAR);
				int cmonth = cal.get(Calendar.MONTH);

				int nd = cyear * 100 + cmonth;
				if (ed > nd) {
					throw new Exception("只允许上报本月及之前的项目月度汇总表！");
				}
				report = projectReportService.start(user, year, month);
			}

			model.addAttribute("currentMonthly", ed);
			model.addAttribute("selectedYear", year);
			model.addAttribute("lastMonth", month);
			model.addAttribute("showMonth", month + 1);

			if (null == report) {
				model.addAttribute("editable", false);
				content.append(
						"<div style='margin-top:100px;text-align:center'><table id='table_content' class='line' style='width:1400px'><tr><td class='reportMessage' colspan='11'>")
						.append(year).append("年").append(month + 1)
						.append("月暂时没有任何项目需要上报月度报表！</td></tr></table></div>");
			} else {
				__parseContent(model.asMap(), content, report);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("editable", false);
			content.append("<tr><td class='reportMessage' colspan='11'>")
					.append(ex.getMessage()).append("</td></tr>");
		}
		model.addAttribute("reports", content);
		return "project/reporting-item";
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/report.save", method = { POST, GET })
	// public JsonResult save(final HttpServletRequest request, Long rid, String
	// toSubmit){
	public JsonResult save(final HttpServletRequest request,
			String report_data, Long rid, String toSubmit) {
		try {
			ObjectMapper om = new ObjectMapper();

			ProjectReportSaveInfo pr = om.readValue(report_data,
					ProjectReportSaveInfo.class);
			if (null == pr.getRid()) {
				throw new Exception("没有需要保存的项目月度报表");
			}

			projectReportService.saveReport(pr,
					"true".equals(pr.getToSubmit()), getUser());
			// projectReportService.save(getUser(), pr.getRid(), new
			// ParamenterValue(){
			//
			// @Override
			// public double getDouble(Long id, String pre) {
			// String val = request.getParameter(pre + id);
			// return StringHelper.todouble(val);
			// }
			//
			// @Override
			// public int getInt(Long id, String pre) {
			// return StringHelper.toint(request.getParameter(pre + id));
			// }
			//
			// @Override
			// public boolean getboolean(Long id, String pre) {
			// return StringHelper.toboolean(request.getParameter(pre + id));
			// }
			//
			// @Override
			// public String getString(Long id, String pre) {
			// return request.getParameter(pre + id);
			// }
			//
			// @Override
			// public Date getDate(Long id, String pre) {
			// return StringHelper.toDate(request.getParameter(pre + id));
			// }
			//
			// @Override
			// public String[] getStrings(Long id, String pre) {
			// return null;
			// }
			//
			// }, "true".equals(pr.getToSubmit()));
			return JsonResult.result();
		} catch (Exception ex) {
			ex.printStackTrace();
			return JsonResult.error(ex);
		}
	}
	//
	// @RequestMapping(value = "!{key}/load.do", method = GET)
	// public String load(HttpServletRequest request, Model model, Integer year,
	// Integer month, Long newRural) throws ParseException{
	// y = year;
	// m = month;
	// //System.out.println(request.getParameter("newRural"));
	// projects = projectService.getProject(newRural);
	// reports = projectReportService.getReport(year, month);
	// StringBuilder sb = new StringBuilder();
	// sb.append("<table id='tb_project' style='overflow:auto;letter-spacing:3px;font-size:14px;border-collapse:collapse;text-align:center;border:solid 1px #000000'>"
	// +
	// "<tr>" +
	// "<td style='border:solid 1px #000000;width:230px'>项目名</td>" +
	// "<td style='border:solid 1px #000000;width:100px'>村名</td>" +
	// "<td style='border:solid 1px #000000;width:80px'>国家投入</td>" +
	// "<td style='border:solid 1px #000000;width:80px'>省级投入</td>" +
	// "<td style='border:solid 1px #000000;width:80px'>当地投入</td>" +
	// "<td style='border:solid 1px #000000;width:80px'>社会资金</td>" +
	// "<td style='border:solid 1px #000000;width:80px'>其它资金</td>" +
	// "</tr>");
	//
	// if (null != projects && projects.size() != 0) {
	// for (ProjectEntity project : projects) {
	// //显示正常状态的项目
	// if(project.getStatus() == 1) {
	// sb.append("<tr>");
	// sb.append("<td style='border:solid 1px #000000;width:230px'><input class='project_id' type='hidden' value='").append(project.getId());
	// sb.append("'/><a class='projectDetails' href='showProjectDetails.do?projectId="+
	// project.getId() +"'>").append(project.getName()).append("</a></td>");
	// sb.append("<td style='border:solid 1px #000000;width:100px'><a class='ruralDetails' href='showRuralDetails.do?ruralId="+
	// project.getRural().getId()
	// +"'>").append(project.getRural().getName()).append("</a></td>");
	// boolean flag = false;
	// for (ProjectReportEntity report: reports) {
	// if (project.getId() == report.getProject().getId()) {
	// InvestmentInfo investment = report.getInvestment();
	// System.out.println(report.getId());
	// sb.append("<td style='border:solid 1px #000000;width:70px'><input class='report_id' type='hidden' value='"
	// + report.getId()
	// +"'/><input type='text' style='width:70px' class='stateFunds' value='" +
	// investment.getStateFunds() +"' /></td>" +
	// "<td style='border:solid 1px #000000;width:70px'><input type='text' style='width:70px' class='provinceFunds' value='"
	// + investment.getProvinceFunds() +"' /></td>" +
	// "<td style='border:solid 1px #000000;width:70px''><input type='text' style='width:70px' class='localFunds' value='"
	// + investment.getLocalFunds() +"' /></td>" +
	// "<td style='border:solid 1px #000000;width:70px'><input type='text' style='width:70px' class='socialFunds' value='"
	// + investment.getSocialFunds() +"' /></td>" +
	// "<td style='border:solid 1px #000000;width:70px'><input type='text' style='width:70px' class='otherFunds' value='"
	// + investment.getOtherFunds() +"' /></td>" +
	// "</tr>");
	// flag = true;
	// break;
	// }
	// }
	// if (!flag) {
	// sb.append("" +
	// "<td style='border:solid 1px #000000;width:70px'><input style='width:70px;' type='text' class='stateFunds' value='0.00' /></td>"
	// +
	// "<input class='report_id' type='hidden' value='0'/>" +
	// "<td style='border:solid 1px #000000;width:70px'><input type='text' style='width:70px' class='provinceFunds' value='0.00' /></td>"
	// +
	// "<td style='border:solid 1px #000000;width:70px'><input type='text' style='width:70px' class='localFunds' value='0.00' /></td>"
	// +
	// "<td style='border:solid 1px #000000;width:70px'><input type='text' style='width:70px' class='socialFunds' value='0.00' /></td>"
	// +
	// "<td style='border:solid 1px #000000;width:70px'><input type='text' style='width:70px' class='otherFunds' value='0.00' /></td>"
	// +
	// "</tr>");
	// }
	// }
	// }
	// }
	// sb.append("<tr><td colspan='8' style='border:solid 1px #000000'><button type='button' id='submit'>提交</button></td></tr></table>");
	// model.addAttribute("reports", sb);
	// return "project/reporting-item";
	// }
	//
	// //查看村详细信息
	// @RequestMapping(value = "!{key}/showRuralDetails.do", method = GET)
	// public ModelAndView showRuralDetails(HttpServletRequest request, Long
	// ruralId) {
	// DialogModel model = new DialogModel(request);
	//
	// NewRuralEntity n = newRuralService.get(ruralId);
	// StringBuilder sb = new StringBuilder();
	// sb.append("<table id='tb_project' style='overflow:auto;letter-spacing:2px;font-size:14px;border-collapse:collapse;text-align:center;width:99%;height:99%;border:solid 1px #000000'>"
	// +
	// "<tr><td style='width:10%;border:solid 1px #000000;background-color:#F0FFEF'>村名：</td><td style='border:solid 1px #000000'>"+
	// n.getName()+"</td></tr>" +
	// "<tr><td style='width:10%;border:solid 1px #000000;background-color:#F0FFEF'>介绍：</td><td style='border:solid 1px #000000'></td></tr>"
	// +
	// "<tr><td style='width:20%;border:solid 1px #000000;background-color:#F0FFEF'>创建人：</td><td style='border:solid 1px #000000'>"+
	// n.getCreatorName() +"</td></tr>" +
	// "<tr><td style='width:10%;border:solid 1px #000000;background-color:#F0FFEF'>创建时间：</td><td style='border:solid 1px #000000'>"+
	// n.getCreateAt() +"</td></tr>" +
	// "<tr><td style='width:20%;border:solid 1px #000000;background-color:#F0FFEF'>更新人：</td><td style='border:solid 1px #000000'>"+
	// n.getUpdaterName() +"</td></tr>" +
	// "<tr><td style='width:20%;border:solid 1px #000000;background-color:#F0FFEF'>更新时间：</td><td style='border:solid 1px #000000'>"+
	// n.getUpdateAt() +"</td></tr>" +
	// "<tr><td style='width:20%;border:solid 1px #000000;background-color:#F0FFEF'>人口：</td><td style='border:solid 1px #000000'>"+
	// n.getPopulation() +"</td></tr>" +
	// "<tr><td style='width:20%;border:solid 1px #000000;background-color:#F0FFEF'>所属地区：：</td><td style='border:solid 1px #000000'>"+
	// n.getModelArea().getPlace().getName() +"</td></tr>" +
	// "<tr><td style='width:20%;border:solid 1px #000000;background-color:#F0FFEF'>备注：</td><td style='border:solid 1px #000000'>"+
	// n.getRemark() +"</td></tr>" +
	// "</table>");
	// model.addObject("ruralDetails", sb);
	// return model.execute("project/ruralDetails");
	// }
	//
	// //查看项目详细信息
	// @RequestMapping(value = "!{key}/showProjectDetails.do", method = GET)
	// public ModelAndView showProjectDetails(HttpServletRequest request, Long
	// projectId) {
	// DialogModel model = new DialogModel(request);
	// ProjectEntity p = projectService.get(projectId);
	// List<ProjectReportEntity> r =
	// projectReportService.find(" and x.project.id=" + projectId);
	// StringBuilder sb = new StringBuilder();
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//
	// model.addObject("p_modelarea", p.getRural().getModelArea().getName());
	// model.addObject("p_rural_name", p.getRural().getName());
	// model.addObject("p_id", p.getId());
	// // model.addObject("p_build_pos", p.getBuildPosition());
	// // model.addObject("p_name", p.getName());
	// // model.addObject("p_type", p.getType().getName());
	// // model.addObject("p_build_unit", p.getBuildUnit());
	// // model.addObject("p_build_unit_leader", p.getBuildUnitLeader());
	// // model.addObject("p_leader_tel", p.getLeaderTel());
	// // model.addObject("p_construction_unit", p.getConstructionUnit());
	// // model.addObject("p_locale_principal", p.getLocalePrincipal());
	// // model.addObject("p_principal_tel", p.getPrincipalTel());
	// // model.addObject("p_land_area", p.getLandArea());
	// // model.addObject("p_construction_scale", p.getConstructionScale());
	// // model.addObject("p_plan_start_working_at",
	// sdf.format(p.getPlanStartWorkingAt()));
	// // model.addObject("p_plan_completed_working_at",
	// sdf.format(p.getPlanCompletedWorkingAt()));
	// // model.addObject("p_benefit_units", p.getBenefitUnits());
	// // model.addObject("p_current_problem", p.getCurrentProblem());
	// // model.addObject("p_construction_content", p.getConstructionContent());
	// // model.addObject("p_real_start_working_at",
	// sdf.format(p.getRealStartWorkingAt()));
	// // model.addObject("p_real_scale", p.getRealScale());
	// // model.addObject("p_construction_status", p.getConstructionStatus());
	// // model.addObject("p_real_completed_working_at",
	// sdf.format(p.getRealCompletedWorkingAt()));
	// // model.addObject("p_check_at", sdf.format(p.getCheckAt()));
	// model.addObject("p_total_funds", p.getInvestment().getTotalFunds());
	// model.addObject("p_state_funds", p.getInvestment().getStateFunds());
	// model.addObject("p_province_funds",
	// p.getInvestment().getProvinceFunds());
	// model.addObject("p_local_funds", p.getInvestment().getLocalFunds());
	// model.addObject("p_social_funds", p.getInvestment().getSocialFunds());
	// model.addObject("p_other_funds", p.getInvestment().getOtherFunds());
	// model.addObject("p_createor", p.getCreatorName());
	// model.addObject("p_create_at", sdf.format(p.getCreateAt()));
	// // model.addObject("p_autitor", p.getAuditor());
	// // model.addObject("p_audit_at", sdf.format(p.getAuditAt()));
	//
	// for(ProjectReportEntity r_ : r) {
	// double sum = r_.getInvestment().getStateFunds() +
	// r_.getInvestment().getProvinceFunds() +
	// r_.getInvestment().getLocalFunds() + r_.getInvestment().getSocialFunds()
	// + r_.getInvestment().getOtherFunds();
	// sb.append("<tr colspan='12' width='780'><td align='center' width='138' colspan='1' valign='top'><p>"+
	// r_.getAnnual() +"-"+ r_.getPeriod() +"</p></td>" +
	// "<td align='center' width='138' colspan='1' valign='top'><p>"+ sum
	// +"</p></td>" +
	// "<td align='center' width='138' colspan='1' valign='top'><p>"+
	// r_.getInvestment().getStateFunds() +"</p></td>" +
	// "<td align='center' width='138' colspan='1' valign='top'><p>"+
	// r_.getInvestment().getProvinceFunds() +"</p></td>" +
	// "<td align='center' width='138' colspan='1' valign='top'><p>"+
	// r_.getInvestment().getLocalFunds() +"</p></td>" +
	// "<td align='center' width='138' colspan='1' valign='top'><p>"+
	// r_.getInvestment().getSocialFunds() +"</p></td>" +
	// "<td align='center' width='138' colspan='6' valign='top'><p>"+
	// r_.getInvestment().getOtherFunds() +"</p></td></tr>" +
	// "");
	// }
	// model.addObject("project_reports", sb);
	// System.out.println(r.size());
	// return model.execute("project/projectDetails");
	// }
	//
	// //保存修改的投入资金
	// @ResponseBody
	// @RequestMapping(value = "!{key}/newInvestment", method =
	// RequestMethod.POST)
	// public JsonResult newInvestment(String pids, String ids, String
	// stateFunds, String provinceFunds, String localFunds, String socialFunds,
	// String otherFunds) throws ParseException {
	// String ym = String.valueOf(y) + "-" + String.valueOf(m);
	// DateFormat formatYM = new SimpleDateFormat("yyyy-MM");
	// Date reportDate = formatYM.parse(ym);
	// Calendar x = Calendar.getInstance();
	// x.setTime(reportDate);
	// //Q:如果项目开始、结束时间在同一个月没有判断
	// for (ProjectEntity project : projects) {
	// Calendar ysCal = Calendar.getInstance();
	// Calendar yeCal = Calendar.getInstance();
	// //Q:填报时间是按实际还是计划
	// //ysCal.setTime(project.getRealStartWorkingAt());
	// //yeCal.setTime(project.getRealCompletedWorkingAt());
	//
	// //设置开始日期为第一日
	// int s_month = ysCal.getActualMinimum(Calendar.DAY_OF_MONTH);
	// //设置结束日期为最后一日
	// int e_month = yeCal.getActualMaximum(Calendar.DAY_OF_MONTH);
	// ysCal.set(Calendar.DATE, s_month);
	// yeCal.set(Calendar.DATE, e_month);
	// if (x.after(yeCal) || x.before(ysCal)) {
	// return JsonResult.error("填报日期要在项目开始与结束日期之间！");
	// }
	// }
	//
	// try {
	// Long[] arr_pids = StringHelper.toLongArray(pids);
	// Long[] arr_ids = StringHelper.toLongArray(ids);
	// Double[] arry_stateFunds = StringHelper.toDoubleArray(stateFunds);
	// Double[] arry_provinceFunds = StringHelper.toDoubleArray(provinceFunds);
	// Double[] arry_localFunds = StringHelper.toDoubleArray(localFunds);
	// Double[] arry_socialFunds = StringHelper.toDoubleArray(socialFunds);
	// Double[] arry_otherFunds = StringHelper.toDoubleArray(otherFunds);
	// projectReportService.newInvestments(y, m, arr_pids, arr_ids,
	// arry_stateFunds, arry_provinceFunds, arry_localFunds, arry_socialFunds,
	// arry_otherFunds);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return JsonResult.error(e);
	// }
	// return JsonResult.result();
	// }
	//
	// //加载村名下拉列表
	// @ResponseBody
	// @RequestMapping(value = "newRural", method = RequestMethod.POST)
	// public List<Item> levelNames(Long areaId) {
	// List<Item> listN = new ArrayList<Item>();
	// List<NewRuralEntity> newRurals = (null == areaId) ?
	// newRuralService.find() : newRuralService.find(" and x.modelArea.id="+
	// areaId +"");
	// //List<NewRuralEntity> newRurals = newRuralService.find(" and x.ownerId="
	// + getUser().getOwnerId());
	//
	// if (null != newRurals && newRurals.size() != 0) {
	// for (int i = 0; i < newRurals.size(); i++) {
	// NewRuralEntity e = newRurals.get(i);
	// listN.add(new Item(e.getId(), e.getName()));
	// }
	// }
	// return listN;
	// }

}
