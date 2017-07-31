package cn.bonoon.entities.logs.capitalcomparer;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CapitalInvestmentInfo {
	/**
	 * 累计投入资金总数
	 */
	@Column(name = "C_TOTALFUNDS")
	private double totalFunds;

	/**
	 * 中央财政资金
	 */
	@Column(name = "C_FUNDS0")
	private double funds0;
	/**
	 * 省级新农村示范片建设补助资金
	 */
	@Column(name = "C_FUNDS1")
	private double funds1;
	
	/**
	 * 市
	 */
	@Column(name = "C_FUNDS2")
	private double funds2;
	
	/**
	 *县
	 */
	@Column(name = "C_FUNDS3")
	private double funds3;
	
	/**
	 * 社会投入
	 */
	@Column(name = "C_FUNDS4")
	private double funds4;
	/**
	 * 群众自筹
	 */
	@Column(name = "C_FUNDS5")
	private double funds5;
	public double getTotalFunds() {
		return totalFunds;
	}
	public void setTotalFunds(double totalFunds) {
		this.totalFunds = totalFunds;
	}
	public double getFunds0() {
		return funds0;
	}
	public void setFunds0(double funds0) {
		this.funds0 = funds0;
	}
	public double getFunds1() {
		return funds1;
	}
	public void setFunds1(double funds1) {
		this.funds1 = funds1;
	}
	public double getFunds2() {
		return funds2;
	}
	public void setFunds2(double funds2) {
		this.funds2 = funds2;
	}
	public double getFunds3() {
		return funds3;
	}
	public void setFunds3(double funds3) {
		this.funds3 = funds3;
	}
	public double getFunds4() {
		return funds4;
	}
	public void setFunds4(double funds4) {
		this.funds4 = funds4;
	}
	public double getFunds5() {
		return funds5;
	}
	public void setFunds5(double funds5) {
		this.funds5 = funds5;
	}
	
}
