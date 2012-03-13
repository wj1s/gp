<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('#myForm').form();
				
				$('button:contains("审核通过")').click(function(){
					if ($('#myForm').data('tjForm').checkSubmit()) {
						$('#myForm').submit();
					}
				});
				
				$('button:contains("审核不通过")').click(function(){
					if ($('#myForm').data('tjForm').checkSubmit()) {
						$('#transitionName').val("驳回");
						$('#myForm').submit();
					}
				});
			});
		</script>
	</head>
	<body>
		<div id="body_div"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="work_info_table" >
				<tr>
					<th colspan="2">${reptGroup.reptGroupName}审核</th>
				</tr>
				<tr>
					<td style="width:30%;" align="center">是否有事故发生：</td>
					<td>${accdFlag}</td>
				</tr>
				<tr>
					<td style="width:30%;" align="center">描述：</td>
					<td>${rmks }</td>
				</tr>
			</table>
			<div>
				<div>
					<form id="myForm" action="${request.contextPath}/report/zero-reporting!audit.action" method="post">
					<input type="hidden" id="taskInstanceId" name="taskId" value="${taskId}"/>
					<input type="hidden" id="transitionName" name="transitionName" value=""/>
					<input type="hidden" id="reptTime" name="reptTime" value="${id}"/>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
						<tr>
							<td width="10%" align="right">
								<strong>审核意见</strong>：
							</td>
							<td width="90%" align="left">
								<textarea id="checkContent" name="checkContent" ztype="textarea" rows="5" cols="130" verify="审核意见|Length<255">审核通过</textarea>
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
