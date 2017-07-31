package cn.bonoon.core.project;

import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ProjectEntity;


public class MaProjectInfo {

	private Object id;
	private Object name;
	private Object code;//项目编号
	private Object batch;
	private Object areaName;
	private Object cityName;
	private Object countyName;
	private Object town;
	private Object villageName;//行政村名
	private Object naturalVillage;//自然村名
	
	private Object proProperty;//项目属性
	private Object projectType;//项目类型
	
	private Object startAt;//启动时间（开工时间）  年+月
	private Object endAt;//预计竣工时间
	
	private Object totalFunds;
	private Object stateFunds;
	private Object specialFunds;
	private Object provinceFunds;
	private Object cityFunds;
	private Object countyFunds;
	private Object socialFunds;
	private Object massFunds;
	private Object otherFunds;
	
	private Object status;
	
	
	
	public MaProjectInfo(ProjectEntity p) {
		ModelAreaEntity area = p.getModelArea();
		if(area!=null){
			this.batch = area.getBatch();
			this.cityName = area.getCityName();
			this.countyName = area.getCounty();
			this.areaName= area.getName();
		}
		this.name = p.getName();
		this.code = p.getCode();
		this.town = p.getTown();
		BaseRuralEntity rural = p.getRural();
		if(rural!=null){
			this.villageName = rural.getName();
			this.naturalVillage = rural.getNaturalVillage();
		}
		this.proProperty = p.getProProperty();
		this.projectType  = p.getProjectType();
		if(p.getStartAtM()>=0 && p.getStartAtY()>0){
			this.startAt = p.getStartAtY()+"."+(p.getStartAtM()+1);
		}else{
			this.startAt = "";
		}
		if(p.getEndAtM()>=0 && p.getEndAtY()>0){
			this.endAt = p.getEndAtY()+"."+(p.getEndAtM()+1);
		}else{
			this.endAt = "";
		}

		InvestmentInfo inves = p.getTotalInvestment();
		if(inves!=null){
			this.totalFunds = inves.getTotalFunds();
			this.stateFunds = inves.getStateFunds();
			this.specialFunds = inves.getSpecialFunds();
			this.provinceFunds = inves.getProvinceFunds();
			this.cityFunds = inves.getCityFunds();
			this.countyFunds = inves.getCountyFunds();
			this.socialFunds = inves.getSocialFunds();
			this.massFunds = inves.getMassFunds();
			this.otherFunds = inves.getOtherFunds();
		}
		
		int _status = p.getStatus();
		switch (_status) {
		case 0:
			this.status = "未提交";
			break;
		case 1:
			this.status = "<font style='color:blue;'>进行中</font>";
			break;
		case 2:
			this.status = "<font style='color:green;'>竣工<font>";
			break;
		case 3:
			this.status = "终止";
			break;
		case 4:
			this.status = "<font style='color:red;'>驳回</font>";
			break;
		}
		
	}
	public Object getName() {
		return name;
	}
	public void setName(Object name) {
		this.name = name;
	}
	public Object getBatch() {
		return batch;
	}
	public void setBatch(Object batch) {
		this.batch = batch;
	}
	public Object getCityName() {
		return cityName;
	}
	public void setCityName(Object cityName) {
		this.cityName = cityName;
	}
	public Object getCountyName() {
		return countyName;
	}
	public void setCountyName(Object countyName) {
		this.countyName = countyName;
	}
	public Object getTown() {
		return town;
	}
	public void setTown(Object town) {
		this.town = town;
	}
	public Object getVillageName() {
		return villageName;
	}
	public void setVillageName(Object villageName) {
		this.villageName = villageName;
	}
	public Object getNaturalVillage() {
		return naturalVillage;
	}
	public void setNaturalVillage(Object naturalVillage) {
		this.naturalVillage = naturalVillage;
	}
	public Object getProProperty() {
		return proProperty;
	}
	public void setProProperty(Object proProperty) {
		this.proProperty = proProperty;
	}
	public Object getProjectType() {
		return projectType;
	}
	public void setProjectType(Object projectType) {
		this.projectType = projectType;
	}
	public Object getStartAt() {
		return startAt;
	}
	public void setStartAt(Object startAt) {
		this.startAt = startAt;
	}
	public Object getEndAt() {
		return endAt;
	}
	public void setEndAt(Object endAt) {
		this.endAt = endAt;
	}
	public Object getTotalFunds() {
		return totalFunds;
	}
	public void setTotalFunds(Object totalFunds) {
		this.totalFunds = totalFunds;
	}
	public Object getStateFunds() {
		return stateFunds;
	}
	public void setStateFunds(Object stateFunds) {
		this.stateFunds = stateFunds;
	}
	public Object getSpecialFunds() {
		return specialFunds;
	}
	public void setSpecialFunds(Object specialFunds) {
		this.specialFunds = specialFunds;
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
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getAreaName() {
		return areaName;
	}
	public void setAreaName(Object areaName) {
		this.areaName = areaName;
	}
	public Object getStatus() {
		return status;
	}
	public void setStatus(Object status) {
		this.status = status;
	}
	public Object getCode() {
		return code;
	}
	public void setCode(Object code) {
		this.code = code;
	}
	
	
	
}
