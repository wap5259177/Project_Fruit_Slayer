package cn.bonoon.controllers.project.reportManager;

import cn.bonoon.kernel.annotations.condition.ConditionField;
import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class ProjetManagetCondition extends PageCondition implements ProjectManagerDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1782178221502995513L;

	@ConditionField("modelArea.")
	@ConditionContent(ordinal = 0,value="市名")
	private String searchCityName;
	
	@ConditionContent(ordinal = 10,value="示范片区名")
	private String searchAreaName;
	
	@ConditionContent(ordinal = 20,value="项目编号")
	private String searchCode;

	public String getSearchCityName() {
		return searchCityName;
	}

	public void setSearchCityName(String searchCityName) {
		this.searchCityName = searchCityName;
	}

	public String getSearchAreaName() {
		return searchAreaName;
	}

	public void setSearchAreaName(String searchAreaName) {
		this.searchAreaName = searchAreaName;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	
}
