package cn.bonoon.entities.felicityVillage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.bonoon.kernel.support.entities.AbstractPersistable;


/**
 * 村字典
 * @author xiaowu
 *
 */
@Entity
@Table(name = "t_fvdicrural")
public class FVRuralEntity extends AbstractPersistable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4797975086372343495L;
	/**
	 * 行政村/自然村名字
	 */
	@Column(name="C_NAME")
	private String name;
	/**
	 *	村子的类型:行政村还是自然村
	 */
	@Column(name = "C_TYPE")
	private String type;
	
	/**
	 * 县的id
	 */
	@Column(name="C_PLACEID")
	private Long placeId;
	
	@Column(name="C_DELETED")
	private boolean deleted;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
	
}
