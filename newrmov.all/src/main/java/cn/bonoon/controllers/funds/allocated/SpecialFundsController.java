package cn.bonoon.controllers.funds.allocated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.SpecialFundsService;
import cn.bonoon.entities.SpecialFundsEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

@Controller
@RequestMapping("s/pl/funds/allocated")
public class SpecialFundsController extends AbstractGridController<SpecialFundsEntity, SpecialFundsItem> {

	private SpecialFundsService specialFundsService;
	@Autowired
	protected SpecialFundsController(SpecialFundsService specialFundsService) {
		super(specialFundsService);
		this.specialFundsService = specialFundsService;
	}
	
	//指定类添加，删除，表格标准定义
	@Override
	@GridStandardDefinition(
			insertClass = SpecialFundsEditor.class, 
			updateClass = SpecialFundsEditor.class, 
			detailClass = SpecialFundsDetail.class)
	//查询条件
	@QueryExpression("x.status=0")
	protected SpecialFundsService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return specialFundsService;
	}
}
