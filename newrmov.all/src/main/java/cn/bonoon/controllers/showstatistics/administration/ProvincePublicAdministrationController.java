package cn.bonoon.controllers.showstatistics.administration;

import static cn.bonoon.util.DoubleHelper.add;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;
import java.util.Iterator;
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
import cn.bonoon.controllers.showstatistics.AbstractExcelUtil;
import cn.bonoon.controllers.showstatistics.CountUtil;
import cn.bonoon.core.AdministrationCoreRuralService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;
import cn.bonoon.util.DoubleHelper;

@Controller
@RequestMapping("s/pls/show/sar3")
public class ProvincePublicAdministrationController extends  AbstractPanelController{
String mybatch="一";
	@Autowired
	protected AdministrationCoreRuralService administrationCoreRuralService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
//		event.setVmPath("showstatistics/public-area-administration");
		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		mybatch=batch;
//		Collection<Object[]> items= 		getItems(event.getUser(), batch);
//		event.getModel().addObject("content", statistics(items)).addObject("batch", batch);
//		String batchs = model.getParameter("batch");
//		model.addObject("select", BatchHelper.batchSelect(batchs));
		Collection<Object[]> items=	administrationCoreRuralService.excelValue3(mybatch);
	
	
		RuralPublicServiceSituationExcel.summaryStatistics(items);

		//对第14列做统计 汇总统计需要用
	List<Object[]> list=	(List<Object[]>)items;
	int sum=0;
		for(int i=0;i<list.size();i++){
			
			if(i==list.size()-1){
				break;
			};
			if(list.get(i)[12].toString().equals("是")){	
			sum+=1;
			}
		}
		list.get(list.size()-1)[12]=sum;
		
		CountUtil.setShowWhat(event, items, batch, "showstatistics/public-area-administration");
	}
	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch){
		Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content", statistics(items));
		return "showstatistics/public-area-administration";
	}

	private Collection<Object[]> getItems(LogonUser user, String batch) {
		return administrationCoreRuralService.statistics(batch);
	}

	private Object statistics(Collection<Object[]> items) {
		StringBuilder statis = new StringBuilder();
		int i = 1;
		double B5 = 0, B6 = 0,x20 = 0,x21 = 0,x22 = 0,x23 = 0,x24 = 0,x25 = 0,x26 = 0,x27 = 0,x28 = 0,x29 = 0;
		int  B4 = 0,B7 = 0,B8 = 0,B9 = 0,B10 = 0,B11 = 0,B12 = 0,B13 = 0,B14 = 0,B15 = 0,B38 = 0,B461 = 0;
		int B39 = 0,B40 = 0,B41 = 0,B42 = 0,B43 = 0;
		double B391 = 0,B401 = 0,B411 = 0,B421 = 0,B431 = 0;
		int B16 = 0,B31 = 0,B32 = 0,B33 = 0,B34 = 0,B35 = 0,B36 = 0,B37 = 0,B44 = 0,B45 = 0;
		int  B46 = 0,B47 = 0,B48 = 0,B49 = 0,B50 = 0;
		
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
				if(null != it[38]) statis.append(it[38]);
				statis.append("</td>");
				statis.append("<td>");
				if(null != it[39]) statis.append(it[39]);
				statis.append("</td>");
				statis.append("<td>");
				if(null != it[40]) statis.append(it[40]);
				statis.append("</td>");
				statis.append("<td>");
				if(null != it[41]) statis.append(it[41]);
				statis.append("</td>");
				statis.append("<td>");
				if(null != it[42]) statis.append(it[42]);
				statis.append("</td>");
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
				statis.append("<td>");
				if(null != it[47]) statis.append(it[47]);
				statis.append("</td>");
				statis.append("<td>");
				if(null != it[48]) statis.append(it[48]);
				statis.append("</td>");

				if(it[38] instanceof Number){
					B39 += ((Number)it[38]).intValue();
				}
				
				B391 = add(B391,it[39]);
				
				if(it[40] instanceof Number){
					B40 += ((Number)it[40]).intValue();
				}
				
				B401 = add(B401,it[41]);
				
				if(it[42] instanceof Number){
					B41 += ((Number)it[42]).intValue();
				}
				
				B411 = add(B411,it[43]);
				
				if(it[44] instanceof Number){
					B42 += ((Number)it[44]).intValue();
				}
			
				if(it[45]!=null&&it[45] instanceof Number){
					B421=DoubleHelper.add(Double.parseDouble(it[45].toString()), B421);
				}
				
				if(it[46] instanceof Number){
					B43 += ((Number)it[46]).intValue();
				}
//				if(it[47] instanceof Number){
//					B431 += ((Number)it[47]).doubleValue();
//				}
				
				B431 = add(B431,it[47]);
				
//				if(it[48] instanceof Number){
//					B44 += ((Number)it[48]).doubleValue();
//				}
				if("是".equals(it[48])){
					B44 += 1;
				}
			statis.append("</tr>");
		}
		
		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td></td><td>");
		statis.append(B39).append("</td><td>");
		statis.append(B391).append("</td><td>");
		statis.append(B40).append("</td><td>");
		statis.append(B401).append("</td><td>");
		statis.append(B41).append("</td><td>");
		statis.append(B411).append("</td><td>");
		statis.append(B42).append("</td><td>");
		statis.append(B421).append("</td><td>");
		statis.append(B43).append("</td><td>");
		statis.append(B431).append("</td><td>");
		statis.append(B44).append("</td><td>");
		return statis;
	}
	
	/**
	 * 导出excel功能
	 */
	@ResponseBody
	@RequestMapping(value = "!{key}/index.excel", method = GET)
	public View excel(HttpServletRequest request, HttpServletResponse response,
			Long id) {
	
		Collection<Object[]> items=	administrationCoreRuralService.excelValue3(mybatch);
		return new RuralPublicServiceSituationExcel(items,mybatch);

	}
}


