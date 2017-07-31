package cn.bonoon.controllers.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bonoon.core.ProjectReportService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;

/**
 * 市级各个资金统计报表
 * @author Administrator
 *
 */
@Controller
@RequestMapping("s/cls/project/report/funds/used")
public class CityFundsController extends AbstractPanelController{

	@Autowired
	private ProjectReportService projectReportService;
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		Long ownerId = event.getUser().getOwnerId();
		event.getModel().addObject("ownerId", ownerId);
		event.setVmPath("fund-report/city-funds-statistics");
	}

	
	@RequestMapping(value = "!{mid}/investment.statistics", method = RequestMethod.POST)
	public String statisticsFunds(Model model,String batch,Long ownerId) {
		List<Object[]> finishedFunds = projectReportService.getCityFinishedFundTotal(ownerId, batch);
		List<Object[]> predictFunds = projectReportService.getCityPredictFundTotal(ownerId, batch);
		if(predictFunds!=null){
			Object[] os = predictFunds.get(0);
			if(os[0]==null){
				model.addAttribute("pre_static", 0);//没有统计的数据
			}else{
				model.addAttribute("pre_static", 1);//有
				model.addAttribute("Investment", os);
			}
		}
		if(finishedFunds!=null){
			Object[] os = finishedFunds.get(0);
			if(os[0]==null){
				model.addAttribute("fin_static", 0);
			}else{
				model.addAttribute("fin_static", 1);
				model.addAttribute("finishedInverstment", os);
			}
		}
		return "fund-report/city-founds-static-content";
	}
	
}
