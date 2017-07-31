package cn.bonoon.controllers.felicity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.FelicityVillageService;
import cn.bonoon.entities.FelicityVillageEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

/**
 * 幸福村居村的基本信息
 * @author jackson
 *
 */
@Controller
@RequestMapping("/s/cl/bfv")
public class FelicityVillageController extends AbstractGridController<FelicityVillageEntity,FelicityVillageItem>{

	private FelicityVillageService felicityVillageService;
	
	@Autowired
	protected FelicityVillageController(FelicityVillageService felicityVillageService) {
		super(felicityVillageService);
		this.felicityVillageService = felicityVillageService;
	}
	@Override
	@GridStandardDefinition(
			autoOperation = false, 
			deleteOperation = false,
			detailClass = FelicityVillageDetail.class
	)
	@QueryExpression("x.county.status>=0")
	protected FelicityVillageService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return felicityVillageService;
	}
	
	@Override
	protected VisibleScope getScope(ItemInfo item) {
		return VisibleScope.DOMAIN;
	}
}
