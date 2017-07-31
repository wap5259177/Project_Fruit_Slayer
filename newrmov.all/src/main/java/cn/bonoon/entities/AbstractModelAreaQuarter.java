package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractModelAreaQuarter extends AbstractModelAreaQuarterRural{

	//行政村有编制规划设计村数

	/**
	 * 
	 */
	private static final long serialVersionUID = -2055092708829733620L;

	@Column(name = "C_ARFINISHPLAN")
	private int arFinishPlan;
	
	public int getArFinishPlan() {
		return arFinishPlan;
	}

	public void setArFinishPlan(int arFinishPlan) {
		this.arFinishPlan = arFinishPlan;
	}

}
