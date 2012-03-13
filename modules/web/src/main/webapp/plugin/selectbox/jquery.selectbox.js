/**
 * Name: 基于jquery的下拉列表组件. 
 * Author: Ahulee;
 * charset: utf-8;
 * 
 */

(function($) {
	var i = 0;
	
	//选择列表框调用的方法
	$.fn.selectBoxDiv = function(){
		var $this = this;
		$this.show();

		var leftBox = $this.find(".selectBoxList").eq(0);
		var rightBox = $this.find(".selectBoxList").eq(1);
		var value ='';
		var name = $this.attr("name");
		var pTop1 = parseInt((leftBox.height() - 72)/3);
		var pTop2 = parseInt((leftBox.height() - 72)/2);
		var te;
		
		$this.find(".selectBoxList").attr("unselectable","on");
		$this.find(".selectBoxList li").attr("unselectable","on");
		//hidden text
		var strHtml     = '<input type="hidden" value="" name="'+name+'"/>';
		var strButton   = '<div style="float:left;text-align:center;width:60px;padding-top:'+pTop1+'px;">';
			strButton  += '<button type="button" class="toRight" disabled="true">添加</button><br/>';
			strButton  += '<button type="button" class="toLeft" disabled="true">删除</button>';
			strButton += '<button type="button" class="toRest" disabled="true">重置</button>';
			strButton  += '</div>';
		var strButtonN  = '<div style="float:left;text-align:center;width:60px;padding-top:'+pTop2+'px;">';
			strButtonN += '<button type="button" class="toTop" disabled="true">向上</button><br/>';
			strButtonN += '<button type="button" class="toBottom" disabled="true">向下</button>';
			strButtonN += '</div><div class="selectLeftBak"></div><div class="selectBak"></div>';
			
		if($this.find(".toRight").length == 0){
			$this.prepend(strHtml);
			leftBox.after(strButton);
			rightBox.after(strButtonN);
			rightBox.find("li").attr("select","select");
			$this.find(".selectLeftBak").html(leftBox.find("li").clone(true));
			$this.find(".selectBak").html(rightBox.find("li[select]").clone(true));
		}

		leftBox.empty().append($this.find(".selectLeftBak").find("li").clone(true));
		rightBox.empty().append($this.find(".selectBak").find("li[select]").clone(true));
		rightBox.find("li[select]").each(function(i){
			value += $(this).attr("val")+",";
		});
		$this.find("input[name="+name+"]").attr("value",value);
		value = '';

		
		//绑定鼠标划过项目有事件
		$this.find(".selectBoxList li").hover(function(){
			$(this).addClass("selectBoxListHover");
		},function(){
			$(this).removeClass("selectBoxListHover");
		});

		//选择
		$this.find(".selectBoxList li").mousedown(function(event){
			//判断是否按下ctrl键进行多选
			if(event.shiftKey){}else if(event.ctrlKey){
				$(this).toggleClass("selectBoxListActive");
			}else{
				$(this).parent().find("li").removeClass("selectBoxListActive")
				$(this).addClass("selectBoxListActive");
				$(this).parent().find("li").mouseover(function(){
					$(this).addClass("selectBoxListActive");
				});
				$(this).parent().find("li").mouseup(function(){
					$(this).parent().find("li").unbind("mouseover");
				});
			};
		});
		//左边
		leftBox.mousedown(function(event){
			$this.find(".toLeft").attr("disabled","true");
			$this.find(".toTop").attr("disabled","true");
			$this.find(".toBottom").attr("disabled","true");
			$this.find(".toRight").removeAttr("disabled");
			rightBox.find("li").removeClass("selectBoxListActive");
		});
		//右边
		rightBox.mousedown(function(event){
			$this.find(".toLeft").removeAttr("disabled");
			$this.find(".toRight").attr("disabled","true");
			leftBox.find("li").removeClass("selectBoxListActive");
			rightBox.find("li").mouseup(function(){
				var a = rightBox.find(".selectBoxListActive").length;
				var r = rightBox.find("li").length;
				if(a == 1 && r > 1){
					var pr = $(this).prev().attr("val");
					var ne = $(this).next().attr("val");
					if(!pr){
						$this.find(".toTop").attr("disabled","true");
						$this.find(".toBottom").removeAttr("disabled");
					}else if(!ne){
						$this.find(".toBottom").attr("disabled","true");
						$this.find(".toTop").removeAttr("disabled");
					}else{
						$this.find(".toTop").removeAttr("disabled");
						$this.find(".toBottom").removeAttr("disabled");
					}
				}else{
					$this.find(".toTop").attr("disabled","true");
					$this.find(".toBottom").attr("disabled","true");
				};
			});
		});
		
		/***************************/
		//添加
		$this.find(".toRight").click(function(){
			var obj = leftBox.find(".selectBoxListActive");
			rightBox.find("li").removeClass("selectBoxListActive");
			rightBox.append(obj);
			obj.removeClass("selectBoxListActive").addClass("selectBoxListSelect");
			setTimeout('clearselectBoxListSelect()',400);
			rightBox.find("li").each(function(i){
				value += $(this).attr("val")+",";
			});
			$this.find("input[name="+name+"]").attr("value",value);
			value = '';
			if(leftBox.find("li").length == 0){
				$this.find(".toRight").attr("disabled","true");
				$this.find(".toLeft").removeAttr("disabled");
			};
			$this.find(".toRest").removeAttr("disabled");
		});
		//删除
		$this.find(".toLeft").click(function(){
			var obj = rightBox.find(".selectBoxListActive");
			leftBox.find("li").removeClass("selectBoxListActive");
			leftBox.append(obj);
			obj.removeClass("selectBoxListActive").addClass("selectBoxListSelect");
			setTimeout('clearselectBoxListSelect()',400);
			rightBox.find("li").each(function(i){
				value += $(this).attr("val")+",";
			});
			$this.find("input[name="+name+"]").attr("value",value);
			value = '';
			if(rightBox.find("li").length == 0){
				$this.find(".toRight").removeAttr("disabled");
				$this.find(".toLeft").attr("disabled","true");
			};
			$this.find(".toRest").removeAttr("disabled");
		});
		//重置
		$this.find(".toRest").unbind("click").click(function(){
			$this.selectBoxDiv();
		});
		/***************************/
		//向上
		$this.find(".toTop").unbind("click").click(function(){
			var obj = rightBox.find(".selectBoxListActive");
			$this.find(".toBottom").removeAttr("disabled");
			obj.prev().before(obj);
			rightBox.find("li").each(function(i){
				value += $(this).attr("val")+",";
			});
			$this.find("input[name="+name+"]").attr("value",value);
			value = '';
			$this.find(".toRest").removeAttr("disabled");
			var pr = obj.prev().attr("val");
			if(pr == undefined){
				$this.find(".toTop").attr("disabled","true");
			}
		});
		//向下
		$this.find(".toBottom").unbind("click").click(function(){
			var obj = rightBox.find(".selectBoxListActive");
			$this.find(".toTop").removeAttr("disabled");
			obj.next().after(obj);
			rightBox.find("li").each(function(i){
				value += $(this).attr("val")+",";
			});
			$this.find("input[name="+name+"]").attr("value",value);
			value = '';
			$this.find(".toRest").removeAttr("disabled");
			var ne = obj.next().attr("val");
			if(ne == undefined){
				$this.find(".toBottom").attr("disabled","true");
			}
		});
		/***************************/
	};
	
	clearselectBoxListSelect = function(){
		$(".selectBoxListSelect").removeClass("selectBoxListSelect");
	}


	
})(jQuery);
