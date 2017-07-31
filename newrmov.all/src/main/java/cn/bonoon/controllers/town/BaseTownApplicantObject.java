package cn.bonoon.controllers.town;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail;

public class BaseTownApplicantObject extends ObjectEditor implements TownApplicantDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2169453623484895444L;

	/**
	 * 镇域面积（平方公里）
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private Double area;
	/**
	 * 城镇人口
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private Integer cityPopulation;
	/**
	 * 镇建成区面积（平方公里）
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private Double downtownArea;
	/**
	 * 农业人口
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private Integer farmerPopulation;
	/**
	 * 地方可支配性财政收入（万元）
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private Double financialIncome;
	/**
	 * 全镇GDP（万元）
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private Double gdp;
	/**
	 * 半年以上暂住人口
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private Integer tempPopulation;

	/**
	 * 人均纯收入（元）
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private Integer personIncome;
	/**
	 * 总人口
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private Integer population;
	/**
	 * 国家级著名农产品商标，有多个用逗号隔开
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private String famousBrandNation;
	/**
	 * 省级著名农产品商标，有多个用逗号隔开
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private String famousBrandProvince;
	/**
	 * 市级称号，有多个用逗号隔开
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private String honorCity;
	/**
	 * 国家级称号，有多个用逗号隔开
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private String honorNation;
	/**
	 * 省级称号，有多个用逗号隔开
	 */
	@TransformField
	@PropertyDetail(ignore = true)
	private String honorProvince;
	
	@TransformField
	@PropertyDetail(ignore = true)
	private String themeDirection;

	@TransformField
	@PropertyDetail(ignore = true)
	private Double investmentLocal;
	@TransformField
	@PropertyDetail(ignore = true)
	private Double investmentNation;
	@TransformField
	@PropertyDetail(ignore = true)
	private Double investmentOther;
	@TransformField
	@PropertyDetail(ignore = true)
	private Double investmentProvince;
	@TransformField
	@PropertyDetail(ignore = true)
	private Double investmentSelf;
	@TransformField
	@PropertyDetail(ignore = true)
	private Double investmentSocial;
	@TransformField
	@PropertyDetail(ignore = true)
	private Double totalInvestment;
	
	public Double getInvestmentLocal() {
		return investmentLocal;
	}

	public void setInvestmentLocal(Double investmentLocal) {
		this.investmentLocal = investmentLocal;
	}

	public Double getInvestmentNation() {
		return investmentNation;
	}

	public void setInvestmentNation(Double investmentNation) {
		this.investmentNation = investmentNation;
	}

	public Double getInvestmentOther() {
		return investmentOther;
	}

	public void setInvestmentOther(Double investmentOther) {
		this.investmentOther = investmentOther;
	}

	public Double getInvestmentProvince() {
		return investmentProvince;
	}

	public void setInvestmentProvince(Double investmentProvince) {
		this.investmentProvince = investmentProvince;
	}

	public Double getInvestmentSelf() {
		return investmentSelf;
	}

	public void setInvestmentSelf(Double investmentSelf) {
		this.investmentSelf = investmentSelf;
	}

	public Double getInvestmentSocial() {
		return investmentSocial;
	}

	public void setInvestmentSocial(Double investmentSocial) {
		this.investmentSocial = investmentSocial;
	}

	public Double getTotalInvestment() {
		return totalInvestment;
	}

	public void setTotalInvestment(Double totalInvestment) {
		this.totalInvestment = totalInvestment;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Integer getCityPopulation() {
		return cityPopulation;
	}

	public void setCityPopulation(Integer cityPopulation) {
		this.cityPopulation = cityPopulation;
	}

	public Double getDowntownArea() {
		return downtownArea;
	}

	public void setDowntownArea(Double downtownArea) {
		this.downtownArea = downtownArea;
	}

	public Integer getFarmerPopulation() {
		return farmerPopulation;
	}

	public void setFarmerPopulation(Integer farmerPopulation) {
		this.farmerPopulation = farmerPopulation;
	}

	public Double getFinancialIncome() {
		return financialIncome;
	}

	public void setFinancialIncome(Double financialIncome) {
		this.financialIncome = financialIncome;
	}

	public Double getGdp() {
		return gdp;
	}

	public void setGdp(Double gdp) {
		this.gdp = gdp;
	}

	public Integer getTempPopulation() {
		return tempPopulation;
	}

	public void setTempPopulation(Integer tempPopulation) {
		this.tempPopulation = tempPopulation;
	}

	public Integer getPersonIncome() {
		return personIncome;
	}

	public void setPersonIncome(Integer personIncome) {
		this.personIncome = personIncome;
	}

	public Integer getPopulation() {
		return population;
	}

	public void setPopulation(Integer population) {
		this.population = population;
	}

	public String getFamousBrandNation() {
		return famousBrandNation;
	}

	public void setFamousBrandNation(String famousBrandNation) {
		this.famousBrandNation = famousBrandNation;
	}

	public String getFamousBrandProvince() {
		return famousBrandProvince;
	}

	public void setFamousBrandProvince(String famousBrandProvince) {
		this.famousBrandProvince = famousBrandProvince;
	}

	public String getHonorCity() {
		return honorCity;
	}

	public void setHonorCity(String honorCity) {
		this.honorCity = honorCity;
	}

	public String getHonorNation() {
		return honorNation;
	}

	public void setHonorNation(String honorNation) {
		this.honorNation = honorNation;
	}

	public String getHonorProvince() {
		return honorProvince;
	}

	public void setHonorProvince(String honorProvince) {
		this.honorProvince = honorProvince;
	}

	public String getThemeDirection() {
		return themeDirection;
	}

	public void setThemeDirection(String themeDirection) {
		this.themeDirection = themeDirection;
	}

}
