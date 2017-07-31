package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.kernel.support.entities.EntityScopable;

@Entity
@Table(name = "t_fvreport")
public class FelicityVillageReportEntity extends AbstractFelicityVillageEntity implements EntityScopable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4227870442395633763L;

	@ManyToOne
	@JoinColumn(name = "R_COUNTYREPORT_ID")
	private FelicityCountyReportEntity countyReport;

	@ManyToOne
	@JoinColumn(name = "R_VILLAGE_ID")
	private FelicityVillageEntity village;
	
	@Column(name = "C_NSPLAN")
	private String nextStagePlanning;//下阶段打算

	public String getNextStagePlanning() {
		return nextStagePlanning;
	}

	public void setNextStagePlanning(String nextStagePlanning) {
		this.nextStagePlanning = nextStagePlanning;
	}

	public FelicityCountyReportEntity getCountyReport() {
		return countyReport;
	}

	public void setCountyReport(FelicityCountyReportEntity countyReport) {
		this.countyReport = countyReport;
	}

	public FelicityVillageEntity getVillage() {
		return village;
	}

	public void setVillage(FelicityVillageEntity village) {
		this.village = village;
	}
}
