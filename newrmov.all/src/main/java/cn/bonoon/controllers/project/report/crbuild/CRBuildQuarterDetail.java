package cn.bonoon.controllers.project.report.crbuild;

import java.util.Date;

import cn.bonoon.kernel.support.models.ObjectEditor;

//@FormDetail(autoIgnore = true, width = 680)
//@InsertCell(value = "applicant/administrationrural-view.vm", type = EmbedType.PARSE)
//@WithDialog(initializer = AdministrationRuralDetailInitializer.class)
public class CRBuildQuarterDetail  extends ObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9160712841562605762L;
//	
//	@TransformField(value = "modelArea.id", writable = WriteModel.NONE)
//	private Long modelareaId;
	
	//拆除危、旧、弃房间数
	private int dismantleOldHouseNum;
	
	//拆除危、旧、弃房面积（平方米）
	private double dismantleOldHouseArea;
	
	//清理垃圾数（吨）
	private double cleanRubbish;
	
	//复绿面积（平方米）
	private double greenArea;
	
	
	//种植绿化树（颗）
	private int afforestationTree;
	
	//完成村、巷道路硬底化数（公里）
	private double hardRoad;
	
	//完成卫生改厕数（个）
	private int completeToilet;
	
	//完成村居外立面改造数（户）
	private int completeFacade;
	
	//铺设雨污分流管网（公里）
	private double rainShunt;
	
	//建设污水处理设施（座）
	private int sewageTreatment;
	
	//铺设自来水管（公里）
	private double tapWater;
	
	

	private String remark;


	private String reportName;
	
	private Date reportDate;

	public int getDismantleOldHouseNum() {
		return dismantleOldHouseNum;
	}

	public void setDismantleOldHouseNum(int dismantleOldHouseNum) {
		this.dismantleOldHouseNum = dismantleOldHouseNum;
	}


	public double getCleanRubbish() {
		return cleanRubbish;
	}

	public void setCleanRubbish(double cleanRubbish) {
		this.cleanRubbish = cleanRubbish;
	}

	public double getGreenArea() {
		return greenArea;
	}

	public void setGreenArea(double greenArea) {
		this.greenArea = greenArea;
	}

	public int getAfforestationTree() {
		return afforestationTree;
	}

	public void setAfforestationTree(int afforestationTree) {
		this.afforestationTree = afforestationTree;
	}

	public double getHardRoad() {
		return hardRoad;
	}

	public void setHardRoad(double hardRoad) {
		this.hardRoad = hardRoad;
	}

	public int getCompleteToilet() {
		return completeToilet;
	}

	public void setCompleteToilet(int completeToilet) {
		this.completeToilet = completeToilet;
	}

	public int getCompleteFacade() {
		return completeFacade;
	}

	public void setCompleteFacade(int completeFacade) {
		this.completeFacade = completeFacade;
	}

	public double getRainShunt() {
		return rainShunt;
	}

	public void setRainShunt(double rainShunt) {
		this.rainShunt = rainShunt;
	}

	public int getSewageTreatment() {
		return sewageTreatment;
	}

	public void setSewageTreatment(int sewageTreatment) {
		this.sewageTreatment = sewageTreatment;
	}

	public double getTapWater() {
		return tapWater;
	}

	public void setTapWater(double tapWater) {
		this.tapWater = tapWater;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public double getDismantleOldHouseArea() {
		return dismantleOldHouseArea;
	}

	public void setDismantleOldHouseArea(double dismantleOldHouseArea) {
		this.dismantleOldHouseArea = dismantleOldHouseArea;
	}
	
	
}
