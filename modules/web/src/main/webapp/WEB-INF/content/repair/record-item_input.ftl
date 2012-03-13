<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){	
				$('#allCheck').click(function(){
					if($(this).attr('checked')==false){
						$('.checkbox:visible').attr('checked','');
					}else if($(this).attr('checked')==true){
						$('.checkbox:visible').attr('checked',true);
					}	
				});

				$('#addButton').click(function(){
					if($('.checkbox:checked').size()<1)
					 {
						tjAlert('请选择一个检修项!');
					 }else{					
					$('.checkbox:checked').each(function(){
						var item = {};
						//var itemName = $(this).parents('td').next().html();
						var itemId= $(this).parents('tr').find('td:eq(0)').find('input').attr('value');
						var pass = 0;
						/*对比id是否相等*/
						for (var i in array){
							if (array[i].id == itemId){
								pass++;
								break;
							}
						}
						if (pass == 0){
							item.id=$(this).parents('tr').find('td:eq(0)').find('input').attr('value');
							item.name = $(this).parents('tr').find('td:eq(1)').html()+'('+$(this).parents('tr').find('td:eq(2)').html()+')';
							item.examineRecord =$(this).parents('tr').find('td:eq(4)').find('a').attr('title');
							item.measure = $(this).parents('tr').find('td:eq(3)').find('a').attr('title');
							item.cycleName=$('#cycleId').tjText();
							item.type=0;
							item.repairName=repairName;
							item.repairNameId=repairNameId;
							array.push(item);
						}
						$(this).parents('tr').remove();
					});
					refresh();
					 }
				});

				$('#closeButton').click(function(){
					$('#dialogDiv').dialog('close');
				});
			});
			
			
			//检修周期
			$('#cycleId').tjChange(function(){
				var id = $('#cycleId').tjVal();
				$.getJSON('${request.contextPath}/repair/record!getCycleCellList.ajax?rd='+Math.random(),
					{'id':id},
					function(data){
						$('#cycleCellId').tjEmpty();
						 $('#work_data_tableId tr:gt(0)').remove();
						if (data.result){
							var arrayCycleCell = eval(data['data']);
							var html = '<li></li>';
							for(var i in arrayCycleCell){
								html += '<li val="'+arrayCycleCell[i].value+'" >'+arrayCycleCell[i].name+'</li>'
							}
							$('#cycleCellId ul').append(html);
							$('#cycleCellId').tjAssemble();
						}
					});
					});
				//检修分区
				 $('#cycleCellId').tjChange(function(){
					 var id = $('#cycleCellId').tjVal();
					 $.ajax({
						 type: 'POST',
						 url: '${request.contextPath}/repair/record!getCardList.ajax?rd='+Math.random(),
						 data: {id:id},
					     success: function(data){
					    	 $('#work_data_tableId tr:gt(0)').remove();
							 var arrayCard = eval(data['data']);
							 var html = '';
							 for(var i in arrayCard){
								 if(arrayCard[i].active!="0")
									 {
							    html +='<tr align="center"><td><input type="checkbox" class="checkbox"  id="cardIds" name="cardIds" value="'+arrayCard[i].id+'"/></td><td>'+arrayCard[i].name+'</td><td>'+(arrayCard[i])["period.name"]+'</td><td><a href="#" title="'+arrayCard[i].measure+'">查看</a></td><td><a href="#" title="'+arrayCard[i].methods+'">查看</a></td></tr>';
									 }
									 }
							 $('#work_data_tableId').append(html);
							}
					 });
			});
		</script>
	</head>
	<body>
		<div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table" id="work_info_tableId">
				<tr>
					<th colspan="4">
						<strong><span>添加检修项目</span></strong>
					</th>
				</tr>
				<tr>
					<td align="right" width="100">
						<strong>系统：</strong>
					</td>
					<td>
						<div id="cycleId" name="cycleId" ztype="select">
   							<ul>
								<#list cycles as cycle>
									<li val="${cycle.id}">${cycle.name}</li>
								</#list>
							</ul>
						</div>
					</td>
					<td align="right">
						<strong>分区：</strong>
					</td>
					<td>
						<div id="cycleCellId" name="cycleCellId" ztype="select">
   							<ul>
								<#list cycleCells as cycleCell>
									<li val="${cycleCell.id}">${cycleCell.name}</li>
								</#list>
							</ul>
						</div>
					</td>
				</tr>
			</table>
			<hr />
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="work_info_table" id="work_data_tableId">
			<tr><th><input id=allCheck name="all" type="checkbox"/></th><th><strong>检修项目</strong></th><th><strong>检修规则</strong></th><th><strong>安全措施</strong></th><th><strong>检修方法</strong></th></tr>
				<#if cards?exists>
				<#list cards as card>
				<#if card.active=="1">
				<tr align="center">
					<td>
						<input type="checkbox" class="checkbox" value="${card.id}" id="cardIds" name="cardIds"/>
					</td>
					<td>${card.name}</td>
					<td>${card.period.name}</td>
					<td><a href="#" title="<#if card.measure?exists>${card.measure}</#if>">查看</a></td>
					<td><a href="#" title="<#if card.methods?exists>${card.methods}</#if>">查看</a></td>
				</tr>
				</#if>
				</#list>
				</#if>
			</table>
		</div>
		<div align="right">
			<button ztype="button" id="addButton" type="button">添加</button><button ztype="button" id="closeButton" type="button">关闭</button>
		</div>
	</body>
</html>
