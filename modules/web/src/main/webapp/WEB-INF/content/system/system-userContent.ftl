<html>
	<head>
		<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/selectbox/skin/blue/jquery.selectbox.css"/>
		<script type="text/javascript" src="${request.contextPath}/plugin/selectbox/jquery.selectbox.js"></script>
		<script type="text/javascript">
		
			$(document).ready(function(){
				$('#tab1').tab(1,0,410,0,0);
				$("#tab1").showTab(0);

				$('input[type="checkbox"].enableCheck').each(function(){
					$(this).checkbox();
				});

				//初始化页面
				$('#popedomeTable').find('tr:gt(0)').each(function(){
					if($(this).find('td:last span').length != 0){
						$(this).find('input[name="enableFlag"]').attr("checked",true);
						var tr = $(this);
						$(this).find('.funSpan').each(function(){
							var funName = $(this).html();
							tr.find('#'+funName).attr("checked",true);
						});
						tr.find('td:eq(3)').find('div:eq(0)').show();
						tr.find('td:eq(3)').find('div:eq(1)').hide();
					}
				});

				$('#roleForm').ajaxForm({
					type: "post",
					dataType: 'json',
					success: function(data){
						if (data.result == true){
							tjAlert('保存成功');
						}
					}
				});

				$('#personFunForm').ajaxForm({
					type: "post",
					dataType: 'json',
					success: function(data){
						if (data.result == true){
							tjAlert('保存成功');
						}
					}
				});
			});

			function enableFun(){
				if ($('input[name="isFunEnable"]:checked').length == 0){
					tjAlert("请至少选择一个部门!");
					return;
				}
				var selectedDept = new Array();
				$('input[name="isFunEnable"]:checked').each(function(){
					$(this).parent().parent().find('input[name="enableFlag"]').attr('checked',true);
					$(this).parent().parent().find('td:eq(3)').find('div:eq(0)').show();
					$(this).parent().parent().find('td:eq(3)').find('div:eq(1)').hide();
					selectedDept.push($(this).val());
				});
				$('input[name="isFunEnable"]:checked').attr('checked',false);
				$('#divPopUp').dialog({
					resizable: false,
					autoOpen: false,
					height: 300,
					width: 250,
					modal: true		
				});	
				$('#divPopUp').dialog('open'); 
				$('#divPopUp').data('dpIds',selectedDept.toString(','));
			}
			
  	  		function disableFun(){
  				$('input[name="isFunEnable"]:checked').each(function(){
					$(this).parent().parent().find('input[name="enableFlag"]').attr('checked',false);
					$(this).parent().parent().find('td:eq(3)').find('div:eq(0)').hide();
					$(this).parent().parent().find('td:eq(3)').find('div:eq(1)').show();
				});	
  				$('input[name="isFunEnable"]:checked').attr('checked',false);
  	  		}
  	  		
			function changeEnable(obj){
				if($(obj).attr("checked")){
					$(obj).parent().parent().find('td:eq(3)').find('div:eq(0)').hide();
					$(obj).parent().parent().find('td:eq(3)').find('div:eq(1)').show();
				}else{
					$(obj).parent().parent().find('td:eq(3)').find('div:eq(0)').show();
					$(obj).parent().parent().find('td:eq(3)').find('div:eq(1)').hide();
				}
			}
			function saveFun(){
				var valiadeFlag = true;
				$('#popedomeTable').find('tr:gt(0)').each(function(){
					if(!$(this).find('input[name="enableFlag"]').attr('checked')){
						$(this).find('input[name="funsStr"]').attr("checked",false);
						return true;
					}
					if($(this).find('td:eq(3)').find('input[type="checkbox"]:checked').length == 0){
						tjAlert($(this).find('td:eq(1)').html()+"开启，但是没有功能权限");
						valiadeFlag = false;
						return false;
					}
					var funKey = new Array();
					$(this).find('td:eq(3)').find('input[type="checkbox"]:checked').each(function(){
						funKey.push($(this).val());
					});
					$(this).find('input[name="funsStr"]').attr("checked",true);
					$(this).find('input[name="funsStr"]').val(funKey.toString(","));
				});
				if(valiadeFlag){
					$('#personFunForm').submit();
				}
			}
			
  	  		function selectAll(obj){
  	  	  		var position = $(obj).attr('name');
  				if($(obj).attr("checked")){
  					$('input[name="'+position+'"]').attr("checked",true);
  				}else{
  					$('input[name="'+position+'"]').attr("checked",false);
  				}
  			}

  			function closeDiv(){
				$('#divPopUp').dialog('close'); 
				$('#divPopUp').find('[name="fun"]').attr('checked',false);
  			}

  			function saveFunInner(){
  				if($('input[name="fun"]:checked').length == 0){
  					tjAlert("至少选择一个功能");
  					return;
  				}
  				var dpIds = $('#divPopUp').data('dpIds').split(",");
  				for(var i = 0 ; i < dpIds.length ; i++){
  					$('#popedomeTable').find('tr:gt(0)').each(function(){
  						if(dpIds[i] == $(this).find('input[id^="dept_"]').val()){
  							$(this).find('td:eq(3)').find('input[type="checkbox"]').each(function(){
								var id = $(this).attr('id');
								$(this).attr("checked",$('#divPopUp').find('[id="'+id+'"]').attr("checked"));
  							});
  						}
  				    });
  				}
  				$('#divPopUp').dialog('close'); 
				$('#divPopUp').find('[name="fun"]').attr('checked',false);
  			}
		</script>
	</head>
	<body>
		<fieldset>
			<legend>人员信息:${model.name}</legend>
			<div>
				<div class="tab" id="tab1">
		        <ul> 
		            <li>角色</li>
		            <li>功能权限</li>
		        </ul>
		        <div>
		        	<form id="roleForm" action="${request.contextPath}/system/system!saveRoles.ajax">
		        		<input name="id" value="${model.id}" type="hidden"/>
			        	<div style="height:160px;margin-left:0;padding-top:3px;">
							<div name="roleIds" ztype="selectbox">
						        <ul>
						        	<#if rolesInit?exists && (rolesInit?size>0)>
							        	<#list rolesInit as roleInit>
							        		<li val="${roleInit.id}">${roleInit.desc}</li>
							        	</#list>
						        	</#if>
						        </ul>
						        <ul>
						        	<#if model.roles?exists && (model.roles?size>0)>
							        	<#list model.roles as role>
							        		<li val="${role.id}">${role.desc}</li>
							        	</#list>
						        	</#if>
								</ul>
					    	</div>
					    </div>
					    <div>
					    	<button ztype="button" type="submit">保存</button>
					    </div>
				    </form>
		        </div>
		        <div>
		        	<button onClick="enableFun();" ztype="button" type="button">批量启用/权限设置</button>
	               	<button onClick="disableFun();" ztype="button" type="button">禁用</button>
	               	<button onClick="saveFun();" ztype="button" type="button">保存</button>
		        	<div id="personFun" >
		               	<form action="${request.contextPath}/system/system!savePersonFunByAjax.ajax" id="personFunForm" method="post">
	               			<input type="hidden" name="id" value="${model.id}" /> 
	               			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table" id="popedomeTable" style="margin-top:10px">
			                	<tr>
			                		<th><input type="checkbox" onClick="selectAll(this)" name="isFunEnable" style="border: none;background:none;"/></th>
			                		<th>部门名称</th>
			                		<th>启用/禁用</th>
			                		<th>功能权限</th>
			                	</tr>
			                	<#if deptList?exists && (deptList?size>0)>
				                	<#list deptList as dept>
					                	<tr>
					                		<td style="width:18px" align="center">
					                			<input type="checkbox" value="${dept.id}" id="dept_${dept.id}" name="isFunEnable"  style="border: none;background:none;"/>
					                		</td>
					                		<td style="width:20%" align="center">
					                			${dept.name}
					                		</td>
					                		<td style="width:10%">
					                			<input name="enableFlag" type="checkbox" class="enableCheck" value="${dept.id}" onClick="changeEnable(this);"/>
					                		</td>
					                		<td align="center">
					                			<div style="display: none;">
					                				<#list funs as fun>
					                					<input type="checkbox" id="${fun.key}" value="${fun.key}" style="border: none;background:none;"/>${fun.desc}
					                				</#list>
					                			</div>
					                			<div>&nbsp;</div>
					                		</td>
					                		<td style="display:none;">
					                			<input type="checkbox" name="funsStr" value=""/>
					                			<div>
					                				<#if model.deptPers?exists && (model.deptPers?size>0)>
					                					<#list model.deptPers as perDept>
					                						<#if perDept.dept.id==dept.id> 
							                					<span class="funSpan">${perDept.funModuleKey}</span>
							                				</#if>
							                			</#list>
							                		</#if>
					                			</div>
					                		</td>
					                	</tr>
					                </#list>
					            <#else>
					            	系统内没有任何部门信息，请先添加部门
								</#if>			              
							</table>
						</form>
					</div>
					<button onClick="enableFun();" ztype="button" type="button">批量启用/权限设置</button>
	               	<button onClick="disableFun();" ztype="button" type="button">禁用</button>
	               	<button  onClick="saveFun();" ztype="button" type="button">保存</button>
	               	<div id="divPopUp" style="display:none;" title="设置功能权限">
 						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
						 	<tr>
						 		<th width="20px">
						 			<input type="checkbox" onClick="selectAll(this)" name="fun" style="border:none;background:none">
						 		</th>
						 		<th>功能名称</th>
						 	</tr>
						 	<#list funs as fun>
						 		<tr>
						 			<td><input type="checkbox" name="fun" id="${fun.key}" value="${fun.key}" style="border: none;background:none"/></td>
						 			<td align="center">${fun.desc}</td>
						 		</tr>
						 	</#list>
						 </table>
						 <div style="text-align:center">
						 	<button onClick="saveFunInner();" ztype="button" type="button">保存</button>
						 	<button onClick="closeDiv();" ztype="button" type="button">关闭</button>
						 </div>
	               	</div>
		        </div>
			</div>
		</fieldset>
	</body>
</html>
