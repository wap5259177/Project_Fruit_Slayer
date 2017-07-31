package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.entities.logs.SuperComparer;
import cn.bonoon.kernel.support.entities.AbstractPersistable;

@Entity
@Table(name = "t_ruralexpertgroup")
public class RuralExpertGroupEntity extends AbstractPersistable{

	
	/**
	 * 专家指导组名单
	 */
	private static final long serialVersionUID = -1151061910428570374L;
	
	@ManyToOne
	@JoinColumn(name = "R_NEWRURAL_ID")
	private NewRuralEntity newRural;
	
	@ManyToOne
	@JoinColumn(name = "R_ADMINRURAL_ID")
	private AdministrationCoreRuralEntity adminRural;
	
	@Column(name = "C_NAME")
	private String  expertName;
	
	@Column(name = "C_UNITJOB")
	private String  expertJob;
	
	@Column(name = "C_PHONE")
	private String  expertPhone;
	
	@Column(name = "C_REMARK")
	private String  expertRemark;

	public NewRuralEntity getNewRural() {
		return newRural;
	}

	public void setNewRural(NewRuralEntity newRural) {
		this.newRural = newRural;
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

	public AdministrationCoreRuralEntity getAdminRural() {
		return adminRural;
	}

	public void setAdminRural(AdministrationCoreRuralEntity adminRural) {
		this.adminRural = adminRural;
	}

	
}
