package cn.bonoon.controllers.survey;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = PageCondition.class, value= @GridOptions(operationWith = 265))
public class CitySurveyItem extends AbstractItem implements CitySurveyDefine{
	/**
	 * 
	 */
	private static final long serialVersionUID = -615384535390847222L;
	@TransformField("province.")
	@AsColumn(width = 80, ordinal = 0,sortable=false)
	private int annual;
	@TransformField("province.")
	@AsColumn(width = 120, ordinal = 10,sortable=false)
	private String deadline;
	@TransformField("province.")
	@AsColumn(width = 120, ordinal = 11,sortable=false)
	private String startAt;
	@TransformField("province.")
	@AsColumn(width = 120, ordinal = 12,sortable=false)
	private String endAt;
	@AsColumn(width = 80, ordinal = 30)
	private String status;
	@TransformField("status")
	private int statusValue;
	@AsColumn(width = 60, ordinal = 20)
	private int countyCount;
	@AsColumn(width = 60, ordinal = 21)
	private int urge;
	public int getAnnual() {
		return annual;
	}
	public void setAnnual(int annual) {
		this.annual = annual;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getStartAt() {
		return startAt;
	}
	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}
	public String getEndAt() {
		return endAt;
	}
	public void setEndAt(String endAt) {
		this.endAt = endAt;
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
	public int getCountyCount() {
		return countyCount;
	}
	public void setCountyCount(int countyCount) {
		this.countyCount = countyCount;
	}
	public int getUrge() {
		return urge;
	}
	public void setUrge(int urge) {
		this.urge = urge;
	}
}
