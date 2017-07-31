package cn.bonoon.controllers.survey;

import java.util.Date;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.util.BoolType;
import cn.bonoon.kernel.web.annotations.components.AsMonthly;
import cn.bonoon.kernel.web.annotations.components.AsSelector;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyInsert;
import cn.bonoon.kernel.web.annotations.form.PropertyUpdate;

@Transform
@FormEditor(headWidth = 200)
public class ProvinceSurveyEditor extends ObjectEditor implements ProvinceSurveyDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1265054053819353788L;

	@AsSelector
	@AsMonthly(annual = true, start = -5)
	@PropertyInsert(required = BoolType.TRUE, value = 0)
	@PropertyUpdate(readonly = BoolType.TRUE, value = 0)
	@TransformField(writable = WriteModel.INSERTONLY)
	private int annual;
	@PropertyEditor(required = true, value = 1)
	private Date deadline;
	@PropertyEditor(required = true, value = 10)
	private Date startAt;
	@PropertyEditor(required = true, value = 11)
	private Date endAt;
	public int getAnnual() {
		return annual;
	}
	public void setAnnual(int annual) {
		this.annual = annual;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public Date getStartAt() {
		return startAt;
	}
	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}
	public Date getEndAt() {
		return endAt;
	}
	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}
	
}
