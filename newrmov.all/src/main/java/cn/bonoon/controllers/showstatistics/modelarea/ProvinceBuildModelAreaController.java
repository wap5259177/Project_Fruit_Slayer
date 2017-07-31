package cn.bonoon.controllers.showstatistics.modelarea;

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
import cn.bonoon.controllers.showstatistics.newrural.AdRuralBasicSituationExcel;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/pls/show/sma3")
public class ProvinceBuildModelAreaController extends AbstractPanelController{
	private String myBatch;
	@Autowired
	protected ModelAreaService statisticsService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		
		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		myBatch=batch;
		Collection<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items)).addObject("batch", batch);
		
		String batchs = model.getParameter("batch");
		model.addObject("select", BatchHelper.batchSelect(batchs));
		event.setVmPath("showstatistics/total-model-area");
		
	}
	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch){
		Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content", statistics(items));
		return "showstatistics/total-model-area-items";
	}
	private Collection<Object[]> getItems(LogonUser user, String batch) {
		return statisticsService.buildStatistics(batch);
	}
	private Object statistics(Collection<Object[]> items) {
		StringBuilder statis = new StringBuilder();
		int i = 1;
//		for(Object[] it : items){
//			statis.append("<tr><td>").append(i++).append("</td>");//序号
//			for(Object obj : it){
//				if(null!=obj){
//				  statis.append("<td>").append(obj).append("</td>");
//				}else{
//					statis.append("<td></td>");
//				}
//			}
//			statis.append("</tr>");
//		}

		double A1 = 0,A19 = 0, A22 = 0, A26 = 0;
		int A2 = 0, A3 = 0, A4 = 0, A5 = 0, A6 = 0, A7 = 0, A8 = 0, A9 = 0;
		double x13 = 0, x14 = 0, x15 = 0, x16 = 0,z13= 0, z14 = 0, z15 = 0, z16 = 0,z17 = 0,x17 = 0;
		int A16 = 0, A17 = 0, A18 = 0,  A20 = 0, A21 = 0;
		int A24 = 0, A25 = 0, A27 = 0, A28 = 0, A29 = 0,A23 = 0;
		
		for(Object[] it : items){
			statis.append("<tr><td>").append(i++).append("</td>");
			for(Object obj : it){
				statis.append("<td>");
				if(null != obj) statis.append(obj);
				statis.append("</td>");
			}
			A22 = add(A22, it[6]);
  			
  			if("有".equals(it[7])){
				A23 += 1;
			}
  			
  			if(it[8] instanceof Number){
  				A24 += ((Number)it[8]).intValue();
  			}
  			if(it[9] instanceof Number){
  				A25 += ((Number)it[9]).intValue();
  			}
  				A26 = add(A26, it[10]);
//  			if(it[33] instanceof Number){
//  			}
  			if(it[11] instanceof Number){
  				A27 += ((Number)it[11]).intValue();
  			}
  			if(it[12] instanceof Number){
  				A28 += ((Number)it[12]).intValue();
  			}
  			if(it[13] instanceof Number){
  				A29 += ((Number)it[13]).intValue();
  			}
 
			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td>");
		statis.append(df.format(A22)).append("</td><td>");
		statis.append(A23).append("</td><td>");
		statis.append(A24).append("</td><td>");
		statis.append(A25).append("</td><td>");
		statis.append(df.format(A26)).append("</td><td>");
		statis.append(A27).append("</td><td>");
		statis.append(A28).append("</td><td>");
		statis.append(A29).append("</td><td></td></tr>");
		
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

		Collection<Object[]> items = statisticsService.buildStatisticsNew(myBatch);
		List<Object[]> itemsNew = new ArrayList<Object[]>();
//		
//		for (Object[] o : items) {
//			Object[] o2 = new Object[16];
//			for (int i = 0; i < o.length; i++) {
//
//				if (i == 3 || i < 14 && i > 5) {
//					for (int i2 = 0; i2 < o2.length; i2++) {
//						if (o2[i2] == null) {
//							o2[i2] = o[i];
//							break;
//						}
//					}
//				}
//			}
//			itemsNew.add(o2);
//		}
//		items = itemsNew;
		return new MoldeAreaConstructionBasicSituationExcel(items, myBatch);

	}
}
