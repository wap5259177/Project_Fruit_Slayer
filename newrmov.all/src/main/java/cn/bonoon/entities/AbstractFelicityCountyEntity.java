package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractFelicityCountyEntity extends AbstractFelicityEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4473916886506374577L;
	@Column(name = "C_CC0")
	protected int constructionCharacteristic0;//人文历史
	@Column(name = "C_CC1")
	protected int constructionCharacteristic1;//乡村旅游
	@Column(name = "C_CC2")
	protected int constructionCharacteristic2;//自然生态
	@Column(name = "C_CC3")
	protected int constructionCharacteristic3;//特色产业
	@Column(name = "C_CC4")
	protected int constructionCharacteristic4;//民居风貌
	@Column(name = "C_CC5")
	protected int constructionCharacteristic5;//渔业渔港
	@Column(name = "C_CC6")
	protected int constructionCharacteristic6;//其它
	//规划设计
	//完成或否
	@Column(name = "C_PCT")
	protected int planningCompletedTrue;
	@Column(name = "C_PCF")
	protected int planningCompletedFalse;
	
	//项目招投标
	//完成或否
	@Column(name = "C_BCT")
	protected int biddingCompletedTrue;
	@Column(name = "C_BCF")
	protected int biddingCompletedFalse;
	
	//建设成效effect
	//是否完成环境卫生整治
	@Column(name = "C_ERT")
	protected int effectRemediationTrue;
	@Column(name = "C_ERF")
	protected int effectRemediationFalse;
	//是否统一民居外立面风貌
	@Column(name = "C_EUT")
	protected int effectUniformStyleTrue;
	@Column(name = "C_EUF")
	protected int effectUniformStyleFalse;
	//是否解决垃圾问题
	@Column(name = "C_EGT")
	protected int effectSolveGarbageTrue;
	@Column(name = "C_EGF")
	protected int effectSolveGarbageFalse;
	//是否建立污水处理设施
	@Column(name = "C_EST")
	protected int effectSewageTreatmentTrue;
	@Column(name = "C_ESF")
	protected int effectSewageTreatmentFalse;
	//是否建立村保洁队伍
	@Column(name = "C_ETT")
	protected int effectCleaningTeamTrue;
	@Column(name = "C_ETF")
	protected int effectCleaningTeamFalse;
	//是否成立村民理事会
	@Column(name = "C_EIT")
	protected int effectCouncilTrue;
	@Column(name = "C_EIF")
	protected int effectCouncilFalse;
	//是否完成村巷道硬底化建设
	@Column(name = "C_EHT")
	protected int effectHardBottomTrue;
	@Column(name = "C_EHF")
	protected int effectHardBottomFalse;
	//是否解决通自来水
	@Column(name = "C_EWT")
	protected int effectThroughWaterTrue;
	
	@Column(name = "C_EWF")
	protected int effectThroughWaterFalse;

	public int getConstructionCharacteristic0() {
		return constructionCharacteristic0;
	}

	public void setConstructionCharacteristic0(int constructionCharacteristic0) {
		this.constructionCharacteristic0 = constructionCharacteristic0;
	}

	public int getConstructionCharacteristic1() {
		return constructionCharacteristic1;
	}

	public void setConstructionCharacteristic1(int constructionCharacteristic1) {
		this.constructionCharacteristic1 = constructionCharacteristic1;
	}

	public int getConstructionCharacteristic2() {
		return constructionCharacteristic2;
	}

	public void setConstructionCharacteristic2(int constructionCharacteristic2) {
		this.constructionCharacteristic2 = constructionCharacteristic2;
	}

	public int getConstructionCharacteristic3() {
		return constructionCharacteristic3;
	}

	public void setConstructionCharacteristic3(int constructionCharacteristic3) {
		this.constructionCharacteristic3 = constructionCharacteristic3;
	}

	public int getConstructionCharacteristic4() {
		return constructionCharacteristic4;
	}

	public void setConstructionCharacteristic4(int constructionCharacteristic4) {
		this.constructionCharacteristic4 = constructionCharacteristic4;
	}

	public int getConstructionCharacteristic5() {
		return constructionCharacteristic5;
	}

	public void setConstructionCharacteristic5(int constructionCharacteristic5) {
		this.constructionCharacteristic5 = constructionCharacteristic5;
	}

	public int getConstructionCharacteristic6() {
		return constructionCharacteristic6;
	}

	public void setConstructionCharacteristic6(int constructionCharacteristic6) {
		this.constructionCharacteristic6 = constructionCharacteristic6;
	}

	public int getPlanningCompletedTrue() {
		return planningCompletedTrue;
	}

	public void setPlanningCompletedTrue(int planningCompletedTrue) {
		this.planningCompletedTrue = planningCompletedTrue;
	}

	public int getPlanningCompletedFalse() {
		return planningCompletedFalse;
	}

	public void setPlanningCompletedFalse(int planningCompletedFalse) {
		this.planningCompletedFalse = planningCompletedFalse;
	}

	public int getBiddingCompletedTrue() {
		return biddingCompletedTrue;
	}

	public void setBiddingCompletedTrue(int biddingCompletedTrue) {
		this.biddingCompletedTrue = biddingCompletedTrue;
	}

	public int getBiddingCompletedFalse() {
		return biddingCompletedFalse;
	}

	public void setBiddingCompletedFalse(int biddingCompletedFalse) {
		this.biddingCompletedFalse = biddingCompletedFalse;
	}

	public int getEffectRemediationTrue() {
		return effectRemediationTrue;
	}

	public void setEffectRemediationTrue(int effectRemediationTrue) {
		this.effectRemediationTrue = effectRemediationTrue;
	}

	public int getEffectRemediationFalse() {
		return effectRemediationFalse;
	}

	public void setEffectRemediationFalse(int effectRemediationFalse) {
		this.effectRemediationFalse = effectRemediationFalse;
	}

	public int getEffectUniformStyleTrue() {
		return effectUniformStyleTrue;
	}

	public void setEffectUniformStyleTrue(int effectUniformStyleTrue) {
		this.effectUniformStyleTrue = effectUniformStyleTrue;
	}

	public int getEffectUniformStyleFalse() {
		return effectUniformStyleFalse;
	}

	public void setEffectUniformStyleFalse(int effectUniformStyleFalse) {
		this.effectUniformStyleFalse = effectUniformStyleFalse;
	}

	public int getEffectSolveGarbageTrue() {
		return effectSolveGarbageTrue;
	}

	public void setEffectSolveGarbageTrue(int effectSolveGarbageTrue) {
		this.effectSolveGarbageTrue = effectSolveGarbageTrue;
	}

	public int getEffectSolveGarbageFalse() {
		return effectSolveGarbageFalse;
	}

	public void setEffectSolveGarbageFalse(int effectSolveGarbageFalse) {
		this.effectSolveGarbageFalse = effectSolveGarbageFalse;
	}

	public int getEffectSewageTreatmentTrue() {
		return effectSewageTreatmentTrue;
	}

	public void setEffectSewageTreatmentTrue(int effectSewageTreatmentTrue) {
		this.effectSewageTreatmentTrue = effectSewageTreatmentTrue;
	}

	public int getEffectSewageTreatmentFalse() {
		return effectSewageTreatmentFalse;
	}

	public void setEffectSewageTreatmentFalse(int effectSewageTreatmentFalse) {
		this.effectSewageTreatmentFalse = effectSewageTreatmentFalse;
	}

	public int getEffectCleaningTeamTrue() {
		return effectCleaningTeamTrue;
	}

	public void setEffectCleaningTeamTrue(int effectCleaningTeamTrue) {
		this.effectCleaningTeamTrue = effectCleaningTeamTrue;
	}

	public int getEffectCleaningTeamFalse() {
		return effectCleaningTeamFalse;
	}

	public void setEffectCleaningTeamFalse(int effectCleaningTeamFalse) {
		this.effectCleaningTeamFalse = effectCleaningTeamFalse;
	}

	public int getEffectCouncilTrue() {
		return effectCouncilTrue;
	}

	public void setEffectCouncilTrue(int effectCouncilTrue) {
		this.effectCouncilTrue = effectCouncilTrue;
	}

	public int getEffectCouncilFalse() {
		return effectCouncilFalse;
	}

	public void setEffectCouncilFalse(int effectCouncilFalse) {
		this.effectCouncilFalse = effectCouncilFalse;
	}

	public int getEffectHardBottomTrue() {
		return effectHardBottomTrue;
	}

	public void setEffectHardBottomTrue(int effectHardBottomTrue) {
		this.effectHardBottomTrue = effectHardBottomTrue;
	}

	public int getEffectHardBottomFalse() {
		return effectHardBottomFalse;
	}

	public void setEffectHardBottomFalse(int effectHardBottomFalse) {
		this.effectHardBottomFalse = effectHardBottomFalse;
	}

	public int getEffectThroughWaterTrue() {
		return effectThroughWaterTrue;
	}

	public void setEffectThroughWaterTrue(int effectThroughWaterTrue) {
		this.effectThroughWaterTrue = effectThroughWaterTrue;
	}

	public int getEffectThroughWaterFalse() {
		return effectThroughWaterFalse;
	}

	public void setEffectThroughWaterFalse(int effectThroughWaterFalse) {
		this.effectThroughWaterFalse = effectThroughWaterFalse;
	}
}
