package cn.bonoon.controllers.file.require;

import java.util.Date;

import cn.bonoon.kernel.annotations.condition.ConditionField;
import cn.bonoon.kernel.annotations.condition.ConditionOpt;
import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.util.Opt;
import cn.bonoon.kernel.web.annotations.condition.ConditionContent;

public class RequireReportCondition extends PageCondition implements RequireReportDefine {

	private static final long serialVersionUID = -9046959936900686158L;

	@ConditionContent
	private String searchName;

	@ConditionField(value = "createAt")
	@ConditionOpt(Opt.GE)
	@ConditionContent(value = "创建时间  从", ordinal = 4)
	private Date searchStartAt;

	@ConditionField(value = "createAt")
	@ConditionOpt(Opt.LE)
	@ConditionContent(value = "到", ordinal = 5)
	private Date searchEndAt;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Date getSearchStartAt() {
		return searchStartAt;
	}

	public void setSearchStartAt(Date searchStartAt) {
		this.searchStartAt = searchStartAt;
	}

	public Date getSearchEndAt() {
		return searchEndAt;
	}

	public void setSearchEndAt(Date searchEndAt) {
		this.searchEndAt = searchEndAt;
	}

}
