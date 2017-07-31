package cn.bonoon.entities.logs.modelAreaManageModuleLogs;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.logs.InvestmentComparer;
import cn.bonoon.entities.logs.SuperModelAreaComparer;
/**
 * 对删除项目初始的累计资金作记录
 * 
 * @author wsf
 * @creation 2016-12-7 
 *
 */
@Entity
@Table(name = "cp_dpsic", catalog = "db_newrmov_log")
public class DelPorSouInvComparerEntity extends SuperModelAreaComparer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 132497344349650300L;

	@Embedded
	InvestmentComparer investCom = new InvestmentComparer();

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "totalFunds", column = @Column(name = "SOU_TOTAL")),
			@AttributeOverride(name = "stateFunds", column = @Column(name = "SOU_STATE")),
			@AttributeOverride(name = "provinceFunds", column = @Column(name = "SOU_PROVINCE")),
			@AttributeOverride(name = "localFunds", column = @Column(name = "SOU_LOCAL")),
			@AttributeOverride(name = "specialFunds", column = @Column(name = "SOU_SPECIAL")),
			@AttributeOverride(name = "socialFunds", column = @Column(name = "SOU_SOCIAL")),
			@AttributeOverride(name = "massFunds", column = @Column(name = "SOU_MASS")),
			@AttributeOverride(name = "otherFunds", column = @Column(name = "SOU_OTHER")),
			@AttributeOverride(name = "cityFunds", column = @Column(name = "SOU_CITY")),
			@AttributeOverride(name = "countyFunds", column = @Column(name = "SOU_COUNTY")),

	})
	InvestmentInfo sourceInvestment = new InvestmentInfo();

	// public InvestmentInfo getTotalInvestmentOld() {
	// return totalInvestmentOld;
	// }
	//
	// public void setTotalInvestmentOld(InvestmentInfo totalInvestmentOld) {
	// this.totalInvestmentOld = totalInvestmentOld;
	// }
	//
	// public InvestmentInfo getTotalInvestmentNow() {
	// return totalInvestmentNow;
	// }
	//
	// public void setTotalInvestmentNow(InvestmentInfo totalInvestmentNow) {
	// this.totalInvestmentNow = totalInvestmentNow;
	// }

	public InvestmentInfo getSourceInvestment() {
		return sourceInvestment;
	}

	public void setSourceInvestment(InvestmentInfo sourceInvestment) {
		this.sourceInvestment = sourceInvestment;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public InvestmentComparer getInvestCom() {
		return investCom;
	}

	public void setInvestCom(InvestmentComparer investCom) {
		this.investCom = investCom;
	}
}
