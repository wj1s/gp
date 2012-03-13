<html>
<head>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){	
			$('#delForm').form();

			$('#delForm').ajaxForm({
				type: "post",
				dataType: 'json',
				beforeSubmit: validateForm,
				success: function(data){
					if (data['result'] == true){
						tjAlert('删除成功!')
						var url = '${request.contextPath}/duty/duty!show.action';
						window.location.href = url;
					}else {
						tjAlert("删除失败!");
					}
				}
			});

			$('button:contains("取消")').click(function(){
				var arrangeUrl = '${request.contextPath}/duty/duty-rule!arrange.ajax';
				$(window.parent.document).find("#arrange").load(arrangeUrl);
			});
		});

		function validateForm(){
			if ($('#delForm').data('tjForm').checkSubmit()) {
				var dpId = '${dept.id}';
				var startDate = $('#startDate').val();
				var flag = false;
				$.ajax({
					  type: 'POST',
					  url: '${request.contextPath}/duty/duty!validateDateAjax.ajax?rd='+Math.random(),
					  dataType: "json",
					  data: {dpId:dpId,startDate:startDate},
					  async: false,
					  success: function(data){
						if (data.result == false){
							flag = true;
						}else {
							tjAlert('今天已经存在值班记录！不能删除排班！');
						}
					}
				});
				return flag;
			}else{
				return false;					
			}
		}
	</script>
</head>
<body>
	<div>
		<form name="delForm" action="${request.contextPath}/duty/duty!delete.action" id="delForm">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
				<th colspan="6">
					&lt;&lt;删除排班&gt;&gt;
				</th>
				<tr>
					<td align="right">
						<strong>部门：</strong>
					</td>
					<td>
						${dept.name}
						<input type="hidden" value="${dept.id}" name="dpId"/>
					</td>
					<td align="right">
						<strong>开始日期：</strong>
					</td>
					<td>
						<input name="startDate" type="text" id="startDate" ztype="startdate" verify="开始时间|NotNull"/>
					</td>
					<td align="right">
						<strong>结束日期：</strong>
					</td>
					<td>
						<input id="endDate" name="endDate" type="text" ztype="enddate" verify="结束时间|NotNull"/>
					</td>
				</tr>
			</table>
			<div align="right">
				<button ztype="button" type="submit">删除</button>
				<button ztype="button" type="button">取消</button>
			</div>
		</form>
	</div>
</body>
</html>
