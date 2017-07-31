package cn.bonoon.controllers.survey;

import cn.bonoon.entities.AbstractSurveySummaryEntity;
import cn.bonoon.entities.SurveySummaryCityEntity;
import cn.bonoon.entities.SurveySummaryCountyEntity;
import cn.bonoon.entities.SurveySummaryProvinceEntity;
import cn.bonoon.kernel.support.models.AbstractAjaxNode;

public class SurveyNode extends AbstractAjaxNode{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7747397938148881767L;
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
	private int status = -1;
	private String oldname;
	public SurveyNode(SurveySummaryCountyEntity ssc){
		this.setName(ssc.getCounty().getName());
		this.setId(-ssc.getId());
		sum(ssc);
	}
	public SurveyNode(SurveySummaryCityEntity ssc){
		this.setName(ssc.getCityName());
		this.setId(ssc.getId());
		this.status = ssc.getStatus();
		if(status == 0){
			this.name += "<font color='red' style='font-size:12px;'>(未开始)</font>";
		}else if(status == 1){
			this.name += "<font color='blue' style='font-size:12px;'>(已提交)</font>";
		}else if(status == 3){
			this.name += "<font color='red' style='font-size:12px;'>(已排除)</font>";
		}else if(status == 2){
			this.name += "<font color='green' style='font-size:12px;'>(正在填报)</font>";
		}
		sum(ssc);
		if(status != 3){
			int cc = ssc.getCountyCount();
			if(cc > 0){
				this.setState(CLOSED);
				this.setSize(cc);
			}
		}
	}
	public SurveyNode(SurveySummaryProvinceEntity ssp) {
		this.setName("全省统计");
		this.setId(0L);
		sum(ssp);
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
		this.oldname = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getOldname() {
		return oldname;
	}
	public void setOldname(String oldname) {
		this.oldname = oldname;
	}
}
