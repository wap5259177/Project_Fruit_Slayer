package cn.bonoon.controllers.statistics;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;

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

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.core.AdministrationCoreRuralService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

public abstract class StatisticsAdministrationController extends AbstractPanelController{
	@Autowired
	protected AdministrationCoreRuralService administrationCoreRuralService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		event.setVmPath("statistics/area-administration");
		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		
		Collection<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items)).addObject("batch", batch);
//		event.setVmPath("statistics/area-administration");
	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch){
		Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content", statistics(items));
		return "statistics/area-administration-items";
	}

	//导出excel
	@RequestMapping(value = "!{key}/index.excel", method = GET)
	public void excel(HttpServletRequest request, HttpServletResponse response, String batch){
		HSSFWorkbook workbook = null;
		try{
			String _name = "主体行政村情况汇总统计.xls";
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
			Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
			 // 声明一个工作薄  
	        workbook = new HSSFWorkbook();  
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.createSheet("主体村情况汇总统计");  
	        
	        sheet.setDefaultColumnWidth(15);  
	        sheet.setDefaultRowHeight((short)(15.625*25));
	        sheet.setColumnWidth(5, 5000); 
	        sheet.setColumnWidth(6, 5000);
	        sheet.setColumnWidth(56, 6000);
	        
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
	        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
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
	        font.setFontHeightInPoints((short) 11); 
	        
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
	        mFont.setFontHeightInPoints((short) 10); 
	        
	        // 把字体应用到当前的样式  
	        mStyle.setFont(mFont); 
	        
	        //标题栏
	        HSSFRow ro = sheet.createRow(0);  
	        HSSFCell cel = ro.createCell(0);
	        cel.setCellValue("广东省新农村连片示范建设工程台账簿 - 所有主体行政村情况累计汇总统计");
	        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 65));
	        
	        cel.setCellStyle(style1); 
        
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
        HSSFCell cell10 = row.createCell(31);
        HSSFCell cell11 = row.createCell(39);
        HSSFCell cell12 = row.createCell(50);
        HSSFCell cell13 = row.createCell(51);
        HSSFCell cell14 = row.createCell(53);
       
        
        cell.setCellStyle(style); 
        cell1.setCellStyle(style); 
        cell2.setCellStyle(style); 
        cell3.setCellStyle(style); 
        cell4.setCellStyle(style); 
        cell5.setCellStyle(style); 
        cell6.setCellStyle(style); 
        cell7.setCellStyle(style2); 
        cell8.setCellStyle(style2); 
        cell9.setCellStyle(style2); 
        cell10.setCellStyle(style2); 
        cell11.setCellStyle(style2); 
        cell12.setCellStyle(style2); 
        cell13.setCellStyle(style2); 
        cell14.setCellStyle(style2); 
        
        cell.setCellValue("序号");
        cell1.setCellValue("批次[A15]");
        cell2.setCellValue("报备年度[A14]");
        cell3.setCellValue("地市");
        cell4.setCellValue("县(区)");
        cell5.setCellValue("示范片名称[A12]");
        cell6.setCellValue("建设主题名称[A13]");
        cell7.setCellValue("行政村名");
        cell8.setCellValue("");
        cell9.setCellValue("（一）基本情况");
        cell10.setCellValue("（二）资源优势情况<");
        cell11.setCellValue("（三）农村公共服务情况");
        cell12.setCellValue("（四）挂点县领导");
        cell13.setCellValue("（五）工作小组");
        cell14.setCellValue("（六）规划进展");
     
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 5, 5));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 6, 6));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 7, 7));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 9, 30));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 31, 38));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 39, 49));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 50, 50));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 51, 52));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 53, 56));
        //第二行
        HSSFRow row1 = sheet.createRow(4);  
        HSSFCell tCell = row1.createCell(8);
        HSSFCell tCel11= row1.createCell(9);
        HSSFCell tCel12= row1.createCell(10);
        HSSFCell tCel13= row1.createCell(11);
        HSSFCell tCel14= row1.createCell(12);
        HSSFCell tCel15= row1.createCell(13);
        HSSFCell tCel16= row1.createCell(14);
        HSSFCell tCel17= row1.createCell(15);
        HSSFCell tCel18= row1.createCell(16);
        HSSFCell tCel19= row1.createCell(17);
        HSSFCell tCel110= row1.createCell(18);
        HSSFCell tCel111= row1.createCell(19);
        HSSFCell tCel112= row1.createCell(20);
        HSSFCell tCel113= row1.createCell(21);
        HSSFCell tCel114= row1.createCell(26);
        HSSFCell tCel115= row1.createCell(31);
        HSSFCell tCel116= row1.createCell(32);
        HSSFCell tCel117= row1.createCell(33);
        HSSFCell tCel118= row1.createCell(34);
        HSSFCell tCel119= row1.createCell(35);
        HSSFCell tCel120= row1.createCell(36);
        HSSFCell tCel121= row1.createCell(37);
        HSSFCell tCel122= row1.createCell(38);
        HSSFCell tCel123= row1.createCell(39);
        HSSFCell tCel124= row1.createCell(40);
        HSSFCell tCel125= row1.createCell(41);
        HSSFCell tCel126= row1.createCell(42);
        HSSFCell tCel127= row1.createCell(43);
        HSSFCell tCel128= row1.createCell(44);
        HSSFCell tCel129= row1.createCell(45);
        HSSFCell tCel130= row1.createCell(46);
        HSSFCell tCel131= row1.createCell(47);
        HSSFCell tCel132= row1.createCell(48);
        HSSFCell tCel133= row1.createCell(49);
        HSSFCell tCel134= row1.createCell(50);
        HSSFCell tCel135= row1.createCell(51);
        HSSFCell tCel136= row1.createCell(52);
        HSSFCell tCel137= row1.createCell(53);
        HSSFCell tCel138= row1.createCell(54);
        HSSFCell tCel139= row1.createCell(55);
        HSSFCell tCel140= row1.createCell(56);
        
        
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
        tCel122.setCellStyle(style); 
        tCel123.setCellStyle(style); 
        tCel124.setCellStyle(style); 
        tCel125.setCellStyle(style);
        tCel126.setCellStyle(style); 
        tCel127.setCellStyle(style); 
        tCel128.setCellStyle(style); 
        tCel129.setCellStyle(style); 
        tCel130.setCellStyle(style); 
        tCel131.setCellStyle(style); 
        tCel132.setCellStyle(style); 
        tCel133.setCellStyle(style); 
        tCel134.setCellStyle(style); 
        tCel135.setCellStyle(style); 
        tCel136.setCellStyle(style); 
        tCel137.setCellStyle(style); 
        tCel138.setCellStyle(style); 
        tCel139.setCellStyle(style); 
        tCel140.setCellStyle(style); 
       
      //第二行标题
        tCell.setCellValue("自然村个数");
        tCel11.setCellValue("总面积");
        tCel12.setCellValue("耕地面积");
        tCel13.setCellValue("户数");
        tCel14.setCellValue("人口数");
        tCel15.setCellValue("劳动力总人数");
        
        tCel16.setCellValue("贫困户数");
        tCel17.setCellValue("贫困人口数");
        tCel18.setCellValue("低保户数");
        tCel19.setCellValue("低保人口数");
        tCel110.setCellValue("五保户数");
        tCel111.setCellValue("需改造的危房户数");
        tCel112.setCellValue("省级扶贫开发重点村（贫困村）个数");
        tCel113.setCellValue("各年度农民人均纯收入");
        tCel114.setCellValue("村集体经济收入");
        
        
        
        tCel115.setCellValue("人文历史");
        tCel116.setCellValue("自然生态");
        tCel117.setCellValue("民居风貌");
        tCel118.setCellValue("乡村旅游");
        tCel119.setCellValue("特色产业");
        tCel120.setCellValue("渔业渔港");
        tCel121.setCellValue("其他");
        tCel122.setCellValue("现有的或正在打造的旅游景点或节点个数");
       
        tCel123.setCellValue("文化活动场所");
        tCel124.setCellValue("文化活动场所");
        tCel125.setCellValue("乡村公园");
        tCel126.setCellValue("乡村公园");
        tCel127.setCellValue("文体广场");
        tCel128.setCellValue("文体广场");
        tCel129.setCellValue("村级卫生站");
        tCel130.setCellValue("村级卫生站");
        tCel131.setCellValue("乡村公厕");
        tCel132.setCellValue("乡村公厕");
        tCel133.setCellValue("建立统一的村级公共服务管理平台村个数");
        tCel134.setCellValue("是否有挂点县领导");
        tCel135.setCellValue("是否有工作小组");
        tCel136.setCellValue("工作小组总人数");
        tCel137.setCellValue("是否建立规划专家指导组");
        tCel138.setCellValue("是否完成总体规划");
        tCel139.setCellValue("是否完成连线连片规划");
        tCel140.setCellValue("是否完成村庄深度规划设计");
        
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 10, 10));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 11, 11));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 12, 12));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 13, 13));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 14, 14));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 15, 15));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 16, 16));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 17, 17));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 18, 18));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 19, 19));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 20, 20));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 21, 25));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 26, 30));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 23, 23));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 31, 31));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 32, 32));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 33, 33));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 34, 34));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 35, 35));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 36, 36));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 37, 37));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 38, 38));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 39, 39));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 40, 40));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 41, 41));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 42, 42));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 43, 43));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 44, 44));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 45, 45));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 46, 46));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 47, 47));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 48, 48));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 49, 49));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 50, 50));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 51, 51));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 52, 52));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 53, 53));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 54, 54));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 55, 55));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 56, 56));
        
      //第三行
        HSSFRow row2 = sheet.createRow(7);  
        HSSFCell sCell = row2.createCell(8);
        HSSFCell sCell1= row2.createCell(9);
        HSSFCell sCell2= row2.createCell(10);
        HSSFCell sCell3= row2.createCell(11);
        HSSFCell sCell4= row2.createCell(12);
        HSSFCell sCell5= row2.createCell(13);
        HSSFCell sCell6= row2.createCell(14);
        HSSFCell sCell7= row2.createCell(15);
        HSSFCell sCell8= row2.createCell(16);
        HSSFCell sCell9= row2.createCell(17);
        HSSFCell sCell10= row2.createCell(18);
        HSSFCell sCell11= row2.createCell(19);
        HSSFCell sCell12= row2.createCell(20);
        HSSFCell sCell13 = row2.createCell(21);
        HSSFCell sCell14 = row2.createCell(22);
        HSSFCell sCell15 = row2.createCell(23);
        HSSFCell sCell16 = row2.createCell(24);
        HSSFCell sCell17 = row2.createCell(25);
        HSSFCell sCell18 = row2.createCell(26);
        HSSFCell sCell19 = row2.createCell(27);
        HSSFCell sCell20 = row2.createCell(28);
        HSSFCell sCell21 = row2.createCell(29);
        HSSFCell sCell22 = row2.createCell(30);
        HSSFCell sCell23 = row2.createCell(31);
        HSSFCell sCell24 = row2.createCell(32);
        HSSFCell sCell25 = row2.createCell(33);
        HSSFCell sCell26 = row2.createCell(34);
        HSSFCell sCell27 = row2.createCell(35);
        HSSFCell sCell28 = row2.createCell(36);
        HSSFCell sCell29 = row2.createCell(37);
        HSSFCell sCell30 = row2.createCell(38);
        HSSFCell sCell31 = row2.createCell(39);
        HSSFCell sCell32 = row2.createCell(40);
        HSSFCell sCell33 = row2.createCell(41);
        HSSFCell sCell34 = row2.createCell(42);
        HSSFCell sCell35 = row2.createCell(43);
        HSSFCell sCell36 = row2.createCell(44);
        HSSFCell sCell37 = row2.createCell(45);
        HSSFCell sCell38 = row2.createCell(46);
        HSSFCell sCell39 = row2.createCell(47);
        HSSFCell sCell40 = row2.createCell(48);
        HSSFCell sCell41 = row2.createCell(49);
        HSSFCell sCell42 = row2.createCell(50);
        HSSFCell sCell43 = row2.createCell(51);
        HSSFCell sCell44 = row2.createCell(52);
        HSSFCell sCell45 = row2.createCell(53);
        HSSFCell sCell46 = row2.createCell(54);
        HSSFCell sCell47 = row2.createCell(55);
        HSSFCell sCell48 = row2.createCell(56);
        
        sCell.setCellStyle(style);
        sCell1.setCellStyle(style);
        sCell2.setCellStyle(style);
        sCell3.setCellStyle(style);
        sCell4.setCellStyle(style);
        sCell5.setCellStyle(style);
        sCell6.setCellStyle(style);
        sCell7.setCellStyle(style);
        sCell8.setCellStyle(style);
        sCell9.setCellStyle(style);
        sCell10.setCellStyle(style);
        sCell11.setCellStyle(style);
        sCell12.setCellStyle(style);
        sCell13.setCellStyle(style);
        sCell14.setCellStyle(style);
        sCell15.setCellStyle(style);
        sCell16.setCellStyle(style);
        sCell17.setCellStyle(style);
        sCell18.setCellStyle(style);
        sCell19.setCellStyle(style);
        sCell20.setCellStyle(style);
        sCell21.setCellStyle(style);
        sCell22.setCellStyle(style);
        sCell23.setCellStyle(style);
        sCell24.setCellStyle(style);
        sCell25.setCellStyle(style);
        sCell26.setCellStyle(style);
        sCell27.setCellStyle(style);
        sCell28.setCellStyle(style);
        sCell29.setCellStyle(style);
        sCell30.setCellStyle(style);
        sCell31.setCellStyle(style);
        sCell32.setCellStyle(style);
        sCell33.setCellStyle(style);
        sCell34.setCellStyle(style);
        sCell35.setCellStyle(style);
        sCell36.setCellStyle(style);
        sCell37.setCellStyle(style);
        sCell38.setCellStyle(style);
        sCell39.setCellStyle(style);
        sCell40.setCellStyle(style);
        sCell41.setCellStyle(style);
        sCell42.setCellStyle(style);
        sCell43.setCellStyle(style);
        sCell44.setCellStyle(style);
        sCell45.setCellStyle(style);
        sCell46.setCellStyle(style);
        sCell47.setCellStyle(style);
        sCell48.setCellStyle(style);
        
      //第三行标题
        sCell.setCellValue("B4[个]");
        sCell1.setCellValue("B5[亩]");
        sCell2.setCellValue("B6[亩]");
        sCell3.setCellValue("B7[户]");
        sCell4.setCellValue("B8[人]");
        sCell5.setCellValue("B9[人]");
        sCell6.setCellValue("[户]");
        sCell7.setCellValue("[个]");
        sCell8.setCellValue("[户]");
        sCell9.setCellValue("[人]");
        sCell10.setCellValue("[户]");
        sCell11.setCellValue("[户]");
        sCell12.setCellValue("B10[个]");
        sCell13.setCellValue("2013年");
        sCell14.setCellValue("2014年");
        sCell15.setCellValue("2015年");
        sCell16.setCellValue("2016年");
        sCell17.setCellValue("2017年");
        sCell18.setCellValue("2013年");
        sCell19.setCellValue("2014年");
        sCell20.setCellValue("2015年");
        sCell21.setCellValue("2016年");
        sCell22.setCellValue("2017年");
        sCell23.setCellValue("B31");
        sCell24.setCellValue("B32");
        sCell25.setCellValue("B33");
        sCell26.setCellValue("B34");
        sCell27.setCellValue("B35");
        sCell28.setCellValue("B36");
        sCell29.setCellValue("B37");
        sCell30.setCellValue("B38[个]");
        sCell31.setCellValue("B39[个]");
        sCell32.setCellValue("B39[平方米]");
        sCell33.setCellValue("B40[个]");
        sCell34.setCellValue("B40[平方米]");
        sCell35.setCellValue("B41[个]");
        sCell36.setCellValue("B41[平方米]");
        sCell37.setCellValue("B42[个]");
        sCell38.setCellValue("B42[平方米]");
        sCell39.setCellValue("B43[个]");
        sCell40.setCellValue("B43[平方米]");
        sCell41.setCellValue("B44[个]");
        sCell42.setCellValue("B45");
        sCell43.setCellValue("B46");
        sCell44.setCellValue("B47[个]");
        sCell45.setCellValue("B48");
        sCell46.setCellValue("B49");
        sCell47.setCellValue("B50");
        sCell48.setCellValue("B51");
        
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 10, 10));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 11, 11));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 12, 12));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 13, 13));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 14, 14));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 15, 15));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 16, 16));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 17, 17));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 18, 18));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 19, 19));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 20, 20));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 21, 21));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 22, 22));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 23, 23));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 24, 24));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 25, 25));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 26, 26));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 27, 27));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 28, 28));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 29, 29));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 30, 30));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 31, 31));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 32, 32));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 33, 33));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 34, 34));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 35, 35));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 36, 36));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 37, 37));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 38, 38));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 39, 39));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 40, 40));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 41, 41));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 42, 42));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 43, 43));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 44, 44));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 45, 45));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 46, 46));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 47, 47));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 48, 48));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 49, 49));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 50, 50));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 51, 51));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 52, 52));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 53, 53));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 54, 54));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 55, 55));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 56, 56));
        
        statistics(items, mStyle, style, sheet);
        workbook.write(response.getOutputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void statistics(Collection<Object[]> items, HSSFCellStyle mStyle, HSSFCellStyle style, HSSFSheet sheet) {

        //显示items里面的数据
        int i=8, j = 1, k = 0;
        
        for(Object[] it : items){
        	k = 0;
            HSSFRow mRow=sheet.createRow(i++); //总行数
            HSSFCell mCell = mRow.createCell(k++); //第一列
            mCell.setCellValue(j++);
            mCell.setCellStyle(mStyle);
        	for(Object obj : it){
        		  mCell = mRow.createCell(k++);
        		  if(null != obj){
        		    mCell.setCellValue(obj.toString());
        		    mCell.setCellStyle(mStyle);
        		  }
            }
        	
        	  
        }
        	}

	protected abstract Collection<Object[]> getItems(IOperator opt, String batch);
	protected StringBuilder statistics(Collection<Object[]> items){
		StringBuilder statis = new StringBuilder();
		int i = 1;
		for(Object[] it : items){
			statis.append("<tr><td>").append(i++).append("</td>");//序号
			for(Object obj : it){
				if(obj!=null){					
					statis.append("<td>").append(obj).append("</td>");
				}
				else{
					statis.append("<td></td>");
				}
			}
			statis.append("</tr>");
		}
		return statis;
	}

}
