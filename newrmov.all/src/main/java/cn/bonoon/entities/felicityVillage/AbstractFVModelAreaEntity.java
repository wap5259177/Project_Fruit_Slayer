package cn.bonoon.entities.felicityVillage;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Administrator
 *
 */
@MappedSuperclass
public abstract class AbstractFVModelAreaEntity extends AbstractFVProjectEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4659798051495908280L;

	//----------------------------------
	//示范片建设覆盖面积
	@Column(name="C_CONSTRUCTIONAREA")
	protected double constructionArea;
	
	//户数
	@Column(name="C_HOUSEHOLDS")
	protected int households;
	
	//人口数
	@Column(name="C_POPULATION")
	protected int population;
	
	
	//--------------------------
	
	/**
	 * 项目个数
	 * TODO:村,录入  向上统计
	 */
	@Column(name="C_PROJECTNUM")
	protected int projectNum;
	
	/**
	 * 项目完成个数
	 */
	@Column(name="C_PROJECTFINISHNUM")
	protected int projectFinishNum;
	

	
	/**
	 * 项目数量
	 * TODO:片区统计项目实际的录入个数
	 * list<project> 的size
	 * 实际上和上面的projectNum 理论上是数据应该对应一样的
	 */
	@Column(name="C_PROJECTAREACOUNT")
	protected int projectAreaCount;
	
	
	
	
	/**
	 * 建设资金筹集(万元)
	 */
	@Embedded
	protected FVInvestmentInfo investment = new FVInvestmentInfo();
	
	/**
	 * 已投入资金(万元)
	 */
	@Column(name = "C_INVESTED")
	protected double invested;
	
	//主体村数
	@Column(name="C_CRNUM")
	protected int crNum;
	
	//辐射村数
	@Column(name="C_PRNUM")
	protected int prNum;
	
	
	//完成规划设计数
	@Column(name="C_FINISHEDPLANNUM")
	protected int finishedPlanNum;
	
	//未完成规划设计数
	@Column(name="C_NOTFINISHEDPLANNUM")
	protected int notFinishedPlanNum;
	
	//完成项目招投标数
	@Column(name="C_FINISHEDBIDNUM")
	protected int finishedBidNum;
	
	//未完成项目招投标数
	@Column(name="C_NOTFINISHEDBIDNUM")
	protected int notFinishedBidNum;
	
	

	public int getFinishedPlanNum() {
		return finishedPlanNum;
	}

	public void setFinishedPlanNum(int finishedPlanNum) {
		this.finishedPlanNum = finishedPlanNum;
	}

	public int getNotFinishedPlanNum() {
		return notFinishedPlanNum;
	}

	public void setNotFinishedPlanNum(int notFinishedPlanNum) {
		this.notFinishedPlanNum = notFinishedPlanNum;
	}

	public int getFinishedBidNum() {
		return finishedBidNum;
	}

	public void setFinishedBidNum(int finishedBidNum) {
		this.finishedBidNum = finishedBidNum;
	}

	public int getNotFinishedBidNum() {
		return notFinishedBidNum;
	}

	public void setNotFinishedBidNum(int notFinishedBidNum) {
		this.notFinishedBidNum = notFinishedBidNum;
	}
	
	public double getConstructionArea() {
		return constructionArea;
	}

	public void setConstructionArea(double constructionArea) {
		this.constructionArea = constructionArea;
	}

	public int getHouseholds() {
		return households;
	}

	public void setHouseholds(int households) {
		this.households = households;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
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

	public int getProjectAreaCount() {
		return projectAreaCount;
	}

	public void setProjectAreaCount(int projectAreaCount) {
		this.projectAreaCount = projectAreaCount;
	}

	public FVInvestmentInfo getInvestment() {
		return investment;
	}

	public void setInvestment(FVInvestmentInfo investment) {
		this.investment = investment;
	}

	public double getInvested() {
		return invested;
	}

	public void setInvested(double invested) {
		this.invested = invested;
	}

	public int getCrNum() {
		return crNum;
	}

	public void setCrNum(int crNum) {
		this.crNum = crNum;
	}

	public int getPrNum() {
		return prNum;
	}

	public void setPrNum(int prNum) {
		this.prNum = prNum;
	}

	
	


}
