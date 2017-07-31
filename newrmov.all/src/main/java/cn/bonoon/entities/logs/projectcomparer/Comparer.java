package cn.bonoon.entities.logs.projectcomparer;

import javax.persistence.Column;
import javax.persistence.Embedded;

import cn.bonoon.entities.InvestmentInfo;

public class Comparer {
	//投工数
	@Column(name = "C_LABOURCOUNT")
	private int labourCount;

	//折资额
	@Column(name = "C_SPEND")
	private double spend;

	@Embedded
	private InvestmentInfo info = new InvestmentInfo();

	public int getLabourCount() {
		return labourCount;
	}

	public void setLabourCount(int labourCount) {
		this.labourCount = labourCount;
	}

	public double getSpend() {
		return spend;
	}

	public void setSpend(double spend) {
		this.spend = spend;
	}

	public InvestmentInfo getInfo() {
		return info;
	}

	public void setInfo(InvestmentInfo info) {
		this.info = info;
	}


	public boolean valueEquals(Comparer other) {

		if(info.getCityFunds() != other.info.getCityFunds()) return false;
		if(info.getCountyFunds() != other.info.getCountyFunds()) return false;
		if(info.getLocalFunds() != other.info.getLocalFunds()) return false;
		if(info.getMassFunds() != other.info.getMassFunds()) return false;
		if(info.getOtherFunds() != other.info.getOtherFunds()) return false;
		if(info.getProvinceFunds()!= other.info.getProvinceFunds()) return false;
		if(info.getSocialFunds() != other.info.getSocialFunds()) return false;
		if(info.getSpecialFunds() != other.info.getSpecialFunds()) return false;
		if(info.getStateFunds() != other.info.getStateFunds()) return false;
		if(info.getTotalFunds() != other.info.getTotalFunds()) return false;
		if(labourCount != other.labourCount) return false;
		if(spend != other.spend) return false;
		return true;
	}
}
