package cn.bonoon.entities.logs.capitalcomparer;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import cn.bonoon.entities.informatioin.CityCapitalInvestmentInformationEntity;

@Embeddable
public class CapitalInvestmentComparer {
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="totalFunds", column=@Column(name = "S_TOTALFUNDS")),
		@AttributeOverride(name="funds0", column=@Column(name = "S_FUNDS0")),
		@AttributeOverride(name="funds1", column=@Column(name = "S_FUNDS1")),
		@AttributeOverride(name="funds2", column=@Column(name = "S_FUNDS2")),
		@AttributeOverride(name="funds3", column=@Column(name = "S_FUNDS3")),
		@AttributeOverride(name="funds4", column=@Column(name = "S_FUNDS4")),
		@AttributeOverride(name="funds5", column=@Column(name = "S_FUNDS5"))
	})
	CapitalInvestmentInfo source = new CapitalInvestmentInfo();

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="totalFunds", column=@Column(name = "T_TOTALFUNDS")),
		@AttributeOverride(name="funds0", column=@Column(name = "T_FUNDS0")),
		@AttributeOverride(name="funds1", column=@Column(name = "T_FUNDS1")),
		@AttributeOverride(name="funds2", column=@Column(name = "T_FUNDS2")),
		@AttributeOverride(name="funds3", column=@Column(name = "T_FUNDS3")),
		@AttributeOverride(name="funds4", column=@Column(name = "T_FUNDS4")),
		@AttributeOverride(name="funds5", column=@Column(name = "T_FUNDS5"))
	})

	CapitalInvestmentInfo target = new CapitalInvestmentInfo();

	public CapitalInvestmentInfo getSource() {
		return source;
	}

	public void setSource(CityCapitalInvestmentInformationEntity source) {
		this.source.setTotalFunds(source.getTotalFunds());
		this.source.setFunds0(source.getFunds0());
		this.source.setFunds1(source.getFunds1());
		this.source.setFunds2(source.getFunds2());
		this.source.setFunds3(source.getFunds3());
		this.source.setFunds4(source.getFunds4());
		this.source.setFunds5(source.getFunds5());
	}

	public CapitalInvestmentInfo getTarget() {
		return target;
	}

	public void setTarget(CityCapitalInvestmentInformationEntity target) {
		this.target.setTotalFunds(target.getTotalFunds());
		this.target.setFunds0(target.getFunds0());
		this.target.setFunds1(target.getFunds1());
		this.target.setFunds2(target.getFunds2());
		this.target.setFunds3(target.getFunds3());
		this.target.setFunds4(target.getFunds4());
		this.target.setFunds5(target.getFunds5());
	}
	
}
