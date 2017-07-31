package cn.bonoon.entities;

import java.util.Calendar;

public class FundUsedInfo {

	private String name;
	
	private String fundContent;
	
	private int lastYear;
	
	private int lastMonth;

	public FundUsedInfo(String name, double localFund, int annual, int month) {
		super();
		Calendar cal = Calendar.getInstance();
		int currentMonth = cal.get(Calendar.MONTH);
		int currentYear = cal.get(Calendar.YEAR);
		if(currentMonth == 11){
			this.lastMonth = 0;
			this.lastYear = currentYear;
		}else if(currentMonth == 0){
			this.lastMonth = 1;
			this.lastYear = currentYear - 1;
		}
		else{
			this.lastMonth = currentMonth + 1;
			this.lastYear = currentYear - 1;
		}
		this.name = name;
		this.fundContent = "";
		setFunds(month, annual, localFund);
	}

	public void setFunds(int month, int annual, double localFund){
		while(!(month == lastMonth && annual == lastYear)){
			this.lastMonth++;
			if(this.lastMonth > 11){
				this.lastMonth = 0;
				this.lastYear++;
			}
			fundContent += ", 0.0";
		}

		if(month == lastMonth && annual == lastYear){
			fundContent += "," + Double.toString(localFund);
		}
		this.lastMonth++;
		if(this.lastMonth > 11){
			this.lastMonth = 0;
			this.lastYear++;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFundContent() {
		return fundContent.substring(1);
	}

	public void setFundContent(String fundContent) {
		this.fundContent = fundContent;
	}
	
	public int getLastYear() {
		return lastYear;
	}

	public void setLastYear(int lastYear) {
		this.lastYear = lastYear;
	}

	public int getLastMonth() {
		return lastMonth;
	}

	public void setLastMonth(int lastMonth) {
		this.lastMonth = lastMonth;
	}
	
	
}
