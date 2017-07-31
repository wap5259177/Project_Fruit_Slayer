package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.support.entities.AbstractEntity;

//主体建设行政村
@Entity
@Table(name = "t_adminrural")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AdministrationRuralEntity extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8373494836027908487L;
	
	@ManyToOne
	@JoinColumn(name = "R_MODELAREA_ID")
	private ModelAreaEntity modelArea;

	// 片区子目录
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_DIRECTORY_ID")
	private DirectoryEntity directory;
	
	//place
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_PLACE_ID")
	private PlaceEntity place;
	
	//状态,0:暂存   1:保存
	@Column(name = "C_STATUS")
	private int status;
	
	/**
	 * (一)基本情况
	 */
	//年度
	@Column(name = "C_ANNUAL")
	private String annual;
	
	//镇,村委会(name:实体名字)  
	@Column(name = "C_TOWN")
	private String town;
	
	@Column(name = "C_CODE", length = 50)
	private String code;
	
	//自然村个数
	@Column(name = "C_NATRUALRURALNUM")
	private int natrualruralNum;
	
	//自然村名单
	@Column(name = "C_NATRUALRURALLIST",length = 1024)
	private String natrualruralList;
	
	//总面积
	@Column(name = "C_RURALAREA")
	private double ruralArea;
	
	//耕地面积
	@Column(name = "C_ARABLELAND")
	private double arableLand;
	
	//户籍数 (户)
	@Column(name = "C_HOUSEHOLD")
	private int houseHold;
	
	//人口数
	@Column(name = "C_POPULATION")
	private int population;
	
	//劳动力总人数
	@Column(name = "C_LABOR")
	private int labor;
	
	//是否省级扶贫开发重点村（贫困村）
	@Column(name = "C_POORV")
	private String poorVillage;
	
	//帮扶单位名称
	@Column(name = "C_HELPUNIT")
	private String helpUnit;
	
	
	/*
	 * 需求     新增的
	 */
	//贫困户数
	@Column(name = "C_POORHOUSEHOLD")
	private int poorHouseHold;
	
	//贫困人口数
	@Column(name = "C_POORPOPULATION")
	private int poorPopulation;
	
	
	//低保户数
	@Column(name = "C_LOWHOUSEHOLD")
	private int lowHouseHold;
	
	//低保人口数
	@Column(name = "C_LOWPOPULATION")
	private int  lowPopulation;
	
	//五保户数
	@Column(name = "C_WUBAOHOUSEHOLD")
	private int wubaoHouseHold;
	
	//需改造危房户数
	@Column(name = "C_DANGERHOUSE")
	private int dangerHouse;
	
	//各年度农民年人均纯收入
//	@Column(name = "C_ANNUALINCOME")
//	private double annualIncome;
	
	@Column(name = "C_ANNUALINCOME_13")
	private double annualIncome_13;
	
	@Column(name = "C_ANNUALINCOME_14")
	private double annualIncome_14;
	
	@Column(name = "C_ANNUALINCOME_15")
	private double annualIncome_15;
	
	@Column(name = "C_ANNUALINCOME_16")
	private double annualIncome_16;
	
	@Column(name = "C_ANNUALINCOME_17")
	private double annualIncome_17;
	
	
	
	/*
	 * author:wuzhanggui
	 * 20150811号,新增字段:
	 * 		村集体经济收入[万元]
	 */
	@Column(name = "C_COLLECTIVITYANNULINCOME_13")
	private double collectivityAnnulIncome_13;
	
	@Column(name = "C_COLLECTIVITYANNULINCOME_14")
	private double collectivityAnnulIncome_14;
	
	@Column(name = "C_COLLECTIVITYANNULINCOME_15")
	private double collectivityAnnulIncome_15;
	
	@Column(name = "C_COLLECTIVITYANNULINCOME_16")
	private double collectivityAnnulIncome_16;
	
	@Column(name = "C_COLLECTIVITYANNULINCOME_17")
	private double collectivityAnnulIncome_17;
	
	
	
//	
//	/**
//	 * (四)优势资源情况
//	 */
//	// 人文历史
//	@Column(name = "C_HISDESC")
//	private String histroyDesc;
//
//	// 自然生态
//	@Column(name = "C_ECODESC")
//	private String ecologicalDesc;
//
//	// 民居风貌
//	@Column(name = "C_CIVDESC")
//	private String civilianDesc;
//
//	// 乡村旅游
//	@Column(name = "C_TOURDESC")
//	private String tourDesc;
//
//	// 特色产业
//	@Column(name = "C_INDDESC")
//	private String industryDesc;
//
//	// 渔业渔港
//	@Column(name = "C_FISHDESC")
//	private String fisheryDesc;
//
//	// 其他
//	@Column(name = "C_OTHERDESC")
//	private String otherDesc;
//
//
//	// 现有的或正在打造的旅游景点
//	@Column(name = "C_VIEWSPOT")
//	private int viewSpot;
//
//	@Column(name = "C_VIEWSPOT1")
//	private String viewSpot1;
//
//	@Column(name = "C_VIEWSPOT2")
//	private String viewSpot2;
//
//	@Column(name = "C_VIEWSPOT3")
//	private String viewSpot3;
//	
//	
	
	/**
	 * (五)农村公共服务情况
	 */
	
	//文化活动场所个数
	@Column(name = "C_CULACT")
	private int culturalAct;
	
	//文化活动场所面积
	@Column(name = "C_CULACTAREA")
	private double culturalActArea;
	
	@Column(name = "C_CULTURALACTANN")
	private String culturalActAnn;
	
	//乡村公园个数
	@Column(name = "C_RURALPARK")
	private int ruralPark;
	
	//乡村公园面积
	@Column(name = "C_RURALPARKAREA")
	private double ruralParkArea;
	
	@Column(name = "C_RURALPARKANN")
	private String ruralParkAnn;
	
	//文体广场个数
	@Column(name = "C_RURALSQUARE")
	private int  ruralSquare;
	
	//文体广场所面积
	@Column(name = "C_SQUAREAREA")
	private double squareArea;
	
	@Column(name = "C_SQUAREANN")
	private String squareAnn;

	//村级卫生站个数
	@Column(name = "C_HEALTHSTATION")
	private int  healthStation;
	
	//村级卫生站面积
	@Column(name = "C_HEALTHSTAAREA")
	private double healthStationArea;
	
	@Column(name = "C_HEALTHSTATIONANN")
	private String healthStationAnn;
	
	//乡村公厕个数
	@Column(name = "C_VILLAGETOILET")
	private int  villageToilet;
	
	//乡村公厕面积
	@Column(name = "C_VILLAGETOILETAREA")
	private double villageToiletArea;
	
	@Column(name = "C_VILLAGETOILETANN")
	private String villageToiletAnn;
	
	//是否建立统一的村级公共服务管理平台
	@Column(name = "C_PUBSERVICE")
	private String  publicService;
	
	@Column(name = "C_PSANNUAL")
	private String  psAnnual;

	public ModelAreaEntity getModelArea() {
		return modelArea;
	}

	public void setModelArea(ModelAreaEntity modelArea) {
		this.modelArea = modelArea;
	}

	public DirectoryEntity getDirectory() {
		return directory;
	}

	public void setDirectory(DirectoryEntity directory) {
		this.directory = directory;
	}

	public PlaceEntity getPlace() {
		return place;
	}

	public void setPlace(PlaceEntity place) {
		this.place = place;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAnnual() {
		return annual;
	}

	public void setAnnual(String annual) {
		this.annual = annual;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	
	
	
	
}
