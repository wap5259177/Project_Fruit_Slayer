package cn.bonoon.entities;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.support.entities.AbstractEntity;

/**
 * 
 * @author jackson
 *
 */
@MappedSuperclass
public abstract class ApplicantEntity extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8273485159362425097L;

	@Column(name = "C_STATUS")
	private int status;
	
	@Column(name = "C_YEAR")
	private int year;
	
	@Column(name = "C_COUNTYNAME")
	private String countyName;

	@ManyToOne
	@JoinColumn(name = "R_PLACE_ID")
	private PlaceEntity place;
	
	//申报表所属的城市的ID
	@Column(name = "C_CITYID")
	private Long cityId;

	@Column(name = "C_CITYNAME")
	private String cityName;

	@Temporal(TemporalType.DATE)
	@Column(name = "C_APPLICANTAT")
	private Date applicantAt;
	
	@Column(name = "C_CONTACTNAME")
	private String contactName;
	@Column(name = "C_CONTACTPHONE")
	private String contactPhone;

	//县（市、区）名镇建设工作牵头负责部门意见
	//县（市、区）名村建设工作牵头负责部门意见
	@Embedded
	@AttributeOverrides({       
		@AttributeOverride(name="opinion", column=@Column(name = "C_COOPINION")),
	    @AttributeOverride(name="opinionAt", column=@Column(name = "C_COOPINIONAT")),
	    @AttributeOverride(name="auditorId", column=@Column(name = "C_COAUDITORID")),
	    @AttributeOverride(name="auditorName", column=@Column(name = "C_COAUDITORNAME"))
	})
	private DepartmentOpinionEmbeddable countyOpinion = new DepartmentOpinionEmbeddable();

	@Embedded
	@AttributeOverrides({       
		@AttributeOverride(name="opinion", column=@Column(name = "C_CIOPINION")),
	    @AttributeOverride(name="opinionAt", column=@Column(name = "C_CIOPINIONAT")),
	    @AttributeOverride(name="auditorId", column=@Column(name = "C_CIAUDITORID")),
	    @AttributeOverride(name="auditorName", column=@Column(name = "C_CIAUDITORNAME"))
	})
	private DepartmentOpinionEmbeddable cityOpinion = new DepartmentOpinionEmbeddable();

	@Embedded
	@AttributeOverrides({       
		@AttributeOverride(name="opinion", column=@Column(name = "C_CGOPINION")),
	    @AttributeOverride(name="opinionAt", column=@Column(name = "C_CGOPINIONAT")),
	    @AttributeOverride(name="auditorId", column=@Column(name = "C_CGAUDITORID")),
	    @AttributeOverride(name="auditorName", column=@Column(name = "C_CGAUDITORNAME"))
	})
	private DepartmentOpinionEmbeddable cityGroup = new DepartmentOpinionEmbeddable();

	@Embedded
	@AttributeOverrides({       
		@AttributeOverride(name="opinion", column=@Column(name = "C_POOPINION")),
	    @AttributeOverride(name="opinionAt", column=@Column(name = "C_POOPINIONAT")),
	    @AttributeOverride(name="auditorId", column=@Column(name = "C_POAUDITORID")),
	    @AttributeOverride(name="auditorName", column=@Column(name = "C_POAUDITORNAME"))
	})
	private DepartmentOpinionEmbeddable provinceOpinion = new DepartmentOpinionEmbeddable();

	@Embedded
	@AttributeOverrides({       
		@AttributeOverride(name="opinion", column=@Column(name = "C_PGOPINION")),
	    @AttributeOverride(name="opinionAt", column=@Column(name = "C_PGOPINIONAT")),
	    @AttributeOverride(name="auditorId", column=@Column(name = "C_PGAUDITORID")),
	    @AttributeOverride(name="auditorName", column=@Column(name = "C_PGAUDITORNAME"))
	})
	private DepartmentOpinionEmbeddable provinceGroup = new DepartmentOpinionEmbeddable();

	@Column(name = "C_REJECTCONTENT")
	private String rejectContent;
	@Column(name = "C_REJECTAT")
	@Temporal(TemporalType.DATE)
	private Date rejectAt;

	@Column(name = "C_REJECTUID")
	private Long rejectUid;
	/**
	 * 国家级著名农产品商标，有多个用逗号隔开
	 */
	@Column(name = "C_FBNATION")
	private String famousBrandNation;
	/**
	 * 省级著名农产品商标，有多个用逗号隔开
	 */
	@Column(name = "C_FBPROVINCE")
	private String famousBrandProvince;
	/**
	 * 市级称号，有多个用逗号隔开
	 */
	@Column(name = "C_HONORCITY")
	private String honorCity;
	/**
	 * 国家级称号，有多个用逗号隔开
	 */
	@Column(name = "C_HONORNATION")
	private String honorNation;
	/**
	 * 省级称号，有多个用逗号隔开
	 */
	@Column(name = "C_HONORPROVINCE")
	private String honorProvince;
	/**
	 * 地方投入（万元）
	 */
	@Column(name = "C_INVESTEMNTLOCAL")
	private double investmentLocal;
	/**
	 * 中央投入（万元）
	 */
	@Column(name = "C_INVESTMENTNATION")
	private double investmentNation;
	/**
	 * 其他（万元）
	 */
	@Column(name = "C_INVESTMENTOTHER")
	private double investmentOther;
	/**
	 * 省级投入（万元）
	 */
	@Column(name = "C_INVESTMENTPROVINCE")
	private double investmentProvince;
	/**
	 * 群众自筹（万元）
	 */
	@Column(name = "C_INVESTMENTSELF")
	private double investmentSelf;
	/**
	 * 社会投入（万元）
	 */
	@Column(name = "C_INVESTMENTSOCIAL")
	private double investmentSocial;
	/**
	 * 累计总投入
	 */
	@Column(name = "C_TOTALINVESTMENT")
	private double totalInvestment;
	/**
	 * 人均纯收入（元）
	 */
	@Column(name = "C_PERSONINCOME")
	private int personIncome;

	/**
	 * 总人口
	 */
	@Column(name = "C_POPULATION")
	private int population;
	
	/**
	 * 村或镇创建的主题或方向
	 */
	@Column(name = "C_THEMEDIRECTION")
	private String themeDirection;
	
	public DepartmentOpinionEmbeddable getCityGroup() {
		if(this.cityGroup == null){
			this.cityGroup = new DepartmentOpinionEmbeddable();
		}
		return cityGroup;
	}

	public void setCityGroup(DepartmentOpinionEmbeddable cityGroup) {
		this.cityGroup = cityGroup;
	}

	public DepartmentOpinionEmbeddable getProvinceOpinion() {
		if(this.provinceOpinion == null){
			this.provinceOpinion = new DepartmentOpinionEmbeddable();
		}
		return provinceOpinion;
	}

	public void setProvinceOpinion(DepartmentOpinionEmbeddable provinceOpinion) {
		this.provinceOpinion = provinceOpinion;
	}

	public DepartmentOpinionEmbeddable getProvinceGroup() {
		if(this.provinceGroup == null){
			this.provinceGroup = new DepartmentOpinionEmbeddable();
		}
		return provinceGroup;
	}

	public void setProvinceGroup(DepartmentOpinionEmbeddable provinceGroup) {
		this.provinceGroup = provinceGroup;
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

	public double getInvestmentLocal() {
		return investmentLocal;
	}

	public void setInvestmentLocal(double investmentLocal) {
		this.investmentLocal = investmentLocal;
	}

	public double getInvestmentNation() {
		return investmentNation;
	}

	public void setInvestmentNation(double investmentNation) {
		this.investmentNation = investmentNation;
	}

	public double getInvestmentOther() {
		return investmentOther;
	}

	public void setInvestmentOther(double investmentOther) {
		this.investmentOther = investmentOther;
	}

	public double getInvestmentProvince() {
		return investmentProvince;
	}

	public void setInvestmentProvince(double investmentProvince) {
		this.investmentProvince = investmentProvince;
	}

	public double getInvestmentSelf() {
		return investmentSelf;
	}

	public void setInvestmentSelf(double investmentSelf) {
		this.investmentSelf = investmentSelf;
	}

	public double getInvestmentSocial() {
		return investmentSocial;
	}

	public void setInvestmentSocial(double investmentSocial) {
		this.investmentSocial = investmentSocial;
	}

	public int getPersonIncome() {
		return personIncome;
	}

	public void setPersonIncome(int personIncome) {
		this.personIncome = personIncome;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public String getRejectContent() {
		return rejectContent;
	}

	public void setRejectContent(String rejectContent) {
		this.rejectContent = rejectContent;
	}

	public Date getRejectAt() {
		return rejectAt;
	}

	public void setRejectAt(Date rejectAt) {
		this.rejectAt = rejectAt;
	}

	public Long getRejectUid() {
		return rejectUid;
	}

	public void setRejectUid(Long rejectUid) {
		this.rejectUid = rejectUid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
//
//	public int getYear() {
//		return year;
//	}
//
//	public void setYear(int year) {
//		this.year = year;
//	}
//
//	public int getMonth() {
//		return month;
//	}
//
//	public void setMonth(int month) {
//		this.month = month;
//	}
//
//	public int getDay() {
//		return day;
//	}
//
//	public void setDay(int day) {
//		this.day = day;
//	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public DepartmentOpinionEmbeddable getCountyOpinion() {
		if(this.countyOpinion == null){
			this.countyOpinion = new DepartmentOpinionEmbeddable();
		}
		return countyOpinion;
	}

	public void setCountyOpinion(DepartmentOpinionEmbeddable countyOpinion) {
		this.countyOpinion = countyOpinion;
	}

	public DepartmentOpinionEmbeddable getCityOpinion() {
		if(this.cityOpinion == null){
			this.cityOpinion = new DepartmentOpinionEmbeddable();
		}
		return cityOpinion;
	}

	public void setCityOpinion(DepartmentOpinionEmbeddable cityOpinion) {
		this.cityOpinion = cityOpinion;
	}

	public Date getApplicantAt() {
		return applicantAt;
	}

	public void setApplicantAt(Date applicantAt) {
		this.applicantAt = applicantAt;
	}

	public PlaceEntity getPlace() {
		return place;
	}

	public void setPlace(PlaceEntity place) {
		this.place = place;
	}

	public String getThemeDirection() {
		return themeDirection;
	}

	public void setThemeDirection(String themeDirection) {
		this.themeDirection = themeDirection;
	}

	public double getTotalInvestment() {
		return totalInvestment;
	}

	public void setTotalInvestment(double totalInvestment) {
		this.totalInvestment = totalInvestment;
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
