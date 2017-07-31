package cn.bonoon.controllers.funds.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.SpecialFundsService;
import cn.bonoon.entities.SpecialFundsEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

@Controller
@RequestMapping("s/pl/funds/track")
public class TrackSpecialFundsController extends AbstractGridController<SpecialFundsEntity, TrackSpecialFundsItem> {

	private SpecialFundsService specialFundsService;
	@Autowired
	protected TrackSpecialFundsController(SpecialFundsService specialFundsService) {
		super(specialFundsService);
		this.specialFundsService = specialFundsService;
	}
	
	//删除工具栏上按钮
	@Override
	@GridStandardDefinition(
			detailClass = TrackSpecialFundsDetail.class,
			deleteOperation = false)
	@QueryExpression("x.status=1")
	protected SpecialFundsService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return specialFundsService;
	}
}
