package cn.bonoon.entities.ruralBuild;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.controllers.project.report.crbuild.CRBuildQuarterDetail;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.UnitEntity;

/**
 * 在同一个批次下的一个示范片区为一条记录，也是示范片区的工作人员需要填写的季度报表的内容
 * @author jackson
 *
 */
@Entity
@Table(name = "t_macrbuilditem")
public class ModelAreaCRBuildQuarterItem extends AbstractModelAreaQuarterCRBuild{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 554619020243502504L;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_BATCH_ID")
	private ModelAreaCRBuildQuarterBatch batch;


	@Column(name = "C_ORDINAL")
	private Integer ordinal;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_MODELAREA_ID")
	private ModelAreaEntity modelArea;
	
	/**
	 * 表示不需要进行季度报表的片区，省办管理人员可以随时进行某一个片区是否需要上报季度报表的设置
	 */
	@Column(name = "C_DISABLED")
	private boolean disabled;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_CITYUNIT_ID")
	private UnitEntity cityUnit;
	
	
	@Column(name = "C_CITYID")
	private Long cityId;
	@Column(name = "C_CITYNAME")
	private String cityName;
	
	//下面是季报的一些审核信息
	@Column(name = "C_AUDITNAME")
	private String auditName;
	@Temporal(TemporalType.DATE)
	@Column(name = "C_AUDITAT")
	private Date auditAt;
	@Column(name = "C_AUDITCONTENT")
	private String auditContent;
	
	//联系人
	@Column(name = "C_REPORTNAME")
	private String reportName;
	//填报时间
	@Temporal(TemporalType.DATE)
	@Column(name = "C_REPORTDATE")
	private Date reportDate;
	
	/**
	 * 催报，对着县级进行催报
	 */
	@Column(name = "C_URGE")
	private int urge;
	/**
	 * 如果省下的所有市都提交了，则自动完成
	 */
	@Column(name = "C_STATUS")
	private int status;
	
	/**
	 * 是否锁定季度20160122
	 */
	@Column(name = "C_ISLOCK")
	private boolean isLock;

	public ModelAreaCRBuildQuarterBatch getBatch() {
		return batch;
	}

	public void setBatch(ModelAreaCRBuildQuarterBatch batch) {
		this.batch = batch;
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public ModelAreaEntity getModelArea() {
		return modelArea;
	}

	public void setModelArea(ModelAreaEntity modelArea) {
		this.modelArea = modelArea;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public UnitEntity getCityUnit() {
		return cityUnit;
	}

	public void setCityUnit(UnitEntity cityUnit) {
		this.cityUnit = cityUnit;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public int getUrge() {
		return urge;
	}

	public void setUrge(int urge) {
		this.urge = urge;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isLock() {
		return isLock;
	}

	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public ModelAreaCRBuildQuarterItem copy(CRBuildQuarterDetail detail) {
		this.setDismantleOldHouseNum(detail.getDismantleOldHouseNum());
		this.setDismantleOldHouseArea(detail.getDismantleOldHouseArea());
		this.setCleanRubbish(detail.getCleanRubbish());
		this.setGreenArea(detail.getGreenArea());
		this.setAfforestationTree(detail.getAfforestationTree());
		this.setHardRoad(detail.getHardRoad());
		this.setCompleteToilet(detail.getCompleteToilet());
		this.setCompleteFacade(detail.getCompleteFacade());
		this.setRainShunt(detail.getRainShunt());
		this.setSewageTreatment(detail.getSewageTreatment());
		this.setTapWater(detail.getTapWater());
		this.setRemark(detail.getRemark());
		this.setReportDate(detail.getReportDate());
		this.setReportName(detail.getReportName());
		return this;
	}
	
	
	
	
}
