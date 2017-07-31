package cn.bonoon.controllers.showstatistics.newrural;

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
import cn.bonoon.core.NewRuralService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;
@Controller
@RequestMapping("s/pls/show/scr3")
public class ProvinceRebuildNewRuralControler extends AbstractPanelController{
	private String myBatch;
	@Autowired
	protected NewRuralService statisticsService;
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();

		int bi = model.intParameter("batch");
		event.setVmPath("showstatistics/rebuild-new-rural");
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
		return "showstatistics/rebuild-new-rural-items";
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
				
				//（三）农村旧房整治情况
				statis.append("<td>");
				if(null != it[32]) statis.append(it[32]);
				statis.append("</td>");
				statis.append("<td>");
				if(null != it[33]) statis.append(it[33]);
				statis.append("</td>");
				statis.append("<td>");
				if(null != it[34]) statis.append(it[34]);
				statis.append("</td>");
				statis.append("<td>");
				if(null != it[35]) statis.append(it[35]);
				statis.append("</td>");
				statis.append("<td>");
				if(null != it[36]) statis.append(it[36]);
				statis.append("</td>");
				statis.append("<td>");
				if(null != it[37]) statis.append(it[37]);
				statis.append("</td>");
				statis.append("<td>");
				if(null != it[38]) statis.append(it[38]);
				statis.append("</td>");
				statis.append("<td>");
				if(null != it[39]) statis.append(it[39]);
				statis.append("</td>");
				
			
//			if(it[8] instanceof Number){
//				B5 += ((Number)it[8]).doubleValue();
//			}
//			if(it[9] instanceof Number){
//				B6 += ((Number)it[9]).doubleValue();
//			}
			
				if(it[32] instanceof Number){
					B24 += ((Number)it[32]).intValue();
				}
				if(it[33] instanceof Number){
					B25 += ((Number)it[33]).intValue();
				}
				if(it[34] instanceof Number){
					B26 += ((Number)it[34]).intValue();
				}
				if("是".equals(it[35])){
					B27 += 1;
				}
				if("是".equals(it[36])){
					B28 += 1;
				}
				if(it[37] instanceof Number){
					B29 += ((Number)it[37]).intValue();
				}
				if(it[38] instanceof Number){
					B291 += ((Number)it[38]).intValue();
				}
				if("是".equals(it[39])){
					B30 += 1;
				}
        	
			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td></td><td></td><td>");
		statis.append(B24).append("</td><td>");
		statis.append(B25).append("</td><td>");
		statis.append(B26).append("</td><td>");
		statis.append(B27).append("</td><td>");
		statis.append(B28).append("</td><td>");
		statis.append(B29).append("</td><td>");
		statis.append(B291).append("</td><td>");
		statis.append(B30).append("</td><td>");
		
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

				if (i == 3 ||i>=6&&i<=7||i < 40 && i > 31) {
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
		return new OldHouseRenovationExcel(items, myBatch);

	}
}
