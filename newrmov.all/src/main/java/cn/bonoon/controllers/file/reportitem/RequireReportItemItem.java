package cn.bonoon.controllers.file.reportitem;

import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = RequireReportItemCondition.class, value = @GridOptions(operationWith = 180))
public class RequireReportItemItem extends BaseRequireReportItemItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3960756943648359691L;

	
	@AsColumn(width = 60, ordinal = 10)
	private int urge;

	public int getUrge() {
		return urge;
	}

	public void setUrge(int urge) {
		this.urge = urge;
	}

}
