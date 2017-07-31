package cn.bonoon.controllers.funds.area;

import cn.bonoon.controllers.funds.SpecialFundsCondition;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = SpecialFundsCondition.class, value = @GridOptions(operationWith = 100))
public class AreaSpecialFundsItem extends AbstractItem implements
		AreaSpecialFundsDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5453801081385110485L;

	//显示为列
	@AsColumn(width = 70, ordinal = 0)
	private int annual;

	// 列表显示为String
//	@TransformField("place.name")
//	@AsColumn(width = 170, ordinal = 1)
//	private String place;

	@AsColumn(width = 70, ordinal = 3)
	private double allocated;
	
	@AsColumn(width = 70, ordinal = 2)
	private double quota;

	@AsColumn(width = 70, ordinal = 4)
	private String contactName;

	@AsColumn(width = 100, ordinal = 5)
	private String contactPhone;

	// 下拨时间显示为String
	@AsColumn(width = 70, ordinal = 6)
	private String recordAt;
	
	//status可能要String显示
	@AsColumn(width = 70, ordinal = 7)
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getAllocated() {
		return allocated;
	}

	public void setAllocated(double allocated) {
		this.allocated = allocated;
	}
	
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

	public String getRecordAt() {
		return recordAt;
	}

	public void setRecordAt(String recordAt) {
		this.recordAt = recordAt;
	}

//	public String getPlace() {
//		return place;
//	}
//
//	public void setPlace(String place) {
//		this.place = place;
//	}
}
