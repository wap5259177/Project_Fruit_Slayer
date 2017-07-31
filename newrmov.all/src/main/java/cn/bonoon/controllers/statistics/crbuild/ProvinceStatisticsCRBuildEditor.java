package cn.bonoon.controllers.statistics.crbuild;

import java.util.Date;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.util.BoolType;
import cn.bonoon.kernel.web.annotations.components.AsMonthly;
import cn.bonoon.kernel.web.annotations.components.AsSelector;
import cn.bonoon.kernel.web.annotations.components.AsTextArea;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyInsert;
import cn.bonoon.kernel.web.annotations.form.PropertyUpdate;

@Transform
@FormEditor(headWidth = 200)
public class ProvinceStatisticsCRBuildEditor extends ObjectEditor implements ProvinceStatisticsCRBuildDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1086954301154195750L;

	@AsSelector
	@AsMonthly(annual = true, start = -5)
	@PropertyInsert(required = BoolType.TRUE, value = 0)
	@PropertyUpdate(readonly = BoolType.TRUE, value = 0)
	@TransformField(writable = WriteModel.INSERTONLY)
	private int annual;
	@AsSelector
	@PropertyInsert(required = BoolType.TRUE, value = 1)
	@TransformField(writable = WriteModel.INSERTONLY)
	private int period;
	@TransformField(value = "period", writable = WriteModel.NONE)
	@PropertyUpdate(readonly = BoolType.TRUE, value = 1, name = "季度")
	@OptionArray(value = {"第一季度", "第二季度","第三季度","第四季度","自示范片启动建设以来至2016年6月30日"})
	private String periodValue;
	@PropertyEditor(required = true, value = 5)
	private Date deadline;
	@PropertyEditor(required = true, value = 10)
	private Date startAt;
	@PropertyEditor(required = true, value = 11)
	private Date endAt;
	
	@PropertyEditor(required = false, value = 50)
	@AsTextArea
	private String remark;
	
	public int getAnnual() {
		return annual;
	}
	public void setAnnual(int annual) {
		this.annual = annual;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
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
	public String getPeriodValue() {
		return periodValue;
	}
	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
