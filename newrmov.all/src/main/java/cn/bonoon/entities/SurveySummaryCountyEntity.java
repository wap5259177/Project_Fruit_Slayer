package cn.bonoon.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.entities.plugins.PlaceEntity;

/**
 * 县级的调查表
 * @author jackson
 *
 */
@Entity
@Table(name = "t_sscounty")
public class SurveySummaryCountyEntity extends AbstractSurveySummaryEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 573805147756645430L;

	@ManyToOne
	@JoinColumn(name = "R_CITY_ID")
	private SurveySummaryCityEntity city;

	@ManyToOne
	@JoinColumn(name = "R_COUNTY_ID")
	private PlaceEntity county;

	public SurveySummaryCityEntity getCity() {
		return city;
	}

	public void setCity(SurveySummaryCityEntity city) {
		this.city = city;
	}

	public PlaceEntity getCounty() {
		return county;
	}

	public void setCounty(PlaceEntity county) {
		this.county = county;
	}
}
