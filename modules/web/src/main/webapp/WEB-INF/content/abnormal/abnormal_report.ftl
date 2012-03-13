<#include "lib/abnormal-temp.ftl">
<html>
<head>
<script type="text/javascript"
	src="${request.contextPath}/js/abnormal/abnormal_input.js"></script>
<script type="text/javascript"
	src="${request.contextPath}/js/abnormal/abnormal_report.js"></script>
<script language="JavaScript" type="text/javascript">
	var accdDutyArray=new Array();
	$(document).ready(function(){
		$('#accdForm').form();	
	 	$('#dialog_list').dialog({
		 	title:'编辑异态信息',
			resizable: false,
			autoOpen: false,
			height: 'auto',
			width: 800,
			modal: true
		});	
		var data=null;
		<#if abnormal?exists>
			data=${abnormal.jsonObject};
		</#if>
		<#if allAccdDuty?exists>
			<#list allAccdDuty as allAccdDuty>
				var temArry=new Array();
				temArry[0]="${allAccdDuty.id}";
				temArry[1]="${allAccdDuty.codeDesc}";
				accdDutyArray[${allAccdDuty_index}]=temArry;				
			</#list>
		</#if>		
		$('#abnormalForm').abnormal(data);
		$('#accdForm').accd(data,"${station.code}","${curDate}");
	});	
	//form提交回调函数
	function onSuccess(result){
		if (result['result'] == false){
			tjAlert("操作失败！");
		}else {
			$('#abnormalForm').abnormal(result);
			$('#accdForm').accd(result,"${station.code}","${curDate}");
			$('#dialog_list').dialog('close');
		}
	}
</script>
<style>
	.tb_title,.tb_box{}
	.tb_title,.tb_box{
		margin:0;padding:0;
		list-style-type: none; 
		clear:both;
		border-right:1px #ccc solid;
		height:30px;
		}
	.tb_title{
		background:#f90;
		color:#fff;
		border-bottom:1px #ccc solid;
		border-top:1px #ccc solid;
		}
	.tb_title li{
		float:left; 
		border-left:1px #ccc solid;
		text-align:center;
		height:30px;line-height:30px;
		font-weight: bold;
		}
	.tb_box li{
		float:left; 
		border-left:1px #ccc solid;
		text-align:center;
		height:30px;line-height:30px;
		}
	.tb_body{clear:both;}
	.accdDutyTimes{
		border-bottom:1px #ccc solid;
		border-left:1px #ccc solid;
		border-right:1px #ccc solid;
		background:#eee;
		}
	.accdDutyTime{
		padding:3px;
		border-top:1px #ccc solid;
		}
	
	.tb_tit_01{width:280px;}
	.tb_tit_02{width:160px;}
	.tb_tit_03{width:160px;}
	.tb_tit_04{width:120px;}
	.tb_tit_05{width:120px;}
	.tb_box_01{width:280px;white-space:nowrap;}
	.tb_box_02{width:160px;}
	.tb_box_03{width:160px;}
	.tb_box_04{width:120px;}
	.tb_box_05{width:120px;}
	
	/*
	.tb_body .even{background: #f3f8fd;}
	.tb_body .odd{background: #fff;}
	*/
	

</style>
</head>
<body>
<form id='accdForm' name="accdForm" action="abnormal!save.action" method='post'>
<div align="right">
	<input id="changeAt" type='button' value='编辑异态信息' ztype="button" onclick="$('#dialog_list').dialog('open');" /> 
	<input  type="button"  value="提交领导审核" ztype="button" onclick="submintFun();"/>
	<a ztype="popUp"  title="备注" url="${request.contextPath}/abnormal/abnormal!refuseInput.ajax?taskId=${taskId}&id=${id}" zwidth="350" zheight="200"><input id="refuse" type="button" value="非事故异态" ztype="button" /></a>
</div>
<div class="formTitle2"><span>1、填写事故的主要信息</span></div>
<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<th colspan="4">录入事故信息</th>
	</tr>
	<tr>
		<td width="120">事故编号：</td>
		<td colspan='3'><span id='accdCodeName'></span> <input
			type='hidden' id='accdCode' name='accdCode' value='' /><input
			type='hidden' id='id' name='id' value='' />
			<#if taskId?exists>
				<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
			</#if>
		</td>
	</tr>
	<tr>
		<td>事故开始时间：</td>
		<td><span id="accdStartTime"></span></td>
		<td width="120">事故结束时间：</td>
		<td><span id="accdEndTime"></span></td>
	</tr>
	<tr>
		<td>处理时长：</td>
		<td><span id='accdProcessTimeSumStr'></span></td>
		<td>停劣播时长：</td>
		<td><span id='accdShutTimeSumStr'></span></td>
	</tr>

	<tr>
		<td>事故描述：</td>
		<td colspan="3"><textarea name="accdDesc" id="accdDesc" cols="80"
			rows="2" length="300" autosize="true" verify="事故描述|NotNull&&Length<300" ztype="textarea"></textarea></td>
	</tr>
	<tr>
		<td>事故简况：</td>
		<td colspan="3"><textarea name="accdReason"  id="accdReason"
			cols="80" rows="2" length="300" autosize="true"
			verify="事故简况|NotNull&&Length<300" ztype="textarea"></textarea></td>
	</tr>
	<tr>
		<td>预防措施：</td>
		<td colspan="3"><textarea name="accdPrevWay" id="accdPrevWay"
			cols="80" rows="2" length="300" autosize="true" verify="预防措施|NotNull&&Length<300" ztype="textarea"></textarea></td>
	</tr>
</table>
<div class="formTitle2"><span>2、确认停传详细信息</span></div>
<div id="title">
	<ul class="tb_title"><li class="tb_tit_01">业务</li><li class="tb_tit_02">开始时间</li><li class="tb_tit_03">结束时间</li><li class="tb_tit_04">时长</li><li class="tb_tit_05">操作</li></ul>
</div>
<div id="accdOperationsBody">
</div>
</form>
<div id="dialog_list" style="display: none">
	<@abnormaltemp editInJb="1"/>
</div>
<div class="formTitle2"><span>3、历史流转信息</span> </div>
<#list comments as com>
	<div align="left">${com}</div>
</#list>
</body>
</html>
