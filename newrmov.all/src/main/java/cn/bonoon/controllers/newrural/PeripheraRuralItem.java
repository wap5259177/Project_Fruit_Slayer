package cn.bonoon.controllers.newrural;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = RuralCondition.class, value = @GridOptions(operationWith = 185))
public class PeripheraRuralItem extends RuralItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1329364609047272433L;
	
	@TransformField("modelArea.status")
	private int statusValue;

	public int getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}


}
