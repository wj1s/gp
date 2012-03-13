<html>
<head>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
			$('#myFormP').form();			
			$('button:contains("关闭")').click(function(){
				$('#dialogDiv').dialog('close');
			});
			$('#myFormP').ajaxForm({
				type: "post",
				dataType: 'json',
				beforeSubmit: validateForm,
				success: function(data){
					if (data.result == true){
						var name = $('#myFormP').find('input[name="name"]').val();
						var id = $('#myFormP').find('input[name="id"]').val();
						var message = '';
						if (id == undefined || id == ''){
							message = '业务'+name+'保存成功';
						}else {
							message = '业务'+name+'更新成功';
						}
						$('#dialogDiv').dialog('close');
						$('#grid-table-operationP-show').jqGrid().trigger("reloadGrid");
						$('#tb_grid-table-operationP-show').html('<font color="#ffb400">'+message+'</font>');
					}else {
						$('#tb_grid-table-operationP-show').html('<font color="#ffb400">'+message+'</font>');
					}
				}
			});
			 $('#cawaveP').tjChange(function(){
				var cawavePId = $('#cawaveP').tjVal();
				$.ajax({
					type: 'POST',
					url: '${request.contextPath}/baseinfo/operation-p!getProgramPAjax.action?rd='+Math.random(),
					dataType: "json",
					data: {id:cawavePId},
					async: false,
					success: function(data){
						$('#programP').tjEmpty();
						if (data.result == true){
							var array = eval(data['data']);
							var html = '';
							if (array.length == 0){
								html += '<li val="-1">无</li>';
							}
							for(var i in array){
								html += '<li val="'+array[i].value+'">'+array[i].name+'</li>';
							}
							$('#programP').find('ul').append(html);
							$('#programP').tjAssemble();
						}
					}
				});
			 });			
		});
		 //点击保存前的验证
		 function validateForm(arr, thisForm){ 
			if (thisForm.data('tjForm').checkSubmit()) {
				//判断节目流；				
				var cawavePId = $('#cawaveP').tjVal();				
				//转发器名称不能为空；
				var transferSId = $('#transferS').tjVal();
				if(cawavePId=='-1'){
					tjAlert("节目流不能为空！");
					return false;	
				}
				if(transferSId=='-1'){
					tjAlert("转发器名称不能为空！");
					return false;	
				}
				return true
			}else{
				return false;					
			}
		}		
	</script>
</head>
<body>
	<div style="width:500px;">
		<form action="${request.contextPath}/baseinfo/operation-p!save.action" id="myFormP" >
			<input type="hidden" name="id" value="<#if operationP?exists>${operationP.id}</#if>" />
			<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="left" >业务名称：</td>
					<td >
						<input type="text" name="name" ztype="text" style="width: 150px;" verify="名称|NotNull&&Length<64" value="<#if operationP?exists>${operationP.name}</#if>">
					</td>					
				</tr>	
				<tr>
					<td align="left" >开始时间：</td>
					<td >
						<input type="text" name="startDate" ztype="startdate" style="width: 150px;"  verify="开始时间|NotNull" value='<#if operationP?exists>${(operationP.startDate)?string("yyyy-MM-dd")}</#if>'/>
					</td>					
				</tr>
				<tr>
					<td align="left" >结束时间：</td>
					<td >
						<input type="text" name="endDate" ztype="startdate" style="width: 150px;"    value='<#if operationP?exists&&(operationP.endDate)?exists>${operationP.endDate?string("yyyy-MM-dd")}</#if>'/>
					</td>					
				</tr>						
				<tr>
					<td align="left" >节目流：</td>
					<td >						
					    <div  id="cawaveP" name="cawaveP.id" ztype="select"   verify="NotNull">
				   			<ul>	
				   				<#if cawavePList.size()==0>
				   					<li val="-1">无</li>
				   				<#else>				   																	
									<#list cawavePList as cawaveP >
										<#if (operationP?exists)&&(operationP.cawaveP.id==cawaveP.id)>
											<li val="${cawaveP.id}" selected="selected">${cawaveP.name}</li>
										<#else>
											<li val="${cawaveP.id}">${cawaveP.name}</li>	
										</#if>					            		           
					            	</#list>	
					            </#if>														
							</ul>
						</div>						
					</td>					
				</tr>	
				<tr>
					<td align="left" >节目：</td>
					<td >						
					    <div  id="programP" name="program.id" ztype="select"   verify="NotNull">
				   			<ul>	
				   				<#if programPList.size()==0>
				   					<li val="-1">无</li>
				   				<#else>				   																	
									<#list programPList as programP >
										<#if (operationP?exists)&&(operationP.program.id==programP.id)>
											<li val="${programP.id}" selected="selected">${programP.name}</li>
										<#else>
											<li val="${programP.id}">${programP.name}</li>	
										</#if>					            		           
					            	</#list>	
					            </#if>														
							</ul>
						</div>						
					</td>					
				</tr>				
				<tr>
					<td align="left" >备注：</td>
					<td >
						<input type="text" name="rmks" ztype="text" style="width: 150px;" verify="备注|Length<64" value="<#if operationP?exists>${operationP.rmks?if_exists}</#if>">
					</td>					
				</tr>								
				<tr>
					<td style="width:50%;text-align: right;border: 0px;background: #fff;padding-right: 5px;" colspan="2">
						<button ztype="button" type="submit">保存</button>
						<button ztype="button" type="button">关闭</button>
					</td>
				</tr>
		     </table>
	     </form>
	</div>
</body>
</html>
