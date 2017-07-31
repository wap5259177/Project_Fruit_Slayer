package cn.bonoon.controllers.statistics;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.web.models.PanelModel;

import static cn.bonoon.util.DoubleHelper.add;

@Controller
@RequestMapping("s/pls/spr")
public class ProvinceStatisticsPeripheralRuralController extends StatisticsPeripheralRuralController{
//
//	@Autowired
//	protected PeripheraRuralService statisticsService;
//	@Override
//	protected void onLoad(PanelEvent event) throws Exception {
//		// TODO Auto-generated method stub
//
//		event.setVmPath("statistics/peripheral-rural");
//		List<Object[]> items = statisticsService.statistics(null);
//		event.getModel().addObject("items", items);
//	}
//
//	@RequestMapping(value = "!{key}/index.batch", method = POST)
//	public String loadItems(Model model, String batch){
//		List<Object[]> items = statisticsService.statistics(batch);
//		model.addAttribute("layout", "layout-empty.vm").addAttribute("items", items);
//		return "statistics/area-residential-items";
//	}

	@Override
	protected Collection<Object[]> getItems(IOperator opt, String batch) {
		return statisticsService.statistics(batch);
	}
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
//		// TODO Auto-generated method stub
//		StringBuilder html = new StringBuilder();
//		PanelModel model = event.getModel();
//		String batch = model.getParameter("batch");
//		html.append("<div style='float:left'><select id='sel-batch'><option value='一' "+_check("一", batch) +">第一批</option><option value='二' "+_check("二", batch) +">第二批</option><option value='三' "+_check("三", batch) +">第三批</option></select></div>" +
//				"<div style='float:left'><a id='btn_load' href='' style='padding-left:10px;' onclick=\"this.href='?batch='+jQuery('#sel-batch').val()\">统计</a></div>");
//		event.getModel().addObject("select", html);
//		super.onLoad(event);

		PanelModel model = event.getModel();
		String batch = model.getParameter("batch");
		
		model.addObject("select", BatchHelper.batchSelect(batch));
		super.onLoad(event);
	}
//	protected String _check(String val, String batch){
//		if(null != batch){
//			if(batch.equals(val))
//				return "selected='selected'";
//		}
//		return "";
//	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "!{key}/index.excel", method = GET)
	public void excel(HttpServletRequest request, HttpServletResponse response, String batch){
		HSSFWorkbook workbook = null;
		try{
		String _name = "非主体村情况汇总统计.xls";
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
		Collection<Object[]> items = getItems(getUser(), batch);
		 // 声明一个工作薄  
        workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet("非主体村情况汇总统计");  
        
        sheet.setDefaultColumnWidth((short) 15);  
        sheet.setDefaultRowHeight((short)(15.625*25));
        sheet.setColumnWidth(5, 5000); 
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(44, 6000);
        
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
        cel.setCellValue("广东省新农村连片示范建设工程台账簿 - 所有辐射带动村情况累计汇总统计");
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 47));
        
        cel.setCellStyle(style1); 
        
        //第一行
        HSSFRow row = sheet.createRow(3);  
        HSSFCell cell = row.createCell(0);
        HSSFCell cell1 = row.createCell(1);
        HSSFCell cell2 = row.createCell(2);
        HSSFCell cell3 = row.createCell(3);
        HSSFCell cell4 = row.createCell(4);
        HSSFCell cell5 = row.createCell(5);
        HSSFCell cell6 = row.createCell(6);
        HSSFCell cell7 = row.createCell(7);
        HSSFCell cell8 = row.createCell(16);
        HSSFCell cell9 = row.createCell(27);
        HSSFCell cell10 = row.createCell(33);
        HSSFCell cell11 = row.createCell(44);
        HSSFCell cell12 = row.createCell(45);
        
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
        
      //第一行标题
        cell.setCellValue("序号");
        cell1.setCellValue("批次[A15]");
        cell2.setCellValue("报备年度[A14]");
        cell3.setCellValue("地市");
        cell4.setCellValue("县(区)");
        cell5.setCellValue("示范片名称[A12]");
        cell6.setCellValue("建设主题名称[A13]");
        cell7.setCellValue("（一）基本情况");
        cell8.setCellValue("（二）基础设施建设和环境卫生整治情况");
        cell9.setCellValue("（三）农村旧房整治情况");
        cell10.setCellValue("（四）农村公共服务情况");
        cell11.setCellValue("（五）规划进展");
        cell12.setCellValue("（六）村民理事会");
        
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 5, 5));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 6, 6));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 7, 15));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 16, 26));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 27, 32));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 33, 43));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 44, 44));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 45, 47));
        
        //第二行
        HSSFRow row1 = sheet.createRow(4);  
        HSSFCell tCell = row1.createCell(7);
        HSSFCell tCel11= row1.createCell(8);
        HSSFCell tCel12= row1.createCell(9);
        HSSFCell tCel13= row1.createCell(10);
        HSSFCell tCel14= row1.createCell(11);
        HSSFCell tCel15= row1.createCell(12);
        HSSFCell tCel16= row1.createCell(13);
        HSSFCell tCel17= row1.createCell(14);
        HSSFCell tCel18= row1.createCell(15);
        HSSFCell tCel19= row1.createCell(16);
        HSSFCell tCel110= row1.createCell(17);
        HSSFCell tCel111= row1.createCell(18);
        HSSFCell tCel112= row1.createCell(19);
        HSSFCell tCel113= row1.createCell(20);
        HSSFCell tCel114= row1.createCell(21);
        HSSFCell tCel115= row1.createCell(22);
        HSSFCell tCel116= row1.createCell(23);
        HSSFCell tCel117= row1.createCell(24);
        HSSFCell tCel118= row1.createCell(25);
        HSSFCell tCel119= row1.createCell(26);
        HSSFCell tCel120= row1.createCell(27);
        HSSFCell tCel121= row1.createCell(28);
        HSSFCell tCel122= row1.createCell(29);
        HSSFCell tCel123= row1.createCell(30);
        HSSFCell tCel124= row1.createCell(31);
        HSSFCell tCel125= row1.createCell(32);
        HSSFCell tCel126= row1.createCell(33);
        HSSFCell tCel127= row1.createCell(34);
        HSSFCell tCel128= row1.createCell(35);
        HSSFCell tCel129= row1.createCell(36);
        HSSFCell tCel130= row1.createCell(37);
        HSSFCell tCel131= row1.createCell(38);
        HSSFCell tCel132= row1.createCell(39);
        HSSFCell tCel133= row1.createCell(40);
        HSSFCell tCel134= row1.createCell(41);
        HSSFCell tCel135= row1.createCell(42);
        HSSFCell tCel136= row1.createCell(43);
        HSSFCell tCel137= row1.createCell(44);
        HSSFCell tCel138= row1.createCell(45);
        HSSFCell tCel139= row1.createCell(46);
        HSSFCell tCel140= row1.createCell(47);
        
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
        tCell.setCellValue("总面积");
        tCel11.setCellValue("耕地面积");
        tCel12.setCellValue("户数");
        tCel13.setCellValue("人口数");
        tCel14.setCellValue("劳动力总人数");
        tCel15.setCellValue("省级扶贫开发重点村（贫困村）个数");
        tCel16.setCellValue("广东名村个数");
        tCel17.setCellValue("“两不具备”整村推进村个数");
        tCel18.setCellValue("上年度农民年人均纯收入");
        tCel19.setCellValue("村内道路和入户路硬底化率");
        tCel110.setCellValue("通自来水个数");
        tCel111.setCellValue("开展农田水利基础设施和现代渔港建设");
        tCel112.setCellValue("整治小山塘、小灌区、小水坡、小泵站和小堤防");
        tCel113.setCellValue("配套建设高标准基本农田、标准鱼塘");
        tCel114.setCellValue("已完成环境卫生整治个数");
        tCel115.setCellValue("已开展村庄垃圾治理个数");
        tCel116.setCellValue("建立村保洁队伍村个数");
        tCel117.setCellValue("建立村保洁队伍保洁员人数");
        tCel118.setCellValue("村民使用卫生厕所户数");
        tCel119.setCellValue("建立污水处理设施村个数");
        tCel120.setCellValue("无价值旧村旧房拆除");
        tCel121.setCellValue("统一拆旧建新");
        tCel122.setCellValue("编制旧房整治改造规划村个数");
        tCel123.setCellValue("提供民居住宅设计标准图村个数");
        tCel124.setCellValue("农家（乡村）旅馆-规划建设");
        tCel125.setCellValue("农家（乡村）旅馆-完成建设");
        tCel126.setCellValue("文化活动场所");
        tCel127.setCellValue("文化活动场所");
        tCel128.setCellValue("乡村公园");
        tCel129.setCellValue("乡村公园");
        tCel130.setCellValue("文体广场");
        tCel131.setCellValue("文体广场");
        tCel132.setCellValue("村级卫生站");
        tCel133.setCellValue("村级卫生站");
        tCel134.setCellValue("乡村公厕");
        tCel135.setCellValue("乡村公厕");
        tCel136.setCellValue("建立统一的村级公共服务管理平台村个数");
        tCel137.setCellValue("编制村庄环境整治规划的村个数");
        tCel138.setCellValue("建立村民理事会村个数");
        tCel139.setCellValue("理事会成员数");
        tCel140.setCellValue("制定村规民约和章程村个数");
        
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 7, 7));
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
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 21, 21));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 22, 22));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 23, 23));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 24, 24));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 25, 25));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 26, 26));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 27, 27));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 28, 28));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 29, 29));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 30, 30));
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
        
        //第三行
        //第三行
        HSSFRow row2 = sheet.createRow(7);  
        HSSFCell sCell = row2.createCell(7);
        HSSFCell sCell1 = row2.createCell(8);
        HSSFCell sCell2 = row2.createCell(9);
        HSSFCell sCell3 = row2.createCell(10);
        HSSFCell sCell4 = row2.createCell(11);
        HSSFCell sCell5 = row2.createCell(12);
        HSSFCell sCell6 = row2.createCell(13);
        HSSFCell sCell7 = row2.createCell(14);
        HSSFCell sCell8 = row2.createCell(15);
        HSSFCell sCell9 = row2.createCell(16);
        HSSFCell sCell10 = row2.createCell(17);
        HSSFCell sCell11 = row2.createCell(18);
        HSSFCell sCell12 = row2.createCell(19);
        HSSFCell sCell13 = row2.createCell(20);
        HSSFCell sCell14 = row2.createCell(21);
        HSSFCell sCell15 = row2.createCell(22);
        HSSFCell sCell16 = row2.createCell(23);
        HSSFCell sCell17 = row2.createCell(24);
        HSSFCell sCell18 = row2.createCell(25);
        HSSFCell sCell19 = row2.createCell(26);
        HSSFCell sCell20 = row2.createCell(27);
        HSSFCell sCell21 = row2.createCell(28);
        HSSFCell sCell22 = row2.createCell(29);
        HSSFCell sCell23 = row2.createCell(30);
        HSSFCell sCell24 = row2.createCell(31);
        HSSFCell sCell25 = row2.createCell(32);
        HSSFCell sCell26 = row2.createCell(33);
        HSSFCell sCell27 = row2.createCell(34);
        HSSFCell sCell28 = row2.createCell(35);
        HSSFCell sCell29 = row2.createCell(36);
        HSSFCell sCell30 = row2.createCell(37);
        HSSFCell sCell31 = row2.createCell(38);
        HSSFCell sCell32 = row2.createCell(39);
        HSSFCell sCell33 = row2.createCell(40);
        HSSFCell sCell34 = row2.createCell(41);
        HSSFCell sCell35 = row2.createCell(42);
        HSSFCell sCell36 = row2.createCell(43);
        HSSFCell sCell37 = row2.createCell(44);
        HSSFCell sCell38 = row2.createCell(45);
        HSSFCell sCell39 = row2.createCell(46);
        HSSFCell sCell40 = row2.createCell(47);
        
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
        
      //第三行标题
        sCell.setCellValue("C6[亩]");
        sCell1.setCellValue("C7[亩]");
        sCell2.setCellValue("C8[户]");
        sCell3.setCellValue("C9[人]");
        sCell4.setCellValue("C10[人]");
        sCell5.setCellValue("C11[个]");
        sCell6.setCellValue("C12[个]");
        sCell7.setCellValue("C13[个]");
        sCell8.setCellValue("C14[元]");
        sCell9.setCellValue("C15[公里]");
        sCell10.setCellValue("C16[个]");
        sCell11.setCellValue("C17[宗]");
        sCell12.setCellValue("C18[个]");
        sCell13.setCellValue("C19[亩]");
        sCell14.setCellValue("C20[个]");
        sCell15.setCellValue("C21[个]");
        sCell16.setCellValue("C22[个]");
        sCell17.setCellValue("C22[人]");
        sCell18.setCellValue("C23[户]");
        sCell19.setCellValue("C24[个]");
        sCell20.setCellValue("C25[间]");
        sCell21.setCellValue("C26[间]");
        sCell22.setCellValue("C27[个]");
        sCell23.setCellValue("C28[个]");
        sCell24.setCellValue("C29[间]");
        sCell25.setCellValue("C29[间]");
        sCell26.setCellValue("C30[个]");
        sCell27.setCellValue("C30[平方米]");
        sCell28.setCellValue("C31[个]");
        sCell29.setCellValue("C31[平方米]");
        sCell30.setCellValue("C32[个]");
        sCell31.setCellValue("C32[平方米]");
        sCell32.setCellValue("C33[个]");
        sCell33.setCellValue("C33[平方米]");
        sCell34.setCellValue("C34[个]");
        sCell35.setCellValue("C34[平方米]");
        sCell36.setCellValue("C35[个]");
        sCell37.setCellValue("C36[个]");
        sCell38.setCellValue("C37[个]");
        sCell39.setCellValue("C38[人]");
        sCell40.setCellValue("C41[个]");
        
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 7, 7));
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
        
      //显示items里面的数据
        int i=8, j = 1, k = 0;
        double C6 = 0, C7 = 0,C14 = 0,C15 = 0,C19 = 0,x30 = 0,x31 = 0,x32 = 0,x33 = 0,x34 = 0;
		int C8= 0, C9 = 0,C10 = 0,C11 = 0,C12 = 0,C13 = 0,C16 = 0,C17 = 0;
		int C18= 0,C20 = 0,C21 = 0,C22 = 0,x22 = 0,C23 = 0,C24 = 0,C25 = 0,C26 = 0,C27 = 0;
		int C28= 0, C29 = 0,x29=0,C30 = 0,C31 = 0,C32 = 0,C33 = 0,C34 = 0,C35 = 0,C36 = 0,C37=0,C38=0,C41=0;
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
        	
        	C6  = add(C6,it[6]);
			C7  = add(C7,it[7]);
			
			if(it[8] instanceof Number){
				C8 += ((Number)it[8]).intValue();
			}
			if(it[9] instanceof Number){
				C9 += ((Number)it[9]).intValue();
			}
			if(it[10] instanceof Number){
				C10 += ((Number)it[10]).intValue();
			}
			if(it[11] instanceof Number){
				C11 += ((Number)it[11]).intValue();
			}
			if(it[12] instanceof Number){
				C12 += ((Number)it[12]).intValue();
			}
			if(it[13] instanceof Number){
				C13 += ((Number)it[13]).intValue();
			}
//			if(it[14] instanceof Number){
//				C14 += ((Number)it[14]).doubleValue();
//			}
//			if(it[15] instanceof Number){
//				C15 += ((Number)it[15]).doubleValue();
//			}
			C14  = add(C14,it[14]);
			C15  = add(C15,it[15]);
			
			
			if(it[16] instanceof Number){
				C16 += ((Number)it[16]).intValue();
			}
			if(it[17] instanceof Number){
				C17 += ((Number)it[17]).intValue();
			}
			if(it[18] instanceof Number){
				C18 += ((Number)it[18]).intValue();
			}
//			if(it[19] instanceof Number){
//				C19 += ((Number)it[19]).doubleValue();
//			}
			C19  = add(C19,it[19]);
			
			if(it[20] instanceof Number){
				C20 += ((Number)it[20]).intValue();
			}
			if(it[21] instanceof Number){
				C21 += ((Number)it[21]).intValue();
			}
			if(it[22] instanceof Number){
				C22 += ((Number)it[22]).intValue();
			}
			if(it[23] instanceof Number){
				x22 += ((Number)it[23]).intValue();
			}
			if(it[24] instanceof Number){
				C23 += ((Number)it[24]).intValue();
			}
			if(it[25] instanceof Number){
				C24 += ((Number)it[25]).intValue();
			}
			if(it[26] instanceof Number){
				C25 += ((Number)it[26]).intValue();
			}
			if(it[27] instanceof Number){
				C26 += ((Number)it[27]).intValue();
			}
			if(it[28] instanceof Number){
				C27 += ((Number)it[28]).intValue();
			}
			if(it[29] instanceof Number){
				C28 += ((Number)it[29]).intValue();
			}
			if(it[30] instanceof Number){
				C29 += ((Number)it[30]).intValue();
			}
			if(it[31] instanceof Number){
				x29 += ((Number)it[31]).intValue();
			}
			if(it[32] instanceof Number){
				C30 += ((Number)it[32]).intValue();
			}
//			if(it[33] instanceof Number){
//				x30 += ((Number)it[33]).doubleValue();
//			}
			x30  = add(x30,it[33]);
			
			if(it[34] instanceof Number){
				C31 += ((Number)it[34]).intValue();
			}
//			if(it[35] instanceof Number){
//				x31 += ((Number)it[35]).doubleValue();
//			}
			x31  = add(x31,it[35]);
			
			if(it[36] instanceof Number){
				C32 += ((Number)it[36]).intValue();
			}
//			if(it[37] instanceof Number){
//				x32 += ((Number)it[37]).doubleValue();
//			}
			x32  = add(x32,it[37]);
			
			if(it[38] instanceof Number){
				C33 += ((Number)it[38]).intValue();
			}
//			if(it[39] instanceof Number){
//				x33 += ((Number)it[39]).doubleValue();
//			}
			
			x33  = add(x33,it[39]);
			
			if(it[40] instanceof Number){
				C34 += ((Number)it[40]).intValue();
			}
//			if(it[41] instanceof Number){
//				x34 += ((Number)it[41]).doubleValue();
//			}
			
			x34  = add(x34,it[41]);
			
			if(it[42] instanceof Number){
				C35 += ((Number)it[42]).intValue();
			}
			if(it[43] instanceof Number){
				C36 += ((Number)it[43]).intValue();
			}
			if(it[44] instanceof Number){
				C37 += ((Number)it[44]).intValue();
			}
			if(it[45] instanceof Number){
				C38 += ((Number)it[45]).intValue();
			}
			if(it[46] instanceof Number){
				C41 += ((Number)it[46]).intValue();
			}
        	
        	  
        }
        HSSFRow mRow1=sheet.createRow(i);
        HSSFCell mCell1 = mRow1.createCell(0);
        mCell1.setCellValue("全省汇总：");
        HSSFCell mCell2 = mRow1.createCell(7);
        mCell2.setCellValue(String.valueOf(C6));
        HSSFCell mCell3 = mRow1.createCell(8);
        mCell3.setCellValue(String.valueOf(C7));
        HSSFCell mCell4 = mRow1.createCell(9);
        mCell4.setCellValue(C8);
        HSSFCell mCell5 = mRow1.createCell(10);
        mCell5.setCellValue(C9);
        HSSFCell mCell6 = mRow1.createCell(11);
        mCell6.setCellValue(C10);
        HSSFCell mCell7 = mRow1.createCell(12);
        mCell7.setCellValue(C11);
        HSSFCell mCell8 = mRow1.createCell(13);
        mCell8.setCellValue(C12);
        HSSFCell mCell9 = mRow1.createCell(14);
        mCell9.setCellValue(C13);
        HSSFCell mCell10 = mRow1.createCell(15);
        mCell10.setCellValue(String.valueOf(C14));
        HSSFCell mCell11 = mRow1.createCell(16);
        mCell11.setCellValue(String.valueOf(C15));
        HSSFCell mCell12 = mRow1.createCell(17);
        mCell12.setCellValue(C16);
        HSSFCell mCell13 = mRow1.createCell(18);
        mCell13.setCellValue(C17);
        HSSFCell mCell14 = mRow1.createCell(19);
        mCell14.setCellValue(C18);
        HSSFCell mCell15 = mRow1.createCell(20);
        mCell15.setCellValue(String.valueOf(C19));
        HSSFCell mCell16 = mRow1.createCell(21);
        mCell16.setCellValue(C20);
        HSSFCell mCell17 = mRow1.createCell(22);
        mCell17.setCellValue(C21);
        HSSFCell mCell18 = mRow1.createCell(23);
        mCell18.setCellValue(C22);
        HSSFCell mCell19 = mRow1.createCell(24);
        mCell19.setCellValue(x22);
        HSSFCell mCell20 = mRow1.createCell(25);
        mCell20.setCellValue(C23);
        HSSFCell mCell21 = mRow1.createCell(26);
        mCell21.setCellValue(C24);
        HSSFCell mCell22 = mRow1.createCell(27);
        mCell22.setCellValue(C25);
        HSSFCell mCell23 = mRow1.createCell(28);
        mCell23.setCellValue(C26);
        HSSFCell mCell24 = mRow1.createCell(29);
        mCell24.setCellValue(C27);
        HSSFCell mCell25 = mRow1.createCell(30);
        mCell25.setCellValue(C28);
        HSSFCell mCell26 = mRow1.createCell(31);
        mCell26.setCellValue(C29);
        HSSFCell mCell27 = mRow1.createCell(32);
        mCell27.setCellValue(x29);
        HSSFCell mCell28 = mRow1.createCell(33);
        mCell28.setCellValue(C30);
        HSSFCell mCell29 = mRow1.createCell(34);
        mCell29.setCellValue(String.valueOf(x30));
        HSSFCell mCell30 = mRow1.createCell(35);
        mCell30.setCellValue(C31);
        HSSFCell mCell31 = mRow1.createCell(36);
        mCell31.setCellValue(String.valueOf(x31));
        HSSFCell mCell32 = mRow1.createCell(37);
        mCell32.setCellValue(C32);
        HSSFCell mCell33 = mRow1.createCell(38);
        mCell33.setCellValue(String.valueOf(x32));
        HSSFCell mCell34 = mRow1.createCell(39);
        mCell34.setCellValue(C33);
        HSSFCell mCell35 = mRow1.createCell(40);
        mCell35.setCellValue(String.valueOf(x33));
        HSSFCell mCell36 = mRow1.createCell(41);
        mCell36.setCellValue(C34);
        HSSFCell mCell37 = mRow1.createCell(42);
        mCell37.setCellValue(String.valueOf(x34));
        HSSFCell mCell38 = mRow1.createCell(43);
        mCell38.setCellValue(C35);
        HSSFCell mCell39 = mRow1.createCell(44);
        mCell39.setCellValue(C36);
        HSSFCell mCell40 = mRow1.createCell(45);
        mCell40.setCellValue(C37);
        HSSFCell mCell41 = mRow1.createCell(46);
        mCell41.setCellValue(C38);
        HSSFCell mCell42 = mRow1.createCell(47);
        mCell42.setCellValue(C41);
        
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
        
        workbook.write(response.getOutputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	protected StringBuilder statistics(Collection<Object[]> items){
		StringBuilder statis = new StringBuilder();
		int i = 1;
		double C6 = 0, C7 = 0,C14 = 0,C15 = 0,C19 = 0,x30 = 0,x31 = 0,x32 = 0,x33 = 0,x34 = 0;
		int C8= 0, C9 = 0,C10 = 0,C11 = 0,C12 = 0,C13 = 0,C16 = 0,C17 = 0;
		int C18= 0,C20 = 0,C21 = 0,C22 = 0,x22 = 0,C23 = 0,C24 = 0,C25 = 0,C26 = 0,C27 = 0;
		int C28= 0, C29 = 0,x29=0,C30 = 0,C31 = 0,C32 = 0,C33 = 0,C34 = 0,C35 = 0,C36 = 0,C37=0,C38=0,C41=0;
		for(Object[] it : items){
			statis.append("<tr><td>").append(i++).append("</td>");
			for(Object obj : it){
				statis.append("<td>");
				if(null != obj) statis.append(obj);
				statis.append("</td>");
			}
//			if(it[6] instanceof Number){
//				C6 += ((Number)it[6]).doubleValue();
//			}
//			if(it[7] instanceof Number){
//				C7 += ((Number)it[7]).doubleValue();
//			}
			
			C6  = add(C6,it[8]);
			C7  = add(C7,it[9]);
			
			if(it[10] instanceof Number){
				C8 += ((Number)it[10]).intValue();
			}
			if(it[11] instanceof Number){
				C9 += ((Number)it[11]).intValue();
			}
			if(it[12] instanceof Number){
				C10 += ((Number)it[12]).intValue();
			}
			if(it[13] instanceof Number){
				C11 += ((Number)it[13]).intValue();
			}
			if(it[14] instanceof Number){
				C12 += ((Number)it[14]).intValue();
			}
			if(it[15] instanceof Number){
				C13 += ((Number)it[15]).intValue();
			}
//			if(it[14] instanceof Number){
//				C14 += ((Number)it[14]).doubleValue();
//			}
//			if(it[15] instanceof Number){
//				C15 += ((Number)it[15]).doubleValue();
//			}
			C14  = add(C14,it[16]);
			C15  = add(C15,it[17]);
			
			
			if(it[18] instanceof Number){
				C16 += ((Number)it[18]).intValue();
			}
			if(it[19] instanceof Number){
				C17 += ((Number)it[19]).intValue();
			}
			if(it[20] instanceof Number){
				C18 += ((Number)it[20]).intValue();
			}
//			if(it[19] instanceof Number){
//				C19 += ((Number)it[19]).doubleValue();
//			}
			C19  = add(C19,it[21]);
			
			if(it[22] instanceof Number){
				C20 += ((Number)it[22]).intValue();
			}
			if(it[23] instanceof Number){
				C21 += ((Number)it[23]).intValue();
			}
			if(it[24] instanceof Number){
				C22 += ((Number)it[24]).intValue();
			}
			if(it[25] instanceof Number){
				x22 += ((Number)it[25]).intValue();
			}
			if(it[26] instanceof Number){
				C23 += ((Number)it[26]).intValue();
			}
			if(it[27] instanceof Number){
				C24 += ((Number)it[27]).intValue();
			}
			if(it[28] instanceof Number){
				C25 += ((Number)it[28]).intValue();
			}
			if(it[29] instanceof Number){
				C26 += ((Number)it[29]).intValue();
			}
			if(it[30] instanceof Number){
				C27 += ((Number)it[30]).intValue();
			}
			if(it[31] instanceof Number){
				C28 += ((Number)it[31]).intValue();
			}
			if(it[32] instanceof Number){
				C29 += ((Number)it[32]).intValue();
			}
			if(it[33] instanceof Number){
				x29 += ((Number)it[33]).intValue();
			}
			if(it[34] instanceof Number){
				C30 += ((Number)it[34]).intValue();
			}
//			if(it[33] instanceof Number){
//				x30 += ((Number)it[33]).doubleValue();
//			}
			x30  = add(x30,it[35]);
			
			if(it[36] instanceof Number){
				C31 += ((Number)it[36]).intValue();
			}
//			if(it[35] instanceof Number){
//				x31 += ((Number)it[35]).doubleValue();
//			}
			x31  = add(x31,it[37]);
			
			if(it[38] instanceof Number){
				C32 += ((Number)it[38]).intValue();
			}
//			if(it[37] instanceof Number){
//				x32 += ((Number)it[37]).doubleValue();
//			}
			x32  = add(x32,it[39]);
			
			if(it[40] instanceof Number){
				C33 += ((Number)it[40]).intValue();
			}
//			if(it[39] instanceof Number){
//				x33 += ((Number)it[39]).doubleValue();
//			}
			
			x33  = add(x33,it[41]);
			
			if(it[42] instanceof Number){
				C34 += ((Number)it[42]).intValue();
			}
//			if(it[41] instanceof Number){
//				x34 += ((Number)it[41]).doubleValue();
//			}
			
			x34  = add(x34,it[43]);
			
			if(it[44] instanceof Number){
				C35 += ((Number)it[44]).intValue();
			}
			if(it[45] instanceof Number){
				C36 += ((Number)it[45]).intValue();
			}
			if(it[46] instanceof Number){
				C37 += ((Number)it[46]).intValue();
			}
			if(it[47] instanceof Number){
				C38 += ((Number)it[47]).intValue();
			}
			if(it[48] instanceof Number){
				C41 += ((Number)it[48]).intValue();
			}
			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td></td><td></td><td>");
		statis.append(C6).append("</td><td>");
		statis.append(C7).append("</td><td>");
		statis.append(C8).append("</td><td>");
		statis.append(C9).append("</td><td>");
		statis.append(C10).append("</td><td>");
		statis.append(C11).append("</td><td>");
		statis.append(C12).append("</td><td>");
		statis.append(C13).append("</td><td>");
		statis.append(C14).append("</td><td>");
		statis.append(C15).append("</td><td>");
		statis.append(C16).append("</td><td>");
		statis.append(C17).append("</td><td>");
		statis.append(C18).append("</td><td>");
		statis.append(C19).append("</td><td>");
		statis.append(C20).append("</td><td>");
		statis.append(C21).append("</td><td>");
		statis.append(C22).append("</td><td>");
		statis.append(x22).append("</td><td>");
		statis.append(C23).append("</td><td>");
		statis.append(C24).append("</td><td>");
		statis.append(C25).append("</td><td>");
		statis.append(C26).append("</td><td>");
		statis.append(C27).append("</td><td>");
		statis.append(C28).append("</td><td>");
		statis.append(C29).append("</td><td>");
		statis.append(x29).append("</td><td>");
		statis.append(C30).append("</td><td>");
		statis.append(x30).append("</td><td>");
		statis.append(C31).append("</td><td>");
		statis.append(x31).append("</td><td>");
		statis.append(C32).append("</td><td>");
		statis.append(x32).append("</td><td>");
		statis.append(C33).append("</td><td>");
		statis.append(x33).append("</td><td>");
		statis.append(C34).append("</td><td>");
		statis.append(x34).append("</td><td>");
		statis.append(C35).append("</td><td>");
		statis.append(C36).append("</td><td>");
		statis.append(C37).append("</td><td>");
		statis.append(C38).append("</td><td>");
		statis.append(C41).append("</td></tr>");
		return statis;
	}
}

