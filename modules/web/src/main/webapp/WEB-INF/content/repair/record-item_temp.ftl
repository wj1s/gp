<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){	
				$('#recordItemTemp').form();
				$('#addButton').click(function(){
					if($('#recordItemTemp').data('tjForm').checkSubmit())
					{      	var item = {};
						    item.name = $('#content').val();						
							item.id=10*Math.random();
							item.examineRecord ='';
							item.measure = '';
							item.cycleName=$('#cycleId').tjText();
							item.type=1;
							item.repairName=repairName;
							item.repairNameId=repairNameId;
							array.push(item);				
					        refresh();
					        $('#content').val('');
					}
				});

				$('#closeButton').click(function(){
					$('#dialogDiv').dialog('close');
				});
			});
		</script>
	</head>
	<body>
	<form action="" id="recordItemTemp">
		<div>
		
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
				<tr>
					<td align="right" width="100" colspan="1" >
						<strong>系统：</strong>
					</td>
					<td colspan="3" >
						<div id="cycleId" name="cycleId" ztype="select" verify="NotNull">
   							<ul>
								<#list cycles as cycle>
									<li val="${cycle.id}">${cycle.name}</li>
								</#list>
							</ul>
						</div>
					</td>
				</tr>
			<tr><td align="right" ><strong>检修项目：</strong></td><td> <textarea  ztype="textarea"  id="content" rows="2" cols="60" name="content" verify="检修项目|NotNull"></textarea></td></tr>
			</table>
		</div>
		<div align="right">
			<button ztype="button" id="addButton" type="button">添加</button><button ztype="button" id="closeButton" type="button">关闭</button>
		</div>
		</form>
	</body>
</html>
