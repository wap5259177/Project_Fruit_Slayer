package cn.bonoon.controllers.file.management;

import cn.bonoon.controllers.file.FileDefine;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.TransformFile;
import cn.bonoon.kernel.support.models.ObjectEditor;

@Transform
//@FormEditor(width = 500)
public class FileEditor extends ObjectEditor implements FileDefine {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5854315109928824085L;
	/**
	 * 文件名
	 */
	@TransformField
	@TransformFile
//	@AsFile
//	@PropertyEditor(value = 0, colspan = 1)
	private String mapPath;
	/**
	 * 所在的目录的ID
	 */
//	private Long directoryId;
//	private String path;
//	@TransformField
//	private String extendedAttributes;
	@TransformField
//	@AsTextArea
//	@PropertyEditor(value = 0, colspan = 1)
	private String remark;

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

//	public Long getDirectoryId() {
//		return directoryId;
//	}
//
//	public void setDirectoryId(Long directoryId) {
//		this.directoryId = directoryId;
//	}
//
//	public String getPath() {
//		return path;
//	}
//
//	public void setPath(String path) {
//		this.path = path;
//	}

//	public String getExtendedAttributes() {
//		return extendedAttributes;
//	}
//
//	public void setExtendedAttributes(String extendedAttributes) {
//		this.extendedAttributes = extendedAttributes;
//	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMapPath() {
		return mapPath;
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}
}
