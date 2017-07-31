package cn.bonoon.controllers.showstatistics.newrural;

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.core.NewRuralService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;
import cn.bonoon.util.StatisticalQueryUtil;

@Controller
@RequestMapping("s/pls/show/scr2")
public class ProvinceBuildNewRuralControler extends AbstractPanelController {
	private String myBatch;
	@Autowired
	protected NewRuralService statisticsService;

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();

		int bi = model.intParameter("batch");
		event.setVmPath("showstatistics/build-new-rural");
		String batch = BatchHelper.getValue(bi);
		myBatch = batch;
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
		return "showstatistics/build-new-rural-items";
	}

	private Collection<Object[]> getItems(LogonUser user, String batch) {
		return statisticsService.statistics(batch);
	}

	private Object statistics(Collection<Object[]> items) {
		StringBuilder statis = new StringBuilder();
		int i = 1;
		double B5 = 0, B6 = 0, B14 = 0;
		int B7 = 0, B8 = 0, B9 = 0, X1 = 0, X2 = 0, X3 = 0, X4 = 0, X5 = 0, X6 = 0, B11 = 0, B12 = 0, B15 = 0, B291 = 0, B30 = 0;
		int B16 = 0, B17 = 0, B18 = 0, B19 = 0, B20 = 0, B21 = 0, B22 = 0, B23 = 0, B24 = 0, B25 = 0, B26 = 0, B27 = 0, B28 = 0, B29 = 0;
		int B53 = 0, B54 = 0, B56 = 0, X57 = 0, X58 = 0, X59 = 0, X60 = 0, B211 = 0;
		for (Object[] it : items) {
			statis.append("<tr><td>").append(i++).append("</td>");

			for (int i2 = 0; i2 < 8; i2++) {
				statis.append("<td>");
				if (null != it[i2]) {
					if (it[i2].getClass().equals(Double.class)) {
						statis.append(StatisticalQueryUtil.format(it[i2]));
					} else {
						statis.append(it[i2]);
					}
				}
				statis.append("</td>");
			}
			
			for (int i2 = 21; i2 < 32; i2++) {
				statis.append("<td>");
				if (null != it[i2]) {
					if (it[i2].getClass().equals(Double.class)) {
						statis.append(StatisticalQueryUtil.format(it[i2]));
					} else {
						statis.append(it[i2]);
					}
				}
				statis.append("</td>");
			}


			B14 = add(B14, it[21]);

			if ("是".equals(it[22])) {
				B15 += 1;
			}
			if (it[23] instanceof Number) {
				B16 += ((Number) it[23]).intValue();
			}
			if (it[24] instanceof Number) {
				B17 += ((Number) it[24]).intValue();
			}
			if (it[25] instanceof Number) {
				B18 += ((Number) it[25]).intValue();
			}
			if ("是".equals(it[26])) {
				B19 += 1;
			}
			if ("是".equals(it[27])) {
				B20 += 1;
			}
			if ("是".equals(it[28])) {
				B21 += 1;
			}
			if (it[29] instanceof Number) {
				B211 += ((Number) it[29]).intValue();
			}
			if (it[30] instanceof Number) {
				B22 += ((Number) it[30]).intValue();
			}
			if ("是".equals(it[31])) {
				B23 += 1;
			}

			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td></td><td></td><td>");
		statis.append(StatisticalQueryUtil.format(B14)).append("</td><td>");
		statis.append(B15).append("</td><td>");
		statis.append(B16).append("</td><td>");
		statis.append(B17).append("</td><td>");
		statis.append(B18).append("</td><td>");
		statis.append(B19).append("</td><td>");
		statis.append(B20).append("</td><td>");
		statis.append(B21).append("</td><td>");
		statis.append(B211).append("</td><td>");
		statis.append(B22).append("</td><td>");
		statis.append(B23).append("</td><td>");

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
			Object[] o2 = new Object[14];
			for (int i = 0; i < o.length; i++) {

				if (i == 3 || i >= 6 && i <= 7 || i < 32 && i > 20) {
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
		return new AllAdRuralSituationStatisticsExcel(items, myBatch);

	}
}
