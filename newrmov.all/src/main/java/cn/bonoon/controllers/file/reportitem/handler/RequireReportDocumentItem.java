package cn.bonoon.controllers.file.reportitem.handler;

import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.RequireReportDocumentEntity;
import cn.bonoon.entities.RequireReportEntity;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.util.StringHelper;

public class RequireReportDocumentItem extends AbstractItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8995446459672188169L;

	private String reportName;
	private String unitName;
	private String fileName;
	private String fileType;
	private String operator;
	private String operateAt;

	@SuppressWarnings("unused")
	private RequireReportDocumentItem() {
	}

	public RequireReportDocumentItem(RequireReportDocumentEntity entity) {
		super.setId(entity.getId());
		RequireReportItemEntity requireReportItem = entity.getRequireReportItem();
		RequireReportEntity requireReport = requireReportItem.getRequireReport();
		if (null != requireReport) {
			this.reportName = requireReport.getName();
		}
		UnitEntity unit = requireReportItem.getUnit();
		if (null != unit) {
			PlaceEntity place = unit.getPlace();
			if (null != place)
				this.unitName = place.getName();
		}
		FileEntity document = entity.getDocument();
		if (null != document) {
			this.fileName = document.getName();
			this.fileType = document.getType().getName();
		}
		this.operator = entity.getOperator();
		this.operateAt = StringHelper.date2String(entity.getOperateAt());
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperateAt() {
		return operateAt;
	}

	public void setOperateAt(String operateAt) {
		this.operateAt = operateAt;
	}

}
