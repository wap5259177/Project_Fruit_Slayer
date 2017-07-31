package cn.bonoon.controllers.workplan;

import cn.bonoon.kernel.web.annotations.WithDialog;
import cn.bonoon.kernel.web.annotations.form.FormDetail;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail;
import cn.bonoon.kernel.web.annotations.form.PropertyDetail.DetailType;

@FormDetail(2)
@WithDialog(height = 500)
public class WorkPlanDetail implements WorkPlanDefine{
	
	@PropertyDetail(value = 0, colspan = 1)
	private String name;

	
	@PropertyDetail(1)
	private int  annual;
	
	@PropertyDetail(2)
	private String monthly;

	@PropertyDetail(3)
	private String unitName;

	@PropertyDetail(4)
	private String createAt;
	
	@PropertyDetail(value = 10,colspan = 1, detailType = DetailType.DOWNLOAD)
	private String enclosure;//附件上传
	
	
	@PropertyDetail(value = 12, colspan = 1, showHead = false)
	private String content;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAnnual() {
		return annual;
	}

	public void setAnnual(int annual) {
		this.annual = annual;
	}

	public String getMonthly() {
		return monthly;
	}

	public void setMonthly(String monthly) {
		this.monthly = monthly;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}
	
	
	
}
