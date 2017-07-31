package cn.bonoon.controllers.felicity.report;

import cn.bonoon.controllers.felicity.FelicityVillageReportDefine;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;

public class FelicityVillageReportBaseItem extends AbstractItem implements FelicityVillageReportDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5360737933033882038L;
	@AsColumn(frozen = true, width = 80, ordinal = 0)
	@TransformField("village.townName")
	private String townName;
	@AsColumn(frozen = true, width = 120, ordinal = 0)
	@TransformField("village.name")
	private String name;
	@AsColumn(frozen = true, width = 120, ordinal = 0)
	@TransformField("village.naturalVillage")
	private String naturalVillage;
	
	@TransformField("village.constructionType")
	@AsColumn(width = 100, ordinal = 1)
	private String constructionType;//村庄建设类型
	@TransformField("village.villageType")
	@AsColumn(width = 80, ordinal = 2)
	private String villageType;//A为行政村 B为自然村
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getConstructionType() {
		return constructionType;
	}
	public void setConstructionType(String constructionType) {
		this.constructionType = constructionType;
	}
	public String getVillageType() {
		return villageType;
	}
	public void setVillageType(String villageType) {
		this.villageType = villageType;
	}
	
	public String getNaturalVillage() {
		return naturalVillage;
	}
	public void setNaturalVillage(String naturalVillage) {
		this.naturalVillage = naturalVillage;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
}
