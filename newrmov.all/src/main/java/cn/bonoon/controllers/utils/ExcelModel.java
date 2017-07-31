package cn.bonoon.controllers.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * 将变量绑定到xls
 * @author Administrator
 *
 */
public class ExcelModel {
	Map<String, Cell> map = new HashMap<String, Cell>();
	private final HSSFWorkbook wb;
	final String REGEX = "\\$\\!\\{\\w+\\}";
	final String HEADER_REGEX = "\\$\\#\\{\\w+\\}";
	Pattern pattern = Pattern.compile(REGEX);
	Pattern headerPatter = Pattern.compile(HEADER_REGEX);
	final HttpServletResponse response;
 
	/**
	 * 
	 * @param templateFilePath 模板文件路径及名称
	 * @param response
	 * @param codedFileName 下载文件的名
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ExcelModel(String templateFilePath,HttpServletResponse response,String codedFileName) throws FileNotFoundException,
			IOException {
		wb = initMap(templateFilePath);
		this.response = response;
		response.setContentType("application/vnd.ms-excel");
		// 进行转码，使其支持中文文件名
		codedFileName = new String(codedFileName.getBytes("UTF-8"), "ISO8859-1");//
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
		
	}


	/**
	 * 将变量绑定到一个单元格
	 * @param para xls模板中定义的变量名，在xls中的符号为西文的"！{para}"
	 * @param value java中的变量
	 */
	public void bindCell(String para, String value) {
		bindCell(para,value,0);
	}
	public void bindCellAttr(String para, String value) {
		bindCell(para,value,0);
	}
	
	/**
	 * 将变量绑定到单元格
	 * @param para
	 * @param value
	 * @param sheet，从0开始算
	 */
	public void bindCell(String para, String value,int sheet) {
		Cell c = map.get(String.format("$!{%s}@%d",para,sheet));
		if (c == null) {
			throw new RuntimeException("XLS模板文件中没有名为【" + para + "】的参数");
		}
		String str = c.getStringCellValue();
		String s = str.replaceAll(REGEX, value==null?"":value);
		c.setCellValue(s);
	}

	private HSSFWorkbook initMap(String templateFilePath)
			throws FileNotFoundException, IOException {		
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(
				new FileInputStream(templateFilePath)));
		for(int i = 0; i < wb.getNumberOfSheets();i++){
			HSSFSheet sheet = wb.getSheetAt(i);			
			for (Row row : sheet) {
				for (Cell cell : row) {
					switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING: {
							String str = cell.getStringCellValue();
							Matcher m = pattern.matcher(str);
							if (m.find()) {
								//System.out.println("MATCH:" + m.group());
								String key = cellKey(m.group(),i);
								if(map.containsKey(key))
									throw new RuntimeException(String.format("已经包含了占位符[%s]", m.group()));
								map.put(key, cell);
							}
						}
					}
				}
			}
		}
		return wb;
	}
	
	private String cellKey(String match,int sheet){
		return String.format("%s@%d",match,sheet);
	}

	/**
	 * 一定要被执行（只能执行一次），否则无法下载
	 * @throws IOException
	 */
	private boolean writeOnce = false;
	public void write() throws Throwable {
		if(writeOnce){
			throw new RuntimeException("写文件已经被执行！");
		}
		try{
			clearPlaceholders();
			wb.write(response.getOutputStream());
		}catch(Throwable t){
			throw t;
		}
		writeOnce = true;
	}

	// 清空未被替换的占位符
	private void clearPlaceholders() {		
		HSSFSheet sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
			for (Cell cell : row) {
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING: {
					String str = cell.getStringCellValue();
					String s = str.replaceAll(REGEX, "").replaceAll(HEADER_REGEX, "");
					cell.setCellValue(s);					
					}
				}
			}
		}
	}
	
	public void bindColumns(int headerRow, Object[] objs) {
		bindColumns(headerRow, objs,0);
	}

	/**
	 * 将对象绑定到excel，中添加多行数据
	 * @param headerRow--变量行 0-based（指示占位符所在行）
	 * @param objs--要绑定的对象集合
	 * @param sheetNo--要绑定sheet，从0开始，不指定默认为0
	 * 模板中的语法为"$#{变量名}"，其中的变量名和objs对象中属性名一致。
	 * 从headerRow开始往下填充objs.length行数据。
	 * 
	 */
	public void bindColumns(int headerRow, Object[] objs,int sheetNo) {	
		// 找到模板中变量和列标的对应关系，存入map

		HSSFSheet sheet = wb.getSheetAt(sheetNo);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Cell cell : sheet.getRow(headerRow)) {
			//System.out.println(cell.getCellType());
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING: {
				String str = cell.getStringCellValue();
				System.out.println(str);
				Matcher m = headerPatter.matcher(str);
				if (m.find()) {
					//System.out.println("MATCH:" + m.group());
					String fn = m.group();
					fn = fn.substring(3, fn.length()-1).trim();
					map.put(fn, cell.getColumnIndex());
					cell.setCellValue("");
				}
			}
			}
		}
		if(map.isEmpty())
			return;
		HSSFCellStyle style = wb.createCellStyle();
		// 遍历对象序列，每个对象都找到模板中所指示的属性，取值后设给cell
		int rowIdx = headerRow;
		for(Object o : objs){
			Row row = sheet.getRow(rowIdx);
			//createCellForMoney(row,col,HSSFCell.CELL_TYPE_NUMERIC,style,yourMoneyValue);//金额
			if(row == null){
				row = sheet.createRow(rowIdx);
			}
			rowIdx++;
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()){
				String fieldName = it.next();
				Cell cell = row.getCell(map.get(fieldName));
				if(cell == null)
					cell = row.createCell(map.get(fieldName));
				
				Object val = this.getFieldValueByName(fieldName, o);
				if(val !=null){
//					if(val instanceof Double){
//						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//						val = String.format("%,.2f",val);
//					}
//					else if(val instanceof Integer){
//						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//					}
						
					cell.setCellValue(val.toString());
				}
			}
		}
	}
	
//	public void createCellForMoney(Row row,int columnIndex,int cellType,HSSFCellStyle style,def value){
//		Cell cell = row.createCell(columnIndex)
//        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00")); // 两位小数，只加了一个格式的自定义，反应到Excle上面为自定义的金额，其他格式类似
//        cell.setCellStyle(style)
//        cell.setCellType(cellType)
//        cell.setCellValue(value)
//    }


	/**
	 * 获取对象属性，返回一个字符串数组
	 * 
	 * @param o
	 *            对象
	 * @return String[] 字符串数组
	 */
	private String[] getFiledName(Object o) {
		try {
			Field[] fields = o.getClass().getDeclaredFields();
			String[] fieldNames = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				fieldNames[i] = fields[i].getName();
			}
			return fieldNames;
		} catch (SecurityException e) {
			e.printStackTrace();
			//System.out.println(e.toString());
		}
		return null;
	}

	/**
	 * 使用反射根据属性名称获取属性值
	 * 
	 * @param fieldName
	 *            属性名称
	 * @param o
	 *            操作对象
	 * @return Object 属性值
	 */

	private Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(fieldName+"属性不存在");
			return null;
		}
	}
}
