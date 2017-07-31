package cn.bonoon.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 村位置
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_ruralpoint")
public class RuralPointEntity extends AbstractPointEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1082764467389105555L;

	@ManyToOne
	@JoinColumn(name = "R_RURAL_ID")
	private BaseRuralEntity rural;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_MODELAREA_ID")
	private ModelAreaEntity modelArea;
	
	@ManyToOne
	@JoinColumn(name = "R_MODELAREAPOINT_ID")
	private ModelAreaPointEntity modelAreaPoint;

	public BaseRuralEntity getRural() {
		return rural;
	}

	public void setRural(BaseRuralEntity rural) {
		this.rural = rural;
	}

	public ModelAreaEntity getModelArea() {
		return modelArea;
	}

	public void setModelArea(ModelAreaEntity modelArea) {
		this.modelArea = modelArea;
	}

	public ModelAreaPointEntity getModelAreaPoint() {
		return modelAreaPoint;
	}

	public void setModelAreaPoint(ModelAreaPointEntity modelAreaPoint) {
		this.modelAreaPoint = modelAreaPoint;
	}
}
