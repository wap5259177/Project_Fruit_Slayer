package cn.bonoon.controllers.file.requirereportapply;

import java.util.Date;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = RequireReportApplyCondition.class, value = @GridOptions(operationWith = 135))
public class RequireReportApplyItem extends AbstractItem implements RequireReportApplyDefine {

	private static final long serialVersionUID = 8650186581190027792L;

	@AsColumn(width = 170, ordinal = 0)
	private String name;

	@AsColumn(width = 100, ordinal = 1)
	@TransformField("report.unit.place.name")
	private String unit;

	@AsColumn(width = 100, ordinal = 2)
	private String createAt;

	@AsColumn(width = 170, ordinal = 4)
	@TransformField("report.requireReport.startReportedAt")
	private Date startReportedAt;

	@AsColumn(width = 170, ordinal = 5)
	@TransformField("report.requireReport.endReportedAt")
	private Date endReportedAt;

	@AsColumn(width = 80, ordinal = 6)
	@TransformField("report.documentCount")
	private int documentCount;

	@AsColumn(width = 80, ordinal = 7)
	private String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
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

	public String getStatus() {
		Date current = new Date();
		status = startReportedAt.after(current) ? "未开始" : endReportedAt.after(current) ? "已开始" : "已结束";
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
