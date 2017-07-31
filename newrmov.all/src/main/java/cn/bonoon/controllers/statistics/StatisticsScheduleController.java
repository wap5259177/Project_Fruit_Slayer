//package cn.bonoon.controllers.statistics;
//
//import static org.springframework.web.bind.annotation.RequestMethod.GET;
//import static org.springframework.web.bind.annotation.RequestMethod.POST;
//
//import java.util.Collection;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import cn.bonoon.kernel.events.PanelEvent;
//import cn.bonoon.kernel.support.IOperator;
//import cn.bonoon.kernel.web.controllers.AbstractPanelController;
//import cn.bonoon.kernel.web.models.PanelModel;
//
//public abstract class StatisticsScheduleController extends AbstractPanelController{
//
//	@Override
//	protected void onLoad(PanelEvent event) throws Exception {
//		PanelModel model = event.getModel();
//		String batch = model.getParameter("batch");
//		event.setVmPath("statistics/area-schedule");
//		Collection<Object[]> items = getItems(event.getUser(), batch);
//		event.getModel().addObject("content", statistics(items)).addObject("batch", batch);
//		event.setVmPath("statistics/area-schedule");
//		
//	}
//	@RequestMapping(value = "!{key}/index.batch", method = POST)
//	public String loadItems(Model model, String batch){
//		Collection<Object[]> items = getItems(getUser(), batch);
//		model.addAttribute("layout", "layout-empty.vm").addAttribute("content", statistics(items));
//		return "statistics/area-schedule-items";
//	}
//
//	@SuppressWarnings("deprecation")
//	@RequestMapping(value = "!{key}/index.excel", method = GET)
//	public void excel(HttpServletRequest request, HttpServletResponse response, String batch){
//		HSSFWorkbook workbook = null;
//		try{
//		String _name = "测试下载.xls";
//		response.setContentType("multipart/form-data");
//		response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
//		Collection<Object[]> items = getItems(getUser(), batch);
//        // 声明一个工作薄  
//        workbook = new HSSFWorkbook();  
//        // 生成一个表格  
//        HSSFSheet sheet = workbook.createSheet("台账封面汇总统计");  
//        
//        sheet.setDefaultColumnWidth((short) 15);  
//        sheet.setDefaultRowHeight((short)(15.625*25));
//        sheet.setColumnWidth(5, 5000); 
//        sheet.setColumnWidth(6, 5000);
//        
//     // 生成一个样式  
//        HSSFCellStyle style1 = workbook.createCellStyle();  
//        // 设置这些样式  
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
//        // 生成一个字体  
//        HSSFFont font1 = workbook.createFont();  
//        font1.setColor(HSSFColor.BLACK.index);  
//        font1.setFontHeightInPoints((short) 20);  
//        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
//        
//        // 把字体应用到当前的样式  
//        style1.setFont(font1); 
//        
//     // 生成一个样式  
//        HSSFCellStyle style2 = workbook.createCellStyle();  
//        // 设置这些样式  
//        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
//        // 生成一个字体  
//        HSSFFont font2 = workbook.createFont();  
//        font2.setColor(HSSFColor.BLACK.index);  
//        font2.setFontHeightInPoints((short) 12);  
//        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
//        // 把字体应用到当前的样式  
//        style2.setFont(font2); 
//        
//        // 生成一个样式  
//        HSSFCellStyle style = workbook.createCellStyle();  
//        // 设置这些样式  
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
//        style.setWrapText(true);
//        // 生成一个字体  
//        HSSFFont font = workbook.createFont();  
//        font.setColor(HSSFColor.BLACK.index);  
//        font.setFontHeightInPoints((short) 11); 
//        
//        // 把字体应用到当前的样式  
//        style.setFont(font); 
//        
//        // 生成一个样式  
//        HSSFCellStyle mStyle = workbook.createCellStyle();  
//        // 设置这些样式  
//        mStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
//        mStyle.setWrapText(true);
//        // 生成一个字体  
//        HSSFFont mFont = workbook.createFont();  
//        mFont.setColor(HSSFColor.BLACK.index);  
//        mFont.setFontHeightInPoints((short) 10); 
//        
//        // 把字体应用到当前的样式  
//        mStyle.setFont(mFont); 
//        
//        //标题栏
//        HSSFRow ro = sheet.createRow(0);  
//        HSSFCell cel = ro.createCell(0);
//        cel.setCellValue("广东省新农村连片示范建设工程台账簿 - 建设台账封面统计");
//        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 38));
//        
//        cel.setCellStyle(style1); 
//        
//        HSSFRow row = sheet.createRow(3);  //第一行
//        HSSFCell cell = row.createCell(0);
//        HSSFCell cell1 = row.createCell(1);
//        HSSFCell cell2 = row.createCell(2);
//        HSSFCell cell3 = row.createCell(3);
//        HSSFCell cell4 = row.createCell(4);
//        HSSFCell cell5 = row.createCell(5);//5,6
//        HSSFCell cell6 = row.createCell(7);
//        HSSFCell cell7 = row.createCell(8);
//        HSSFCell cell8 = row.createCell(9);
//        HSSFCell cell9 = row.createCell(10);
//        HSSFCell cell10 = row.createCell(11);//11,13
//        HSSFCell cell11 = row.createCell(14);//14,20
//        HSSFCell cell12 = row.createCell(21);
//        
//        cell.setCellStyle(style); 
//        cell1.setCellStyle(style); 
//        cell2.setCellStyle(style); 
//        cell3.setCellStyle(style); 
//        cell4.setCellStyle(style); 
//        cell5.setCellStyle(style); 
//        cell6.setCellStyle(style); 
//        cell7.setCellStyle(style2); 
//        cell8.setCellStyle(style2); 
//        cell9.setCellStyle(style2); 
//        cell10.setCellStyle(style2);
//        cell11.setCellStyle(style2);
//        cell12.setCellStyle(style2);
//        
//        
//        HSSFRow row1 = sheet.createRow(4);  //第二行
//        HSSFCell tCell = row1.createCell(7);
//        HSSFCell tCel11= row1.createCell(8);
//        HSSFCell tCel12= row1.createCell(9);
//        HSSFCell tCel13= row1.createCell(10);
//        HSSFCell tCel14= row1.createCell(11);
//        HSSFCell tCel15= row1.createCell(12);
//        HSSFCell tCel16= row1.createCell(13);
//        HSSFCell tCel17= row1.createCell(14);
//        HSSFCell tCel18= row1.createCell(15);
//        HSSFCell tCel19= row1.createCell(16);
//        HSSFCell tCel110= row1.createCell(20);
//        HSSFCell tCel111= row1.createCell(20);
//        
//        
//        tCell.setCellStyle(style); 
//        tCel11.setCellStyle(style); 
//        tCel12.setCellStyle(style); 
//        tCel13.setCellStyle(style); 
//        tCel14.setCellStyle(style); 
//        tCel15.setCellStyle(style); 
//        tCel16.setCellStyle(style); 
//        tCel17.setCellStyle(style); 
//        tCel18.setCellStyle(style); 
//        tCel19.setCellStyle(style); 
//        tCel110.setCellStyle(style); 
//        tCel111.setCellStyle(style); 
//        
//        
//        HSSFRow row2 = sheet.createRow(6);  //第三行
//        HSSFCell sCell = row2.createCell(7);
//        HSSFCell sCell1 = row2.createCell(7);
//     
//         
//        sCell.setCellStyle(style);
//        sCell1.setCellStyle(style);
//        
//       
//        
//         
//        //第一行标题
//        cell.setCellValue("示范片区所在市县名称");
//        cell1.setCellValue("行政村数");
//        cell2.setCellValue("自然村数");
//        cell3.setCellValue("户数");
//        cell4.setCellValue("人数");
//        cell5.setCellValue("完成村庄整治规划情况");
//        cell6.setCellValue("通自来水自然村数（个）");
//        cell7.setCellValue("完成卫生改厕所自然村数（个）");
//        cell8.setCellValue("完成道路硬化建设自然村数（个）");
//        cell9.setCellValue("完成民居外立面特色改造的自然村数（个）");
//        cell10.setCellValue("环境卫生综合整治情况");
//        cell11.setCellValue("资金投入情况（万元）");
//        cell12.setCellValue("已成立村民理事会并制定了村规民约和章程的自然村数（个）");
//        
//        
//        
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 0, 0));
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 1, 1));
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 2, 2));
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 3, 3));
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 4, 4));
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 5, 5));
//        sheet.addMergedRegion(new CellRangeAddress(3, 6, 6, 6));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 7, 23));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 24, 29));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 30, 38));
//        
//        //第二行标题
//        tCell.setCellValue("行政村有编制规划设计村数（个）");
//        tCel11.setCellValue("自然村有编制规划设计村数（个）");
//        tCel12.setCellValue("完成卫生整治及建立保洁队和长效机制的自然村数（个）");
//        tCel13.setCellValue("实行雨污分流并建有污水处理设施自然村数（个）");
//        tCel14.setCellValue("实行人畜分离（集中圈养）的自然村数");
//        tCel15.setCellValue("累计投入资金总数");
//        tCel16.setCellValue("1.中央财政资金");
//        tCel17.setCellValue("2.省级新农村示范片建设补助资金");
//        tCel18.setCellValue("3.其他省级财政资金");
//        tCel19.setCellValue("4.市级财政资金");
//        tCel110.setCellValue("5.县级财政资金");
//        tCel111.setCellValue("6.社会投入及群众自筹资金");
//       
//        
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 7, 7));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 8, 8));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 9, 9));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 10, 10));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 11, 11));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 12, 12));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 13, 13));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 14, 14));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 15, 15));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 16, 19));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 20, 23));
//        sheet.addMergedRegion(new CellRangeAddress(4, 5, 24, 24));
//        
//       
//        
//        
//        //第三行标题
//        sCell.setCellValue("行政村有编制规划设计村数（个）");
//        sCell1.setCellValue("自然村有编制规划设计村数（个）");
//       
//        
//        
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 7, 7));
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 8, 8));
//       
//
//
//        //显示items里面的数据
//        int i=7, j = 1, k = 0;
//        
//        for(Object[] it : items){
//        	k = 0;
//            HSSFRow mRow=sheet.createRow(i++); //总行数
//            HSSFCell mCell = mRow.createCell(k++); //第一列
//            mCell.setCellValue(j++);
//            mCell.setCellStyle(mStyle);
//        	for(Object obj : it){
//        		  mCell = mRow.createCell(k++);
//        		  if(null != obj){
//        		    mCell.setCellValue(obj.toString());
//        		    mCell.setCellStyle(mStyle);
//        		  }
//            }
//        	
//        	  
//        }
//       
//        	
//        workbook.write(response.getOutputStream());
//        
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//	protected abstract Collection<Object[]> getItems(IOperator opt, String batch);
//	protected StringBuilder statistics(Collection<Object[]> items){
//		StringBuilder statis = new StringBuilder();
//		int i = 1;
//		for(Object[] it : items){
//			statis.append("<tr><td>").append(i++).append("</td>");//序号
//			for(Object obj : it){
//				if(obj!=null){					
//					statis.append("<td>").append(obj).append("</td>");
//				}
//				else{
//					statis.append("<td></td>");
//				}
//			}
//			statis.append("</tr>");
//		}
//		return statis;
//	}
//
//}
