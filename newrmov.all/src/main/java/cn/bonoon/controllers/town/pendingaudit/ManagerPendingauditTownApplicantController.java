package cn.bonoon.controllers.town.pendingaudit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.town.AbstractTownApplicantController;
import cn.bonoon.controllers.town.TownApplicantDetail;
import cn.bonoon.core.TownApplicantService;
import cn.bonoon.entities.TownApplicantEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.BaseButtonResolver;
import cn.bonoon.kernel.web.handlers.DialogUpdateHandler;

 
@Controller
@RequestMapping("s/ml/audit/town") 
public class ManagerPendingauditTownApplicantController extends AbstractTownApplicantController{
	private final TownApplicantService taService;
	@Autowired
	public ManagerPendingauditTownApplicantController(TownApplicantService taService) {
		super(taService);
		this.taService = taService;  
	}   
	

	@GridStandardDefinition(detailClass = TownApplicantDetail.class, deleteOperation = false, autoOperation = false)
	@QueryExpression("x.status>3 and x.cityId=(select y.place.id from UnitEntity y where y.id={USER owner})")
	@Override
	protected TownApplicantService initLayoutGrid(LayoutGridRegister register) throws Exception {
		TownApplicantAuditHandler ahandler = new TownApplicantAuditHandler(0, taService, autoManager, TownApplicantAuditor.class);
		DialogUpdateHandler<TownApplicantEntity> dh = new DialogUpdateHandler<>(register, ahandler);
		
		BaseButtonResolver br = dh.register(register.button());
		br.setName("审核");
		br.ordinal(0);
		return service;
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.GLOBAL;		//严格限制在域范围内
	}
}
