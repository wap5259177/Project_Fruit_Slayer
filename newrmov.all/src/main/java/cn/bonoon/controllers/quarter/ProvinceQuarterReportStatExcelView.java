package cn.bonoon.controllers.quarter;

import java.util.List;
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

import cn.bonoon.entities.ModelAreaQuarterBatch;
import cn.bonoon.entities.ModelAreaQuarterItem;

public class ProvinceQuarterReportStatExcelView extends AbstractExcelView{
    private final ModelAreaQuarterBatch batch;
    private List<ModelAreaQuarterItem> items;
	@Autowired
	ProvinceQuarterReportStatExcelView(ModelAreaQuarterBatch batch,List<ModelAreaQuarterItem> items){
		this.batch=batch;
		this.items=items;
	}
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int period = batch.getQuarter().getPeriod();
		String _period = period==0?"第一季度":period==1?"第二季度":period==2?"第三季度":"第四季度";
		String shellName = batch.getQuarter().getAnnual()+"年第"+batch.getBatchName()+"批省级新农村连片示范建设工程进展情况统计表("+_period+")";
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
		for(int i=0;i<23;i++){
			sheet.setColumnWidth(i, 12*256);
		}
		
		sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) 25));
		
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
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 1, 16, (short) 24));
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 2, 25, (short) 25));
		
		
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
		getCell(sheet, 1, 25).setCellValue("已成立村民理事会并制定了村规民约和章程的自然村数（个）");
		getCell(sheet, 1, 25).setCellStyle(style);
		
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
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 24, (short) 24));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 25, (short) 25));
		
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
		getCell(sheet, 2, 17).setCellValue("1.中央财政资金");
		getCell(sheet, 2, 17).setCellStyle(style);
		getCell(sheet, 2, 18).setCellValue("2.省级新农村示范片建设补助资金");
		getCell(sheet, 2, 18).setCellStyle(style);
		getCell(sheet, 2, 19).setCellValue("3.其他省级财政资金");
		getCell(sheet, 2, 19).setCellStyle(style);
		getCell(sheet, 2, 20).setCellValue("4.市级财政资金");
		getCell(sheet, 2, 20).setCellStyle(style);
		getCell(sheet, 2, 21).setCellValue("5.县级财政资金");
		getCell(sheet, 2, 21).setCellStyle(style);
		getCell(sheet, 2, 22).setCellValue("6.社会投入资金");
		getCell(sheet, 2, 22).setCellStyle(style);
		getCell(sheet, 2, 23).setCellValue("7.群众自筹资金");
		getCell(sheet, 2, 23).setCellStyle(style);
		getCell(sheet, 2, 24).setCellValue("8.其他资金");
		getCell(sheet, 2, 24).setCellStyle(style);
		
		//for (int j = 0; j < item.length; j++) {
		if(batch!=null){
	      toSheet(sheet, 1, batch);
		}
		for(int j=0;j<items.size();j++){
			toSheet1(sheet,j+1,items.get(j));
		}
		//}
		String name = shellName + ".xls";
		response.setHeader("Content-disposition", "attachment;filename="+ 
					new String(name.getBytes("gb2312"), "ISO8859-1"));
	}

	private void toSheet(HSSFSheet sheet, int i, ModelAreaQuarterBatch batch) {
		int rowIndex = i + 2, colIndex = 0;
//		int A1=0,A2=0,A3=0,A4=0,A5=0,P1=0,P2=0;
//		int N1=0,N2=0,N3=0,N4=0,N5=0,N6=0,N7=0,N8=0,N9=0;
//		double F1=0.0,F2=0.0,F3=0.0,F4=0.0,F5=0.0,F6=0.0,F7=0.0,F8=0.0,F9=0.0;
//		
//		for(ModelAreaQuarterItem getItem : items){
//			RuralNeedFinishInfo needFinish = getItem.getNeedFinish();
//			InvestmentInfo investment = getItem.getInvestment();
//			
//				A1 += ((Number)getItem.getAdminRurals()).intValue();
//				A2 += ((Number)getItem.getNrCount()).intValue();
//				A3 += ((Number)getItem.getPopulationCount()).intValue();
//				A4 += ((Number)getItem.getHouseholdCount()).intValue();
//				A5 += ((Number)getItem.getArFinishPlan()).intValue();
//				N1 += ((Number)needFinish.getNeedFinish1()).intValue();
//				N2 += ((Number)needFinish.getNeedFinish2()).intValue();
//				N3 += ((Number)needFinish.getNeedFinish3()).intValue();
//				N4 += ((Number)needFinish.getNeedFinish4()).intValue();
//				N5 += ((Number)needFinish.getNeedFinish5()).intValue();
//				N6 += ((Number)needFinish.getNeedFinish6()).intValue();
//				N7 += ((Number)needFinish.getNeedFinish7()).intValue();
//				N8 += ((Number)needFinish.getNeedFinish8()).intValue();
//				N9 += ((Number)needFinish.getNeedFinish9()).intValue();
//				
//				F1 = add(F1,investment.getTotalFunds());
//				F2 = add(F2,investment.getStateFunds());
//				F3 = add(F3,investment.getSpecialFunds());
//				F4 = add(F4,investment.getProvinceFunds());
//				F5 = add(F5,investment.getCityFunds());
//				F6 = add(F6,investment.getCountyFunds());
//				F7 = add(F7,investment.getSocialFunds());
//				F8 = add(F8,investment.getMassFunds());
//				F9 = add(F9,investment.getOtherFunds());
//				
//				if(null!=getItem.getStartProject()){
//					P1 += getItem.getStartProject().intValue();
//				}else{
//					P1 += 0;
//				}
//				if(null!=getItem.getFinishProject()){
//					P2 += getItem.getFinishProject().intValue();
//				}else{
//					P2 += 0;
//				}
//				
//				
//				
//		}
//		getCell(sheet, rowIndex, colIndex++).setCellValue("第"+item.getBatchName()+"批");
//		getCell(sheet, rowIndex, colIndex++).setCellValue(A1);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(A2);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(A3);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(A4);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(A5);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(N1);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(N2);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(N3);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(N4);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(N5);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(N6);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(N7);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(N8);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(P1);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(P1);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(F1);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(F2);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(F3);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(F4);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(F5);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(F6);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(F7);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(F8);
//		getCell(sheet, rowIndex, colIndex++).setCellValue(F9);
//		
//		getCell(sheet, rowIndex, colIndex++).setCellValue(N9);
		batch.sum();
		getCell(sheet, rowIndex, colIndex++).setCellValue("第"+batch.getBatchName()+"批");
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getArCount());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getNrCount());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getHouseholdCount());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getPopulationCount());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getArFinishPlan());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getNeedFinish().getNeedFinish1());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getNeedFinish().getNeedFinish2());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getNeedFinish().getNeedFinish3());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getNeedFinish().getNeedFinish4());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getNeedFinish().getNeedFinish5());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getNeedFinish().getNeedFinish6());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getNeedFinish().getNeedFinish7());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getNeedFinish().getNeedFinish8());
		
		if(null!=batch.getStartProject()){
			getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getStartProject());
		}else{
			getCell(sheet, rowIndex, colIndex++).setCellValue(0);
		}
		if(null!=batch.getFinishProject()){
			getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getFinishProject());
		}else{
			getCell(sheet, rowIndex, colIndex++).setCellValue(0);
		}
		
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getInvestment().getTotalFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getInvestment().getStateFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getInvestment().getSpecialFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getInvestment().getProvinceFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getInvestment().getCityFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getInvestment().getCountyFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getInvestment().getSocialFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getInvestment().getMassFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getInvestment().getOtherFunds());
		getCell(sheet, rowIndex, colIndex++).setCellValue(batch.getNeedFinish().getNeedFinish9());

	}
	
	private void toSheet1(HSSFSheet sheet, int i, ModelAreaQuarterItem item) {
		int rowIndex = i + 3, colIndex = 0;
		//getCell(sheet, rowIndex, colIndex++).setCellValue(i);
		getCell(sheet, rowIndex, colIndex++).setCellValue(item.getCityName()+item.getModelArea().getCounty());
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
}
