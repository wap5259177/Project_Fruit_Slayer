package cn.bonoon.controllers.project;

import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class ProjectCondition extends PageCondition implements ProjectDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1251418518063359704L;
	@ConditionContent
	private String searchName;
	@ConditionContent
	private String searchCode;
	
	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}
}
