package cn.bonoon.entities.logs;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.entities.RuralNeedFinishCompare;
import cn.bonoon.kernel.support.entities.AbstractPersistable;

/**
 * 关于“季度报表”里，选择已经完成的村后，下一个季度数据异常情况的手动处理记录表
 * 
 * @author jackson
 *
 */
@Entity
@Table(name = "cp_maq", catalog = "db_newrmov_log")
public class ModelAreaQuarterComparer extends AbstractPersistable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -93619202790274959L;

	//表示某一次的对比处理过程
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_LOG_ID")
	private HandleLogEntity log;

	//当前对比的片区的Id，所有对比都是以一个片区为单位进行的，一个片区下有多个村，每个村下面有多个季度的报表
	@Column(name = "C_MAID")
	private long maid;
	
	@Column(name = "C_TARGETTYPE")
	private String targetType;
	
	@Column(name = "C_TARGETID")
	private long targetId;

	@Column(name = "C_NAME")
	private String name;

	@Embedded
	private RuralNeedFinishCompare rnfCompare = new RuralNeedFinishCompare();

	/*
	 * 0 表示未处理
	 * 1 表示已经处理
	 * 2 表示不处理这条记录手动
	 * 10 不处理，自动
	 * 
	 * 不管是上级还是下级的，实际处理过的，都应该被记录
	 */
	@Column(name = "C_STATUS")
	private int status;

	public HandleLogEntity getLog() {
		return log;
	}

	public void setLog(HandleLogEntity log) {
		this.log = log;
	}

	public long getMaid() {
		return maid;
	}

	public void setMaid(long maid) {
		this.maid = maid;
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

	public RuralNeedFinishCompare getRnfCompare() {
		return rnfCompare;
	}

	public void setRnfCompare(RuralNeedFinishCompare rnfCompare) {
		this.rnfCompare = rnfCompare;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
