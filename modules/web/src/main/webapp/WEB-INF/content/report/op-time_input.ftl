<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				<#if !map?exists || (map?size==0)>
					$('button:contains("保存")').attr('disabled','disabled');
				</#if>
				
				$('button:contains("取消")').click(function(){
					$("#editDiv",parent.document.body).empty();
				});
			});

			function onSuccess(data){
				if (data.result == true){
					tjAlert('保存成功!');
					$('#dialogDiv').dialog('close');
				}
			}
		</script>
	</head>
	<body>
		<div>
			${msg?if_exists}
		</div>
		<div>
			<form ztype="form" id="myForm" action="${request.contextPath}/report/op-time!save.action">
				<input type="hidden" name="reptTime" value="${reptTime}"/>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
					<tr>
						<th>业务名称</th>
						<th>时间（年月）</th>
						<th>应传时间(分钟)</th>
					</tr>
					<#if map?exists && (map?size>0)>
						<#list map?keys as operation>
							<tr>
								<td align="center">${operation.name}</td>
								<td align="center">${reptTime}</td>
								<td align="center">
									<input type="hidden" name="opIds" value="${operation.id}"/>
									<#if (canEdit==1)>
										<input name="broadTimes" ztype="text" verify="传输时间|NotNull&&Decimal=12,2" value="${map.get(operation)}"/>
									<#else>
										${map.get(operation)}
									</#if>
								</td>
							</tr>
						</#list>
					<#else>
						<tr><td colspan="3">本月份下没有任何业务，请检查业务数据是否正确!</td></tr>				
					</#if>
					<tr>
						<td colspan="3" align="right">
							<#if (canEdit==1)>
								<button ztype="button" type="submit">保存</button>
							</#if>
							<button ztype="button" type="button">取消</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
