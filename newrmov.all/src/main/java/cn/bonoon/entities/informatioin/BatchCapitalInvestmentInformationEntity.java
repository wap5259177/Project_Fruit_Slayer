package cn.bonoon.entities.informatioin;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.entities.plugins.PlaceEntity;

@Entity
@Table(name = "t_bcii")
public class BatchCapitalInvestmentInformationEntity extends CapitalInvestmentInformationEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1801728532842137294L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_CCII_ID")
	private CityCapitalInvestmentInformationEntity cciInformation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_PLACE_ID")
	private PlaceEntity place;

	public CityCapitalInvestmentInformationEntity getCciInformation() {
		return cciInformation;
	}

	public void setCciInformation(CityCapitalInvestmentInformationEntity cciInformation) {
		this.cciInformation = cciInformation;
	}

	public PlaceEntity getPlace() {
		return place;
	}

	public void setPlace(PlaceEntity place) {
		this.place = place;
	}
}
