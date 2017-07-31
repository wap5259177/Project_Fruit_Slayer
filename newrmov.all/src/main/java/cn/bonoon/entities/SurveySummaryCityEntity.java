package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 市级的调查表
 * @author jackson
 *
 */
@Entity
@Table(name = "t_sscity")
public class SurveySummaryCityEntity extends AbstractSurveySummaryEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -760123719370759453L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "R_UNIT_ID")
	private UnitEntity unit; // 上报单位

	@ManyToOne
	@JoinColumn(name = "R_PROVINCE_ID")
	private SurveySummaryProvinceEntity province;

	/**
	 * 由省创建的时候，状态为：0；这时需要市级用户点击确定上报县数据的，
	 * 确定时系统会自动创建县的空白记录，这时状态为2；当所有县的数据都填
	 * 写完成后，需要点击完成上报，并提交到省进行统计处理，这时状态为：1
	 * 
	 * 3为这一条记录是不需要上报的
	 */
	@Column(name = "C_STATUS")
	private int status;
	@Temporal(TemporalType.DATE)
	@Column(name = "C_REPORTAT")
	private Date reportAt;
	@Column(name = "C_REPORTERID")
	private Long reporterId;
	@Column(name = "C_REPORTERNAME")
	private String reporterName;
	/**
	 * 对一个市进行催报，主要是在首页进行着重显示
	 */
	@Column(name = "C_URGE")
	private int urge;//
	
	@Column(name = "C_COUNTYCOUNT")
	private int countyCount;

	@Column(name = "C_CITYID")
	private Long cityId;
	@Column(name = "C_CITYNAME")
	private String cityName;
	
	public SurveySummaryProvinceEntity getProvince() {
		return province;
	}
	public void setProvince(SurveySummaryProvinceEntity province) {
		this.province = province;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getReportAt() {
		return reportAt;
	}
	public void setReportAt(Date reportAt) {
		this.reportAt = reportAt;
	}
	public Long getReporterId() {
		return reporterId;
	}
	public void setReporterId(Long reporterId) {
		this.reporterId = reporterId;
	}
	public String getReporterName() {
		return reporterName;
	}
	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}
	public int getUrge() {
		return urge;
	}
	public void setUrge(int urge) {
		this.urge = urge;
	}
	public UnitEntity getUnit() {
		return unit;
	}
	public void setUnit(UnitEntity unit) {
		this.unit = unit;
	}
	public int getCountyCount() {
		return countyCount;
	}
	public void setCountyCount(int countyCount) {
		this.countyCount = countyCount;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
