package cn.bonoon.controllers.showstatistics.administration;

import static cn.bonoon.util.DoubleHelper.add;

import java.util.Collection;
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
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class AdministrationExelView extends AbstractExcelView{

	private Collection<Object[]> items;
//	private String batch;
	@Autowired
	public AdministrationExelView(Collection<Object[]> items) {
//		this.batch=batch;
		this.items=items;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
	String _name = "主体行政村情况汇总统计.xls";
	response.setContentType("multipart/form-data");
	response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
//	Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
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

	private void statistics(Collection<Object[]> items2, HSSFCellStyle mStyle,
			HSSFCellStyle style, HSSFSheet sheet) {

        //显示items里面的数据
		int i=8, j = 1, k = 0;
		double B5 = 0, B6 = 0,x20 = 0,x21 = 0,x22 = 0,x23 = 0,x24 = 0,x25 = 0,x26 = 0,x27 = 0,x28 = 0,x29 = 0;
		int  B4 = 0,B7 = 0,B8 = 0,B9 = 0,B10 = 0,B11 = 0,B12 = 0,B13 = 0,B14 = 0,B15 = 0,B38 = 0,B461 = 0;
		int B39 = 0,B40 = 0,B41 = 0,B42 = 0,B43 = 0;
		double B391 = 0,B401 = 0,B411 = 0,B421 = 0,B431 = 0;
		int B16 = 0,B31 = 0,B32 = 0,B33 = 0,B34 = 0,B35 = 0,B36 = 0,B37 = 0,B44 = 0,B45 = 0;
		int  B46 = 0,B47 = 0,B48 = 0,B49 = 0,B50 = 0;
		
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
        	if(it[7] instanceof Number){
				B4 += ((Number)it[7]).intValue();
			}
//			if(it[8] instanceof Number){
//				B5 += ((Number)it[8]).doubleValue();
//			}
			
			B5 = add(B5,it[8]);
			
//			if(it[9] instanceof Number){
//				B6 += ((Number)it[9]).doubleValue();
//			}
			
			B6 = add(B6,it[9]);
			
			if(it[10] instanceof Number){
				B7 += ((Number)it[10]).intValue();
			}
			if(it[11] instanceof Number){
				B8 += ((Number)it[11]).intValue();
			}
			if(it[12] instanceof Number){
				B9 += ((Number)it[12]).intValue();
			}
			if(it[13] instanceof Number){
				B10 += ((Number)it[13]).intValue();
			}
			if(it[14] instanceof Number){
				B11 += ((Number)it[14]).intValue();
			}
			if(it[15] instanceof Number){
				B12 += ((Number)it[15]).intValue();
			}
			if(it[16] instanceof Number){
				B13 += ((Number)it[16]).intValue();
			}
			if(it[17] instanceof Number){
				B14 += ((Number)it[17]).intValue();
			}
			if(it[18] instanceof Number){
				B15 += ((Number)it[18]).intValue();
			}
			if("是".equals(it[19])){
				B16 += 1;
			}
			
//			if(it[20] instanceof Number){
//				x20 += ((Number)it[20]).doubleValue();
//			}
//			if(it[21] instanceof Number){
//				x21 += ((Number)it[21]).doubleValue();
//			}
//			if(it[22] instanceof Number){
//				x22 += ((Number)it[22]).doubleValue();
//			}
//			if(it[23] instanceof Number){
//				x23 += ((Number)it[23]).doubleValue();
//			}
//			if(it[24] instanceof Number){
//				x24 += ((Number)it[24]).doubleValue();
//			}
//			if(it[25] instanceof Number){
//				x25 += ((Number)it[25]).doubleValue();
//			}
//			if(it[26] instanceof Number){
//				x26 += ((Number)it[26]).doubleValue();
//			}
//			if(it[27] instanceof Number){
//				x27 += ((Number)it[27]).doubleValue();
//			}
//			if(it[28] instanceof Number){
//				x28 += ((Number)it[28]).doubleValue();
//			}
//			if(it[29] instanceof Number){
//				x29 += ((Number)it[29]).doubleValue();
//			}
			
			x20 = add(x20,it[20]);
			x21 = add(x21,it[21]);
			x22 = add(x22,it[22]);
			x23 = add(x23,it[23]);
			x24 = add(x24,it[24]);
			x25 = add(x25,it[25]);
			x26 = add(x26,it[26]);
			x27 = add(x27,it[27]);
			x28 = add(x28,it[28]);
			x29 = add(x29,it[29]);
			
			//资源优势
			if(it[30].equals("是")){
				B31 += 1;
			}
			if(it[31].equals("是")){
				B32 += 1;
			}
			if(it[32].equals("是")){
				B33 += 1;
			}
			if(it[33].equals("是")){
				B34 += 1;
			}
			if(it[34].equals("是")){
				B35 += 1;
			}
			if(it[35].equals("是")){
				B36 += 1;
			}
			if(it[36].equals("是")){
				B37 += 1;
			}
			if(it[37] instanceof Number){
				B38 += ((Number)it[37]).intValue();
			}
			if(it[38] instanceof Number){
				B39 += ((Number)it[38]).intValue();
			}
//			if(it[39] instanceof Number){
//				B391 += ((Number)it[39]).doubleValue();
//			}
			
			B391 = add(B391,it[39]);
			
			if(it[40] instanceof Number){
				B40 += ((Number)it[40]).intValue();
			}
//			if(it[41] instanceof Number){
//				B401 += ((Number)it[41]).doubleValue();
//			}
			
			B401 = add(B401,it[41]);
			
			if(it[42] instanceof Number){
				B41 += ((Number)it[42]).intValue();
			}
//			if(it[43] instanceof Number){
//				B411 += ((Number)it[43]).doubleValue();
//			}
			
			B411 = add(B411,it[43]);
			
			if(it[44] instanceof Number){
				B42 += ((Number)it[44]).intValue();
			}
//			if(it[45] instanceof Number){
//				B421 += ((Number)it[45]).doubleValue();
//			}
			
			B421 = add(B421,it[45]);
			
			if(it[46] instanceof Number){
				B43 += ((Number)it[46]).intValue();
			}
//			if(it[47] instanceof Number){
//				B431 += ((Number)it[47]).doubleValue();
//			}
			
			B431 = add(B431,it[47]);
			
//			if(it[48] instanceof Number){
//				B44 += ((Number)it[48]).doubleValue();
//			}
			if("是".equals(it[48])){
				B44 += 1;
			}
			//是否有挂点领导
			if("是".equals(it[49])){
				B45 += 1;
			}
			//是否建立工作小组
			if("是".equals(it[50])){
				B46 += 1;
			}
			//工作小组总人数
			if(it[51] instanceof Number){
				B461 += ((Number)it[51]).intValue();
			}
			//是否建立规划专家指导组
			if("是".equals(it[52])){
				B47 += 1;
			}
			if("是".equals(it[53])){
				B48 += 1;
			}
			if("是".equals(it[54])){
				B49 += 1;
			}
			if("是".equals(it[55])){
				B50 += 1;
			}
        	
        }
		HSSFRow mRow1=sheet.createRow(i);
        HSSFCell mCell1 = mRow1.createCell(0);
        mCell1.setCellValue("全省汇总：");
        HSSFCell mCell2 = mRow1.createCell(8);
        mCell2.setCellValue(B4);
        HSSFCell mCell3 = mRow1.createCell(9);
        mCell3.setCellValue(B5);
        HSSFCell mCell4 = mRow1.createCell(10);
        mCell4.setCellValue(B6);
        HSSFCell mCell5 = mRow1.createCell(11);
        mCell5.setCellValue(B7);
        HSSFCell mCell6 = mRow1.createCell(12);
        mCell6.setCellValue(B8);
        HSSFCell mCell7 = mRow1.createCell(13);
        mCell7.setCellValue(B9);
        HSSFCell mCell8 = mRow1.createCell(14);
        mCell8.setCellValue(B10);
        HSSFCell mCell9 = mRow1.createCell(15);
        mCell9.setCellValue(B11);
        HSSFCell mCell10 = mRow1.createCell(16);
        mCell10.setCellValue(B12);
        HSSFCell mCell11 = mRow1.createCell(17);
        mCell11.setCellValue(B13);
        HSSFCell mCell12 = mRow1.createCell(18);
        mCell12.setCellValue(B14);
        HSSFCell mCell13 = mRow1.createCell(19);
        mCell13.setCellValue(B15);
        HSSFCell mCell14 = mRow1.createCell(20);
        mCell14.setCellValue(B16);
        
        HSSFCell mCell15 = mRow1.createCell(21);
        mCell15.setCellValue(x20);
        HSSFCell mCell16 = mRow1.createCell(22);
        mCell16.setCellValue(x21);
        HSSFCell mCell17 = mRow1.createCell(23);
        mCell17.setCellValue(x22);
        HSSFCell mCell18 = mRow1.createCell(24);
        mCell18.setCellValue(x23);
        HSSFCell mCell19 = mRow1.createCell(25);
        mCell19.setCellValue(x24);
        HSSFCell mCell20 = mRow1.createCell(26);
        mCell20.setCellValue(x25);
        HSSFCell mCell21 = mRow1.createCell(27);
        mCell21.setCellValue(x26);
        HSSFCell mCell22 = mRow1.createCell(28);
        mCell22.setCellValue(x27);
        HSSFCell mCell23 = mRow1.createCell(29);
        mCell23.setCellValue(x28);
        HSSFCell mCell24 = mRow1.createCell(30);
        mCell24.setCellValue(x29);
        
        HSSFCell mCell25 = mRow1.createCell(31);
        mCell25.setCellValue(B31);
        HSSFCell mCell26 = mRow1.createCell(32);
        mCell26.setCellValue(B32);
        HSSFCell mCell27 = mRow1.createCell(33);
        mCell27.setCellValue(B33);
        HSSFCell mCell28 = mRow1.createCell(34);
        mCell28.setCellValue(B34);
        HSSFCell mCell29 = mRow1.createCell(35);
        mCell29.setCellValue(B35);
        HSSFCell mCell30 = mRow1.createCell(36);
        mCell30.setCellValue(B36);
        HSSFCell mCell31 = mRow1.createCell(37);
        mCell31.setCellValue(B37);
        HSSFCell mCell32 = mRow1.createCell(38);
        mCell32.setCellValue(B38);
        
        HSSFCell mCell33 = mRow1.createCell(39);
        mCell33.setCellValue(B39);
        HSSFCell mCell34 = mRow1.createCell(40);
        mCell34.setCellValue(B391);
        HSSFCell mCell35 = mRow1.createCell(41);
        mCell35.setCellValue(B40);
        HSSFCell mCell36 = mRow1.createCell(42);
        mCell36.setCellValue(B401);
        HSSFCell mCell37 = mRow1.createCell(43);
        mCell37.setCellValue(B41);
        HSSFCell mCell38 = mRow1.createCell(44);
        mCell38.setCellValue(B411);
        HSSFCell mCell39 = mRow1.createCell(45);
        mCell39.setCellValue(B42);
        HSSFCell mCell40 = mRow1.createCell(46);
        mCell40.setCellValue(B421);
        HSSFCell mCell41 = mRow1.createCell(47);
        mCell41.setCellValue(B43);
        HSSFCell mCell42 = mRow1.createCell(48);
        mCell42.setCellValue(B431);
        HSSFCell mCell43 = mRow1.createCell(49);
        mCell43.setCellValue(B44);
       
        HSSFCell mCell44 = mRow1.createCell(50);
        mCell44.setCellValue(B45);
        HSSFCell mCell45 = mRow1.createCell(51);
        mCell45.setCellValue(B46);
        HSSFCell mCell46 = mRow1.createCell(52);
        mCell46.setCellValue(B461);
        HSSFCell mCell47 = mRow1.createCell(53);
        mCell47.setCellValue(B47);
        HSSFCell mCell48 = mRow1.createCell(54);
        mCell48.setCellValue(B48);
        HSSFCell mCell49 = mRow1.createCell(55);
        mCell49.setCellValue(B49);
        HSSFCell mCell50 = mRow1.createCell(56);
        mCell50.setCellValue(B50);
        
        mCell1.setCellStyle(style);
        mCell2.setCellStyle(mStyle);
        mCell3.setCellStyle(mStyle);
        mCell4.setCellStyle(mStyle);
        mCell5.setCellStyle(mStyle);
        mCell6.setCellStyle(mStyle);
        mCell7.setCellStyle(mStyle);
        mCell8.setCellStyle(mStyle);
        mCell9.setCellStyle(mStyle);
        mCell10.setCellStyle(mStyle);
        mCell11.setCellStyle(mStyle);
        mCell12.setCellStyle(mStyle);
        mCell13.setCellStyle(mStyle);
        mCell14.setCellStyle(mStyle);
        mCell15.setCellStyle(mStyle);
        mCell16.setCellStyle(mStyle);
        mCell17.setCellStyle(mStyle);
        mCell18.setCellStyle(mStyle);
        mCell19.setCellStyle(mStyle);
        mCell20.setCellStyle(mStyle);
        mCell21.setCellStyle(mStyle);
        mCell22.setCellStyle(mStyle);
        mCell23.setCellStyle(mStyle);
        mCell24.setCellStyle(mStyle);
        mCell25.setCellStyle(mStyle);
        mCell26.setCellStyle(mStyle);
        mCell27.setCellStyle(mStyle);
        mCell28.setCellStyle(mStyle);
        mCell29.setCellStyle(mStyle);
        mCell30.setCellStyle(mStyle);
        mCell31.setCellStyle(mStyle);
        mCell32.setCellStyle(mStyle);
        mCell33.setCellStyle(mStyle);
        mCell34.setCellStyle(mStyle);
        mCell35.setCellStyle(mStyle);
        mCell36.setCellStyle(mStyle);
        mCell37.setCellStyle(mStyle);
        mCell38.setCellStyle(mStyle);
        mCell39.setCellStyle(mStyle);
        mCell40.setCellStyle(mStyle);
        mCell41.setCellStyle(mStyle);
        mCell42.setCellStyle(mStyle);
        mCell43.setCellStyle(mStyle);
        mCell44.setCellStyle(mStyle);
        mCell45.setCellStyle(mStyle);
        mCell46.setCellStyle(mStyle);
        mCell47.setCellStyle(mStyle);
        mCell48.setCellStyle(mStyle);
        mCell49.setCellStyle(mStyle);
        mCell50.setCellStyle(mStyle);
	}

}
