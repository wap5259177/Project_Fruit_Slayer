package cn.bonoon.controllers.inofs;

import java.util.Date;

import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.kernel.annotations.Ignore;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;

public class BaseNewRuralInfo extends AbstractRuralInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 578298484994310563L;

	private String faceView;
	private String unitProperty;
	private String expertGroup;
	private String generalPlan;
	private String areaPlan;
	private String depthPlanning;
	private String histroyDesc;
	private String ecologicalDesc;
	private String civilianDesc;
	private String tourDesc;
	private String industryDesc;
	private String fisheryDesc;
	private String otherDesc;
	private int viewSpot;
	private String viewSpot1;
	private String viewSpot2;
	private String viewSpot3;
	private String leaderName;
	private String leaderJob;
	private String leaderHP;
	private int oldProtect;
	private String faceStyle;
	private String fvAnnual;
	private String leaderName2;
	private String leaderJob2;
	private String leaderHP2;
	private String unPro2;
	private Date gpDate;
	private Date apDate;
	private Date dpDate;
	//新属性
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
	
	//需改造危房户数
	private int dangerHouse;
	
	/**
	 * 7月9号  新字段   
	 * 		(十)民生问题调查梳理去情况
	 */
	//完成自然村数
	private int finishNatureVillageNum;
	
	//梳理出项目数
	private int teasedProjectNum;	
	
	//排查出的矛盾纠纷数
	private int troubleshooting;
	
	//其中已化解数
	private int resolvedTroubleshooting;
	
	
	@TransformField(writable = WriteModel.NONE)
	private long placeId;
	private String eniPlan;
	private int eniPlanDate;
	@Ignore
	private long adminRuralId;
	public String getFaceView() {
		return faceView;
	}
	public void setFaceView(String faceView) {
		this.faceView = faceView;
	}
	public String getUnitProperty() {
		return unitProperty;
	}
	public void setUnitProperty(String unitProperty) {
		this.unitProperty = unitProperty;
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
	public String getAreaPlan() {
		return areaPlan;
	}
	public void setAreaPlan(String areaPlan) {
		this.areaPlan = areaPlan;
	}
	public String getDepthPlanning() {
		return depthPlanning;
	}
	public void setDepthPlanning(String depthPlanning) {
		this.depthPlanning = depthPlanning;
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
	public int getOldProtect() {
		return oldProtect;
	}
	public void setOldProtect(int oldProtect) {
		this.oldProtect = oldProtect;
	}
	public String getFaceStyle() {
		return faceStyle;
	}
	public void setFaceStyle(String faceStyle) {
		this.faceStyle = faceStyle;
	}
	public String getFvAnnual() {
		return fvAnnual;
	}
	public void setFvAnnual(String fvAnnual) {
		this.fvAnnual = fvAnnual;
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
	public Date getGpDate() {
		return gpDate;
	}
	public void setGpDate(Date gpDate) {
		this.gpDate = gpDate;
	}
	public Date getApDate() {
		return apDate;
	}
	public void setApDate(Date apDate) {
		this.apDate = apDate;
	}
	public Date getDpDate() {
		return dpDate;
	}
	public void setDpDate(Date dpDate) {
		this.dpDate = dpDate;
	}
	public long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(long placeId) {
		this.placeId = placeId;
	}
	
	public int getEniPlanDate() {
		return eniPlanDate;
	}
	public void setEniPlanDate(int eniPlanDate) {
		this.eniPlanDate = eniPlanDate;
	}
	public String getEniPlan() {
		return eniPlan;
	}
	public void setEniPlan(String eniPlan) {
		this.eniPlan = eniPlan;
	}

	public long getAdminRuralId() {
		return adminRuralId;
	}
	public void setAdminRuralId(long adminRuralId) {
		this.adminRuralId = adminRuralId;
	}
	
	public final void reset(AdministrationCoreRuralEntity are) {
		adminRuralId = are.getId();
		
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
	
	
	
	
}
