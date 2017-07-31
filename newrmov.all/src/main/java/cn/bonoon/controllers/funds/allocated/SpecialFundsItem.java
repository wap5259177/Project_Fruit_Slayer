package cn.bonoon.controllers.funds.allocated;

import cn.bonoon.controllers.funds.SpecialFundsCondition;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = SpecialFundsCondition.class, value= @GridOptions(operationWith = 100))
public class SpecialFundsItem extends AbstractItem implements SpecialFundsDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5453801081385110485L;

	//显示为列annotation
	@AsColumn(width = 170, ordinal = 0)
	private int annual;
	
	//显示String
	@TransformField("place.name")
	@AsColumn(width = 170, ordinal = 1)
	private String place;
	
	@AsColumn(width = 170, ordinal = 2)
	private double quota;
	
	@AsColumn(width = 170, ordinal = 3)
	private String contactName;
	
	@AsColumn(width = 170, ordinal = 4)
	private String contactPhone;
	
	@AsColumn(width = 170, ordinal = 5)
	private String recordAt;
	
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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
}
