package cn.bonoon.entities.logs.projectcomparer;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;

import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.logs.InvestmentComparer;

public class ProjectReporInfoComparer{

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="labourCount", column=@Column(name = "S_LABOURCOUNT")),
		@AttributeOverride(name="spend", column=@Column(name = "S_SPEND")),
		@AttributeOverride(name="info.totalFunds", column=@Column(name = "S_TOTAL")),
		@AttributeOverride(name="info.stateFunds", column=@Column(name = "S_STATE")),
		@AttributeOverride(name="info.provinceFunds", column=@Column(name = "S_PROVINCE")),
		@AttributeOverride(name="info.localFunds", column=@Column(name = "S_LOCAL")),
		@AttributeOverride(name="info.specialFunds", column=@Column(name = "S_SPECIAL")),
		@AttributeOverride(name="info.socialFunds", column=@Column(name = "S_SOCIAL")),
		@AttributeOverride(name="info.massFunds", column=@Column(name = "S_MASS")),
		@AttributeOverride(name="info.otherFunds", column=@Column(name = "S_OTHER")),
		@AttributeOverride(name="info.cityFunds", column=@Column(name = "S_CITY")),
		@AttributeOverride(name="info.countyFunds", column=@Column(name = "S_COUNTY"))
	})
	ProjectReportInfo source = new ProjectReportInfo();

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="labourCount", column=@Column(name = "T_LABOURCOUNT")),
		@AttributeOverride(name="spend", column=@Column(name = "T_SPEND")),
		@AttributeOverride(name="info.totalFunds", column=@Column(name = "T_TOTAL")),
		@AttributeOverride(name="info.stateFunds", column=@Column(name = "T_STATE")),
		@AttributeOverride(name="info.provinceFunds", column=@Column(name = "T_PROVINCE")),
		@AttributeOverride(name="info.localFunds", column=@Column(name = "T_LOCAL")),
		@AttributeOverride(name="info.specialFunds", column=@Column(name = "T_SPECIAL")),
		@AttributeOverride(name="info.socialFunds", column=@Column(name = "T_SOCIAL")),
		@AttributeOverride(name="info.massFunds", column=@Column(name = "T_MASS")),
		@AttributeOverride(name="info.otherFunds", column=@Column(name = "T_OTHER")),
		@AttributeOverride(name="info.cityFunds", column=@Column(name = "T_CITY")),
		@AttributeOverride(name="info.countyFunds", column=@Column(name = "T_COUNTY"))
	})
	ProjectReportInfo target = new ProjectReportInfo();

	public ProjectReportInfo getSource() {
		return source;
	}

	public void setSource(ProjectReportInfo source) {
		this.source = source;
	}

	public ProjectReportInfo getTarget() {
		return target;
	}

	public void setTarget(ProjectReportInfo target) {
		this.target = target;
	}

	/**
	 * 如果有值不相等的，则表示需要调整
	 */
	public boolean needAdjust(){
		return source.valueEquals(target);
	}
}
