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
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.entities.AbstractEntity;

@Entity
@Table(name = "t_rural")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BaseRuralEntity extends AbstractEntity{

	//填写核心村与非主体村的公共字段
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8990302777680592154L;

	// 片区子目录
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_DIRECTORY_ID")
	private DirectoryEntity directory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_DIRMEDIA_ID")
	private DirectoryEntity directoryMedia;
	
	@ManyToOne
	@JoinColumn(name = "R_MODELAREA_ID")
	private ModelAreaEntity modelArea;

	//状态,0:暂存   1:保存
	@Column(name = "C_STATUS")
	private int status;

	@Column(name = "C_CODE", length = 50)
	private String code;
	
	//存储村对应的Place ID
	@Column(name = "C_PLACEID")
	private long placeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_PLACE_ID")
	private PlaceEntity place;
	
	/**
	 * 常住人口
	 */
	@Column(name = "C_POPULATION")
	private int population;
	
	/**
	 * 7月9号字段(新)
	 * 		(一)基本情况   多添加的几个字段
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
	
	/**
	 * 7月9号  新字段   
	 * 		(十)民生问题调查梳理去情况
	 */
	//完成自然村数
	@Column(name = "C_FINISHNATUREVILLAGENUM")
	private int finishNatureVillageNum;
	
	//梳理出项目数
	@Column(name = "C_TEASEDPROJECTNUM")
	private int teasedProjectNum;	
	
	//排查出的矛盾纠纷数
	@Column(name = "C_TROUBLESHOOTING")
	private int troubleshooting;
	
	//其中已化解数
	@Column(name = "C_RESOLVEDTROUBLESHOOTING")
	private int resolvedTroubleshooting;
	
	/**
	 * 2月25号字段(新)
	 * 
	 */
	//年度
	@Column(name = "C_ANNUAL")
	private String annual;
	
	//村庄类型
	@Column(name = "C_TYPE")
	private String type;
	
	//镇
	@Column(name = "C_TOWN")
	private String town;
	
	//自然村
	@Column(name = "C_NATURALVILLAGE")
	private String naturalVillage;
	
	//总面积
	@Column(name = "C_RURALAREA")
	private double ruralArea;
	
	//耕地面积
	@Column(name = "C_ARABLELAND")
	private double arableLand;
	
	//户籍数 (户)
	@Column(name = "C_HOUSEHOLD")
	private int houseHold;
	
	//劳动力总人数
	@Column(name = "C_LABOR")
	private int labor;
	
	//是否省级扶贫开发重点村（贫困村）
	@Column(name = "C_POORV")
	private String poorVillage;
	
	//帮扶单位名称
	@Column(name = "C_HELPUNIT")
	private String helpUnit;
	
	//是否广东名村
	@Column(name = "C_FAMOUSV")
	private String famousVillage;
	
	//广东名村批次
	@Column(name = "C_FAMOUSBATCH")
	private String famousBatch;
	
	//是否“两不具备”整村推进村
	@Column(name = "C_PUSHV")
	private String pushVillage;
	
	//“两不具备”整村推进村年度
	@Column(name = "C_PVANNUAL")
	private String pvAnnual;
	
	//上年度农民年人均纯收入
	@Column(name = "C_ANNUALINCOME")
	private double annualIncome;
	
	@Column(name = "C_ANNUALINCOME_13")
	private double annualIncome_13;
	
	@Column(name = "C_ANNUALINCOME_14")
	private double annualIncome_14;
	
	@Column(name = "C_ANNUALINCOME_15")
	private double annualIncome_15;
	
//	@Column(name = "C_ANNUALINCOME_16")
//	private double annualIncome_16;
//	
//	@Column(name = "C_ANNUALINCOME_17")
//	private double annualIncome_17;
	
	//村内道路和入户路硬底化率
	@Column(name = "C_HARDBOTTOM")
	private double hardBottom;
	
	//道路硬底化公里
	@Column(name = "C_BOTTOMDIS")
	private double bottomDis;
	
	//是否已开展过村庄垃圾、生活污水治理
	@Column(name = "C_VILLAGEMANAGE")
	private String villageManage;
	/**
	 * 7月9号  新
	 */
	//开展垃圾整治年度
	@Column(name = "C_GARBAGEANNUAL")
	private String  garbageAnnual;

	//是否通自来水
	@Column(name = "C_TAPWATER")
	private String tapWater;
	
	//通自来水时间
	@Column(name = "C_TAPANNUAL")
	private String tapAnnual;
	
	//TODO string->int
	//开展农田水利基础设施和现代渔港建设
	@Column(name = "C_WATERBASE")
	private int waterBase;
	
	//TODO string->int
	//整治小山塘、小灌区、小水坡、小泵站和小堤防[
	@Column(name = "C_SMALLWATER")
	private int smallWater;
	
	//配套建设高标准基本农田、标准鱼塘
	@Column(name = "C_FARMLAND")
	private double farmland;

	//是否已完成环境卫生整治
	@Column(name = "C_HYFIX")
	private String  hyFix;
	
	//卫生整治年度
	@Column(name = "C_HYGIENEFIX")
	private String  fixAnnual;
	
	//是否建立村保洁队伍
	@Column(name = "C_CLEANTEAM")
	private String  cleanTeam;
	
	//TODO string->int
	//保洁员
	@Column(name = "C_CLEANERS")
	private int  cleaners;
	
	//村民使用卫生厕所户数
	@Column(name = "C_TOILET")
	private int toilet;
	
	@Column(name = "C_TOILETPERCENT")
	private double toiletPercent;
	
	//是否建立污水处理设施
	@Column(name = "C_BADWATER")
	private String badWater;
	
	@Column(name = "C_BADWATERANN")
	private String badWaterAnn;
	
	/**
	 * 农村旧房整治情况
	 */
	//无价值旧村旧房拆除
	@Column(name = "C_NOVALUE")
	private int noValue;
	
	//统一拆旧建新
	@Column(name = "C_BUILDAGAIN")
	private int buildAgain;
	
	//是否编制旧房整治改造规划
	@Column(name = "C_CHANGEPLAN")
	private String changePlan;
	
	//是否提供民居住宅设计标准图
	@Column(name = "C_DESIGNPIC")
	private String designPic;
	
	//农家（乡村）旅馆
	@Column(name = "C_PLANCOUNT")
	private int planCount;
	
	@Column(name = "C_FINISHCOUNT")
	private int finishCount;
	
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
	
	//是否建立专家小组
	@Column(name = "C_EXPERTGROUP")
	private String  expertGroup;
	
	/**
	 * 村民理事会
	 * 
	 */
	//是否建立村民理事会
	@Column(name = "C_COUNCIL")
	private String  council;
	
	//年份
	@Column(name = "C_COUNCILANN")
	private String  councilAnnual;
	
	//理事会成员数
	@Column(name = "C_COUNCILMEM")
	private int  councilMember;
	
	//姓名1
	@Column(name = "C_COUNCILNAME1")
	private String  councilName1;
	
	//联系电话1
	@Column(name = "C_COUNCILHP1")
	private String  councilHP1;
	
	//姓名2
	@Column(name = "C_COUNCILNAME2")
	private String  councilName2;
	
	//联系电话2
	@Column(name = "C_COUNCILHP2")
	private String  councilHP2;
	
	//组织开展新农村建设工作情况（在新农村建设工作中发挥的作用）
	@Column(name = "C_WORKSITUATION")
	private String workSituation;
	
	//是否制定村规民约和章程
	@Column(name = "C_CONSTITU")
	private String  constitu;


	// 20150509 新添加字段
	@TransformField(writable = WriteModel.NONE)
	@Column(name = "C_COUNCILFILENAME")
	private String councilFileName;
	@TransformField(writable = WriteModel.NONE)
	@Column(name = "C_COUNCILFILEPATH")
	private String councilFilePath;
		
	
	public DirectoryEntity getDirectory() {
		return directory;
	}

	public void setDirectory(DirectoryEntity directory) {
		this.directory = directory;
	}

	public ModelAreaEntity getModelArea() {
		return modelArea;
	}

	public void setModelArea(ModelAreaEntity modelArea) {
		this.modelArea = modelArea;
	}

//	public String getIntroduce() {
//		return introduce;
//	}
//
//	public void setIntroduce(String introduce) {
//		this.introduce = introduce;
//	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(long placeId) {
		this.placeId = placeId;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

//	public String getAdminVillage() {
//		return adminVillage;
//	}
//
//	public void setAdminVillage(String adminVillage) {
//		this.adminVillage = adminVillage;
//	}

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

	public String getFamousBatch() {
		return famousBatch;
	}

	public void setFamousBatch(String famousBatch) {
		this.famousBatch = famousBatch;
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

	public String getExpertGroup() {
		return expertGroup;
	}

	public void setExpertGroup(String expertGroup) {
		this.expertGroup = expertGroup;
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

	public String getAnnual() {
		return annual;
	}

	public void setAnnual(String annual) {
		this.annual = annual;
	}

	public double getBottomDis() {
		return bottomDis;
	}

	public void setBottomDis(double bottomDis) {
		this.bottomDis = bottomDis;
	}

	public String getTapAnnual() {
		return tapAnnual;
	}

	public void setTapAnnual(String tapAnnual) {
		this.tapAnnual = tapAnnual;
	}

	public int getWaterBase() {
		return waterBase;
	}

	public void setWaterBase(int waterBase) {
		this.waterBase = waterBase;
	}

	public int getSmallWater() {
		return smallWater;
	}

	public void setSmallWater(int smallWater) {
		this.smallWater = smallWater;
	}

	public double getFarmland() {
		return farmland;
	}

	public void setFarmland(double farmland) {
		this.farmland = farmland;
	}

	public String getHyFix() {
		return hyFix;
	}

	public void setHyFix(String hyFix) {
		this.hyFix = hyFix;
	}

	public String getFixAnnual() {
		return fixAnnual;
	}

	public void setFixAnnual(String fixAnnual) {
		this.fixAnnual = fixAnnual;
	}

	public String getCleanTeam() {
		return cleanTeam;
	}

	public void setCleanTeam(String cleanTeam) {
		this.cleanTeam = cleanTeam;
	}

	public int getCleaners() {
		return cleaners;
	}

	public void setCleaners(int cleaners) {
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

	public String getWorkSituation() {
		return workSituation;
	}

	public void setWorkSituation(String workSituation) {
		this.workSituation = workSituation;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public DirectoryEntity getDirectoryImg() {
//		return directoryImg;
//	}
//
//	public void setDirectoryImg(DirectoryEntity directoryImg) {
//		this.directoryImg = directoryImg;
//	}
//
//	public DirectoryEntity getDirectoryVideo() {
//		return directoryVideo;
//	}
//
//	public void setDirectoryVideo(DirectoryEntity directoryVideo) {
//		this.directoryVideo = directoryVideo;
//	}

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

	public DirectoryEntity getDirectoryMedia() {
		return directoryMedia;
	}

	public void setDirectoryMedia(DirectoryEntity directoryMedia) {
		this.directoryMedia = directoryMedia;
	}

	public PlaceEntity getPlace() {
		return place;
	}

	public void setPlace(PlaceEntity place) {
		this.place = place;
	}

	public String getGarbageAnnual() {
		return garbageAnnual;
	}

	public void setGarbageAnnual(String garbageAnnual) {
		this.garbageAnnual = garbageAnnual;
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

	public int getFinishNatureVillageNum() {
		return finishNatureVillageNum;
	}

	public void setFinishNatureVillageNum(int finishNatureVillageNum) {
		this.finishNatureVillageNum = finishNatureVillageNum;
	}

	public int getTeasedProjectNum() {
		return teasedProjectNum;
	}

	public void setTeasedProjectNum(int teasedProjectNum) {
		this.teasedProjectNum = teasedProjectNum;
	}

	public int getTroubleshooting() {
		return troubleshooting;
	}

	public void setTroubleshooting(int troubleshooting) {
		this.troubleshooting = troubleshooting;
	}

	public int getResolvedTroubleshooting() {
		return resolvedTroubleshooting;
	}

	public void setResolvedTroubleshooting(int resolvedTroubleshooting) {
		this.resolvedTroubleshooting = resolvedTroubleshooting;
	}

//	public double getAnnualIncome_16() {
//		return annualIncome_16;
//	}
//
//	public void setAnnualIncome_16(double annualIncome_16) {
//		this.annualIncome_16 = annualIncome_16;
//	}
//
//	public double getAnnualIncome_17() {
//		return annualIncome_17;
//	}
//
//	public void setAnnualIncome_17(double annualIncome_17) {
//		this.annualIncome_17 = annualIncome_17;
//	}
	
	
	
	
}
