package cn.bonoon.controllers.project.reportManager;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = ModelAreaManageConditionByImage.class, value = @GridOptions(operationWith = 400))
public class ModelAreaItemByImage extends AbstractItem implements
		ProjectImageDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8815339591849811163L;
	/**
	 * 
	 */

	@AsColumn(width = 160, ordinal = 10)
	private String name;

	@AsColumn(width = 50, ordinal = 11)
	private String batch;

	@AsColumn(width = 50, ordinal = 30)
	private String status;

	@TransformField("status")
	private int statusValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
