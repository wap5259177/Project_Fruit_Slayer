package cn.bonoon.controllers.file.reportitem;

import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = RequireReportItemCondition.class, value = @GridOptions(operationWith = 80))
public class RequireReportItemFinishItem extends BaseRequireReportItemItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7658652379762863164L;

	@AsColumn(width = 100, ordinal = 10)
	private String submitAt;

	public String getSubmitAt() {
		return submitAt;
	}

	public void setSubmitAt(String submitAt) {
		this.submitAt = submitAt;
	}
}
