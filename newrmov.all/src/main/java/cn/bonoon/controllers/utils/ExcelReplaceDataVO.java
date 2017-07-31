package cn.bonoon.controllers.utils;


/**
 * Excel替换内容存储对象
 * 
 * @author Administrator
 * 
 */
public class ExcelReplaceDataVO {
	private final int row;// Excel单元格行
	private final int column;// Excel单元格列
	private final  String key;// 替换的关键字
	private final String value;// 替换的文本	
	public ExcelReplaceDataVO(int row, int column, String key, String value) {
		super();
		this.row = row;
		this.column = column;
		this.key = key;
		this.value = value;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
}

