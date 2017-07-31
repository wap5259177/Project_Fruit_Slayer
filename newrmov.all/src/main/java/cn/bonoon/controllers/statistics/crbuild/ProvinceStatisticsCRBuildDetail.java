package cn.bonoon.controllers.statistics.crbuild;

import java.util.Date;

import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@FormDetail(autoIgnore = true, width = 800)
@InsertCell(value = "report/crbuild/view.vm", type = EmbedType.PARSE)
@WithDialog(initializer = ProvinceStatisticsCRBuildDetailInitializer.class, closeButton = false, title = "省级新农村示范片主体村建设情况统计表")
public class ProvinceStatisticsCRBuildDetail extends ObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6444931611516418624L;
	private Date deadline;
	private int status;
	private String remark;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
