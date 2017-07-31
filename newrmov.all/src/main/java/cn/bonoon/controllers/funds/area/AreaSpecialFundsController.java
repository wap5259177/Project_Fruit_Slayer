package cn.bonoon.controllers.funds.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.SpecialFundsService;
import cn.bonoon.entities.SpecialFundsEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

@Controller
@RequestMapping("s/ml/funds/area")
public class AreaSpecialFundsController extends AbstractGridController<SpecialFundsEntity, AreaSpecialFundsItem> {

	private SpecialFundsService specialFundsService;
	@Autowired
	protected AreaSpecialFundsController(SpecialFundsService specialFundsService) {
		super(specialFundsService);
		this.specialFundsService = specialFundsService;
	}
	
	//删除工具栏上按钮
	@Override
	@GridStandardDefinition(
			detailClass = AreaSpecialFundsDetail.class,
			deleteOperation = false)
	@QueryExpression("x.status>1")
	protected SpecialFundsService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return specialFundsService;
	}
}
