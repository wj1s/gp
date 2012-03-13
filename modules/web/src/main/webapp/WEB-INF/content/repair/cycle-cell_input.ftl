<html>
	<head>
		<script language="JavaScript" type="text/javascript">	
			var equipNameOld='';
			$(document).ready(function(){
				//添加设备的blur事件blur以后刷新模版列表
				$('#cardModelName').tjChange(function(){					
					changeCardModel();
				});
				$('#cardModelName').tjEmpty();
				var html ='<li val="-1" ></li>';
				$('#cardModelName ul').append(html);
				$('#cardModelName').tjAssemble();					
			});
			//改变模版刷界面
			function changeCardModel(){
				var cardModelId = $('#cardModelName').tjVal();//卡片模版名称
				if(cardModelId=='-1'){//如果-1则返回
					return ;
				}
				$.ajax({
					type:'POST',
					url: '${request.contextPath}/repair/cycle-cell!getCardModelById.action?rd='+Math.random(),
					dataType: "json",
					data: {cardModelId:cardModelId},
					async: false,
					success: function(data){
						if (data.result == true){
							$('#shutdownTime1').val(data.shutdownTime);
							$('#processTime1').val(data.processTime);
							$('#tools').val(data.tools);
							$('#measure').val(data.measure);
							$('#precaution').val(data.attention);
							$('#others').val(data.other);
							$('#method').val(data.methods);
							$('#techStd').val(data.technicalStandards);
							$('#rmks').val(data.remark);
						}
					}
				});
			}
			//设备和卡片模版联动
			function changeEquipt(obj){
				var equipName=$(obj).val();
				if(equipNameOld==equipName){
					return ;
				}else{
					equipNameOld=equipName;
				}
				$.ajax({
					type: 'POST',
					url: '${request.contextPath}/repair/cycle-cell!getCardModelByEquip.action?rd='+Math.random(),
					dataType: "json",
					data: {equipName:equipName},
					async: false,
					success: function(data){						
						if (data.result == true){	
							var array = eval(data['data']);
							var html = '';
							$('#cardModelName').tjEmpty();
							html +='<li val="-1" ></li>';
							if (array.length != 0){
								for(var i in array){
									html += '<li val="'+array[i].id+'" >'+array[i].name+'</li>';
								}
							}
							$('#cardModelName ul').append(html);
							$('#cardModelName').tjAssemble();								
						}
					}
				});			      									
			}
			//设置验证form				
			$('#cardForm').form();			
			function saveCards(){
				var check=$('#cardForm').data('tjForm').checkSubmit();
				if(!check)return;
				//判断停机时间和需用时间
				var obj2=document.getElementById('shutdownTime1');	
				var obj3=document.getElementById('processTime1');	
				var obj4=document.getElementById('shutdownTime');	
				var obj5=document.getElementById('processTime');	
				obj4.value=((obj2.value).split('小时'))[0]+':'+((((obj2.value).split('小时'))[1]).split('分钟'))[0];
				obj5.value=((obj3.value).split('小时'))[0]+':'+((((obj3.value).split('小时'))[1]).split('分钟'))[0];
				var obj=document.getElementById('cardForm');
				obj.submit();		
			}
		</script>		
	</head>
	<body>
		<div align="center">
			<form id="cardForm" action="${request.contextPath}/repair/cycle-cell!saveCardDeal.action"  method="post" style="border:0;margin:0;padding:0;">
				<table border="0" cellpadding="0" cellspacing="0" class="work_info_table" align="center" width="100%">
					<tr>
						<td rowspan="5" align="right" width="10">
							<strong>检修内容</strong>
						</td>					
						<td align="right" width="80">
							<strong>检修项目</strong>：</td>
						<td align="left" colspan="5">
							<input type="text"  ztype="text"  name="repairItem" id="repairItem"  value="${formMap.card.name}" disabled/>
						</td>												
					</tr>
					<tr>
						<td align="right">
							<strong>选择设备</strong>：</td>
						<td align="left" colspan="5">
							<input id='equipF' name='equipName'  ztype="auto" zurl="${request.contextPath}/baseinfo/equip!autocompleteTAjax.action" value="<#if (formMap.card.equip?exists)>${formMap.card.equip.name}</#if>" onblur="changeEquipt(this);" verify="NotNull"  maxlength="50" />
						</td>												
					</tr>
					<tr>
						<td align="right">
							<strong>卡片模版</strong>：</td>
						<td align="left" colspan="5">
							<div id="cardModelDiv">
							<div id="cardModelName" name="cardModelName" ztype="select">
		      					<ul id="cardModelName_ul" >
									<li val="" ></li>									
								</ul>
							</div>					
							</div>			
						</td>
					</tr>
					<tr>
						<td align="right">
							<strong>停机时间</strong>：
						</td>
						<td align="left" colspan="5">
							<input type="text" name="formMap.shutdownTime1" id="shutdownTime1"  ztype="timeHMC"  value="${formMap.shutdownTimeShow}" verify="停机时间|NotNull" readonly/>
						</td>						
					</tr>
					<tr>
						<td align="right">
							<strong>需用时间</strong>：
						</td>
						<td align="left" colspan="5">
							<input type="text" name="formMap.processTime1" id="processTime1"  ztype="timeHMC"  value="${formMap.processTimeShow}" verify="需用时间|NotNull" readonly/>
						</td>						
					</tr>
					<tr>
						<td rowspan="3" align="right" width="10">
							<strong>保证措施</strong>
						</td>					
						<td align="right">
							<strong>仪器工具</strong>：
						</td>
						<td align="left" colspan="5">
							<textarea ztype="textarea" cols="30" rows="4" name="formMap.tools" id="tools"   verify="仪器工具|NotNull&&Length<300" ><#if (formMap.card.tools?exists)>${formMap.card.tools}</#if></textarea>
						</td>						
					</tr>
					<tr>
						<td align="right">
							<strong>安全措施</strong>：
						</td>
						<td align="left" colspan="5">
							<textarea ztype="textarea" cols="30" rows="4" name="formMap.measure" id="measure"  verify="安全措施|NotNull&&Length<300" ><#if (formMap.card.tools?exists)>${formMap.card.measure}</#if></textarea>
						</td>						
					</tr>
					<tr>
						<td align="right">
							<strong>注意事项</strong>：
						</td>
						<td align="left" colspan="5">
							<textarea ztype="textarea" cols="30" rows="4" name="formMap.attention" id="precaution"  verify="注意事项|NotNull&&Length<300" ><#if (formMap.card.tools?exists)>${formMap.card.attention}</#if></textarea>
						</td>						
					</tr>
					<tr>
						<td align="right" width="10">
							<strong>其他措施</strong>
						</td>
						<td align="left" colspan="6">
							<textarea ztype="textarea" cols="30" rows="4" name="formMap.other" id="others"  verify="其他措施|NotNull&&Length<300" ><#if (formMap.card.tools?exists)>${formMap.card.other}</#if></textarea>
						</td>						
					</tr>
					<tr>
						<td align="right" width="10">
							<strong>检修方法</strong>
						</td>
						<td align="left" colspan="6">
							<textarea ztype="textarea" cols="30" rows="4" name="formMap.methods" id="method"  verify="检修方法|NotNull&&Length<2000" ><#if (formMap.card.tools?exists)>${formMap.card.methods}</#if></textarea>
						</td>						
					</tr>
					<tr>
						<td align="right" width="10">
							<strong>技术标准<strong>
						</td>
						<td align="left" colspan="6">
							<textarea ztype="textarea" cols="30" rows="4" name="formMap.technicalStandards" id="techStd"  verify="技术标准|NotNull&&Length<512" ><#if (formMap.card.tools?exists)>${formMap.card.technicalStandards}</#if></textarea>
						</td>						
					</tr>
					<tr>
						<td align="right" width="10">
							<strong>备注<strong>
						</td>
						<td align="left" colspan="6">
							<textarea ztype="textarea" cols="30" rows="4" name="formMap.remark" id="rmks"  verify="备注|Length<300"><#if (formMap.card.tools?exists)>${formMap.card.remark}</#if></textarea>
						</td>
					</tr>
															
				</table>
				<input type="hidden" id="formMap.id" name="formMap.id" value="${formMap.id1}"/>
				<input type="hidden" id="formMap.cardId" name="formMap.cardId" value="${formMap.card.id}"/>
				
				<input type="hidden" id="shutdownTime" name="formMap.shutdownTime" value=""/>
				<input type="hidden" id="processTime" name="formMap.processTime" value=""/>
				<div class="form_submitDiv">
					<input type="button" value="保存" ztype="button" onclick="saveCards();"/>
					<input type="button" value="关闭" ztype="button" onclick="$('#dialogDiv').dialog('close');" />
				</div>
			</form>
		</div>
	</body>
</html>
