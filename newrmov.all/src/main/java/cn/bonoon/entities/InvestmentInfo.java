package cn.bonoon.entities;

import static cn.bonoon.util.DoubleHelper.add;
import static cn.bonoon.util.DoubleHelper.subtract;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import cn.bonoon.kernel.util.StringHelper;

/**
 * <pre>
 * 资金的投入和使用情况的类
 * 
 * 关于项目资金和使用情况的统计都需要基于这个类
 * 可以对各种资金投入或使用情况进行统计监控
 * </pre>
 * 
 * @author jackson
 * 
 */

@Embeddable
public class InvestmentInfo {

	/**
	 * 总投入
	 */
	@Column(name = "C_TOTALFUNDS")
	private Double totalFunds;

	/**
	 * 国家的资金
	 */
	@Column(name = "C_STATEFUNDS")
	private Double stateFunds;

	/**
	 * 省投入、其它省级财政资金
	 */
	@Column(name = "C_PROVINCEFUNDS")
	private Double provinceFunds;

	// /**
	// * 所属市级的资金投入
	// */
	// @Column(name = "C_CITYFUNDS")
	// private double cityFunds;

	/**
	 * 当地政府的资金投入
	 * 
	 * @deprecated 这个应该不会被用到的
	 */
	@Column(name = "C_LOCALFUNDS")
	private Double localFunds;

	/**
	 * 其中省级新农村示范片专项 省级新农村连片示范工程建设补助资金
	 */
	@Column(name = "C_SPECIALFUNDS")
	private Double specialFunds;

	/**
	 * 社会资金；企业投入，集资等
	 */
	@Column(name = "C_SOCIALFUNDS")
	private Double socialFunds;

	/**
	 * 其它方面的资金
	 */
	@Column(name = "C_OTHERFUNDS")
	private Double otherFunds;

	/**
	 * 市级财政资金
	 */
	@Column(name = "C_CITYFUNDS")
	private Double cityFunds;

	/**
	 * 县级财政资金
	 */
	@Column(name = "C_COUNTYFUNDS")
	private Double countyFunds;

	/**
	 * 群众自筹资金
	 */
	@Column(name = "C_MASSFUNDS")
	private Double massFunds;

	
	public InvestmentInfo(){
		totalFunds=new Double(0.0);
		stateFunds=new Double(0.0);
		provinceFunds=new Double(0.0);
		localFunds=new Double(0.0);
		specialFunds=new Double(0.0);
		socialFunds=new Double(0.0);
		otherFunds=new Double(0.0);
		cityFunds=new Double(0.0);
		countyFunds=new Double(0.0);
		massFunds=new Double(0.0);
	}
	
	/**
	 * 自己进行累加计算
	 * 
	 * @return
	 */
	public InvestmentInfo sumSelf() {
		totalFunds = add(stateFunds, provinceFunds, specialFunds, socialFunds,
				otherFunds, cityFunds, countyFunds, massFunds);

		return this;
	}
	

	public void sum(InvestmentInfo inv) {
		if (null == inv)
			return;
		totalFunds = add(totalFunds.doubleValue(), inv.totalFunds);
		// totalFunds, inv.totalFunds;
		stateFunds = add(stateFunds.doubleValue(), inv.stateFunds);
		provinceFunds = add(provinceFunds.doubleValue(), inv.provinceFunds);
		// localFunds = add(localFunds, inv.localFunds);
		specialFunds = add(specialFunds.doubleValue(), inv.specialFunds);
		socialFunds = add(socialFunds.doubleValue(), inv.socialFunds);
		otherFunds = add(otherFunds.doubleValue(), inv.otherFunds);

		cityFunds = add(cityFunds.doubleValue(), inv.cityFunds);
		countyFunds = add(countyFunds.doubleValue(), inv.countyFunds);
		massFunds = add(massFunds.doubleValue(), inv.massFunds);
	}

	public InvestmentInfo copy() {
		InvestmentInfo cy = new InvestmentInfo();
		return copy(cy);
	}

	public InvestmentInfo copy(InvestmentInfo cy) {
		cy.totalFunds = totalFunds;
		cy.stateFunds = stateFunds;
		cy.provinceFunds = provinceFunds;
		// cy.localFunds = localFunds;
		cy.specialFunds = specialFunds;
		cy.socialFunds = socialFunds;
		cy.otherFunds = otherFunds;
		cy.cityFunds = cityFunds;
		cy.countyFunds = countyFunds;
		cy.massFunds = massFunds;
		return cy;
	}

	public void clear() {
		totalFunds = 0.0;
		stateFunds = 0.0;
		provinceFunds = 0.0;
		localFunds = 0.0;
		specialFunds = 0.0;
		socialFunds = 0.0;
		otherFunds = 0.0;

		//
		cityFunds = 0.0;
		countyFunds = 0.0;
		massFunds = 0.0;
	}

	/**
	 * 把this-inv里的属性值
	 * 
	 * @param inv
	 */
	public void subtractTo(InvestmentInfo inv) {
		if (null == inv)
			return;
		totalFunds = subtract(totalFunds, inv.totalFunds);
		// totalFunds, inv.totalFunds;
		stateFunds = subtract(stateFunds, inv.stateFunds);
		provinceFunds = subtract(provinceFunds, inv.provinceFunds);
		// localFunds = subtract(localFunds, inv.localFunds);
		specialFunds = subtract(specialFunds, inv.specialFunds);
		socialFunds = subtract(socialFunds, inv.socialFunds);
		otherFunds = subtract(otherFunds, inv.otherFunds);

		cityFunds = subtract(cityFunds, inv.cityFunds);
		countyFunds = subtract(countyFunds, inv.countyFunds);
		massFunds = subtract(massFunds, inv.massFunds);
	}

	public void reSubtract(InvestmentInfo inv) {
		if (null == inv)
			return;
		totalFunds = subtract(inv.totalFunds, totalFunds);
		// totalFunds, inv.totalFunds;
		stateFunds = subtract(inv.stateFunds, stateFunds);
		provinceFunds = subtract(inv.provinceFunds, provinceFunds);
		// localFunds = subtract(localFunds, inv.localFunds);
		specialFunds = subtract(inv.specialFunds, specialFunds);
		socialFunds = subtract(inv.socialFunds, socialFunds);
		otherFunds = subtract(inv.otherFunds, otherFunds);

		cityFunds = subtract(inv.cityFunds, cityFunds);
		countyFunds = subtract(inv.countyFunds, countyFunds);
		massFunds = subtract(inv.massFunds, massFunds);
	}

	public void clear(InvestmentInfo inv) {
		subtractTo(inv);

		inv.clear();
	}

	// public InvestmentInfo copy(){
	// InvestmentInfo inv = new InvestmentInfo();
	// inv.totalFunds += totalFunds;
	// inv.stateFunds += stateFunds;
	// inv.provinceFunds += provinceFunds;
	// inv.localFunds += localFunds;
	// inv.specialFunds += specialFunds;
	// inv.socialFunds += socialFunds;
	// inv.otherFunds += otherFunds;
	//
	// inv.cityFunds += cityFunds;
	// inv.countyFunds += countyFunds;
	// inv.massFunds += massFunds;
	//
	// return inv;
	// }

	
	public boolean equals(InvestmentInfo compareMoney) {
		if (compareMoney == null) {
			return false;
		}
		if (this.totalFunds == compareMoney.totalFunds
				&& this.stateFunds == compareMoney.stateFunds
				&& this.provinceFunds == compareMoney.provinceFunds
				&& this.localFunds == compareMoney.localFunds
				&& this.specialFunds == compareMoney.specialFunds
				&& this.socialFunds == compareMoney.socialFunds
				&& this.otherFunds == compareMoney.otherFunds
				&& this.cityFunds == compareMoney.cityFunds
				&& this.countyFunds == compareMoney.countyFunds
				&& this.massFunds == compareMoney.massFunds)
			return true;

		return false;

	}

	public Double getTotalFunds() {
		return totalFunds;
	}

	public void setTotalFunds(Double totalFunds) {
		this.totalFunds = totalFunds;
	}

	public Double getStateFunds() {
		return stateFunds;
	}

	public void setStateFunds(Double stateFunds) {
		this.stateFunds = stateFunds;
	}

	public Double getProvinceFunds() {
		return provinceFunds;
	}

	public void setProvinceFunds(Double provinceFunds) {
		this.provinceFunds = provinceFunds;
	}

	public Double getLocalFunds() {
		return localFunds;
	}

	public void setLocalFunds(Double localFunds) {
		this.localFunds = localFunds;
	}

	public Double getSpecialFunds() {
		return specialFunds;
	}

	public void setSpecialFunds(Double specialFunds) {
		this.specialFunds = specialFunds;
	}

	public Double getSocialFunds() {
		return socialFunds;
	}

	public void setSocialFunds(Double socialFunds) {
		this.socialFunds = socialFunds;
	}

	public Double getOtherFunds() {
		return otherFunds;
	}

	public void setOtherFunds(Double otherFunds) {
		this.otherFunds = otherFunds;
	}

	public Double getCityFunds() {
		return cityFunds;
	}

	public void setCityFunds(Double cityFunds) {
		this.cityFunds = cityFunds;
	}

	public Double getCountyFunds() {
		return countyFunds;
	}

	public void setCountyFunds(Double countyFunds) {
		this.countyFunds = countyFunds;
	}

	public Double getMassFunds() {
		return massFunds;
	}

	public void setMassFunds(Double massFunds) {
		this.massFunds = massFunds;
	}
}
