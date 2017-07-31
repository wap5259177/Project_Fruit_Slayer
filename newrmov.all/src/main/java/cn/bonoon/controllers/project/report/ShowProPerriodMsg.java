package cn.bonoon.controllers.project.report;

import java.util.TreeSet;

public class ShowProPerriodMsg {
	private Long id;
	private String name;
	private String code;
	private int status;
	private String showStatus;
	private String createAt;
	private TreeSet<Integer[]> ts;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	public TreeSet<Integer[]> getTs() {
		return ts;
	}
	public void setTs(TreeSet<Integer[]> ts) {
		this.ts = ts;
	}
	public String getShowStatus() {
		if(this.getStatus()==0)
			showStatus="未开始";
		if(this.getStatus()==1)
			showStatus="进行中";
		if(this.getStatus()==2)
			showStatus="竣工";
		if(this.getStatus()==3)
			showStatus="终止";
		return showStatus;
	}
}
