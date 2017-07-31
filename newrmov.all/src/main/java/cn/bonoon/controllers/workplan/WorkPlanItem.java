package cn.bonoon.controllers.workplan;

import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = WorkPlanCondition.class, value = @GridOptions(operationWith = 135))
public class WorkPlanItem extends AbstractItem implements WorkPlanDefine{

	private static final long serialVersionUID = -1678502703542671033L;
	
	@AsColumn(width = 220, ordinal = 0)
	private String name;

	@AsColumn(width = 60, ordinal = 1)
	private int annual;
	
	@AsColumn(width = 60, ordinal = 2)
	private String monthly;
	
	@AsColumn(width = 180, ordinal = 3)
	private String unitName;

	@AsColumn(width = 140, ordinal = 4)
	private String createAt;

	
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

	public String getMonthly() {
		return monthly;
	}

	public void setMonthly(String monthly) {
		this.monthly = monthly;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	
	

}
