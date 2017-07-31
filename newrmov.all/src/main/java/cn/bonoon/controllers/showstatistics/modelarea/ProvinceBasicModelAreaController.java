package cn.bonoon.controllers.showstatistics.modelarea;

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
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;
import cn.bonoon.util.StatisticalQueryUtil;

@Controller
@RequestMapping("s/pls/show/sma1")
public class ProvinceBasicModelAreaController extends AbstractPanelController {
	@Autowired
	protected ModelAreaService statisticsService;
	// private LogonUser user;
	private PanelEvent event;
	private String mybatch = "一";

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();

		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		mybatch = batch;
		Collection<Object[]> items = getItems(event.getUser(), batch);
		// this.user=event.getUser();
		this.event = event;
		event.getModel().addObject("content", statistics(items))
				.addObject("batch", batch);
		String batchs = model.getParameter("batch");

		model.addObject("select", BatchHelper.batchSelect(batchs));
		event.setVmPath("showstatistics/basic-model-area");
	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch) {
		Collection<Object[]> items = getItems(getUser(),
				BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content",
				statistics(items));
		return "showstatistics/basic-model-area-items";
	}

	private Collection<Object[]> getItems(LogonUser user, String batch) {
		return statisticsService.statistics(batch);
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

		for (Object[] it : items) {
			statis.append("<tr><td>").append(i++).append("</td>");
			for (Object obj : it) {
				statis.append("<td>");
				if (null != obj) {
					if (obj.getClass().equals(Double.class))
						obj = StatisticalQueryUtil.format(obj);
					statis.append(obj);
				} else {
					statis.append("");
				}
				statis.append("</td>");
			}
			A1 = add(A1, it[6]);

			if (it[7] instanceof Number) {
				A2 += ((Number) it[7]).intValue();
			}
			if (it[8] instanceof Number) {
				A3 += ((Number) it[8]).intValue();
			}
			if (it[9] instanceof Number) {
				A4 += ((Number) it[9]).intValue();
			}
			if (it[10] instanceof Number) {
				A5 += ((Number) it[10]).intValue();
			}
			if (it[11] instanceof Number) {
				A6 += ((Number) it[11]).intValue();
			}
			if (it[12] instanceof Number) {
				A7 += ((Number) it[12]).intValue();
			}
			if (it[13] instanceof Number) {
				A8 += ((Number) it[13]).intValue();
			}
			if (it[14] instanceof Number) {
				A9 += ((Number) it[14]).intValue();
			}
			// if(it[15] instanceof Number){
			// x13 += ((Number)it[15]).doubleValue();
			// }
			x13 = add(x13, it[15]);

			// if(it[16] instanceof Number){
			// x14 += ((Number)it[16]).doubleValue();
			// }
			x14 = add(x14, it[16]);

			// if(it[17] instanceof Number){
			// x15 += ((Number)it[17]).doubleValue();
			// }
			x15 = add(x15, it[17]);

			// if(it[18] instanceof Number){
			// x16 += ((Number)it[18]).doubleValue();
			// }
			x16 = add(x16, it[18]);
			x17 = add(x17, it[19]);
			// if(it[19] instanceof Number){
			// z13 += ((Number)it[19]).doubleValue();
			// }
			z13 = add(z13, it[20]);

			// if(it[20] instanceof Number){
			// z14 += ((Number)it[20]).doubleValue();
			// }
			z14 = add(z14, it[21]);

			// if(it[21] instanceof Number){
			// z15 += ((Number)it[21]).doubleValue();
			// }
			z15 = add(z15, it[22]);

			// if(it[22] instanceof Number){
			// z16 += ((Number)it[22]).doubleValue();
			// }
			z16 = add(z16, it[23]);
			z17 = add(z17, it[24]);

			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td>");
		statis.append(StatisticalQueryUtil.format(A1)).append("</td><td>");
		statis.append(A2).append("</td><td>");
		statis.append(A3).append("</td><td>");
		statis.append(A4).append("</td><td>");
		statis.append(A5).append("</td><td>");
		statis.append(A6).append("</td><td>");
		statis.append(A7).append("</td><td>");
		statis.append(A8).append("</td><td>");
		statis.append(A9).append("</td><td>");
		statis.append(StatisticalQueryUtil.format(x13)).append("</td><td>");
		statis.append(StatisticalQueryUtil.format(x14)).append("</td><td>");
		statis.append(StatisticalQueryUtil.format(x15)).append("</td><td>");
		statis.append(StatisticalQueryUtil.format(x16)).append("</td><td>");
		statis.append(StatisticalQueryUtil.format(x17)).append("</td><td>");
		statis.append(StatisticalQueryUtil.format(z13)).append("</td><td>");
		statis.append(StatisticalQueryUtil.format(z14)).append("</td><td>");
		statis.append(StatisticalQueryUtil.format(z15)).append("</td><td>");
		statis.append(StatisticalQueryUtil.format(z16)).append("</td><td>");
		statis.append(StatisticalQueryUtil.format(z17)).append("</td><td>");
		return statis;
	}

	// /**
	// * 导出excel功能
	// */
	@ResponseBody
	@RequestMapping(value = "!{key}/index.excel", method = GET)
	public View excel(HttpServletRequest request, HttpServletResponse response,
			Long id) {
		// ModelAreaQuarterItem item = localQuarterReportService.get(id);
		// return new CountyQuarterReportExcelView(item);
		// return new CountyQuarterReportExcelView(item);
		// int batch=request.getParameter("batch");
		// int bi = model.intParameter("batch");
		// String batch = BatchHelper.getValue(bi);
		// event的批次是页面的event没重新负最新值话批次没改会导致excel之前的
		int bi = this.event.getModel().intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		// Collection<Object[]> items = getItems(event.getUser(), batch);
		// 声明字段mybatch证明了控制器没被销毁因为字段值没改变,那就是event.model会变为默认值,导出excel时本方法调用了俩次第一次bi为上一次的值第二次直接赋值为0
		// 直接在控制器上声明字段mybatch好了
		Collection<Object[]> items = getItems(event.getUser(), mybatch);

		List<Object[]> list = new ArrayList<Object[]>();
		for (Object[] o : items) {
			Object[] o2 = new Object[24 - 6 + 2];
			for (int i = 0; i < o.length; i++) {
				if (i == 3 || i > 5 && i < 25) {
					for (int i2 = 0; i2 < o2.length; i2++) {
						if (o2[i2] == null) {
							o2[i2] = o[i];
							break;
						}
					}
				}
			}
			list.add(o2);
		}
		items=list;
		return new ModelAreaBasicSituationInCountyExcel(items, mybatch);
		// return new TestExcel();
	}

}
