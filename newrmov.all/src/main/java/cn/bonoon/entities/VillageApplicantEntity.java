package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_villageapplicant")
public class VillageApplicantEntity extends ApplicantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1545206913799380938L;

	@Column(name = "C_TOWNNAME")
	private String townName;
	/**
	 * 是否组建专业合作社
	 */
	@Column(name = "C_PC")
	private boolean professionalCooperatives;
	/**
	 * 是否成立村民理事会等自治组织
	 */
	@Column(name = "C_VC")
	private boolean villagerCouncil;

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public boolean isVillagerCouncil() {
		return villagerCouncil;
	}

	public void setVillagerCouncil(boolean villagerCouncil) {
		this.villagerCouncil = villagerCouncil;
	}

	public boolean isProfessionalCooperatives() {
		return professionalCooperatives;
	}

	public void setProfessionalCooperatives(boolean professionalCooperatives) {
		this.professionalCooperatives = professionalCooperatives;
	}
}
