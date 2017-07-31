package cn.bonoon.controllers.newrural;

import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;

public class RuralItem extends AbstractItem implements RuralDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1329364609047272433L;
	@AsColumn(width = 150, ordinal = 0)
	private String town;
	@AsColumn(width = 150, ordinal = 10)
	private String name;
	@AsColumn(width = 150, ordinal = 11)
	private String naturalVillage;
	
	@AsColumn(width = 100, ordinal = 13)
	private String type;
	@AsColumn(width = 80, ordinal = 50)
	private String status;
	
	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNaturalVillage() {
		return naturalVillage;
	}

	public void setNaturalVillage(String naturalVillage) {
		this.naturalVillage = naturalVillage;
	}
}
