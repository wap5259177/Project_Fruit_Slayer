package cn.bonoon.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.kernel.support.entities.AbstractPersistable;

@Entity
@Table(name = "t_projectreport")
public class ProjectReportEntity extends AbstractPersistable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6335362826748454556L;

	@Embedded
	private InvestmentInfo investment = new InvestmentInfo();

	//投工数
	@Column(name = "C_LABOURCOUNT")
	private int labourCount;
	
	//折资额
	@Column(name = "C_SPEND")
	private double spend;

	//退回判断
	@Column(name = "C_BACK")
	private Integer back;
	
	@Column(name = "C_STATUS")
	private int status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "C_SUBMITAT")
	private Date submitAt;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "C_BACKAT")
	private Date backAt;

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
	@Column(name = "C_PERIOD")
	private int period;
	
	@Column(name = "C_ANNUAL")
	private int annual;
	
	@Enumerated
	@Column(name = "C_TYPE")
	private ProjectReportType type;
	
	@OneToMany(mappedBy = "report")
	private List<ProjectReportItem> reports;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "R_MODELAREA_ID")
	private ModelAreaEntity modelArea;
	
	//------------提交后进行一些统计
	//记录数
	@Column(name = "C_TOTALCOUNT")
	private int totalCount;
	//竣工数
	@Column(name = "C_FINISHCOUNT")
	private int finishCount;
	//终止项目的数量
	@Column(name = "C_STOPCOUNT")
	private int stopCount;
	//0申报的数量
	@Column(name = "C_ZEROCOUNT")
	private int zeroCount;
	
	public InvestmentInfo getInvestment() {
		return investment;
	}
	public void setInvestment(InvestmentInfo investment) {
		this.investment = investment;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public int getAnnual() {
		return annual;
	}
	public void setAnnual(int annual) {
		this.annual = annual;
	}
	public ProjectReportType getType() {
		return type;
	}
	public void setType(ProjectReportType type) {
		this.type = type;
	}
	public List<ProjectReportItem> getReports() {
		return reports;
	}
	public void setReports(List<ProjectReportItem> reports) {
		this.reports = reports;
	}
	public ModelAreaEntity getModelArea() {
		return modelArea;
	}
	public void setModelArea(ModelAreaEntity modelArea) {
		this.modelArea = modelArea;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getFinishCount() {
		return finishCount;
	}
	public void setFinishCount(int finishCount) {
		this.finishCount = finishCount;
	}
	public int getStopCount() {
		return stopCount;
	}
	public void setStopCount(int stopCount) {
		this.stopCount = stopCount;
	}
	public int getZeroCount() {
		return zeroCount;
	}
	public void setZeroCount(int zeroCount) {
		this.zeroCount = zeroCount;
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
	public Integer getBack() {
		return back;
	}
	public void setBack(Integer back) {
		this.back = back;
	}
	public Date getBackAt() {
		return backAt;
	}
	public void setBackAt(Date backAt) {
		this.backAt = backAt;
	}
	public Date getSubmitAt() {
		return submitAt;
	}
	public void setSubmitAt(Date submitAt) {
		this.submitAt = submitAt;
	}
	
}
