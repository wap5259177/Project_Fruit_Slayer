package cn.bonoon.controllers.area.pendingaudit;

import cn.bonoon.controllers.area.ModelAreaDetail;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormUpdate;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@FormUpdate(value = 3, headWidth = 100, width = 140)
@InsertCell(value = "applicant/modelarea-audit.vm", type = EmbedType.PARSE)
@WithDialog(initializer = ModelAreaAuditorInitializer.class, height = 500, width = 780)
@DialogDefaultButton(buttonName = "通过审核", functionBody = "if(!confirm('是否确定通过审核？')){return false;}", otherButtonName = "驳回", otherParameterName = "applicant-reject", otherParameterValue = "true", otherFunctionBody = "if(!confirm('是否确定要驳回？')){return false;}")
public class ModelAreaAuditor extends ModelAreaDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3873084286653203678L;

	@TransformField(writable = WriteModel.NONE)
	private String cityName;
	@TransformField(writable = WriteModel.NONE)
	private double ruralsArea;
	@TransformField(writable = WriteModel.NONE)
	private int townNumber;
	@TransformField(writable = WriteModel.NONE)
	private int adminVN;
	@TransformField(writable = WriteModel.NONE)
	private int naturalVN;
	@TransformField(writable = WriteModel.NONE)
	private int farmerHous;
	@TransformField(writable = WriteModel.NONE)
	private long sumFarmers;
	@TransformField(writable = WriteModel.NONE)
	private Long lyIncome;
	@TransformField(writable = WriteModel.NONE)
	private String county;
	@TransformField(writable = WriteModel.NONE)
	private String reportAnnual;
	@TransformField(writable = WriteModel.NONE)
	private int batch;
	@TransformField(writable = WriteModel.NONE)
	private int mainBuild;
	@TransformField(writable = WriteModel.NONE)
	private int around;
	@TransformField(writable = WriteModel.NONE)
	private int sumMAdmin;
	@TransformField(writable = WriteModel.NONE)
	private int sumMRural;
	@TransformField(writable = WriteModel.NONE)
	private int sumArAdmin;
	@TransformField(writable = WriteModel.NONE)
	private int sumArRural;
	
	@TransformField(writable = WriteModel.NONE)
	private int coverTown;
	@TransformField(writable = WriteModel.NONE)
	private double areaAcreage;
	@TransformField(writable = WriteModel.NONE)
	private double mainAreaAcreage;
	@TransformField(writable = WriteModel.NONE)
	private double aroundAreaAcreage;
	@TransformField(writable = WriteModel.NONE)
	private int sumHouse;
	@TransformField(writable = WriteModel.NONE)
	private int mainSumHouse;
	@TransformField(writable = WriteModel.NONE)
	private int aroundSumHouse;
	@TransformField(writable = WriteModel.NONE)
	private long sumP;
	@TransformField(writable = WriteModel.NONE)
	private long mainSumP;
	@TransformField(writable = WriteModel.NONE)
	private long aroundSumP;
	@TransformField(writable = WriteModel.NONE)
	private long population;

	@TransformField(writable = WriteModel.NONE)
	private int villagersGroup;

	@TransformField(writable = WriteModel.NONE)
	private int popHous;

	@TransformField(writable = WriteModel.NONE)
	private long sumTownPopu;

	@TransformField(writable = WriteModel.NONE)
	private double ci3;

	@TransformField(writable = WriteModel.NONE)
	private double ci4;

	@TransformField(writable = WriteModel.NONE)
	private double ci5;

	@TransformField(writable = WriteModel.NONE)
	private double ci6;

	@TransformField(writable = WriteModel.NONE)
	private double ci7;

	@TransformField(writable = WriteModel.NONE)
	private double vi3;

	@TransformField(writable = WriteModel.NONE)
	private double vi4;

	@TransformField(writable = WriteModel.NONE)
	private double vi5;

	@TransformField(writable = WriteModel.NONE)
	private double vi6;

	@TransformField(writable = WriteModel.NONE)
	private double vi7;

	@TransformField(writable = WriteModel.NONE)
	private String themeName;

	@TransformField(writable = WriteModel.NONE)
	private double lineScale;

	@TransformField(writable = WriteModel.NONE)
	private int planMark;

	@TransformField(writable = WriteModel.NONE)
	private String postCount;

	@TransformField(writable = WriteModel.NONE)
	private double greenRoad;

	@TransformField(writable = WriteModel.NONE)
	private int viewPlatform;

	@TransformField(writable = WriteModel.NONE)
	private int introCard;

	@TransformField(writable = WriteModel.NONE)
	private int greenProject;

	@TransformField(writable = WriteModel.NONE)
	private String areaRoad;

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public double getRuralsArea() {
		return ruralsArea;
	}

	public void setRuralsArea(double ruralsArea) {
		this.ruralsArea = ruralsArea;
	}

	public int getTownNumber() {
		return townNumber;
	}

	public int getVillagersGroup() {
		return villagersGroup;
	}

	public void setVillagersGroup(int villagersGroup) {
		this.villagersGroup = villagersGroup;
	}

	public int getPopHous() {
		return popHous;
	}

	public void setPopHous(int popHous) {
		this.popHous = popHous;
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

	public Long getLyIncome() {
		return lyIncome;
	}

	public void setLyIncome(Long lyIncome) {
		this.lyIncome = lyIncome;
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

	//
	// public int getBatch() {
	// return batch;
	// }

	public void setBatch(int batch) {
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