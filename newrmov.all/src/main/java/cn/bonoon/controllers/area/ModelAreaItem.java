package cn.bonoon.controllers.area;

import java.util.Date;

import cn.bonoon.controllers.area.manage.ModelAreaManageCondition;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = ModelAreaManageCondition.class, value= @GridOptions(operationWith = 400))
public class ModelAreaItem extends AbstractItem implements ModelAreaDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5360635473296633888L;

	@AsColumn(width = 90, ordinal = 0)
	private String cityName;
	@AsColumn(width = 90, ordinal = 2)
	private String county;
	@AsColumn(width = 160, ordinal = 10)
	private String name;
	@AsColumn(width = 90, ordinal = 12)
	private String contactName;
	@AsColumn(width = 50, ordinal = 11)
	private String batch;
//	@AsColumn(width = 80, ordinal = 12)
//	private String contactJob;
	@AsColumn(width = 100, ordinal = 13)
	private String contactPhone;
	@AsColumn(width = 100, ordinal = 14)
	private String contactPhone2;
	@AsColumn(width = 100, ordinal = 15)
	private Date applicatAt;
	@AsColumn(width = 50, ordinal = 30)
	private String status;
	
	@TransformField("status")
	private int statusValue;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getApplicatAt() {
		return applicatAt;
	}
	public void setApplicatAt(Date applicatAt) {
		this.applicatAt = applicatAt;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}
//	public String getContactJob() {
//		return contactJob;
//	}
//	public void setContactJob(String contactJob) {
//		this.contactJob = contactJob;
//	}
	public String getContactPhone2() {
		return contactPhone2;
	}
	public void setContactPhone2(String contactPhone2) {
		this.contactPhone2 = contactPhone2;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	
}
