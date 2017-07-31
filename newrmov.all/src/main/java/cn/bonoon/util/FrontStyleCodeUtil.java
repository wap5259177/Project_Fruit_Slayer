package cn.bonoon.util;

public class FrontStyleCodeUtil {

private static FrontStyleCodeUtil fscu=new FrontStyleCodeUtil();
public static FrontStyleCodeUtil getFrontStyleCodeUtilEntity(){
	return fscu;
}
private FrontStyleCodeUtil() {
	super();
}

// cdn
private String bootstrap_min_css = "<link href='http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css' rel='stylesheet'>";
private String bootstrap_theme_min_css = "<script src='http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap-theme.min.css'></script>" ;
private String jquery_min_js = "<script src='http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js'></script>" ;
//这个返回的js在下拉菜单时不起作用的得在页面引用才能起作用
private String bootstrap_min_js = "<script src='http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js'></script>";

// 本地bootstrap时间插件
//这个bootstrap css是专门给时间插件用的如果用了上面的bootstrap_min_css则时间插件无效 版本问题
private String dateTimePicker_Bootstrap_bootstrap_min_css = "<link rel='stylesheet' href='/res/css/dateTimePicker_Bootstrap/bootstrap.min.css' type='text/css' />";
private String dateTimePicker_Bootstrap_bootstrap_datetimepicker_min_css = "<link rel='stylesheet' href='/res/css/dateTimePicker_Bootstrap/bootstrap-datetimepicker.min.css' type='text/css' />";
private String dateTimePicker_Bootstrap_bootstrap_datetimepicker_fr_js = "<script type='text/javascript' src='/res/js/dateTimePicker_Bootstrap/bootstrap-datetimepicker.fr.js'></script>";
private String dateTimePicker_Bootstrap_bootstrap_datetimepicker_js = "<script type='text/javascript' src='/res/js/dateTimePicker_Bootstrap/bootstrap-datetimepicker.js'></script>";
private String bootstrap_datetimepicker_zh_CN_js="<script type='text/javascript' src='/res/js/dateTimePicker_Bootstrap/bootstrap-datetimepicker.zh-CN.js'></script>";
public static String quoteStyle(String... jsAndCss) {
	String resultJsAndCss ="";
	for (String var : jsAndCss)
		resultJsAndCss += var;
	return resultJsAndCss;
}


public String getBootstrap_min_css() {
	return bootstrap_min_css;
}
public void setBootstrap_min_css(String bootstrap_min_css) {
	this.bootstrap_min_css = bootstrap_min_css;
}
public String getBootstrap_theme_min_css() {
	return bootstrap_theme_min_css;
}
public void setBootstrap_theme_min_css(String bootstrap_theme_min_css) {
	this.bootstrap_theme_min_css = bootstrap_theme_min_css;
}
public String getJquery_min_js() {
	return jquery_min_js;
}
public void setJquery_min_js(String jquery_min_js) {
	this.jquery_min_js = jquery_min_js;
}
public String getBootstrap_min_js() {
	return bootstrap_min_js;
}
public void setBootstrap_min_js(String bootstrap_min_js) {
	this.bootstrap_min_js = bootstrap_min_js;
}
public String getDateTimePicker_Bootstrap_bootstrap_min_css() {
	return dateTimePicker_Bootstrap_bootstrap_min_css;
}
public void setDateTimePicker_Bootstrap_bootstrap_min_css(
		String dateTimePicker_Bootstrap_bootstrap_min_css) {
	this.dateTimePicker_Bootstrap_bootstrap_min_css = dateTimePicker_Bootstrap_bootstrap_min_css;
}
public String getDateTimePicker_Bootstrap_bootstrap_datetimepicker_min_css() {
	return dateTimePicker_Bootstrap_bootstrap_datetimepicker_min_css;
}
public void setDateTimePicker_Bootstrap_bootstrap_datetimepicker_min_css(
		String dateTimePicker_Bootstrap_bootstrap_datetimepicker_min_css) {
	this.dateTimePicker_Bootstrap_bootstrap_datetimepicker_min_css = dateTimePicker_Bootstrap_bootstrap_datetimepicker_min_css;
}
public String getDateTimePicker_Bootstrap_bootstrap_datetimepicker_fr_js() {
	return dateTimePicker_Bootstrap_bootstrap_datetimepicker_fr_js;
}
public void setDateTimePicker_Bootstrap_bootstrap_datetimepicker_fr_js(
		String dateTimePicker_Bootstrap_bootstrap_datetimepicker_fr_js) {
	this.dateTimePicker_Bootstrap_bootstrap_datetimepicker_fr_js = dateTimePicker_Bootstrap_bootstrap_datetimepicker_fr_js;
}
public String getDateTimePicker_Bootstrap_bootstrap_datetimepicker_js() {
	return dateTimePicker_Bootstrap_bootstrap_datetimepicker_js;
}
public void setDateTimePicker_Bootstrap_bootstrap_datetimepicker_js(
		String dateTimePicker_Bootstrap_bootstrap_datetimepicker_js) {
	this.dateTimePicker_Bootstrap_bootstrap_datetimepicker_js = dateTimePicker_Bootstrap_bootstrap_datetimepicker_js;
}
public String getBootstrap_datetimepicker_zh_CN_js() {
	return bootstrap_datetimepicker_zh_CN_js;
}
public void setBootstrap_datetimepicker_zh_CN_js(
		String bootstrap_datetimepicker_zh_CN_js) {
	this.bootstrap_datetimepicker_zh_CN_js = bootstrap_datetimepicker_zh_CN_js;
}

}
