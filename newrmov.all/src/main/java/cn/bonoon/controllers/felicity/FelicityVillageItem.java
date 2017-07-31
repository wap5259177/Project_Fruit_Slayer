package cn.bonoon.controllers.felicity;

import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = FelicityVillageCondition.class, value = @GridOptions(operationWith = 135))
public class FelicityVillageItem extends AbstractItem implements FelicityVillageDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3436900799229141031L;
	
	@AsColumn(width = 160, ordinal = 0)
	private String name;

	@AsColumn(width = 160, ordinal = 1)
	private String townName;
	
	@AsColumn(width = 160, ordinal = 2)
	private String naturalVillage;
	
	@AsColumn(width = 160, ordinal = 3)
	private String constructionType;

	@AsColumn(width = 100, ordinal = 4)
	private String villageType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
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

	public String getConstructionType() {
		return constructionType;
	}

	public void setConstructionType(String constructionType) {
		this.constructionType = constructionType;
	}

	public String getVillageType() {
		return villageType;
	}

	
	
	
}
