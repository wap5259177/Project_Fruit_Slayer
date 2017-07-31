package cn.bonoon.entities.logs;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import cn.bonoon.entities.InvestmentInfo;

@Embeddable
public class InvestmentComparer {
	
	@Embedded
	@AttributeOverrides({
	       @AttributeOverride(name="totalFunds", column=@Column(name = "S_TOTAL")),
	       @AttributeOverride(name="stateFunds", column=@Column(name = "S_STATE")),
	       @AttributeOverride(name="provinceFunds", column=@Column(name = "S_PROVINCE")),
	       @AttributeOverride(name="localFunds", column=@Column(name = "S_LOCAL")),
	       @AttributeOverride(name="specialFunds", column=@Column(name = "S_SPECIAL")),
	       @AttributeOverride(name="socialFunds", column=@Column(name = "S_SOCIAL")),
	       @AttributeOverride(name="massFunds", column=@Column(name = "S_MASS")),
	       @AttributeOverride(name="otherFunds", column=@Column(name = "S_OTHER")),
	       @AttributeOverride(name="cityFunds", column=@Column(name = "S_CITY")),
	       @AttributeOverride(name="countyFunds", column=@Column(name = "S_COUNTY"))
	       
	   })
	InvestmentInfo source = new InvestmentInfo();

	@Embedded
	@AttributeOverrides({
	       @AttributeOverride(name="totalFunds", column=@Column(name = "T_TOTAL")),
	       @AttributeOverride(name="stateFunds", column=@Column(name = "T_STATE")),
	       @AttributeOverride(name="provinceFunds", column=@Column(name = "T_PROVINCE")),
	       @AttributeOverride(name="localFunds", column=@Column(name = "T_LOCAL")),
	       @AttributeOverride(name="specialFunds", column=@Column(name = "T_SPECIAL")),
	       @AttributeOverride(name="socialFunds", column=@Column(name = "T_SOCIAL")),
	       @AttributeOverride(name="massFunds", column=@Column(name = "T_MASS")),
	       @AttributeOverride(name="otherFunds", column=@Column(name = "T_OTHER")),
	       @AttributeOverride(name="cityFunds", column=@Column(name = "T_CITY")),
	       @AttributeOverride(name="countyFunds", column=@Column(name = "T_COUNTY"))
	   })
	
	InvestmentInfo target = new InvestmentInfo();
	
	public InvestmentInfo getSource() {
		return source;
	}

	public void setSource(InvestmentInfo source) {
		this.source = source;
	}

	public InvestmentInfo getTarget() {
		return target;
	}

	public void setTarget(InvestmentInfo target) {
		this.target = target;
	}
}
