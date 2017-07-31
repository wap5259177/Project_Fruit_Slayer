package cn.bonoon.entities.felicityVillage;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 11个县的年报
 * 2014年1次,2015年2次(上下半年各1次上：6月30；下：12月31日),2016年1一次
 * 一次年报由下面的11个县来填报
 * @author xiaowu
 *
 */

@Entity
@Table(name = "t_fvreport")
public class FVReportEntity extends AbstractFVFelicityEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2496159541576808088L;
	
	@OneToMany(mappedBy = "report")
	private List<FVMACountyReportEntity> countyReports;
	
	
	/**
	 * 年度
	 */
	@Column(name = "C_ANNUAL")
	private int annual;
	
	/**
	 * 系统开始填报时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_STARTAT")
	private Date startAt;
	/**
	 * 系统结束填报时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_ENDAT")
	private Date endAt;
	
	
	/**
	 * 统计起始时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_STARTTIME")
	private Date startTime;
	/**
	 * 统计截止时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "C_DEADLINE")
	private Date deadline;
	
	/**
	 * 如果指定的11个县都提交了，则自动完成
	 * 梅州:8个
	 * 韶关:南雄
	 * 潮州:饶平
	 */
	@Column(name = "C_STATUS")
	private int status;
//	
//	
//	
//	/**
//	 * <pre>
//	 * 该记录的创建者的Id。
//	 * 一般不需要显示创建者ID，只显示创建者的登录名creatorName。
//	 * </pre>
//	 */
//	@Column(name = "C_CREATORID")
//	private Long creatorId;
//
//	/**
//	 * 创建时间
//	 */
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "C_CREATEAT", nullable = false)
//	private Date createAt;
//
//	/**
//	 * <pre>
//	 * 创建人的名称，登录账号。
//	 * 一般可用于直接显示，如列表或查看的页面等。
//	 * 一般creatorName不作为关联关系，关联关系请使用createrId。
//	 * </pre>
//	 */
//	@Column(name = "C_CREATORNAME")
//	private String creatorName;
//	

	public List<FVMACountyReportEntity> getCountyReports() {
		return countyReports;
	}

	public void setCountyReports(List<FVMACountyReportEntity> countyReports) {
		this.countyReports = countyReports;
	}

	public int getAnnual() {
		return annual;
	}

	public void setAnnual(int annual) {
		this.annual = annual;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
