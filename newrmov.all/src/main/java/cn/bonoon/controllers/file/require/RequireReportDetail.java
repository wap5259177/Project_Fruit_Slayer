package cn.bonoon.controllers.file.require;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail;

@FormDetail(value = 3, width = 160, headWidth = 110)
@InsertCell(value = "report/report_detail_tabs.vm", type = EmbedType.PARSE, ordinal = 1, colspan = 2)
@WithDialog(initializer = RequireReportDetailInitializer.class)
public class RequireReportDetail implements RequireReportDefine {

	@PropertyDetail(ignore = true)
	private Long id;
	
	@PropertyDetail(value = 0, colspan = 2)
	private String name;

	@PropertyDetail(ignore = true)
	private String status;

	@TransformField("status")
	@PropertyDetail(ignore = true)
	private int statusValue;
	
	@PropertyDetail(ignore = true)
	private String statusIssue;

//	@PropertyDetail(2)
//	private String creatorName;
//
//	@PropertyDetail(3)
//	private String createAt;
	@PropertyDetail(10)
	private int itemCount;
	@PropertyDetail(11)
	private int finishCount;
	@PropertyDetail(12)
	private int documentCount;

	@PropertyDetail(ignore = true)
	private String startReportedAt;

	@PropertyDetail(ignore = true)
	private String endReportedAt;

	@PropertyDetail(ignore = true)
	private String remark;

	@PropertyDetail(ignore = true)
	private String content;
	@PropertyDetail(ignore = true)
	private String issueAt;
	@PropertyDetail(ignore = true)
	private String draftAt;
	@PropertyDetail(ignore = true)
	private String finishAt;
	@PropertyDetail(ignore = true)
	private String annex;
	@PropertyDetail(ignore = true)
	private String annexName;
	@PropertyDetail(ignore = true)
	private String offices;
	@PropertyDetail(ignore = true)
	private String other;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusIssue() {
		return statusIssue;
	}

	public void setStatusIssue(String statusIssue) {
		this.statusIssue = statusIssue;
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

	public int getDocumentCount() {
		return documentCount;
	}

	public void setDocumentCount(int documentCount) {
		this.documentCount = documentCount;
	}

	public String getStartReportedAt() {
		return startReportedAt;
	}

	public void setStartReportedAt(String startReportedAt) {
		this.startReportedAt = startReportedAt;
	}

	public String getEndReportedAt() {
		return endReportedAt;
	}

	public void setEndReportedAt(String endReportedAt) {
		this.endReportedAt = endReportedAt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIssueAt() {
		return issueAt;
	}

	public void setIssueAt(String issueAt) {
		this.issueAt = issueAt;
	}

	public String getDraftAt() {
		return draftAt;
	}

	public void setDraftAt(String draftAt) {
		this.draftAt = draftAt;
	}

	public String getFinishAt() {
		return finishAt;
	}

	public void setFinishAt(String finishAt) {
		this.finishAt = finishAt;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
//
//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}

//	public String getCreatorName() {
//		return creatorName;
//	}
//
//	public void setCreatorName(String creatorName) {
//		this.creatorName = creatorName;
//	}
//
//	public Date getStartReportedAt() {
//		return startReportedAt;
//	}
//
//	public void setStartReportedAt(Date startReportedAt) {
//		this.startReportedAt = startReportedAt;
//	}
//
//	public Date getEndReportedAt() {
//		return endReportedAt;
//	}
//
//	public void setEndReportedAt(Date endReportedAt) {
//		this.endReportedAt = endReportedAt;
//	}

}
