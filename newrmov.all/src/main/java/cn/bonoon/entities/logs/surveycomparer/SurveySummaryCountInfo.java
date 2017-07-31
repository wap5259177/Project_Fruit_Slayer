package cn.bonoon.entities.logs.surveycomparer;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SurveySummaryCountInfo {
	/**
	 * 1.农业户数（户）
	 */
	@Column(name = "C_AH")
	private int agriculturalHousehold;
	/**
	 * 2.农业人口数（人）
	 */
	@Column(name = "C_AP")
	private int agriculturalPopulation;
	/**
	 * 3.建制行政村或居委会(个)   
	 */
	@Column(name = "C_VC")
	private int villageCommittee;
	/**
	 * 4.20户以上自然村（个）
	 */
	@Column(name = "C_NV")
	private int naturalVillage;
	/**
	 * 5.已完成村庄规划的自然村（条）
	 */
	@Column(name = "C_VP")
	private int villagePlanning;
	/**
	 * 6.外立面统一装饰风格风貌的自然村（条）
	 */
	@Column(name = "C_US")
	private int unifiedStyle;
	/**
	 * 7.已实现村巷道硬底化的自然村(条)
	 */
	@Column(name = "C_HB")
	private int hardBottom;
	/**
	 * 8.已实现村村通自来水的自然村（条）
	 */
	@Column(name = "C_TW")
	private int tapWater;
	/**
	 * 9.建有小公园、文化活动场所或绿化带的自然村（条）
	 * Small parks, cultural venues or green belts
	 */
	@Column(name = "C_SPCVGB")
	private int spcvgb;
	/**
	 * 10.已完成村容村貌整治的自然村（条）
	 */
	@Column(name = "C_VR")
	private int villageRenovation;
	/**
	 * 11.建有卫生保洁队伍的自然村（条）
	 */
	@Column(name = "C_CT")
	private int cleaningTeam;
	/**
	 * 12.已实行雨污分流的自然村（条）
	 */
	@Column(name = "C_RSD")
	private int rainSewageDiversion;
	/**
	 * 13.建有人工湿地、厌氧池、沼气池等处理生活污水的自然村（条）
	 */
	@Column(name = "C_ST")
	private int sewageTreatment;
	/**
	 * 14. 实行畜禽集中圈养、人畜分离的自然村（条）
	 */
	@Column(name = "C_LCC")
	private int livestockConcentratedCaptive;
	/**
	 * 15.健全村规民约、章程及村民理事会的自然村（条）
	 */
	@Column(name = "C_VRC")
	private int villagerCouncil;
	
	
	
	
	public int getAgriculturalHousehold() {
		return agriculturalHousehold;
	}
	public void setAgriculturalHousehold(int agriculturalHousehold) {
		this.agriculturalHousehold = agriculturalHousehold;
	}
	public int getAgriculturalPopulation() {
		return agriculturalPopulation;
	}
	public void setAgriculturalPopulation(int agriculturalPopulation) {
		this.agriculturalPopulation = agriculturalPopulation;
	}
	public int getVillageCommittee() {
		return villageCommittee;
	}
	public void setVillageCommittee(int villageCommittee) {
		this.villageCommittee = villageCommittee;
	}
	public int getNaturalVillage() {
		return naturalVillage;
	}
	public void setNaturalVillage(int naturalVillage) {
		this.naturalVillage = naturalVillage;
	}
	public int getVillagePlanning() {
		return villagePlanning;
	}
	public void setVillagePlanning(int villagePlanning) {
		this.villagePlanning = villagePlanning;
	}
	public int getUnifiedStyle() {
		return unifiedStyle;
	}
	public void setUnifiedStyle(int unifiedStyle) {
		this.unifiedStyle = unifiedStyle;
	}
	public int getHardBottom() {
		return hardBottom;
	}
	public void setHardBottom(int hardBottom) {
		this.hardBottom = hardBottom;
	}
	public int getTapWater() {
		return tapWater;
	}
	public void setTapWater(int tapWater) {
		this.tapWater = tapWater;
	}
	public int getSpcvgb() {
		return spcvgb;
	}
	public void setSpcvgb(int spcvgb) {
		this.spcvgb = spcvgb;
	}
	public int getVillageRenovation() {
		return villageRenovation;
	}
	public void setVillageRenovation(int villageRenovation) {
		this.villageRenovation = villageRenovation;
	}
	public int getCleaningTeam() {
		return cleaningTeam;
	}
	public void setCleaningTeam(int cleaningTeam) {
		this.cleaningTeam = cleaningTeam;
	}
	public int getRainSewageDiversion() {
		return rainSewageDiversion;
	}
	public void setRainSewageDiversion(int rainSewageDiversion) {
		this.rainSewageDiversion = rainSewageDiversion;
	}
	public int getSewageTreatment() {
		return sewageTreatment;
	}
	public void setSewageTreatment(int sewageTreatment) {
		this.sewageTreatment = sewageTreatment;
	}
	public int getLivestockConcentratedCaptive() {
		return livestockConcentratedCaptive;
	}
	public void setLivestockConcentratedCaptive(int livestockConcentratedCaptive) {
		this.livestockConcentratedCaptive = livestockConcentratedCaptive;
	}
	public int getVillagerCouncil() {
		return villagerCouncil;
	}
	public void setVillagerCouncil(int villagerCouncil) {
		this.villagerCouncil = villagerCouncil;
	}
}
