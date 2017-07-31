package cn.bonoon.controllers.file.require;

import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = RequireReportCondition.class, value = @GridOptions(operationWith = 250))
public class RequireReportItem extends BaseRequireReportItem {

	private static final long serialVersionUID = 3960756943648359691L;


	@AsColumn(width = 60, ordinal = 24)
	private String statusIssue;
	@AsColumn(width = 100, ordinal = 23)
	private String issueAt;

	public String getStatusIssue() {
		return statusIssue;
	}

	public void setStatusIssue(String statusIssue) {
		this.statusIssue = statusIssue;
	}

	public String getIssueAt() {
		return issueAt;
	}

	public void setIssueAt(String issueAt) {
		this.issueAt = issueAt;
	}

}
