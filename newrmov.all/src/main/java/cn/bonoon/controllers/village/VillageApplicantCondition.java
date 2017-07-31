package cn.bonoon.controllers.village;

import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class VillageApplicantCondition extends PageCondition implements VillageApplicantDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1750406591533644890L;
	@ConditionContent
	private String searchName;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
}
