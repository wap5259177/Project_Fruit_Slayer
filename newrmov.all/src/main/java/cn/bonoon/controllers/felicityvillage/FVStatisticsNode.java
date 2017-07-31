package cn.bonoon.controllers.felicityvillage;

import cn.bonoon.entities.felicityVillage.AbstractFVModelAreaEntity;
import cn.bonoon.entities.felicityVillage.FVMACountyReportEntity;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.kernel.support.models.AbstractAjaxNode;

public class FVStatisticsNode extends AbstractAjaxNode {

	
	private static final long serialVersionUID = 5642165653300592935L;
	
	private Object constructionArea;      
	private Object households;
	private Object population;
	
	
	private String name;
	private int status = -1;
	
	
	public FVStatisticsNode() {
	}
	public FVStatisticsNode(FVMACountyReportEntity cr) {
		String status = "";
		switch (cr.getStatus()) {
		case 1:
			status = "(<font style='color:blue;'>完成</font>)";
			break;
		case 2:
			status = "(<font style='color:green;'>进行中</font>)";
			break;
		case 3:
			status = "(<font style='color:red;'>驳回</font>)";
			break;
		case 4:
			status = "(<font style='color:red;'>待审核</font>)";
			break;

		default:
			status = "未开始";
			break;
		}
		this.name = cr.getPlace().getName()+status;
		this.setId(-cr.getId());
		this.status = cr.getStatus();
		this.setState(CLOSED);
		sum(cr);
	}
	
	
	
	
	public FVStatisticsNode(FVModelAreaReportEntity ma) {
		this.setName(ma.getName());
		this.setId(ma.getId());
		sum(ma);
	}
	private void sum(AbstractFVModelAreaEntity cr) {
		this.constructionArea = cr.getConstructionArea();
		this.households = cr.getHouseholds();
		this.population = cr.getPopulation();
	}
	public Object getConstructionArea() {
		return constructionArea;
	}
	public void setConstructionArea(Object constructionArea) {
		this.constructionArea = constructionArea;
	}
	public Object getHouseholds() {
		return households;
	}
	public void setHouseholds(Object households) {
		this.households = households;
	}
	public Object getPopulation() {
		return population;
	}
	public void setPopulation(Object population) {
		this.population = population;
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

	
	
}
