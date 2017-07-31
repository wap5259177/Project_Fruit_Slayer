package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.kernel.support.entities.AbstractPersistable;

/**
 * 上传的文档
 * 
 * @author ocean~
 */
@Entity
@Table(name = "t_requirereportdocument")
public class RequireReportDocumentEntity extends AbstractPersistable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4830702994501263158L;
	
	@ManyToOne
	@JoinColumn(name = "R_REQUIREREPORTITEM_ID")
	private RequireReportItemEntity requireReportItem;

	@Column(name = "C_OPERATOR")
	private String operator; // 操作人

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "C_OPERATEAT")
	private Date operateAt; // 操作时间
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_DOCUMENT_ID")
	private FileEntity document;

	public RequireReportItemEntity getRequireReportItem() {
		return requireReportItem;
	}

	public void setRequireReportItem(RequireReportItemEntity requireReportItem) {
		this.requireReportItem = requireReportItem;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOperateAt() {
		return operateAt;
	}

	public void setOperateAt(Date operateAt) {
		this.operateAt = operateAt;
	}

	public FileEntity getDocument() {
		return document;
	}

	public void setDocument(FileEntity document) {
		this.document = document;
	}
}
