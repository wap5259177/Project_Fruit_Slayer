package cn.bonoon.controllers.file.requirereportapply;

import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class RequireReportApplyCondition extends PageCondition implements RequireReportApplyDefine{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6706638165632927909L;
	@ConditionContent
	private String searchName;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
}
