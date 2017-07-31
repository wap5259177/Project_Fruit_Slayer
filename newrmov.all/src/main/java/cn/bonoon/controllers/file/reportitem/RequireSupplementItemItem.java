package cn.bonoon.controllers.file.reportitem;

import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = RequireReportItemCondition.class, value = @GridOptions(operationWith = 100))
public class RequireSupplementItemItem extends BaseRequireReportItemItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = -885898745247205210L;

}
