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
import cn.bonoon.controllers.showstatistics.peripherarural.PeripheralRuralPublicServiceExcel;
import cn.bonoon.core.ProjectService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("s/pls/show/spj1")
public class ProvinceBasicProjectController extends AbstractPanelController {
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
		event.setVmPath("showstatistics/basic-area-project");
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
		return "showstatistics/basic-area-project-items";
	}

	private Collection<Object[]> getItems(LogonUser user, String batch) {
		return statisticsService.statistics(batch);
	}

	private Object statistics(Collection<Object[]> items) {

		StringBuilder statis = new StringBuilder();
		int i = 1;
		int E1 = 0, E2 = 0, E3 = 0, E4 = 0, E5 = 0, E6 = 0, E7 = 0, E8 = 0;
		int E9 = 0, E10 = 0, E11 = 0, E12 = 0, E131 = 0, E132 = 0, E14 = 0;
	
		for (Object[] it : items) {
			statis.append("<tr><td>").append(i++).append("</td>");

			for (Object obj : it) {
				statis.append("<td>");
				if (null != obj)
					statis.append(obj);

				statis.append("</td>");
			}
			for (int i2 = 0; i2 < it.length; i2++) {
				if (it[i2] != null && it[i2].getClass().equals(Double.class)){
					it[i2] = ((Double) it[i2]).intValue();
					System.out.println(it[i2]);
					}
				}
			if (it[6] instanceof Number) {
				E1 += ((Number) it[6]).intValue();
			}
			if (it[7] instanceof Number) {
				E2 += ((Number) it[7]).intValue();
			}
			if (it[8] instanceof Number) {
				E3 += ((Number) it[8]).intValue();
			}
			if (it[9] instanceof Number) {
				E4 += ((Number) it[9]).intValue();
			}
			if (it[10] instanceof Number) {
				E5 += ((Number) it[10]).intValue();
			}
			if (it[11] instanceof Number) {
				E6 += ((Number) it[11]).intValue();
			}
			if (it[12] instanceof Number) {
				E7 += ((Number) it[12]).intValue();
			}
			if (it[13] instanceof Number) {
				E8 += ((Number) it[13]).intValue();
			}
			if (it[14] instanceof Number) {
				E9 += ((Number) it[14]).intValue();
			}
			if (it[15] instanceof Number) {
				E10 += ((Number) it[15]).intValue();
			}
			if (it[16] instanceof Number) {
				E11 += ((Number) it[16]).intValue();
			}
			if (it[17] instanceof Number) {
				E12 += ((Number) it[17]).intValue();
			}
			if (it[18] instanceof Number) {
				E131 += ((Number) it[18]).intValue();
			}
			if (it[19] instanceof Number) {
				E132 += ((Number) it[19]).intValue();
			}
			if (it[20] instanceof Number) {
				E14 += ((Number) it[20]).intValue();
			}

			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td>");
		statis.append(E1).append("</td><td>");
		statis.append(E2).append("</td><td>");
		statis.append(E3).append("</td><td>");
		statis.append(E4).append("</td><td>");
		statis.append(E5).append("</td><td>");
		statis.append(E6).append("</td><td>");
		statis.append(E7).append("</td><td>");
		statis.append(E8).append("</td><td>");
		statis.append(E9).append("</td><td>");
		statis.append(E10).append("</td><td>");
		statis.append(E11).append("</td><td>");
		statis.append(E12).append("</td><td>");
		statis.append(E131).append("</td><td>");
		statis.append(E132).append("</td><td>");
		statis.append(E14).append("</td><td>");

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
			Object[] o2 = new Object[20 - 6 + 1 + 1];
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
		return new BasicInformationProjectExcel(items, myBatch);

	}
}
