package cn.bonoon.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.kernel.support.entities.EntityScopable;
import cn.bonoon.kernel.util.StringHelper;

@Entity
@Table(name = "t_fcreport")
public class FelicityCountyReportEntity extends AbstractFelicityCountyEntity implements EntityScopable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5422015743291475437L;

	@ManyToOne
	@JoinColumn(name = "R_COUNTY_ID")
	private FelicityCountyEntity county;

	@Column(name = "C_INFORMANT")
	private String informant;//填报人

	@Column(name = "C_CONTACT")
	private String contact;//联系电话

	@Temporal(TemporalType.DATE)
	@Column(name = "C_REPORTINGAT")
	private Date reportingAt;
	
	@OneToMany(mappedBy = "countyReport")
	private List<FelicityVillageReportEntity> items;

	@Column(name = "C_STATUS")
	private int status;

	@Column(name = "C_AUDITNAME")
	private String auditName;//审核人

	@Temporal(TemporalType.DATE)
	@Column(name = "C_AUDITAT")
	private Date auditAt;//审核时间

	@Column(name = "C_AUDITCONTENT", length = 4096)
	private String auditContent;

	@Column(name = "C_ANNUAL")
	private int annual;

	@Column(name = "C_MONTH")
	private int month;

	public FelicityCountyEntity getCounty() {
		return county;
	}

	public void setCounty(FelicityCountyEntity county) {
		this.county = county;
	}

	public String getInformant() {
		return informant;
	}

	public void setInformant(String informant) {
		this.informant = informant;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getReportingAt() {
		return reportingAt;
	}

	public void setReportingAt(Date reportingAt) {
		this.reportingAt = reportingAt;
	}

	public List<FelicityVillageReportEntity> getItems() {
		return items;
	}

	public void setItems(List<FelicityVillageReportEntity> items) {
		this.items = items;
	}

	/**
	 * <pre>
	 * 0  正在编辑，未提交审核
	 * 2  编辑结束，已提交审核
	 * 3  审核不通过，驳回修改
	 * 1  审核通过，结束 
	 * </pre>
	 * @return
	 */
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAnnual() {
		return annual;
	}

	public void setAnnual(int annual) {
		this.annual = annual;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getAuditName() {
		return auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	public Date getAuditAt() {
		return auditAt;
	}

	public void setAuditAt(Date auditAt) {
		this.auditAt = auditAt;
	}

	public String getAuditContent() {
		return auditContent;
	}

	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}

	public void clear() {
		
		this.fundsCity = 0;
		this.fundsCounty = 0;
		this.fundsMasses = 0;
		this.fundsOther = 0;
		this.fundsProvince = 0;
		this.fundsSociety = 0;
		this.fundsTotal = 0;
		this.fundsTown = 0;
		this.fundsVillage = 0;
		
		this.planningCompletedFalse = 0;
		this.planningCompletedTrue = 0;
		this.biddingCompletedFalse = 0;
		this.biddingCompletedTrue = 0;
		this.effectCleaningTeamFalse = 0;
		this.effectCleaningTeamTrue = 0;
		this.effectCouncilFalse = 0;
		this.effectCouncilTrue = 0;
		this.effectHardBottomFalse = 0;
		this.effectHardBottomTrue = 0;
		this.effectRemediationFalse = 0;
		this.effectRemediationTrue = 0;
		this.effectSewageTreatmentFalse = 0;
		this.effectSewageTreatmentTrue = 0;
		this.effectSolveGarbageFalse = 0;
		this.effectSolveGarbageTrue = 0;
		this.effectThroughWaterFalse = 0;
		this.effectThroughWaterTrue = 0;
		this.effectUniformStyleFalse = 0;
		this.effectUniformStyleTrue = 0;
		
		
		this.constructionArea = 0;
		this.investmentBudget = 0;
		this.investmentCompleted = 0;
		this.population = 0;
		this.projectCount = 0;
		this.householdCount = 0;
//		this.biddingProgress = 0;
//		this.planningProgress = 0;
//		this.projectProgress = 0;
		this.constructionCharacteristic0 = 0;
		this.constructionCharacteristic1 = 0;
		this.constructionCharacteristic2 = 0;
		this.constructionCharacteristic3 = 0;
		this.constructionCharacteristic4 = 0;
		this.constructionCharacteristic5 = 0;
		this.constructionCharacteristic6 = 0;
	}
	public void sum(FelicityVillageReportEntity fvr) {
		String cc = fvr.getConstructionCharacteristic();
		if(StringHelper.isNotEmpty(cc)){
			if(cc.contains("人文历史")){
				constructionCharacteristic0++;
			}else if(cc.contains("乡村旅游")){
				constructionCharacteristic1++;
			}else if(cc.contains("自然生态")){
				constructionCharacteristic2++;
			}else if(cc.contains("特色产业")){
				constructionCharacteristic3++;
			}else if(cc.contains("民居风貌")){
				constructionCharacteristic4++;
			}else if(cc.contains("渔业渔港")){
				constructionCharacteristic5++;
			}else{
				constructionCharacteristic6++;
			}
		}
		if(fvr.isEffectSolveGarbage()){
			effectSolveGarbageTrue++;
		}else{
			effectSolveGarbageFalse++;
		}
		if(fvr.isEffectThroughWater()){
			effectThroughWaterTrue++;
		}else{
			effectThroughWaterFalse++;
		}
		if(fvr.isEffectUniformStyle()){
			effectUniformStyleTrue++;
		}else{
			effectUniformStyleFalse++;
		}
		if(fvr.isEffectCleaningTeam()){
			effectCleaningTeamTrue++;
		}else{
			effectCleaningTeamFalse++;
		}
		if(fvr.isEffectCouncil()){
			effectCouncilTrue++;
		}else{
			effectCouncilFalse++;
		}
		if(fvr.isEffectHardBottom()){
			effectHardBottomTrue++;
		}else{
			effectHardBottomFalse++;
		}
		if(fvr.isEffectRemediation()){
			effectRemediationTrue++;
		}else{
			effectRemediationFalse++;
		}
		if(fvr.isEffectSewageTreatment()){
			effectSewageTreatmentTrue++;
		}else{
			effectSewageTreatmentFalse++;
		}
		if(fvr.isPlanningCompleted()){
			planningCompletedTrue++;
		}else{
			planningCompletedFalse++;
		}
		if(fvr.isBiddingCompleted()){
			biddingCompletedTrue++;
		}else{
			biddingCompletedFalse++;
		}
		
		this.fundsCity += fvr.fundsCity;
		this.fundsCounty += fvr.fundsCounty;
		this.fundsMasses += fvr.fundsMasses;
		this.fundsOther += fvr.fundsOther;
		this.fundsProvince += fvr.fundsProvince;
		this.fundsSociety += fvr.fundsSociety;
		this.fundsTotal += fvr.fundsTotal;
		this.fundsTown += fvr.fundsTown;
		this.fundsVillage += fvr.fundsVillage;
		
		this.constructionArea += fvr.constructionArea;
		this.investmentBudget += fvr.investmentBudget;
		this.investmentCompleted += fvr.investmentCompleted;
		this.population += fvr.population;
		this.projectCount += fvr.projectCount;
		this.householdCount += fvr.householdCount;
	}

}
