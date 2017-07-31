package cn.bonoon.controllers.statistics;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.core.ResidentialEnvironmentService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

public abstract class StatisticsResidentialEnvironmentController extends AbstractPanelController{

	@Autowired
	protected ResidentialEnvironmentService statisticsService;
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
//		String batch = model.getParameter("batch");
		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		event.setVmPath("statistics/area-residential");
		List<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items)).addObject("batch", batch);;
	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch){
		List<Object[]> items = getItems(getUser(), batch);
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content", statistics(items));
		return "statistics/area-residential-items";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "!{key}/index.excel", method = GET)
	public void excel(HttpServletRequest request, HttpServletResponse response, String batch){
		HSSFWorkbook workbook = null;
		try{
		String _name = "完成人居环境综合整治自然村汇总统计.xls";
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
		Collection<Object[]> items = getItems(getUser(), batch);
		 // 声明一个工作薄  
        workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet("完成人居环境综合整治自然村汇总统计");  
        
        sheet.setDefaultColumnWidth((short) 15);  
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
        cel.setCellValue("广东省新农村连片示范建设工程台账簿 - 完成人居环境综合整治的自然村汇总统计");
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 10));
        
        cel.setCellStyle(style1); 
        
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
        
        cell.setCellStyle(style); 
        cell1.setCellStyle(style); 
        cell2.setCellStyle(style); 
        cell3.setCellStyle(style); 
        cell4.setCellStyle(style); 
        cell5.setCellStyle(style); 
        cell6.setCellStyle(style); 
        cell7.setCellStyle(style2); 
        
      //第一行标题
        cell.setCellValue("序号");
        cell1.setCellValue("批次[A15]");
        cell2.setCellValue("报备年度[A14]");
        cell3.setCellValue("地市");
        cell4.setCellValue("县(区)");
        cell5.setCellValue("示范片名称[A12]");
        cell6.setCellValue("建设主题名称[A13]");
        cell7.setCellValue("年度");
        
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 5, 5));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 6, 6));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 7, 10));
        
        //第二行
        HSSFRow row1 = sheet.createRow(4);  //第二行
        HSSFCell tCell = row1.createCell(7);
        HSSFCell tCel11= row1.createCell(8);
        HSSFCell tCel12= row1.createCell(9);
        HSSFCell tCel13= row1.createCell(10);
        
        tCell.setCellStyle(style); 
        tCel11.setCellStyle(style); 
        tCel12.setCellStyle(style); 
        tCel13.setCellStyle(style);
        
      //第二行标题
        tCell.setCellValue("2014年");
        tCel11.setCellValue("2015年");
        tCel12.setCellValue("2016年");
        tCel13.setCellValue("2017年");
        
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 7, 7));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 10, 10));
        
       
      //第三行
        HSSFRow row2 = sheet.createRow(6);  //第三行
        HSSFCell sCell = row2.createCell(7);
        HSSFCell sCell1 = row2.createCell(8);
        HSSFCell sCell2 = row2.createCell(9);
        HSSFCell sCell3 = row2.createCell(10);
        
        sCell.setCellStyle(style);
        sCell1.setCellStyle(style);
        sCell2.setCellStyle(style);
        sCell3.setCellStyle(style);
        
      //第三行标题
        sCell.setCellValue("村个数");
        sCell1.setCellValue("村个数");
        sCell2.setCellValue("村个数");
        sCell3.setCellValue("村个数");
        
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 7, 7));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 10, 10));


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

	/**
	 * 已经处理batch的值
	 * @param opt
	 * @param batch
	 * @return
	 */
	protected abstract List<Object[]> getItems(IOperator opt, String batch);
	
	protected StringBuilder statistics(List<Object[]> items){
		StringBuilder statis = new StringBuilder();
		int i = 1;
		for(Object[] it : items){
			statis.append("<tr><td>").append(i++).append("</td>");
			for(Object obj : it){
				statis.append("<td>").append(obj).append("</td>");
			}
			statis.append("</tr>");
		}
		return statis;
	}
}
