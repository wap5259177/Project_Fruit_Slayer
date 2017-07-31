package cn.bonoon.controllers.file.require;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import cn.bonoon.core.IRequireReportEditor;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.annotations.TransformField;
import cn.bonoon.kernel.annotations.WriteModel;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.components.AsDateBox;
import cn.bonoon.kernel.web.annotations.form.FormEditor;
import cn.bonoon.kernel.web.annotations.form.HasFile;
import cn.bonoon.kernel.web.annotations.form.InsertCell;
import cn.bonoon.kernel.web.annotations.form.PropertyEditor;

@HasFile
@Transform
@FormEditor(value = 3, width = 160, headWidth = 110)
@InsertCell(value = "report/require-report.vm", type = EmbedType.PARSE)
@WithDialog(initializer = RequireReportEditorInitializer.class,
    beforeSubmit = "KindEditor.sync('#editor-content');",
	onloadedjs = "KindEditor.create('#editor-content', {resizeMode : 1,allowPreviewEmoticons : false,allowUpload : false,items : ['source', '|', 'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline','removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist','insertunorderedlist', '|', 'link']});")
@DialogDefaultButton(buttonName = "保存")
public class RequireReportInsert extends ObjectEditor implements RequireReportDefine, IRequireReportEditor {

	private static final long serialVersionUID = -8798096794938953534L;

	@TransformField
	@PropertyEditor(value = 0, colspan = 2)
	private String name;

	@PropertyEditor(1)
	@AsDateBox
	private Date draftAt;
	@TransformField
	@PropertyEditor(2)
	@AsDateBox
	private Date startReportedAt;

	@TransformField
	@PropertyEditor(3)
	@AsDateBox
	private Date endReportedAt;
	
	private String content;
	@TransformField(writable = WriteModel.NONE)
	private String annex;
	@TransformField(writable = WriteModel.NONE, readable = false)
	private MultipartFile reportAnnex;
	@TransformField(writable = WriteModel.NONE)
	private String annexName;
	@TransformField(writable = WriteModel.NONE, readable = false)
	private Boolean deleteOldAnnex;
	private String offices;
	private String remark;
	private String other;

	
	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
	}

	public MultipartFile getReportAnnex() {
		return reportAnnex;
	}

	public void setReportAnnex(MultipartFile reportAnnex) {
		this.reportAnnex = reportAnnex;
	}

	public String getAnnexName() {
		return annexName;
	}

	public void setAnnexName(String annexName) {
		this.annexName = annexName;
	}

	public Boolean getDeleteOldAnnex() {
		return deleteOldAnnex;
	}

	public void setDeleteOldAnnex(Boolean deleteOldAnnex) {
		this.deleteOldAnnex = deleteOldAnnex;
	}

	public String getOffices() {
		return offices;
	}

	public void setOffices(String offices) {
		this.offices = offices;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartReportedAt() {
		return startReportedAt;
	}

	public void setStartReportedAt(Date startReportedAt) {
		this.startReportedAt = startReportedAt;
	}

	public Date getEndReportedAt() {
		return endReportedAt;
	}

	public void setEndReportedAt(Date endReportedAt) {
		this.endReportedAt = endReportedAt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDraftAt() {
		return draftAt;
	}

	public void setDraftAt(Date draftAt) {
		this.draftAt = draftAt;
	}
}
