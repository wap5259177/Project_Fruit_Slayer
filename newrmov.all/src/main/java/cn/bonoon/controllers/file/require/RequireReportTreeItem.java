package cn.bonoon.controllers.file.require;

import java.util.List;

import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.RequireReportDocumentEntity;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.util.StringHelper;

public class RequireReportTreeItem extends AbstractItem {

	private static final long serialVersionUID = 786473363691037900L;

	public RequireReportTreeItem() {
	}

	public RequireReportTreeItem(Long id, String name) {
		super.setId(id);
		this.name = name;
	}

	public RequireReportTreeItem(RequireReportDocumentEntity entity) {
		super.setId(entity.getId());
		this.operator = entity.getOperator();
		this.operateAt = StringHelper.date2String(entity.getOperateAt());
		FileEntity document = entity.getDocument();
		if (null != document) {
			this.name = document.getName();
			this.type = document.getType().getName();
			this.length = document.getLength();
		}
	}

	private String name;
	private String type;
	private Long length;
	private String operator;
	private String operateAt;
	private List<RequireReportTreeItem> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
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

	public List<RequireReportTreeItem> getChildren() {
		return children;
	}

	public void setChildren(List<RequireReportTreeItem> children) {
		this.children = children;
	}

}
