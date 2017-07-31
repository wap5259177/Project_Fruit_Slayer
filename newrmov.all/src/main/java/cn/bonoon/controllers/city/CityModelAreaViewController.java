package cn.bonoon.controllers.city;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.AbstractModelAreaViewController;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.models.PanelModel;

/*
 * 台账查阅
 */
@Controller
@RequestMapping("s/ml/cmav")
public class CityModelAreaViewController extends AbstractModelAreaViewController{
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		LogonUser user = event.getUser();
		Long owner = user.getOwnerId();
		//找出这个市下面的所有片区
		List<Object[]> items = modelAreaService.cityModelArea(owner);
		
		model.addObject("items", items);
		event.setVmPath("applicant/area/city-view");
	}

}
