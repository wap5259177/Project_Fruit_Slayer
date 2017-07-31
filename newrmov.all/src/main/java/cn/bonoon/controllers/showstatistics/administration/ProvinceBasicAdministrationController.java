package cn.bonoon.controllers.showstatistics.administration;

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.math.BigDecimal;
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
import cn.bonoon.core.AdministrationCoreRuralService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/pls/show/sar1")
public class ProvinceBasicAdministrationController extends
		AbstractPanelController {
	String mybatch = "一";
	@Autowired
	protected AdministrationCoreRuralService administrationCoreRuralService;

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		event.setVmPath("showstatistics/basic-area-administration");
		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		mybatch = batch;
		Collection<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items))
				.addObject("batch", batch);
		String batchs = model.getParameter("batch");
		model.addObject("select", BatchHelper.batchSelect(batchs));
		// Collection<Object[]> items=
		// administrationCoreRuralService.excelValue(mybatch);
		// CountUtil.setShowWhat(event, items, batch,
		// "showstatistics/basic-area-administration");
		// CountUtil.setShowWhat(event, items, batch, "showstatistics/test");
	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch) {
		Collection<Object[]> items = getItems(getUser(),
				BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content",
				statistics(items));
		return "showstatistics/basic-area-administration-items";
	}

	private Collection<Object[]> getItems(LogonUser user, String batch) {
		return administrationCoreRuralService.statistics(batch);
	}

	private Object statistics(Collection<Object[]> items) {
		StringBuilder statis = new StringBuilder();
		int i = 1;
		double B5 = 0, B6 = 0, x20 = 0, x21 = 0, x22 = 0, x23 = 0, x24 = 0, x25 = 0, x26 = 0, x27 = 0, x28 = 0, x29 = 0;
		int B4 = 0, B7 = 0, B8 = 0, B9 = 0, B10 = 0, B11 = 0, B12 = 0, B13 = 0, B14 = 0, B15 = 0, B38 = 0, B461 = 0;
		int B39 = 0, B40 = 0, B41 = 0, B42 = 0, B43 = 0;
		double B391 = 0, B401 = 0, B411 = 0, B421 = 0, B431 = 0;
		int B16 = 0, B31 = 0, B32 = 0, B33 = 0, B34 = 0, B35 = 0, B36 = 0, B37 = 0, B44 = 0, B45 = 0;
		int B46 = 0, B47 = 0, B48 = 0, B49 = 0, B50 = 0;
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		for (Object[] it : items) {
			statis.append("<tr><td>").append(i++).append("</td>");
			for (Object obj : it) {
				statis.append("<td>");
				if (null != obj) {
					if(obj.getClass().equals(Double.class)){
					
						statis.append(df.format(obj));
					}else{
					statis.append(obj);
					}
				} else {
					statis.append("");
				}
				
				statis.append("</td>");
			}
			if (it[7] instanceof Number) {
				B4 += ((Number) it[7]).intValue();
			}
			// if(it[8] instanceof Number){
			// B5 += ((Number)it[8]).doubleValue();
			// }

			B5 = add(B5, it[8]);

			// if(it[9] instanceof Number){
			// B6 += ((Number)it[9]).doubleValue();
			// }

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
				B10 += ((Number) it[13]).intValue();
			}
			if (it[14] instanceof Number) {
				B11 += ((Number) it[14]).intValue();
			}
			if (it[15] instanceof Number) {
				B12 += ((Number) it[15]).intValue();
			}
			if (it[16] instanceof Number) {
				B13 += ((Number) it[16]).intValue();
			}
			if (it[17] instanceof Number) {
				B14 += ((Number) it[17]).intValue();
			}
			if (it[18] instanceof Number) {
				B15 += ((Number) it[18]).intValue();
			}
			if ("是".equals(it[19])) {
				B16 += 1;
			}

			// if(it[20] instanceof Number){
			// x20 += ((Number)it[20]).doubleValue();
			// }
			// if(it[21] instanceof Number){
			// x21 += ((Number)it[21]).doubleValue();
			// }
			// if(it[22] instanceof Number){
			// x22 += ((Number)it[22]).doubleValue();
			// }
			// if(it[23] instanceof Number){
			// x23 += ((Number)it[23]).doubleValue();
			// }
			// if(it[24] instanceof Number){
			// x24 += ((Number)it[24]).doubleValue();
			// }
			// if(it[25] instanceof Number){
			// x25 += ((Number)it[25]).doubleValue();
			// }
			// if(it[26] instanceof Number){
			// x26 += ((Number)it[26]).doubleValue();
			// }
			// if(it[27] instanceof Number){
			// x27 += ((Number)it[27]).doubleValue();
			// }
			// if(it[28] instanceof Number){
			// x28 += ((Number)it[28]).doubleValue();
			// }
			// if(it[29] instanceof Number){
			// x29 += ((Number)it[29]).doubleValue();
			// }
		
			x20 = add(x20, it[20]);
			x21 = add(x21, it[21]);
			x22 = add(x22, it[22]);
			x23 = add(x23, it[23]);
			x24 = add(x24, it[24]);
			x25 = add(x25, it[25]);
			x26 = add(x26, it[26]);
			x27 = add(x27, it[27]);
			x28 = add(x28, it[28]);
			x29 = add(x29, it[29]);
			
			// 资源优势
			if (it[30].equals("是")) {
				B31 += 1;
			}

			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td></td><td>");
		statis.append(B4).append("</td><td>");
		statis.append(df.format(B5)).append("</td><td>");
		statis.append(df.format(B6)).append("</td><td>");
		statis.append(B7).append("</td><td>");
		statis.append(B8).append("</td><td>");
		statis.append(B9).append("</td><td>");
		statis.append(B10).append("</td><td>");
		statis.append(B11).append("</td><td>");
		statis.append(B12).append("</td><td>");
		statis.append(B13).append("</td><td>");
		statis.append(B14).append("</td><td>");
		statis.append(B15).append("</td><td>");
		statis.append(B16).append("</td><td>");
		statis.append(df.format(x20)).append("</td><td>");
		statis.append(df.format(x21)).append("</td><td>");
		statis.append(df.format(x22)).append("</td><td>");
		statis.append(df.format(x23)).append("</td><td>");
		statis.append(df.format(x24)).append("</td><td>");
		statis.append(df.format(x25)).append("</td><td>");
		statis.append(df.format(x26)).append("</td><td>");
		statis.append(df.format(x27)).append("</td><td>");
		statis.append(df.format(x28)).append("</td><td>");
		statis.append(df.format(x29)).append("</td><td>");

		return statis;
	}

	/**
	 * 导出excel功能
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/index.excel", method = GET)
	public View excel(HttpServletRequest request, HttpServletResponse response,
			Long id) {

		Collection<Object[]> items = administrationCoreRuralService
				.excelValue(mybatch);
		return new AdRuralBasicSituationExcel(items, mybatch);

	}
}
