package cn.bonoon.controllers.file.usergroup;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;

@FormEditor(value = 2,headWidth=120,width=250)
@Transform
@WithDialog(initFormClass = true)
public class UserGroupEditor extends ObjectEditor implements UserGroupDefine{

	/**
	 * 廖夏妮6225882008685262（广州招商银行）
	 */
	private static final long serialVersionUID = -833713388004168072L;

	@PropertyEditor(value = 0, colspan = 1)
	private String name;
	
	@PropertyEditor(value = 1, colspan = 1)
	
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
	
	
}
