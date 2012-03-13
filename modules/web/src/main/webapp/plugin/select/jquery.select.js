/**
 * Name: 基于jquery的下拉列表组件. 
 * Author: Ahulee;
 * charset: utf-8;
 * 
 */

(function($) {
	var i = 0;
	
	//初始化下拉列表
	$.fn.selectDiv = function(width,name,readonly,must){
		
		var inputName = this.attr("name");
		this.attr("class","select");
		var width = parseFloat(width)+20;
		
		//如果没有写明的选中框，则把第一个设成默认选中框
		if (this.find('li[selected="selected"]').length == 0){
			this.find('li:first').attr('selected','selected');
		}
		
		//判断当前列表框中是否存在类为selectDIV的DIV，如果没有则新增一个
		if(this.find(".selectDIV").length == 0){
			var strHtml  = '<div class="selectDIV">';
			//判断是否只读
			if(readonly=="false"){
				strHtml += '<input value="" class="selectDIV_input"/><input type="hidden"  name="'+name+'" value="" class="selectDIV_input_hidden"/>';
			}else{
				strHtml += '<input value="" class="selectDIV_input" readonly="readonly"/><input type="hidden"  name="'+name+'" value="" class="selectDIV_input_hidden"/>';
			}
  			strHtml += '</div>';
			var strHtmlT = '<div class="select_bottom"></div>';
			this.find("ul").attr("class","selectIist").wrap('<div class="selectTreeDIV"></div>');
			this.find(".selectTreeDIV").before(strHtml).append(strHtmlT);
		};
		
		//判断是否非空
		if(must == "true"){
			this.find(".selectDIV_input").addClass("selectDIV_input_NO");
		}
		var blockNum = $('div[id^="select_block_"]').length + 1;
		var blockId = 'select_block_'+blockNum;
		$("body").append('<div id='+blockId+'></div>');
		this.data('blockId', blockId);
		$("#"+blockId).append($(this).find(".selectTreeDIV").clone());
		$("#"+blockId).bindMyEvent($(this));
		
		//设定选项列表大小
		this.css("width",width+"px").find(".selectTreeDIV").css("width",(width-2)+"px");
		this.find(".selectDIV_input").css("width",(width-20)+"px");
		
		if($.browser.msie){
			//兼容IE浏览器，调整下拉列表的位置;
			var ua = navigator.userAgent;
			if (/msie 7/i.test(ua)) {}
			if(/msie 6/i.test(ua)){
				this.find(".selectTreeDIV").css("top","26px");
				this.find(".selectTreeDIV").css("width",(width+2)+"px");	
			};
		};
		
		//获取默认值
		var defaultName  = jQuery.trim(this.find(".selectTreeDIV li[selected]").text());
		var defaultValue = jQuery.trim(this.find(".selectTreeDIV li[selected]").attr("val"));
		
		//调用设值方法并为默认值添加样式
		this.find(".selectTreeDIV li[selected]").addClass("selectIistActive");
		this.setThisValue(defaultName,defaultValue);
		this.bindEvent();
		
		//绑定拖拽调整大小事件
		var defaultWidth  = this.find(".selectTreeDIV").width()+this.find(".selectTreeDIV").offset().left;
		var defaultHeight = this.find(".selectTreeDIV").height()+this.find(".selectTreeDIV").offset().top;
		this.find(".select_bottom").mousedown(function(){
			$(this).thisMouseMove(defaultWidth,defaultHeight);
		});
		
		//新建一个js对象
		if (this.length==1&&$(this).data('tjSelect') == undefined) {
			var tjSelect=new TjSelect(this);
			$(this).data('tjSelect',tjSelect);
			$(this).bind("tjChange",function(){
				
				$(this).data('tjSelect').change();
			});
		}
		
		if (this.attr('onchange' != undefined)){
			var fun = this.attr('onchange');
			$(this).tjChange(fun);
		}
	};
	
	//为克隆的下拉列表项目绑定事件
	$.fn.bindMyEvent =function(obj){
		var o = $(obj);
		
		//鼠标划过列表项目的事件
		this.find(".selectIist li").hover(function(){$(this).addClass("selectIistHover");},function(){$(this).removeClass("selectIistHover");});	
		
		//绑定选中列表项目时的事件
		this.find(".selectIist li").click(function(){
			var name = jQuery.trim($(this).text());
			var value = jQuery.trim($(this).attr("val"));	
			closeSelect();
			$(this).parent().find("li").removeAttr("selected").removeClass("selectIistActive").removeClass("selectIistHover");
			$(this).attr("selected","selected").addClass("selectIistActive");
			$(o).find(".selectTreeDIV ul").empty().append($("#select_block").find("ul").html());
			
			//添加change事件
			if (value != $(o).find(".selectDIV_input_hidden").val()){
				//给选框赋值（必须在触发change事件之前）
				$(o).setThisValue(name,value);
				$(o).find(".selectDIV_input").focus();
				$(o).trigger('tjChange');
			}
		});
	}
	
	//为对象绑定事
	$.fn.bindEvent = function(){
		
		//绑定鼠标划过输入框样式
		this.hover(function(){},function(){
			$(document).unbind("click").bind("click",function(){closeSelect();});
		});
		
		//绑定输入框的单击样式
		this.find(".selectDIV").click(function(){
			var blockId = $(this).parents('.select').data('blockId');
			
			var left = $(this).parent().offset().left+"px";
			var top = (parseFloat($(this).parent().offset().top)+27)+"px";
			$("#"+blockId).find(".selectTreeDIV").css("position","absolute");
			$("#"+blockId).find(".selectTreeDIV").css("left",left);
			$("#"+blockId).find(".selectTreeDIV").css("top",top);
			$("#"+blockId).find(".selectTreeDIV").css("z-index","1200");
			$("#"+blockId).find(".selectTreeDIV").css("width","200px");
			$("#"+blockId).find(".selectTreeDIV").slideDown("fast");
			
			
			if ($(this).parent().find('.selectTreeDIV:visible').length>0){
				closeSelect();
			}else {
				$(this).parent().addClass("selecthover");
				$(this).addClass("selectDIV_hover");
			}
		});
		
		//获取焦点变换风格
		this.find(".selectDIV_input").focus(function(){
			$(this).parent().parent().addClass("selecthover");
			$(this).parent().addClass("selectDIV_hover");
			$(this).addClass("selectDIV_input_input");
		});
		
		//绑定方向键
		this.keydown(function(event){
			if(event.keyCode==38){
				var name = jQuery.trim($(this).find(".selectIistActive").prev().text());
				if(name.length != 0){
					var value = jQuery.trim($(this).find(".selectIistActive").prev().attr("val"));
					$(this).find(".selectIistActive").removeClass("selectIistActive").prev().addClass("selectIistActive");
					$(this).setThisValue(name,value);
					$(this).find(".selectDIV_input").focus();
				};
			}
			if(event.keyCode==40){
				var name = jQuery.trim($(this).find(".selectIistActive").next().text());
				if(name.length != 0){
					var value = jQuery.trim($(this).find(".selectIistActive").next().attr("val"));
					$(this).find(".selectIistActive").removeClass("selectIistActive").next().addClass("selectIistActive");
					$(this).setThisValue(name,value);
					$(this).find(".selectDIV_input").focus();
				};
			}
			if(event.keyCode==13||event.keyCode==9){closeSelect();}
		});
	};
	
	//关闭下拉列表并清理样式
	closeSelect = function(){
		//$(document).unbind("click");
		$(".selectTreeDIV").hide();
		$(".select").removeClass("selecthover").find(".selectDIV").removeClass("selectDIV_hover");
	};

	//设值函数
	$.fn.setThisValue = function(name,value){
		this.find(".selectDIV_input").val(name);
		this.find(".selectDIV_input_hidden").val(value);
	};
	//拖拽改变选项列表大小
	$.fn.thisMouseMove = function(defaultWidth,defaultHeight){
		var $obj = $(this).parents(".select");
		$(document).mousemove(function(e){
			$(document).unbind("click");
			var $width = e.pageX - $obj.offset().left;
			var $height = e.pageY - $obj.offset().top;
			$obj.find('.selectTreeDIV').css("width",$width + "px").find("ul").css("width",$width + "px");
			$obj.find('.selectTreeDIV').css("height",$height + "px").find("ul").css("height",$height + "px");
		});
		$(document).mouseup(function(){
			$(document).unbind("mousemove");
		});
		
	};
	
/*--------------------外部使用的方法-------------------------	*/

	//监听值改变的事件
	$.fn.tjChange = function(fun){
		if (this.length==1&&$(this).data('tjSelect') != undefined) {
			$(this).data('tjSelect').change = fun;
		}
	};
	
	//获得值，或赋值
	$.fn.tjVal = function(value){
		var $this = this;
		if (this.length==1&&$(this).data('tjSelect') != undefined) {
			if (value == undefined){
				return $(this).find('.selectDIV_input_hidden').val();
			}else {
				$(this).find('.selectTreeDIV li').each(function(){
					if ($(this).attr('val') == value){
						$($this).find(".selectDIV_input").val(jQuery.trim($(this).text()));
						$($this).find(".selectDIV_input_hidden").val(jQuery.trim($(this).attr("val")));
					}
				});
			}
		}
	};
	
	//通过显示值赋值
	$.fn.tjText = function(text){
		var $this = this;
		if (this.length==1&&$(this).data('tjSelect') != undefined) {
			if (text == undefined){
				return $(this).find('.selectDIV_input').val();
			}else {
				$(this).find('.selectTreeDIV li').each(function(){
					if ($(this).text() == text){
						$($this).find(".selectDIV_input").val(jQuery.trim($(this).text()));
						$($this).find(".selectDIV_input_hidden").val(jQuery.trim($(this).attr("val")));
					}
				});
			}
		}
	}
	
	//清空
	$.fn.tjEmpty = function(){
		if (this.length==1&&$(this).data('tjSelect') != undefined) {
			$(this).find(".selectDIV_input").val('');
			$(this).find(".selectDIV_input_hidden").val('');
			$(this).find('ul').html('');
		}
	};
	
	//重新组装成select
	$.fn.tjAssemble = function(){
		$(this).selectDiv("150",name,"true","true");	
	};
	
	//TjSelect构造方法
	function TjSelect(target){
		this.target = target;
		this.change = function() {};
	}
	
})(jQuery);
