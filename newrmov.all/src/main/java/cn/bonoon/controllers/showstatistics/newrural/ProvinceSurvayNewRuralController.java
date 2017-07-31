package cn.bonoon.controllers.showstatistics.newrural;

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
import cn.bonoon.core.NewRuralService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;
@Controller
@RequestMapping("s/pls/show/scr4")
public class ProvinceSurvayNewRuralController extends AbstractPanelController{
	private String myBatch;
	@Autowired
	protected NewRuralService statisticsService;
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();

		int bi = model.intParameter("batch");
		event.setVmPath("showstatistics/survay-new-rural");
		String batch = BatchHelper.getValue(bi);
		myBatch=batch;
		Collection<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items)).addObject("batch", batch);
		
		String batchs = model.getParameter("batch");
		model.addObject("select", BatchHelper.batchSelect(batchs));
	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch){
		Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content", statistics(items));
		return "showstatistics/survay-new-rural-items";
	}
	
	private Collection<Object[]> getItems(LogonUser user, String batch) {
		return statisticsService.statistics(batch);
	}
	
	private Object statistics(Collection<Object[]> items) {
		StringBuilder statis = new StringBuilder();
		int i = 1;
		double B5 = 0,B6 = 0,B14 = 0;
		int B7 = 0,B8 = 0, B9 = 0,X1 = 0,X2 = 0,X3 = 0,X4 = 0,X5 = 0,X6 = 0,B11 = 0,B12 = 0,B15 = 0,B291 = 0,B30 = 0;
		int B16 = 0,B17 = 0,B18 = 0,B19 = 0,B20 = 0,B21 = 0,B22 = 0,B23 = 0,B24 = 0,B25 = 0,B26 = 0,B27 = 0,B28 = 0,B29 = 0;
		int B53 = 0,B54 = 0,B56 = 0,X57 = 0,X58 = 0,X59 = 0,X60 = 0,B211 = 0;
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
			
//			if(it[8] instanceof Number){
//				B5 += ((Number)it[8]).doubleValue();
//			}
//			if(it[9] instanceof Number){
//				B6 += ((Number)it[9]).doubleValue();
//			}
			//（四）村民理事会
			statis.append("<td>");
			if(null != it[40]) statis.append(it[40]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[41]) statis.append(it[41]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[42]) statis.append(it[42]);
			statis.append("</td>");
			//（五）民生问题调查梳理情况
			statis.append("<td>");
			if(null != it[43]) statis.append(it[43]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[44]) statis.append(it[44]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[45]) statis.append(it[45]);
			statis.append("</td>");
			statis.append("<td>");
			if(null != it[46]) statis.append(it[46]);
			statis.append("</td>");
			
			
			if("是".equals(it[40])){
				B53 += 1;
			}
			if(it[41] instanceof Number){
				B54 += ((Number)it[41]).intValue();
			}
			if("是".equals(it[42])){
				B56 += 1;
			}
			
			if(it[43] instanceof Number){
				X57 += ((Number)it[43]).intValue();
			}
			if(it[44] instanceof Number){
				X58 += ((Number)it[44]).intValue();
			}
			if(it[45] instanceof Number){
				X59 += ((Number)it[45]).intValue();
			}
			if(it[46] instanceof Number){
				X60 += ((Number)it[46]).intValue();
			}	
			
        	
			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td></td><td></td><td>");
		statis.append(B53).append("</td><td>");
		statis.append(B54).append("</td><td>");
		statis.append(B56).append("</td><td>");
		statis.append(X57).append("</td><td>");
		statis.append(X58).append("</td><td>");
		statis.append(X59).append("</td><td>");
		statis.append(X60).append("</td><td>");
		
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
			Object[] o2 = new Object[14];
			for (int i = 0; i < o.length; i++) {

				if (i == 3 ||i>=6&&i<=7||i < 47 && i > 39) {
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
		return new VillagersCouncilLivelihoodIssuesExcel(items, myBatch);

	}
}

	

