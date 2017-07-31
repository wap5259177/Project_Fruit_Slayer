package cn.bonoon.controllers.information;

import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class ProvinceInformationCondition extends PageCondition implements ProvinceInformationDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8389522845173126975L;
	@ConditionContent(ordinal = 0)
	private String searchName;

//	@ConditionContent(ordinal = 1, value = "统计时间 从")
//	private Date searchStartTime;
//
//	@ConditionContent(ordinal = 2, value = "到")
//	private Date searchDeadline;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

//	public Date getSearchStartTime() {
//		return searchStartTime;
//	}
//
//	public void setSearchStartTime(Date searchStartTime) {
//		this.searchStartTime = searchStartTime;
//	}
//
//	public Date getSearchDeadline() {
//		return searchDeadline;
//	}
//
//	public void setSearchDeadline(Date searchDeadline) {
//		this.searchDeadline = searchDeadline;
//	}

	
}
