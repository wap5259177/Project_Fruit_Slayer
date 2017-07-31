package cn.bonoon.controllers.felicity;

import cn.bonoon.kernel.support.models.ObjectEditor;

public class FelicityVillageInfo extends ObjectEditor{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4206963534479343008L;
	private String constructionCharacteristic;//村庄建设特色
	private String constructionFeature;//村庄建设特点
	
	//规划设计
	private boolean planningCompleted;//完成或否
	//项目招投标
	private boolean biddingCompleted;//完成或否
	//建设成效effect
	private boolean effectRemediation;//是否完成环境卫生整治
	private boolean effectUniformStyle;//是否统一民居外立面风貌
	private boolean effectSolveGarbage;//是否解决垃圾问题
	private boolean effectSewageTreatment;//是否建立污水处理设施
	private boolean effectCleaningTeam;//是否建立村保洁队伍
	private boolean effectCouncil;//是否成立村民理事会
	private boolean effectHardBottom;//是否完成村巷道硬底化建设
	private boolean effectThroughWater;//是否解决通自来水
	private String effectOther;//其它

	private double constructionArea;//村庄建设覆盖面积

	private int householdCount;//户数

	private int population;//人口数

	private int projectCount;//确定的建设项目数（个）

	private double projectProgress;//项目建设进度（%）

	private double investmentBudget;//投入预算（万元）

	private double investmentCompleted;//目前已完成投入（万元）
	
	//资金来源（万元）
	private double fundsTotal;//小计
	private double fundsProvince;//省
	private double fundsCity;//市
	private double fundsCounty;//县
	private double fundsTown;//镇
	private double fundsVillage;//村
	private double fundsMasses;//群众
	private double fundsSociety;//社会
	private double fundsOther;//其它
	
	//规划设计
	private double planningProgress;//规划进度（%）
	
	//项目招投标
	private double biddingProgress;//完成招投标比例（%）

	public String getConstructionCharacteristic() {
		return constructionCharacteristic;
	}

	public void setConstructionCharacteristic(String constructionCharacteristic) {
		this.constructionCharacteristic = constructionCharacteristic;
	}

	public String getConstructionFeature() {
		return constructionFeature;
	}

	public void setConstructionFeature(String constructionFeature) {
		this.constructionFeature = constructionFeature;
	}

	public boolean isPlanningCompleted() {
		return planningCompleted;
	}

	public void setPlanningCompleted(boolean planningCompleted) {
		this.planningCompleted = planningCompleted;
	}

	public boolean isBiddingCompleted() {
		return biddingCompleted;
	}

	public void setBiddingCompleted(boolean biddingCompleted) {
		this.biddingCompleted = biddingCompleted;
	}

	public boolean isEffectRemediation() {
		return effectRemediation;
	}

	public void setEffectRemediation(boolean effectRemediation) {
		this.effectRemediation = effectRemediation;
	}

	public boolean isEffectUniformStyle() {
		return effectUniformStyle;
	}

	public void setEffectUniformStyle(boolean effectUniformStyle) {
		this.effectUniformStyle = effectUniformStyle;
	}

	public boolean isEffectSolveGarbage() {
		return effectSolveGarbage;
	}

	public void setEffectSolveGarbage(boolean effectSolveGarbage) {
		this.effectSolveGarbage = effectSolveGarbage;
	}

	public boolean isEffectSewageTreatment() {
		return effectSewageTreatment;
	}

	public void setEffectSewageTreatment(boolean effectSewageTreatment) {
		this.effectSewageTreatment = effectSewageTreatment;
	}

	public boolean isEffectCleaningTeam() {
		return effectCleaningTeam;
	}

	public void setEffectCleaningTeam(boolean effectCleaningTeam) {
		this.effectCleaningTeam = effectCleaningTeam;
	}

	public boolean isEffectCouncil() {
		return effectCouncil;
	}

	public void setEffectCouncil(boolean effectCouncil) {
		this.effectCouncil = effectCouncil;
	}

	public boolean isEffectHardBottom() {
		return effectHardBottom;
	}

	public void setEffectHardBottom(boolean effectHardBottom) {
		this.effectHardBottom = effectHardBottom;
	}

	public boolean isEffectThroughWater() {
		return effectThroughWater;
	}

	public void setEffectThroughWater(boolean effectThroughWater) {
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
	
}
