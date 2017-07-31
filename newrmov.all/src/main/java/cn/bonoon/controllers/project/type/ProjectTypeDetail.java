package cn.bonoon.controllers.project.type;

import cn.bonoon.controllers.project.ProjectDefine;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail;

@FormDetail(2)
public class ProjectTypeDetail implements ProjectDefine {

	@PropertyDetail(1)
	private String createAt;
	@PropertyDetail(2)
	private String creatorName;
	@PropertyDetail(3)
	private String status;
	@PropertyDetail(4)
	private String name;
	@PropertyDetail(value = 5, colspan = 1)
	private String remark;

	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
