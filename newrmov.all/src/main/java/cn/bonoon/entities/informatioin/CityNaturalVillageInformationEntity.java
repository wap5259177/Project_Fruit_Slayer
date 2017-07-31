package cn.bonoon.entities.informatioin;

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

@Entity
@Table(name = "t_cnvi")
public class CityNaturalVillageInformationEntity extends NaturalVillageInformationEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6406416953728564715L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_PNVI_ID")
	private ProvinceNaturalVillageInformationEntity pnvInformation;
	
	@OneToMany(mappedBy = "cnvInformation")
	private List<CountyNaturalVillageInformationEntity> items;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_PLACE_ID")
	private PlaceEntity place;

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
	public PlaceEntity getPlace() {
		return place;
	}
	public void setPlace(PlaceEntity place) {
		this.place = place;
	}
	public ProvinceNaturalVillageInformationEntity getPnvInformation() {
		return pnvInformation;
	}
	public void setPnvInformation(
			ProvinceNaturalVillageInformationEntity pnvInformation) {
		this.pnvInformation = pnvInformation;
	}
	public List<CountyNaturalVillageInformationEntity> getItems() {
		return items;
	}
	public void setItems(List<CountyNaturalVillageInformationEntity> items) {
		this.items = items;
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
}
