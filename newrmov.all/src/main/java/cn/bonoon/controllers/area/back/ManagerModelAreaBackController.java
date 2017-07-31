package cn.bonoon.controllers.area.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.area.ModelAreaDetail;
import cn.bonoon.controllers.area.ModelAreaHandleDetail;
import cn.bonoon.controllers.area.ModelAreaItem;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

@Controller
@RequestMapping("s/ml/area/back")
public class ManagerModelAreaBackController extends AbstractGridController<ModelAreaEntity, ModelAreaItem> {

	private ModelAreaService modelAreaService;
	
	@Autowired
	protected ManagerModelAreaBackController(ModelAreaService modelAreaService) {
		super(modelAreaService);
		this.modelAreaService = modelAreaService;
	}
	
	@Override
	@GridStandardDefinition(
			detailClass = ModelAreaHandleDetail.class,
			deleteOperation = false)
	@QueryExpression("x.status=3 and cityId=(select y.place.id from UnitEntity y where y.id={USER owner})")
	protected ModelAreaService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return modelAreaService;
	}
	
	protected VisibleScope getScope(ItemInfo item){
		return VisibleScope.GLOBAL;
	}
}
