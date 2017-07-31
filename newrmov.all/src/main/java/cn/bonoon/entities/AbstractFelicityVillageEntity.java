package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 幸福村居
 * @author jackson
 *
 */
@MappedSuperclass
public class AbstractFelicityVillageEntity extends AbstractFelicityEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4473916886506374577L;

	@Column(name = "C_CONSCHARA")
	protected String constructionCharacteristic;//村庄建设特色
	@Column(name = "C_CONSFEATURE")
	protected String constructionFeature;//村庄建设特点
	
	//规划设计
	@Column(name = "C_PLANNINGCOMPLETED")
	protected boolean planningCompleted;//完成或否
	//项目招投标
	@Column(name = "C_BIDDINGCOMPLETED")
	protected boolean biddingCompleted;//完成或否
	//建设成效effect
	@Column(name = "C_EREMEDIATION")
	protected boolean effectRemediation;//是否完成环境卫生整治
	@Column(name = "C_EUNIFORMSTYLE")
	protected boolean effectUniformStyle;//是否统一民居外立面风貌
	@Column(name = "C_ESOLVEGARBAGE")
	protected boolean effectSolveGarbage;//是否解决垃圾问题
	@Column(name = "C_ESEWAGETREATMENT")
	protected boolean effectSewageTreatment;//是否建立污水处理设施
	@Column(name = "C_ECLEANINGTEAM")
	protected boolean effectCleaningTeam;//是否建立村保洁队伍
	@Column(name = "C_ECOUNCIL")
	protected boolean effectCouncil;//是否成立村民理事会
	@Column(name = "C_EHARDBOTTOM")
	protected boolean effectHardBottom;//是否完成村巷道硬底化建设
	@Column(name = "C_ETHROUGHWATER")
	protected boolean effectThroughWater;//是否解决通自来水
	
	@Column(name = "C_EOTHER")
	protected String effectOther;//其它

	public String getConstructionCharacteristic() {
		return constructionCharacteristic;
	}

	public void setConstructionCharacteristic(String constructionCharacteristic) {
		this.constructionCharacteristic = constructionCharacteristic;
	}

	public String getConstructionFeature() {
		return constructionFeature;
	}

	public void setConstructionFeature(String constructionFeature) {
		this.constructionFeature = constructionFeature;
	}

	public boolean isPlanningCompleted() {
		return planningCompleted;
	}

	public void setPlanningCompleted(boolean planningCompleted) {
		this.planningCompleted = planningCompleted;
	}

	public boolean isBiddingCompleted() {
		return biddingCompleted;
	}

	public void setBiddingCompleted(boolean biddingCompleted) {
		this.biddingCompleted = biddingCompleted;
	}

	public boolean isEffectRemediation() {
		return effectRemediation;
	}

	public void setEffectRemediation(boolean effectRemediation) {
		this.effectRemediation = effectRemediation;
	}

	public boolean isEffectUniformStyle() {
		return effectUniformStyle;
	}

	public void setEffectUniformStyle(boolean effectUniformStyle) {
		this.effectUniformStyle = effectUniformStyle;
	}

	public boolean isEffectSolveGarbage() {
		return effectSolveGarbage;
	}

	public void setEffectSolveGarbage(boolean effectSolveGarbage) {
		this.effectSolveGarbage = effectSolveGarbage;
	}

	public boolean isEffectSewageTreatment() {
		return effectSewageTreatment;
	}

	public void setEffectSewageTreatment(boolean effectSewageTreatment) {
		this.effectSewageTreatment = effectSewageTreatment;
	}

	public boolean isEffectCleaningTeam() {
		return effectCleaningTeam;
	}

	public void setEffectCleaningTeam(boolean effectCleaningTeam) {
		this.effectCleaningTeam = effectCleaningTeam;
	}

	public boolean isEffectCouncil() {
		return effectCouncil;
	}

	public void setEffectCouncil(boolean effectCouncil) {
		this.effectCouncil = effectCouncil;
	}

	public boolean isEffectHardBottom() {
		return effectHardBottom;
	}

	public void setEffectHardBottom(boolean effectHardBottom) {
		this.effectHardBottom = effectHardBottom;
	}

	public boolean isEffectThroughWater() {
		return effectThroughWater;
	}

	public void setEffectThroughWater(boolean effectThroughWater) {
		this.effectThroughWater = effectThroughWater;
	}

	public String getEffectOther() {
		return effectOther;
	}

	public void setEffectOther(String effectOther) {
		this.effectOther = effectOther;
	}
	
}
