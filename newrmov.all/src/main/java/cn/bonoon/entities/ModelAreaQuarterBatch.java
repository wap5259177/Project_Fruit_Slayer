package cn.bonoon.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 一个季度下的多个批次的示范片区。如：2015年第三季度，现在正在进行的有第一批和第二批。
 * 所以这里就会有两个批次的记录
 * @author jackson
 *
 */
@Entity
@Table(name = "t_mabatch")
public class ModelAreaQuarterBatch extends AbstractModelAreaQuarter{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1549079564397204906L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_QUARTER_ID")
	private ModelAreaQuarterEntity quarter;
	
	@OneToMany(mappedBy = "batch")
	private List<ModelAreaQuarterItem> items;
	
	/**
	 * 批次的名称：一、二、三...等
	 */
	@Column(name = "C_BATCHNAME")
	private String batchName;
	
	/**
	 * 这一批次下有多少个片区
	 */
	@Column(name = "C_MACOUNT")
	private int maCount;

	@Column(name = "C_ORDINAL")
	private Integer ordinal;

	public ModelAreaQuarterEntity getQuarter() {
		return quarter;
	}

	public void setQuarter(ModelAreaQuarterEntity quarter) {
		this.quarter = quarter;
	}

	public List<ModelAreaQuarterItem> getItems() {
		return items;
	}

	public void setItems(List<ModelAreaQuarterItem> items) {
		this.items = items;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public int getMaCount() {
		return maCount;
	}

	public void setMaCount(int maCount) {
		this.maCount = maCount;
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}
	
	
	public void sum(){
		int arCount = 0,nrCount = 0,houseHouseholdCount = 0,populationCount=0,arFinishPlan=0,startPro = 0,finishPro = 0;
		RuralNeedFinishInfo needFinish = new RuralNeedFinishInfo();
		InvestmentInfo info = new InvestmentInfo();
		for(ModelAreaQuarterItem item:this.items){
			arCount += item.getArCount();
			nrCount += item.getNrCount();
			int _sp = item.getStartProject()==null?0:item.getStartProject();
			int _fp = item.getFinishProject()==null?0:item.getFinishProject();
			startPro += _sp;
			finishPro += _fp;
			houseHouseholdCount += item.getHouseholdCount();
			populationCount += item.getPopulationCount();
			arFinishPlan += item.getArFinishPlan();
			
			needFinish.sum(item.getNeedFinish());
			info.sum(item.getInvestment().sumSelf());
		}
		this.setArCount(arCount);
		this.setNrCount(nrCount);
		this.setHouseholdCount(houseHouseholdCount);
		this.setPopulationCount(populationCount);
		this.setArFinishPlan(arFinishPlan);
		this.setStartProject(startPro);
		this.setFinishProject(finishPro);
		this.setNeedFinish(needFinish);
		this.setInvestment(info);
	}
	
}
