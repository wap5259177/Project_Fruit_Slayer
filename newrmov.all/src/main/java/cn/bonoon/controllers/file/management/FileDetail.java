package cn.bonoon.controllers.file.management;

import cn.bonoon.controllers.file.FileDefine;
import cn.bonoon.kernel.support.models.ObjectEditor;

public class FileDetail extends ObjectEditor implements FileDefine {
	private static final long serialVersionUID = 6253332333745891221L;

	/**
	 * 文件名
	 */
	private String name;

	private String mapPath;
	/**
	 * 文件大小
	 */
	private long length;
	/**
	 * 文件的扩展名
	 */
	private String ext;

	/**
	 * 用户的登录名
	 */
	private String creatorName;
	private String createAt;
	private String updaterName;
	private String updateAt;
	private String extendedAttributes;
	private long version;
	private String type;
	private String remark;

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getExtendedAttributes() {
		return extendedAttributes;
	}

	public void setExtendedAttributes(String extendedAttributes) {
		this.extendedAttributes = extendedAttributes;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public String getMapPath() {
		return mapPath;
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
