package cn.bonoon.entities.felicityVillage;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 片区字典,统计用
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_fvdicmodelarea")
public class FVModelAreaEntity extends AbstractFVModelAreaEntity{//片区继承abproject 里面小计字段

	/**
	 * 
	 */
	private static final long serialVersionUID = 3172446392415234918L;
	
	/**
	 * 一个字典片区可有多个片区报表
	 */
	@OneToMany(mappedBy = "dicModelArea")
	private List<FVModelAreaReportEntity> modelAreas;

	public List<FVModelAreaReportEntity> getModelAreas() {
		return modelAreas;
	}

	public void setModelAreas(List<FVModelAreaReportEntity> modelAreas) {
		this.modelAreas = modelAreas;
	}
	
	
}
