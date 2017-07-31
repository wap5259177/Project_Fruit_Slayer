function getStyle(obj, attr){
	return obj.currentStyle?obj.currentStyle[attr]:getComputedStyle(obj, false)[attr];
}

function show(ts){
	var oBg = document.getElementById('bg');
	var oFrame = document.getElementById('frame');
	//var src=document.getElementById("_img").src;
	with(oFrame.style){display = 'block',top = this.offsetTop +'px',left = this.offsetLeft +'px',width = this.offsetWidth +'px',height = this.offsetHeight +'px';}
	oFrame.innerHTML = '<div><img src="'+(ts.src)+'" /></div><div id="bottom"><ul><li class="close" onclick="close_()"></li></ul></div>';
	var oBot = document.getElementById('bottom');
	//with(oBot.style){top = oFrame.offsetTop + oFrame.height + 10 + 'px',left = this.offsetLeft +'px',width = this.offsetWidth +'px',height = this.offsetHeight +'px';}
	var oImg = oFrame.getElementsByTagName('img')[0];
	var iWidth = oImg.width;
	var iHeight = oImg.height;
	var iLeft = parseInt((document.documentElement.clientWidth / 2) - (iWidth /2));
	var iTop = parseInt((document.documentElement.clientHeight / 2) - (iHeight /2) - 50);
	with(oImg.style){height = width = '100%';}
	startMove(oFrame, {opacity:100, left:iLeft, top:iTop, width:iWidth, height:iHeight});
	oBg.style.display = 'block';
	oBot.style.display = 'block';
	iNow = this.index + 1;
}

function close_(){
	var oBox = document.getElementById('box');
	var oBg = document.getElementById('bg');
	var oBot = document.getElementById('bottom');
	var aBli = oBot.getElementsByTagName('li');
	var oFrame = document.getElementById('frame');
	var aImg = oBox.getElementsByTagName('img');
	oFrame.style.display = 'none';
    oBg.style.display = 'none';
	oBot.style.display = 'none';
	oFrame.onmousedown = null;
	oFrame.style.cursor = 'auto';
	
	return true;
}

window.onload = function(){
	/*
	var oBox = document.getElementById('box');
	var oBg = document.getElementById('bg');
	var oBot = document.getElementById('bottom');
	var aBli = oBot.getElementsByTagName('li');
	var oFrame = document.getElementById('frame');
	var aLi = oBox.getElementsByTagName('li');
	var aImg = oBox.getElementsByTagName('img');
	var i = iNow =  0;
	for(i=0;i<aLi.length;i++){
		aLi[i].index = i;
		alert(i);
		aLi[i].onclick = function(){
			var src=document.getElementById("_img").src;
			with(oFrame.style){display = 'block',top = this.offsetTop +'px',left = this.offsetLeft +'px',width = this.offsetWidth +'px',height = this.offsetHeight +'px';}
			oFrame.innerHTML = '<img src="'+(src)+'" />';
			var oImg = oFrame.getElementsByTagName('img')[0];
			var iWidth = oImg.width;
			var iHeight = oImg.height;
			var iLeft = parseInt((document.documentElement.clientWidth / 2) - (iWidth /2));
			var iTop = parseInt((document.documentElement.clientHeight / 2) - (iHeight /2) - 50);
			with(oImg.style){height = width = '100%';}
			startMove(oFrame, {opacity:100, left:iLeft, top:iTop, width:iWidth, height:iHeight});
			oBg.style.display = 'block';
			oBot.style.display = 'block';
			iNow = this.index + 1;
		};
	}*/
	document.onmousedown = function(){
		return false
	};

	
	
	
};
function startMove(obj, json, onEnd){
	clearInterval(obj.timer);
	obj.timer=setInterval(function (){
		doMove(obj, json, onEnd);
	}, 30);
}
function doMove(obj, json, onEnd){
	var attr='';
	var bStop=true;
	for(attr in json){
		var iCur=0;
		if(attr=='opacity'){
			iCur=parseInt(parseFloat(getStyle(obj, attr))*100);
		}else{
			iCur=parseInt(getStyle(obj, attr));
		}
		var iSpeed=(json[attr]-iCur)/5;
		iSpeed=iSpeed>0?Math.ceil(iSpeed):Math.floor(iSpeed);
		
		if(json[attr]!=iCur){
			bStop=false;
		}
		if(attr=='opacity'){
			obj.style.filter='alpha(opacity:'+(iCur+iSpeed)+')';
			obj.style.opacity=(iCur+iSpeed)/100;
		}else{
			obj.style[attr]=iCur+iSpeed+'px';
		}
	}
	if(bStop){
		clearInterval(obj.timer);		
		if(onEnd){
			onEnd();
		}
	}
}