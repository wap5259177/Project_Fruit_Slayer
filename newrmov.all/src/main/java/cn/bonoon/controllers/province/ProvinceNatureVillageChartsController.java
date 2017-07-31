package cn.bonoon.controllers.province;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;

@Controller
@RequestMapping("s/pls/nvstv")
public class ProvinceNatureVillageChartsController extends AbstractPanelController{

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		event.setVmPath("pl-nvst-v");
	}

}
