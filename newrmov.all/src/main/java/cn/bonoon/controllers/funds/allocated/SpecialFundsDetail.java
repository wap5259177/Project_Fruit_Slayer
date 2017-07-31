package cn.bonoon.controllers.funds.allocated;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail;

@FormDetail(2)
public class SpecialFundsDetail implements SpecialFundsDefine {

	//查看annotation
	@PropertyDetail(1)
	private int annual;
	//显示string
	@TransformField("place.name")
	@PropertyDetail(2)
	private String place;
	@PropertyDetail(3)
	private double quota;
	@PropertyDetail(4)
	private String contactName;
	@PropertyDetail(5)
	private String contactPhone;
	//时间显示string
	@PropertyDetail(6)
	private String recordAt;
	@PropertyDetail(7)
	private String createAt;
	@PropertyDetail(value = 10, colspan = 1)
	private String remark;
	@PropertyDetail(8)
	private int status;
	
	public String getRecordAt() {
		return recordAt;
	}
	public void setRecordAt(String recordAt) {
		this.recordAt = recordAt;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getAnnual() {
		return annual;
	}
	public void setAnnual(int annual) {
		this.annual = annual;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public double getQuota() {
		return quota;
	}
	public void setQuota(double quota) {
		this.quota = quota;
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
