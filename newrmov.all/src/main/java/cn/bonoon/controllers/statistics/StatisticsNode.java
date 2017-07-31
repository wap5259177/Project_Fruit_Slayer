package cn.bonoon.controllers.statistics;

import cn.bonoon.core.QuarterReportStatus;
import cn.bonoon.entities.AbstractModelAreaQuarter;
import cn.bonoon.entities.ModelAreaQuarterBatch;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.kernel.support.models.AbstractAjaxNode;

public class StatisticsNode extends AbstractAjaxNode {

	private static final long serialVersionUID = 5642165653300592935L;
	
	private int arCount;
	private int nrCount;
	private int householdCount;
	private int populationCount;
	private int arFinishPlan;
	private int needFinish1;
	private int needFinish2;
	private int needFinish3;
	private int needFinish4;
	private int needFinish5;
	private int needFinish6;
	private int needFinish7;
	private int needFinish8;
	private int needFinish9;
	private double totalFunds;
	private double stateFunds;
	private double specialFunds;
	private double provinceFunds;
	private double cityFunds;
	private double countyFunds;
	private double socialFunds;
	private double massFunds;
	private double otherFunds;
	private String name;
	private int status = -1;
	private boolean disabled;
	
	private int startProject;
	private int finishProject;
	
	public StatisticsNode(ModelAreaQuarterItem ssc){
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
		this.setId(ssc.getId());
		this.status = ssc.getStatus();
		this.disabled = ssc.isDisabled();
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
	
	public StatisticsNode(ModelAreaQuarterBatch ssc){
		this.setName("第" + ssc.getBatchName() + "批");
		this.setId(-ssc.getId());
		sum(ssc);
		int cc = ssc.getMaCount();
		if(cc > 0){
			this.setState(CLOSED);
			this.setSize(cc);
		}
	}
	
	public void sum(AbstractModelAreaQuarter o) {
		this.arCount = o.getArCount();
		this.nrCount = o.getNrCount();
		this.householdCount = o.getHouseholdCount();
		this.populationCount = o.getPopulationCount();
		this.arFinishPlan = o.getArFinishPlan();
		this.needFinish1 = o.getNeedFinish().getNeedFinish1();
		this.needFinish2 = o.getNeedFinish().getNeedFinish2();
		this.needFinish3 = o.getNeedFinish().getNeedFinish3();
		this.needFinish4 = o.getNeedFinish().getNeedFinish4();
		this.needFinish5 = o.getNeedFinish().getNeedFinish5();
		this.needFinish6 = o.getNeedFinish().getNeedFinish6();
		this.needFinish7 = o.getNeedFinish().getNeedFinish7();
		this.needFinish8 = o.getNeedFinish().getNeedFinish8();
		this.needFinish9 = o.getNeedFinish().getNeedFinish9();
		this.totalFunds = o.getInvestment().getTotalFunds();
		this.stateFunds = o.getInvestment().getStateFunds();
		this.specialFunds = o.getInvestment().getSpecialFunds();
		this.provinceFunds = o.getInvestment().getProvinceFunds();
		this.cityFunds = o.getInvestment().getCityFunds();
		this.countyFunds = o.getInvestment().getCountyFunds();
		this.socialFunds = o.getInvestment().getSocialFunds();
		this.massFunds = o.getInvestment().getMassFunds();
		this.otherFunds = o.getInvestment().getOtherFunds();
		if(null!=o.getStartProject()){
			this.setStartProject(o.getStartProject());
		}
		else{
			this.setStartProject(0);
		}
		if(null!=o.getFinishProject()){
			this.setFinishProject(o.getFinishProject());
		}else{
			this.setFinishProject(0);
		}
		
	}
	
	
	public int getArCount() {
		return arCount;
	}
	public void setArCount(int arCount) {
		this.arCount = arCount;
	}
	public int getHouseholdCount() {
		return householdCount;
	}
	public void setHouseholdCount(int householdCount) {
		this.householdCount = householdCount;
	}
	public int getNrCount() {
		return nrCount;
	}
	public void setNrCount(int nrCount) {
		this.nrCount = nrCount;
	}
	public int getPopulationCount() {
		return populationCount;
	}
	public void setPopulationCount(int populationCount) {
		this.populationCount = populationCount;
	}
	public int getArFinishPlan() {
		return arFinishPlan;
	}
	public void setArFinishPlan(int arFinishPlan) {
		this.arFinishPlan = arFinishPlan;
	}
	public int getNeedFinish1() {
		return needFinish1;
	}
	public void setNeedFinish1(int needFinish1) {
		this.needFinish1 = needFinish1;
	}
	public int getNeedFinish2() {
		return needFinish2;
	}
	public void setNeedFinish2(int needFinish2) {
		this.needFinish2 = needFinish2;
	}
	public int getNeedFinish3() {
		return needFinish3;
	}
	public void setNeedFinish3(int needFinish3) {
		this.needFinish3 = needFinish3;
	}
	public int getNeedFinish4() {
		return needFinish4;
	}
	public void setNeedFinish4(int needFinish4) {
		this.needFinish4 = needFinish4;
	}
	public int getNeedFinish5() {
		return needFinish5;
	}
	public void setNeedFinish5(int needFinish5) {
		this.needFinish5 = needFinish5;
	}
	public int getNeedFinish6() {
		return needFinish6;
	}
	public void setNeedFinish6(int needFinish6) {
		this.needFinish6 = needFinish6;
	}
	public int getNeedFinish7() {
		return needFinish7;
	}
	public void setNeedFinish7(int needFinish7) {
		this.needFinish7 = needFinish7;
	}
	public int getNeedFinish8() {
		return needFinish8;
	}
	public void setNeedFinish8(int needFinish8) {
		this.needFinish8 = needFinish8;
	}
	public int getNeedFinish9() {
		return needFinish9;
	}
	public void setNeedFinish9(int needFinish9) {
		this.needFinish9 = needFinish9;
	}
	public double getTotalFunds() {
		return totalFunds;
	}
	public void setTotalFunds(double totalFunds) {
		this.totalFunds = totalFunds;
	}
	public double getStateFunds() {
		return stateFunds;
	}
	public void setStateFunds(double stateFunds) {
		this.stateFunds = stateFunds;
	}
	public double getSpecialFunds() {
		return specialFunds;
	}
	public void setSpecialFunds(double specialFunds) {
		this.specialFunds = specialFunds;
	}
	public double getProvinceFunds() {
		return provinceFunds;
	}
	public void setProvinceFunds(double provinceFunds) {
		this.provinceFunds = provinceFunds;
	}
	public double getCityFunds() {
		return cityFunds;
	}
	public void setCityFunds(double cityFunds) {
		this.cityFunds = cityFunds;
	}
	public double getCountyFunds() {
		return countyFunds;
	}
	public void setCountyFunds(double countyFunds) {
		this.countyFunds = countyFunds;
	}
	public double getSocialFunds() {
		return socialFunds;
	}
	public void setSocialFunds(double socialFunds) {
		this.socialFunds = socialFunds;
	}
	public double getMassFunds() {
		return massFunds;
	}
	public void setMassFunds(double massFunds) {
		this.massFunds = massFunds;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

	public double getOtherFunds() {
		return otherFunds;
	}

	public void setOtherFunds(double otherFunds) {
		this.otherFunds = otherFunds;
	}

	public int getStartProject() {
		return startProject;
	}

	public void setStartProject(int startProject) {
		this.startProject = startProject;
	}

	public int getFinishProject() {
		return finishProject;
	}

	public void setFinishProject(int finishProject) {
		this.finishProject = finishProject;
	}
	

}
