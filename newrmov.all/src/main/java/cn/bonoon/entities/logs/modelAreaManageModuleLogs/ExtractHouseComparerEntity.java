package cn.bonoon.entities.logs.modelAreaManageModuleLogs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.bonoon.entities.logs.SuperComparer;

@Entity
@Table(name = "cp_ehc", catalog = "db_newrmov_log")
public class ExtractHouseComparerEntity extends SuperComparer{

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 823986981918365355L;
	public ExtractHouseComparerEntity(){};
	public ExtractHouseComparerEntity(int mainSumHouseOld, int mainSumPOld,
			int aroundSumHouseOld, int aroundSumPOld, int mainSumHouseNow,
			int mainSumPNow, int aroundSumHouseNow, int aroundSumPNow) {
		super();
		this.mainSumHouseOld = mainSumHouseOld;
		this.mainSumPOld = mainSumPOld;
		this.aroundSumHouseOld = aroundSumHouseOld;
		this.aroundSumPOld = aroundSumPOld;
		this.mainSumHouseNow = mainSumHouseNow;
		this.mainSumPNow = mainSumPNow;
		this.aroundSumHouseNow = aroundSumHouseNow;
		this.aroundSumPNow = aroundSumPNow;
	}
	@Column(name = "S_MSH")
	int mainSumHouseOld;
	@Column(name = "S_MSP")
	int mainSumPOld;
	@Column(name = "S_ASH")
	int aroundSumHouseOld;
	@Column(name = "S_ASP")
	int aroundSumPOld;
	
	@Column(name = "T_MSH")
	int mainSumHouseNow;
	@Column(name = "T_MSP")
	int mainSumPNow;
	@Column(name = "T_ASH")
	int aroundSumHouseNow;
	@Column(name = "T_ASP")
	int aroundSumPNow;
	public int getMainSumHouseOld() {
		return mainSumHouseOld;
	}
	public void setMainSumHouseOld(int mainSumHouseOld) {
		this.mainSumHouseOld = mainSumHouseOld;
	}
	public int getMainSumPOld() {
		return mainSumPOld;
	}
	public void setMainSumPOld(int mainSumPOld) {
		this.mainSumPOld = mainSumPOld;
	}
	public int getAroundSumHouseOld() {
		return aroundSumHouseOld;
	}
	public void setAroundSumHouseOld(int aroundSumHouseOld) {
		this.aroundSumHouseOld = aroundSumHouseOld;
	}
	public int getAroundSumPOld() {
		return aroundSumPOld;
	}
	public void setAroundSumPOld(int aroundSumPOld) {
		this.aroundSumPOld = aroundSumPOld;
	}
	public int getMainSumHouseNow() {
		return mainSumHouseNow;
	}
	public void setMainSumHouseNow(int mainSumHouseNow) {
		this.mainSumHouseNow = mainSumHouseNow;
	}
	public int getMainSumPNow() {
		return mainSumPNow;
	}
	public void setMainSumPNow(int mainSumPNow) {
		this.mainSumPNow = mainSumPNow;
	}
	public int getAroundSumHouseNow() {
		return aroundSumHouseNow;
	}
	public void setAroundSumHouseNow(int aroundSumHouseNow) {
		this.aroundSumHouseNow = aroundSumHouseNow;
	}
	public int getAroundSumPNow() {
		return aroundSumPNow;
	}
	public void setAroundSumPNow(int aroundSumPNow) {
		this.aroundSumPNow = aroundSumPNow;
	}
	
}
