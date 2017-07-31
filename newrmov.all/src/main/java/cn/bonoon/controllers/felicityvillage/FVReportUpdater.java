package cn.bonoon.controllers.felicityvillage;

import java.util.Date;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.util.BoolType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormUpdate;
import cn.bonoon.kernel.web.annotations.form.PropertyUpdate;

@Transform
@FormUpdate(headWidth = 150,width = 200, value=2)
@WithDialog()
public class FVReportUpdater extends  ObjectEditor  implements FVReportDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6225206130264864998L;

	/**
	 * 
	 */

	@PropertyUpdate(value=1,colspan=1)
	private String name;
	
	@PropertyUpdate(value=10,readonly=BoolType.TRUE,colspan=1)
	private int annual;
	
	
	@PropertyUpdate(value=20)
	private Date startTime;
	@PropertyUpdate(value=21)
	private Date deadline;
	
	@PropertyUpdate(value=30)
	private Date startAt;
	@PropertyUpdate(value=31)
	private Date endAt;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getEndAt() {
		return endAt;
	}
	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getStartAt() {
		return startAt;
	}
	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}
	
	
	
	
}
