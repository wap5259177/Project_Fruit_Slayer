package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import cn.bonoon.kernel.support.entities.AbstractPersistable;

@MappedSuperclass
public abstract class AbstractPointEntity extends AbstractPersistable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 728238402941865139L;

	@Embedded
	private PointEmbeddable point = new PointEmbeddable();
	
	@Column(name = "C_NAME")
	private String name;//地区名称，可以直接用于在地图上搜索的地方名称，如：广东省广州市天河区

	@Column(name = "C_TITLE")
	private String title;//显示在地图上弹出信息框的标题
	@Column(name = "C_PICTURE")
	private String picture;//用显示片区风貌的图片

//	@Column(name = "C_POINTICON")
//	private String pointIcon;//在地图标新的图标

	//注意：不允许使用半角的双引号""
	@Column(name = "C_DESCRIBE")
	private String describe;//关于片区的描述

	public PointEmbeddable getPoint() {
		return point;
	}

	public void setPoint(PointEmbeddable point) {
		this.point = point;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
