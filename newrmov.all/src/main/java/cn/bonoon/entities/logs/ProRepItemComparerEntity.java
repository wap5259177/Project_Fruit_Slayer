package cn.bonoon.entities.logs;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cp_pric", catalog = "db_newrmov_log")
public class ProRepItemComparerEntity extends SuperComparer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8715592641080724552L;

	@Column(name = "S_LABOURCOUNT")
	private int oldLabourCount;
	
	@Column(name = "T_LABOURCOUNT")
	private int newLabourCount;
	
	@Column(name = "S_SPEND")
	private double oldSpend;
	
	@Column(name = "T_SPEND")
	private double newSpend;

	@Embedded
	InvestmentComparer icp = new InvestmentComparer();

	public int getOldLabourCount() {
		return oldLabourCount;
	}

	public void setOldLabourCount(int oldLabourCount) {
		this.oldLabourCount = oldLabourCount;
	}

	public double getOldSpend() {
		return oldSpend;
	}

	public void setOldSpend(double oldSpend) {
		this.oldSpend = oldSpend;
	}

	public int getNewLabourCount() {
		return newLabourCount;
	}

	public void setNewLabourCount(int newLabourCount) {
		this.newLabourCount = newLabourCount;
	}

	public double getNewSpend() {
		return newSpend;
	}

	public void setNewSpend(double newSpend) {
		this.newSpend = newSpend;
	}

	public InvestmentComparer getIcp() {
		return icp;
	}

	public void setIcp(InvestmentComparer icp) {
		this.icp = icp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
