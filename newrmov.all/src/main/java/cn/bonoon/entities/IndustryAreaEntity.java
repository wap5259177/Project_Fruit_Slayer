package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bonoon.kernel.support.entities.AbstractEntity;
import cn.bonoon.entities.ModelAreaEntity;

@Entity
@Table(name = "t_industryarea")
public class IndustryAreaEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6417406221177143465L;

	@ManyToOne
	@JoinColumn(name = "R_MODELAREA_ID")
	private ModelAreaEntity modelArea;

	@ManyToOne
	@JoinColumn(name = "R_RURAL_ID")
	private BaseRuralEntity rural;

	@Column(name = "C_CODE", length = 50)
	private String code;

	@Column(name = "C_STATUS")
	private int status;
	
	@Column(name = "C_REPORTDATE")
	private Date reportDate;

	@Column(name = "C_TOWN")
	private String town;

	@Column(name = "C_VILLAGENAME")
	private String villageName;// 村名称

	@Column(name = "C_PLACEID")
	private Long placeId;

	@Column(name = "C_VILLAGEFEA")
	private String villageFea;// 村属性

	@Column(name = "C_COOPNAME")
	private String coopName;// 专业合作社名称

	@Column(name = "C_LEADERNAME")
	private String leaderName;// 负责人姓名

	@Column(name = "C_LEADERPHONE")
	private String leaderPhone;// 负责人手机

	@Column(name = "C_MEMBERHOUS")
	private int memberHous;// 成员总户数

	@Column(name = "C_NONMEMBERHOUS")
	private int nonMemberHous;// 带动非成员户数

	@Column(name = "C_REGISTERFUNDS")
	private double registerFunds;// 注册资金

	@Column(name = "C_BUSINESSSCOPE")
	private String businessScope;// 主要经营范围

	@Column(name = "C_QUANTITYSCALE")
	private int quantityScale;// 规模数量

	@Column(name = "C_SCALEUNITS")
	private String scaleUnits;// 规模单位

	@Column(name = "C_REGITRADEMARK")
	private int regiTradeMark;// 注册商标个数

	@Column(name = "C_AGRICULPROS")
	private int agriculPros;// 拥有使用农产品质量认证数

	@Column(name = "C_FREEPOLLUTION")
	private int freePollution;// 无公害农产品产地认定个数

	@Column(name = "C_SPECIALPRODUCT")
	private String specialProduct;// 特色产品名称

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getVillageFea() {
		return villageFea;
	}

	public void setVillageFea(String villageFea) {
		this.villageFea = villageFea;
	}

//	public ModelAreaEntity getModelArea() {
//		return modelArea;
//	}
//
//	public void setModelArea(ModelAreaEntity modelArea) {
//		this.modelArea = modelArea;
//	}

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BaseRuralEntity getRural() {
		return rural;
	}

	public void setRural(BaseRuralEntity rural) {
		this.rural = rural;
	}

	public ModelAreaEntity getModelArea() {
		return modelArea;
	}

	public void setModelArea(ModelAreaEntity modelArea) {
		this.modelArea = modelArea;
	}

}
