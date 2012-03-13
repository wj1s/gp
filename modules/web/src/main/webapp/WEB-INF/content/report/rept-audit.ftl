<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){	
				$('#workflowForm').form();
				
				<#if reptGroup.repts?exists && (reptGroup.repts?size==1)>
					$('#rqReportDiv').load($('#hiddenUrl').val());
				</#if>

				$('button:contains("审核通过")').click(function(){
					if ($('#workflowForm').data('tjForm').checkSubmit()) {
						$('#workflowForm').submit();
					}
				});
				
				$('button:contains("审核不通过")').click(function(){
					if ($('#workflowForm').data('tjForm').checkSubmit()) {
						$('#transitionName').val("驳回");
						$('#workflowForm').submit();
					}
				});
			});
		</script>
	</head>
	<body>
		<div>
			<#if !reptGroup.repts?exists && (reptGroup.repts?size==0)>
				<strong><span style="COLOR: red">没有符合条件的数据！</span> </strong>
			</#if>
			<#if reptGroup.repts?exists && (reptGroup.repts?size==1)>
				<#list reptGroup.repts as rept>
					<input type="hidden" id="hiddenUrl" value="${request.contextPath}/${rept.reportViewUrl}"/>
				</#list>
				<div id="rqReportDiv"></div>
			</#if>
			<#if reptGroup.repts?exists && (reptGroup.repts?size>1)>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
					<tr>
						<th nowrap="nowrap">
							报表名称
						</th>
						<th nowrap="nowrap" width="150">
							操作
						</th>
					</tr>
					<#list reptGroup.repts as rept>
						<tr>
							<td nowrap="nowrap">
								${rept.reptDef.reptName} &nbsp;
							</td>
							<td nowrap="nowrap" align="center">
								<a ztype="popUp" title="查看" url="${request.contextPath}/${rept.reportViewUrl}"
									zwidth="950" zheight="350" zmode="iframe"><img src="${request.contextPath}/img/find.gif" alt="查看" border="0" />查看报表</a>
							</td>
						</tr>
					</#list>
				</table>
			</#if>
			<div>
				<div>
					<form id="workflowForm" action="${request.contextPath}/report/rept!audit.action" method="post">
					<input type="hidden" id="taskInstanceId" name="taskId" value="${taskId}"/>
					<input type="hidden" id="transitionName" name="transitionName" value=""/>
					<input type="hidden" id="reptDate" name="reptDate" value="${id}"/>
					<input type="hidden" id="dataSource" name="dataSource" value="${dataSource}"/>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
						<tr>
							<td width="10%" align="right">
								<strong>审核意见</strong>：
							</td>
							<td width="90%" align="left">
								<textarea name="checkContent" ztype="textarea" rows="5" cols="130" verify="审核意见|Length<255">审核通过</textarea>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="right">
								<button ztype="button" type="button">审核通过</button>
								<button ztype="button" type="button">审核不通过</button>
							</td>
						</tr>
					</table>
					</form>
				</div>
				<div>
					<fieldset>
						<legend><strong>操作历史记录</strong></legend>
					  	<#list comments as comment>
							<div align="left" style="margin-left: 5px">${comment}</div>
						</#list>
					</fieldset>
				</div>
			</div>
		</div>
	</body>
</html>
