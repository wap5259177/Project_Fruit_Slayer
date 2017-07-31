package cn.bonoon.controllers.quarter;

import java.util.Date;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
@AsDataGrid
public class ModelAreaQuarterAuditItem extends AbstractItem implements ModelAreaQuarterAuditDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1724134358231402504L;
	
	@TransformField("modelArea.place.")
	@AsColumn(width = 80, ordinal = 0,sortable=false)
	private String name;
	
	@TransformField("batch.")
	@AsColumn(width = 80, ordinal = 10,sortable=false)
	private String batchName;
	
	@TransformField("batch.quarter.")
	@AsColumn(width = 80, ordinal = 14,sortable=false)
	private String period;
	
	@TransformField("batch.quarter.")
	@AsColumn(width = 80, ordinal = 13,sortable=false)
	private int annual; 
	
	
	
	@TransformField("batch.quarter.")
	@AsColumn(width = 120, ordinal = 20,sortable=false)
	private Date startAt;
	
	@TransformField("batch.quarter.")
	@AsColumn(width = 120, ordinal = 30,sortable=false)
	private Date endAt;
	
	@TransformField("batch.quarter.")
	@AsColumn(width = 120, ordinal = 40,sortable=false)
	private Date deadline;
	
	@AsColumn(width = 80, ordinal = 50,sortable=false)
	private String status;
	
	@TransformField(value = "status", writable = WriteModel.NONE)
	private int statusValue;

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	

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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public int getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}


	
	
}
