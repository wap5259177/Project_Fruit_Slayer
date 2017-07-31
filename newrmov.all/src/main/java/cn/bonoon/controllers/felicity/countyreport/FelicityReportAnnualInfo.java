package cn.bonoon.controllers.felicity.countyreport;

import java.util.List;

import cn.bonoon.entities.FelicityCountyReportEntity;

public class FelicityReportAnnualInfo {
	//系统当前时间的"年+月"
	private int nowMonthly;
	//当前节点的"年+月"
	private int currentMonthly;
	//当前选中显示的"年+月"
	private int selectedMonthly;
	private int year;
	private int month;
	private int showMonth;
	private FelicityCountyReportEntity report;
	
	public FelicityReportAnnualInfo(List<FelicityCountyReportEntity> reports, int year, int month, int nowMonthly, int selectedMonthly){
		this.year = year;
		this.month = month;
		this.nowMonthly = nowMonthly;
		this.selectedMonthly = selectedMonthly;
		this.showMonth = month + 1;
		this.currentMonthly = year * 100 + month;
		for(FelicityCountyReportEntity pre : reports){
			if(pre.getAnnual() == year && pre.getMonth() == month){
				report = pre;
				break;
			}
		}
	}
	
	public FelicityCountyReportEntity getReport(){
		return report;
	}
	
	public boolean isSelected(){
		return currentMonthly == selectedMonthly;
	}
	
	@Override
	public String toString() {
		StringBuilder item = new StringBuilder();
		item.append("<td class='annual-monthly");
		if(isSelected()){
			item.append(" annual-selected");
		}
		item.append("'><a id='monthly_").append(currentMonthly).append("'");
		if(currentMonthly > nowMonthly){
			//超出当前月，不允许上报数据
			item.append(" class='annual-disabled' title='只允许上报").append(nowMonthly + 1)
				.append("月份及以前的数据！' href='javascript:void(null);'>");
		}else{
			item.append(" href='report!").append(year)
				.append('!').append(month)
				.append(".start?id=");
			if(null != report){//已经上报过
				item.append(report.getId()).append("'");
				int rs = report.getStatus();
				if(rs == 0){//未提交，正在编辑中
					item.append(" title='幸福村居项目月报已经填写、未提交可继续编辑！' class='annual-editing'");
				}else if(rs == 2){
					item.append(" title='幸福村居项目月报等待审核！' class='annual-auditing'");
				}else if(rs == 3){
					item.append(" title='幸福村居项目月报被驳回修改！' class='annual-reject'");
				}else{//已提交的，不允许修改
					item.append(" title='幸福村居项目月报已经提交！' class='annual-finish'");
				}
			}else{//未创建，可以先创建
				item.append("' title='请先创建幸福村居项目月报！' class='annual-new'");
			}
			item.append(" onclick=\"return loadReport(this);\">");
		}
		item.append(showMonth).append("</a></td>");
		return item.toString();
	}
}
