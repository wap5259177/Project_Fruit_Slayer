package cn.bonoon.controllers.newrural;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@FormDetail(autoIgnore = true, width = 620)
@InsertCell(value = "applicant/administrationrural-uncore-view.vm", type = EmbedType.PARSE)
@WithDialog(initializer = AdministrationRuralDetailInitializerNoCore.class)
public class AdministrationRuralDetailNoCore  extends ObjectEditor implements AdministrationRuralDefine{

	/**
	 * 
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 102367464634516153L;

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
	 * 不需要手动填写，从上报备案时的村子得来
	 */
//	//自然村个数
	private int natrualruralNum;
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public int getNatrualruralNum() {
//		return natrualruralNum;
//	}
//
//	public void setNatrualruralNum(int natrualruralNum) {
//		this.natrualruralNum = natrualruralNum;
//	}

//	public String getNatrualruralList() {
//		return natrualruralList;
//	}
//
//	public void setNatrualruralList(String natrualruralList) {
//		this.natrualruralList = natrualruralList;
//	}

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

	public int getDangerHouse() {
		return dangerHouse;
	}

	public void setDangerHouse(int dangerHouse) {
		this.dangerHouse = dangerHouse;
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
