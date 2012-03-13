<html>
<head>
<style  type="text/css" >
	.uploadImg{ overflow:hidden; position:absolute}
	.delLoadImg{ vertical-align:middle; cursor:hand}
	.file{position:absolute; z-index:100; margin-left:-180px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;height:26px;}
</style>
<script type="text/javascript" language="javasrcipt">  
        var rFlag='${formMap.rFlag}';
        function add(){
        	document.getElementById("ifBackFlag").value="1";           	
        	submForm('确定审核通过?');        	
        }
        function back(){
        	document.getElementById("ifBackFlag").value="0";
        	submForm('确定驳回?');
        }
        function submForm(msg){
	        if (confirm(msg)) {
	            //添加数据处理提示信息sbButton
	            document.getElementById("sbButton").disabled=true;
            	document.getElementById("sbButtonB").disabled=true;
	        	document.getElementById("techAccidentForm").submit();
	        }        	
        }
        $().ready(function(){
        	
        })        
</script>
</head>
<body>
<div class="formTitle2"><span>1、事故基本信息</span> </div>
<form id="techAccidentForm" method="post" ztype="form" action="tech-accident!updateTechAccident.action">	
	<input type='hidden' id="workflowId" name='formMap.workflowId' value='${formMap.workflowId}'/>
	<input type='hidden' id="ifBackFlag" name='formMap.ifBackFlag' value='0'/>	
	<input type='hidden' id="rFlag" name='formMap.rFlag' value='${formMap.rFlag}'/>		
	<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="transType">
		<td>是否结束：</td>
		<td colspan="3">
			<#if formMap.techAccident.endFlag=='N'>
			否
			</#if>
			<#if formMap.techAccident.endFlag=='Y'>
			是
			</#if>
		</td>
	</tr>
	<tr>
		<td>开始时间：</td>
		<td>${formMap.techAccident.startTime}</td>
		<td>结束时间：</td>		
		<td>${formMap.techAccident.endDate}</td>
	</tr>
	<tr>
		<td>事故性质：</td>		
		<td>${formMap.accdKindShow}</td>
		<td>事故位置：</td>
		<td>${formMap.techAccident.happenLocation}</td>		
	</tr>
	<tr>
		<td colspan="1">责任人：</td>		
		<td colspan="3">${formMap.techAccident.dutyPerson}</td>		
	</tr>
	<tr>
		<td colspan="1">预防措施：</td>
		<td colspan="3">${formMap.techAccident.accdPrev}</td>
	</tr>
	<tr>
		<td colspan="1">事故原因：</td>		
		<td colspan="3">${formMap.techAccident.accdReason}</td>
	</tr>
	<tr>
		<td colspan="1">事故经过：</td>		
		<td colspan="3">${formMap.techAccident.accdCourse}</td>
	</tr>
	<tr>
		<td colspan="1">事故处理情况：</td>		
		<td colspan="3">${formMap.techAccident.accdManage}</td>
	</tr>
	<tr>
		<td colspan="1">事故结果：</td>		
		<td colspan="3">${formMap.techAccident.accdResult} &nbsp;</td>
	</tr>
	<tr>
		<td colspan="1">附件：</td>		
		<td colspan="3">&nbsp;
		<#list formMap.techAccident.techAccidentMedias as techAccidentMedias>
		<a style="margin-left:7px;"   href="${request.contextPath}/abnormal/tech-accident!downLoadOne.action?showName=${techAccidentMedias.fileName}&saveName=${techAccidentMedias.saveName}">${techAccidentMedias.fileName}</a>
		</#list>		
		</td>
	</tr>			
	</table>
</form>	
<div class="formTitle2">
<span>
2、以上信息确认无误后点击【审核通过】上报事故。
</span> 
</div>	
<div align="center">
	<input id="sbButton" type="button" onclick="add();" value="审核通过" ztype="button"/>&nbsp;&nbsp;
	<input id="sbButtonB" type="button" onclick="back();" value="驳回" ztype="button"/>
</div>
<div class="formTitle2"><span>3、历史流转信息 </span></div>
<#list formMap.comments as com>
	<div class="killwangjian">${com}</div>
</#list>	
</body>
</html>
