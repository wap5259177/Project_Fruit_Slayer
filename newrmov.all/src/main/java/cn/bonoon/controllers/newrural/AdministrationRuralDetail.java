package cn.bonoon.controllers.newrural;

import java.util.Date;

import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@FormDetail(autoIgnore = true, width = 680)
@InsertCell(value = "applicant/administrationrural-view.vm", type = EmbedType.PARSE)
@WithDialog(initializer = AdministrationRuralDetailInitializer.class)
public class AdministrationRuralDetail  extends ObjectEditor implements AdministrationRuralDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9160712841562605762L;

	/**
	 * 
	 */
	
	@TransformField(value = "modelArea.id", writable = WriteModel.NONE)
	private Long modelareaId;
	
	@TransformField(writable = WriteModel.NONE)
	private String name;
	
	@TransformField(writable = WriteModel.NONE)
	private String town;

	@TransformField(writable = WriteModel.NONE)
	private long placeId;
	
	
	
	/**
	 * (一)基本情况
	 */
	//年度
	private String annual;
	
	//序号
	private String code;
	
	/*
	 * 不需要手动填，从上报备案时的村子自动获取
	 */
	//自然村个数
	private int natrualruralNum;
	
	//TODO:自然村名单
	private String natrualruralList;
	
	//总面积
	private double ruralArea;
	
	//耕地面积
	private double arableLand;
	
	//户籍数 (户)
	private int houseHold;
	
	//人口数
	private int population;
	
	//劳动力总人数
	private int labor;
	
	//是否省级扶贫开发重点村（贫困村）
	private String poorVillage;
	
	//帮扶单位名称
	private String helpUnit;
	
	
	/*
	 * 需求     新增的
	 */
	//贫困户数
	private int poorHouseHold;
	
	//贫困人口数
	private int poorPopulation;
	
	
	//低保户数
	private int lowHouseHold;
	
	//低保人口数
	private int  lowPopulation;
	
	//五保户数
	private int wubaoHouseHold;
	
	//需改造的危房户数
	private int dangerHouse;
	
	//各年度农民年人均纯收入
	//private double annualIncome;
	
	private double annualIncome_13;
	
	private double annualIncome_14;
	
	private double annualIncome_15;
	
	private double annualIncome_16;
	
	private double annualIncome_17;
	
	
	/*
	 * 20150811号新增字段:
	 * 		村集体经济收入[万元]
	 */
	
	private double collectivityAnnulIncome_13;
	
	private double collectivityAnnulIncome_14;
	
	private double collectivityAnnulIncome_15;
	
	private double collectivityAnnulIncome_16;
	
	private double collectivityAnnulIncome_17;
	
	
	
	/**
	 * (四)优势资源情况
	 */
	// 人文历史
	private String histroyDesc;

	// 自然生态
	private String ecologicalDesc;

	// 民居风貌
	private String civilianDesc;

	// 乡村旅游
	private String tourDesc;

	// 特色产业
	private String industryDesc;

	// 渔业渔港
	private String fisheryDesc;

	// 其他
	private String otherDesc;


	// 现有的或正在打造的旅游景点
	private int viewSpot;

	private String viewSpot1;

	private String viewSpot2;

	private String viewSpot3;
	
	
	
	/**
	 * (五)农村公共服务情况
	 */
	
	//文化活动场所个数
	private int culturalAct;
	
	//文化活动场所面积
	private double culturalActArea;
	
	private String culturalActAnn;
	
	//乡村公园个数
	private int ruralPark;
	
	//乡村公园面积
	private double ruralParkArea;
	
	private String ruralParkAnn;
	
	//文体广场个数
	private int  ruralSquare;
	
	//文体广场所面积
	private double squareArea;
	
	private String squareAnn;

	//村级卫生站个数
	private int  healthStation;
	
	//村级卫生站面积
	private double healthStationArea;
	
	private String healthStationAnn;
	
	//乡村公厕个数
	private int  villageToilet;
	
	//乡村公厕面积
	private double villageToiletArea;
	
	private String villageToiletAnn;
	
	//是否建立统一的村级公共服务管理平台
	private String  publicService;
	
	private String  psAnnual;
	

	/**
	 * (六)挂点县领导
	 * 
	 */
	private String leaderName;

	private String leaderJob;

	private String leaderHP;

	private String unitProperty;

	private String leaderName2;

	private String leaderJob2;

	private String leaderHP2;

	private String unPro2;

	/**
	 * (七)工作小组
	 */
	
	//是否建立专家小组
	private String  expertGroup;
	
	/**
	 * 
	 * (八)规划进展
	 */
	// 是否完成总体规划
	private String generalPlan;
	private Date gpDate;

	// 是否完成连线连片规划
	private String areaPlan;
	private Date apDate;

	// 是否完成村庄深度规划设计（含节点规划设计）
	private String depthPlanning;
	private Date dpDate;
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
	public long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(long placeId) {
		this.placeId = placeId;
	}
	public String getAnnual() {
		return annual;
	}
	public void setAnnual(String annual) {
		this.annual = annual;
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
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public int getLabor() {
		return labor;
	}
	public void setLabor(int labor) {
		this.labor = labor;
	}
	public String getPoorVillage() {
		return poorVillage;
	}
	public void setPoorVillage(String poorVillage) {
		this.poorVillage = poorVillage;
	}
	public String getHelpUnit() {
		return helpUnit;
	}
	public void setHelpUnit(String helpUnit) {
		this.helpUnit = helpUnit;
	}
	public int getPoorHouseHold() {
		return poorHouseHold;
	}
	public void setPoorHouseHold(int poorHouseHold) {
		this.poorHouseHold = poorHouseHold;
	}
	public int getPoorPopulation() {
		return poorPopulation;
	}
	public void setPoorPopulation(int poorPopulation) {
		this.poorPopulation = poorPopulation;
	}
	public int getLowHouseHold() {
		return lowHouseHold;
	}
	public void setLowHouseHold(int lowHouseHold) {
		this.lowHouseHold = lowHouseHold;
	}
	public int getLowPopulation() {
		return lowPopulation;
	}
	public void setLowPopulation(int lowPopulation) {
		this.lowPopulation = lowPopulation;
	}
	public int getWubaoHouseHold() {
		return wubaoHouseHold;
	}
	public void setWubaoHouseHold(int wubaoHouseHold) {
		this.wubaoHouseHold = wubaoHouseHold;
	}
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
	public double getAnnualIncome_16() {
		return annualIncome_16;
	}
	public void setAnnualIncome_16(double annualIncome_16) {
		this.annualIncome_16 = annualIncome_16;
	}
	public double getAnnualIncome_17() {
		return annualIncome_17;
	}
	public void setAnnualIncome_17(double annualIncome_17) {
		this.annualIncome_17 = annualIncome_17;
	}
	public String getHistroyDesc() {
		return histroyDesc;
	}
	public void setHistroyDesc(String histroyDesc) {
		this.histroyDesc = histroyDesc;
	}
	public String getEcologicalDesc() {
		return ecologicalDesc;
	}
	public void setEcologicalDesc(String ecologicalDesc) {
		this.ecologicalDesc = ecologicalDesc;
	}
	public String getCivilianDesc() {
		return civilianDesc;
	}
	public void setCivilianDesc(String civilianDesc) {
		this.civilianDesc = civilianDesc;
	}
	public String getTourDesc() {
		return tourDesc;
	}
	public void setTourDesc(String tourDesc) {
		this.tourDesc = tourDesc;
	}
	public String getIndustryDesc() {
		return industryDesc;
	}
	public void setIndustryDesc(String industryDesc) {
		this.industryDesc = industryDesc;
	}
	public String getFisheryDesc() {
		return fisheryDesc;
	}
	public void setFisheryDesc(String fisheryDesc) {
		this.fisheryDesc = fisheryDesc;
	}
	public String getOtherDesc() {
		return otherDesc;
	}
	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}
	public int getViewSpot() {
		return viewSpot;
	}
	public void setViewSpot(int viewSpot) {
		this.viewSpot = viewSpot;
	}
	public String getViewSpot1() {
		return viewSpot1;
	}
	public void setViewSpot1(String viewSpot1) {
		this.viewSpot1 = viewSpot1;
	}
	public String getViewSpot2() {
		return viewSpot2;
	}
	public void setViewSpot2(String viewSpot2) {
		this.viewSpot2 = viewSpot2;
	}
	public String getViewSpot3() {
		return viewSpot3;
	}
	public void setViewSpot3(String viewSpot3) {
		this.viewSpot3 = viewSpot3;
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
	public String getCulturalActAnn() {
		return culturalActAnn;
	}
	public void setCulturalActAnn(String culturalActAnn) {
		this.culturalActAnn = culturalActAnn;
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
	public String getRuralParkAnn() {
		return ruralParkAnn;
	}
	public void setRuralParkAnn(String ruralParkAnn) {
		this.ruralParkAnn = ruralParkAnn;
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
	public String getSquareAnn() {
		return squareAnn;
	}
	public void setSquareAnn(String squareAnn) {
		this.squareAnn = squareAnn;
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
	public String getPublicService() {
		return publicService;
	}
	public void setPublicService(String publicService) {
		this.publicService = publicService;
	}
	public String getPsAnnual() {
		return psAnnual;
	}
	public void setPsAnnual(String psAnnual) {
		this.psAnnual = psAnnual;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getLeaderJob() {
		return leaderJob;
	}
	public void setLeaderJob(String leaderJob) {
		this.leaderJob = leaderJob;
	}
	public String getLeaderHP() {
		return leaderHP;
	}
	public void setLeaderHP(String leaderHP) {
		this.leaderHP = leaderHP;
	}
	public String getUnitProperty() {
		return unitProperty;
	}
	public void setUnitProperty(String unitProperty) {
		this.unitProperty = unitProperty;
	}
	public String getLeaderName2() {
		return leaderName2;
	}
	public void setLeaderName2(String leaderName2) {
		this.leaderName2 = leaderName2;
	}
	public String getLeaderJob2() {
		return leaderJob2;
	}
	public void setLeaderJob2(String leaderJob2) {
		this.leaderJob2 = leaderJob2;
	}
	public String getLeaderHP2() {
		return leaderHP2;
	}
	public void setLeaderHP2(String leaderHP2) {
		this.leaderHP2 = leaderHP2;
	}
	public String getUnPro2() {
		return unPro2;
	}
	public void setUnPro2(String unPro2) {
		this.unPro2 = unPro2;
	}
	public String getExpertGroup() {
		return expertGroup;
	}
	public void setExpertGroup(String expertGroup) {
		this.expertGroup = expertGroup;
	}
	public String getGeneralPlan() {
		return generalPlan;
	}
	public void setGeneralPlan(String generalPlan) {
		this.generalPlan = generalPlan;
	}
	public Date getGpDate() {
		return gpDate;
	}
	public void setGpDate(Date gpDate) {
		this.gpDate = gpDate;
	}
	public String getAreaPlan() {
		return areaPlan;
	}
	public void setAreaPlan(String areaPlan) {
		this.areaPlan = areaPlan;
	}
	public Date getApDate() {
		return apDate;
	}
	public void setApDate(Date apDate) {
		this.apDate = apDate;
	}
	public String getDepthPlanning() {
		return depthPlanning;
	}
	public void setDepthPlanning(String depthPlanning) {
		this.depthPlanning = depthPlanning;
	}
	public Date getDpDate() {
		return dpDate;
	}
	public void setDpDate(Date dpDate) {
		this.dpDate = dpDate;
	}
	
	
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
//	public int getNatrualruralNum() {
//		return natrualruralNum;
//	}
//	public void setNatrualruralNum(int natrualruralNum) {
//		this.natrualruralNum = natrualruralNum;
//	}
	
	public int getDangerHouse() {
		return dangerHouse;
	}
	public void setDangerHouse(int dangerHouse) {
		this.dangerHouse = dangerHouse;
	}
	
	
	
	
	
//	public String getNatrualruralList() {
//		return natrualruralList;
//	}
//	public void setNatrualruralList(String natrualruralList) {
//		this.natrualruralList = natrualruralList;
//	}
	
	
	
	
	public double getCollectivityAnnulIncome_13() {
		return collectivityAnnulIncome_13;
	}
	public void setCollectivityAnnulIncome_13(double collectivityAnnulIncome_13) {
		this.collectivityAnnulIncome_13 = collectivityAnnulIncome_13;
	}
	public double getCollectivityAnnulIncome_14() {
		return collectivityAnnulIncome_14;
	}
	public void setCollectivityAnnulIncome_14(double collectivityAnnulIncome_14) {
		this.collectivityAnnulIncome_14 = collectivityAnnulIncome_14;
	}
	public double getCollectivityAnnulIncome_15() {
		return collectivityAnnulIncome_15;
	}
	public void setCollectivityAnnulIncome_15(double collectivityAnnulIncome_15) {
		this.collectivityAnnulIncome_15 = collectivityAnnulIncome_15;
	}
	public double getCollectivityAnnulIncome_16() {
		return collectivityAnnulIncome_16;
	}
	public void setCollectivityAnnulIncome_16(double collectivityAnnulIncome_16) {
		this.collectivityAnnulIncome_16 = collectivityAnnulIncome_16;
	}
	public double getCollectivityAnnulIncome_17() {
		return collectivityAnnulIncome_17;
	}
	public void setCollectivityAnnulIncome_17(double collectivityAnnulIncome_17) {
		this.collectivityAnnulIncome_17 = collectivityAnnulIncome_17;
	}
	public final void reset(AdministrationCoreRuralEntity are) {
		//adminRuralId = are.getId();
		
		setLeaderName(are.getLeaderName());
		setLeaderName2(are.getLeaderName2());
		setLeaderHP(are.getLeaderHP());
		setLeaderHP2(are.getLeaderHP2());
		setLeaderJob(are.getLeaderJob());
		setLeaderJob2(are.getLeaderJob2());
		setUnitProperty(are.getUnitProperty());
		setUnPro2(are.getUnPro2());
		
		setGeneralPlan(are.getGeneralPlan());
		setGpDate(are.getGpDate());
		setApDate(are.getApDate());
		setDpDate(are.getDpDate());
		setAreaPlan(are.getAreaPlan());
		setDepthPlanning(are.getDepthPlanning());
		setExpertGroup(are.getExpertGroup());
	}
	public int getNatrualruralNum() {
		return natrualruralNum;
	}
	public void setNatrualruralNum(int natrualruralNum) {
		this.natrualruralNum = natrualruralNum;
	}
	public String getNatrualruralList() {
		return natrualruralList;
	}
	public void setNatrualruralList(String natrualruralList) {
		this.natrualruralList = natrualruralList;
	}
	
	
	
}
