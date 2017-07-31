package cn.bonoon.core.felicity;

import cn.bonoon.entities.felicityVillage.FVInvestmentInfo;
import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.entities.plugins.PlaceEntity;

public class FVTable3Info {

	private Object id;
	private Object cityCountyMaName;
	private Object totalFunds;

	private Object provinceFunds;
	private Object cityFunds;
	private Object countyFunds;
	private Object socialFunds;
	private Object massFunds;
	private Object otherFunds;
	
	private Object invested;
	
	private Object feature;
	private Object problem;
	private Object remark;
	
	public FVTable3Info(){}
	public FVTable3Info(FVMACountyReportEntity cReport) {
		this.cityCountyMaName = "小计";
		FVInvestmentInfo invs = cReport.getInvestment();
		this.totalFunds = invs.getTotalFunds();
		this.provinceFunds = invs.getProvinceFunds();
		this.cityFunds = invs.getCityFunds();
		this.countyFunds = invs.getCountyFunds();
		this.socialFunds = invs.getSocialFunds();
		this.massFunds = invs.getMassFunds();
		this.otherFunds = invs.getOtherFunds();
		
		this.invested = cReport.getInvested();
		this.feature = "";
		this.problem = "";
		this.remark = "";
	}
	public FVTable3Info(FVModelAreaReportEntity ma) {
		PlaceEntity cp = ma.getCountyReport().getPlace();
		this.cityCountyMaName = cp.getParent().getName()+cp.getName()+ma.getName();
		FVInvestmentInfo invs = ma.getInvestment();
		this.totalFunds = invs.getTotalFunds();
		this.provinceFunds = invs.getProvinceFunds();
		this.cityFunds = invs.getCityFunds();
		this.countyFunds = invs.getCountyFunds();
		this.socialFunds = invs.getSocialFunds();
		this.massFunds = invs.getMassFunds();
		this.otherFunds = invs.getOtherFunds();
		
		this.invested = ma.getInvested();
		this.feature = ma.getFeature();
		this.problem = ma.getFeature();
		this.remark = ma.getRemark();
		
		this.id = ma.getId();
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getTotalFunds() {
		return totalFunds;
	}
	public void setTotalFunds(Object totalFunds) {
		this.totalFunds = totalFunds;
	}
	public Object getProvinceFunds() {
		return provinceFunds;
	}
	public void setProvinceFunds(Object provinceFunds) {
		this.provinceFunds = provinceFunds;
	}
	public Object getCityFunds() {
		return cityFunds;
	}
	public void setCityFunds(Object cityFunds) {
		this.cityFunds = cityFunds;
	}
	public Object getCountyFunds() {
		return countyFunds;
	}
	public void setCountyFunds(Object countyFunds) {
		this.countyFunds = countyFunds;
	}
	public Object getSocialFunds() {
		return socialFunds;
	}
	public void setSocialFunds(Object socialFunds) {
		this.socialFunds = socialFunds;
	}
	public Object getMassFunds() {
		return massFunds;
	}
	public void setMassFunds(Object massFunds) {
		this.massFunds = massFunds;
	}
	public Object getOtherFunds() {
		return otherFunds;
	}
	public void setOtherFunds(Object otherFunds) {
		this.otherFunds = otherFunds;
	}
	public Object getInvested() {
		return invested;
	}
	public void setInvested(Object invested) {
		this.invested = invested;
	}
	public Object getFeature() {
		return feature;
	}
	public void setFeature(Object feature) {
		this.feature = feature;
	}
	public Object getProblem() {
		return problem;
	}
	public void setProblem(Object problem) {
		this.problem = problem;
	}
	public Object getRemark() {
		return remark;
	}
	public void setRemark(Object remark) {
		this.remark = remark;
	}
	public Object getCityCountyMaName() {
		return cityCountyMaName;
	}
	public void setCityCountyMaName(Object cityCountyMaName) {
		this.cityCountyMaName = cityCountyMaName;
	}
	
	
	
}
