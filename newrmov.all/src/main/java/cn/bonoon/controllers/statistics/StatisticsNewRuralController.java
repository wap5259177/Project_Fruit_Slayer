package cn.bonoon.controllers.statistics;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.core.NewRuralService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

public abstract class StatisticsNewRuralController extends AbstractPanelController{

	@Autowired
	protected NewRuralService statisticsService;
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();

		int bi = model.intParameter("batch");
		event.setVmPath("statistics/new-rural");
		String batch = BatchHelper.getValue(bi);
		Collection<Object[]> items = getItems(event.getUser(), batch);
		event.getModel().addObject("content", statistics(items)).addObject("batch", batch);
	}

	@RequestMapping(value = "!{key}/index.batch", method = POST)
	public String loadItems(Model model, String batch){
		Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
		model.addAttribute("layout", "layout-empty.vm").addAttribute("content", statistics(items));
		return "statistics/new-rural-items";
	}
	
	@RequestMapping(value = "!{key}/index.excel", method = GET)
	public void excel(HttpServletRequest request, HttpServletResponse response, String batch){
		HSSFWorkbook workbook = null;
		try{
		String _name = "主体村情况汇总统计.xls";
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + new String(_name.getBytes("GBK"), "iso8859-1"));
		Collection<Object[]> items = getItems(getUser(), BatchHelper.batch(batch));
		 // 声明一个工作薄  
        workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet("主体村情况汇总统计");  
        
        sheet.setDefaultColumnWidth(15);  
        sheet.setDefaultRowHeight((short)(15.625*25));
        sheet.setColumnWidth(5, 5000); 
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(56, 6000);
        
     // 生成一个样式  
        HSSFCellStyle style1 = workbook.createCellStyle();  
        // 设置这些样式  
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        // 生成一个字体  
        HSSFFont font1 = workbook.createFont();  
        font1.setColor(HSSFColor.BLACK.index);  
        font1.setFontHeightInPoints((short) 20);  
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        
        // 把字体应用到当前的样式  
        style1.setFont(font1); 
        
     // 生成一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();  
        // 设置这些样式  
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        // 生成一个字体  
        HSSFFont font2 = workbook.createFont();  
        font2.setColor(HSSFColor.BLACK.index);  
        font2.setFontHeightInPoints((short) 12);  
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        // 把字体应用到当前的样式  
        style2.setFont(font2); 
        
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        // 设置这些样式  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        style.setWrapText(true);
        // 生成一个字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.BLACK.index);  
        font.setFontHeightInPoints((short) 11); 
        
        // 把字体应用到当前的样式  
        style.setFont(font); 
        
        // 生成一个样式  
        HSSFCellStyle mStyle = workbook.createCellStyle();  
        // 设置这些样式  
        mStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        mStyle.setWrapText(true);
        // 生成一个字体  
        HSSFFont mFont = workbook.createFont();  
        mFont.setColor(HSSFColor.BLACK.index);  
        mFont.setFontHeightInPoints((short) 10); 
        
        // 把字体应用到当前的样式  
        mStyle.setFont(mFont); 
        
        //标题栏
        HSSFRow ro = sheet.createRow(0);  
        HSSFCell cel = ro.createCell(0);
        cel.setCellValue("广东省新农村连片示范建设工程台账簿 - 所有主体建设村情况累计汇总统计");
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 65));
        
        cel.setCellStyle(style1); 
        
        //第一行
        HSSFRow row = sheet.createRow(3);  //第一行
        HSSFCell cell = row.createCell(0);
        HSSFCell cell1 = row.createCell(1);
        HSSFCell cell2 = row.createCell(2);
        HSSFCell cell3 = row.createCell(3);
        HSSFCell cell4 = row.createCell(4);
        HSSFCell cell5 = row.createCell(5);
        HSSFCell cell6 = row.createCell(6);
        HSSFCell cell7 = row.createCell(7);
        HSSFCell cell8 = row.createCell(8);//16
        HSSFCell cell9 = row.createCell(9);//27
        HSSFCell cell10 = row.createCell(18);//36
        HSSFCell cell11 = row.createCell(29);//45
        HSSFCell cell12 = row.createCell(38);//56
        HSSFCell cell13 = row.createCell(47);//57
        HSSFCell cell14 = row.createCell(58);//59
        HSSFCell cell15 = row.createCell(59);//63
        HSSFCell cell16 = row.createCell(61);//
        HSSFCell cell17 = row.createCell(65);//
        
        cell.setCellStyle(style); 
        cell1.setCellStyle(style); 
        cell2.setCellStyle(style); 
        cell3.setCellStyle(style); 
        cell4.setCellStyle(style); 
        cell5.setCellStyle(style); 
        cell6.setCellStyle(style); 
        cell7.setCellStyle(style2); 
        cell8.setCellStyle(style2); 
        cell9.setCellStyle(style2); 
        cell10.setCellStyle(style2); 
        cell11.setCellStyle(style2); 
        cell12.setCellStyle(style2); 
        cell13.setCellStyle(style2); 
        cell14.setCellStyle(style2); 
        cell15.setCellStyle(style2); 
        cell16.setCellStyle(style2); 
        cell17.setCellStyle(style2); 
        
      //第一行标题
        cell.setCellValue("序号");
        cell1.setCellValue("批次[A15]");
        cell2.setCellValue("报备年度[A14]");
        cell3.setCellValue("地市");
        cell4.setCellValue("县(区)");
        cell5.setCellValue("示范片名称[A12]");
        cell6.setCellValue("建设主题名称[A13]");
        cell7.setCellValue("行政村名");
        cell8.setCellValue("自然村名");
        cell9.setCellValue("（一）基本情况");
        cell10.setCellValue("（二）基础设施建设和环境卫生整治情况");
        cell11.setCellValue("（三）农村旧房整治情况");
//        cell12.setCellValue("（四）资源优势情况");
//        cell13.setCellValue("（五）农村公共服务情况");
//        cell14.setCellValue("（六）挂点县领导");
//        cell15.setCellValue("（七）工作小组");
//        cell16.setCellValue("（八）规划进展");
//        cell17.setCellValue("（九）村民理事会");
        
        //20160328
        cell12.setCellValue("（四）村民理事会");
        cell13.setCellValue("（五）民生问题调查梳理情况");
        
        
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 5, 5));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 6, 6));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 7, 7));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 9, 21));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 22, 32));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 33, 40));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 41, 43));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 44, 47));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 47, 57));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 58, 58));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 59, 60));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 61, 64));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 65, 67));
        
        //第二行
        HSSFRow row1 = sheet.createRow(4);  
        HSSFCell tCell = row1.createCell(9);
        HSSFCell tCel11= row1.createCell(10);
        HSSFCell tCel12= row1.createCell(11);
        HSSFCell tCel13= row1.createCell(12);
        HSSFCell tCel14= row1.createCell(13);
        HSSFCell tCel15= row1.createCell(14);
        HSSFCell tCel16= row1.createCell(15);
        HSSFCell tCel17= row1.createCell(16);
        HSSFCell tCel18= row1.createCell(17);
        HSSFCell tCel19= row1.createCell(18);
        HSSFCell tCel110= row1.createCell(19);
        HSSFCell tCel111= row1.createCell(20);
        HSSFCell tCel112= row1.createCell(21);
        HSSFCell tCel113= row1.createCell(22);
        HSSFCell tCel114= row1.createCell(23);
        HSSFCell tCel115= row1.createCell(24);
        HSSFCell tCel116= row1.createCell(25);
        HSSFCell tCel117= row1.createCell(26);
        HSSFCell tCel118= row1.createCell(27);
        HSSFCell tCel119= row1.createCell(28);
        HSSFCell tCel120= row1.createCell(29);
        HSSFCell tCel121= row1.createCell(30);
        HSSFCell tCel122= row1.createCell(31);
        HSSFCell tCel123= row1.createCell(32);
        HSSFCell tCel124= row1.createCell(33);
        HSSFCell tCel125= row1.createCell(34);
        HSSFCell tCel126= row1.createCell(35);
        HSSFCell tCel127= row1.createCell(36);
        HSSFCell tCel128= row1.createCell(37);
        HSSFCell tCel129= row1.createCell(38);
        HSSFCell tCel130= row1.createCell(39);
        HSSFCell tCel131= row1.createCell(40);
        HSSFCell tCel132= row1.createCell(41);
        HSSFCell tCel133= row1.createCell(42);
        HSSFCell tCel134= row1.createCell(43);
        HSSFCell tCel135= row1.createCell(44);
        HSSFCell tCel136= row1.createCell(45);
        HSSFCell tCel137= row1.createCell(46);
        HSSFCell tCel138= row1.createCell(47);
//        HSSFCell tCel139= row1.createCell(48);
//        HSSFCell tCel140= row1.createCell(49);
//        HSSFCell tCel141= row1.createCell(50);
//        HSSFCell tCel142= row1.createCell(51);
//        HSSFCell tCel143= row1.createCell(52);
//        HSSFCell tCel144= row1.createCell(53);
//        HSSFCell tCel145= row1.createCell(54);
//        HSSFCell tCel146= row1.createCell(55);
//        HSSFCell tCel147= row1.createCell(56);
//        HSSFCell tCel148= row1.createCell(57);
//        HSSFCell tCel149= row1.createCell(58);
//        HSSFCell tCel150= row1.createCell(59);
//        HSSFCell tCel151= row1.createCell(60);
//        HSSFCell tCel152= row1.createCell(61);
//        HSSFCell tCel153= row1.createCell(62);
//        HSSFCell tCel154= row1.createCell(63);
//        HSSFCell tCel155= row1.createCell(64);
//        HSSFCell tCel156= row1.createCell(65);
//        HSSFCell tCel157= row1.createCell(66);
//        HSSFCell tCel158= row1.createCell(67);
        
        tCell.setCellStyle(style); 
        tCel11.setCellStyle(style); 
        tCel12.setCellStyle(style); 
        tCel13.setCellStyle(style); 
        tCel14.setCellStyle(style); 
        tCel15.setCellStyle(style); 
        tCel16.setCellStyle(style); 
        tCel17.setCellStyle(style); 
        tCel18.setCellStyle(style); 
        tCel19.setCellStyle(style); 
        tCel110.setCellStyle(style); 
        tCel111.setCellStyle(style); 
        tCel112.setCellStyle(style); 
        tCel113.setCellStyle(style); 
        tCel114.setCellStyle(style); 
        tCel115.setCellStyle(style); 
        tCel116.setCellStyle(style); 
        tCel117.setCellStyle(style); 
        tCel118.setCellStyle(style); 
        tCel119.setCellStyle(style); 
        tCel120.setCellStyle(style); 
        tCel121.setCellStyle(style); 
        tCel122.setCellStyle(style); 
        tCel123.setCellStyle(style); 
        tCel124.setCellStyle(style); 
        tCel125.setCellStyle(style);
        tCel126.setCellStyle(style); 
        tCel127.setCellStyle(style); 
        tCel128.setCellStyle(style); 
        tCel129.setCellStyle(style); 
        tCel130.setCellStyle(style); 
        tCel131.setCellStyle(style); 
        tCel132.setCellStyle(style); 
        tCel133.setCellStyle(style); 
        tCel134.setCellStyle(style); 
        tCel135.setCellStyle(style); 
        tCel136.setCellStyle(style); 
        tCel137.setCellStyle(style); 
        tCel138.setCellStyle(style); 
//        tCel139.setCellStyle(style); 
//        tCel140.setCellStyle(style); 
//        tCel141.setCellStyle(style); 
//        tCel142.setCellStyle(style); 
//        tCel143.setCellStyle(style); 
//        tCel144.setCellStyle(style); 
//        tCel145.setCellStyle(style); 
//        tCel146.setCellStyle(style); 
//        tCel147.setCellStyle(style); 
//        tCel148.setCellStyle(style); 
//        tCel149.setCellStyle(style); 
//        tCel150.setCellStyle(style); 
//        tCel151.setCellStyle(style);
//        tCel152.setCellStyle(style);
//        tCel153.setCellStyle(style);
//        tCel154.setCellStyle(style);
//        tCel155.setCellStyle(style);
//        tCel156.setCellStyle(style);
//        tCel157.setCellStyle(style);
//        tCel158.setCellStyle(style);
        
      //第二行标题
        tCell.setCellValue("总面积");
        tCel11.setCellValue("耕地面积");
        tCel12.setCellValue("户数");
        tCel13.setCellValue("人口数");
        tCel14.setCellValue("劳动力总人数");
        
        tCel15.setCellValue("贫困户数");
        tCel16.setCellValue("贫困人口数");
        tCel17.setCellValue("低保户数");
        tCel18.setCellValue("低保人口数");
        tCel19.setCellValue("五保户数");
        tCel110.setCellValue("需改造的危房户数");
        tCel111.setCellValue("广东名村个数");
        tCel112.setCellValue("“两不具备”整村推进村个数");
        
//        tCel15.setCellValue("省级扶贫开发重点村（贫困村）个数");
       
        
       
//        tCel18.setCellValue("上年度农民年人均纯收入");
        tCel113.setCellValue("村内道路和入户路硬底化率");
        tCel114.setCellValue("通自来水个数");
        tCel115.setCellValue("开展农田水利基础设施和现代渔港建设");
        tCel116.setCellValue("整治小山塘、小灌区、小水坡、小泵站和小堤防");
        tCel117.setCellValue("配套建设高标准基本农田、标准鱼塘");
        tCel118.setCellValue("已完成环境卫生整治个数");
        tCel119.setCellValue("已开展村庄垃圾治理个数");
        tCel120.setCellValue("建立村保洁队伍村个数");
        tCel121.setCellValue("建立村保洁队伍保洁员人数");
        tCel122.setCellValue("村民使用卫生厕所户数");
        tCel123.setCellValue("建立污水处理设施村个数");
        tCel124.setCellValue("无价值旧村旧房拆除");
        tCel125.setCellValue("统一拆旧建新");
        tCel126.setCellValue("古建筑保护");
        tCel127.setCellValue("编制旧房整治改造规划村个数");
        tCel128.setCellValue("提供民居住宅设计标准图村个数");
        tCel129.setCellValue("农家（乡村）旅馆-规划建设");
        tCel130.setCellValue("农家（乡村）旅馆-完成建设");
        tCel131.setCellValue("统一民居外立面风貌村个数");
//        tCel128.setCellValue("统一民居外立面风貌风格列表");
//        tCel129.setCellValue("人文历史");
//        tCel130.setCellValue("自然生态");
//        tCel131.setCellValue("民居风貌");
//        tCel132.setCellValue("乡村旅游");
//        tCel133.setCellValue("特色产业");
//        tCel134.setCellValue("渔业渔港");
//        tCel135.setCellValue("其他");
//        tCel136.setCellValue("现有的或正在打造的旅游景点或节点个数");
//        tCel137.setCellValue("现有的或正在打造的旅游景点或节点名称列表");
//        tCel138.setCellValue("文化活动场所");
//        tCel139.setCellValue("文化活动场所");
//        tCel140.setCellValue("乡村公园");
//        tCel141.setCellValue("乡村公园");
//        tCel142.setCellValue("文体广场");
//        tCel143.setCellValue("文体广场");
//        tCel144.setCellValue("村级卫生站");
//        tCel145.setCellValue("村级卫生站");
//        tCel146.setCellValue("乡村公厕");
//        tCel147.setCellValue("乡村公厕");
//        tCel148.setCellValue("建立统一的村级公共服务管理平台村个数");
//        tCel149.setCellValue("有挂点县领导的村个数");
//        tCel150.setCellValue("有工作小组的村个数");
//        tCel151.setCellValue("工作小组总人数");
//        tCel152.setCellValue("建立规划专家指导组村个数");
//        tCel153.setCellValue("完成总体规划村个数");
//        tCel154.setCellValue("完成连线连片规划村个数");
//        tCel155.setCellValue("完成村庄深度规划设计村个数");
        tCel132.setCellValue("建立村民理事会村个数");
        tCel133.setCellValue("理事会成员数");
        tCel134.setCellValue("制定村规民约和章程村个数");
        
        tCel135.setCellValue("完成自然村个数");
        tCel136.setCellValue("梳理出项目数");
        tCel137.setCellValue("排查出的矛盾纠纷数");
        tCel138.setCellValue("其中已化解数");
        
        
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 10, 10));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 11, 11));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 12, 12));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 13, 13));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 14, 14));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 15, 15));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 16, 16));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 17, 17));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 18, 18));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 19, 19));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 20, 20));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 21, 21));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 22, 22));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 23, 23));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 24, 24));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 25, 25));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 26, 26));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 27, 27));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 28, 28));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 29, 29));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 30, 30));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 31, 31));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 32, 32));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 33, 33));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 34, 34));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 35, 35));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 36, 36));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 37, 37));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 38, 38));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 39, 39));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 40, 40));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 41, 41));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 42, 42));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 43, 43));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 44, 44));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 45, 45));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 46, 46));
        sheet.addMergedRegion(new CellRangeAddress(4, 6, 47, 47));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 48, 48));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 49, 49));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 50, 50));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 51, 51));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 52, 52));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 53, 53));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 54, 54));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 55, 55));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 56, 56));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 57, 57));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 58, 58));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 59, 59));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 60, 60));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 61, 61));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 62, 62));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 63, 63));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 64, 64));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 65, 65));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 66, 66));
//        sheet.addMergedRegion(new CellRangeAddress(4, 6, 67, 67));
        
        //第三行
        HSSFRow row2 = sheet.createRow(7);  
        HSSFCell sCell = row2.createCell(9);
        HSSFCell sCell1= row2.createCell(10);
        HSSFCell sCell2= row2.createCell(11);
        HSSFCell sCell3= row2.createCell(12);
        HSSFCell sCell4= row2.createCell(13);
        HSSFCell sCell5= row2.createCell(14);
        HSSFCell sCell6= row2.createCell(15);
        HSSFCell sCell7= row2.createCell(16);
        HSSFCell sCell8= row2.createCell(17);
        HSSFCell sCell9= row2.createCell(18);
        HSSFCell sCell10= row2.createCell(19);
        HSSFCell sCell11= row2.createCell(20);
        HSSFCell sCell12= row2.createCell(21);
        HSSFCell sCell13= row2.createCell(22);
        HSSFCell sCell14= row2.createCell(23);
        HSSFCell sCell15= row2.createCell(24);
        HSSFCell sCell16= row2.createCell(25);
        HSSFCell sCell17= row2.createCell(26);
        HSSFCell sCell18= row2.createCell(27);
        HSSFCell sCell19= row2.createCell(28);
        HSSFCell sCell20= row2.createCell(29);
        HSSFCell sCell21= row2.createCell(30);
        HSSFCell sCell22= row2.createCell(31);
        HSSFCell sCell23= row2.createCell(32);
        HSSFCell sCell24= row2.createCell(33);
        HSSFCell sCell25= row2.createCell(34);
        HSSFCell sCell26= row2.createCell(35);
        HSSFCell sCell27= row2.createCell(36);
        HSSFCell sCell28= row2.createCell(37);
        HSSFCell sCell29= row2.createCell(38);
        HSSFCell sCell30= row2.createCell(39);
        HSSFCell sCell31= row2.createCell(40);
        HSSFCell sCell32= row2.createCell(41);
        HSSFCell sCell33= row2.createCell(42);
        HSSFCell sCell34= row2.createCell(43);
        HSSFCell sCell35= row2.createCell(44);
        HSSFCell sCell36= row2.createCell(45);
        HSSFCell sCell37= row2.createCell(46);
        HSSFCell sCell38= row2.createCell(47);
//        HSSFCell sCell39= row2.createCell(48);
//        HSSFCell sCell40= row2.createCell(49);
//        HSSFCell sCell41= row2.createCell(50);
//        HSSFCell sCell42= row2.createCell(51);
//        HSSFCell sCell43= row2.createCell(52);
//        HSSFCell sCell44= row2.createCell(53);
//        HSSFCell sCell45= row2.createCell(54);
//        HSSFCell sCell46= row2.createCell(55);
//        HSSFCell sCell47= row2.createCell(56);
//        HSSFCell sCell48= row2.createCell(57);
//        HSSFCell sCell49= row2.createCell(58);
//        HSSFCell sCell50= row2.createCell(59);
//        HSSFCell sCell51= row2.createCell(60);
//        HSSFCell sCell52= row2.createCell(61);
//        HSSFCell sCell53= row2.createCell(62);
//        HSSFCell sCell54= row2.createCell(63);
//        HSSFCell sCell55= row2.createCell(64);
//        HSSFCell sCell56= row2.createCell(65);
//        HSSFCell sCell57= row2.createCell(66);
//        HSSFCell sCell58= row2.createCell(67);
        
        sCell.setCellStyle(style);
        sCell1.setCellStyle(style);
        sCell2.setCellStyle(style);
        sCell3.setCellStyle(style);
        sCell4.setCellStyle(style);
        sCell5.setCellStyle(style);
        sCell6.setCellStyle(style);
        sCell7.setCellStyle(style);
        sCell8.setCellStyle(style);
        sCell9.setCellStyle(style);
        sCell10.setCellStyle(style);
        sCell11.setCellStyle(style);
        sCell12.setCellStyle(style);
        sCell13.setCellStyle(style);
        sCell14.setCellStyle(style);
        sCell15.setCellStyle(style);
        sCell16.setCellStyle(style);
        sCell17.setCellStyle(style);
        sCell18.setCellStyle(style);
        sCell19.setCellStyle(style);
        sCell20.setCellStyle(style);
        sCell21.setCellStyle(style);
        sCell22.setCellStyle(style);
        sCell23.setCellStyle(style);
        sCell24.setCellStyle(style);
        sCell25.setCellStyle(style);
        sCell26.setCellStyle(style);
        sCell27.setCellStyle(style);
        sCell28.setCellStyle(style);
        sCell29.setCellStyle(style);
        sCell30.setCellStyle(style);
        sCell31.setCellStyle(style);
        sCell32.setCellStyle(style);
        sCell33.setCellStyle(style);
        sCell34.setCellStyle(style);
        sCell35.setCellStyle(style);
        sCell36.setCellStyle(style);
        sCell37.setCellStyle(style);
        sCell38.setCellStyle(style);
//        sCell39.setCellStyle(style);
//        sCell40.setCellStyle(style);
//        sCell41.setCellStyle(style);
//        sCell42.setCellStyle(style);
//        sCell43.setCellStyle(style);
//        sCell44.setCellStyle(style);
//        sCell45.setCellStyle(style);
//        sCell46.setCellStyle(style);
//        sCell47.setCellStyle(style);
//        sCell48.setCellStyle(style);
//        sCell49.setCellStyle(style);
//        sCell50.setCellStyle(style);
//        sCell51.setCellStyle(style);
//        sCell52.setCellStyle(style);
//        sCell53.setCellStyle(style);
//        sCell54.setCellStyle(style);
//        sCell55.setCellStyle(style);
//        sCell56.setCellStyle(style);
//        sCell57.setCellStyle(style);
//        sCell58.setCellStyle(style);
        
      //第三行标题
        sCell.setCellValue("B5[亩]");
        sCell1.setCellValue("B6[亩]");
        sCell2.setCellValue("B7[户]");
        sCell3.setCellValue("B8[人]");
        sCell4.setCellValue("B9[人]");
        sCell5.setCellValue("");
        sCell6.setCellValue("");
        sCell7.setCellValue("");
        sCell8.setCellValue("");
        sCell9.setCellValue("");
        sCell10.setCellValue("");
        sCell11.setCellValue("B11");
        sCell12.setCellValue("B12");
        sCell13.setCellValue("B14[公里]");
        sCell14.setCellValue("B15");
        sCell15.setCellValue("B16[宗]");
        sCell16.setCellValue("B17[个]");
        sCell17.setCellValue("B18[亩]");
        sCell18.setCellValue("B19");
        sCell19.setCellValue("B20");
        sCell20.setCellValue("B21");
        sCell21.setCellValue("B21[人]");
        sCell22.setCellValue("B22[户]");
        sCell23.setCellValue("B23");
        sCell24.setCellValue("B24[间]");
        sCell25.setCellValue("B25[间]");
        sCell26.setCellValue("B26[间]");
        sCell27.setCellValue("B27");
        sCell28.setCellValue("B28");
        sCell29.setCellValue("B29[间]");
        sCell30.setCellValue("B29[间]");
        sCell31.setCellValue("B30");
        sCell32.setCellValue("B53");
        sCell33.setCellValue("B54[人]");
        sCell34.setCellValue("B56");
        sCell35.setCellValue("");
        sCell36.setCellValue("");
        sCell37.setCellValue("");
        sCell38.setCellValue("");
//        sCell39.setCellValue("B39[平方米]");
//        sCell40.setCellValue("B40[个]");
//        sCell41.setCellValue("B40[平方米]");
//        sCell42.setCellValue("B41[个]");
//        sCell43.setCellValue("B41[平方米]");
//        sCell44.setCellValue("B42[个]");
//        sCell45.setCellValue("B42[平方米]");
//        sCell46.setCellValue("B43[个]");
//        sCell47.setCellValue("B43[平方米]");
//        sCell48.setCellValue("B44[个]");
//        sCell49.setCellValue("B45[个]");
//        sCell50.setCellValue("B46[个]");
//        sCell51.setCellValue("B46[人]");
//        sCell52.setCellValue("B47[个]");
//        sCell53.setCellValue("B49[个]");
//        sCell54.setCellValue("B50[个]");
//        sCell55.setCellValue("B51[个]");
//        sCell56.setCellValue("B53[个]");
//        sCell57.setCellValue("B54[人]");
//        sCell58.setCellValue("B56[个]");
        
        
      
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 10, 10));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 11, 11));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 12, 12));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 13, 13));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 14, 14));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 15, 15));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 16, 16));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 17, 17));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 18, 18));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 19, 19));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 20, 20));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 21, 21));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 22, 22));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 23, 23));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 24, 24));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 25, 25));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 26, 26));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 27, 27));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 28, 28));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 29, 29));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 30, 30));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 31, 31));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 32, 32));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 33, 33));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 34, 34));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 35, 35));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 36, 36));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 37, 37));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 38, 38));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 39, 39));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 40, 40));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 41, 41));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 42, 42));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 43, 43));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 44, 44));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 45, 45));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 46, 46));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 47, 47));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 48, 48));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 49, 49));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 50, 50));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 51, 51));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 52, 52));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 53, 53));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 54, 54));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 55, 55));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 56, 56));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 57, 57));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 58, 58));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 59, 59));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 60, 60));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 61, 61));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 62, 62));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 63, 63));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 64, 64));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 65, 65));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 66, 66));
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 67, 67));
        statistics(items, mStyle, style, sheet);
        workbook.write(response.getOutputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void statistics(Collection<Object[]> items, HSSFCellStyle mStyle, HSSFCellStyle style, HSSFSheet sheet) {

        //显示items里面的数据
        int i=8, j = 1, k = 0;
        
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
        	
        	  
        }
        	}

	protected abstract Collection<Object[]> getItems(IOperator opt, String batch);
	protected StringBuilder statistics(Collection<Object[]> items){
		StringBuilder statis = new StringBuilder();
		int i = 1;
		for(Object[] it : items){
			statis.append("<tr><td>").append(i++).append("</td>");//序号
			for(Object obj : it){
				if(obj!=null){					
					statis.append("<td>").append(obj).append("</td>");
				}
				else{
					statis.append("<td></td>");
				}
			}
			statis.append("</tr>");
		}
		return statis;
	}

}
