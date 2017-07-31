package cn.bonoon.controllers.felicity.countyreport;

import java.util.Date;

import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class FelicityCountyReportCondition extends PageCondition implements FelicityCountyReportDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8389522845173126975L;
	@ConditionContent(ordinal = 0)
	private String searchInformant;

	@ConditionContent(ordinal = 1, value = "填报时间 从")
	private Date searchStartReportingAt;

	@ConditionContent(ordinal = 2, value = "到")
	private Date searchEndReportingAt;

	public Date getSearchEndReportingAt() {
		return searchEndReportingAt;
	}

	public void setSearchEndReportingAt(Date searchEndReportingAt) {
		this.searchEndReportingAt = searchEndReportingAt;
	}

	public Date getSearchStartReportingAt() {
		return searchStartReportingAt;
	}

	public void setSearchStartReportingAt(Date searchStartReportingAt) {
		this.searchStartReportingAt = searchStartReportingAt;
	}

	public String getSearchInformant() {
		return searchInformant;
	}

	public void setSearchInformant(String searchInformant) {
		this.searchInformant = searchInformant;
	}
}
