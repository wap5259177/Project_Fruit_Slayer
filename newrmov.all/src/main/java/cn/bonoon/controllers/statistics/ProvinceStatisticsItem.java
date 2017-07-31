package cn.bonoon.controllers.statistics;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = PageCondition.class, value= @GridOptions(operationWith = 600))
public class ProvinceStatisticsItem extends AbstractItem implements ProvinceStatisticsDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1023159138395895459L;

	@AsColumn(width = 80, ordinal = 0)
	private int annual;
	@AsColumn(width = 80, ordinal = 1)
	private String period;
	@AsColumn(width = 120, ordinal = 10)
	private String deadline;
	@AsColumn(width = 120, ordinal = 11)
	private String startAt;
	@AsColumn(width = 120, ordinal = 12)
	private String endAt;
	@AsColumn(width = 60, ordinal = 30)
	private String status;
	@TransformField("status")
	private int statusValue;
	
	//20160122
	@AsColumn(width = 60, ordinal = 30)
	private String isLock;
	@TransformField("isLock")
	private int isLockValue;
	
	public int getAnnual() {
		return annual;
	}
	public void setAnnual(int annual) {
		this.annual = annual;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
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
		if(isLockValue > 0){
			return 100;
		}
		return statusValue;
	}
	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public int getIsLockValue() {
		return isLockValue;
	}
	public void setIsLockValue(int isLockValue) {
		this.isLockValue = isLockValue;
	}
	
}





