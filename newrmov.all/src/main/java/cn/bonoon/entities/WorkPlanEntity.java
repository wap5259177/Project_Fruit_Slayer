package cn.bonoon.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.support.entities.AbstractEntity;

@Entity
@Table(name = "t_workplan")
public class WorkPlanEntity extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2487540389850028661L;

	@Column(name = "C_ANNUAL")
	private int annual;

	@Column(name = "C_MONTHLY")
	private int monthly;

	//TODO TEXT->LONGTEXT
//	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "C_CONTENT", columnDefinition="LONGTEXT")
//	@Column(name = "C_CONTENT", columnDefinition="TEXT")
	
	
	private String content;

	@Column(name = "C_UNITNAME")
	private String unitName;
	
	
	/**
	 * 附件
	 */
	@Column(name = "C_ENCLOSURE")
	private String enclosure;
	
	@ManyToOne
	@JoinColumn(name = "R_PLACE_ID")
	private PlaceEntity place;

	public int getAnnual() {
		return annual;
	}

	public void setAnnual(int annual) {
		this.annual = annual;
	}

	public int getMonthly() {
		return monthly;
	}

	public void setMonthly(int monthly) {
		this.monthly = monthly;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public PlaceEntity getPlace() {
		return place;
	}

	public void setPlace(PlaceEntity place) {
		this.place = place;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}
}
