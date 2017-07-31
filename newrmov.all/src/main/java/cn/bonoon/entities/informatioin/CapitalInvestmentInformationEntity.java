package cn.bonoon.entities.informatioin;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import cn.bonoon.kernel.support.entities.AbstractPersistable;
import cn.bonoon.kernel.support.entities.EntityDeletable;

/**
 * 资金投入情况表
 * @author jackson
 *
 */
@MappedSuperclass
public class CapitalInvestmentInformationEntity extends AbstractPersistable implements EntityDeletable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4064145223770306013L;

//	/**
//	 * 第几批或市名或省记录填写的名字
//	 */
	/**
	 * 这里是市或县的名字
	 */
	@Column(name = "C_NAME")
	private String name;

	/**
	 * 已启动的项目个数
	 */
	@Column(name = "C_PSC")
	private int projectStartCount;

	/**
	 * 其中已竣工的项目个数
	 */
	@Column(name = "C_PFC")
	private int projectFinishCount;

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
	
	
//	/**
//	 * 其他省级财政资金
//	 */
//	@Column(name = "C_FUNDS2")
//	private double funds2;
//	/**
//	 * 市级财政资金
//	 */
//	@Column(name = "C_FUNDS3")
//	private double funds3;
//	/**
//	 * 县级财政资金
//	 */
//	@Column(name = "C_FUNDS4")
//	private double funds4;
//	/**
//	 * 社会投入和群众自筹资金
//	 */
//	@Column(name = "C_FUNDS5")
//	private double funds5;
	
	@Column(name = "C_DELETED")
	private boolean deleted;

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProjectStartCount() {
		return projectStartCount;
	}

	public void setProjectStartCount(int projectStartCount) {
		this.projectStartCount = projectStartCount;
	}

	public int getProjectFinishCount() {
		return projectFinishCount;
	}

	public void setProjectFinishCount(int projectFinishCount) {
		this.projectFinishCount = projectFinishCount;
	}

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
