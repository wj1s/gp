<html>
	<head>
		<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/jquery-checkbox/jquery.checkbox.css"/>
		<script type='text/javascript' src="${request.contextPath}/plugin/jquery-checkbox/jquery.checkbox.js"></script>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('#myForm').form();
				$('#autoFlag').checkbox();
				$('#innerBroadByReason').tjChange(function(){
					var text = $('#innerBroadByReason').tjText();
					if(text=='其他'){
						$('#otherReasonDiv').html('<input type="text" ztype="text" id="otherReason" name="otherReason" verify="原因|NotNull" value="" />');
						$('#myForm').init_style();
						$('#myForm').form();
					}else{						
						$('#otherReasonDiv').html('');
					}
				});
				$('#transType').tjChange(function(){
					var id = $('#transType').tjVal();
					$.ajax({
						type: 'POST',
						url: '${request.contextPath}/baseinfo/trans-type!getOperationByTransType.ajax?rd='+Math.random(),
						dataType: "json",
						data: {id:id},
						async: false,
						success: function(data){
							var $select = $('#transType').parents('form').find('div[name="operation.id"]');
							$select.tjEmpty();
							if (data.result == true){
								var array = eval(data['data']);
								var html = '';
								for(var i in array){
									html += '<li val="'+array[i].value+'">'+array[i].name+'</li>';
								}
								$select.find('ul').append(html);
								$select.tjAssemble();
							}else {
								$select.find('ul').append('<li val="-1">无</li>');
								$select.tjAssemble();
							}
						}
					});
				});

				$('#saveBoad').click(function(){
					$('input[name="autoFlag"]').val($('#autoFlag').attr('checked'));
					$('#myForm').submit();
				});
				$('#closeBoad').click(function(){
					$('#dialogDiv').dialog('close');
				});

				<#if broadByTime?exists && !broadByTime.autoFlag>
					$('#autoFlag').attr('checked', false);
				</#if>
	
				<#if broadByTime?exists && broadByTime.broadByFlag=='D'>
					$('input[name="broadByFlag"][value="D"]').attr('checked',true);
				<#else>
					$('input[name="broadByFlag"][value="B"]').attr('checked',true);
				</#if>
			});

			function validateForm(arr, thisForm){
				var flag = true;
				if ($('#opId').tjVal() == '-1'){
					flag = false;
					tjAlert('没有相关的业务，请先填写业务信息!');
				}
				if (flag && thisForm.data('tjForm').checkSubmit()) {
					var startTime = $('#btbStartTime').val();
					var endTime = $('#btbEndTime').val();
					var id = $('#hiddenBbtId').val();
					var broadByFlag = $('input[name="broadByFlag"]:checked').val();
					var broadByStationId = $('#innerBroadByStation').tjVal();
					var msg = '';
					$.ajax({
						type: 'POST',
						url: '${request.contextPath}/report/broad-by-time!validateTimeAjax.ajax?rd='+Math.random(),
						dataType: "json",
						data: {startDate:startTime, endDate:endTime, id:id, broadByFlag:broadByFlag,broadByStationId:broadByStationId},
						async: false,
						success: function(data){
							if (data.result == true){
								msg = data.msg;
							}
						}
					});
					if (msg == undefined){
						return true;
					}else {
						tjAlert(msg);
						return false;
					}
				}else{
					return false;					
				}
			}

			function onSuccess(data) {
				if (data.result == true){
					var id = $('#myForm').find('input[name="id"]').val();
					var message = '';
					if (id == undefined || id == ''){
						message = '代播信息保存成功!';
					}else {
						message = '代播信息更新成功!';
					}
					$('#dialogDiv').dialog('close');
					$('#grid-table-broadByTime-show').jqGrid().trigger("reloadGrid");
					$('#tb_grid-table-broadByTime-show').html('<font color="#ffb400">'+message+'</font>');
				}else {
					$('#tb_grid-table-broadByTime-show').html('<font color="#ffb400">'+message+'</font>');
				}
			}

		</script>
	</head>
	<body>
		<#if msg?exists && msg != ''>
			<div>
				${msg}
			</div>
		<#else>
			<div>
			 	<form action="${request.contextPath}/report/broad-by-time!save.action" id="myForm" ztype="form">
					<input type="hidden" name="id" id="hiddenBbtId" value="<#if broadByTime?exists>${broadByTime.id}</#if>"/>
		    		<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<th align="center" colspan="2">
								&lt;&lt;节目代播信息&gt;&gt;
							</th>
						</tr>
						<tr>
							<td align="right" width="35%">
								传输类型:
							</td>
							<td align="left" width="65%">
								<div ztype="select" id="transType">
									<ul>
										<#if (transTypes?size>0)>
											<#list transTypes as transType>
												<#if broadByTime?exists && (broadByTime.operation.transType.id == transType.id)>
													<li val="${transType.id}" selected="selected">${transType.codeDesc}</li>
												<#else>
													<li val="${transType.id}">${transType.codeDesc}</li>
												</#if>
											</#list>
										</#if>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								业务:
							</td>
							<td align="left" width="65%">
								<div ztype="select" name="operation.id" id="opId" verify="业务|NotNull">
									<ul>
										<#if (operations?size>0)>
											<#list operations as operation>
												<#if broadByTime?exists && (broadByTime.operation.id == operation.id)>
													<li val="${operation.id}" selected="selected">${operation.name}</li>
												<#else>
													<li val="${operation.id}">${operation.name}</li>
												</#if>
											</#list>
										<#else>
											<li val="-1" selected="selected">无</li>
										</#if>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								代播原因:
							</td>
							<td align="left" width="65%">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<td  width="50%" align="left">
								<div ztype="select" name="broadByReason.id" id="innerBroadByReason" >
									<ul>
										<#if (broadByReasons?size>0)>
											<#list broadByReasons as broadByReason>
												<#if broadByTime?exists && (broadByTime.broadByReason.id == broadByReason.id)>
													<li val="${broadByReason.id}" selected="selected">${broadByReason.codeDesc}</li>
												<#else>
													<li val="${broadByReason.id}">${broadByReason.codeDesc}</li>
												</#if>
											</#list>
										</#if>
									</ul>
								</div>	
								</td>
								<td  width="50%" >
								<#if broadByTime?exists && (broadByTime.broadByReason.codeDesc == '其他')>	
								<div  id="otherReasonDiv" >									
									<input type="text" ztype="text" id="otherReason" name="otherReason" verify="原因|NotNull" value="${broadByTime.otherReason}"/>
								</div>	
								<#else>
								<div  id="otherReasonDiv">
									
								</div>	
								</#if>	
								</td>
							</table>								
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								是否自动代播:
							</td>
							<td align="left" width="65%">
								<input id="autoFlag" type="checkbox" checked="checked" />
								<input type="hidden" name="autoFlag" />
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								对方联系人:
							</td>
							<td align="left" width="65%">
								<input ztype="text" type="text" name="notifyPerson" verify="对方联系人|NotNull" value="<#if broadByTime?exists>${broadByTime.notifyPerson}</#if>"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								我方联系人:
							</td>
							<td align="left" width="65%">
								<input ztype="auto" type="text" name="notified" verify="我方联系人|NotNull" value="<#if broadByTime?exists>${broadByTime.notified}</#if>"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								代播开始时间:
							</td>
							<td align="left" width="65%">
								<input type="text" id="btbStartTime" ztype="startdatetime" verify="代播开始时间|NotNull" name="startTime" value="<#if broadByTime?exists>${broadByTime.startTime}</#if>"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								代播结束时间:
							</td>
							<td align="left" width="65%">
								<input type="text" id="btbEndTime" ztype="enddatetime" name="endTime" value="<#if broadByTime?exists && broadByTime.endTime?exists>${broadByTime.endTime}</#if>"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								代播被代标志:
							</td>
							<td align="left" width="65%">
								<input type="radio" name="broadByFlag" value="D" checked="checked"/>代播
								<input type="radio" name="broadByFlag" value="B"/>被代
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								对方站名称:
							</td>
							<td align="left" width="65%">
								<div ztype="select" name="broadByStation.id" verify="NotNull" id="innerBroadByStation">
									<ul>
										<#if (broadByStations?size>0)>
											<#list broadByStations as broadByStation>
												<#if broadByTime?exists && (broadByTime.broadByStation.id == broadByStation.id)>
													<li val="${broadByStation.id}" selected="selected">${broadByStation.codeDesc}</li>
												<#else>
													<li val="${broadByStation.id}">${broadByStation.codeDesc}</li>
												</#if>
											</#list>
										</#if>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								代播结果:
							</td>
							<td align="left" width="65%">
								<textarea cols="25" rows="8" ztype="textarea" name="broadResult"><#if broadByTime?exists>${broadByTime.broadResult}</#if></textarea>
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								备注:
							</td>
							<td align="left" width="65%">
								<textarea cols="25" rows="8" ztype="textarea" name="rmks"><#if broadByTime?exists && broadByTime.rmks?exists>${broadByTime.rmks}</#if></textarea>
							</td>
						</tr>
						<tr>
							<td style="width:50%;text-align: right;border: 0px;background: #fff;padding-right: 5px;" colspan="2">
								<button ztype="button" type="button" id="saveBoad">保存</button>
								<button ztype="button" type="button" id="closeBoad">关闭</button>
							</td>
						</tr>
					</table>
				</form>
		    </div>
		</#if>
	</body>
</html>
