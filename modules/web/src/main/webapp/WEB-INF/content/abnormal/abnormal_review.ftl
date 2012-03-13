<html>
<head>
<script language="JavaScript" type="text/javascript">
	var subFlag=true;
	$(document).ready(function(){
		$('#accdForm').form();		
	});
	function audit(isPass){
		if ($('#accdForm').data('tjForm').checkSubmit()) {
			if(subFlag){
				subFlag=false;
				var msg='确定通过审核？';
				if(!isPass){
					msg='确定驳回审核？';
					$('#isPass').val(0);
				}				
				tjConfirm(msg,function(flag){
					if (!flag){	
						subFlag=true;
						return;
					}else{
						$('#accdForm').submit();										
					}
				});					
			}
		}
	}
</script>
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
	width: 280px;
	white-space:nowrap;
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
	width: 280px;
	white-space:nowrap;
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
	width: 220px;
	white-space:nowrap;
	
}

.tb_body .even {
	background: #f3f8fd;
}

.tb_body .odd {
	background: #fff;
}
.killwangjian{height:22px; line-height:22px;}
</style>
</head>
<body>
<div class="formTitle2"><span>1、事故信息</span></div>
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
<div class="formTitle2"><span>2、确认停传详细信息</span></div>
<div id="title">
<ul class="tb_title">
	<li class="tb_tit_01">影响业务</li>
	<li class="tb_tit_02">开始时间</li>
	<li class="tb_tit_03">结束时间</li>
	<li class="tb_tit_04">停播时长</li>
	<li class="tb_tit_05">定性</li>
</ul>
<div class="tb_body">
	<#list abnormal.abnOperationAs as abnOperationA>
	<ul class="tb_box" style="border-bottom:1px #ccc solid;border-right:1px #ccc solid;">
		<li class="tb_box_01">${abnOperationA.operation.name}</li>
		<li class="tb_box_02">${abnOperationA.startTime}</li>
		<li class="tb_box_03">${abnOperationA.endTime}</li>
		<li class="tb_box_04">${abnOperationA.shutTimeStr}</li>
		<li class="tb_box_05">
			<#list abnOperationA.accdDutyTimes as accdDutyTime>
	    		${accdDutyTime.accdDuty.codeDesc}:${accdDutyTime.dutyTimeStr};
	    	</#list>
    	</li>
	</ul>
	</#list>
</div>
<div class="formTitle2"><span>3、历史流转信息 </span></div>
<#list comments as com>
	<div class="killwangjian">${com}</div>
</#list>
<form id='accdForm' name="accdForm" action="abnormal!audit.action" method='post'>
	<input type="hidden" id="id" name="id" value="${abnormal.id}" />
	<input type="hidden" id="taskId" name="taskId" value="${taskId}" />
	<input type="hidden" id="isPass" name="isPass" value="1" />
	<div class="formTitle2"><span>审核意见</span></div>
	<fieldset>
	<textarea name="comment" id="comment" rows="3" length="300" autosize="true" style="width: 400px;"
	verify="审核意见|NotNull&&Length<300" ztype="textarea">审核通过</textarea>
	<br/>
	<input id="changeAt" type='button'
	value='通过' ztype="button"
	onclick="audit(true);" />
	<input id="changeAt" type='button'
	value='驳回' ztype="button"
	onclick="audit(false);" />
	</fieldset>
</form>
</body>
</html>
