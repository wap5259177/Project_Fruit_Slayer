package cn.bonoon.controllers.county;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.AbstractModelAreaViewController;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/cl/cmav")
public class CountyModelAreaViewController extends AbstractModelAreaViewController{

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		LogonUser user = event.getUser();
		Long owner = user.getOwnerId();
		//找出这个市下面的所有片区
		ModelAreaEntity items = modelAreaService.getByOnwer(owner);
		
		event.setVmPath(__detail(model.getRequest(), model, user, items.getId()));
	}
}
