package cn.bonoon.controllers.statistics;

import static cn.bonoon.util.DoubleHelper.add;

import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.web.models.PanelModel;
@Controller
@RequestMapping("s/pls/sar")
public class ProvinceStatisticsAdministrationController extends StatisticsAdministrationController{

	
	@Override
	protected Collection<Object[]> getItems(IOperator opt, String batch) {
		return administrationCoreRuralService.statistics(batch);
	}
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		String batch = model.getParameter("batch");
		
		model.addObject("select", BatchHelper.batchSelect(batch));
		super.onLoad(event);
	}
	
	protected void statistics(Collection<Object[]> items, HSSFCellStyle mStyle, HSSFCellStyle style, HSSFSheet sheet) {
		int i=8, j = 1, k = 0;
		double B5 = 0, B6 = 0,x20 = 0,x21 = 0,x22 = 0,x23 = 0,x24 = 0,x25 = 0,x26 = 0,x27 = 0,x28 = 0,x29 = 0;
		int  B4 = 0,B7 = 0,B8 = 0,B9 = 0,B10 = 0,B11 = 0,B12 = 0,B13 = 0,B14 = 0,B15 = 0,B38 = 0,B461 = 0;
		int B39 = 0,B40 = 0,B41 = 0,B42 = 0,B43 = 0;
		double B391 = 0,B401 = 0,B411 = 0,B421 = 0,B431 = 0;
		int B16 = 0,B31 = 0,B32 = 0,B33 = 0,B34 = 0,B35 = 0,B36 = 0,B37 = 0,B44 = 0,B45 = 0;
		int  B46 = 0,B47 = 0,B48 = 0,B49 = 0,B50 = 0;
		
		for(Object[] it : items){
        	k = 0;
            HSSFRow mRow=sheet.createRow(i++); //总行数
            HSSFCell mCell = mRow.createCell(k++); //第一列
            mCell.setCellValue(j++);
            mCell.setCellStyle(mStyle);
        	for(Object obj : it){
        		  mCell = mRow.createCell(k++);
        		  if(null != obj){
        		    mCell.setCellValue(obj.toString());
        		    mCell.setCellStyle(mStyle);
        		  }
            }
        	if(it[7] instanceof Number){
				B4 += ((Number)it[7]).intValue();
			}
//			if(it[8] instanceof Number){
//				B5 += ((Number)it[8]).doubleValue();
//			}
			
			B5 = add(B5,it[8]);
			
//			if(it[9] instanceof Number){
//				B6 += ((Number)it[9]).doubleValue();
//			}
			
			B6 = add(B6,it[9]);
			
			if(it[10] instanceof Number){
				B7 += ((Number)it[10]).intValue();
			}
			if(it[11] instanceof Number){
				B8 += ((Number)it[11]).intValue();
			}
			if(it[12] instanceof Number){
				B9 += ((Number)it[12]).intValue();
			}
			if(it[13] instanceof Number){
				B10 += ((Number)it[13]).intValue();
			}
			if(it[14] instanceof Number){
				B11 += ((Number)it[14]).intValue();
			}
			if(it[15] instanceof Number){
				B12 += ((Number)it[15]).intValue();
			}
			if(it[16] instanceof Number){
				B13 += ((Number)it[16]).intValue();
			}
			if(it[17] instanceof Number){
				B14 += ((Number)it[17]).intValue();
			}
			if(it[18] instanceof Number){
				B15 += ((Number)it[18]).intValue();
			}
			if("是".equals(it[19])){
				B16 += 1;
			}
			
//			if(it[20] instanceof Number){
//				x20 += ((Number)it[20]).doubleValue();
//			}
//			if(it[21] instanceof Number){
//				x21 += ((Number)it[21]).doubleValue();
//			}
//			if(it[22] instanceof Number){
//				x22 += ((Number)it[22]).doubleValue();
//			}
//			if(it[23] instanceof Number){
//				x23 += ((Number)it[23]).doubleValue();
//			}
//			if(it[24] instanceof Number){
//				x24 += ((Number)it[24]).doubleValue();
//			}
//			if(it[25] instanceof Number){
//				x25 += ((Number)it[25]).doubleValue();
//			}
//			if(it[26] instanceof Number){
//				x26 += ((Number)it[26]).doubleValue();
//			}
//			if(it[27] instanceof Number){
//				x27 += ((Number)it[27]).doubleValue();
//			}
//			if(it[28] instanceof Number){
//				x28 += ((Number)it[28]).doubleValue();
//			}
//			if(it[29] instanceof Number){
//				x29 += ((Number)it[29]).doubleValue();
//			}
			
			x20 = add(x20,it[20]);
			x21 = add(x21,it[21]);
			x22 = add(x22,it[22]);
			x23 = add(x23,it[23]);
			x24 = add(x24,it[24]);
			x25 = add(x25,it[25]);
			x26 = add(x26,it[26]);
			x27 = add(x27,it[27]);
			x28 = add(x28,it[28]);
			x29 = add(x29,it[29]);
			
			//资源优势
			if(it[30].equals("是")){
				B31 += 1;
			}
			if(it[31].equals("是")){
				B32 += 1;
			}
			if(it[32].equals("是")){
				B33 += 1;
			}
			if(it[33].equals("是")){
				B34 += 1;
			}
			if(it[34].equals("是")){
				B35 += 1;
			}
			if(it[35].equals("是")){
				B36 += 1;
			}
			if(it[36].equals("是")){
				B37 += 1;
			}
			if(it[37] instanceof Number){
				B38 += ((Number)it[37]).intValue();
			}
			if(it[38] instanceof Number){
				B39 += ((Number)it[38]).intValue();
			}
//			if(it[39] instanceof Number){
//				B391 += ((Number)it[39]).doubleValue();
//			}
			
			B391 = add(B391,it[39]);
			
			if(it[40] instanceof Number){
				B40 += ((Number)it[40]).intValue();
			}
//			if(it[41] instanceof Number){
//				B401 += ((Number)it[41]).doubleValue();
//			}
			
			B401 = add(B401,it[41]);
			
			if(it[42] instanceof Number){
				B41 += ((Number)it[42]).intValue();
			}
//			if(it[43] instanceof Number){
//				B411 += ((Number)it[43]).doubleValue();
//			}
			
			B411 = add(B411,it[43]);
			
			if(it[44] instanceof Number){
				B42 += ((Number)it[44]).intValue();
			}
//			if(it[45] instanceof Number){
//				B421 += ((Number)it[45]).doubleValue();
//			}
			
			B421 = add(B421,it[45]);
			
			if(it[46] instanceof Number){
				B43 += ((Number)it[46]).intValue();
			}
//			if(it[47] instanceof Number){
//				B431 += ((Number)it[47]).doubleValue();
//			}
			
			B431 = add(B431,it[47]);
			
//			if(it[48] instanceof Number){
//				B44 += ((Number)it[48]).doubleValue();
//			}
			if("是".equals(it[48])){
				B44 += 1;
			}
			//是否有挂点领导
			if("是".equals(it[49])){
				B45 += 1;
			}
			//是否建立工作小组
			if("是".equals(it[50])){
				B46 += 1;
			}
			//工作小组总人数
			if(it[51] instanceof Number){
				B461 += ((Number)it[51]).intValue();
			}
			//是否建立规划专家指导组
			if("是".equals(it[52])){
				B47 += 1;
			}
			if("是".equals(it[53])){
				B48 += 1;
			}
			if("是".equals(it[54])){
				B49 += 1;
			}
			if("是".equals(it[55])){
				B50 += 1;
			}
        	
        }
		HSSFRow mRow1=sheet.createRow(i);
        HSSFCell mCell1 = mRow1.createCell(0);
        mCell1.setCellValue("全省汇总：");
        HSSFCell mCell2 = mRow1.createCell(8);
        mCell2.setCellValue(B4);
        HSSFCell mCell3 = mRow1.createCell(9);
        mCell3.setCellValue(B5);
        HSSFCell mCell4 = mRow1.createCell(10);
        mCell4.setCellValue(B6);
        HSSFCell mCell5 = mRow1.createCell(11);
        mCell5.setCellValue(B7);
        HSSFCell mCell6 = mRow1.createCell(12);
        mCell6.setCellValue(B8);
        HSSFCell mCell7 = mRow1.createCell(13);
        mCell7.setCellValue(B9);
        HSSFCell mCell8 = mRow1.createCell(14);
        mCell8.setCellValue(B10);
        HSSFCell mCell9 = mRow1.createCell(15);
        mCell9.setCellValue(B11);
        HSSFCell mCell10 = mRow1.createCell(16);
        mCell10.setCellValue(B12);
        HSSFCell mCell11 = mRow1.createCell(17);
        mCell11.setCellValue(B13);
        HSSFCell mCell12 = mRow1.createCell(18);
        mCell12.setCellValue(B14);
        HSSFCell mCell13 = mRow1.createCell(19);
        mCell13.setCellValue(B15);
        HSSFCell mCell14 = mRow1.createCell(20);
        mCell14.setCellValue(B16);
        
        HSSFCell mCell15 = mRow1.createCell(21);
        mCell15.setCellValue(x20);
        HSSFCell mCell16 = mRow1.createCell(22);
        mCell16.setCellValue(x21);
        HSSFCell mCell17 = mRow1.createCell(23);
        mCell17.setCellValue(x22);
        HSSFCell mCell18 = mRow1.createCell(24);
        mCell18.setCellValue(x23);
        HSSFCell mCell19 = mRow1.createCell(25);
        mCell19.setCellValue(x24);
        HSSFCell mCell20 = mRow1.createCell(26);
        mCell20.setCellValue(x25);
        HSSFCell mCell21 = mRow1.createCell(27);
        mCell21.setCellValue(x26);
        HSSFCell mCell22 = mRow1.createCell(28);
        mCell22.setCellValue(x27);
        HSSFCell mCell23 = mRow1.createCell(29);
        mCell23.setCellValue(x28);
        HSSFCell mCell24 = mRow1.createCell(30);
        mCell24.setCellValue(x29);
        
        HSSFCell mCell25 = mRow1.createCell(31);
        mCell25.setCellValue(B31);
        HSSFCell mCell26 = mRow1.createCell(32);
        mCell26.setCellValue(B32);
        HSSFCell mCell27 = mRow1.createCell(33);
        mCell27.setCellValue(B33);
        HSSFCell mCell28 = mRow1.createCell(34);
        mCell28.setCellValue(B34);
        HSSFCell mCell29 = mRow1.createCell(35);
        mCell29.setCellValue(B35);
        HSSFCell mCell30 = mRow1.createCell(36);
        mCell30.setCellValue(B36);
        HSSFCell mCell31 = mRow1.createCell(37);
        mCell31.setCellValue(B37);
        HSSFCell mCell32 = mRow1.createCell(38);
        mCell32.setCellValue(B38);
        
        HSSFCell mCell33 = mRow1.createCell(39);
        mCell33.setCellValue(B39);
        HSSFCell mCell34 = mRow1.createCell(40);
        mCell34.setCellValue(B391);
        HSSFCell mCell35 = mRow1.createCell(41);
        mCell35.setCellValue(B40);
        HSSFCell mCell36 = mRow1.createCell(42);
        mCell36.setCellValue(B401);
        HSSFCell mCell37 = mRow1.createCell(43);
        mCell37.setCellValue(B41);
        HSSFCell mCell38 = mRow1.createCell(44);
        mCell38.setCellValue(B411);
        HSSFCell mCell39 = mRow1.createCell(45);
        mCell39.setCellValue(B42);
        HSSFCell mCell40 = mRow1.createCell(46);
        mCell40.setCellValue(B421);
        HSSFCell mCell41 = mRow1.createCell(47);
        mCell41.setCellValue(B43);
        HSSFCell mCell42 = mRow1.createCell(48);
        mCell42.setCellValue(B431);
        HSSFCell mCell43 = mRow1.createCell(49);
        mCell43.setCellValue(B44);
       
        HSSFCell mCell44 = mRow1.createCell(50);
        mCell44.setCellValue(B45);
        HSSFCell mCell45 = mRow1.createCell(51);
        mCell45.setCellValue(B46);
        HSSFCell mCell46 = mRow1.createCell(52);
        mCell46.setCellValue(B461);
        HSSFCell mCell47 = mRow1.createCell(53);
        mCell47.setCellValue(B47);
        HSSFCell mCell48 = mRow1.createCell(54);
        mCell48.setCellValue(B48);
        HSSFCell mCell49 = mRow1.createCell(55);
        mCell49.setCellValue(B49);
        HSSFCell mCell50 = mRow1.createCell(56);
        mCell50.setCellValue(B50);
        
        mCell1.setCellStyle(style);
        mCell2.setCellStyle(mStyle);
        mCell3.setCellStyle(mStyle);
        mCell4.setCellStyle(mStyle);
        mCell5.setCellStyle(mStyle);
        mCell6.setCellStyle(mStyle);
        mCell7.setCellStyle(mStyle);
        mCell8.setCellStyle(mStyle);
        mCell9.setCellStyle(mStyle);
        mCell10.setCellStyle(mStyle);
        mCell11.setCellStyle(mStyle);
        mCell12.setCellStyle(mStyle);
        mCell13.setCellStyle(mStyle);
        mCell14.setCellStyle(mStyle);
        mCell15.setCellStyle(mStyle);
        mCell16.setCellStyle(mStyle);
        mCell17.setCellStyle(mStyle);
        mCell18.setCellStyle(mStyle);
        mCell19.setCellStyle(mStyle);
        mCell20.setCellStyle(mStyle);
        mCell21.setCellStyle(mStyle);
        mCell22.setCellStyle(mStyle);
        mCell23.setCellStyle(mStyle);
        mCell24.setCellStyle(mStyle);
        mCell25.setCellStyle(mStyle);
        mCell26.setCellStyle(mStyle);
        mCell27.setCellStyle(mStyle);
        mCell28.setCellStyle(mStyle);
        mCell29.setCellStyle(mStyle);
        mCell30.setCellStyle(mStyle);
        mCell31.setCellStyle(mStyle);
        mCell32.setCellStyle(mStyle);
        mCell33.setCellStyle(mStyle);
        mCell34.setCellStyle(mStyle);
        mCell35.setCellStyle(mStyle);
        mCell36.setCellStyle(mStyle);
        mCell37.setCellStyle(mStyle);
        mCell38.setCellStyle(mStyle);
        mCell39.setCellStyle(mStyle);
        mCell40.setCellStyle(mStyle);
        mCell41.setCellStyle(mStyle);
        mCell42.setCellStyle(mStyle);
        mCell43.setCellStyle(mStyle);
        mCell44.setCellStyle(mStyle);
        mCell45.setCellStyle(mStyle);
        mCell46.setCellStyle(mStyle);
        mCell47.setCellStyle(mStyle);
        mCell48.setCellStyle(mStyle);
        mCell49.setCellStyle(mStyle);
        mCell50.setCellStyle(mStyle);
	}
	
	protected StringBuilder statistics(Collection<Object[]> items){
		StringBuilder statis = new StringBuilder();
		int i = 1;
		double B5 = 0, B6 = 0,x20 = 0,x21 = 0,x22 = 0,x23 = 0,x24 = 0,x25 = 0,x26 = 0,x27 = 0,x28 = 0,x29 = 0;
		int  B4 = 0,B7 = 0,B8 = 0,B9 = 0,B10 = 0,B11 = 0,B12 = 0,B13 = 0,B14 = 0,B15 = 0,B38 = 0,B461 = 0;
		int B39 = 0,B40 = 0,B41 = 0,B42 = 0,B43 = 0;
		double B391 = 0,B401 = 0,B411 = 0,B421 = 0,B431 = 0;
		int B16 = 0,B31 = 0,B32 = 0,B33 = 0,B34 = 0,B35 = 0,B36 = 0,B37 = 0,B44 = 0,B45 = 0;
		int  B46 = 0,B47 = 0,B48 = 0,B49 = 0,B50 = 0;
		
		for(Object[] it : items){
			statis.append("<tr><td>").append(i++).append("</td>");
			for(Object obj : it){
				statis.append("<td>");
				if(null != obj) statis.append(obj);
				statis.append("</td>");
			}
			if(it[7] instanceof Number){
				B4 += ((Number)it[7]).intValue();
			}
//			if(it[8] instanceof Number){
//				B5 += ((Number)it[8]).doubleValue();
//			}
			
			B5 = add(B5,it[8]);
			
//			if(it[9] instanceof Number){
//				B6 += ((Number)it[9]).doubleValue();
//			}
			
			B6 = add(B6,it[9]);
			
			if(it[10] instanceof Number){
				B7 += ((Number)it[10]).intValue();
			}
			if(it[11] instanceof Number){
				B8 += ((Number)it[11]).intValue();
			}
			if(it[12] instanceof Number){
				B9 += ((Number)it[12]).intValue();
			}
			if(it[13] instanceof Number){
				B10 += ((Number)it[13]).intValue();
			}
			if(it[14] instanceof Number){
				B11 += ((Number)it[14]).intValue();
			}
			if(it[15] instanceof Number){
				B12 += ((Number)it[15]).intValue();
			}
			if(it[16] instanceof Number){
				B13 += ((Number)it[16]).intValue();
			}
			if(it[17] instanceof Number){
				B14 += ((Number)it[17]).intValue();
			}
			if(it[18] instanceof Number){
				B15 += ((Number)it[18]).intValue();
			}
			if("是".equals(it[19])){
				B16 += 1;
			}
			
//			if(it[20] instanceof Number){
//				x20 += ((Number)it[20]).doubleValue();
//			}
//			if(it[21] instanceof Number){
//				x21 += ((Number)it[21]).doubleValue();
//			}
//			if(it[22] instanceof Number){
//				x22 += ((Number)it[22]).doubleValue();
//			}
//			if(it[23] instanceof Number){
//				x23 += ((Number)it[23]).doubleValue();
//			}
//			if(it[24] instanceof Number){
//				x24 += ((Number)it[24]).doubleValue();
//			}
//			if(it[25] instanceof Number){
//				x25 += ((Number)it[25]).doubleValue();
//			}
//			if(it[26] instanceof Number){
//				x26 += ((Number)it[26]).doubleValue();
//			}
//			if(it[27] instanceof Number){
//				x27 += ((Number)it[27]).doubleValue();
//			}
//			if(it[28] instanceof Number){
//				x28 += ((Number)it[28]).doubleValue();
//			}
//			if(it[29] instanceof Number){
//				x29 += ((Number)it[29]).doubleValue();
//			}
			
			x20 = add(x20,it[20]);
			x21 = add(x21,it[21]);
			x22 = add(x22,it[22]);
			x23 = add(x23,it[23]);
			x24 = add(x24,it[24]);
			x25 = add(x25,it[25]);
			x26 = add(x26,it[26]);
			x27 = add(x27,it[27]);
			x28 = add(x28,it[28]);
			x29 = add(x29,it[29]);
			
			//资源优势
			if(it[30].equals("是")){
				B31 += 1;
			}
			if(it[31].equals("是")){
				B32 += 1;
			}
			if(it[32].equals("是")){
				B33 += 1;
			}
			if(it[33].equals("是")){
				B34 += 1;
			}
			if(it[34].equals("是")){
				B35 += 1;
			}
			if(it[35].equals("是")){
				B36 += 1;
			}
			if(it[36].equals("是")){
				B37 += 1;
			}
			if(it[37] instanceof Number){
				B38 += ((Number)it[37]).intValue();
			}
			if(it[38] instanceof Number){
				B39 += ((Number)it[38]).intValue();
			}
//			if(it[39] instanceof Number){
//				B391 += ((Number)it[39]).doubleValue();
//			}
			
			B391 = add(B391,it[39]);
			
			if(it[40] instanceof Number){
				B40 += ((Number)it[40]).intValue();
			}
//			if(it[41] instanceof Number){
//				B401 += ((Number)it[41]).doubleValue();
//			}
			
			B401 = add(B401,it[41]);
			
			if(it[42] instanceof Number){
				B41 += ((Number)it[42]).intValue();
			}
//			if(it[43] instanceof Number){
//				B411 += ((Number)it[43]).doubleValue();
//			}
			
			B411 = add(B411,it[43]);
			
			if(it[44] instanceof Number){
				B42 += ((Number)it[44]).intValue();
			}
//			if(it[45] instanceof Number){
//				B421 += ((Number)it[45]).doubleValue();
//			}
			
			B421 = add(B421,it[45]);
			
			if(it[46] instanceof Number){
				B43 += ((Number)it[46]).intValue();
			}
//			if(it[47] instanceof Number){
//				B431 += ((Number)it[47]).doubleValue();
//			}
			
			B431 = add(B431,it[47]);
			
//			if(it[48] instanceof Number){
//				B44 += ((Number)it[48]).doubleValue();
//			}
			if("是".equals(it[48])){
				B44 += 1;
			}
			//是否有挂点领导
			if("是".equals(it[49])){
				B45 += 1;
			}
			//是否建立工作小组
			if("是".equals(it[50])){
				B46 += 1;
			}
			//工作小组总人数
			if(it[51] instanceof Number){
				B461 += ((Number)it[51]).intValue();
			}
			//是否建立规划专家指导组
			if("是".equals(it[52])){
				B47 += 1;
			}
			if("是".equals(it[53])){
				B48 += 1;
			}
			if("是".equals(it[54])){
				B49 += 1;
			}
			if("是".equals(it[55])){
				B50 += 1;
			}
			
			statis.append("</tr>");
		}
		
		statis.append("<tr><td></td><td></td><td></td><td></td><td>全省汇总：</td><td></td><td></td><td></td><td>");
		statis.append(B4).append("</td><td>");
		statis.append(B5).append("</td><td>");
		statis.append(B6).append("</td><td>");
		statis.append(B7).append("</td><td>");
		statis.append(B8).append("</td><td>");
		statis.append(B9).append("</td><td>");
		statis.append(B10).append("</td><td>");
		statis.append(B11).append("</td><td>");
		statis.append(B12).append("</td><td>");
		statis.append(B13).append("</td><td>");
		statis.append(B14).append("</td><td>");
		statis.append(B15).append("</td><td>");
		statis.append(B16).append("</td><td>");
		statis.append(x20).append("</td><td>");
		statis.append(x21).append("</td><td>");
		statis.append(x22).append("</td><td>");
		statis.append(x23).append("</td><td>");
		statis.append(x24).append("</td><td>");
		statis.append(x25).append("</td><td>");
		statis.append(x26).append("</td><td>");
		statis.append(x27).append("</td><td>");
		statis.append(x28).append("</td><td>");
		statis.append(x29).append("</td><td>");
		statis.append(B31).append("</td><td>");
		statis.append(B32).append("</td><td>");
		statis.append(B33).append("</td><td>");
		statis.append(B34).append("</td><td>");
		statis.append(B35).append("</td><td>");
		statis.append(B36).append("</td><td>");
		statis.append(B37).append("</td><td>");
		statis.append(B38).append("</td><td>");
		statis.append(B39).append("</td><td>");
		statis.append(B391).append("</td><td>");
		statis.append(B40).append("</td><td>");
		statis.append(B401).append("</td><td>");
		statis.append(B41).append("</td><td>");
		statis.append(B411).append("</td><td>");
		statis.append(B42).append("</td><td>");
		statis.append(B421).append("</td><td>");
		statis.append(B43).append("</td><td>");
		statis.append(B431).append("</td><td>");
		statis.append(B44).append("</td><td>");
		statis.append(B45).append("</td><td>");
		statis.append(B46).append("</td><td>");
		statis.append(B461).append("</td><td>");
		statis.append(B47).append("</td><td>");
		statis.append(B48).append("</td><td>");
		statis.append(B49).append("</td><td>");
		
		statis.append(B50).append("</td><td>");
		
		return statis;
	}
}
