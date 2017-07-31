package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 非主体村
 * 
 * @author jackson
 * 
 */
@Entity
public class PeripheralRuralEntity extends BaseRuralEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5343079600482903692L;

	/**
	 * 这一个非主体村是由哪个核心村带动起来的
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_NEWRURAL_ID")
	private NewRuralEntity newRural;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_NADMINRURAL_ID")
	private AdministrationUncoreRuralEntity adminRural;

	// 是否编制村庄环境整治规划
	@Column(name = "C_ENIPLAN")
	private String eniPlan;

	@Column(name = "C_ENIPLANDATE")
	private int eniPlanDate;

	public NewRuralEntity getNewRural() {
		return newRural;
	}

	public void setNewRural(NewRuralEntity newRural) {
		this.newRural = newRural;
	}

	public String getEniPlan() {
		return eniPlan;
	}

	public void setEniPlan(String eniPlan) {
		this.eniPlan = eniPlan;
	}public int getEniPlanDate() {
		return eniPlanDate;
	}

	public void setEniPlanDate(int eniPlanDate) {
		this.eniPlanDate = eniPlanDate;
	}

	public AdministrationUncoreRuralEntity getAdminRural() {
		return adminRural;
	}

	public void setAdminRural(AdministrationUncoreRuralEntity adminRural) {
		this.adminRural = adminRural;
	}

}
