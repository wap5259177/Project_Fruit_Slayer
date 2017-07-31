package cn.bonoon.entities.logs.modelAreaManageModuleLogs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.bonoon.entities.logs.SuperModelAreaComparer;

@Entity
@Table(name = "cp_arfbrc", catalog = "db_newrmov_log")
public class AdminRuralFrushBaseRuralComparerEntity extends
		SuperModelAreaComparer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3491223988667501312L;

	@Column(name = "R_NL")
	private String natrualruralListOld;
	
	@Column(name = "T_NL")
	private String natrualruralListNew;
	
	@Column(name = "S_NN")
	private int natrualruralNumOld;
	
	@Column(name = "T_NN")
	private int natrualruralNumNew;

	public String getNatrualruralListOld() {
		return natrualruralListOld;
	}

	public void setNatrualruralListOld(String natrualruralListOld) {
		this.natrualruralListOld = natrualruralListOld;
	}

	public String getNatrualruralListNew() {
		return natrualruralListNew;
	}

	public void setNatrualruralListNew(String natrualruralListNew) {
		this.natrualruralListNew = natrualruralListNew;
	}

	public int getNatrualruralNumOld() {
		return natrualruralNumOld;
	}

	public void setNatrualruralNumOld(int natrualruralNumOld) {
		this.natrualruralNumOld = natrualruralNumOld;
	}

	public int getNatrualruralNumNew() {
		return natrualruralNumNew;
	}

	public void setNatrualruralNumNew(int natrualruralNumNew) {
		this.natrualruralNumNew = natrualruralNumNew;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
