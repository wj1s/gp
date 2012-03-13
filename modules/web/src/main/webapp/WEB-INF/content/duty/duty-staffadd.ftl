<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('#myForm').form();

				$('#dpId').tjChange(function(){
					var id = $('#dpId').tjVal();
					$.getJSON('${request.contextPath}/baseinfo/dept!getGroupsAjax.action?rd='+Math.random(),
						{'id':id},
						function(data){
							if (data.result == true){
								if ($('#noGroups').length > 0){
									$('#tdGroup').empty();
									var html = '<div ztype="select" name="groupId" id="groupId" verify="NotNull"><ul></ul></div>';
									$('#tdGroup').append(html).init_style();
								}else {
									$('#groupId').tjEmpty();
								}
								var array = eval(data['data']);
								var html = '';
								for(var i in array){
									html += '<li val="'+array[i].value+'">'+array[i].name+'</li>';
								}
								$('#groupId ul').append(html);
								$('#groupId').tjAssemble();
							}else {
								$('#tdGroup').empty().append('<div id="noGroups">本部门没有任何班组</div>');
							}
						}
					);
				});

				$('#optionView table tr:eq(1) input').each(function(){
					$(this).blur(function(){
						$('#queryResult table tr:gt(0)').remove();
						$('#aleadyHave').remove();
						$('#queryResult').hide();
					});
				});

				$('button:contains("预览")').click(function(){
					preview();
				});

				$('#queryForm').ajaxForm({
					type: "post",
					dataType: 'json',
					success: function(data){
						if (data.result == true){
							tjAlert('已添加至所选的班次');
							window.location.href = '${request.contextPath}/duty/duty!show.action';
						}
					}
				});

				//全选按钮点击事件
				$('#allCheck').click(function(){
					//联动复选多选按钮
					if ($(this).attr('checked')){
						$('input[type="checkbox"]:enabled:not(:checked):visible').attr('checked','checked');
					}else{
						$('input[type="checkbox"]:enabled:checked:visible').removeAttr('checked');
					}
					$('input[type="checkbox"]:enabled:visible').not($('#allCheck')).each(function(){
						addOrDelPerson($(this));
					});
					checkAddButton();
				});

				<#if !depts?exists || (depts?size==0) || !groups?exists || (groups?size==0)>
					$('button:contains("预览")').button('enable');
				</#if>
			});

			//检查复选按钮
			function checkCheckBox(){
				$('input[type="checkbox"]').not($('#allCheck')).each(function(){
					var str = $(this).parent().prev().find('font').text();
					if (str != ''){
						$(this).attr("disabled",true);
					}else {
						$(this).attr("disabled",false);
					}
				});
			}
			//检查全选按钮
			function checkAllCheck(){
				if ($('input[type="checkbox"]').not($('#allCheck')).filter(':enabled:visible').length > 0){
					$('#allCheck').attr('disabled',false);
				}else{
					$('#allCheck').attr('disabled',true);
				}
			}

			//增加删除人的方法 
			function addOrDelPerson(obj){
				if (obj.parent().prev().find('font').length == 0){
					if (obj.attr('checked')){
						var name = $('#empName').val();
						var text = obj.parent().prev().text();
						text += ',<font color="#9901d8">' + name + '</font>';
						obj.parent().prev().html(text);
					}
				}else {
					if (obj.attr('checked') == false){
						obj.parent().prev().find('font').remove();
						var text = obj.parent().prev().text();
						if (text.substring(text.length - 1, text.length) == ','){
							obj.parent().prev().text(text.substring(0, text.length - 1));
						}
					}
				}
			}
			//检查添加按钮
			function checkAddButton(){
				if ($('#queryResult table tr:visible').length > 2 && $('input[type="checkbox"]:enabled').not($('#allCheck')).length > 0 && 
						$('input[type="checkbox"]:visible:checked').not($('#allCheck')).length > 0){
					 $('button:contains("添加")').button('enable')
        		}else{
        			 $('button:contains("添加")').button('disable');
        		}
			}
			
			function preview(){
				if ($('#myForm').data('tjForm').checkSubmit()){
					var dpId = $('#dpId1').tjVal();
					var startDate = $('#startDate').val();
					var endDate = $('#endDate').val();
					var empName = $('#empName').val();
					var groupId = $('#groupId').tjVal();
					$.ajax({
						url : '${request.contextPath}/duty/duty!selectAddStaffAjax.ajax',
						data : {dpId:dpId,startDate:startDate,endDate:endDate,empName:empName,groupId:groupId} ,
						dataType: 'json',
						type : 'post',
						success: function(data){
							$('#queryResult').show();
							$('#queryResult table tr:gt(0)').remove();
							$('#alreadyHave').remove();
							$('#allCheck').removeAttr('checked');
							if (data.result == true){
								var list = eval(data['data']);
								if(list.length > 0){
									var options = ['<li val="全部">全部</li>'];
									var alreadyHave = false;
									for (var i = 0; i < list.length; i ++){
										var obj = list[i];
										var html = '';
										html += '<tr><td align="center">(' + obj['dutyRuleName']+ ')'+ obj['schName'] + '</td>';
										html += '<td align="center">' + obj['group_name'] + '</td>';
										html += '<td align="center">' + obj['date'] + '</td>';
										html += '<td align="center">' + obj['staffOnDuty'] + '</td>';
										html += '<td align="center"><input type="hidden" value="' + obj['id'] +
										 '"/><input type="checkbox" style="border: 0px;background: transparent"/></td></tr>';
										$('#queryResult tr:last').after(html);

										var pass = true;
										for(var i in options){
											if ('<li>(' + obj['dutyRuleName']+ ')'+ obj['schName'] + '</li>' == options[i]){
												pass = false;
												break;
											}
										}
										if (pass){
											options.push('<li val="'+'(' + obj['dutyRuleName']+ ')'+ obj['schName']+'">(' + obj['dutyRuleName']+ ')'+ obj['schName'] + '</li>');
										}
										if (obj['staffOnDuty'].substring('color') != -1){
											alreadyHas = true;
										}
									}
									var optionsStr = '';
									for(var i in options){
										optionsStr += options[i];
									}
									var html = '';
									html += '<tr><td colspan="3">&nbsp;</td><td align="right"><span style="float:left;">班次筛选:</span> <div id="selectDutySchedule" ztype="select" style="float:right;"><ul>';
									html += optionsStr;
									html += '</ul></div></td><td align="right"><button ztype="button" type="button" onclick="addSubmit();">添加</button></td></tr>';
									$('#queryResult tr:last').after(html);
									$('#queryResult tr:last').init_style();;
									$('#selectDutySchedule').tjAssemble();

									if (alreadyHave){
										$('#optionView table').append('<tr id="alreadyHave"><td colspan="8" align="left"><font color="red">查询出的排班记录中已有此值班员</font></td></tr>');
									}else{
										$('#isMonitor').remove();
									}

									//拼页面到此结束
									checkCheckBox();
									checkAllCheck();
									checkAddButton();

									$('input[type="checkbox"]').not('#allCheck').click(function(){
										addOrDelPerson($(this));
										checkAddButton();
									});

									$('#selectDutySchedule').tjChange(function(){
										var filter = $('#selectDutySchedule').tjText();
										if (filter == '全部'){
											$('#queryResult table tr').show().find('input[type="checkbox"]').show();
										}else {
											$('#queryResult table tr').not($('tr:has(th)')).not($('tr:last')).each(function(){
												if ($(this).find('td:first').text() != filter){
													$(this).hide().find('input[type="checkbox"]').hide();
												}else {
													$(this).show().find('input[type="checkbox"]').show();
												}
											});
										}

										checkAllCheck();
										checkAddButton();
									});
								}else{
									$('#queryResult').show();
									$('#queryResult table').append('<tr><td colspan="5" align="left"><strong><span style="COLOR: red">没有符合条件的数据</span></strong></td></tr>');
									$('#allCheck').attr('disabled',true);
								}
							}
						}
					});
				}
			}

			function addSubmit(){
				var empName = $('#empName').val();
				$('#queryForm').find('input[name="empName"]').val(empName);
				tjConfirm('确认把' + empName + '添加到所选排班的人员中吗？',function(flag){
					if (flag){
					$('input[type="checkbox"]:checked').not('#allCheck').each(function(){
						var id = $(this).prev().val();
						$(this).after('<input type="hidden" name="duIds" value="'+ id +'"/>');
					});
					$('#queryForm').submit();
					}
				});
			}
		</script>
	</head>
	<body>
		<div id="optionView">
			<form action="${request.contextPath}/duty/duty!selectAddStaffAjax.ajax" id="myForm">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
					<tr>
						<th colspan="10">
						    &lt;&lt;值班人员添加条件选择&gt;&gt;
						</th>
         			</tr>
         			<tr>
		            	<td align="right" >
		                	<strong>部门：</strong>
		             	</td>
             			<td align="left">
             				<#if depts?exists && (depts?size>0)>
             					<div ztype="select" name="dpId" id="dpId1" style="width:80px;">
             						<ul>
             							<#list depts as dept>
             								<li val="${dept.id}">${dept.name}</li>
             							</#list>
             						</ul>
             					</div>
             				<#else>
             					<div>没有任何部门的值班权限</div>
             				</#if>
             			</td>
             			<td align="right">
							<strong>班组：</strong>
						</td>
						<td id="tdGroup">
							<#if groups?exists && (groups?size>0)>
             					<div ztype="select" name="groupId" id="groupId" style="width:80px;">
             						<ul>
             							<li val="-1">全部</li>
             							<#list groups as group>
             								<li val="${group.id}">${group.name}</li>
             							</#list>
             						</ul>
             					</div>
             				<#else>
             					<div id="noGroups">本部门没有任何班组</div>
             				</#if>
						</td>
			            <td align="right">
							<strong>开始日期：</strong>
						</td>
						<td>
							<input name="startDate" style="text-align:center;width:80px;" ztype="startdate" znow="startnow" type="text" id="startDate" verify="开始时间|NotNull"/>
						</td>
						<td align="right">
							<strong>结束日期：</strong>
						</td>
						<td>
							<input id="endDate" style="text-align:center;width:80px;" name="endDate" ztype="enddate" type="text" verify="结束时间|NotNull"/>
						</td>
						<td align="right">
							<strong>添加人员：</strong>
						</td>
						<td>
							<input type="text" style="text-align:center;width:80px;" ztype="auto" zurl="${request.contextPath}/baseinfo/person!autocompleteAjax.action?funModule=duty" verify="人员|NotNull" id="empName" verify="添加人员|NotNull"/>
						</td>
					</tr>
                    <tr> 
	                    <td align="right" colspan="10">
	                    	<button ztype="button" type="button">预览</button>
	                    </td>
                    </tr>
				</table>
			</form>
		</div>
		<div id="queryResult" style="display: none;">
			<form action="${request.contextPath}/duty/duty!addStaffAjax.ajax" id="queryForm">
				<input type="hidden" name="empName" />
       			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
       				<tr>
       					<th width="20%" nowrap="nowrap">
							班次
						</th>
						<th width="20%" nowrap="nowrap">
							班组
						</th>
						<th width="20%" nowrap="nowrap">
							日期
						</th>
						<th width="20%" nowrap="nowrap">
							人员
						</th>
						<th width="20%" nowrap="nowrap">
							添加所选人员 <input id="allCheck" type="checkbox" style="border: 0px; background: transparent" disabled="disabled" />
						</th>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
