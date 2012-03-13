var abnTcount=0;
function validateTime(){
//	var zTime=0;
//	$('.accdTimeLongs').each(function(){
//		zTime+=getTimeSecond($(this).val());
//	});
//	var allTime=getTimeSecond($('#allTime').text());
//	
//	if(zTime==allTime){
//		return true;
//	}else{
//		return "����Ŀͣ��ʱ���ܺ�Ӧ������ʱ��:"+$('#allTime').text();
//	}
	alert(1);
}
var subFlagP=true;
function submintFun(){
	if ($('#accdForm').data('tjForm').checkSubmit()) {
		if(subFlagP){
			//判断所有定性时长之和等于个字影响业务的时长
			//获取每个业务的时间长度
			//获取每个定性单独的时间长度	
			var dutyTimeOs=document.getElementsByName("dutyTimeO");//存放业务总时长
			var sucFlag=true;			
			if(dutyTimeOs&&(dutyTimeOs!=null)&&(dutyTimeOs.length!=0)){					
				for(var i=0;i<dutyTimeOs.length;i++){
					var timeO=getTimeStrByHMS(dutyTimeOs[i].value);						
					var objOid=((dutyTimeOs[i].id).split('dutyTimeO'))[1];	
					var dutyTimeOTs=document.getElementsByName("dutyTimeOT"+objOid);//存放dutyTime的id
					var dutyTimeOTValue=0;
					if(dutyTimeOTs&&dutyTimeOTs!=null){							
						for(var j=0;j<dutyTimeOTs.length;j++){
							 var dutyTimeOT=document.getElementById(dutyTimeOTs[j].value).value;
							//时间累加
							dutyTimeOTValue=dutyTimeOTValue+getTimeStrByHMS(dutyTimeOT);								
						}							
					}
					//判断dutyTimeOTValue和dutyTimeOs的值是否相等
					if(dutyTimeOTValue!=timeO){
						sucFlag=false;
						break;
					}
				}
			}else{
				tjAlert("停传详细信息不能为空！");
				return false;	
			}
			if(!sucFlag){
				tjAlert("各停传时长之和必须等于对应业务时长！");
			}else{
				subFlagP=false;
				tjConfirm("确定提交审核？",function(flag){
					if (!flag){	
						subFlagP=true;
						return;
					}else{
						$('#accdForm').submit();											
					}
				});					
			}						
		}
	}else{
		return false;					
	}			
}
//根据字符串计算时分秒的
function getTimeStrByHMS(timeStr){
	var timeArr=timeStr.split(":");
 	var hours = parseInt(timeArr[0]);
	var minus = parseInt(timeArr[1]);
	var secs = parseInt(timeArr[2]);
	var rtnValue=secs+minus*60+hours*60*60;
	return rtnValue;
}
(function($) {
	
	/*以下是调用.form方法，为form统一创建验证*/
	$.fn.accd = function(data,stCode,curTime){
		var accdForm=new AccdForm(this,data,stCode,curTime);
		$.accd=accdForm;
		accdForm.init();
    };
    
    //FORM对象
	function AccdForm(target,data,stCode,curTime){
		this.target=target;
		this.data=data;
		this.stCode=stCode;
		this.curTime=curTime;
		this.accdOperations=new AccdOperations(this);
		$(this.target).data('accd',this);
		return this;
    }
	
	$.extend(AccdForm.prototype, {
		init:function(){
			var $this=this;
			if(this.data!=null){
				this.initData();
			}			
		},
		initData:function(){
			$('#id').val(this.data.id);
			if(this.data.accdCode==undefined){
				$('#accdCode').val(this.createAccdCode());
				$('#accdCodeName').text(this.createAccdCode());
			}else{
				$('#accdCode').val(this.data.accdCode);
				$('#accdCodeName').text(this.data.accdCode);
			}
			
			$('#accdStartTime').text(this.data.startTime);
			$('#accdEndTime').text(this.data.endTime);
			$('#accdProcessTimeSumStr').text(this.data.processTimeSumStr);
			$('#accdShutTimeSumStr').text(this.data.shutTimeSumStr);
			//如果是上报则显示故障相关信息，如果是重新上报，显示事故相关信息
			if(this.data.accdDesc==undefined){
				$('#accdDesc').val(this.data.desc);
			}else{
				$('#accdDesc').val(this.data.accdDesc);
			}
			if(this.data.accdPrevWay==undefined){
				$('#accdPrevWay').val(this.data.processStep);
			}else{
				$('#accdPrevWay').val(this.data.accdPrevWay);
			}
			if(this.data.accdReason==undefined){
				$('#accdReason').val(this.data.reason);
			}else{
				$('#accdReason').val(this.data.accdReason);
			}
			this.accdOperations.initData();
		},
		//生成事故编号
		createAccdCode:function(){
			var accdCode = '';
			var str = this.curTime;
			var dateTemp = new Date(str.replace(/-/g,'/'));
			var year = dateTemp.getYear();
			var month = dateTemp.getMonth()+1;
			var day = dateTemp.getDate();
			var hours = dateTemp.getHours();
			var minu = dateTemp.getMinutes();
			var sec = dateTemp.getSeconds();
			//针对724台站设置
			accdCode = this.stCode + year + fillZero(month)+ fillZero(day)
						+fillZero(hours)+fillZero(minu) + fillZero(sec);
			
			return accdCode;
		},
		form:function(){
			this.target.form();
		}
	});
	
	//异态影响业务都情况
    function AccdOperations(accdForm){
    	this.accdForm=accdForm;
		this.target=$('#accdOperations');
		$(this.target).data('accdOperations',this);
		return this;
	}
    
    $.extend(AccdOperations.prototype, {
    	initData:function(){
    		var $this=this;
    		var data=this.accdForm.data;
    		$('#accdOperationsBody').empty();
    		$('#accdOperationsFoot').show();
			if(data!=null&&data.abnOperations.length>0){
				var abnos=data.abnOperations;
				$(abnos).each(function(i){
					$this.addAccdOperation(abnos[i]);
				});
			}
		},

    	//添加一行影响业务详细信息
		addAccdOperation:function (abnOperation){
    		var $this=this;
    		if(abnOperation.type=='A'){
    			var html='';
        		html+='<div class="tb_body">';
        		html+='<ul class="tb_box"><li class="tb_box_01">'+abnOperation.operation.name+'</li><li class="tb_box_02">'+abnOperation.startTime+'</li><li class="tb_box_03">'+abnOperation.endTime+'</li><li class="tb_box_04">'+getTimeHMSstr(getDatemargin(abnOperation.startTime,abnOperation.endTime))+'</li><li class="tb_box_05"><input id="add'+abnOperation.id+'"  type="button" ztype="button" value="添加定性信息"/></li></ul>';
        		html+='';
        		html+='<input id="dutyTimeO'+abnOperation.id+'" name="dutyTimeO"  type="hidden" value="'+getTimeHMSstr(getDatemargin(abnOperation.startTime,abnOperation.endTime))+'" />';
        		html+='<div id="accdDutyTimes'+abnOperation.id+'" class="accdDutyTimes">';
        		html+='</div>';
        		$('#accdOperationsBody').append(html);
        		if(abnOperation.accdDutyTimes.length>0){
    				var accdDutyTimes=abnOperation.accdDutyTimes;
    				$(accdDutyTimes).each(function(i){
    					var accdDutyTime=accdDutyTimes[i];
    					$this.addAccdDutyTime(abnOperation.id, accdDutyTime);
    				});
    			}
        		var k= abnOperation.id;
        		$('#add' + abnOperation.id).click(function(){
        			$this.addAccdDutyTime(k);
        		});
        		$('#accdOperationsBody').init_style();
        		this.accdForm.form();
    		}
    	},
    	
    	//添加一行影响业务详细信息
		addAccdDutyTime:function (abnOperationId,accdDutyTime){
    		var $this=this;
			var html='';
			html+='<div id="accdDutyTime'+abnTcount+'" class="accdDutyTime">';
			html+='<input id="abnOperationId'+abnTcount+'" name="abnOperationIds"  type="hidden" val=""/>';
			html+='<input id="dutyTimeId'+abnTcount+'" name="dutyTimeIds"  type="hidden" val="" />';
			
			html+='<input id="dutyTimeIdH'+abnTcount+'" name="dutyTimeOT'+abnOperationId+'" type="hidden" value="dutyTime'+abnTcount+'" />';
			
			html+='<table class=""><tr><td>';			
			html+='性质：';
			html+='</td><td>';
    		html+='<div id="accdDuty'+abnTcount+'" name="accdDutys"  ztype="select" verify="NotNull">';
    		html+='<ul>';
    		for(var i=0;i<accdDutyArray.length;i++){
    			if((accdDutyTime!=undefined)&&(accdDutyArray[i][0]==(accdDutyTime.accdDuty.id+''))){
    				html+='<li val="'+accdDutyArray[i][0]+'" selected="selected">'+accdDutyArray[i][1]+'</li>';	
    			}else{
    				html+='<li val="'+accdDutyArray[i][0]+'">'+accdDutyArray[i][1]+'</li>';	
    			}
    		}
    		html+='</ul>'; 
    		html+='</div>';
    		html+='</td><td>';
    		html+='停传时长：';
    		html+='</td><td>';
    		html+='<input id="dutyTime'+abnTcount+'" name="dutyTimes" class="dutyTimes'+abnOperationId+'"  type="text" ztype="text" verify="停传时长|NotNull&&TimeL"/>';
    		html+='</td><td>';
    		html+='<input id="del'+abnTcount+'" type="button" ztype="button" value="删除"/>';
    		html+='</td></tr></table>';
    		html+='</div>';
    		$('#accdDutyTimes'+abnOperationId).append(html);
    		var l= abnTcount;
    		$('#del' + abnTcount).click(function(){
    			$this.delAccdDutyTime(l);
    		});
    		$('#abnOperationId'+abnTcount).val(abnOperationId);
    		if(accdDutyTime!=undefined){
    			$('#dutyTimeId'+abnTcount).val(accdDutyTime.id);
    			$('#dutyTime'+abnTcount).val(accdDutyTime.dutyTimeStr);
    		}else{
    			$('#dutyTimeId'+abnTcount).val(0);
    		}
    		$('#accdOperationsBody').init_style();
    		this.accdForm.form();
    		abnTcount++;
    	},
    	delAccdDutyTime:function (id){
    		$('#accdDutyTime'+id).remove();
    		$('#accdOperationsBody').init_style();
    		$('#accdForm').form();
    	}
	});
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
	 
	//用'0'补位
	function fillZero(str){
		if(str<10){
			return '0'+ str;
		}else{
			return str;
		}
	}
})(jQuery);