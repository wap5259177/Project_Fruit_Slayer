package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.kernel.support.entities.AbstractPersistable;

@MappedSuperclass
public abstract class AbstractFelicityEntity extends AbstractPersistable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4659798051495908280L;

	@Column(name = "C_CONSAREA")
	protected double constructionArea;//村庄建设覆盖面积

	@Column(name = "C_HOUSCOUNT")
	protected int householdCount;//户数

	@Column(name = "C_POPULATION")
	protected int population;//人口数

	@Column(name = "C_PROJECTCOUNT")
	protected int projectCount;//确定的建设项目数（个）

	@Column(name = "C_PROJECTPROGRESS")
	protected double projectProgress;//项目建设进度（%）

	@Column(name = "C_INVESTMENTBUDGET")
	protected double investmentBudget;//投入预算（万元）

	@Column(name = "C_INVESTMENTCOMPLETED")
	protected double investmentCompleted;//目前已完成投入（万元）
	
	//资金来源（万元）
	@Column(name = "C_FUNDSTOTAL")
	protected double fundsTotal;//小计
	@Column(name = "C_FUNDSPROVINCE")
	protected double fundsProvince;//省
	@Column(name = "C_FUNDSCITY")
	protected double fundsCity;//市
	@Column(name = "C_FUNDSCOUNTY")
	protected double fundsCounty;//县
	@Column(name = "C_FUNDSTOWN")
	protected double fundsTown;//镇
	@Column(name = "C_FUNDSVILLAGE")
	protected double fundsVillage;//村
	@Column(name = "C_FUNDSMASSES")
	protected double fundsMasses;//群众
	@Column(name = "C_FUNDSSOCIETY")
	protected double fundsSociety;//社会
	@Column(name = "C_FUNDSOTHER")
	protected double fundsOther;//其它
	
	//规划设计
	@Column(name = "C_PLANNINGPROGRESS")
	protected double planningProgress;//规划进度（%）
	
	//项目招投标
	@Column(name = "C_BIDDINGPROGRESS")
	protected double biddingProgress;//完成招投标比例（%）

	/**
	 * <pre>
	 * 该记录的创建者的Id。
	 * 一般不需要显示创建者ID，只显示创建者的登录名creatorName。
	 * </pre>
	 */
	@Column(name = "C_CREATORID")
	private Long creatorId;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "C_CREATEAT", nullable = false)
	private Date createAt;

	/**
	 * <pre>
	 * 创建人的名称，登录账号。
	 * 一般可用于直接显示，如列表或查看的页面等。
	 * 一般creatorName不作为关联关系，关联关系请使用createrId。
	 * </pre>
	 */
	@Column(name = "C_CREATORNAME")
	private String creatorName;
	
	@Column(name = "C_OWNERID")
	private long ownerId;
	
	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public double getConstructionArea() {
		return constructionArea;
	}

	public void setConstructionArea(double constructionArea) {
		this.constructionArea = constructionArea;
	}

	public int getHouseholdCount() {
		return householdCount;
	}

	public void setHouseholdCount(int householdCount) {
		this.householdCount = householdCount;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public int getProjectCount() {
		return projectCount;
	}

	public void setProjectCount(int projectCount) {
		this.projectCount = projectCount;
	}

	public double getProjectProgress() {
		return projectProgress;
	}

	public void setProjectProgress(double projectProgress) {
		this.projectProgress = projectProgress;
	}

	public double getInvestmentBudget() {
		return investmentBudget;
	}

	public void setInvestmentBudget(double investmentBudget) {
		this.investmentBudget = investmentBudget;
	}

	public double getInvestmentCompleted() {
		return investmentCompleted;
	}

	public void setInvestmentCompleted(double investmentCompleted) {
		this.investmentCompleted = investmentCompleted;
	}

	public double getFundsTotal() {
		return fundsTotal;
	}

	public void setFundsTotal(double fundsTotal) {
		this.fundsTotal = fundsTotal;
	}

	public double getFundsProvince() {
		return fundsProvince;
	}

	public void setFundsProvince(double fundsProvince) {
		this.fundsProvince = fundsProvince;
	}

	public double getFundsCity() {
		return fundsCity;
	}

	public void setFundsCity(double fundsCity) {
		this.fundsCity = fundsCity;
	}

	public double getFundsCounty() {
		return fundsCounty;
	}

	public void setFundsCounty(double fundsCounty) {
		this.fundsCounty = fundsCounty;
	}

	public double getFundsTown() {
		return fundsTown;
	}

	public void setFundsTown(double fundsTown) {
		this.fundsTown = fundsTown;
	}

	public double getFundsVillage() {
		return fundsVillage;
	}

	public void setFundsVillage(double fundsVillage) {
		this.fundsVillage = fundsVillage;
	}

	public double getFundsMasses() {
		return fundsMasses;
	}

	public void setFundsMasses(double fundsMasses) {
		this.fundsMasses = fundsMasses;
	}

	public double getFundsSociety() {
		return fundsSociety;
	}

	public void setFundsSociety(double fundsSociety) {
		this.fundsSociety = fundsSociety;
	}

	public double getFundsOther() {
		return fundsOther;
	}

	public void setFundsOther(double fundsOther) {
		this.fundsOther = fundsOther;
	}

	public double getPlanningProgress() {
		return planningProgress;
	}

	public void setPlanningProgress(double planningProgress) {
		this.planningProgress = planningProgress;
	}

	public double getBiddingProgress() {
		return biddingProgress;
	}

	public void setBiddingProgress(double biddingProgress) {
		this.biddingProgress = biddingProgress;
	}

}
