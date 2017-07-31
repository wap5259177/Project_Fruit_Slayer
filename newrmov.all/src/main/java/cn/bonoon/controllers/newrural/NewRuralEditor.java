package cn.bonoon.controllers.newrural;

import cn.bonoon.controllers.inofs.BaseNewRuralInfo;
import cn.bonoon.core.IRuralEditor;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.HasFile;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@InsertCell(value = "applicant/newrural-editor.vm", type = EmbedType.PARSE)
@DialogDefaultButton(buttonName = "暂存(未确认)", functionBody = "_checkNumber();", otherButtonName = "确认", otherParameterName = "applicant-submit", otherParameterValue = "true", otherFunctionBody = "if(!confirm('确认后示范片区信息才可以提交，是否确认？')){return false;}_checkNumber();")
@WithDialog(initializer = NewRuralEditorInitializer.class)
@FormEditor(width = 620)
@HasFile
public class NewRuralEditor extends BaseNewRuralInfo implements IRuralEditor{
	/**
	 * 
	 */
	private static final long serialVersionUID = -543390148860092050L;

	
	@TransformField(readable = false, writable = WriteModel.NONE)
	private String[] unitItems;
	@TransformField(readable = false, writable = WriteModel.NONE)
	private String[] workgroupItems;
	@TransformField(readable = false, writable = WriteModel.NONE)
	private String[] expertgroupItems;
	
	public String[] getUnitItems() {
		return unitItems;
	}
	public void setUnitItems(String[] unitItems) {
		this.unitItems = unitItems;
	}
	public String[] getWorkgroupItems() {
		return workgroupItems;
	}
	public void setWorkgroupItems(String[] workgroupItems) {
		this.workgroupItems = workgroupItems;
	}
	public String[] getExpertgroupItems() {
		return expertgroupItems;
	}
	public void setExpertgroupItems(String[] expertgroupItems) {
		this.expertgroupItems = expertgroupItems;
	}

}
