package cn.bonoon.controllers.information;

import cn.bonoon.entities.informatioin.BatchCapitalInvestmentInformationEntity;
import cn.bonoon.entities.informatioin.CapitalInvestmentInformationEntity;
import cn.bonoon.entities.informatioin.CityCapitalInvestmentInformationEntity;
import cn.bonoon.kernel.support.models.AbstractAjaxNode;

public class PCInvestNode extends AbstractAjaxNode {

	
	private static final long serialVersionUID = 5642165653300592935L;
	
	private Object projectStartCount;    
	private Object projectFinishCount;
	private Object totalFunds;
	
	private Object funds0;
	private Object funds1;
	private Object funds2;
	private Object funds3;
	private Object funds4;
	private Object funds5;
	
	
	private String name;
	private int status = -1;
	
	
	public PCInvestNode() {
	}
//	public PCInvestNode(FVMACountyReportEntity cr) {
//		this.name = cr.getPlace().getName();
//		this.setId(-cr.getId());
//		this.status = cr.getStatus();
//		this.setState(CLOSED);
//		sum(cr);
//	}
//	
//	
//	
//	
//	public PCInvestNode(FVModelAreaReportEntity ma) {
//		this.setName(ma.getName());
//		this.setId(ma.getId());
//		sum(ma);
//	}
	
	
	


	public PCInvestNode(CityCapitalInvestmentInformationEntity cr) {
		String status = "";
		switch (cr.getStatus()) {
		case 1:
			status = "(<font style='color:blue'>完成</font>)";
			break;
		case 2:
			status = "(<font style='color:green'>进行中</font>)";
			break;
		case 3:
			status = "(<font style='color:red'>驳回</font>)";
			break;
		case 4:
			status = "(<font style='color:red'>待审核</font>)";
			break;
		default:
			status = "(<font style='color:red'>未开始</font>)";//0
			break;
		}
		this.setName(cr.getPlace().getName()+status);
		this.setId(-cr.getId());
		this.setStatus(cr.getStatus());
		this.setState(CLOSED);
		sum(cr);
	}
	
	public PCInvestNode(BatchCapitalInvestmentInformationEntity ma) {
		this.setName(ma.getName());
		this.setId(ma.getId());
		sum(ma);
	}


	private void sum(CapitalInvestmentInformationEntity c) {
		this.funds0 = c.getFunds0();
		this.funds1 = c.getFunds1();
		this.funds2 = c.getFunds2();
		this.funds3 = c.getFunds3();
		this.funds4 = c.getFunds4();
		this.funds5 = c.getFunds5();
		
		this.projectStartCount = c.getProjectStartCount();
		this.projectFinishCount = c.getProjectFinishCount();
		this.totalFunds = c.getTotalFunds();
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
	public Object getProjectStartCount() {
		return projectStartCount;
	}
	public void setProjectStartCount(Object projectStartCount) {
		this.projectStartCount = projectStartCount;
	}
	public Object getProjectFinishCount() {
		return projectFinishCount;
	}
	public void setProjectFinishCount(Object projectFinishCount) {
		this.projectFinishCount = projectFinishCount;
	}
	public Object getTotalFunds() {
		return totalFunds;
	}
	public void setTotalFunds(Object totalFunds) {
		this.totalFunds = totalFunds;
	}
	public Object getFunds0() {
		return funds0;
	}
	public void setFunds0(Object funds0) {
		this.funds0 = funds0;
	}
	public Object getFunds1() {
		return funds1;
	}
	public void setFunds1(Object funds1) {
		this.funds1 = funds1;
	}
	public Object getFunds2() {
		return funds2;
	}
	public void setFunds2(Object funds2) {
		this.funds2 = funds2;
	}
	public Object getFunds3() {
		return funds3;
	}
	public void setFunds3(Object funds3) {
		this.funds3 = funds3;
	}
	public Object getFunds4() {
		return funds4;
	}
	public void setFunds4(Object funds4) {
		this.funds4 = funds4;
	}
	public Object getFunds5() {
		return funds5;
	}
	public void setFunds5(Object funds5) {
		this.funds5 = funds5;
	}

	
	
}
