package cn.bonoon.controllers.showstatistics.modelarea;

import static cn.bonoon.util.DoubleHelper.add;

import java.util.Collection;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.bonoon.controllers.showstatistics.AbstractExcelUtil;

public class ModelAreaBasicSituationInCountyExcel extends AbstractExcelUtil {
	private final Collection<Object[]> items;
	private final String whichBatch;

	public ModelAreaBasicSituationInCountyExcel(Collection<Object[]> items,
			String whichBatch) {
		this.items = items;
		this.whichBatch = whichBatch;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 这里文件名用了,号下载没反应
		// String sheetName = "广东省示范片所在县(市,区)基本情况统计表";
		String sheetName = "示范片所在县(市、区)基本情况统计表" + "(批次" + whichBatch + ")";

		HSSFSheet sheet = workbook.createSheet(sheetName);
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

		// 设置单元格的宽度
		for (int i = 0; i < 23; i++) {
			if (i == 0) {
				sheet.setColumnWidth(i, 2700);
				continue;
			}
			if (i == 1) {
				sheet.setColumnWidth(i, 9000);
				continue;
			}
			sheet.setColumnWidth(i, 4000);

		}
		// 第0行
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 20));// 行从哪个下标起，到哪个下标止
																	// ：含头含尾，列：从哪个下标起，到哪个下标止

		// 第1行
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));// 序号
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));// 县(区)
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 20));// 示范片所在县(市、区）基本情况
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 2));// 农村面积
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 3));// 乡镇个数
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 4));// 行政村个数
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 5));// 自然村个数
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 6, 6));// 村民小组数
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 7, 7));// 县人口总户数
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 8, 8));// 县农村总户数
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 9, 9));// 县总人口数
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 10, 10));// 县农村总人口数
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 11, 15));// 各年度城镇居民人均纯收入A10[元]
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 16, 20));// 民各年度农民人均纯收入A11[元]

		sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 4));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 5));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 6, 6));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 7, 7));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 8, 8));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 9, 9));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 10, 10));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 11, 11));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 12, 12));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 13, 13));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 14, 14));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 15, 15));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 16, 16));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 17, 17));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 18, 18));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 19, 19));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 20, 20));
		// 开始设置表头列名,样式

		getCell(sheet, 0, 0).setCellValue(sheetName);
		getCell(sheet, 0, 0).setCellStyle(titleStyle);
		getCell(sheet, 1, 0).setCellValue("序号");
		getCell(sheet, 1, 0).setCellStyle(style);
		getCell(sheet, 1, 1).setCellValue("县(区)");
		getCell(sheet, 1, 1).setCellStyle(style);
		getCell(sheet, 1, 2).setCellValue("示范片所在县(市、区）基本情况");
		getCell(sheet, 1, 2).setCellStyle(style);
		getCell(sheet, 2, 2).setCellValue("农村面积");
		getCell(sheet, 2, 2).setCellStyle(style);
		getCell(sheet, 2, 3).setCellValue("乡镇个数");
		getCell(sheet, 2, 3).setCellStyle(style);
		getCell(sheet, 2, 4).setCellValue("行政村个数 ");
		getCell(sheet, 2, 4).setCellStyle(style);
		getCell(sheet, 2, 5).setCellValue("自然村个数 ");
		getCell(sheet, 2, 5).setCellStyle(style);
		getCell(sheet, 2, 6).setCellValue("村民小组数");
		getCell(sheet, 2, 6).setCellStyle(style);
		getCell(sheet, 2, 7).setCellValue("县人口总户数");
		getCell(sheet, 2, 7).setCellStyle(style);
		getCell(sheet, 2, 8).setCellValue("县农村总户数");
		getCell(sheet, 2, 8).setCellStyle(style);
		getCell(sheet, 2, 9).setCellValue("县总人口数");
		getCell(sheet, 2, 9).setCellStyle(style);
		getCell(sheet, 2, 10).setCellValue("县农村总人口数");
		getCell(sheet, 2, 10).setCellStyle(style);
		getCell(sheet, 2, 11).setCellValue("各年度城镇居民人均纯收入A10[元]");
		getCell(sheet, 2, 11).setCellStyle(style);
		getCell(sheet, 2, 16).setCellValue("各年度农民人均纯收入A11[元]");
		getCell(sheet, 2, 16).setCellStyle(style);
		getCell(sheet, 3, 2).setCellValue("A1[平方公里]");
		getCell(sheet, 3, 2).setCellStyle(style);
		getCell(sheet, 3, 3).setCellValue("A2[个]");
		getCell(sheet, 3, 3).setCellStyle(style);
		getCell(sheet, 3, 4).setCellValue("A3[个] ");
		getCell(sheet, 3, 4).setCellStyle(style);
		getCell(sheet, 3, 5).setCellValue("A4[个] ");
		getCell(sheet, 3, 5).setCellStyle(style);
		getCell(sheet, 3, 6).setCellValue("A5[个] ");
		getCell(sheet, 3, 6).setCellStyle(style);
		getCell(sheet, 3, 7).setCellValue("A6[户] ");
		getCell(sheet, 3, 7).setCellStyle(style);
		getCell(sheet, 3, 8).setCellValue("A7[户] ");
		getCell(sheet, 3, 8).setCellStyle(style);
		getCell(sheet, 3, 9).setCellValue("A8[个]");
		getCell(sheet, 3, 9).setCellStyle(style);
		getCell(sheet, 3, 10).setCellValue("A9[个]");
		getCell(sheet, 3, 10).setCellStyle(style);
		getCell(sheet, 3, 11).setCellValue("2013年");
		getCell(sheet, 3, 11).setCellStyle(style);
		getCell(sheet, 3, 12).setCellValue("2014年");
		getCell(sheet, 3, 12).setCellStyle(style);
		getCell(sheet, 3, 13).setCellValue("2015年");
		getCell(sheet, 3, 13).setCellStyle(style);
		getCell(sheet, 3, 14).setCellValue("2016年");
		getCell(sheet, 3, 14).setCellStyle(style);
		getCell(sheet, 3, 15).setCellValue("2017年");
		getCell(sheet, 3, 15).setCellStyle(style);
		getCell(sheet, 3, 16).setCellValue("2013年");
		getCell(sheet, 3, 16).setCellStyle(style);
		getCell(sheet, 3, 17).setCellValue("2014年");
		getCell(sheet, 3, 17).setCellStyle(style);
		getCell(sheet, 3, 18).setCellValue("2015年");
		getCell(sheet, 3, 18).setCellStyle(style);
		getCell(sheet, 3, 19).setCellValue("2016年");
		getCell(sheet, 3, 19).setCellStyle(style);
		getCell(sheet, 3, 20).setCellValue("2017年");
		getCell(sheet, 3, 20).setCellStyle(style);
		// 第2行
		HSSFCellStyle valueStyle = workbook.createCellStyle();
		valueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		font = workbook.createFont();
		// font.setBoldweight(HSSFFont.COLOR_NORMAL);
		font.setFontHeightInPoints((short) 12);
		font.setFontName("宋体");

		valueStyle.setFont(font);
		// setDataForModeaAreaInfo(items, sheet,valueStyle);
		summaryStatistics(items);
		setDataAndHSSFSheet(items, sheet, valueStyle, 4);

	}

	public void setDataForModeaAreaInfo(Collection<Object[]> items,
			HSSFSheet sheet, HSSFCellStyle valueStyle) {

		double A1 = 0;
		int A2 = 0, A3 = 0, A4 = 0, A5 = 0, A6 = 0, A7 = 0, A8 = 0, A9 = 0;
		double x13 = 0, x14 = 0, x15 = 0, x16 = 0, x17 = 0, z13 = 0, z14 = 0, z15 = 0, z16 = 0, z17 = 0;
		int sequenceValue = 1;

		for (Object[] it : items) {
			for (int i = 0; i < 21; i++) {
				sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
						sequenceValue + 3, i, i));
				getCell(sheet, sequenceValue + 3, i).setCellStyle(valueStyle);
			}
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 0, 0));// 序号值
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 1, 1));// 县(区)值
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 2, 2));// 农村面积值
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 3, 3));// 乡镇个数值
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 4, 4));// 行政村个数值
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 5, 5));// 自然村个数值
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 6, 6));// 村民小组数值
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 7, 7));// 县人口总户数值
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 8, 8));// 县农村总户数
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 9, 9));// 县总人口数值
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 10, 10));// 县农村总人口数值
			//
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 11, 11));// 2013年
			// // 各年度城镇居民人均纯收入A10[元]
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 12, 12));// 2014年
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 13, 13));// 2015年
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 14, 14));// 2016年
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 15, 15));// 2017年
			// // 各年度农民人均纯收入A11[元]
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 16, 16));// 2013年
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 17, 17));// 2014年
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 18, 18));// 2015年
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 19, 19));// 2016年
			// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
			// sequenceValue + 3, 20, 20));// 2017年
			// 开始设置每个记录的每个列的值,累计总和

			getCell(sheet, sequenceValue + 3, 0).setCellValue(sequenceValue);

			getCell(sheet, sequenceValue + 3, 1).setCellValue(it[3].toString());

			if (it[6] instanceof Number) {
				A1 = add(A1, it[6]);
				getCell(sheet, sequenceValue + 3, 2).setCellValue(
						Double.parseDouble(it[6].toString()));

			}

			// getCell(sheet, sequenceValue + 3, 4).setCellValue(A3);
			// getCell(sheet, sequenceValue + 3, 5).setCellValue(A4);
			// getCell(sheet, sequenceValue + 3, 6).setCellValue(A5);
			// getCell(sheet, sequenceValue + 3, 7).setCellValue(A6);
			// getCell(sheet, sequenceValue + 3, 8).setCellValue(A7);
			// getCell(sheet, sequenceValue + 3, 9).setCellValue(A8);
			// getCell(sheet, sequenceValue + 3, 10).setCellValue(A9);
			if (it[7] instanceof Number) {
				A2 += ((Number) it[7]).intValue();
				getCell(sheet, sequenceValue + 3, 3).setCellValue(
						Integer.parseInt(it[7].toString()));

			}
			if (it[8] instanceof Number) {
				A3 += ((Number) it[8]).intValue();
				getCell(sheet, sequenceValue + 3, 4).setCellValue(
						Integer.parseInt(it[8].toString()));

			}
			if (it[9] instanceof Number) {
				A4 += ((Number) it[9]).intValue();
				getCell(sheet, sequenceValue + 3, 5).setCellValue(
						Integer.parseInt(it[9].toString()));

			}
			if (it[10] instanceof Number) {
				A5 += ((Number) it[10]).intValue();
				getCell(sheet, sequenceValue + 3, 6).setCellValue(
						Integer.parseInt(it[10].toString()));

			}
			if (it[11] instanceof Number) {
				A6 += ((Number) it[11]).intValue();
				getCell(sheet, sequenceValue + 3, 7).setCellValue(
						Integer.parseInt(it[11].toString()));

			}
			if (it[12] instanceof Number) {
				A7 += ((Number) it[12]).intValue();
				getCell(sheet, sequenceValue + 3, 8).setCellValue(
						Integer.parseInt(it[12].toString()));

			}
			if (it[13] instanceof Number) {
				A8 += ((Number) it[13]).intValue();
				getCell(sheet, sequenceValue + 3, 9).setCellValue(
						Integer.parseInt(it[13].toString()));

			}
			if (it[14] instanceof Number) {
				A9 += ((Number) it[14]).intValue();
				getCell(sheet, sequenceValue + 3, 10).setCellValue(
						Integer.parseInt(it[14].toString()));

			}
			x13 = add(x13, it[15]);
			x14 = add(x14, it[16]);
			x15 = add(x15, it[17]);
			x16 = add(x16, it[18]);
			x17 = add(x17, it[19]);
			z13 = add(z13, it[20]);
			z14 = add(z14, it[21]);
			z15 = add(z15, it[22]);
			z16 = add(z16, it[23]);
			z17 = add(z17, it[24]);

			getCell(sheet, sequenceValue + 3, 11).setCellValue(
					Double.parseDouble(it[15].toString()));
			getCell(sheet, sequenceValue + 3, 12).setCellValue(
					Double.parseDouble(it[16].toString()));
			getCell(sheet, sequenceValue + 3, 13).setCellValue(
					Double.parseDouble(it[17].toString()));
			getCell(sheet, sequenceValue + 3, 14).setCellValue(
					Double.parseDouble(it[18].toString()));
			getCell(sheet, sequenceValue + 3, 15).setCellValue(
					Double.parseDouble(it[19].toString()));
			getCell(sheet, sequenceValue + 3, 16).setCellValue(
					Double.parseDouble(it[20].toString()));
			getCell(sheet, sequenceValue + 3, 17).setCellValue(
					Double.parseDouble(it[21].toString()));
			getCell(sheet, sequenceValue + 3, 18).setCellValue(
					Double.parseDouble(it[22].toString()));
			getCell(sheet, sequenceValue + 3, 19).setCellValue(
					Double.parseDouble(it[23].toString()));
			getCell(sheet, sequenceValue + 3, 20).setCellValue(
					Double.parseDouble(it[24].toString()));
			++sequenceValue;
			// A1=A2=A3=A4 =A5=A6=A7=A8=A9=0;
			// x13=x14=x15=x16=x17=z13=z14=z15=z16=z17=0;

		}
		for (int i = 1; i < 21; i++) {
			sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
					sequenceValue + 3, i, i));
			getCell(sheet, sequenceValue + 3, i).setCellStyle(valueStyle);
		}
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 1, 1));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 2, 2));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 3, 3));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 4, 4));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 5, 5));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 6, 6));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 7, 7));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 8, 8));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 9, 9));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 10, 10));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 11, 11));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 12, 12));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 13, 13));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 14, 14));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 15, 15));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 16, 16));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 17, 17));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 18, 18));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 19, 19));
		// sheet.addMergedRegion(new CellRangeAddress(sequenceValue + 3,
		// sequenceValue + 3, 20, 20));
		getCell(sheet, sequenceValue + 3, 1).setCellValue("全省汇总：");
		getCell(sheet, sequenceValue + 3, 2).setCellValue(A1);
		getCell(sheet, sequenceValue + 3, 3).setCellValue(A2);
		getCell(sheet, sequenceValue + 3, 4).setCellValue(A3);
		getCell(sheet, sequenceValue + 3, 5).setCellValue(A4);
		getCell(sheet, sequenceValue + 3, 6).setCellValue(A5);
		getCell(sheet, sequenceValue + 3, 7).setCellValue(A6);
		getCell(sheet, sequenceValue + 3, 8).setCellValue(A7);
		getCell(sheet, sequenceValue + 3, 9).setCellValue(A8);
		getCell(sheet, sequenceValue + 3, 10).setCellValue(A9);
		getCell(sheet, sequenceValue + 3, 11).setCellValue(x13);
		getCell(sheet, sequenceValue + 3, 12).setCellValue(x14);
		getCell(sheet, sequenceValue + 3, 13).setCellValue(x15);
		getCell(sheet, sequenceValue + 3, 14).setCellValue(x16);
		getCell(sheet, sequenceValue + 3, 15).setCellValue(x17);
		getCell(sheet, sequenceValue + 3, 16).setCellValue(z13);
		getCell(sheet, sequenceValue + 3, 17).setCellValue(z14);
		getCell(sheet, sequenceValue + 3, 18).setCellValue(z15);
		getCell(sheet, sequenceValue + 3, 19).setCellValue(z16);
		getCell(sheet, sequenceValue + 3, 20).setCellValue(z17);

	}

	public Collection<Object[]> getItems() {
		return items;
	}

}
