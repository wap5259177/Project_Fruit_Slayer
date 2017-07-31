package cn.bonoon.controllers.industry;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = IndustryAreaCondition.class, value= @GridOptions(operationWith = 180))
public class IndustryAreaItem extends AbstractItem implements IndustryAreaDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5801741213773000217L;

	@AsColumn(width = 150, ordinal = 0)
	private String coopName;

//	@TransformField("rural.modelArea.name")
//	@AsColumn(width = 180, ordinal = 1)
//	private String modelArea;

	@AsColumn(width = 150, ordinal = 20)
	private String town;
	
	@AsColumn(width = 120, ordinal = 30)
	private String villageName;
	
//	@TransformField("rural.naturalVillage")
//	@AsColumn(width = 120, ordinal = 31)
//	private String naturalVillage;
	
	@AsColumn(width = 120, ordinal = 40)
	private String villageFea;
	
	@AsColumn(width = 80, ordinal = 80)
	private String reportDate;

	@AsColumn(width = 60, ordinal = 50)
	private String status;
	
	@TransformField("modelArea.status")
	private int statusValue;

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getVillageFea() {
		return villageFea;
	}

	public void setVillageFea(String villageFea) {
		this.villageFea = villageFea;
	}

//	public String getModelArea() {
//		return modelArea;
//	}
//
//	public void setModelArea(String modelArea) {
//		this.modelArea = modelArea;
//	}

	public String getCoopName() {
		return coopName;
	}

	public void setCoopName(String coopName) {
		this.coopName = coopName;
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

//	public String getNaturalVillage() {
//		return naturalVillage;
//	}
//
//	public void setNaturalVillage(String naturalVillage) {
//		this.naturalVillage = naturalVillage;
//	}

	
}