package cn.bonoon.controllers.area.finish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.area.ModelAreaHandleDetail;
import cn.bonoon.controllers.area.ModelAreaItem;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

@Controller
@RequestMapping("s/cl/area/finish")
public class ReportingModelAreaFinishController extends AbstractGridController<ModelAreaEntity, ModelAreaItem> {

	private ModelAreaService modelAreaService;
	
	@Autowired
	protected ReportingModelAreaFinishController(ModelAreaService modelAreaService) {
		super(modelAreaService);
		this.modelAreaService = modelAreaService;
	}
	
	@Override
	@GridStandardDefinition(
			detailClass = ModelAreaHandleDetail.class,
			deleteOperation = false)
	@QueryExpression("x.status=1")
	protected ModelAreaService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return modelAreaService;
	}
}
