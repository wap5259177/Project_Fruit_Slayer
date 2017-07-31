package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.support.entities.AbstractEntity;

/**
 * <pre>
 * 示范片
 * 每一个示范片下面有5个左右的核心村
 * 每个核心村都会带动周边的村
 * </pre>
 * 
 * @author jackson
 * 
 */
@Entity
@Table(name = "t_modelarea")
public class ModelAreaEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1202834700169224455L;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_MODELAREAIMG")
	private FileEntity modelAreaImg;

	/**
	 * 自定义的排序
	 */
	@Column(name = "C_ORDMODEL")
	private Integer ordinalModel;
	
	// 放文件夹的实体的引用，所有上传的文件都会被归入这个文件夹下面，在文档系统里上传的文件，也会被归到该项目里来
	// 存放文档的目录
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_DIRECTORY_ID")
	private DirectoryEntity directory;

	// 存放项目文件的目录
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_DIRPROJECT_ID")
	private DirectoryEntity directoryProject;

	// 存放核心村的目录
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_DIRRURAL_ID")
	private DirectoryEntity directoryRural;

	/**
	 * 所属的专项资金；这个只有在市级审核通过的时候才会建立关联关系
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_SPECIALFUNDS_ID")
	private SpecialFundsEntity specialFunds;

	/**
	 * 示范片区，这里的地址是县区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_PLACE_ID")
	private PlaceEntity place;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_UNIT_ID")
	private UnitEntity unit;

	/**
	 * 台账编码
	 */
	@Column(name = "C_CODE", length = 50)
	private String code;

	/**
	 * 片区介绍
	 */
	@Column(name = "C_INTRODUCE")
	private String introduce;

	/**
	 * 常住人口
	 */
	@Column(name = "C_POPULATION")
	private long population;

	/**
	 * 某一个示范片区市级所分配下来的省专项资金的额度
	 */
	@Column(name = "C_QUOTA")
	private double quota;

	// 所在的城市的ID，这样方便处理，而且以后这个县区归到其它市去，这里也应该记录下当时所属的市
	@Column(name = "C_CITYID")
	private long cityId;

	@Column(name = "C_CITYNAME")
	private String cityName;

	// 申请相关
	@Column(name = "C_CONTACTNAME")
	private String contactName;
	@Column(name = "C_CONTACTPHONE")
	private String contactPhone;
	// 20150513新增加以下两个字段
	@Column(name = "C_CONTACTJOB")
	private String contactJob;// 职务、职位
	@Column(name = "C_CONTACTPHONE2")
	private String contactPhone2;// 联系电话2
	@Column(name = "C_CONTACTQQNUMBER")
	private String contactQQNum;// 联系qq

	@Temporal(TemporalType.DATE)
	@Column(name = "C_APPLICATAT")
	private Date applicatAt;

	@Column(name = "C_STATUS")
	private int status;// 0草稿、1提高待审核、2审核通过、3审核不通过

	// 审核相关
	@Column(name = "C_AUDITNAME")
	private String auditName;
	@Temporal(TemporalType.DATE)
	@Column(name = "C_AUDITAT")
	private Date auditAt;
	@Column(name = "C_AUDITCONTENT")
	private String auditContent;
	// 20150513 增加审核结果，通过或驳回
	@Column(name = "C_AUDITRESULT")
	private String auditResult;

	@Embedded
	private InvestmentInfo investment = new InvestmentInfo();

	/**
	 * 2.25修改
	 * 
	 */
	// 全县农村面积
	@Column(name = "C_RURALSAREA")
	private double ruralsArea;

	// 乡镇个数
	@Column(name = "C_TOWNNUMBER")
	private int townNumber;

	// 行政村个数
	@Column(name = "C_ADMINVN")
	private int adminVN;

	// 自然村个数
	@Column(name = "C_NATURALVN")
	private int naturalVN;

	// 村民小组数
	@Column(name = "C_VILLAGERSGROUP")
	private int villagersGroup;

	// 县人口总户数
	@Column(name = "C_POPHOUS")
	private int popHous;

	// 县农户总数
	@Column(name = "C_FARMERHOUS")
	private int farmerHous;

	// 县农村总人口数
	@Column(name = "C_SUMFARMERS")
	private int sumFarmers;

	// 县总人口数
	@Column(name = "C_SUMTOWNPOPU")
	private int sumTownPopu;

	// 2013城镇居民人均收入
	@Column(name = "C_CI3")
	private double ci3;
	@Column(name = "C_CI4")
	private double ci4;
	@Column(name = "C_CI5")
	private double ci5;
	@Column(name = "C_CI6")
	private double ci6;
	@Column(name = "C_CI7")
	private double ci7;

	// 2013农民人均收入
	@Column(name = "C_VI3")
	private double vi3;
	@Column(name = "C_VI4")
	private double vi4;
	@Column(name = "C_VI5")
	private double vi5;
	@Column(name = "C_VI6")
	private double vi6;
	@Column(name = "C_VI7")
	private double vi7;

	// 县名
	@Column(name = "C_COUNTY")
	private String county;

	// 建设主题名称
	@Column(name = "C_THEMENAME")
	private String themeName;

	// 报备年度
	@Column(name = "C_REPORTANNUAL")
	private String reportAnnual;

	// 批次一
	@Column(name = "C_BATCH")
	private String batch;

	// 主体村个数
	@Column(name = "C_MAINBUILD")
	private int mainBuild;

	// 非主体村个数
	@Column(name = "C_AROUND")
	private int around;

	// 主体建设村的行政村个数
	@Column(name = "C_SUMMAINADMIN")
	private int sumMAdmin;
	// 主体建设村的自然村个数
	@Column(name = "C_SUMMAINRURAL")
	private int sumMRural;
	// 非主体建设村的行政村个数
	@Column(name = "C_SUMAROUNDADMIN")
	private int sumArAdmin;
	// 非主体建设村的自然村个数
	@Column(name = "C_SUMAROUNDRURAL")
	private int sumArRural;
	// 覆盖乡镇数
	@Column(name = "C_COVERTOWN")
	private int coverTown;

	// 示范片面积
	@Column(name = "C_AREAACREAGE")
	private double areaAcreage;

	// 总户数
	@Column(name = "C_SUMHOUSE")
	private int sumHouse;

	// 总人口数
	@Column(name = "C_SUMP")
	private int sumP;

	// 20150703新添加的六个字段
	// 主体建设村示范片面积
	@Column(name = "C_MAINAREAACREAGE")
	private double mainAreaAcreage;

	// 非主体建设村示范片面积
	@Column(name = "C_AROUNDAREAACREAGE")
	private double aroundAreaAcreage;

	// 主体建设村总户数
	@Column(name = "C_MAINSUMHOUSE")
	private int mainSumHouse;

	// 非主体建设村总户数
	@Column(name = "C_AROUNDSUMHOUSE")
	private int aroundSumHouse;

	// 主体建设村总人口数
	@Column(name = "C_MAINSUMP")
	private int mainSumP;

	// 非主体建设村总人口数
	@Column(name = "C_AROUNDSUMP")
	private int aroundSumP;

	// 连线规模
	@Column(name = "C_LINESCALE")
	private double lineScale;

	// 起点标志
	@Column(name = "C_STARTMARK")
	private String startMark;

	// 规划设计标识
	@Column(name = "C_PLANMARK")
	private int planMark;

	// TODO string->int
	// 驿站
	@Column(name = "C_POSTCOUNT")
	private int postCount;

	// 绿道
	@Column(name = "C_GREENROAD")
	private double greenRoad;

	// 观景台
	@Column(name = "C_VIEWPLATFORM")
	private int viewPlatform;

	// 宣传介绍牌
	@Column(name = "C_INTROCARD")
	private int introCard;

	// 道路两旁美化绿化工程
	@Column(name = "C_GREENPROJECT")
	private int greenProject;

	// 示范片连线线路
	@Column(name = "C_AREAROAD")
	private String areaRoad;
	
	/** 表示这个地区是否是珠三角地区的城市.20161025 */
	@Column(name = "C_ZSJ")
	private boolean zsj;

	public boolean isZsj() {
		return zsj;
	}

	public void setZsj(boolean zsj) {
		this.zsj = zsj;
	}

	public SpecialFundsEntity getSpecialFunds() {
		return specialFunds;
	}

	public void setSpecialFunds(SpecialFundsEntity specialFunds) {
		this.specialFunds = specialFunds;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public double getQuota() {
		return quota;
	}

	public void setQuota(double quota) {
		this.quota = quota;
	}

	public PlaceEntity getPlace() {
		return place;
	}

	public void setPlace(PlaceEntity place) {
		this.place = place;
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

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Date getApplicatAt() {
		return applicatAt;
	}

	public void setApplicatAt(Date applicatAt) {
		this.applicatAt = applicatAt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public DirectoryEntity getDirectory() {
		return directory;
	}

	public void setDirectory(DirectoryEntity directory) {
		this.directory = directory;
	}

	// public DirectoryEntity getDirectoryImg() {
	// return directoryImg;
	// }
	//
	// public void setDirectoryImg(DirectoryEntity directoryImg) {
	// this.directoryImg = directoryImg;
	// }

	public InvestmentInfo getInvestment() {
		return investment;
	}

	public void setInvestment(InvestmentInfo investment) {
		this.investment = investment;
	}

	public double getRuralsArea() {
		return ruralsArea;
	}

	public void setRuralsArea(double ruralsArea) {
		this.ruralsArea = ruralsArea;
	}

	public int getTownNumber() {
		return townNumber;
	}

	public void setTownNumber(int townNumber) {
		this.townNumber = townNumber;
	}

	public int getAdminVN() {
		return adminVN;
	}

	public void setAdminVN(int adminVN) {
		this.adminVN = adminVN;
	}

	public int getNaturalVN() {
		return naturalVN;
	}

	public void setNaturalVN(int naturalVN) {
		this.naturalVN = naturalVN;
	}

	public int getFarmerHous() {
		return farmerHous;
	}

	public void setFarmerHous(int farmerHous) {
		this.farmerHous = farmerHous;
	}

	public int getSumFarmers() {
		return sumFarmers;
	}

	public void setSumFarmers(int sumFarmers) {
		this.sumFarmers = sumFarmers;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getReportAnnual() {
		return reportAnnual;
	}

	public void setReportAnnual(String reportAnnual) {
		this.reportAnnual = reportAnnual;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public int getMainBuild() {
		return mainBuild;
	}

	public void setMainBuild(int mainBuild) {
		this.mainBuild = mainBuild;
	}

	public int getAround() {
		return around;
	}

	public void setAround(int around) {
		this.around = around;
	}

	public int getCoverTown() {
		return coverTown;
	}

	public void setCoverTown(int coverTown) {
		this.coverTown = coverTown;
	}

	public double getAreaAcreage() {
		return areaAcreage;
	}

	public void setAreaAcreage(double areaAcreage) {
		this.areaAcreage = areaAcreage;
	}

	public int getSumHouse() {
		return sumHouse;
	}

	public void setSumHouse(int sumHouse) {
		this.sumHouse = sumHouse;
	}

	public int getSumP() {
		return sumP;
	}

	public void setSumP(int sumP) {
		this.sumP = sumP;
	}

	public int getVillagersGroup() {
		return villagersGroup;
	}

	public void setVillagersGroup(int villagersGroup) {
		this.villagersGroup = villagersGroup;
	}

	public double getCi3() {
		return ci3;
	}

	public void setCi3(double ci3) {
		this.ci3 = ci3;
	}

	public double getCi4() {
		return ci4;
	}

	public void setCi4(double ci4) {
		this.ci4 = ci4;
	}

	public double getCi5() {
		return ci5;
	}

	public void setCi5(double ci5) {
		this.ci5 = ci5;
	}

	public double getCi6() {
		return ci6;
	}

	public void setCi6(double ci6) {
		this.ci6 = ci6;
	}

	public double getCi7() {
		return ci7;
	}

	public void setCi7(double ci7) {
		this.ci7 = ci7;
	}

	public double getVi3() {
		return vi3;
	}

	public void setVi3(double vi3) {
		this.vi3 = vi3;
	}

	public double getVi4() {
		return vi4;
	}

	public void setVi4(double vi4) {
		this.vi4 = vi4;
	}

	public double getVi5() {
		return vi5;
	}

	public void setVi5(double vi5) {
		this.vi5 = vi5;
	}

	public double getVi6() {
		return vi6;
	}

	public void setVi6(double vi6) {
		this.vi6 = vi6;
	}

	public double getVi7() {
		return vi7;
	}

	public void setVi7(double vi7) {
		this.vi7 = vi7;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public double getLineScale() {
		return lineScale;
	}

	public void setLineScale(double lineScale) {
		this.lineScale = lineScale;
	}

	public String getStartMark() {
		return startMark;
	}

	public void setStartMark(String startMark) {
		this.startMark = startMark;
	}

	public int getPlanMark() {
		return planMark;
	}

	public void setPlanMark(int planMark) {
		this.planMark = planMark;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	public double getGreenRoad() {
		return greenRoad;
	}

	public void setGreenRoad(double greenRoad) {
		this.greenRoad = greenRoad;
	}

	public int getViewPlatform() {
		return viewPlatform;
	}

	public void setViewPlatform(int viewPlatform) {
		this.viewPlatform = viewPlatform;
	}

	public int getIntroCard() {
		return introCard;
	}

	public void setIntroCard(int introCard) {
		this.introCard = introCard;
	}

	public int getGreenProject() {
		return greenProject;
	}

	public void setGreenProject(int greenProject) {
		this.greenProject = greenProject;
	}

	public String getAreaRoad() {
		return areaRoad;
	}

	public void setAreaRoad(String areaRoad) {
		this.areaRoad = areaRoad;
	}

	public int getSumTownPopu() {
		return sumTownPopu;
	}

	public void setSumTownPopu(int sumTownPopu) {
		this.sumTownPopu = sumTownPopu;
	}

	public int getPopHous() {
		return popHous;
	}

	public void setPopHous(int popHous) {
		this.popHous = popHous;
	}

	public UnitEntity getUnit() {
		return unit;
	}

	public void setUnit(UnitEntity unit) {
		this.unit = unit;
	}

	//
	// public DirectoryEntity getDirectoryDoc() {
	// return directoryDoc;
	// }
	//
	// public void setDirectoryDoc(DirectoryEntity directoryDoc) {
	// this.directoryDoc = directoryDoc;
	// }
	//
	// public DirectoryEntity getDirectoryProj() {
	// return directoryProj;
	// }
	//
	// public void setDirectoryProj(DirectoryEntity directoryProj) {
	// this.directoryProj = directoryProj;
	// }
	//
	// public DirectoryEntity getDirectoryNrl() {
	// return directoryNrl;
	// }
	//
	// public void setDirectoryNrl(DirectoryEntity directoryNrl) {
	// this.directoryNrl = directoryNrl;
	// }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAuditContent() {
		return auditContent;
	}

	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}

	public String getContactJob() {
		return contactJob;
	}

	public void setContactJob(String contactJob) {
		this.contactJob = contactJob;
	}

	public String getContactPhone2() {
		return contactPhone2;
	}

	public void setContactPhone2(String contactPhone2) {
		this.contactPhone2 = contactPhone2;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public DirectoryEntity getDirectoryProject() {
		return directoryProject;
	}

	public void setDirectoryProject(DirectoryEntity directoryProject) {
		this.directoryProject = directoryProject;
	}

	public DirectoryEntity getDirectoryRural() {
		return directoryRural;
	}

	public void setDirectoryRural(DirectoryEntity directoryRural) {
		this.directoryRural = directoryRural;
	}

	public double getMainAreaAcreage() {
		return mainAreaAcreage;
	}

	public void setMainAreaAcreage(double mainAreaAcreage) {
		this.mainAreaAcreage = mainAreaAcreage;
	}

	public double getAroundAreaAcreage() {
		return aroundAreaAcreage;
	}

	public void setAroundAreaAcreage(double aroundAreaAcreage) {
		this.aroundAreaAcreage = aroundAreaAcreage;
	}

	public int getMainSumHouse() {
		return mainSumHouse;
	}

	public void setMainSumHouse(int mainSumHouse) {
		this.mainSumHouse = mainSumHouse;
	}

	public int getAroundSumHouse() {
		return aroundSumHouse;
	}

	public void setAroundSumHouse(int aroundSumHouse) {
		this.aroundSumHouse = aroundSumHouse;
	}

	public int getMainSumP() {
		return mainSumP;
	}

	public void setMainSumP(int mainSumP) {
		this.mainSumP = mainSumP;
	}

	public int getAroundSumP() {
		return aroundSumP;
	}

	public void setAroundSumP(int aroundSumP) {
		this.aroundSumP = aroundSumP;
	}

	public int getSumMAdmin() {
		return sumMAdmin;
	}

	public void setSumMAdmin(int sumMAdmin) {
		this.sumMAdmin = sumMAdmin;
	}

	public int getSumMRural() {
		return sumMRural;
	}

	public void setSumMRural(int sumMRural) {
		this.sumMRural = sumMRural;
	}

	public int getSumArAdmin() {
		return sumArAdmin;
	}

	public void setSumArAdmin(int sumArAdmin) {
		this.sumArAdmin = sumArAdmin;
	}

	public int getSumArRural() {
		return sumArRural;
	}

	public void setSumArRural(int sumArRural) {
		this.sumArRural = sumArRural;
	}

	public Integer getOrdinalModel() {
		return ordinalModel;
	}

	public void setOrdinalModel(Integer ordinalModel) {
		this.ordinalModel = ordinalModel;
	}

	public String getContactQQNum() {
		return contactQQNum;
	}

	public void setContactQQNum(String contactQQNum) {
		this.contactQQNum = contactQQNum;
	}

	public FileEntity getModelAreaImg() {
		return modelAreaImg;
	}

	public void setModelAreaImg(FileEntity modelAreaImg) {
		this.modelAreaImg = modelAreaImg;
	}
	
}
