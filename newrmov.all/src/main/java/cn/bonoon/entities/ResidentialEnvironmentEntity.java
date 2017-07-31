package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.kernel.support.entities.AbstractPersistable;

/**
 * 人居环境整治
 * @author jackson
 *
 */
@Entity
@Table(name = "t_residentialenv")
public class ResidentialEnvironmentEntity extends AbstractPersistable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1977525953912876801L;
   
	@ManyToOne
	@JoinColumn(name = "R_RURAL_ID")
	private BaseRuralEntity rural;
	/**
	 * 年份
	 */
	@Column(name = "C_ANNUAL")
	private int annual;
	/**
	 * 序号
	 */
	@Column(name = "C_ORDINAL")
	private int ordinal;
	
	@Column(name = "C_TOWNNAME", length = 200)
	private String townName;
	
	@Column(name = "C_VILLAGENAME", length = 200)
	private String villageName;
	
	@Column(name = "C_NATURALVILLAGE", length = 200)
	private String naturalVillage;
	/**
	 * 创建人id
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
	 * 创建人名称
	 */
	@Column(name = "C_CREATORNAME")
	private String creatorName;
	/**
	 * 状态id
	 */
	@Column(name = "C_OWNERID")
	private long ownerId;

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

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public BaseRuralEntity getRural() {
		return rural;
	}

	public void setRural(BaseRuralEntity rural) {
		this.rural = rural;
	}

	public int getAnnual() {
		return annual;
	}

	public void setAnnual(int annual) {
		this.annual = annual;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getNaturalVillage() {
		return naturalVillage;
	}

	public void setNaturalVillage(String naturalVillage) {
		this.naturalVillage = naturalVillage;
	}
}
