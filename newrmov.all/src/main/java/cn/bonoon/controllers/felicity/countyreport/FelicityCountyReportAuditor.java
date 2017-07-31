package cn.bonoon.controllers.felicity.countyreport;

import java.util.Date;

import cn.bonoon.controllers.felicity.report.FelicityVillageReportItem;
import cn.bonoon.entities.FelicityVillageReportEntity;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.AutoDataLoader;
import cn.bonoon.kernel.web.annotations.DialogDefaultButton;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.components.AsTabs;
import cn.bonoon.kernel.web.annotations.components.TabHome;
import cn.bonoon.kernel.web.annotations.components.TabItem;
import cn.bonoon.kernel.web.annotations.components.TabItem.TabItemType;
import cn.bonoon.kernel.web.annotations.form.FormUpdate;
import cn.bonoon.kernel.web.annotations.form.InsertCell;

@Transform
@FormUpdate(3)
@InsertCell(value = "felicity/report-county-editor.vm", type = EmbedType.PARSE)
@WithDialog(initializer = FelicityCountyReportAuditorInitializer.class, height = 550)
@DialogDefaultButton(
		buttonName = "通过审核",
		functionBody = "if(!confirm('是否确定通过审核？')){return false;}",
		otherButtonName = "驳回",
		otherParameterName = "applicant-reject",
		otherParameterValue = "true",
		otherFunctionBody = "if(!confirm('是否确定要驳回？')){return false;}"
	)
@AsTabs(@TabItem(
		name = "村报表", 
		clazz = FelicityVillageReportItem.class, 
		type = TabItemType.GRID,
		value = "x.countyReport.id=?",
		loader = @AutoDataLoader(FelicityVillageReportEntity.class)
))
@TabHome(name = "示范片区报表")
public class FelicityCountyReportAuditor extends FelicityCountyReportInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7387098490785323656L;

	private String auditName;
	private Date auditAt;
	private String auditContent;
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public Date getAuditAt() {
		return auditAt;
	}
	public void setAuditAt(Date auditAt) {
		this.auditAt = auditAt;
	}
	public String getAuditContent() {
		return auditContent;
	}
	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}
	
}
