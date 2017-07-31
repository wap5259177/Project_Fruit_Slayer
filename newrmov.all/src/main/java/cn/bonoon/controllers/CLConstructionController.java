package cn.bonoon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;

/**
 * 
 * @author jackson
 *
 */
@Controller
@RequestMapping("s/cl/construction")
public class CLConstructionController extends AbstractPanelController{

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		event.setVmPath("construction");
	}

}
