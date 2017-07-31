package cn.bonoon.controllers.felicity.countyreport;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = FelicityCountyReportCondition.class, value = @GridOptions(operationWith = 180))
public class FelicityCountyReportItem extends AbstractItem implements FelicityCountyReportDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4249672904548209249L;

//	@TransformField("county.name")
//	@AsColumn(width = 160, ordinal = 0, sortable = false)
//	private String name;

	@AsColumn(width = 80, ordinal = 0)
	private int annual;
	@AsColumn(width = 60, ordinal = 1)
	private String month;
	@AsColumn(width = 160, ordinal = 11)
	private String informant;
	
	@AsColumn(width = 160, ordinal = 12)
	private String contact;
	
	@AsColumn(width = 100, ordinal = 13)
	private String reportingAt;

	@AsColumn(width = 80, ordinal = 14)
	private String status;

	@TransformField(value = "status", writable = WriteModel.NONE)
	private int statusValue;
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

	public String getInformant() {
		return informant;
	}

	public void setInformant(String informant) {
		this.informant = informant;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getReportingAt() {
		return reportingAt;
	}

	public void setReportingAt(String reportingAt) {
		this.reportingAt = reportingAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getAnnual() {
		return annual;
	}

	public void setAnnual(int annual) {
		this.annual = annual;
	}

	public int getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}

	
}
