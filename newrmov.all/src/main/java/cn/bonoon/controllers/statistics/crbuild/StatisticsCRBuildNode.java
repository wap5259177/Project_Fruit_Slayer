package cn.bonoon.controllers.statistics.crbuild;

import cn.bonoon.core.QuarterReportStatus;
import cn.bonoon.entities.ruralBuild.AbstractModelAreaQuarterCRBuild;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterBatch;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterItem;
import cn.bonoon.kernel.support.models.AbstractAjaxNode;

public class StatisticsCRBuildNode extends AbstractAjaxNode {

	private static final long serialVersionUID = 5642165653300592935L;
	
	private String name;
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
	private int status = -1;
	private boolean disabled;
	
	public StatisticsCRBuildNode(ModelAreaCRBuildQuarterItem ssc){
//		this.setName(ssc.getModelArea().getName());
		/*
		 * 两个特殊的片区（统一三个字）：
		 * 		乳源瑶族自治县  -->乳源县
		 * 		南三岛滨海旅游示范区 -->南山岛
		 */
		String cName = ssc.getModelArea().getCounty();
		if("乳源瑶族自治县".equals(cName))cName = "乳源县";
		if("南三岛滨海旅游示范区".equals(cName))cName = "南三岛";
		this.setName(cName);
		this.setRemark(ssc.getRemark());
		this.setId(ssc.getId());
		this.setStatus(ssc.getStatus());
		this.setDisabled(ssc.isDisabled());
		if(disabled){
			this.name += "<font color='gray' style='font-size:12px;'>(已禁止)</font>";
		}else {
			if(status == QuarterReportStatus.NOTSTART){
				this.name += "<font color='red' style='font-size:12px;'>(未开始)</font>";
			}else if(status == QuarterReportStatus.FINISH){
				this.name += "<font color='blue' style='font-size:12px;'>(已提交)</font>";
			}
			//多添加了，对其他的状态的显示
			else if(status == QuarterReportStatus.EDIT){
				this.name += "<font color='green' style='font-size:12px;'>(正在填报)</font>";
			}
			else if(status == QuarterReportStatus.REJECT){
				this.name += "<font color='red' style='font-size:12px;'>(驳回)</font>";
			}
			else if(status == QuarterReportStatus.AUDIT){
				this.name += "<font color='green' style='font-size:12px;'>(待审核)</font>";
			}
			
		}
		sum(ssc);
	}
	
	
	public StatisticsCRBuildNode(ModelAreaCRBuildQuarterBatch ssc){
		this.setName("第" + ssc.getBatchName() + "批");
		this.setId(-ssc.getId());
		sum(ssc);
		int cc = ssc.getMaCount();
		if(cc > 0){
			this.setState(CLOSED);
			this.setSize(cc);
		}
	}

	private void sum(AbstractModelAreaQuarterCRBuild ssc) {
		this.dismantleOldHouseNum = ssc.getDismantleOldHouseNum();
		this.dismantleOldHouseArea = ssc.getDismantleOldHouseArea();
		this.cleanRubbish = ssc.getCleanRubbish();
		this.greenArea = ssc.getGreenArea();
		this.afforestationTree = ssc.getAfforestationTree();
		this.hardRoad = ssc.getHardRoad();
		this.completeToilet = ssc.getCompleteToilet();
		this.completeFacade = ssc.getCompleteFacade();
		this.rainShunt = ssc.getRainShunt();
		this.sewageTreatment = ssc.getSewageTreatment();
		this.tapWater = ssc.getTapWater();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDismantleOldHouseNum() {
		return dismantleOldHouseNum;
	}

	public void setDismantleOldHouseNum(int dismantleOldHouseNum) {
		this.dismantleOldHouseNum = dismantleOldHouseNum;
	}

	public double getDismantleOldHouseArea() {
		return dismantleOldHouseArea;
	}

	public void setDismantleOldHouseArea(double dismantleOldHouseArea) {
		this.dismantleOldHouseArea = dismantleOldHouseArea;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
//	public StatisticsCRBuildNode(ModelAreaCRBuildQuarterCRBuildBatch ssc){
//		this.setName("第" + ssc.getBatchName() + "批");
//		this.setId(-ssc.getId());
//		sum(ssc);
//		int cc = ssc.getMaCount();
//		if(cc > 0){
//			this.setState(CLOSED);
//			this.setSize(cc);
//		}
//	}
	
	
	

}
