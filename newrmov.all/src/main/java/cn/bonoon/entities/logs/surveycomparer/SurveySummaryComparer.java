package cn.bonoon.entities.logs.surveycomparer;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.entities.logs.HandleLogEntity;
import cn.bonoon.kernel.support.entities.AbstractPersistable;

@Entity
@Table(name = "cp_ss", catalog = "db_newrmov_log")
public class SurveySummaryComparer  extends AbstractPersistable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5857593725055039859L;
	
	//表示某一次的对比处理过程
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_LOG_ID")
	private HandleLogEntity log;

	@Column(name = "C_TARGETTYPE")
	private String targetType;

	@Column(name = "C_TARGETID")
	private long targetId;

	@Column(name = "C_NAME")
	private String name;

	@Embedded
	private SurveySummaryCountComparer sscCompare = new SurveySummaryCountComparer();

	

	public HandleLogEntity getLog() {
		return log;
	}

	public void setLog(HandleLogEntity log) {
		this.log = log;
	}


	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SurveySummaryCountComparer getSscCompare() {
		return sscCompare;
	}

	public void setSscCompare(SurveySummaryCountComparer sscCompare) {
		this.sscCompare = sscCompare;
	}

	
}
