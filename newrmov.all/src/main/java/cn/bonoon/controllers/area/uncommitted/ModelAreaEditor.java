package cn.bonoon.controllers.area.uncommitted;

import java.util.Date;

import cn.bonoon.controllers.area.ModelAreaDefine;
import cn.bonoon.core.ModelAreaInserter;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.components.AsDateBox;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.InsertCell;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyHelper;

@Transform
@FormEditor(value = 2, width = 250, headWidth = 150)
@DialogDefaultButton(buttonName = "暂存", otherButtonName = "创建", otherParameterName = "applicant-create", otherParameterValue = "true", otherFunctionBody = "if(!confirm('创建示范片区封面后将不可修改，是否确定创建？')){return false;}")
@InsertCell(value = "applicant/modelarea-rural.vm", type = EmbedType.PARSE, ordinal = 20, colspan = 1)
@WithDialog(initializer = ModelAraeInsertInitializer.class)
public class ModelAreaEditor extends ObjectEditor implements ModelAreaDefine ,ModelAreaInserter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8006112650108588985L;

	@PropertyEditor(value = 1)
	@PropertyHelper("如果不填写，则使用“当前县名+示范片区”自动填写！")
	private String name;

	@PropertyEditor(required = true, value = 12)
	@AsDateBox
	private Date applicatAt;

	@PropertyEditor(required = true, value = 13)
	private String contactName;

	@PropertyEditor(required = true, value = 14)
	private String contactPhone;

	@PropertyEditor(required = true, value = 15)
	private String contactJob;//职务、职位
	@PropertyEditor(required = true, value = 16)
	private String contactPhone2;//联系电话2
	
	@PropertyEditor(value = 26, colspan = 1)
	private String remark;
	
	@TransformField(readable = false, writable = WriteModel.NONE)
	private int[] crorder;
	@TransformField(readable = false, writable = WriteModel.NONE)
	private int[] prorder;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getApplicatAt() {
		return applicatAt;
	}

	public void setApplicatAt(Date applicatAt) {
		this.applicatAt = applicatAt;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int[] getCrorder() {
		return crorder;
	}

	public void setCrorder(int[] crorder) {
		this.crorder = crorder;
	}

	public int[] getProrder() {
		return prorder;
	}

	public void setProrder(int[] prorder) {
		this.prorder = prorder;
	}

	public String getContactPhone2() {
		return contactPhone2;
	}

	public void setContactPhone2(String contactPhone2) {
		this.contactPhone2 = contactPhone2;
	}

	public String getContactJob() {
		return contactJob;
	}

	public void setContactJob(String contactJob) {
		this.contactJob = contactJob;
	}

}
