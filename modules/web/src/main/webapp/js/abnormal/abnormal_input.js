var abnOcount=0;
var abnEcount=0;
var subFlag=true;
var isEdit=false;
var abnTypeFlag='F';
//点击保存前的验证
function validateForm(arr, thisForm){	 
	if (thisForm.data('tjForm').checkSubmit()) {
		//判断一下如果类型是F则故障设别必须填写
		if(abnTypeFlag=='F'){
			if(($('#equipF').val())==''){
				tjAlert("故障设备不能为空！");
				subFlag=true;
				return false;	
			}
		}
		return true;
	}else{		
		subFlag=true;
		return false;					
	}
}
function submintFun(){
	var check=$('#abnormalForm').data('tjForm').checkSubmit();
	if(!check)return;	
	if(subFlag){
		subFlag=false;
		tjConfirm("确定提交审核？",function(flag){
			if (!flag){	
				subFlag=true;
				return;
			}else{
				$('#abnormalForm').submit();										
			}
		});			
					
	}
}
function saveFun(){	
	$('#abnormalForm').submit();	
}
function onSuccess(data){	
//判断-如果是值班长提交则跳转到查询界面-如果是机房主任则跳转到待办列表-如果是技办则关闭对话框	
	if (data['result'] == true){
		alert("操作成功！");
		var type=$('#type').tjVal();
		if (type=='F') {
			$('#tabNum').val("1");
		}else{
			$('#tabNum').val("2");
		}		
		var obj=document.getElementById("rtnForm");		
		obj.submit();		
	}else {
		tjAlert("操作失败！");
	}	
}
function getTransType(){
	var a=$('#transType').tjVal();//选出传输类型	
	return a;//选出传输类型
}
(function($) {
	
	/*以下是调用.form方法，为form统一创建验证*/
	$.fn.abnormal = function(data){
		var abnormalForm=new AbnormalForm(this,data);
		$.abnormal=abnormalForm;
		abnormalForm.init();
    };
    
    //FORM对象
	function AbnormalForm(target,data){
		this.target=target;
		this.data=data;
		this.abnType=$( "#abnType" );
		this.equipF=$( "#equipF" );
		this.startTime=$('#startTime');
		this.endTime=$('#endTime');
		this.abnOperations=new AbnOperations(this);
		this.abnEquips=new AbnEquips(this);
		$(this.target).data('abnormal',this);
		return this;
    }
	
	$.extend(AbnormalForm.prototype, {
		init:function(){			
			var $this=this;
			this.endTime.change(function(){
				$this.abnOperations.refreshAbosBody();
			}).blur(function(){
				$this.abnOperations.refreshAbosBody();
			});
			this.form();			
			if(this.data!=null){//编辑
				isEdit=true;
				this.initData();
				$('.type').hide();		
				$('.transType').hide();
				if("F"==this.data.type){	
					abnTypeFlag='F';
					$this.target.attr("action","abnormal-f!save.action");
					$('.f').show();
					$('.o').hide();	
				}else{				
					abnTypeFlag='O';
					$this.target.attr("action","abnormal-o!save.action");
					$('.f').hide();
					$('.o').show();
				}
				$('#reset').click(function(){
					$this.initData();
				});
			}else{//新增
				isEdit=false;
				$('#reset').hide();
				$('#refuse').hide();
				$('#type').tjChange(function(){
					var type=$('#type').tjVal();
					if (type=='F') {
						abnTypeFlag='F';
						$this.target.attr("action","abnormal-f!save.action");
						$('.f').show();
						$('.o').hide();
					} else {
						abnTypeFlag='O';
						$this.target.attr("action","abnormal-o!save.action");
						$('.f').hide();
						$('.o').show();
					}
				});	
				$('#transType').tjChange(function(){
					$this.abnOperations.emptyOperationsBody();
				});					
			}			
		},
		initData:function(){
			$('#abnId').val(this.data.id);
			$('#desc').val(this.data.desc);
			$('#startTime').val(this.data.startTime);
			$('#endTime').val(this.data.endTime);
			$('#processStep').val(this.data.processStep);
			$('#reason').val(this.data.reason);
			$('#reason').val(this.data.reason);
			if("F"==this.data.type){
				$('#equipF').val(this.data.equipF.name);
			}else if("O"==this.data.type){
				$('#abnType').tjText(this.data.abnType);
				this.abnEquips.initData();
			}
			this.abnOperations.initData();
		},
		form:function(){
			this.target.form();
		}
	});
	
	//异态影响业务都情况
    function AbnOperations(abnForm){
    	this.abnForm=abnForm;
		this.target=$('#abnOperations');
		$(this.target).data('abnOperations',this);
		return this;
	}
    
    $.extend(AbnOperations.prototype, {
    	initData:function(){
    		var $this=this;
    		var data=this.abnForm.data;
    		this.emptyOperationsBody();
    		$('#abnOperationsFoot').show();
			if(data!=null&&data.abnOperations.length>0){
				var abnos=data.abnOperations;
				$(abnos).each(function(i){
					$this.addAbnOperation(abnos[i]);
				});
			}
		},
		//清空影响业务列表，重置计数器
		emptyOperationsBody:function(){
    		if($('#abnOperationsBody tr').length!=0){
    			var $abn=$('#abnOperationsBody tr');
    			var abnLength=$abn.length;
    			for(var i=abnLength-1;i>=0;i--){
    				document.getElementById("abnOperationsBody").deleteRow(i);
    			}
    		}
    		$('#abnOperationsBody').init_style();
    		this.abnForm.form();			
    		abnOcount=0;
		},
    	//刷新整个影响业务的列表
    	refreshAbosBody:function (){
    		var $this=this;
    		var startTime = this.abnForm.startTime.val();
    	    var endTime = this.abnForm.endTime.val();
    	    var equipName = this.abnForm.equipF.val();
    	    var transType=$('#transType').tjVal();
    		$("#addAbnOperation").show();
    		 $.post(ctp + '/baseinfo/schedule!getAbnOperationsByEquipAjax.ajax',{equipName:equipName,startTime:startTime,endTime:endTime,transType:transType},function(data){
            	if (data!=undefined) {
            		$this.emptyOperationsBody();
            		var nob=0
        			$.each(data.data, function(i, abnOperation){
        				$this.addAbnOperation(abnOperation);
        				nob++;
        			});
            		if(nob==0){
            			$('#abnOperationsFoot').show();
            		}
        		} else {
        			$('#abnOperationsBody').append('<tr><td colspan="8" align="center">您选择的时间段没有节目信息，请重新选择时间！可以查看<a id="view_2">运行图</a>了解发射机运行信息！</td></tr>');
        		}
        	});
    	},
    	//重新计算时长
    	refreshTime:function (id){
    		var startTime=$('#abnoStartTime'+ id).val();
    		var endTime=$('#abnoEndTime'+ id).val();
    		if(startTime!=""&&startTime!=undefined&&endTime!=""&&endTime!=undefined){
    			$('#abno_proce_time_'+id).text(getTimeHMSstr(getDatemargin(startTime,endTime)));
    		}else{
    			$('#abno_proce_time_'+id).text("");
    		}
    	},

    	//添加一行影响业务详细信息
    	addAbnOperation:function (abnOperation){
    		var $this=this;
    		$('#abnOperationsFoot').hide();
    		var html='';
    		html+='<tr id="abo'+abnOcount+'" class="abo">';
    		html+='<td align="center"><input id="accdFlag' +abnOcount+ '" type="checkbox" name="accdFlags" value="' +abnOcount+ '"/></td>';
    		html+='<td><input id="abnoId' +abnOcount+ '" type="hidden" name="abnoIds" value="0"/>';
    		html+='<input id="operation' +abnOcount+ '" type="text"  zfun="getTransType" ';
    		html+='ztype="auto"	zurl="'+ctp+'/baseinfo/operation!autocompleteTAjax.action?funModule=baseinfo"';
    		html+='  name="abnoNames" verify="业务|NotNull"  style="width:150px" />';
    		html+='</td>';
    		html+='<td><input type="text" id="abnoStartTime' +abnOcount+ '" name="abnoStartTimes" ztype="datetime" verify="开始时间|NotNull&&Datetime&&Times<#abnoEndTime' +abnOcount+ '@结束时间&&Times>=#startTime@故障发生时间" new =true/></td>';
    		html+='<td><input type="text" id="abnoEndTime' +abnOcount+ '" name="abnoEndTimes" ztype="datetime" verify="结束时间|NotNull&&Datetime&&Times>#abnoStartTime' +abnOcount+ '@开始时间&&Times<=#endTime@故障结束时间" new =true/></td>';
    		html+='<td><span id="abno_proce_time_'+abnOcount+'" class="proce_time"><span></td>';
    		html+='<td><a onclick="$.abnormal.abnOperations.delAbnOperation('+abnOcount+')">删除</a></td></tr>';
    		html+='</tr>';
    		$('#abnOperationsBody').append(html);
    		//必须重新建立变量，否则最后执行的时候都是取当时的COUNT值，一般是页面元素已用值后边的一个
    		var i=abnOcount;
    		$('#abnoStartTime' + abnOcount).change(function(){
    			$this.refreshTime(i);
    		}).blur(function(){
    			$this.refreshTime(i);
    		});
    		
    		$('#abnoEndTime' + abnOcount).change(function(){
    			$this.refreshTime(i);
    		}).blur(function(){
    			$this.refreshTime(i);
    		});
    		if(abnOperation!=undefined){
    			var operation=abnOperation.operation;
    			if(abnOperation.id!=undefined){
    				$('#abnoId'+abnOcount).val(abnOperation.id);
    			}
    			if(abnOperation.type=='A'){
    				$('#accdFlag'+abnOcount).attr("checked",true);
    			}
    			$('#abnoStartTime'+abnOcount).val(abnOperation.startTime);
    			$('#abnoEndTime'+abnOcount).val(abnOperation.endTime);
    			$('#operation'+abnOcount).val(operation.name);
    			$('#abno_proce_time_'+abnOcount).text(getTimeHMSstr(getDatemargin(abnOperation.startTime,abnOperation.endTime)));
    		}
    		$('#abnOperationsBody').init_style();
    		this.abnForm.form();
    		abnOcount++;
    	},
    	
    	delAbnOperation:function (id){
    		$('#abo'+id).remove();
    		if($('#abnOperationsBody tr').length==0){
    			$('#abnOperationsFoot').show();
    		}
    		$('#abnOperationsBody').init_style();
    		this.abnForm.form();
    	}
	});
    
  //异态影响设备情况
    function AbnEquips(abnForm){
    	this.abnForm=abnForm;
		this.target=$('#abnEquips');
		$(this.target).data('abnEquips',this);
		return this;
	}
    
    $.extend(AbnEquips.prototype, {
    	initData:function(){
			var $this=this;
			var data=this.abnForm.data;
			this.emptyEquipsBody();
			$('#abnEquipsFoot').show();
			if(data!=null&&data.abnEquips.length>0){
				var abnes=data.abnEquips;
				$(abnes).each(function(i){
					$this.addAbnEquip(abnes[i]);
				});
			}
		},
		//清空影响故障列表，重置计数器
		emptyEquipsBody:function(){
    		$('#abnEquipsBody').empty();
			abnEcount=0;
		},
    	//重新计算时长
    	refreshTime:function (id){
    		var startTime=$('#abneStartTime'+ id).val();
    		var endTime=$('#abneEndTime'+ id).val();
    		if(startTime!=""&&startTime!=undefined&&endTime!=""&&endTime!=undefined){
    			$('#abne_proce_time_'+id).text(getTimeHMSstr(getDatemargin(startTime,endTime)));
    		}else{
    			$('#abne_proce_time_'+id).text("");
    		}
    	},
    	//添加一行影响设备详细信息
    	addAbnEquip:function (abnEquip){
    		var $this=this;
    		$('#abnEquipsFoot').hide();
    		var html='';
    		html+='<tr id="abne'+abnEcount+'" class="abne">';
    		html+='<td align="center"><input id="faultFlag' +abnEcount+ '" type="checkbox" name="faultFlags" value="'+abnEcount+'"/></td>';
    		html+='<td><input id="abneId' +abnEcount+ '" type="hidden" name="abneIds" value="0"/>';
    		html+='<input id="equip' +abnEcount+ '" type="text"  zfun="getTransType"	';
    		html+='ztype="auto"	zurl="'+ctp+'/baseinfo/equip!autocompleteTAjax.action" ';
    		html+=' name="abneNames" verify="设备|NotNull" style="width:150px"/>';
    		html+='</td>';
    		html+='<td><input type="text" id="abneStartTime' +abnEcount+ '" name="abneStartTimes" ztype="datetime" verify="开始时间|NotNull&&Datetime&&Times<#abneEndTime' +abnEcount+ '@结束时间&&Times>=#startTime@故障发生时间" new =true/></td>';
    		html+='<td><input type="text" id="abneEndTime' +abnEcount+ '" name="abneEndTimes" ztype="datetime" verify="结束时间|NotNull&&Datetime&&Times>#abneStartTime' +abnEcount+ '@开始时间&&Times<=#endTime@故障结束时间" new =true/></td>';
    		html+='<td><span id="abne_proce_time_'+abnEcount+'" class="proce_time"><span></td>';
    		html+='<td><a onclick="$.abnormal.abnEquips.delAbnEquip('+abnEcount+')">删除</a></td></tr>';
    		html+='</tr>';
    		$('#abnEquipsBody').append(html);
    		//必须重新建立变量，否则最后执行的时候都是取当时的COUNT值，一般是页面元素已用值后边的一个
    		var j=abnEcount;
    		$('#abneStartTime' + abnEcount).change(function(){
    			$this.refreshTime(j);
    		}).blur(function(){
    			$this.refreshTime(j);
    		});
    		
    		$('#abneEndTime' + abnEcount).change(function(){
    			$this.refreshTime(j);
    		}).blur(function(){
    			$this.refreshTime(j);
    		});
    		if(abnEquip!=undefined){
    			if(abnEquip.id!=undefined){
    				$('#abneId'+abnEcount).val(abnEquip.id);
    			}
    			if(abnEquip.type=='B'){
    				$('#faultFlag'+abnEcount).attr("checked",true);
    			}else{
    				$('#faultFlag'+abnEcount).attr("checked",false);
    			}
    			$('#abneStartTime'+abnEcount).val(abnEquip.startTime);
    			$('#abneEndTime'+abnEcount).val(abnEquip.endTime);
    			$('#equip'+abnEcount).val(abnEquip.equip.name);
    			$('#abne_proce_time_'+abnEcount).text(getTimeHMSstr(getDatemargin(abnEquip.startTime,abnEquip.endTime)));
    		}
    		$('#abnEquipsBody').init_style();
    		this.abnForm.form();
    		abnEcount++;
    	},
    	delAbnEquip:function (id){
    		$('#abne'+id).remove();
    		if($('#abnEquipsBody tr').length==0){
    			$('#abnEquipsFoot').show();
    		}
    		$('#abnEquipsBody').init_style();
    		this.abnForm.form();
    	}
	});    
    
    //根据两个日期字符串获得秒差
	function getDatemargin(startTime , endTime){
		var startDate = new Date(startTime.replace(/-/g,'/'));
		var endDate = new Date(endTime.replace(/-/g,'/'));
		var times = (endDate.getTime() - startDate.getTime())/1000;
		if(times){
			return times;
		}else{
			return 0;
		}
	}
	//根据时分秒字符串得到具体秒数
	 function getTimeSecond(timeStr){
	 	if(timeStr.length == 0){
	 		return 0;
	 	}
	 	var second = 0;	
	 	var hour = parseInt(timeStr.substring(0,(timeStr.length-6)),10);
	 	var min = parseInt(timeStr.substring((timeStr.length-5),(timeStr.length-3)),10);
		var sec = parseInt(timeStr.substring((timeStr.length-2)),10);
	 	second =hour*3600 + min*60 + sec*1;
	 	return second;
	 }
	//根据秒计算时分秒的字符串
	 function getTimeHMSstr(second){
	 	var fu=false;
	 	if(second<0){
			fu=true;
			second=0-second;
		}
	 	var hours = parseInt(second / (60 * 60));
		var minus = parseInt((second - hours * 60 * 60) / 60);
		var secs = parseInt(second - hours * 60 * 60 - minus * 60);
		var timeStr = '';
		if (hours < 10) {
			timeStr = timeStr +'0';
		}
		timeStr = timeStr + hours;
		timeStr = timeStr + ':';
		if (minus < 10) {
			timeStr = timeStr +'0';
		}
		timeStr = timeStr +minus;
		timeStr = timeStr + ':';
		if (secs < 10) {
			timeStr = timeStr +'0';;
		}
		timeStr = timeStr +secs;
		if(fu){
			return "-"+timeStr;
		}else{
			return timeStr;
		}		
	 }
})(jQuery);
//根据两个日期字符串获得秒差
function getDatemargin(startTime , endTime){
	var startDate = new Date(startTime.replace(/-/g,'/'));
	var endDate = new Date(endTime.replace(/-/g,'/'));
	var times = (endDate.getTime() - startDate.getTime())/1000;
	if(times){
		return times;
	}else{
		return 0;
	}
}