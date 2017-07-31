package cn.bonoon.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 示范片区的季报，由省工作人员创建。
 * 一个季度同时进行着不同的批次，所以当前季度有几批片区则需要创建几个{@link ModelAreaQuarterBatch}记录
 * @author jackson
 *
 */
@Entity
@Table(name = "t_maquarter")
public class ModelAreaQuarterEntity extends AbstractModelAreaQuarter{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5796874196010446729L;
	
	@OneToMany(mappedBy = "quarter")
	private List<ModelAreaQuarterBatch> batchs;
	
	/**
	 * 如果省下的所有市都提交了，则自动完成
	 */
	@Column(name = "C_STATUS")
	private int status;
	/**
	 * 年度
	 */
	@Column(name = "C_ANNUAL")
	private int annual;

	/**
	 * <pre>
	 * 季报：为一年中的4个季度，
	 *     0-第一季度
	 *     1-第二季度
	 *     2-第三季度
	 *     3-第四季度
	 * </pre>
	 */
	@Column(name = "C_PERIOD")
	private int period;
	
	/**
	 * 有多少个批次需要上报数据，目前最多只有三个批次
	 */
	@Column(name = "C_BATCHCOUNT")
	private int batchCount;
	/**
	 * 所有需要上报的片区数量
	 */
	@Column(name = "C_MACOUNT")
	private int maCount;
	
	/**
	 * 年度+季度，用来排序的字段
	 */
	@Column(name = "C_ORDINAL")
	private int ordinal;
	
	/**
	 * 截止时间，即统计截止的时间，如，第一季度是1、2、3三个月份，
	 * 
	 * 统计截止的时间到3月份的5号或2月份28号等情况
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
	 * 是否锁定季度20160122
	 */
	@Column(name = "C_ISLOCK")
	private boolean isLock;

	public List<ModelAreaQuarterBatch> getBatchs() {
		return batchs;
	}

	public void setBatchs(List<ModelAreaQuarterBatch> batchs) {
		this.batchs = batchs;
	}

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

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getBatchCount() {
		return batchCount;
	}

	public void setBatchCount(int batchCount) {
		this.batchCount = batchCount;
	}

	public int getMaCount() {
		return maCount;
	}

	public void setMaCount(int maCount) {
		this.maCount = maCount;
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

	public boolean isLock() {
		return isLock;
	}

	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	

}
