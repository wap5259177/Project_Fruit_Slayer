package cn.bonoon.controllers.project;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = ProjectCondition.class, value= @GridOptions(sortName = "code", operationWith = 240))
public class ProjectItem extends AbstractItem implements ProjectDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5360635473296633888L;
	
	@AsColumn(width = 200, ordinal = 10)
	private String name;

	@TransformField("rural.town")
	@AsColumn(width = 100, ordinal = 40)
	private String town;

	@TransformField("rural.name")
	@AsColumn(width = 120, ordinal = 50)
	private String villageName;
	
	@TransformField("rural.naturalVillage")
	@AsColumn(width = 120, ordinal = 31, sortable = false)
	private String naturalVillage;
	
//	@TransformField("modelArea.name")
//	@AsColumn(width = 180, ordinal = 1)
//	private String modelArea;
	
	@AsColumn(width = 140, ordinal = 20)
	private String proProperty;
	@AsColumn(width = 120, ordinal = 30)
	private String projectType;
//	@AsColumn(width = 70, ordinal = 30)
//	private int labourCount;
//	@AsColumn(width = 80, ordinal = 40)
//	private double spend;
	@AsColumn(width = 80, ordinal = 50)
	private String status;
	
	//20150813新添加字段
	@AsColumn(width = 120, ordinal = 0)
	private String code;
//	@TransformField("type.name")
//	@AsColumn(width = 120, name="项目类型名称", ordinal = 1)
//	private String type;
//	
//	@TransformField("newRural.name")
//	@AsColumn(width = 120, name="村名", ordinal = 2)
//	private String newRural;
//	
//	@AsColumn(width = 120, name="创建时间", ordinal = 3)
//	private String createAt;
//	
//	@AsColumn(width = 120, name="创建人", ordinal = 4)
//	private String creatorName;

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

//	public int getLabourCount() {
//		return labourCount;
//	}
//
//	public void setLabourCount(int labourCount) {
//		this.labourCount = labourCount;
//	}

//	public double getSpend() {
//		return spend;
//	}
//
//	public void setSpend(double spend) {
//		this.spend = spend;
//	}

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

	

//	public String getModelArea() {
//		return modelArea;
//	}
//
//	public void setModelArea(String modelArea) {
//		this.modelArea = modelArea;
//	}

//	public String getProProperty() {
//		return proProperty;
//	}
//
//	public void setProProperty(String proProperty) {
//		this.proProperty = proProperty;
//	}
//
//	public String getProjectType() {
//		return projectType;
//	}
//
//	public void setProjectType(String projectType) {
//		this.projectType = projectType;
//	}
//
//	public int getLabourCount() {
//		return labourCount;
//	}
//
//	public void setLabourCount(int labourCount) {
//		this.labourCount = labourCount;
//	}
//
//	public double getSpend() {
//		return spend;
//	}
//
//	public void setSpend(double spend) {
//		this.spend = spend;
//	}
	

//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public String getNewRural() {
//		return newRural;
//	}
//
//	public void setNewRural(String newRural) {
//		this.newRural = newRural;
//	}
//
//	public String getCreateAt() {
//		return createAt;
//	}
//
//	public void setCreateAt(String createAt) {
//		this.createAt = createAt;
//	}
//
//	public String getCreatorName() {
//		return creatorName;
//	}
//
//	public void setCreatorName(String creatorName) {
//		this.creatorName = creatorName;
//	}
	
}
