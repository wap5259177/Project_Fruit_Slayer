package cn.bonoon.entities.logs.modelAreaManageModuleLogs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.logs.SuperComparer;
import cn.bonoon.kernel.support.entities.AbstractPersistable;
@Entity
@Table(name = "cp_regc", catalog = "db_newrmov_log")
public class RuralExpertGroupComparerEntity extends SuperComparer{
	public RuralExpertGroupComparerEntity(){};
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3152934399446819848L;

	public RuralExpertGroupComparerEntity(NewRuralEntity newRural,
			AdministrationCoreRuralEntity adminRural, String expertName,
			String expertJob, String expertPhone, String expertRemark) {
		super();
		this.newRural = newRural;
		this.adminRural = adminRural;
		this.expertName = expertName;
		this.expertJob = expertJob;
		this.expertPhone = expertPhone;
		this.expertRemark = expertRemark;
	}

	@ManyToOne
	@JoinColumn(name = "R_NEWRURAL_ID")
	private NewRuralEntity newRural;
	
	@ManyToOne
	@JoinColumn(name = "R_ADMINRURAL_ID")
	private AdministrationCoreRuralEntity adminRural;

	@Column(name = "C_EXPERTNAME")
	private String  expertName;
	
	@Column(name = "C_UNITJOB")
	private String  expertJob;
	
	@Column(name = "C_PHONE")
	private String  expertPhone;
	
	@Column(name = "C_REMARK")
	private String  expertRemark;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public NewRuralEntity getNewRural() {
		return newRural;
	}

	public void setNewRural(NewRuralEntity newRural) {
		this.newRural = newRural;
	}

	public AdministrationCoreRuralEntity getAdminRural() {
		return adminRural;
	}

	public void setAdminRural(AdministrationCoreRuralEntity adminRural) {
		this.adminRural = adminRural;
	}

	public String getExpertName() {
		return expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public String getExpertJob() {
		return expertJob;
	}

	public void setExpertJob(String expertJob) {
		this.expertJob = expertJob;
	}

	public String getExpertPhone() {
		return expertPhone;
	}

	public void setExpertPhone(String expertPhone) {
		this.expertPhone = expertPhone;
	}

	public String getExpertRemark() {
		return expertRemark;
	}

	public void setExpertRemark(String expertRemark) {
		this.expertRemark = expertRemark;
	}
}
