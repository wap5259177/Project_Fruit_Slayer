package cn.bonoon.controllers.showstatistics.newrural;

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

public class OldHouseRenovationExcel extends AbstractExcelUtil {
	private Collection<Object[]> items;
	private String batch;

	public OldHouseRenovationExcel() {
	}

	public OldHouseRenovationExcel(Collection<Object[]> items, String batch) {
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
		String sheetName = "主体村农村旧房整治情况" + "(批次" + batch + ")";
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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 11));
		this.setCellValueAndCellStyle(0, 0, sheetName, titleStyle);
		this.setCellValueAndCellStyle(1, 0, "序号", style);
		this.setCellValueAndCellStyle(1, 1, "县区", style);
		this.setCellValueAndCellStyle(1, 2, "行政村名", style);
		this.setCellValueAndCellStyle(1, 3, "自然村名", style);
		this.setCellValueAndCellStyle(1, 4, "农村旧房整治情况", style);

		String[] tableColumnName1 = new String[] { "无价值旧村旧房拆除", "统一拆旧建新",
				"古建筑保护", "是否编制旧房整治改造规划", "是否提供民居住宅设计标准图", "农家（乡村）旅馆-规划建设",
				"农家（乡村）旅馆-完成建设", "是否统一民居外立面风貌" };
		for (int i = 4; i < 12; i++) {
			sheet.addMergedRegion(new CellRangeAddress(2, 2, i, i));
			this.setCellValueAndCellStyle(2, i, tableColumnName1[i - 4], style);
		}
		String[] tableColumnName2 = new String[] { "B24[间]", "B25[间]",
				"B26[间]", "B27", "B28", "B29[间]", "B29[间]", "B30" };
		for (int i = 4; i < 12; i++) {
			sheet.addMergedRegion(new CellRangeAddress(3, 3, i, i));
			this.setCellValueAndCellStyle(3, i, tableColumnName2[i - 4], style);
		}
		HSSFCellStyle valueStyle = workbook.createCellStyle();
		valueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		font = workbook.createFont();
		// font.setBoldweight(HSSFFont.COLOR_NORMAL);
		font.setFontHeightInPoints((short) 12);
		font.setFontName("宋体");

		valueStyle.setFont(font);

		summaryStatistics(items);
		// 对第最后一列做统计 汇总统计需要用
		List<Object[]> list = (List<Object[]>) items;
		int sum = 0;
		int sum2 = 0;
		int sum3 = 0;
		for (int i = 0; i < list.size(); i++) {

			if (i == list.size() - 1) {
				break;
			}
			if (list.get(i)[6].toString().equals("是")) {
				sum += 1;
			}
			if (list.get(i)[7].toString().equals("是")) {
				sum2 += 1;
			}
			if (list.get(i)[10].toString().equals("是")) {
				sum3 += 1;
			}

		}
		list.get(list.size() - 1)[6] = sum;
		list.get(list.size() - 1)[7] = sum2;
		list.get(list.size() - 1)[10] = sum3;

		// excel显示集
		setDataAndHSSFSheet(items, sheet, valueStyle, 4);

		Map<Integer, Integer> hm = new HashMap<Integer, Integer>();
		hm.put(0, 2700);
		hm.put(1, 6000);
		hm.put(2, 6000);
		hm.put(3, 6000);
		setColumWidth(17, hm);

	}

}
