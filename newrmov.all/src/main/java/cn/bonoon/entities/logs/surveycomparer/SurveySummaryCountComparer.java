package cn.bonoon.entities.logs.surveycomparer;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import cn.bonoon.entities.SurveySummaryCityEntity;

@Embeddable
public class SurveySummaryCountComparer {
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="agriculturalHousehold", column=@Column(name = "S_AH")),
		@AttributeOverride(name="agriculturalPopulation", column=@Column(name = "S_AP")),
		@AttributeOverride(name="villageCommittee", column=@Column(name = "S_VC")),
		@AttributeOverride(name="naturalVillage", column=@Column(name = "S_NV")),
		@AttributeOverride(name="villagePlanning", column=@Column(name = "S_VP")),
		@AttributeOverride(name="unifiedStyle", column=@Column(name = "S_US")),
		@AttributeOverride(name="hardBottom", column=@Column(name = "S_HB")),
		@AttributeOverride(name="tapWater", column=@Column(name = "S_TW")),
		@AttributeOverride(name="spcvgb", column=@Column(name = "S_SPCVGB")),
		@AttributeOverride(name="villageRenovation", column=@Column(name = "S_VR")),
		
		@AttributeOverride(name="cleaningTeam", column=@Column(name = "S_CT")),
		@AttributeOverride(name="rainSewageDiversion", column=@Column(name = "S_RSD")),
		@AttributeOverride(name="sewageTreatment", column=@Column(name = "S_ST")),
		@AttributeOverride(name="livestockConcentratedCaptive", column=@Column(name = "S_LCC")),
		@AttributeOverride(name="villagerCouncil", column=@Column(name = "S_VRC"))
	})
	private SurveySummaryCountInfo source = new SurveySummaryCountInfo();

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="agriculturalHousehold", column=@Column(name = "T_AH")),
		@AttributeOverride(name="agriculturalPopulation", column=@Column(name = "T_AP")),
		@AttributeOverride(name="villageCommittee", column=@Column(name = "T_VC")),
		@AttributeOverride(name="naturalVillage", column=@Column(name = "T_NV")),
		@AttributeOverride(name="villagePlanning", column=@Column(name = "T_VP")),
		@AttributeOverride(name="unifiedStyle", column=@Column(name = "T_US")),
		@AttributeOverride(name="hardBottom", column=@Column(name = "T_HB")),
		@AttributeOverride(name="tapWater", column=@Column(name = "T_TW")),
		@AttributeOverride(name="spcvgb", column=@Column(name = "T_SPCVGB")),
		@AttributeOverride(name="villageRenovation", column=@Column(name = "T_VR")),
		
		@AttributeOverride(name="cleaningTeam", column=@Column(name = "T_CT")),
		@AttributeOverride(name="rainSewageDiversion", column=@Column(name = "T_RSD")),
		@AttributeOverride(name="sewageTreatment", column=@Column(name = "T_ST")),
		@AttributeOverride(name="livestockConcentratedCaptive", column=@Column(name = "T_LCC")),
		@AttributeOverride(name="villagerCouncil", column=@Column(name = "T_VRC"))
	})
	private SurveySummaryCountInfo target = new SurveySummaryCountInfo();

	

	public SurveySummaryCountInfo getSource() {
		return source;
	}



	public void setSource(SurveySummaryCityEntity source) {
		this.source.setAgriculturalHousehold(source.getAgriculturalHousehold());
		this.source.setAgriculturalPopulation(source.getAgriculturalPopulation());
		this.source.setVillageCommittee(source.getVillageCommittee());
		this.source.setNaturalVillage(source.getNaturalVillage());
		
		this.source.setVillagePlanning(source.getVillagePlanning());
		this.source.setUnifiedStyle(source.getUnifiedStyle());
		this.source.setHardBottom(source.getHardBottom());
		this.source.setTapWater(source.getTapWater());
		
		this.source.setSpcvgb(source.getSpcvgb());
		this.source.setVillageRenovation(source.getVillageRenovation());
		this.source.setCleaningTeam(source.getCleaningTeam());
		this.source.setRainSewageDiversion(source.getRainSewageDiversion());
		
		this.source.setSewageTreatment(source.getSewageTreatment());
		this.source.setVillageRenovation(source.getVillageRenovation());
		this.source.setCleaningTeam(source.getCleaningTeam());
		this.source.setLivestockConcentratedCaptive(source.getLivestockConcentratedCaptive());
		this.source.setVillagerCouncil(source.getVillagerCouncil());
	}



	public SurveySummaryCountInfo getTarget() {
		return target;
	}



	public void setTarget(SurveySummaryCityEntity target) {
		this.target.setAgriculturalHousehold(target.getAgriculturalHousehold());
		this.target.setAgriculturalPopulation(target.getAgriculturalPopulation());
		this.target.setVillageCommittee(target.getVillageCommittee());
		this.target.setNaturalVillage(target.getNaturalVillage());
		
		this.target.setVillagePlanning(target.getVillagePlanning());
		this.target.setUnifiedStyle(target.getUnifiedStyle());
		this.target.setHardBottom(target.getHardBottom());
		this.target.setTapWater(target.getTapWater());
		
		this.target.setSpcvgb(target.getSpcvgb());
		this.target.setVillageRenovation(target.getVillageRenovation());
		this.target.setCleaningTeam(target.getCleaningTeam());
		this.target.setRainSewageDiversion(target.getRainSewageDiversion());
		
		this.target.setSewageTreatment(target.getSewageTreatment());
		this.target.setVillageRenovation(target.getVillageRenovation());
		this.target.setCleaningTeam(target.getCleaningTeam());
		this.target.setLivestockConcentratedCaptive(target.getLivestockConcentratedCaptive());
		this.target.setVillagerCouncil(target.getVillagerCouncil());
	}
}
