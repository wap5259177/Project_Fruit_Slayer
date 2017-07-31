package cn.bonoon.controllers.area.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.area.ModelAreaHandleDetail;
import cn.bonoon.controllers.area.ModelAreaItem;
import cn.bonoon.controllers.area.ModelAreaUpdate;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

@Controller
@RequestMapping("s/cl/area/back")
public class ReportingModelAreaBackController extends AbstractGridController<ModelAreaEntity, ModelAreaItem> {

	private ModelAreaService modelAreaService;
	
	@Autowired
	protected ReportingModelAreaBackController(ModelAreaService modelAreaService) {
		super(modelAreaService);
		this.modelAreaService = modelAreaService;
	}
	
	@Override
	@GridStandardDefinition(
			detailClass = ModelAreaHandleDetail.class,
			updateClass = ModelAreaUpdate.class,
			deleteOperation = false)
	@QueryExpression("x.status=3")
	protected ModelAreaService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return modelAreaService;
	}
}
