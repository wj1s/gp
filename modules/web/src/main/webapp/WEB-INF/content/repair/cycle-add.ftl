<html>
	<head>	
		<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
			//设置验证form			
			 $('#formMap').form();
		});
		//保存新增周期表
		function saveNewCycle(){
			var check=$('#formMap').data('tjForm').checkSubmit();
			var obj=document.getElementById('formMap');
			if(check){
				obj.submit();
			}else{
				return ;			
			}
		}
		</script>
	</head>
	<body>    
	<form id="formMap" action="${request.contextPath}/repair/cycle!add.action" method="post" style="border:0;margin:0;padding:0;">
	    <div style="margin-top:10px">		    	    	
	    	<table width="100%" border="0" cellpadding="0" cellspacing="5">
	    	<tr>
		    	<td style="text-align:right;">部门名称：</td>	    	
		    	<td>
				    <div id="deptId" name="formMap.deptId" ztype="select" verify="NotNull">
			   			<ul>															
							<#list formMap.deptList as deptList >
				            	<li val="${deptList.id}">${deptList.name}</li>		           
			            	</#list>														
						</ul>
					</div>
		    	</td>
	    	</tr>
	    	<tr>
		    	<td style="text-align:right;">检修周期表名称：</td>		    	
		    	<td><input type="text"  ztype="text"  name="formMap.name" id="namess" verify="检修周期表名称|NotNull&&Length<50"/></td>
	    	</tr>
	    	</table>
			<div class="form_submitDiv">
				<input type="button" value="保存" ztype="button" onclick="saveNewCycle();"/>
				<input type="button" value="关闭" ztype="button" onclick="$('#dialogDiv').dialog('close');" />
			</div>										
		</div>   
	</form>	 
	</body>
</html>
