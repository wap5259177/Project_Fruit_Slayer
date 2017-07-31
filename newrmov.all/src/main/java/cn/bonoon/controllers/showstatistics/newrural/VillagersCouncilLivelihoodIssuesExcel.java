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

public class VillagersCouncilLivelihoodIssuesExcel extends AbstractExcelUtil {

	private Collection<Object[]> items;
	private String batch;

	public VillagersCouncilLivelihoodIssuesExcel() {
	}

	public VillagersCouncilLivelihoodIssuesExcel(Collection<Object[]> items, String batch) {
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
		String sheetName = "主体村村民理事会和民生问题调查梳理情况统计" + "(批次" + batch + ")";
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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 6));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 10));
		this.setCellValueAndCellStyle(0, 0, sheetName, titleStyle);
		this.setCellValueAndCellStyle(1, 0, "序号", style);
		this.setCellValueAndCellStyle(1, 1, "县区", style);
		this.setCellValueAndCellStyle(1, 2, "行政村名", style);
		this.setCellValueAndCellStyle(1, 3, "自然村名", style);
		this.setCellValueAndCellStyle(1, 4, "（四）村民理事会", style);
		this.setCellValueAndCellStyle(1, 7, "（五）民生问题调查梳理情况", style);
		String[] tableColumnName1 = new String[] { "是否建立村民理事会", "理事会成员数",
				"制定村规民约和章程村个数", "完成自然村数", "梳理出项目数", "排查出的矛盾纠纷数", "其中已化解数" };
		for (int i = 4; i < tableColumnName1.length + 4; i++) {
			sheet.addMergedRegion(new CellRangeAddress(2, 2, i, i));
			this.setCellValueAndCellStyle(2, i, tableColumnName1[i - 4], style);
		}
		String[] tableColumnName2 = new String[] { "B53", "B54[人]", "B56", "",
				"", "", "" };
		for (int i = 4; i < tableColumnName2.length + 4; i++) {
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

		for (int i = 0; i < list.size(); i++) {

			if (i == list.size() - 1) {
				break;
			}
			if (list.get(i)[3].toString().equals("是")) {
				sum += 1;
			}
			if (list.get(i)[5].toString().equals("是")) {
				sum2 += 1;
			}

		}
		list.get(list.size() - 1)[3] = sum;
		list.get(list.size() - 1)[5] = sum2;

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
