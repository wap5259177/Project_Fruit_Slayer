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

@Controller
@RequestMapping("s/pls/show/spr5")
public class ProvinceCouncilPeripheraController extends AbstractPanelController{
	private String myBatch;
	@Autowired
	protected PeripheraRuralService statisticsService;
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
//		String batch = model.getParameter("batch");
		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		myBatch=batch;
		event.setVmPath("showstatistics/council-peripheral-rural");
		Collection<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items)).addObject("batch", batch);;
		
		String batchs = model.getParameter("batch");
		model.addObject("select", BatchHelper.batchSelect(batchs));
	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch){
		Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content", statistics(items));
		return "showstatistics/council-peripheral-rural-items";
	}
	
	private Collection<Object[]> getItems(LogonUser user, String batch) {
		return statisticsService.statistics(batch);
	}
	private Object statistics(Collection<Object[]> items) {
		StringBuilder statis = new StringBuilder();
		int i = 1;
		double C6 = 0, C7 = 0,C14 = 0,C15 = 0,C19 = 0,x30 = 0,x31 = 0,x32 = 0,x33 = 0,x34 = 0;
		int C8= 0, C9 = 0,C10 = 0,C11 = 0,C12 = 0,C13 = 0,C16 = 0,C17 = 0;
		int C18= 0,C20 = 0,C21 = 0,C22 = 0,x22 = 0,C23 = 0,C24 = 0,C25 = 0,C26 = 0,C27 = 0;
		int C28= 0, C29 = 0,x29=0,C30 = 0,C31 = 0,C32 = 0,C33 = 0,C34 = 0,C35 = 0,C36 = 0,C37=0,C38=0,C41=0;
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
			if(null != it[6]) statis.append(it[6]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[7]) statis.append(it[7]);
			statis.append("</td>");
			
			statis.append("<td>");
			if(null != it[45]) statis.append(it[45]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[46]) statis.append(it[46]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[47]) statis.append(it[47]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[48]) statis.append(it[48]);
			statis.append("</td>");
			
			
			if(it[45] instanceof Number){
				C36 += ((Number)it[45]).intValue();
			}
			if(it[46] instanceof Number){
				C37 += ((Number)it[46]).intValue();
			}
			if(it[47] instanceof Number){
				C38 += ((Number)it[47]).intValue();
			}
			if(it[48] instanceof Number){
				C41 += ((Number)it[48]).intValue();
			}
			
			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td></td><td></td><td>");
		statis.append(C36).append("</td><td>");
		statis.append(C37).append("</td><td>");
		statis.append(C38).append("</td><td>");
		statis.append(C41).append("</td></tr>");
		
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
			Object[] o2 = new Object[33-28+1+3];
			for (int i = 0; i < o.length; i++) {

				if (i == 3 ||i>=6&&i<=7||i < 49 && i > 44) {
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
		return new PeripheralRuralPlanningProgressRuralCouncilExcel(items, myBatch);

	}
}
