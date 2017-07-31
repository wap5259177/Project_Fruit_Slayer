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
 * 市、县单位的补报申请
 * @author jackson
 *
 */
@Entity
@Table(name = "t_requirereportapply")
public class RequireReportApplyEntity extends AbstractPersistable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8638084131165034852L;

	@ManyToOne
	@JoinColumn(name = "R_REPORT_ID")
	private RequireReportItemEntity report;
	
	//名称，对应report.requireReport.name
	@Column(name = "C_NAME")
	private String name;
	
	//限制在1000字以内，申请补报的理由
	@Column(name = "C_REASON", length = 1000)
	private String reason;

	//省办回复的备注，即该申请通过或不通过，省办可以在这里做一些文字说明
	//这个字段由省办填写，市、县办只能查看
	@Column(name = "C_REMARK", length = 1000)
	private String remark;

	@Column(name = "C_STATUS")
	private int status; // 状态（0:草稿状态  1:等待省办审核 2：省办审核通过，可以补报文档 3：省办审核不通过）

	@Column(name = "C_CREATORID")
	private Long creatorId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "C_CREATEAT", nullable = false)
	private Date createAt;
	@Column(name = "C_CREATORNAME")
	private String creatorName;
	@Column(name = "C_OWNERID")
	private long ownerId;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public RequireReportItemEntity getReport() {
		return report;
	}

	public void setReport(RequireReportItemEntity report) {
		this.report = report;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
