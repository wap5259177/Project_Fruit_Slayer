package cn.bonoon.controllers.file.usergroup;

import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = UserGroupCondition.class, value = @GridOptions(operationWith = 200))
public class UserGroupItem extends AbstractItem implements  UserGroupDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8463631685591354089L;

	
	@AsColumn(width = 220, ordinal = 0)
	private String name;
	
	@AsColumn(width = 220, ordinal = 20)
	private String createAt;
	
	@AsColumn(width = 220, ordinal = 10)
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
