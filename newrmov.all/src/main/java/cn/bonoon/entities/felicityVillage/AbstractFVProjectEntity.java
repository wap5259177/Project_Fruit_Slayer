package cn.bonoon.entities.felicityVillage;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import cn.bonoon.kernel.support.entities.AbstractEntity;

/**
 * 放项目小计的字段
 * @author Administrator
 *
 */
@MappedSuperclass
public abstract class AbstractFVProjectEntity extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4659798051495908280L;

	/**
	 * 项目预算投入
	 */
	@Column(name = "C_BUDGETMONEY")
	private double budgetMoney;
	/**
	 * 项目完成投入
	 */
	@Column(name = "C_FINISHMONEY")
	private double finishMoney;
	
	
	
	public double getBudgetMoney() {
		return budgetMoney;
	}
	public void setBudgetMoney(double budgetMoney) {
		this.budgetMoney = budgetMoney;
	}
	public double getFinishMoney() {
		return finishMoney;
	}
	public void setFinishMoney(double finishMoney) {
		this.finishMoney = finishMoney;
	}
	

}
