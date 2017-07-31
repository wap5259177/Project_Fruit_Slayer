/**
 *  页面加载等待页面
 *
 */
 var height = window.screen.height;
 var width = window.screen.width;
 var leftW = 300;
 if(width>1200){
 	leftW = 500;
 }else if(width>1000){
 	leftW = 350;
 }else {
 	leftW = 100;
 }
 
 var _html = "<div id='ploading' style='z-index:9999999;position:absolute;left:0;width:100%;height:"+height+"px;top:0;background:#E0ECFF;opacity:1.0;filter:alpha(opacity=100);'>\
 <div style='position:absolute;  cursor1:wait;left:"+leftW+"px;top:200px;width:auto;height:16px;padding:12px 5px 10px 30px;\
 background-color:#E0ECFF;border:2px solid #E0ECFF;color:#000;'>正在加载，请等待...</div></div>";
<!--  background-color:#ccc;border:2px solid #ccc; -->

 window.onload = function(){
 	
	setTimeout(a,100);
 }	 

function a(){
	var _mask = document.getElementById('ploading');_mask.parentNode.removeChild(_mask);	
}
document.write(_html);