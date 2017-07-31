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
@RequestMapping("s/pls/show/spr4")
public class ProvincePublicPeripheraController extends AbstractPanelController {
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
		event.setVmPath("showstatistics/public-peripheral-rural");
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
		return "showstatistics/public-peripheral-rural-items";
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
//			statis.append("<td>");
//			if (null != it[0])
//				statis.append(it[0]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[1])
//				statis.append(it[1]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[2])
//				statis.append(it[2]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[3])
//				statis.append(it[3]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[4])
//				statis.append(it[4]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[5])
//				statis.append(it[5]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[6])
//				statis.append(it[6]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[7])
//				statis.append(it[7]);
//			statis.append("</td>");
			
			for (int i2 = 34; i2 < 45; i2++) {
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
//			statis.append("<td>");
//			if (null != it[41])
//				statis.append(it[41]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[42])
//				statis.append(it[42]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[43])
//				statis.append(it[43]);
//			statis.append("</td>");
//			statis.append("<td>");
//			if (null != it[44])
//				statis.append(it[44]);
//			statis.append("</td>");

			if (it[34] instanceof Number) {
				C30 += ((Number) it[34]).intValue();
			}
			// if(it[33] instanceof Number){
			// x30 += ((Number)it[33]).doubleValue();
			// }
			x30 = add(x30, it[35]);

			if (it[36] instanceof Number) {
				C31 += ((Number) it[36]).intValue();
			}
			// if(it[35] instanceof Number){
			// x31 += ((Number)it[35]).doubleValue();
			// }
			x31 = add(x31, it[37]);

			if (it[38] instanceof Number) {
				C32 += ((Number) it[38]).intValue();
			}
			// if(it[37] instanceof Number){
			// x32 += ((Number)it[37]).doubleValue();
			// }
			x32 = add(x32, it[39]);

			if (it[40] instanceof Number) {
				C33 += ((Number) it[40]).intValue();
			}
			// if(it[39] instanceof Number){
			// x33 += ((Number)it[39]).doubleValue();
			// }

			x33 = add(x33, it[41]);

			if (it[42] instanceof Number) {
				C34 += ((Number) it[42]).intValue();
			}
			// if(it[41] instanceof Number){
			// x34 += ((Number)it[41]).doubleValue();
			// }

			x34 = add(x34, it[43]);

			if (it[44] instanceof Number) {
				C35 += ((Number) it[44]).intValue();
			}

			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td></td><td></td><td>");
		statis.append(C30).append("</td><td>");
		statis.append(x30).append("</td><td>");
		statis.append(C31).append("</td><td>");
		statis.append(x31).append("</td><td>");
		statis.append(C32).append("</td><td>");
		statis.append(x32).append("</td><td>");
		statis.append(C33).append("</td><td>");
		statis.append(x33).append("</td><td>");
		statis.append(C34).append("</td><td>");
		statis.append(x34).append("</td><td>");
		statis.append(C35).append("</td><td>");

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
			Object[] o2 = new Object[44 - 34 + 1 + 3];
			for (int i = 0; i < o.length; i++) {

				if (i == 3 || i >= 6 && i <= 7 || i < 45 && i > 33) {
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
		return new PeripheralRuralPublicServiceExcel(items, myBatch);

	}
}
