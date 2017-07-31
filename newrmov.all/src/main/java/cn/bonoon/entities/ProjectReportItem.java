package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.kernel.support.entities.AbstractPersistable;

/**
 * <pre>
 * 项目报表，月报或季报等
 * </pre>
 * @author jackson
 *
 */
@Entity
@Table(name = "t_pritem")
public class ProjectReportItem extends AbstractPersistable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8456454761361531483L;

	@ManyToOne
	@JoinColumn(name = "R_REPORT_ID")
	private ProjectReportEntity report;
	
	@ManyToOne
	@JoinColumn(name = "R_PROJECT_ID")
	private ProjectEntity project;
	
	@ManyToOne
	@JoinColumn(name = "R_STATUSCHANGE_ID")
	private ProjectStatusChangeEntity statusChange;
	
	/**
	 * 资金投入的使用情况
	 */
	@Embedded
	private InvestmentInfo investment = new InvestmentInfo();

	//投工数
	@Column(name = "C_LABOURCOUNT")
	private int labourCount;
	
	//折资额
	@Column(name = "C_SPEND")
	private double spend;

	/**
	 * 0-进行中
	 * 1-竣工
	 * 2-终止
	 */
	@Column(name = "C_PROJECTSTATUS")
	private int projectStatus;
	
	//项目终止原因
	@Column(name = "C_EXITREASON")
	private String exitReason;
	
//	
//	@Enumerated
//	@Column(name = "C_TYPE")
//	private ProjectReportType type;

	/**
	 * <pre>
	 * 周期，该值的意义需要根据ProjectReportType的定义：
	 * 季报：为一年中的4个季度，
	 *     0-第一季度
	 *     1-第二季度
	 *     2-第三季度
	 *     3-第四季度
	 * 月报：表示月份
	 * 周报：表示当前周是一年中的第几周
	 * </pre>
	 */
//	@Column(name = "C_PERIOD")
//	private int period;
//	
//	@Column(name = "C_ANNUAL")
//	private int annual;
//	
//	@Column(name = "C_STATUS")
//	private int status;
	
	//项目是否竣工
	//这个属性不使用，用projectStatus替代
//	@Column(name = "C_PROJECTFINISH")
//	private boolean projectFinish;

	
//	public ProjectReportType getType() {
//		return type;
//	}
//
//	public void setType(ProjectReportType type) {
//		this.type = type;
//	}

	public ProjectEntity getProject() {
		return project;
	}

//	public int getAnnual() {
//		return annual;
//	}
//
//	public void setAnnual(int annual) {
//		this.annual = annual;
//	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

//	public int getPeriod() {
//		return period;
//	}
//
//	public void setPeriod(int period) {
//		this.period = period;
//	}

	public InvestmentInfo getInvestment() {
		return investment;
	}

	public void setInvestment(InvestmentInfo investment) {
		this.investment = investment;
	}

//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}

//	public boolean isProjectFinish() {
//		return projectFinish;
//	}
//
//	public void setProjectFinish(boolean projectFinish) {
//		this.projectFinish = projectFinish;
//	}

	public int getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}

	public ProjectReportEntity getReport() {
		return report;
	}

	public void setReport(ProjectReportEntity report) {
		this.report = report;
	}

	public double getSpend() {
		return spend;
	}

	public void setSpend(double spend) {
		this.spend = spend;
	}

	public int getLabourCount() {
		return labourCount;
	}

	public void setLabourCount(int labourCount) {
		this.labourCount = labourCount;
	}

	public String getExitReason() {
		return exitReason;
	}

	public void setExitReason(String exitReason) {
		this.exitReason = exitReason;
	}

	/**
	 * 表示该月报是否为0申报，如果是0申报，则需要在0申报里进行累加
	 * @return
	 */
	public boolean zeroReport(){
		return investment.getTotalFunds() == 0 && labourCount == 0 && spend == 0;
	}

	public ProjectStatusChangeEntity getStatusChange() {
		return statusChange;
	}

	public void setStatusChange(ProjectStatusChangeEntity statusChange) {
		this.statusChange = statusChange;
	}
}
