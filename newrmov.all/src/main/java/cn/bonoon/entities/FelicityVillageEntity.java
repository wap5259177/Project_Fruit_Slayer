package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.support.entities.EntityScopable;

@Entity
@Table(name = "t_felicityvillage")
public class FelicityVillageEntity extends AbstractFelicityVillageEntity implements EntityScopable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 959654528446569656L;

	@ManyToOne
	@JoinColumn(name = "R_COUNTY_ID")
	private FelicityCountyEntity county;
	
	@ManyToOne
	@JoinColumn(name = "R_PLACE_ID")
	private PlaceEntity place;//村
	
	@Column(name = "C_NAME")
	private String name;//村名

	@Column(name = "C_TOWNNAME")
	private String townName;//镇名

	@Column(name = "C_NATURALVILLAGE")
	private String naturalVillage;//自然村名
	
	@Column(name = "C_CONSTYPE")
	private String constructionType;//村庄建设类型
	
	@Column(name = "C_VILLTYPE")
	private String villageType;//A为行政村 B为自然村
	
	@Column(name = "C_DELETED")
	private boolean deleted;

	public FelicityCountyEntity getCounty() {
		return county;
	}

	public void setCounty(FelicityCountyEntity county) {
		this.county = county;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConstructionType() {
		return constructionType;
	}

	public void setConstructionType(String constructionType) {
		this.constructionType = constructionType;
	}

	public String getVillageType() {
		return villageType;
	}

	public void setVillageType(String villageType) {
		this.villageType = villageType;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public PlaceEntity getPlace() {
		return place;
	}

	public void setPlace(PlaceEntity place) {
		this.place = place;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getNaturalVillage() {
		return naturalVillage;
	}

	public void setNaturalVillage(String naturalVillage) {
		this.naturalVillage = naturalVillage;
	}

	public void sync(FelicityVillageReportEntity fvr) {
		
		this.constructionCharacteristic = fvr.constructionCharacteristic;
		this.constructionFeature = fvr.constructionFeature;
		this.effectSolveGarbage = fvr.effectSolveGarbage;
		this.effectThroughWater = fvr.effectThroughWater;
		this.effectUniformStyle = fvr.effectUniformStyle;
		this.effectCleaningTeam = fvr.effectCleaningTeam;
		this.effectCouncil = fvr.effectCouncil;
		this.effectHardBottom = fvr.effectHardBottom;
		this.effectRemediation = fvr.effectRemediation;
		this.effectSewageTreatment = fvr.effectSewageTreatment;
		this.effectOther = fvr.effectOther;
		this.planningCompleted = fvr.planningCompleted;
		this.biddingCompleted = fvr.biddingCompleted;
		this.constructionArea = fvr.constructionArea;
		this.investmentBudget = fvr.investmentBudget;
		this.investmentCompleted = fvr.investmentCompleted;
		this.population = fvr.population;
		this.projectCount = fvr.projectCount;
		this.householdCount = fvr.householdCount;
		
		this.fundsCity += fvr.fundsCity;
		this.fundsCounty += fvr.fundsCounty;
		this.fundsMasses += fvr.fundsMasses;
		this.fundsOther += fvr.fundsOther;
		this.fundsProvince += fvr.fundsProvince;
		this.fundsSociety += fvr.fundsSociety;
		this.fundsTotal += fvr.fundsTotal;
		this.fundsTown += fvr.fundsTown;
		this.fundsVillage += fvr.fundsVillage;		
	}
}
