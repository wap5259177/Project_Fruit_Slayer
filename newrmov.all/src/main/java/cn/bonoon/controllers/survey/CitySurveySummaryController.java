package cn.bonoon.controllers.survey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.SurveySummaryCityService;
import cn.bonoon.entities.SurveySummaryCityEntity;
import cn.bonoon.entities.SurveySummaryCountyEntity;
import cn.bonoon.entities.SurveySummaryProvinceEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractLayoutController;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/ml/ss/summary")
public class CitySurveySummaryController extends AbstractLayoutController{
	
	@Autowired
	private SurveySummaryCityService surveySummaryCityService;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		//把已经填报的(最近10年)年度、id、和一些内容取出来，生成一个下拉选择
		List<SurveySummaryCityEntity> items = surveySummaryCityService.get(getUser(), 10);
		String data = "", select = "";
		for(SurveySummaryCityEntity ssc : items){
			SurveySummaryProvinceEntity pro = ssc.getProvince();
			Long id = ssc.getId();
			select += "<option value='_" + id + "'>" + pro.getAnnual() + "</option>";
			data += ",_" + id + ":{id:" + id 
					+ ",deadline:'" + sdf.format(pro.getDeadline()) 
					+ "',name:'" + ssc.getCityName() + "'}";
		}
		PanelModel model = event.getModel();
		model.addObject("select", select);
		if(!data.isEmpty()){
			model.addObject("data", data.substring(1));
		}
		event.setVmPath("survey/summary-city");
	}

	
	//打印
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid, Model model) {
		SurveySummaryCityEntity sscity = surveySummaryCityService.get(id);
		
		model.addAttribute("deadline", sdf.format(sscity.getProvince().getDeadline()));
		model.addAttribute("sscity", sscity);
		model.addAttribute("title", "广东省<div style='font-size:20px;display:inline-block;width:100px;height:22px;line-height:22px;border-bottom: solid 1px #000000;'>"+sscity.getCityName()+"</div>新农村建设摸底调查汇总表");
		if(null != id && id > 0){
			List<SurveySummaryCountyEntity> sscs = surveySummaryCityService.countyReports(id);
			model.addAttribute("sscs", sscs);
		}
		return "survey/summary-city-view";
	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping("!{key}/data.report")
	public List<SurveyItem> items(Long id){
		if(null != id && id > 0){
			List<SurveySummaryCountyEntity> sscs = surveySummaryCityService.countyReports(id);
			List<SurveyItem> sis = new ArrayList<>();
			sis.add(new SurveyItem(surveySummaryCityService.get(id)));
			for(SurveySummaryCountyEntity ssc : sscs){
				sis.add(new SurveyItem(ssc));
			}
			return sis;
		}
		return Collections.emptyList();
	}
	
	@RequestMapping("!{key}/export.excel")
	public void excel(Long id,HttpServletRequest request, HttpServletResponse response){
		String name=null;
		String time=null;
		SurveySummaryCityEntity sc=surveySummaryCityService.getCity(id);
		SurveySummaryProvinceEntity pro = sc.getProvince();
		time=sdf.format(pro.getDeadline());
		name=sc.getCityName();
		HSSFWorkbook workbook = null;
		try{//TODO excel start
		String _name = "新农村建设摸底调查汇总统计.xls";
		response.setContentType("multipart/form-data");
		
		response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
		List<SurveySummaryCountyEntity> sscs = surveySummaryCityService.countyReports(id);
		 // 声明一个工作薄  
        workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet("新农村建设摸底调查汇总统计");  
        sheet.setDefaultRowHeight((short)(15.625*25));
        sheet.setDefaultColumnWidth(15);  
        sheet.setColumnWidth(0, 3000); 
        sheet.setColumnWidth(1, 5000); 
        
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
        font2.setColor(HSSFColor.BLUE.index);  
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
        font2.setFontHeightInPoints((short) 10);  
        // 把字体应用到当前的样式  
        style2.setFont(font2); 
        
        HSSFCellStyle style3 = workbook.createCellStyle();
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
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
        cel.setCellValue(("广东省")+name+("新农村建设摸底调查汇总表"));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 16));
        
        HSSFRow ro1 = sheet.createRow(2);  
        HSSFCell cel1 = ro1.createCell(0);
   
        cel1.setCellValue("截止时间:"+time);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 16));
        
        cel.setCellStyle(style1); 
        cel1.setCellStyle(style); 
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
        HSSFCell cell13 = row.createCell(13);
        HSSFCell cell14 = row.createCell(14);
        HSSFCell cell15 = row.createCell(15);
        HSSFCell cell16 = row.createCell(16);
        
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
        cell1.setCellValue("县（市、区）名称");
        cell2.setCellValue("1.农业户数（户）");
        cell3.setCellValue("2.农业人口数（人");
        cell4.setCellValue("3.建制行政村或居委会(个)   ");
        cell5.setCellValue("4.20户以上自然村（个）");
        cell6.setCellValue("5.已完成村庄规划的自然村（条）");
        cell7.setCellValue("6.外立面统一装饰风格风貌的自然村（条）");
        cell8.setCellValue("7.已实现村巷道硬底化的自然村(条)");
        cell9.setCellValue("8.已实现村村通自来水的自然村（条）");
        cell10.setCellValue("9.建有小公园、文化活动场所或绿化带的自然村（条）");
        cell11.setCellValue("10.已完成村容村貌整治的自然村（条）");
        cell12.setCellValue("11.建有卫生保洁队伍的自然村（条）");
        cell13.setCellValue("12.已实行雨污分流的自然村（条）");
        cell14.setCellValue("13.建有人工湿地、厌氧池、沼气池等处理生活污水的自然村（条）");
        cell15.setCellValue("14. 实行畜禽集中圈养、人畜分离的自然村（条）");
        cell16.setCellValue("15.健全村规民约、章程及村民理事会的自然村（条）");
        
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 5, 5));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 6, 6));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 7, 7));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 10, 10));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 11, 11));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 12, 12));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 13, 13));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 14, 14));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 15, 15));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 16, 16));
        
        //显示items里面的数据
        SurveySummaryCityEntity city=surveySummaryCityService.get(id);
        int i=9, k = 1;
        HSSFRow Row=sheet.createRow(8);
        HSSFCell rmcell =Row.createCell(0);
        rmcell.setCellValue(1);
        rmcell.setCellStyle(style3);
        HSSFCell mcell = Row.createCell(1);
        mcell.setCellValue("全市合计:");
        HSSFCell mcell1=Row.createCell(2);
        mcell1.setCellValue(city.getAgriculturalHousehold());
        HSSFCell mcell2=Row.createCell(3);
        mcell2.setCellValue(city.getAgriculturalPopulation());
        HSSFCell mcell3=Row.createCell(4);
        mcell3.setCellValue(city.getVillageCommittee());
        HSSFCell mcell4=Row.createCell(5);
        mcell4.setCellValue(city.getNaturalVillage());
        HSSFCell mcell5=Row.createCell(6);
        mcell5.setCellValue(city.getVillagePlanning());
        HSSFCell mcell6=Row.createCell(7);
        mcell6.setCellValue(city.getUnifiedStyle());
        HSSFCell mcell7=Row.createCell(8);
        mcell7.setCellValue(city.getHardBottom());
        HSSFCell mcell8=Row.createCell(9);
        mcell8.setCellValue(city.getTapWater());
        HSSFCell mcell9=Row.createCell(10);
        mcell9.setCellValue(city.getSpcvgb());
        HSSFCell mcell10=Row.createCell(11);
        mcell10.setCellValue(city.getVillageRenovation());
        HSSFCell mcell11=Row.createCell(12);
        mcell11.setCellValue(city.getCleaningTeam());
        HSSFCell mcell12=Row.createCell(13);
        mcell12.setCellValue(city.getRainSewageDiversion());
        HSSFCell mcell13=Row.createCell(14);
        mcell13.setCellValue(city.getSewageTreatment());
        HSSFCell mcell14=Row.createCell(15);
        mcell14.setCellValue(city.getLivestockConcentratedCaptive());
        HSSFCell mcell15=Row.createCell(16);  
        mcell15.setCellValue(city.getVillagerCouncil());
        
        mcell.setCellStyle(style2);
        mcell1.setCellStyle(style2);
        mcell2.setCellStyle(style2);
        mcell3.setCellStyle(style2);
        mcell4.setCellStyle(style2);
        mcell5.setCellStyle(style2);
        mcell6.setCellStyle(style2);
        mcell7.setCellStyle(style2);
        mcell8.setCellStyle(style2);
        mcell9.setCellStyle(style2);
        mcell10.setCellStyle(style2);
        mcell11.setCellStyle(style2);
        mcell12.setCellStyle(style2);
        mcell13.setCellStyle(style2);
        mcell14.setCellStyle(style2);
        mcell15.setCellStyle(style2);
        
        for(SurveySummaryCountyEntity ssc : sscs){
        	k++;
            HSSFRow mRow=sheet.createRow(i++); //总行数
            HSSFCell khcell=mRow.createCell(0);
            khcell.setCellValue(k);
            khcell.setCellStyle(style3);
            HSSFCell hcell =mRow.createCell(1);
            hcell.setCellValue(ssc.getCounty().getName());
            hcell.setCellStyle(style3);
            HSSFCell hcell1=mRow.createCell(2);
            hcell1.setCellValue(ssc.getAgriculturalHousehold());
            hcell1.setCellStyle(style3);
            HSSFCell hcell2 =mRow.createCell(3);
            hcell2.setCellValue(ssc.getAgriculturalPopulation());
            hcell2.setCellStyle(style3);
            HSSFCell hcell3 =mRow.createCell(4);
            hcell3.setCellValue(ssc.getVillageCommittee());
            hcell3.setCellStyle(style3);
            HSSFCell hcell4 =mRow.createCell(5);
            hcell4.setCellValue(ssc.getNaturalVillage());
            hcell4.setCellStyle(style3);
            HSSFCell hcell5 =mRow.createCell(6);
            hcell5.setCellValue(ssc.getVillagePlanning());
            hcell5.setCellStyle(style3);
            HSSFCell hcell6 =mRow.createCell(7);
            hcell6.setCellValue(ssc.getUnifiedStyle());
            hcell6.setCellStyle(style3);
            HSSFCell hcell7 =mRow.createCell(8);
            hcell7.setCellValue(ssc.getHardBottom());
            hcell7.setCellStyle(style3);
            HSSFCell hcell8 =mRow.createCell(9);
            hcell8.setCellValue(ssc.getTapWater());
            hcell8.setCellStyle(style3);
            HSSFCell hcell9 =mRow.createCell(10);
            hcell9.setCellValue(ssc.getSpcvgb());
            hcell9.setCellStyle(style3);
            HSSFCell hcell10 =mRow.createCell(11);
            hcell10.setCellValue(ssc.getVillageRenovation());
            hcell10.setCellStyle(style3);
            HSSFCell hcell11 =mRow.createCell(12);
            hcell11.setCellValue(ssc.getCleaningTeam());
            hcell11.setCellStyle(style3);
            HSSFCell hcell12 =mRow.createCell(13);
            hcell12.setCellValue(ssc.getRainSewageDiversion());
            hcell12.setCellStyle(style3);
            HSSFCell hcell13 =mRow.createCell(14);
            hcell13.setCellValue(ssc.getSewageTreatment());
            hcell13.setCellStyle(style3);
            HSSFCell hcell14 =mRow.createCell(15);
            hcell14.setCellValue(ssc.getLivestockConcentratedCaptive());
            hcell14.setCellStyle(style3);
            HSSFCell hcell15 =mRow.createCell(16);  
            hcell15.setCellValue(ssc.getVillagerCouncil());
            hcell15.setCellStyle(style3);
        }
        workbook.write(response.getOutputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
