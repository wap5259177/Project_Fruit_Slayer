package cn.bonoon.controllers.project.report.crbuild;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.query.PageCondition;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;


@AsDataGrid(condition = PageCondition.class, value= @GridOptions(operationWith = 280))
public class CRBuildQuarterItem extends AbstractItem implements CRBuildQuarterDefine{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 625740691086617427L;
	@TransformField(value="batch.quarter.annual")
	
	@AsColumn(width = 80, ordinal = 0,sortable = false)
	private int annual;//年度
	
	@TransformField("batch.quarter.period")
	@AsColumn(width = 280, ordinal = 1,sortable = false)
	private String period;//季度
	
	@TransformField("batch.quarter.deadline")
	@AsColumn(width = 120, ordinal = 10,sortable = false)
	
	private String deadline;//截止日期
	
	@TransformField("batch.quarter.startAt")
	@AsColumn(width = 120, ordinal = 11,sortable = false)
	private String startAt;//开始上报时间
	
	@TransformField("batch.quarter.endAt")
	@AsColumn(width = 120, ordinal = 12,sortable = false)
	private String endAt;//结束上报时间
	
	@AsColumn(width = 80, ordinal = 30,sortable = false)
	private String status;//状态
	
	@AsColumn(width = 60, ordinal = 21,sortable = false)
	private int urge;//催报次数

	@TransformField("status")
	private int statusValue;//button 的状态
	
	
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
	public int getUrge() {
		return urge;
	}
	public void setUrge(int urge) {
		this.urge = urge;
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
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
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
