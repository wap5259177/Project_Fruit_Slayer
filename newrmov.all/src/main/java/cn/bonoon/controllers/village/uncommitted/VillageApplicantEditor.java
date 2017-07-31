package cn.bonoon.controllers.village.uncommitted;

import cn.bonoon.controllers.village.BaseVillageApplicantEditor;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@FormEditor(3)
@InsertCell(value = "applicant/village-editor.vm", type = EmbedType.PARSE)
@WithDialog(initializer = VillageApplicantEditorInitializer.class)
@DialogDefaultButton(
		buttonName = "暂存",
		otherButtonName = "提交",
		otherParameterName = "applicant-submit",
		otherParameterValue = "true",
		otherFunctionBody = "if(!confirm('提交后不可能再修改数据，是否确定提交？')){return false;}"
	)
public class VillageApplicantEditor extends BaseVillageApplicantEditor{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2328697487137761361L;
	
	@TransformField
	private int year;
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
}
