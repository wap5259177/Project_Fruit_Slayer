package cn.bonoon.controllers.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.AdministrationCoreRuralService;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.AdministrationRuralEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/pls/aim")
public class ProvinceAnnualIncomeController extends AbstractPanelController{
	@Autowired
	protected AdministrationCoreRuralService administrationCoreRuralService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		Long id = event.getUser().getOwnerId();
		
		
		List<AdministrationRuralEntity> items  = administrationCoreRuralService.annualIncome(id);
		
		StringBuilder statis = new StringBuilder();
		
		int i = 1;
		
		for(AdministrationRuralEntity are:items){
			
			String county = are.getModelArea().getCounty();
			String batch  = are.getModelArea().getBatch();
			String adminRural = are.getName();
			String newr = null;
			double c_13 = are.getAnnualIncome_13();
			double c_14 = are.getAnnualIncome_14();
			double c_15 = are.getAnnualIncome_15();
			double c_16 = are.getAnnualIncome_16();
			double c_17 = are.getAnnualIncome_17();
			
			if (are instanceof AdministrationCoreRuralEntity) {
				newr="是";
			}else{
				newr="否";
			}
			
			statis.append("<tr><td>").append(i++).append("</td>");//序号
			
			statis.append("<td>");
			if(null != batch) statis.append(batch);
			statis.append("</td>");
			statis.append("<td>");
			if(null != county) statis.append(county);
			statis.append("</td>");
			statis.append("<td>");
			if(null != adminRural) statis.append(adminRural);
			statis.append("</td>");
			statis.append("<td>").append(c_13).append("</td>");
			statis.append("<td>").append(c_14).append("</td>");
			statis.append("<td>").append(c_15).append("</td>");
			statis.append("<td>").append(c_16).append("</td>");
			statis.append("<td>").append(c_17).append("</td>");
			statis.append("<td>").append(newr).append("</td>");
			
			statis.append("</tr>");
		}
		
		
		model.addObject("content", statis);
		event.setVmPath("statistics/annual-income");
	
	}
}
