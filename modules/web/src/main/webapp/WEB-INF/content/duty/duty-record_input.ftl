<html>
<head>
	<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/jquery-checkbox/jquery.checkbox.css"/>
	<script type='text/javascript' src="${request.contextPath}/plugin/jquery-checkbox/jquery.checkbox.js"></script>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
			$('#recordType').tjChange(function(){
				var recType = $('#recordType').tjVal();
				showContent(recType);
			});
			$('#broadByReason').tjChange(function(){
				var text = $('#broadByReason').tjText();
				if(text=='其他'){
					$('#otherReasonDiv123').html('<input type="text" ztype="text" id="otherReason" name="broadByTime.otherReason" verify="原因|NotNull" value="" />');
					$('#broadByTimeForm').init_style();
					$('#broadByTimeForm').form();
				}else{
					$('#otherReasonDiv123').html('');
				}				
			});			
			$('input[type="checkbox"]').each(function(){
				$(this).checkbox();
			});

			$('#otherForm').form();
			$('#patrolTimeForm').form();
			$('#warningForm').form();
			$('#broadByTimeForm').form();

			$('#otherForm').ajaxForm({
				type: "post",
				dataType: 'json',
				beforeSubmit: validateForm,
				success: function(data){
					if (data.result == true){
						var recordAdd = {};
						recordAdd.id = data.id;
						recordAdd.content = data.content;

						//更新值班记录集合
						updateRecords(recordAdd);
						$('#dialogDiv').dialog('close');
					}
				}
			});

			$('#patrolTimeForm').ajaxForm({
				type: "post",
				dataType: 'json',
				beforeSubmit: validateForm,
				success: function(data){
					if (data.result == true){
						var recordAdd = {};
						recordAdd.id = data.id;
						recordAdd.content = data.content;

						//更新值班记录集合
						updateRecords(recordAdd);
						$('#dialogDiv').dialog('close');
					}
				}
			});

			$('#warningForm').ajaxForm({
				type: "post",
				dataType: 'json',
				beforeSubmit: validateForm,
				success: function(data){
					if (data.result == true){
						var recordAdd = {};
						recordAdd.id = data.id;
						recordAdd.content = data.content;

						//更新值班记录集合
						updateRecords(recordAdd);
						$('#dialogDiv').dialog('close');
					}
				}
			});

			$('#broadByTimeForm').ajaxForm({
				type: "post",
				dataType: 'json',
				beforeSubmit: validateFormForBbt,
				success: function(data){
					if (data.result == true){
						var recordAdd = {};
						recordAdd.id = data.id;
						recordAdd.content = data.content;

						//更新值班记录集合
						updateRecords(recordAdd);
						$('#dialogDiv').dialog('close');
					}
				}
			});

			$('button:contains("确定")').click(function(){
				$('input[name="broadByTime.autoFlag"]').val($('#autoFlag').attr('checked'));
				var $formObj=$(this).parents('form:eq(0)');				
				formObj=$formObj[0];
				$formObj.submit();
			});

			$('button:contains("取消")').click(function(){
				$('#dialogDiv').dialog('close');
			});

			<#if dutyRecord?exists>
				$('#recordType').tjEmpty();
				<#list dutyRecord.recTypeMap?keys as recType>
					$('#recordType').find('ul').append('<li>${dutyRecord.recTypeMap.get(recType)}</li>');
					$('#recordType').attr('disabled',true);
					$('#recordType').tjAssemble();
					showContent('${recType}');
				</#list>
			</#if>

			transTypeChange('w-transType');
			transTypeChange('d-transType');
		});

		//选择类型时隐藏和现实各种DIV
		function showContent(recType){
			if (recType == 'A'){
				
			}else {
				if (recType == 'D'){
					<#if !dutyRecord?exists>
						$.ajax({
							type: 'POST',
							url: '${request.contextPath}/report/broad-by-time!getBroadByTimeAjax.action?rd='+Math.random(),
							dataType: "json",
							async: false,
							success: function(data){
								if (data.result == true){
									var arrayBbt = eval(data['data']);
									var html = '';
									for(var i=0;i<arrayBbt.length;i++){
										html += '<tr><td align="center"><input type="radio" value="'+arrayBbt[i].id+'" name="selectBbt" onclick="chooseBbt(this);"/>';
										html += '<input type="hidden" id="opIdHidden_'+arrayBbt[i].id+'" value="'+arrayBbt[i]['operation.id']+'"/>';
										html += '<input type="hidden" id="autoFlagHidden_'+arrayBbt[i].id+'" value="'+arrayBbt[i]['autoFlag']+'"/>';
										html += '<input type="hidden" id="broadResultHidden_'+arrayBbt[i].id+'" value="'+arrayBbt[i]['broadResult']+'"/>';
										html += '<input type="hidden" id="transTypeHidden_'+arrayBbt[i].id+'" value="'+arrayBbt[i]['operation.transType.id']+'"/>';
										html += '<input type="hidden" id="notifyPersonHidden_'+arrayBbt[i].id+'" value="'+arrayBbt[i]['notifyPerson']+'"/>';
										html += '<input type="hidden" id="rmksHidden_'+arrayBbt[i].id+'" value="'+arrayBbt[i]['rmks']+'"/>';
										html += '<input type="hidden" id="notifiedHidden_'+arrayBbt[i].id+'" value="'+arrayBbt[i]['notified']+'"/></td>';
										html += '<td align="center">'+arrayBbt[i]['operation.name']+'</td>';
										html += '<td align="center">'+arrayBbt[i]['startTime']+'</td>';
										html += '<td align="center">'+arrayBbt[i]['braodByFlagStr']+'</td>';
										html += '<td align="center">'+arrayBbt[i]['broadByStation.codeDesc']+'</td>';
										html += '<td align="center">'+arrayBbt[i]['broadByReason.codeDesc']+'</td></tr>';
									}
									$('#broadByTimeTable').append(html);
								}
							}
						});
					<#else>
						<#if dutyRecord.broadByTime?exists && !dutyRecord.broadByTime.autoFlag>
							$('#autoFlag').attr('checked', false);
						</#if>
		
						<#if dutyRecord.broadByTime?exists && dutyRecord.broadByTime.broadByFlag=='D'>
							$('input[name="broadByTime.broadByFlag"][value="D"]').attr('checked',true);
						<#else>
							$('input[name="broadByTime.broadByFlag"][value="B"]').attr('checked',true);
						</#if>
						<#if dutyRecord.broadByTime?exists>
							$('#hiddenBbtId').val('${dutyRecord.broadByTime.id}');
						</#if>
					</#if>
				}
				$('#dutyRecord > div').hide();
				$('#'+recType+'-Div').show();
			}
		}

		//ajaxForm验证方法
		function validateForm(arr, thisForm){
			var flag = true;
			var selLength = $(thisForm).find('div[ztype="select"]').length;
			for(var i=0;i<selLength;i++){
				var theTarget = $(thisForm).find('div[ztype="select"]:eq('+i+')');
				var selVal = $(theTarget).tjVal();
				if (selVal == '-1'){
					var theText = $(theTarget).parents('td:first').prev().text();
					theText = theText.substring(0, theText.indexOf(':'));
					tjAlert(theText + '为空!请先添加' + theText + '信息!');
					flag = false;
					break;
				}
			}
			if (flag && thisForm.data('tjForm').checkSubmit()) {
				return true;
			}else{
				return false;					
			}
		}

		//ajaxForm验证方法
		function validateFormForBbt(arr, thisForm){
			var flag = true;
			var selLength = $(thisForm).find('div[ztype="select"]').length;
			for(var i=0;i<selLength;i++){
				var theTarget = $(thisForm).find('div[ztype="select"]:eq('+i+')');
				var selVal = $(theTarget).tjVal();
				if (selVal == '-1'){
					var theText = $(theTarget).parents('td:first').prev().text();
					theText = theText.substring(0, theText.indexOf(':'));
					tjAlert(theText + '为空!请先添加' + theText + '信息!');
					flag = false;
					break;
				}
			}
			if (flag && thisForm.data('tjForm').checkSubmit()) {
				var startTime = $('#btbStartTime').val();
				var endTime = $('#btbEndTime').val();
				var id = $('#hiddenBbtId').val();
				var broadByFlag = $('input[name="broadByTime.broadByFlag"]:checked').val();
				var broadByStationId = $('#broadByStation').tjVal();
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
				return true;
			}else{
				return false;					
			}
		}

		function transTypeChange(selectId){
			$('#' + selectId).tjChange(function(){
				var id = $('#' + selectId).tjVal();
				$.ajax({
					  type: 'POST',
					  url: '${request.contextPath}/baseinfo/trans-type!getOperationByTransType.ajax?rd='+Math.random(),
					  dataType: "json",
					  data: {id:id},
					  async: true,
					  success: function(data){
						var $select = $('#' + selectId).parents('form').find('div[name="opId"]');
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
		}

		function chooseBbt(target){
			var bbtId = $(target).val();
			if (bbtId == undefined || bbtId == ''){
				$('#hiddenBbtId').val('');
				$('#d-transType').tjVal($('#d-transType').find('li:first').attr('val'));
				$('#d-transType').trigger('tjChange');
				$('#d-opId').tjVal($('#d-opId').find('li:first').attr('val'));
				$('#broadByReason').tjVal($('#broadByReason').find('li:first').attr('val'));
				$('#autoFlag').attr('checked',true);
				$('input[name="broadByTime.notifyPerson"]').val('');
				$('input[name="broadByTime.notified"]').val('');
				$('textarea[name="broadByTime.rmks"]').text('');
				$('#btbStartTime').val('');
				$('#btbEndTime').val('');
				$('input[name="broadByTime.broadByFlag"][value="D"]').attr('checked',true);
				$('#broadByStation').tjVal($('#broadByStation').find('li:first').attr('val'));
				$('textarea[name="broadByTime.broadResult"]').text('');

				$('#btbStartTime').val('');
				$('#hintTr').hide();
				$('#editTr').show();
			}else {
				$('#hiddenBbtId').val(bbtId);
				$('#d-transType').tjVal($('#transTypeHidden_' + bbtId).val());
				$('#d-transType').trigger('tjChange');
				$('#d-opId').tjVal($('#opIdHidden_' + bbtId).val());
				$('#broadByReason').tjText($(target).parents('tr:first').find('td:eq(5)').text());
				if($('#autoFlagHidden_' + bbtId).val() == 'true'){
					$('#autoFlag').attr('checked',true);
				}else {
					$('#autoFlag').attr('checked',false);
				}
				$('input[name="broadByTime.notifyPerson"]').val($('#notifyPersonHidden_' + bbtId).val());
				if ($(target).parents('tr:first').find('td:eq(3)').text() == '代播'){
					$('input[name="broadByTime.broadByFlag"][value="D"]').attr('checked',true);
				}else {
					$('input[name="broadByTime.broadByFlag"][value="B"]').attr('checked',true);
				}
				$('#broadByStation').tjText($(target).parents('tr:first').find('td:eq(4)').text());
				$('textarea[name="broadByTime.broadResult"]').text($('#broadResultHidden_' + bbtId).val());
				$('input[name="broadByTime.notified"]').val($('#notifiedHidden_' + bbtId).val());
				$('textarea[name="broadByTime.rmks"]').text($('#rmksHidden_' + bbtId).val());

				var startTime = $(target).parents('tr:first').find('td:eq(2)').text();
				$('#btbStartTime').val(startTime)
			}
			$('#broadByTimeForm').form();
		}

	</script>
</head>
<body>
	<div id="selectDiv">
   		<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right" width="35%" style="font-size: 14px;">
					值班记录类型:
				</td>
				<td align="left" width="65%">
					<div ztype="select" id="recordType">
						<ul>
							<li val="O">其他</li>
							<li val="D">代播</li>
							<li val="W">告警</li>
							<li val="P">巡视</li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
   	</div>
    <div id="dutyRecord">
    	<div id="O-Div">
    		<form id="otherForm" name="otherForm" ztype="form" action="${request.contextPath}/duty/duty-record-o!save.ajax">
    			<#if dutyRecord?exists>
    				<input type="hidden" name="id" value="${dutyRecord.id}" />
    			</#if>
    			<#if dutyId?exists>
    				<input type="hidden" name="duty.id" value="${dutyId}" />
    			</#if>
    			<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th align="center" colspan="2">
							&lt;&lt;其他信息&gt;&gt;
						</th>
					</tr>
					<tr>
						<td align="right" width="35%">
							内容:
						</td>
						<td align="left" width="65%">
							<textarea cols="25" rows="12" ztype="textarea" id="content" name="content" verify="内容|NotNull"><#if dutyRecord?exists>${dutyRecord.content}</#if></textarea>
						</td>
					</tr>
					<tr>
						<td style="width:50%;text-align: right;border: 0px;background: #fff;padding-right: 5px;" colspan="2">
							<button ztype="button" type="button">确定</button>
							<button ztype="button" type="button">取消</button>
						</td>
					</tr>
				</table>
    		</form>
    	</div>
    	<div id="P-Div" style="display:none;">
    		<form id="patrolTimeForm" name="patrolTimeForm" ztype="form" action="${request.contextPath}/duty/duty-record-p!save.ajax">
    			<#if dutyRecord?exists>
    				<input type="hidden" name="id" value="${dutyRecord.id}" />
    			</#if>
    			<#if dutyId?exists>
    				<input type="hidden" name="duty.id" value="${dutyId}" />
    			</#if>
	    		<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th align="center" colspan="2">
							&lt;&lt;领导巡视信息&gt;&gt;
						</th>
					</tr>
					<tr>
						<td align="right" width="35%">
							巡视人员:
						</td>
						<td align="left" width="65%">
							<input ztype="auto" type="text" name="patrolTime.empName" verify="巡视人员|NotNull" value="<#if dutyRecord?exists && dutyRecord.patrolTime?exists>${dutyRecord.patrolTime.empName}</#if>"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="35%">
							巡视开始时间:
						</td>
						<td align="left" width="65%">
							<input type="text" id="ptStartTime" ztype="startdatetime" verify="巡视开始时间|NotNull" name="patrolTime.startTime" value="<#if dutyRecord?exists && dutyRecord.patrolTime?exists>${dutyRecord.patrolTime.startTime}</#if>"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="35%">
							巡视结束时间:
						</td>
						<td align="left" width="65%">
							<input type="text" id="ptEndTime" ztype="enddatetime" verify="巡视结束时间|NotNull" name="patrolTime.endTime" value="<#if dutyRecord?exists && dutyRecord.patrolTime?exists>${dutyRecord.patrolTime.endTime}</#if>"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="35%">
							巡视内容:
						</td>
						<td align="left" width="65%">
							<textarea cols="25" rows="8" ztype="textarea" name="patrolTime.content" verify="巡视内容|NotNull"><#if dutyRecord?exists && dutyRecord.patrolTime?exists>${dutyRecord.patrolTime.content}</#if></textarea>
						</td>
					</tr>
					<tr>
						<td style="width:50%;text-align: right;border: 0px;background: #fff;padding-right: 5px;" colspan="2">
							<button ztype="button" type="button">确定</button>
							<button ztype="button" type="button">取消</button>
						</td>
					</tr>
				</table>
			</form>
    	</div>
    	<div id="W-Div" style="display:none;">
    		<form id="warningForm" name="warningForm" ztype="form" action="${request.contextPath}/duty/duty-record-w!save.ajax">
    			<#if dutyRecord?exists>
    				<input type="hidden" name="id" value="${dutyRecord.id}" />
    			</#if>
    			<#if dutyId?exists>
    				<input type="hidden" name="duty.id" value="${dutyId}" />
    			</#if>
	    		<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th align="center" colspan="2">
							&lt;&lt;业务告警信息&gt;&gt;
						</th>
					</tr>
					<tr>
						<td align="right" width="35%">
							传输类型:
						</td>
						<td align="left" width="65%">
							<div ztype="select" id="w-transType" verify="NotNull">
								<ul>
									<#if transTypeList?exists>
										<#list transTypeList as transType>
											<#if dutyRecord?exists && dutyRecord.dutyWarning?exists && (dutyRecord.dutyWarning.operation.transType.id == transType.id)>
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
							<div ztype="select" name="opId" verify="NotNull" id="w-opId">
								<ul>
									<#if operationList?exists && (operationList?size>0)>
										<#list operationList as operation>
											<#if dutyRecord?exists && dutyRecord.dutyWarning?exists && (dutyRecord.dutyWarning.operation.id == operation.id)>
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
							告警时间:
						</td>
						<td align="left" width="65%">
							<input type="text" ztype="datetime" name="dutyWarning.warnTime" verify="告警时间|NotNull" value="<#if dutyRecord?exists && dutyRecord.dutyWarning?exists>${dutyRecord.dutyWarning.warnTime}</#if>"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="35%">
							告警类型:
						</td>
						<td align="left" width="65%">
							<div ztype="select" name="wnTpId" verify="NotNull" id="wnTpId">
								<ul>
									<#if warnTypeList?exists>
										<#list warnTypeList as warnType>
											<#if dutyRecord?exists && dutyRecord.dutyWarning?exists && (dutyRecord.dutyWarning.warnType.id == warnType.id)>
												<li val="${warnType.id}" selected="selected">${warnType.codeDesc}</li>
											<#else>
												<li val="${warnType.id}">${warnType.codeDesc}</li>
											</#if>
										</#list>
									</#if>
								</ul>
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" width="35%">
							处理情况:
						</td>
						<td align="left" width="65%">
							<textarea cols="25" rows="8" ztype="textarea" name="dutyWarning.process" verify="处理情况|NotNull"><#if dutyRecord?exists && dutyRecord.dutyWarning?exists>${dutyRecord.dutyWarning.process}</#if></textarea>
						</td>
					</tr>
					<tr>
						<td align="right" width="35%">
							原因分析:
						</td>
						<td align="left" width="65%">
							<textarea cols="25" rows="8" ztype="textarea" name="dutyWarning.analysis" verify="原因分析|NotNull"><#if dutyRecord?exists && dutyRecord.dutyWarning?exists>${dutyRecord.dutyWarning.analysis}</#if></textarea>
						</td>
					</tr>
					<tr>
						<td style="width:50%;text-align: right;border: 0px;background: #fff;padding-right: 5px;" colspan="2">
							<button ztype="button" type="button">确定</button>
							<button ztype="button" type="button">取消</button>
						</td>
					</tr>
				</table>
			</form>
    	</div>
    	<div id="D-Div" style="display:none;">
    		<form id="broadByTimeForm" name="broadByTimeForm" ztype="form" action="${request.contextPath}/duty/duty-record-d!save.ajax">
    			<input type="hidden" name="broadByTime.id" id="hiddenBbtId"/>
    			<#if dutyRecord?exists>
    				<input type="hidden" name="id" value="${dutyRecord.id}" />
    			</#if>
    			<#if dutyId?exists>
    				<input type="hidden" name="duty.id" value="${dutyId}" />
    			</#if>
    			<#if dutyRecord?exists && !dutyRecord.broadByTime?exists>
    				本条代播记录已经删除!
    			<#else>
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
								<div ztype="select" id="d-transType" verify="NotNull">
									<ul>
										<#if transTypeList?exists>
											<#list transTypeList as transType>
												<#if dutyRecord?exists && dutyRecord.broadByTime?exists && (dutyRecord.broadByTime.operation.transType.id == transType.id)>
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
								<div ztype="select" name="opId" verify="NotNull"  id="d-opId">
									<ul>
										<#if operationList?exists && (operationList?size>0)>
											<#list operationList as operation>
												<#if dutyRecord?exists && dutyRecord.broadByTime?exists && (dutyRecord.broadByTime.operation.id == operation.id)>
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
								<div ztype="select" name="reasonId"  id="broadByReason">
									<ul>
										<#if broadByReasonList?exists>
											<#list broadByReasonList as broadByReason>
												<#if dutyRecord?exists && dutyRecord.broadByTime?exists && (dutyRecord.broadByTime.broadByReason.id == broadByReason.id)>
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
								<#if dutyRecord?exists && dutyRecord.broadByTime?exists && (dutyRecord.broadByTime.broadByReason.codeDesc == '其他')>	
								<div  id="otherReasonDiv123" >									
									<input type="text" ztype="text" id="otherReason" name="broadByTime.otherReason" verify="原因|NotNull" value="${dutyRecord.broadByTime.otherReason}"/>
								</div>	
								<#else>
								<div  id="otherReasonDiv123">
									
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
								<input type="hidden" name="broadByTime.autoFlag" />
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								对方联系人:
							</td>
							<td align="left" width="65%">
								<input ztype="text" type="text" name="broadByTime.notifyPerson" verify="对方联系人|NotNull" value="<#if dutyRecord?exists && dutyRecord.broadByTime?exists>${dutyRecord.broadByTime.notifyPerson}</#if>"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								我方联系人:
							</td>
							<td align="left" width="65%">
								<input ztype="auto" type="text" name="broadByTime.notified" verify="我方联系人|NotNull" value="<#if dutyRecord?exists && dutyRecord.broadByTime?exists>${dutyRecord.broadByTime.notified}</#if>"/>
							</td>
						</tr>
						<tr id="editTr">
							<td align="right" width="35%">
								代播开始时间:
							</td>
							<td align="left" width="65%">
								<input type="text" id="btbStartTime" ztype="startdatetime" verify="代播开始时间|NotNull" name="broadByTime.startTime" value="<#if dutyRecord?exists && dutyRecord.broadByTime?exists>${dutyRecord.broadByTime.startTime}</#if>"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								代播结束时间:
							</td>
							<td align="left" width="65%">
								<input type="text" id="btbEndTime" ztype="enddatetime" name="broadByTime.endTime" value="<#if dutyRecord?exists && dutyRecord.broadByTime?exists && dutyRecord.broadByTime.endTime?exists>${dutyRecord.broadByTime.endTime}</#if>"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								代播被代标志:
							</td>
							<td align="left" width="65%">
								<input type="radio" name="broadByTime.broadByFlag" value="D" checked="checked"/>代播
								<input type="radio" name="broadByTime.broadByFlag" value="B"/>被代
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								对方站名称:
							</td>
							<td align="left" width="65%">
								<div ztype="select" name="broadByTime.broadByStation.id" verify="NotNull" id="broadByStation">
									<ul>
										<#if broadByStationList?exists>
											<#list broadByStationList as broadByStation>
												<#if dutyRecord?exists && dutyRecord.broadByTime?exists && (dutyRecord.broadByTime.broadByStation.id == broadByStation.id)>
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
								<textarea cols="25" rows="8" ztype="textarea" name="broadByTime.broadResult"><#if dutyRecord?exists && dutyRecord.broadByTime?exists>${dutyRecord.broadByTime.broadResult}</#if></textarea>
							</td>
						</tr>
						<tr>
							<td align="right" width="35%">
								备注:
							</td>
							<td align="left" width="65%">
								<textarea cols="25" rows="8" ztype="textarea" name="broadByTime.rmks"><#if dutyRecord?exists && dutyRecord.broadByTime?exists && dutyRecord.broadByTime.rmks?exists>${dutyRecord.broadByTime.rmks}</#if></textarea>
							</td>
						</tr>
						<#if !dutyRecord?exists>
							<tr><td colspan="2">尚未结束的代播信息</td></tr>
							<tr>
								<td colspan="2">
									<div>
										<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0" id="broadByTimeTable">
											<tr>
												<th>&nbsp;</th>
												<th>业务</th>
												<th>开始时间</th>
												<th>代播类型</th>
												<th>代播台站</th>
												<th>代播原因</th>
											</tr>
											<tr>
												<td align="center"><input type="radio" value="" name="selectBbt" onclick="chooseBbt(this);" checked="checked"/></td>
												<td colspan="5" align="center">新建一条代播记录</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</#if>
						<tr>
							<td style="width:50%;text-align: right;border: 0px;background: #fff;padding-right: 5px;" colspan="2">
								<button ztype="button" type="button">确定</button>
								<button ztype="button" type="button">取消</button>
							</td>
						</tr>
					</table>
    			</#if>
			</form>
    	</div>
    </div>
</body>
</html>
