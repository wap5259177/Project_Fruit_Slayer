package cn.bonoon.controllers.file.reportitem;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail;

@FormDetail(value = 3, headWidth = 110, width = 160)
@InsertCell(value = "report/item_detail_tabs.vm", type = EmbedType.PARSE, ordinal = 1, colspan = 2)
@WithDialog(initializer = RequireReportItemDetailInitializer.class)
public class RequireReportItemDetail extends ObjectEditor implements RequireReportItemDefine {

	private static final long serialVersionUID = 8252629444126003068L;

	@TransformField("requireReport.name")
	@PropertyDetail(colspan = 2, value = 0)
	private String name;

	@PropertyDetail(3)
	private int documentCount;

	@TransformField("requireReport.startReportedAt")
	@PropertyDetail(4)
	private String startReportedAt;

	@TransformField("requireReport.endReportedAt")
	@PropertyDetail(5)
	private String endReportedAt;

	@PropertyDetail(ignore = true)
	private String status;
	@TransformField("requireReport.issueAt")
	@PropertyDetail(ignore = true)
	private String issueAt;
	@TransformField("requireReport.draftAt")
	@PropertyDetail(ignore = true)
	private String draftAt;

	@TransformField("unit.name")
	@PropertyDetail(ignore = true)
	private String unit;
	@TransformField("requireReport.content")
	@PropertyDetail(ignore = true)
	private String content;
	@TransformField("requireReport.annex")
	@PropertyDetail(ignore = true)
	private String annex;
	@TransformField("requireReport.annexName")
	@PropertyDetail(ignore = true)
	private String annexName;
	@TransformField("requireReport.offices")
	@PropertyDetail(ignore = true)
	private String offices;
	@TransformField("requireReport.other")
	@PropertyDetail(ignore = true)
	private String other;
	@TransformField("requireReport.remark")
	@PropertyDetail(ignore = true)
	private String remark;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getDraftAt() {
		return draftAt;
	}

	public void setDraftAt(String draftAt) {
		this.draftAt = draftAt;
	}

	public String getIssueAt() {
		return issueAt;
	}

	public void setIssueAt(String issueAt) {
		this.issueAt = issueAt;
	}

}
