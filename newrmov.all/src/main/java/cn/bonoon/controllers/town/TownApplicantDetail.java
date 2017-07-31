package cn.bonoon.controllers.town;

import java.util.Date;

import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail;

@Transform(write = WriteModel.NONE)
@FormDetail(3)
@InsertCell(value = "applicant/town-editor.vm", type = EmbedType.PARSE)
@WithDialog(initializer = TownApplicantDetailInitializer.class)
public class TownApplicantDetail extends BaseTownApplicantDetail{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5171657706895987058L;
	
	
	//不生成界面
	@PropertyDetail(ignore = true)
	private String rejectContent;
	
	@PropertyDetail(ignore = true)
	private Date rejectAt;
	
	@PropertyDetail(3)
	private String contactName;
	
	@PropertyDetail(4)
	private String contactPhone;
	
	@PropertyDetail(5)
	private String applicantAt;
	
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
	public Date getRejectAt() {
		return rejectAt;
	}
	public void setRejectAt(Date rejectAt) {
		this.rejectAt = rejectAt;
	}
	public String getRejectContent() {
		return rejectContent;
	}
	public void setRejectContent(String rejectContent) {
		this.rejectContent = rejectContent;
	}
}
