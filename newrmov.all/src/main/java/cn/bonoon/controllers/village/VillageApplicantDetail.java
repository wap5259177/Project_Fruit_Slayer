package cn.bonoon.controllers.village;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail;

@Transform(write = WriteModel.NONE)
@FormDetail(3)
@InsertCell(value = "applicant/village-editor.vm", type = EmbedType.PARSE)
@WithDialog(initializer = VillageApplicantDetailInitializer.class)
public class VillageApplicantDetail extends BaseVillageApplicantDetail{

	/**
	 * 
	 */
	private static final long serialVersionUID = 403039660414277677L;
//	@PropertyDetail(ignore = true)
//	private String countyName;
//	@PropertyDetail(ignore = true)
//	private String townName;
//	@PropertyDetail(ignore = true)
//	private String name;
	
	@PropertyDetail(3)
	private String contactName;
	@PropertyDetail(4)
	private String contactPhone;
	@PropertyDetail(5)
	private String applicantAt;
//	
//	public String getCountyName() {
//		return countyName;
//	}
//	public void setCountyName(String countyName) {
//		this.countyName = countyName;
//	}
//	public String getTownName() {
//		return townName;
//	}
//	public void setTownName(String townName) {
//		this.townName = townName;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
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
	public String getApplicantAt() {
		return applicantAt;
	}
	public void setApplicantAt(String applicantAt) {
		this.applicantAt = applicantAt;
	}
}
