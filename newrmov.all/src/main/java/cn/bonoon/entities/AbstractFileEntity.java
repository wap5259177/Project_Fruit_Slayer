package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import cn.bonoon.core.FileStatus;
import cn.bonoon.kernel.support.entities.AbstractEntity;

@MappedSuperclass
public abstract class AbstractFileEntity extends AbstractEntity implements FileStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7779353128972711982L;

	/**
	 * 在文件系统中的目录结构的路径 文件或文件夹的路径
	 */
	@Column(name = "C_MAPPATH")
	private String mapPath;

	/**
	 * 已经使用的空间或者文件的大小
	 */
	@Column(name = "C_LENGTH")
	private long length;

	/**
	 * 状态 0:删除 1:正常 -1:禁止，不可修改
	 */
	@Column(name = "C_STATUS")
	private int status;

	@Column(name = "C_ORDINAL")
	private int ordinal;

	// ----以下是文件或文件夹的扩展属性，这些扩展属性的具体值和意义由具体的子系统来定义
	// 如：extattr3=Project.id可以表示extattr3字段用来存放项目的id来表示该文档是属于哪一个项目的文档
	@Column(name = "C_EXTATTR1")
	private int extattr1; // 台账状态
	@Column(name = "C_EXTATTR2")
	private int extattr2; // 1市 10区 11区文档 12区图片 20村 21村文档 22村图片 30项目 31项目文档 32项目图片
	@Column(name = "C_EXTATTR3")
	private Long extattr3; // city/area/rural/project id
	@Column(name = "C_EXTATTR4")
	private Long extattr4;
	@Column(name = "C_EXTATTR5")
	private String extattr5; // city/area/rural/project name
	@Column(name = "C_EXTATTR6")
	private String extattr6;

	public int getExtattr1() {
		return extattr1;
	}

	public void setExtattr1(int extattr1) {
		this.extattr1 = extattr1;
	}

	public int getExtattr2() {
		return extattr2;
	}

	public void setExtattr2(int extattr2) {
		this.extattr2 = extattr2;
	}

	public Long getExtattr3() {
		return extattr3;
	}

	public void setExtattr3(Long extattr3) {
		this.extattr3 = extattr3;
	}

	public Long getExtattr4() {
		return extattr4;
	}

	public void setExtattr4(Long extattr4) {
		this.extattr4 = extattr4;
	}

	public String getExtattr5() {
		return extattr5;
	}

	public void setExtattr5(String extattr5) {
		this.extattr5 = extattr5;
	}

	public String getExtattr6() {
		return extattr6;
	}

	public void setExtattr6(String extattr6) {
		this.extattr6 = extattr6;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
