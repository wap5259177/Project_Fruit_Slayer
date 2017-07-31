package cn.bonoon.controllers.felicity.report;

import java.util.Date;

import cn.bonoon.controllers.felicity.countyreport.FelicityCountyReportDefine;
import cn.bonoon.controllers.felicity.countyreport.FelicityCountyReportInfo;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.form.FormUpdate;
import cn.bonoon.kernel.web.annotations.form.InsertCell;
import cn.bonoon.kernel.web.annotations.form.PropertyUpdate;

@Transform
@DialogDefaultButton(
		buttonName = "暂存",
		functionBody = "_checkNumber();",
		otherButtonName = "提交",
		otherParameterName = "applicant-submit",
		otherParameterValue = "true",
		otherFunctionBody = "if(!confirm('是否确定提交幸福村居的月度报表？')){return false;}_checkNumber();"
	)
@FormUpdate(value = 3, width = 100, headWidth = 140)
@InsertCell(value = "felicity/report-county-editor.vm", type = EmbedType.PARSE)
public class FelicityCountyReportUpdater extends FelicityCountyReportInfo implements FelicityCountyReportDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -416072690260394753L;

	@PropertyUpdate(3)
	private String informant;//填报人
	@PropertyUpdate(4)
	private String contact;//联系电话
	@PropertyUpdate(5)
	private Date reportingAt;

	@TransformField(writable = WriteModel.NONE)
	private String auditName;
	@TransformField(writable = WriteModel.NONE)
	private String auditAt;
	@TransformField(writable = WriteModel.NONE)
	private String auditContent;
	
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getAuditAt() {
		return auditAt;
	}
	public void setAuditAt(String auditAt) {
		this.auditAt = auditAt;
	}
	public String getAuditContent() {
		return auditContent;
	}
	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}
	public String getInformant() {
		return informant;
	}
	public void setInformant(String informant) {
		this.informant = informant;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Date getReportingAt() {
		return reportingAt;
	}
	public void setReportingAt(Date reportingAt) {
		this.reportingAt = reportingAt;
	}
	
}
