(function($) {	
	$(document).ready(function(){
		$.init_style();
	});
	
	$.fn.init_style = function(){
		return this.each(function(){
        	 var $this=$(this);
			 init_style_impl($this);
		});
    };
	
	$.init_style = function(){
			var $this = $(document.body);
			init_style_impl($this);
    };
    
	function init_style_impl($this){
		
		$this.find("tr:odd").addClass("odd");
		$this.find("tr:even").addClass("even");
		
		//按钮
		$this.find('*[ztype="button"]').not($('[zinit]')).each(function(){
			$(this).button();	
			elementAlreadyInit($(this));
		});
		
		//弹出框
		$this.find('*[ztype="popUp"]').not($('[zinit]')).each(function(index){
			var $a_herf=$(this);
			$(this).click(function(){
				if ($('#dialogDiv').length == 0){
					$('body').append('<div style="display:none;" id="dialogDiv"></div>');
				}else{
					$('#dialogDiv').empty();
				}
				var url = $a_herf.attr('url');
				if ($a_herf.attr('title') != undefined)$('#dialogDiv').attr('title', $a_herf.attr('title'));
				var width = $a_herf.attr('zwidth') == undefined? 500:$a_herf.attr('zwidth');
				var height = $a_herf.attr('zheight') == undefined? 'auto':$a_herf.attr('zheight');
				var isIframe = $a_herf.attr('zmode') == 'iframe'? true:false;
				if (isIframe){
					$('#dialogDiv').append('<iframe id="iframe1" name="iframe1" frameborder="no" scrolling="no" border="0" style="border-width:0;" height="'+height+'" width="'+(width-20)+'" src="'+url+'"></iframe>');
				}else {
					if ($a_herf.data('postData') != undefined){
						var postData = $a_herf.data('postData');
						$('#dialogDiv').load(url, postData);
					}else {
						$('#dialogDiv').load(url);
					}
				}
				$('#dialogDiv').dialog({
					resizable: false,
					autoOpen: false,
					height: height,
					width: width,
					modal: true,
					close: function(event, ui) {
						$('#dialogDiv').dialog('destroy');
						$('#dialogDiv').dialog('close'); 
						//关闭验证提示框
						$('#dialogDiv').removeTip();
						//取消高亮显示
						
						$('*[id^=gbox_]').find('tr[aria-selected="true"]').each(function(){
							$(this).trigger('click');
						});
						//关闭所有显示的下拉选
						$('*[id*="block"]').hide();
					}
				});	
				$('#dialogDiv').dialog('open'); 
			});
			elementAlreadyInit($(this));
		});
		
		//下拉选
		$this.find('div[ztype="select"]').not($('[zinit]')).each(function(){
			var width = $(this).css('width');
			if (width.indexOf('%')!=-1){
				width = parseInt($(this).parent().innerWidth()) * parseInt(width) / 100;
			}else if (width == 'auto' || width == '0px'){
				width = 136;
			}
			if ($(this).attr('verify') == 'NotNull'){
				$(this).tjSelect(width,"true","true");	
			}else {
				$(this).tjSelect(width,"true","false");	
			}
			elementAlreadyInit($(this));
		});
		
		//多选
		$this.find('div[ztype="selectbox"]').not($('[zinit]')).each(function(){
			var name = $(this).attr('name');
			$(this).addClass('selectBox');
			$(this).find('> ul').addClass('selectBoxList');
			if ($(this).find('> ul:first').css('height') == 'auto'){
				$(this).find('> ul').css('height','150px');
			}
			$(this).selectBoxDiv();
			elementAlreadyInit($(this));
		});
		
		//文本框
		$this.find('input[ztype="text"]').not($('[zinit]')).each(function(){
			initNewTextCss($(this));
			elementAlreadyInit($(this));
			
		});
		
		//文本域
		$this.find('textarea[ztype="textarea"]').not($('[zinit]')).each(function(){
			if($(this).css('width') == 'auto' ){
				var cols = parseInt($(this).attr('cols'));
				$(this).css('width',18 + cols * 6 + 'px');
			}
			var textareaWidth = parseFloat($(this).css('width'));
			$(this).attr('class','textareaNo');
			$(this).wrap('<div class="textareaIn"></div>');
			var $thisDiv = $(this).parent();
			if ($($thisDiv).siblings('label').length > 0){
				$($thisDiv).siblings('label').andSelf().wrapAll('<div class="textareaOut"></div>');
			}else{
				$($thisDiv).wrapAll('<div class="textareaOut"></div>');
			}
			$(this).parents('.textareaOut').css('width',(textareaWidth+6)+'px');
			$(this).parents('.textareaIn').css('width',(textareaWidth+4)+'px');
			if ($(this).parent().siblings('label').length == 0){
				$(this).parents('.textareaOut').css('margin-left','0px');
			}
			$(this).click(function(){
				$(this).parents(".textareaOut").removeClass('textareaOutHover');
				$(this).parents(".textareaIn").removeClass('textareaInHover');
				$(this).parent().addClass('textareaInHover');
				$(this).parent().parent().addClass('textareaOutHover');
			}).blur(function(){
				$(this).parent().removeClass('textareaInHover');
				$(this).parent().parent().removeClass('textareaOutHover');
			});
			elementAlreadyInit($(this));
		});
		
		$this.find('input[ztype="time"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var datePara = {};
			datePara.dateFmt = 'HH:mm:ss';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%H:%m:%s';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%H:%m:%s';
			}
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});
		/*
		 * zhaozx add 添加时间段。
		 */		
		$this.find('input[ztype="timeHMC"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var datePara = {};
			datePara.dateFmt = 'HH小时mm分钟';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%H:%m';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%H:%m';
			}
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});		
		$this.find('input[ztype="timeHM"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var datePara = {};
			datePara.dateFmt = 'HH:mm';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%H:%m';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%H:%m';
			}
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});
		/*
		 * gx add 去除检修时长的“今天”按钮。
		 */
		$this.find('input[ztype="timeHL"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var datePara = {};
			datePara.dateFmt = 'HH:mm';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%H:%m';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%H:%m';
			}
			datePara.isShowToday=false;
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});
		/*
		 * end
		 */
		$this.find('input[ztype="date"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var datePara = {};
			datePara.dateFmt = 'yyyy-MM-dd';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%y-%M-%d';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%y-%M-%d';
			}
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});
		$this.find('input[ztype="datetime"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var datePara = {};
			datePara.dateFmt = 'yyyy-MM-dd HH:mm:ss';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%y-%M-%d %H:%m:%s';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%y-%M-%d %H:%m:%s';
			}
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});
		$this.find('input[ztype="dateYm"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var datePara = {};
			datePara.dateFmt = 'yyyy年MM月';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%y年%M月';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%y年%M月';
			}
			$(this).addClass('Wdate');
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});
		/*
		 * zhaozx add 添加年份。
		 */		
		$this.find('input[ztype="dateY"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var datePara = {};
			datePara.dateFmt = 'yyyy';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%y';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%y';
			}
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});			
		$this.find('input[ztype="startdate"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var theId = $(this).parents('.inputUl').find('input[ztype="enddate"]').attr('id');
			if (typeof(theId) == 'undefined'){
				theId = $(this).parents('tr').find('input[ztype="enddate"]').attr('id');
			}
			var datePara = {};
			datePara.dateFmt = 'yyyy-MM-dd';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%y-%M-%d';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%y-%M-%d';
			}
			if (theId != undefined){
				datePara.maxDate='#F{$dp.$D(\''+theId+'\')}';
			}
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});
		$this.find('input[ztype="enddate"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var theId = $(this).parents('.inputUl').find('input[ztype="startdate"]').attr('id');
			if (typeof(theId) == 'undefined'){
				theId = $(this).parents('tr').find('input[ztype="startdate"]').attr('id');
			}
			var datePara = {};
			datePara.dateFmt = 'yyyy-MM-dd';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%y-%M-%d';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%y-%M-%d';
			}
			if (theId != undefined){
				datePara.minDate='#F{$dp.$D(\''+theId+'\')}';
			}
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});
		$this.find('input[ztype="startdatetime"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var theId = $(this).parents('.inputUl').find('input[ztype="enddatetime"]').attr('id');
			if (typeof(theId) == 'undefined'){
				theId = $(this).parents('table').find('input[ztype="enddatetime"]').attr('id');
			}
			var datePara = {};
			datePara.dateFmt = 'yyyy-MM-dd HH:mm:ss';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%y-%M-%d %H:%m:%s';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%y-%M-%d %H:%m:%s';
			}
			if (theId != undefined){
				datePara.maxDate='#F{$dp.$D(\''+theId+'\')}';
			}
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});
		$this.find('input[ztype="enddatetime"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var theId = $(this).parents('.inputUl').find('input[ztype="startdatetime"]').attr('id');
			if (typeof(theId) == 'undefined'){
				theId = $(this).parents('table').find('input[ztype="startdatetime"]').attr('id');
			}
			var datePara = {};
			datePara.dateFmt = 'yyyy-MM-dd HH:mm:ss';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%y-%M-%d %H:%m:%s';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%y-%M-%d %H:%m:%s';
			}
			if (theId != undefined){
				datePara.minDate='#F{$dp.$D(\''+theId+'\')}'
			}
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});
		$this.find('input[ztype="startdatetimeHM"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var theId = $(this).parents('.inputUl').find('input[ztype="enddatetimeHM"]').attr('id');
			if (typeof(theId) == 'undefined'){
				theId = $(this).parents('table').find('input[ztype="enddatetimeHM"]').attr('id');
			}
			var datePara = {};
			datePara.dateFmt = 'yyyy-MM-dd HH:mm:00';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%y-%M-%d %H:%m:%s';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%y-%M-%d %H:%m:%s';
			}
			if (theId != undefined){
				datePara.maxDate='#F{$dp.$D(\''+theId+'\')}';
			}
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});
		$this.find('input[ztype="enddatetimeHM"]').not($('[zinitDate]')).each(function(){
			initNewTextCss($(this));
			var theId = $(this).parents('.inputUl').find('input[ztype="startdatetimeHM"]').attr('id');
			if (typeof(theId) == 'undefined'){
				theId = $(this).parents('table').find('input[ztype="startdatetimeHM"]').attr('id');
			}
			var datePara = {};
			datePara.dateFmt = 'yyyy-MM-dd HH:mm:00';
			if ($(this).attr('znow') == 'startnow'){
				datePara.minDate = '%y-%M-%d %H:%m:%s';
			}
			if ($(this).attr('znow') == 'endnow'){
				datePara.maxDate = '%y-%M-%d %H:%m:%s';
			}
			if (theId != undefined){
				datePara.minDate='#F{$dp.$D(\''+theId+'\')}'
			}
			$(this).focus(function(){WdatePicker(datePara);});
			elementAlreadyInitForDate($(this));
		});
		
		$this.find('form[ztype="form"]').not($('[zinit]')).each(function(index){
			$(this).form();
			var theForm = $(this);
			$(this).ajaxForm({
				type: "post",
				dataType: 'json',
				beforeSubmit: function(data){
					if(typeof(validateForm) == 'undefined'){
						return defaultValidateForm(data,theForm);
					}else {
						return validateForm(data,theForm);
					}
				},
				success: function(data){
					if(typeof(onSuccess) == 'undefined'){
						onSuccessDefault(data,theForm);
					}else {
						onSuccess(data);
					}
				}
			});
			elementAlreadyInit($(this));
		});
		
		//默认值 人员\单选的自动完成，如果设定为多选，采用默认的回调函数会排除重复值 
		$this.find('input[ztype="auto"]').not($('[zinit]')).each(function(index){
			initNewTextCss($(this));
			var url = $(this).attr('zurl')==undefined?ctp+'/baseinfo/person!autocompleteAjax.action':$(this).attr('zurl');
			var isMulti = $(this).attr('zmode')=='multi'?true:false;
			function autoCallback(event, data){//自动回调
				//去除重复值
				var value = $(event.target).val();
				var array = value.split(',');//数组
				var data={};   
				var desiredArr=new Array();   
				for(var i=0;i<array.length;i++){
					data[array[i]]=array[i];   
				}   
				for(var pro in data){   
				  desiredArr.push(data[pro]);
				}   
				$(event.target).val(desiredArr.join(","));
			}
			var func = $(this).attr('zcallback')==undefined?autoCallback:new Function($(this).attr('zcallback')+"();");
			var $this = $(this);
			$(this).autocomplete(url, {
				multiple: isMulti,
		        mustMatch: true,
		        matchContains: true,
		        scrollHeight: 220,
		        delay:500,
		        extraParams:{autoData:function(){
		        	var funName = $this.attr('zfun');
		        	if (funName != undefined){
		        		var a = eval(funName+'()');
		        		return a;
		        	}
				}},
		        autoFill: true,
		        multipleSeparator: ',',
		        cacheLength:0,
				parse: function(data) {
					return $.map(eval(data), function(item) {
						return {
							data: item,
							value: item.name,
							result: item.name
						};
					});
				},
				formatItem: function(item) {
					return item.name;
				},
		        formatResult: function(item){
		        	return item.name;
		        }
			}).result(func);
			elementAlreadyInit($(this));
		});
	}
	
	function initNewTextCss($this){
		var width = $($this).css('width');
		if (width.indexOf('%')!=-1){
			width = parseInt(parseInt($($this).parent().innerWidth()) * parseInt(width) / 100);
		}else if (width == 'auto'){
			$($this).css('width','150px');
			width = parseFloat($(this).css('width'));
		}else {
			width = parseInt(width);
		}
		$($this).removeClass();
		$($this).attr('class','input');
		$($this).click(function(){
			$(this).addClass('inputHover');
		}).blur(function(){
			$(this).removeClass('inputHover');
		});
	}
	
	function elementAlreadyInit($this){
		$this.attr('zinit',true);
	}
	
	function elementAlreadyInitForDate($this){
		$this.attr('zinitDate',true);
	}
})(jQuery);

function defaultValidateForm(arr, thisForm){
	if (thisForm.data('tjForm').checkSubmit()) {
		return true;
	}else{
		return false;					
	}
}

function onSuccessDefault(data,theForm){
	if (data['result'] == true){
		tjAlert("操作成功！");
	}else {
		tjAlert("操作失败！");
	}
}

function tjAlert(str){
	if($('#tjAlert').length==0){
		$('body').append('<div id="tjAlert" style="text-align:center;"><span>'+str+'</span></div>');
	}else {
		$('#tjAlert').remove();
		$('body').append('<div id="tjAlert" style="text-align:center;"><span>'+str+'</span></div>');
	}
	var divWidth = $('#tjAlert span').width();
	if (divWidth < 250){
		divWidth = 250;
	}
	$('#tjAlert').dialog({
		resizable: false,
		autoOpen: true,
		height: 'auto',
		width: divWidth,
		modal: true,
		buttons:{
			关闭: function() {
				$(this).dialog('close');
			}
		}
	});
}

function tjConfirm(str, callback){
	if($('#tjConfirm').length==0){
		$('body').append('<div id="tjConfirm" style="text-align:center;"><span>'+str+'</span></div>');
	}else {
		$('#tjConfirm').remove();
		$('body').append('<div id="tjConfirm" style="text-align:center;"><span>'+str+'</span></div>');
	}
	var divWidth = $('#tjConfirm span').width();
	if (divWidth < 250){
		divWidth = 250;
	}
	$('#tjConfirm').dialog({
		resizable: false,
		autoOpen: false,
		height: 'auto',
		width: divWidth,
		modal: true,
		buttons:{
			确定: function(){
				$(this).dialog('close');
				confirmCallback(true);
			},
			取消: function(){
				$(this).dialog('close');
				confirmCallback(false);
			}
		}
	});
	$('#tjConfirm').dialog('open');
	
	var confirmCallback = callback;
}
//其他扩展方法
//encode && decode js to json//非原创，网上找的
(function($) {
    $.type = function(o) {
        var _toS = Object.prototype.toString;
        var _types = {
            'undefined': 'undefined',
            'number': 'number',
            'boolean': 'boolean',
            'string': 'string',
            '[object Function]': 'function',
            '[object RegExp]': 'regexp',
            '[object Array]': 'array',
            '[object Date]': 'date',
            '[object Error]': 'error'
        };
        return _types[typeof o] || _types[_toS.call(o)] || (o ? 'object' : 'null');
    };
    var $specialChars = { '\b': '\\b', '\t': '\\t', '\n': '\\n', '\f': '\\f', '\r': '\\r', '"': '\\"', '\\': '\\\\' };
    var $replaceChars = function(chr) {
        return $specialChars[chr] || '\\u00' + Math.floor(chr.charCodeAt() / 16).toString(16) + (chr.charCodeAt() % 16).toString(16);
    };
    $.toJSON = function(o) {
        var s = [];
        switch ($.type(o)) {
            case 'undefined':
                return 'undefined';
                break;
            case 'null':
                return 'null';
                break;
            case 'number':
            case 'boolean':
            case 'date':
            case 'function':
                return o.toString();
                break;
            case 'string':
                return '"' + o.replace(/[\x00-\x1f\\"]/g, $replaceChars) + '"';
                break;
            case 'array':
                for (var i = 0, l = o.length; i < l; i++) {
                    s.push($.toJSON(o[i]));
                }
                return '[' + s.join(',') + ']';
                break;
            case 'error':
            case 'object':
                for (var p in o) {
                    s.push(p + ':' + $.toJSON(o[p]));
                }
                return '{' + s.join(',') + '}';
                break;
            default:
                return '';
                break;
        }
    };
    $.evalJSON = function(s) {
        if ($.type(s) != 'string' || !s.length) return null;
        return eval('(' + s + ')');
    };
})(jQuery);

