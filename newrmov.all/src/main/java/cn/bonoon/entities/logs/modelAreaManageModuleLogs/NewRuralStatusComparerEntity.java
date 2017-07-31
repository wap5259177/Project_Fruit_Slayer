package cn.bonoon.entities.logs.modelAreaManageModuleLogs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.entities.AdministrationCoreRuralEntity;
/**
 * 俩个对应字段为0,或者null的表示不需要比较
 * @author wsf
 *
 */

@Entity
@Table(name = "cp_nrsc", catalog = "db_newrmov_log")
public class NewRuralStatusComparerEntity extends PlaceComparer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4207060270182759188L;

	
	/**
	 *  自然村先前的 行政村
	 */
	@ManyToOne
	@JoinColumn(name = "S_AR")
	AdministrationCoreRuralEntity adRuralOld;

	/**
	 *  自然村现在的 行政村
	 */
	@ManyToOne
	@JoinColumn(name = "T_AR")
	AdministrationCoreRuralEntity adRuralNow;


	
	/**
	 *  自然村先前的 所在地区Id
	 */

	@Column(name = "S_NRPI")
	Long newRuralPlaceIdOld;

	/**
	 *  自然村现在的 所在地区Id
	 */

	@Column(name = "T_NRPI")
	Long newRuralPlaceIdNow;

//	public boolean isNewAddAdRural() {
//		return isNewAddAdRural;
//	}
//
//	public void setNewAddAdRural(boolean isNewAddAdRural) {
//		this.isNewAddAdRural = isNewAddAdRural;
//	}

	public AdministrationCoreRuralEntity getAdRuralOld() {
		return adRuralOld;
	}

	public void setAdRuralOld(AdministrationCoreRuralEntity adRuralOld) {
		this.adRuralOld = adRuralOld;
	}

	public AdministrationCoreRuralEntity getAdRuralNow() {
		return adRuralNow;
	}

	public void setAdRuralNow(AdministrationCoreRuralEntity adRuralNow) {
		this.adRuralNow = adRuralNow;
	}

//	public boolean isAdRuralIsDel() {
//		return adRuralIsDel;
//	}
//
//	public void setAdRuralIsDel(boolean adRuralIsDel) {
//		this.adRuralIsDel = adRuralIsDel;
//	}
//
//	public Long getAdRuralOwnerIdOld() {
//		return adRuralOwnerIdOld;
//	}
//
//	public void setAdRuralOwnerIdOld(Long adRuralOwnerIdOld) {
//		this.adRuralOwnerIdOld = adRuralOwnerIdOld;
//	}
//
//	public Long getAdRuralOwnerIdNow() {
//		return adRuralOwnerIdNow;
//	}
//
//	public void setAdRuralOwnerIdNow(Long adRuralOwnerIdNow) {
//		this.adRuralOwnerIdNow = adRuralOwnerIdNow;
//	}
//
//	public PlaceEntity getRuralPlaceOld() {
//		return ruralPlaceOld;
//	}
//
//	public void setRuralPlaceOld(PlaceEntity ruralPlaceOld) {
//		this.ruralPlaceOld = ruralPlaceOld;
//	}
//
//	public PlaceEntity getRuralPlaceNow() {
//		return ruralPlaceNow;
//	}
//
//	public void setRuralPlaceNow(PlaceEntity ruralPlaceNow) {
//		this.ruralPlaceNow = ruralPlaceNow;
//	}

	public Long getNewRuralPlaceIdOld() {
		return newRuralPlaceIdOld;
	}

	public void setNewRuralPlaceIdOld(Long newRuralPlaceIdOld) {
		this.newRuralPlaceIdOld = newRuralPlaceIdOld;
	}

	public Long getNewRuralPlaceIdNow() {
		return newRuralPlaceIdNow;
	}

	public void setNewRuralPlaceIdNow(Long newRuralPlaceIdNow) {
		this.newRuralPlaceIdNow = newRuralPlaceIdNow;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
