package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import cn.bonoon.kernel.support.entities.AbstractEntity;

/**
 * @author Administrator
 * @version 1.0
 * @created 12-十一月-2014 23:12:34
 */
@MappedSuperclass
public abstract class AbstractProjectEntity<E extends ApplicantEntity> extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 405861503121842082L;

	@ManyToOne
	@JoinColumn(name = "R_APPLICANT_ID")
	private E applicant;
	/**
	 * 预算投入（万元）
	 */
	@Column(name = "C_BUDGET")
	private double budget;
	/**
	 * 建设内容
	 */
	@Column(name = "C_CONTENT")
	private String content;
	/**
	 * 完成投入（万元）
	 */
	@Column(name = "C_DONEINVESTMENT")
	private double doneInvestment;
	/**
	 * 实施时间
	 */
	@Column(name = "C_EXECUTETIME")
	private String executeTime;
	/**
	 * 实施主体
	 */
	@Column(name = "C_EXECUTOR")
	private String executor;
	
	public E getApplicant() {
		return applicant;
	}
	public void setApplicant(E applicant) {
		this.applicant = applicant;
	}
	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getDoneInvestment() {
		return doneInvestment;
	}
	public void setDoneInvestment(double doneInvestment) {
		this.doneInvestment = doneInvestment;
	}
	public String getExecuteTime() {
		return executeTime;
	}
	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}
	public String getExecutor() {
		return executor;
	}
	public void setExecutor(String executor) {
		this.executor = executor;
	}
}