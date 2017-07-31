package cn.bonoon.controllers.project.type;

import cn.bonoon.controllers.project.ProjectCondition;
import cn.bonoon.controllers.project.ProjectDefine;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = ProjectCondition.class, value = @GridOptions(operationWith = 100))
public class ProjectTypeItem extends AbstractItem implements ProjectDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5240848622501292192L;
	
	@AsColumn(width = 170, ordinal = 20)
	private String name;

	@AsColumn(width = 170, ordinal = 20)
	private String remark;
	
	@AsColumn(width = 70, ordinal = 20)
	private String createAt;

	@AsColumn(width = 70, ordinal=20)
	private String creatorName;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}


}
