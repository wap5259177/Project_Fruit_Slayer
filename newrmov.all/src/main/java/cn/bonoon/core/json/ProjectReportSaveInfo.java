package cn.bonoon.core.json;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目月报数据封装
 */
public class ProjectReportSaveInfo {
	private List<ProjectReportItemBean> save_data = new ArrayList<>();//表单提交的数据
	private Long rid;//report（月报）的id 
	private String toSubmit;//取值"true"或"false",是否为提交
	
	public ProjectReportSaveInfo(){}
	public List<ProjectReportItemBean> getSave_data() {
		return save_data;
	}
	public void setSave_data(List<ProjectReportItemBean> save_data) {
		this.save_data = save_data;
	}
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public String getToSubmit() {
		return toSubmit;
	}
	public void setToSubmit(String toSubmit) {
		this.toSubmit = toSubmit;
	}
}
