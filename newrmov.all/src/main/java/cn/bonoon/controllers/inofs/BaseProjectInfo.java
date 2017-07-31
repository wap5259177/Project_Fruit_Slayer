package cn.bonoon.controllers.inofs;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.ObjectEditor;

public class BaseProjectInfo extends ObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1843592544926742968L;

	@TransformField("rural.id")
	private Long newRural;
	@TransformField("rural.name")
	private String ruralName;
	// 20150128
	@TransformField("rural.naturalVillage")
	private String naturalVillage;

	private String code;
	// 资金
	@TransformField("investment.")
	private Double totalFunds;
	@TransformField("investment.")
	private Double stateFunds;
	@TransformField("investment.")
	private Double provinceFunds;
	@TransformField("investment.")
	private Double localFunds;
	@TransformField("investment.")
	private Double socialFunds;
	@TransformField("investment.")
	private Double massFunds;
	@TransformField("investment.")
	private Double specialFunds;
	@TransformField("investment.")
	private Double otherFunds;
	@TransformField("investment.")
	private Double cityFunds;
	@TransformField("investment.")
	private Double countyFunds;
	// 累计投入
	@TransformField("totalInvestment.totalFunds")
	private Double ttotalFunds;
	@TransformField("totalInvestment.stateFunds")
	private Double tstateFunds;
	@TransformField("totalInvestment.provinceFunds")
	private Double tprovinceFunds;
	@TransformField("totalInvestment.localFunds")
	private Double tlocalFunds;
	@TransformField("totalInvestment.socialFunds")
	private Double tsocialFunds;
	@TransformField("totalInvestment.massFunds")
	private Double tmassFunds;
	@TransformField("totalInvestment.specialFunds")
	private Double tspecialFunds;
	@TransformField("totalInvestment.otherFunds")
	private Double totherFunds;
	@TransformField("totalInvestment.cityFunds")
	private Double tcityFunds;
	@TransformField("totalInvestment.countyFunds")
	private Double tcountyFunds;

	// 初始资金20151208
	@TransformField("sourceInvestment.totalFunds")
	private Double stotalFunds;
	@TransformField("sourceInvestment.stateFunds")
	private Double sstateFunds;
	@TransformField("sourceInvestment.provinceFunds")
	private Double sprovinceFunds;
	@TransformField("sourceInvestment.localFunds")
	private Double slocalFunds;
	@TransformField("sourceInvestment.socialFunds")
	private Double ssocialFunds;
	@TransformField("sourceInvestment.massFunds")
	private Double smassFunds;
	@TransformField("sourceInvestment.specialFunds")
	private Double sspecialFunds;
	@TransformField("sourceInvestment.otherFunds")
	private Double sotherFunds;
	@TransformField("sourceInvestment.cityFunds")
	private Double scityFunds;
	@TransformField("sourceInvestment.countyFunds")
	private Double scountyFunds;

	private String proProperty;
	private String name;
	private String projectType;
	private String exactKind;
	private int labourCount;
	private double spend;

	private int startAtY;
	private int startAtM;
	private int endAtY;
	private int endAtM;
	private int finishAtY;
	private int finishAtM;

	private String remark;
	// 项目是否覆盖多个自然村20160127
	private String cover;

	public Long getNewRural() {
		return newRural;
	}

	public void setNewRural(Long newRural) {
		this.newRural = newRural;
	}

	public String getRuralName() {
		return ruralName;
	}

	public void setRuralName(String ruralName) {
		this.ruralName = ruralName;
	}

	public String getNaturalVillage() {
		return naturalVillage;
	}

	public void setNaturalVillage(String naturalVillage) {
		this.naturalVillage = naturalVillage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getTotalFunds() {
		return totalFunds;
	}

	public void setTotalFunds(Double totalFunds) {
		this.totalFunds = totalFunds;
	}

	public Double getStateFunds() {
		return stateFunds;
	}

	public void setStateFunds(Double stateFunds) {
		this.stateFunds = stateFunds;
	}

	public Double getProvinceFunds() {
		return provinceFunds;
	}

	public void setProvinceFunds(Double provinceFunds) {
		this.provinceFunds = provinceFunds;
	}

	public Double getLocalFunds() {
		return localFunds;
	}

	public void setLocalFunds(Double localFunds) {
		this.localFunds = localFunds;
	}

	public Double getSocialFunds() {
		return socialFunds;
	}

	public void setSocialFunds(Double socialFunds) {
		this.socialFunds = socialFunds;
	}

	public Double getMassFunds() {
		return massFunds;
	}

	public void setMassFunds(Double massFunds) {
		this.massFunds = massFunds;
	}

	public Double getSpecialFunds() {
		return specialFunds;
	}

	public void setSpecialFunds(Double specialFunds) {
		this.specialFunds = specialFunds;
	}

	public Double getOtherFunds() {
		return otherFunds;
	}

	public void setOtherFunds(Double otherFunds) {
		this.otherFunds = otherFunds;
	}

	public Double getCityFunds() {
		return cityFunds;
	}

	public void setCityFunds(Double cityFunds) {
		this.cityFunds = cityFunds;
	}

	public Double getCountyFunds() {
		return countyFunds;
	}

	public void setCountyFunds(Double countyFunds) {
		this.countyFunds = countyFunds;
	}

	public Double getTtotalFunds() {
		return ttotalFunds;
	}

	public void setTtotalFunds(Double ttotalFunds) {
		this.ttotalFunds = ttotalFunds;
	}

	public Double getTstateFunds() {
		return tstateFunds;
	}

	public void setTstateFunds(Double tstateFunds) {
		this.tstateFunds = tstateFunds;
	}

	public Double getTprovinceFunds() {
		return tprovinceFunds;
	}

	public void setTprovinceFunds(Double tprovinceFunds) {
		this.tprovinceFunds = tprovinceFunds;
	}

	public Double getTlocalFunds() {
		return tlocalFunds;
	}

	public void setTlocalFunds(Double tlocalFunds) {
		this.tlocalFunds = tlocalFunds;
	}

	public Double getTsocialFunds() {
		return tsocialFunds;
	}

	public void setTsocialFunds(Double tsocialFunds) {
		this.tsocialFunds = tsocialFunds;
	}

	public Double getTmassFunds() {
		return tmassFunds;
	}

	public void setTmassFunds(Double tmassFunds) {
		this.tmassFunds = tmassFunds;
	}

	public Double getTspecialFunds() {
		return tspecialFunds;
	}

	public void setTspecialFunds(Double tspecialFunds) {
		this.tspecialFunds = tspecialFunds;
	}

	public Double getTotherFunds() {
		return totherFunds;
	}

	public void setTotherFunds(Double totherFunds) {
		this.totherFunds = totherFunds;
	}

	public Double getTcityFunds() {
		return tcityFunds;
	}

	public void setTcityFunds(Double tcityFunds) {
		this.tcityFunds = tcityFunds;
	}

	public Double getTcountyFunds() {
		return tcountyFunds;
	}

	public void setTcountyFunds(Double tcountyFunds) {
		this.tcountyFunds = tcountyFunds;
	}

	public Double getStotalFunds() {
		return stotalFunds;
	}

	public void setStotalFunds(Double stotalFunds) {
		this.stotalFunds = stotalFunds;
	}

	public Double getSstateFunds() {
		return sstateFunds;
	}

	public void setSstateFunds(Double sstateFunds) {
		this.sstateFunds = sstateFunds;
	}

	public Double getSprovinceFunds() {
		return sprovinceFunds;
	}

	public void setSprovinceFunds(Double sprovinceFunds) {
		this.sprovinceFunds = sprovinceFunds;
	}

	public Double getSlocalFunds() {
		return slocalFunds;
	}

	public void setSlocalFunds(Double slocalFunds) {
		this.slocalFunds = slocalFunds;
	}

	public Double getSsocialFunds() {
		return ssocialFunds;
	}

	public void setSsocialFunds(Double ssocialFunds) {
		this.ssocialFunds = ssocialFunds;
	}

	public Double getSmassFunds() {
		return smassFunds;
	}

	public void setSmassFunds(Double smassFunds) {
		this.smassFunds = smassFunds;
	}

	public Double getSspecialFunds() {
		return sspecialFunds;
	}

	public void setSspecialFunds(Double sspecialFunds) {
		this.sspecialFunds = sspecialFunds;
	}

	public Double getSotherFunds() {
		return sotherFunds;
	}

	public void setSotherFunds(Double sotherFunds) {
		this.sotherFunds = sotherFunds;
	}

	public Double getScityFunds() {
		return scityFunds;
	}

	public void setScityFunds(Double scityFunds) {
		this.scityFunds = scityFunds;
	}

	public Double getScountyFunds() {
		return scountyFunds;
	}

	public void setScountyFunds(Double scountyFunds) {
		this.scountyFunds = scountyFunds;
	}

	public String getProProperty() {
		return proProperty;
	}

	public void setProProperty(String proProperty) {
		this.proProperty = proProperty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getExactKind() {
		return exactKind;
	}

	public void setExactKind(String exactKind) {
		this.exactKind = exactKind;
	}

	public int getLabourCount() {
		return labourCount;
	}

	public void setLabourCount(int labourCount) {
		this.labourCount = labourCount;
	}

	public double getSpend() {
		return spend;
	}

	public void setSpend(double spend) {
		this.spend = spend;
	}

	public int getStartAtY() {
		return startAtY;
	}

	public void setStartAtY(int startAtY) {
		this.startAtY = startAtY;
	}

	public int getStartAtM() {
		return startAtM;
	}

	public void setStartAtM(int startAtM) {
		this.startAtM = startAtM;
	}

	public int getEndAtY() {
		return endAtY;
	}

	public void setEndAtY(int endAtY) {
		this.endAtY = endAtY;
	}

	public int getEndAtM() {
		return endAtM;
	}

	public void setEndAtM(int endAtM) {
		this.endAtM = endAtM;
	}

	public int getFinishAtY() {
		return finishAtY;
	}

	public void setFinishAtY(int finishAtY) {
		this.finishAtY = finishAtY;
	}

	public int getFinishAtM() {
		return finishAtM;
	}

	public void setFinishAtM(int finishAtM) {
		this.finishAtM = finishAtM;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
