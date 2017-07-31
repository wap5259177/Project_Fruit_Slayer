package cn.bonoon.controllers.showstatistics.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.core.ProjectService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;
import cn.bonoon.util.StatisticalQueryUtil;

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("s/pls/show/spj4")
public class ProvinceActualProjectController extends AbstractPanelController {
	private String myBatch;
	@Autowired
	protected ProjectService statisticsService;

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		// String batch = model.getParameter("batch");
		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		myBatch = batch;
		event.setVmPath("showstatistics/actual-area-project");
		Collection<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items))
				.addObject("batch", batch);

		String batchs = model.getParameter("batch");
		model.addObject("select", BatchHelper.batchSelect(batchs));
	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch) {
		Collection<Object[]> items = getItems(getUser(),
				BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content",
				statistics(items));
		return "showstatistics/actual-area-project-items";
	}

	private Collection<Object[]> getItems(LogonUser user, String batch) {
		return statisticsService.statistics(batch);
	}

	private Object statistics(Collection<Object[]> items) {
		StringBuilder statis = new StringBuilder();
		int i = 1;
		int E1 = 0, E2 = 0, E3 = 0, E4 = 0, E5 = 0, E6 = 0, E7 = 0, E8 = 0;
		double E9 = 0, E10 = 0, E11 = 0, E12 = 0, E131 = 0, E132 = 0, E14 = 0, E141 = 0, E15 = 0;
		double E16 = 0, E17 = 0, E18 = 0, E19 = 0, E201 = 0, E202 = 0, E21 = 0, E211 = 0, E22 = 0, E23 = 0, E24 = 0, E25 = 0;
		double E26 = 0, E27 = 0, E28 = 0, E29 = 0, E30 = 0, E31 = 0;
		for (Object[] it : items) {
			statis.append("<tr><td>").append(i++).append("</td>");

			for (int i2 = 0; i2 < 6; i2++) {
				statis.append("<td>");
				if (it[i2] != null) {
					if (it[i2].getClass().equals(Double.class)) {
						statis.append(StatisticalQueryUtil.format(it[i2]));
					} else {
						statis.append(it[i2]);
					}
				}
				statis.append("</td>");
			}
			// statis.append("<td>");
			// if (null != it[0])
			// statis.append(it[0]);
			// statis.append("</td>");
			// statis.append("<td>");
			// if (null != it[1])
			// statis.append(it[1]);
			// statis.append("</td>");
			// statis.append("<td>");
			// if (null != it[2])
			// statis.append(it[2]);
			// statis.append("</td>");
			// statis.append("<td>");
			// if (null != it[3])
			// statis.append(it[3]);
			// statis.append("</td>");
			// statis.append("<td>");
			// if (null != it[4])
			// statis.append(it[4]);
			// statis.append("</td>");
			// statis.append("<td>");
			// if (null != it[5])
			// statis.append(it[5]);
			// statis.append("</td>");

			for (int i2 = 30; i2 < 41; i2++) {
				statis.append("<td>");
				if (it[i2] != null) {
					if (it[i2].getClass().equals(Double.class)) {
						statis.append(StatisticalQueryUtil.format(it[i2]));
					} else {
						statis.append(it[i2]);
					}
				}
				statis.append("</td>");
			}
//			statis.append("<td>");
//			if (null != it[30])
//				statis.append(it[30]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[31])
//				statis.append(it[31]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[32])
//				statis.append(it[32]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[33])
//				statis.append(it[33]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[34])
//				statis.append(it[34]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[35])
//				statis.append(it[35]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[36])
//				statis.append(it[36]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[37])
//				statis.append(it[37]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[38])
//				statis.append(it[38]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[39])
//				statis.append(it[39]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[40])
//				statis.append(it[40]);
//			statis.append("</td>");

			E211 = add(E211, it[30]);
			E22 = add(E22, it[31]);
			E23 = add(E23, it[32]);
			E24 = add(E24, it[33]);
			E25 = add(E25, it[34]);
			E26 = add(E26, it[35]);
			E27 = add(E27, it[36]);
			E28 = add(E28, it[37]);
			E29 = add(E29, it[38]);
			E30 = add(E30, it[39]);
			E31 = add(E31, it[40]);

			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td>");
		statis.append(E211).append("</td><td>");
		statis.append(E22).append("</td><td>");
		statis.append(E23).append("</td><td>");
		statis.append(E24).append("</td><td>");
		statis.append(E25).append("</td><td>");
		statis.append(E26).append("</td><td>");
		statis.append(E27).append("</td><td>");
		statis.append(E28).append("</td><td>");
		statis.append(E29).append("</td><td>");
		statis.append(E30).append("</td><td>");
		statis.append(E31).append("</td></tr>");
		return statis;
	}

	/**
	 * 导出excel功能
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/index.excel", method = GET)
	public View excel(HttpServletRequest request, HttpServletResponse response,
			Long id) {

		Collection<Object[]> items = statisticsService.statistics(myBatch);
		List<Object[]> itemsNew = new ArrayList<Object[]>();

		for (Object[] o : items) {
			Object[] o2 = new Object[40 - 30 + 1 + 1];
			for (int i = 0; i < o.length; i++) {

				if (i == 3 || i < 41 && i > 29) {
					for (int i2 = 0; i2 < o2.length; i2++) {
						if (o2[i2] == null) {
							o2[i2] = o[i];
							break;
						}
					}
				}
			}
			itemsNew.add(o2);
		}
		items = itemsNew;
		return new TotalInvestmentProjectCompleteExcel(items, myBatch);

	}
}
