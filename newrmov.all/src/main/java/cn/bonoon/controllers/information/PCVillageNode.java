package cn.bonoon.controllers.information;

import cn.bonoon.entities.informatioin.CityNaturalVillageInformationEntity;
import cn.bonoon.entities.informatioin.CountyNaturalVillageInformationEntity;
import cn.bonoon.entities.informatioin.NaturalVillageInformationEntity;
import cn.bonoon.kernel.support.models.AbstractAjaxNode;

public class PCVillageNode extends AbstractAjaxNode {

	
	private static final long serialVersionUID = 5642165653300592935L;
	
	private Object householdCount;    
	private Object peopleCount;
	private Object avCount;
	
	private Object household0;
	private Object household1;
	private Object household2;
	private Object household3;
	private Object household4;
	
	
	private String name;
	private int status = -1;
	
	
	public PCVillageNode() {
	}
	
	public PCVillageNode(CityNaturalVillageInformationEntity cr) {
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
	
	public PCVillageNode(CountyNaturalVillageInformationEntity b) {
		this.setName(b.getName());
		this.setId(b.getId());
		sum(b);
	}

	private void sum(NaturalVillageInformationEntity c) {
		this.householdCount = c.getHouseholdCount();
		this.peopleCount = c.getPeopleCount();
		this.avCount = c.getAvCount();
		
		this.household0 = c.getHousehold0();
		this.household1 = c.getHousehold1();
		this.household2 = c.getHousehold2();
		this.household3 = c.getHousehold3();
		this.household4 = c.getHousehold4();
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




	public Object getHouseholdCount() {
		return householdCount;
	}




	public void setHouseholdCount(Object householdCount) {
		this.householdCount = householdCount;
	}




	public Object getPeopleCount() {
		return peopleCount;
	}




	public void setPeopleCount(Object peopleCount) {
		this.peopleCount = peopleCount;
	}




	public Object getAvCount() {
		return avCount;
	}




	public void setAvCount(Object avCount) {
		this.avCount = avCount;
	}




	public Object getHousehold0() {
		return household0;
	}




	public void setHousehold0(Object household0) {
		this.household0 = household0;
	}




	public Object getHousehold1() {
		return household1;
	}




	public void setHousehold1(Object household1) {
		this.household1 = household1;
	}




	public Object getHousehold2() {
		return household2;
	}




	public void setHousehold2(Object household2) {
		this.household2 = household2;
	}




	public Object getHousehold3() {
		return household3;
	}




	public void setHousehold3(Object household3) {
		this.household3 = household3;
	}




	public Object getHousehold4() {
		return household4;
	}




	public void setHousehold4(Object household4) {
		this.household4 = household4;
	}

	
	
}
