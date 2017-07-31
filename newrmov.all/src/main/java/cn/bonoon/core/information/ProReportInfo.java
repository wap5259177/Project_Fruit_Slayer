package cn.bonoon.core.information;

import java.text.SimpleDateFormat;

import cn.bonoon.entities.informatioin.CityCapitalInvestmentInformationEntity;
import cn.bonoon.entities.informatioin.CityNaturalVillageInformationEntity;
import cn.bonoon.entities.informatioin.ProvinceCapitalInvestmentInformationEntity;
import cn.bonoon.entities.informatioin.ProvinceNaturalVillageInformationEntity;

public class ProReportInfo {
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private Long id;//市的id
	private Long reportId;//省年报实体的id
	
	private String name;//report 的名字
	
//	private int annual;//年度
	
//	private String startTime;//统计起始时间
	private String deadline;//统计截止时间
	
	private String startAt;//系统填报起始时间
	private String endAt;//系统填报截止时间
	
	private String status;

	public ProReportInfo(){}
	public ProReportInfo(ProvinceCapitalInvestmentInformationEntity r) {
		this.id = null;
		this.reportId = r.getId();
		this.name = r.getName();
		
		this.deadline = sdf.format(r.getDeadline());
		
		this.startAt = sdf.format(r.getStartAt());
		this.endAt = sdf.format(r.getEndAt());
		
		this.status = "<font color='red'>未开始</font>";
	}

	public ProReportInfo(CityCapitalInvestmentInformationEntity fvcr) {
		this.id = fvcr.getId();
		this.reportId = fvcr.getPciInformation().getId();
		this.name = fvcr.getPciInformation().getName();
		
		this.deadline = sdf.format(fvcr.getPciInformation().getDeadline());
		
		this.startAt = sdf.format(fvcr.getPciInformation().getStartAt());
		this.endAt = sdf.format(fvcr.getPciInformation().getEndAt());
		
		switch (fvcr.getStatus()) {
		case 0:
			this.status = "<font color='red'>未开始</font>";
			break;
		case 1:
			this.status = "完成";
			break;
		case 2:
			this.status = "<font color='green'>正在填报</font>";
			break;
		case 3:
			this.status = "<font color='red'>驳回</font>";
			break;
		case 4:
			this.status = "待审核";
			break;
		default:
			this.status = "未开始";
		}
	}
//---------------------------------------------------------------------------------------------------
	public ProReportInfo(ProvinceNaturalVillageInformationEntity r) {
		this.id = null;
		this.reportId = r.getId();
		this.name = r.getName();
		
		this.deadline = sdf.format(r.getDeadline());
		
		this.startAt = sdf.format(r.getStartAt());
		this.endAt = sdf.format(r.getEndAt());
		
		this.status = "<font color='red'>未开始</font>";
	}
	public ProReportInfo(CityNaturalVillageInformationEntity fvcr) {
		this.id = fvcr.getId();
		this.reportId = fvcr.getPnvInformation().getId();
		this.name = fvcr.getPnvInformation().getName();
		
		this.deadline = sdf.format(fvcr.getPnvInformation().getDeadline());
		
		this.startAt = sdf.format(fvcr.getPnvInformation().getStartAt());
		this.endAt = sdf.format(fvcr.getPnvInformation().getEndAt());
		
		switch (fvcr.getStatus()) {
		case 0:
			this.status = "<font color='red'>未开始</font>";
			break;
		case 1:
			this.status = "完成";
			break;
		case 2:
			this.status = "<font color='green'>正在填报</font>";
			break;
		case 3:
			this.status = "<font color='red'>驳回</font>";
			break;
		case 4:
			this.status = "待审核";
			break;
		default:
			this.status = "未开始";
		}
	}
//-------------------------------------------------------------------------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}



	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getStartAt() {
		return startAt;
	}

	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}

	public String getEndAt() {
		return endAt;
	}

	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
