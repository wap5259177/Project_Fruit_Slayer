package cn.bonoon.controllers.showstatistics.newrural;

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.DecimalFormat;
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
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/pls/show/scr1")
public class ProvinceBasicNewRuralController extends AbstractPanelController {
	private String myBatch;
	@Autowired
	protected NewRuralService statisticsService;

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();

		int bi = model.intParameter("batch");
		event.setVmPath("showstatistics/basic-new-rural");
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
		return "showstatistics/basic-new-rural-items";
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
		DecimalFormat df = new DecimalFormat("######0.00");
		for (Object[] it : items) {
			System.out.println(it.length);
			statis.append("<tr><td>").append(i++).append("</td>");
			for (Object obj : it) {
				statis.append("<td>");
				if (null != obj) {
					if (obj.getClass().equals(Double.class))
						obj = df.format(obj);
					statis.append(obj);
				} else {
					statis.append("");
				}
				statis.append("</td>");
			}
			// if(it[8] instanceof Number){
			// B5 += ((Number)it[8]).doubleValue();
			// }
			// if(it[9] instanceof Number){
			// B6 += ((Number)it[9]).doubleValue();
			// }

			B5 = add(B5, it[8]);
			B6 = add(B6, it[9]);

			if (it[10] instanceof Number) {
				B7 += ((Number) it[10]).intValue();
			}
			if (it[11] instanceof Number) {
				B8 += ((Number) it[11]).intValue();
			}
			if (it[12] instanceof Number) {
				B9 += ((Number) it[12]).intValue();
			}
			if (it[13] instanceof Number) {
				X1 += ((Number) it[13]).intValue();
			}
			if (it[14] instanceof Number) {
				X2 += ((Number) it[14]).intValue();
			}
			if (it[15] instanceof Number) {
				X3 += ((Number) it[15]).intValue();
			}
			if (it[16] instanceof Number) {
				X4 += ((Number) it[16]).intValue();
			}
			if (it[17] instanceof Number) {
				X5 += ((Number) it[17]).intValue();
			}
			if (it[18] instanceof Number) {
				X6 += ((Number) it[18]).intValue();
			}
			if ("是".equals(it[19])) {
				B11 += 1;
			}
			if ("是".equals(it[20])) {
				B12 += 1;
			}

			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td></td><td></td><td>");
		statis.append(df.format(B5)).append("</td><td>");
		statis.append(df.format(B6)).append("</td><td>");
		statis.append(B7).append("</td><td>");
		statis.append(B8).append("</td><td>");
		statis.append(B9).append("</td><td>");
		statis.append(X1).append("</td><td>");
		statis.append(X2).append("</td><td>");
		statis.append(X3).append("</td><td>");
		statis.append(X4).append("</td><td>");
		statis.append(X5).append("</td><td>");
		statis.append(X6).append("</td><td>");
		statis.append(B11).append("</td><td>");
		statis.append(B12).append("</td><td>");

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
			Object[] o2 = new Object[16];
			for (int i = 0; i < o.length; i++) {

				if (i == 3 || i < 21 && i > 5) {
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
		return new AdRuralBasicSituationExcel(items, myBatch);

	}
}
