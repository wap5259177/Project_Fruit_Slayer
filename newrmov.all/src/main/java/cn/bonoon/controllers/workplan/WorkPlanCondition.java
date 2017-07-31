package cn.bonoon.controllers.workplan;

import cn.bonoon.kernel.annotations.condition.ConditionField;
import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class WorkPlanCondition extends PageCondition implements WorkPlanDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2801144990401551231L;
	
	@ConditionField("unitName")
	@ConditionContent(ordinal = 0,value="单位")
	private String searchUnitName;
	
	@ConditionField("annual")
	@ConditionContent(ordinal = 2)
	private Integer searchAnnual;
	
	@ConditionField("monthly")
	@ConditionContent(ordinal = 3)
	private Integer searchMonthly;
	
	


	public String getSearchUnitName() {
		return searchUnitName;
	}


	public void setSearchUnitName(String searchUnitName) {
		this.searchUnitName = searchUnitName;
	}


	public Integer getSearchAnnual() {
		return searchAnnual;
	}


	public void setSearchAnnual(Integer searchAnnual) {
		this.searchAnnual = searchAnnual;
	}


	public Integer getSearchMonthly() {
		return searchMonthly;
	}


	public void setSearchMonthly(Integer searchMonthly) {
		this.searchMonthly = searchMonthly;
	}



	
	
	

}
