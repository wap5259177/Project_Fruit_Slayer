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
import cn.bonoon.core.IndustryAreaService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

public abstract class StatisticsAreaIndustryController extends AbstractPanelController{

	@Autowired
	protected IndustryAreaService statisticsService;
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		int bi = model.intParameter("batch");
		String batch = BatchHelper.getValue(bi);
		event.setVmPath("statistics/area-industry");
		Collection<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items)).addObject("batch", batch);;
	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch){
		Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content", statistics(items));
		return "statistics/area-industry-items";
	}
	
	@RequestMapping(value = "!{key}/index.excel", method = GET)
	public void excel(HttpServletRequest request, HttpServletResponse response, String batch){
		HSSFWorkbook workbook = null;
		try{
		String _name = "产业发展情况汇总统计.xls";
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
		Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
		 // 声明一个工作薄  
        workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet("产业发展情况汇总统计");  
        
        sheet.setDefaultColumnWidth(15);  
        sheet.setDefaultRowHeight((short)(15.625*25));
        sheet.setColumnWidth(5, 5000); 
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(23, 5000);
        
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
        cel.setCellValue("广东省新农村连片示范建设工程台账簿 - 产业发展情况汇总统计");
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 23));
        
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
        HSSFCell cell7 = row.createCell(13);
        
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
        cell7.setCellValue("主要经营范围");
        
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 5, 5));
        sheet.addMergedRegion(new CellRangeAddress(3, 6, 6, 6));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 7, 12));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 13, 19));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 20, 23));
        
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
        
        tCell.setCellValue("专业合作社个数");
        tCel11.setCellValue("属于主体建设村的个数");
        tCel12.setCellValue("属于辐射带动村的个数");
        tCel13.setCellValue("成员总户数");
        tCel14.setCellValue("带动非成员户数");
        tCel15.setCellValue("注册资金");
        tCel16.setCellValue("种植业个数");
        tCel17.setCellValue("畜牧业个数");
        tCel18.setCellValue("渔业个数");
        tCel19.setCellValue("林业个数");
        tCel110.setCellValue("服务业个数");
        tCel111.setCellValue("手工业个数");
        tCel112.setCellValue("其他个数");
        tCel113.setCellValue("拥有注册商标数");
        tCel114.setCellValue("拥有使用农产品质量认证数");
        tCel115.setCellValue("无公害农产品产地认定个数");
        tCel116.setCellValue("特色产品名称");
        
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 7, 7));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 10, 10));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 11, 11));
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
        
        //第三行
        HSSFRow row2 = sheet.createRow(6);  
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
        
      //第三行标题
        sCell.setCellValue("D4[个]");
        sCell1.setCellValue("D3[个]");
        sCell2.setCellValue("D4[个]");
        sCell3.setCellValue("D7[户]");
        sCell4.setCellValue("D8[户]");
        sCell5.setCellValue("D9[万元]");
        sCell6.setCellValue("D10[个]");
        sCell7.setCellValue("D10[个]");
        sCell8.setCellValue("D10[个]");
        sCell9.setCellValue("D10[个]");
        sCell10.setCellValue("D10[个]");
        sCell11.setCellValue("D10[个]");
        sCell12.setCellValue("D10[个]");
        sCell13.setCellValue("D12[个]");
        sCell14.setCellValue("D13[个]");
        sCell15.setCellValue("D14[个]");
        sCell16.setCellValue("D15[列表]");
        
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
			statis.append("<tr><td>").append(i++).append("</td>");
			for(Object obj : it){
				statis.append("<td>").append(obj).append("</td>");
			}
			statis.append("</tr>");
		}
		return statis;
	}
}
