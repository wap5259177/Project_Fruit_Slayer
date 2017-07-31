package cn.bonoon.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.ProjectReportService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;

/**
 * 县级各个资金统计报表
 * 
 *
 */

@Controller
@RequestMapping("s/cl/project/report/funds/used")

public class FundsPredictUsedController extends AbstractPanelController { 

	
	@Autowired
	private ProjectReportService projectReportService;
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		Long ownerId = event.getUser().getOwnerId();
		
		List<Object[]> founds = projectReportService.getAllKindsOfFoundTotal(ownerId);
		List<Object[]> finishedFounds = projectReportService.getFinishedAllKindsOfFoundTotal(ownerId);
		if(founds!=null){
			Object[] os = founds.get(0);
			if(os[0]==null){
				event.getModel().addObject("pre_static",0);
			}else{
				event.getModel().addObject("pre_static",1);
				event.getModel().addObject("Investment", os);
			}
		}
		if(finishedFounds!=null){
			Object[] os = finishedFounds.get(0);
			if(os[0]==null){
				event.getModel().addObject("fin_static",0);
			}else{
				event.getModel().addObject("fin_static",1);
				event.getModel().addObject("finishedInverstment", os);
			}
		}
		event.setVmPath("fund-report/county-predict-founds");
	}
}
