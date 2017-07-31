package cn.bonoon.controllers.inofs;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.ObjectEditor;

public class BaseModelAreaInfo extends ObjectEditor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 407312652077939268L;

	/***
	 * 与Entity字段对应，与暂存对应
	 */
	
	private String code;
	private String name;
	
	@TransformField
	private double ruralsArea;
	@TransformField
	private int townNumber;
	@TransformField
	private int adminVN;
	@TransformField
	private int naturalVN;
	@TransformField
	private int villagersGroup;
	@TransformField
	private int popHous;
	@TransformField
	private int farmerHous;
	@TransformField
	private long sumFarmers;
	@TransformField
	private long sumTownPopu;
//	//2013城镇居民人均收入
	@TransformField
	private double ci3;
	@TransformField
	private double ci4;
	@TransformField
	private double ci5;
	@TransformField
	private double ci6;
	@TransformField
	private double ci7;
	
	//2013农民人均收入
	@TransformField
	private double vi3;
	@TransformField
	private double vi4;
	@TransformField
	private double vi5;
	@TransformField
	private double vi6;
	@TransformField
	private double vi7;
	@TransformField
	private String cityName;
	@TransformField
	private String county;
	@TransformField
	private String themeName;
	@TransformField
	private String reportAnnual;
	@TransformField
	private String batch;
	@TransformField
	private int mainBuild;
	@TransformField
	private int around ;
	@TransformField
	private int sumMAdmin;
	@TransformField
	private int sumMRural;
	@TransformField
	private int sumArAdmin;
	@TransformField
	private int sumArRural;
	@TransformField
	private int coverTown;
	@TransformField
	private double areaAcreage;
	@TransformField
	private double mainAreaAcreage;
	@TransformField
	private double aroundAreaAcreage;
	@TransformField
	private int sumHouse;
	@TransformField
	private int mainSumHouse;
	@TransformField
	private int aroundSumHouse;
	@TransformField
	private long sumP;
	@TransformField
	private long mainSumP;
	@TransformField
	private long aroundSumP;
	@TransformField
	private double lineScale;
	@TransformField
	private String startMark;
	@TransformField
	private int planMark;
	@TransformField
	private String postCount;
	@TransformField
	private double greenRoad;
	@TransformField	
	private int viewPlatform;
	@TransformField
	private int introCard;
	@TransformField
	private int greenProject;
	@TransformField
	private String areaRoad;
//	
//	@TransformField
//	@PropertyEditor(value = 3, width = 100)
//	private String contactName;
//	
//	@TransformField
//	@PropertyEditor(value = 4, width = 100)
//	private String contactPhone;
//	
//	@TransformField
//	@PropertyEditor(value = 5, width = 100)
//	@AsDateBox
//	private Date applicatAt;

//	public String getContactName() {
//		return contactName;
//	}
//
//	public void setContactName(String contactName) {
//		this.contactName = contactName;
//	}
//
//	public String getContactPhone() {
//		return contactPhone;
//	}
//
//	public void setContactPhone(String contactPhone) {
//		this.contactPhone = contactPhone;
//	}
//
//	public Date getApplicatAt() {
//		return applicatAt;
//	}
//
//	public void setApplicatAt(Date applicatAt) {
//		this.applicatAt = applicatAt;
//	}

	public double getRuralsArea() {
		return ruralsArea;
	}

	public void setRuralsArea(double ruralsArea) {
		this.ruralsArea = ruralsArea;
	}

	public int getTownNumber() {
		return townNumber;
	}

	public void setTownNumber(int townNumber) {
		this.townNumber = townNumber;
	}

	public int getAdminVN() {
		return adminVN;
	}

	public void setAdminVN(int adminVN) {
		this.adminVN = adminVN;
	}

	public int getNaturalVN() {
		return naturalVN;
	}

	public void setNaturalVN(int naturalVN) {
		this.naturalVN = naturalVN;
	}

	public int getFarmerHous() {
		return farmerHous;
	}

	public void setFarmerHous(int farmerHous) {
		this.farmerHous = farmerHous;
	}

	public long getSumFarmers() {
		return sumFarmers;
	}

	public void setSumFarmers(long sumFarmers) {
		this.sumFarmers = sumFarmers;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getReportAnnual() {
		return reportAnnual;
	}

	public void setReportAnnual(String reportAnnual) {
		this.reportAnnual = reportAnnual;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public int getMainBuild() {
		return mainBuild;
	}

	public void setMainBuild(int mainBuild) {
		this.mainBuild = mainBuild;
	}

	public int getAround() {
		return around;
	}

	public void setAround(int around) {
		this.around = around;
	}

	public int getCoverTown() {
		return coverTown;
	}

	public void setCoverTown(int coverTown) {
		this.coverTown = coverTown;
	}

	public double getAreaAcreage() {
		return areaAcreage;
	}

	public void setAreaAcreage(double areaAcreage) {
		this.areaAcreage = areaAcreage;
	}

	public int getSumHouse() {
		return sumHouse;
	}

	public void setSumHouse(int sumHouse) {
		this.sumHouse = sumHouse;
	}

	public long getSumP() {
		return sumP;
	}

	public void setSumP(long sumP) {
		this.sumP = sumP;
	}

	public int getVillagersGroup() {
		return villagersGroup;
	}

	public void setVillagersGroup(int villagersGroup) {
		this.villagersGroup = villagersGroup;
	}

	public long getSumTownPopu() {
		return sumTownPopu;
	}

	public void setSumTownPopu(long sumTownPopu) {
		this.sumTownPopu = sumTownPopu;
	}


	public double getCi3() {
		return ci3;
	}

	public void setCi3(double ci3) {
		this.ci3 = ci3;
	}

	public double getCi4() {
		return ci4;
	}

	public void setCi4(double ci4) {
		this.ci4 = ci4;
	}

	public double getCi5() {
		return ci5;
	}

	public void setCi5(double ci5) {
		this.ci5 = ci5;
	}

	public double getCi6() {
		return ci6;
	}

	public void setCi6(double ci6) {
		this.ci6 = ci6;
	}

	public double getCi7() {
		return ci7;
	}

	public void setCi7(double ci7) {
		this.ci7 = ci7;
	}

	public double getVi3() {
		return vi3;
	}

	public void setVi3(double vi3) {
		this.vi3 = vi3;
	}

	public double getVi4() {
		return vi4;
	}

	public void setVi4(double vi4) {
		this.vi4 = vi4;
	}

	public double getVi5() {
		return vi5;
	}

	public void setVi5(double vi5) {
		this.vi5 = vi5;
	}

	public double getVi6() {
		return vi6;
	}

	public void setVi6(double vi6) {
		this.vi6 = vi6;
	}

	public double getVi7() {
		return vi7;
	}

	public void setVi7(double vi7) {
		this.vi7 = vi7;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public double getLineScale() {
		return lineScale;
	}

	public void setLineScale(double lineScale) {
		this.lineScale = lineScale;
	}

	public String getStartMark() {
		return startMark;
	}

	public void setStartMark(String startMark) {
		this.startMark = startMark;
	}

	public int getPlanMark() {
		return planMark;
	}

	public void setPlanMark(int planMark) {
		this.planMark = planMark;
	}

	public String getPostCount() {
		return postCount;
	}

	public void setPostCount(String postCount) {
		this.postCount = postCount;
	}

	public double getGreenRoad() {
		return greenRoad;
	}

	public void setGreenRoad(double greenRoad) {
		this.greenRoad = greenRoad;
	}

	public int getViewPlatform() {
		return viewPlatform;
	}

	public void setViewPlatform(int viewPlatform) {
		this.viewPlatform = viewPlatform;
	}

	public int getIntroCard() {
		return introCard;
	}

	public void setIntroCard(int introCard) {
		this.introCard = introCard;
	}

	public int getGreenProject() {
		return greenProject;
	}

	public void setGreenProject(int greenProject) {
		this.greenProject = greenProject;
	}

	public String getAreaRoad() {
		return areaRoad;
	}

	public void setAreaRoad(String areaRoad) {
		this.areaRoad = areaRoad;
	}

	public int getPopHous() {
		return popHous;
	}

	public void setPopHous(int popHous) {
		this.popHous = popHous;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMainAreaAcreage() {
		return mainAreaAcreage;
	}

	public void setMainAreaAcreage(double mainAreaAcreage) {
		this.mainAreaAcreage = mainAreaAcreage;
	}

	public double getAroundAreaAcreage() {
		return aroundAreaAcreage;
	}

	public void setAroundAreaAcreage(double aroundAreaAcreage) {
		this.aroundAreaAcreage = aroundAreaAcreage;
	}

	public int getMainSumHouse() {
		return mainSumHouse;
	}

	public void setMainSumHouse(int mainSumHouse) {
		this.mainSumHouse = mainSumHouse;
	}

	public int getAroundSumHouse() {
		return aroundSumHouse;
	}

	public void setAroundSumHouse(int aroundSumHouse) {
		this.aroundSumHouse = aroundSumHouse;
	}

	public long getMainSumP() {
		return mainSumP;
	}

	public void setMainSumP(long mainSumP) {
		this.mainSumP = mainSumP;
	}

	public long getAroundSumP() {
		return aroundSumP;
	}

	public void setAroundSumP(long aroundSumP) {
		this.aroundSumP = aroundSumP;
	}

	public int getSumMAdmin() {
		return sumMAdmin;
	}

	public void setSumMAdmin(int sumMAdmin) {
		this.sumMAdmin = sumMAdmin;
	}

	public int getSumMRural() {
		return sumMRural;
	}

	public void setSumMRural(int sumMRural) {
		this.sumMRural = sumMRural;
	}

	public int getSumArAdmin() {
		return sumArAdmin;
	}

	public void setSumArAdmin(int sumArAdmin) {
		this.sumArAdmin = sumArAdmin;
	}

	public int getSumArRural() {
		return sumArRural;
	}

	public void setSumArRural(int sumArRural) {
		this.sumArRural = sumArRural;
	}
	
}
