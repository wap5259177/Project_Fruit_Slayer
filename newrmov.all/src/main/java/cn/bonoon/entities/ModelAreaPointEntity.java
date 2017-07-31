package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 片区位置
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_modelareapoint")
public class ModelAreaPointEntity extends AbstractPointEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7731533921749755072L;
	
	
	@ManyToOne
	@JoinColumn(name = "R_MODELAREA_ID")
	private ModelAreaEntity modelArea;
	
//	@ElementCollection
//    @CollectionTable(name="t_polygon") 
//	private Set<PointEmbeddable> polygon = new HashSet<>();
	@Column(name = "C_POLYGON",length=5000)
	private String polygon;
	
	public ModelAreaEntity getModelArea() {
		return modelArea;
	}

	public void setModelArea(ModelAreaEntity modelArea) {
		this.modelArea = modelArea;
	}

	public String getPolygon() {
		return polygon;
	}

	public void setPolygon(String polygon) {
		this.polygon = polygon;
	}

//	public Set<PointEmbeddable> getPolygon() {
//		return polygon;
//	}
//
//	public void setPolygon(Set<PointEmbeddable> polygon) {
//		this.polygon = polygon;
//	}
}
