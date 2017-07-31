package cn.bonoon.controllers.file.require;

import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = RequireReportCondition.class, value = @GridOptions(operationWith = 100))
public class RequireReportEndedItem extends BaseRequireReportItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2900871097634368274L;

	@AsColumn(width = 100, ordinal = 40)
	private String finishAt;

	public String getFinishAt() {
		return finishAt;
	}

	public void setFinishAt(String finishAt) {
		this.finishAt = finishAt;
	}
	
}
