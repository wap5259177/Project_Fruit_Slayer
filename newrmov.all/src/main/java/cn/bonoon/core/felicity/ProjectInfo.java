package cn.bonoon.core.felicity;

import java.text.SimpleDateFormat;

import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.entities.felicityVillage.FVProjectRuralEntity;

public class ProjectInfo {
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	

	private Object id;
	
	private Object maName;
	private Object pjType;
	private Object pjName;
	private Object pjInRuralName;
	
	private Object budgetMoney;
	private Object finishMoney;
	private Object startTime;
	private Object finishTime;
	
	private Object checkTime;
	private Object checkUnit;
	
	private Object cityCountyMaName;//县(市/区)示范片名
	
	public ProjectInfo(){}
	
	
	public ProjectInfo(FVProjectRuralEntity pr){
		this.id = pr.getId();
		this.maName = pr.getModelArea().getName();
		this.pjType = pr.getProject().getType();
		this.pjName = pr.getProject().getName();
		this.pjInRuralName = pr.getName();
		this.budgetMoney = pr.getBudgetMoney();
		this.finishMoney = pr.getFinishMoney();
		this.startTime = pr.getStartTime();
		this.finishTime = pr.getFinishTime();
		this.checkTime = pr.getCheckTime();
		this.checkUnit = pr.getCheckUnit();
		
		//导出显示用
		FVMACountyReportEntity cReport = pr.getModelArea().getCountyReport();
		this.cityCountyMaName = cReport.getPlace().getParent().getName()+cReport.getPlace().getName()+this.maName;
	}
	public ProjectInfo(FVMACountyReportEntity cReport) {
		this.maName = "小计";
		this.pjType = "";
		this.pjName = cReport.getProjectAreaCount();
		this.pjInRuralName = "";
		this.budgetMoney = cReport.getBudgetMoney();
		this.finishMoney = cReport.getFinishMoney();
		this.startTime = "";
		this.finishTime = "";
		this.checkTime = "";
		this.checkUnit = "";
		
//		this.cityCountyMaName = cReport.getPlace().getParent().getName()+cReport.getPlace().getName()+ma.getName();
		this.cityCountyMaName = "小计";
	}


	public ProjectInfo(FVModelAreaReportEntity ma) {
		this.maName = "小计";
		this.pjType = "";
		this.pjName = ma.getProjectAreaCount();
		this.pjInRuralName = "";
		this.budgetMoney = ma.getBudgetMoney();
		this.finishMoney = ma.getFinishMoney();
		this.startTime = "";
		this.finishTime = "";
		this.checkTime = "";
		this.checkUnit = "";
	}


	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getPjType() {
		return pjType;
	}
	public void setPjType(Object pjType) {
		this.pjType = pjType;
	}
	public Object getPjName() {
		return pjName;
	}
	public void setPjName(Object pjName) {
		this.pjName = pjName;
	}
	public Object getPjInRuralName() {
		return pjInRuralName;
	}
	public void setPjInRuralName(Object pjInRuralName) {
		this.pjInRuralName = pjInRuralName;
	}
	public Object getBudgetMoney() {
		return budgetMoney;
	}
	public void setBudgetMoney(Object budgetMoney) {
		this.budgetMoney = budgetMoney;
	}
	public Object getFinishMoney() {
		return finishMoney;
	}
	public void setFinishMoney(Object finishMoney) {
		this.finishMoney = finishMoney;
	}
	public Object getStartTime() {
		return startTime;
	}
	public void setStartTime(Object startTime) {
		this.startTime = startTime;
	}
	public Object getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Object finishTime) {
		this.finishTime = finishTime;
	}
	public Object getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Object checkTime) {
		this.checkTime = checkTime;
	}
	public Object getCheckUnit() {
		return checkUnit;
	}
	public void setCheckUnit(Object checkUnit) {
		this.checkUnit = checkUnit;
	}
	public SimpleDateFormat getSdf() {
		return sdf;
	}


	public Object getMaName() {
		return maName;
	}


	public void setMaName(Object maName) {
		this.maName = maName;
	}


	public Object getCityCountyMaName() {
		return cityCountyMaName;
	}


	public void setCityCountyMaName(Object cityCountyMaName) {
		this.cityCountyMaName = cityCountyMaName;
	}
	
	
	
	
	
	








	
	
}
