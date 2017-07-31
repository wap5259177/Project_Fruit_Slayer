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

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
@Controller
@RequestMapping("s/pls/show/spj3")
public class ProvincePlanProjectController extends AbstractPanelController{
	private String myBatch;
	@Autowired
	protected ProjectService statisticsService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
//		String batch = model.getParameter("batch");
		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		myBatch=batch;
		event.setVmPath("showstatistics/plan-area-project");
		Collection<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items)).addObject("batch", batch);
		
		String batchs = model.getParameter("batch");
		model.addObject("select", BatchHelper.batchSelect(batchs));
	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch){
		Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content", statistics(items));
		return "showstatistics/plan-area-project-items";
	}

	private Collection<Object[]> getItems(LogonUser user, String batch) {
		return statisticsService.statistics(batch);
	}
	private Object statistics(Collection<Object[]> items) {
		StringBuilder statis = new StringBuilder();
		int i = 1;
		int E1 = 0, E2 = 0, E3 = 0, E4 = 0, E5 = 0, E6 = 0, E7 = 0, E8 = 0;
		double E9 = 0, E10 = 0, E11 = 0, E12 = 0, E131 = 0,E132 = 0,E14 = 0, E141 = 0,E15 = 0;
		double E16 = 0, E17 = 0, E18 = 0, E19 = 0, E201 = 0,E202 = 0, E21 = 0,E211 = 0, E22 = 0, E23 = 0, E24 = 0, E25 = 0;
		double E26 = 0, E27 = 0, E28 = 0, E29 = 0, E30 = 0, E31 = 0;
		for(Object[] it : items){
			statis.append("<tr><td>").append(i++).append("</td>");
			
			statis.append("<td>");
			if(null != it[0]) statis.append(it[0]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[1]) statis.append(it[1]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[2]) statis.append(it[2]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[3]) statis.append(it[3]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[4]) statis.append(it[4]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[5]) statis.append(it[5]);
			statis.append("</td>");
			
			statis.append("<td>");
			if(null != it[21]) statis.append(it[21]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[22]) statis.append(it[23]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[24]) statis.append(it[24]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[25]) statis.append(it[25]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[26]) statis.append(it[26]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[27]) statis.append(it[27]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[28]) statis.append(it[28]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[29]) statis.append(it[29]);
			statis.append("</td>");
			
			
			
			E141 = add(E141,it[21]);
			E15 = add(E15,it[22]);
			E16 = add(E16,it[23]);
			E17 = add(E17,it[24]);
			E18 = add(E18,it[25]);
			E19 = add(E19,it[26]);
			E201 = add(E201,it[27]);
			E202 = add(E202,it[28]);
			E21 = add(E21,it[29]);
			
			
			
			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td>");
		
		statis.append(E141).append("</td><td>");
		statis.append(E15).append("</td><td>");
		statis.append(E16).append("</td><td>");
		statis.append(E17).append("</td><td>");
		statis.append(E18).append("</td><td>");
		statis.append(E19).append("</td><td>");
		statis.append(E201).append("</td><td>");
		statis.append(E202).append("</td><td>");
		statis.append(E21).append("</td><td>");
		
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
			Object[] o2 = new Object[29-21+1+1];
			for (int i = 0; i < o.length; i++) {

				if (i == 3 ||i < 30 && i > 20) {
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
		return new TotalInvestmentProjectExcel(items, myBatch);

	}
}
	
