package cn.bonoon.controllers.file.reportitem;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;

public class BaseRequireReportItemItem extends AbstractItem implements RequireReportItemDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2262320564436997313L;

	@TransformField("requireReport.name")
	@AsColumn(width = 180, ordinal = 0, sortable = false)
	private String name;

	@TransformField("unit.name")
	@AsColumn(width = 180, ordinal = 1)
	private String unit;

	@AsColumn(width = 60, ordinal = 2)
	private int documentCount;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getDocumentCount() {
		return documentCount;
	}

	public void setDocumentCount(int documentCount) {
		this.documentCount = documentCount;
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

	public int getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}

}
