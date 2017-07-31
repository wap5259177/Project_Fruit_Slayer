package cn.bonoon.controllers.funds.allocated;

import java.util.Date;

import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.annotations.AutoDataLoader;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.components.AsComboBox;
import cn.bonoon.kernel.web.annotations.components.AsDateBox;
import cn.bonoon.kernel.web.annotations.components.AsTextArea;
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
public class SpecialFundsEditor extends ObjectEditor implements SpecialFundsDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1278982409165603813L;

	//编辑annotation
	@PropertyEditor(0)
	private int annual;

	//place编辑显示为Long
	@PropertyEditor(1)
	@AsComboBox
	@AutoDataLoader(value = PlaceEntity.class, queries = @QueryExpression("x.level=2"))
	private Long place;

	@PropertyEditor(2)
	private double quota;

	@PropertyEditor(4)
	private String contactName;

	@PropertyEditor(5)
	private String contactPhone;

	@PropertyEditor(3)
	@AsDateBox
	private Date recordAt;

	@PropertyEditor(value = 100, colspan = 1)
	@AsTextArea
	private String remark;

	public int getAnnual() {
		return annual;
	}

	public void setAnnual(int annual) {
		this.annual = annual;
	}

	public double getQuota() {
		return quota;
	}

	public void setQuota(double quota) {
		this.quota = quota;
	}

	public Long getPlace() {
		return place;
	}

	public void setPlace(Long place) {
		this.place = place;
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

	public Date getRecordAt() {
		return recordAt;
	}

	public void setRecordAt(Date recordAt) {
		this.recordAt = recordAt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
