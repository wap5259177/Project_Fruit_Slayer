package cn.bonoon.entities.logs.projectcomparer;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.bonoon.entities.logs.SuperComparer;
@Entity
@Table(name = "cp_pro", catalog = "db_newrmov_log")
public class ProjectComparer extends SuperComparer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
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
	Comparer source = new Comparer();

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
	Comparer target = new Comparer();

	

	public Comparer getSource() {
		return source;
	}



	public void setSource(Comparer source) {
		this.source = source;
	}



	public Comparer getTarget() {
		return target;
	}



	public void setTarget(Comparer target) {
		this.target = target;
	}



	/**
	 * 如果有值不相等的，则表示需要记录
	 */
	public boolean needAdjust(){
		return source.valueEquals(target);
	}

}
