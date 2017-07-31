package cn.bonoon.controllers.funds.area;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail;

@FormDetail(2)
public class AreaSpecialFundsDetail implements AreaSpecialFundsDefine {

	//detail排序
	@PropertyDetail(1)
	private int annual;

	// 显示为String
	@TransformField("place.name")
	@PropertyDetail(2)
	private String place;

	@PropertyDetail(3)
	private double allocated;
	@PropertyDetail(4)
	private double quota;
	@PropertyDetail(5)
	private String contactName;
	@PropertyDetail(6)
	private String contactPhone;
	@PropertyDetail(7)
	private String recordAt;
	@PropertyDetail(value = 9, colspan = 1, height = 100)
	private String remark;
	@PropertyDetail(8)
	private String status;

	public String getRecordAt() {
		return recordAt;
	}

	public void setRecordAt(String recordAt) {
		this.recordAt = recordAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public double getAllocated() {
		return allocated;
	}

	public void setAllocated(double allocated) {
		this.allocated = allocated;
	}

	public double getQuota() {
		return quota;
	}

	public void setQuota(double quota) {
		this.quota = quota;
	}

}
