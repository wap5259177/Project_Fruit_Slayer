package cn.bonoon.entities.felicityVillage;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.entities.plugins.PlaceEntity;

/**
 * 一个年报下的多个县报,如:2014年1.1---2014-12-31
 * @author xiaowu
 *
 */
@Entity
@Table(name = "t_fvmacountyreport")
public class FVMACountyReportEntity extends AbstractFVFelicityEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6017444651371200266L;
	

	/**
	 * 示范片区，这里的地址是县区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_PLACE_ID")
	private PlaceEntity place;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_REPORT_ID")
	private FVReportEntity report;
	
	
	
	/**
	 * 一个县报下面可以有多个片区报
	 */
	@OneToMany(mappedBy = "countyReport")
	private List<FVModelAreaReportEntity> modelAreas;
	
	
	/**
	 * 9个字段   
	 * 填报人1,联系电话1,填报时间1,..2,3
	 */
	/**
	 * 填报人
	 */
	@Column(name="C_REPORTER1")
	private String reporter1;
	@Column(name="C_REPORTER2")
	private String reporter2;
	@Column(name="C_REPORTER3")
	private String reporter3;
	
	/**
	 *联系电话
	 */
	@Column(name="C_TELEPHONE1")
	private String telephone1;
	@Column(name="C_TELEPHONE2")
	private String telephone2;
	@Column(name="C_TELEPHONE3")
	private String telephone3;
	
	/**
	 * 填报时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_REPORTTIME1")
	private Date reportTime1;
	@Temporal(TemporalType.DATE)
	@Column(name = "C_REPORTTIME2")
	private Date reportTime2;
	@Temporal(TemporalType.DATE)
	@Column(name = "C_REPORTTIME3")
	private Date reportTime3;
	
	// 所在的城市的ID，这样方便处理，而且以后这个县区归到其它市去，这里也应该记录下当时所属的市
	@Column(name = "C_CITYID")
	private long cityId;

	@Column(name = "C_CITYNAME")
	private String cityName;
	
	
	/**
	 * 0：未开始
	 * 1：完成
	 * 2：进行中（正在填报）
	 * 3:驳回
	 * 4：待审核
	 */
	@Column(name = "C_STATUS")
	private int status;
	
	/**
	 * 是否已经初始化了村子,既是否已经填好了表1,只有填了表1才能填后续的表
	 */
	@Column(name = "C_ISINIT")
	private boolean isInit;
	
	
	public FVMACountyReportEntity(){}
	public FVMACountyReportEntity(FVMACountyReportEntity cReport){
		this.constructionArea = cReport.getConstructionArea();
		this.households = cReport.getHouseholds();
		this.population = cReport.getPopulation();
		
		this.place = cReport.getPlace();
		this.cityId = cReport.getCityId();
		this.cityName = cReport.getCityName();
		
		//表一
		this.crNum = cReport.getCrNum();
		this.prNum = cReport.getPrNum();
		this.finishedPlanNum = cReport.getFinishedPlanNum();
		this.finishedBidNum = cReport.getFinishedBidNum();
		this.notFinishedPlanNum = cReport.getFinishedPlanNum();
		this.notFinishedBidNum = cReport.getNotFinishedBidNum();
		
		//TODO:表二 ,表三
		
	}
	
	

	public PlaceEntity getPlace() {
		return place;
	}

	public void setPlace(PlaceEntity place) {
		this.place = place;
	}

	public FVReportEntity getReport() {
		return report;
	}

	public void setReport(FVReportEntity report) {
		this.report = report;
	}

	public List<FVModelAreaReportEntity> getModelAreas() {
		return modelAreas;
	}

	public void setModelAreas(List<FVModelAreaReportEntity> modelAreas) {
		this.modelAreas = modelAreas;
	}

	public String getReporter1() {
		return reporter1;
	}

	public void setReporter1(String reporter1) {
		this.reporter1 = reporter1;
	}

	public String getReporter2() {
		return reporter2;
	}

	public void setReporter2(String reporter2) {
		this.reporter2 = reporter2;
	}

	public String getReporter3() {
		return reporter3;
	}

	public void setReporter3(String reporter3) {
		this.reporter3 = reporter3;
	}

	public String getTelephone1() {
		return telephone1;
	}

	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	public String getTelephone2() {
		return telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	public String getTelephone3() {
		return telephone3;
	}

	public void setTelephone3(String telephone3) {
		this.telephone3 = telephone3;
	}

	public Date getReportTime1() {
		return reportTime1;
	}

	public void setReportTime1(Date reportTime1) {
		this.reportTime1 = reportTime1;
	}

	public Date getReportTime2() {
		return reportTime2;
	}

	public void setReportTime2(Date reportTime2) {
		this.reportTime2 = reportTime2;
	}

	public Date getReportTime3() {
		return reportTime3;
	}

	public void setReportTime3(Date reportTime3) {
		this.reportTime3 = reportTime3;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isInit() {
		return isInit;
	}

	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}
	
	
	
	
	
//	/**
//	 * 小计:建设资金筹集(万元)
//	 */
//	@Embedded
//	private FVInvestmentInfo investment = new FVInvestmentInfo();
	
	
	
	
}
