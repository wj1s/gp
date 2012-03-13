<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('#myForm').form();

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
					checkDeleteButton();
				});

				$('#optionView table tr:eq(1) input').each(function(){
					$(this).blur(function(){
						$('#queryResult table tr:gt(0)').remove();
						$('#isMonitor').remove();
						$('#queryResult').hide();
					});
				});

				$('#dpId').tjChange(function(){
					$('#queryResult table tr:gt(0)').remove();
					$('#isMonitor').remove();
					$('#queryResult').hide();
				});

				$('#queryForm').ajaxForm({
					type: "post",
					dataType: 'json',
					success: function(data){
						if (data.result == true){
							tjAlert('已从所选的排班表中删除');
							window.location.href = '${request.contextPath}/duty/duty!show.action';
						}
					}
				});

				<#if !depts?exists || (depts?size==0)>
					$('button:contains("预览")').button('enable');
				</#if>
			});

			function query(){
				if ($('#myForm').data('tjForm').checkSubmit()){
					$('#isMonitor').remove();
					
					var dpId = $('#dpId').tjVal();
					var startDate = $('#startDate').val();
					var endDate = $('#endDate').val();
					var empName = $('input[name="name"]').val();
					var url = '${request.contextPath}/duty/duty!selectDelStaffAjax.ajax';
					var data = {dpId:dpId,startDate:startDate,endDate:endDate,empName:empName};
					$.ajax({
						url : url ,
						data :data ,
						dataType: 'json',
						type: 'post',
						success: function(data){
							if (data.result == true){
								$('#queryResult').show();
								$('#queryResult table tr:gt(0)').remove();
								$('#allCheck').attr('checked',false);
								var list = eval(data['data']);
								//查到相关数据
								if(list.length > 0){
									var options = ['<li val="全部">全部</li>'];
									var isMoniter = false;
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
										if (obj['moniterName'].indexOf('color') != -1){
											isMoniter = true;
										}
									}
									var optionsStr = '';
									for(var i in options){
										optionsStr += options[i];
									}
									var html = '';
									html += '<tr><td colspan="3">&nbsp;</td><td align="right"><span style="float:left;">班次筛选:</span> <div id="selectDutySchedule" ztype="select" style="float:right;"><ul>';
									html += optionsStr;
									html += '</ul></div></td><td align="right"><button ztype="button" type="button" onClick="deleteSubmit();">删除</button></td></tr>';
									$('#queryResult tr:last').after(html);
									$('#queryResult tr:last').init_style();;
									$('#selectDutySchedule').tjAssemble();

									if (isMoniter){
										$('#optionView table').append('<tr id="isMonitor"><td colspan="8" align="left"><font color="red">删除人员为值班长，系统会默认把本班组的班长设定为第一位值班员</font></td></tr>');
									}else{
										$('#isMonitor').remove();
									}

									//操作各种checkBox验证
									//拼页面到此结束
									checkCheckBox();
									checkAllCheck();
									checkDeleteButton();


									$('input[type="checkbox"]').not('#allCheck').click(function(){
										addOrDelPerson($(this));
										checkDeleteButton();
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
										checkDeleteButton();
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

			//检查复选按钮
			function checkCheckBox(){
				$('input[type="checkbox"]').not($('#allCheck')).each(function(){
					var str = $(this).parent().prev().find('font').text();
					if (str == ''){
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
				if (obj.parent().prev().find('font').attr('color') == '#9901d8'){
					if (obj.attr('checked')){
						obj.parent().prev().find('font').attr('color', '#cacbca');
					}
				}else {
					if (obj.attr('checked') == false){
						obj.parent().prev().find('font').attr('color', '#9901d8');
					}
				}
			}
			//检查删除按钮
			function checkDeleteButton(){
				if ($('#queryResult table tr:visible').length > 2 && $('input[type="checkbox"]:enabled').not($('#allCheck')).length > 0 && 
						$('input[type="checkbox"]:visible:checked').not($('#allCheck')).length > 0){
        		    $('button:contains("删除")').button('enable')
        		}else{
        			 $('button:contains("删除")').button('disable');
        		}
			}


			function deleteSubmit(){
				var empName = $('input[name="name"]').val();
				$('#queryForm').find('input[name="empName"]').val(empName);
				tjConfirm('确认把' + empName + '从所选排班的人员中删除吗？', function(flag){
					if (flag){
						$('[type="checkbox"]:checked').not('#allCheck').each(function(){
							var	id = $(this).prev().val();
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
			<form action="${request.contextPath}/duty/duty!selectDelStaffAjax.ajax" id="myForm">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
					<tr>
					    <th colspan="8">
					        &lt;&lt;值班人员删除条件选择&gt;&gt;
					    </th>
					</tr>
		      		<tr>
						<td align="right">
						    <strong>部门：</strong>
						</td>
						<td align="left">
							<#if depts?exists && (depts?size>0)>
								<div ztype="select" id="dpId" >
									<ul>
								   		<#list depts as dept>
								     		<li val="${dept.id }">${dept.name }</li>
								     	</#list>
							    	</ul>	
							   	</div>
							<#else>
								没有任何部门的值班权限
							</#if>
						</td>
		          		<td align="right">
							<strong>开始日期：</strong>
						</td>
						<td align="left">
							<input name="startDate" style="text-align:center" type="text" id="startDate" ztype="startdate" znow="startnow" verify="开始时间|NotNull"/>
						</td>
						<td align="right">
							<strong>结束日期：</strong>
						</td>
						<td align="left">
							<input name="endDate" style="text-align:center;" type="text" id="endDate" ztype="enddate" verify="结束时间|NotNull"/>
						</td>
						<td align="right">
							<strong>删除人员：</strong>
						</td>
						<td align="left">
							<input type="text" style="text-align:center;" name="name" ztype="auto" zurl="${request.contextPath}/baseinfo/person!autocompleteAjax.action?funModule=duty" verify="人员|NotNull" verify="删除人员|NotNull"/>
						</td>
					</tr>
		            <tr> 
		            	<td align="right" colspan="8">
		            		<button ztype="button" type="button" onclick="query();">预览</button>
						</td>
		  			</tr>
				</table>
			</form>
		</div>
		<div id="queryResult" style="display: none;">
			<form action="${request.contextPath}/duty/duty!deleteStaffAjax.ajax" id="queryForm">
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
							删除所选人员 <input id="allCheck" type="checkbox"  style="border: 0px;background: transparent" disabled="disabled"/>
						</th>
					</tr>
       			</table>
       		</form>
 		</div>
	</body>
</html>
