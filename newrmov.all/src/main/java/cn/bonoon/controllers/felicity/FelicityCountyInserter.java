package cn.bonoon.controllers.felicity;

import cn.bonoon.core.IFelicityCountyInserter;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@FormEditor(width = 350, headWidth = 250)
@DialogDefaultButton(buttonName = "暂存", otherButtonName = "创建", otherParameterName = "applicant-create", otherParameterValue = "true", otherFunctionBody = "if(!confirm('创建幸福村居封面后将不可修改，是否确定创建？')){return false;}")
@InsertCell(value = "felicity/county-editor.vm", type = EmbedType.PARSE, ordinal = 20, colspan = 1)
@WithDialog(initializer = FelicityCountyInsertInitializer.class)
public class FelicityCountyInserter extends ObjectEditor implements IFelicityCountyInserter{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2826273876612444418L;

	@TransformField(readable = false, writable = WriteModel.NONE)
	private String[] ordinals;
	
	public String[] getOrdinals() {
		return ordinals;
	}
	
	public void setOrdinals(String[] ordinals) {
		this.ordinals = ordinals;
	}
}
