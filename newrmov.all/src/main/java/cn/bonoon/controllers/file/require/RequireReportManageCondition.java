package cn.bonoon.controllers.file.require;

import cn.bonoon.controllers.file.reportitem.RequireReportItemDefine;
import cn.bonoon.kernel.annotations.condition.ConditionField;
import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class RequireReportManageCondition extends PageCondition implements RequireReportItemDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7522838835299784012L;
	
	@ConditionField("requireReport.name")
	@ConditionContent(ordinal = 0 , value = "名称")
	private String searchRname;

	@ConditionField("requireReport.offices")
	@ConditionContent(ordinal = 1 , value = "业务处室")
	private String searchOffices;
	
	@ConditionField("unit.name")
	@ConditionContent(ordinal = 2 , value = "上报单位")
	private String searchUnit;

	public String getSearchRname() {
		return searchRname;
	}

	public void setSearchRname(String searchRname) {
		this.searchRname = searchRname;
	}

	public String getSearchOffices() {
		return searchOffices;
	}

	public void setSearchOffices(String searchOffices) {
		this.searchOffices = searchOffices;
	}

	public String getSearchUnit() {
		return searchUnit;
	}

	public void setSearchUnit(String searchUnit) {
		this.searchUnit = searchUnit;
	}
	
	
}
