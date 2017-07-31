package cn.bonoon.controllers.showstatistics.administration;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.bonoon.controllers.showstatistics.AbstractExcelUtil;

public class RuralPublicServiceSituationExcel extends AbstractExcelUtil {
	private Collection<Object[]> items;
	private String batch;

	public RuralPublicServiceSituationExcel() {
	}

	public RuralPublicServiceSituationExcel(Collection<Object[]> items,
			String batch) {
		this.items = items;
		this.batch = batch;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (items == null) {
			throw new Exception("没数据");
		}
		String sheetName = "行政村农村公共服务情况" + "(批次" + batch
				+ ")";

		HSSFSheet sheet = workbook.createSheet(sheetName);
		this.setSheet(sheet);
		String name = sheetName + ".xls";
		response.setHeader("Content-disposition", "attachment;filename="
				+ new String(name.getBytes("gb2312"), "ISO8859-1"));
		// 标题的样式
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
		HSSFFont titleFont = workbook.createFont();
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// ?
		titleFont.setFontHeightInPoints((short) 22);
		titleStyle.setFont(titleFont);

		// 表头的样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);
		style.setWrapText(true);// 自动换行
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 13));
		this.setCellValueAndCellStyle(0, 0, sheetName, titleStyle);
		this.setCellValueAndCellStyle(1, 0, "序号", style);
		this.setCellValueAndCellStyle(1, 1, "县区", style);
		this.setCellValueAndCellStyle(1, 2, "行政村名", style);
		this.setCellValueAndCellStyle(1, 3, "", style);
		this.setCellValueAndCellStyle(1, 4, "农村公共服务情况", style);
		String[] tableColumnName1 = new String[] { "文化活动场所个数", "文化活动场所面积", "乡村公园个数", "乡村公园面积", "文体广场个数", "文体广场所面积" ,"村级卫生站个数", "村级卫生站面积", "乡村公厕个数", "乡村公厕面积", "是否建立统一的村级公共服务管理平台" };
		for (int i = 3; i < 14; i++) {
			
			sheet.addMergedRegion(new CellRangeAddress(2, 2, i, i));
			this.setCellValueAndCellStyle(2, i, tableColumnName1[i - 3], style);
			
		}

		String[] tableColumnName2 = new String[] { "B39[个]",  "B39[平方米]" , "B40[个]" , "B40[平方米]" , "B41[个]" , "B41[平方米]"  ,"B42[个]" , "B42[平方米]" , "B43[个]" , "B43[平方米]" , "B44[个]"   };
		for (int i = 3; i < 14; i++) {
			sheet.addMergedRegion(new CellRangeAddress(3, 3, i, i));
			this.setCellValueAndCellStyle(3, i, tableColumnName2[i - 3], style);
		}

		HSSFCellStyle valueStyle = workbook.createCellStyle();
		valueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		font = workbook.createFont();
		// font.setBoldweight(HSSFFont.COLOR_NORMAL);
		font.setFontHeightInPoints((short) 12);
		font.setFontName("宋体");

		valueStyle.setFont(font);

		summaryStatistics(items);
		
		//对第14列做统计 汇总统计需要用
	List<Object[]> list=	(List<Object[]>)items;
	int sum=0;
		for(int i=0;i<list.size();i++){
			
			if(i==list.size()-1){
				break;
			};
			if(list.get(i)[12].toString().equals("是")){	
			sum+=1;
			}
		}
		list.get(list.size()-1)[12]=sum;
		
		
		setDataAndHSSFSheet(items, sheet, valueStyle, 4);
		Map<Integer,Integer> hm=new HashMap<Integer,Integer>(); 
		hm.put(0, 2700);
		hm.put(2,6000);
		hm.put(13,6000);
		setColumWidth(26,hm);

	}
	public Collection<Object[]> getItems() {
		return items;
	}

	public void setItems(Collection<Object[]> items) {
		this.items = items;
	}

	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}

}
