<html>
	<head>
	</head>
	<body>
	    <div>
	    	<div style="float:left;width:496px;">
	    		<iframe id="iframe1" name="iframe1" frameborder="no" scrolling="auto" border="0" style="border-width:0;" height="450" width="100%" src="${request.contextPath}/duty/duty!scanDuty.blank<#if dutyScan?exists>?id=${dutyScan.id}</#if>"></iframe>
	    	</div>
	    	<div style="width:auto;margin-left:500px;">
	    		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
					<tr>
						<th nowrap="nowrap">流程描述</th>
						<th nowrap="nowrap">当前状态</th>
						<th nowrap="nowrap">流程开始时间</th>
						<th nowrap="nowrap">操作</th>
					</tr>
					<#if newList?exists>
						<#list newList as map>
							<#list map?keys as key>
								<#assign pi=map.get(key)>
								<tr>
									<td align="center">${key}&nbsp;</td>
									<td align="center">${pi.rootToken.node.name}&nbsp;</td>
									<td align="center">${pi.start}</td>
									<td align="center">
										<a href="${request.contextPath}/workflow/task-execute.action?id=${pi.id}">执行任务</a>
										<a href="${request.contextPath}/workflow/task-detail.action?id=${pi.id}">任务详情</a>
									</td>
								</tr>
							</#list>
						</#list>
					</#if>
				</table>
	    	</div>
	    </div>
	</body>
</html>
