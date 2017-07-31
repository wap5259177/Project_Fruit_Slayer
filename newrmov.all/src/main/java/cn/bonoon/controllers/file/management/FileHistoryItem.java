package cn.bonoon.controllers.file.management;

import cn.bonoon.kernel.support.models.AbstractItem;

public class FileHistoryItem extends AbstractItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9178428187120194568L;

	private String name;
	private String extendedAttributes;
	private String length;
	private String mapPath;
	private String createAt;

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

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getMapPath() {
		return mapPath;
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

}
