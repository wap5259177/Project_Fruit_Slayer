package cn.bonoon.controllers.village.pendingaudit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.village.AbstractVillageApplicantController;
import cn.bonoon.controllers.village.VillageApplicantDetail;
import cn.bonoon.core.VillageApplicantService;
import cn.bonoon.entities.VillageApplicantEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.BaseButtonResolver;
import cn.bonoon.kernel.web.handlers.DialogUpdateHandler;
 

@Controller
@RequestMapping("s/ml/village/pendingaudit")
public class ManagerPendingauditVillageApplicantController extends AbstractVillageApplicantController{
	private final VillageApplicantService vaService;
	
	@Autowired
	public ManagerPendingauditVillageApplicantController(VillageApplicantService vaService) {
		super(vaService); 
		this.vaService = vaService; 
	}  
 
	@GridStandardDefinition(detailClass = VillageApplicantDetail.class, deleteOperation = false, autoOperation = false)
	@QueryExpression("x.status>3 and x.cityId=(select y.place.id from UnitEntity y where y.id={USER owner})")
	@Override
	protected VillageApplicantService initLayoutGrid(LayoutGridRegister register) throws Exception {
		VillageApplicantAuditHandler ahandler = new VillageApplicantAuditHandler(0, vaService, autoManager, VillageApplicantAuditor.class);
		DialogUpdateHandler<VillageApplicantEntity> dh = new DialogUpdateHandler<>(register, ahandler);
		BaseButtonResolver br = dh.register(register.button());
		
		br.setName("审核");
		br.ordinal(0);
		return service;
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.GLOBAL;//严格限制在域范围内
	}
}
