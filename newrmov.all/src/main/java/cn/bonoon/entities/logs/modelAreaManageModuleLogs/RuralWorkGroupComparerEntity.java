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
@Table(name = "cp_rwgc", catalog = "db_newrmov_log")
public class RuralWorkGroupComparerEntity extends SuperComparer{
	public RuralWorkGroupComparerEntity(){};
	public RuralWorkGroupComparerEntity(NewRuralEntity newRural,
			AdministrationCoreRuralEntity adminRural, String workName,
			String unitJob, String workPhone, String workRemark) {
		super();
		this.newRural = newRural;
		this.adminRural = adminRural;
		this.workName = workName;
		this.unitJob = unitJob;
		this.workPhone = workPhone;
		this.workRemark = workRemark;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7029917231441400468L;

	@ManyToOne
	@JoinColumn(name = "R_NEWRURAL_ID")
	private NewRuralEntity newRural;
	
	@ManyToOne
	@JoinColumn(name = "R_ADMINRURAL_ID")
	private AdministrationCoreRuralEntity adminRural;
	
	@Column(name = "C_WORKNAME")
	private String  workName;
	
	@Column(name = "C_UNITJOB")
	private String  unitJob;
	
	@Column(name = "C_PHONE")
	private String  workPhone;
	
	@Column(name = "C_REMARK")
	private String  workRemark;

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

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getUnitJob() {
		return unitJob;
	}

	public void setUnitJob(String unitJob) {
		this.unitJob = unitJob;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getWorkRemark() {
		return workRemark;
	}

	public void setWorkRemark(String workRemark) {
		this.workRemark = workRemark;
	}

	
}