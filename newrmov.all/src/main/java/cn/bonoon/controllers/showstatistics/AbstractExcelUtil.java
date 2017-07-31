package cn.bonoon.controllers.showstatistics;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import cn.bonoon.util.DoubleHelper;
import cn.bonoon.util.StatisticalQueryUtil;

/**
 * <pre>
 * 主要是提供了 setCellValueAndCellValue()方法处理excel表格中的数据
 * 表头样式列名则还是由AbstractExcelView提供的buildExcelDocument()方法设置
 * </pre>
 * 
 * @author wsf
 * @creation 2016-12-15
 * 
 */
public abstract class AbstractExcelUtil extends AbstractExcelView {
	private HSSFSheet sheet;

	/**
	 * 
	 * @param items
	 *            你的所有数据
	 * @param sheet
	 * @param valueStyle
	 *            每个数据的单元格的样式
	 * @param firstLine
	 *            你的数据记录行是从哪一行开始的
	 * @throws Exception
	 */
	protected void setDataAndHSSFSheet(Collection<Object[]> items,
			HSSFSheet sheet, HSSFCellStyle valueStyle, int firstLine)
			throws Exception {
		if (sheet == null) {
			sheet = this.sheet;
			if (sheet == null) {
				throw new Exception("HSSFSheet对象参数sheet为null");
			}
		}
		if (items != null) {
			DecimalFormat    df   = new DecimalFormat("######0.00"); 
			int i = 0;
			for (Object[] o : items) {
				sheet.addMergedRegion(new CellRangeAddress(i + firstLine, i
						+ firstLine, 0, 0));
				getCell(sheet, i + firstLine, 0).setCellStyle(valueStyle);
				getCell(sheet, i + firstLine, 0).setCellValue(i + 1);
				for (int i2 = 0; i2 < o.length; i2++) {

					sheet.addMergedRegion(new CellRangeAddress(i + firstLine, i
							+ firstLine, i2 + 1, i2 + 1));
					getCell(sheet, i + firstLine, i2 + 1).setCellStyle(
							valueStyle);
					if (o[i2] != null) {
						if(Double.class.equals(o[i2].getClass())){
							o[i2]=StatisticalQueryUtil.format(o[i2]);
						}
						getCell(sheet, i + firstLine, i2 + 1).setCellValue(
								String.valueOf(o[i2]));
					} else {
						getCell(sheet, i + firstLine, i2 + 1).setCellValue("");
					}
				}
				i++;
			}
			//去掉最后一行也就是统计行的序号
			getCell(sheet, items.size()+firstLine-1, 0).setCellValue("");
		}
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @param name
	 *            该列列名
	 * @param style
	 *            样式
	 */
	public void setCellValueAndCellStyle(int row, int col, String name,
			HSSFCellStyle style) {
		if (name != null)
			getCell(this.sheet, row, col).setCellValue(name);
		if (style != null)
			getCell(this.sheet, row, col).setCellStyle(style);
	}

	public HSSFSheet getSheet() {
		return this.sheet;
	}

	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	/**
	 * 设置单元格宽度
	 * 
	 * @param ColumnSize
	 *            有多少列需要设置
	 * @param customLength
	 *            指定某一列的单元格长度
	 */
	public void setColumWidth(int ColumnSize, Map<Integer, Integer> customLength) {
		// 设置单元格的宽度
		for (int i = 0; i < ColumnSize; i++) {

			sheet.setColumnWidth(i, 4000);
		}

		for (Map.Entry<Integer, Integer> set : customLength.entrySet()) {
			sheet.setColumnWidth(set.getKey(), set.getValue());
		}

	}

	/**
	 * 这个方法是汇总统计用的
	 * 
	 * @param items
	 *            数据集 注意这里汇总统计记录的长度length用了该表第一条记录的长度
	 */
	public static void summaryStatistics(Collection<Object[]> items) {
		if (items == null) {
			return;
		}
		Object[] var;
		Iterator<Object[]> it = items.iterator();
		if (it.hasNext()) {
			var = new Object[it.next().length];

		} else {
			return;
		}
		for (Object[] o : items) {
			for (int i = 0; i < o.length; i++) {
				if (o[i] instanceof Number) {
					if (var[i] == null) {
						var[i] = 0;
					}
					boolean isInt = false;
					if (Integer.class.equals(o[i].getClass())) {
						isInt = true;
					}

					var[i] = DoubleHelper.add(
							Double.parseDouble(o[i].toString()),
							Double.parseDouble(var[i].toString()));
					if (isInt) {
						var[i]=((Double)var[i]).intValue();
					}else{
						//保留俩位小数
						DecimalFormat    df   = new DecimalFormat("######0.00"); 
						var[i]=	df.format(var[i]);
					}

				}
			}
		}

		var[0] = "全省汇总";
		items.add(var);
	}
}
