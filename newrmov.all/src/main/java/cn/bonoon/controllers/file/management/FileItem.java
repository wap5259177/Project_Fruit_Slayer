package cn.bonoon.controllers.file.management;

import cn.bonoon.controllers.file.FileDefine;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.AbstractItem;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.annotations.grid.AsColumn;
import cn.bonoon.kernel.web.annotations.grid.AsDataGrid;
import cn.bonoon.kernel.web.annotations.grid.GridOptions;

@AsDataGrid(condition = FileCondition.class, value = @GridOptions(operationWith = 130))
public class FileItem extends AbstractItem implements FileDefine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1277428592833169509L;
	@AsColumn(width = 500, ordinal = 0)
	private String name;

//	@AsColumn(width = 80, ordinal = 1)
//	private long length;

//	@AsColumn(width = 80, ordinal = 2)
//	private String ext;
//
//	@AsColumn(width = 60, ordinal = 3)
//	private String shared;
//
//	@AsColumn(width = 60, ordinal = 4)
//	private String status;
//	
//	@AsColumn(width = 80, ordinal = 5)
//	private String type;

	@AsColumn(width = 130, ordinal = 6)
	private String createAt;
	
	@AsColumn(width = 80, ordinal = 1)
	private String creatorName;

//	@AsColumn(width = 150, ordinal = 7)
//	private String remark;
//
//	@AsColumn(width = 150, ordinal = 8)
//	private String extendedAttributes;
	
	private long version;
	
	@TransformField("status")
	private int statusValue;
	
	public FileItem() {}
	
	public FileItem(FileEntity file) {
		if (null == file)
			return;
		super.setId(file.getId());
		this.createAt = StringHelper.date2String(file.getCreateAt());
//		this.ext = file.getExt();
////		this.extendedAttributes = file.getExtendedAttributes();
//		this.length = file.getLength();
		this.name = file.getName();
//		this.remark = file.getRemark();
//		this.shared =file.isShared() ? "是" : "否";
//		this.statusValue = file.getStatus();
//		this.status = statusValue == 0 ? "删除" : statusValue == 1 ? "正常" : statusValue == -1 ? "禁止" : "";
//		this.type = file.getType().getName();
		
		this.version = file.getVersion();
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getRemark() {
//		return remark;
//	}
//
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
//
//	public void setExtendedAttributes(String extendedAttributes) {
//		this.extendedAttributes = extendedAttributes;
//	}
//
//	public String getExtendedAttributes() {
//		return extendedAttributes;
//	}

//	public String getExt() {
//		return ext;
//	}
//
//	public void setExt(String ext) {
//		this.ext = ext;
//	}
//
//	public String getShared() {
//		return shared;
//	}
//
//	public void setShared(String shared) {
//		this.shared = shared;
//	}
//
//	public String getLength() {
//		return FileHelper.helper.len(length);
//	}
//
//	public void setLength(long length) {
//		this.length = length;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public int getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
	

}