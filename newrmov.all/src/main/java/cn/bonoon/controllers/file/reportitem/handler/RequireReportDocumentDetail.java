package cn.bonoon.controllers.file.reportitem.handler;

import cn.bonoon.controllers.file.management.FileDetail;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.RequireReportDocumentEntity;
import cn.bonoon.kernel.util.StringHelper;

public class RequireReportDocumentDetail extends FileDetail {

	private static final long serialVersionUID = 5693102877462008050L;
	
	private String operator;
	private String operateAt;
	private Long fileId; // 文件实体的id，用于下载

	public RequireReportDocumentDetail() { }

	public RequireReportDocumentDetail(RequireReportDocumentEntity entity) {
		if (null == entity)
			return;
		
		this.operateAt = StringHelper.date2String(entity.getOperateAt());
		this.operator = entity.getOperator();
		FileEntity document = entity.getDocument();
		if (null != document) {
			super.setCreateAt(StringHelper.date2String(document.getCreateAt()));
			super.setCreatorName(document.getCreatorName());
			super.setExt(document.getExt());
			super.setExtendedAttributes(document.getExtendedAttributes());
			super.setLength(document.getLength());
			super.setName(document.getName());
			super.setRemark(document.getRemark());
			super.setType(document.getType().getName());
			super.setVersion(document.getVersion());
			super.setMapPath(document.getMapPath());
			this.fileId = document.getId();
		}
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

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}


}
