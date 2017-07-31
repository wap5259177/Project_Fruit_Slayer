package cn.bonoon.controllers.felicity.report;


import cn.bonoon.controllers.felicity.FelicityVillageReportDefine;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.ColumnGroup;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;
import cn.bonoon.kernel.web.annotations.grid.PaginationType;

@Transform
@AsDataGrid(
		pagination = PaginationType.NONE, 
		value = @GridOptions(
				operation=false,
				groups = {
						@ColumnGroup(value = "资金来源（万元）", ordinal = 20),
						@ColumnGroup(value = "规划设计", ordinal = 30),
						@ColumnGroup(value = "项目招投标", ordinal = 40),
						@ColumnGroup(value = "建设成效", ordinal = 50)
				}
		)
)
public class FelicityVillageReportItem extends AbstractItem implements FelicityVillageReportDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5360737933033882038L;
	@AsColumn(frozen = true, width = 80, ordinal = 0)
	@TransformField("village.townName")
	private String townName;
	@AsColumn(frozen = true, width = 100, ordinal = 1)
	@TransformField("village.name")
	private String name;
	@AsColumn(frozen = true, width = 100, ordinal = 2)
	@TransformField("village.naturalVillage")
	private String naturalVillage;
	
	@TransformField("village.constructionType")
	@AsColumn(width = 80, ordinal = 3)
	private String constructionType;//村庄建设类型
	@TransformField("village.villageType")
	@AsColumn(width = 80, ordinal = 4)
	private String villageType;//A为行政村 B为自然村
	
//	@TransformField("village.constructionCharacteristic")
	@AsColumn(width = 140, ordinal = 8)
	private String constructionCharacteristic;//村庄建设特色
	
	//建设成效effect
//	@TransformField("village.effectRemediation")
	@AsColumn(width = 80, ordinal = 26, group = 4)
	private String effectRemediation;//是否完成环境卫生整治
	
//	@TransformField("village.effectUniformStyle")
	@AsColumn(width = 80, ordinal = 27, group = 4)
	private String effectUniformStyle;//是否统一民居外立面风貌
	
//	@TransformField("village.effectSolveGarbage")
	@AsColumn(width = 80, ordinal = 28, group = 4)
	private String effectSolveGarbage;//是否解决垃圾问题
	
//	@TransformField("village.effectSewageTreatment")
	@AsColumn(width = 80, ordinal = 29, group = 4)
	private String effectSewageTreatment;//是否建立污水处理设施
	
//	@TransformField("village.effectCleaningTeam")
	@AsColumn(width = 80, ordinal = 30, group = 4)
	private String effectCleaningTeam;//是否建立村保洁队伍
	
//	@TransformField("village.effectCouncil")
	@AsColumn(width = 80, ordinal = 31, group = 4)
	private String effectCouncil;//是否成立村民理事会
	
//	@Transfor/mField("village.effectHardBottom")
	@AsColumn(width = 80, ordinal = 32, group = 4)
	private String effectHardBottom;//是否完成村巷道硬底化建设
    
//	@TransformField("village.effectThroughWater")
	@AsColumn(width = 80, ordinal = 33, group = 4)
	private String effectThroughWater;//是否解决通自来水
	
//	@TransformField("village.effectOther")
	@AsColumn(width = 80, ordinal = 34, group = 4)
	private String effectOther;//其它
//	
//	@TransformField("village.constructionArea")
	@AsColumn(width = 80, ordinal = 10)
	private double constructionArea;//村庄建设覆盖面积

//	@TransformField("village.householdCount")
	@AsColumn(width = 80, ordinal = 11)
	private int householdCount;//户数

//	@TransformField("village.population")
	@AsColumn(width = 80, ordinal = 12)
	private int population;//人口数

//	@TransformField("village.projectCount")
	@AsColumn(width = 80, ordinal = 13)
	private int projectCount;//确定的建设项目数（个）

//	@TransformField("village.projectProgress")
	@AsColumn(width = 80, ordinal = 14)
	private double projectProgress;//项目建设进度（%）

//	@TransformField("village.investmentBudget")
	@AsColumn(width = 80, ordinal = 15)
	private double investmentBudget;//投入预算（万元）

//	@TransformField("village.investmentCompleted")
	@AsColumn(width = 80, ordinal = 16)
	private double investmentCompleted;//目前已完成投入（万元）
	
	//资金来源（万元）
//	@TransformField("village.fundsTotal")
	@AsColumn(width = 80, ordinal = 13, group = 1)
	private double fundsTotal;//小计
    
//	@TransformField("village.fundsProvince")
	@AsColumn(width = 80, ordinal = 14, group = 1)
	private double fundsProvince;//省
    
//	@TransformField("village.fundsCity")
	@AsColumn(width = 80, ordinal = 15, group = 1)
	private double fundsCity;//市
    
//	@TransformField("village.fundsCounty")
	@AsColumn(width = 80, ordinal = 16, group = 1)
	private double fundsCounty;//县
    
//	@TransformField("village.fundsTown")
	@AsColumn(width = 80, ordinal = 17, group = 1)
	private double fundsTown;//镇
    
//	@TransformField("village.fundsVillage")
	@AsColumn(width = 80, ordinal = 18, group = 1)
	private double fundsVillage;//村
    
//	@TransformField("village.fundsMasses")
	@AsColumn(width = 80, ordinal = 19, group = 1)
	private double fundsMasses;//群众
    
//	@TransformField("village.fundsSociety")
	@AsColumn(width = 80, ordinal = 20, group = 1)
	private double fundsSociety;//社会
    
//	@TransformField("village.fundsOther")
	@AsColumn(width = 80, ordinal = 21, group = 1)
	private double fundsOther;//其它

	//规划设计
//	@TransformField("village.planningCompleted")
	@AsColumn(width = 80, ordinal = 22, group = 2)
	private String planningCompleted;//完成或否
	
	//规划设计
//	@TransformField("village.planningProgress")
	@AsColumn(width = 80, ordinal = 23, group = 2)
	private double planningProgress;//规划进度（%）
	
	//项目招投标
//	@TransformField("village.biddingCompleted")
	@AsColumn(width = 80, ordinal = 24, group = 3)
	private String biddingCompleted;//完成或否
	
	//项目招投标
//	@TransformField("village.biddingProgress")
	@AsColumn(width = 80, ordinal = 25, group = 3)
	private double biddingProgress;//完成招投标比例（%）
	
//	@TransformField("village.biddingProgress")
	@AsColumn(width = 200, ordinal = 60)
	private String nextStagePlanning;//下阶段打算
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getConstructionType() {
		return constructionType;
	}
	public void setConstructionType(String constructionType) {
		this.constructionType = constructionType;
	}
	public String getVillageType() {
		return villageType;
	}
	public void setVillageType(String villageType) {
		this.villageType = villageType;
	}
	public String getNaturalVillage() {
		return naturalVillage;
	}
	public void setNaturalVillage(String naturalVillage) {
		this.naturalVillage = naturalVillage;
	}
	public String getConstructionCharacteristic() {
		return constructionCharacteristic;
	}
	public void setConstructionCharacteristic(String constructionCharacteristic) {
		this.constructionCharacteristic = constructionCharacteristic;
	}
	public String getPlanningCompleted() {
		return planningCompleted;
	}
	public void setPlanningCompleted(String planningCompleted) {
		this.planningCompleted = planningCompleted;
	}
	public String getBiddingCompleted() {
		return biddingCompleted;
	}
	public void setBiddingCompleted(String biddingCompleted) {
		this.biddingCompleted = biddingCompleted;
	}
	public String getEffectRemediation() {
		return effectRemediation;
	}
	public void setEffectRemediation(String effectRemediation) {
		this.effectRemediation = effectRemediation;
	}
	public String getEffectUniformStyle() {
		return effectUniformStyle;
	}
	public void setEffectUniformStyle(String effectUniformStyle) {
		this.effectUniformStyle = effectUniformStyle;
	}
	public String getEffectSolveGarbage() {
		return effectSolveGarbage;
	}
	public void setEffectSolveGarbage(String effectSolveGarbage) {
		this.effectSolveGarbage = effectSolveGarbage;
	}
	public String getEffectSewageTreatment() {
		return effectSewageTreatment;
	}
	public void setEffectSewageTreatment(String effectSewageTreatment) {
		this.effectSewageTreatment = effectSewageTreatment;
	}
	public String getEffectCleaningTeam() {
		return effectCleaningTeam;
	}
	public void setEffectCleaningTeam(String effectCleaningTeam) {
		this.effectCleaningTeam = effectCleaningTeam;
	}
	public String getEffectCouncil() {
		return effectCouncil;
	}
	public void setEffectCouncil(String effectCouncil) {
		this.effectCouncil = effectCouncil;
	}
	public String getEffectHardBottom() {
		return effectHardBottom;
	}
	public void setEffectHardBottom(String effectHardBottom) {
		this.effectHardBottom = effectHardBottom;
	}
	public String getEffectThroughWater() {
		return effectThroughWater;
	}
	public void setEffectThroughWater(String effectThroughWater) {
		this.effectThroughWater = effectThroughWater;
	}
	public String getEffectOther() {
		return effectOther;
	}
	public void setEffectOther(String effectOther) {
		this.effectOther = effectOther;
	}
	public double getConstructionArea() {
		return constructionArea;
	}
	public void setConstructionArea(double constructionArea) {
		this.constructionArea = constructionArea;
	}
	public int getHouseholdCount() {
		return householdCount;
	}
	public void setHouseholdCount(int householdCount) {
		this.householdCount = householdCount;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public int getProjectCount() {
		return projectCount;
	}
	public void setProjectCount(int projectCount) {
		this.projectCount = projectCount;
	}
	public double getProjectProgress() {
		return projectProgress;
	}
	public void setProjectProgress(double projectProgress) {
		this.projectProgress = projectProgress;
	}
	public double getInvestmentBudget() {
		return investmentBudget;
	}
	public void setInvestmentBudget(double investmentBudget) {
		this.investmentBudget = investmentBudget;
	}
	public double getInvestmentCompleted() {
		return investmentCompleted;
	}
	public void setInvestmentCompleted(double investmentCompleted) {
		this.investmentCompleted = investmentCompleted;
	}
	public double getFundsTotal() {
		return fundsTotal;
	}
	public void setFundsTotal(double fundsTotal) {
		this.fundsTotal = fundsTotal;
	}
	public double getFundsProvince() {
		return fundsProvince;
	}
	public void setFundsProvince(double fundsProvince) {
		this.fundsProvince = fundsProvince;
	}
	public double getFundsCity() {
		return fundsCity;
	}
	public void setFundsCity(double fundsCity) {
		this.fundsCity = fundsCity;
	}
	public double getFundsCounty() {
		return fundsCounty;
	}
	public void setFundsCounty(double fundsCounty) {
		this.fundsCounty = fundsCounty;
	}
	public double getFundsTown() {
		return fundsTown;
	}
	public void setFundsTown(double fundsTown) {
		this.fundsTown = fundsTown;
	}
	public double getFundsVillage() {
		return fundsVillage;
	}
	public void setFundsVillage(double fundsVillage) {
		this.fundsVillage = fundsVillage;
	}
	public double getFundsMasses() {
		return fundsMasses;
	}
	public void setFundsMasses(double fundsMasses) {
		this.fundsMasses = fundsMasses;
	}
	public double getFundsSociety() {
		return fundsSociety;
	}
	public void setFundsSociety(double fundsSociety) {
		this.fundsSociety = fundsSociety;
	}
	public double getFundsOther() {
		return fundsOther;
	}
	public void setFundsOther(double fundsOther) {
		this.fundsOther = fundsOther;
	}
	public double getPlanningProgress() {
		return planningProgress;
	}
	public void setPlanningProgress(double planningProgress) {
		this.planningProgress = planningProgress;
	}
	public double getBiddingProgress() {
		return biddingProgress;
	}
	public void setBiddingProgress(double biddingProgress) {
		this.biddingProgress = biddingProgress;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getNextStagePlanning() {
		return nextStagePlanning;
	}
	public void setNextStagePlanning(String nextStagePlanning) {
		this.nextStagePlanning = nextStagePlanning;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	
}
