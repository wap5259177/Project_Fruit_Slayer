package cn.bonoon.entities.logs.modelAreaManageModuleLogs;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.ProjectReportEntity;
import cn.bonoon.entities.ProjectStatusChangeEntity;
import cn.bonoon.entities.logs.InvestmentComparer;
import cn.bonoon.entities.logs.SuperComparer;
import cn.bonoon.entities.logs.SuperModelAreaComparer;

/**
 * <pre>
 *	对被删除的项目月报作记录实体,包含了项目月报{@link ProjectReportEntity}所有的信息(字段)
 * </pre>
 * @author wsf
 * @creation 2016-12-13 
 */
@Entity
@Table(name = "cp_dpric", catalog = "db_newrmov_log")
public class DelProRepItemComparerEntity extends SuperComparer{
/**
	 * 
	 */
	private static final long serialVersionUID = -7113047227853782662L;
	//片区的资金
	//项目资金+  pe.setSumLabourCount(rplc);pe.setSumSpend(rpsd);
	//被删掉的projectItem要记
	
	
	/**
	 *projectItem被删除前的资金
	 * modelArea的之前现在资金用log吧
	 * project之前现在的资金
	 */
	public DelProRepItemComparerEntity(){};
	
	public DelProRepItemComparerEntity(ProjectReportEntity report,
			ProjectEntity project, ProjectStatusChangeEntity statusChange,
			InvestmentInfo investment, int labourCount, double spend,
			int projectStatus, String exitReason) {
		super();
		this.report = report;
		this.project = project;
		this.statusChange = statusChange;
		this.investment = investment;
		this.labourCount = labourCount;
		this.spend = spend;
		this.projectStatus = projectStatus;
		this.exitReason = exitReason;
	}

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

	public ProjectReportEntity getReport() {
		return report;
	}

	public void setReport(ProjectReportEntity report) {
		this.report = report;
	}

	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

	public ProjectStatusChangeEntity getStatusChange() {
		return statusChange;
	}

	public void setStatusChange(ProjectStatusChangeEntity statusChange) {
		this.statusChange = statusChange;
	}

	public InvestmentInfo getInvestment() {
		return investment;
	}

	public void setInvestment(InvestmentInfo investment) {
		this.investment = investment;
	}

	public int getLabourCount() {
		return labourCount;
	}

	public void setLabourCount(int labourCount) {
		this.labourCount = labourCount;
	}

	public double getSpend() {
		return spend;
	}

	public void setSpend(double spend) {
		this.spend = spend;
	}

	public int getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getExitReason() {
		return exitReason;
	}

	public void setExitReason(String exitReason) {
		this.exitReason = exitReason;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
//	@ManyToOne
//	@JoinColumn(name = "R_REPORT_ID")
//	private ProjectReportEntity report;
//
//	public ProjectReportEntity getReport() {
//		return report;
//	}
//
//	public void setReport(ProjectReportEntity report) {
//		this.report = report;
//	}
//
//	public ProjectEntity getProject() {
//		return project;
//	}
//
//	public void setProject(ProjectEntity project) {
//		this.project = project;
//	}
//
//	public ProjectStatusChangeEntity getStatusChange() {
//		return statusChange;
//	}
//
//	public void setStatusChange(ProjectStatusChangeEntity statusChange) {
//		this.statusChange = statusChange;
//	}
//
//	public int getLabourCount() {
//		return labourCount;
//	}
//
//	public void setLabourCount(int labourCount) {
//		this.labourCount = labourCount;
//	}
//
//	public double getSpend() {
//		return spend;
//	}
//
//	public void setSpend(double spend) {
//		this.spend = spend;
//	}
//
//	public int getProjectStatus() {
//		return projectStatus;
//	}
//
//	public void setProjectStatus(int projectStatus) {
//		this.projectStatus = projectStatus;
//	}
//
//	public String getExitReason() {
//		return exitReason;
//	}
//
//	public void setExitReason(String exitReason) {
//		this.exitReason = exitReason;
//	}
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
//
//	@ManyToOne
//	@JoinColumn(name = "R_PROJECT_ID")
//	private ProjectEntity project;
//	
//	@ManyToOne
//	@JoinColumn(name = "R_STATUSCHANGE_ID")
//	private ProjectStatusChangeEntity statusChange;
//	
//
//
//	//投工数
//	@Column(name = "C_LABOURCOUNT")
//	private int labourCount;
//	
//	//折资额
//	@Column(name = "C_SPEND")
//	private double spend;
//
//	/**
//	 * 0-进行中
//	 * 1-竣工
//	 * 2-终止
//	 */
//	@Column(name = "C_PROJECTSTATUS")
//	private int projectStatus;
//	
//	//项目终止原因
//	@Column(name = "C_EXITREASON")
//	private String exitReason;
	
}
