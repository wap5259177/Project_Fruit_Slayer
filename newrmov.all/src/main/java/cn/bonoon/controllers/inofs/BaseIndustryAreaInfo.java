package cn.bonoon.controllers.inofs;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.support.models.ObjectEditor;

@Transform
public class BaseIndustryAreaInfo extends ObjectEditor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5768795128889569373L;

	@TransformField("rural.id")
	private Long rural;
	@TransformField("rural.name")
	private String ruralName;
	private String villageName;//行政村名20151125
	private String code;
	private String reportDate;
	private String villageFea;
	private String coopName;
	private String leaderName;
	private String leaderPhone;
	private int memberHous;
	private int nonMemberHous;
	private double registerFunds;
	private String businessScope;
	private int quantityScale;
	private String scaleUnits;
	private int regiTradeMark;
	private int agriculPros;
	private int freePollution;
	private String specialProduct;
	public Long getRural() {
		return rural;
	}
	public void setRural(Long rural) {
		this.rural = rural;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getVillageFea() {
		return villageFea;
	}
	public void setVillageFea(String villageFea) {
		this.villageFea = villageFea;
	}
	public String getCoopName() {
		return coopName;
	}
	public void setCoopName(String coopName) {
		this.coopName = coopName;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getLeaderPhone() {
		return leaderPhone;
	}
	public void setLeaderPhone(String leaderPhone) {
		this.leaderPhone = leaderPhone;
	}
	public int getMemberHous() {
		return memberHous;
	}
	public void setMemberHous(int memberHous) {
		this.memberHous = memberHous;
	}
	public int getNonMemberHous() {
		return nonMemberHous;
	}
	public void setNonMemberHous(int nonMemberHous) {
		this.nonMemberHous = nonMemberHous;
	}
	public double getRegisterFunds() {
		return registerFunds;
	}
	public void setRegisterFunds(double registerFunds) {
		this.registerFunds = registerFunds;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public int getQuantityScale() {
		return quantityScale;
	}
	public void setQuantityScale(int quantityScale) {
		this.quantityScale = quantityScale;
	}
	public String getScaleUnits() {
		return scaleUnits;
	}
	public void setScaleUnits(String scaleUnits) {
		this.scaleUnits = scaleUnits;
	}
	public int getRegiTradeMark() {
		return regiTradeMark;
	}
	public void setRegiTradeMark(int regiTradeMark) {
		this.regiTradeMark = regiTradeMark;
	}
	public int getAgriculPros() {
		return agriculPros;
	}
	public void setAgriculPros(int agriculPros) {
		this.agriculPros = agriculPros;
	}
	public int getFreePollution() {
		return freePollution;
	}
	public void setFreePollution(int freePollution) {
		this.freePollution = freePollution;
	}
	public String getSpecialProduct() {
		return specialProduct;
	}
	public void setSpecialProduct(String specialProduct) {
		this.specialProduct = specialProduct;
	}
	public String getRuralName() {
		return ruralName;
	}
	public void setRuralName(String ruralName) {
		this.ruralName = ruralName;
	}
	public String getVillageName() {
		return villageName;
	}
	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}
	
}
