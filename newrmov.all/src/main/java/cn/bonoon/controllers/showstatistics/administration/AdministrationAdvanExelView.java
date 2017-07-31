package cn.bonoon.controllers.showstatistics.administration;

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

public class AdministrationAdvanExelView extends AbstractExcelView{

	private Collection<Object[]> items;
	private String batchName;
	
	public AdministrationAdvanExelView(Collection<Object[]> items,String batchName) {
		this.batchName=batchName;
		this.items=items;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			String _name = "广东省资源优势情况("+this.batchName+").xls";
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
			  
			// 生成一个表格  
			HSSFSheet sheet = workbook.createSheet("广东省资源优势情况("+this.batchName+")");  

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
			sheet.setColumnWidth(2, 8000);
			sheet.setDefaultRowHeightInPoints(20);
			
			//定义总共的列数
			int rows = 10;

			//标题的单元格
			sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, rows));
			//属性的单元格
			sheet.addMergedRegion(new CellRangeAddress(3,5,0,0));
			sheet.addMergedRegion(new CellRangeAddress(3,5,1,1));
			sheet.addMergedRegion(new CellRangeAddress(3,5,2,2));
			sheet.addMergedRegion(new CellRangeAddress(3,3,3,rows));
		

			//创建标题的行跟列
			getCell(sheet, 0, 0).setCellValue("广东省新农村连片示范建设工程台账簿 - 建设台账封面统计");
			getCell(sheet, 0, 0).setCellStyle(titleStyle);
		

			String[] column_name1 = {"序号","县(区)","行政村名"};
			for(int i=0;i<column_name1.length;i++){
				getCell(sheet, 3, i).setCellStyle(style);
				getCell(sheet, 3, i).setCellValue(column_name1[i]);
			}

			getCell(sheet, 3, 3).setCellValue("资源优势情况");
			getCell(sheet, 3, 3).setCellStyle(style);

			
			String[] column_name2 = {"人文历史","自然生态","民居风貌","乡村旅游","特色产业","渔业渔港","其他","现有的或正在打造的旅游景点或节点个数"};
			for(int i=0;i<column_name2.length;i++){
				getCell(sheet, 4, 3+i).setCellValue(column_name2[i]);
				getCell(sheet, 4, 3+i).setCellStyle(style);
			}
			
			String[] column_name3 = {"B31[个]","B32[个]","B33[个]","B34[个]","B35[个]","B36[个]","B37[个]","B38[个]"};
			for(int i=0;i<column_name3.length;i++){
				getCell(sheet, 5, 3+i).setCellValue(column_name3[i]);
				getCell(sheet, 5, 3+i).setCellStyle(style);
				sheet.setColumnWidth(3+i, 5000);
				if(3+i>9){
					sheet.setColumnWidth(3+i, 15000);
				}
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
		Integer b31=0,b32=0,b33=0,b34=0,b35=0,b36=0,b37=0,b38=0;
		
		for(Object[] it:items){
			if("是".equals(it[30]))b31+=1;
			if("是".equals(it[31]))b32+=1;
			if("是".equals(it[32]))b33+=1;
			if("是".equals(it[33]))b34+=1;
			if("是".equals(it[34]))b35+=1;
			if("是".equals(it[35]))b36+=1;
			if("是".equals(it[36]))b37+=1;
			if(it[37] instanceof Number) b38 += ((Number)it[14]).intValue();
			Object[] its = {number,null!=it[3]?it[3]:"",null!=it[6]?it[6]:"",null!=it[30]?it[30]:"",null!=it[31]?it[31]:"",null!=it[32]?it[32]:"",null!=it[33]?it[33]:"",null!=it[34]?it[34]:"",null!=it[35]?it[35]:"",null!=it[36]?it[36]:"",null!=it[37]?it[37]:""};
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
		Integer[] count_array={b31,b32,b33,b34,b35,b36,b37,b38};
		getCell(sheet, count,1).setCellStyle(valueStyle);
		getCell(sheet, count,1).setCellValue("全省汇总:");
		
		for(int i=0;i<count_array.length;i++){
			getCell(sheet, count,3+i).setCellStyle(valueStyle);
			getCell(sheet, count,3+i).setCellValue(count_array[i]);
		}
		
	}

}
