package cn.bonoon.core.felicity;

import java.text.SimpleDateFormat;

import cn.bonoon.entities.felicityVillage.FVCoreRuralEntity;
import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.entities.felicityVillage.FVPeripheralRuralEntity;

public class RuralInfo {
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	

	private Object id;
	private Object cityCountyName;
	private Object modelAreaName;
	private Object constructionArea;
	private Object households;
	private Object population;
	
	private Object crName;
	private Object crType;
	private Object prName;
	private Object prType;
	
	private Object finishedPlan;
	private Object noFinishedPlan;
	private Object finishedBid;
	private Object noFinishedBid;
	
	private Object projectNum;
	private Object projectFinishNum;
	
	
	private Object startTime;
	private Object predictFinishTime;
	private Object constructProgress;
	
	
	
	
	public RuralInfo(){}




	//片小计
	public RuralInfo(FVModelAreaReportEntity ma) {
		this.prName = ma.getPrNum();
		this.prType = "";
		this.crName = ma.getCrNum();
		this.crType = "";
			this.finishedPlan = ""+ma.getFinishedPlanNum(); 
			this.noFinishedPlan = ""+ma.getNotFinishedPlanNum();;
			this.finishedBid = ""+ma.getFinishedBidNum();
			this.noFinishedBid = ""+ma.getNotFinishedBidNum();
		this.projectNum = ma.getProjectNum();
		this.projectFinishNum = ma.getProjectFinishNum();
		this.startTime = "";
		this.predictFinishTime = "";
		this.constructProgress = "";
		
		
		this.cityCountyName = ma.getCountyReport().getPlace().getParent().getName()+ma.getCountyReport().getPlace().getName();
		this.modelAreaName = ma.getName();
		
		this.constructionArea = ma.getConstructionArea();
		this.households = ma.getHouseholds();
		this.population = ma.getPopulation();
	}




	//主体村
	public RuralInfo(FVCoreRuralEntity cr) {
		this.setId(cr.getId());
		this.crName = cr.getName();
		this.crType = cr.getType();
		this.prName = "";
		this.prType = "";
		if(cr.isFinishedPlan()){
			this.finishedPlan = "√";
			this.noFinishedPlan = "";
		}else{
			this.noFinishedPlan = "√";
			this.finishedPlan = "";
		}
		if(cr.isFinishedBid()){
			this.finishedBid = "√";
			this.noFinishedBid = "";
		}else{
			this.noFinishedBid = "√";
			this.finishedBid = "";
		}
		this.projectNum = cr.getProjectNum();
		this.projectFinishNum = cr.getProjectFinishNum();
		if(cr.getStartTime()==null){
			this.startTime = "";
		}else{
			this.startTime = sdf.format(cr.getStartTime());
		}
		if(cr.getPredictFinishTime()==null){
			this.predictFinishTime = "";
		}else{
			this.predictFinishTime = sdf.format(cr.getPredictFinishTime());
		}
		if(cr.getConstructProgress()==null||cr.getConstructProgress()==""){
			this.constructProgress  = "";
		}else{
			this.constructProgress = cr.getConstructProgress()+"%";
		}
		
	}




	//辐射村
	public RuralInfo(FVPeripheralRuralEntity pr) {
		this.setId(pr.getId());
		this.prName = pr.getName();
		this.prType = pr.getType();
		this.crName = "";
		this.crType = "";
			this.finishedPlan = "";
			this.noFinishedPlan = "";
			this.finishedBid = "";
			this.noFinishedBid = "";
		this.projectNum = pr.getProjectNum();
		this.projectFinishNum = pr.getProjectFinishNum();
		
		if(pr.getStartTime()==null){
			this.startTime = "";
		}else{
			this.startTime = sdf.format(pr.getStartTime());
		}
		if(pr.getPredictFinishTime()==null){
			this.predictFinishTime = "";
		}else{
			this.predictFinishTime = sdf.format(pr.getPredictFinishTime());
		}
		
		if(pr.getConstructProgress()==null||pr.getConstructProgress()==""){
			this.constructProgress  = "";
		}else{
			this.constructProgress = pr.getConstructProgress()+"%";
		}
	}



	//县小计
	public RuralInfo(FVMACountyReportEntity cReport) {
		this.prName = cReport.getPrNum();
		this.prType = "";
		this.crName = cReport.getCrNum();
		this.crType = "";
			this.finishedPlan = ""+cReport.getFinishedPlanNum(); 
			this.noFinishedPlan = ""+cReport.getNotFinishedPlanNum();;
			this.finishedBid = ""+cReport.getFinishedBidNum();
			this.noFinishedBid = ""+cReport.getNotFinishedBidNum();
		this.projectNum = cReport.getProjectNum();
		this.projectFinishNum = cReport.getProjectFinishNum();
		this.startTime = "";
		this.predictFinishTime = "";
		this.constructProgress = "";
		
		this.cityCountyName = "";
		this.modelAreaName = "小计";
		this.constructionArea = cReport.getConstructionArea();
		this.households = cReport.getHouseholds();
		this.population = cReport.getPopulation();
	}




	public Object getCityCountyName() {
		return cityCountyName;
	}




	public void setCityCountyName(Object cityCountyName) {
		this.cityCountyName = cityCountyName;
	}




	public Object getModelAreaName() {
		return modelAreaName;
	}




	public void setModelAreaName(Object modelAreaName) {
		this.modelAreaName = modelAreaName;
	}




	public Object getConstructionArea() {
		return constructionArea;
	}




	public void setConstructionArea(Object constructionArea) {
		this.constructionArea = constructionArea;
	}




	public Object getHouseholds() {
		return households;
	}




	public void setHouseholds(Object households) {
		this.households = households;
	}




	public Object getPopulation() {
		return population;
	}




	public void setPopulation(Object population) {
		this.population = population;
	}




	public Object getCrName() {
		return crName;
	}




	public void setCrName(Object crName) {
		this.crName = crName;
	}




	public Object getCrType() {
		return crType;
	}




	public void setCrType(Object crType) {
		this.crType = crType;
	}




	public Object getPrName() {
		return prName;
	}




	public void setPrName(Object prName) {
		this.prName = prName;
	}




	public Object getPrType() {
		return prType;
	}




	public void setPrType(Object prType) {
		this.prType = prType;
	}




	public Object getFinishedPlan() {
		return finishedPlan;
	}




	public void setFinishedPlan(Object finishedPlan) {
		this.finishedPlan = finishedPlan;
	}




	public Object getNoFinishedPlan() {
		return noFinishedPlan;
	}




	public void setNoFinishedPlan(Object noFinishedPlan) {
		this.noFinishedPlan = noFinishedPlan;
	}




	public Object getFinishedBid() {
		return finishedBid;
	}




	public void setFinishedBid(Object finishedBid) {
		this.finishedBid = finishedBid;
	}




	public Object getNoFinishedBid() {
		return noFinishedBid;
	}




	public void setNoFinishedBid(Object noFinishedBid) {
		this.noFinishedBid = noFinishedBid;
	}




	public Object getProjectNum() {
		return projectNum;
	}




	public void setProjectNum(Object projectNum) {
		this.projectNum = projectNum;
	}




	public Object getProjectFinishNum() {
		return projectFinishNum;
	}




	public void setProjectFinishNum(Object projectFinishNum) {
		this.projectFinishNum = projectFinishNum;
	}




	public Object getStartTime() {
		return startTime;
	}




	public void setStartTime(Object startTime) {
		this.startTime = startTime;
	}




	public Object getPredictFinishTime() {
		return predictFinishTime;
	}




	public void setPredictFinishTime(Object predictFinishTime) {
		this.predictFinishTime = predictFinishTime;
	}




	public Object getConstructProgress() {
		return constructProgress;
	}




	public void setConstructProgress(Object constructProgress) {
		this.constructProgress = constructProgress;
	}




	public SimpleDateFormat getSdf() {
		return sdf;
	}




	public Object getId() {
		return id;
	}




	public void setId(Object id) {
		this.id = id;
	}








	
	
}
