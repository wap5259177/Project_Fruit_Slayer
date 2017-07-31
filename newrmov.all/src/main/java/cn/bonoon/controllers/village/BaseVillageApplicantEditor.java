package cn.bonoon.controllers.village;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import cn.bonoon.core.ImportPicture;
import cn.bonoon.kernel.annotations.Ignore;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.web.annotations.components.AsDateBox;
import cn.bonoon.kernel.web.annotations.form.HasFile;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;

@HasFile
public class BaseVillageApplicantEditor extends BaseVillageApplicantObject implements ImportPicture{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7455389382474951994L;

	@TransformField
	@PropertyEditor(value = 3)
	private String contactName;
	@TransformField
	@PropertyEditor(value = 4)
	private String contactPhone;
	
	@TransformField
	@PropertyEditor(value = 5)
	@AsDateBox
	private Date applicantAt;

	@TransformField("place.id")
	private Long place;
	
	@Ignore
	private MultipartFile[] pictures;
	
	@TransformField("countyOpinion.opinion")
	private String opinion1;
	
	@TransformField("countyOpinion.opinionAt")
	private Date opinionAt1;

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

	public Date getApplicantAt() {
		return applicantAt;
	}

	public void setApplicantAt(Date applicantAt) {
		this.applicantAt = applicantAt;
	}

	public Long getPlace() {
		return place;
	}

	public void setPlace(Long place) {
		this.place = place;
	}

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

	public MultipartFile[] getPictures() {
		return pictures;
	}

	public void setPictures(MultipartFile[] pictures) {
		this.pictures = pictures;
	}
}
