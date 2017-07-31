package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.kernel.support.entities.AbstractPersistable;

/**
 * 要求的文档
 * 
 * @author ocean~
 */
@Entity
@Table(name = "t_requirereportitem")
public class RequireReportItemEntity extends AbstractPersistable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2948583177116150534L;

	@ManyToOne
	@JoinColumn(name = "R_REQUIREREPORT_ID")
	private RequireReportEntity requireReport;
	
//	@OneToMany(mappedBy = "requireReportItem", targetEntity = RequireReportDocumentEntity.class)
//	private List<RequireReportDocumentEntity> requireReportDocument;

	@ManyToOne
	@JoinColumn(name = "R_UNIT_ID")
	private UnitEntity unit; // 上报单位

	@Column(name = "C_STATUS")
	private int status; // 状态

	@Temporal(TemporalType.DATE)
	@Column(name = "C_SUBMITAT")
	private Date submitAt;//上报，提交时间

	@Column(name = "C_DOCUMENTCOUNT")
	private int documentCount; // 文档数量

	@Column(name = "C_DELETED")
	private boolean deleted;

	@Column(name = "C_URGE")
	private int urge;//催报，如果还没有上报文档，则进行重点通知；表示催报的次数

	public RequireReportEntity getRequireReport() {
		return requireReport;
	}

	public void setRequireReport(RequireReportEntity requireReport) {
		this.requireReport = requireReport;
	}

	public UnitEntity getUnit() {
		return unit;
	}

	public void setUnit(UnitEntity unit) {
		this.unit = unit;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDocumentCount() {
		return documentCount;
	}

	public void setDocumentCount(int documentCount) {
		this.documentCount = documentCount;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getUrge() {
		return urge;
	}

	public void setUrge(int urge) {
		this.urge = urge;
	}

	public Date getSubmitAt() {
		return submitAt;
	}

	public void setSubmitAt(Date submitAt) {
		this.submitAt = submitAt;
	}
}
