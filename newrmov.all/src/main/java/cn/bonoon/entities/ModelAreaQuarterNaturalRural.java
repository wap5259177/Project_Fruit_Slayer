package cn.bonoon.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 行政下的自然村，需要示范片区的工作人员填写自然村的工作进度的完成情况，
 * 然后再统计到行政村，再由行政村统计到片区
 * @author jackson
 *
 */
@Entity
@Table(name = "t_manrural")
public class ModelAreaQuarterNaturalRural extends AbstractModelAreaQuarterRural{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4084680573258977198L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_ARQUARTER_ID")
	private ModelAreaQuarterAdministrationRural arQuarter;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_NEWRURAL_ID")
	private NewRuralEntity newRural;

	public ModelAreaQuarterAdministrationRural getArQuarter() {
		return arQuarter;
	}

	public void setArQuarter(ModelAreaQuarterAdministrationRural arQuarter) {
		this.arQuarter = arQuarter;
	}

	public NewRuralEntity getNewRural() {
		return newRural;
	}

	public void setNewRural(NewRuralEntity newRural) {
		this.newRural = newRural;
	}
}
