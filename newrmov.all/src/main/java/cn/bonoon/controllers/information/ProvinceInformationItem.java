package cn.bonoon.controllers.information;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = ProvinceInformationCondition.class, value = @GridOptions(operationWith = 180))
public class ProvinceInformationItem extends AbstractItem implements ProvinceInformationDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4249672904548209249L;

	@AsColumn(width = 200, ordinal = 0, sortable = false)
	private String name;

	@AsColumn(width = 120, ordinal = 10)
	private String startAt;
	@AsColumn(width = 120, ordinal = 20)
	private String endAt;
	
	@AsColumn(width = 120, ordinal = 30)
	private String deadline;
	
	@AsColumn(width = 80, ordinal = 40)
	private String status;
	
	@TransformField(value="status")
	private int statusValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}

	public String getEndAt() {
		return endAt;
	}

	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}

	public String getStartAt() {
		return startAt;
	}

	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}
//
//	
	
	
	
	
	

	
}
