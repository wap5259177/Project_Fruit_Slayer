package cn.bonoon.controllers.optimize.province.survey;

import java.text.SimpleDateFormat;

import cn.bonoon.entities.SurveySummaryProvinceEntity;



public class SurveyReport {

	/**
	 * 数据结构
	 */
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private Long id;
	private int annual;
	private String deadline;
	private String startAt;
	private String endAt;
	
	private int needReport;
	private int startReport;
	private int finishReport;
	private String status;
	
	//还可以添加其他的属性
	
	public SurveyReport(SurveySummaryProvinceEntity s){
		this.id = s.getId();
		this.annual = s.getAnnual();
		this.deadline = sdf.format(s.getDeadline());
		this.startAt = sdf.format(s.getStartAt());
		this.endAt = sdf.format(s.getEndAt());
		
		this.needReport = s.getNeedReport();
		this.startReport = s.getStartReport();
		this.finishReport = s.getFinishReport();
		if(s.getStatus()==0){
			this.status  = "进行中";
		}else if(s.getStatus()==1){
			this.status = "完成";
		}
	}
	
	public int getAnnual() {
		return annual;
	}
	public void setAnnual(int annual) {
		this.annual = annual;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNeedReport() {
		return needReport;
	}

	public void setNeedReport(int needReport) {
		this.needReport = needReport;
	}

	public int getStartReport() {
		return startReport;
	}

	public void setStartReport(int startReport) {
		this.startReport = startReport;
	}

	public int getFinishReport() {
		return finishReport;
	}

	public void setFinishReport(int finishReport) {
		this.finishReport = finishReport;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
