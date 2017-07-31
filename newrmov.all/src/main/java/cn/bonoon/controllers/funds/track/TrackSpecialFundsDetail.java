package cn.bonoon.controllers.funds.track;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail;

@FormDetail(2)
public class TrackSpecialFundsDetail implements TrackSpecialFundsDefine {

	@PropertyDetail(7)
	private int annual;
	
	//显示为String
	@TransformField("place.name")
	@PropertyDetail(0)
	private String place;
	
	@PropertyDetail(1)
	private double quota;
	@PropertyDetail(2)
	private String contactName;
	@PropertyDetail(3)
	private String contactPhone;
	@PropertyDetail(4)
	private String recordAt;
	@PropertyDetail(value = 8 , colspan = 1)
	private String remark;
	@PropertyDetail(6)	
	private String createAt;
	@PropertyDetail(5)
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
