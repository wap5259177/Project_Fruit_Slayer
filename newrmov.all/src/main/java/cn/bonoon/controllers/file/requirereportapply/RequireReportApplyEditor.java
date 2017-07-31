package cn.bonoon.controllers.file.requirereportapply;

import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.annotations.AutoDataLoader;
import cn.bonoon.kernel.web.annotations.AutoDataResultFields;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.components.AsComboBox;
import cn.bonoon.kernel.web.annotations.components.AsTextArea;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;

@Transform
@FormEditor(value = 2, headWidth = 100, width = 140)
//@InsertCell(value = "applicant/modelarea-editor.vm", type = EmbedType.PARSE)
@DialogDefaultButton(
		buttonName = "暂存", 
		otherButtonName = "提交", 
		otherParameterName = "applicant-submit", 
		otherParameterValue = "true", 
		otherFunctionBody = "if(!confirm('提交后不可能再修改数据，是否确定提交？')){return false;}")


public class RequireReportApplyEditor extends ObjectEditor implements RequireReportApplyDefine{
	
	private static final long serialVersionUID = 1056351581192896142L;
	
	@PropertyEditor(0)
	private String name;
	
	@PropertyEditor(1)
	@AsComboBox
	@TransformField
	@AutoDataLoader(
			value = RequireReportItemEntity.class, 
			queries = {@QueryExpression("x.unit.id={USER owner} and x.deleted=0 and x.requireReport.deleted=0 and x.requireReport.endReportedAt<curdate()")})
	@AutoDataResultFields("x.id, x.requireReport.name")
	private Long report;
	
	@PropertyEditor(value = 2, colspan = 1)
	@AsTextArea
	private String reason;
	
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
	public Long getReport() {
		return report;
	}
	public void setReport(Long report) {
		this.report = report;
	}
	
}
