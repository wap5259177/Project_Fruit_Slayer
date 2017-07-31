package cn.bonoon.controllers.showstatistics.modelarea;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.bonoon.controllers.showstatistics.AbstractExcelUtil;

public class LedgerBookExcel extends AbstractExcelUtil {
	private Collection<Object[]> items;
	private String batch;

	public LedgerBookExcel() {
	}

	public LedgerBookExcel(Collection<Object[]> items,
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
		String sheetName = "广东省示范片总体情况" + "(批次" + batch
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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 9));
	
		this.setCellValueAndCellStyle(0, 0, sheetName, titleStyle);
		this.setCellValueAndCellStyle(1, 0, "序号", style);
		this.setCellValueAndCellStyle(1, 1, "县区", style);
		this.setCellValueAndCellStyle(1, 2, "示范片总体情况", style);
		
		String[] tableColumnName1 = new String[] { "主体村个数(行政村)", "其中主体自然村个数", "非主体村个数（行政村）", "其中非主体自然村个数", "覆盖乡镇数", "示范片面积" ,"总户数", "总人口数" };
		for (int i = 2; i < 10; i++) {
			
			sheet.addMergedRegion(new CellRangeAddress(2, 2, i, i));
			this.setCellValueAndCellStyle(2, i, tableColumnName1[i - 2], style);
			
		}
		String titleVar="[个]";
		String[] tableColumnName2 = new String[] { titleVar,  titleVar , titleVar , titleVar , "A18[个]" , "A19[平方公里]"  ,"A20[户]" , "A21[人]"  };
		for (int i = 2; i < 10; i++) {
			sheet.addMergedRegion(new CellRangeAddress(3, 3, i, i));
			this.setCellValueAndCellStyle(3, i, tableColumnName2[i - 2], style);
		}

		HSSFCellStyle valueStyle = workbook.createCellStyle();
		valueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		font = workbook.createFont();
		// font.setBoldweight(HSSFFont.COLOR_NORMAL);
		font.setFontHeightInPoints((short) 12);
		font.setFontName("宋体");

		valueStyle.setFont(font);

		summaryStatistics(items);
		setDataAndHSSFSheet(items, sheet, valueStyle, 4);
		Map<Integer,Integer> hm=new HashMap<Integer,Integer>(); 
		hm.put(0, 2700);
		hm.put(1,6000);
		
		setColumWidth(10,hm);
	}

}
