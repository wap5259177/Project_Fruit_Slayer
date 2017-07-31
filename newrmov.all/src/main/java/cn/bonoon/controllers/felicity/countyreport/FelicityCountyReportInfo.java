package cn.bonoon.controllers.felicity.countyreport;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.ObjectEditor;

public class FelicityCountyReportInfo extends ObjectEditor{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1328667552095453014L;

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 0)
	private int annual;
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 1)
	private String month;
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 2)
	private String status;
	
	@TransformField(writable = WriteModel.NONE, value = "county.name")
	private String name;
	@TransformField(writable = WriteModel.NONE, value = "county.cityName")
	private String cityName;

	@TransformField(value = "status", writable = WriteModel.NONE)
	private int statusValue;
	

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 10)
	private int constructionCharacteristic0;//人文历史
    
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 20)
	private int constructionCharacteristic1;//乡村旅游

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 30)
	private int constructionCharacteristic2;//自然生态

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 40) 
	private int constructionCharacteristic3;//特色产业

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 50)
	private int constructionCharacteristic4;//民居风貌

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 60)
	private int constructionCharacteristic5;//渔业渔港

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 70)
	private int constructionCharacteristic6;//其它
	//规划设计完成或否

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 11)
	private int planningCompletedTrue;
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 12)
	private int planningCompletedFalse;
	//项目招投标完成或否
	//
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 21)
	private int biddingCompletedTrue;
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 22)
	private int biddingCompletedFalse;
	
	//建设成效effect
	//是否完成环境卫生整治
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 31)
	private int effectRemediationTrue;
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 32)
	private int effectRemediationFalse;
	//是否统一民居外立面风貌

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectUniformStyleTrue;
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectUniformStyleFalse;
	//是否解决垃圾问题
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectSolveGarbageTrue;

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectSolveGarbageFalse;
	//是否建立污水处理设施
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectSewageTreatmentTrue;
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectSewageTreatmentFalse;
	//是否建立村保洁队伍
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectCleaningTeamTrue;
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectCleaningTeamFalse;
	//是否成立村民理事会
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectCouncilTrue;
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectCouncilFalse;
	//是否完成村巷道硬底化建设
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectHardBottomTrue;

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectHardBottomFalse;
	//是否解决通自来水
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectThroughWaterTrue;
	
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int effectThroughWaterFalse;
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private double constructionArea;//村庄建设覆盖面积

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int householdCount;//户数
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int population;//人口数
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private int projectCount;//确定的建设项目数（个）
	
//	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(10)
	private double projectProgress;//项目建设进度（%）

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private double investmentBudget;//投入预算（万元）

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private double investmentCompleted;//目前已完成投入（万元）
	
	//资金来源（万元）	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private double fundsTotal;//小计

	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private double fundsProvince;//省
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private double fundsCity;//市
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private double fundsCounty;//县
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private double fundsTown;//镇
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private double fundsVillage;//村
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private double fundsMasses;//群众
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private double fundsSociety;//社会
	
	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(readonly = BoolType.TRUE, value = 100)
	private double fundsOther;//其它
	
	//规划设计

//	@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(10)
	private double planningProgress;//规划进度（%）
	
	//项目招投标

	//@TransformField(writable = WriteModel.NONE)
	//@PropertyUpdate(10)
	private double biddingProgress;//完成招投标比例（%）
	public int getAnnual() {
		return annual;
	}
	public void setAnnual(int annual) {
		this.annual = annual;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
//	public int getStatusValue() {
//		return statusValue;
//	}
//	public void setStatusValue(int statusValue) {
//		this.statusValue = statusValue;
//	}
	public int getConstructionCharacteristic0() {
		return constructionCharacteristic0;
	}
	public void setConstructionCharacteristic0(int constructionCharacteristic0) {
		this.constructionCharacteristic0 = constructionCharacteristic0;
	}
	public int getConstructionCharacteristic1() {
		return constructionCharacteristic1;
	}
	public void setConstructionCharacteristic1(int constructionCharacteristic1) {
		this.constructionCharacteristic1 = constructionCharacteristic1;
	}
	public int getConstructionCharacteristic2() {
		return constructionCharacteristic2;
	}
	public void setConstructionCharacteristic2(int constructionCharacteristic2) {
		this.constructionCharacteristic2 = constructionCharacteristic2;
	}
	public int getConstructionCharacteristic3() {
		return constructionCharacteristic3;
	}
	public void setConstructionCharacteristic3(int constructionCharacteristic3) {
		this.constructionCharacteristic3 = constructionCharacteristic3;
	}
	public int getConstructionCharacteristic4() {
		return constructionCharacteristic4;
	}
	public void setConstructionCharacteristic4(int constructionCharacteristic4) {
		this.constructionCharacteristic4 = constructionCharacteristic4;
	}
	public int getConstructionCharacteristic5() {
		return constructionCharacteristic5;
	}
	public void setConstructionCharacteristic5(int constructionCharacteristic5) {
		this.constructionCharacteristic5 = constructionCharacteristic5;
	}
	public int getConstructionCharacteristic6() {
		return constructionCharacteristic6;
	}
	public void setConstructionCharacteristic6(int constructionCharacteristic6) {
		this.constructionCharacteristic6 = constructionCharacteristic6;
	}
	public int getPlanningCompletedTrue() {
		return planningCompletedTrue;
	}
	public void setPlanningCompletedTrue(int planningCompletedTrue) {
		this.planningCompletedTrue = planningCompletedTrue;
	}
	public int getPlanningCompletedFalse() {
		return planningCompletedFalse;
	}
	public void setPlanningCompletedFalse(int planningCompletedFalse) {
		this.planningCompletedFalse = planningCompletedFalse;
	}
	public int getBiddingCompletedTrue() {
		return biddingCompletedTrue;
	}
	public void setBiddingCompletedTrue(int biddingCompletedTrue) {
		this.biddingCompletedTrue = biddingCompletedTrue;
	}
	public int getBiddingCompletedFalse() {
		return biddingCompletedFalse;
	}
	public void setBiddingCompletedFalse(int biddingCompletedFalse) {
		this.biddingCompletedFalse = biddingCompletedFalse;
	}
	public int getEffectRemediationTrue() {
		return effectRemediationTrue;
	}
	public void setEffectRemediationTrue(int effectRemediationTrue) {
		this.effectRemediationTrue = effectRemediationTrue;
	}
	public int getEffectRemediationFalse() {
		return effectRemediationFalse;
	}
	public void setEffectRemediationFalse(int effectRemediationFalse) {
		this.effectRemediationFalse = effectRemediationFalse;
	}
	public int getEffectUniformStyleTrue() {
		return effectUniformStyleTrue;
	}
	public void setEffectUniformStyleTrue(int effectUniformStyleTrue) {
		this.effectUniformStyleTrue = effectUniformStyleTrue;
	}
	public int getEffectUniformStyleFalse() {
		return effectUniformStyleFalse;
	}
	public void setEffectUniformStyleFalse(int effectUniformStyleFalse) {
		this.effectUniformStyleFalse = effectUniformStyleFalse;
	}
	public int getEffectSolveGarbageTrue() {
		return effectSolveGarbageTrue;
	}
	public void setEffectSolveGarbageTrue(int effectSolveGarbageTrue) {
		this.effectSolveGarbageTrue = effectSolveGarbageTrue;
	}
	public int getEffectSolveGarbageFalse() {
		return effectSolveGarbageFalse;
	}
	public void setEffectSolveGarbageFalse(int effectSolveGarbageFalse) {
		this.effectSolveGarbageFalse = effectSolveGarbageFalse;
	}
	public int getEffectSewageTreatmentTrue() {
		return effectSewageTreatmentTrue;
	}
	public void setEffectSewageTreatmentTrue(int effectSewageTreatmentTrue) {
		this.effectSewageTreatmentTrue = effectSewageTreatmentTrue;
	}
	public int getEffectSewageTreatmentFalse() {
		return effectSewageTreatmentFalse;
	}
	public void setEffectSewageTreatmentFalse(int effectSewageTreatmentFalse) {
		this.effectSewageTreatmentFalse = effectSewageTreatmentFalse;
	}
	public int getEffectCleaningTeamTrue() {
		return effectCleaningTeamTrue;
	}
	public void setEffectCleaningTeamTrue(int effectCleaningTeamTrue) {
		this.effectCleaningTeamTrue = effectCleaningTeamTrue;
	}
	public int getEffectCleaningTeamFalse() {
		return effectCleaningTeamFalse;
	}
	public void setEffectCleaningTeamFalse(int effectCleaningTeamFalse) {
		this.effectCleaningTeamFalse = effectCleaningTeamFalse;
	}
	public int getEffectCouncilTrue() {
		return effectCouncilTrue;
	}
	public void setEffectCouncilTrue(int effectCouncilTrue) {
		this.effectCouncilTrue = effectCouncilTrue;
	}
	public int getEffectCouncilFalse() {
		return effectCouncilFalse;
	}
	public void setEffectCouncilFalse(int effectCouncilFalse) {
		this.effectCouncilFalse = effectCouncilFalse;
	}
	public int getEffectHardBottomTrue() {
		return effectHardBottomTrue;
	}
	public void setEffectHardBottomTrue(int effectHardBottomTrue) {
		this.effectHardBottomTrue = effectHardBottomTrue;
	}
	public int getEffectHardBottomFalse() {
		return effectHardBottomFalse;
	}
	public void setEffectHardBottomFalse(int effectHardBottomFalse) {
		this.effectHardBottomFalse = effectHardBottomFalse;
	}
	public int getEffectThroughWaterTrue() {
		return effectThroughWaterTrue;
	}
	public void setEffectThroughWaterTrue(int effectThroughWaterTrue) {
		this.effectThroughWaterTrue = effectThroughWaterTrue;
	}
	public int getEffectThroughWaterFalse() {
		return effectThroughWaterFalse;
	}
	public void setEffectThroughWaterFalse(int effectThroughWaterFalse) {
		this.effectThroughWaterFalse = effectThroughWaterFalse;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}
	
	
}
