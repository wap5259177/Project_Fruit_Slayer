package cn.bonoon.controllers.felicityvillage;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = FVReportCondition.class, value = @GridOptions(operationWith = 180))
public class FVReportItem extends AbstractItem implements FVReportDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4249672904548209249L;

	@AsColumn(width = 200, ordinal = 0, sortable = false)
	private String name;

	@AsColumn(width = 80, ordinal = 10)
	private int annual;
	
	@AsColumn(width = 120, ordinal = 20)
	private String startTime;
	
	@AsColumn(width = 120, ordinal = 30)
	private String deadline;
	
	@AsColumn(width = 80, ordinal = 40)
	private String status;
	
	@TransformField(value="status")
	private String statusValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAnnual() {
		return annual;
	}

	public void setAnnual(int annual) {
		this.annual = annual;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	
	
	
	
	
	

	
}
