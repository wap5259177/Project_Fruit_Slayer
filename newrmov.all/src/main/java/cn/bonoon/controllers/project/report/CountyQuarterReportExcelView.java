package cn.bonoon.controllers.project.report;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import cn.bonoon.entities.ModelAreaQuarterAdministrationRural;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.ModelAreaQuarterNaturalRural;

public class CountyQuarterReportExcelView extends AbstractExcelView{

	private final ModelAreaQuarterItem item ;
	@Autowired
	public CountyQuarterReportExcelView(ModelAreaQuarterItem item){
		this.item = item;
	}
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String sheetName = "广东省"+item.getCityName()+item.getModelArea().getName()+"连片示范建设工程进展情况统计表";
		HSSFSheet sheet = workbook.createSheet(sheetName);
		String name = sheetName+".xls";
		response.setHeader("Content-disposition", "attachment;filename="+ 
				new String(name.getBytes("gb2312"), "ISO8859-1"));
		//标题的样式
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		HSSFFont titleFont = workbook.createFont();
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//?
		titleFont.setFontHeightInPoints((short)22);
		titleStyle.setFont(titleFont);
		
		//表头的样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)10);
		style.setFont(font);
		style.setWrapText(true);//自动换行
		
//		HSSFCell cell = getCell(sheet, 0, 0);//获得单元格    -- 标题
//		cell.setCellStyle(titleStyle);
//		cell.setCellValue(sheetName);
		
		
		//----------表头
		//设置单元格的宽度
		for(int i=0;i<23;i++){
			sheet.setColumnWidth(i, 12*215);
		}
		
		//第0行
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 25));//行从哪个下标起，到哪个下标止 ：含头含尾，列：从哪个下标起，到哪个下标止
		
		//第1行
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2,2));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 3,3));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 4,4));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 5,6));//完成村庄规划
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 7,7));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 8,8));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 9,9));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 10,10));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 11,13));//环境卫生整治
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 14,15));//项目
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 16,24));//资金投入情况
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 25,25));//民约名归
		
		//第2行
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 5, 5));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 6, 6));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 11, 11));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 12, 12));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 13, 13));
		
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 14, 14));//项目
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 15, 15));
		
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 16, 24));
		
		
		//第3行
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 16, 16));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 17, 17));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 18, 18));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 19, 19));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 20, 20));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 21, 21));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 22, 22));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 23, 23));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 24, 24));
		
		
		
		getCell(sheet, 0, 0).setCellValue(sheetName);
		getCell(sheet, 0, 0).setCellStyle(titleStyle);
		
		getCell(sheet, 1, 0).setCellValue("示范片名称");
		getCell(sheet, 1, 0).setCellStyle(style);
		getCell(sheet, 1, 1).setCellValue("行政村数");
		getCell(sheet, 1, 1).setCellStyle(style);
		getCell(sheet, 1, 2).setCellValue("自然村数");
		getCell(sheet, 1, 2).setCellStyle(style);
		getCell(sheet, 1, 3).setCellValue("户数");
		getCell(sheet, 1, 3).setCellStyle(style);
		getCell(sheet, 1, 4).setCellValue("人数");
		getCell(sheet, 1, 4).setCellStyle(style);
		getCell(sheet, 1, 5).setCellValue("完成村庄整治规划情况");
		getCell(sheet, 1, 5).setCellStyle(style);
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
		getCell(sheet, 1, 25).setCellValue("已成立村民理事会并制定了村规民约和章程的自然村数（个）");
		getCell(sheet, 1, 25).setCellStyle(style);
		
		
		
		//...
		
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
		getCell(sheet, 2, 14).setCellValue("");
		
		getCell(sheet, 2, 14).setCellValue("已启动的项目个数（个）");
		getCell(sheet, 2, 14).setCellStyle(style);
		getCell(sheet, 2, 15).setCellValue("其中已竣工的项目个数（个）");
		getCell(sheet, 2, 15).setCellStyle(style);
		//...
		
		getCell(sheet, 3, 16).setCellValue("累计投入资金总数");
		getCell(sheet, 3, 16).setCellStyle(style);
		getCell(sheet, 3, 17).setCellValue("中央财政资金");
		getCell(sheet, 3, 17).setCellStyle(style);
		getCell(sheet, 3, 18).setCellValue("省级新农村示范片建设补助资金");
		getCell(sheet, 3, 18).setCellStyle(style);
		getCell(sheet, 3, 19).setCellValue("其他省级财政资金");
		getCell(sheet, 3, 19).setCellStyle(style);
		getCell(sheet, 3, 20).setCellValue("市级财政资金");
		getCell(sheet, 3, 20).setCellStyle(style);
		getCell(sheet, 3, 21).setCellValue("县级财政资金");
		getCell(sheet, 3, 21).setCellStyle(style);
		getCell(sheet, 3, 22).setCellValue("社会投入资金");
		getCell(sheet, 3, 22).setCellStyle(style);
		getCell(sheet, 3, 23).setCellValue("群众自筹资金");
		getCell(sheet, 3, 23).setCellStyle(style);
		getCell(sheet, 3, 24).setCellValue("其他资金");
		getCell(sheet, 3, 24).setCellStyle(style);
		//....
		
		
		toSheet(sheet, 1, item);//第4行
		
		//第5行
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 22));
		getCell(sheet, 5, 0).setCellValue("请按实际情况填表完成情况：");
		
		//---------------tbody
		List<ModelAreaQuarterAdministrationRural> adminRurals = item.getAdminRurals();
		int row = 6;//从第6行开始
		for(ModelAreaQuarterAdministrationRural qar:adminRurals){
			sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 4));
			sheet.addMergedRegion(new CellRangeAddress(row,row,5,5));
			sheet.addMergedRegion(new CellRangeAddress(row, row, 6, 22));
			getCell(sheet,row,0).setCellValue(qar.getAdminRural().getName());
			_checkAndSetVal(sheet, row, 5, qar.getArFinishPlan());
			getCell(sheet, 6, 22).setCellValue("");
			
			row++;
			for(ModelAreaQuarterNaturalRural qnr:qar.getNaturaRurals()){
				sheet.addMergedRegion(new CellRangeAddress(row,row,0,4));
				for(int i=5;i<=13;i++){
					sheet.addMergedRegion(new CellRangeAddress(row,row,i,i));
				}
				sheet.addMergedRegion(new CellRangeAddress(row,row,14,21));
				sheet.addMergedRegion(new CellRangeAddress(row,row,22,22));
				getCell(sheet, row, 0).setCellValue("    "+qnr.getNewRural().getNaturalVillage());
				getCell(sheet, row, 5).setCellValue("");
				_checkAndSetVal(sheet, row, 6, qnr.getNeedFinish().getNeedFinish1());
				_checkAndSetVal(sheet, row, 7, qnr.getNeedFinish().getNeedFinish2());
				_checkAndSetVal(sheet, row, 8, qnr.getNeedFinish().getNeedFinish3());
				_checkAndSetVal(sheet, row, 9, qnr.getNeedFinish().getNeedFinish4());
				_checkAndSetVal(sheet, row, 10, qnr.getNeedFinish().getNeedFinish5());
				_checkAndSetVal(sheet, row, 11, qnr.getNeedFinish().getNeedFinish6());
				_checkAndSetVal(sheet, row, 12, qnr.getNeedFinish().getNeedFinish7());
				_checkAndSetVal(sheet, row, 13, qnr.getNeedFinish().getNeedFinish8());
				_checkAndSetVal(sheet, row, 13, qnr.getNeedFinish().getNeedFinish8());
				getCell(sheet, row, 14).setCellValue("");
				_checkAndSetVal(sheet, row, 25, qnr.getNeedFinish().getNeedFinish9());
				row++;
			}
		}
		
	}
	
	
	private void toSheet(HSSFSheet sheet, int i, ModelAreaQuarterItem item) {
		int rowIndex = i + 3, colIndex = 0;
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
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getTotalFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getStateFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getSpecialFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getProvinceFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getCityFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getCountyFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getSocialFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getMassFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getInvestment().getOtherFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getNeedFinish().getNeedFinish9());
	}
	
	private void _checkAndSetVal(HSSFSheet sheet,int row,int cel,int val){
		if(val!=0){
			getCell(sheet, row, cel).setCellValue("√");
		}else{
			getCell(sheet, row, cel).setCellValue("");
		}
	}

}
