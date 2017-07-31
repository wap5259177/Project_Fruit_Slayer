package cn.bonoon.core;

import cn.bonoon.entities.InvestmentInfo;
import cn.bonoon.entities.ModelAreaEntity;

/**
 * 主体村、非主体村项目资金导出excel的界面实体
 * @author Administrator
 *
 */
public class ModelAreaStatisInfo {
	private String name; //市县名称
	private String batch;//批次
	
		
		
	private int stp;
	private int fp;
	private int endp;
	
	private int coreStp;//主体村启动项目数
	private int coreFp;// 主体村其中已菌功能项目数
	private int uncoreStp;//非主体村启动项目数
	private int uncoreFp;// 非主体村其中已菌功能项目数
	
	private InvestmentInfo invest = new InvestmentInfo();
	private InvestmentInfo coreInvest = new InvestmentInfo();  //界面实体用
	private InvestmentInfo uncoreInvest = new InvestmentInfo();//界面实体用
	
	
	
	private int mainBuild;// 主体村个数
	private int around;// 非主体村个数

	
	private int sumMAdmin;// 主体建设村的行政村个数
	private int sumMRural;// 主体建设村的自然村个数
	private int sumArAdmin;// 非主体建设村的行政村个数
	private int sumArRural;// 非主体建设村的自然村个数
	
	
	
	private int mainSumHouse;// 主体建设村总户数
	private int mainSumP;	//  主体建设村总人口数
	private int aroundSumHouse;// 非主体建设村总户数
	private int aroundSumP;	   // 非主体建设村总人口数
	
	
	

	/*
	 * 主体村（导出excel用）
	 */
	private double totalFunds;//总投入
	private double stateFunds;//中央财政资金
	private double specialFunds;// 其中省级新农村示范片专项;省级新农村连片示范工程建设补助资金 
	private double provinceFunds;//其他省级资金
	private double cityFunds;// 市级财政资金
	private double countyFunds;//县级财政资金
	private double socialFunds;//社会资金；企业投入，集资等
	private double massFunds;//群众自筹资金
	private double otherFunds;//其它方面的资金
	
	/*
	 * 非主体村（导出excel用）
	 */
	private double uctotalFunds;//总投入
	private double ucstateFunds;//中央财政资金
	private double ucspecialFunds;// 其中省级新农村示范片专项;省级新农村连片示范工程建设补助资金 
	private double ucprovinceFunds;//其他省级资金
	private double uccityFunds;// 市级财政资金
	private double uccountyFunds;//县级财政资金
	private double ucsocialFunds;//社会资金；企业投入，集资等
	private double ucmassFunds;//群众自筹资金
	private double ucotherFunds;//其它方面的资金
	
	
	
	
	public ModelAreaStatisInfo(){}
	public ModelAreaStatisInfo(ModelAreaEntity ma) {
		batch = ma.getBatch();
		name = ma.getCounty();
		if("乳源瑶族自治县".equals(ma.getCounty())){
			name = "乳源县";
		}
		if("南三岛滨海旅游示范区".equals(ma.getCounty())){
			name = "南山岛";
		}
		
		sumMAdmin = ma.getSumMAdmin();
		sumMRural = ma.getSumMRural();
		mainSumHouse = ma.getMainSumHouse();
		mainSumP = ma.getMainSumP();
		
		sumArAdmin = ma.getSumArAdmin();
		sumArRural = ma.getSumArRural();
		aroundSumHouse = ma.getAroundSumHouse();
		aroundSumP = ma.getAroundSumP();
	}
	public InvestmentInfo getUncoreInvest() {
		return uncoreInvest;
	}
	public void setUncoreInvest(InvestmentInfo uncoreInvest) {
		this.uncoreInvest = uncoreInvest;
		
		this.uctotalFunds = uncoreInvest.getTotalFunds();
		this.ucstateFunds = uncoreInvest.getStateFunds();
		this.ucspecialFunds = uncoreInvest.getSpecialFunds();
		this.ucprovinceFunds = uncoreInvest.getProvinceFunds();
		this.uccityFunds = uncoreInvest.getCityFunds();
		this.uccountyFunds = uncoreInvest.getCountyFunds();
		this.ucsocialFunds = uncoreInvest.getSocialFunds();
		this.ucmassFunds = uncoreInvest.getMassFunds();
		this.ucotherFunds = uncoreInvest.getOtherFunds();
	}
	public InvestmentInfo getCoreInvest() {
		return coreInvest;
	}
	public void setCoreInvest(InvestmentInfo coreInvest) {
		this.coreInvest = coreInvest;
		
		this.totalFunds = coreInvest.getTotalFunds();
		this.stateFunds = coreInvest.getStateFunds();
		this.specialFunds = coreInvest.getSpecialFunds();
		this.provinceFunds = coreInvest.getProvinceFunds();
		this.cityFunds = coreInvest.getCityFunds();
		this.countyFunds = coreInvest.getCountyFunds();
		this.socialFunds = coreInvest.getSocialFunds();
		this.massFunds = coreInvest.getMassFunds();
		this.otherFunds = coreInvest.getOtherFunds();
	}
	
	
	public int getCoreFp() {
		return coreFp;
	}
	public void setCoreFp(int coreFp) {
		this.coreFp = coreFp;
	}
	public int getUncoreFp() {
		return uncoreFp;
	}
	public void setUncoreFp(int uncoreFp) {
		this.uncoreFp = uncoreFp;
	}
	public int getCoreStp() {
		return coreStp;
	}
	public void setCoreStp(int coreStp) {
		this.coreStp = coreStp;
	}
	public int getUncoreStp() {
		return uncoreStp;
	}
	public void setUncoreStp(int uncoreStp) {
		this.uncoreStp = uncoreStp;
	}

	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMainBuild() {
		return mainBuild;
	}
	public void setMainBuild(int mainBuild) {
		this.mainBuild = mainBuild;
	}
	public int getAround() {
		return around;
	}
	public void setAround(int around) {
		this.around = around;
	}
	public int getSumMAdmin() {
		return sumMAdmin;
	}
	public void setSumMAdmin(int sumMAdmin) {
		this.sumMAdmin = sumMAdmin;
	}
	public int getSumMRural() {
		return sumMRural;
	}
	public void setSumMRural(int sumMRural) {
		this.sumMRural = sumMRural;
	}
	public int getSumArAdmin() {
		return sumArAdmin;
	}
	public void setSumArAdmin(int sumArAdmin) {
		this.sumArAdmin = sumArAdmin;
	}
	public int getSumArRural() {
		return sumArRural;
	}
	public void setSumArRural(int sumArRural) {
		this.sumArRural = sumArRural;
	}
	public int getMainSumHouse() {
		return mainSumHouse;
	}
	public void setMainSumHouse(int mainSumHouse) {
		this.mainSumHouse = mainSumHouse;
	}
	public int getAroundSumHouse() {
		return aroundSumHouse;
	}
	public void setAroundSumHouse(int aroundSumHouse) {
		this.aroundSumHouse = aroundSumHouse;
	}
	public int getMainSumP() {
		return mainSumP;
	}
	public void setMainSumP(int mainSumP) {
		this.mainSumP = mainSumP;
	}
	public int getAroundSumP() {
		return aroundSumP;
	}
	public void setAroundSumP(int aroundSumP) {
		this.aroundSumP = aroundSumP;
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
	public double getOtherFunds() {
		return otherFunds;
	}
	public void setOtherFunds(double otherFunds) {
		this.otherFunds = otherFunds;
	}
	public double getUctotalFunds() {
		return uctotalFunds;
	}
	public void setUctotalFunds(double uctotalFunds) {
		this.uctotalFunds = uctotalFunds;
	}
	public double getUcstateFunds() {
		return ucstateFunds;
	}
	public void setUcstateFunds(double ucstateFunds) {
		this.ucstateFunds = ucstateFunds;
	}
	public double getUcspecialFunds() {
		return ucspecialFunds;
	}
	public void setUcspecialFunds(double ucspecialFunds) {
		this.ucspecialFunds = ucspecialFunds;
	}
	public double getUcprovinceFunds() {
		return ucprovinceFunds;
	}
	public void setUcprovinceFunds(double ucprovinceFunds) {
		this.ucprovinceFunds = ucprovinceFunds;
	}
	public double getUccityFunds() {
		return uccityFunds;
	}
	public void setUccityFunds(double uccityFunds) {
		this.uccityFunds = uccityFunds;
	}
	public double getUccountyFunds() {
		return uccountyFunds;
	}
	public void setUccountyFunds(double uccountyFunds) {
		this.uccountyFunds = uccountyFunds;
	}
	public double getUcsocialFunds() {
		return ucsocialFunds;
	}
	public void setUcsocialFunds(double ucsocialFunds) {
		this.ucsocialFunds = ucsocialFunds;
	}
	public double getUcmassFunds() {
		return ucmassFunds;
	}
	public void setUcmassFunds(double ucmassFunds) {
		this.ucmassFunds = ucmassFunds;
	}
	public double getUcotherFunds() {
		return ucotherFunds;
	}
	public void setUcotherFunds(double ucotherFunds) {
		this.ucotherFunds = ucotherFunds;
	}
	public InvestmentInfo getInvest() {
		return invest;
	}
	public void setInvest(InvestmentInfo invest) {
		this.invest = invest;
		this.totalFunds = invest.getTotalFunds();
		this.stateFunds = invest.getStateFunds();
		this.specialFunds = invest.getSpecialFunds();
		this.provinceFunds = invest.getProvinceFunds();
		this.cityFunds = invest.getCityFunds();
		this.countyFunds = invest.getCountyFunds();
		this.socialFunds = invest.getSocialFunds();
		this.massFunds = invest.getMassFunds();
		this.otherFunds = invest.getOtherFunds();
	}
	public int getStp() {
		return stp;
	}
	public void setStp(int stp) {
		this.stp = stp;
	}
	public int getFp() {
		return fp;
	}
	public void setFp(int fp) {
		this.fp = fp;
	}
	public int getEndp() {
		return endp;
	}
	public void setEndp(int endp) {
		this.endp = endp;
	}

	
}
