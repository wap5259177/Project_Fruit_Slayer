package cn.bonoon.controllers.project.status;

import cn.bonoon.controllers.project.ProjectDefine;
import cn.bonoon.controllers.project.ProjectEditor;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@FormEditor(width = 620)
@InsertCell(value = "applicant/areaproject-editor.vm", type = EmbedType.PARSE)
@DialogDefaultButton(
		buttonName = "确定",
		functionBody = "_checkNumber();"
//		otherButtonName = "提交",
//		otherParameterName = "applicant-submit",
//		otherParameterValue = "true",
//		otherFunctionBody = "if(!confirm('提交后不可能再修改数据，是否确定提交？')){return false;}_checkNumber();"
	)
@WithDialog(initializer = ProjectUpdateBaseInfoEditorInitializer.class)
public class ProjectUpdateBaseInfoEditor extends ProjectEditor implements ProjectDefine{

	private static final long serialVersionUID = -9176142379702216697L;

	@TransformField(value="status",writable=WriteModel.NONE)
	private int status;
	
	@TransformField(value="isUpdate",writable=WriteModel.NONE)
	private Boolean isUpdate;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Boolean getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	

	
}
