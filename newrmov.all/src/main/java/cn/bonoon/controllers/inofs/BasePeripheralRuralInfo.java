package cn.bonoon.controllers.inofs;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;

@Transform
public class BasePeripheralRuralInfo extends AbstractRuralInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016853087807767083L;

	@TransformField("newRural.id")
	private Long newRural;
	@TransformField("newRural.name")
	private String nrName;
	private String eniPlan;
	
	private int eniPlanDate;

	
	//新属性
	//贫困户数
	private int poorHouseHold;
	
	//贫困人口数
	private int poorPopulation;
	
	//低保户数
	private int lowHouseHold;
	
	//低保人口数
	private int  lowPopulation;
	
	//五保户数
	private int wubaoHouseHold;
	
	//需改造危房户数
	private int dangerHouse;
	
	/**
	 * 7月9号  新字段   
	 * 		(十)民生问题调查梳理去情况
	 */
	//完成自然村数
	private int finishNatureVillageNum;
	
	//梳理出项目数
	private int teasedProjectNum;	
	
	//排查出的矛盾纠纷数
	private int troubleshooting;
	
	//其中已化解数
	private int resolvedTroubleshooting;
	
	
	public Long getNewRural() {
		return newRural;
	}

	public void setNewRural(Long newRural) {
		this.newRural = newRural;
	}

	public String getNrName() {
		return nrName;
	}

	public void setNrName(String nrName) {
		this.nrName = nrName;
	}

	public String getEniPlan() {
		return eniPlan;
	}

	public void setEniPlan(String eniPlan) {
		this.eniPlan = eniPlan;
	}

	public int getEniPlanDate() {
		return eniPlanDate;
	}

	public void setEniPlanDate(int eniPlanDate) {
		this.eniPlanDate = eniPlanDate;
	}

	public int getPoorHouseHold() {
		return poorHouseHold;
	}

	public void setPoorHouseHold(int poorHouseHold) {
		this.poorHouseHold = poorHouseHold;
	}

	public int getPoorPopulation() {
		return poorPopulation;
	}

	public void setPoorPopulation(int poorPopulation) {
		this.poorPopulation = poorPopulation;
	}

	public int getLowHouseHold() {
		return lowHouseHold;
	}

	public void setLowHouseHold(int lowHouseHold) {
		this.lowHouseHold = lowHouseHold;
	}

	public int getLowPopulation() {
		return lowPopulation;
	}

	public void setLowPopulation(int lowPopulation) {
		this.lowPopulation = lowPopulation;
	}

	public int getWubaoHouseHold() {
		return wubaoHouseHold;
	}

	public void setWubaoHouseHold(int wubaoHouseHold) {
		this.wubaoHouseHold = wubaoHouseHold;
	}

	public int getDangerHouse() {
		return dangerHouse;
	}

	public void setDangerHouse(int dangerHouse) {
		this.dangerHouse = dangerHouse;
	}

	public int getFinishNatureVillageNum() {
		return finishNatureVillageNum;
	}

	public void setFinishNatureVillageNum(int finishNatureVillageNum) {
		this.finishNatureVillageNum = finishNatureVillageNum;
	}

	public int getTeasedProjectNum() {
		return teasedProjectNum;
	}

	public void setTeasedProjectNum(int teasedProjectNum) {
		this.teasedProjectNum = teasedProjectNum;
	}

	public int getTroubleshooting() {
		return troubleshooting;
	}

	public void setTroubleshooting(int troubleshooting) {
		this.troubleshooting = troubleshooting;
	}

	public int getResolvedTroubleshooting() {
		return resolvedTroubleshooting;
	}

	public void setResolvedTroubleshooting(int resolvedTroubleshooting) {
		this.resolvedTroubleshooting = resolvedTroubleshooting;
	}
	
	
	
}
