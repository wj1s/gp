<html>
<head>
<style  type="text/css" >
	.uploadImg{ overflow:hidden; position:absolute}
	.delLoadImg{ vertical-align:middle; cursor:hand}
	.file{position:absolute; z-index:100; margin-left:-180px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;height:26px;}
</style>
<script type="text/javascript" language="javasrcipt">    
</script>
</head>
<body>
<div class="formTitle2"><span>1、事故基本信息</span> </div>
<form id="techAccidentForm" method="post" ztype="form" action="tech-accident!insertTechAccident.action">	
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
<div class="formTitle2"><span>2、处理结果 历史记录</span> </div>
                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
                    <tr>
                        <th>处理结果</th>
                        <th width="80px">更新人</th>
                        <th width="140px">更新时间</th>
                    </tr>
					<#list formMap.techAccident.techAccidentHs as techAccidentH>						
                        <tr>
                            <td align="left">
                                ${techAccidentH.accdResult}&nbsp;
                            </td>
                            <td width="80px">${techAccidentH.updName}&nbsp;
                            </td>
                            <td  width="140px">
                                ${techAccidentH.updDate?string("yyyy-MM-dd hh:mm:ss")}&nbsp;
                            </td>
                        </tr>						
					</#list>                    
                </table>		
	<div align="right">
	<input id="changeAt" type='button' value='关闭' ztype="button" onclick="$('#dialogDiv').dialog('close');" />&nbsp;&nbsp;&nbsp;
	</div>	
</body>
</html>
