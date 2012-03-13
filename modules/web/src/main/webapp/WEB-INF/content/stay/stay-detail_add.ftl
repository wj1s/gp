<html>
	<head>
		<script type="text/javascript">
			$(document).ready(function(){
				fillDefaultInfo();

				$('#upStayForm').form();
			});

			function changeSection(obj) {
				var count = parseInt($(obj).val());
				var content = $(obj).parent().next();
				var divCount = content.find('div').length;
				var hint = $(obj).parents('tr').attr('hint');
				if (count > divCount) {
					var contentStr = '<div>时间：<input type="text" id="startTime_'+hint+'" name="startTimes" ztype="timeHM" verify="开始时间|NotNull" style="width:35px;"/> ---- <input type="text" id="endTime_'+hint+'" name="endTimes" ztype="timeHM" verify="结束时间|NotNull" style="width:35px;"/>';
					contentStr += '&nbsp;&nbsp;留守人员：<input type="text" id="empNames_'+hint+'" name="empNames" style="width:200px" ztype="auto" zmode="multi" verify="留守人员|NotNull"/></div>';
					var str = '';
					var diff = count - divCount;
					for ( var i = 0; i < diff; i++) {
						str = str + contentStr;
					}
					content.find('div:last').after(str);
					content.init_style();
					$('#upStayForm').form();
				} else {
					content.find('div:gt(' + (count - 1) + ')').remove();
				}
			}

			function onSuccess(data) {
				if (data.result == true){
					tjAlert("保存成功!");
					window.location.href = '${request.contextPath}/stay/stay-detail!searchDetails.action?dpId='
						+ $('[name="deptId"]').val();
				}else {
					tjAlert("保存失败,请联系管理员!");
				}
			}

			function validateForm(data, thisForm) {
				if (thisForm.data('tjForm').checkSubmit()) {
					if ($('input[name="startIndex"]:checked').length == 0){
						tjAlert("请选择起始标记!");
						return false;
					}else {
						return true;
					}
				}else {
					return false;
				}
			}

			function fillDefaultInfo() {
				var existData = $('#existData');
				var table = $('.work_info_table');
				if(existData.find('div').length != 0){
					existData.find('div').each(function(){
						var index = $(this).find('span:eq(0)').html();
						var count = $(this).find('span:eq(1)').html();
						var findTr = table.find('tr:eq('+index+')');
						var selectObj = findTr.find('select');
						selectObj.val(count);
						changeSection(selectObj);
						
						$(this).find('ul').each(function(index){
							var startTime = $(this).find('li:eq(0)').html();
							findTr.find('div:eq('+index+')').find('input[name="startTimes"]').val(startTime);
							
							var endTime = $(this).find('li:eq(1)').html();
							findTr.find('div:eq('+index+')').find('input[name="endTimes"]').val(endTime);
							
							var people = $(this).find('li:eq(2)').html();
							findTr.find('div:eq('+index+')').find('input[name="empNames"]').val(people);
						});
					});
				}
			}
		</script>
	</head>
	<body>
		<div align="center">
			<form id="upStayForm" name="upStayForm" method="post" action="${request.contextPath}/stay/stay-detail!toGenerateDetails.action" ztype="form">
				<#if stayRule?exists>
					<input type="hidden" name="ruleId" value="${stayRule.id}"/>
				</#if>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
					<tr>
						<th align="center" colspan="4">
							<strong>新增排班留守</strong>
						</th>
					</tr>
					<tr height="30">
						<td align="right" width="20%">
							部门名称：
						</td>
						<td align="left" width="30%">
							${model.dept.name}
							<input type="hidden" name="deptId" value="${model.dept.id}"/>
						</td>
						<td align="right" width="20%">
							排班周期：
						</td>
						<td align="left" width="30%">
							${periodCount}天
							<input type="hidden" name="periodCount" value="${periodCount}"/>
						</td>
					</tr>
					<tr height="30">
						<td align="right" width="20%">排班开始日期：</td>
						<td align="left" width="30%">
							${startTime?string("yyyy-MM-dd")}
							<input type="hidden" name="startTime" value="${startTime?string("yyyy-MM-dd")}" />
						</td>
						<td align="right" width="20%">排班结束日期：</td>
						<td align="left" width="30%">
							${endTime?string("yyyy-MM-dd")}
							<input type="hidden" name="endTime" value="${endTime?string("yyyy-MM-dd")}" />
						</td>
					</tr>		
				</table>
				<div style="width:100%">
					<fieldset>
           				<legend><strong>进行留守排班</strong></legend>
            			<br/>
			            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
			          		<tr>
			                    <th width="20%" nowrap="nowrap">天数</th>
			                    <th width="20%" nowrap="nowrap">分段数</th>
			                    <th width="50%" nowrap="nowrap">排班规则</th>
			                    <th width="10%" nowrap="nowrap">起始标记</th>
			                </tr>
			                <#list 1..periodCount as i>
				                <tr hint="${i}">
			                		<td align="center">第${i}天</td>
			                		<td align="center">
			                				分<select id="periodIndexArray" onChange="changeSection(this)" name="periodIndexArray">
			                						<option value="1">1</option>
			                						<option value="2">2</option>
			                						<option value="3">3</option>
			                						<option value="4">4</option>
			                						<option value="5">5</option>
			                				</select>段
			                		</td>
			                		<td align="center">
		                				<div>
		                					时间：<input type="text" id="startTime_${i}" name="startTimes" ztype="timeHM" verify="开始时间|NotNull" style="width:35px;"/> ---- <input type="text" id="endTime_${i}" name="endTimes" ztype="timeHM" verify="结束时间|NotNull" style="width:35px;"/>
		                					 &nbsp;&nbsp;留守人员：<input type="text" id="empNames_${i}" name="empNames" style="width:200px" ztype="auto" zmode="multi" verify="留守人员|NotNull"/>
		                				</div>		
			                		</td>
			                		<td>
			                			<input type="radio" name="startIndex" value="${i-1}"/>
			                		</td>
			                	</tr>
			                </#list>
			          	</table>
					</fieldset>
				</div>
				<div style="text-align:right;padding-right:20px">
					<button type="button" ztype="button" onClick="window.history.back(-1);">上一步</button>
					<button type="submit" ztype="button">保存</button>
				</div>
			</form>
		</div>
		<div style="display:none" id="existData">
			<#if stayRule?exists && stayRule.stayPeriods?exists && (stayRule.stayPeriods?size>0)>
				<#list stayRule.stayPeriods as stayPeriod>
					<div>
						<span>${stayPeriod.periodIndex}</span>
						<span>${stayPeriod.staySections?size}</span>
						<#list stayPeriod.staySections as staySection>
							<ul>
								<li>${staySection.startTime?string("HH:mm")}</li>
								<li>${staySection.endTime?string("HH:mm")}</li>
								<li>
									<#list staySection.staySectionPeople as person>
										${person.id.empName},
									</#list>
								</li>
							</ul>
						</#list>
					</div>
				</#list>
			</#if>
		</div>
	</body>
</html>
