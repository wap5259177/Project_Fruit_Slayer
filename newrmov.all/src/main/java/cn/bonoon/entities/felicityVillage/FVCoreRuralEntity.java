package cn.bonoon.entities.felicityVillage;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * 主体村
 * @author xiaowu
 *
 */
@Entity
public class FVCoreRuralEntity extends FVBaseRuralEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4680455177205468564L;

	/**
	 * 一个主题村带动多个辐射村
	 */
	@OrderBy("C_ID desc")
	@OneToMany(mappedBy = "coreRural")
	private List<FVPeripheralRuralEntity> fvprs;
	
	/**
	 * 是否完成规划设计
	 */
	@Column(name="C_FINISHEDPLAN")
	private boolean finishedPlan;
	
	/**
	 * 是否完成项目招投标
	 */
	@Column(name="C_FINISHEDBID")
	private boolean finishedBid;
	
	
	
	public FVCoreRuralEntity(){}
	
	public FVCoreRuralEntity(FVCoreRuralEntity cr){
		this.finishedPlan = cr.isFinishedPlan();
		this.finishedBid = cr.isFinishedBid();
//		this.fvprs = cr.getFvprs();
	}
	

	public List<FVPeripheralRuralEntity> getFvprs() {
		return fvprs;
	}

	public void setFvprs(List<FVPeripheralRuralEntity> fvprs) {
		this.fvprs = fvprs;
	}

	public boolean isFinishedPlan() {
		return finishedPlan;
	}

	public void setFinishedPlan(boolean finishedPlan) {
		this.finishedPlan = finishedPlan;
	}

	public boolean isFinishedBid() {
		return finishedBid;
	}

	public void setFinishedBid(boolean finishedBid) {
		this.finishedBid = finishedBid;
	}
	
	
}
