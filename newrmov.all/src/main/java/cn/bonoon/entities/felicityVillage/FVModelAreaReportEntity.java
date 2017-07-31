package cn.bonoon.entities.felicityVillage;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 片区
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_fvmodelarea")
public class FVModelAreaReportEntity extends AbstractFVModelAreaEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3172446392415234918L;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_DICMODELAREA_ID")
	private FVModelAreaEntity dicModelArea;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_COUNTYREPORT_ID")
	private FVMACountyReportEntity countyReport;
	
	
	/**
	 * 一个片区下面有多个项目
	 */
	@OneToMany(mappedBy = "modelArea")
	private List<FVProjectEntity> projects;
	
//	/**
//	 * 一个片区下面有多个主体村
//	 */
//	@OneToMany(mappedBy = "modelArea")
//	private List<FVCoreRuralEntity> fvcrs;
//
//	/**
//	 * 一个片区下面有多个辐射村
//	 */
//	@OneToMany(mappedBy = "modelArea")
//	private List<FVPeripheralRuralEntity> fvprs;
	
	
	//--------------------------------表1的信息----------------------------------------------------------
//	/**
//	 * 示范片建设覆盖面积
//	 */
//	@Column(name="C_CONSTRUCTIONAREA")
//	private double constructionArea;
//	
//	/**
//	 * 户数
//	 */
//	@Column(name="C_HOUSEHOLDS")
//	private int households;
//	
//	/**
//	 * 人口数
//	 */
//	@Column(name="C_POPULATION")
//	private int population;
	
	
	
	
	//--------------------------------表3的信息-----------------------------------------------------
//	/**
//	 * 建设资金筹集(万元)
//	 */
//	@Embedded
//	private FVInvestmentInfo investment = new FVInvestmentInfo();
//	
//	/**
//	 * 已投入资金(万元)
//	 */
//	@Column(name = "C_INVESTED")
//	private double invested;
	
	/**
	 * 建设特点,特色,措施
	 * 不少于500字
	 */
	
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "C_FEATURE", columnDefinition = "TEXT")
	private String feature;
	
	/**
	 * 存在问题
	 * 200字以内
	 */
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "C_PROBLEM", columnDefinition = "TEXT")
	private String problem;
	
	
	
	
	
	
	public FVModelAreaReportEntity(){}
	
	public FVModelAreaReportEntity(FVModelAreaReportEntity ma){
		this.constructionArea = ma.getConstructionArea();
		this.households = ma.getHouseholds();
		this.population = ma.getPopulation();
		
		//表一的统计
		this.crNum = ma.getCrNum();
		this.prNum = ma.getPrNum();
		this.finishedPlanNum  = ma.getFinishedPlanNum();
		this.finishedBidNum  = ma.getFinishedBidNum();
		this.notFinishedPlanNum = ma.getNotFinishedPlanNum();
		this.notFinishedBidNum = ma.getNotFinishedBidNum();
		
		//表二..表三
		
	}
	
	
	

	public FVModelAreaEntity getDicModelArea() {
		return dicModelArea;
	}

	public void setDicModelArea(FVModelAreaEntity dicModelArea) {
		this.dicModelArea = dicModelArea;
	}

	public FVMACountyReportEntity getCountyReport() {
		return countyReport;
	}

	public void setCountyReport(FVMACountyReportEntity countyReport) {
		this.countyReport = countyReport;
	}

	public List<FVProjectEntity> getProjects() {
		return projects;
	}

	public void setProjects(List<FVProjectEntity> projects) {
		this.projects = projects;
	}

//	public List<FVCoreRuralEntity> getFvcrs() {
//		return fvcrs;
//	}
//
//	public void setFvcrs(List<FVCoreRuralEntity> fvcrs) {
//		this.fvcrs = fvcrs;
//	}
//
//	public List<FVPeripheralRuralEntity> getFvprs() {
//		return fvprs;
//	}
//
//	public void setFvprs(List<FVPeripheralRuralEntity> fvprs) {
//		this.fvprs = fvprs;
//	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}
	
//	/**
//	 * 备注
//	 */
//	@Basic(fetch = FetchType.LAZY)
//	@Column(name = "C_REMARK", columnDefinition = "TEXT")
//	private String remark;
	
	
//	//表2
//	/**
//	 * 
//	 * 小计:预算投入,完成投入
//	 * 一个片区,小计一下他所有项目预算投入,完成投入
//	 * 
//	 * 把这个片区下的所有项目的预算投入,完成投入汇总得来
//	 */
//	@Column(name = "C_BUDGETMONEY")
//	private double budgetMoney;
//	
//	@Column(name = "C_FINISHMONEY")
//	private double finishMoney;
	
	
//	//TODO:
//	/**
//	 * 小计:示范片覆盖面积,户数,人口数,主体村个数,辐射村个数,完成规划设计数,完成项目招投标数,
//	 * 			项目个数,完成个数
//	 * 
//	 * 如果该县这有一个片区,那么小计的的结果就是一条数据
//	 * 若有多个片区,那么小计的则是多个片区统计的结果
//	 */
//	
	//-------------------------------------------------------------------------------------------
	
//	// 所在的城市的ID，这样方便处理，而且以后这个县区归到其它市去，这里也应该记录下当时所属的市
//	@Column(name = "C_CITYID")
//	private long cityId;
//
//	@Column(name = "C_CITYNAME")
//	private String cityName;
	
	
	
}
