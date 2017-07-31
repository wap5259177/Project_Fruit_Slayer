package cn.bonoon.controllers.survey;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = PageCondition.class, value= @GridOptions(operationWith = 150))
public class ProvinceSurveyItem extends AbstractItem implements ProvinceSurveyDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5711255162967885223L;
	/**
	 * 
	 */

	@AsColumn(width = 80, ordinal = 0)
	private int annual;
	@AsColumn(width = 120, ordinal = 10)
	private String deadline;
	@AsColumn(width = 120, ordinal = 11)
	private String startAt;
	@AsColumn(width = 120, ordinal = 12)
	private String endAt;
	@AsColumn(width = 80, ordinal = 20)
	private int needReport;
	@AsColumn(width = 90, ordinal = 21)
	private int startReport;
	@AsColumn(width = 90, ordinal = 22)
	private int finishReport;
	@AsColumn(width = 60, ordinal = 30)
	private String status;
	@TransformField("status")
	private int statusValue;
	
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
	public int getNeedReport() {
		return needReport;
	}
	public void setNeedReport(int needReport) {
		this.needReport = needReport;
	}
	public int getStartReport() {
		return startReport;
	}
	public void setStartReport(int startReport) {
		this.startReport = startReport;
	}
	public int getFinishReport() {
		return finishReport;
	}
	public void setFinishReport(int finishReport) {
		this.finishReport = finishReport;
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
}
