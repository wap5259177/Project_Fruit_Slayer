package cn.bonoon.controllers.area;

import cn.bonoon.controllers.inofs.BaseModelAreaInfo;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormUpdate;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@FormUpdate(width = 620)
@InsertCell(value = "applicant/modelarea-editor.vm", type = EmbedType.PARSE)
@WithDialog(initializer = ModelAreaEditorInitializer.class, height = 500)
@DialogDefaultButton(buttonName="暂存",functionBody = "_checkNumber();")
public class ModelAreaUpdate extends BaseModelAreaInfo implements ModelAreaDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5008493008951006924L;

	@TransformField("status")
	private int auditStatus;
	private String auditName;
	private String auditAt;
	private String auditContent;
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
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
}