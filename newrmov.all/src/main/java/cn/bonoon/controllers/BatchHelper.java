package cn.bonoon.controllers;

import cn.bonoon.kernel.util.StringHelper;

public class BatchHelper {

	private static final String[] batchs = {"一","二","三","珠三角"};
	
	public static int length(){
		return batchs.length;
	}
	
	public static String getValue(int i){
		if(i >= 0 && i < batchs.length) return batchs[i];
		return batchs[0];
	}
	
	public static int indexOf(String batch){
		for(int i = 0; i < batchs.length; i++){
			if(batchs[i].equals(batch)) return i;
		}
		return batchs.length - 1;
	}
	
	public static boolean contains(String batch){
		for(String b : batchs){
			if(b.equals(batch)) return true;
		}
		return false;
	}
	
	public static String get(String batch){
		if(contains(batch)) return batch;
		return batchs[batchs.length - 1];
	}
	
	//protected final static String batchs = "一二三";
	public static Object batchSelect(String batch){
		StringBuilder html = new StringBuilder();
		html.append("<div style='float:left'><select id='sel-batch'><option value='0' ");
		html.append(_check("0", batch));
		html.append(">第一批</option><option value='1' ");
		html.append(_check("1", batch));
		html.append(">第二批</option><option value='2' ");
		html.append(_check("2", batch));
		html.append(">第三批</option><option value='3' ");
		html.append(_check("3", batch));
		html.append(">珠三角</option></select></div>");
		html.append("<div style='float:left'><a id='btn_load' href='javascipt:void(null);' style='padding-left:10px;' onclick=\"this.href='?batch='+jQuery('#sel-batch').val()\">统计</a></div>");
		return html;
	}
	
	protected static String _check(String val, String batch){
		if(null != batch){
			if(batch.equals(val))
				return "selected='selected'";
		}
		return "";
	}
	
//	public static String batch(int index){
//		if(index >= batchs.length()){
//			index = 0;
//		}
//		return String.valueOf(batchs.charAt(index));
//	}
	
	public static String batch(String indexStr){
		int index = StringHelper.toint(indexStr);
		return getValue(index);
	}
}
