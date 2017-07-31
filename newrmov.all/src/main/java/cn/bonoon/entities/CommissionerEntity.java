package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.kernel.support.entities.AbstractEntity;

/**
 * <pre>
 * 信息专员的信息，包括：县和市级的
 * 需要控制一个市、县的账号同时只有一个信息专员的信息有效
 * </pre>
 * @author jackson
 *
 */
@Entity
@Table(name = "t_commissioner")
public class CommissionerEntity extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7602361298900156526L;

	@ManyToOne
	@JoinColumn(name = "R_UNIT_ID")
	private UnitEntity unit;

	@Column(name = "C_HISTORY")
	private boolean history;

	@Column(name = "C_JOB")
	private String job;//职位、职务

	@Column(name = "C_PHONE1")
	private String phone1;
	
	@Column(name = "C_PHONE2")
	private String phone2;
	

	@Column(name = "C_QQNUNBER")
	private String qqNum;

	public UnitEntity getUnit() {
		return unit;
	}

	public void setUnit(UnitEntity unit) {
		this.unit = unit;
	}

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getQqNum() {
		return qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	
}
