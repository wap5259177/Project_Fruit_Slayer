package cn.bonoon.entities.felicityVillage;

import static cn.bonoon.util.DoubleHelper.add;
import static cn.bonoon.util.DoubleHelper.subtract;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * <pre>
 * 资金的投入和使用情况的类
 * 
 * 关于项目资金和使用情况的统计都需要基于这个类
 * 可以对各种资金投入或使用情况进行统计监控
 * </pre>
 * @author jackson
 *
 */
@Embeddable//表示此类可以被插入某个entity中
public class FVInvestmentInfo {
	
	/**
	 * 总计
	 */
	@Column(name = "C_TOTALFUNDS")
	private double totalFunds;
	
	/**
	 * 省级财政资金
	 */
	@Column(name = "C_PROVINCEFUNDS")
	private double provinceFunds;
	
	/**
	 * 市级财政资金
	 */
	@Column(name = "C_CITYFUNDS")
	private double cityFunds;
	
	/**
	 * 县级财政资金
	 */
	@Column(name = "C_COUNTYFUNDS")
	private double countyFunds;
	
	
	
	/**
	 * 社会团体
	 */
	@Column(name = "C_SOCIALFUNDS")
	private double socialFunds;
	
	
	
	

	/**
	 * 群众自筹资金
	 */
	@Column(name = "C_MASSFUNDS")
	private double massFunds;
	
	/**
	 * 其它方面的资金
	 */
	@Column(name = "C_OTHERFUNDS")
	private double otherFunds;
	
	/**
	 * 自己进行累加计算
	 * @return
	 */
	public FVInvestmentInfo sumSelf(){
		totalFunds = add(
				provinceFunds,
				socialFunds,
				otherFunds,
				cityFunds,
				countyFunds,
				massFunds);
		
		return this;
	}
	
	public void sum(FVInvestmentInfo inv){
		if(null == inv) return;
		totalFunds = add(totalFunds, inv.totalFunds);
		provinceFunds = add(provinceFunds, inv.provinceFunds);
		socialFunds = add(socialFunds, inv.socialFunds);
		otherFunds = add(otherFunds, inv.otherFunds);
		
		cityFunds = add(cityFunds, inv.cityFunds);
		countyFunds = add(countyFunds, inv.countyFunds);
		massFunds = add(massFunds, inv.massFunds);
	}
	

	public FVInvestmentInfo copy() {
		FVInvestmentInfo cy = new FVInvestmentInfo();
		return copy(cy);
	}
	
	public FVInvestmentInfo copy(FVInvestmentInfo cy) {
		cy.totalFunds = totalFunds;
		cy.provinceFunds = provinceFunds;
		cy.socialFunds = socialFunds;
		cy.otherFunds = otherFunds;
		cy.cityFunds = cityFunds;
		cy.countyFunds = countyFunds;
		cy.massFunds = massFunds;
		return cy;
	}
	
	public void clear(){
		totalFunds = 0;
		provinceFunds = 0;
		socialFunds = 0;
		otherFunds = 0;
		
		//
		cityFunds = 0;
		countyFunds = 0;
		massFunds = 0;
	}
	
	/**
	 * 把this-inv里的属性值
	 * @param inv
	 */
	public void subtractTo(FVInvestmentInfo inv){
		if(null == inv) return;
		totalFunds = subtract(totalFunds, inv.totalFunds);
		provinceFunds = subtract(provinceFunds, inv.provinceFunds);
		socialFunds = subtract(socialFunds, inv.socialFunds);
		otherFunds = subtract(otherFunds, inv.otherFunds);
		
		cityFunds = subtract(cityFunds, inv.cityFunds);
		countyFunds = subtract(countyFunds, inv.countyFunds);
		massFunds = subtract(massFunds, inv.massFunds);
	}
	
	public void reSubtract(FVInvestmentInfo inv){
		if(null == inv) return;
		totalFunds = subtract(inv.totalFunds,totalFunds);
		provinceFunds = subtract(inv.provinceFunds,provinceFunds);
		socialFunds = subtract(inv.socialFunds,socialFunds);
		otherFunds = subtract(inv.otherFunds,otherFunds);
		
		cityFunds = subtract( inv.cityFunds,cityFunds);
		countyFunds = subtract(inv.countyFunds,countyFunds);
		massFunds = subtract(inv.massFunds,massFunds);
	}
	
	public void clear(FVInvestmentInfo inv){
		subtractTo(inv);
		
		inv.clear();
	}
	
	
//	public InvestmentInfo copy(){
//		InvestmentInfo inv = new InvestmentInfo();
//		inv.totalFunds += totalFunds;
//		inv.stateFunds += stateFunds;
//		inv.provinceFunds += provinceFunds;
//		inv.localFunds += localFunds;
//		inv.specialFunds += specialFunds;
//		inv.socialFunds += socialFunds;
//		inv.otherFunds += otherFunds;
//		
//		inv.cityFunds += cityFunds;
//		inv.countyFunds += countyFunds;
//		inv.massFunds += massFunds;
//		
//		return inv;
//	}
	
	public double getTotalFunds() {
		return totalFunds;
	}

	public void setTotalFunds(double totalFunds) {
		this.totalFunds = totalFunds;
	}

	public double getProvinceFunds() {
		return provinceFunds;
	}

	public void setProvinceFunds(double provinceFunds) {
		this.provinceFunds = provinceFunds;
	}


	public double getSocialFunds() {
		return socialFunds;
	}

	public void setSocialFunds(double socialFunds) {
		this.socialFunds = socialFunds;
	}

	public double getOtherFunds() {
		return otherFunds;
	}

	public void setOtherFunds(double otherFunds) {
		this.otherFunds = otherFunds;
	}

	public double getCityFunds() {
		return cityFunds;
	}

	public void setCityFunds(double cityFunds) {
		this.cityFunds = cityFunds;
	}

	public double getCountyFunds() {
		return countyFunds;
	}

	public void setCountyFunds(double countyFunds) {
		this.countyFunds = countyFunds;
	}

	public double getMassFunds() {
		return massFunds;
	}

	public void setMassFunds(double massFunds) {
		this.massFunds = massFunds;
	}
	
}
