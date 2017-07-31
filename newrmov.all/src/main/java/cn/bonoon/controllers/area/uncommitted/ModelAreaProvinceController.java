//package cn.bonoon.controllers.area.uncommitted;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import cn.bonoon.controllers.area.ModelAreaItem;
//import cn.bonoon.core.ModelAreaService;
//import cn.bonoon.entities.ModelAreaEntity;
//import cn.bonoon.kernel.annotations.QueryExpression;
//import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
//import cn.bonoon.kernel.web.controllers.AbstractGridController;
//
//@Controller
//@RequestMapping("s/cl/area/uncommitted")
//public class ModelAreaProvinceController extends AbstractGridController<ModelAreaEntity, ModelAreaItem> {
//
//	private ModelAreaService modelAreaService;
//	@Autowired
//	protected ModelAreaProvinceController(ModelAreaService modelAreaService) {
//		super(modelAreaService);
//		this.modelAreaService = modelAreaService;
//	}
//	
//	@Override
//	@GridStandardDefinition(
//			insertClass = ModelAreaEditor.class, 
//			updateClass = ModelAreaUpdate.class)
//	@QueryExpression("x.status=0")
//	protected ModelAreaService initLayoutGrid(LayoutGridRegister register) throws Exception {
//		return modelAreaService;
//	}
//}
