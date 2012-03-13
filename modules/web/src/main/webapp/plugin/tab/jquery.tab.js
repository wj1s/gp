
(function($) {
	
	
	
	//临时变量，记录点击标签时该标签的索引数字    
	var tab_index;
	
	//初始化标签
	$.fn.tab = function(tabMethod,tabWidth,tabHeight,tab_index,a_d,i_tab) {
		$(this).append('<div class="tabBottom"></div>');//插入底部边线样式
		$(this).find('> ul').attr('class','tabTitle');
		$(this).find('> div').not('.tabBottom').attr('class','tabContent');
		
		var $tabs = this.find(".tabTitle li");
		var $contents = this.find(".tabContent");
		var id = this.find(".tabTitle li").eq(tab_index).attr('id');
		
		
		
		//判断如果此标签页有数字显示模块，则显示此标签页的数字
		this.find(".tabTitle li").each(function(){
			var num = $(this).attr('Num');
			if(num != undefined && num != null){
				num = $.trim(num);
				var html = $(this).html();
				//判断如果此标签页有数字显示模块，则显示此标签页的数字
				if(num>99){
					$(this).html('<div class="tabNumMore">'+''+'</div>'+html);
				}else{
					$(this).html('<div class="tabNum">'+num+'</div>'+html);
				};
			}
		})
		

		//设置tab页的宽度与高度
		if(tabWidth != '0'){this.css("width",tabWidth+"px");}else{this.css("width","auto");}
		if(tabHeight != '0'){this.find('.tabContent').css("height",(tabHeight-32)+"px");}
		
		//循环标签页，初始化标签样式。
		$tabs.each(function(index){
			//设置是否启用增删功能的图标
			if($tabs.find(".titleRightImg").length == 0){
				if(a_d == '0'){
					$tabs.append("<div class='titleRightImg'></div>");}else{$tabs.append("<div class='titleRightImg'></div><div class='tabClose'></div>");
				}
			}
			if ($.browser.msie) {
				var ua = navigator.userAgent;
				if (/msie 6/i.test(ua)) {$(".titleRightImg").css("right","-1px");$(".tabClose").css("background-image","url(../images/tab_close.png)");}
			}
		});
		
		//调用启用标签方法，传入要显示的高度和要强化显示的标签索引数值
		this.setTab(tabMethod,tabHeight,i_tab);
		
		//激活要显示的标签，传入激活的标签索引数值
		this.activeTab(tabMethod,tab_index,id); 
		
		$('.tab').show();
	};
	
	//启用标签页的方法
	$.fn.setTab = function(tabMethod,tabHeight,i_tab){
		var $this = this;
		
		//如果传入的高度不为0或空，则设置标签内容的高度；
		if(tabHeight!='0'&& tabHeight!='undefined'){$this.find('.tabContent').css("height",(tabHeight-32)+"px");}
		//alert(tabHeight+','+i_tab)
		//为标签绑定事件
		this.find(".tabTitle li").each(function(index){
			
			$(this).hover(function(){
				if($(this).attr("class") != "tabActive"){
					$(this).addClass("tabHover").find(".titleRightImg").addClass("titleRightImgHover");
					$(this).find('.tabClose').show();
				}
				$(this).click(function(){
					var id = $(this).attr('id');
					tab_index = index;
					$this.clearTabClass();
					$this.activeTab(tabMethod,tab_index,id);
				})
			},function(){
				$(this).removeClass("tabHover").find(".titleRightImg").removeClass("titleRightImgHover");
				$(this).find('.tabClose').hide();
			});	
			
			//为标签的删除按钮绑定事件
			if($(this).find('.tabClose').length == 1){
				$(this).find('.tabClose').unbind('click').click(function(){
					//var id = $(this).parent().attr('name').split('_')[1];
					var id = $(this).parent().attr('id');
					$this.delTab(tabMethod,tabHeight,id);
				}).hover(function(){
					$(this).css('background-position','-14px 0');
				},function(){
					$(this).css('background-position','0 0');
				});
			};
			
		});
		
		//设置强化标签
		if(i_tab != 'undefined'){
			this.find('.tabTitle li').eq(i_tab).addClass("itab");
			this.find('.tabTitle li').eq(i_tab).find(".titleRightImg").addClass("titleRightImgitab");
		};
	};
	
	//增加新标签
	$.fn.addTab = function(title,str,id,tabHeight,tabMethod){
		var $this = this;
		//多行ul时，默认在最后一组ul里增加新标签，以后考虑根据传入的参数设置在哪行新增标签；
		this.find(".tabTitle").eq(this.find(".tabTitle").length-1).append('<div class="newTab"></div>');
		this.find('.tabBottom').before(str);
		this.find('.newTab').fadeOut(1200,function(){
			if(id == 'false'){
				$(this).replaceWith('<li><div class="titleRightImg"></div><div class="tabClose"></div><a>'+title+'</a></li>');
			}else{
				$(this).replaceWith('<li'+id+'><div class="titleRightImg"></div><div class="tabClose"></div><a>'+title+'</a></li>');
			}
			$this.setTab(tabMethod,tabHeight);
 		});	
	};

	//删除标签
	$.fn.delTab = function(tabMethod,tabHeight,id){	
		var flag = confirm("您确定要删除此标签吗？");
		if(flag){
			this.find(".tabTitle").find('li[id='+id+']').remove();
			//this.find(".tabContent[name=content_"+name+"]").remove();
			this.setTab(tabMethod,tabHeight);
		}else{
			return false;
		}
		return false;
	};
	
	//激活选定的标签
	$.fn.activeTab = function(tabMethod,sel_index,id){
		this.clearTabClass();
		this.find(".tabTitle li").eq(sel_index).addClass("tabActive").find(".titleRightImg").addClass("titleRightImgActive");
		if(tabMethod == "0"){
			this.find(".tabContent").eq(0).show();
			refreshMethod(id);
		}else{
			this.find(".tabContent").eq(sel_index).show();
		}
		if (this.find(".tabContent").eq(sel_index).data('url') != undefined){
			this.find(".tabContent").empty();
			var url = this.find(".tabContent").eq(sel_index).data('url');
			this.find(".tabContent").eq(sel_index).load(url);
		}
		this.find(".tabTitle li").eq(sel_index).find('.tabClose').hide();
		this.find(".tabTitle li").find('.tabNum').hide();
		this.find(".tabTitle li").find('.tabNumMore').hide();
		
		//判断如果此标签页有数字显示模块，则显示此标签页的数字
		this.find(".tabTitle li").eq(sel_index).find('.tabNum').show();
		this.find(".tabTitle li").eq(sel_index).find('.tabNumMore').show();
		tab_index = sel_index;
	};
	
	//清除标签样式
	$.fn.clearTabClass = function(){
		this.find(".tabTitle li").removeClass("tabActive").removeClass("tabHover");
		this.find(".tabTitle li").find(".titleRightImg").removeClass("titleRightImgActive").removeClass("titleRightImgHover");
		this.find(".tabContent").hide();
	};
	
	//启用AJAX读取数据以后生成标签内容
	$.fn.tabAjax = function(sel_index,url){
		this.find(".tabContent").eq(sel_index).data('url',url);
	};
	
	//标签初始化
	$.fn.tabInit = function(){
		if ($(this).hasClass('tab')){
			$(this).tab(1,0,0,0,0);
		}
	};
	
	//隐藏某个标签(从0开始)
	$.fn.hideTab = function(index){
		if ($(this).hasClass('tab')){
			$(this).find('.tabTitle li:eq('+index+')').hide();
			$(this).find('>div:eq('+index+')').hide();
		}
		return $(this);
	};
	
	//展示某个标签(从0开始)
	$.fn.showTab = function(index){
		if ($(this).hasClass('tab')){
			this.activeTab(1,index); 
		}
		return $(this);
	};
	
})(jQuery);