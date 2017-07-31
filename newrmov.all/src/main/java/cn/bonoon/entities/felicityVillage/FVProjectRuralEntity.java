package cn.bonoon.entities.felicityVillage;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 项目-村  中间表
 * @author xiaowu
 *
 */
@Entity
@Table(name="t_fvpro_rural")
public class FVProjectRuralEntity extends AbstractFVProjectEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1772068930623118198L;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_PROJECT_ID")
	private FVProjectEntity project;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_MODELAREA_ID")
	private FVModelAreaReportEntity modelArea;
	
	@ManyToMany
	@JoinTable(name="rt_pro_br",
		joinColumns= @JoinColumn(name="PR_ID", referencedColumnName="C_ID"),
        inverseJoinColumns= @JoinColumn(name="BR_ID", referencedColumnName="C_ID")
	)
	private List<FVBaseRuralEntity> rurals;
	
	
//
//	/**
//	 * 项目预算投入
//	 */
//	@Column(name = "C_BUDGETMONEY")
//	private double budgetMoney;
//	/**
//	 * 项目完成投入
//	 */
//	@Column(name = "C_FINISHMONEY")
//	private double finishMoney;
//	
	
	/**
	 * 项目启动时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_STARTTIME")
	private Date startTime;
	
	/**
	 * 项目完成时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_FINISHTIME")
	private Date finishTime;
	
	/**
	 * 项目验收时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_CHECKTIME")
	private Date checkTime;
	
	/**
	 * 验收部门
	 */
	@Column(name="C_CHECKUNIT")
	private String checkUnit;

	public FVProjectEntity getProject() {
		return project;
	}

	public void setProject(FVProjectEntity project) {
		this.project = project;
	}

	public List<FVBaseRuralEntity> getRurals() {
		return rurals;
	}

	public void setRurals(List<FVBaseRuralEntity> rurals) {
		this.rurals = rurals;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckUnit() {
		return checkUnit;
	}

	public void setCheckUnit(String checkUnit) {
		this.checkUnit = checkUnit;
	}

	public FVModelAreaReportEntity getModelArea() {
		return modelArea;
	}

	public void setModelArea(FVModelAreaReportEntity modelArea) {
		this.modelArea = modelArea;
	}

	
	
	
	
}
