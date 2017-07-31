package cn.bonoon.controllers.project.reportManager;

import cn.bonoon.kernel.annotations.condition.ConditionField;
import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class ReportManagerCondition extends PageCondition implements ReportMangerDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1571621367111907647L;


	
	@ConditionField("modelArea.name")
	@ConditionContent(ordinal = 0,value="示范片区名")
	private String searchName;
	
	
	@ConditionContent(ordinal = 2)
	private Integer searchAnnual;
	
	@ConditionContent(ordinal = 3)
	private Integer searchPeriod;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}


	public Integer getSearchPeriod() {
		return searchPeriod;
	}

	public void setSearchPeriod(Integer searchPeriod) {
		this.searchPeriod = searchPeriod;
	}

	public Integer getSearchAnnual() {
		return searchAnnual;
	}

	public void setSearchAnnual(Integer searchAnnual) {
		this.searchAnnual = searchAnnual;
	}

	
	
}
