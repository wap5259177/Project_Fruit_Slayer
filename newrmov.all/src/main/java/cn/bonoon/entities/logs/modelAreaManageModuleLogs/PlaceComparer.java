package cn.bonoon.entities.logs.modelAreaManageModuleLogs;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import cn.bonoon.entities.logs.SuperModelAreaComparer;
import cn.bonoon.entities.plugins.PlaceEntity;

@MappedSuperclass
public class PlaceComparer extends SuperModelAreaComparer{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4722475217422586697L;

	/**
	 *  自然村或行政村先前的 所在地区
	 */
	@ManyToOne
	@JoinColumn(name = "S_RP")
   private	PlaceEntity ruralPlaceOld;

	/**
	 *  自然村或行政村现在的 所在地区
	 */
	@ManyToOne
	@JoinColumn(name = "T_RP")
	private PlaceEntity ruralPlaceNow;

	public PlaceEntity getRuralPlaceOld() {
		return ruralPlaceOld;
	}

	public void setRuralPlaceOld(PlaceEntity ruralPlaceOld) {
		this.ruralPlaceOld = ruralPlaceOld;
	}

	public PlaceEntity getRuralPlaceNow() {
		return ruralPlaceNow;
	}

	public void setRuralPlaceNow(PlaceEntity ruralPlaceNow) {
		this.ruralPlaceNow = ruralPlaceNow;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
