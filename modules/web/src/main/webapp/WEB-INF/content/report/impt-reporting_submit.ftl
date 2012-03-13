<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('button:contains("重新上报")').click(function(){
					$('#myForm').attr('action','${request.contextPath}/report/rept!audit.action');
					$('#myForm').submit();
				});

				$('button:contains("修改传输时间")').click(function(){
					$('#toInput').trigger('click');
				});
			});
		</script>
	</head>
	<body>
		<div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
				<tr>
					<th colspan="6">
						<strong>&lt;&lt;${reptGroup.reptGroupName} &gt;&gt;</strong>
					</th>
				</tr>
			</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
				<tr>
					<th nowrap="nowrap">
						报表名称
					</th>
					<th nowrap="nowrap" width="150">
						操作
					</th>
				</tr>
				<#if reptGroup.repts?exists && (reptGroup.repts?size>0)>
					<#list reptGroup.repts as rept>
						<tr>
							<td nowrap="nowrap">
								${rept.reptDef.reptName} &nbsp;
							</td>
							<td nowrap="nowrap" align="center">
								<a ztype="popUp" title="查看" url="${request.contextPath}/${rept.reportViewUrl}"
									zwidth="950" zheight="550" zmode="iframe"><img src="${request.contextPath}/img/find.gif" alt="查看" border="0" />查看报表</a>
							</td>
						</tr>
					</#list>
				<#else>
					<tr>
						<td colspan="11">
							<strong><span style="COLOR: red">没有符合条件的数据！</span> </strong>
						</td>
					</tr>
				</#if>
			</table>
		</div>
		<div>
			<form method="post" id="myForm" action="${request.contextPath}/report/impt-reporting!submit.action">
				<input type="hidden" name="reptTime" value="${reptGroup.reptTime}"/> 
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
					<tr>
						<td align="right">
							<#if reSubmitFlag?exists && reSubmitFlag=='reSubmit'>
								<input type="hidden" name="taskId" value="${taskId}"/>
								<input type="hidden" name="transitionName" value=""/>
								<input type="hidden" name="checkContent" value="重新上报"/>
								<input type="hidden" name="dataSource" value="IMPT" />
								<input type="hidden" name="reptDate" value="${reptGroup.reptTime}" />
								<button ztype="button" type="button">重新上报</button>
							<#else>
								<button ztype="button" type="submit">报表上报</button>
							</#if>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<#if reSubmitFlag?exists && reSubmitFlag=='reSubmit'>
			<div>
				<fieldset>
					<legend><strong>操作历史记录</strong></legend>
					<#list comments as comment>
						<div align="left" style="margin-left: 5px">${comment}</div>
					</#list>
				</fieldset>
			</div>
		</#if>
	</body>
</html>
