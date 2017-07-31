package cn.bonoon.controllers.project.reportManager;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = ReportManagerCondition.class,value= @GridOptions(operationWith = 400))
public class ReportManagerItem extends AbstractItem implements ReportMangerDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -821346538332251630L;

	@TransformField("modelArea.county")
	@AsColumn(width = 100, ordinal = 1,sortable= false)
	private String county;
	
	//片区名
	@TransformField("modelArea.name")
	@AsColumn(width = 120, ordinal = 10,sortable= false)
	private String name;
	
	@TransformField("modelArea.batch")
	@AsColumn(width = 50, ordinal = 11,sortable= false)
	private String batch;
	
	//年份
	@AsColumn(width = 80, ordinal = 12)
	private String annual;
	//月份
	@AsColumn(width = 80, ordinal = 13)
	private String period;
	
//	省级新农村连片示范工程建设补助资金
	@TransformField("investment.")
	@AsColumn(width = 100, ordinal = 21,sortable= false)
	private String specialFunds;
	
//	合计
	@TransformField("investment.")
	@AsColumn(width = 100, ordinal = 28,sortable= false)
	private String totalFunds;
	
//	状态
	@AsColumn(width = 80, ordinal = 50)
	private String status;

	@TransformField(value = "status", writable = WriteModel.NONE)
	private int statusValue;

//	//市名
//	@TransformField("report.modelArea.")
//	@AsColumn(width = 120, ordinal = 0,sortable= false)
//	private String cityName;
	
//	//镇名
//	@TransformField("project.")
//	@AsColumn(width = 120, ordinal = 11,sortable= false)
//	private String town;
	
////	项目编号
//	@TransformField("project.")
//	@AsColumn(width = 120, ordinal = 14,sortable= false)
//	private String code;
////	项目名称
//	@TransformField("project.")
//	@AsColumn(width = 120, ordinal = 15,sortable= false)
//	private String name;
	
////	中央
//	@TransformField("investment.")
//	@AsColumn(width = 100, ordinal = 20)
//	private String stateFunds;
//
////	市级财政资金
//	@TransformField("investment.")
//	@AsColumn(width = 100, ordinal = 23 )
//	private String cityFunds;
////	县级财政资金
//	@TransformField("investment.")
//	@AsColumn(width = 100, ordinal = 24)
//	private String countyFunds;
////	社会投入
//	@TransformField("investment.")
//	@AsColumn(width = 100, ordinal = 25)
//	private String socialFunds;
////	群众自筹
//	@TransformField("investment.")
//	@AsColumn(width = 100, ordinal = 26)
//	private String massFunds;
////	其他
//	@TransformField("investment.")
//	@AsColumn(width = 100, ordinal = 27)
//	private String otherFunds;
//
////	投资额
//	@AsColumn(width = 80, ordinal = 30)
//	private String spend;
//	
////	投工数
//	@AsColumn(width = 80, ordinal = 40)
//	private String labourCount;

//	@AsColumn(width = 140, ordinal = 20)
//	private String proProperty;
//	@AsColumn(width = 120, ordinal = 30)
//	private String projectType;

//	@TransformField("rural.naturalVillage")
//	@AsColumn(width = 120, ordinal = 31, sortable = false)
//	private String naturalVillage;
//	
//	@TransformField("rural.name")
//	@AsColumn(width = 120, ordinal = 50)
//	private String villageName;

//	public String getCityName() {
//		return cityName;
//	}
//
//	public void setCityName(String cityName) {
//		this.cityName = cityName;
//	}

//	public String getTown() {
//		return town;
//	}
//
//	public void setTown(String town) {
//		this.town = town;
//	}

	public String getAnnual() {
		return annual;
	}
	
	public void setAnnual(String annual) {
		this.annual = annual;
	}

	public int getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

//	public String getCode() {
//		return code;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

//	public String getStateFunds() {
//		return stateFunds;
//	}
//
//	public void setStateFunds(String stateFunds) {
//		this.stateFunds = stateFunds;
//	}

	public String getSpecialFunds() {
		return specialFunds;
	}

	public void setSpecialFunds(String specialFunds) {
		this.specialFunds = specialFunds;
	}

//	public String getProvinceFunds() {
//		return provinceFunds;
//	}
//
//	public void setProvinceFunds(String provinceFunds) {
//		this.provinceFunds = provinceFunds;
//	}
//
//	public String getCityFunds() {
//		return cityFunds;
//	}
//
//	public void setCityFunds(String cityFunds) {
//		this.cityFunds = cityFunds;
//	}
//
//	public String getCountyFunds() {
//		return countyFunds;
//	}
//
//	public void setCountyFunds(String countyFunds) {
//		this.countyFunds = countyFunds;
//	}
//
//	public String getSocialFunds() {
//		return socialFunds;
//	}
//
//	public void setSocialFunds(String socialFunds) {
//		this.socialFunds = socialFunds;
//	}
//
//	public String getMassFunds() {
//		return massFunds;
//	}
//
//	public void setMassFunds(String massFunds) {
//		this.massFunds = massFunds;
//	}
//
//
//	public String getOtherFunds() {
//		return otherFunds;
//	}
//
//	public void setOtherFunds(String otherFunds) {
//		this.otherFunds = otherFunds;
//	}

	public String getTotalFunds() {
		return totalFunds;
	}

	public void setTotalFunds(String totalFunds) {
		this.totalFunds = totalFunds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

//	public String getSpend() {
//		return spend;
//	}
//
//	public void setSpend(String spend) {
//		this.spend = spend;
//	}
//
//
//	public String getLabourCount() {
//		return labourCount;
//	}
//
//	public void setLabourCount(String labourCount) {
//		this.labourCount = labourCount;
//	}

//	public String getNaturalVillage() {
//		return naturalVillage;
//	}
//
//	public void setNaturalVillage(String naturalVillage) {
//		this.naturalVillage = naturalVillage;
//	}
//
//	public String getVillageName() {
//		return villageName;
//	}
//
//	public void setVillageName(String villageName) {
//		this.villageName = villageName;
//	}
	

}
