package cn.bonoon.entities.logs.modelAreaManageModuleLogs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cp_arsc", catalog = "db_newrmov_log")
public class AdRuralStatusComparerEntity extends PlaceComparer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 501241785790404595L;

	/**
	 * 	是否为为没有自然村的行政村而新添加的行政村
	 */
	@Column(name = "C_ISNEWADDADRURAL")
	private boolean isNewAddAdRural;
	
	/**
	 *  行政村是否被删除
	 */
	@Column(name = "C_ADRURALISDEL")
	private boolean adRuralIsDel;

	
	/**
	 *  行政村先前的 所有者
	 */
	@Column(name = "S_AROI")
	
	private Long adRuralOwnerIdOld;

	/**
	 *  行政村现在的 所有者
	 */
	@Column(name = "T_AROI")
	private Long adRuralOwnerIdNow;

	
	public boolean isNewAddAdRural() {
		return isNewAddAdRural;
	}

	public void setNewAddAdRural(boolean isNewAddAdRural) {
		this.isNewAddAdRural = isNewAddAdRural;
	}

	public boolean isAdRuralIsDel() {
		return adRuralIsDel;
	}

	public void setAdRuralIsDel(boolean adRuralIsDel) {
		this.adRuralIsDel = adRuralIsDel;
	}

	public Long getAdRuralOwnerIdOld() {
		return adRuralOwnerIdOld;
	}

	public void setAdRuralOwnerIdOld(Long adRuralOwnerIdOld) {
		this.adRuralOwnerIdOld = adRuralOwnerIdOld;
	}

	public Long getAdRuralOwnerIdNow() {
		return adRuralOwnerIdNow;
	}

	public void setAdRuralOwnerIdNow(Long adRuralOwnerIdNow) {
		this.adRuralOwnerIdNow = adRuralOwnerIdNow;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}





	
	
}
