package cn.bonoon.controllers.showstatistics.modelarea;

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.DecimalFormat;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.controllers.showstatistics.administration.RuralPublicServiceSituationExcel;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;
import cn.bonoon.util.StatisticalQueryUtil;

@Controller
@RequestMapping("s/pls/show/sma2")
public class ProvinceTotalModelAreaController extends AbstractPanelController {
	String myBatch = "一";
	@Autowired
	protected ModelAreaService statisticsService;

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();

		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		myBatch = batch;
		Collection<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items))
				.addObject("batch", batch);

		String batchs = model.getParameter("batch");
		model.addObject("select", BatchHelper.batchSelect(batchs));
		event.setVmPath("showstatistics/total-model-area");

	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch) {
		Collection<Object[]> items = getItems(getUser(),
				BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content",
				statistics(items));
		return "showstatistics/total-model-area-items";
	}

	private Collection<Object[]> getItems(LogonUser user, String batch) {
		return statisticsService.totalStatistics(batch);
	}

	private Object statistics(Collection<Object[]> items) {
		StringBuilder statis = new StringBuilder();
		int i = 1;
		// for(Object[] it : items){
		// statis.append("<tr><td>").append(i++).append("</td>");//序号
		// for(Object obj : it){
		// if(null!=obj){
		// statis.append("<td>").append(obj).append("</td>");
		// }else{
		// statis.append("<td></td>");
		// }
		// }
		// statis.append("</tr>");
		// }

		double A1 = 0, A19 = 0, A22 = 0, A26 = 0;
		int A2 = 0, A3 = 0, A4 = 0, A5 = 0, A6 = 0, A7 = 0, A8 = 0, A9 = 0;
		double x13 = 0, x14 = 0, x15 = 0, x16 = 0, z13 = 0, z14 = 0, z15 = 0, z16 = 0, z17 = 0, x17 = 0;
		int A16 = 0, A17 = 0, A18 = 0, A20 = 0, A21 = 0;
		int A24 = 0, A25 = 0, A27 = 0, A28 = 0, A29 = 0, A23 = 0;
		DecimalFormat df = new DecimalFormat("######0.00");
		for (Object[] it : items) {
			statis.append("<tr><td>").append(i++).append("</td>");
			for (Object obj : it) {
				statis.append("<td>");
				if (null != obj) {
					if (obj.getClass().equals(Double.class))
						obj=StatisticalQueryUtil.format(obj);
					statis.append(obj);
				} else {
					statis.append("");

				};
				statis.append("</td>");
			}

			if (it[6] instanceof Number) {
				A16 += ((Number) it[6]).intValue();
			}
			if (it[7] instanceof Number) {
				A17 += ((Number) it[7]).intValue();
			}

			/* A2-A5（it[8]-it[11]） 主体行政村，主体自然村，非主体行政村，非主体自然村 */
			if (it[8] instanceof Number) {
				A2 += ((Number) it[8]).intValue();
			}
			if (it[9] instanceof Number) {
				A3 += ((Number) it[9]).intValue();
			}
			if (it[10] instanceof Number) {
				A4 += ((Number) it[10]).intValue();
			}
			if (it[11] instanceof Number) {
				A5 += ((Number) it[11]).intValue();
			}

			if (it[12] instanceof Number) {
				A18 += ((Number) it[12]).intValue();
			}
			// if(it[26] instanceof Number){
			// A19 += ((Number)it[26]).doubleValue();
			// }
			A19 = add(A19, it[13]);

			if (it[13] instanceof Number) {
				A20 += ((Number) it[14]).intValue();
			}
			if (it[14] instanceof Number) {
				A21 += ((Number) it[15]).intValue();
			}

			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td>");
		statis.append(A16).append("</td><td>");
		statis.append(A17).append("</td><td>");

		statis.append(A2).append("</td><td>");
		statis.append(A3).append("</td><td>");
		statis.append(A4).append("</td><td>");
		statis.append(A5).append("</td><td>");

		statis.append(A18).append("</td><td>");
		statis.append(df.format(A19)).append("</td><td>");
		statis.append(A20).append("</td><td>");
		statis.append(A21).append("</td><td>");

		return statis;
	}

	private final static DecimalFormat df = new DecimalFormat("0.##");

	/**
	 * 导出excel功能
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/index.excel", method = GET)
	public View excel(HttpServletRequest request, HttpServletResponse response,
			Long id) {

		Collection<Object[]> items = statisticsService
				.totalStatisticsNew(myBatch);
		return new LedgerBookExcel(items, myBatch);

	}
}
