package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.kernel.support.entities.AbstractPersistable;

/**
 * 村全景
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_ruralpanorama")
public class RuralPanoramaEntity extends  AbstractPersistable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 328951460748531930L;

	@ManyToOne
	@JoinColumn(name = "R_RURAL_ID")
	private BaseRuralEntity rural;
	
	@ManyToOne
	@JoinColumn(name = "R_MODELAREA_ID")
	private ModelAreaEntity modelArea;
	
	@ManyToOne
	@JoinColumn(name = "R_MODELAREAPOINT_ID")
	private ModelAreaPointEntity modelAreaPoint;
	
	//村子的全景图片
	@Column(name = "C_PANORAMA",length=1000)
	private String panorama;
	
	//村子的缩略图
	@Column(name = "C_THUMBNAIL",length=1000)
	private String thumbnail;
	
	//全景图的定位点
	@Embedded
	private PointEmbeddable point = new PointEmbeddable();

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

	public String getPanorama() {
		return panorama;
	}

	public void setPanorama(String panorama) {
		this.panorama = panorama;
	}

	public PointEmbeddable getPoint() {
		return point;
	}

	public void setPoint(PointEmbeddable point) {
		this.point = point;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	
	
}
