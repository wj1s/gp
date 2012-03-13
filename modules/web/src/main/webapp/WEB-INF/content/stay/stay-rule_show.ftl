<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('#myForm').form();
				
				$('button:contains("下一步")').click(function(){
					if (validateForm()){
						$.ajax({
							url:'${request.contextPath}/stay/stay-detail!valToAddDetailAjax.ajax?rd='+Math.random(),
							method:'post',
							data:{deptId:$('#deptId').tjVal(),startTime:$('#startTime').val(),endTime:$('#endTime').val(),periodCoun:$('#periodCoun').val()},
							dataType : 'json',
							success: function(data){
								if(data['success'] == false){
									alert(data['message']);
									return ;
								}else {
									if (data['isExist'] == false){
										$('#myForm').submit();
									} else {
										if (confirm('该时间段内已经存在留守排班记录，是否覆盖')){
											$('#myForm').submit();
										}
									}
								}
							}
						});
					}
				});

				
				$('#deptId').tjChange(function(){
					var deptId = $('#deptId').tjVal();
					$.ajax({
						url:'${request.contextPath}/stay/stay-rule!getRulesAjax.ajax?rd='+Math.random(),
						method:'post',
						data:{deptId:deptId},
						dataType : 'json',
						success: function(data){
							var rules = eval(data['stayRules']);
							$('#ruleDetailTable tr:gt(0)').remove();
							if (rules.length == 0){
								$('<tr><td colspan="4" align="center">本部门没有任何留守规则</td></tr>').insertAfter('#ruleDetailTable tr:last');
							}else {
								var rulesContent = '';
								for (var i = 0; i < rules.length; i ++){
									var obj = rules[i];
									rulesContent += '<tr>';
									rulesContent += '<td align="center">' + obj['deptName'] + '</td>';
									rulesContent += '<td align="center">' + obj['periodCount'] + '天</td>';
									rulesContent += '<td align="center"><a ztype="popUp" title="查看留守排班规则" url="${request.contextPath}/stay/stay-rule!getRuleDetail.ajax?id='+obj['id']+'" zwidth="650">查看</a></td>';
									rulesContent += '</tr>';
								}
								$('#ruleDetailTable').find('tr:last').after(rulesContent);
								$('#ruleDetailTable').init_style();
							}
						}
					});
				});
			});

			function validateForm(){
				if ($('#myForm').data('tjForm').checkSubmit()) {
					return true;
				}else{
					return false;					
				}
			}
			
		</script>
	</head>
	<body>
	<div align="center">
		<form id="myForm" name="stayForm" method="post" action="${request.contextPath}/stay/stay-detail!toAddDetail.action">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
				<tr>
					<th align="center" colspan="4">
						<strong>新增排班留守</strong>
					</th>
				</tr>
				<tr>
					<td align="right" width="20%">
						部门名称：
					</td>
					<td align="left" width="30%">
						<#if deptList?exists && (deptList?size > 0)>
							<div id="deptId" name="deptId" ztype="select">
								<ul>
									<#list deptList as dept>
										<#if deptId?exists && (deptId==dept.id)>
											<li val="${dept.id }" selected="selected">${dept.name }</li>
										<#else>
											<li val="${dept.id }">${dept.name }</li>
										</#if>
									</#list>
								</ul>
							</div>
						<#else>
							当前人员没有任何权限部门
						</#if>
					</td>
					<td align="right" width="20%">
						排班周期（天）：
					</td>
					<td align="left" width="30%">
						<input type="text" ztype="text" name="periodCount" id="periodCount" verify="排班周期|NotNull&&Int&&Value>0">
					</td>
				</tr>
				<tr>
					<td align="right" width="20%">
						排班开始日期：
					</td>
					<td align="left" width="30%">
						<input type="text" id="startTime" name="startTime" ztype="date" value="${startTime}" verify="开始日期|NotNull"/>
					</td>
					<td align="right" width="20%">
						排班结束日期：
					</td>
					<td align="left" width="30%">
						<input type="text" id="endTime" name="endTime" ztype="date" verify="结束日期|NotNull"/>
					</td>
				</tr>
				<tr>
					<td align="right" colspan="4" style="padding-right: 20px">
						<button ztype="button" type="button">下一步</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="width:100%">
		<table id="ruleDetailTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
			<tr>
				<th width="35%" nowrap="nowrap">
					部门名称
				</th>
				<th width="35%" nowrap="nowrap">
					周期数
				</th>
				<th nowrap="nowrap">
					操作
				</th>
			</tr>
			<#if !stayRules?exists || (stayRules?size==0)>
				<tr>
					<td colspan="3" style="text-align: center">本部门没有任何留守规则</td>
				</tr>
			<#else>
				<#list stayRules as stayRule>
					<tr>
						<td style="text-align:center">
							${stayRule.dept.name}
						</td>
						<td style="text-align:center">
							${stayRule.periodCount}天
						</td>
						<td style="text-align:center">
							<a ztype="popUp" title="查看留守排班规则" url="${request.contextPath}/stay/stay-rule!getRuleDetail.ajax?id=${stayRule.id}" zwidth="650">查看</a>
						</td>
					</tr>
				</#list>
			</#if>
		</table>
	</div>
	</body>
</html>
