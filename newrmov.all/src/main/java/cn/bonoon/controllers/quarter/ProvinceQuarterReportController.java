package cn.bonoon.controllers.quarter;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.core.CityQuarterReportService;
import cn.bonoon.core.LocalQuarterReportService;
import cn.bonoon.core.StatisticsScheduleService;
import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaQuarterBatch;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;
import cn.bonoon.util.MonthAndQuarterUtil;

@Controller
@RequestMapping("s/pls/province/sms")
public class ProvinceQuarterReportController extends AbstractPanelController {

	@Autowired
	protected CityQuarterReportService cityQuarterReportService;
	@Autowired
	private StatisticsScheduleService statisticsScheduleService;
	@Autowired
	private LocalQuarterReportService localQuarterReportService;

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		Long ownerId = event.getUser().getOwnerId();
		event.setVmPath("statistics/area-schedule-province");
		event.getModel().addObject("ownerId", ownerId);

		// 年度下拉
		PanelModel model = event.getModel();
		StringBuilder selyear = new StringBuilder();
		selyear.append("<option value=''>请选择</option>");
		List<int[]> periodlist = new ArrayList<int[]>();
		List<Object[]> annualPeriodList = statisticsScheduleService.periodSet();
		if (annualPeriodList == null || annualPeriodList.size() == 0)
			throw new Exception("出现没有季度异常！");
		for (Object[] periodOList : annualPeriodList) {
			int[] pArray = new int[] { (int) periodOList[0],
					(int) periodOList[1] };
			periodlist.add(pArray);
		}

		Set<Integer> yearSet = new TreeSet<Integer>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		});
		for (int o[] : periodlist) {
			yearSet.add((Integer) o[0]);
		}

		for (Integer year : yearSet) {
			selyear.append("<option value='").append(year).append("'>")
					.append(year).append("</option>");
		}
		model.addObject("selyear", selyear);
	}

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

	/**
	 * 统计
	 * 
	 * @param model
	 * @param batchName
	 * @param ownerId
	 * @param period
	 * @param annual
	 * @param name
	 * @param time
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "!{mid}/provincequarter.statistics", method = RequestMethod.POST)
	public String statistics(Model model, int batchName, Long ownerId,
			int period, int annual, String name, String time) throws Exception {
		String bName = BatchHelper.getValue(batchName);
		StringBuilder batchName1 = new StringBuilder();
		batchName1.append(bName);
		model.addAttribute("batchName1", bName);
		StringBuilder deadline = new StringBuilder();
		StringBuilder jsContent = new StringBuilder();

		List<ModelAreaQuarterItem> items = cityQuarterReportService
				.getQuarterReport(bName, period, annual);

		ModelAreaQuarterBatch batch = cityQuarterReportService.getBatch(bName,
				annual, period);
		model.addAttribute("items", items);
		model.addAttribute("batch", batch);

		if (null != batch && items != null) {
			localQuarterReportService.show9ParamByNoStart(items);
			cityQuarterReportService.updateItem4ParamToLast(items);
			batch.sum();
			deadline.append(sdf.format(batch.getQuarter().getDeadline()));
			model.addAttribute("deadline", deadline);
			setViewShowWhat(jsContent, batch, items);
			model.addAttribute("jsContent", jsContent);
		} else {
			int nowPeriod = period;
			int noReport = 0;
			List<Object[]> annualPeriodList = statisticsScheduleService
					.periodSet();
			// 得到最久远的那个季度的年，月
			Object[] oldYPObjArray = annualPeriodList.get(annualPeriodList
					.size() - 1);
			int[] oldYP = new int[] { (int) oldYPObjArray[0],
					(int) oldYPObjArray[1] };
			while (items == null || batch == null) {
				items = null;
				batch = null;
				int[] lastYM = MonthAndQuarterUtil.getLastQuarter(annual,
						nowPeriod);
				annual = lastYM[0];
				nowPeriod = lastYM[1];
				items = cityQuarterReportService.getQuarterReport(bName,
						lastYM[1], lastYM[0]);
				batch = cityQuarterReportService.getBatch(bName, lastYM[0],
						lastYM[1]);
				if (batch != null && items != null)
					break;
				// 如果等于省季报季度最久远的那个则没有季报了
				if (lastYM[0] == oldYP[0] && lastYM[1] == oldYP[1]) {
					noReport = 1;
					model.addAttribute("error", "没有查到自" + annual + "年" + period
							+ "季度以后的季度工程进展！");
				}
			}

			if (noReport != 1) {
				localQuarterReportService.show9ParamByNoStart(items);
				cityQuarterReportService.updateItem4ParamToLast(items);
				// isLast 0表示当前查询前季度成功
				model.addAttribute("isLast", 0);
				model.addAttribute("msg", "没有查询到" + annual + "年" + (period + 1)
						+ "季度的工程进展统计情况 " + "现在查询到的是"
						+ batch.getQuarter().getAnnual() + "年"
						+ (batch.getQuarter().getPeriod() + 1) + "季度工程进展的统计情况!");
				batch.sum();
				deadline.append(sdf.format(batch.getQuarter().getDeadline()));
				model.addAttribute("deadline", deadline);
				setViewShowWhat(jsContent, batch, items);
				model.addAttribute("jsContent", jsContent);
			}
		}
		if (batch != null && items != null) {
			model.addAttribute("nowQuaryPeriod", batch.getQuarter().getAnnual()
					+ "年" + (batch.getQuarter().getPeriod() + 1) + "季度");
			model.addAttribute(
					"excel",
					"<a class=\"bupabutton\" style='padding-left:10px' href=\"#\" data-settings=\"iconClassname:'icon-excel'\" onclick=\"return excelTest_()\" target=\"_self\">导出Excel</a><script type='text/javascript'>"
							+ "function excelTest_(){"
							+ "location.href='/s/pls/province/sms/index.excel?batchName="
							+ batchName
							+ "&ownerId="
							+ ownerId
							+ "&period="
							+ batch.getQuarter().getPeriod()
							+ "&annual="
							+ batch.getQuarter().getAnnual()
							+ "'"
							+ "}"
							+ "</script>");
		}

		return "statistics/area-schedule-province-content";
	}

	private void setViewShowWhat(StringBuilder jsContent,
			ModelAreaQuarterBatch batch, List<ModelAreaQuarterItem> items) {
		jsContent.append("<tr height='20' style='text-align:center;' ><td>第")
				.append(batch.getBatchName()).append("批次</td>");
		jsContent.append("<td>").append(batch.getArCount()).append("</td>");
		jsContent.append("<td>").append(batch.getNrCount()).append("</td>");
		jsContent.append("<td>").append(batch.getHouseholdCount())
				.append("</td>");
		jsContent.append("<td>").append(batch.getPopulationCount())
				.append("</td>");
		jsContent.append("<td>").append(batch.getArFinishPlan())
				.append("</td>");
		jsContent.append("<td>").append(batch.getNeedFinish().getNeedFinish1())
				.append("</td>");
		jsContent.append("<td>").append(batch.getNeedFinish().getNeedFinish2())
				.append("</td>");
		jsContent.append("<td>").append(batch.getNeedFinish().getNeedFinish3())
				.append("</td>");
		jsContent.append("<td>").append(batch.getNeedFinish().getNeedFinish4())
				.append("</td>");
		jsContent.append("<td>").append(batch.getNeedFinish().getNeedFinish5())
				.append("</td>");
		jsContent.append("<td>").append(batch.getNeedFinish().getNeedFinish6())
				.append("</td>");
		jsContent.append("<td>").append(batch.getNeedFinish().getNeedFinish7())
				.append("</td>");
		jsContent.append("<td>").append(batch.getNeedFinish().getNeedFinish8())
				.append("</td>");
		if (null != batch.getStartProject()) {
			jsContent.append("<td>").append(batch.getStartProject())
					.append("</td>");
		} else {
			jsContent.append("<td>0</td>");
		}
		if (null != batch.getFinishProject()) {
			jsContent.append("<td>").append(batch.getFinishProject())
					.append("</td>");
		} else {
			jsContent.append("<td>0</td>");
		}

		jsContent.append("<td>").append(batch.getInvestment().getTotalFunds())
				.append("</td>");
		jsContent.append("<td>").append(batch.getInvestment().getStateFunds())
				.append("</td>");
		jsContent.append("<td>")
				.append(batch.getInvestment().getSpecialFunds())
				.append("</td>");
		jsContent.append("<td>")
				.append(batch.getInvestment().getProvinceFunds())
				.append("</td>");
		jsContent.append("<td>").append(batch.getInvestment().getCityFunds())
				.append("</td>");
		jsContent.append("<td>").append(batch.getInvestment().getCountyFunds())
				.append("</td>");
		jsContent.append("<td>").append(batch.getInvestment().getSocialFunds())
				.append("</td>");
		jsContent.append("<td>").append(batch.getInvestment().getMassFunds())
				.append("</td>");
		jsContent.append("<td>").append(batch.getInvestment().getOtherFunds())
				.append("</td>");
		jsContent.append("<td>").append(batch.getNeedFinish().getNeedFinish9())
				.append("</td>");

		jsContent.append("</tr>");

		for (ModelAreaQuarterItem item : items) {
			InvestmentInfo inves = item.getInvestment();
			inves.sumSelf();

			jsContent.append("<tr style='text-align:center;'><td>")
					.append(item.getModelArea().getName()).append("</td>");
			jsContent.append("<td>").append(item.getArCount()).append("</td>");
			jsContent.append("<td>").append(item.getNrCount()).append("</td>");
			jsContent.append("<td>").append(item.getHouseholdCount())
					.append("</td>");
			jsContent.append("<td>").append(item.getPopulationCount())
					.append("</td>");
			jsContent.append("<td>").append(item.getArFinishPlan())
					.append("</td>");
			jsContent.append("<td>")
					.append(item.getNeedFinish().getNeedFinish1())
					.append("</td>");
			jsContent.append("<td>")
					.append(item.getNeedFinish().getNeedFinish2())
					.append("</td>");
			jsContent.append("<td>")
					.append(item.getNeedFinish().getNeedFinish3())
					.append("</td>");
			jsContent.append("<td>")
					.append(item.getNeedFinish().getNeedFinish4())
					.append("</td>");
			jsContent.append("<td>")
					.append(item.getNeedFinish().getNeedFinish5())
					.append("</td>");
			jsContent.append("<td>")
					.append(item.getNeedFinish().getNeedFinish6())
					.append("</td>");
			jsContent.append("<td>")
					.append(item.getNeedFinish().getNeedFinish7())
					.append("</td>");
			jsContent.append("<td>")
					.append(item.getNeedFinish().getNeedFinish8())
					.append("</td>");
			if (null != item.getStartProject()) {
				jsContent.append("<td>").append(item.getStartProject())
						.append("</td>");
			} else {
				jsContent.append("<td>0</td>");
			}
			if (null != item.getFinishProject()) {
				jsContent.append("<td>").append(item.getFinishProject())
						.append("</td>");
			} else {
				jsContent.append("<td>0</td>");
			}

			jsContent.append("<td>").append(inves.getTotalFunds())
					.append("</td>");
			jsContent.append("<td>").append(inves.getStateFunds())
					.append("</td>");
			jsContent.append("<td>").append(inves.getSpecialFunds())
					.append("</td>");
			jsContent.append("<td>").append(inves.getProvinceFunds())
					.append("</td>");
			jsContent.append("<td>").append(inves.getCityFunds())
					.append("</td>");
			jsContent.append("<td>").append(inves.getCountyFunds())
					.append("</td>");
			jsContent.append("<td>").append(inves.getSocialFunds())
					.append("</td>");
			jsContent.append("<td>").append(inves.getMassFunds())
					.append("</td>");
			jsContent.append("<td>").append(inves.getOtherFunds())
					.append("</td>");
			jsContent.append("<td>")
					.append(item.getNeedFinish().getNeedFinish9())
					.append("</td>");
			jsContent.append("</tr>");
		}
	}

	/**
	 * 导出
	 * 
	 * @param request
	 * @param response
	 * @param batchName
	 * @param ownerId
	 * @param period
	 * @param annual
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "index.excel", method = GET)
	public View excel(HttpServletRequest request, HttpServletResponse response,
			int batchName, Long ownerId, int period, int annual)
			throws Exception {
		// batchName = StringHelper.get(batchName, "一");
		// 获取统计截止时间
		String bName = BatchHelper.getValue(batchName);
		List<ModelAreaQuarterItem> items = cityQuarterReportService
				.getQuarterReport(bName, period, annual);
		ModelAreaQuarterBatch batch = cityQuarterReportService.getBatch(bName,
				annual, period);

		localQuarterReportService.show9ParamByNoStart(items);

		return new ProvinceQuarterReportStatExcelView(batch, items);
	}

}
