package cn.bonoon.controllers.survey;

import cn.bonoon.entities.AbstractSurveySummaryEntity;
import cn.bonoon.entities.SurveySummaryCityEntity;
import cn.bonoon.entities.SurveySummaryCountyEntity;
import cn.bonoon.kernel.support.models.AbstractItem;

public class SurveyItem extends AbstractItem{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7573770021284064275L;

	private int agriculturalHousehold;
	private int agriculturalPopulation;
	private int villageCommittee;
	private int naturalVillage;
	private int villagePlanning;
	private int unifiedStyle;
	private int hardBottom;
	private int tapWater;
	private int spcvgb;
	private int villageRenovation;
	private int cleaningTeam;
	private int rainSewageDiversion;
	private int sewageTreatment;
	private int livestockConcentratedCaptive;
	private int villagerCouncil;

	private String name;

	public SurveyItem(SurveySummaryCountyEntity ssc) {
		this.setId(ssc.getId());
		this.setName(ssc.getCounty().getName());
		sum(ssc);
	}

	public SurveyItem(SurveySummaryCityEntity ssc) {
		this.setId(-ssc.getId());
		this.setName("全市合计：");
		sum(ssc);
	}

	public void sum(AbstractSurveySummaryEntity o){
		this.agriculturalHousehold = o.getAgriculturalHousehold();
		this.agriculturalPopulation = o.getAgriculturalPopulation();
		this.villageCommittee = o.getVillageCommittee();
		this.naturalVillage = o.getNaturalVillage();
		this.villagePlanning = o.getVillagePlanning();

		this.unifiedStyle = o.getUnifiedStyle();
		this.hardBottom = o.getHardBottom();
		this.tapWater = o.getTapWater();
		this.spcvgb = o.getSpcvgb();
		this.villageRenovation = o.getVillageRenovation();
		
		this.cleaningTeam = o.getCleaningTeam();
		this.setRainSewageDiversion(o.getRainSewageDiversion());
		this.sewageTreatment = o.getSewageTreatment();
		this.livestockConcentratedCaptive = o.getLivestockConcentratedCaptive();
		this.villagerCouncil = o.getVillagerCouncil();
	}
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

	public int getRainSewageDiversion() {
		return rainSewageDiversion;
	}

	public void setRainSewageDiversion(int rainSewageDiversion) {
		this.rainSewageDiversion = rainSewageDiversion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
