package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.kernel.support.entities.AbstractEntity;

@Entity
@Table(name = "t_project")
public class ProjectEntity extends AbstractEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2610410018928833990L;

	/**
	 * 终止原因
	 */
	@Column(name = "C_ENDREMARK")
	private String endRemark;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "R_MODELAREA_ID")
	private ModelAreaEntity modelArea;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "R_TYPE_ID")
	private ProjectTypeEntity type;

	/**
	 * 这个项目是属于某一个新农村的
	 * 
	 * 一个项目可以不止对应一个村，所以这里可能为null，也可能为主导村
	 */
	@ManyToOne
	@JoinColumn(name = "R_RURAL_ID")
	private BaseRuralEntity rural;

	/*
	 * 存储：
	 * 项目的目录就放在这个目录下面，项目相关的图片和视频以月度为单位自动建目录存放
	 * 逻辑目录：
	 * 
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "R_DIRECTORY_ID")
	private DirectoryEntity directory;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "R_DIRMEDIA_ID")
	private DirectoryEntity directoryMedia;
	
	//以下两个目录需要删除
	// 以下目录为 directory 的子目录
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name = "R_DIRECTORYDOC_ID")
//	private DirectoryEntity directoryDoc;
//	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name = "R_DIRECTORYIMG_ID")
//	private DirectoryEntity directoryImg;
	
	/**
	 * 0-未开始
	 * 1-进行中
	 * 2-竣工
	 * 3-终止
	 * 
	 * 4-退回  ：退回修改//-->不用这个状态值了 用下面的isUpdate代替
	 */
	@Column(name = "C_STATUS")
	private int status;
	
	/**
	 * 2016.5.25
	 * 是否是退回修改项目基本信息的情况
	 * 		true:县级可对该项目修改基本信息
	 * 		false:不能修改
	 */
	@Column(name = "C_ISUPDATE")
	private Boolean isUpdate;

	@Column(name = "C_CODE", length = 50)
	private String code;

	/**
	 * 项目相关的资金投入，这里是预算投入的资金
	 */
	@Embedded
	private InvestmentInfo investment = new InvestmentInfo();

	/**
	 * 总的累计已经投入使用的资金
	 */
	@Embedded
	@AttributeOverrides({
	       @AttributeOverride(name="totalFunds", column=@Column(name = "CT_TOTALFUNDS")),
	       @AttributeOverride(name="stateFunds", column=@Column(name = "CT_STATEFUNDS")),
	       @AttributeOverride(name="provinceFunds", column=@Column(name = "CT_PROVINCEFUNDS")),
	       @AttributeOverride(name="localFunds", column=@Column(name = "CT_LOCALFUNDS")),
	       @AttributeOverride(name="specialFunds", column=@Column(name = "CT_SPECIALFUNDS")),
	       @AttributeOverride(name="socialFunds", column=@Column(name = "CT_SOCIALFUNDS")),
	       @AttributeOverride(name="massFunds", column=@Column(name = "CT_MASSFUNDS")),
	       @AttributeOverride(name="otherFunds", column=@Column(name = "CT_OTHERFUNDS")),
	       @AttributeOverride(name="cityFunds", column=@Column(name = "CT_CITYFUNDS")),
	       @AttributeOverride(name="countyFunds", column=@Column(name = "CT_COUNTYFUNDS"))
	   })
	private InvestmentInfo totalInvestment = new InvestmentInfo();

	/**
	 * 原始的累计投入资金
	 */
	@Embedded
	@AttributeOverrides({
	       @AttributeOverride(name="totalFunds", column=@Column(name = "ST_TOTALFUNDS")),
	       @AttributeOverride(name="stateFunds", column=@Column(name = "ST_STATEFUNDS")),
	       @AttributeOverride(name="provinceFunds", column=@Column(name = "ST_PROVINCEFUNDS")),
	       @AttributeOverride(name="localFunds", column=@Column(name = "ST_LOCALFUNDS")),
	       @AttributeOverride(name="specialFunds", column=@Column(name = "ST_SPECIALFUNDS")),
	       @AttributeOverride(name="socialFunds", column=@Column(name = "ST_SOCIALFUNDS")),
	       @AttributeOverride(name="massFunds", column=@Column(name = "ST_MASSFUNDS")),
	       @AttributeOverride(name="otherFunds", column=@Column(name = "ST_OTHERFUNDS")),
	       @AttributeOverride(name="cityFunds", column=@Column(name = "ST_CITYFUNDS")),
	       @AttributeOverride(name="countyFunds", column=@Column(name = "ST_COUNTYFUNDS"))
	   })
	private InvestmentInfo sourceInvestment = new InvestmentInfo();
	

	//片区名称
	@Column(name = "C_AREANAME")
	private String areaName;
	
	//镇
	@Column(name = "C_TOWN")
	private String town;
	
	//村名
	@Column(name = "C_VILLAGENAME", length = 200)
	private String villageName;
	
	// 项目开工时间年
	@Column(name = "C_STARTATY")
	private int startAtY;
	
	// 项目开工时间月
	@Column(name = "C_STARTATM")
	private int startAtM;

	// 项目预计竣工时间年
	@Column(name = "C_ENDATY")
	private int endAtY;
	
	// 项目预计竣工时间月
	@Column(name = "C_ENDATM")
	private int endAtM;
	
	// 项目实际竣工时间
	@Column(name = "C_FINISHATY")
	private int finishAtY;
	
	// 项目实际竣工时间
	@Column(name = "C_FINISHATM")
	private int finishAtM;

	@Temporal(TemporalType.DATE)
	@Column(name = "C_STOPAT")
	private Date stopAt;

	//填报日期
	@Temporal(TemporalType.DATE)
	@Column(name = "C_REPORTAT")
	private Date reportAt;
	
	//属性
	@Column(name = "C_PROPROPERTY")
	private String proProperty;
	
	//项目类别
	@Column(name = "C_PROJECTTYPE")
	private String projectType;
	
	//具体类别
	@Column(name = "C_EXACTKIND")
	private String exactKind;
	
	//投工数
	//最开始的时候用户填写的，所以不应该被改变，需要用另一个字段进行记录累计的情况
	@Column(name = "C_LABOURCOUNT")
	private int labourCount;
	@Column(name = "C_SLABOURCOUNT")
	private Integer sumLabourCount;
	
	//折资额
	//最开始的时候用户填写的，所以不应该被改变，需要用另一个字段进行记录累计的情况
	@Column(name = "C_SPEND")
	private double spend;
	@Column(name = "C_SSPEND")
	private Double sumSpend;//这里是对应上一个字段累计的情况

	@Column(name = "SC_LABOURCOUNT")
	private int sourceLabourCount;

	@Column(name = "SC_SPEND")
	private double sourceSpend;

	//项目是否覆盖多个自然村20160127
	@Column(name = "COVER")
	private String  cover;
	
	public ProjectTypeEntity getType() {
		return type;
	}

	public void setType(ProjectTypeEntity type) {
		this.type = type;
	}


	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

//	public NewRuralEntity getNewRural() {
//		return newRural;
//	}
//
//	public void setNewRural(NewRuralEntity newRural) {
//		this.newRural = newRural;
//	}

	public InvestmentInfo getInvestment() {
		if(investment==null)
			investment=new InvestmentInfo();
		return investment;
	}

	public void setInvestment(InvestmentInfo investment) {
		this.investment = investment;
	}

	public DirectoryEntity getDirectory() {
		return directory;
	}

	public void setDirectory(DirectoryEntity directory) {
		this.directory = directory;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

//	public DirectoryEntity getDirectoryImg() {
//		return directoryImg;
//	}
//
//	public void setDirectoryImg(DirectoryEntity directoryImg) {
//		this.directoryImg = directoryImg;
//	}

	public Date getReportAt() {
		return reportAt;
	}

	public void setReportAt(Date reportAt) {
		this.reportAt = reportAt;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getProProperty() {
		return proProperty;
	}

	public void setProProperty(String proProperty) {
		this.proProperty = proProperty;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public int getLabourCount() {
		return labourCount;
	}

	public void setLabourCount(int labourCount) {
		this.labourCount = labourCount;
	}

	public double getSpend() {
		return spend;
	}

	public void setSpend(double spend) {
		this.spend = spend;
	}

	public String getExactKind() {
		return exactKind;
	}

	public void setExactKind(String exactKind) {
		this.exactKind = exactKind;
	}

	public int getStartAtY() {
		return startAtY;
	}

	public void setStartAtY(int startAtY) {
		this.startAtY = startAtY;
	}

	public int getStartAtM() {
		return startAtM;
	}

	public void setStartAtM(int startAtM) {
		this.startAtM = startAtM;
	}

	public int getEndAtY() {
		return endAtY;
	}

	public void setEndAtY(int endAtY) {
		this.endAtY = endAtY;
	}

	public int getEndAtM() {
		return endAtM;
	}

	public void setEndAtM(int endAtM) {
		this.endAtM = endAtM;
	}

	public int getFinishAtY() {
		return finishAtY;
	}

	public void setFinishAtY(int finishAtY) {
		this.finishAtY = finishAtY;
	}

	public int getFinishAtM() {
		return finishAtM;
	}

	public void setFinishAtM(int finishAtM) {
		this.finishAtM = finishAtM;
	}

	public ModelAreaEntity getModelArea() {
		return modelArea;
	}

	public void setModelArea(ModelAreaEntity modelArea) {
		this.modelArea = modelArea;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public DirectoryEntity getDirectoryDoc() {
//		return directoryDoc;
//	}
//
//	public void setDirectoryDoc(DirectoryEntity directoryDoc) {
//		this.directoryDoc = directoryDoc;
//	}

	public InvestmentInfo getTotalInvestment() {
		if(totalInvestment==null)
			totalInvestment=new InvestmentInfo();
		return totalInvestment;
	}

	public void setTotalInvestment(InvestmentInfo totalInvestment) {
		this.totalInvestment = totalInvestment;
	}

	public BaseRuralEntity getRural() {
		return rural;
	}

	public void setRural(BaseRuralEntity rural) {
		this.rural = rural;
	}

	public Date getStopAt() {
		return stopAt;
	}

	public void setStopAt(Date stopAt) {
		this.stopAt = stopAt;
	}

	public InvestmentInfo getSourceInvestment() {
		if(sourceInvestment==null)
			sourceInvestment=new InvestmentInfo();
		return sourceInvestment;
	}

	public void setSourceInvestment(InvestmentInfo sourceInvestment) {
		this.sourceInvestment = sourceInvestment;
	}

	public int getSourceLabourCount() {
		return sourceLabourCount;
	}

	public void setSourceLabourCount(int sourceLabourCount) {
		this.sourceLabourCount = sourceLabourCount;
	}

	public double getSourceSpend() {
		return sourceSpend;
	}

	public void setSourceSpend(double sourceSpend) {
		this.sourceSpend = sourceSpend;
	}

	public DirectoryEntity getDirectoryMedia() {
		return directoryMedia;
	}

	public void setDirectoryMedia(DirectoryEntity directoryMedia) {
		this.directoryMedia = directoryMedia;
	}

	public String getEndRemark() {
		return endRemark;
	}

	public void setEndRemark(String endRemark) {
		this.endRemark = endRemark;
	}

	public Integer getSumLabourCount() {
		return sumLabourCount;
	}

	public void setSumLabourCount(Integer sumLabourCount) {
		this.sumLabourCount = sumLabourCount;
	}

	public Double getSumSpend() {
		return sumSpend;
	}

	public void setSumSpend(Double sumSpend) {
		this.sumSpend = sumSpend;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Boolean getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
}