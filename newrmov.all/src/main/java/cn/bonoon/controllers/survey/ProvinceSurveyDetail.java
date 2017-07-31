package cn.bonoon.controllers.survey;

import java.util.Date;

import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@FormDetail(autoIgnore = true, width = 800)
@InsertCell(value = "survey/view.vm", type = EmbedType.PARSE)
@WithDialog(initializer = ProvinceSurveyDetailInitializer.class, closeButton = false, title = "广东省新农村建设摸底调查汇总表")
public class ProvinceSurveyDetail extends ObjectEditor{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6661363839608111444L;
	private Date deadline;
	private int status;
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
