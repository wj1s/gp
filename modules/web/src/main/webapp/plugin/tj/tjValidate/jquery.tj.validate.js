(function($){ 
	/*以下是调用.form方法，为form统一创建验证*/
	$.fn.form = function(){
		var tjForm=new TjForm(this);
		$(this).data("tjForm",tjForm);
        return tjForm;
    };
    
    $.fn.valiForm = function(){
		var tjForm=$(this).data("tjForm");
		tjForm.validateForm();
    };
    
    $.fn.removeTip = function(){
		$('.tooltiptable, .tooltipfang').remove();
    };
    
    $.fn.validate = function(tjForm){
		var arr=[];
        this.each(function(){
			if ($(this).data("tjVal") == undefined) {
				var tjValidate = TjValidate.createTjValidate(this, tjForm);
				$(this).data("tjVal",tjValidate);
				arr.push(tjValidate);
			}else{
				var tjValidate=$(this).data("tjVal");
				tjValidate.tjForm=tjForm;
				arr.push(tjValidate);
			}
		});
		return arr;
    };
	
	/**清理表单，回复为未执行过validate方法的状态，可以在其后重新定义验证规则，通过执行VALIDATEA或是FORM方法改变表单的验证规则*/
	$.fn.validateClear = function(){
        this.each(function(){
			if($(this).data("tjVal") != undefined){
				var valId=$(this).data("tjVal").valId;
				$("#after"+valId).remove();
				$(this).removeData("tjVal");
//				$(this).removeAttr("verify");
			}
		});
    };
    
	function TjForm(target){
		this.target=target;
		this.tjValidates;
		this.init();
		
    }
	
	$.extend(TjForm.prototype, {
		init: function(){
			var $this = $(this.target);
			//加入表单验证（必须在类型初始化后边，否则会影响验证效果，例如失去焦点先应该通过类型控件做处理，再对最后结果做验证）
			this.tjValidates=$this.find('*[verify]').not('div').validate(this);
			this.validateForm(event);
        },
		checkSubmit:function(){
			var pass=true;
			if (this.tjValidates!=undefined) {
				$.each(this.tjValidates, function(i) {
					if (this.check() == false) {
						pass=false;
						var msg = this.validateForSubmit();
						this.showTip(msg);
					}
			    });
			}
			return pass;
		},
		hideTips:function(){
			if (this.tjValidates!=undefined) {
				$.each(this.tjValidates, function() {
					this.hideTip();
			    });
			}
		},
		validateForm:function(event){
			if (this.tjValidates!=undefined) {
				$.each(this.tjValidates, function(i) {
					this.validateForInput(event);
			    });
			}
		}
	});
	function TjValCell(tjValidate,fName,op,para,msg,delay,link,useCache,hint){
		//基础属性
		this.tjValidate=tjValidate;
		this.fName=fName;
		this.op=op;
		this.para=para;
		this.msg=msg;
		
		//显示
		this.hint=hint;//表单后面加的标示，例如非空的*
		//附加配置
		this.delay = delay;//判断是否延迟执行验证，当配置为true时，只有blur事件时才执行验证
		this.useCache=useCache;//判断是否使用缓存
		this.link=link;//关联表单
		//缓存
		this.selfValCache;//自身的值缓存
		this.targetValCache;//关联的值缓存
		this.tjValRes = new TjValRes();//缓存结果信息
    }
	$.extend(TjValCell.prototype, {
		validate: function(event){
			//首先判断是否执行验证，默认delay为false的时候所有事件都执行;或者当delay设置为true，并且是blur事件的时候才执行判断
			if(!this.delay||(this.delay && (event == undefined || event == null || event.type == 'blur' ))){
				//判断是否使用缓存，默认useCache为true的时候都是用缓存，只有当useCache手动设置为false时不使用缓存
				if (!this.useCache) {
					this.tjValRes = this.runMethod();
				} else {
					//和其他验证的标签无关
					if ( this.isFirstVal() || this.selfValueChange() || this.targetValueChange()){
						this.tjValRes = this.runMethod();
						this.selfValCache = this.getSelfValue();
						this.targetValCache = this.getTargetValues();
						
					}
				}
			}
			return this.tjValRes;
        },
        validateSubmit :function (){
        	this.tjValRes = this.runMethod();
        	//如果此验证采用了延迟验证，则可能在提交的时候验证结果已经改变，需要重新清空记录值
        	this.selfValCache=undefined;  //清理缓存
    		this.targetValCache=undefined;//关联的值缓存
        	return this.tjValRes;
        },
        //执行验证单元的验证方法
        runMethod:function(){
			return eval('this.'+this.fName+'()');
        },
        //获取被验证表单当前的值
        getSelfValue:function(){
        	return $(this.tjValidate.target).val();
        },
        //获取被验证表单关联的值，可以为数值，或对比标签的值，或是相关标签的值
        getTargetValues:function(){
        	var targetValues=[];
        	var secondVal = (this.para==undefined|| this.para.indexOf("#") ==-1) ? this.para : $(this.para).val();
        	targetValues[0] = secondVal;
        	if(this.hasLink()){
        		var ids = this.linkIds();
        		for ( var i in ids) {
        			targetValues[i+1] = $($.trim(ids[i]+"")).val();
				}
        	}
        	return targetValues;
        },
        //判断是否是第一次执行验证
        isFirstVal: function(){
        	return this.selfValCache == undefined ? true : false;
        },
        //判断标签当前的值是否对比缓存已经变化
        selfValueChange: function(){
        	return this.selfValCache != this.getSelfValue() ? true : false;
        },
        //判断当前标签对比的标签的值是否对比缓存已经变化
        targetValueChange: function(){
        	var targetTargetValues=this.getTargetValues();
        	for (var i in targetTargetValues) {
        		if (this.targetValCache[i] != targetTargetValues[i]){
					return true;
				}
			}
        	return false;
        },
        //判断是否有关联表单
        hasLink: function(){
        	return this.link != undefined ?true:false;
        },
        //获取关联表单的id列表
        linkIds: function(){
        	return this.link.split(',');
        },
        
        //!验证方法
        getLength: function(target) {
			switch( target.nodeName.toLowerCase() ) {
			case 'select':
				return $("option:selected", target).length;
			case 'input':
				if(/radio|checkbox/i.test(target.type))
					//return this.findByName(element.name).filter(':checked').length;
					return false;
			}
			return $(target).val().length;
		},
		//根据两个日期字符串获得秒差
		getDatemargin:function (startTime,endTime){
			var startDate = new Date(startTime.replace(/-/g,'/'));
			var endDate = new Date(endTime.replace(/-/g,'/'));
			var times = (endDate.getTime() - startDate.getTime())/1000;
			if(times){
				return times;
			}else{
				return 0;
			}
		},
		//根据两个HM时间获得秒差
		getTimeHM:function(startTime,endTime){
			var start = parseInt(startTime.substring(0,2)) * 60 +  parseInt(startTime.substring(3));
			var end = parseInt(endTime.substring(0,2)) * 60 +  parseInt(endTime.substring(3));
			var times = end - start;
			if(times){
				return times;
			}else{
				return 0;
			}
		},
		//根据时分秒字符串得到具体秒数
		getTimeSecond:function (timeStr){
		 	if(timeStr.length == 0){
		 		return 0;
		 	}
		 	var second = 0;	
		 	var hour = parseInt(timeStr.substring(0,(timeStr.length-6)),10);
		 	var min = parseInt(timeStr.substring((timeStr.length-5),(timeStr.length-3)),10);
			var sec = parseInt(timeStr.substring((timeStr.length-2)),10);
		 	second =hour*3600 + min*60 + sec*1;
		 	return second;
		},
		match:function(reg,msg){
			var value=this.getSelfValue();
			return this.verf(function(){return reg.test(value);}, msg);
		},
		verf:function(fun,msg){
			var name=this.tjValidate.name;
			var value=this.getSelfValue();
			if (value == null|| value == "") {
				return new TjValRes(true);
			}
			var res=fun();
			if(!res){
				return new TjValRes(false,name + msg);
			}
			return new TjValRes(true);
		},
		Regex:function(){
			return this.match(new RegExp(eval(this.para)), this.msg);
		},
		Fun:function(){
			var name=this.tjValidate.name;
			var value=this.getSelfValue();
			if (value == null|| value == "") {
				return new TjValRes(true);
			}
			var res=eval(this.para + "(this.tjValidate.target)");
			if (typeof res == 'string'){
				return new TjValRes(false,res);
			}else if (typeof res == 'boolean') {
				if(!res){
					return new TjValRes(false,this.msg);
				}
			}
			return new TjValRes(true);
		},
		NotNull:function(){
			var name=this.tjValidate.name;
			var value=this.getSelfValue();
            if (value == null || value == '') {
            	return new TjValRes(false,name + '不能为空');
            }
            return new TjValRes(true);
		},
		NotRepeat:function(){
			var name=this.tjValidate.name;
			var checkValue=this.getSelfValue();					
			var paraArray = this.para.split('.');
			var pack=paraArray[0];
			var model=paraArray[1];
			var checkKey=paraArray[2];
			var checkValue=this.getSelfValue();
			var url=ctp +'/'+pack+'/'+model+'!checkUnique.action';			
			var flag=false;			
			var id=$(this.tjValidate.target).parents('form').find('input[name="id"]').val();
			var data;
			if(id==''){//新增
				 data= {checkKey:checkKey,checkValue:checkValue};
			}else{//编辑
				 data= {checkKey:checkKey,checkValue:checkValue,id:id};
			}
			$.ajax({
				  type: 'POST',
				  url: url,
				  dataType: 'json',
				  data: data,
				  async: false,
				  success: function(data){	
					  if (data.result == true){
						  flag=true;
					  }
				  }
			});			
			if (flag){
				  return new TjValRes(true);
			}else{//执行变更操作	
				  return new TjValRes(false,name + '不能重复');
			}
		},				
		Number:function(){
			return this.match(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/, '必须是一个数字');
		},
		Fraction:function(){
			return this.match(/^[1-9][0-9]*[\/][1-9][0-9]*$/, '必须是一个分数');
		},
		Int:function(){
			return this.match(/^\-?\d+$/, '必须是一个整数');
		},
		Date:function(){
			return this.match(/^([1-2]\d{3})\-(0[1-9]|1[0-2])\-(0[1-9]|[12]\d|3[01])$/, '不是正确的日期格式，类似：2001-01-01');
		},
		Time:function(){
			return this.match(/^([01]\d|2[0-3]):([0-5]\d):([0-5]\d)$/, '不是正确的时间格式，类似：01:01:01');
		},
		/*
		 * zhaozx add 带天数的时间
		 */	
		TimeL:function(){
			var val=this.getSelfValue();
			val=val.replace(/：/g, ":");
			$(this.tjValidate.target).val(val);
			return this.match(/^(\d+\d):([0-5]\d):([0-5]\d)$/, '不是正确的时间格式，类似：01:01:01');
		},		
		Fraction:function(){
			var val=this.getSelfValue();
			$(this.tjValidate.target).val(val);
			return this.match(/^(\d+)\/(\d+)$/, '不是正确的分数格是，类似：3/4');
		},
		Datetime:function(){
			return this.match(/^([1-2]\d{3})\-(0[1-9]|1[0-2])\-(0[1-9]|[12]\d|3[01]) ([01]\d|2[0-3]):([0-5]\d):([0-5]\d)$/, '不是正确格式，类似：2001-01-01 01:01:01');
		},
		Email:function(){
			return this.match(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, '不是正确的EMAIL格式');
		},
		Length:function(){
			var op=this.op;
			var value;
			var selfTarget=this.tjValidate.target;
			var getLength=this.getLength;
			if(this.para.indexOf("#")!=-1){
				value=this.getLength($(this.para));
	        }else{
	        	value = parseInt(this.para);
	        }
			if (op == "=") {
				return this.verf(function(){return getLength(selfTarget) == value;}, '必须等于' + this.para + '个字.');
			} else if (op == ">") {
				return this.verf(function(){return getLength(selfTarget) > value;}, '不能小于' + this.para + '个字.');
			} else if (op == "<") {
				return this.verf(function(){return getLength(selfTarget) < value;}, '不能大于' + this.para + '个字.');
			} else if (op == "<="){
				return this.verf(function(){return getLength(selfTarget) <= value;}, '不能超过' + this.para + '个字.');
			} else if (op == ">="){
				return this.verf(function(){return getLength(selfTarget) >= value;}, '不能少于' + this.para + '个字.');
			}
		},
		IllChar:function(){
			var name=this.tjValidate.name;
			var targetVal=$(this.tjValidate.target).val();
			if (targetVal.indexOf("'") != -1 || targetVal.indexOf("\"") != -1){
				return new TjValRes(false,name + "不能包含英文输入法中的单引号或双引号！");
			}else {
				return new TjValRes(true);
			}
		},
		Decimal:function(){
			var op=this.op;
			var selfTarget=this.tjValidate.target;
			var index = this.para.indexOf(",");
			var m = 0;
			var d = 0;
			var sum = 0;
			if(index!=-1){
				m = this.para.substring(0,index);
				d = this.para.substring(index+1);
				sum = m-d;
	        }else{
	        	m = parseInt(this.para);
	        }
			var reg = eval('/^[0-9]{0,'+sum+'}\\.[0-9]{0,'+d+'}$|^[0-9]{0,'+sum+'}$/');
			return this.match(reg, '必须满足小数点前'+ sum + '位，小数点后'+d+'位!');
		},
		Times:function(){
			var op=this.op;
			var selfValue=this.getSelfValue();
			var getDatemargin=this.getDatemargin;
			var value;
			var msg;
			if(this.para.indexOf("#")!=-1){
				value=$(this.para).val();
				msg=this.msg;
	        }else{
	        	value = this.para;
	        	msg = this.para;
	        }
			if (op == "=") {
				return this.verf(function(){return getDatemargin(selfValue,value)==0;}, '时间必须为' +  msg);
			} else if (op == ">") {
				return this.verf(function(){return getDatemargin(selfValue,value)<0;}, '时间必须晚于' +  msg);
			} else if (op == "<") {
				return this.verf(function(){return getDatemargin(selfValue,value)>0;}, '时间必须早于' +  msg);
			}else if (op == ">=") {
				return this.verf(function(){return getDatemargin(selfValue,value)<=0;}, '时间必须晚于或者等于' +  msg);
			}else if (op == "<=") {
				return this.verf(function(){return getDatemargin(selfValue,value)>=0;}, '时间必须早于或者等于' +  msg);
			}
		},
		TimeHM:function(){
			var op=this.op;
			var selfValue=this.getSelfValue();
			var getTimeHM=this.getTimeHM;
			var value;
			var msg;
			if(this.para.indexOf("#")!=-1){
				value=$(this.para).val();
				msg=this.msg;
	        }else{
	        	value = this.para;
	        	msg = this.para;
	        }
			if (op == "=") {
				return this.verf(function(){return getTimeHM(selfValue,value)==0;}, '时间必须为' +  msg);
			} else if (op == ">") {
				return this.verf(function(){return getTimeHM(selfValue,value)<0;}, '时间必须晚于' +  msg);
			} else if (op == "<") {
				return this.verf(function(){return getTimeHM(selfValue,value)>0;}, '时间必须早于' +  msg);
			}else if (op == ">=") {
				return this.verf(function(){return getTimeHM(selfValue,value)<=0;}, '时间必须晚于或者等于' +  msg);
			}else if (op == "<=") {
				return this.verf(function(){return getTimeHM(selfValue,value)>=0;}, '时间必须早于或者等于' +  msg);
			}
		},
		Value:function(){
			var op=this.op;
			var selfValue=this.getSelfValue();
			var value;
			var msg;
			if(this.para.indexOf("#")!=-1){
				value=$(this.para).val();
				msg=this.msg;
	        }else{
	        	value = this.para;
	        	msg=this.para;
	        }
			//支持00:00:00与秒值的对比
			if (this.Time().val) {
				selfValue = this.getTimeSecond(selfValue);
			}
			if (op == "=") {
				return this.verf(function(){return selfValue == value;}, '必须等于' +  msg);
			} else if (op == ">") {
				return this.verf(function(){return parseFloat(selfValue) > parseFloat(value);}, '必须大于' +  msg);
			} else if (op == "<") {
				return this.verf(function(){return parseFloat(selfValue) < parseFloat(value);}, '必须小于' +  msg);
			} else if (op == ">=") {
				return this.verf(function(){return parseFloat(selfValue) >= parseFloat(value);}, '必须大于或等于' +  msg);
			} else if (op == "<=") {
				return this.verf(function(){return parseFloat(selfValue) <= parseFloat(value);}, '必须小于或等于' +  msg);
			}
		}
	});
	
	
	function TjValRes(val,msg){
		this.val = val==undefined?true:val;//true or false
		this.msg = msg==undefined?'':msg;
	}
	
    function TjValidate(target,tjForm){
		this.target=target;
		this.tjForm=tjForm;
		this.valId=$(target).attr("id")!=""?$(target).attr("id")+'_val':$(target).attr("name")+'_val';
		this.name='';
		this.tjValCells;
    }
    $.extend(TjValidate, {
        closeTip:function(b) {
    		if (b&&b._VerifyTip) {
    			b._VerifyTip.close();
    			b._VerifyTip = null;
    			b._VerifyMsg = null;
    		}
    	},
    	createTjValidate:function(target,tjForm) {
    		var tjValidate=new TjValidate(target,tjForm);
    		var v=$(target).attr("verify");//时长|NotNull&&Time&&Value>0&&Fun<'validateTime'
    		var vArr = v.split("|");
    		var rules;//规则组：NotNull&&Time&&Value>0&&Fun<'validateTime'
    		if (vArr.length==2) {
    			tjValidate.name = vArr[0];//提示名称头:时长
    			rules=vArr[1];
			} else if(vArr.length==1){
				rules=vArr[0];
			}
    		if(rules!=undefined){
    			var tjValCells=[];
    			var rulesArr = rules.split("&&");//规则单元：[NotNull,Time,Value>0,Fun<'validateTime'@d,Fun={...}]
    			for ( var i = 0; i < rulesArr.length; i++) {
    				var rule=rulesArr[i];
    				var op = "=";//验证模式
        	        if (rule.indexOf(">=") > 0) {
        	            op = ">=";
        	        }else if (rule.indexOf(">") > 0) {
        	            op = ">";
        	        }else if (rule.indexOf("<=") > 0) {
                        op = "<=";
                    }else if (rule.indexOf("<") > 0) {
                    	op = "<";
                	}
        	        var ruleArr = rule.split(op);
        	        var fName = ruleArr[0];//验证方法簇的名称：NotNull,Fun
        	        var fValue;
        	        var fMsg = '';
        	        var delay = false;
        	        var link;
        	        var useCache=true;
        	        var hint='';
        	        if (ruleArr.length > 1) {
        				var fValues = ruleArr[1].split("@");//1 or [1,提示辅助信息]
        				fMsg=fValues.length > 1?fValues[1]:'';
        				var temp=fValues[0];
        				if(temp.indexOf("{") == 0){
        					fValue = eval("("+temp+")").val;
        					fMsg = eval("("+temp+")").msg;
        					
            	        	if (eval("("+temp+")").delay != undefined){
            	        		delay = eval("("+temp+")").delay;
            	        	}
            	        	if (eval("("+temp+")").link != undefined){
            	        		link = eval("("+temp+")").link;
        	        		}
            	        	if (eval("("+temp+")").useCache != undefined){
            	        		useCache = eval("("+temp+")").useCache;
        	        		}
            	        }else{
            	        	fValue=temp;
            	        }
        	        }
        	        if(fName=='NotNull'){
        	        	hint='NotNull';
        	        }
        	        var tjValCell = new TjValCell(tjValidate, fName, op, fValue, fMsg,delay,link,useCache,hint);
        	        tjValCells.push(tjValCell);
    			}
			}
    		
    	    tjValidate.tjValCells=tjValCells;
    	    tjValidate.init();
    	    return tjValidate;
    	}
    	
    });
	
    
    $.extend(TjValidate.prototype, {
		init: function(){
			$(this.tjValCells).each(function(){
				if (this.hint == 'NotNull'){
					var tagName = this.tjValidate.target.tagName;
					if (tagName == 'INPUT' || tagName == 'input'){
						$(this.tjValidate.target).after('<span class="asterisk">&nbsp;&nbsp;</span><span id="after'+this.valId+'"></span>');
					}
					else if (tagName == 'textarea' || tagName == 'TEXTAREA'){
						$(this.tjValidate.target).after('<span class="asterisk">&nbsp;&nbsp;</span><span id="after'+this.valId+'"></span>');
					}
				}
			});
			var html = '<span class="validate_img" id="img'+this.valId+'" >&nbsp;</span>';
			$(this.target).siblings('*[id^="after"]').html(html);
			var obj=this;
			$(this.target).keyup(function(event){
				if(obj.tjForm!=undefined){
					obj.tjForm.hideTips();
				}
				var msg=obj.validateForInput(event);
				obj.showTip(msg);
			}).click(function(event){
				if(obj.tjForm!=undefined){
					obj.tjForm.hideTips();
				}
				var msg=obj.validateForInput(event);
				obj.showTip(msg);
			}).focus(function(event){
				if(obj.tjForm!=undefined){
					obj.tjForm.hideTips();
				}
				var msg=obj.validateForInput(event);
				obj.showTip(msg);
			}).blur(function(event){
				if(obj.tjForm!=undefined){
					obj.tjForm.hideTips();
				}
				obj.tjForm.validateForm(event);
				obj.hideTip();
			}).change(function(event){
				if(obj.tjForm!=undefined){
					obj.tjForm.hideTips();
				}
				obj.tjForm.validateForm(event);
				obj.hideTip();
			});
			$('#img'+this.valId).hover(function(event){
				var msg=obj.validateForInput(event);
				obj.showTip(msg);
			},function(event){
				obj.hideTip();
			});
        },
		
		validateForInput:function (event){
			var valId=this.valId;
			var msg='';
			$(this.tjValCells).each(function(){
				var tjValRes = this.validate(event);
				if (tjValRes.val == false) {
					msg=tjValRes.msg;
					return false;
				}
			});
			if(msg!=''){
				$('#img'+valId).show();
				if (this.target.tagName == 'INPUT'){
					$(this.target).addClass('textInputInvalid');
				}else if (this.target.tagName == 'TEXTAREA'){
					$(this.target).addClass('textTextareaInvalid');
				}
			}else{
				$('#img'+valId).hide();
				if (this.target.tagName == 'INPUT'){
					$(this.target).removeClass('textInputInvalid');
				}else if (this.target.tagName == 'TEXTAREA'){
					$(this.target).removeClass('textTextareaInvalid');
				}
			}
			return msg;
		},
		
		validateForSubmit:function (event){
			var valId=this.valId;
			var msg='';
			$(this.tjValCells).each(function(){
				var tjValRes = this.validateSubmit(event);
				if (tjValRes.val == false) {
					msg=tjValRes.msg;
					return false;
				}
			});
			if(msg!=''){
				$('#img'+valId).show();
				$(this.target).addClass('textTnputInvalid');
			}else{
				$('#img'+valId).hide();
				$(this.target).removeClass('textTnputInvalid');
			}
			return msg;
		},

		check:function(){
			var pass=true;
			for(var i = 0; i < this.tjValCells.length; i++){
				var tjValRes = this.tjValCells[i].validateSubmit(event);
				if (tjValRes.val == false) {
					pass = false;
					break;
				}
			}
			return pass;
		},
		
		hideTip:function(){
			TjValidate.closeTip(this.target);
		},
		
		showTip:function(msg){
			//暗示提示信息
			if (msg.length > 0) {
				var txt = msg+"<br>";
				var ele=this.target;
				if (txt != ele._VerifyMsg) {
					TjValidate.closeTip(ele);
					tip = Tip.show(ele, txt);
					ele._VerifyTip = tip;
					ele._VerifyMsg = txt
				}
			} else {
				TjValidate.closeTip(this.target);
			}
		}
    });
	
	var _TipIDCounter = 0;
	function Tip(a, b) {
		this.Element = a;
		this.Message = b;
		this.AutoClose = false;
		var left = $(this.Element).offset().left;
		if ($(window).width() - left <= 180){
			this.Clock = 3;
		}else {
			this.Clock = 9;
		}
		this.initHtml();
	}
	Tip.AutoCloseTips = [];
	Tip.prototype.initHtml = function() {
		var a = [];
		a
				.push("  <table border='0' cellspacing='0' cellpadding='0' class='tooltiptable'>");
		a
				.push("  	<tr><td class='corner topleft'> </td><td class='topcenter'> </td>");
		a
				.push("  			<td class='corner topright'> </td></tr><tr><td class='bodyleft'> </td>");
		a.push("				<td class='tooltipcontent'>" + this.Message + "</td>");
		a.push("				<td class='bodyright'> </td></tr>");
		a
				.push("		<tr><td class='corner footerleft'> </td><td class='footercenter'> </td>");
		a.push("				<td class='corner footerright'> </td></tr></table>");
		a.push("		<div class='tooltipfang'></div>");
		this.Html = a.join("");
	};
	Tip.prototype.show = function() {
		var k = document.createElement("div");
		k.id = "" + _TipIDCounter++;
		k.style.position = "absolute";
		k.style.left = "0px";
		k.style.top = "0px";
		k.className = "tooltip callout" + this.Clock;
		$('body').append(k)
		$(k).append(this.Html);
		var j = $(this.Element).offset();
		var f = {width:$(this.Element).width(),height:$(this.Element).height()};
		f.width += 30
		if ($(this.Element).attr("ztype")&&($(this.Element).attr("ztype").toLowerCase() == "date"||$(this.Element).attr("ztype").toLowerCase() == "time"||$(this.Element).attr("ztype").toLowerCase() == "datetime")) {
			f.width += 11
		}
		var d = {width:$(k).width(),height:$(k).height()};
		var h = this.Clock;
		var a, g;
		/*if (h == 2 || h == 3 || h == 4) {
			a = j.left - f.width
		}
		if (h == 8 || h == 9 || h == 10) {
			a = j.left + f.width
		}
		if (h == 11 || h == 12 || h == 1) {
			g = j.top + f.height
		}
		if (h == 5 || h == 6 || h == 7) {
			g = j.top - f.height
		}
		if (h == 9 || h == 3) {
			g = j.top + f.height / 2 - d.height / 2
		}*/
		if (h == 9){
			a = j.left + f.width;
			g = j.top + f.height / 2 - d.height / 2
		}else if (h == 3){
			if ($(k).width() > 150){
				k.style.width = 150 + 'px';
				a = j.left - 155;
			}else {
				a = j.left - $(k).width() - 5;
			}
			g = j.top + f.height / 2 - d.height / 2;
		}
		k.style.left = (j.left + f.width - 15) + "px";
		k.style.top = (j.top-13) + "px";
		k.style.zIndex = 1010;//JQUERY DIALOG ZINDEX=1001
		$(k).show();
		this.Div = k;
		if (this.AutoClose) {
			Tip.AutoCloseTips.push(this)
		}
	};
	Tip.prototype.close = function() {
		if (this.Div) {
			$(this.Div).remove();
			this.Div = null
		}
	};
	Tip.show = function(d, f, a, b) {
		var c = new Tip(d, f);
		c.AutoClose = a;
		if (b) {
			c.Clock = b
		}
		c.show();
		if (!c.AutoClose) {
			if (!d._Tips) {
				d._Tips = []
			}
			d._Tips.push(c)
		}
		return c
	};
	Tip.getTipCount = function(a) {
		a = $(a);
		if (!a._Tips) {
			return 0
		}
		return a._Tips.length
	};
	Tip.close = function(b) {
		b = $(b);
		if (b._Tips) {
			for (var a = 0; a < b._Tips.length; a++) {
				if (b._Tips[a]) {
					b._Tips[a].close()
				}
			}
			b._Tips = []
		}
	};

})(jQuery)
