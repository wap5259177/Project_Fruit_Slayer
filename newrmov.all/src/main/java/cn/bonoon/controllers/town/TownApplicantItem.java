package cn.bonoon.controllers.town;

import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;

@AsDataGrid(condition = TownApplicantCondition.class)
public class TownApplicantItem extends AbstractItem implements TownApplicantDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 938772022675558127L;

	@AsColumn(width = 100, ordinal = 0)
	private String countyName;
	@AsColumn(width = 100, ordinal = 2)
	private String name;
	@AsColumn(width = 100, ordinal = 3)
	private String contactName;
	@AsColumn(width = 100, ordinal = 4)
	private String contactPhone;
	@AsColumn(width = 90, ordinal = 5)
	private String applicantAt;
	
	@AsColumn(width = 60, ordinal = 6)
	private String status;

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

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

	public String getApplicantAt() {
		return applicantAt;
	}

	public void setApplicantAt(String applicantAt) {
		this.applicantAt = applicantAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
