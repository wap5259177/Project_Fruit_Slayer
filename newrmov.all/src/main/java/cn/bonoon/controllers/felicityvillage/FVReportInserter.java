package cn.bonoon.controllers.felicityvillage;

import java.util.Date;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.annotations.components.AsMonthly;
import cn.bonoon.kernel.web.annotations.components.AsSelector;
import cn.bonoon.kernel.web.annotations.form.FormInsert;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;

@Transform
//@WithDialog(width = 900,height = 500)
@FormInsert(headWidth = 150,width = 200, value=2)
public class FVReportInserter extends ObjectEditor implements FVReportDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5107382376704760408L;

	

	@PropertyEditor(value=1,colspan=1)
	private String name;
	
	@AsSelector
	@AsMonthly(annual = true, start = -5)
	@PropertyEditor(value=15,required=true,colspan=1)
	@TransformField(writable = WriteModel.INSERTONLY)
	private int annual;
	
	
	@PropertyEditor(value=20,required=true)
	private Date startTime;
	@PropertyEditor(value=21,required=true)
	private Date deadline;
	
	@PropertyEditor(value=30,required=false)
	private Date startAt;
	@PropertyEditor(value=31,required=false)
	private Date endAt;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public int getAnnual() {
		return annual;
	}
	public void setAnnual(int annual) {
		this.annual = annual;
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
