package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.support.entities.AbstractPersistable;

/**
 * 省农办的专项资金
 * @author jackson
 *
 */
@Entity
@Table(name = "t_specialfunds")
public class SpecialFundsEntity extends AbstractPersistable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2898763770420431750L;

	/**
	 * 1:正常   -1:禁止  0:草稿
	 */
	@Column(name = "C_STATUS")
	private int status;
	
	//年度
	@Column(name = "C_ANNUAL")
	private int annual;
	
	//额度，如，1亿，这里是万为单位
	@Column(name = "C_QUOTA")
	private double quota;
	
	/**
	 * 这里是已分配的资金；
	 * 已分配资金是由市里分配出去的；
	 * 县提交申请单，如果市里通过了，则需要填写市分配给该县的资金额度；
	 * 市里分配出去多少，这时就会记录多少；
	 * 正常情况下最后的分配资金与省下来的额度相同，即：quota=allocated
	 */
	@Column(name = "C_ALLOCATED")
	private double allocated;

	//联系人
	@Column(name = "C_CONTACTNAME")
	private String contactName;

	//联系电话
	@Column(name = "C_CONTACTPHONE")
	private String contactPhone;
	
	/**
	 * 下拨时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_RECORDAT", nullable = false)
	private Date recordAt;
	
	//地区，市级的
	@ManyToOne
	@JoinColumn(name = "R_PLACE_ID")
	private PlaceEntity place;
	
	@Column(name = "C_CREATORID")
	private Long creatorId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "C_CREATEAT", nullable = false)
	private Date createAt;
	
	@Column(name = "C_CREATORNAME")
	private String creatorName;
	
	//备注
	@Column(name = "C_REMARK", length = 600)
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getAnnual() {
		return annual;
	}

	public void setAnnual(int annual) {
		this.annual = annual;
	}

	public double getQuota() {
		return quota;
	}

	public void setQuota(double quota) {
		this.quota = quota;
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

	public Date getRecordAt() {
		return recordAt;
	}

	public void setRecordAt(Date recordAt) {
		this.recordAt = recordAt;
	}

	public PlaceEntity getPlace() {
		return place;
	}

	public void setPlace(PlaceEntity place) {
		this.place = place;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getAllocated() {
		return allocated;
	}

	public void setAllocated(double allocated) {
		this.allocated = allocated;
	}
	
}
