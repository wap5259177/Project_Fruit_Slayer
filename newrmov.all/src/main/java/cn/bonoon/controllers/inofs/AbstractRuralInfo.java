package cn.bonoon.controllers.inofs;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.ObjectEditor;

public abstract class AbstractRuralInfo extends ObjectEditor{

	/**
	 * 
	 */
	private static final long serialVersionUID = -791015742761269678L;

	@TransformField(value = "modelArea.id", writable = WriteModel.NONE)
	private Long modelareaId;
	
	@TransformField(writable = WriteModel.NONE)
	private String name;
	@TransformField(writable = WriteModel.NONE)
	private String town;
	@TransformField(writable = WriteModel.NONE)
	private String naturalVillage;
	
	private String code;
	private String type;
	private String poorVillage;
	private String famousVillage;
	private String famousBatch;
	private String pushVillage;
	private String villageManage;//是否开展村庄垃圾整治
	private String garbageAnnual;//开展垃圾整治年度
	private String tapWater;
	private String hyFix;
	private String cleanTeam;
	private String publicService;
	private String council;
	private String constitu;
	private double ruralArea;
	private double arableLand;
	private int houseHold;
	private long population;
	private int labor;
	private String helpUnit;
	private String pvAnnual;
//	private double annualIncome;
	private double annualIncome_13;
	private double annualIncome_14;
	private double annualIncome_15;
//	private double annualIncome_16;
//	private double annualIncome_17;
	private int toilet;
	private double hardBottom;
	private String fixAnnual;
	private int culturalAct;
	private double culturalActArea;
	private int ruralPark;
	private double ruralParkArea;
	private int ruralSquare;
	private double squareArea;
	private int healthStation;
	private double healthStationArea;
	private String councilAnnual;
	private int councilMember;
	private String councilName1;
	private String councilHP1;
	private String councilName2;
	private String councilHP2;
	private String annual;
	private String tapAnnual;
	private double bottomDis;
	private String waterBase;
	private String smallWater;
	private double farmland;
	private String cleaners;
	private double toiletPercent;
	private String badWater;
	private String badWaterAnn;
	private int noValue;
	private int buildAgain;
	private String changePlan;
	private String designPic;
	
	private int planCount;
	private int finishCount;
	private int villageToilet;
	private double villageToiletArea;
	private String ruralParkAnn;
	private String culturalActAnn;
	private String squareAnn;
	private String healthStationAnn;
	private String villageToiletAnn;
	private String psAnnual;
	private String workSituation;
	@TransformField(writable = WriteModel.NONE)
	private String councilFileName;
	@TransformField(writable = WriteModel.NONE)
	private String councilFilePath;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPoorVillage() {
		return poorVillage;
	}
	public void setPoorVillage(String poorVillage) {
		this.poorVillage = poorVillage;
	}
	public String getFamousVillage() {
		return famousVillage;
	}
	public void setFamousVillage(String famousVillage) {
		this.famousVillage = famousVillage;
	}
	public String getFamousBatch() {
		return famousBatch;
	}
	public void setFamousBatch(String famousBatch) {
		this.famousBatch = famousBatch;
	}
	public String getPushVillage() {
		return pushVillage;
	}
	public void setPushVillage(String pushVillage) {
		this.pushVillage = pushVillage;
	}
	public String getVillageManage() {
		return villageManage;
	}
	public void setVillageManage(String villageManage) {
		this.villageManage = villageManage;
	}
	public String getTapWater() {
		return tapWater;
	}
	public void setTapWater(String tapWater) {
		this.tapWater = tapWater;
	}
	public String getHyFix() {
		return hyFix;
	}
	public void setHyFix(String hyFix) {
		this.hyFix = hyFix;
	}
	public String getCleanTeam() {
		return cleanTeam;
	}
	public void setCleanTeam(String cleanTeam) {
		this.cleanTeam = cleanTeam;
	}
	public String getPublicService() {
		return publicService;
	}
	public void setPublicService(String publicService) {
		this.publicService = publicService;
	}
	public String getCouncil() {
		return council;
	}
	public void setCouncil(String council) {
		this.council = council;
	}
	public String getConstitu() {
		return constitu;
	}
	public void setConstitu(String constitu) {
		this.constitu = constitu;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getNaturalVillage() {
		return naturalVillage;
	}
	public void setNaturalVillage(String naturalVillage) {
		this.naturalVillage = naturalVillage;
	}
	public double getRuralArea() {
		return ruralArea;
	}
	public void setRuralArea(double ruralArea) {
		this.ruralArea = ruralArea;
	}
	public double getArableLand() {
		return arableLand;
	}
	public void setArableLand(double arableLand) {
		this.arableLand = arableLand;
	}
	public int getHouseHold() {
		return houseHold;
	}
	public void setHouseHold(int houseHold) {
		this.houseHold = houseHold;
	}
	public long getPopulation() {
		return population;
	}
	public void setPopulation(long population) {
		this.population = population;
	}
	public int getLabor() {
		return labor;
	}
	public void setLabor(int labor) {
		this.labor = labor;
	}
	public String getHelpUnit() {
		return helpUnit;
	}
	public void setHelpUnit(String helpUnit) {
		this.helpUnit = helpUnit;
	}
	public String getPvAnnual() {
		return pvAnnual;
	}
	public void setPvAnnual(String pvAnnual) {
		this.pvAnnual = pvAnnual;
	}
//	public double getAnnualIncome() {
//		return annualIncome;
//	}
//	public void setAnnualIncome(double annualIncome) {
//		this.annualIncome = annualIncome;
//	}
	
	public double getAnnualIncome_13() {
		return annualIncome_13;
	}
	public void setAnnualIncome_13(double annualIncome_13) {
		this.annualIncome_13 = annualIncome_13;
	}
	public double getAnnualIncome_14() {
		return annualIncome_14;
	}
	public void setAnnualIncome_14(double annualIncome_14) {
		this.annualIncome_14 = annualIncome_14;
	}
	public double getAnnualIncome_15() {
		return annualIncome_15;
	}
	public void setAnnualIncome_15(double annualIncome_15) {
		this.annualIncome_15 = annualIncome_15;
	}
	
//	public double getAnnualIncome_16() {
//		return annualIncome_16;
//	}
//	public void setAnnualIncome_16(double annualIncome_16) {
//		this.annualIncome_16 = annualIncome_16;
//	}
//	public double getAnnualIncome_17() {
//		return annualIncome_17;
//	}
//	public void setAnnualIncome_17(double annualIncome_17) {
//		this.annualIncome_17 = annualIncome_17;
//	}
	public int getToilet() {
		return toilet;
	}
	public void setToilet(int toilet) {
		this.toilet = toilet;
	}
	public double getHardBottom() {
		return hardBottom;
	}
	public void setHardBottom(double hardBottom) {
		this.hardBottom = hardBottom;
	}
	public String getFixAnnual() {
		return fixAnnual;
	}
	public void setFixAnnual(String fixAnnual) {
		this.fixAnnual = fixAnnual;
	}
	public int getCulturalAct() {
		return culturalAct;
	}
	public void setCulturalAct(int culturalAct) {
		this.culturalAct = culturalAct;
	}
	public double getCulturalActArea() {
		return culturalActArea;
	}
	public void setCulturalActArea(double culturalActArea) {
		this.culturalActArea = culturalActArea;
	}
	public int getRuralPark() {
		return ruralPark;
	}
	public void setRuralPark(int ruralPark) {
		this.ruralPark = ruralPark;
	}
	public double getRuralParkArea() {
		return ruralParkArea;
	}
	public void setRuralParkArea(double ruralParkArea) {
		this.ruralParkArea = ruralParkArea;
	}
	public int getRuralSquare() {
		return ruralSquare;
	}
	public void setRuralSquare(int ruralSquare) {
		this.ruralSquare = ruralSquare;
	}
	public double getSquareArea() {
		return squareArea;
	}
	public void setSquareArea(double squareArea) {
		this.squareArea = squareArea;
	}
	public int getHealthStation() {
		return healthStation;
	}
	public void setHealthStation(int healthStation) {
		this.healthStation = healthStation;
	}
	public double getHealthStationArea() {
		return healthStationArea;
	}
	public void setHealthStationArea(double healthStationArea) {
		this.healthStationArea = healthStationArea;
	}
	public String getCouncilAnnual() {
		return councilAnnual;
	}
	public void setCouncilAnnual(String councilAnnual) {
		this.councilAnnual = councilAnnual;
	}
	public int getCouncilMember() {
		return councilMember;
	}
	public void setCouncilMember(int councilMember) {
		this.councilMember = councilMember;
	}
	public String getCouncilName1() {
		return councilName1;
	}
	public void setCouncilName1(String councilName1) {
		this.councilName1 = councilName1;
	}
	public String getCouncilHP1() {
		return councilHP1;
	}
	public void setCouncilHP1(String councilHP1) {
		this.councilHP1 = councilHP1;
	}
	public String getCouncilName2() {
		return councilName2;
	}
	public void setCouncilName2(String councilName2) {
		this.councilName2 = councilName2;
	}
	public String getCouncilHP2() {
		return councilHP2;
	}
	public void setCouncilHP2(String councilHP2) {
		this.councilHP2 = councilHP2;
	}
	public String getAnnual() {
		return annual;
	}
	public void setAnnual(String annual) {
		this.annual = annual;
	}
	public String getTapAnnual() {
		return tapAnnual;
	}
	public void setTapAnnual(String tapAnnual) {
		this.tapAnnual = tapAnnual;
	}
	public double getBottomDis() {
		return bottomDis;
	}
	public void setBottomDis(double bottomDis) {
		this.bottomDis = bottomDis;
	}
	public String getWaterBase() {
		return waterBase;
	}
	public void setWaterBase(String waterBase) {
		this.waterBase = waterBase;
	}
	public String getSmallWater() {
		return smallWater;
	}
	public void setSmallWater(String smallWater) {
		this.smallWater = smallWater;
	}
	public double getFarmland() {
		return farmland;
	}
	public void setFarmland(double farmland) {
		this.farmland = farmland;
	}
	public String getCleaners() {
		return cleaners;
	}
	public void setCleaners(String cleaners) {
		this.cleaners = cleaners;
	}
	public double getToiletPercent() {
		return toiletPercent;
	}
	public void setToiletPercent(double toiletPercent) {
		this.toiletPercent = toiletPercent;
	}
	public String getBadWater() {
		return badWater;
	}
	public void setBadWater(String badWater) {
		this.badWater = badWater;
	}
	public String getBadWaterAnn() {
		return badWaterAnn;
	}
	public void setBadWaterAnn(String badWaterAnn) {
		this.badWaterAnn = badWaterAnn;
	}
	public int getNoValue() {
		return noValue;
	}
	public void setNoValue(int noValue) {
		this.noValue = noValue;
	}
	public int getBuildAgain() {
		return buildAgain;
	}
	public void setBuildAgain(int buildAgain) {
		this.buildAgain = buildAgain;
	}
	public String getChangePlan() {
		return changePlan;
	}
	public void setChangePlan(String changePlan) {
		this.changePlan = changePlan;
	}
	public String getDesignPic() {
		return designPic;
	}
	public void setDesignPic(String designPic) {
		this.designPic = designPic;
	}
	public int getPlanCount() {
		return planCount;
	}
	public void setPlanCount(int planCount) {
		this.planCount = planCount;
	}
	public int getFinishCount() {
		return finishCount;
	}
	public void setFinishCount(int finishCount) {
		this.finishCount = finishCount;
	}
	public int getVillageToilet() {
		return villageToilet;
	}
	public void setVillageToilet(int villageToilet) {
		this.villageToilet = villageToilet;
	}
	public double getVillageToiletArea() {
		return villageToiletArea;
	}
	public void setVillageToiletArea(double villageToiletArea) {
		this.villageToiletArea = villageToiletArea;
	}
	public String getRuralParkAnn() {
		return ruralParkAnn;
	}
	public void setRuralParkAnn(String ruralParkAnn) {
		this.ruralParkAnn = ruralParkAnn;
	}
	public String getCulturalActAnn() {
		return culturalActAnn;
	}
	public void setCulturalActAnn(String culturalActAnn) {
		this.culturalActAnn = culturalActAnn;
	}
	public String getSquareAnn() {
		return squareAnn;
	}
	public void setSquareAnn(String squareAnn) {
		this.squareAnn = squareAnn;
	}
	public String getHealthStationAnn() {
		return healthStationAnn;
	}
	public void setHealthStationAnn(String healthStationAnn) {
		this.healthStationAnn = healthStationAnn;
	}
	public String getVillageToiletAnn() {
		return villageToiletAnn;
	}
	public void setVillageToiletAnn(String villageToiletAnn) {
		this.villageToiletAnn = villageToiletAnn;
	}
	public String getPsAnnual() {
		return psAnnual;
	}
	public void setPsAnnual(String psAnnual) {
		this.psAnnual = psAnnual;
	}
	public String getWorkSituation() {
		return workSituation;
	}
	public void setWorkSituation(String workSituation) {
		this.workSituation = workSituation;
	}
	public Long getModelareaId() {
		return modelareaId;
	}
	public void setModelareaId(Long modelareaId) {
		this.modelareaId = modelareaId;
	}
	public String getCouncilFilePath() {
		return councilFilePath;
	}
	public void setCouncilFilePath(String councilFilePath) {
		this.councilFilePath = councilFilePath;
	}
	public String getCouncilFileName() {
		return councilFileName;
	}
	public void setCouncilFileName(String councilFileName) {
		this.councilFileName = councilFileName;
	}
	public String getGarbageAnnual() {
		return garbageAnnual;
	}
	public void setGarbageAnnual(String garbageAnnual) {
		this.garbageAnnual = garbageAnnual;
	}
	
	
}
