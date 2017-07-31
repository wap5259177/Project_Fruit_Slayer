package cn.bonoon.controllers.felicity.countyreport;

import cn.bonoon.controllers.felicity.report.FelicityVillageReportItem;
import cn.bonoon.entities.FelicityVillageReportEntity;
import cn.bonoon.kernel.web.EmbedType;
import cn.bonoon.kernel.web.annotations.AutoDataLoader;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.components.AsTabs;
import cn.bonoon.kernel.web.annotations.components.TabHome;
import cn.bonoon.kernel.web.annotations.components.TabItem;
import cn.bonoon.kernel.web.annotations.components.TabItem.TabItemType;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.InsertCell;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail;

@FormDetail(value = 3, width = 100, headWidth = 140, autoIgnore = true)
@AsTabs(@TabItem(
		name = "村报表", 
		clazz = FelicityVillageReportItem.class, 
		type = TabItemType.GRID,
		value = "x.countyReport.id=?",
		loader = @AutoDataLoader(FelicityVillageReportEntity.class)
))
@TabHome(name = "示范片区报表")
@WithDialog(
		initializer = FelicityCountyReportDetailInitializer.class, 
		height = 550
	//	closeButton = false
)
@InsertCell(value = "felicity/report-county-editor.vm", type = EmbedType.PARSE)
public class FelicityCountyReportDetail extends FelicityCountyReportInfo implements FelicityCountyReportDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7176407838515831366L;

	
	@PropertyDetail(3)
	private String informant;//填报人
	@PropertyDetail(4)
	private String contact;//联系电话
	@PropertyDetail(5)
	private String reportingAt;

	private String auditName;
	private String auditAt;
	private String auditContent;
	
	public String getInformant() {
		return informant;
	}
	public void setInformant(String informant) {
		this.informant = informant;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getReportingAt() {
		return reportingAt;
	}
	public void setReportingAt(String reportingAt) {
		this.reportingAt = reportingAt;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getAuditAt() {
		return auditAt;
	}
	public void setAuditAt(String auditAt) {
		this.auditAt = auditAt;
	}
	public String getAuditContent() {
		return auditContent;
	}
	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}
	
}
