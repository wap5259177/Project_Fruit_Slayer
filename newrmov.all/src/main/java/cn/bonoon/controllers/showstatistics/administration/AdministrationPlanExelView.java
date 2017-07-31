package cn.bonoon.controllers.showstatistics.administration;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class AdministrationPlanExelView extends AbstractExcelView{

	private Collection<Object[]> items;
	private String batchName;
	
	public AdministrationPlanExelView(Collection<Object[]> items,String batchName) {
		this.batchName=batchName;
		this.items=items;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			String _name = "广东省规划进展情况("+this.batchName+").xls";
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
			  
			// 生成一个表格  
			HSSFSheet sheet = workbook.createSheet("广东省规划进展情况("+this.batchName+")");  

			// 标题的样式
			HSSFCellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
			titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
			
			//设置标题字体
			HSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// ?
			titleFont.setFontHeightInPoints((short) 22);
			titleStyle.setFont(titleFont);

			// 设置属性样式
			HSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
			
			//设置属性字体
			HSSFFont font = workbook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setFontHeightInPoints((short) 12);
			style.setFont(font);
			style.setWrapText(true);// 自动换行
			
			//设置单元格的宽度
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 6500);
			sheet.setDefaultRowHeightInPoints(20);
			
			//定义总共的列数
			int rows = 9;

			//标题的单元格
			sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, rows));
			//属性的单元格
			sheet.addMergedRegion(new CellRangeAddress(3,5,0,0));
			sheet.addMergedRegion(new CellRangeAddress(3,5,1,1));
			sheet.addMergedRegion(new CellRangeAddress(3,5,2,2));
			sheet.addMergedRegion(new CellRangeAddress(3,3,4,5));
			sheet.addMergedRegion(new CellRangeAddress(3,3,6,rows));
			
					
			//创建标题的行跟列
			getCell(sheet, 0, 0).setCellValue("广东省新农村连片示范建设工程台账簿 - 建设台账封面统计");
			getCell(sheet, 0, 0).setCellStyle(titleStyle);

			String[] column_name1 = {"序号","县(区)","行政村名"};
			for(int i=0;i<column_name1.length;i++){
				getCell(sheet, 3, i).setCellStyle(style);
				getCell(sheet, 3, i).setCellValue(column_name1[i]);
			}
			
			getCell(sheet, 3, 3).setCellStyle(style);
			getCell(sheet, 3, 3).setCellValue("（四）挂点县领导");
			getCell(sheet, 3, 4).setCellStyle(style);
			getCell(sheet, 3, 4).setCellValue("（五）工作小组");
			getCell(sheet, 3, 6).setCellStyle(style);
			getCell(sheet, 3, 6).setCellValue("（六）规划进展");
			
			
			String[] column_name2 = {"是否有挂点县领导","是否有工作小组","工作小组总人数","是否建立规划专家指导组","是否完成总体规划","是否完成连线连片规划","是否完成村庄深度规划设计"};
			for(int i=0;i<column_name2.length;i++){
				getCell(sheet, 4, i+3).setCellStyle(style);
				getCell(sheet, 4, i+3).setCellValue(column_name2[i]);
			}
			
			
			String[] column_name3 = {"B45[个]","B46[个]","B47[个]","B48[个]","B49[个]","B50[个]","B51[个]"};
			for(int i=0;i<column_name3.length;i++){
				getCell(sheet, 5, i+3).setCellStyle(style);
				getCell(sheet, 5, i+3).setCellValue(column_name3[i]);
				sheet.setColumnWidth(3+i, 8000);
				
			}
						
			setDataForModeaAreaInfo(items, sheet, style, rows);		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//下面开始设置数据
	public void setDataForModeaAreaInfo(Collection<Object[]> items,
			HSSFSheet sheet,HSSFCellStyle valueStyle,int rows) {
	    
		//rows单元格总共的列数
		
		Integer number = 1;//序号
		int  seq = 0;//控制行数 设置完一行的数据到下一行的数据要++
		int row_start=6;//从第6行开始设置数据
		Integer b45=0,b46=0,b47=0,b48=0,b49=0,b50=0,b51=0;
		
		for(Object[] it:items){
			if("是".equals(it[49]))b45+=1;
			if("是".equals(it[50]))b46+=1;
			if(it[51] instanceof Number)b47+=((Number)it[51]).intValue();
			if("是".equals(it[52]))b48+=1;
			if("是".equals(it[53]))b49+=1;
			if("是".equals(it[54]))b50+=1;
			if("是".equals(it[55]))b51+=1;
			Object[] its = {number,null!=it[3]?it[3]:"",null!=it[6]?it[6]:"",null!=it[49]?it[49]:"",null!=it[50]?it[50]:"",null!=it[51]?it[51]:"",null!=it[52]?it[52]:"",null!=it[53]?it[53]:"",null!=it[54]?it[54]:"",null!=it[55]?it[55]:""};
			for(int i=0;i<=rows;i++){
				getCell(sheet, row_start+seq,i).setCellStyle(valueStyle);
				getCell(sheet, row_start+seq,i).setCellValue(its[i].toString());	
			}
			seq++;
			number++;
		}
		
		//下面还要统计总数
		int count = number+row_start-1;//统计总数开始的行数
		//统计出来的数组
		Integer[] count_array={b45,b46,b47,b48,b49,b50,b51};
		getCell(sheet, count,1).setCellStyle(valueStyle);
		getCell(sheet, count,1).setCellValue("全省汇总:");
		
		for(int i=0;i<count_array.length;i++){
			getCell(sheet, count,3+i).setCellStyle(valueStyle);
			getCell(sheet, count,3+i).setCellValue(count_array[i]);
		}
		
	}

}
