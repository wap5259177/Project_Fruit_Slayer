package cn.bonoon.controllers.newrural;

import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class RuralCondition extends PageCondition implements RuralDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6622666869575599985L;
	@ConditionContent
	private String searchName;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
}
