package cn.bonoon.core.felicity;

import java.text.SimpleDateFormat;

import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.entities.felicityVillage.FVReportEntity;

public class ReportInfo {
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private Long id;//县报的id
	private Long reportId;//省年报实体的id
	
	private String name;//report 的名字
	
	private int annual;//年度
	
	private String startTime;//统计起始时间
	private String deadline;//统计截止时间
	
	private String startAt;//系统填报起始时间
	private String endAt;//系统填报截止时间
	
	private String status;

	
	
	public ReportInfo(FVReportEntity fvr){
		this.id = null;
		this.reportId = fvr.getId();
		this.name = fvr.getName();
		this.annual = fvr.getAnnual();
		this.startTime = sdf.format(fvr.getStartTime());
		this.deadline = sdf.format(fvr.getDeadline());
		
		this.startAt = sdf.format(fvr.getStartAt());
		this.endAt = sdf.format(fvr.getEndAt());
		
		this.status = "<font color='red'>未开始</font>";
		
	}
	
	public ReportInfo(FVMACountyReportEntity fvcr){
		this.id = fvcr.getId();
		this.reportId = fvcr.getReport().getId();
		this.name = fvcr.getReport().getName();
		
		this.annual = fvcr.getReport().getAnnual();
		this.startTime = sdf.format(fvcr.getReport().getStartTime());
		this.deadline = sdf.format(fvcr.getReport().getDeadline());
		
		this.startAt = sdf.format(fvcr.getReport().getStartAt());
		this.endAt = sdf.format(fvcr.getReport().getEndAt());
		
		switch (fvcr.getStatus()) {
		case 0:
			this.status = "<font color='red'>未开始</font>";
			break;
		case 1:
			this.status = "完成";
			break;
		case 2:
			this.status = "<font color='green'>正在填报</font>";
			break;
		case 3:
			this.status = "<font color='red'>驳回</font>";
			break;
		case 4:
			this.status = "待审核";
			break;
		default:
			this.status = "未开始";
		}
		
	}
	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public int getAnnual() {
		return annual;
	}

	public void setAnnual(int annual) {
		this.annual = annual;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getStartAt() {
		return startAt;
	}

	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}

	public String getEndAt() {
		return endAt;
	}

	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
