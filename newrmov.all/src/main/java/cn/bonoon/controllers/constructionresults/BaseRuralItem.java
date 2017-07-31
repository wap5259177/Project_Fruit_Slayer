package cn.bonoon.controllers.constructionresults;

import cn.bonoon.controllers.newrural.RuralCondition;
import cn.bonoon.controllers.newrural.RuralItem;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = RuralCondition.class, value = @GridOptions(operationWith = 135))
public class BaseRuralItem extends RuralItem {

	private static final long serialVersionUID = -6940605360665326494L;
}
