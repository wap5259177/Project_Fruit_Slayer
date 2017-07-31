package cn.bonoon.controllers.village.back;

import java.util.Date;

import cn.bonoon.controllers.village.BaseVillageApplicantEditor;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@FormEditor(3)
@InsertCell(value = "applicant/village-editor.vm", type = EmbedType.PARSE)
@WithDialog(initializer = BackVillageApplicantEditorInitializer.class)
@DialogDefaultButton(
		buttonName = "暂存",
		otherButtonName = "提交",
		otherParameterName = "applicant-submit",
		otherParameterValue = "true",
		otherFunctionBody = "if(!confirm('提交后不可能再修改数据，是否确定提交？')){return false;}"
	)
public class BackVillageApplicantEditor extends BaseVillageApplicantEditor{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2328697487137761361L;

	@TransformField(writable = WriteModel.NONE)
	private String rejectContent;
	@TransformField(writable = WriteModel.NONE)
	private Date rejectAt;

	public String getRejectContent() {
		return rejectContent;
	}

	public void setRejectContent(String rejectContent) {
		this.rejectContent = rejectContent;
	}

	public Date getRejectAt() {
		return rejectAt;
	}

	public void setRejectAt(Date rejectAt) {
		this.rejectAt = rejectAt;
	}
}
