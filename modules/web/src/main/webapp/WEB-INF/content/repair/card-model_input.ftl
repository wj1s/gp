<html>
	<head>
		<title>卡片模版展现</title>		
<style type="text/css">
@media print {
	.notprint {
		display: none;
	}
	.pageNext {
		page-break-after: always;
	}
}

@media screen {
	.notprint {
		display: inline;
		cursor: hand;
	}
}
</style>
<script language="JavaScript" type="text/javascript">			
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
		obj4.value=((obj2.value).split('小时'))[0]+'.'+((((obj2.value).split('小时'))[1]).split('分钟'))[0];
		obj5.value=((obj3.value).split('小时'))[0]+'.'+((((obj3.value).split('小时'))[1]).split('分钟'))[0];
		var obj=document.getElementById('cardForm');
		obj.submit();		
	}				
</script>
</head>
	<body>
		<div>
		<form id="cardForm" action="${request.contextPath}/repair/card-model!saveCardModelDeal.action"  method="post" style="border:0;margin:0;padding:0;">
			<table border="0" cellpadding="0" cellspacing="0" align="center"  width="95%">
				<tr>
					<td>
						<h3 align="center">
							检修卡片模版
						</h3>
							<table border="0" cellpadding="0" cellspacing="0"
								class="work_info_table" align="center" width="85%">
								<tr>
									<td rowspan="4" align="right" width="10">
										<strong>检修内容</strong>
									</td>
									<td align="right" width="80">
										<strong>模版名称</strong>：
									</td>
									<td >
										<input type="text"  ztype="text"  name="cardModel.name" id="name"   value="<#if (cardModel?exists)>${cardModel.name}</#if>"  verify="模版名称|NotNull&&Length<50"/>
									</td>									
									</tr>
								<tr>
									<td align="right">
										<strong>设备类型</strong>：
									</td>
									<td colspan="5">
										<div id="equipTypeId1" name="cardModel.equipType.id" ztype="select">
					      					<ul>
											<#if (equipTypes?exists)&&(equipTypes.size()!=0) >
												<#list equipTypes as equipType>												
													<#if (cardModel?exists)&&(cardModel.equipType.id==equipType.id) >
													<li val="${equipType.id}" selected="selected">${equipType.codeDesc}</li>
													<#else>
														<li val="${equipType.id}">${equipType.codeDesc}</li>
													</#if>
												</#list>
											</#if> 	
											</ul>
										</div>									
									</td>
								</tr>									
								<tr>
									<td align="right">
										<strong>停机时间</strong>：
									</td>									
									<td align="left" colspan="5">
									<input type="text" name="shutdownTime1" id="shutdownTime1"  ztype="timeHMC"  value="<#if (cardModel?exists)>${shutdownTimeShow}</#if>" verify="停机时间|NotNull" readonly/>									
									</td>
								</tr>
								<tr>
									<td align="right">
										<strong>需用时间</strong>：
									</td>
									<td align="left" colspan="5">	
									<input type="text" name="processTime1" id="processTime1"  ztype="timeHMC"  value="<#if (cardModel?exists)>${processTimeShow}</#if>" verify="需用时间|NotNull" readonly/>																		
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
										<textarea ztype="textarea" cols="30" rows="4" name="cardModel.tools" id="tools"   verify="仪器工具|NotNull&&Length<300" ><#if (cardModel?exists)>${cardModel.tools}</#if></textarea>																			
									</td>
								</tr>
								<tr>
									<td align="right">
										<strong>安全措施</strong>：
									</td>
									<td colspan="5">																		
										<textarea ztype="textarea" cols="30" rows="4" name="cardModel.measure" id="measure"  verify="安全措施|NotNull&&Length<300" ><#if (cardModel?exists)>${cardModel.measure}</#if></textarea>									     
									</td>
								</tr>
								<tr>
									<td align="right">
										<strong>注意事项</strong>：
									</td>
									<td colspan="5">
										<textarea ztype="textarea" cols="30" rows="4" name="cardModel.attention" id="precaution"  verify="注意事项|NotNull&&Length<300" ><#if (cardModel?exists)>${cardModel.attention}</#if></textarea>
									</td>
								</tr>
								<tr>
									<td align="right" width="10">
											<strong>其他措施</strong>
									</td>
									<td align="left" colspan="6">
									   <textarea ztype="textarea" cols="30" rows="4" name="cardModel.other" id="others"  verify="其他措施|NotNull&&Length<300" ><#if (cardModel?exists)>${cardModel.other}</#if></textarea>
									</td>
								</tr>
								<tr>
									<td align="right" width="10">
										<strong>检修方法</strong>
									</td>
									<td colspan="6">																
									    <textarea ztype="textarea" cols="30" rows="4" name="cardModel.methods" id="method"  verify="检修方法|NotNull&&Length<2000" ><#if (cardModel?exists)>${cardModel.methods}</#if></textarea>
									</td>
								</tr>
								<tr>
									<td align="right" width="10">
										<strong>技术标准</strong>
									</td>
									<td colspan="6">																		
									    <textarea ztype="textarea" cols="30" rows="4" name="cardModel.technicalStandards" id="techStd"  verify="技术标准|NotNull&&Length<512" ><#if (cardModel?exists)>${cardModel.technicalStandards}</#if></textarea>
									</td>
								</tr>
								<tr>
									<td align="right" width="10">
										<strong>备注</strong>
									</td>
									<td align="left" colspan="6">																		
									    <textarea ztype="textarea" cols="30" rows="4" name="cardModel.remark" id="rmks"  verify="备注|Length<300"><#if (cardModel?exists)>${cardModel.remark}</#if></textarea>
									</td>
								</tr>
							</table>							
							<input type="hidden" id="id1" name="cardModel.id" value="<#if (cardModel?exists)>${cardModel.id}</#if>"/>				
							<input type="hidden" id="shutdownTime" name="cardModel.shutdownTime" value=""/>
							<input type="hidden" id="processTime" name="cardModel.processTime" value=""/>
							<div class="form_submitDiv">
								<input type="button" value="保存" ztype="button" onclick="saveCards();"/>
								<input type="button" value="关闭" ztype="button" onclick="$('#dialogDiv').dialog('close');" />
							</div>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</body>
</html>
