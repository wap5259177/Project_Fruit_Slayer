package cn.bonoon.controllers.felicity;


import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.FelicityCountyReportService;
import cn.bonoon.entities.AbstractFelicityEntity;
import cn.bonoon.entities.FelicityCountyEntity;
import cn.bonoon.entities.FelicityCountyReportEntity;
import cn.bonoon.entities.FelicityVillageEntity;
import cn.bonoon.entities.FelicityVillageReportEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

public abstract class StatisticsFelicityController extends AbstractPanelController{

	@Autowired
	protected FelicityCountyReportService fcrService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		Calendar cal = Calendar.getInstance();
		int nowYear = cal.get(Calendar.YEAR);
		int nowMonth = cal.get(Calendar.MONTH);
		StringBuilder years = new StringBuilder("<option value='-1'>请选择</option>"), 
				months = new StringBuilder("<option value='-1'>请选择</option>");
		for(int i = 2010; i <= nowYear; i++){
			years.append("<option value='").append(i).append("'>").append(i).append("</option>");
		}
		for(int i = 0; i < 12; i++){
			months.append("<option value='").append(i).append("'>").append(i + 1).append("</option>");
		}
		model.addObject("years", years);
		model.addObject("months", months);
		model.addObject("nowYear", nowYear);
		model.addObject("nowMonth", nowMonth);
		event.setVmPath("felicity/statistics");
	}
	
	@RequestMapping(value = "!{key}/items.load")
	public String loadItems(Model model, int startYear, int startMonth, int endYear, int endMonth){
		model.addAttribute("layout", "layout-empty.vm");
		LogonUser user = getUser();
		SFTInfo total = __parseItems(startYear, startMonth, endYear, endMonth, user);
		StringBuilder html = new StringBuilder();
		parseTotalItem(html, total);
		for(SFCInfo info : total.items()){
			info.html(html);
		}
		if(html.length() > 0){
			model.addAttribute("html", html);
		}
		return "felicity/statistics-items";
	}
	
	protected void parseTotalItem(StringBuilder html, SFTInfo total){}
	protected int parseTotalItem(int i, SFTInfo total,HSSFSheet sheet){return i;}
	
	//protected abstract StringBuilder parseHtml(StringBuilder html, SFTInfo total, LogonUser user);
	
	private SFTInfo __parseItems(int startYear, int startMonth, int endYear, int endMonth, LogonUser user) {
		int startAt = 0, endAt = 0;
		if(startYear > 0){
			startAt = startYear * 100;
			if(startMonth > 0){
				startAt += startMonth;
			}
		}
		if(endYear > 0){
			endAt = endYear * 100;
			if(endMonth > 0){
				endAt += endMonth;
			}
		}
		SFTInfo total = new SFTInfo();
		List<FelicityCountyEntity> counties = counties(fcrService, user);
		String in = "";
		for(FelicityCountyEntity fce : counties){
			total.items.put(fce.getId(), new SFCInfo(fce));
			in += "," + fce.getId();
		}
		List<FelicityCountyReportEntity> fcs;
		if(in.isEmpty()){
			fcs = Collections.emptyList();
		}else{
			fcs = fcrService.statistics(startAt, endAt, in.substring(1));
		}
		total.parse(fcs);
		
		return total;
	}
	
	protected abstract List<FelicityCountyEntity> counties(FelicityCountyReportService fcrService, LogonUser user);
	@SuppressWarnings("deprecation")
	@RequestMapping("!{key}/index.excel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,int startYear, int startMonth, int endYear, int endMonth){
		LogonUser user = getUser();
		HSSFWorkbook workbook = null;
		try{//TODO excel start
		String _name = "幸福村居统计表.xls";
		response.setContentType("multipart/form-data");
		
		response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
		SFTInfo total = __parseItems(startYear, startMonth, endYear, endMonth,user);
		 // 声明一个工作薄  
        workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet("工程项目库汇总统计");  
        sheet.setDefaultRowHeight((short)(15.625*25));
        sheet.setDefaultColumnWidth((short) 15);  
        sheet.setColumnWidth(0, 3000); 
        sheet.setColumnWidth(1, 8000); 
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(7, 8000);
        sheet.setColumnWidth(34, 10000);
        
     // 生成一个样式  
        HSSFCellStyle style1 = workbook.createCellStyle();  
        // 设置这些样式  
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        // 生成一个字体  
        HSSFFont font1 = workbook.createFont();  
        font1.setColor(HSSFColor.BLACK.index);  
        font1.setFontHeightInPoints((short) 20);  
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        
        // 把字体应用到当前的样式  
        style1.setFont(font1); 
        
     // 生成一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();  
        // 设置这些样式  
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        // 生成一个字体  
        HSSFFont font2 = workbook.createFont();  
        font2.setColor(HSSFColor.BLACK.index);  
        font2.setFontHeightInPoints((short) 12);  
        // 把字体应用到当前的样式  
        style2.setFont(font2); 
        
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        // 设置这些样式  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        style.setWrapText(true);
        // 生成一个字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.BLACK.index);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
        font.setFontHeightInPoints((short) 12); 
        
        // 把字体应用到当前的样式  
        style.setFont(font); 
        
        // 生成一个样式  
        HSSFCellStyle mStyle = workbook.createCellStyle();  
        // 设置这些样式  
        mStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        mStyle.setWrapText(true);
        // 生成一个字体  
        HSSFFont mFont = workbook.createFont();  
        mFont.setColor(HSSFColor.BLACK.index);  
        mFont.setFontHeightInPoints((short) 12); 
        
        // 把字体应用到当前的样式  
        mStyle.setFont(mFont); 
      //标题栏
        HSSFRow ro = sheet.createRow(0);  
        HSSFCell cel = ro.createCell(0);
        cel.setCellValue(startYear+"年原中央苏区县幸福村居（新农村）连片示范建设工程基本情况统计表");
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 34));
        
        HSSFRow ro1 = sheet.createRow(2);  
        HSSFCell cel1 = ro1.createCell(0);
        String str="";
        String startMonth1=(startMonth+1) +str;
        if(startMonth1.equals("0")){
        	startMonth1="-";
        }
        String endMonth1=(endMonth+1) + str;
        if(endMonth1.equals("0")){
        	endMonth1="-";
        }
        cel1.setCellValue(("统计起止时间")+startYear+("年")+startMonth1+("月至")+endYear+("年")+endMonth1+("月"));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 34));
        
        cel.setCellStyle(style1); 
        cel1.setCellStyle(style2); 
        //第一行
        HSSFRow row = sheet.createRow(3);  //第一行
        HSSFCell cell = row.createCell(0);
        HSSFCell cell1 = row.createCell(1);
        HSSFCell cell2 = row.createCell(2);
        HSSFCell cell3 = row.createCell(3);
        HSSFCell cell4 = row.createCell(4);
        HSSFCell cell5 = row.createCell(5);
        HSSFCell cell6 = row.createCell(6);
        HSSFCell cell7 = row.createCell(7);
        HSSFCell cell8 = row.createCell(8);
        HSSFCell cell9 = row.createCell(9);
        HSSFCell cell10 = row.createCell(10);
        HSSFCell cell11 = row.createCell(11);
        HSSFCell cell12 = row.createCell(12);
        HSSFCell cell13 = row.createCell(21);
        HSSFCell cell14 = row.createCell(23);
        HSSFCell cell15 = row.createCell(25);
        HSSFCell cell16 = row.createCell(34);
        
        cell.setCellStyle(style); 
        cell1.setCellStyle(style); 
        cell2.setCellStyle(style); 
        cell3.setCellStyle(style); 
        cell4.setCellStyle(style); 
        cell5.setCellStyle(style); 
        cell6.setCellStyle(style); 
        cell7.setCellStyle(style); 
        cell8.setCellStyle(style);
        cell9.setCellStyle(style);
        cell10.setCellStyle(style);
        cell11.setCellStyle(style);
        cell12.setCellStyle(style);
        cell13.setCellStyle(style);
        cell14.setCellStyle(style);
        cell15.setCellStyle(style);
        cell16.setCellStyle(style);
    
        
      //第一行标题
        cell.setCellValue("序号");
        cell1.setCellValue("名称");
        cell2.setCellValue("村庄建设类型");
        cell3.setCellValue("A为行政村B为自然村");
        cell4.setCellValue("村庄建设覆盖面积 ㎡");
        cell5.setCellValue("户数");
        cell6.setCellValue("人口数");
        cell7.setCellValue("村建设特色及特点");
        cell8.setCellValue("确定的建设项目数（个）");
        cell9.setCellValue("项目建设进度（%）");
        cell10.setCellValue("投入预算（万元）");
        cell11.setCellValue("目前已完成投入（万元）");
        cell12.setCellValue("资金来源（万元）");
        cell13.setCellValue("规划设计");
        cell14.setCellValue("项目招投标");
        cell15.setCellValue("建设成效");
        cell16.setCellValue("下阶段打算");
        
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 5, 5));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 6, 6));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 7, 7));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 10, 10));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 11, 11));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 12, 20));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 21, 22));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 23, 24));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 25, 33));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 34, 34));
       
        
        //第二行
        HSSFRow row1 = sheet.createRow(4);  //第二行
        HSSFCell tCell = row1.createCell(12);
        HSSFCell tCel11= row1.createCell(13);
        HSSFCell tCel12= row1.createCell(14);
        HSSFCell tCel13= row1.createCell(15);
        HSSFCell tCel14= row1.createCell(16);
        HSSFCell tCel15= row1.createCell(17);
        HSSFCell tCel16= row1.createCell(18);
        HSSFCell tCel17= row1.createCell(19);
        HSSFCell tCel18= row1.createCell(20);
        HSSFCell tCel19= row1.createCell(21);
        HSSFCell tCel110= row1.createCell(22);
        HSSFCell tCel111= row1.createCell(23);
        HSSFCell tCel112= row1.createCell(24);
        HSSFCell tCel113= row1.createCell(25);
        HSSFCell tCel114= row1.createCell(26);
        HSSFCell tCel115= row1.createCell(27);
        HSSFCell tCel116= row1.createCell(28);
        HSSFCell tCel117= row1.createCell(29);
        HSSFCell tCel118= row1.createCell(30);
        HSSFCell tCel119= row1.createCell(31);
        HSSFCell tCel120= row1.createCell(32);
        HSSFCell tCel121= row1.createCell(33);
        
        tCell.setCellStyle(style); 
        tCel11.setCellStyle(style); 
        tCel12.setCellStyle(style); 
        tCel13.setCellStyle(style); 
        tCel14.setCellStyle(style); 
        tCel15.setCellStyle(style); 
        tCel16.setCellStyle(style); 
        tCel17.setCellStyle(style); 
        tCel18.setCellStyle(style); 
        tCel19.setCellStyle(style); 
        tCel110.setCellStyle(style); 
        tCel111.setCellStyle(style); 
        tCel112.setCellStyle(style); 
        tCel113.setCellStyle(style); 
        tCel114.setCellStyle(style); 
        tCel115.setCellStyle(style); 
        tCel116.setCellStyle(style); 
        tCel117.setCellStyle(style); 
        tCel118.setCellStyle(style); 
        tCel119.setCellStyle(style); 
        tCel120.setCellStyle(style); 
        tCel121.setCellStyle(style); 
       
        //第二行标题
        tCell.setCellValue("小计");
        tCel11.setCellValue("省");
        tCel12.setCellValue("市");
        tCel13.setCellValue("县");
        tCel14.setCellValue("镇");
        tCel15.setCellValue("村");
        tCel16.setCellValue("群众");
        tCel17.setCellValue("社会");
        tCel18.setCellValue("其他");
        tCel19.setCellValue("完成或否");
        tCel110.setCellValue("规划进度（%）");
        tCel111.setCellValue("完成或否");
        tCel112.setCellValue("完成招投标比例（%）");
        tCel113.setCellValue("是否完成环境卫生整治");
        tCel114.setCellValue("是否统一民居外立面风貌");
        tCel115.setCellValue("是否解决垃圾问题");
        tCel116.setCellValue("是否建立污水处理设施");
        tCel117.setCellValue("是否建立村保洁队伍");
        tCel118.setCellValue("是否成立村民理事会");
        tCel119.setCellValue("是否完成村巷道硬底化建设");
        tCel120.setCellValue("是否解决通自来水");
        tCel121.setCellValue("其它");
       
        
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 12, 12));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 13, 13));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 14, 14));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 15, 15));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 16, 16));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 17, 17));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 18, 18));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 19, 19));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 20, 20));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 21, 21));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 22, 22));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 23, 23));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 24, 24));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 25, 25));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 26, 26));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 27, 27));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 28, 28));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 29, 29));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 30, 30));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 31, 31));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 32, 32));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 33, 33));
       

        //TODO 显示items里面的数据
        int i=6,s=1;
		i = parseTotalItem(i, total,sheet);
        for(SFCInfo info : total.items()){
            if(null != info){
              info.exce(i++,sheet);
	      	  for(SFVInfo fvi : info.items.values()){
	      	    	fvi.exce(i++, s++,sheet);
	      	  }
            }
        }
        workbook.write(response.getOutputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

abstract class SFInfo{//基本的统计
//	private double constructionArea;//村庄建设覆盖面积
//	private int householdCount;//户数
//	private int population;//人口数
//	private int projectCount;//确定的建设项目数（个）
//	private double investmentBudget;//投入预算（万元）
//	private double investmentCompleted;//目前已完成投入（万元）
//	void read(AbstractFelicityEntity afe){
//		constructionArea = afe.getConstructionArea();
//		householdCount = afe.getHouseholdCount();
//		population = afe.getPopulation();
//		projectCount = afe.getProjectCount();
//		investmentBudget = afe.getInvestmentBudget();
//		investmentCompleted = afe.getInvestmentCompleted();
//		
//		sum(afe);
//	}

	DecimalFormat df = new DecimalFormat("0.0000"); 
	String _4(double d){
		return df.format(d);
	}

	//资金来源（万元）
	double fundsTotal;//小计
	double fundsProvince;//省
	double fundsCity;//市
	double fundsCounty;//县
	double fundsTown;//镇
	double fundsVillage;//村
	double fundsMasses;//群众
	double fundsSociety;//社会
	double fundsOther;//其它
}
abstract class ASFInfo extends SFInfo{
	String name;
	int time = 0;

	void sum(AbstractFelicityEntity afe){
		fundsTotal += afe.getFundsTotal();
		fundsProvince += afe.getFundsProvince();
		fundsCity += afe.getFundsCity();
		fundsCounty += afe.getFundsCounty();
		fundsTown += afe.getFundsTown();
		fundsVillage += afe.getFundsVillage();
		fundsMasses += afe.getFundsMasses();
		fundsSociety += afe.getFundsSociety();
		fundsOther += afe.getFundsOther();
	}
//	private double projectProgress;//项目建设进度（%）
//	private double planningProgress;//规划进度（%）
//	private double biddingProgress;//完成招投标比例（%）
//	
//	@Override
//	void read(AbstractFelicityEntity afe) {
//		super.read(afe);
//		projectProgress = afe.getProjectProgress();
//		planningProgress = afe.getPlanningProgress();
//		biddingProgress = afe.getBiddingProgress();
//	}
}

class SFTInfo extends SFInfo{
	Map<Long, SFCInfo> items = new HashMap<>();
	void parse(List<FelicityCountyReportEntity> fcs){
		for(FelicityCountyReportEntity it : fcs){
			SFCInfo sfc = items.get(it.getCounty().getId());
			if(null != sfc){
				sfc.parse(this, it);
			}
		}
	}
	
	void __cal(){
		//进行统计处理
		for(SFCInfo it : items.values()){
			if(null == it.report) continue;
			sum(it);
			read(it.report);
		}
	}
	@SuppressWarnings("deprecation")
	void exce(int i,String name,HSSFSheet sheet){
		 sheet.setDefaultRowHeight((short)(15.625*25));
	     sheet.setDefaultColumnWidth((short) 15);  
		__cal(); //TODO pro
		int j = 0;
		HSSFRow row=sheet.createRow(i);
		HSSFCell cel=row.createCell(j++);
		cel.setCellValue(name);
		HSSFCellStyle cellStyle2 = cel.getCellStyle();
		cellStyle2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		cel.setCellStyle(cellStyle2);
		sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 3));
		j+=3;
		row.createCell(j++).setCellValue(_4(constructionArea));
		
		row.createCell(j++).setCellValue(householdCount);
		
		row.createCell(j++).setCellValue(population);
		
		HSSFCell tCell4 = row.createCell(j++);
		if(constructionCharacteristic0 > 0){
			tCell4.setCellValue("人文历史"+constructionCharacteristic0+"个");
		}
		if(constructionCharacteristic1 > 0){
			tCell4.setCellValue("乡村旅游"+constructionCharacteristic1+"个");
		}
		if(constructionCharacteristic2 > 0){
			tCell4.setCellValue("自然生态"+constructionCharacteristic2+"个");
		}
		if(constructionCharacteristic3 > 0){
			tCell4.setCellValue("特色产业"+constructionCharacteristic3+"个");
		}
		if(constructionCharacteristic4 > 0){
			tCell4.setCellValue("民居风貌"+constructionCharacteristic4+"个");
		}
		if(constructionCharacteristic5 > 0){
			tCell4.setCellValue("渔业渔港"+constructionCharacteristic5+"个");
		}
		if(constructionCharacteristic5 > 0){
			tCell4.setCellValue("其它"+constructionCharacteristic1+"个");
		}
		
		row.createCell(j++).setCellValue(projectCount);
		
		row.createCell(j++).setCellValue("-");
		
		
		row.createCell(j++).setCellValue(_4(investmentBudget));
		
		row.createCell(j++).setCellValue(_4(investmentCompleted));
		
		row.createCell(j++).setCellValue(_4(fundsTotal));
		
		row.createCell(j++).setCellValue(_4(fundsProvince));
		
		row.createCell(j++).setCellValue(_4(fundsCity));
		
		row.createCell(j++).setCellValue(_4(fundsCounty));
		
		row.createCell(j++).setCellValue(_4(fundsTown));
		
		row.createCell(j++).setCellValue(_4(fundsVillage));
		
		row.createCell(j++).setCellValue(_4(fundsMasses));
		
		row.createCell(j++).setCellValue(_4(fundsSociety));
		
		row.createCell(j++).setCellValue(_4(fundsOther));
	    
		row.createCell(j++).setCellValue(planningCompletedTrue+"/"+planningCompletedFalse);
	    
		row.createCell(j++).setCellValue("-");
		
		row.createCell(j++).setCellValue(biddingCompletedTrue+"/"+biddingCompletedFalse);
		
		row.createCell(j++).setCellValue("-");
		
		row.createCell(j++).setCellValue(effectRemediationTrue+"/"+effectRemediationFalse);
		
		
		row.createCell(j++).setCellValue(effectUniformStyleTrue+"/"+effectUniformStyleFalse);
		
		row.createCell(j++).setCellValue(effectSolveGarbageTrue+"/"+effectSolveGarbageFalse);
		
	    row.createCell(j++).setCellValue(effectSewageTreatmentTrue+"/"+effectSewageTreatmentFalse);
		
		row.createCell(j++).setCellValue(effectCleaningTeamTrue+"/"+effectCleaningTeamFalse);
		
		row.createCell(j++).setCellValue(effectCouncilTrue+"/"+effectCouncilFalse);
		
		row.createCell(j++).setCellValue(effectHardBottomTrue+"/"+effectHardBottomFalse);
		
        row.createCell(j++).setCellValue(effectThroughWaterTrue+"/"+effectThroughWaterFalse);
		
		row.createCell(j++).setCellValue("-");
		
		row.createCell(j++).setCellValue("-");
	}
	void html(String name, StringBuilder html){
		__cal();
		
		html.append("<tr><td colspan='4' style='text-align:right;font-weight:bold;color:blue;'>").append(name);
		html.append("</td><td>").append(_4(constructionArea));
		html.append("</td><td>").append(householdCount);
		html.append("</td><td>").append(population);
		
		html.append("</td><td style='text-align:left;'>");//村建设特色及特点
		if(constructionCharacteristic0 > 0){
			html.append("人文历史").append(constructionCharacteristic0).append("个<br/>");
		}
		if(constructionCharacteristic1 > 0){
			html.append("乡村旅游").append(constructionCharacteristic1).append("个<br/>");
		}
		if(constructionCharacteristic2 > 0){
			html.append("自然生态").append(constructionCharacteristic2).append("个<br/>");
		}
		if(constructionCharacteristic3 > 0){
			html.append("特色产业").append(constructionCharacteristic3).append("个<br/>");
		}
		if(constructionCharacteristic4 > 0){
			html.append("民居风貌").append(constructionCharacteristic4).append("个<br/>");
		}
		if(constructionCharacteristic5 > 0){
			html.append("渔业渔港").append(constructionCharacteristic5).append("个<br/>");
		}
		if(constructionCharacteristic6 > 0){
			html.append("其它").append(constructionCharacteristic6).append("个<br/>");
		}
		
		html.append("</td><td>").append(projectCount);
		html.append("</td><td> - ");//.append(" - ");
		html.append("</td><td>").append(_4(investmentBudget));
		html.append("</td><td>").append(_4(investmentCompleted));
		
		html.append("</td><td>").append(_4(fundsTotal));//资金来源
		html.append("</td><td>").append(_4(fundsProvince));
		html.append("</td><td>").append(_4(fundsCity));
		html.append("</td><td>").append(_4(fundsCounty));
		html.append("</td><td>").append(_4(fundsTown));
		html.append("</td><td>").append(_4(fundsVillage));
		html.append("</td><td>").append(_4(fundsMasses));
		html.append("</td><td>").append(_4(fundsSociety));
		html.append("</td><td>").append(_4(fundsOther));
		
		html.append("</td><td title='完成/未完成'>").append(planningCompletedTrue);//项目设计
		html.append("/").append(planningCompletedFalse);
		html.append("</td><td> - ");//.append(PlanningProgress);
		
		html.append("</td><td title='完成/未完成'>").append(biddingCompletedTrue);//项目招投标
		html.append("/").append(biddingCompletedFalse);
		html.append("</td><td> - ");//.append(BiddingProgress);
		
		html.append("</td><td title='完成/未完成'>").append(effectRemediationTrue);//建设成效
		html.append("/").append(effectRemediationFalse);
		html.append("</td><td title='统一/未统一'>").append(effectUniformStyleTrue);
		html.append("/").append(effectUniformStyleFalse);
		html.append("</td><td title='解决/未解决'>").append(effectSolveGarbageTrue);
		html.append("/").append(effectSolveGarbageFalse);
		html.append("</td><td title='建立/未建立'>").append(effectSewageTreatmentTrue);
		html.append("/").append(effectSewageTreatmentFalse);
		html.append("</td><td title='建立/未建立'>").append(effectCleaningTeamTrue);
		html.append("/").append(effectCleaningTeamFalse);
		html.append("</td><td title='成立/未成立'>").append(effectCouncilTrue);
		html.append("/").append(effectCouncilFalse);
		html.append("</td><td title='完成/未完成'>").append(effectHardBottomTrue);
		html.append("/").append(effectHardBottomFalse);
		html.append("</td><td title='解决/未解决'>").append(effectThroughWaterTrue);
		html.append("/").append(effectThroughWaterFalse);
		html.append("</td><td> - </td><td> - </td></tr>");//其它,下阶段打算
		
	}

	double constructionArea;//村庄建设覆盖面积
	int householdCount;//户数
	int population;//人口数
	int projectCount;//确定的建设项目数（个）
	double investmentBudget;//投入预算（万元）
	double investmentCompleted;//目前已完成投入（万元）
	
	int constructionCharacteristic0;//人文历史
	int constructionCharacteristic1;//乡村旅游
	int constructionCharacteristic2;//自然生态
	int constructionCharacteristic3;//特色产业
	int constructionCharacteristic4;//民居风貌
	int constructionCharacteristic5;//渔业渔港
	int constructionCharacteristic6;//其它
	void read(FelicityCountyReportEntity afe){
		//constructionArea += afe.get;
		constructionCharacteristic0 += afe.getConstructionCharacteristic0();
		constructionCharacteristic1 += afe.getConstructionCharacteristic1();
		constructionCharacteristic2 += afe.getConstructionCharacteristic2();
		constructionCharacteristic3 += afe.getConstructionCharacteristic3();
		constructionCharacteristic4 += afe.getConstructionCharacteristic4();
		constructionCharacteristic5 += afe.getConstructionCharacteristic5();
		constructionCharacteristic6 += afe.getConstructionCharacteristic6();
		planningCompletedTrue += afe.getPlanningCompletedTrue();
		planningCompletedFalse += afe.getPlanningCompletedFalse();
		biddingCompletedTrue += afe.getBiddingCompletedTrue();
		biddingCompletedFalse += afe.getBiddingCompletedFalse();
		effectRemediationTrue += afe.getEffectRemediationTrue();
		effectRemediationFalse += afe.getEffectRemediationFalse();
		effectUniformStyleTrue += afe.getEffectUniformStyleTrue();
		effectUniformStyleFalse += afe.getEffectUniformStyleFalse();
		effectSolveGarbageTrue += afe.getEffectSolveGarbageTrue();
		effectSolveGarbageFalse += afe.getEffectSolveGarbageFalse();
		effectSewageTreatmentTrue += afe.getEffectSewageTreatmentTrue();
		effectSewageTreatmentFalse += afe.getEffectSewageTreatmentFalse();
		effectCleaningTeamTrue += afe.getEffectCleaningTeamTrue();
		effectCleaningTeamFalse += afe.getEffectCleaningTeamFalse();
		effectCouncilTrue += afe.getEffectCouncilTrue();
		effectCouncilFalse += afe.getEffectCouncilFalse();
		effectHardBottomTrue += afe.getEffectHardBottomTrue();
		effectHardBottomFalse += afe.getEffectHardBottomFalse();
		effectThroughWaterTrue += afe.getEffectThroughWaterTrue();
		effectThroughWaterFalse += afe.getEffectThroughWaterFalse();
		
		constructionArea += afe.getConstructionArea();
		householdCount += afe.getHouseholdCount();
		population += afe.getPopulation();
		projectCount += afe.getProjectCount();
		investmentBudget += afe.getInvestmentBudget();
		investmentCompleted += afe.getInvestmentCompleted();
		
	}
	//规划设计
	//完成或否
	int planningCompletedTrue;
	int planningCompletedFalse;
	//项目招投标
	//完成或否
	int biddingCompletedTrue;
	int biddingCompletedFalse;
	//建设成效effect
	//是否完成环境卫生整治
	int effectRemediationTrue;
	int effectRemediationFalse;
	//是否统一民居外立面风貌
	int effectUniformStyleTrue;
	int effectUniformStyleFalse;
	//是否解决垃圾问题
	int effectSolveGarbageTrue;
	int effectSolveGarbageFalse;
	//是否建立污水处理设施
	int effectSewageTreatmentTrue;
	int effectSewageTreatmentFalse;
	//是否建立村保洁队伍
	int effectCleaningTeamTrue;
	int effectCleaningTeamFalse;
	//是否成立村民理事会
	int effectCouncilTrue;
	int effectCouncilFalse;
	//是否完成村巷道硬底化建设
	int effectHardBottomTrue;
	int effectHardBottomFalse;
	//是否解决通自来水
	int effectThroughWaterTrue;
	int effectThroughWaterFalse;
	
	Collection<SFCInfo> items(){
		return items.values();
	}
	
	void sum(SFInfo afe){
		fundsTotal += afe.fundsTotal;
		fundsProvince += afe.fundsProvince;
		fundsCity += afe.fundsCity;
		fundsCounty += afe.fundsCounty;
		fundsTown += afe.fundsTown;
		fundsVillage += afe.fundsVillage;
		fundsMasses += afe.fundsMasses;
		fundsSociety += afe.fundsSociety;
		fundsOther += afe.fundsOther;
	}
}
class SFCInfo extends ASFInfo{//县
	
	Map<Long, SFVInfo> items = new HashMap<>();
	
	SFCInfo(FelicityCountyEntity fce){
		name = fce.getCityName() + fce.getName() + "幸福村居示范片小计";
		for(FelicityVillageEntity fv : fce.getVillages()){
			items.put(fv.getId(), new SFVInfo(fv));
		}
	}
	
	FelicityCountyReportEntity report;
	
	void parse(SFInfo total, FelicityCountyReportEntity fcs){
		int time = fcs.getAnnual() * 100 + fcs.getMonth();
		sum(fcs);
		boolean replace = time > this.time;
		if(replace){
			report = fcs;
		}

		for(FelicityVillageReportEntity fvr : fcs.getItems()){
			SFVInfo info = items.get(fvr.getVillage().getId());
			if(null != info){
				info.parse(fvr, replace);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	void exce(int i,HSSFSheet sheet){
		 sheet.setDefaultRowHeight((short)(15.625*25));
	        sheet.setDefaultColumnWidth((short) 15);  
		int j = 0;
		HSSFRow row=sheet.createRow(i);
		HSSFCell cel=row.createCell(j);
		cel.setCellValue(name);
		HSSFCellStyle cellStyle2 = cel.getCellStyle();
		cellStyle2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		cel.setCellStyle(cellStyle2);
		sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 3));
		j+=4;
		if(null == report) return;
		row.createCell(j++).setCellValue(report.getConstructionArea());
		
		row.createCell(j++).setCellValue(report.getHouseholdCount());
		
		row.createCell(j++).setCellValue(report.getPopulation());
		
		HSSFCell tCell4 = row.createCell(j++);
		if(report.getConstructionCharacteristic0() > 0){
			tCell4.setCellValue("人文历史"+report.getConstructionCharacteristic0()+"个");
		}
		if(report.getConstructionCharacteristic1() > 0){
			tCell4.setCellValue("乡村旅游"+report.getConstructionCharacteristic1()+"个");
		}
		if(report.getConstructionCharacteristic2() > 0){
			tCell4.setCellValue("自然生态"+report.getConstructionCharacteristic2()+"个");
		}
		if(report.getConstructionCharacteristic3() > 0){
			tCell4.setCellValue("特色产业"+report.getConstructionCharacteristic3()+"个");
		}
		if(report.getConstructionCharacteristic4() > 0){
			tCell4.setCellValue("民居风貌"+report.getConstructionCharacteristic4()+"个");
		}
		if(report.getConstructionCharacteristic5() > 0){
			tCell4.setCellValue("渔业渔港"+report.getConstructionCharacteristic5()+"个");
		}
		if(report.getConstructionCharacteristic6() > 0){
			tCell4.setCellValue("其它"+report.getConstructionCharacteristic6()+"个");
		}
		
		row.createCell(j++).setCellValue(report.getProjectCount());
		
		row.createCell(j++).setCellValue(report.getProjectProgress());
		
		row.createCell(j++).setCellValue(_4(report.getInvestmentBudget()));
		
		row.createCell(j++).setCellValue(_4(report.getInvestmentCompleted()));
		
		row.createCell(j++).setCellValue(_4(fundsTotal));
		
		row.createCell(j++).setCellValue(_4(fundsProvince));
		
		row.createCell(j++).setCellValue(_4(fundsCity));
		
		row.createCell(j++).setCellValue(_4(fundsCounty));
		
		row.createCell(j++).setCellValue(_4(fundsTown));
		
		row.createCell(j++).setCellValue(_4(fundsVillage));
		
		row.createCell(j++).setCellValue(_4(fundsMasses));
		
		row.createCell(j++).setCellValue(_4(fundsSociety));
		
		row.createCell(j++).setCellValue(_4(fundsOther));
	    
		row.createCell(j++).setCellValue(report.getPlanningCompletedTrue()+"/"+report.getPlanningCompletedFalse());
	    
		row.createCell(j++).setCellValue(report.getPlanningProgress());
		
		row.createCell(j++).setCellValue(report.getBiddingCompletedTrue()+"/"+report.getBiddingCompletedFalse());
		
		row.createCell(j++).setCellValue(report.getBiddingProgress());
		
		row.createCell(j++).setCellValue(report.getEffectRemediationTrue()+"/"+report.getEffectRemediationFalse());
		
		row.createCell(j++).setCellValue(report.getEffectUniformStyleTrue()+"/"+report.getEffectUniformStyleFalse());
		
		row.createCell(j++).setCellValue(report.getEffectSolveGarbageTrue()+"/"+report.getEffectSolveGarbageFalse());
		
		row.createCell(j++).setCellValue(report.getEffectSewageTreatmentTrue()+"/"+report.getEffectSewageTreatmentFalse());
		
		row.createCell(j++).setCellValue(report.getEffectCleaningTeamTrue()+"/"+report.getEffectCleaningTeamFalse());
		
		row.createCell(j++).setCellValue(report.getEffectCouncilTrue()+"/"+report.getEffectCouncilFalse());
		
		row.createCell(j++).setCellValue(report.getEffectHardBottomTrue()+"/"+report.getEffectHardBottomFalse());
		
		row.createCell(j++).setCellValue(report.getEffectThroughWaterTrue()+"/"+report.getEffectThroughWaterFalse());
		
		row.createCell(j++).setCellValue("-");
		
		row.createCell(j++).setCellValue("-");
//		int i = 1;
//		int j=7;
//		for(SFVInfo info : items.values()){
//			info.exce(i++, row,sheet,j++);
//		}
	}
	
	void html(StringBuilder html){
		if(null == report) return;
		html.append("<tr><td colspan='4' style='text-align:right;font-weight:bold;'>").append(name);
		html.append("</td><td>").append(report.getConstructionArea());
		html.append("</td><td>").append(report.getHouseholdCount());
		html.append("</td><td>").append(report.getPopulation());
		
		html.append("</td><td style='text-align:left;'>");//村建设特色及特点
		if(report.getConstructionCharacteristic0() > 0){
			html.append("人文历史").append(report.getConstructionCharacteristic0()).append("个<br/>");
		}
		if(report.getConstructionCharacteristic1() > 0){
			html.append("乡村旅游").append(report.getConstructionCharacteristic1()).append("个<br/>");
		}
		if(report.getConstructionCharacteristic2() > 0){
			html.append("自然生态").append(report.getConstructionCharacteristic2()).append("个<br/>");
		}
		if(report.getConstructionCharacteristic3() > 0){
			html.append("特色产业").append(report.getConstructionCharacteristic3()).append("个<br/>");
		}
		if(report.getConstructionCharacteristic4() > 0){
			html.append("民居风貌").append(report.getConstructionCharacteristic4()).append("个<br/>");
		}
		if(report.getConstructionCharacteristic5() > 0){
			html.append("渔业渔港").append(report.getConstructionCharacteristic5()).append("个<br/>");
		}
		if(report.getConstructionCharacteristic6() > 0){
			html.append("其它").append(report.getConstructionCharacteristic6()).append("个<br/>");
		}
		
		html.append("</td><td>").append(report.getProjectCount());
		html.append("</td><td>").append(report.getProjectProgress());
		html.append("</td><td>").append(_4(report.getInvestmentBudget()));
		html.append("</td><td>").append(_4(report.getInvestmentCompleted()));
		
		html.append("</td><td>").append(_4(fundsTotal));//资金来源
		html.append("</td><td>").append(_4(fundsProvince));
		html.append("</td><td>").append(_4(fundsCity));
		html.append("</td><td>").append(_4(fundsCounty));
		html.append("</td><td>").append(_4(fundsTown));
		html.append("</td><td>").append(_4(fundsVillage));
		html.append("</td><td>").append(_4(fundsMasses));
		html.append("</td><td>").append(_4(fundsSociety));
		html.append("</td><td>").append(_4(fundsOther));
		
		html.append("</td><td title='完成/未完成'>").append(report.getPlanningCompletedTrue());//项目设计
		html.append("/").append(report.getPlanningCompletedFalse());
		html.append("</td><td>").append(report.getPlanningProgress());
		
		html.append("</td><td title='完成/未完成'>").append(report.getBiddingCompletedTrue());//项目招投标
		html.append("/").append(report.getBiddingCompletedFalse());
		html.append("</td><td>").append(report.getBiddingProgress());
		
		html.append("</td><td title='完成/未完成'>").append(report.getEffectRemediationTrue());//建设成效
		html.append("/").append(report.getEffectRemediationFalse());
		html.append("</td><td title='统一/未统一'>").append(report.getEffectUniformStyleTrue());
		html.append("/").append(report.getEffectUniformStyleFalse());
		html.append("</td><td title='解决/未解决'>").append(report.getEffectSolveGarbageTrue());
		html.append("/").append(report.getEffectSolveGarbageFalse());
		html.append("</td><td title='建立/未建立'>").append(report.getEffectSewageTreatmentTrue());
		html.append("/").append(report.getEffectSewageTreatmentFalse());
		html.append("</td><td title='建立/未建立'>").append(report.getEffectCleaningTeamTrue());
		html.append("/").append(report.getEffectCleaningTeamFalse());
		html.append("</td><td title='成立/未成立'>").append(report.getEffectCouncilTrue());
		html.append("/").append(report.getEffectCouncilFalse());
		html.append("</td><td title='完成/未完成'>").append(report.getEffectHardBottomTrue());
		html.append("/").append(report.getEffectHardBottomFalse());
		html.append("</td><td title='解决/未解决'>").append(report.getEffectThroughWaterTrue());
		html.append("/").append(report.getEffectThroughWaterFalse());
		html.append("</td><td> - </td><td> - </td></tr>");//其它,下阶段打算
		
		int i = 1;
		for(SFVInfo info : items.values()){
			info.html(i++, html);
		}
	}
}

class SFVInfo extends ASFInfo{//村
	String constructionType;//村庄建设类型
	String villageType;//A为行政村 B为自然村
	FelicityVillageReportEntity report;
	String constructionCharacteristic = "";
	SFVInfo(FelicityVillageEntity fv){
		name = fv.getTownName() + fv.getName() + fv.getNaturalVillage();
		constructionType = fv.getConstructionType();
		villageType = fv.getVillageType();
	}
	
	void parse(FelicityVillageReportEntity afe, boolean replace) {
		if(replace){
			report = afe;
		}
		if(StringHelper.isNotEmpty(afe.getConstructionCharacteristic())){
			constructionCharacteristic += "," + afe.getConstructionCharacteristic();
		}
		sum(afe);
	}
	@SuppressWarnings("deprecation")
	void exce(int i,int s,HSSFSheet sheet){
		 sheet.setDefaultRowHeight((short)(15.625*25));
	     sheet.setDefaultColumnWidth((short) 15);  
		//TODO village
		int j = 0;
		HSSFRow row=sheet.createRow(i);
		row.createCell(j++).setCellValue(s);
		
		row.createCell(j++).setCellValue(name);
		
		row.createCell(j++).setCellValue(constructionType);
		
		row.createCell(j++).setCellValue(villageType);
		if(null != report){
			row.createCell(j++).setCellValue(_4(report.getConstructionArea()));
			
			row.createCell(j++).setCellValue(report.getHouseholdCount());
			
			row.createCell(j++).setCellValue(report.getPopulation());
			
			row.createCell(j++).setCellValue(StringHelper.join(",", StringHelper.toList(constructionCharacteristic)));
			
			row.createCell(j++).setCellValue(report.getProjectCount());
			
			row.createCell(j++).setCellValue(report.getProjectProgress());
			
			row.createCell(j++).setCellValue(_4(report.getInvestmentBudget()));
			
			row.createCell(j++).setCellValue(_4(report.getInvestmentCompleted()));
			
			row.createCell(j++).setCellValue(_4(fundsTotal));
		
			
			row.createCell(j++).setCellValue(_4(fundsProvince));
			
			
			row.createCell(j++).setCellValue(_4(fundsCity));
			
			
			row.createCell(j++).setCellValue(_4(fundsCounty));
			
			
			row.createCell(j++).setCellValue(_4(fundsTown));
			
			
			row.createCell(j++).setCellValue(_4(fundsVillage));
		
		    
			row.createCell(j++).setCellValue(_4(fundsMasses));
			
		    
			row.createCell(j++).setCellValue(_4(fundsSociety));
			
			
			row.createCell(j++).setCellValue(_4(fundsOther));
			
			
			row.createCell(j++).setCellValue(_(report.isPlanningCompleted()));
			
			
			row.createCell(j++).setCellValue(report.getPlanningProgress());
			
			
			row.createCell(j++).setCellValue(_(report.isBiddingCompleted()));
			
			
			row.createCell(j++).setCellValue(report.getBiddingProgress());
			
			
			row.createCell(j++).setCellValue(_(report.isEffectRemediation()));
			
			
			row.createCell(j++).setCellValue(_(report.isEffectUniformStyle()));
			
			row.createCell(j++).setCellValue(_(report.isEffectSolveGarbage()));
			
		
			row.createCell(j++).setCellValue(_(report.isEffectSewageTreatment()));
			
			row.createCell(j++).setCellValue(_(report.isEffectCleaningTeam()));
			
			
			row.createCell(j++).setCellValue(_(report.isEffectCouncil()));
			
			
			row.createCell(j++).setCellValue(_(report.isEffectHardBottom()));
			
			row.createCell(j++).setCellValue(_(report.isEffectThroughWater()));
			
			HSSFCell tCell33 = row.createCell(j++);
			if(null != report.getEffectOther()){
				tCell33.setCellValue(report.getEffectOther());
			}else{
				tCell33.setCellValue("");
			}	
			HSSFCell tCell34 = row.createCell(j++);
			if(null != report.getNextStagePlanning()){
				tCell34.setCellValue(report.getNextStagePlanning());
			}else{
				tCell34.setCellValue("");
			}
			
		}
	}
	void html(int i, StringBuilder html){
		html.append("<tr><td>").append(i);
		html.append("</td><td style='text-align:right;font-weight:bold;'>").append(name);
		html.append("</td><td>").append(constructionType);
		html.append("</td><td>").append(villageType);
		if(null != report){
			html.append("</td><td>").append(_4(report.getConstructionArea()));
			html.append("</td><td>").append(report.getHouseholdCount());
			html.append("</td><td>").append(report.getPopulation());
			
			html.append("</td><td style='text-align:left;'>");//村建设特色及特点
			html.append(StringHelper.join(",<br/>", StringHelper.toList(constructionCharacteristic)));
			
			html.append("</td><td>").append(report.getProjectCount());
			html.append("</td><td>").append(report.getProjectProgress());
			html.append("</td><td>").append(_4(report.getInvestmentBudget()));
			html.append("</td><td>").append(_4(report.getInvestmentCompleted()));

			html.append("</td><td>").append(_4(fundsTotal));//资金来源
			html.append("</td><td>").append(_4(fundsProvince));
			html.append("</td><td>").append(_4(fundsCity));
			html.append("</td><td>").append(_4(fundsCounty));
			html.append("</td><td>").append(_4(fundsTown));
			html.append("</td><td>").append(_4(fundsVillage));
			html.append("</td><td>").append(_4(fundsMasses));
			html.append("</td><td>").append(_4(fundsSociety));
			html.append("</td><td>").append(_4(fundsOther));

			html.append("</td><td>").append(_(report.isPlanningCompleted()));//项目设计
			html.append("</td><td>").append(report.getPlanningProgress());

			html.append("</td><td>").append(_(report.isBiddingCompleted()));//项目招投标
			html.append("</td><td>").append(report.getBiddingProgress());
			
			html.append("</td><td>").append(_(report.isEffectRemediation()));//建设成效
			html.append("</td><td>").append(_(report.isEffectUniformStyle()));
			html.append("</td><td>").append(_(report.isEffectSolveGarbage()));
			html.append("</td><td>").append(_(report.isEffectSewageTreatment()));
			html.append("</td><td>").append(_(report.isEffectCleaningTeam()));
			html.append("</td><td>").append(_(report.isEffectCouncil()));
			html.append("</td><td>").append(_(report.isEffectHardBottom()));
			html.append("</td><td>").append(_(report.isEffectThroughWater()));
			html.append("</td><td>");
			if(null != report.getEffectOther()){
				html.append(report.getEffectOther());
			}
			html.append("</td><td>");
			if(null != report.getNextStagePlanning()){
				html.append(report.getNextStagePlanning());
			}
			html.append("</td>");
		}
		html.append("</tr>");
	}
	private String _(boolean b){
		return b ? "是" : "否";
	}
}