package cn.bonoon.controllers.industry;

import cn.bonoon.controllers.inofs.BaseIndustryAreaInfo;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@InsertCell(value = "applicant/areaindustry-editor.vm", type = EmbedType.PARSE)
@DialogDefaultButton(
		buttonName = "暂存(未确认)",
		functionBody = "_checkNumber();",
		otherButtonName = "确认",
		otherParameterName = "applicant-submit",
		otherParameterValue = "true",
		otherFunctionBody = "if(!confirm('确认后示范片区信息才可以提交，是否确认？')){return false;}_checkNumber();"
	)
@WithDialog(initializer = IndustryAreaEditorInitializer.class)
@FormEditor(width = 620)
public class IndustryAreaEditor extends BaseIndustryAreaInfo implements IndustryAreaDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5985090962562487788L;

	private Long placeId;
	
	public Long getPlaceId() {
		return placeId;
	}
	
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
}
