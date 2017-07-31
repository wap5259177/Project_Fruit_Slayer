package cn.bonoon.entities.informatioin;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.entities.plugins.PlaceEntity;

@Entity
@Table(name = "t_onvi")
public class CountyNaturalVillageInformationEntity extends NaturalVillageInformationEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4602359357529231440L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_CNVI_ID")
	private CityNaturalVillageInformationEntity cnvInformation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_PLACE_ID")
	private PlaceEntity place;

	public CityNaturalVillageInformationEntity getCnvInformation() {
		return cnvInformation;
	}

	public void setCnvInformation(CityNaturalVillageInformationEntity cnvInformation) {
		this.cnvInformation = cnvInformation;
	}

	public PlaceEntity getPlace() {
		return place;
	}

	public void setPlace(PlaceEntity place) {
		this.place = place;
	}
}
