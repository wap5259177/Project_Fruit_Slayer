package cn.bonoon.controllers.newrural;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.HasFile;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@InsertCell(value = "applicant/administrationrural-uncore-editor.vm", type = EmbedType.PARSE)
@DialogDefaultButton(buttonName = "暂存(未确认)", functionBody = "_checkNumber();", otherButtonName = "确认", otherParameterName = "applicant-submit", otherParameterValue = "true", otherFunctionBody = "if(!confirm('确认后示范片区信息才可以提交，是否确认？')){return false;}_checkNumber();")
@WithDialog(initializer = AdministrationRuralEditorInitializerNoCore.class)
@FormEditor(width = 620)
@HasFile
public class AdministrationRuralEditorNoCore  extends AdministrationRuralDetailNoCore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -498874489390952739L;

	/**
	 * 
	 */

	
}
