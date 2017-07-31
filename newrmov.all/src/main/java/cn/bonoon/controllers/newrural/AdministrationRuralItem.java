package cn.bonoon.controllers.newrural;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;
/**
 * 定义界面显示的内容
 * @author Administrator
 *
 */
@AsDataGrid(condition = RuralCondition.class, value = @GridOptions(operationWith = 135))
public class AdministrationRuralItem extends AbstractItem implements AdministrationRuralDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1329364609047272433L;
	
	
	@TransformField("modelArea.status")//定义数据库实体和界面实体两个实体之间数据的转换。
	private int statusValue;
	
	@AsColumn(width = 150, ordinal = 0)
	private String town;
	@AsColumn(width = 150, ordinal = 10)
	private String name;
//	@AsColumn(width = 150, ordinal = 11)
//	private String naturalVillage;
	
//	@AsColumn(width = 100, ordinal = 13)
//	private String type;
	@AsColumn(width = 80, ordinal = 50)
	private String status;

	public int getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
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
	
	
}
