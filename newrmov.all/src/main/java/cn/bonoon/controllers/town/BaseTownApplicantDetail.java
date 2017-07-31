package cn.bonoon.controllers.town;

import java.util.Date;

import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail;

public class BaseTownApplicantDetail extends BaseTownApplicantObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162963557391887054L;

	@PropertyDetail(ignore = true)
	private String countyName;
	
	@PropertyDetail(ignore = true)
	private String name;
	
	@PropertyDetail(ignore = true)
	@TransformField("applicantAt")
	private Date aat;

	@PropertyDetail(ignore = true)
	@TransformField("countyOpinion.opinion")
	private String opinion1;

	@PropertyDetail(ignore = true)
	@TransformField("countyOpinion.opinionAt")
	private Date opinionAt1;

	@PropertyDetail(ignore = true)
	@TransformField("cityOpinion.opinion")
	private String opinion2;

	@PropertyDetail(ignore = true)
	@TransformField("cityOpinion.opinionAt")
	private Date opinionAt2;

	@PropertyDetail(ignore = true)
	@TransformField("cityGroup.opinion")
	private String opinion3;

	@PropertyDetail(ignore = true)
	@TransformField("cityGroup.opinionAt")
	private Date opinionAt3;

	@PropertyDetail(ignore = true)
	@TransformField("provinceOpinion.opinion")
	private String opinion4;

	@PropertyDetail(ignore = true)
	@TransformField("provinceOpinion.opinionAt")
	private Date opinionAt4;

	@PropertyDetail(ignore = true)
	@TransformField("provinceGroup.opinion")
	private String opinion5;

	@PropertyDetail(ignore = true)
	@TransformField("provinceGroup.opinionAt")
	private Date opinionAt5;

	public String getOpinion1() {
		return opinion1;
	}

	public void setOpinion1(String opinion1) {
		this.opinion1 = opinion1;
	}

	public Date getOpinionAt1() {
		return opinionAt1;
	}

	public void setOpinionAt1(Date opinionAt1) {
		this.opinionAt1 = opinionAt1;
	}

	public String getOpinion2() {
		return opinion2;
	}

	public void setOpinion2(String opinion2) {
		this.opinion2 = opinion2;
	}

	public Date getOpinionAt2() {
		return opinionAt2;
	}

	public void setOpinionAt2(Date opinionAt2) {
		this.opinionAt2 = opinionAt2;
	}

	public String getOpinion3() {
		return opinion3;
	}

	public void setOpinion3(String opinion3) {
		this.opinion3 = opinion3;
	}

	public Date getOpinionAt3() {
		return opinionAt3;
	}

	public void setOpinionAt3(Date opinionAt3) {
		this.opinionAt3 = opinionAt3;
	}

	public String getOpinion4() {
		return opinion4;
	}

	public void setOpinion4(String opinion4) {
		this.opinion4 = opinion4;
	}

	public Date getOpinionAt4() {
		return opinionAt4;
	}

	public void setOpinionAt4(Date opinionAt4) {
		this.opinionAt4 = opinionAt4;
	}

	public String getOpinion5() {
		return opinion5;
	}

	public void setOpinion5(String opinion5) {
		this.opinion5 = opinion5;
	}

	public Date getOpinionAt5() {
		return opinionAt5;
	}

	public void setOpinionAt5(Date opinionAt5) {
		this.opinionAt5 = opinionAt5;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getAat() {
		return aat;
	}

	public void setAat(Date aat) {
		this.aat = aat;
	}
	
}
