<html>
<head>
<script language="JavaScript" type="text/javascript">
$(document).ready(function(){	
	<#if (abnormal.accdCode?exists)>
		$("#tab2").tab(1,0,410,1,0);
	<#else>
		$("#tab2").tab(1,0,410,0,0);	
	</#if>
	var data=${abnormal.jsonObject};
	$('#desc').text(data.desc);
	$('#startTime').text(data.startTime);
	$('#endTime').text(data.endTime);
	$('#processStep').text(data.processStep);
	$('#reason').text(data.reason);
	if("undefined"==(data.type+"")||"B"==data.type){
		$('.f').show();
		$('.o').hide();
		$('#equipF').text(data.equipB_name);
		$('#abnEquipDetails').hide();	
		$('#abnOperationDetails').hide();
		$('#abnLabel').hide();		
	}else{
		if("F"==data.type){
			$('.f').show();
			$('.o').hide();	
			$('#equipF').text(data.equipF.name);
			$('#abnEquipDetails').hide();		
		}else{
			$('.f').hide();
			$('.o').show();
			$('#abnType').text(data.abnType);	
			if(data.abnEquips.length>0){
				var abnes=data.abnEquips;
				$(abnes).each(function(i){
					addAbnEquip(abnes[i]);
				});
			}		
		}
		if(data.abnOperations.length>0){
			var abnos=data.abnOperations;
			$(abnos).each(function(i){
				addAbnOperation(abnos[i]);
			});
		}		
	}
});	
function addAbnEquip(abnEquip){
	var html='';
	html+='<tr>';
	if(abnEquip.type=='B'){
		html+='<td>是</td>';
	}else{
		html+='<td>否</td>';
	}	
	html+='<td>'+abnEquip.equip.name+'</td>';
	html+='<td>'+abnEquip.startTime+'</td>';
	html+='<td>'+abnEquip.endTime+'</td>';
	html+='<td>'+getTimeHMSstr(getDatemargin(abnEquip.startTime,abnEquip.endTime))+'</td>';
	html+='</tr>';
	$('#abnEquipsBody').append(html);
	$('#abnEquipsBody').init_style();
}
function addAbnOperation(abnOperation){
	var operation=abnOperation.operation;
	var html='';
	html+='<tr>';
	if(abnOperation.type=='A'){
		html+='<td>是</td>';
	}else{
		html+='<td>否</td>';
	}	
	html+='<td>'+operation.name+'</td>';
	html+='<td>'+abnOperation.startTime+'</td>';
	html+='<td>'+abnOperation.endTime+'</td>';
	html+='<td>'+getTimeHMSstr(getDatemargin(abnOperation.startTime,abnOperation.endTime))+'</td>';
	html+='</tr>';
	$('#abnOperationsBody').append(html);
	$('#abnOperationsBody').init_style();
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
</script>
<style type="text/css">
	.o {display: none;}
</style>
<style>
.tb_title,.tb_box {
	
}

.tb_title,.tb_box {
	margin: 0;
	padding: 0;
	list-style-type: none;
	clear: both;
	border-right: 1px #ccc solid;
	height: 30px;
}

.tb_title {
	background: #f90;
	color: #fff;
	border-bottom: 1px #ccc solid;
	border-top: 1px #ccc solid;
}

.tb_title li {
	float: left;
	border-left: 1px #ccc solid;
	text-align: center;
	height: 30px;
	line-height: 30px;
	font-weight: bold;
}

.tb_box li {
	float: left;
	border-left: 1px #ccc solid;
	text-align: center;
	height: 30px;
	line-height: 30px;
}

.tb_body {
	clear: both;
}

.accdDutyTimes {
	border-bottom: 1px #ccc solid;
	border-left: 1px #ccc solid;
	border-right: 1px #ccc solid;
	background: #eee;
}

.accdDutyTime {
	padding: 3px;
	border-top: 1px #ccc solid;
}

.tb_tit_01 {
	width: 140px;
}

.tb_tit_02 {
	width: 160px;
}

.tb_tit_03 {
	width: 160px;
}

.tb_tit_04 {
	width: 120px;
}

.tb_tit_05 {
	width: 120px;
}

.tb_box_01 {
	width: 140px;
}

.tb_box_02 {
	width: 160px;
}

.tb_box_03 {
	width: 160px;
}

.tb_box_04 {
	width: 120px;
}

.tb_box_05 {
	
}

.tb_body .even {
	background: #f3f8fd;
}

.tb_body .odd {
	background: #fff;
}
</style>
</head>
<body>
    <!-- 导航结束 -->
    <!-- 标签开始 -->
    <div class="tab" id="tab2">
        <ul> 
            <li>故障信息</li>
            <#if (abnormal.accdCode?exists)>
            <li>事故信息</li>
            </#if>
        </ul>
        <div>
			<div id="abnormalB-show">
				<div class="formTitle2"><span>1、故障信息:</span> </div>
				<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="f">故障设备：</td>
						<td colspan="3" class="f" id="equipF"></td>
						<td class="o">异态类型：</td>
						<td colspan="3" class="o" id="abnType"></td>
					</tr>
					<tr>
						<td>故障发生时间：</td>
						<td id="startTime"></td>
						<td>故障结束时间：</td>
						<td id="endTime"></td>
					</tr>
					<tr>
						<td colspan="1">故障现象：</td>
						<td colspan="3" id="desc"></td>
					</tr>
					<tr>
						<td colspan="1">故障原因：</td>
						<td colspan="3" id="reason"></td>
					</tr>
					<tr>
						<td colspan="1">处理措施：</td>
						<td colspan="3" id="processStep"></td>
					</tr>					
				</table>				
				<div class="formTitle2"><span>2、影响业务:</span> </div>
				<table id='abnOperationDetails' class="work_info_table" width="100%"
					border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<th width="100px">是否停传</th>
							<th>业务</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>时长</th>
						</tr>
					</thead>
					<tbody id='abnOperationsBody'>
					</tbody>
				</table>
				<div  id='abnEquipDetails'>
					<div class="formTitle2"><span>3、影响设备:</span> </div>
					<table class="work_info_table" width="100%"
						border="0" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th width="100px">是否引起故障</th>
								<th>设备</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>时长</th>
							</tr>
						</thead>
						<tbody id='abnEquipsBody'>
						</tbody>
					</table>
				</div>													
			</div>
        </div>
        
        <#if (abnormal.accdCode?exists)>
       	<div>
			<div id="abnormalF-show">	
				<div class="formTitle2"><span>1、事故信息:</span> </div>			
				<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="120">事故编号：</td>
						<td colspan='3'>${abnormal.accdCode}</td>
					</tr>
					<tr>
						<td>事故开始时间：</td>
						<td>${abnormal.startTime}</td>
						<td width="120">事故结束时间：</td>
						<td>${abnormal.endTime}</td>
					</tr>
					<tr>
						<td>处理时长：</td>
						<td>${abnormal.processTimeSumStr}</td>
						<td>停劣播时长：</td>
						<td>${abnormal.shutTimeSumStr}</td>
					</tr>
				
					<tr>
						<td>事故描述：</td>
						<td colspan="3">${abnormal.accdDescStr}</td>
					</tr>
					<tr>
						<td>事故简况：</td>
						<td colspan="3">${abnormal.accdReasonStr}</td>
					</tr>
					<tr>
						<td>预防措施：</td>
						<td colspan="3">${abnormal.accdPrevWayStr}</td>
					</tr>
				</table>				
				<div class="formTitle2"><span>2、影响业务:</span> </div>			
				<table class="work_info_table"  width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr >					
					<th>业务</td>
					<th>开始时间</td>
					<th>结束时间</td>
					<th>停播时长</td>
					<th>定性</th>
				</tr>					
				<#list abnormal.abnOperationAs as abnOperationA>
				<tr>					
					<td>${abnOperationA.operation.name}</td>
					<td>${abnOperationA.startTime}</td>
					<td>${abnOperationA.endTime}</td>
					<td>${abnOperationA.shutTimeStr}</td>
					<td style="word-wrap:break-word;">
						<#if (abnOperationA.accdDutyTimes?exists)&&((abnOperationA.accdDutyTimes).size()>1)>
							<#list abnOperationA.accdDutyTimes as accdDutyTime>
					    		${accdDutyTime.accdDuty.codeDesc}:${accdDutyTime.dutyTimeStr};
					    	</#list>	
					    <#else>
					    &nbsp;						
						</#if>
			    	</td>
				</tr>
				</#list>
				</table>				
        </div>
    </div>
    </#if>
    </div>
	<div align="right">
	<input id="changeAt" type='button' value='关闭' ztype="button" onclick="$('#dialogDiv').dialog('close');" />&nbsp;&nbsp;&nbsp;
	</div>	    
    <!-- 标签结束 -->
</body>
</html>
