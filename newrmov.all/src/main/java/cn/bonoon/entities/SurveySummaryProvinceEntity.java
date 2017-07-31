package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 省发启的，一个年度一个记录
 * 
 * 需要填写的选择一个年度，选择一个截止时间、选择一个开始上报时间和结束上报时间
 * 其它字段为自动生成或进行统计处理的，不要在界面进行编辑
 * @author jackson
 *
 */
@Entity
@Table(name = "t_ssprovince")
public class SurveySummaryProvinceEntity extends AbstractSurveySummaryEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7232614533506522298L;

	/**
	 * 年度
	 */
	@Column(name = "C_ANNUAL")
	private int annual;
	/**
	 * 截止时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_DEADLINE")
	private Date deadline;
	/**
	 * 开始填报时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_STARTAT")
	private Date startAt;
	/**
	 * 结束填报时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_ENDAT")
	private Date endAt;

	/**
	 * 如果省下的所有市都提交了，则自动完成
	 */
	@Column(name = "C_STATUS")
	private int status;

	/**
	 * <pre>
	 * 该记录的创建者的Id。
	 * 一般不需要显示创建者ID，只显示创建者的登录名creatorName。
	 * </pre>
	 */
	@Column(name = "C_CREATORID")
	private Long creatorId;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "C_CREATEAT", nullable = false)
	private Date createAt;

	/**
	 * <pre>
	 * 创建人的名称，登录账号。
	 * 一般可用于直接显示，如列表或查看的页面等。
	 * 一般creatorName不作为关联关系，关联关系请使用createrId。
	 * </pre>
	 */
	@Column(name = "C_CREATORNAME")
	private String creatorName;
	/**
	 * 需要上报的市的数量，全省21个市
	 */
	@Column(name = "C_NEEDREPORT")
	private int needReport;
	/**
	 * 已经开始上报，并没有完成上报的市的数量
	 */
	@Column(name = "C_STARTREPORT")
	private int startReport;
	/**
	 * 已经完成上报的数量
	 */
	@Column(name = "C_FINISHREPORT")
	private int finishReport;
	
	public int getNeedReport() {
		return needReport;
	}

	public void setNeedReport(int needReport) {
		this.needReport = needReport;
	}

	public int getStartReport() {
		return startReport;
	}

	public void setStartReport(int startReport) {
		this.startReport = startReport;
	}

	public int getFinishReport() {
		return finishReport;
	}

	public void setFinishReport(int finishReport) {
		this.finishReport = finishReport;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public int getAnnual() {
		return annual;
	}

	public void setAnnual(int annual) {
		this.annual = annual;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
