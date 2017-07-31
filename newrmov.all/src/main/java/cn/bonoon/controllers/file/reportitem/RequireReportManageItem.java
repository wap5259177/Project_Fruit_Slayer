package cn.bonoon.controllers.file.reportitem;

import cn.bonoon.controllers.file.require.RequireReportManageCondition;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = RequireReportManageCondition.class, value= @GridOptions(operationWith = 200))
public class RequireReportManageItem extends AbstractItem implements RequireReportItemDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 397762405169970470L;
	
	@TransformField("requireReport.name")
	@AsColumn(width = 180, ordinal = 0, sortable = false)
	private String rname;
	
	@TransformField("requireReport.offices")
	@AsColumn(width = 160, ordinal = 1, sortable = false)
	private String offices;
	
	@TransformField("unit.name")
	@AsColumn(width = 180, ordinal = 2)
	private String unit;
	
	@AsColumn(width = 60, ordinal = 3)
	private String status;
	
	@TransformField("status")
	private int statusValue;

	@TransformField("requireReport.startReportedAt")
	@AsColumn(width = 100, ordinal = 4, sortable = false)
	private String startReportedAt;

	@TransformField("requireReport.endReportedAt")
	@AsColumn(width = 100, ordinal = 5, sortable = false)
	private String endReportedAt;
	
	@AsColumn(width = 100, ordinal = 6)
	private String submitAt;

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getOffices() {
		return offices;
	}

	public void setOffices(String offices) {
		this.offices = offices;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartReportedAt() {
		return startReportedAt;
	}

	public void setStartReportedAt(String startReportedAt) {
		this.startReportedAt = startReportedAt;
	}

	public String getEndReportedAt() {
		return endReportedAt;
	}

	public void setEndReportedAt(String endReportedAt) {
		this.endReportedAt = endReportedAt;
	}

	public String getSubmitAt() {
		return submitAt;
	}

	public void setSubmitAt(String submitAt) {
		this.submitAt = submitAt;
	}

	public int getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}

}
