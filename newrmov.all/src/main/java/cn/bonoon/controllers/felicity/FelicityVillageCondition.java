package cn.bonoon.controllers.felicity;

import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class FelicityVillageCondition extends PageCondition implements FelicityVillageDefine{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2431541870572864879L;
	@ConditionContent
	private String searchName;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
}
