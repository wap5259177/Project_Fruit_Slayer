package cn.bonoon.controllers.file.requirereportapply;

import java.util.Date;

import cn.bonoon.controllers.file.require.RequireReportDetailInitializer;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail;


@WithDialog(initializer = RequireReportDetailInitializer.class, width = 450, height = 400)
public class RequireReportApplyDetail implements RequireReportApplyDefine{
	
	
	@PropertyDetail(0)
	private String name;

	@PropertyDetail(7)
	private String status;

	@PropertyDetail(1)
	@TransformField("report.unit.place.name")
	private String unit;
	
	@PropertyDetail(2)
	private Date createAt;

	@PropertyDetail(4)
	@TransformField("report.requireReport.startReportedAt")
	private Date startReportedAt;

	@PropertyDetail(5)
	@TransformField("report.requireReport.endReportedAt")
	private Date endReportedAt;
	
	@TransformField("report.document")
	@PropertyDetail(value = 10, colspan = 1)
	private int documentCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
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

	public int getDocumentCount() {
		return documentCount;
	}

	public void setDocumentCount(int documentCount) {
		this.documentCount = documentCount;
	}



	
	
}
