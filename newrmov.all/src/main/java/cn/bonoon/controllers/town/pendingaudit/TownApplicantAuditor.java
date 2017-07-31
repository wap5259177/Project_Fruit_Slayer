package cn.bonoon.controllers.town.pendingaudit;

import cn.bonoon.controllers.town.BaseTownApplicantDetail;
import cn.bonoon.kernel.annotations.Ignore;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.util.BoolType;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormUpdate;
import cn.bonoon.kernel.web.annotations.form.InsertCell;
import cn.bonoon.kernel.web.annotations.form.PropertyUpdate;

@Transform
@FormUpdate(3)
@InsertCell(value = "applicant/town-editor.vm", type = EmbedType.PARSE)
@WithDialog(initializer = TownApplicantAuditorInitializer.class)
@DialogDefaultButton(
		buttonName = "通过审核",
		functionBody = "if(!confirm('是否确定通过审核？')){return false;}",
		otherButtonName = "驳回",
		otherParameterName = "applicant-reject",
		otherParameterValue = "true",
		otherFunctionBody = "if(!confirm('是否确定要驳回？')){return false;}"
	)
public class TownApplicantAuditor extends BaseTownApplicantDetail{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6117483645356824809L;
	@TransformField(writable = WriteModel.NONE)
	@PropertyUpdate(value = 0, readonly = BoolType.TRUE)
	private String contactName;
	@TransformField(writable = WriteModel.NONE)
	@PropertyUpdate(value = 1, readonly = BoolType.TRUE)
	private String contactPhone;

	@TransformField(writable = WriteModel.NONE)
	@PropertyUpdate(value = 2, readonly = BoolType.TRUE)
	private String applicantAt;
	
	@Ignore
	private String rejectContent;

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

	public String getRejectContent() {
		return rejectContent;
	}

	public void setRejectContent(String rejectContent) {
		this.rejectContent = rejectContent;
	}
}
