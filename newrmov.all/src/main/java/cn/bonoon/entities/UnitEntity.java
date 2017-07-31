package cn.bonoon.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cn.bonoon.entities.plugins.OwnerEntity;
import cn.bonoon.entities.plugins.PlaceEntity;

/**
 * 上报单位
 * @author ocean~
 */
@Entity
public class UnitEntity extends OwnerEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3439419047469246323L;

	@ManyToOne
	@JoinColumn(name = "R_PLACE_ID")
	private PlaceEntity place;

	// isCity == 城市 ? 1:0
	private int isCity;

	//1表示该单位是片区单位，需要处理与片区相关的信息
	private int exttype;
	
	public PlaceEntity getPlace() {
		return place;
	}

	public void setPlace(PlaceEntity place) {
		this.place = place;
	}

	public int isCity() {
		return isCity;
	}

	public void setCity(int isCity) {
		this.isCity = isCity;
	}

	public int getExttype() {
		return exttype;
	}

	public void setExttype(int exttype) {
		this.exttype = exttype;
	}
}
