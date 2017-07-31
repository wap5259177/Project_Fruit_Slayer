/*!
 * 核心框架V3.0
 * 
 * 统一命名规范
 * 所有框架定义的函数、属性，都以"__"开关
 */

/**
 * 对访问路径追加随机数，这样每次访问的路径都不相同，就不会被浏览器缓存起来
 */
function __toUrl(href) {
	if (href.indexOf('?') === -1) {
		href = href + '?_=' + Math.random();
	} else {
		href = href + '&_=' + Math.random();
	}
	return href;
}

function __ajaxSuccess(__resultData) {
	if (typeof __resultData === "string"){
		__resultData = eval('(' + __resultData + ')');
	}
	if (__resultData.error) {
		$.bupmsg.alert('提示', __resultData.msg, 'error');
		return false;
	}
	return __resultData;
}

function __ajaxError(){
	$.bupmsg.alert('提示', '网络连接出错，请重试', 'error');
}

function __ajaxComplete(){
	$.unblockUI();
}

function __extendsGridLoader(_241, _242, _243){
	var opts = $(this).bupgrid("settings"); // options
	if (!opts.url) {
		return false;
	}
	$.ajax({
		type : opts.method,
		url : opts.url,
		data : _241,
		dataType : "json",
		success : function(data) {
			if('error' in data){
				if(data.error){
					_242([]);
					alert(data.msg);
				}else{
					_242(data.data);
				}
			}else{
				_242(data);
			}
		},
		error : function() {
			_243.apply(this, arguments);
		}
	});
}

(function($) {
	$.fn.__get = function( _name, _success ){
		if(confirm('是否确定执行[' + _name + ']操作？')){
			lock('正在执行[' + _name + ']操作...');
			$.ajax({
				url:__toUrl(this.attr('href')),
				type:'get',
				success:function(__resultData){
					__resultData = __ajaxSuccess(__resultData);
					if(__resultData && $.isFunction( _success )){
						_success.call( this, __resultData );
					}
				},
				error:__ajaxError,
				complete:__ajaxComplete
			});
		}
	}
	$.fn.__post = function( _name, _data, _success ){
		if(confirm('是否确定执行[' + _name + ']操作？')){
			lock('正在执行[' + _name + ']操作...');
			$.ajax({
				url:__toUrl(this.attr('href')),
				data:_data,
				type:'post',
				traditional:true,
				success:function(__resultData){
					__resultData = __ajaxSuccess(__resultData);
					if(__resultData && $.isFunction( _success )){
						_success.call( this, __resultData );
					}
				},
				error:__ajaxError,
				complete:__ajaxComplete
			});
		}
	}
	$.fn.__form = function( _key, _name, _before, _success ){
		lock('正在[' + _name + ']数据...');
		jQuery('#' + _key + ' form').bupform('submit', {
			url:this.attr('href'),
			onSubmit:function(){
				if(jQuery(this).bupform('validate')){
					if($.isFunction( _before )){
						if(_before.call( this ) === false){
							$.unblockUI();
							return false;
						}
						return true;
					}
					//lock('正在[' + _name + ']数据...');
					return true;
				}
				$.unblockUI();
				return false;
			},
			success:function(__resultData){
				$.unblockUI();
				__resultData = __ajaxSuccess(__resultData);
				if(__resultData){
					if($.isFunction( _success )){
						_success.call( this, __resultData );
					}
					jQuery('#' + _key).bupdlg('close');
				}
			}
		});
	}
	$.fn.__multiple = function( _grid, _name, _field, _status ){
		var rows = this[_grid]('getSelections');
		if (!rows || rows.length == 0){
			$.bupmsg.alert('提示', '至少选择一条记录!', 'info');
			return false;
		}
		var ids=[];
		if(typeof _field === 'string' && $.isArray(_status)){
			for (i = 0; i < rows.length; i++) {
				var checkStatus=rows[i][_field];
				for(j = 0; j < _status.length; j++){
					if(checkStatus == _status[j]){
						ids[i]=rows[i]['id'];
						break;
					}
				}
				if(!ids[i]){
					$.bupmsg.alert('提示', '第' + i + '条记录不允许进行' + _name + '操作!', 'info');
					return false;
				}
			}
		}else{
			for (i = 0; i < rows.length; i++) {
				ids[i]=rows[i]['id'];
			}
		}
		return {ids:ids};
	}
	$.fn.__single = function( _grid, _name, _field, _status ){
		var rows = this[_grid]('getSelections');
		if (!rows || rows.length != 1){
			$.bupmsg.alert('提示', '请只选择一条记录!', 'info');
			return false;
		}
		if(typeof _field === 'string' && $.isArray(_status)){
			var checkStatus=rows[0][_field];
			for(j = 0; j < _status.length; j++){
				if(checkStatus == _status[j]){
					return {id:rows[0]['id']};
				}
			}
			return false;
		}
		return {id:rows[0]['id']};
	}
	$.fn.__menu = function( _items, _click ){
		var _option = {onClick:function(node){ $('a', node.target).click(); }};
		
		if($.isArray(_items)){
			$('#cm-tree-sys-menus').bupmenu({
				onClick:function(item){
					var node = $('body').data('dt-current-selected-menu-item');
					for(var _item in _items){
						if(item.name === _item.name){
							_item.event.call(item);
						}
					}
				}
			});
			_option.onContextMenu = function(e,node){
				e.preventDefault();
				jQuery('body').data('dt-current-selected-menu-item', node);
				jQuery('#tree-sys-menus').buptree('select', node.target);
				jQuery('#cm-tree-sys-menus').bupmenu('show',{left: e.pageX,top: e.pageY});
				return false;
			};
		}else if($.isFunction(_items)){
			_click = _items;
		}
		this.buptree(_option);
		
		if($.isFunction(_click)){
			$('#tree-sys-menus a').click(function(){
				$('#tree-sys-menus a').removeClass('menuSelected');
				$(this).addClass('menuSelected');
				_click.call(this);
				return false;
			});
		}else{
			$('#tree-sys-menus a').click(function(){
				window.location.href=this.href;
				return false;
			});
		}
	}
})(jQuery);
//-------------------------------------------------以下的旧版权的源码，有部分可能需要在新的版本中被删除
/*!
 * jQuery blockUI plugin
 * Version 2.35 (23-SEP-2010)
 * @requires jQuery v1.2.3 or later
 *
 * Examples at: http://malsup.com/jquery/block/
 * Copyright (c) 2007-2008 M. Alsup
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 * Thanks to Amir-Hossein Sobhi for some excellent contributions!
 */

;(function($) {

if (/1\.(0|1|2)\.(0|1|2)/.test($.fn.jquery) || /^1.1/.test($.fn.jquery)) {
	alert('blockUI requires jQuery v1.2.3 or later!  You are using v' + $.fn.jquery);
	return;
}

$.fn._fadeIn = $.fn.fadeIn;

var noOp = function() {};

// this bit is to ensure we don't call setExpression when we shouldn't (with extra muscle to handle
// retarded userAgent strings on Vista)
var mode = document.documentMode || 0;
var setExpr = $.browser.msie && (($.browser.version < 8 && !mode) || mode < 8);
var ie6 = $.browser.msie && /MSIE 6.0/.test(navigator.userAgent) && !mode;

// global $ methods for blocking/unblocking the entire page
$.blockUI   = function(opts) { install(window, opts); };
$.unblockUI = function(opts) { remove(window, opts); };

// convenience method for quick growl-like notifications  (http://www.google.com/search?q=growl)
$.growlUI = function(title, message, timeout, onClose) {
	var $m = $('<div class="growlUI"></div>');
	if (title) $m.append('<h1>'+title+'</h1>');
	if (message) $m.append('<h2>'+message+'</h2>');
	if (timeout == undefined) timeout = 3000;
	$.blockUI({
		message: $m, fadeIn: 700, fadeOut: 1000, centerY: false,
		timeout: timeout, showOverlay: false,
		onUnblock: onClose, 
		css: $.blockUI.defaults.growlCSS
	});
};

// plugin method for blocking element content
$.fn.block = function(opts) {
	return this.unblock({ fadeOut: 0 }).each(function() {
		if ($.css(this,'position') == 'static')
			this.style.position = 'relative';
		if ($.browser.msie)
			this.style.zoom = 1; // force 'hasLayout'
		install(this, opts);
	});
};

// plugin method for unblocking element content
$.fn.unblock = function(opts) {
	return this.each(function() {
		remove(this, opts);
	});
};

$.blockUI.version = 2.35; // 2nd generation blocking at no extra cost!

// override these in your code to change the default behavior and style
$.blockUI.defaults = {
	// message displayed when blocking (use null for no message)
	message:  '<h1>Please wait...</h1>',

	title: null,	  // title string; only used when theme == true
	draggable: true,  // only used when theme == true (requires jquery-ui.js to be loaded)
	
	theme: false, // set to true to use with jQuery UI themes
	
	// styles for the message when blocking; if you wish to disable
	// these and use an external stylesheet then do this in your code:
	// $.blockUI.defaults.css = {};
	css: {
		padding:	0,
		margin:		0,
		width:		'30%',
		top:		'40%',
		left:		'35%',
		textAlign:	'center',
		color:		'#000',
		border:		'3px solid #aaa',
		backgroundColor:'#fff',
		cursor:		'wait'
	},
	
	// minimal style set used when themes are used
	themedCSS: {
		width:	'30%',
		top:	'40%',
		left:	'35%'
	},

	// styles for the overlay
	overlayCSS:  {
		backgroundColor: '#000',
		opacity:	  	 0.6,
		cursor:		  	 'wait'
	},

	// styles applied when using $.growlUI
	growlCSS: {
		width:  	'350px',
		top:		'10px',
		left:   	'',
		right:  	'10px',
		border: 	'none',
		padding:	'5px',
		opacity:	0.6,
		cursor: 	'default',
		color:		'#fff',
		backgroundColor: '#000',
		'-webkit-border-radius': '10px',
		'-moz-border-radius':	 '10px',
		'border-radius': 		 '10px'
	},
	
	// IE issues: 'about:blank' fails on HTTPS and javascript:false is s-l-o-w
	// (hat tip to Jorge H. N. de Vasconcelos)
	iframeSrc: /^https/i.test(window.location.href || '') ? 'javascript:false' : 'about:blank',

	// force usage of iframe in non-IE browsers (handy for blocking applets)
	forceIframe: false,

	// z-index for the blocking overlay
	baseZ: 1000,

	// set these to true to have the message automatically centered
	centerX: true, // <-- only effects element blocking (page block controlled via css above)
	centerY: true,

	// allow body element to be stetched in ie6; this makes blocking look better
	// on "short" pages.  disable if you wish to prevent changes to the body height
	allowBodyStretch: true,

	// enable if you want key and mouse events to be disabled for content that is blocked
	bindEvents: true,

	// be default blockUI will supress tab navigation from leaving blocking content
	// (if bindEvents is true)
	constrainTabKey: true,

	// fadeIn time in millis; set to 0 to disable fadeIn on block
	fadeIn:  200,

	// fadeOut time in millis; set to 0 to disable fadeOut on unblock
	fadeOut:  400,

	// time in millis to wait before auto-unblocking; set to 0 to disable auto-unblock
	timeout: 0,

	// disable if you don't want to show the overlay
	showOverlay: true,

	// if true, focus will be placed in the first available input field when
	// page blocking
	focusInput: true,

	// suppresses the use of overlay styles on FF/Linux (due to performance issues with opacity)
	applyPlatformOpacityRules: true,
	
	// callback method invoked when fadeIn has completed and blocking message is visible
	onBlock: null,

	// callback method invoked when unblocking has completed; the callback is
	// passed the element that has been unblocked (which is the window object for page
	// blocks) and the options that were passed to the unblock call:
	//	 onUnblock(element, options)
	onUnblock: null,

	// don't ask; if you really must know: http://groups.google.com/group/jquery-en/browse_thread/thread/36640a8730503595/2f6a79a77a78e493#2f6a79a77a78e493
	quirksmodeOffsetHack: 4,

	// class name of the message block
	blockMsgClass: 'blockMsg'
};

// private data and functions follow...

var pageBlock = null;
var pageBlockEls = [];

function install(el, opts) {
	var full = (el == window);
	var msg = opts && opts.message !== undefined ? opts.message : undefined;
	opts = $.extend({}, $.blockUI.defaults, opts || {});
	opts.overlayCSS = $.extend({}, $.blockUI.defaults.overlayCSS, opts.overlayCSS || {});
	var css = $.extend({}, $.blockUI.defaults.css, opts.css || {});
	var themedCSS = $.extend({}, $.blockUI.defaults.themedCSS, opts.themedCSS || {});
	msg = msg === undefined ? opts.message : msg;

	// remove the current block (if there is one)
	if (full && pageBlock)
		remove(window, {fadeOut:0});

	// if an existing element is being used as the blocking content then we capture
	// its current place in the DOM (and current display style) so we can restore
	// it when we unblock
	if (msg && typeof msg != 'string' && (msg.parentNode || msg.jquery)) {
		var node = msg.jquery ? msg[0] : msg;
		var data = {};
		$(el).data('blockUI.history', data);
		data.el = node;
		data.parent = node.parentNode;
		data.display = node.style.display;
		data.position = node.style.position;
		if (data.parent)
			data.parent.removeChild(node);
	}

	var z = opts.baseZ;

	// blockUI uses 3 layers for blocking, for simplicity they are all used on every platform;
	// layer1 is the iframe layer which is used to supress bleed through of underlying content
	// layer2 is the overlay layer which has opacity and a wait cursor (by default)
	// layer3 is the message content that is displayed while blocking

	var lyr1 = ($.browser.msie || opts.forceIframe) 
		? $('<iframe class="blockUI" style="z-index:'+ (z++) +';display:none;border:none;margin:0;padding:0;position:absolute;width:100%;height:100%;top:0;left:0" src="'+opts.iframeSrc+'"></iframe>')
		: $('<div class="blockUI" style="display:none"></div>');
	var lyr2 = $('<div class="blockUI blockOverlay" style="z-index:'+ (z++) +';display:none;border:none;margin:0;padding:0;width:100%;height:100%;top:0;left:0"></div>');
	
	var lyr3, s;
	if (opts.theme && full) {
		s = '<div class="blockUI ' + opts.blockMsgClass + ' blockPage ui-dialog ui-widget ui-corner-all" style="z-index:'+z+';display:none;position:fixed">' +
				'<div class="ui-widget-header ui-dialog-titlebar ui-corner-all blockTitle">'+(opts.title || '&nbsp;')+'</div>' +
				'<div class="ui-widget-content ui-dialog-content"></div>' +
			'</div>';
	}
	else if (opts.theme) {
		s = '<div class="blockUI ' + opts.blockMsgClass + ' blockElement ui-dialog ui-widget ui-corner-all" style="z-index:'+z+';display:none;position:absolute">' +
				'<div class="ui-widget-header ui-dialog-titlebar ui-corner-all blockTitle">'+(opts.title || '&nbsp;')+'</div>' +
				'<div class="ui-widget-content ui-dialog-content"></div>' +
			'</div>';
	}
	else if (full) {
		s = '<div class="blockUI ' + opts.blockMsgClass + ' blockPage" style="z-index:'+z+';display:none;position:fixed"></div>';
	}			
	else {
		s = '<div class="blockUI ' + opts.blockMsgClass + ' blockElement" style="z-index:'+z+';display:none;position:absolute"></div>';
	}
	lyr3 = $(s);

	// if we have a message, style it
	if (msg) {
		if (opts.theme) {
			lyr3.css(themedCSS);
			lyr3.addClass('ui-widget-content');
		}
		else 
			lyr3.css(css);
	}

	// style the overlay
	if (!opts.applyPlatformOpacityRules || !($.browser.mozilla && /Linux/.test(navigator.platform)))
		lyr2.css(opts.overlayCSS);
	lyr2.css('position', full ? 'fixed' : 'absolute');

	// make iframe layer transparent in IE
	if ($.browser.msie || opts.forceIframe)
		lyr1.css('opacity',0.0);

	//$([lyr1[0],lyr2[0],lyr3[0]]).appendTo(full ? 'body' : el);
	var layers = [lyr1,lyr2,lyr3], $par = full ? $('body') : $(el);
	$.each(layers, function() {
		this.appendTo($par);
	});
	
	if (opts.theme && opts.draggable && $.fn.draggable) {
		lyr3.bupdragger({
			handle: '.ui-dialog-titlebar',
			cancel: 'li'
		});
	}

	// ie7 must use absolute positioning in quirks mode and to account for activex issues (when scrolling)
	var expr = setExpr && (!$.boxModel || $('object,embed', full ? null : el).length > 0);
	if (ie6 || expr) {
		// give body 100% height
		if (full && opts.allowBodyStretch && $.boxModel)
			$('html,body').css('height','100%');

		// fix ie6 issue when blocked element has a border width
		if ((ie6 || !$.boxModel) && !full) {
			var t = sz(el,'borderTopWidth'), l = sz(el,'borderLeftWidth');
			var fixT = t ? '(0 - '+t+')' : 0;
			var fixL = l ? '(0 - '+l+')' : 0;
		}

		// simulate fixed position
		$.each([lyr1,lyr2,lyr3], function(i,o) {
			var s = o[0].style;
			s.position = 'absolute';
			if (i < 2) {
				full ? s.setExpression('height','Math.max(document.body.scrollHeight, document.body.offsetHeight) - (jQuery.boxModel?0:'+opts.quirksmodeOffsetHack+') + "px"')
					 : s.setExpression('height','this.parentNode.offsetHeight + "px"');
				full ? s.setExpression('width','jQuery.boxModel && document.documentElement.clientWidth || document.body.clientWidth + "px"')
					 : s.setExpression('width','this.parentNode.offsetWidth + "px"');
				if (fixL) s.setExpression('left', fixL);
				if (fixT) s.setExpression('top', fixT);
			}
			else if (opts.centerY) {
				if (full) s.setExpression('top','(document.documentElement.clientHeight || document.body.clientHeight) / 2 - (this.offsetHeight / 2) + (blah = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop) + "px"');
				s.marginTop = 0;
			}
			else if (!opts.centerY && full) {
				var top = (opts.css && opts.css.top) ? parseInt(opts.css.top) : 0;
				var expression = '((document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop) + '+top+') + "px"';
				s.setExpression('top',expression);
			}
		});
	}

	// show the message
	if (msg) {
		if (opts.theme)
			lyr3.find('.ui-widget-content').append(msg);
		else
			lyr3.append(msg);
		if (msg.jquery || msg.nodeType)
			$(msg).show();
	}

	if (($.browser.msie || opts.forceIframe) && opts.showOverlay)
		lyr1.show(); // opacity is zero
	if (opts.fadeIn) {
		var cb = opts.onBlock ? opts.onBlock : noOp;
		var cb1 = (opts.showOverlay && !msg) ? cb : noOp;
		var cb2 = msg ? cb : noOp;
		if (opts.showOverlay)
			lyr2._fadeIn(opts.fadeIn, cb1);
		if (msg)
			lyr3._fadeIn(opts.fadeIn, cb2);
	}
	else {
		if (opts.showOverlay)
			lyr2.show();
		if (msg)
			lyr3.show();
		if (opts.onBlock)
			opts.onBlock();
	}

	// bind key and mouse events
	bind(1, el, opts);

	if (full) {
		pageBlock = lyr3[0];
		pageBlockEls = $(':input:enabled:visible',pageBlock);
		if (opts.focusInput)
			setTimeout(focus, 20);
	}
	else
		center(lyr3[0], opts.centerX, opts.centerY);

	if (opts.timeout) {
		// auto-unblock
		var to = setTimeout(function() {
			full ? $.unblockUI(opts) : $(el).unblock(opts);
		}, opts.timeout);
		$(el).data('blockUI.timeout', to);
	}
};

// remove the block
function remove(el, opts) {
	var full = (el == window);
	var $el = $(el);
	var data = $el.data('blockUI.history');
	var to = $el.data('blockUI.timeout');
	if (to) {
		clearTimeout(to);
		$el.removeData('blockUI.timeout');
	}
	opts = $.extend({}, $.blockUI.defaults, opts || {});
	bind(0, el, opts); // unbind events
	
	var els;
	if (full) // crazy selector to handle odd field errors in ie6/7
		els = $('body').children().filter('.blockUI').add('body > .blockUI');
	else
		els = $('.blockUI', el);

	if (full)
		pageBlock = pageBlockEls = null;

	if (opts.fadeOut) {
		els.fadeOut(opts.fadeOut);
		setTimeout(function() { reset(els,data,opts,el); }, opts.fadeOut);
	}
	else
		reset(els, data, opts, el);
};

// move blocking element back into the DOM where it started
function reset(els,data,opts,el) {
	els.each(function(i,o) {
		// remove via DOM calls so we don't lose event handlers
		if (this.parentNode)
			this.parentNode.removeChild(this);
	});

	if (data && data.el) {
		data.el.style.display = data.display;
		data.el.style.position = data.position;
		if (data.parent)
			data.parent.appendChild(data.el);
		$(el).removeData('blockUI.history');
	}

	if (typeof opts.onUnblock == 'function')
		opts.onUnblock(el,opts);
};

// bind/unbind the handler
function bind(b, el, opts) {
	var full = el == window, $el = $(el);

	// don't bother unbinding if there is nothing to unbind
	if (!b && (full && !pageBlock || !full && !$el.data('blockUI.isBlocked')))
		return;
	if (!full)
		$el.data('blockUI.isBlocked', b);

	// don't bind events when overlay is not in use or if bindEvents is false
	if (!opts.bindEvents || (b && !opts.showOverlay)) 
		return;

	// bind anchors and inputs for mouse and key events
	var events = 'mousedown mouseup keydown keypress';
	b ? $(document).bind(events, opts, handler) : $(document).unbind(events, handler);

// former impl...
//	   var $e = $('a,:input');
//	   b ? $e.bind(events, opts, handler) : $e.unbind(events, handler);
};

// event handler to suppress keyboard/mouse events when blocking
function handler(e) {
	// allow tab navigation (conditionally)
	if (e.keyCode && e.keyCode == 9) {
		if (pageBlock && e.data.constrainTabKey) {
			var els = pageBlockEls;
			var fwd = !e.shiftKey && e.target == els[els.length-1];
			var back = e.shiftKey && e.target == els[0];
			if (fwd || back) {
				setTimeout(function(){focus(back)},10);
				return false;
			}
		}
	}
	var opts = e.data;
	// allow events within the message content
	if ($(e.target).parents('div.' + opts.blockMsgClass).length > 0)
		return true;

	// allow events for content that is not being blocked
	return $(e.target).parents().children().filter('div.blockUI').length == 0;
};

function focus(back) {
	if (!pageBlockEls)
		return;
	var e = pageBlockEls[back===true ? pageBlockEls.length-1 : 0];
	if (e)
		e.focus();
};

function center(el, x, y) {
	var p = el.parentNode, s = el.style;
	var l = ((p.offsetWidth - el.offsetWidth)/2) - sz(p,'borderLeftWidth');
	var t = ((p.offsetHeight - el.offsetHeight)/2) - sz(p,'borderTopWidth');
	if (x) s.left = l > 0 ? (l+'px') : '0';
	if (y) s.top  = t > 0 ? (t+'px') : '0';
};

function sz(el, p) {
	return parseInt($.css(el,p))||0;
};

})(jQuery);
//----------------------------end blockui

/**
 * 定时器
 */
jQuery.fn.extend({
	everyTime: function(interval, label, fn, times, belay) {
		return this.each(function() {
			jQuery.timer.add(this, interval, label, fn, times, belay);
		});
	},
	oneTime: function(interval, label, fn) {
		return this.each(function() {
			jQuery.timer.add(this, interval, label, fn, 1);
		});
	},
	stopTime: function(label, fn) {
		return this.each(function() {
			jQuery.timer.remove(this, label, fn);
		});
	}
});

jQuery.extend({
	timer: {
		guid: 1,
		global: {},
		regex: /^([0-9]+)\s*(.*s)?$/,
		powers: {
			// Yeah this is major overkill...
			'ms': 1,
			'cs': 10,
			'ds': 100,
			's': 1000,
			'das': 10000,
			'hs': 100000,
			'ks': 1000000
		},
		timeParse: function(value) {
			if (value == undefined || value == null)
				return null;
			var result = this.regex.exec(jQuery.trim(value.toString()));
			if (result[2]) {
				var num = parseInt(result[1], 10);
				var mult = this.powers[result[2]] || 1;
				return num * mult;
			} else {
				return value;
			}
		},
		add: function(element, interval, label, fn, times, belay) {
			var counter = 0;
			
			if (jQuery.isFunction(label)) {
				if (!times) 
					times = fn;
				fn = label;
				label = interval;
			}
			
			interval = jQuery.timer.timeParse(interval);

			if (typeof interval != 'number' || isNaN(interval) || interval <= 0)
				return;

			if (times && times.constructor != Number) {
				belay = !!times;
				times = 0;
			}
			
			times = times || 0;
			belay = belay || false;
			
			if (!element.$timers) 
				element.$timers = {};
			
			if (!element.$timers[label])
				element.$timers[label] = {};
			
			fn.$timerID = fn.$timerID || this.guid++;
			
			var handler = function() {
				if (belay && this.inProgress) 
					return;
				this.inProgress = true;
				if ((++counter > times && times !== 0) || fn.call(element, counter) === false)
					jQuery.timer.remove(element, label, fn);
				this.inProgress = false;
			};
			
			handler.$timerID = fn.$timerID;
			
			if (!element.$timers[label][fn.$timerID]) 
				element.$timers[label][fn.$timerID] = window.setInterval(handler,interval);
			
			if ( !this.global[label] )
				this.global[label] = [];
			this.global[label].push( element );
			
		},
		remove: function(element, label, fn) {
			var timers = element.$timers, ret;
			
			if ( timers ) {
				
				if (!label) {
					for ( label in timers )
						this.remove(element, label, fn);
				} else if ( timers[label] ) {
					if ( fn ) {
						if ( fn.$timerID ) {
							window.clearInterval(timers[label][fn.$timerID]);
							delete timers[label][fn.$timerID];
						}
					} else {
						for ( var fn in timers[label] ) {
							window.clearInterval(timers[label][fn]);
							delete timers[label][fn];
						}
					}
					
					for ( ret in timers[label] ) break;
					if ( !ret ) {
						ret = null;
						delete timers[label];
					}
				}
				
				for ( ret in timers ) break;
				if ( !ret ) 
					element.$timers = null;
			}
		}
	}
});

if (jQuery.browser.msie){
	jQuery(window).one("unload", function() {
		var global = jQuery.timer.global;
		for ( var label in global ) {
			var els = global[label], i = els.length;
			while ( --i )
				jQuery.timer.remove(els[i], label);
		}
	});
}
//-------------定时器结束

/**
 * 参数页面选择项
 */
(function($) {
	function load(container, a) {
		var href = a.attr('href');
		if(null == href) return false;
		href = toUrl(href);
		lock('正在加载工作区...');
		if (a.hasClass('iframe')) {
			var i = $('<iframe frameborder="no" width="100%" height="100%" border="0"></iframe>');
			i.bind('load', unlock);
			i.attr('src', href);
			container.html(i);
		} else {
			container.load(href, {}, unlock);
		}
	}

	function unlock() {
		$.unblockUI();
	}

	$.fn.parameterTabs = function(options) {
		return this.each(function() {
			$('a', this).bind('click', function() {
				var a = $(this);
				$('div.parameterTabs  li.tabsSelected').removeClass('tabsSelected');
				a.parent('li').addClass('tabsSelected');
				load($('#ptF102C8B7AA7B3FF0'), a);
				return false;
			});
			var a = $('li.tabsSelected a', this);
			load($('#ptF102C8B7AA7B3FF0'), a);
		});
	}
})(jQuery);

/**
 * 验证码
 */
(function($){
	var vcid = 0;
	$.fn.verifyCode = function(options) {
		return this.each(function(){
			var i = $(this);
			i.css('width', 100);
			i.css('vertical-align', 'middle');
			var src = i.attr('src');
			i.removeAttr('src');
			i.after('<img id="VC_F102C8B7AA7B3FF0_' + vcid + '" src="' + src + '" alt="验证码" style="vertical-align:middle;"/>');
			$('#VC_F102C8B7AA7B3FF0_' + vcid).bind('click', function(){
				var s = $(this).attr('src');
				$(this).attr('src', toUrl(s));
			});
			vcid++;
		});
	}
})(jQuery);
function lock(msg) {
	$.blockUI({
		baseZ : 9999,
		css : {
			border : 'none',
			padding : '15px',
			backgroundColor : '#000',
			'-webkit-border-radius' : '10px',
			'-moz-border-radius' : '10px',
			opacity : .5,
			color : '#fff'
		},
		message : '<h2>' + msg + '</h2>'
	});
}

var defaultDataGridId = '#dgF102C8B7AA7B3FF0';
var defaultTreeGridId = '#tgF102C8B7AA7B3FF0';
//var defaultAjaxTreeGridId = '#atgF102C8B7AA7B3FF0';
function closeDialog(id) {
	$('#' + id).bupdlg('close');
	return false;
}
var initDialogOpen;
var beforeDialogClose = function(){};
//弹出对话框事件
function openDialog(t) {
	lock('正在打开对话框...');
	var h = "";
	if (typeof t === "string") {
		h = t;
	} else {
		h = t.href;
	}
	$('#div-kernel-dialog-loader').load(toUrl(h), {});
	return false;
}

//弹出对话框事件(多选)
function openDialog2(t) {
	var data = getSelectedData();
	
	if(data === false) return false;
	lock('正在打开对话框...');
	var h = "";
	if (typeof t === "string") {
		h = t;
	} else {
		h = t.href;
	}
	$('#div-kernel-dialog-loader').load(toUrl(h), data, function() {
		$('#dlg-buttons a,#dlg-toolbar a').bupabutton({plain : false});
		var dlg = $('div.bupdlg', this).bupdlg({
			modal : true,
			onClose : function() {
				// 必须手动清除弹出的对话框
				dlg.bupdlg('destroy').remove();
			}
		});
		$.unblockUI();
	});
	return false;
}

//弹出对话框事件(单选)
function openDialog3(t) {
	var data = getSelectedData2();
	
	if(data === false) return false;
	lock('正在打开对话框...');
	var h = "";
	if (typeof t === "string") {
		h = t;
	} else {
		h = t.href;
	}
	$('#div-kernel-dialog-loader').load(toUrl(h), data, function() {
		$('#dlg-buttons a,#dlg-toolbar a').bupabutton({plain : false});
		var dlg = $('div.bupdlg', this).bupdlg({
			modal : true,
			onClose : function() {
				// 必须手动清除弹出的对话框
				dlg.bupdlg('destroy').remove();
				beforeDialogClose();
			}
		});
		$.unblockUI();
	});
	return false;
}

function loadToWorkspace(t) {
	changeSelectedMenu(t);
	return loadWorkspace(t);
}

function loadWorkspace(t) {
	lock('正在加载工作区...');
//	$('#mainWorkspace').load(toUrl(t.href), {}, function() {
//		$.unblockUI();
//	});
	$('#mainWorkspace').load(toUrl(t.href), {}, $.unblockUI);
	return false;
}

function changeSelectedMenu(t) {
	$('#menuTree a.menuSelected').removeClass('menuSelected');
	$(t).addClass('menuSelected');
	$('#mainWorkspace').bupply({
		title : '当前位置:' + t.title
	});
}

function loadToIframe(t) {
	changeSelectedMenu(t)
	return loadIframe(t);
}
function loadIframe(t) {
	lock('正在加载工作区...');
	var i = $('<iframe frameborder="0" width="100%" height="100%" border="0"></iframe>');
//	i.load(function() {
//		$.unblockUI();
//	});
	i.load($.unblockUI);
	i.attr('src', toUrl(t.href));
	$('#mainWorkspace').html(i);
	return false;
}
var getSelections = function() {
	alert('没有定义!');
	return null;
};

function getSingleSelect() {
	var rows = getSelections();
	if (!rows || rows.length === 0) {
		$.bupmsg.alert('提示', '请选择记录!', 'info');
		return null;
	}
	if (rows.length > 1) {
		$.bupmsg.alert('提示', '只允许选择一条记录!', 'info');
		return null;
	}
	return rows[0].id;
};

function ajaxSubmit(ts) {
	return doAjaxSubmit(ts);
}

function doAjaxSubmit(ts, data) {
	if(data === false) return false;
	var t = ts.title;
	if (confirm((!t || t === '') ? '是否确定执行此操作？' : '是否确定执行' + t + '操作？')) {
		lock('正在执行' + t + '操作...');
		$.ajax({
			url : toUrl(ts.href),
			type : 'post',
			data : data,
			success : function(rd) {
				callbackMessage(rd, refreshGrid);
			},
			error : function() {
				$.unblockUI();
				$.bupmsg.alert('提示', "网络连接出错，请重试", 'error');
			},complete:function(){
				$.unblockUI();
			}
		});
	}
	return false;
}

function callbackMessage(rd, f, ff) {
	$.unblockUI();
	if (typeof rd === "string")
		rd = eval('(' + rd + ')');
	if (rd.error) {
		$.bupmsg.alert('提示', rd.msg, 'error');
	}else{
		if($.isFunction(f)){
			f(rd);
		}else if($.isFunction(ff)){
			ff(rd);
		}
	}
	return false;
}

function ajaxMultiSubmit(ts) {
	return doAjaxSubmit(ts, getSelectedData());
}

function getSelectedData(){
	var rows = getSelections();
	if (!rows || rows.length === 0) {
		$.bupmsg.alert('提示', '至少选择一条记录!', 'info');
		return false;
	}else{
		var f = $('<form></form>')
		for (i = 0; i < rows.length; i++) {
			$('<input type="hidden" name="ids" value="' + rows[i].id + '"/>').appendTo(f);
		}
		return f.serialize();
	}
}

function getSelectedData2(){
	var rows = getSelections();
	if (!rows || rows.length === 0) {
		$.bupmsg.alert('提示', '至少选择一条记录!', 'info');
		return false;
	}else if(rows.length > 1){
		$.bupmsg.alert('提示', '只能选择一条记录!', 'info');
		return false;
	}else{
		var f = $('<form></form>')
		$('<input type="hidden" name="id" value="' + rows[0].id + '"/>').appendTo(f);
		return f.serialize();
	}
}

var beforeSimpleSave = function(){}

function simpleSave(ts, id) {
	$('#' + id + ' form').bupform('submit', {
		url : toUrl(ts.href),
		onSubmit : function() {
			if ($(this).bupform('validate')) {
				beforeSimpleSave();
				if(confirm("是否确认执行操作？")){
					lock('正在保存数据...');
					return true;
				}
			}
			return false;
		},
		success : function(rd) {
			callbackMessage(rd, function(){
				beforeSimpleSave = function(){}
				$('#' + id).bupdlg('close');
			});
		}
	});
	return false;
}
//窗口提交保存
function submitSave(ts, id) {
	$('#' + id + ' form').bupform('submit', {
		url : toUrl(ts.href),
		onSubmit : function() {
			if ($(this).bupform('validate')) {
				if(confirm("是否确认执行操作？")){
					lock('正在保存数据...');
					beforeDialogSave();
					return true;
				}
			}
			return false;
		},
		success : function(rd) {
			callbackMessage(rd);
		}
	});
	return false;
}
function saveForm(ts, id, t, f) {
	$('#' + id).bupform('submit', {
		url : toUrl(ts.href),
		onSubmit : function() {
			if ($(this).bupform('validate')) {
				var c;
				if(typeof t === 'string'){
					c = t;
				}else if(typeof f === 'string'){
					c = f;
				}else{
					c = "是否确证执行操作？";
				}
				if(confirm(c)){
					lock('正在保存数据...');
					return true;
				}
			}
			return false;
		},
		success : function(rd) {
			callbackMessage(rd, f, t);
		}
	});
	return false;
}

function searchHanlder(ts, id, callback) {
	$('#' + id).bupform('submit', {
		url : toUrl(ts.href),
		onSubmit : function() {
			lock('正在查找...');
			return true;
		},
		success : function(rd) {
			callbackMessage(rd, callback);
		}
	});
	return false;
}

var beforeDialogSave = function(){}
//var kecontent;
function dialogSave(ts, id) {
//	beforeDialogSave();
//	alert($('#content').val());
//	alert(kecontent.html());
//	return false;
	$('#' + id + ' form').bupform('submit', {
		url : toUrl(ts.href),
		onSubmit : function() {
			if ($(this).bupform('validate')) {
				beforeDialogSave();
				lock('正在保存数据...');
				return true;
			}
			return false;
		},
		success : function(rd) {
			callbackMessage(rd, function(){
				refreshGrid();
				beforeDialogSave = function(){}
				$('#' + id).bupdlg('close');
			});
		}
	});
	return false;
}

//-------------------------
//提交预约计划
function saveVisitPlan(t, id){
	var vs = '';
	var rows = $("#dg").bupgrid('getSelections');
	for(i = 0; i < rows.length; i++){
		vs+='<input type="hidden" name="ids" value="' + rows[i].id + '" />';
	}
	$('#hidids').html(vs);
	$('#visitPlanForm').bupform('submit', {
		url : t.href,
		onSubmit : function() {
			if ($(this).bupform('validate')) {
				beforeDialogSave();
				lock('正在保存数据...');
				return true;
			}
			return false;
		},
		success : function(rd) {
			callbackMessage(rd, function(){
				refreshGrid();
				beforeDialogSave = function(){}
				$('#' + id).bupdlg('close');
			});
		}
	});
	return false;
}

var refreshGrid = function() {
	return false;
};

var conditionData = function() {
	return null;
}

var gridReload = function() {
	return false;
}
function toUrl(href) {
	if (href.indexOf('?') === -1) {
		href = href + '?_=' + Math.random();
	} else {
		href = href + '&_=' + Math.random();
	}
	return href;
}

$(function() {
	$(document).ajaxStop($.unblockUI);
//	$(document).bind('contextmenu', function(e) {
//		$('#userMenus').menu('show', {
//			left : e.pageX,
//			top : e.pageY
//		});
//		return false;
//	});

	if ($('#menuTree').length > 0) {
		$('#menuTree ul').buptree();
	}

	$('a.hrefLoadToWorkspace').click(function() {
		return loadToWorkspace(this);
	});

	$('a.hrefLoadToIframe').click(function() {
		return loadToIframe(this);
	});

	var initButton = function() {
		// $('a.clsBindDialogOpen').click(function() {
		// return openDialog(this);
		// });
		//
		// $('a.clsBindAjaxOperate').click(function() {
		// return ajaxSubmit(this);
		// });
	};

	if ($(defaultDataGridId).length > 0) {
		// 有使用datagrid的界面
		$(defaultDataGridId).bupgrid({
			dataType : 'json',
			checkOnSelect : false,
			onLoadSuccess : function(rd) {
//				if (typeof rd === "string")
//					rd = eval('(' + rd + ')');
//				if (rd["error"]) {
//					$.bupmsg.alert('提示', rd.msg, 'error');
//					return;
//				}
//				alert(rd.rows[0].id);
				//重新处理a标签事件处理问题
				$('.datagrid-view a').click(function(e){
					return false;
				});
				initButton();
			},
			onLoadError : function(a,b,c) {
				//alert(a["error"]);
				$.bupmsg.alert('提示', "网络连接出错，请重试", 'error');
			}
		});
		refreshGrid = function() {
			$(defaultDataGridId).bupgrid('reload');
		};
		getSelections = function() {
			return $(defaultDataGridId).bupgrid('getSelections');
		};
		gridReload = function() {
			var dt = conditionData();
			if (null != dt) {
				$(defaultDataGridId).bupgrid('reload', dt);
			} else {
				$(defaultDataGridId).bupgrid('reload');
			}
			return false;
		}
	} else if ($(defaultTreeGridId).length > 0) {
		// 使用了treegrid的界面
		$(defaultTreeGridId).buptreegrid({
			onLoadSuccess : function(data, param) {
				$('.treegrid-view a').click(function(e){
					return false;
				});
				initButton();
			},
			onLoadError : function(data) {
				$.bupmsg.alert('提示', "网络连接出错，请重试", 'error');
			}
		});
		refreshGrid = function() {
			$(defaultTreeGridId).buptreegrid('reload');
		};
		getSelections = function() {
			return $(defaultTreeGridId).buptreegrid('getSelections');
		};
//	} else if ($(defaultAjaxTreeGridId).length > 0) {
//		// 使用了treegrid的界面
//		$(defaultAjaxTreeGridId).buptreegrid({
//			collapsible:true,
//			onBeforeExpand:function(row){
//				var url = $('#treegriddataurl').val() + '?rid=' + row['id'];
//				 $(defaultAjaxTreeGridId).buptreegrid("options").url = url;  
//				return true;
//			},
//			onLoadSuccess : function(data, param) {
//				$('.treegrid-view a').click(function(e){
//					return false;
//				});
//				initButton();
//			},
//			onLoadError : function(data) {
//				$.bupmsg.alert('提示', "网络连接出错，请重试", 'error');
//			}
//		});
//		refreshGrid = function() {
//			$(defaultAjaxTreeGridId).buptreegrid('reload');
//		};
//		getSelections = function() {
//			return $(defaultAjaxTreeGridId).buptreegrid('getSelections');
//		};
	}

	// 短信息的处理
//	$('#myMessageCount').everyTime('50s', function() {
//		$.get(CONTEXT_PATH + '/u/message/my/refresh.b', function(rdata) {
//			if(typeof rdata == 'string'){
//				rdata = $.parseJSON(rdata);
//			}
//			if (rdata.error) {
//				$.messager.show({
//					msg : rdata.msg,
//					title : '读取异常',
//					timeout : 10
//				});
//			} else {
//				$('#myMessageCount span #msgCount').html(rdata.data);
//			}
//		});
//	});

//	$('a#topCollapsed').click(function() {
//		$('body.easyui-layout').layout('collapse', 'north');
//		return false;
//	});

	if ($('#adminPortal').length > 0) {
		$('#adminPortal').portal({
			border : false,
			fit : true
		});
	}

	$('div.parameterTabs').parameterTabs();
	$('input.verifycode').verifyCode();
});

function addTab(tid, title, href){
	if($(tid).buptab('exists', title) != true){
		$(tid).buptab('add', {
			href:href,
			title:title,
			closable:true
		});
	}else{
		$(tid).buptab('select', title)
	}
	return false;
}

function cssToggle(t, cls){
	$(t).toggleClass(cls);
}

