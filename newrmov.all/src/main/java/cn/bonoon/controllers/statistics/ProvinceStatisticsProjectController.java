package cn.bonoon.controllers.statistics;

import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.web.models.PanelModel;

import static cn.bonoon.util.DoubleHelper.add;
@Controller
@RequestMapping("s/pls/spj")
public class ProvinceStatisticsProjectController extends StatisticsProjectController{
//	@Autowired
//	protected ProjectService statisticsService;
//	@Override
//	protected void onLoad(PanelEvent event) throws Exception {
//		event.setVmPath("statistics/area-project");
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
//		StringBuilder html = new StringBuilder();
//		PanelModel model = event.getModel();
//		String batch = model.getParameter("batch");
//		html.append("<div style='float:left'><select id='sel-batch'><option value='一' "+_check("一", batch) +">第一批</option><option value='二' "+_check("二", batch) +">第二批</option><option value='三' "+_check("三", batch) +">第三批</option></select></div>" +
//				"<div style='float:left'><a id='btn_load' href='' style='padding-left:10px;' onclick=\"this.href='?batch='+jQuery('#sel-batch').val()\">统计</a></div>");
//		model.addObject("select", html);
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
	
//	@SuppressWarnings("deprecation")
//	@RequestMapping(value = "!{key}/index.excel", method = GET)
//	public void excel(HttpServletRequest request, HttpServletResponse response, String batch){
//		HSSFWorkbook workbook = null;
//		try{
//		String _name = "工程项目库汇总统计.xls";
//		response.setContentType("multipart/form-data");
//		response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
//		Collection<Object[]> items = getItems(getUser(), batch);
//		 // 声明一个工作薄  
//        workbook = new HSSFWorkbook();  
//        // 生成一个表格  
//        HSSFSheet sheet = workbook.createSheet("工程项目库汇总统计");  
//        
//        sheet.setDefaultColumnWidth((short) 15);  
//        sheet.setDefaultRowHeight((short)(15.625*25));
//        sheet.setColumnWidth(5, 5000); 
//        sheet.setColumnWidth(6, 5000);
//        
//     // 生成一个样式  
//        HSSFCellStyle style1 = workbook.createCellStyle();  
//        // 设置这些样式  
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
//        // 生成一个字体  
//        HSSFFont font1 = workbook.createFont();  
//        font1.setColor(HSSFColor.BLACK.index);  
//        font1.setFontHeightInPoints((short) 20);  
//        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
//        
//        // 把字体应用到当前的样式  
//        style1.setFont(font1); 
//        
//     // 生成一个样式  
//        HSSFCellStyle style2 = workbook.createCellStyle();  
//        // 设置这些样式  
//        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
//        // 生成一个字体  
//        HSSFFont font2 = workbook.createFont();  
//        font2.setColor(HSSFColor.BLACK.index);  
//        font2.setFontHeightInPoints((short) 12);  
//        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
//        // 把字体应用到当前的样式  
//        style2.setFont(font2); 
//        
//        // 生成一个样式  
//        HSSFCellStyle style = workbook.createCellStyle();  
//        // 设置这些样式  
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
//        style.setWrapText(true);
//        // 生成一个字体  
//        HSSFFont font = workbook.createFont();  
//        font.setColor(HSSFColor.BLACK.index);  
//        font.setFontHeightInPoints((short) 11); 
//        
//        // 把字体应用到当前的样式  
//        style.setFont(font); 
//        
//        // 生成一个样式  
//        HSSFCellStyle mStyle = workbook.createCellStyle();  
//        // 设置这些样式  
//        mStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
//        mStyle.setWrapText(true);
//        // 生成一个字体  
//        HSSFFont mFont = workbook.createFont();  
//        mFont.setColor(HSSFColor.BLACK.index);  
//        mFont.setFontHeightInPoints((short) 10); 
//        
//        // 把字体应用到当前的样式  
//        mStyle.setFont(mFont); 
//        
//        //标题栏
//        HSSFRow ro = sheet.createRow(0);  
//        HSSFCell cel = ro.createCell(0);
//        cel.setCellValue("广东省新农村连片示范建设工程台账簿 - 所有工程项目情况累计汇总统计");
//        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 37));
//        
//        cel.setCellStyle(style1); 
//        
//        //第一行
//        HSSFRow row = sheet.createRow(3);  
//        HSSFCell cell = row.createCell(0);
//        HSSFCell cell1 = row.createCell(1);
//        HSSFCell cell2 = row.createCell(2);
//        HSSFCell cell3 = row.createCell(3);
//        HSSFCell cell4 = row.createCell(4);
//        HSSFCell cell5 = row.createCell(5);
//        HSSFCell cell6 = row.createCell(6);
//        HSSFCell cell7 = row.createCell(10);
//        HSSFCell cell8 = row.createCell(13);
//        HSSFCell cell9 = row.createCell(22);
//        HSSFCell cell10 = row.createCell(29);
//        HSSFCell cell11 = row.createCell(36);
//        
//        cell.setCellStyle(style); 
//        cell1.setCellStyle(style); 
//        cell2.setCellStyle(style); 
//        cell3.setCellStyle(style); 
//        cell4.setCellStyle(style); 
//        cell5.setCellStyle(style); 
//        cell6.setCellStyle(style); 
//        cell7.setCellStyle(style2); 
//        cell8.setCellStyle(style2);
//        cell9.setCellStyle(style2);
//        cell10.setCellStyle(style2);
//        cell11.setCellStyle(style2);
//        
//      //第一行标题
//        cell.setCellValue("序号");
//        cell1.setCellValue("批次[A15]");
//        cell2.setCellValue("报备年度[A14]");
//        cell3.setCellValue("地市");
//        cell4.setCellValue("县(区)");
//        cell5.setCellValue("示范片名称[A12]");
//        cell6.setCellValue("建设主题名称[A13]");
//        cell7.setCellValue("属性分类");
//        cell8.setCellValue("项目类别分类汇总");
//        cell9.setCellValue("计划总投入资金");
//        cell10.setCellValue("累计完成总投入资金");
//        cell11.setCellValue("群众投工投劳情况");
//        
//        sheet.addMergedRegion(new CellRangeAddress(3, 7, 0, 0));
//        sheet.addMergedRegion(new CellRangeAddress(3, 7, 1, 1));
//        sheet.addMergedRegion(new CellRangeAddress(3, 7, 2, 2));
//        sheet.addMergedRegion(new CellRangeAddress(3, 7, 3, 3));
//        sheet.addMergedRegion(new CellRangeAddress(3, 7, 4, 4));
//        sheet.addMergedRegion(new CellRangeAddress(3, 7, 5, 5));
//        sheet.addMergedRegion(new CellRangeAddress(3, 7, 6, 6));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 7, 9));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 10, 12));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 13, 21));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 22, 28));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 29, 35));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 36, 37));
//        
//        //第二行
//        HSSFRow row1 = sheet.createRow(4);  
//        HSSFCell tCell = row1.createCell(7);
//        HSSFCell tCel11= row1.createCell(8);
//        HSSFCell tCel12= row1.createCell(9);
//        HSSFCell tCel13= row1.createCell(10);
//        HSSFCell tCel14= row1.createCell(11);
//        HSSFCell tCel15= row1.createCell(12);
//        HSSFCell tCel16= row1.createCell(13);
//        HSSFCell tCel17= row1.createCell(14);
//        HSSFCell tCel18= row1.createCell(15);
//        HSSFCell tCel19= row1.createCell(16);
//        HSSFCell tCel110= row1.createCell(17);
//        HSSFCell tCel111= row1.createCell(18);
//        HSSFCell tCel112= row1.createCell(19);
//        HSSFCell tCel113= row1.createCell(20);
//        HSSFCell tCel114= row1.createCell(21);
//        HSSFCell tCel115= row1.createCell(22);
//        HSSFCell tCel116= row1.createCell(23);
//        HSSFCell tCel117= row1.createCell(24);
//        HSSFCell tCel118= row1.createCell(25);
//        HSSFCell tCel119= row1.createCell(26);
//        HSSFCell tCel120= row1.createCell(27);
//        HSSFCell tCel121= row1.createCell(28);
//        HSSFCell tCel122= row1.createCell(29);
//        HSSFCell tCel123= row1.createCell(30);
//        HSSFCell tCel124= row1.createCell(31);
//        HSSFCell tCel125= row1.createCell(32);
//        HSSFCell tCel126= row1.createCell(33);
//        HSSFCell tCel127= row1.createCell(34);
//        HSSFCell tCel128= row1.createCell(35);
//        HSSFCell tCel129= row1.createCell(36);
//        HSSFCell tCel130= row1.createCell(37);
//        HSSFCell tCel131= row1.createCell(38);
//        HSSFCell tCel132= row1.createCell(39);
//        HSSFCell tCel133= row1.createCell(40);
//        HSSFCell tCel134= row1.createCell(41);
//        
//        tCell.setCellStyle(style); 
//        tCel11.setCellStyle(style); 
//        tCel12.setCellStyle(style); 
//        tCel13.setCellStyle(style); 
//        tCel14.setCellStyle(style); 
//        tCel15.setCellStyle(style); 
//        tCel16.setCellStyle(style); 
//        tCel17.setCellStyle(style); 
//        tCel18.setCellStyle(style); 
//        tCel19.setCellStyle(style); 
//        tCel110.setCellStyle(style); 
//        tCel111.setCellStyle(style); 
//        tCel112.setCellStyle(style); 
//        tCel113.setCellStyle(style); 
//        tCel114.setCellStyle(style); 
//        tCel115.setCellStyle(style); 
//        tCel116.setCellStyle(style); 
//        tCel117.setCellStyle(style); 
//        tCel118.setCellStyle(style); 
//        tCel119.setCellStyle(style); 
//        tCel120.setCellStyle(style); 
//        tCel121.setCellStyle(style); 
//        tCel122.setCellStyle(style); 
//        tCel123.setCellStyle(style); 
//        tCel124.setCellStyle(style); 
//        tCel125.setCellStyle(style);
//        tCel126.setCellStyle(style); 
//        tCel127.setCellStyle(style); 
//        tCel128.setCellStyle(style); 
//        tCel129.setCellStyle(style); 
//        tCel130.setCellStyle(style); 
//        tCel131.setCellStyle(style); 
//        tCel132.setCellStyle(style); 
//        tCel133.setCellStyle(style); 
//        tCel134.setCellStyle(style); 
//        
//        //第二行标题
//        tCell.setCellValue("当前项目总个数");
//        tCel11.setCellValue("已竣工");
//        tCel12.setCellValue("建设中");
//        tCel13.setCellValue("连线建设工程个数");
//        tCel14.setCellValue("主体建设村工程");
//        tCel15.setCellValue("辐射带动村工程");
//        tCel16.setCellValue("规划设计");
//        tCel17.setCellValue("村庄环境整治（垃圾、污水处理等）");
//        tCel18.setCellValue("村居外立面整治");
//        tCel19.setCellValue("旧村旧房改造");
//        tCel110.setCellValue("文化传承保护");
//        tCel111.setCellValue("美化绿化建设");
//        tCel112.setCellValue("基础设施建设");
//        tCel113.setCellValue("连线工程项目");
//        tCel114.setCellValue("其他");
//        tCel115.setCellValue("合计");
//        tCel116.setCellValue("中央");
//        tCel117.setCellValue("省级新农村连片示范工程建设补助资金");
//        tCel118.setCellValue("其他省级财政资金");
//        tCel119.setCellValue("市级财政资金");
//        tCel120.setCellValue("县级财政资金");
//        tCel121.setCellValue("社会");
//        tCel122.setCellValue("群众自筹");
//        tCel123.setCellValue("其他");
//        tCel124.setCellValue("合计");
//        tCel125.setCellValue("中央");
//        tCel126.setCellValue("省级新农村连片示范工程建设补助资金");
//        tCel127.setCellValue("其他省级财政资金");
//        tCel128.setCellValue("市级财政资金");
//        tCel129.setCellValue("县级财政资金");
//        tCel130.setCellValue("社会");
//        tCel131.setCellValue("群众自筹");
//        tCel132.setCellValue("其他");
//        tCel133.setCellValue("投工数");
//        tCel134.setCellValue("折资额");
//        
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 7, 7));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 8, 8));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 9, 9));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 10, 10));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 11, 11));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 12, 12));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 13, 13));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 14, 14));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 15, 15));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 16, 16));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 17, 17));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 18, 18));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 19, 19));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 20, 20));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 21, 21));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 22, 22));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 23, 23));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 24, 24));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 25, 25));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 26, 26));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 27, 27));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 28, 28));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 29, 29));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 30, 30));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 31, 31));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 32, 32));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 33, 33));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 34, 34));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 35, 35));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 36, 36));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 37, 37));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 38, 38));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 39, 39));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 40, 40));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 41, 41));
//        
//       
//        //第三行
//        HSSFRow row2 = sheet.createRow(7); 
//        HSSFCell sCell = row2.createCell(7);
//        HSSFCell sCell1 = row2.createCell(8);
//        HSSFCell sCell2 = row2.createCell(9);
//        HSSFCell sCell3 = row2.createCell(10);
//        HSSFCell sCell4 = row2.createCell(11);
//        HSSFCell sCell5 = row2.createCell(12);
//        HSSFCell sCell6 = row2.createCell(13);
//        HSSFCell sCell7 = row2.createCell(14);
//        HSSFCell sCell8 = row2.createCell(15);
//        HSSFCell sCell9 = row2.createCell(16);
//        HSSFCell sCell10 = row2.createCell(17);
//        HSSFCell sCell11 = row2.createCell(18);
//        HSSFCell sCell12 = row2.createCell(19);
//        HSSFCell sCell13 = row2.createCell(20);
//        HSSFCell sCell14 = row2.createCell(21);
//        HSSFCell sCell15 = row2.createCell(22);
//        HSSFCell sCell16 = row2.createCell(23);
//        HSSFCell sCell17 = row2.createCell(24);
//        HSSFCell sCell18 = row2.createCell(25);
//        HSSFCell sCell19 = row2.createCell(26);
//        HSSFCell sCell20 = row2.createCell(27);
//        HSSFCell sCell21 = row2.createCell(28);
//        HSSFCell sCell22 = row2.createCell(29);
//        HSSFCell sCell23 = row2.createCell(30);
//        HSSFCell sCell24 = row2.createCell(31);
//        HSSFCell sCell25 = row2.createCell(32);
//        HSSFCell sCell26 = row2.createCell(33);
//        HSSFCell sCell27 = row2.createCell(34);
//        HSSFCell sCell28 = row2.createCell(35);
//        HSSFCell sCell29 = row2.createCell(36);
//        HSSFCell sCell30 = row2.createCell(37);
//        HSSFCell sCell31 = row2.createCell(38);
//        HSSFCell sCell32 = row2.createCell(39);
//        HSSFCell sCell33 = row2.createCell(40);
//        HSSFCell sCell34 = row2.createCell(41);
//        
//        sCell.setCellStyle(style);
//        sCell1.setCellStyle(style);
//        sCell2.setCellStyle(style);
//        sCell3.setCellStyle(style);
//        sCell4.setCellStyle(style);
//        sCell5.setCellStyle(style);
//        sCell6.setCellStyle(style);
//        sCell7.setCellStyle(style);
//        sCell8.setCellStyle(style);
//        sCell9.setCellStyle(style);
//        sCell10.setCellStyle(style);
//        sCell11.setCellStyle(style);
//        sCell12.setCellStyle(style);
//        sCell13.setCellStyle(style);
//        sCell14.setCellStyle(style);
//        sCell15.setCellStyle(style);
//        sCell16.setCellStyle(style);
//        sCell17.setCellStyle(style);
//        sCell18.setCellStyle(style);
//        sCell19.setCellStyle(style);
//        sCell20.setCellStyle(style);
//        sCell21.setCellStyle(style);
//        sCell22.setCellStyle(style);
//        sCell23.setCellStyle(style);
//        sCell24.setCellStyle(style);
//        sCell25.setCellStyle(style);
//        sCell26.setCellStyle(style);
//        sCell27.setCellStyle(style);
//        sCell28.setCellStyle(style);
//        sCell29.setCellStyle(style);
//        sCell30.setCellStyle(style);
//        sCell31.setCellStyle(style);
//        sCell32.setCellStyle(style);
//        sCell33.setCellStyle(style);
//        sCell34.setCellStyle(style);
//        
//      //第三行标题
//        sCell.setCellValue("个");
//        sCell1.setCellValue("个");
//        sCell2.setCellValue("个");
//        sCell3.setCellValue("个");
//        sCell4.setCellValue("个");
//        sCell5.setCellValue("个");
//        sCell6.setCellValue("个");
//        sCell7.setCellValue("个");
//        sCell8.setCellValue("个");
//        sCell9.setCellValue("个");
//        sCell10.setCellValue("个");
//        sCell11.setCellValue("个");
//        sCell12.setCellValue("个");
//        sCell13.setCellValue("个");
//        sCell14.setCellValue("个");
//        sCell15.setCellValue("E9[万元]");
//        sCell16.setCellValue("E10[万元]");
//        sCell17.setCellValue("E11[万元]");
//        sCell18.setCellValue("E12[万元]");
//        sCell19.setCellValue("E131[万元]");
//        sCell20.setCellValue("E132[万元]");
//        sCell21.setCellValue("E14[万元]");
//        sCell22.setCellValue("E141[万元]");
//        sCell23.setCellValue("E15[万元]");
//        sCell24.setCellValue("E16[万元]");
//        sCell25.setCellValue("E17[万元]");
//        sCell26.setCellValue("E18[万元]");
//        sCell27.setCellValue("E19[万元]");
//        sCell28.setCellValue("E201[万元]");
//        sCell29.setCellValue("E202[万元]");
//        sCell30.setCellValue("E21[万元]");
//        sCell31.setCellValue("E211[万元]");
//        sCell32.setCellValue("E22[万元]");
//        sCell33.setCellValue("E23[万元]");
//        sCell34.setCellValue("E24[万元]");
//        
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 7, 7));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 8, 8));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 9, 9));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 10, 10));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 11, 11));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 12, 12));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 13, 13));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 14, 14));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 15, 15));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 16, 16));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 17, 17));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 18, 18));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 19, 19));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 20, 20));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 21, 21));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 22, 22));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 23, 23));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 24, 24));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 25, 25));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 26, 26));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 27, 27));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 28, 28));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 29, 29));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 30, 30));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 31, 31));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 32, 32));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 33, 33));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 34, 34));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 35, 35));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 36, 36));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 37, 37));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 38, 38));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 39, 39));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 40, 40));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 41, 41));
    	protected void statistics(Collection<Object[]> items, HSSFCellStyle mStyle, HSSFCellStyle style, HSSFSheet sheet) {

        //显示items里面的数据
        int i=8, j = 1, k = 0;
        int E1 = 0, E2 = 0, E3 = 0, E4 = 0, E5 = 0, E6 = 0, E7 = 0, E8 = 0;
        double E9 = 0, E10 = 0, E11 = 0, E12 = 0, E131 = 0,E132 = 0,E14 = 0, E141=0,E15 = 0;
		double E16 = 0, E17 = 0, E18 = 0, E19 = 0, E201 = 0, E202 = 0, E21 = 0, E211=0,E22 = 0, E23 = 0, E24 = 0, E25 = 0;
		double E26 = 0, E27 = 0, E28 = 0, E29 = 0, E30 = 0, E31 = 0;
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
        	if(it[6] instanceof Number){
				E1 += ((Number)it[6]).intValue();
			}
			if(it[7] instanceof Number){
				E2 += ((Number)it[7]).intValue();
			}
			if(it[8] instanceof Number){
				E3 += ((Number)it[8]).intValue();
			}
			if(it[9] instanceof Number){
				E4 += ((Number)it[9]).intValue();
			}
			if(it[10] instanceof Number){
				E5 += ((Number)it[10]).intValue();
			}
			if(it[11] instanceof Number){
				E6 += ((Number)it[11]).intValue();
			}
			if(it[12] instanceof Number){
				E7 += ((Number)it[12]).intValue();
			}
			if(it[13] instanceof Number){
				E8 += ((Number)it[13]).intValue();
			}
			if(it[14] instanceof Number){
				E9 += ((Number)it[14]).intValue();
			}
			if(it[15] instanceof Number){
				E10 += ((Number)it[15]).intValue();
			}
			if(it[16] instanceof Number){
				E11 += ((Number)it[16]).intValue();
			}
			if(it[17] instanceof Number){
				E12 += ((Number)it[17]).intValue();
			}
			if(it[18] instanceof Number){
				E131 += ((Number)it[18]).intValue();
			}
			if(it[19] instanceof Number){
				E132 += ((Number)it[19]).intValue();
			}
			if(it[20] instanceof Number){
				E14 += ((Number)it[20]).intValue();
			}
//			if(it[21] instanceof Number){
//				E141 += ((Number)it[21]).intValue();
//			}
//			if(it[22] instanceof Number){
//				E15 += ((Number)it[22]).doubleValue();
//			}
//			if(it[23] instanceof Number){
//				E16 += ((Number)it[23]).doubleValue();
//			}
//			if(it[24] instanceof Number){
//				E17 += ((Number)it[24]).doubleValue();
//			}
//			if(it[25] instanceof Number){
//				E18 += ((Number)it[25]).doubleValue();
//			}
//			if(it[26] instanceof Number){
//				E19 += ((Number)it[26]).doubleValue();
//			}
//			if(it[27] instanceof Number){
//				E201 += ((Number)it[27]).doubleValue();
//			}
//			if(it[28] instanceof Number){
//				E202 += ((Number)it[28]).doubleValue();
//			}
//			if(it[29] instanceof Number){
//				E211 += ((Number)it[29]).doubleValue();
//			}
//			if(it[30] instanceof Number){
//				E22 += ((Number)it[30]).doubleValue();
//			}
//			if(it[31] instanceof Number){
//				E23 += ((Number)it[31]).doubleValue();
//			}
//			if(it[32] instanceof Number){
//				E24 += ((Number)it[32]).doubleValue();
//			}
//			if(it[33] instanceof Number){
//				E25 += ((Number)it[33]).doubleValue();
//			}
//			if(it[34] instanceof Number){
//				E26 += ((Number)it[34]).doubleValue();
//			}
//			if(it[35] instanceof Number){
//				E27 += ((Number)it[35]).doubleValue();
//			}
//			if(it[36] instanceof Number){
//				E28 += ((Number)it[36]).doubleValue();
//			}
//			if(it[37] instanceof Number){
//				E29 += ((Number)it[37]).doubleValue();
//			}
//			if(it[38] instanceof Number){
//				E30 += ((Number)it[38]).doubleValue();
//			}
//			if(it[39] instanceof Number){
//				E31 += ((Number)it[39]).doubleValue();
//			}
			
			E141 = add(E141,it[21]);
			E15 = add(E15,it[22]);
			E16 = add(E16,it[23]);
			E17 = add(E17,it[24]);
			E18 = add(E18,it[25]);
			E19 = add(E19,it[26]);
			E201 = add(E201,it[27]);
			E202 = add(E202,it[28]);
			E21 = add(E21,it[29]);
			E211 = add(E211,it[30]);
			E22 = add(E22,it[31]);
			E23 = add(E23,it[32]);
			E24 = add(E24,it[33]);
			E25 = add(E25,it[34]);
			E26 = add(E26,it[35]);
			E27 = add(E27,it[36]);
			E28 = add(E28,it[37]);
			E29 = add(E29,it[38]);
			E30 = add(E30,it[39]);
			E31 = add(E31,it[40]);
        	  
        }
        HSSFRow mRow1=sheet.createRow(i);
        HSSFCell mCell1 = mRow1.createCell(0);
        mCell1.setCellValue("全省汇总：");
        HSSFCell mCell2 = mRow1.createCell(7);
        mCell2.setCellValue(E1);
        HSSFCell mCell3 = mRow1.createCell(8);
        mCell3.setCellValue(E2);
        HSSFCell mCell4 = mRow1.createCell(9);
        mCell4.setCellValue(E3);
        HSSFCell mCell5 = mRow1.createCell(10);
        mCell5.setCellValue(E4);
        HSSFCell mCell6 = mRow1.createCell(11);
        mCell6.setCellValue(E5);
        HSSFCell mCell7 = mRow1.createCell(12);
        mCell7.setCellValue(E6);
        HSSFCell mCell8 = mRow1.createCell(13);
        mCell8.setCellValue(E7);
        HSSFCell mCell9 = mRow1.createCell(14);
        mCell9.setCellValue(E8);
        HSSFCell mCell10 = mRow1.createCell(15);
        mCell10.setCellValue(E9);
        HSSFCell mCell11 = mRow1.createCell(16);
        mCell11.setCellValue(E10);
        HSSFCell mCell12 = mRow1.createCell(17);
        mCell12.setCellValue(E11);
        HSSFCell mCell13 = mRow1.createCell(18);
        mCell13.setCellValue(E12);
        HSSFCell mCell14 = mRow1.createCell(19);
        mCell14.setCellValue(E131);
        HSSFCell mCell15 = mRow1.createCell(20);
        mCell15.setCellValue(E132);
        HSSFCell mCell16 = mRow1.createCell(21);
        mCell16.setCellValue(E14);
        HSSFCell mCell17 = mRow1.createCell(22);
        mCell17.setCellValue(String.valueOf(E141));
        HSSFCell mCell18 = mRow1.createCell(23);
        mCell18.setCellValue(String.valueOf(E15));
        HSSFCell mCell19 = mRow1.createCell(24);
        mCell19.setCellValue(String.valueOf(E16));
        HSSFCell mCell20 = mRow1.createCell(25);
        mCell20.setCellValue(String.valueOf(E17));
        HSSFCell mCell21 = mRow1.createCell(26);
        mCell21.setCellValue(String.valueOf(E18));
        HSSFCell mCell22 = mRow1.createCell(27);
        mCell22.setCellValue(String.valueOf(E19));
        HSSFCell mCell23 = mRow1.createCell(28);
        mCell23.setCellValue(String.valueOf(E201));
        HSSFCell mCell24 = mRow1.createCell(29);
        mCell24.setCellValue(String.valueOf(E202));
        HSSFCell mCell25 = mRow1.createCell(30);
        mCell25.setCellValue(String.valueOf(E21));
        HSSFCell mCell26 = mRow1.createCell(31);
        mCell26.setCellValue(String.valueOf(E211));
        HSSFCell mCell27 = mRow1.createCell(32);
        mCell27.setCellValue(String.valueOf(E22));
        HSSFCell mCell28 = mRow1.createCell(33);
        mCell28.setCellValue(String.valueOf(E23));
        HSSFCell mCell29 = mRow1.createCell(34);
        mCell29.setCellValue(String.valueOf(E24));
        HSSFCell mCell30 = mRow1.createCell(35);
        mCell30.setCellValue(String.valueOf(E25));
        HSSFCell mCell31 = mRow1.createCell(36);
        mCell31.setCellValue(String.valueOf(E26));
        HSSFCell mCell32 = mRow1.createCell(37);
        mCell32.setCellValue(String.valueOf(E27));
        HSSFCell mCell33 = mRow1.createCell(38);
        mCell33.setCellValue(String.valueOf(E28));
        HSSFCell mCell34 = mRow1.createCell(39);
        mCell34.setCellValue(String.valueOf(E29));
        HSSFCell mCell35 = mRow1.createCell(40);
        mCell35.setCellValue(String.valueOf(E30));
        HSSFCell mCell36 = mRow1.createCell(41);
        mCell36.setCellValue(String.valueOf(E31));
        
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
        
        
//        workbook.write(response.getOutputStream());
//		}catch (Exception e) {
//			e.printStackTrace();
//		}

	}
	
	protected StringBuilder statistics(Collection<Object[]> items){
		StringBuilder statis = new StringBuilder();
		int i = 1;
		int E1 = 0, E2 = 0, E3 = 0, E4 = 0, E5 = 0, E6 = 0, E7 = 0, E8 = 0;
		double E9 = 0, E10 = 0, E11 = 0, E12 = 0, E131 = 0,E132 = 0,E14 = 0, E141 = 0,E15 = 0;
		double E16 = 0, E17 = 0, E18 = 0, E19 = 0, E201 = 0,E202 = 0, E21 = 0,E211 = 0, E22 = 0, E23 = 0, E24 = 0, E25 = 0;
		double E26 = 0, E27 = 0, E28 = 0, E29 = 0, E30 = 0, E31 = 0;
		for(Object[] it : items){
			statis.append("<tr><td>").append(i++).append("</td>");
			for(Object obj : it){
				statis.append("<td>");
				if(null != obj) statis.append(obj);
				statis.append("</td>");
			}
			if(it[6] instanceof Number){
				E1 += ((Number)it[6]).intValue();
			}
			if(it[7] instanceof Number){
				E2 += ((Number)it[7]).intValue();
			}
			if(it[8] instanceof Number){
				E3 += ((Number)it[8]).intValue();
			}
			if(it[9] instanceof Number){
				E4 += ((Number)it[9]).intValue();
			}
			if(it[10] instanceof Number){
				E5 += ((Number)it[10]).intValue();
			}
			if(it[11] instanceof Number){
				E6 += ((Number)it[11]).intValue();
			}
			if(it[12] instanceof Number){
				E7 += ((Number)it[12]).intValue();
			}
			if(it[13] instanceof Number){
				E8 += ((Number)it[13]).intValue();
			}
			if(it[14] instanceof Number){
				E9 += ((Number)it[14]).intValue();
			}
			if(it[15] instanceof Number){
				E10 += ((Number)it[15]).intValue();
			}
			if(it[16] instanceof Number){
				E11 += ((Number)it[16]).intValue();
			}
			if(it[17] instanceof Number){
				E12 += ((Number)it[17]).intValue();
			}
			if(it[18] instanceof Number){
				E131 += ((Number)it[18]).intValue();
			}
			if(it[19] instanceof Number){
				E132 += ((Number)it[19]).intValue();
			}
			if(it[20] instanceof Number){
				E14 += ((Number)it[20]).intValue();
			}
//			if(it[21] instanceof Number){
//				E141 += ((Number)it[21]).doubleValue();
//			}
//			if(it[22] instanceof Number){
//				E15 += ((Number)it[22]).doubleValue();
//			}
//			if(it[23] instanceof Number){
//				E16 += ((Number)it[23]).doubleValue();
//			}
//			if(it[24] instanceof Number){
//				E17 += ((Number)it[24]).doubleValue();
//			}
//			if(it[25] instanceof Number){
//				E18 += ((Number)it[25]).doubleValue();
//			}
//			if(it[26] instanceof Number){
//				E19 += ((Number)it[26]).doubleValue();
//			}
//			if(it[27] instanceof Number){
//				E201 += ((Number)it[27]).doubleValue();
//			}
//			if(it[28] instanceof Number){
//				E202 += ((Number)it[28]).doubleValue();
//			}
//			if(it[29] instanceof Number){
//				E21 += ((Number)it[29]).doubleValue();
//			}
//			if(it[30] instanceof Number){
//				E211 += ((Number)it[30]).doubleValue();
//			}
//			if(it[31] instanceof Number){
//				E22 += ((Number)it[31]).doubleValue();
//			}
//			if(it[32] instanceof Number){
//				E23 += ((Number)it[32]).doubleValue();
//			}
//			if(it[33] instanceof Number){
//				E24 += ((Number)it[33]).doubleValue();
//			}
//			if(it[34] instanceof Number){
//				E25 += ((Number)it[34]).doubleValue();
//			}
//			if(it[35] instanceof Number){
//				E26 += ((Number)it[35]).doubleValue();
//			}
//			if(it[36] instanceof Number){
//				E27 += ((Number)it[36]).doubleValue();
//			}
//			if(it[37] instanceof Number){
//				E28 += ((Number)it[37]).doubleValue();
//			}
//			if(it[38] instanceof Number){
//				E29 += ((Number)it[38]).doubleValue();
//			}
//			if(it[39] instanceof Number){
//				E30 += ((Number)it[39]).doubleValue();
//			}
//			if(it[40] instanceof Number){
//				E31 += ((Number)it[40]).doubleValue();
//			}
			
			E141 = add(E141,it[21]);
			E15 = add(E15,it[22]);
			E16 = add(E16,it[23]);
			E17 = add(E17,it[24]);
			E18 = add(E18,it[25]);
			E19 = add(E19,it[26]);
			E201 = add(E201,it[27]);
			E202 = add(E202,it[28]);
			E21 = add(E21,it[29]);
			E211 = add(E211,it[30]);
			E22 = add(E22,it[31]);
			E23 = add(E23,it[32]);
			E24 = add(E24,it[33]);
			E25 = add(E25,it[34]);
			E26 = add(E26,it[35]);
			E27 = add(E27,it[36]);
			E28 = add(E28,it[37]);
			E29 = add(E29,it[38]);
			E30 = add(E30,it[39]);
			E31 = add(E31,it[40]);
			
			
			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td>");
		statis.append(E1).append("</td><td>");
		statis.append(E2).append("</td><td>");
		statis.append(E3).append("</td><td>");
		statis.append(E4).append("</td><td>");
		statis.append(E5).append("</td><td>");
		statis.append(E6).append("</td><td>");
		statis.append(E7).append("</td><td>");
		statis.append(E8).append("</td><td>");
		statis.append(E9).append("</td><td>");
		statis.append(E10).append("</td><td>");
		statis.append(E11).append("</td><td>");
		statis.append(E12).append("</td><td>");
		statis.append(E131).append("</td><td>");
		statis.append(E132).append("</td><td>");
		statis.append(E14).append("</td><td>");
		statis.append(E141).append("</td><td>");
		statis.append(E15).append("</td><td>");
		statis.append(E16).append("</td><td>");
		statis.append(E17).append("</td><td>");
		statis.append(E18).append("</td><td>");
		statis.append(E19).append("</td><td>");
		statis.append(E201).append("</td><td>");
		statis.append(E202).append("</td><td>");
		statis.append(E21).append("</td><td>");
		statis.append(E211).append("</td><td>");
		statis.append(E22).append("</td><td>");
		statis.append(E23).append("</td><td>");
		statis.append(E24).append("</td><td>");
		statis.append(E25).append("</td><td>");
		statis.append(E26).append("</td><td>");
		statis.append(E27).append("</td><td>");
		statis.append(E28).append("</td><td>");
		statis.append(E29).append("</td><td>");
		statis.append(E30).append("</td><td>");
		statis.append(E31).append("</td></tr>");
		return statis;
	}
}
