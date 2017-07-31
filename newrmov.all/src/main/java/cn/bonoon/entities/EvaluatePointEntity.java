package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import cn.bonoon.kernel.support.entities.AbstractPersistable;

/**
 * @author Administrator
 * @version 1.0
 * @created 12-十一月-2014 23:12:30
 */
@MappedSuperclass
public abstract class EvaluatePointEntity<E extends ApplicantEntity> extends AbstractPersistable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2248656758411503486L;

	@ManyToOne
	@JoinColumn(name = "R_APPLICANT_ID")
	private E applicant;

	@Column(name = "C_CODE")
	private String code;
	/**
	 * 审核得分
	 */
	@Column(name = "C_POINTAUDIT")
	private double pointAudit;
	/**
	 * 自评得分
	 */
	@Column(name = "C_POINTSELF")
	private double pointSelf;

	@Column(name = "C_POINTMAX")
	private double pointMax;
	
	public E getApplicant() {
		return applicant;
	}
	public void setApplicant(E applicant) {
		this.applicant = applicant;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getPointAudit() {
		return pointAudit;
	}
	public void setPointAudit(double pointAudit) {
		this.pointAudit = pointAudit;
	}
	public double getPointSelf() {
		return pointSelf;
	}
	public void setPointSelf(double pointSelf) {
		this.pointSelf = pointSelf;
	}
	public double getPointMax() {
		return pointMax;
	}
	public void setPointMax(double pointMax) {
		this.pointMax = pointMax;
	}

}