package cn.bonoon.controllers.showstatistics.peripherarural;

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
import cn.bonoon.core.PeripheraRuralService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;
import cn.bonoon.util.StatisticalQueryUtil;

@Controller
@RequestMapping("s/pls/show/spr2")
public class ProvinceBuildPeripheraController extends AbstractPanelController {
	private String myBatch;
	@Autowired
	protected PeripheraRuralService statisticsService;

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		// String batch = model.getParameter("batch");
		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		myBatch = batch;
		event.setVmPath("showstatistics/build-peripheral-rural");
		Collection<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items))
				.addObject("batch", batch);
		;

		String batchs = model.getParameter("batch");
		model.addObject("select", BatchHelper.batchSelect(batchs));
	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch) {
		Collection<Object[]> items = getItems(getUser(),
				BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content",
				statistics(items));
		return "showstatistics/build-peripheral-rural-items";
	}

	private Collection<Object[]> getItems(LogonUser user, String batch) {
		return statisticsService.statistics(batch);
	}

	private Object statistics(Collection<Object[]> items) {
		StringBuilder statis = new StringBuilder();
		int i = 1;
		double C6 = 0, C7 = 0, C14 = 0, C15 = 0, C19 = 0, x30 = 0, x31 = 0, x32 = 0, x33 = 0, x34 = 0;
		int C8 = 0, C9 = 0, C10 = 0, C11 = 0, C12 = 0, C13 = 0, C16 = 0, C17 = 0;
		int C18 = 0, C20 = 0, C21 = 0, C22 = 0, x22 = 0, C23 = 0, C24 = 0, C25 = 0, C26 = 0, C27 = 0;
		int C28 = 0, C29 = 0, x29 = 0, C30 = 0, C31 = 0, C32 = 0, C33 = 0, C34 = 0, C35 = 0, C36 = 0, C37 = 0, C38 = 0, C41 = 0;
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

			for (int i2 = 17; i2 < 28; i2++) {
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

			C15 = add(C15, it[17]);

			if (it[18] instanceof Number) {
				C16 += ((Number) it[18]).intValue();
			}
			if (it[19] instanceof Number) {
				C17 += ((Number) it[19]).intValue();
			}
			if (it[20] instanceof Number) {
				C18 += ((Number) it[20]).intValue();
			}
			// if(it[19] instanceof Number){
			// C19 += ((Number)it[19]).doubleValue();
			// }
			C19 = add(C19, it[21]);

			if (it[22] instanceof Number) {
				C20 += ((Number) it[22]).intValue();
			}
			if (it[23] instanceof Number) {
				C21 += ((Number) it[23]).intValue();
			}
			if (it[24] instanceof Number) {
				C22 += ((Number) it[24]).intValue();
			}
			if (it[25] instanceof Number) {
				x22 += ((Number) it[25]).intValue();
			}
			if (it[26] instanceof Number) {
				C23 += ((Number) it[26]).intValue();
			}
			if (it[27] instanceof Number) {
				C24 += ((Number) it[27]).intValue();
			}

			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td></td><td></td><td>");
		statis.append(StatisticalQueryUtil.format(C15)).append("</td><td>");
		statis.append(C16).append("</td><td>");
		statis.append(C17).append("</td><td>");
		statis.append(C18).append("</td><td>");
		statis.append(StatisticalQueryUtil.format(C19)).append("</td><td>");
		statis.append(C20).append("</td><td>");
		statis.append(C21).append("</td><td>");
		statis.append(C22).append("</td><td>");
		statis.append(x22).append("</td><td>");
		statis.append(C23).append("</td><td>");
		statis.append(C24).append("</td><td>");

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
			Object[] o2 = new Object[27 - 17 + 1 + 3];
			for (int i = 0; i < o.length; i++) {

				if (i == 3 || i >= 6 && i <= 7 || i < 28 && i > 16) {
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
		return new InfrastructureSonstructionEnvironmentalSanitationExcel(items, myBatch);

	}
}
