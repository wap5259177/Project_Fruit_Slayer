package cn.bonoon.controllers.area.back;

import java.util.Date;

import cn.bonoon.controllers.area.ModelAreaDefine;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.components.AsDateBox;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;

@Transform
@FormEditor(2)
@DialogDefaultButton(
		buttonName = "暂存", 
		otherButtonName = "提交", 
		otherParameterName = "applicant-submit", 
		otherParameterValue = "true", 
		otherFunctionBody = "if(!confirm('提交后不可能再修改数据，是否确定提交？')){return false;}")
public class ModelAreaBackEditor extends ObjectEditor implements ModelAreaDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8006112650108588985L;

	@PropertyEditor(1)
	private String name;
	
	@PropertyEditor(2)
	@AsDateBox
	private Date applicatAt;
	
	@PropertyEditor(3)
	private String contactName;

	@PropertyEditor(4)
	private String contactPhone;
	
	@PropertyEditor(5)
	private String cityName;
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

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
}
