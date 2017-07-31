package cn.bonoon.core.felicity;

import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.entities.plugins.PlaceEntity;

public class AreaReportInfo {

	private Long id;
	private String cityCountyName;
	private String modelAreaName;
	private String constructionArea;
	private String households;
	private String population;
	
	private String coreRuralName;
	private String crType;
	private String peripheralRuralName;
	private String prType;
	
	private String finishedPlan;
	private String noFinishedPlan;
	private String finishedBid;
	private String noFinishedBid;
	
	private int projectNum;
	private int projectFinishNum;
	
	
	private String startTime;
	private String predictFinishTime;
	private String constructProgress;
	
	
	
	
	public AreaReportInfo(){};
	
	
	//最初是创建片区的时候,还没创建村子
	public AreaReportInfo(FVModelAreaReportEntity ma){
		PlaceEntity place = ma.getCountyReport().getPlace();
		this.id = ma.getId();
		this.cityCountyName = place.getParent().getName()+place.getName();
		this.modelAreaName = ma.getName();
		this.constructionArea = String.valueOf(ma.getConstructionArea());
		this.households = String.valueOf(ma.getHouseholds());
		this.population = String.valueOf(ma.getPopulation());
		
		
	}
	
	//有主体村
	public AreaReportInfo(FVModelAreaReportEntity ma,String cRural,String crType){
		PlaceEntity place = ma.getCountyReport().getPlace();
		this.id = ma.getId();
		this.cityCountyName = place.getParent().getName()+place.getName();
		this.modelAreaName = ma.getName();
		this.constructionArea = String.valueOf(ma.getConstructionArea());
		this.households = String.valueOf(ma.getHouseholds());
		this.population = String.valueOf(ma.getPopulation());
		
		this.coreRuralName = cRural;
		this.crType = crType;
		
	}
	
	//有主体村,有辐射村
	public AreaReportInfo(FVModelAreaReportEntity ma,String cRural,String pRural,String prType){
		this.peripheralRuralName  = pRural;
		this.prType = prType;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCityCountyName() {
		return cityCountyName;
	}


	public void setCityCountyName(String cityCountyName) {
		this.cityCountyName = cityCountyName;
	}


	public String getModelAreaName() {
		return modelAreaName;
	}


	public void setModelAreaName(String modelAreaName) {
		this.modelAreaName = modelAreaName;
	}


	public String getConstructionArea() {
		return constructionArea;
	}


	public void setConstructionArea(String constructionArea) {
		this.constructionArea = constructionArea;
	}


	public String getHouseholds() {
		return households;
	}


	public void setHouseholds(String households) {
		this.households = households;
	}


	public String getPopulation() {
		return population;
	}


	public void setPopulation(String population) {
		this.population = population;
	}


	public String getCoreRuralName() {
		return coreRuralName;
	}


	public void setCoreRuralName(String coreRuralName) {
		this.coreRuralName = coreRuralName;
	}


	public String getCrType() {
		return crType;
	}


	public void setCrType(String crType) {
		this.crType = crType;
	}


	public String getPeripheralRuralName() {
		return peripheralRuralName;
	}


	public void setPeripheralRuralName(String peripheralRuralName) {
		this.peripheralRuralName = peripheralRuralName;
	}


	public String getPrType() {
		return prType;
	}


	public void setPrType(String prType) {
		this.prType = prType;
	}


	public String getFinishedPlan() {
		return finishedPlan;
	}


	public void setFinishedPlan(String finishedPlan) {
		this.finishedPlan = finishedPlan;
	}


	public String getNoFinishedPlan() {
		return noFinishedPlan;
	}


	public void setNoFinishedPlan(String noFinishedPlan) {
		this.noFinishedPlan = noFinishedPlan;
	}


	public String getFinishedBid() {
		return finishedBid;
	}


	public void setFinishedBid(String finishedBid) {
		this.finishedBid = finishedBid;
	}


	public String getNoFinishedBid() {
		return noFinishedBid;
	}


	public void setNoFinishedBid(String noFinishedBid) {
		this.noFinishedBid = noFinishedBid;
	}


	public int getProjectNum() {
		return projectNum;
	}


	public void setProjectNum(int projectNum) {
		this.projectNum = projectNum;
	}


	public int getProjectFinishNum() {
		return projectFinishNum;
	}


	public void setProjectFinishNum(int projectFinishNum) {
		this.projectFinishNum = projectFinishNum;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getPredictFinishTime() {
		return predictFinishTime;
	}


	public void setPredictFinishTime(String predictFinishTime) {
		this.predictFinishTime = predictFinishTime;
	}


	public String getConstructProgress() {
		return constructProgress;
	}


	public void setConstructProgress(String constructProgress) {
		this.constructProgress = constructProgress;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
