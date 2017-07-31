package cn.bonoon.controllers.showstatistics.project;

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

public class TotalInvestmentProjectCompleteExcel extends AbstractExcelUtil {

	private Collection<Object[]> items;
	private String batch;

	public TotalInvestmentProjectCompleteExcel() {
	}

	public TotalInvestmentProjectCompleteExcel(Collection<Object[]> items, String batch) {
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
		String sheetName = "工程项目累计完成总投入资金" + "(批次" + batch + ")";
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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 10));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 10, 12));
		this.setCellValueAndCellStyle(0, 0, sheetName, titleStyle);
		this.setCellValueAndCellStyle(1, 0, "序号", style);
		this.setCellValueAndCellStyle(1, 1, "县区", style);
		this.setCellValueAndCellStyle(1, 2, "累计完成总投入资金", style);
		this.setCellValueAndCellStyle(1, 11, "群众投工投劳情况", style);
		String[] tableColumnName1 = new String[] { "合计", "中央",
				"省级新农村连片示范工程建设补助资金", "其他省级财政资金", "市级财政资金", "县级财政资金", "社会",
				"群众自筹", "其他", "投工数", "折资额" };
		for (int i = 2; i < tableColumnName1.length + 2; i++) {
			sheet.addMergedRegion(new CellRangeAddress(2, 2, i, i));
			this.setCellValueAndCellStyle(2, i, tableColumnName1[i - 2], style);
		}
		String[] tableColumnName2 = new String[] { "E16[万元]", "E17[万元]",
				"E18[万元]", "E19[万元]", "E201[万元]", "E202[万元]", "E21[万元]",
				"E211[万元]", "E22[万元]", "E23[万元]", "E23[万元]" };
		for (int i = 2; i < tableColumnName2.length + 2; i++) {
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
