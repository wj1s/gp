<html>
	<head>
		<script type="text/javascript">
			$(document).ready(function(){
				
				var names = '';
				$('#hiddenDiv').find('span').each(function(){
					names+=$(this).text() + ',';
				});
				$('#peopleNamesChange').val(names);

				$.init_style();

			});

			function onSuccess(data) {
				if (data.result == true){
					$('#dialogDiv').dialog('close');
					$('#grid-table-stay-detail-show').jqGrid().trigger("reloadGrid");
				}else {
					tjAlert("保存失败,请联系管理员!");
				}
			}

		</script>
	</head>
	<body>
		<#if model?exists && canChange == 1>
			<form id="myForm" ztype="form" method="post" action="${request.contextPath}/stay/stay-detail!save.action" >
				<input type="hidden" name="id" value="${model.id}"/>
				<input type="hidden" name="dept.id" value="${model.dept.id}"/>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
					<tr>
						<th width="20%" nowrap="nowrap">
							部门
						</th>
						<th width="15%" nowrap="nowrap">
							开始时间
						</th>
						<th width="15%" nowrap="nowrap">
							结束时间
						</th>
						<th width="50%" nowrap="nowrap">
							人员选择
						</th>
					</tr>
					<tr height="30">
						<td align="center">
							${model.dept.name }
						</td>
						<td align="center">
							<input type="text" ztype="startdatetimeHM" id="startTimeChange" name="startTime" verify="开始时间|NotNull" value="${model.startTime?string('yyyy-MM-dd HH:mm')}" style="width: 115px"/>
						</td>
						<td align="center">
							<input type="text" ztype="enddatetimeHM" id="endTimeChange" name="endTime" verify="开始时间|NotNull" value="${model.endTime?string('yyyy-MM-dd HH:mm')}" style="width: 115px"/>
						</td>
						<td align="center">
							<input id="peopleNamesChange" name="peopleNames" type="text" ztype="auto" zmode="multi" verify="留守人员|NotNull" value="" style="width: 175px"/>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<button ztype="button" type="submit">保存</button>
							<button ztype="button" type="button">关闭</button>
						</td>
					</tr>
				</table>
			</form>
			<div style="display: none;" id="hiddenDiv">
				<#list model.stayDetailPeople as stayDetailperson>
					<span>${stayDetailperson.id.empName }</span>
				</#list>
			</div>
		<#else>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
				<tr><td align="center">当前留守任务已经结束，无法进行编辑!</td></tr>
			</table>
		</#if>
	</body>
</html>
