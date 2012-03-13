<html>
	<head>
		<style type="text/css">
			.navigation_bar{
				width:98%;
				font-size:14px;
				margin-bottom:4px;
				text-align:right;
			}
		</style>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('.work_info_table, .work_data_table').each(function(){
					$(this).attr('width', '100%').attr('border', '0').attr('cellpadding', '0').attr('cellspacing', '0');
				});
				
				var recordsArray = [];
				var promptsArray = [];
				
				//标签页设定
				$("#tab").tabInit();

				//帮助永远显示 
				$("#tab").showTab(3);

				//如果存在可以继续填写值班日志的班（包括跨天班），则显示填写值班日志，否则不显示
				<#if alreadyShiftDuties?exists && (alreadyShiftDuties?size>0)>
					$("#tab").showTab(2);
				<#else>
					$("#tab").hideTab(2);
				</#if>

				//如果有跨天班可以交接，则显示跨天交接，否则不显示
				
				<#if crossAndLastDuty?exists && crossAndLastDuty.shift>
					$("#tab").showTab(1);
				<#else>
					$("#tab").hideTab(1);
				</#if>

				//如果有班可以交接，则显示交接班，否则不显示
				<#if canShiftDuties?exists && (canShiftDuties?size>0)>
					$("#tab").showTab(0);
				<#else>
					$("#tab").hideTab(0);
				</#if>


				$('button:contains("添加值班记录")').click(function(){
					$('#toAddRecord').trigger('click');
				});
				$('button:contains("添加值班提醒")').click(function(){
					$('#toAddPrompt').trigger('click');
				});
				$('button:contains("填写交班记录")').click(function(){
					$('#working_offDutyRecord').hide();
					$('#writing_offDutyRecord').show();
				});

				$('button:contains("确认交班")').click(function(){
					$('button:contains("填写交班记录")').attr('disabled',true).text('完成交接可以下班');
					$('#writing_offDutyRecord').hide();
					var id = $('#hiddenDutyId').val();
					var offDutyRecord = $('#writing_offDutyRecord textarea').text();
					$.ajax({
						  type: 'POST',
						  url: '${request.contextPath}/duty/duty!offDuty.action?rd='+Math.random(),
						  dataType: "json",
						  data: {id:id, offDutyRecord:offDutyRecord},
						  async: false,
						  success: function(data){}
					});
					$('#working_offDutyRecord').show().html($('#writing_offDutyRecord textarea').text());
					
				});

				$('button:contains("取消")').click(function(){
					$('#working_offDutyRecord').show();
					$('#writing_offDutyRecord').hide();
				});

				//初始化确认交班记录按钮
				$('input[type="checkbox"]').checkbox();
				$('#shiftConfirmODR').click(function(){
					if ($(this).attr('checked')){
						$('#shiftODR').show();
					}else{
						$('#shiftODR').hide();
					}
				});
				$('#crossConfirmODR').click(function(){
					if ($(this).attr('checked')){
						$('#crossODR').show();
					}else{
						$('#crossODR').hide();
					}
				});
				
				//交接班表单提交
				$('#shiftConfirm').click(function(){
					$('#shiftDutyId').val($('#shiftSel').tjVal());
					$('#shiftForm').submit();
				});

				$('#shiftForm').ajaxForm({
					type: "post",
					dataType: 'json',
					beforeSubmit: validateForm,
					success: function(data){
						initWork(data);
					}
				});
				
				//跨天交接班表单提交
				$('#crossConfirm').click(function(){
					$('#crossForm').submit();
				});
				
				//跨天交接表单提交
				$('#crossForm').ajaxForm({
					type: "post",
					dataType: 'json',
					beforeSubmit: validateForm,
					success: function(data){
						initWork(data);
					}
				});

				$('#writeConfirm').click(function(){
					$(this).attr('disabled',true);
					$('#writeHiddenId').val($('#writeSel').tjVal());
					$('#writeForm').submit();
				});
				
				//填写值班记录表单提交
				$('#writeForm').ajaxForm({
					type: "post",
					dataType: 'json',
					beforeSubmit: validateForm,
					success: function(data){
						initWork(data);
					}
				});

				//交接班中改变班组选项时
				$('#shiftSel').tjChange(function(){
					var val = $('#shiftSel').tjVal();
					$('#shiftDutyId').val(val);
					<#if canShiftDuties?exists>
						<#list canShiftDuties as canShiftDuty>
							if (${canShiftDuty.id} == val){
								<#if canShiftDuty.preDuty?exists>
									$('#shift .important:eq(0)').html('${canShiftDuty.preDuty.group.name}');
									$('#shift .important:eq(1)').html('${canShiftDuty.preDuty.schName}');
									var text = '';
									<#if canShiftDuty.preDuty.offDutyRecord?exists>
										text = '${canShiftDuty.preDuty.offDutyRecord}';
									<#else>
										text = '前一个班次还没有下班或是下班时忘记填写交班记录';
									</#if>
									$('#shift .important:eq(2)').html(text);
								<#else>
									$('#shift .important:eq(0)').html('本班次是系统中第一个班');
									$('#shift .important:eq(1)').html('本班次是系统中第一个班');
									$('#shift .important:eq(2)').html('本班次是系统中第一个班');
								</#if>
							}
						</#list>
					</#if>
				});
				
				setInterval('checkPromptAjax()', 60000);
			});

			//值班记录悬停
			function importantHover() {
				$('.important_list').hover(function(){
					$(this).css("border","1px #f90 solid");
					$(this).css("background","#fff");
				},function(){
					$(this).css("border","1px transparent solid");
					$(this).css("background","transparent");
				});
			}


			//生成上班页面的方法
			function initWork(data) {
				//隐藏标签页
				$('#shiftDiv').hide();
				//显示上班页面
				$('#working').show();
				//初始化页面信息
				$('#working_title').html(data['date']+' '+data['week']+' '+data['schName']+' '+data['time']+' '+data['weather']+' '+data['temperature']+"℃");
				$('#working_group').html(data['group']);
				$('#working_people').html(data['people']);
				$('#last_working_people').html(data['preDutyGroup']);
				$('#working_offDutyRecord').html(data['offDutyRecord']);
				$('#working_last_offDutyRecord').html(data['preDutyOffRecord']);
				if (!data['canClick']){
					$('button:contains("填写交班记录")').attr('disabled',true).text('完成交接可以下班');
				}

				
				//值班记录
				recordsArray = eval(data['records']);
				updateRecords();

				//值班提醒
				promptsArray = eval(data['prompts']);
				updatePrompts();

				//添加值班记录的url
				var url = $('#toAddRecord').attr('url');
				$('#toAddRecord').attr('url', url + data['id']);
				//添加值班提醒的url
				var url = $('#toAddPrompt').attr('url');
				$('#toAddPrompt').attr('url', url + data['id']);
				//用于交班的dutyId
				$('#hiddenDutyId').val(data['id']);
				//用于显示当前值班提醒的dutyId
				var url = $('#toShowPrompts').attr('url');
				$('#toShowPrompts').attr('url', url + data['id']);
			}

			//ajaxForm验证方法
			function validateForm(arr, thisForm){
				if (thisForm.data('tjForm').checkSubmit()) {
					$('button:contains("交接班")').attr('disabled',true);
					return true;
				}else{
					return false;					
				}
			}

			//刷新值班记录列表
			function updateRecords(record, index){
				var editUrl = '${request.contextPath}/duty/duty-record!toEdit.ajax?id=';
				var delUrl = '${request.contextPath}/duty/duty-record!delete.ajax?id=';
				$('#recordsTable').empty();

				if (record != undefined){
					var flag = 0;
					for(var i in recordsArray){
						if (recordsArray[i].id == record.id){
							recordsArray[i] = record;
							flag++;
							break;
						}
					}
					if (flag == 0){
						recordsArray.push(record);
					}
				}
				//如果从数组中删除id为index的元素
				if (index != undefined){
					recordsArray = $.grep(recordsArray,
						function(record,i){
							return record.id != index;
						});
				}
				if (recordsArray.length > 0){
					var html = '';
					for(var i in recordsArray){
						var index = parseInt(i) + 1;
						html += '<div class="important_list"><div style="float:right;width:100px;text-align:right;"><a ztype="popUp" title="编辑值班记录" url="'+editUrl+recordsArray[i].id+'" href="#">编辑</a>&nbsp;&nbsp;';
						html += '<a url="'+delUrl+recordsArray[i].id+'" onclick="delRecord(this)" href="#">删除</a></div>';
						html += index + '. ' +recordsArray[i].content + '</div>';
					}
					$('#recordsTable').append(html).init_style();
				}else {
					var nothing = '<div class="important_list"><span class="important">';
					nothing += '<strong>本次值班记录还没有任何值班记录,请通过"添加值班记录"功能添加!</strong>';
					nothing += '</span></div>';
					$('#recordsTable').append(nothing);
				}

				importantHover();
			}

			//刷新值班提醒列表
			function updatePrompts(prompt, index) {
				var editUrl = '${request.contextPath}/duty/duty-prompt!toEdit.ajax?id=';
				var delUrl = '${request.contextPath}/duty/duty-prompt!delete.ajax?id=';
				$('#promptsTable').empty();

				if (prompt != undefined){
					var flag = 0;
					for(var i in promptsArray){
						if (promptsArray[i].id == prompt.id){
							promptsArray[i] = prompt;
							flag++;
							break;
						}
					}
					if (flag == 0){
						promptsArray.push(prompt);
					}
				}
				//如果从数组中删除id为index的元素
				if (index != undefined){
					promptsArray = $.grep(promptsArray,
						function(prompt,i){
							return prompt.id != index;
						});
				}
				if (promptsArray.length > 0){
					var html = '';
					for(var i in promptsArray){
						var index = parseInt(i) + 1;
						html += '<div class="important_list"><div style="float:right;width:100px;text-align:right;"><a ztype="popUp" title="编辑值班提醒" url="'+editUrl+promptsArray[i].id+'" href="#">编辑</a>&nbsp;&nbsp;';
						html += '<a url="'+delUrl+promptsArray[i].id+'" onclick="delPrompt(this)" href="#">删除</a></div>';
						html += index + '. ' +promptsArray[i].content + '</div>';
					}
					$('#promptsTable').append(html).init_style();
				}else {
					var nothing = '<div class="important_list"><span class="important">';
					nothing += '<strong>本次值班记录还没有任何值班提醒,请通过"添加值班提醒"功能添加!</strong>';
					nothing += '</span></div>';
					$('#promptsTable').append(nothing);
				}

				importantHover();
			}

			//删除值班提醒
			function delPrompt(obj){
				var url = $(obj).attr('url');
				$.getJSON(url,{},function(data){
					if (data.result == true){
						updatePrompts(undefined, data.id);
					}
				});
			}

			//删除值班记录
			function delRecord(obj){
				var url = $(obj).attr('url');
				$.getJSON(url,{},function(data){
					if (data.result == true){
						updateRecords(undefined, data.id);
					}
				});
			}

			//细化到分钟的定时提醒功能
			function checkPromptAjax(){
				if ($('#working').css('display') != 'none'){
					$.post('${request.contextPath}/duty/duty-prompt!checkPromptAjax.ajax',
							function(data){
								if (data.result == true){
									$('#toShowPrompts').trigger('click');
								}
							}
						);
				}
			}
		</script>
	</head>
	<body>
		<div id="shiftDiv">
			<div>
				<span class="work_clew_td" style="color:black;"><img src="${request.contextPath}/img/info.png"
				style="margin-bottom: -4px;" /> 您可以在【交接班】栏中进行交接班，或在【值班记录】栏中继续填写值班记录</span>
			</div>
			<div class="tab" id="tab">
				<ul> 
				    <li>交接班</li>
				    <li>跨天交接</li>
				    <li>填写值班记录</li>	
				    <li>帮助</li>
				</ul>
				<div id="shift">
					<#if canShiftDuties?exists && (canShiftDuties?size>0)>
						<form id="shiftForm" name="shiftForm" ztype="form" action="${request.contextPath}/duty/duty!toWork.ajax">
							<input type="hidden" name="id" id="shiftDutyId"/>
							<div>
								<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<th align="center" colspan="4">
											&lt;&lt;交班班次&gt;&gt;
										</th>
									</tr>
									<tr>
										<td align="right" width="20%">
											部门名称：
										</td>
										<td align="left" width="30%">
											<html:hidden styleId="dpId" property="dpId" value="${dept.id}"/>
											${dept.name}
										</td>
										<td colspan="2">
											&nbsp;
										</td>
									</tr>
									<tr>
										<td align="right" width="20%">
											日期：
										</td>
										<td align="left" width="30%">
											${today?string("yyyy年MM月dd日")}
										</td>
										<td align="right" width="20%">
											选择您要上的班次：
										</td>
										<td align="left" width="30%">
											<div id="shiftSel" ztype="select">
												<ul>
													<#list canShiftDuties as canShiftDuty>
														<#if !canShiftDuty_has_next>
															<#assign lastCanShiftDuty = canShiftDuty>
															<li val="${canShiftDuty.id}" selected="selected">${canShiftDuty.schName}(${canShiftDuty.group.name})</li>
														<#else>
															<li val="${canShiftDuty.id}">${canShiftDuty.schName}(${canShiftDuty.group.name})</li>
														</#if>
													</#list>
												</ul>
											</div>
										</td>
									</tr>
								</table>
							</div>
							<div>
								<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<th align="center" colspan="4" style="font-size: 16px;"><strong>确认交班信息</strong></th>
									</tr>
									<tr>
										<td align="right" width="20%" style="font-size: 14px;">
											交班班组：
										</td>
										<td>
											<span class='important'>
												<#if lastCanShiftDuty.preDuty?exists>
													${lastCanShiftDuty.preDuty.group.name}
												<#else>
													本班次是系统中第一个班
												</#if>
											</span>
										</td>
										<td align="right" width="20%" style="font-size: 14px;">
											交班班次：
										</td>
										<td>
											<span class='important'>
												<#if lastCanShiftDuty.preDuty?exists>
													${lastCanShiftDuty.preDuty.schName}
												<#else>
													本班次是系统中第一个班
												</#if>
											</span>
										</td>
									</tr>
									<tr>
										<td align="right" width="20%" style="font-size: 14px;">交班日志：</td>
										<td colspan="3" style="font-size: 14px;">
											<span class="important">
												<#if lastCanShiftDuty.preDuty?exists>
													<#if lastCanShiftDuty.preDuty.offDutyRecord?exists>
														${lastCanShiftDuty.preDuty.offDutyRecord}
													<#else>
														前一个班次还没有下班或是下班时忘记填写交班记录
													</#if>
												<#else>
													本班次是系统中第一个班
												</#if>
											</span>
										</td>
									</tr>
									<tr>
										<td align="right" width="20%" style="font-size: 14px;">是否确认上一个班的交班记录：</td>
										<td colspan="3" align="left">
											<#if lastCanShiftDuty?exists && lastCanShiftDuty.preDuty?exists && lastCanShiftDuty.preDuty.offDutyRecord?exists>
												<input id="shiftConfirmODR" type="checkbox" checked="checked" name="confirm"/>
											<#else>
												<input id="shiftConfirmODR" type="checkbox" name="confirm" />
											</#if>
										</td>
									</tr>
									<#if lastCanShiftDuty?exists && lastCanShiftDuty.preDuty?exists && lastCanShiftDuty.preDuty.offDutyRecord?exists>
										<tr id="shiftODR" style="display: none;">
									<#else>
										<tr id="shiftODR">
									</#if>
										<td align="right" width="20%" style="font-size: 14px;">填写接班记录：</td>
										<td colspan="3" align="left">
											<textarea rows="4" cols="10" ztype="textarea" name="OnDutyRecord"></textarea>
										</td>
									</tr>
									<tr>
										<td align="right" width="20%" style="font-size: 14px;">
											天气：
										</td>
										<td align="left" width="30%" style="font-size: 14px;">
											<div name="weather" ztype="select" verify="NotNull">
												<ul>
													<li val="晴">晴</li>
													<li val="多云">多云</li>
													<li val="阴">阴</li>
													<li val="阵雨">阵雨</li>
													<li val="小雨">小雨</li>
													<li val="中雨">中雨</li>
													<li val="大雨">大雨</li>
													<li val="暴雨">暴雨</li>
													<li val="小雪">小雪</li>
													<li val="中雪">中雪</li>
													<li val="大雪">大雪</li>
													<li val="暴雪">暴雪</li>
													<li val="大雾">大雾</li>
													<li val="大风">大风</li>
												</ul>
											</div>
										</td>
										<td align="right" width="20%" style="font-size: 14px;">
											室温(℃)：
										</td>
										<td align="left" width="30%" style="font-size: 14px;">
											<input type="text" id="shiftTemperature" name="temperature" ztype="text" verify="温度|NotNull" value="20" onfocus="this.select();"/> 
										</td>
									</tr>
									<tr>
										<td align="center" colspan="4" style="font-size: 14px;">
											<table class="frame_table">
												<tr>
													<td style="width:50%;text-align: right;border: 0px;background: #fff;padding-right: 5px;" ><button ztype="button" type="button" id="shiftConfirm">交接班</button></td>
													<td style="width:50%;text-align: left;border: 0px;background: #fff;padding-left: 5px"><button ztype="button" type="button">返    回</button></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<div>
								${message}
							</div>
						</form>
					</#if>
				</div>
				<div id="crossDay">
					<#if crossAndLastDuty?exists && crossAndLastDuty.shift>
						<form id="crossForm" name="crossForm" action="${request.contextPath}/duty/duty!toWork.ajax" ztype="form">
							<input type="hidden" name="id" value="${crossAndLastDuty.id}"/>
							<div>
								<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<th align="center" colspan="4">
											&lt;&lt;交班班次&gt;&gt;
										</th>
									</tr>
									<tr>
										<td align="right" width="20%" >
											部门名称：
										</td>
										<td align="left" width="30%" style="font-size: 14px;">
											${dept.name}
										</td>
										<td align="right" width="20%" style="font-size: 14px;">
											班组：
										</td>
										<td align="left" width="30%" style="font-size: 14px;">
											${crossAndLastDuty.group.name}
										</td>
									</tr>
									<tr>
										<td align="right" width="20%" style="font-size: 14px;">
											日期：
										</td>
										<td align="left" width="30%" style="font-size: 14px;">
											${yesterday?string("yyyy年MM月dd日")}
										</td>
										<td align="right" width="20%" style="font-size: 14px;">
											您要上的班次：
										</td>
										<td align="left" width="30%" style="font-size: 14px;">
											${crossAndLastDuty.schName}
										</td>
									</tr>
								</table>
							</div>
							<div id="crossDay-shift">
								<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<th align="center" colspan="4" style="font-size: 16px;"><strong>确认交班信息</strong></th>
									</tr>
									<tr>
										<td align="right" width="20%" style="font-size: 14px;">
											交班班组：
										</td>
										<td>
											<span class='important'>
												${crossAndLastDuty.preDuty.group.name}
											</span>
										</td>
										<td align="right" width="20%" style="font-size: 14px;">
											交班班次：
										</td>
										<td>
											<span class='important'>
												${crossAndLastDuty.preDuty.schName}
											</span>
										</td>
									</tr>
									<tr>
										<td align="right" width="20%" style="font-size: 14px;">交班日志：</td>
										<td colspan="3" style="font-size: 14px;">
											<span class="important">
												<#if crossAndLastDuty.preDuty.offDutyRecord?exists>
													${crossAndLastDuty.preDuty.offDutyRecord}
												<#else>
													前一个班次还没有下班或是下班时忘记填写交班记录
												</#if>
											</span>
										</td>
									</tr>
									<tr>
										<td align="right" width="20%" style="font-size: 14px;">是否确认上一个班的交班记录：</td>
										<td colspan="3" align="left">
											<#if crossAndLastDuty.preDuty.offDutyRecord?exists>
												<input id="crossConfirmODR" type="checkbox" checked="checked" name="confirm"/>
											<#else>
												<input id="crossConfirmODR" type="checkbox" name="confirm"/>
											</#if>
										</td>
									</tr>
									<#if crossAndLastDuty.preDuty.offDutyRecord?exists>
										<tr id="crossODR" style="display: none;">
									<#else>
										<tr id="crossODR">
									</#if>
										<td align="right" width="20%" style="font-size: 14px;">填写接班记录：</td>
										<td colspan="3" align="left">
											<textarea rows="4" cols="10" ztype="textarea" name="OnDutyRecord"></textarea>
										</td>
									</tr>
									<tr>
										<td align="right" width="20%" style="font-size: 14px;">
											天气：
										</td>
										<td align="left" width="30%" style="font-size: 14px;">
											<div name="weather" ztype="select" verify="NotNull">
												<ul>
													<li val="晴">晴</li>
													<li val="多云">多云</li>
													<li val="阴">阴</li>
													<li val="阵雨">阵雨</li>
													<li val="小雨">小雨</li>
													<li val="中雨">中雨</li>
													<li val="大雨">大雨</li>
													<li val="暴雨">暴雨</li>
													<li val="小雪">小雪</li>
													<li val="中雪">中雪</li>
													<li val="大雪">大雪</li>
													<li val="暴雪">暴雪</li>
													<li val="大雾">大雾</li>
													<li val="大风">大风</li>
												</ul>
											</div>
										</td>
										<td align="right" width="20%" style="font-size: 14px;">
											室温(℃)：
										</td>
										<td align="left" width="30%" style="font-size: 14px;">
											<input type="text" id="crossTemperature" name="temperature" ztype="text" verify="温度|NotNull" onfocus="this.select();"/> 
										</td>
									</tr>
									<tr>
										<td align="center" colspan="4" style="font-size: 14px;">
											<table style="width:100%" cellpadding="0" border="0">
												<tr>
													<td style="width:50%;text-align: right;border: 0px;background: #fff;padding-right: 5px;" ><button ztype="button" type="button" id="crossConfirm">交接班</button></td>
													<td style="width:50%;text-align: left;border: 0px;background: #fff;padding-left: 5px"><button ztype="button" type="button">返    回</button></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<div>
								${message}
							</div>
						</form>
					</#if>
				</div>
				<div id="write">
					<#if alreadyShiftDuties?exists && (alreadyShiftDuties?size>0)>
						<form id="writeForm" name="writeForm" ztype="form" action="${request.contextPath}/duty/duty!toWork.ajax">
							<input type="hidden" id="writeHiddenId" name="id"/>
							<div>
							  	<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
							  		<tr>
										<th align="center" colspan="4">
											&lt;&lt;填写值班记录&gt;&gt;
										</th>
									</tr>
									<tr>
										<td align="right" width="20%">部门名称：</td>
										<td align="left" width="30%">
											${dept.name}
										</td>
										<td align="right" width="20%">值班记录：</td>
										<td align="left" width="30%">
											<div id="writeSel" ztype="select">
												<ul>
													<#list alreadyShiftDuties as alreadyShiftDuty>
														<#if !alreadyShiftDuty_has_next>
															<li val="${alreadyShiftDuty.id}" selected="selected">${alreadyShiftDuty.schName}(${alreadyShiftDuty.startTime?string("MM月dd日")})${alreadyShiftDuty.group.name})</li>
														<#else>
															<li val="${alreadyShiftDuty.id}">${alreadyShiftDuty.schName}(${alreadyShiftDuty.startTime?string("MM月dd日")})${alreadyShiftDuty.group.name})</li>
														</#if>
													</#list>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td align="center" colspan="4">
											<table style="width:100%" cellpadding="0" border="0">
												<tr>
													<td style="width:50%;text-align: right;border: 0px;background: #fff;padding-right: 5px;" ><button ztype="button" type="button" id="writeConfirm">确    定</button></td>
													<td style="width:50%;text-align: left;border: 0px;background: #fff;padding-left: 5px"><button ztype="button" type="button">返    回</button></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<div>
								${message}
							</div>
						</form>
					</#if>
				</div>
				<div id="help">
					<p>>> 欢迎使用值班记录功能，本功能支持： </p>
			  		<p/>
			  		<img src="${request.contextPath}/img/info2.png" />
		  			<span style="color: blue">交接班</span><br/>
		  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您可以通过交接班功能完成上班前与前一班组的交接<br/>
		  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果机房主任事先已经完成本日的排班，则之需要选择相应的班次交接班即可<br/>
		  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果机房主任事先没有完成本日的排班，需要联系机房主任排班<br/>
		  			<img src="${request.contextPath}/img/info2.png" />
		  			<span style="color: blue">继续填写未完成记录</span><br/>
		  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您可以通过填写值班记录功能补录未完成的值班记录<br/>
		  			<img src="${request.contextPath}/img/info2.png" />
		  			<span style="color: blue">今日值班情况</span><br/>
			  		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
						<tr>
							<th align="center" nowrap="nowrap">
								日期
							</th>
							<th align="center" nowrap="nowrap">
								部门名称
							</th>
							<th align="center" nowrap="nowrap">
								班次
							</th>
							<th nowrap="nowrap" width="120">
								上班时间
							</th>
							<th nowrap="nowrap" width="120">
								下班时间
							</th>
							<th align="center" nowrap="nowrap">
								班组
							</th>
							<th align="center" nowrap="nowrap">
								人员（*指班长）
							</th>
							<th align="center" nowrap="nowrap">
								接班时间
							</th>
						</tr>
						<#if (todayDutyList?exists) && (todayDutyList?size>0) >
							<#list todayDutyList as todayDuty>
								<tr>
									<td>
										${today?string("yyyy年MM月dd日")}
									</td>
									<td>
										${dept.name}&nbsp;
									</td>
									<td>
										${todayDuty.schName}&nbsp;
									</td>
									<td>
										${todayDuty.startTime?string("HH:mm")}&nbsp;
									</td>
									<td>
										${todayDuty.endTime?string("HH:mm")}&nbsp;
									</td>
									<td>
										${todayDuty.group.name}&nbsp;
									</td>
									<td>
										${todayDuty.staffOnDuty}
									</td>
									<#if !todayDuty.shift>
										<td class="update-td" align="center"><span style="color:green">未交接班</span></td>
									<#else>
										<td align="center">${todayDuty.updDate?string("yyyy-MM-dd HH:MM")}</td>
									</#if>
								</tr>
							</#list>
						<#else>
							<tr>
								<td colspan="8">今日还没有排班，请联系机房主任排班!</td>
							</tr>
						</#if>
					</table>
				</div>
			</div>
		</div>
		<!-- 值班记录填写主页面 -->
		<div id="working" style="display:none;">
			<div class="navigation_bar">
		        <strong>
			        [ <a id="toShowPrompts" ztype="popUp" title="当前值班提醒" zwidth="700" href="#" url="${request.contextPath}/duty/duty-prompt!viewTodayPrompt.ajax?dutyId=">值班提醒</a> ]&nbsp;
		        </strong>
	        </div>
			<div>
				<form>
					<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<th colspan="7">
								<strong><span id="working_title"></span></strong>
							</th>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">
								<strong>班组</strong>：
							</td>
							<td>
								<span id="working_group" class="important">&nbsp;</span>
							</td>
							<td align="right" nowrap="nowrap">
								<strong>值班人员</strong>：
							</td>
							<td>
								<span id="working_people" class="important">&nbsp;</span>
							</td>
							<td align="center">
								<strong>[ <a id="edit_working_people" href="#" >修改</a> ]</strong>
							</td>
							<td align="right" nowrap="nowrap">
								<strong>前一个班组</strong>：
							</td>
							<td>
								<span id="last_working_people">&nbsp;</span>
							</td>
	
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">
								<strong>前一个班的交班记录</strong>：
							</td>
							<td colspan="6">
								<span id="working_last_offDutyRecord" class="important">&nbsp;</span>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">
								<strong>您的交班记录</strong>：
							</td>
							<td colspan="6">
								<span id="working_offDutyRecord" class="important">&nbsp;</span>
								<div  id="writing_offDutyRecord" style="display:none;">
									<div>
										<textarea rows="4" cols="15" ztype="textarea" name="offDutyRecord">工具齐全，备件完好，设备运行正常。</textarea>
									</div>
									<div>
										<input type="hidden" id="hiddenDutyId" />
										<button ztype="button" type="button">确认交班</button><button ztype="button" type="button">取消</button>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="7" align="center" nowrap="nowrap">
								<div style="margin-top:4px;" align="right">
									<a id="toAddRecord" ztype="popUp" title="添加值班记录" url="${request.contextPath}/duty/duty-record!toAdd.ajax?dutyId="></a><button ztype="button" type="button">添加值班记录</button>
									<a id="toAddPrompt" ztype="popUp" title="添加值班提醒" url="${request.contextPath}/duty/duty-prompt!toAdd.ajax?dutyId="></a><button ztype="button" type="button">添加值班提醒</button>
									<button ztype="button" type="button">填写交班记录</button>
								</div>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">
								<strong>值班记录</strong>：
							</td>
							<td colspan="7">
								<div id="recordsTable">
								</div>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">
								<strong>值班提醒</strong>：
							</td>
							<td colspan="7">
								<div id="promptsTable">
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>
