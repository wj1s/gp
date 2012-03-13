<html>
<head>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
			$('#myForm').form();
			
			$('button:contains("关闭")').click(function(){
				$('#dialogDiv').dialog('close');
			});

			$('#mySelectBox').selectBoxDiv();

			$('#myForm').ajaxForm({
				type: "post",
				dataType: 'json',
				beforeSubmit:defaultValidateForm,
				success: function(data){
					if (data.result == true){
						var name = $('#myForm').find('input[name="name"]').val();
						var id = $('#myForm').find('input[name="id"]').val();
						var message = '';
						if (id == undefined || id == ''){
							message = '节目流'+name+'保存成功';
						}else {
							message = '节目流'+name+'更新成功';
						}
						$('#dialogDiv').dialog('close');
						$('#grid-table-cawave-show').jqGrid().trigger("reloadGrid");
						$('#tb_grid-table-cawave-show').html('<font color="#ffb400">'+message+'</font>');
					}else {
						$('#tb_grid-table-cawave-show').html('<font color="#ffb400">'+message+'</font>');
					}
				}
			});
		});
	</script>
</head>
<body>
	<div style="width:500px;">
		<form action="${request.contextPath}/baseinfo/cawave!save.action" id="myForm" >
			<input type="hidden" name="id" value="<#if cawave?exists>${cawave.id}</#if>" />
			<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="left" colspan="2">节目流名称：</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="text" name="name" ztype="text" style="width: 350px;" verify="名称|NotNull&&NotRepeat={val:'baseinfo.cawave.name',delay:true}" value="<#if cawave?exists>${cawave.name}</#if>">
					</td>
				</tr>
				<tr>
					<td align="left" colspan="2">请选择节目流对应的节目：</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="text" name="programNames" ztype="auto" style="width: 350px;"
						 zmode="multi" verify="名称|NotNull" zurl="${request.contextPath}/baseinfo/program!autocompleteAjax.action"
						 value="<#if cawave?exists>${programNames},</#if>"/>
					</td>
				</tr>
				<tr>
					<td style="width:50%;text-align: right;border: 0px;background: #fff;padding-right: 5px;" colspan="2">
						<button ztype="button" type="submit">保存</button>
						<button ztype="button"  type="button">关闭</button>
					</td>
				</tr>
		     </table>
	     </form>
	</div>
</body>
</html>
