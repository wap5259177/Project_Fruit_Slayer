package cn.bonoon.entities.informatioin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_pnvi")
public class ProvinceNaturalVillageInformationEntity extends NaturalVillageInformationEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3758262371339016945L;

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
	 */
	@Column(name = "C_STATUS")
	private int status;

	/**
	 * 系统开始填报时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_STARTAT")
	private Date startAt;
	/**
	 * 系统结束填报时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_ENDAT")
	private Date endAt;
	/**
	 * 统计截止时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_DEADLINE")
	private Date deadline;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
}
