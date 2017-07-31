package cn.bonoon.controllers.showstatistics.administration;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.controllers.quarter.ProvinceQuarterReportStatExcelView;
import cn.bonoon.core.AdministrationCoreRuralService;
import cn.bonoon.entities.ModelAreaQuarterBatch;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/pls/show/sar2")
public class ProvinceAdvanAdministrationController extends  AbstractPanelController{

	@Autowired
	protected AdministrationCoreRuralService administrationCoreRuralService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		Long ownerId = event.getUser().getOwnerId();
		PanelModel model = event.getModel();
		event.setVmPath("showstatistics/advan-area-administration");
		event.getModel().addObject("ownerId", ownerId);
		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		
		Collection<Object[]> items = administrationCoreRuralService.statistics(batch);
		event.getModel().addObject("content", statistics(items)).addObject("batch", batch);
		String batchs = model.getParameter("batch");
		model.addObject("select", BatchHelper.batchSelect(batchs));
	}
	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch){
		Collection<Object[]> items = administrationCoreRuralService.statistics(BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content", statistics(items));
		return "showstatistics/advan-area-administration-items";
	}

//	private Collection<Object[]> getItems(LogonUser user, String batch) {
//		return administrationCoreRuralService.statistics(batch);
//	}

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
			//资源优势
			if(it[30].equals("是")){
				B31 += 1;
			}
			if(it[31].equals("是")){
				B32 += 1;
			}
			if(it[32].equals("是")){
				B33 += 1;
			}
			if(it[33].equals("是")){
				B34 += 1;
			}
			if(it[34].equals("是")){
				B35 += 1;
			}
			if(it[35].equals("是")){
				B36 += 1;
			}
			if(it[36].equals("是")){
				B37 += 1;
			}
			if(it[37] instanceof Number){
				B38 += ((Number)it[14]).intValue();
			}
			
			statis.append("</tr>");
		}
		
		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td></td><td>");
		statis.append(B31).append("</td><td>");
		statis.append(B32).append("</td><td>");
		statis.append(B33).append("</td><td>");
		statis.append(B34).append("</td><td>");
		statis.append(B35).append("</td><td>");
		statis.append(B36).append("</td><td>");
		statis.append(B37).append("</td><td>");
		statis.append(B38).append("</td><td>");
		return statis;
	}
	@RequestMapping(value = "{!key}/index.excel", method = GET)
	public View excel(HttpServletRequest request, HttpServletResponse response, 
			Long ownerId,String batch){
		//batchName = StringHelper.get(batchName, "一");
		//获取
		//int bi = this.event.getModel().intParameter("batch");
		//获取统计截止时间
		//
		batch = BatchHelper.batch(batch);
        String batchName = "珠三角".equals(batch)?"珠三角批次":"批次"+batch;
        Collection<Object[]> items = administrationCoreRuralService.statistics(batch);
		return new AdministrationAdvanExelView(items,batchName);
	}
}


