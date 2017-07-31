package cn.bonoon.entities.felicityVillage;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="t_fvproject")
public class FVProjectEntity extends AbstractFVProjectEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181391619119032152L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_MODELAREA_ID")
	private FVModelAreaReportEntity modelArea;
	
	/**
	 * 一个项目可以有多个村
	 */
	@OneToMany(mappedBy = "project")
	private List<FVProjectRuralEntity> rurals;
	
	/**
	 * 项目类型
	 */
	@Column(name="C_TYPE")
	private String type;

	public FVModelAreaReportEntity getModelArea() {
		return modelArea;
	}

	public void setModelArea(FVModelAreaReportEntity modelArea) {
		this.modelArea = modelArea;
	}

	public List<FVProjectRuralEntity> getRurals() {
		return rurals;
	}

	public void setRurals(List<FVProjectRuralEntity> rurals) {
		this.rurals = rurals;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
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
	
	
	
}
