package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import cn.bonoon.kernel.support.entities.AbstractPersistable;

@MappedSuperclass
public abstract class AbstractModelAreaQuarterRural extends AbstractPersistable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2522650839677540467L;
	// 需要统计的一些数据
	// 行政村数
	@Column(name = "C_ARCOUNT")
	private int arCount;
	// 自然村数
	@Column(name = "C_NRCOUNT")
	private int nrCount;
	// 户数
	@Column(name = "C_HOUSEHOLDCOUNT")
	private int householdCount;
	// 人数
	@Column(name = "C_POPULATIONCOUNT")
	private int populationCount;

	@Column(name = "C_FPRO")
	private Integer finishProject;

	@Column(name = "C_SPRO")
	private Integer startProject;

	@Embedded
	private RuralNeedFinishInfo needFinish = new RuralNeedFinishInfo();

	//资金
	@Embedded
	private InvestmentInfo investment = new InvestmentInfo();

	public int getArCount() {
		return arCount;
	}

	public void setArCount(int arCount) {
		this.arCount = arCount;
	}

	public int getNrCount() {
		return nrCount;
	}

	public void setNrCount(int nrCount) {
		this.nrCount = nrCount;
	}

	public int getHouseholdCount() {
		return householdCount;
	}

	public void setHouseholdCount(int householdCount) {
		this.householdCount = householdCount;
	}

	public int getPopulationCount() {
		return populationCount;
	}

	public void setPopulationCount(int populationCount) {
		this.populationCount = populationCount;
	}

	public RuralNeedFinishInfo getNeedFinish() {
		return needFinish;
	}

	public void setNeedFinish(RuralNeedFinishInfo needFinish) {
		this.needFinish = needFinish;
	}

	public Integer getStartProject() {
		if(startProject==null)
			startProject=0;
		return startProject;
	}

	public void setStartProject(Integer startProject) {
		this.startProject = startProject;
	}

	public Integer getFinishProject() {
		if(finishProject==null)
			finishProject=0;
		return finishProject;
	}

	public void setFinishProject(Integer finishProject) {
		this.finishProject = finishProject;
	}

	public InvestmentInfo getInvestment() {
		if(investment==null)
			investment=new InvestmentInfo();
		return investment;
	}

	public void setInvestment(InvestmentInfo investment) {
		this.investment = investment;
	}

}
