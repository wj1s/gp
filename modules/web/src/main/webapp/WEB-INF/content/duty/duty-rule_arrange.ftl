<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				if ($('#noDeptPer:visible').length > 0){
					$('button').attr('disabled',true);
				}
				
				//页面加载完成后首先根据规则ajax调出规则详细
				var ruleId = $('#ruleId').tjVal();
			 	if(ruleId!=null && ruleId!='' && ruleId!=undefined){
					getRuleDetail();
	            }
	            //规则变更的操作
				$('#ruleId').tjChange(function(){
					getRuleDetail();
				});

				//部门变更的操作
				$('#deptId').tjChange(function(){
					changeDept();
				});

				//修改规则的操作
				$('button:contains("修改规则")').click(function(){
					//判断是否存在规则，不存在规则则不能进行编辑
					var ruleId = $('#ruleId').tjVal();
					var deptId = $('#deptId').tjVal();
					if(ruleId == null || ruleId == ''){
						tjAlert('请先创建新规则！');
						return false;
					}
					$('#ruleModify').show();
					$('#ruleModifyButton').show();
					$('#ruleDetail').hide();	
					$('#allButton').hide();	
					addOrModifyFlag = 'modify';		
				});

				$('button:contains("排 班")').click(function(){
					var ruleId = $('#ruleId').tjVal();
					var dpId = $('#deptId').tjVal();
					var flag = true;
					$.ajax({
					   url: '${request.contextPath}/baseinfo/dept!getGroupsAjax.action?rd='+Math.random(),
					   data: {id:dpId},
					   async: false,
					   success: function(data){
							if (data.result == false){
								tjAlert('本部门没有任何班组,请先创建班组和班组中的人员!');
								flag = false;
							}
						}
					});
					if (!flag){
						return false;
					}
					
					if(ruleId == null || ruleId == ''){
						tjAlert('请先创建新规则！');
						return false;
					}
					
					toArrangeClass(ruleId);
				});

				//创建新规则操作
				$('button:contains("创建新规则")').click(function(){
					createAddDiv();
				});

				//删除排班
				$('button:contains("删除排班")').click(function(){
					var deptId = $('#deptId').tjVal();
					var delUrl = '${request.contextPath}/duty/duty!toDelete.ajax?dpId='+deptId;
					$(window.parent.document).find("#arrange").load(delUrl);
				});

				$('#schePrev').click(function(){
					createAddDiv();
				});

				//新增规则时运转数下拉选与运转名称之间的联动
				$('#dayPartCount').tjChange(function(){
					var ruleName =  '运转';
					var value = $('#dayPartCount').tjVal();
					switch(value){
						case '1' : ruleName = '一' + ruleName;break;
						case '2' : ruleName = '二' + ruleName;break;
						case '3' : ruleName = '三' + ruleName;break;
						case '4' : ruleName = '四' + ruleName;break;
						case '5' : ruleName = '五' + ruleName;break;
						case '6' : ruleName = '六' + ruleName;break;
						case '7' : ruleName = '七' + ruleName;break;
						case '8' : ruleName = '八' + ruleName;break;
						case '9' : ruleName = '九' + ruleName;break;
						case '10' : ruleName = '十' + ruleName;break;
						default  : ruleName = '';break;
					}
					$('#ruleName').val(ruleName);
				});

				//运转规则选择的下一步操作
				$('#ruleNext').click(function(){
					//跳转到下一步信息
					 var dpName = $('#dept_id').tjText();
					 var dayPartCount = $('#dayPartCount').tjText(); 
					 isRuleExist();
				});

				//编辑提交表单
				$('#ruleModifyNext').click(function(){
					$('#hiddenRuleId').val($('#ruleId').tjVal());
					$('#dutyRuleModifyForm').submit();
				});

				//新增提交表单
				$('#scheNext').click(function(){
					$('#dutySchForm').submit();
				});

				//单击取消按钮的操作
				$('button[name="cannelRule"]').click(function(){
					var arrangeUrl = '${request.contextPath}/duty/duty-rule!arrange.ajax';
					$(window.parent.document).find("#arrange").load(arrangeUrl);
				});

				//表单提交
				$('form[name="dutyRuleForm"]').each(function(){
					$(this).ajaxForm({
						type: "post",
						dataType: 'json',
						beforeSubmit: validateForm,
						success: function(data){
							if (data['result'] == true){
								tjConfirm("操作成功!是否继续排班",function(flag){
									if (flag){
										toArrangeClass(data['id']);
									}else {
										var arrangeUrl = '${request.contextPath}/duty/duty-rule!arrange.ajax';
										$(window.parent.document).find("#arrange").load(arrangeUrl);
									}
								});
							}else {
								tjAlert("操作失败！");
							}
						}
					});
				});
			});

			//跳转到排班页面
			function toArrangeClass(ruleId){
				var url = '${request.contextPath}/duty/duty!add.ajax?ruleId=' + ruleId;
				$(window.parent.document).find("#arrange").load(url);
			}
		
			//ajaxForm验证方法
			function validateForm(arr, thisForm){
				if (thisForm.data('tjForm').checkSubmit()) {
					return true;
				}else{
					return false;					
				}
			}
			//显示添加规则页面
			function createAddDiv(){
				$('#ruleId').attr('disabled',true);
				$('#deptId').attr('disabled',true);
				$('#ruleSelect').hide();
				$('#ruleButton').show();
				$('#newRule').show();
				$('#allButton').hide();
				$('#ruleDetail').hide();
				$('#dutySchedules').hide();
				$('#scheButton').hide();

				$('#dept_id').tjVal($('#deptId').tjVal());
			}
		
			//验证部门是否已经存在该运转数的规则
			function isRuleExist(){
				var dpId = $('#dept_id').tjVal();
				var ruleName = $('#ruleName').val();
				var dayPartCount = $('#dayPartCount').tjVal();
				$.getJSON('${request.contextPath}/duty/duty-rule!validateRuleAjax.action?rd='+Math.random(),
					{dpId:dpId, dayPartCount:dayPartCount},
					function(data){
						if (data.result){
							tjAlert('已经存在该运转规则!');
							//$('#dayPartCount').trigger('focus');
						}else {
							toDutySch(dpId,ruleName,dayPartCount);
						}
					}
				);
			}

			//跳入值班时间规则设置
			function toDutySch(dpId,ruleName,dayPartCount){
				$('#ruleButton').hide();
				$('#newRule').hide();
				$('#ruleSelect').show();
				$('#deptId').val(dpId);
				$('#ruleId ul li').removeAttr('selected');
				$('#ruleId ul').append('<li val="temp" selected="selected">'+ruleName+'</li>');
				$('#ruleId').tjAssemble();
				$('#dutySchedules').show();
				$('#scheButton').show();
				$('#dutySchTable tr:gt(0)').remove();

				//给新增表单带入默认参数
				$('#hiddenDeptId').val(dpId);
				$('#hiddenRuleName').val(ruleName);
				$('#hiddenDpc').val(dayPartCount);
				createAddTable();
			}

			//填写值班时间规则表格
			function createAddTable(){
				var count = $('#dayPartCount').tjVal();
				var html = '';
				for(var i=1;i<=count;i++){
					html += '<tr><td style="text-align: center;"><input name="schNames" id="schNames-add-'+i+'" type="text" verify="班次名称|NotNull" ztype="text" /></td>';
					html += '<td style="text-align: center;"><input name="startTimes" id="startTimes-add-'+i+'" type="text" verify="开始时间|NotNull&&Fun={val:\'valTime\', msg:\'开始时间不能大于结束时间，跨天班请排在最后\'}" ztype="timeHM"/></td>';
					html += '<td style="text-align: center;"><input name="endTimes" id="endTimes-add-'+i+'" type="text" verify="结束时间|NotNull&&Fun={val:\'valTime\', msg:\'开始时间不能大于结束时间，跨天班请排在最后\'}" ztype="timeHM"/></td>';
					html += '<td style="text-align: center;">'+createSelect(count,i)+'</td></tr>';
				}
				$('#dutySchTable').append(html).init_style();
				$('#dutySchForm').form();
			}

			//查找部门排班规则详细
			function getRuleDetail(){
				var ruleId = $('#ruleId').tjVal();
				if (ruleId!=null && ruleId!='' && ruleId!=undefined){
					$.getJSON('${request.contextPath}/duty/duty-rule!getRuleDetailAjax.action?rd='+Math.random(),
						{'id':ruleId},
						function(data){
							if (data.result == true){
								$('#ruleDetailTable tr:gt(0)').remove();
								$('#ruleModifyTable tr:gt(0)').remove();
								var dutySchedules = eval(data['data']);
								if (dutySchedules.length == 0){
									$('#ruleDetail').append('<tr><td>本部门没有任何排班规则，请新建规则。</td></tr>');
								}else{
									createViewTable(dutySchedules);
									createModifyTable(dutySchedules);
									$('#detailDept').val($('#deptId').tjVal());
									$('#detailRule').val($('#ruleId').tjVal());
								}
							}
					 	}
					);
				}
			}

			//生成规则详细查看表格
			function createViewTable(dutySchedules){
				var html = '';
				for(var i in dutySchedules){
					html += '<tr>';
					html += '<td style="text-align: center;">'+dutySchedules[i].schName+'</td>';
					html += '<td style="text-align: center;">'+dutySchedules[i].startTime+'</td>';
					html += '<td style="text-align: center;">'+dutySchedules[i].endTime+'</td>';
					html += '<td style="text-align: center;">第'+dutySchedules[i].schOrder+'班</td>';
					html += '</tr>'
				}
				$('#ruleDetailTable').append(html);
			}
			
			//生成规则详细修改表格
			function createModifyTable(dutySchedules){
				var html = '';
				var count = dutySchedules.length;
				for(var i in dutySchedules){
					html += '<tr>';
					html += '<td style="text-align: center;"><input name="schNames" id="schNames-update-'+i+'" type="text" verify="班次名称|NotNull" ztype="text" value="'+dutySchedules[i].schName+'"/></td>';
					html += '<td style="text-align: center;"><input name="startTimes" id="startTimes-update-'+i+'" type="text" verify="开始时间|NotNull&&Fun={val:\'valTime\', msg:\'开始时间不能大于结束时间，跨天班请排在最后\'}" ztype="timeHM" value="'+dutySchedules[i].startTime+'"/></td>';
					html += '<td style="text-align: center;"><input name="endTimes" id="endTimes-update-'+i+'" type="text" verify="结束时间|NotNull&&Fun={val:\'valTime\', msg:\'开始时间不能大于结束时间，跨天班请排在最后\'}" ztype="timeHM" value="'+dutySchedules[i].endTime+'"/></td>';
					html += '<td style="text-align: center;">'+createSelect(count,dutySchedules[i].schOrder)+'</td>';
					html += '</tr>'
				}
				$('#ruleModifyTable').append(html).init_style();
				$('#dutyRuleModifyForm').form();
			}

			//生成排班顺序的下拉选
			function createSelect(count,order){
				var html = '<div ztype="select" name="schOrders" id="schOrder-'+order+'"><ul>';
				for(var i=1;i<=count;i++){
					if (i == order){
						html += '<li selected="selected" val='+i+'>第'+i+'班</li>';
					}else{
						html += '<li val='+i+'>第'+i+'班</li>';
					}
				}
				html += '</ul></div>';
				return html;
			}

			function validateSameValue(objName,msg){
				var objs = document.getElementsByName(objName);
				var flag = true;
				if(objs!=null&&objs.length>1){
					var length = objs.length;
					for(var i = 0;i<length;i++){
						var temp = objs[i].value;
						for(j=i+1;j<length;j++){
							if(temp == objs[j].value){
								alert(msg + '不能有重复值！');
								objs[j].focus();
								flag = false;
								break;
							}
						}
						if(!flag){
							break;
						}
					}
				}
				return flag;
			}

			//当改变部门时，运转数会发生变化，并且运转的详细信息也会发生变化
			function changeDept(){
				var dpId = $('#deptId').tjVal();
				$.post('${request.contextPath}/duty/duty-rule!getRuleByDeptAjax.ajax?rd='+Math.random(),
				    {dpId:dpId},
					function(data){
					   	if (data.result == true){
						   	//首先清空详细和编辑列表
						   	$('#ruleDetailTable tr:gt(0)').remove();
							$('#ruleModifyTable tr:gt(0)').remove();
							
					   		var rules = eval(data['dutyRules']);
					   		$('#ruleId').tjEmpty();
					   		if (rules.length > 0){
					   			var optionContent = '';
								for(var i = 0 ; i < rules.length ; i++){
							  		var obj = rules[i];
								  	optionContent += '<li val="'+obj['id']+'">'+obj['ruleName']+'</li>';
								}	 
								$('#ruleId ul').append(optionContent);
								$('#ruleId').tjAssemble();
					   		}
					   		if(rules.length != 0){
								 var ruleContent = ''; 
								 var editScheduleContent = '';
								 var schedules = eval(data['dutySchedules']);
								 for(var i = 0 ; i < schedules.length ; i++){
									 var schedule = schedules[i];
									 var schedule_id = schedule['id'];
									 var schedule_name = schedule['schName'];
									 var schedule_startTime = schedule['startTime'];
									 var schedule_endTime = schedule['endTime'];
									 var schedule_order = schedule['schOrder'];
									 ruleContent += '<tr>';
									 ruleContent += '<td style="text-align: center;">'+schedule_name+'</td>';
									 ruleContent += '<td style="text-align: center;">'+schedule_startTime+'</td>';
									 ruleContent += '<td style="text-align: center;">'+schedule_endTime+'</td>';
									 ruleContent += '<td style="text-align: center;">第'+schedule_order+'班</td>';
									 ruleContent += '</tr>';
									 
									 editScheduleContent += '<tr>';
									 editScheduleContent += '<td style="text-align: center;"><input type="text" ztype="text" id="schNames-'+i+'" verify="班次名称|NotNull" name="schNames" value="'+schedule_name+'"/>'+
									                        '<input type="hidden" name="ids" value="'+schedule_id+'"/></td>';
									 editScheduleContent += '<td style="text-align: center;"><input type="text" id="startTimes-'+i+'" name="startTimes" verify="开始时间|NotNull&&Fun={val:\'valTime\', msg:\'开始时间不能大于结束时间，跨天班请排在最后\'}" ztype="timeHM" value="'+schedule_startTime+'"/></td>';
									 editScheduleContent += '<td style="text-align: center;"><input type="text" id="endTimes-'+i+'" name="endTimes" verify="结束时间|NotNull&&Fun={val:\'valTime\', msg:\'开始时间不能大于结束时间，跨天班请排在最后\'}" ztype="timeHM" value="'+schedule_endTime+'"/></td>';
									 editScheduleContent += '<td style="text-align: center;">'+createSelect(schedules.length, schedule_order)+'</td>';
									 editScheduleContent += '</tr>';
								 }
								 $('#ruleDetailTable').find('tr:eq(0)').after(ruleContent);
								 $('#ruleModifyTable').find('tr:eq(0)').after(editScheduleContent);
								 $('#dutyRuleModifyForm').init_style();
								 $('#detailDept').val($('#deptId').tjVal());
								 $('#detailRule').val($('#ruleId').tjVal());
							 }
					   	}
					}
			    );
			}

			//验证开始时间结束时间
			function valTime(obj) {
				var startTime = $(obj).parents('tr').find('input[name="startTimes"]').val();
				var endTime = $(obj).parents('tr').find('input[name="endTimes"]').val();
				var schOrder = $(obj).parents('tr').find('div[name="schOrders"]').tjVal();
				var count = $(obj).parents('table').find('div[name="schOrders"]').length;
				var start = parseInt(startTime.substring(0,2),10) * 60 +  parseInt(startTime.substring(3),10);
				var end = parseInt(endTime.substring(0,2),10) * 60 +  parseInt(endTime.substring(3),10);
				var times = end - start;
				if (schOrder < count){
					if (times < 0){
						return false;
					}else {
						return true;
					}
				}else {
					return true;
				}
			}
				
		</script>
	</head>
	<body>
		<div id="ruleSelect">
			<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<th colspan="4">
						&lt;&lt;规则选择&gt;&gt;
					</th>
				</tr>
				<#if depts?exists && (depts?size>0)>
					<tr>
						<td align="right" width="15%">
							<strong>部门：</strong>
						</td>
						<td>
							<div id="deptId" name="deptId" ztype="select" verify="NotNull">
	   							<ul>
									<#list depts as dept>
										<li val="${dept.id}">${dept.name}</li>
									</#list>
								</ul>
							</div>
						</td>
						<td align="right" width="15%">
							<strong>运转选择：</strong>
						</td>
						<td>
							<div id="ruleId" name="ruleId" ztype="select" verify="NotNull">
								<ul>
									<#list dutyRules as dutyRule>
										<li val="${dutyRule.id}">${dutyRule.ruleName}</li>
									</#list>
								</ul>
							</div>
						</td>
					</tr>
				<#else>
					<tr id="noDeptPer"><td colspan="4">登录用户没有任何值班的部门权限！</td></tr>
				</#if>
			</table>
		</div>
		<hr/>
		<div id="ruleDetail">
			<table class="work_info_table" id="ruleDetailTable" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<th width="25%">
						班次名称
					</th>
					<th width="25%">
						开始时间
					</th>
					<th width="25%">
						结束时间
					</th>
					<th>
						先后顺序
					</th>
				</tr>
			</table>
		</div>
		<div id="ruleModify" style="display:none;">
			<span class="work_clew_td"><img src="${request.contextPath}/img/info.png" style="margin-bottom: -4px;"/> 提示：设置时间请使用24小时制；一种规则最多允许存在一个跨天时间段；如果开始时间与结束时间相同，视为跨天。</span>
			<form name="dutyRuleForm" action="${request.contextPath}/duty/duty-rule!save.action" id="dutyRuleModifyForm">
				<input type="hidden" id="hiddenRuleId" name="id"/>
				<table class="work_info_table" id="ruleModifyTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th width="25%">
							班次名称
						</th>
						<th width="25%">
							开始时间
						</th>
						<th width="25%">
							结束时间
						</th>
						<th>
							先后顺序
						</th>
					</tr>
				</table>
			</form>
		</div>
		<div id="newRule" style="display: none;">
			<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<th colspan="6">
						新运转规则
					</th>
				</tr>
				<tr>
					<td style="width: 15%; text-align: right;">部门:</td>
					<td>
						<div id="dept_id" name="dept_id" ztype="select">
							<ul>
								<#list depts as dept>
									<li val="${dept.id}">${dept.name}</li>
								</#list>
							</ul>
						</div>
					</td>
					<td style="width: 15%; text-align: right;">
						新运转名称:
					</td>
					<td width="20%">
						<input type="text" id="ruleName" ztype="text" name="ruleName" value="一运转" readOnly="true"/>
					</td>
					<td style="width: 15%; text-align: right;">
						运转数:
					</td>
					<td>
						<div ztype="select" id="dayPartCount" name="dayPartCount">
							<ul>
								<#assign x=9>
								<#list 1..x as i>
									<#if i == 1>
										<li val="${i}" selected="selected">${i}</li>
									<#else>
										<li val="${i}">${i}</li>
									</#if>
								</#list>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="dutySchedules" style="display: none;">
			<form id="dutySchForm" action="${request.contextPath}/duty/duty-rule!save.action" name="dutyRuleForm">
				<input type="hidden" name="dept.id" id="hiddenDeptId" />
				<input type="hidden" name="ruleName" id="hiddenRuleName" />
				<input type="hidden" name="dayPartCount" id="hiddenDpc" />
				<span class="work_clew_td"><img src="${request.contextPath}/img/info.png" style="margin-bottom: -4px;"/> 提示：设置时间请使用24小时制；一种规则最多允许存在一个跨天时间段；如果开始时间与结束时间相同，视为跨天。</span>
				<table class="work_info_table" id="dutySchTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th width="25%">
							班次名称
						</th>
						<th width="25%">
							开始时间
						</th>
						<th width="25%">
							结束时间
						</th>
						<th>
							先后顺序选择
						</th>
					</tr>
				</table>
			</form>
		</div>
		<div align="right" id="allButton">
			<button ztype="button" type="button">创建新规则</button>
			<button ztype="button" type="button">修改规则</button>
			<button ztype="button" type="button">排 班</button>
			<button ztype="button" type="button">删除排班</button>								
		</div>
		<div align="right" id="ruleButton" style="display:none;">								
			<button ztype="button" type="button" id="ruleNext">下一步</button>&nbsp;
			<button ztype="button" type="button" name="cannelRule">取消</button>&nbsp;
		</div>
		<div align="right" id="scheButton" style="display:none;">	
			<button ztype="button" type="button" id="schePrev">上一步</button>&nbsp;
			<button ztype="button" type="button" id="scheNext">下一步</button>&nbsp;
			<button ztype="button" type="button" name="cannelRule">取消</button>&nbsp;							
		</div>
		<div align="right" id="ruleModifyButton" style="display:none;">
			<button ztype="button" type="button" id="ruleModifyNext">下一步</button>&nbsp;	
			<button ztype="button" type="button" name="cannelRule">取消</button>&nbsp;	
		</div>
	</body>
</html>
