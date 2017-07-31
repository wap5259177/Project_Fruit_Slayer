package cn.bonoon.controllers.project.type;

import java.util.Date;

import cn.bonoon.controllers.project.ProjectDefine;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.annotations.components.AsDateBox;
import cn.bonoon.kernel.web.annotations.components.AsSelector;
import cn.bonoon.kernel.web.annotations.components.AsTextArea;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;

@Transform
@FormEditor(2)
public class ProjectTypeEditor extends ObjectEditor implements ProjectDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8464998990729505260L;
	
	@PropertyEditor(value = 1, required = true)
	private String name;

	@PropertyEditor(value = 100, colspan = 1, required = true)
	@AsTextArea
	private String remark;
	
	@PropertyEditor(9)
	@AsDateBox
	private Date createAt;
	
	@PropertyEditor(value = 2)
	private String creatorName;
	
	@PropertyEditor(value = 4)
	@AsSelector
	private int status;

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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
