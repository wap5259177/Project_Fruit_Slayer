package cn.bonoon.core.json;

/**
 * 项目月度汇报那里表单提交的数据封装
 */
public class ProjectReportItemBean {
	private Long id;
	private Double state;
	private Double special;
	private Double province;
	private Double city;
	private Double county;
	private Double social;
	private Double mass;
	private Double other;
	private Double total;
	
	private Integer status;
	private String reason;
	
	private Integer labour;
	private Double spend;
	public Double getState() {
		return state;
	}
	public void setState(Double state) {
		this.state = state;
	}
	public Double getSpecial() {
		return special;
	}
	public void setSpecial(Double special) {
		this.special = special;
	}
	public Double getProvince() {
		return province;
	}
	public void setProvince(Double province) {
		this.province = province;
	}
	public Double getCity() {
		return city;
	}
	public void setCity(Double city) {
		this.city = city;
	}
	public Double getCounty() {
		return county;
	}
	public void setCounty(Double county) {
		this.county = county;
	}
	public Double getSocial() {
		return social;
	}
	public void setSocial(Double social) {
		this.social = social;
	}
	public Double getMass() {
		return mass;
	}
	public void setMass(Double mass) {
		this.mass = mass;
	}
	public Double getOther() {
		return other;
	}
	public void setOther(Double other) {
		this.other = other;
	}
	public Integer getLabour() {
		return labour;
	}
	public void setLabour(Integer labour) {
		this.labour = labour;
	}
	public Double getSpend() {
		return spend;
	}
	public void setSpend(Double spend) {
		this.spend = spend;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	
	
}
