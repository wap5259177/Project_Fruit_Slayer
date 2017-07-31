package cn.bonoon.controllers.project.reportManager;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;
@AsDataGrid(condition = ProjetManagetCondition.class, value= @GridOptions(sortName = "code", operationWith = 750))
public class ProjectManagetItem extends AbstractItem implements ProjectManagerDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5715791281325928541L;

	
	@TransformField("modelArea.cityName")
	@AsColumn(width = 80, ordinal = 0,sortable=false)
	private String cityName;
	
	@TransformField("modelArea.county")
	@AsColumn(width = 80, ordinal = 1,sortable=false)
	private String county;
	
	@AsColumn(width = 150, ordinal = 10)
	private String areaName;
	
	@TransformField("modelArea.batch")
	@AsColumn(width = 50, ordinal = 11,sortable=false)
	private String batch;
	
	@AsColumn(width = 80, ordinal = 20)
	private String code;
	
	@AsColumn(width = 120, ordinal = 30)
	private String name;
	
	@TransformField("rural.town")
	@AsColumn(width = 100, ordinal = 40 ,sortable=false)
	private String town;

	@TransformField("rural.name")
	@AsColumn(width = 120, ordinal = 50,sortable=false)
	private String villageName;
	
	@TransformField("rural.naturalVillage")
	@AsColumn(width = 120, ordinal = 51, sortable = false)
	private String naturalVillage;
	
	@AsColumn(width = 140, ordinal = 60)
	private String proProperty;
	@AsColumn(width = 120, ordinal = 70)
	private String projectType;

	@AsColumn(width = 80, ordinal = 80)
	private String status;
	
	@TransformField(value = "status", writable = WriteModel.NONE)
	private int statusValue;
	
	@TransformField("investment.")
	@AsColumn(width = 100, ordinal = 71,sortable=false)
	private String specialFunds;
	@TransformField("investment.")
	@AsColumn(width = 80, ordinal = 72,sortable=false)
	private String totalFunds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProProperty() {
		return proProperty;
	}

	public void setProProperty(String proProperty) {
		this.proProperty = proProperty;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getStatus() {
		return status;
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

	public String getNaturalVillage() {
		return naturalVillage;
	}

	public void setNaturalVillage(String naturalVillage) {
		this.naturalVillage = naturalVillage;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getSpecialFunds() {
		return specialFunds;
	}

	public void setSpecialFunds(String specialFunds) {
		this.specialFunds = specialFunds;
	}

	public String getTotalFunds() {
		return totalFunds;
	}

	public void setTotalFunds(String totalFunds) {
		this.totalFunds = totalFunds;
	}

	public int getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	
}
