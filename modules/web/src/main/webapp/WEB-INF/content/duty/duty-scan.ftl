<html>
	<head>
		<link type="text/css" rel="stylesheet" href="${request.contextPath}/css/style.css" />
		<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/jquery-ui-1.8.4.custom/css/redmond/jquery-ui-1.8.4.custom.css"/>
		<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/tj/tjValidate/jquery.tj.validate.css"/>
		<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/tj/tjStyle/jquery.tj.style.css"/>
		<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/select/skin/blue/jquery.select.css"/>
		<style type="text/css">
		html{overflow-x:hidden}
		</style>
		<script type="text/javascript" src="${request.contextPath}/plugin/jquery-ui-1.8.4.custom/js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="${request.contextPath}/plugin/jquery-ui-1.8.4.custom/js/jquery-ui-1.8.4.custom.min.js"></script>
		<script type="text/javascript" src="${request.contextPath}/plugin/select/jquery.select.js"></script>
		<script type="text/javascript" src="${request.contextPath}/plugin/selectbox/jquery.selectbox.js"></script>
		<script type="text/javascript" src="${request.contextPath}/plugin/tj/tjStyle/jquery.tj.style.js"></script>
		<script type="text/javascript" src="${request.contextPath}/plugin/tj/tjValidate/jquery.tj.validate.js"></script>
		<script type="text/javascript" src="${request.contextPath}/plugin/Form/jquery.form.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$('button:contains("审核意见")').click(function(){
					$('#checkDiv').show();
					$('button:contains("审核意见")').hide();
				});
	
				$('button:contains("取消")').click(function(){
					$('#checkDiv').hide();
					$('button:contains("审核意见")').show();
				});

				$('#chooseDept').change(function(){
					var dpId = $(this).val();
					var url = '${request.contextPath}/duty/duty!scanDutyChangeDept.blank?dpId=' + dpId + '&rd=' + Math.random();
					$(window.parent.document).find('iframe').attr('src',url);
				});
				
				$('#toleft').hover(function(){
					$(this).attr('src','${request.contextPath}/img/toleft2.jpg');
				},function(){
					$(this).attr('src','${request.contextPath}/img/toleft.jpg');
				});
				$('#toleft').click(function(){
					<#if dutyScan?exists && dutyScan.preDuty?exists>
					 scanAnotherDuty(${dutyScan.preDuty.id});
                    </#if>  
				});
				$('#toright').hover(function(){
					$(this).attr('src','${request.contextPath}/img/toright2.jpg');
				},function(){
					$(this).attr('src','${request.contextPath}/img/toright.jpg');
				});
				$('#toright').click(function(){
					<#if dutyScan?exists && dutyScan.nextDuty?exists>
					scanAnotherDuty(${dutyScan.nextDuty.id});
					</#if>
				});
			});
	
			//添加审核信息成功后的回调方法
			function onSuccess(data){
				if (data.result == true){
					var html = '<tr><input type="hidden" id="checkId" value="'+data.id+'"/>';
					html += '<td align="center">'+data.empName+'</td>';
					html += '<td align="left" style="white-space:normal;">'+data.content+'</td>';
					html += '<td align="center">'+data.startTime+'</td>';
					html += '<td align="center"><a href="#" onclick="delCheck(this)">删除</a></td></tr>';
					if ($('#noCheckTr:visible').length > 0){
						$('#noCheckTr').hide();
					}
					$('#checks_table').append(html);
					$('button:contains("取消")').trigger('click');
				}else {
					tjAlert('审核信息添加失败！');
				}
			}
	
			//点击上一班
			function scanAnotherDuty(id){
				var url = '${request.contextPath}/duty/duty!scanDuty.blank?id=' + id + '&rd=' + Math.random();
				$(window.parent.document).find('iframe').attr('src',url);
			}
		
			//删除审核意见
			function delCheck(obj){
				var checkId = $(obj).parents('tr').find('input[type="hidden"]:first').val();
				$.ajax({
					  type: 'POST',
					  url: '${request.contextPath}/duty/duty!delCheckAjax.ajax',
					  dataType: "json",
					  data: {checkId:checkId},
					  async: false,
					  success: function(data){
						if (data.result == true){
							$('#checks_table input[type="hidden"]').each(function(){
								var id = $(this).val();
								if (id == parseInt(data.id)){
									$(this).parents('tr').remove();
								}
							});
							if ($('#checks_table tr:gt(0)').length == 0){
								$('#checks_table').append('<tr id="noCheckTr"><td colspan="4">暂无领导批示信息</td></tr>');
							}
						}else {
							tjAlert('审核信息删除失败！');
						}
					}
				});
			}
		</script>
		<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
	</head>
	<body>
		<div id="dutyScanDiv">
			<#if depts?exists && (depts?size>0)>
				<#if dutyScan?exists>
				    <div style="width:496px;">
						<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" style="background:url(${request.contextPath}/img/left_title_background.jpg); height:50px; ">
							<tr>
								<#if dutyScan.preDuty?exists>
									<td width="80" align="right" valign="top" style="cursor: pointer"><img id="toleft" src="${request.contextPath}/img/toleft.jpg" style="margin-top:3px;" alt="上一页" ></td>
								<#else>
									<td width="80" align="right" valign="top"><div style="padding-top:10px;text-align:center;"><br/>无值班记录</div></td>
								</#if>
								<td align="center">
									<div style="font-family:黑体;font-size:20px;">值班记录</div>
								</td>
								<#if dutyScan.nextDuty?exists>
				 					<td width="80" align="left" valign="top" style="cursor: pointer"><img id="toright" src="${request.contextPath}/img/toright.jpg" style="margin-top:3px;" alt="下一页"></td>
				 				<#else>
				 					<td width="80" align="left" valign="top"><div style="padding-top:10px;text-align:center;"><br/>无值班记录</div></td>
				 				</#if>
							</tr>
						</table>
					 </div>
					 <div style="width:488px;margin-left:3px;">
					 	<table align="center" border="0" cellpadding="0" cellspacing="0"
								class="work_info_table" style="width:100%">
							<tr>
								<th colspan="7" align="center">
									<strong>
										${dutyScan.startTime?string("yyyy年MM月dd日")} &nbsp;	${dutyScan.week}
										<#if depts?exists && (depts?size>1)>
											<select id="chooseDept">
												<#list depts as dept>
													<#if dept.name == dutyScan.group.dept.name>
														<option selected="selected" value="${dept.id }">${dept.name}</option>
													<#else>
														<option value="${dept.id }">${dept.name}</option>
													</#if>
												</#list>
											</select>
										<#else>
											${dutyScan.group.dept.name}
										</#if>
										${dutyScan.schName}
										${dutyScan.startTime?string(" HH:mm -")} ${dutyScan.endTime?string(" HH:mm")}
											<#if dutyScan.weather?exists>${dutyScan.weather}&nbsp;${dutyScan.temperature}℃</#if>
									</strong>
								</th>
							</tr>
							<tr>
								<td align="right" nowrap="nowrap">
									<strong>班组：</strong>
								</td>
								<td>
									<span class="important">${dutyScan.group.name}</span>
								</td>
								<td align="right" nowrap="nowrap">
									<strong>值班人员：</strong>
								</td>
								<td>
									<span class="important">${dutyScan.staffOnDuty}&nbsp;</span>
								</td>
							</tr>
							<tr>
								<td align="right" nowrap="nowrap">
									<strong>接班记录：</strong>
								</td>
								<td colspan="6">
									<span class="important"><#if dutyScan.onDutyRecord?exists>${dutyScan.onDutyRecord}<#else>本班次并未接班</#if>&nbsp;</span>
								</td>
							</tr>
							<tr>
								<td align="right" nowrap="nowrap">
									<strong>交班记录：</strong>
								</td>
								<td colspan="5">
									<span class="important"><#if dutyScan.offDutyRecord?exists>${dutyScan.offDutyRecord?if_exists}<#else>本班次并未交班</#if>&nbsp;</span>
								</td>
							</tr>
							<tr>
								<td align="right" nowrap="nowrap">
									<strong>值班记录：</strong>
								</td>
								<td colspan="7">
									<#if dutyScan.dutyRecords?exists && (dutyScan.dutyRecords?size>0)>
										<#list dutyScan.dutyRecords as dutyRecord>
											<div>
												<span class="important">
													${dutyRecord_index + 1}.&nbsp;${dutyRecord.content}
												</span>
											</div>
										</#list>
									<#else>
										<div>
											<span class="important"><strong>本次值班记录没有值班日志。</strong>
										</div>
									</#if>
								</td>
							</tr>
							<tr>
								<td align="right" nowrap="nowrap">
									<strong>值班提醒：</strong>
								</td>
								<td colspan="7">
									<#if dutyScan.dutyPrompts?exists && (dutyScan.dutyPrompts?size>0)>
										<#list dutyScan.dutyPrompts as dutyPrompt>
											<div>
												<span class="important">
													${dutyPrompt_index + 1}.&nbsp;${dutyPrompt.empName}提醒：${dutyPrompt.content}
												</span>
											</div>
										</#list>
									<#else>
										<div>
											<span class="important"><strong>本次值班记录没有值班提醒。</strong>
										</div>
									</#if>
								</td>
							</tr>
						</table>
					</div>
					<div id="checkDiv" style="display:none;width:488px;margin-left:3px;">
						<form action="${request.contextPath}/duty/duty!addCheckAjax.action" method="post" id="checkForm" ztype="form">
							<input type="hidden"  id="dutyId" name="id" value="${dutyScan.id}"/>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="work_info_table">
								<tr>
									<td align="left">
										<strong>领导意见：</strong>
									</td>
								</tr>
								<tr>
									<td colspan="7">
										<textarea rows="6" cols="65" ztype="textarea" id="checkContent" name="check" verify="审核意见|NotNull&&Length<200"></textarea>
									</td>
								</tr>
								<tr> 
									<td align="right" colspan="8">
										<button ztype="button" type="submit">保存</button>
										<button ztype="button" type="button">取消</button>
									</td>  
								</tr>
							</table>
						</form>
					</div>
					<#if dutyScan.updDate?exists>
						<div id="checkButtonDiv" style="width:488px;margin-left:3px;margin-bottom:6px;text-align: right;">
						<@security.authorize ifAnyGranted="ROLE_TEKOFFICER,ROLE_DIRECTOR,ROLE_ADMIN,ROLE_GOVERNOR,ROLE_OFFICER">
						<button ztype="button" type="button">审核意见</button>
                        </@security.authorize>
						</div>
					</#if>
					<div id="dutyChecks" style="width:488px;margin-left:3px;">
						<table id="checks_table" width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
							<tr>
								<th nowrap="nowrap"> 批示领导 </th>
								<th> 内容 </th>
								<th nowrap="nowrap"> 时间 </th>
								<th nowrap="nowrap"> 操作 </th>
							</tr>
							<#if dutyScan.dutyChecks?exists && (dutyScan.dutyChecks?size>0)>
								<#list dutyScan.dutyChecks as dutyCheck>
									<tr>
										<input type="hidden" id="checkId" value="${dutyCheck.id}"/>
										<td align="center">${dutyCheck.empName }</td>
										<td align="left" style="white-space:normal;">${dutyCheck.content}</td>
										<td align="center">${dutyCheck.startTime?string("MM月dd日 HH:mm")}</td>
										<td align="center">
											<#if dutyCheck.empName == curUser.name>
												<a href="#" onclick="delCheck(this);">删除</a>
											<#else>
												--
											</#if>	
										</td>
									</tr>
								</#list>
							<#else>
								<tr id="noCheckTr"><td colspan="4"><span style="color:red;">暂无领导批示信息</span></td></tr>
							</#if>
						</table>
					</div>
				<#else>
					 <div style="width:488px;margin-left:3px;">
						<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" style="background:url(${request.contextPath}/img/left_title_background.jpg); height:50px; ">
							<tr>
								<td width="80" align="right" valign="top"><div style="padding-top:10px;text-align:center;"><br/>无值班记录</div></td>
								<td align="center">
									<div style="font-family:黑体;font-size:20px;">值班记录</div>
								</td>
			 					<td width="80" align="left" valign="top"><div style="padding-top:10px;text-align:center;"><br/>无值班记录</div></td>
							</tr>
						</table>
					 </div>
					 <div style="width:488px;margin-left:3px;">
					 	<table align="center" border="0" cellpadding="0" cellspacing="0"
								class="work_info_table" style="width:100%">
							<tr>
								<th colspan="7" align="center">
									<strong>
										<#if depts?exists && (depts?size>1)>
											<select id="chooseDept">
												<#list depts as dept>
													<#if dpId?exists && dept.id == dpId>
														<option selected="selected" value="${dept.id }">${dept.name}</option>
													<#else>
														<option value="${dept.id }">${dept.name}</option>
													</#if>
												</#list>
											</select>
										<#else>
											${curUser.dept.name}
										</#if>
									</strong>
								</th>
							</tr>
							<tr>
								<td>当前部门没有任何值班记录</td>
							</tr>
						</table>
					</div>
				</#if>
			<#else>
				<div><span>无任何值班部门权限，无法浏览值班记录！</span></div>
			</#if>
		</div>
	</body>
</html>
