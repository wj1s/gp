<html>
	<head>
        <script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('#periodForm').form();
			<#if periodInput?exists>
			    <#if periodInput.type?exists>
				test(${periodInput.type?if_exists});
				$('select').attr("disabled","disabled");
				$("#type option[value=${periodInput.type?if_exists}]").attr("selected",true);
				$("#type>option").attr("disabled","disabled");
				$("#type onchange").remove();
			    </#if>
			</#if>             
				$('button:contains("关闭")').click(function(){
					$('#dialogDiv').dialog('close');
				});
				$('#periodForm').ajaxForm({
					type: "post",
					dataType: 'json',
					success: function(data){
						if (data.result == true){
							var name = $('#periodForm').find('input[name="name"]').val();
							var id = $('#periodForm').find('input[name="id"]').val();
							var message = '';
							if (id == undefined || id == ''){
								message = '检修周期'+name+'保存成功';
							}else {
								message = '检修周期'+name+'更新成功';
							}
							$('#dialogDiv').dialog('close');
							$('#grid-table-period-show').jqGrid().trigger("reloadGrid");
							$('#tb_grid-table-period-show').html('<font color="#ffb400">'+message+'</font>');
						}else {
							$('#tb_grid-table-period-show').html('<font color="#ffb400">'+message+'</font>');
						}
					}
				});
			});
			function test(view){//显示规则类型
				$('#al').empty();				
				var html="";
				if(view==1){
				    html+='<input id="value" name="value" type="text" ztype="text"  verify="每两次检修间隔天数|NotNull&&Int" maxlength="3" value="<#if periodInput?exists>${periodInput.value?if_exists}</#if>" />（每两次检修间隔天数）';
				}else if(view==2){
				    html+='<input id="startDay" name="startDay" verify="开始时间|NotNull" ztype="startdate" maxlength="50" type="text"   readonly="readonly" value="<#if periodInput?exists>${periodInput.startDay?if_exists}</#if>" />—<input id="endDay" ztype="enddate" name="endDay"  verify="结束时间|NotNull" maxlength="50" type="text" readonly="readonly" value="<#if periodInput?exists>${periodInput.endDay?if_exists}</#if>" />（检修时间区间）';
				}
				$('#al').append(html);
				$('#periodForm').form();
				$('#periodForm').init_style();
			}
        </script>
    </head>
    <body>
    	<div style="width:600px;">
	        <form id="periodForm" action="${request.contextPath}/repair/period!save.action"  name="periodForm">	            
	            <input type="hidden" name="id" value="<#if periodInput?exists>${periodInput.id?if_exists}</#if>" />	          
	            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
	                <tr>
	                    <td align="right" nowrap="nowrap">
	                        <strong>周期名称</strong>：
	                    </td>
	                    <td>
	                        <input id="name" name="name" type="text" ztype="text" verify="名称|NotNull"  maxlength="25"  value="<#if periodInput?exists>${periodInput.name?if_exists}</#if>" />
	                    </td>
	                    <td align="right" nowrap="nowrap">
	                        <strong>提醒</strong>：
	                    </td>
	                    <td nowrap="nowrap">
	                        <input id="previousValue" name="previousValue"  type="text" ztype="text" verify="提前提醒天数|NotNull&&Number" maxlength="3"  value="<#if periodInput?exists>${periodInput.previousValue?if_exists}</#if>" />（系统提前提醒）
	                    </td>
	                </tr>
	                <tr>
	                    <td align="right" nowrap="nowrap">
	                        <strong>规则</strong>：
	                    </td>
	                    <td>
	                        <select id="type" name="type" onchange="test(this.value);">
	                            <option value="1">检修间隔型</option>
	                            <option value="2">时间段型</option>
	                        </select>
	                    </td>
	                    <td colspan="3"  id="al">
	                       <input id="value" name="value" type="text" ztype="text" verify="每两次检修间隔天数|NotNull&&Int" maxlength="3" value="<#if periodInput?exists>${periodInput.value?if_exists}</#if>" />（每两次检修间隔天数）
	                    </td>
                	</tr>
           			<tr>
						<td style="text-align: center; background: #fff;padding-right: 5px;" colspan="5">					
								<button ztype="button" type="submit">保存</button>
								<button ztype="button" type="button">关闭</button>
						</td>
					</tr>
            	</table>
        	</form>
        </div>
    </body>
</html>