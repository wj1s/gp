<html>
<head>
	<script language="JavaScript" type="text/javascript">
	</script>
</head>
<body>
    <div id="dutyPrompt">
  			<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center" width="20%">
					发起人员
				</td>
				<td align="center" width="20%">
					提醒开始时间
				</td>
				<td align="center" width="20%">
					提醒结束时间
				</td>
				<td align="center">
					提醒内容
				</td>
			</tr>
			<#if dutyPrompts?exists && (dutyPrompts?size>0)>
				<#list dutyPrompts as dutyPrompt>
					<tr>
						<td>${dutyPrompt.empName}</td>
						<td>${dutyPrompt.startTime}</td>
						<td>${dutyPrompt.endTime}</td>
						<td>${dutyPrompt.content}</td>
					</tr>
				</#list>
			<#else>
				<tr><td colspan="4">当前没有任何值班提醒</td></tr>
			</#if>
		</table>
    </div>
</body>
</html>
