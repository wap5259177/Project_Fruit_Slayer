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

public class AllAdRuralSituationStatisticsExcel extends AbstractExcelUtil {
	private Collection<Object[]> items;
	private String batch;

	public AllAdRuralSituationStatisticsExcel() {
	}

	public AllAdRuralSituationStatisticsExcel(Collection<Object[]> items,
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
		String sheetName = "主体村基础设施建设和环境卫生整治统计" + "(批次" + batch + ")";
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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 14));
		this.setCellValueAndCellStyle(0, 0, sheetName, titleStyle);
		this.setCellValueAndCellStyle(1, 0, "序号", style);
		this.setCellValueAndCellStyle(1, 1, "县区", style);
		this.setCellValueAndCellStyle(1, 2, "行政村名", style);
		this.setCellValueAndCellStyle(1, 3, "自然村名", style);
		this.setCellValueAndCellStyle(1, 4, "（二）基础设施建设和环境卫生整治情况", style);

		String[] tableColumnName1 = new String[] { "村内道路和入户路硬底化率", "是否通自来水",
				"开展农田水利基础设施和现代渔港建设", "整治小山塘、小灌区、小水坡、小泵站和小堤防",
				"配套建设高标准基本农田、标准鱼塘", "是否已完成环境卫生整治", "是否已开展村庄垃圾治理",
				"是否建立村保洁队伍村个数", "建立村保洁队伍保洁员人数 ", "村民使用卫生厕所户数", "建立污水处理设施村个数" };
		for (int i = 4; i < tableColumnName1.length + 4; i++) {
			sheet.addMergedRegion(new CellRangeAddress(2, 2, i, i));
			this.setCellValueAndCellStyle(2, i, tableColumnName1[i - 4], style);
		}
		String[] tableColumnName2 = new String[] { "B14[公里]", "B15", "B16[宗]",
				"B17[个]", "B18[亩]", "B19", "B20", "B21", "B21[人]", "B22[户]",
				"B23" };
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
		// 对第14列做统计 汇总统计需要用
		List<Object[]> list = (List<Object[]>) items;
		int sum = 0;
		int sum2 = 0;
		int sum3 = 0;
		int sum4 = 0;
		int sum5 = 0;
		for (int i = 0; i < list.size(); i++) {

			if (i == list.size() - 1) {
				break;
			}
			if (list.get(i)[4].toString().equals("是")) {
				sum += 1;
			}
			if (list.get(i)[8].toString().equals("是")) {
				sum2 += 1;
			}
			if (list.get(i)[9].toString().equals("是")) {
				sum3 += 1;
			}
			if (list.get(i)[10].toString().equals("是")) {
				sum4 += 1;
			}
			if (list.get(i)[13].toString().equals("是")) {
				sum5 += 1;
			}
		}
		list.get(list.size() - 1)[4] = sum;
		list.get(list.size() - 1)[8] = sum2;
		list.get(list.size() - 1)[9] = sum3;
		list.get(list.size() - 1)[10] = sum4;
		list.get(list.size() - 1)[13] = sum5;

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
