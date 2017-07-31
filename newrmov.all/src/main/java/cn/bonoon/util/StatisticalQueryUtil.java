package cn.bonoon.util;

import java.text.DecimalFormat;

/**
 * 
 * 
 * @author wsf
 * @creation 2017-3-14 统计查询的一个工具类
 */
public class StatisticalQueryUtil {
	private static DecimalFormat df = new DecimalFormat("######0.00");

	/**
	 * 默认格式化double为俩位小数点
	 * 可通过本类方法set(String howMany)设置小数点后保留几位
	 * @param o
	 *            你要格式化的小数
	 * 
	 */
	public static String format(Object o) {
		return df.format(o);
	}

	/**
	 * 
	 * @param howMany
	 *            小数点后想要留几位格式为eg:"######0.00"为保留2位小数点 "######0.000"为保留3位小数点
	 */
	public static void set(String howMany) {
		df = new DecimalFormat(howMany);
	}
}
