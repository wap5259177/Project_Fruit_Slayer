package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class DepartmentOpinionEmbeddable {
	//意见
	private String opinion;

	@Temporal(TemporalType.DATE)
	private Date opinionAt;
	
//	private int year;
//	private int month;
//	private int day;
	
	private Long auditorId;
	private String auditorName;
	
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
//	public int getYear() {
//		return year;
//	}
//	public void setYear(int year) {
//		this.year = year;
//	}
//	public int getMonth() {
//		return month;
//	}
//	public void setMonth(int month) {
//		this.month = month;
//	}
//	public int getDay() {
//		return day;
//	}
//	public void setDay(int day) {
//		this.day = day;
//	}
	
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	public Long getAuditorId() {
		return auditorId;
	}
	public void setAuditorId(Long auditorId) {
		this.auditorId = auditorId;
	}
	public Date getOpinionAt() {
		return opinionAt;
	}
	public void setOpinionAt(Date opinionAt) {
		this.opinionAt = opinionAt;
	}
}
