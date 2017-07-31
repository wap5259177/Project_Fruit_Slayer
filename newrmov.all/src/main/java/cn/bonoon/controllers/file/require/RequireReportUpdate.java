package cn.bonoon.controllers.file.require;

import java.util.Date;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.components.AsDateBox;
import cn.bonoon.kernel.web.annotations.form.FormUpdate;
import cn.bonoon.kernel.web.annotations.form.InsertCell;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;

@Transform
@FormUpdate(value = 3, width = 100)
@InsertCell(value = "report/require-report.vm", type = EmbedType.PARSE)
@WithDialog(initializer = RequireReportEditorInitializer.class, width = 750, height = 550)
@DialogDefaultButton(buttonName = "保存")
public class RequireReportUpdate extends ObjectEditor implements RequireReportDefine {

	private static final long serialVersionUID = -8798096794938953534L;

	@TransformField
	@PropertyEditor(1)
	private String name;

	@TransformField
	@PropertyEditor(2)
	@AsDateBox
	private Date startReportedAt;

	@TransformField
	@PropertyEditor(3)
	@AsDateBox
	private Date endReportedAt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartReportedAt() {
		return startReportedAt;
	}

	public void setStartReportedAt(Date startReportedAt) {
		this.startReportedAt = startReportedAt;
	}

	public Date getEndReportedAt() {
		return endReportedAt;
	}

	public void setEndReportedAt(Date endReportedAt) {
		this.endReportedAt = endReportedAt;
	}
}
