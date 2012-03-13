<html>
<head>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
			$('#myFormS').form();			
			$('button:contains("关闭")').click(function(){
				$('#dialogDiv').dialog('close');
			});
			$('#myFormS').ajaxForm({
				type: "post",
				dataType: 'json',
				beforeSubmit: validateForm,
				success: function(data){
					if (data.result == true){
						var name = $('#myFormS').find('input[name="name"]').val();
						var id = $('#myFormS').find('input[name="id"]').val();
						var message = '';
						if (id == undefined || id == ''){
							message = '业务'+name+'保存成功';
						}else {
							message = '业务'+name+'更新成功';
						}
						$('#dialogDiv').dialog('close');
						$('#grid-table-operationS-show').jqGrid().trigger("reloadGrid");
						$('#tb_grid-table-operationS-show').html('<font color="#ffb400">'+message+'</font>');
					}else {
						$('#tb_grid-table-operationS-show').html('<font color="#ffb400">'+message+'</font>');
					}
				}
			});
			 $('#satelliteS').tjChange(function(){
				var satelliteSId = $('#satelliteS').tjVal();
				$.ajax({
					type: 'POST',
					url: '${request.contextPath}/baseinfo/operation-s!getSatelliteSAjax.action?rd='+Math.random(),
					dataType: "json",
					data: {id:satelliteSId},
					async: false,
					success: function(data){
						$('#transferS').tjEmpty();
						if (data.result == true){
							var array = eval(data['data']);
							var html = '';
							if (array.length == 0){
								html += '<li val="-1">无</li>';
							}
							for(var i in array){
								html += '<li val="'+array[i].value+'">'+array[i].name+'</li>';
							}
							$('#transferS').find('ul').append(html);
							$('#transferS').tjAssemble();
						}
					}
				});
			 });			
		});
		 //点击保存前的验证
		 function validateForm(arr, thisForm){ 
			if (thisForm.data('tjForm').checkSubmit()) {
				//判断节目流；				
				var cawaveSId = $('#cawaveS').tjVal();				
				//转发器名称不能为空；
				var transferSId = $('#transferS').tjVal();
				if(cawaveSId=='-1'){
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
		<form action="${request.contextPath}/baseinfo/operation-s!save.action" id="myFormS" >
			<input type="hidden" name="id" value="<#if operationS?exists>${operationS.id}</#if>" />
			<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="left" >业务名称：</td>
					<td >
						<input type="text" name="name" ztype="text" style="width: 150px;" verify="名称|NotNull&&Length<64" value="<#if operationS?exists>${operationS.name}</#if>">
					</td>					
				</tr>
				<tr>
					<td align="left" >载波频率：</td>
					<td >
						<input type="text" name="cawaveFrq" ztype="text" style="width: 150px;" verify="载波频率|NotNull&&Decimal=6,1" value="<#if operationS?exists>${operationS.cawaveFrq?string.computer}</#if>">
					</td>					
				</tr>
				<tr>
					<td align="left" >符号率：</td>
					<td >
						<input type="text" name="simbleRate" ztype="text" style="width: 150px;" verify="符号率|NotNull&&Decimal=6,3" value="<#if operationS?exists>${operationS.simbleRate?string.computer}</#if>">
					</td>					
				</tr>	

				<tr>
					<td align="left" >开始时间：</td>
					<td >
						<input type="text" name="startDate" ztype="startdate" style="width: 150px;"  verify="开始时间|NotNull" value='<#if operationS?exists>${(operationS.startDate)?string("yyyy-MM-dd")}</#if>'/>
					</td>					
				</tr>
				<tr>
					<td align="left" >结束时间：</td>
					<td >
						<input type="text" name="endDate" ztype="startdate" style="width: 150px;"    value='<#if operationS?exists&&(operationS.endDate)?exists>${operationS.endDate?string("yyyy-MM-dd")}</#if>'/>
					</td>					
				</tr>					
				<tr>
					<td align="left" >信道编码率：</td>
					<td >
						<input type="text" name="channalRate" ztype="text" style="width: 150px;" verify="信道编码率|NotNull&&Fraction" value="<#if operationS?exists>${operationS.channalRate}</#if>">
					</td>					
				</tr>	
				<tr>
					<td align="left" >备注：</td>
					<td >
						<input type="text" name="rmks" ztype="text" style="width: 150px;" verify="备注|Length<64" value="<#if operationS?exists && operationS.rmks?exists>${operationS.rmks}</#if>">
					</td>					
				</tr>	
				<tr>
					<td align="left" >节目流：</td>
					<td >						
					    <div  id="cawaveS" name="cawaveS.id" ztype="select"   verify="NotNull">
				   			<ul>	
				   				<#if cawaveSList.size()==0>
				   					<li val="-1">无</li>
				   				<#else>				   																	
									<#list cawaveSList as cawaveS >
										<#if (operationS?exists)&&(operationS.cawaveS.id==cawaveS.id)>
											<li val="${cawaveS.id}" selected="selected">${cawaveS.name}</li>
										<#else>
											<li val="${cawaveS.id}">${cawaveS.name}</li>	
										</#if>					            		           
					            	</#list>	
					            </#if>														
							</ul>
						</div>						
					</td>					
				</tr>	
				<tr>
					<td align="left" >卫星：</td>
					<td >
					    <div id="satelliteS" name="satelliteS" ztype="select"   verify="NotNull">
				   			<ul>
				   				<#if satelliteSList.size()==0>
				   					<li val="-1">无</li>
				   				<#else>				   																		
								<#list satelliteSList as satelliteS >
									<#if (operationS?exists)&&(operationS.transfer.satellite.id==satelliteS.id)>
										<li val="${satelliteS.id}" selected="selected">${satelliteS.name}</li>
									<#else>
										<li val="${satelliteS.id}">${satelliteS.name}</li>	
									</#if>					            		           
				            	</#list>
				            	</#if>
							</ul>
						</div>
					</td>					
				</tr>	
				<tr>
					<td align="left" >转发器名称：</td>
					<td >						
					    <div id="transferS" name="transfer.id" ztype="select"   verify="NotNull">
				   			<ul>
				   				<#if transferSList.size()==0>
				   					<li val="-1">无</li>
				   				<#else>
									<#list transferSList as transferS >
										<#if (operationS?exists)&&(operationS.transfer.id==transferS.id)>
											<li val="${transferS.id}" selected="selected">${transferS.name}</li>
										<#else>
											<li val="${transferS.id}">${transferS.name}</li>	
										</#if>					            		           
					            	</#list>				   					
				   				</#if>													
							</ul>
						</div>						
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
