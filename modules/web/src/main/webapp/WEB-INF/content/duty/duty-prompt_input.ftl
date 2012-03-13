<html>
<head>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
			$('#promptForm').form();
			$('#promptForm').ajaxForm({
				type: "post",
				dataType: 'json',
				beforeSubmit: validateForm,
				success: function(data){
					if (data.result == true){
						var promptAdd = {};
						promptAdd.id = data.id;
						promptAdd.content = data.content;

						//更新值班记录集合
						updatePrompts(promptAdd);
						$('#dialogDiv').dialog('close');
					}
				}
			});

			$('button:contains("取消")').click(function(){
				$('#dialogDiv').dialog('close');
			});

			$('button:contains("确定")').click(function(){
				$('#promptForm').submit();
			});
		});
	</script>
</head>
<body>
    <div id="dutyPrompt">
   		<form id="promptForm" name="promptForm" action="${request.contextPath}/duty/duty-prompt!save.ajax">
   			<#if dutyPrompt?exists>
   				<input type="hidden" name="id" id="promptId" value="${dutyPrompt.id}" />
   			</#if>
   			<#if dutyId?exists>
   				<input type="hidden" name="duty.id" value="${dutyId}" />
   			</#if>
   			<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right" width="35%">
						发起人员：
					</td>
					<td align="left" width="65%">
						<input ztype="auto" type="text" id="empName" name="empName" verify="发起人员|NotNull" value="<#if dutyPrompt?exists>${dutyPrompt.empName}</#if>"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="35%">
						提醒开始时间：
					</td>
					<td align="left" width="65%">
						<input type="text" id="startTime" ztype="startdatetimeHM" name="startTime" verify="开始时间|NotNull" value="<#if dutyPrompt?exists>${dutyPrompt.startTime}</#if>"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="35%">
						提醒结束时间：
					</td>
					<td align="left" width="65%">
						<input type="text" id="endTime" ztype="enddatetimeHM" name="endTime" verify="结束时间|NotNull" value="<#if dutyPrompt?exists>${dutyPrompt.endTime}</#if>"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="35%">
						提醒内容:
					</td>
					<td align="left" width="65%">
						<textarea cols="25" rows="8" ztype="textarea" verify="提醒内容|NotNull" name="content"><#if dutyPrompt?exists>${dutyPrompt.content}</#if></textarea>
					</td>
				</tr>
				<tr>
					<td style="width:50%;text-align: right;border: 0px;background: #fff;padding-right: 5px;" colspan="2">
						<button ztype="button" type="button">确定</button>
						<button ztype="button" type="button">取消</button>
					</td>
				</tr>
			</table>
   		</form>
    </div>
</body>
</html>
