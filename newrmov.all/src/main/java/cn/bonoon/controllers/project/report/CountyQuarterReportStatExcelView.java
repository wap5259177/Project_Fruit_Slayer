package cn.bonoon.controllers.project.report;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import cn.bonoon.entities.ModelAreaQuarterItem;

public class CountyQuarterReportStatExcelView extends AbstractExcelView{
    private final ModelAreaQuarterItem item;
    
	@Autowired
	CountyQuarterReportStatExcelView(ModelAreaQuarterItem item){
		this.item=item;
	}
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String shellName;
	    shellName = "广东省"+item.getCityName()+item.getModelArea().getName()+"连片示范建设工程进展情况统计表";
		
		// 设置标题样式
		HSSFCellStyle styleTit = workbook.createCellStyle();
		styleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont fontTit = workbook.createFont();
		fontTit.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontTit.setFontHeightInPoints((short)18);
		styleTit.setFont(fontTit);
		
		HSSFCellStyle style = workbook.createCellStyle();
		// 标题字体居中、字体变粗
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setWrapText(true);//自动换行
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		
		HSSFSheet sheet = workbook.createSheet(shellName);
		HSSFCell cell = getCell(sheet, 0, 0);
		cell.setCellStyle(styleTit);
		cell.setCellValue(shellName);
		
		//保留两位小数
		HSSFCellStyle doubleStyle = workbook.createCellStyle();
		doubleStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		
		//设定列的宽度
//		sheet.setColumnWidth(1, 12*256);
//		sheet.setColumnWidth(2, 15*256);
//		sheet.setColumnWidth(3, 25*256);
//		sheet.setColumnWidth(4, 25*256);
		for(int i=0;i<25;i++){
			sheet.setColumnWidth(i, 12*256);
		}
		
		sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) 24));
		
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 2, 0, (short) 0));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 2, 1, (short) 1));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 2, 2, (short) 2));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 2, 3, (short) 3));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 2, 4, (short) 4));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 1, 5, (short) 6));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 2, 7, (short) 7));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 2, 8, (short) 8));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 2, 9, (short) 9));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 2, 10, (short) 10));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 1, 11, (short) 13));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 1, 14, (short) 15));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 1, 16, (short) 23));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 2, 24, (short) 24));
		
		
		String[] type = {"示范片区所在市县名称", "行政村数", "自然村数", "户数", "人数","完成村庄整治规划情况"}; 
		for (int i = 0; i < type.length; i++) {
			getCell(sheet, 1, i).setCellValue(type[i]);
			getCell(sheet, 1, i).setCellStyle(style);
		}
		getCell(sheet, 1, 7).setCellValue("通自来水自然村数（个）");
		getCell(sheet, 1, 7).setCellStyle(style);
		getCell(sheet, 1, 8).setCellValue("完成卫生改厕所自然村数（个）");
		getCell(sheet, 1, 8).setCellStyle(style);
		getCell(sheet, 1, 9).setCellValue("完成道路硬化建设自然村数（个）");
		getCell(sheet, 1, 9).setCellStyle(style);
		getCell(sheet, 1, 10).setCellValue("完成民居外立面特色改造的自然村数（个）");
		getCell(sheet, 1, 10).setCellStyle(style);
		getCell(sheet, 1, 11).setCellValue("环境卫生综合整治情况");
		getCell(sheet, 1, 11).setCellStyle(style);
		getCell(sheet, 1, 14).setCellValue("项目");
		getCell(sheet, 1, 14).setCellStyle(style);
		getCell(sheet, 1, 16).setCellValue("资金投入情况（万元）");
		getCell(sheet, 1, 16).setCellStyle(style);
		getCell(sheet, 1, 24).setCellValue("已成立村民理事会并制定了村规民约和章程的自然村数（个）");
		getCell(sheet, 1, 24).setCellStyle(style);
		
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 5, (short) 5));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 6, (short) 6));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 11, (short) 11));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 12, (short) 12));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 13, (short) 13));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 14, (short) 14));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 15, (short) 15));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 16, (short) 16));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 17, (short) 17));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 18, (short) 18));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 19, (short) 19));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 20, (short) 20));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 21, (short) 21));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 22, (short) 22));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 23, (short) 23));
		
		getCell(sheet, 2, 5).setCellValue("行政村有编制规划设计村数（个）");
		getCell(sheet, 2, 5).setCellStyle(style);
		getCell(sheet, 2, 6).setCellValue("自然村有编制规划设计村数（个）");
		getCell(sheet, 2, 6).setCellStyle(style);
		getCell(sheet, 2, 11).setCellValue("完成卫生整治及建立保洁队和长效机制的自然村数（个）");
		getCell(sheet, 2, 11).setCellStyle(style);
		getCell(sheet, 2, 12).setCellValue("实行雨污分流并建有污水处理设施自然村数（个）");
		getCell(sheet, 2, 12).setCellStyle(style);
		getCell(sheet, 2, 13).setCellValue("实行人畜分离（集中圈养）的自然村数（个）");
		getCell(sheet, 2, 13).setCellStyle(style);
		

		getCell(sheet, 2, 14).setCellValue("已启动的项目个数（个）");
		getCell(sheet, 2, 14).setCellStyle(style);
		getCell(sheet, 2, 15).setCellValue("其中已竣工的项目个数（个）");
		getCell(sheet, 2, 15).setCellStyle(style);
		
		
		getCell(sheet, 2, 16).setCellValue("累计投入资金总数");
		getCell(sheet, 2, 16).setCellStyle(style);
		getCell(sheet, 2, 17).setCellValue("中央财政资金");
		getCell(sheet, 2, 17).setCellStyle(style);
		getCell(sheet, 2, 18).setCellValue("省级新农村示范片建设补助资金");
		getCell(sheet, 2, 18).setCellStyle(style);
		getCell(sheet, 2, 19).setCellValue("其他省级财政资金");
		getCell(sheet, 2, 19).setCellStyle(style);
		getCell(sheet, 2, 20).setCellValue("市级财政资金");
		getCell(sheet, 2, 20).setCellStyle(style);
		getCell(sheet, 2, 21).setCellValue("县级财政资金");
		getCell(sheet, 2, 21).setCellStyle(style);
		getCell(sheet, 2, 22).setCellValue("社会投入资金");
		getCell(sheet, 2, 22).setCellStyle(style);
		getCell(sheet, 2, 23).setCellValue("群众自筹资金");
		getCell(sheet, 2, 23).setCellStyle(style);
		
		//for (int j = 0; j < item.length; j++) {
		if(item!=null){
	      toSheet(sheet, 1, item);
		}
		//}
		String name = shellName + ".xls";
		response.setHeader("Content-disposition", "attachment;filename="+ 
					new String(name.getBytes("gb2312"), "ISO8859-1"));
	}

	private void toSheet(HSSFSheet sheet, int i, ModelAreaQuarterItem item) {
		int rowIndex = i + 2, colIndex = 0;
		//getCell(sheet, rowIndex, colIndex++).setCellValue(i);
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getModelArea().getCounty());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getArCount());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getNrCount());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getHouseholdCount());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getPopulationCount());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getArFinishPlan());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getNeedFinish().getNeedFinish1());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getNeedFinish().getNeedFinish2());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getNeedFinish().getNeedFinish3());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getNeedFinish().getNeedFinish4());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getNeedFinish().getNeedFinish5());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getNeedFinish().getNeedFinish6());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getNeedFinish().getNeedFinish7());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getNeedFinish().getNeedFinish8());
		if(null!=item.getStartProject()){
			getCell(sheet, rowIndex, colIndex++).setCellValue(item.getStartProject());
		}else{
			getCell(sheet, rowIndex, colIndex++).setCellValue(0);
		}
		if(null!=item.getFinishProject()){
			getCell(sheet, rowIndex, colIndex++).setCellValue(item.getFinishProject());
		}else{
			getCell(sheet, rowIndex, colIndex++).setCellValue(0);
		}
//		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getStartProject());
//		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getFinishProject());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getTotalFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getStateFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getSpecialFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getProvinceFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getCityFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getCountyFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getSocialFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getMassFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getNeedFinish().getNeedFinish9());

	}
}
