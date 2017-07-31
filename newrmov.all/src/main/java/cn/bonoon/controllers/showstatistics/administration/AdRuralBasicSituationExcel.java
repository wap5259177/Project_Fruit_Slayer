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

public class AdRuralBasicSituationExcel extends AbstractExcelUtil {
	private Collection<Object[]> items;
	private String batch;

	public AdRuralBasicSituationExcel() {
	};

	public AdRuralBasicSituationExcel(Collection<Object[]> items, String batch) {
		this.items = items;
		this.batch = batch;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		if (items == null) {
			throw new Exception("没数据");
		}
		String sheetName = "行政村基本情况汇总统计" + "(批次" + batch
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

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 25));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 25));
		// getCell(sheet, 0, 0).setCellValue(sheetName);
		// getCell(sheet, 0, 0).setCellStyle(titleStyle);
		// getCell(sheet, 1, 0).setCellValue("序号");
		// getCell(sheet, 1, 0).setCellStyle(style);

		this.setCellValueAndCellStyle(0, 0, sheetName, titleStyle);
		this.setCellValueAndCellStyle(1, 0, "序号", style);
		this.setCellValueAndCellStyle(1, 1, "县区", style);
		this.setCellValueAndCellStyle(1, 2, "行政村名", style);
		this.setCellValueAndCellStyle(1, 3, "", style);
		this.setCellValueAndCellStyle(1, 4, "（一）基本情况", style);
		String[] tableColumnName1 = new String[] { "自然村个数", "总面积", "耕地面积",
				"户数", "人口数", "劳动力总人数", "贫困户数", "贫困人口数", "低保户数", "低保人口数",
				"五保户数", "需改造的危房户数", "省级扶贫开发重点村（贫困村）个数", "各年度农民人均纯收入", "村集体经济收入" };
		for (int i = 3; i < 18; i++) {
			if(i==16){
				sheet.addMergedRegion(new CellRangeAddress(2, 2, i, i+4));
				this.setCellValueAndCellStyle(2, i, tableColumnName1[i - 3], style);
				continue;
			}else if(i==17){
				sheet.addMergedRegion(new CellRangeAddress(2, 2,i+4,i+8));
				this.setCellValueAndCellStyle(2, i+4, tableColumnName1[i - 3], style);
				continue;
			}
			sheet.addMergedRegion(new CellRangeAddress(2, 2, i, i));
			this.setCellValueAndCellStyle(2, i, tableColumnName1[i - 3], style);
			
		}

		String[] tableColumnName2 = new String[] { "B4[个]", "B5[亩]", "B6[亩]",
				"B7[户]", "B8[人]", "B9[人]", "[户]", "[人]", "[户]", "[人]", "[户]",
				"[户]", "B10[个]", "2013年", "2014年", "2015年", "2016年", "2017年",
				"2013年", "2014年", "2015年", "2016年", "2017年" };
		for (int i = 3; i < 26; i++) {
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
		
		//对第16列做统计 汇总统计需要用
		List<Object[]> list=	(List<Object[]>)items;
		int sum=0;
			for(int i=0;i<list.size();i++){
				
				if(i==list.size()-1){
					break;
				};
				if(list.get(i)[14].toString().equals("是")){	
				sum+=1;
				}
			}
			list.get(list.size()-1)[14]=sum;
		
		setDataAndHSSFSheet(items, sheet, valueStyle, 4);
		Map<Integer,Integer> hm=new HashMap<Integer,Integer>(); 
		hm.put(0, 2700);
		hm.put(2,6000);
		hm.put(17,6000);
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
