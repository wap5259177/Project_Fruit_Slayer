package cn.bonoon.controllers.newrural;

import cn.bonoon.controllers.inofs.BasePeripheralRuralInfo;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.HasFile;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@InsertCell(value = "applicant/peripheralrural-editor.vm", type = EmbedType.PARSE)
@DialogDefaultButton(buttonName = "暂存(未确认)", functionBody = "_checkNumber();", otherButtonName = "确认", otherParameterName = "applicant-submit", otherParameterValue = "true", otherFunctionBody = "if(!confirm('确认后示范片区信息才可以提交，是否确认？')){return false;}_checkNumber();")
@WithDialog(initializer = PeripheraRuralEditorInitializer.class)
@FormEditor(width = 680)
@HasFile
public class PeripheraRuralEditor extends BasePeripheralRuralInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -543390148860092050L;
	
	@TransformField(readable = false, writable = WriteModel.NONE)
	private String[] unitItems;
	@TransformField(readable = false, writable = WriteModel.NONE)
	private String[] workgroupItems;
	@TransformField(readable = false, writable = WriteModel.NONE)
	private String[] expertgroupItems;
	
	public String[] getUnitItems() {
		return unitItems;
	}
	public void setUnitItems(String[] unitItems) {
		this.unitItems = unitItems;
	}
	public String[] getWorkgroupItems() {
		return workgroupItems;
	}
	public void setWorkgroupItems(String[] workgroupItems) {
		this.workgroupItems = workgroupItems;
	}
	public String[] getExpertgroupItems() {
		return expertgroupItems;
	}
	public void setExpertgroupItems(String[] expertgroupItems) {
		this.expertgroupItems = expertgroupItems;
	}

	// private Integer submit; // 0:暂存 1:提交
	// @TransformField("modelArea.id")
	// @PropertyEditor(name="选择片区")
	// @AsComboBox
	// @AutoDataLoader(ModelAreaEntity.class)
	// private Long modelArea;
/*
	private String code;

	public PeripheralRuralEntity toEntity(PeripheralRuralEntity e,
			ModelAreaEntity mae) {
		e.setModelArea(mae);
		e.setType(this.type);
		e.setPoorVillage(this.poorVillage);
		e.setFamousVillage(this.famousVillage);
		e.setFamousBatch(this.famousBatch);
		e.setPushVillage(this.pushVillage);
		e.setVillageManage(this.villageManage);
		e.setTapWater(this.tapWater);
		e.setHyFix(this.hyFix);
		e.setCleanTeam(this.cleanTeam);
		e.setPublicService(this.publicService);
		e.setCouncil(this.council);
		e.setConstitu(this.constitu);
		e.setTown(this.town);
		e.setName(this.name);
		e.setNaturalVillage(this.naturalVillage);
		e.setRuralArea(this.ruralArea);
		e.setArableLand(this.arableLand);
		e.setHouseHold(this.houseHold);
		e.setPopulation(this.population);
		e.setLabor(this.labor);
		e.setHelpUnit(this.helpUnit);
		e.setPvAnnual(this.pvAnnual);
		e.setAnnualIncome(this.annualIncome);
		e.setHardBottom(this.hardBottom);
		e.setToilet(this.toilet);
		e.setFixAnnual(this.fixAnnual);
		e.setCulturalAct(this.culturalAct);
		e.setCulturalActArea(this.culturalActArea);
		e.setRuralPark(this.ruralPark);
		e.setRuralParkArea(ruralParkArea);
		e.setRuralSquare(this.ruralSquare);
		e.setSquareArea(this.squareArea);
		e.setHealthStation(this.healthStation);
		e.setHealthStationArea(this.healthStationArea);
		e.setCouncilAnnual(this.councilAnnual);
		e.setCouncilMember(this.councilMember);
		e.setCouncilName1(this.councilName1);
		e.setCouncilHP1(this.councilHP1);
		e.setCouncilName2(this.councilName2);
		e.setCouncilHP2(this.councilHP2);
		e.setCreateAt(new Date());
		e.setEniPlan(this.eniPlan);
		e.setEniPlanDate(this.eniPlanDate);
		e.setTapAnnual(this.tapAnnual);
		e.setBottomDis(this.bottomDis);
		e.setWaterBase(this.waterBase);
		e.setSmallWater(this.smallWater);
		e.setFarmland(this.farmland);
		e.setCleaners(this.cleaners);
		e.setToiletPercent(this.toiletPercent);
		e.setBadWater(this.badWater);
		e.setBadWaterAnn(this.badWaterAnn);
		e.setNoValue(this.noValue);
		e.setBuildAgain(this.buildAgain);
		e.setChangePlan(this.changePlan);
		e.setDesignPic(this.designPic);
		e.setPlanCount(this.planCount);
		e.setFinishCount(this.finishCount);
		e.setCulturalActAnn(this.culturalActAnn);
		e.setRuralParkAnn(this.ruralParkAnn);
		e.setSquareAnn(this.squareAnn);
		e.setHealthStationAnn(this.healthStationAnn);
		e.setVillageToilet(this.villageToilet);
		e.setVillageToiletArea(this.villageToiletArea);
		e.setVillageToiletAnn(this.villageToiletAnn);
		e.setPsAnnual(this.psAnnual);
		e.setWorkSituation(this.workSituation);
		// e.setPlaceId(this.placeId);
		e.setAnnual(this.annual);
		return e;
	}

	public void set(PeripheralRuralEntity e) {

		*//************** radioButton *******************//*
		this.type = e.getType();
		this.poorVillage = e.getPoorVillage();
		this.famousVillage = e.getFamousVillage();
		this.famousBatch = e.getFamousBatch();
		this.pushVillage = e.getPushVillage();
		this.villageManage = e.getVillageManage();
		this.tapWater = e.getTapWater();
		this.hyFix = e.getHyFix();
		this.cleanTeam = e.getCleanTeam();
		this.publicService = e.getPublicService();
		this.council = e.getCouncil();
		this.constitu = e.getConstitu();

		*//************ input ***************//*
		this.modelareaId = e.getModelArea().getId();
		this.name = e.getName();
		this.town = e.getTown();
		this.naturalVillage = e.getNaturalVillage();
		this.ruralArea = e.getRuralArea();
		this.arableLand = e.getArableLand();
		this.houseHold = e.getHouseHold();
		this.population = e.getPopulation();
		this.labor = e.getLabor();
		this.helpUnit = e.getHelpUnit();
		this.pvAnnual = e.getPvAnnual();
		this.annualIncome = e.getAnnualIncome();
		this.hardBottom = e.getHardBottom();
		this.toilet = e.getToilet();
		this.fixAnnual = e.getFixAnnual();
		this.culturalAct = e.getCulturalAct();
		this.councilAnnual = e.getCouncilAnnual();
		this.culturalActArea = e.getCulturalActArea();
		this.ruralPark = e.getRuralPark();
		this.ruralParkArea = e.getRuralParkArea();
		this.ruralSquare = e.getRuralSquare();
		this.squareArea = e.getSquareArea();
		this.healthStation = e.getHealthStation();
		this.healthStationArea = e.getHealthStationArea();
		this.councilMember = e.getCouncilMember();
		this.councilName1 = e.getCouncilName1();
		this.councilHP1 = e.getCouncilHP1();
		this.councilName2 = e.getCouncilName2();
		this.councilHP2 = e.getCouncilHP2();

		this.eniPlan = e.getEniPlan();
		this.eniPlanDate = e.getEniPlanDate();
		this.tapAnnual = e.getTapAnnual();
		this.bottomDis = e.getBottomDis();
		this.waterBase = e.getWaterBase();
		this.smallWater = e.getSmallWater();
		this.farmland = e.getFarmland();
		this.cleaners = e.getCleaners();
		this.toiletPercent = e.getToiletPercent();
		this.badWater = e.getBadWater();
		this.badWaterAnn = e.getBadWaterAnn();
		this.noValue = e.getNoValue();
		this.buildAgain = e.getBuildAgain();
		this.changePlan = e.getChangePlan();
		this.designPic = e.getDesignPic();
		this.planCount = e.getPlanCount();
		this.finishCount = e.getFinishCount();
		this.culturalActAnn = e.getCulturalActAnn();
		this.ruralParkAnn = e.getRuralParkAnn();
		this.squareAnn = e.getSquareAnn();
		this.healthStationAnn = e.getHealthStationAnn();
		this.villageToilet = e.getVillageToilet();
		this.villageToiletArea = e.getVillageToiletArea();
		this.villageToiletAnn = e.getVillageToiletAnn();
		this.psAnnual = e.getPsAnnual();
		this.workSituation = e.getWorkSituation();
		// this.placeId = e.getPlaceId();
		this.annual = e.getAnnual();
	}

	// public Integer getSubmit() {
	// return submit;
	// }

	// public void setSubmit(Integer submit) {
	// this.submit = submit;
	// }
	@TransformField(value = "modelArea.id", writable = WriteModel.NONE)
	private Long modelareaId;
	@TransformField("newRural.id")
	private Long newRural;

	*//*********** RadioButton *************//*
//	private String type;
//	private String poorVillage;
//	private String famousVillage;
//	
//	private String famousBatch;
//	private String pushVillage;
//	private String villageManage;
//	private String tapWater;
//	
//	private String hyFix;
//	private String cleanTeam;
//	private String publicService;
//	
//	private String council;
//	private String constitu;
	*//*********** input *************//*
//	private String name;
//	private String town;
//	private String naturalVillage;
//	private double ruralArea;
//	private double arableLand;
	
//	private int houseHold;
//	private long population;
//	private int labor;
//	private String helpUnit;
//	private String pvAnnual;
	
////	private double annualIncome;
//	private int toilet;
//	private double hardBottom;
//	private String fixAnnual;
//	private int culturalAct;
//	private double culturalActArea;
//	private int ruralPark;
//	private double ruralParkArea;
//	private int ruralSquare;
//	private double squareArea;
//	private int healthStation;
//	private double healthStationArea;
//	private String councilAnnual;
//	private int councilMember;
//	private String councilName1;
//	private String councilHP1;
//	private String councilName2;
//	private String councilHP2;

	private String eniPlan;
	private int eniPlanDate;
//	
//	private String tapAnnual;
//	private double bottomDis;
//	private String waterBase;
//	private String smallWater;
//	private double farmland;
//	private String cleaners;
//	private double toiletPercent;
//	private String badWater;
//	private String badWaterAnn;
//	private int noValue;
//	private int buildAgain;
//	private String changePlan;
//	private String designPic;
//	
//	private int planCount;
//	private int finishCount;
//	private int villageToilet;
//	private double villageToiletArea;
//	private String ruralParkAnn;
//	private String culturalActAnn;
//	private String squareAnn;
//	private String healthStationAnn;
//	private String villageToiletAnn;
//	private String psAnnual;
//	private String workSituation;
	// private long placeId;
//	private String annual;

	public String getAnnual() {
		return annual;
	}

	public void setAnnual(String annual) {
		this.annual = annual;
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

	// public long getPlaceId() {
	// return placeId;
	// }
	//
	// public void setPlaceId(long placeId) {
	// this.placeId = placeId;
	// }

	public void setPushVillage(String pushVillage) {
		this.pushVillage = pushVillage;
	}

	public String getTapWater() {
		return tapWater;
	}

	public void setTapWater(String tapWater) {
		this.tapWater = tapWater;
	}

	public String getVillageManage() {
		return villageManage;
	}

	public void setVillageManage(String villageManage) {
		this.villageManage = villageManage;
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

	public Long getModelareaId() {
		return modelareaId;
	}

	public void setModelareaId(Long modelareaId) {
		this.modelareaId = modelareaId;
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

	public double getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(double annualIncome) {
		this.annualIncome = annualIncome;
	}

	public double getHardBottom() {
		return hardBottom;
	}

	public void setHardBottom(double hardBottom) {
		this.hardBottom = hardBottom;
	}

	public int getToilet() {
		return toilet;
	}

	public void setToilet(int toilet) {
		this.toilet = toilet;
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

	public String getEniPlan() {
		return eniPlan;
	}

	public void setEniPlan(String eniPlan) {
		this.eniPlan = eniPlan;
	}

	public int getEniPlanDate() {
		return eniPlanDate;
	}

	public void setEniPlanDate(int eniPlanDate) {
		this.eniPlanDate = eniPlanDate;
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

	public String getCulturalActAnn() {
		return culturalActAnn;
	}

	public void setCulturalActAnn(String culturalActAnn) {
		this.culturalActAnn = culturalActAnn;
	}

	public String getRuralParkAnn() {
		return ruralParkAnn;
	}

	public void setRuralParkAnn(String ruralParkAnn) {
		this.ruralParkAnn = ruralParkAnn;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getNewRural() {
		return newRural;
	}

	public void setNewRural(Long newRural) {
		this.newRural = newRural;
	}
*/
	// public Long getModelArea() {
	// return modelArea;
	// }
	//
	// public void setModelArea(Long modelArea) {
	// this.modelArea = modelArea;
	// }

}
