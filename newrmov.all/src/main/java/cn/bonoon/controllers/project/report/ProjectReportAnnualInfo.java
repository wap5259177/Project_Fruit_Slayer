package cn.bonoon.controllers.project.report;

import java.util.List;

import cn.bonoon.entities.ProjectReportEntity;
/**
 * 界面显示月报实体
 * 
 * @author wsf
 * @creation 2017-6-1 
 *
 */
public class ProjectReportAnnualInfo {
	//系统当前时间的"年+月"
	private int nowMonthly;
	//当前节点的"年+月"
	private int currentMonthly;
	//当前选中显示的"年+月"
	private int selectedMonthly;
	private int year;
	private int month;
	private int showMonth;
	private ProjectReportEntity report;
	/**
	 * 
	 * @param reports
	 * @param year	整个月度报表显示出来的某一年
	 * @param month	整个月度报表显示出来的某一月
	 * @param nowMonthly 当前最新年+月
	 * @param selectedMonthly	选中显示的"年+月"
	 */
	public ProjectReportAnnualInfo(List<ProjectReportEntity> reports, int year, int month, int nowMonthly, int selectedMonthly){
		this.year = year;
		this.month = month;
		this.nowMonthly = nowMonthly;
		this.selectedMonthly = selectedMonthly;
		this.showMonth = month + 1;
		//当前节点的年月进行赋值
		this.currentMonthly = year * 100 + month;
		for(ProjectReportEntity pre : reports){
			if(pre.getAnnual() == year && pre.getPeriod() == month){
				report = pre;
				break;
			}
		}
	}
	
	public ProjectReportEntity getReport(){
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
				if(report.getStatus() == 0){//未提交，正在编辑中
					item.append(" title='项目月报已经填写、未提交可继续编辑！' class='annual-editing'");
				}else{//已提交的，不允许修改
					item.append(" title='项目月报已经提交！' class='annual-finish'");
				}
			}else{//未创建，可以先创建
				item.append("' title='请先创建项目月报！' class='annual-new'");
			}
			item.append(" onclick=\"return startReport(this);\">");
		}
		item.append(showMonth).append("</a></td>");
		return item.toString();
	}
}
