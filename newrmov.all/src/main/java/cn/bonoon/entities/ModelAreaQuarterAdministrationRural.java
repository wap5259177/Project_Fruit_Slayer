package cn.bonoon.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 示范片区的行政村，用于统计这个行政村的要求项的完成情况及一些行政村的基本信息
 * @author jackson
 *
 */
@Entity
@Table(name = "t_maarural")
public class ModelAreaQuarterAdministrationRural extends AbstractModelAreaQuarterRural{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1638938531823786758L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_ITEM_ID")
	private ModelAreaQuarterItem item; 
	
	@OneToMany(mappedBy = "arQuarter")
	private List<ModelAreaQuarterNaturalRural> naturaRurals;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_ADMINRURAL_ID")
	private AdministrationCoreRuralEntity adminRural;

	@Column(name = "C_ARFINISHPLAN")
	private int arFinishPlan; //行政村有编制规划设计村数

	public ModelAreaQuarterItem getItem() {
		return item;
	}

	public void setItem(ModelAreaQuarterItem item) {
		this.item = item;
	}

	public List<ModelAreaQuarterNaturalRural> getNaturaRurals() {
		return naturaRurals;
	}

	public void setNaturaRurals(List<ModelAreaQuarterNaturalRural> naturaRurals) {
		this.naturaRurals = naturaRurals;
	}

	public AdministrationCoreRuralEntity getAdminRural() {
		return adminRural;
	}

	public void setAdminRural(AdministrationCoreRuralEntity adminRural) {
		this.adminRural = adminRural;
	}

	public int getArFinishPlan() {
		return arFinishPlan;
	}

	public void setArFinishPlan(int arFinishPlan) {
		this.arFinishPlan = arFinishPlan;
	}

	
}
