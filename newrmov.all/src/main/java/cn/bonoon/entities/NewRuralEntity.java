package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 新农村（核心村）----changeTo : 自然村---
 * 
 * @author jackson
 * 
 */
@Entity
public class NewRuralEntity extends BaseRuralEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6979519855027576270L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_ADMINRURAL_ID")
	private AdministrationCoreRuralEntity adminRural;
	

	@Embedded
	private RuralNeedFinishInfo needFinish = new RuralNeedFinishInfo();

	
	/**
	 * 1月29号字段(新)
	 * 
	 */
	// 人文历史
	@Column(name = "C_HISDESC")
	private String histroyDesc;

	// 自然生态
	@Column(name = "C_ECODESC")
	private String ecologicalDesc;

	// 民居风貌
	@Column(name = "C_CIVDESC")
	private String civilianDesc;

	// 乡村旅游
	@Column(name = "C_TOURDESC")
	private String tourDesc;

	// 特色产业
	@Column(name = "C_INDDESC")
	private String industryDesc;

	// 渔业渔港
	@Column(name = "C_FISHDESC")
	private String fisheryDesc;

	// 其他
	@Column(name = "C_OTHERDESC")
	private String otherDesc;

	// 古建筑保护
	@Column(name = "C_OLDPROTECT")
	private int oldProtect;

	// 是否统一民居外立面风貌
	@Column(name = "C_FACEVIEW")
	private String faceView;

	@Column(name = "C_FACESTYLE")
	private String faceStyle;

	@Column(name = "C_FVANNUAL")
	private String fvAnnual;

	// 现有的或正在打造的旅游景点
	@Column(name = "C_VIEWSPOT")
	private int viewSpot;

	@Column(name = "C_VIEWSPOT1")
	private String viewSpot1;

	@Column(name = "C_VIEWSPOT2")
	private String viewSpot2;

	@Column(name = "C_VIEWSPOT3")
	private String viewSpot3;

	
	/**
	 * 挂点县领导
	 * 
	 */
	@Column(name = "C_LEADERNAME")
	private String leaderName;

	@Column(name = "C_LEADERJOB")
	private String leaderJob;

	@Column(name = "C_LEADERHP")
	private String leaderHP;

	@Column(name = "C_UNITPROPERTY")
	private String unitProperty;

	@Column(name = "C_LEADERNAME2")
	private String leaderName2;

	@Column(name = "C_LEADERJOB2")
	private String leaderJob2;

	@Column(name = "C_LEADERHP2")
	private String leaderHP2;

	@Column(name = "C_UNPRO2")
	private String unPro2;

	/**
	 * 
	 * 规划进展
	 */
	// 是否完成总体规划
	@Column(name = "C_GENERALPLAN")
	private String generalPlan;

	@Temporal(TemporalType.DATE)
	@Column(name = "C_GPDATE")
	private Date gpDate;

	// 是否完成连线连片规划
	@Column(name = "C_AREAPLAN")
	private String areaPlan;

	@Temporal(TemporalType.DATE)
	@Column(name = "C_APDATE")
	private Date apDate;

	// 是否完成村庄深度规划设计（含节点规划设计）
	@Column(name = "C_DEPTHPLANNING")
	private String depthPlanning;

	@Temporal(TemporalType.DATE)
	@Column(name = "C_DPDATE")
	private Date dpDate;
	
	//TODO 20150409 新添加的字段
	@Column(name = "C_WGCOUNT")
	private int workGroupCount;
	@Column(name = "C_EGCOUNT")
	private int expertGroupCount;
	@Column(name = "C_RUCOUNT")
	private int unitCount;
	
	public int getWorkGroupCount() {
		return workGroupCount;
	}

	public void setWorkGroupCount(int workGroupCount) {
		this.workGroupCount = workGroupCount;
	}

	public int getExpertGroupCount() {
		return expertGroupCount;
	}

	public void setExpertGroupCount(int expertGroupCount) {
		this.expertGroupCount = expertGroupCount;
	}

	public int getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(int unitCount) {
		this.unitCount = unitCount;
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

	public String getUnitProperty() {
		return unitProperty;
	}

	public void setUnitProperty(String unitProperty) {
		this.unitProperty = unitProperty;
	}

	public String getFaceView() {
		return faceView;
	}

	public void setFaceView(String faceView) {
		this.faceView = faceView;
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

	public String getUnPro2() {
		return unPro2;
	}

	public void setUnPro2(String unPro2) {
		this.unPro2 = unPro2;
	}

	public AdministrationCoreRuralEntity getAdminRural() {
		return adminRural;
	}

	public void setAdminRural(AdministrationCoreRuralEntity adminRural) {
		this.adminRural = adminRural;
	}

	public RuralNeedFinishInfo getNeedFinish() {
		return needFinish;
	}

	public void setNeedFinish(RuralNeedFinishInfo needFinish) {
		this.needFinish = needFinish;
	}
	
	public void parseNeedFinish(RuralNeedFinishInfo rnf){
		if(null == needFinish) needFinish = new RuralNeedFinishInfo();
		needFinish.parseFinish(rnf);
	}

	public static int __checkSetNf(int value, int oldValue){
		int result = oldValue;
		if(value==1)result = 1;
		if(value==2)result = 1;
		return result;
	}

}
