package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.kernel.support.entities.AbstractEntity;

/**
 * 要求上报记录
 * 
 * @author ocean~
 */
@Entity
@Table(name = "t_requirereport")
public class RequireReportEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7036395129933527619L;

//	@OneToMany(mappedBy = "requireReport", targetEntity = RequireReportItemEntity.class)
//	private List<RequireReportItemEntity> requireReportItem; // 文档要求

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_DIRECTORY_ID")
	private DirectoryEntity directory;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "C_STARTREPORTEDAT")
	private Date startReportedAt; // 开始上投时间

	@Temporal(TemporalType.DATE)
	@Column(name = "C_ENDREPORTEDAT")
	private Date endReportedAt; // 结束上投时间

	@Temporal(TemporalType.DATE)
	@Column(name = "C_ISSUEAT")
	private Date issueAt;//发布时间

	@Column(name = "C_STATUSISSUE")
	private int statusIssue;//发布状态

	@Temporal(TemporalType.DATE)
	@Column(name = "C_DRAFTAT")
	private Date draftAt;//草拟时间

	@Column(name = "C_ITEMCOUNT")
	private int itemCount; // 总数量

	@Column(name = "C_FINISHCOUNT")
	private int finishCount; // 已完成数量（统计已经有文档的item）

	@Column(name = "C_DOCUMENTCOUNT")
	private int documentCount;

	@Column(name = "C_STATUS")
	private int status; // 状态（0：草稿  1：提交  2：禁止）暂时不使用

	@Temporal(TemporalType.DATE)
	@Column(name = "C_FINISHAT")
	private Date finishAt;//归档时间

	//TODO TEXT->LONGTEXT
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "C_CONTENT", columnDefinition="LONGTEXT")
	private String content;//正文

	@Column(name = "C_ANNEX")
	private String annex;//附件
	@Column(name = "C_ANNEXNAME")
	private String annexName;//附件原来的名称和后缀
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_ANNEXFILE_ID")
	private FileEntity annexFile;

	@Column(name = "C_OFFICES")
	private String offices;//业务处室

	@Column(name = "C_OTHER")
	private String other;//其它

	public Date getIssueAt() {
		return issueAt;
	}

	public void setIssueAt(Date issueAt) {
		this.issueAt = issueAt;
	}

	public int getStatusIssue() {
		return statusIssue;
	}

	public void setStatusIssue(int statusIssue) {
		this.statusIssue = statusIssue;
	}

	public Date getDraftAt() {
		return draftAt;
	}

	public void setDraftAt(Date draftAt) {
		this.draftAt = draftAt;
	}

	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
	}

	public String getAnnexName() {
		return annexName;
	}

	public void setAnnexName(String annexName) {
		this.annexName = annexName;
	}

	public String getOffices() {
		return offices;
	}

	public void setOffices(String offices) {
		this.offices = offices;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Date getStartReportedAt() {
		return startReportedAt;
	}

	public void setStartReportedAt(Date startReportedAt) {
		this.startReportedAt = startReportedAt;
	}

	public Date getEndReportedAt() {
		return endReportedAt;
	}

	public void setEndReportedAt(Date endReportedAt) {
		this.endReportedAt = endReportedAt;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getFinishCount() {
		return finishCount;
	}

	public void setFinishCount(int finishCount) {
		this.finishCount = finishCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getFinishAt() {
		return finishAt;
	}

	public void setFinishAt(Date finishAt) {
		this.finishAt = finishAt;
	}

	public int getDocumentCount() {
		return documentCount;
	}

	public void setDocumentCount(int documentCount) {
		this.documentCount = documentCount;
	}

	public DirectoryEntity getDirectory() {
		return directory;
	}

	public void setDirectory(DirectoryEntity directory) {
		this.directory = directory;
	}

	public FileEntity getAnnexFile() {
		return annexFile;
	}

	public void setAnnexFile(FileEntity annexFile) {
		this.annexFile = annexFile;
	}
}
