package cn.bonoon.controllers.area.manage;

import cn.bonoon.controllers.area.ModelAreaDefine;
import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class ModelAreaManageCondition extends PageCondition implements ModelAreaDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2732880769234091797L;

	@ConditionContent(ordinal = 0)
	private String searchCityName;
	
	@ConditionContent(ordinal = 2)
	private String searchCounty;
	
	@ConditionContent(ordinal = 3)
	private String searchName;
	

	public String getSearchCityName() {
		return searchCityName;
	}

	public void setSearchCityName(String searchCityName) {
		this.searchCityName = searchCityName;
	}

	public String getSearchCounty() {
		return searchCounty;
	}

	public void setSearchCounty(String searchCounty) {
		this.searchCounty = searchCounty;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	
}
