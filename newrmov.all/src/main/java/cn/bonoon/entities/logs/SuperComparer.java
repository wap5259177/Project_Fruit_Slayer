package cn.bonoon.entities.logs;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import cn.bonoon.kernel.support.entities.AbstractPersistable;
/**
 * 某次比较通用字段
 * 
 * @author wsf
 * @creation 2016-12-7 
 *
 */
@MappedSuperclass
public class SuperComparer extends AbstractPersistable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 454247228594363506L;

	// 表示某一次的对比处理过程
	@ManyToOne
	@JoinColumn(name = "R_LOG_ID")
	private HandleLogEntity log;

	@Column(name = "C_TARGETTYPE")
	private String targetType;

	@Column(name = "C_TARGETID")
	private long targetId;

	@Column(name = "C_NAME")
	private String name;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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
}
