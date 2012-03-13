<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('input[type="checkbox"]').checkbox();

				//由异态引起的故障
				var colModel=[{name:'id',header:"id",sortable:true,hidden:true,editable:false},				               
							   {name:'startTime',header:"开始时间",sortable:false,editable:true,verify:"NotNull",dateFmt:'date',now:'endnow'},
							   {name:'endTime',header:"结束时间",sortable:false,editable:true,verify:"NotNull",dateFmt:'date',now:'endnow'},				               
				               {name:'transType',header:"传输类型",sortable:false,editable:true,verify:"NotNull"},				               
							   {name:'desc',header:"异态现象",sortable:false,editable:true,verify:"NotNull"},
							   {name:'reason',header:"异态原因",sortable:false,editable:true,verify:"NotNull"},
							   {name:'processStep',header:"处理措施",sortable:false,editable:true,verify:"NotNull"},
							   {name:'accdCode',header:"事故编号",sortable:false,editable:true,verify:"NotNull"},
							   {name:'accdPrevWay',header:"事故预防措施",sortable:false,editable:true,verify:"NotNull"}];										          										   						          						
				var	loadUrl = '${request.contextPath}/abnormal/abnormal!loadForZeroInfo.action?start=${reptGroup.zeroReptDtl.startTime?string("yyyyMMddHHmmss")}&end=${reptGroup.zeroReptDtl.endTime?string("yyyyMMddHHmmss")}';		
				$('#abnormal-show').grid({name:'故障列表',colModel:colModel,gridType:'onlyCheck',
					viewUrl:{content:'${request.contextPath}/abnormal/abnormal!detail.ajax',width:'950px'},
					loadUrl:{content:loadUrl},operation:{add:false,edit:false,del:false},viewDetail:{view:true}});						          			   

				$('#return').click(function(){
					var thUrl = '${request.contextPath}/report/rept!report.action';
					window.location.href = thUrl;
			  	});
			});

			function disableAndSubmit(obj){
				$(obj).attr('disabled','disabled');
				$('#myForm').submit();
		  	}
		</script>
	</head>
	<body>
		<form action="${request.contextPath}/report/zero-reporting!submit.action" method="post" id="myForm">
			<input type="hidden" name="reptTime" value="${reptGroup.reptTime}"/>
			<#if reAuditFlag?exists>
				<input type="hidden" name="reAuditFlag" value="${reAuditFlag}"/>
			</#if>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
				<tr>
					<th colspan="5">
						<strong>&lt;&lt;${reptGroup.reptGroupName}&gt;&gt;</strong>
					</th>
				</tr>
				<tr>
					<td colspan="2" align="right">
						时间范围：
					</td>
					<td colspan="3" align="left">
						${time}
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						是否有事故发生：
					</td>
					<td colspan="3" align="left">
						<#if !preview?exists>
							<#if reAuditFlag?exists && (reAuditFlag=='1')>
								<#if accdFlag=='Y'>
									<input id="accdFlag" type="checkbox" checked="checked" name="accdFlag" />
								</#if>
								<#if accdFlag=='N'>
									<input id="accdFlag" type="checkbox" name="accdFlag" />
								</#if>
							<#else>
								<#if !accds?exists || (accds?size==0)>
									<input id="accdFlag" type="checkbox" name="accdFlag" />
								<#else>
									<input id="accdFlag" type="checkbox" checked="checked" name="accdFlag" />
								</#if>
							</#if>
						<#else>
							${accdFlag }
						</#if>
					</td>
				</tr>
				<#if !preview?exists>
					<tr>
						<td colspan="2" align="right">
							报告内容：
						</td>
						<td colspan="3" align="left">
							<textarea rows="6" cols="100" name="rmks" ztype="textarea">${rmks}</textarea>
						</td>
					</tr>
					<tr>
						<td colspan="5" align="right">
							<#if reAuditFlag?exists && reAuditFlag != '1'>
								<button type="button" ztype="button" onclick="disableAndSubmit(this);">重新上报</button>&nbsp;&nbsp;<button type="button" ztype="button" id="return">返回</button>
							<#else>
								<button type="button" ztype="button" onclick="disableAndSubmit(this);">提交审核</button>&nbsp;&nbsp;<button type="button" ztype="button" id="return">返回</button>
							</#if>
						</td>
					</tr>
				<#else>
					<td colspan="2" align="right">
						报告内容：
					</td>
					<td colspan="3">
						${rmks}
					</td>
				</#if>
			</table>
		</form>
		<#if reAuditFlag?exists && (reAuditFlag=='1')>
			<div>
				<fieldset>
					<legend><strong>操作历史记录</strong></legend> 
					<#if comments?exists && (comments?size>0)>
						<#list comments as com>
							<div align="left" style="margin-left: 5px">${com}</div>
						</#list>
					</#if>
				</fieldset>
			</div>
		</#if>
	    <div>
	    	<fieldset>
		    	<legend><strong>本次零报告时间段内发生的事故信息</strong></legend>
				<#if accds?exists && (accds?size>0)>
					<div id="abnormal-show"></div>
				<#else>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
						<tr>
							<td align="left">
								<span style="COLOR: black" id="noAccd">本时间段内没有已经上报的事故</span>
							</td>
						</tr>
					</table>
				</#if>
			</fieldset>
		</div>
	</body>
</html>
