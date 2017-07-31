package cn.bonoon.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.support.entities.EntityScopable;

@Entity
@Table(name = "t_felicitycounty")
public class FelicityCountyEntity extends AbstractFelicityCountyEntity implements EntityScopable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3534993709033830741L;
	//县名，与place.name一致
	@Column(name = "C_NAME")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "R_PLACE_ID")
	private PlaceEntity place;

	// 所在的城市的ID，这样方便处理，而且以后这个县区归到其它市去，这里也应该记录下当时所属的市
	@Column(name = "C_CITYID")
	private long cityId;

	@Column(name = "C_CITYNAME")
	private String cityName;

	@ManyToOne
	@JoinColumn(name = "R_UNIT_ID")
	private UnitEntity unit;
	
	@Column(name = "C_DELETED")
	private boolean deleted;

	@Column(name = "C_STATUS")
	private int status;
	
	@OneToMany(mappedBy = "county")
	private List<FelicityVillageEntity> villages;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PlaceEntity getPlace() {
		return place;
	}

	public void setPlace(PlaceEntity place) {
		this.place = place;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public UnitEntity getUnit() {
		return unit;
	}

	public void setUnit(UnitEntity unit) {
		this.unit = unit;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<FelicityVillageEntity> getVillages() {
		return villages;
	}

	public void setVillages(List<FelicityVillageEntity> villages) {
		this.villages = villages;
	}

	public void sync(FelicityCountyReportEntity fcre) {
		this.constructionCharacteristic0 = fcre.constructionCharacteristic0;
		this.constructionCharacteristic1 = fcre.constructionCharacteristic1;
		this.constructionCharacteristic2 = fcre.constructionCharacteristic2;
		this.constructionCharacteristic3 = fcre.constructionCharacteristic3;
		this.constructionCharacteristic4 = fcre.constructionCharacteristic4;
		this.constructionCharacteristic5 = fcre.constructionCharacteristic5;
		this.constructionCharacteristic6 = fcre.constructionCharacteristic6;
		this.effectSolveGarbageTrue = fcre.effectSolveGarbageTrue;
		this.effectSolveGarbageFalse = fcre.effectSolveGarbageFalse;
		this.effectThroughWaterTrue = fcre.effectThroughWaterTrue;
		this.effectThroughWaterFalse = fcre.effectThroughWaterFalse;
		this.effectUniformStyleTrue = fcre.effectUniformStyleTrue;
		this.effectUniformStyleFalse = fcre.effectUniformStyleFalse;
		this.effectCleaningTeamTrue = fcre.effectCleaningTeamTrue;
		this.effectCleaningTeamFalse = fcre.effectCleaningTeamFalse;
		this.effectCouncilTrue = fcre.effectCouncilTrue;
		this.effectCouncilFalse = fcre.effectCouncilFalse;
		this.effectHardBottomTrue = fcre.effectHardBottomTrue;
		this.effectHardBottomFalse = fcre.effectHardBottomFalse;
		this.effectRemediationTrue = fcre.effectRemediationTrue;
		this.effectRemediationFalse = fcre.effectRemediationFalse;
		this.effectSewageTreatmentTrue = fcre.effectSewageTreatmentTrue;
		this.effectSewageTreatmentFalse = fcre.effectSewageTreatmentFalse;
		this.planningCompletedTrue = fcre.planningCompletedTrue;
		this.planningCompletedFalse = fcre.planningCompletedFalse;
		this.biddingCompletedTrue = fcre.biddingCompletedTrue;
		this.biddingCompletedFalse = fcre.biddingCompletedFalse;
		this.constructionArea = fcre.constructionArea;
		this.investmentBudget = fcre.investmentBudget;
		this.investmentCompleted = fcre.investmentCompleted;
		this.population = fcre.population;
		this.projectCount = fcre.projectCount;
		this.householdCount = fcre.householdCount;
		
		this.fundsCity += fcre.fundsCity;
		this.fundsCounty += fcre.fundsCounty;
		this.fundsMasses += fcre.fundsMasses;
		this.fundsOther += fcre.fundsOther;
		this.fundsProvince += fcre.fundsProvince;
		this.fundsSociety += fcre.fundsSociety;
		this.fundsTotal += fcre.fundsTotal;
		this.fundsTown += fcre.fundsTown;
		this.fundsVillage += fcre.fundsVillage;		
	}
}
