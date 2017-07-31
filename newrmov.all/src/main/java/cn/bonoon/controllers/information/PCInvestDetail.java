package cn.bonoon.controllers.information;

import java.util.Date;

import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@FormDetail(autoIgnore = true,width = 800)
@InsertCell(value = "information/pv-invest-view.vm", type = EmbedType.PARSE)
@WithDialog(initializer = PCInvestDetailInitializer.class, closeButton = false, title = "广东省新农村建设资金投入情况统计表")
public class PCInvestDetail extends ObjectEditor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4900793561001716507L;
	
	
	
//	@PropertyDetail(value=1,colspan=1)
//	private String name;
//	
//	@PropertyDetail(value=15,colspan=1)
//	private String annual;
//	
//	
//	@PropertyDetail(value=20)
//	private Date startTime;
//	@PropertyDetail(value=21)
	private Date deadline;
//	
//	@PropertyDetail(value=30)
//	private Date startAt;
//	@PropertyDetail(value=31)
//	private Date endAt;
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getAnnual() {
//		return annual;
//	}
//	public void setAnnual(String annual) {
//		this.annual = annual;
//	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
//	public Date getStartAt() {
//		return startAt;
//	}
//	public void setStartAt(Date startAt) {
//		this.startAt = startAt;
//	}
//	public Date getEndAt() {
//		return endAt;
//	}
//	public void setEndAt(Date endAt) {
//		this.endAt = endAt;
//	}


}
