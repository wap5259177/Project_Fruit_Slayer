package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_townapplicant")
public class TownApplicantEntity extends ApplicantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6642019734774340686L;

	/**
	 * 镇域面积（平方公里）
	 */
	@Column(name = "C_AREA")
	private double area;
	/**
	 * 城镇人口
	 */
	@Column(name = "C_CITYPOPULATION")
	private int cityPopulation;
	/**
	 * 镇建成区面积（平方公里）
	 */
	@Column(name = "C_DOWNTOWNAREA")
	private double downtownArea;
	/**
	 * 农业人口
	 */
	@Column(name = "C_FARMERPOPULATION")
	private int farmerPopulation;
	/**
	 * 地方可支配性财政收入（万元）
	 */
	@Column(name = "C_FINANCIALINCOME")
	private double financialIncome;
	/**
	 * 全镇GDP（万元）
	 */
	@Column(name = "C_GDP")
	private double gdp;
	
	/**
	 * 半年以上暂住人口
	 */
	@Column(name = "C_TEMPPOPULATION")
	private int tempPopulation;

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public int getCityPopulation() {
		return cityPopulation;
	}

	public void setCityPopulation(int cityPopulation) {
		this.cityPopulation = cityPopulation;
	}

	public double getDowntownArea() {
		return downtownArea;
	}

	public void setDowntownArea(double downtownArea) {
		this.downtownArea = downtownArea;
	}

	public int getFarmerPopulation() {
		return farmerPopulation;
	}

	public void setFarmerPopulation(int farmerPopulation) {
		this.farmerPopulation = farmerPopulation;
	}

	public double getFinancialIncome() {
		return financialIncome;
	}

	public void setFinancialIncome(double financialIncome) {
		this.financialIncome = financialIncome;
	}

	public double getGdp() {
		return gdp;
	}

	public void setGdp(double gdp) {
		this.gdp = gdp;
	}

	public int getTempPopulation() {
		return tempPopulation;
	}

	public void setTempPopulation(int tempPopulation) {
		this.tempPopulation = tempPopulation;
	}
}
