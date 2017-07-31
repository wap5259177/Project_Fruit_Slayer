package cn.bonoon.controllers.felicity.report;

import java.util.Calendar;
import java.util.Date;

import cn.bonoon.controllers.felicity.countyreport.FelicityCountyReportDefine;
import cn.bonoon.kernel.annotations.Transform;
import cn.bonoon.kernel.support.models.ObjectEditor;
import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.components.AsMonthly;
import cn.bonoon.kernel.web.annotations.components.AsSelector;
import cn.bonoon.kernel.web.annotations.form.FormInsert;
import cn.bonoon.kernel.web.annotations.form.PropertyInsert;

@Transform
@FormInsert
@WithDialog(width = 350, initFormClass = true)
public class FelicityCountyReportInserter extends ObjectEditor implements FelicityCountyReportDefine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5107382376704760408L;

	public FelicityCountyReportInserter(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		annual = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
	}
	@AsSelector
	@AsMonthly("month")
	@PropertyInsert(name = "月度", value = 0)
	private int annual;
	private int month;

	@PropertyInsert(1)
	private String informant;//填报人
	@PropertyInsert(2)
	private String contact;//联系电话
	@PropertyInsert(3)
	private Date reportingAt;
	
	public int getAnnual() {
		return annual;
	}
	public void setAnnual(int annual) {
		this.annual = annual;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
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
	public Date getReportingAt() {
		return reportingAt;
	}
	public void setReportingAt(Date reportingAt) {
		this.reportingAt = reportingAt;
	}
	
}
