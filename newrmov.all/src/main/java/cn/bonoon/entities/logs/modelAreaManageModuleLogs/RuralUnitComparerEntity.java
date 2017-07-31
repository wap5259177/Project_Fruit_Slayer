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
@Table(name = "cp_ruc", catalog = "db_newrmov_log")
public class RuralUnitComparerEntity extends SuperComparer{
	public RuralUnitComparerEntity(){};
	public RuralUnitComparerEntity(AdministrationCoreRuralEntity adminRural,
			NewRuralEntity newRural, String unitName, String registeredAddress,
			String contactName, String unitPhone) {
		super();
		this.adminRural = adminRural;
		this.newRural = newRural;
		this.unitName = unitName;
		this.registeredAddress = registeredAddress;
		this.contactName = contactName;
		this.unitPhone = unitPhone;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7539210007132779680L;
	

	@ManyToOne
	@JoinColumn(name = "R_ADMINRURAL_ID")
	private AdministrationCoreRuralEntity adminRural;
	
	@ManyToOne
	@JoinColumn(name = "R_NEWRURAL_ID")
	private NewRuralEntity newRural;
	
	@Column(name = "C_UNITNAME")
	private String  unitName;
	
	@Column(name = "C_REGADDRESS")
	private String  registeredAddress;
	
	@Column(name = "C_CONTACTNAME")
	private String  contactName;
	
	@Column(name = "C_PHONE")
	private String  unitPhone;

	public AdministrationCoreRuralEntity getAdminRural() {
		return adminRural;
	}

	public void setAdminRural(AdministrationCoreRuralEntity adminRural) {
		this.adminRural = adminRural;
	}

	public NewRuralEntity getNewRural() {
		return newRural;
	}

	public void setNewRural(NewRuralEntity newRural) {
		this.newRural = newRural;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getUnitPhone() {
		return unitPhone;
	}

	public void setUnitPhone(String unitPhone) {
		this.unitPhone = unitPhone;
	}

	
}
