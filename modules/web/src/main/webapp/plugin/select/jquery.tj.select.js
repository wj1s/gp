/**
-- * Name: 基于jquery的下拉列表组件. 
 * Author: Ahulee;
 * charset: utf-8;
 * 
 */

(function($) {
	$.fn.tjSelect = function(width,readonly,must){
		var tjSelect=new TjSelect(this,true,width);
		$(this).data("tjSelect",tjSelect);
	};
	
	function TjSelect(target,readOnly,width){
		this.target=target;
		this.name=this.target.attr('name')==undefined?this.target.attr('id'):this.target.attr('name');//名字
		this.id=this.target.attr('id')==undefined?this.target.attr('name'):this.target.attr('id');//ID
		this.blockId=this.id+'_block';
		this.readOnly=readOnly;
		this.width=width;
		
		//用于处理change事件
		this.change = function() {};
		
		//初始化input和block
		this.initInput();
		this.initBlock();
	}
	
	$.extend(TjSelect.prototype,{
		//初始化input
		initInput:function(){
			$(this.target).attr("class","select");
			if($(this.target).find(".selectDIV").length == 0){
				var name = this.name;
				var strHtml  = '<div class="selectDIV">';
				//判断是否只读
				if(this.readonly==false){
					strHtml += '<input value="" class="selectDIV_input" id="'+name+'_input'+'"/><input type="hidden"  name="'+name+'" value="" class="selectDIV_input_hidden"/>';
				}else{
					strHtml += '<input value="" class="selectDIV_input" readonly="readonly" id="'+name+'_input'+'"/><input type="hidden"  name="'+name+'" value="" class="selectDIV_input_hidden"/>';
				}
	  			strHtml += '</div>';
				var strHtmlT = '<div class="select_bottom"></div>';
				$(this.target).find("ul").attr("class","selectIist").wrap('<div class="selectTreeDIV"></div>');
				$(this.target).find(".selectTreeDIV").before(strHtml).append(strHtmlT);
			}
			this.bindInputEvent();
		},
		//初始化block
		initBlock:function(){
			var width = parseFloat(this.width)+20;
			//如果没有写明的选中框，则把第一个设成默认选中框
			if ($(this.target).find('li[selected="selected"]').length == 0){
				$(this.target).find('li:first').attr('selected','selected');
			}
			//设定选项列表大小
			$(this.target).css("width",width+"px").find(".selectTreeDIV").css("width",(width-2)+"px");
			$(this.target).find(".selectDIV_input").css("width",(width-20)+"px");
			//获取默认值
			var defaultName  = jQuery.trim($(this.target).find(".selectTreeDIV li[selected]").text());
			var defaultValue = jQuery.trim($(this.target).find(".selectTreeDIV li[selected]").attr("val"));
			
			//调用设值方法并为默认值添加样式
			$(this.target).find(".selectTreeDIV li[selected]").addClass("selectIistActive");
			//设定默认显示的值
			this.setThisValue(defaultName,defaultValue);
			
			$("body").append('<div id="'+this.id+'_block"><div class="selectTreeDIV" style="width:'+(width-2)+'px;">'+$(this.target).find(".selectTreeDIV").html()+'</div></div>');
			
			//由于里边的.selectTreeDIV在样式表中为hidden(为了隐藏初始的ul&li，必须hidden),所以把html复制过来之后得先show才行。
			$('*[id="'+this.blockId+'"]').hide().find(".selectTreeDIV").show();
			this.bindBlockEvent();
		},
		//给input赋值
		setThisValue:function(text,value){
			$(this.target).find(".selectDIV_input").val(text);
			$(this.target).find(".selectDIV_input_hidden").val(value);
		},
		bindInputEvent:function(){
			var $this = this;
			//绑定鼠标划过输入框样式
			$(this.target).hover(
				function(){},
				function(){
					if($('*[id="'+$this.blockId+'"]').find(".selectTreeDIV:visible").length == 1){
						$(document).click(function(){
							$this.closeSelect();
						});
					}
				}
			);
			
			//绑定输入框的单击样式
			$(this.target).find(".selectDIV").click(function(){
				$this.refreshOperOrCloseState();
				var left = $(this).find('.selectDIV_input').offset().left -4 +"px";
				var top = (parseFloat($(this).find('.selectDIV_input').offset().top)-3)+"px";
				$('*[id="'+$this.blockId+'"]').css("position","absolute");
				$('*[id="'+$this.blockId+'"]').css("left",left);
				$('*[id="'+$this.blockId+'"]').css("top",top);
				$('*[id="'+$this.blockId+'"]').css("z-index","1200");
			});
			
//			//获取焦点变换风格
//			$(this.target).find(".selectDIV_input").focus(function(){
//				if ($("#"+$this.blockId).find('.selectTreeDIV:visible').length==0){
//					$(this).parent().parent().addClass("selecthover");
//					$(this).parent().addClass("selectDIV_hover");
//					$(this).addClass("selectDIV_input_input");
//				}
//			});
			
			//绑定方向键
			$(this.target).keydown(function(event){
				if(event.keyCode==38){
					var name = jQuery.trim($('*[id="'+$this.blockId+'"]').find(".selectIistActive").prev().text());
					if(name.length != 0){
						var value = jQuery.trim($('*[id="'+$this.blockId+'"]').find(".selectIistActive").prev().attr("val"));
						$('*[id="'+$this.blockId+'"]').find(".selectIistActive").removeClass("selectIistActive").prev().addClass("selectIistActive");
						$this.setThisValue(name,value);
						$(this.target).find(".selectDIV_input").focus();
					};
				}
				if(event.keyCode==40){
					var name = jQuery.trim($('*[id="'+$this.blockId+'"]').find(".selectIistActive").next().text());
					if(name.length != 0){
						var value = jQuery.trim($('*[id="'+$this.blockId+'"]').find(".selectIistActive").next().attr("val"));
						$('*[id="'+$this.blockId+'"]').find(".selectIistActive").removeClass("selectIistActive").next().addClass("selectIistActive");
						$this.setThisValue(name,value);
						$(this.target).find(".selectDIV_input").focus();
					};
				}
				if(event.keyCode==13||event.keyCode==9){$this.closeSelect();}
			});
		},
		bindBlockEvent:function(){
			//鼠标划过列表项目的事件
			$('*[id="'+this.blockId+'"]').find(".selectIist li").hover(
				function(){
					$(this).addClass("selectIistHover");
				},
				function(){
					$(this).removeClass("selectIistHover");
				}
			);	
			
			//绑定选中列表项目时的事件
			var $this = this;
			
			$(this.target).bind("tjChange",function(){
				$($this.target).data('tjSelect').change();
			});
			
			if ($(this.target).attr('onchange' != undefined)){
				var fun = this.attr('onchange');
				$(this).tjChange(fun);
			}
			
			$('*[id="'+this.blockId+'"]').find(".selectIist li").click(function(){
				var name = jQuery.trim($(this).text());
				var value = jQuery.trim($(this).attr("val"));	
				$this.refreshOperOrCloseState();
				$(this).parent().find("li").removeAttr("selected").removeClass("selectIistActive").removeClass("selectIistHover");
				$(this).attr("selected","selected").addClass("selectIistActive");
				$($this.target).find(".selectTreeDIV ul").empty().append($('*[id="'+$this.blockId+'"]').find("ul").html());
				
				//添加change事件
				if (value != $($this.target).find(".selectDIV_input_hidden").val()){
					//给选框赋值（必须在触发change事件之前）
					$this.setThisValue(name,value);
					$($this.target).find(".selectDIV_input").focus();
					$($this.target).trigger('tjChange');
				}
			});

		},
		//全屏绑定关闭下拉列表
		closeSelect:function(){
			$('div[id*="_block"]').hide();
			$(".select").removeClass("selecthover").find(".selectDIV").removeClass("selectDIV_hover");
			$(document).unbind('click');
		},
		refreshOperOrCloseState:function(){
			if ($('*[id="'+this.blockId+'"]').find('.selectTreeDIV:visible').length==0){
				$(this.target).addClass("selecthover");
				$(this.target).find('.selectDIV').addClass("selectDIV_hover");
				$('*[id="'+this.blockId+'"]').show();
			}else {
				$(this.target).removeClass("selecthover");
				$(this.target).find(".selectDIV").removeClass("selectDIV_hover");
				$('*[id="'+this.blockId+'"]').hide();
			}
		}
	});
	
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
					if (jQuery.trim($(this).attr('val')) == jQuery.trim(value)){
						$($this).find(".selectDIV_input").val(jQuery.trim($(this).text()));
						$($this).find(".selectDIV_input_hidden").val(jQuery.trim($(this).attr("val")));
						$('*[id="'+$($this).data('tjSelect').blockId+'"]').find("li").removeAttr("selected").removeClass("selectIistActive").removeClass("selectIistHover");
						$('*[id="'+$($this).data('tjSelect').blockId+'"]').find('li[val="'+value+'"]').attr("selected","selected").addClass("selectIistActive");
					}
				});
				return $(this);
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
					if (jQuery.trim($(this).text()) == jQuery.trim(text)){
						$($this).find(".selectDIV_input").val(jQuery.trim($(this).text()));
						$($this).find(".selectDIV_input_hidden").val(jQuery.trim($(this).attr("val")));
						$('*[id="'+$($this).data('tjSelect').blockId+'"]').find("li").removeAttr("selected").removeClass("selectIistActive").removeClass("selectIistHover");
						$('*[id="'+$($this).data('tjSelect').blockId+'"]').find('li').each(function(){
							if ($(this).html() == text){
								$(this).attr("selected","selected").addClass("selectIistActive");
							}
						});
					}
				});
				return $(this);
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
		$('*[id="'+$(this).data('tjSelect').blockId+'"]').find('li').remove();
		return $(this);
	};
	
	//重新组装成select
	$.fn.tjAssemble = function(){
		var tjSelect = $(this).data('tjSelect');
		if ($(this).find('li[selected="selected"]').length == 0){
			$(this).find('li:first').attr('selected','selected').addClass('selectIistActive');
		}
		else{
			$(this).find('li[selected]').addClass('selectIistActive');
		}
		//获取默认值
		var defaultName  = jQuery.trim($(this).find(".selectTreeDIV li[selected]").text());
		var defaultValue = jQuery.trim($(this).find(".selectTreeDIV li[selected]").attr("val"));
		tjSelect.setThisValue(defaultName,defaultValue);
		$('*[id="'+tjSelect.blockId+'"]').find('.selectTreeDIV').html($(this).find(".selectTreeDIV").html());
		tjSelect.bindBlockEvent();
	};
	
})(jQuery);
