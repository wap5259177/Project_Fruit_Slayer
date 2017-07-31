package cn.bonoon.entities.ruralBuild;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import cn.bonoon.kernel.support.entities.AbstractPersistable;

@MappedSuperclass
public abstract class AbstractModelAreaQuarterCRBuild extends AbstractPersistable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2055092708829733620L;

	//拆除危、旧、弃房间数
	@Column(name = "C_DISMANTLEOLDHOUSENUM")
	private int dismantleOldHouseNum;
	
	//拆除危、旧、弃房面积（平方米）
	@Column(name = "C_DISMANTLEOLDHOUSEAREA")
	private double dismantleOldHouseArea;
	
	//清理垃圾数（吨）
	@Column(name = "C_CLEANRUBBISH")
	private double cleanRubbish;
	
	//复绿面积（平方米）
	@Column(name = "C_GREENAREA")
	private double greenArea;
	
	
	//种植绿化树（颗）
	@Column(name = "C_AFFORESTATIONTREE")
	private int afforestationTree;
	
	//完成村、巷道路硬底化数（公里）
	@Column(name = "C_HARDROAD")
	private double hardRoad;
	
	//完成卫生改厕数（个）
	@Column(name = "C_COMPLETETOILET")
	private int completeToilet;
	
	//完成村居外立面改造数（户）
	@Column(name = "C_COMPLETEFACADE")
	private int completeFacade;
	
	//铺设雨污分流管网（公里）
	@Column(name = "C_RAINSHUNT")
	private double rainShunt;
	
	//建设污水处理设施（座）
	@Column(name = "C_SEWAGETREATMENT")
	private int sewageTreatment;
	
	//铺设自来水管（公里）
	@Column(name = "C_TAPWATER")
	private double tapWater;
	
	

	@Column(name = "C_REMARK")
	private String remark;



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



	public double getDismantleOldHouseArea() {
		return dismantleOldHouseArea;
	}



	public void setDismantleOldHouseArea(double dismantleOldHouseArea) {
		this.dismantleOldHouseArea = dismantleOldHouseArea;
	}
	
	
	
	
}
