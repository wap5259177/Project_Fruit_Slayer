package cn.bonoon.controllers.file.management;

import java.util.Date;

import cn.bonoon.core.FileType;
import cn.bonoon.entities.FileEntity;

public class FileUploadForm {

	public FileEntity set(FileEntity entity) {
		entity.setName(this.name);
		entity.setExtendedAttributes(this.extendedAttributes);
		entity.setStatus(this.status);
		entity.setRemark(this.remark);
		entity.setType(this.type);
		entity.setIssueAt(this.issueAt);
		return entity;
	}

	public void get(FileEntity entity) {
		this.fileId = entity.getId();
		this.name = entity.getName();
		this.extendedAttributes = entity.getExtendedAttributes();
		this.status = entity.getStatus();
		this.type = entity.getType();
		this.issueAt = entity.getIssueAt();
	}

	private Long fileId;
	private String name;
	private String extendedAttributes;
	private Integer status;
	private String remark;
	private FileType type;
	private Date issueAt;

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtendedAttributes() {
		return extendedAttributes;
	}

	public void setExtendedAttributes(String extendedAttributes) {
		this.extendedAttributes = extendedAttributes;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public FileType getType() {
		return type;
	}

	public void setType(FileType type) {
		this.type = type;
	}

	public Date getIssueAt() {
		return issueAt;
	}

	public void setIssueAt(Date issueAt) {
		this.issueAt = issueAt;
	}

}
