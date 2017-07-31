package cn.bonoon.controllers.file.reportitem;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.InsertCell;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;

@Transform
@FormEditor(value = 2, headWidth = 100, width = 140)
@InsertCell(value = "report/report_editor.vm", type = EmbedType.PARSE)
@DialogDefaultButton(
		buttonName = "暂存", 
		otherButtonName = "提交", 
		otherParameterName = "applicant-submit", 
		otherParameterValue = "true", 
		otherFunctionBody = "if(!confirm('提交后不可能再修改数据，是否确定提交？')){return false;}")
public class RequireReportItemEditor extends ObjectEditor implements RequireReportItemDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1794251764663589206L;
	
	@PropertyEditor(readonly = true)
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
