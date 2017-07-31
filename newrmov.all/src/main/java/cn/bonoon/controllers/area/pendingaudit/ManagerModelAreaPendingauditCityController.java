package cn.bonoon.controllers.area.pendingaudit;


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
import cn.bonoon.kernel.web.controllers.BaseButtonResolver;
import cn.bonoon.kernel.web.handlers.DialogUpdateHandler;

@Controller
@RequestMapping("s/ml/area/pendingaudit")
public class ManagerModelAreaPendingauditCityController extends AbstractGridController<ModelAreaEntity, ModelAreaItem> {

	private ModelAreaService modelAreaService;
	
	@Autowired  
	protected ManagerModelAreaPendingauditCityController(ModelAreaService modelAreaService) {
		super(modelAreaService);  
		this.modelAreaService = modelAreaService; 
	}
	//没有显示记录，ownerId
	@Override
	@GridStandardDefinition(
			detailClass = ModelAreaHandleDetail.class,
			deleteOperation = false)
	@QueryExpression("x.status=4 and cityId=(select y.place.id from UnitEntity y where y.id={USER owner})")
	protected ModelAreaService initLayoutGrid(LayoutGridRegister register) throws Exception {
		ModelAreaAuditHandler mhandler = new ModelAreaAuditHandler(0, modelAreaService, autoManager, ModelAreaAuditor.class);
		DialogUpdateHandler<ModelAreaEntity> dh = new DialogUpdateHandler<>(register, mhandler);
		BaseButtonResolver br = dh.register(register.button());
		br.setName("审核");
		br.ordinal(0);
		return modelAreaService;
	}
	
	//此方法可以在查询时不要owenerId
	protected VisibleScope getScope(ItemInfo item){
		return VisibleScope.GLOBAL;
	}
}

