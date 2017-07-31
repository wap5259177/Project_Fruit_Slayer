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
@RequestMapping("s/pls/show/spr3")
public class ProvinceRebuildPeripheraController extends AbstractPanelController{
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
		event.setVmPath("showstatistics/rebuild-peripheral-rural");
		Collection<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items)).addObject("batch", batch);;
		
		String batchs = model.getParameter("batch");
		model.addObject("select", BatchHelper.batchSelect(batchs));
	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch){
		Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content", statistics(items));
		return "showstatistics/rebuild-peripheral-rural-items";
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
			if(null != it[28]) statis.append(it[28]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[29]) statis.append(it[29]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[30]) statis.append(it[30]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[31]) statis.append(it[31]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[32]) statis.append(it[32]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[33]) statis.append(it[33]);
			statis.append("</td>");
			
			
			if(it[28] instanceof Number){
				C25 += ((Number)it[28]).intValue();
			}
			if(it[29] instanceof Number){
				C26 += ((Number)it[29]).intValue();
			}
			if(it[30] instanceof Number){
				C27 += ((Number)it[30]).intValue();
			}
			if(it[31] instanceof Number){
				C28 += ((Number)it[31]).intValue();
			}
			if(it[32] instanceof Number){
				C29 += ((Number)it[32]).intValue();
			}
			if(it[33] instanceof Number){
				x29 += ((Number)it[33]).intValue();
			}
			
			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td></td><td></td><td>");
		statis.append(C25).append("</td><td>");
		statis.append(C26).append("</td><td>");
		statis.append(C27).append("</td><td>");
		statis.append(C28).append("</td><td>");
		statis.append(C29).append("</td><td>");
		statis.append(x29).append("</td><td>");
		
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

				if (i == 3 ||i>=6&&i<=7||i < 34 && i > 27) {
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
		return new PeripheralRuralOldHouseExcel(items, myBatch);

	}
}
