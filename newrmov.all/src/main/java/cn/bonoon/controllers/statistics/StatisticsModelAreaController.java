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
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

public abstract class StatisticsModelAreaController extends AbstractPanelController{

	@Autowired
	protected ModelAreaService statisticsService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		
		Collection<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items)).addObject("batch", batch);
		event.setVmPath("statistics/model-area");
	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch){
		Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content", statistics(items));
		return "statistics/model-area-items";
	}

	//导出excel
	@RequestMapping(value = "!{key}/index.excel", method = GET)
	public void excel(HttpServletRequest request, HttpServletResponse response, String batch){
		HSSFWorkbook workbook = null;
		try{
		String _name = "广东省新农村连片示范建设工程台账簿.xls";
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
		Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
        // 声明一个工作薄  
        workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet("台账封面汇总统计");  
        
        sheet.setDefaultColumnWidth(15);  
        sheet.setDefaultRowHeight((short)(15.625*25));
        sheet.setColumnWidth(5, 5000); 
        sheet.setColumnWidth(6, 5000);
        
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
        cel.setCellValue("广东省新农村连片示范建设工程台账簿 - 建设台账封面统计");
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 38));//合并    
        
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
        HSSFCell cell8 = row.createCell(24);
        HSSFCell cell9 = row.createCell(30);
        
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
        
        
        HSSFRow row1 = sheet.createRow(4);  //第二行
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
        HSSFCell tCel110= row1.createCell(20);
        HSSFCell tCel111= row1.createCell(24);
        HSSFCell tCel112= row1.createCell(25);
        HSSFCell tCel113= row1.createCell(26);
        HSSFCell tCel114= row1.createCell(27);
        HSSFCell tCel115= row1.createCell(28);
        HSSFCell tCel116= row1.createCell(29);
        HSSFCell tCel117= row1.createCell(30);
        HSSFCell tCel118= row1.createCell(31);
        HSSFCell tCel119= row1.createCell(32);
        HSSFCell tCel120= row1.createCell(33);
        HSSFCell tCel121= row1.createCell(34);
        HSSFCell tCel122= row1.createCell(35);
        HSSFCell tCel123= row1.createCell(36);
        HSSFCell tCel124= row1.createCell(37);
        HSSFCell tCel125= row1.createCell(38);
        
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
        
        HSSFRow row2 = sheet.createRow(6);  //第三行
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
        
         
        //第一行标题
        cell.setCellValue("序号");
        cell1.setCellValue("批次[A15]");
        cell2.setCellValue("报备年度[A14]");
        cell3.setCellValue("地市");
        cell4.setCellValue("县(区)");
        cell5.setCellValue("示范片名称[A12]");
        cell6.setCellValue("建设主题名称[A13]");//7个
        
        cell7.setCellValue("（一）示范片所在县(市、区）基本情况");//19个
        cell8.setCellValue("（二）示范片总体情况");//6
        cell9.setCellValue("（三）连片建设基本情况");//9
        
        
        
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 5, 5));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 6, 6));
        
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 7, 25));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 26, 31));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 32, 41));
        
        //第二行标题
        tCell.setCellValue("农村面积");
        tCel11.setCellValue("乡镇个数");
        tCel12.setCellValue("行政村个数");
        tCel13.setCellValue("自然村个数");
        tCel14.setCellValue("村民小组数");
        tCel15.setCellValue("县人口总户数");
        tCel16.setCellValue("县农村总户数");
        tCel17.setCellValue("县总人口数");
        tCel18.setCellValue("县农村总人口数");
        
        tCel19.setCellValue("各年度城镇居民人均纯收入A10[元]");
        tCel110.setCellValue("各年度农民人均纯收入A11[元]");
        
        tCel111.setCellValue("主体建设村个数");
        tCel112.setCellValue("辐射带动村个数");
        tCel113.setCellValue("覆盖乡镇数");
        tCel114.setCellValue("示范片面积");
        tCel115.setCellValue("总户数");
        tCel116.setCellValue("总人口数");
        
        tCel117.setCellValue("主体村之间连线连片规模");
        tCel118.setCellValue("起点标志");
        tCel119.setCellValue("规划设计标识");
        tCel120.setCellValue("驿站");
        tCel121.setCellValue("绿道");
        tCel122.setCellValue("观景台（点）");
        tCel123.setCellValue("宣传介绍牌");
        tCel124.setCellValue("道路两旁美化绿化工程");
        tCel125.setCellValue("示范片连线线路走");
        
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 7, 7));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 10, 10));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 11, 11));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 12, 12));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 13, 13));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 14, 14));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 15, 15));
        
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 16, 20));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 21, 25));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 26, 26));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 25, 25));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 26, 26));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 27, 27));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 28, 28));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 29, 29));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 30, 30));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 31, 31));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 32, 32));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 33, 33));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 34, 34));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 35, 35));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 36, 36));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 37, 37));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 38, 38));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 39, 39));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 40, 40));
        
        
        //第三行标题
        sCell.setCellValue("A1[平方公里]");
        sCell1.setCellValue("A2[个]");
        sCell2.setCellValue("A3[个]");
        sCell3.setCellValue("A4[个]");
        sCell4.setCellValue("A5[个]");
        sCell5.setCellValue("A6[户]");
        sCell6.setCellValue("A7[户]");
        sCell7.setCellValue("A8[人]");
        sCell8.setCellValue("A9[人]");
        
        sCell9.setCellValue("2013年");
        sCell10.setCellValue("2014年");
        sCell11.setCellValue("2015年");
        sCell12.setCellValue("2016年");
        sCell13.setCellValue("2017年");
        sCell14.setCellValue("2013年");
        sCell15.setCellValue("2014年");
        sCell16.setCellValue("2015年");
        sCell17.setCellValue("2016年");
        sCell18.setCellValue("2017年");
        
        sCell19.setCellValue("A16[个]");
        sCell20.setCellValue("A17[个]");
        sCell21.setCellValue("A18[个]");
        sCell22.setCellValue("A19[平方公里]");
        sCell23.setCellValue("A20[户]");
        sCell24.setCellValue("A21[人]");
        
        sCell25.setCellValue("A22[公里]");
        sCell26.setCellValue("A23[无或有]");
        sCell27.setCellValue("A24[个]");
        sCell28.setCellValue("A25[个]");
        sCell29.setCellValue("A26[公里]");
        sCell30.setCellValue("A27[个]");
        sCell31.setCellValue("A28[块]");
        sCell32.setCellValue("A29[宗]");
        sCell33.setCellValue("A30");
        
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 7, 7));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 10, 10));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 11, 11));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 12, 12));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 13, 13));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 14, 14));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 15, 15));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 16, 16));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 17, 17));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 18, 18));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 19, 19));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 20, 20));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 21, 21));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 22, 22));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 23, 23));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 24, 24));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 25, 25));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 26, 26));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 27, 27));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 28, 28));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 29, 29));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 30, 30));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 31, 31));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 32, 32));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 33, 33));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 34, 34));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 35, 35));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 36, 36));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 37, 37));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 38, 38));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 39, 39));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 40, 40));


        statistics(items, mStyle, style, sheet);
       
        	
        workbook.write(response.getOutputStream());
        
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void statistics(Collection<Object[]> items, HSSFCellStyle mStyle, HSSFCellStyle style, HSSFSheet sheet) {
		//显示items里面的数据
        int i=7, j = 1, k = 0;
        
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
				if(null!=obj){
				  statis.append("<td>").append(obj).append("</td>");
				}else{
					statis.append("<td></td>");
				}
			}
			statis.append("</tr>");
		}
		return statis;
	}

}
