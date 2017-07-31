package cn.bonoon.controllers.statistics;

import java.text.DecimalFormat;
import static cn.bonoon.util.DoubleHelper.add;
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

@Controller
@RequestMapping("s/pls/sma")
public class ProvinceStatisticsModelAreaController extends StatisticsModelAreaController{

//	@Autowired
//	protected ModelAreaService statisticsService;
//	
//	@Override
//	protected void onLoad(PanelEvent event) throws Exception {
//		event.setVmPath("statistics/model-area");
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
		PanelModel model = event.getModel();
		String batch = model.getParameter("batch");
		
		model.addObject("select", BatchHelper.batchSelect(batch));
		super.onLoad(event);
	}
//	
//	@SuppressWarnings("deprecation")
//	@RequestMapping(value = "!{key}/index.excel", method = GET)
//	public void excel(HttpServletRequest request, HttpServletResponse response, String batch){
//		HSSFWorkbook workbook = null;
//		try{
//		String _name = "广东省新农村连片示范建设工程台账簿.xls";
//		response.setContentType("multipart/form-data");
//		response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
//		Collection<Object[]> items = getItems(getUser(), batch);
//        // 声明一个工作薄  
//        workbook = new HSSFWorkbook();  
//        // 生成一个表格  
//        HSSFSheet sheet = workbook.createSheet("测试");  
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
//        cel.setCellValue("广东省新农村连片示范建设工程台账簿 - 建设台账封面统计");
//        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 38));
//        
//        cel.setCellStyle(style1); 
//        
//        HSSFRow row = sheet.createRow(3);  //第一行
//        HSSFCell cell = row.createCell(0);
//        HSSFCell cell1 = row.createCell(1);
//        HSSFCell cell2 = row.createCell(2);
//        HSSFCell cell3 = row.createCell(3);
//        HSSFCell cell4 = row.createCell(4);
//        HSSFCell cell5 = row.createCell(5);
//        HSSFCell cell6 = row.createCell(6);
//        HSSFCell cell7 = row.createCell(7);
//        HSSFCell cell8 = row.createCell(24);
//        HSSFCell cell9 = row.createCell(30);
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
//        
//        
//        HSSFRow row1 = sheet.createRow(4);  //第二行
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
//        HSSFCell tCel110= row1.createCell(20);
//        HSSFCell tCel111= row1.createCell(24);
//        HSSFCell tCel112= row1.createCell(25);
//        HSSFCell tCel113= row1.createCell(26);
//        HSSFCell tCel114= row1.createCell(27);
//        HSSFCell tCel115= row1.createCell(28);
//        HSSFCell tCel116= row1.createCell(29);
//        HSSFCell tCel117= row1.createCell(30);
//        HSSFCell tCel118= row1.createCell(31);
//        HSSFCell tCel119= row1.createCell(32);
//        HSSFCell tCel120= row1.createCell(33);
//        HSSFCell tCel121= row1.createCell(34);
//        HSSFCell tCel122= row1.createCell(35);
//        HSSFCell tCel123= row1.createCell(36);
//        HSSFCell tCel124= row1.createCell(37);
//        HSSFCell tCel125= row1.createCell(38);
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
//        
//        HSSFRow row2 = sheet.createRow(6);  //第三行
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
//         
//        //第一行标题
//        cell.setCellValue("序号");
//        cell1.setCellValue("批次[A15]");
//        cell2.setCellValue("报备年度[A14]");
//        cell3.setCellValue("地市");
//        cell4.setCellValue("县(区)");
//        cell5.setCellValue("示范片名称[A12]");
//        cell6.setCellValue("建设主题名称[A13]");
//        cell7.setCellValue("（一）示范片所在县(市、区）基本情况");
//        cell8.setCellValue("（二）示范片总体情况");
//        cell9.setCellValue("（三）连片建设基本情况");
//        
//        
//        
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, 0));
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 1, 1));
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 2, 2));
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 3, 3));
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 4, 4));
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 5, 5));
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 6, 6));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 7, 23));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 24, 29));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 30, 38));
//        
//        //第二行标题
//        tCell.setCellValue("农村面积");
//        tCel11.setCellValue("乡镇个数");
//        tCel12.setCellValue("行政村个数");
//        tCel13.setCellValue("自然村个数");
//        tCel14.setCellValue("村民小组数");
//        tCel15.setCellValue("县人口总户数");
//        tCel16.setCellValue("县农村总户数");
//        tCel17.setCellValue("县总人口数");
//        tCel18.setCellValue("县农村总人口数");
//        tCel19.setCellValue("各年度城镇居民人均纯收入A10[元]");
//        tCel110.setCellValue("各年度农民人均纯收入A11[元]");
//        tCel111.setCellValue("主体建设村个数");
//        tCel112.setCellValue("辐射带动村个数");
//        tCel113.setCellValue("覆盖乡镇数");
//        tCel114.setCellValue("示范片面积");
//        tCel115.setCellValue("总户数");
//        tCel116.setCellValue("总人口数");
//        tCel117.setCellValue("主体村之间连线连片规模");
//        tCel118.setCellValue("起点标志");
//        tCel119.setCellValue("规划设计标识");
//        tCel120.setCellValue("驿站");
//        tCel121.setCellValue("绿道");
//        tCel122.setCellValue("观景台（点）");
//        tCel123.setCellValue("宣传介绍牌");
//        tCel124.setCellValue("道路两旁美化绿化工程");
//        tCel125.setCellValue("示范片连线线路走");
//        
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 7, 7));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 8, 8));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 9, 9));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 10, 10));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 11, 11));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 12, 12));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 13, 13));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 14, 14));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 15, 15));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 16, 19));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 20, 23));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 24, 24));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 25, 25));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 26, 26));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 27, 27));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 28, 28));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 29, 29));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 30, 30));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 31, 31));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 32, 32));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 33, 33));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 34, 34));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 35, 35));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 36, 36));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 37, 37));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 38, 38));
//        
//        //第三行标题
//        sCell.setCellValue("A1[平方公里]");
//        sCell1.setCellValue("A2[个]");
//        sCell2.setCellValue("A3[个]");
//        sCell3.setCellValue("A4[个]");
//        sCell4.setCellValue("A5[个]");
//        sCell5.setCellValue("A6[户]");
//        sCell6.setCellValue("A7[户]");
//        sCell7.setCellValue("A8[人]");
//        sCell8.setCellValue("A9[人]");
//        sCell9.setCellValue("2013年");
//        sCell10.setCellValue("2014年");
//        sCell11.setCellValue("2015年");
//        sCell12.setCellValue("2016年");
//        sCell13.setCellValue("2017年");
//        sCell14.setCellValue("2013年");
//        sCell15.setCellValue("2014年");
//        sCell16.setCellValue("2015年");
//        sCell17.setCellValue("2016年");
//        sCell18.setCellValue("2017年");
//        sCell19.setCellValue("A16[个]");
//        sCell20.setCellValue("A17[个]");
//        sCell21.setCellValue("A18[个]");
//        sCell22.setCellValue("A19[平方公里]");
//        sCell23.setCellValue("A20[户]");
//        sCell24.setCellValue("A21[人]");
//        sCell25.setCellValue("A22[公里]");
//        sCell26.setCellValue("A23[无或有]");
//        sCell27.setCellValue("A24[个]");
//        sCell28.setCellValue("A25[个]");
//        sCell29.setCellValue("A26[公里]");
//        sCell30.setCellValue("A27[个]");
//        sCell31.setCellValue("A28[块]");
//        sCell32.setCellValue("A29[宗]");
//        sCell33.setCellValue("A30");
//        
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 7, 7));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 8, 8));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 9, 9));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 10, 10));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 11, 11));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 12, 12));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 13, 13));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 14, 14));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 15, 15));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 16, 16));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 17, 17));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 18, 18));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 19, 19));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 20, 20));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 21, 21));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 22, 22));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 23, 23));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 24, 24));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 25, 25));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 26, 26));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 27, 27));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 28, 28));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 29, 29));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 30, 30));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 31, 31));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 32, 32));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 33, 33));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 34, 34));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 35, 35));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 36, 36));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 37, 37));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 38, 38));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 39, 39));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 40, 40));
//        
    protected void statistics(Collection<Object[]> items, HSSFCellStyle mStyle, HSSFCellStyle style, HSSFSheet sheet) {

        //显示items里面的数据
        int i=7, j = 1, k = 0;
        double A1 = 0,A19 = 0, A22 = 0, A26 = 0;
		int A2 = 0, A3 = 0, A4 = 0, A5 = 0, A6 = 0, A7 = 0, A8 = 0, A9 = 0;
		double x13 = 0, x14 = 0, x15 = 0, x16 = 0,z13= 0, z14 = 0, z15 = 0, z16 = 0;
		int A16 = 0, A17 = 0, A18 = 0,  A20 = 0, A21 = 0;
		int A24 = 0, A25 = 0, A27 = 0, A28 = 0, A29 = 0;
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
//        	if(it[6] instanceof Number){
//  				A1 += ((Number)it[6]).doubleValue();
//  			}
        	A1 = add(A1, it[6]);
        	
  			if(it[7] instanceof Number){
  				A2 += ((Number)it[7]).intValue();
  			}
  			if(it[8] instanceof Number){
  				A3 += ((Number)it[8]).intValue();
  			}
  			if(it[9] instanceof Number){
  				A4 += ((Number)it[9]).intValue();
  			}
  			if(it[10] instanceof Number){
  				A5 += ((Number)it[10]).intValue();
  			}
  			if(it[11] instanceof Number){
  				A6 += ((Number)it[11]).intValue();
  			}
  			if(it[12] instanceof Number){
  				A7 += ((Number)it[12]).intValue();
  			}
  			if(it[13] instanceof Number){
  				A8 += ((Number)it[13]).intValue();
  			}
  			if(it[14] instanceof Number){
  				A9 += ((Number)it[14]).intValue();
  			}
//  			if(it[15] instanceof Number){
//  				x13 += ((Number)it[15]).doubleValue();
//  			}
  			x13 = add(x13, it[15]);
  			
//  			if(it[16] instanceof Number){
//  				x14 += ((Number)it[16]).doubleValue();
//  			}
  			x14 = add(x14, it[16]);
  			
//  			if(it[17] instanceof Number){
//  				x15 += ((Number)it[17]).doubleValue();
//  			}
  			x15 = add(x15, it[17]);
  			
//  			if(it[18] instanceof Number){
//  				x16 += ((Number)it[18]).doubleValue();
//  			}
  			x16 = add(x16, it[18]);
  			
//  			if(it[19] instanceof Number){
//  				z13 += ((Number)it[19]).doubleValue();
//  			}
  			z13 = add(z13, it[19]);
  			
//  			if(it[20] instanceof Number){
//  				z14 += ((Number)it[20]).doubleValue();
//  			}
  			z14 = add(z14, it[20]);
  			
//  			if(it[21] instanceof Number){
//  				z15 += ((Number)it[21]).doubleValue();
//  			}
  			z15 = add(z15, it[21]);
  			
//  			if(it[22] instanceof Number){
//  				z16 += ((Number)it[22]).doubleValue();
//  			}
  			z16 = add(z16, it[22]);
  			
  			if(it[23] instanceof Number){
  				A16 += ((Number)it[23]).intValue();
  			}
  			if(it[24] instanceof Number){
  				A17 += ((Number)it[24]).intValue();
  			}
  			if(it[25] instanceof Number){
  				A18 += ((Number)it[25]).intValue();
  			}
//  			if(it[26] instanceof Number){
//  				A19 += ((Number)it[26]).doubleValue();
//  			}
  			A19 = add(A19, it[26]);
  			
  			if(it[27] instanceof Number){
  				A20 += ((Number)it[27]).intValue();
  			}
  			if(it[28] instanceof Number){
  				A21 += ((Number)it[28]).intValue();
  			}
//  			if(it[29] instanceof Number){
//  				A22 += ((Number)it[29]).doubleValue();
//  			}
  			A22 = add(A22, it[29]);
  			
  			if(it[31] instanceof Number){
  				A24 += ((Number)it[31]).intValue();
  			}
  			if(it[32] instanceof Number){
  				A25 += ((Number)it[32]).intValue();
  			}
  				A26 = add(A26, it[33]);
//  			if(it[33] instanceof Number){
//  			}
  			if(it[34] instanceof Number){
  				A27 += ((Number)it[34]).intValue();
  			}
  			if(it[35] instanceof Number){
  				A28 += ((Number)it[35]).intValue();
  			}
  			if(it[36] instanceof Number){
  				A29 += ((Number)it[36]).intValue();
  			}
        	  
        }
        HSSFRow mRow1=sheet.createRow(i);
        HSSFCell mCell1 = mRow1.createCell(0);
        mCell1.setCellValue("全省汇总：");
        HSSFCell mCell2 = mRow1.createCell(7);
        mCell2.setCellValue(String.valueOf(A1));
        HSSFCell mCell3 = mRow1.createCell(8);
        mCell3.setCellValue(A2);
        HSSFCell mCell4 = mRow1.createCell(9);
        mCell4.setCellValue(A3);
        HSSFCell mCell5 = mRow1.createCell(10);
        mCell5.setCellValue(A4);
        HSSFCell mCell6 = mRow1.createCell(11);
        mCell6.setCellValue(A5);
        HSSFCell mCell7 = mRow1.createCell(12);
        mCell7.setCellValue(A6);
        HSSFCell mCell8 = mRow1.createCell(13);
        mCell8.setCellValue(A7);
        HSSFCell mCell9 = mRow1.createCell(14);
        mCell9.setCellValue(A8);
        HSSFCell mCell10 = mRow1.createCell(15);
        mCell10.setCellValue(A9);
        HSSFCell mCell11 = mRow1.createCell(16);
        mCell11.setCellValue(String.valueOf(x13));
        HSSFCell mCell12 = mRow1.createCell(17);
        mCell12.setCellValue(String.valueOf(x14));
        HSSFCell mCell13 = mRow1.createCell(18);
        mCell13.setCellValue(String.valueOf(x15));
        HSSFCell mCell14 = mRow1.createCell(19);
        mCell14.setCellValue(String.valueOf(x16));
        HSSFCell mCell15 = mRow1.createCell(20);
        mCell15.setCellValue(String.valueOf(z13));
        HSSFCell mCell16 = mRow1.createCell(21);
        mCell16.setCellValue(String.valueOf(z14));
        HSSFCell mCell17 = mRow1.createCell(22);
        mCell17.setCellValue(String.valueOf(z15));
        HSSFCell mCell18 = mRow1.createCell(23);
        mCell18.setCellValue(String.valueOf(z16));
        HSSFCell mCell19 = mRow1.createCell(24);
        mCell19.setCellValue(A16);
        HSSFCell mCell20 = mRow1.createCell(25);
        mCell20.setCellValue(A17);
        HSSFCell mCell21 = mRow1.createCell(26);
        mCell21.setCellValue(A18);
        HSSFCell mCell22 = mRow1.createCell(27);
        mCell22.setCellValue(String.valueOf(A19));
        HSSFCell mCell23 = mRow1.createCell(28);
        mCell23.setCellValue(A20);
        HSSFCell mCell24 = mRow1.createCell(29);
        mCell24.setCellValue(A21);
        HSSFCell mCell25 = mRow1.createCell(30);
        mCell25.setCellValue(String.valueOf(A22));
        HSSFCell mCell26 = mRow1.createCell(32);
        mCell26.setCellValue(A24);
        HSSFCell mCell27 = mRow1.createCell(33);
        mCell27.setCellValue(A25);
        HSSFCell mCell28 = mRow1.createCell(34);
        mCell28.setCellValue(String.valueOf(A26));
        HSSFCell mCell29 = mRow1.createCell(35);
        mCell29.setCellValue(A27);
        HSSFCell mCell30 = mRow1.createCell(36);
        mCell30.setCellValue(A28);
        HSSFCell mCell31 = mRow1.createCell(37);
        mCell31.setCellValue(A29);
  
        
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
//        	
//        workbook.write(response.getOutputStream());
//        
//		}catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	
	protected StringBuilder statistics(Collection<Object[]> items){
		StringBuilder statis = new StringBuilder();
		int i = 1;
		double A1 = 0,A19 = 0, A22 = 0, A26 = 0;
		int A2 = 0, A3 = 0, A4 = 0, A5 = 0, A6 = 0, A7 = 0, A8 = 0, A9 = 0;
		double x13 = 0, x14 = 0, x15 = 0, x16 = 0,z13= 0, z14 = 0, z15 = 0, z16 = 0,z17 = 0,x17 = 0;
		int A16 = 0, A17 = 0, A18 = 0,  A20 = 0, A21 = 0;
		int A24 = 0, A25 = 0, A27 = 0, A28 = 0, A29 = 0,A23 = 0;
		for(Object[] it : items){
			statis.append("<tr><td>").append(i++).append("</td>");
			for(Object obj : it){
				statis.append("<td>");
				if(null != obj) statis.append(obj);
				statis.append("</td>");
			}
A1 = add(A1, it[6]);
        	
  			if(it[7] instanceof Number){
  				A2 += ((Number)it[7]).intValue();
  			}
  			if(it[8] instanceof Number){
  				A3 += ((Number)it[8]).intValue();
  			}
  			if(it[9] instanceof Number){
  				A4 += ((Number)it[9]).intValue();
  			}
  			if(it[10] instanceof Number){
  				A5 += ((Number)it[10]).intValue();
  			}
  			if(it[11] instanceof Number){
  				A6 += ((Number)it[11]).intValue();
  			}
  			if(it[12] instanceof Number){
  				A7 += ((Number)it[12]).intValue();
  			}
  			if(it[13] instanceof Number){
  				A8 += ((Number)it[13]).intValue();
  			}
  			if(it[14] instanceof Number){
  				A9 += ((Number)it[14]).intValue();
  			}
//  			if(it[15] instanceof Number){
//  				x13 += ((Number)it[15]).doubleValue();
//  			}
  			x13 = add(x13, it[15]);
  			
//  			if(it[16] instanceof Number){
//  				x14 += ((Number)it[16]).doubleValue();
//  			}
  			x14 = add(x14, it[16]);
  			
//  			if(it[17] instanceof Number){
//  				x15 += ((Number)it[17]).doubleValue();
//  			}
  			x15 = add(x15, it[17]);
  			
//  			if(it[18] instanceof Number){
//  				x16 += ((Number)it[18]).doubleValue();
//  			}
  			x16 = add(x16, it[18]);
  			x17 = add(x17, it[19]);
//			if(it[19] instanceof Number){
//				z13 += ((Number)it[19]).doubleValue();
//			}
			z13 = add(z13, it[20]);
			
//			if(it[20] instanceof Number){
//				z14 += ((Number)it[20]).doubleValue();
//			}
			z14 = add(z14, it[21]);
			
//			if(it[21] instanceof Number){
//				z15 += ((Number)it[21]).doubleValue();
//			}
			z15 = add(z15, it[22]);
			
//			if(it[22] instanceof Number){
//				z16 += ((Number)it[22]).doubleValue();
//			}
			z16 = add(z16, it[23]);
			z17 = add(z17, it[24]);
  			
  			if(it[25] instanceof Number){
  				A16 += ((Number)it[25]).intValue();
  			}
  			if(it[26] instanceof Number){
  				A17 += ((Number)it[26]).intValue();
  			}
  			if(it[27] instanceof Number){
  				A18 += ((Number)it[27]).intValue();
  			}
//  			if(it[26] instanceof Number){
//  				A19 += ((Number)it[26]).doubleValue();
//  			}
  			A19 = add(A19, it[28]);
  			
  			if(it[29] instanceof Number){
  				A20 += ((Number)it[29]).intValue();
  			}
  			if(it[30] instanceof Number){
  				A21 += ((Number)it[30]).intValue();
  			}
//  			if(it[29] instanceof Number){
//  				A22 += ((Number)it[29]).doubleValue();
//  			}
  			A22 = add(A22, it[31]);
  			
  			if("有".equals(it[32])){
				A23 += 1;
			}
  			
  			if(it[33] instanceof Number){
  				A24 += ((Number)it[33]).intValue();
  			}
  			if(it[34] instanceof Number){
  				A25 += ((Number)it[34]).intValue();
  			}
  				A26 = add(A26, it[35]);
//  			if(it[33] instanceof Number){
//  			}
  			if(it[36] instanceof Number){
  				A27 += ((Number)it[36]).intValue();
  			}
  			if(it[37] instanceof Number){
  				A28 += ((Number)it[37]).intValue();
  			}
  			if(it[38] instanceof Number){
  				A29 += ((Number)it[38]).intValue();
  			}
			statis.append("</tr>");
		}

		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td>");
		statis.append(df.format(A1)).append("</td><td>");
		statis.append(A2).append("</td><td>");
		statis.append(A3).append("</td><td>");
		statis.append(A4).append("</td><td>");
		statis.append(A5).append("</td><td>");
		statis.append(A6).append("</td><td>");
		statis.append(A7).append("</td><td>");
		statis.append(A8).append("</td><td>");
		statis.append(A9).append("</td><td>");
		statis.append(df.format(x13)).append("</td><td>");
		statis.append(df.format(x14)).append("</td><td>");
		statis.append(df.format(x15)).append("</td><td>");
		statis.append(df.format(x16)).append("</td><td>");
		statis.append(df.format(x17)).append("</td><td>");
		statis.append(df.format(z13)).append("</td><td>");
		statis.append(df.format(z14)).append("</td><td>");
		statis.append(df.format(z15)).append("</td><td>");
		statis.append(df.format(z16)).append("</td><td>");
		statis.append(df.format(z17)).append("</td><td>");
		statis.append(A16).append("</td><td>");
		statis.append(A17).append("</td><td>");
		statis.append(A18).append("</td><td>");
		statis.append(df.format(A19)).append("</td><td>");
		statis.append(A20).append("</td><td>");
		statis.append(A21).append("</td><td>");
		statis.append(df.format(A22)).append("</td><td>");
		statis.append(A23).append("</td><td>");
		statis.append(A24).append("</td><td>");
		statis.append(A25).append("</td><td>");
		statis.append(df.format(A26)).append("</td><td>");
		statis.append(A27).append("</td><td>");
		statis.append(A28).append("</td><td>");
		statis.append(A29).append("</td><td></td></tr>");
		return statis;
   }
	
	private final static DecimalFormat df = new DecimalFormat("0.##");
}
