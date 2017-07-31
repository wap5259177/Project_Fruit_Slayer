package cn.bonoon.controllers.constructionresults;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;

@Controller
@RequestMapping("s/html/distribution")
public class DistributionController extends AbstractPanelController{
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		event.setVmPath("applicant/constructionresults/distribution");
	}
}
