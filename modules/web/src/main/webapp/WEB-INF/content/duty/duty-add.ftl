<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('#arrangeClassForm').form();
				$('#arrangeClassForm').init_style();

				$('#arrangeClassForm').ajaxForm({
					type: "post",
					dataType: 'json',
					beforeSubmit: validateForm,
					success: function(data){
						if (data['result'] == true){
							tjAlert('操作成功!')
							var url = '${request.contextPath}/duty/duty!show.action';
							window.location.href = url;
						}else {
							tjAlert("操作失败!");
						}
					}
				});
				
				$('button:contains("上一步")').click(function(){
					prevRule();
				});

				$('button:contains("保存")').click(function(){
					$('#emptyTable input[name="groupsBeSelected"]').each(function(){
						var val = $(this).val();
						$(this).siblings('input[type="hidden"]').val(val);
					});
					$('input[name="firstDayIndex"]').each(function(i){
						if ($(this).attr('checked')){
							$('#hiddenIndex').val(i + 1);
						}
					});
					$('#arrangeClassForm').submit();
				});
				
				$('button:contains("下一步")').click(function(){
					var dpId = ${dutyRule.dept.id};
					var startDate = $('#startDate').val();
					$.getJSON('${request.contextPath}/duty/duty!validateDateAjax.ajax',
						{dpId:dpId,startDate:startDate},
						function(data){
							if (data.result == false){
								arrangeNext();
							}else {
								tjAlert('今天已经存在值班记录！不能进行排班！');
							}
						}
					);
				});

				$('button:contains("设置班组")').click(function(){
					var flag = false;
					var groupId = $('input[name="groupRadio"]:checked').attr('id');
					if (validateGroup(groupId)){
						var groupName = $('input[name="groupRadio"]:checked').val();
						$('input[name="groupsBeSelected"]:checked:visible').each(function(){
							$(this).val(groupId);
							$(this).attr('checked',false);
							$(this).hide();
							if ($(this).siblings('span').length > 0){
								$(this).siblings('span:first').html(groupName);
							}else {
								$(this).parents('td:first').append('<span style="color: blue" name="groupSpan">'+groupName+'</span>');
							}						
						});
						$('#emptyTable td[name="clickTd"] span').each(function(){
							if ($(this).html() == groupName){
								$(this).css('color', 'blue');
							}
						});
					}else {
						return;
					}
				});

				$('button:contains("取消")').click(function(){
					var arrangeUrl = '${request.contextPath}/duty/duty-rule!arrange.ajax';
					$(window.parent.document).find("#arrange").load(arrangeUrl);
				});
			});
		
			//点击保存前的验证
			function validateForm(){
				var flag = true;
				$('#emptyTable td[name="clickTd"]').each(function(){
					if ($(this).find('span').length == 0 || $(this).find('span').html() == ''){
						tjAlert('请将排班表填满!');
						flag = false;
						return false;
					}
				});
				if (!flag) {
					return false;
				}
				if ($('input[name="firstDayIndex"]:checked').length == 0){
					tjAlert('请选择第几天为开始日期!');
					return false;
				}
				return true;
			}

			//验证班组是否有人员
			function validateGroup(groupId){
				var flag = true;
				$.ajax({
				   url: '${request.contextPath}/duty/duty!validateGroup.ajax',
				   data: {groupId:groupId},
				   async: false,
				   success: function(data){
						if (data.result == false){
							var array = eval(data['groupIds']);
							var str = '';
							var groupId = data.groupId;
							var groupName = data.groupName;
							tjAlert(groupName + '没有任何人员，请选择其他班组!<br>');
							flag = false;
						}
					}
			    });
			    return flag;
			}
		
			//选择日期和周期后下一步操作
			function arrangeNext(){
				createEmptyTable();		

				//班组单选按钮单机事件
				$('input[name="groupRadio"]').click(function(){
					$('button:contains("设置班组")').button('enable');
					$('span[name="groupSpan"]:not(:contains('+$(this).val()+'))').css('color','black');
					$('span[name="groupSpan"]:contains('+$(this).val()+')').css('color','blue');
				});
				
				//通过单元格的onclick事件，显示checkbox
				$('td[name="clickTd"]').click(function(){
					var checkBox = $(this).find('input[name="groupsBeSelected"]');
					if (checkBox.css('display') == 'none'){
						checkBox.show().attr('checked',true);
					}
				});

				$('input[name="groupsBeSelected"]').click(function(event){
					if(!($(this).attr('checked'))){
						$(this).hide();
						event.stopPropagation();
					}
				});		
				optionDisplay(false);		
				arrangePrev();
				setArrMessage();
				//设置班组按钮不可选
				if ($('input[name="groupRadio"]:checked').length == 0){
					$('button:contains("设置班组")').button('disable');
				}
				//不显示下一步按钮
				$('button:contains("下一步")').hide();
				//显示保存按钮
				$('button:contains("保存")').show();
			}

			//设置排班描述信息
			function setArrMessage(){
				var dpName = $('#dpName').val();
				var ruleName = $('input[name="ruleName"]').val();
				var cycle = $('input[name="cycle"]').val();
				var startDate = $('input[name="startDate"]').val();
				var endDate = $('input[name="endDate"]').val();
				var html = '<span class="work_clew_td" style="color:black;"><img src="${request.contextPath}/img/info.png" style="margin-bottom: -4px;" />' 
								+ '规则为' +dpName+ruleName + '，周期为' + cycle +'天，起止日期为'+startDate + '至' + endDate;
								+ '</span>';
				$('#arrMessage').html(html);
			}

			//点击上一步，返回创建排班规则页面
			function prevRule(){
				var url = '${request.contextPath}/duty/duty-rule!arrange.ajax';
				$(window.parent.document).find("#arrange").load(url);
			}
			
			//显示具体排班表格时，上一步按钮绑定的onclick事件
			function arrangePrev(){	
				$('button:contains("上一步")').unbind().click(function(){
					optionDisplay(true);
					//当返回排班页面初始状态时，重新绑定”上一步“按钮的onclick事件
					$(this).unbind().click(function(){
						prevRule();	
					}); 

					//三个按钮解除绑定
					$('input[name="groupRadio"]').unbind();
					$('td[name="clickTd"]').unbind();
					$('input[name="groupsBeSelected"]').unbind();
					
					//显示下一步按钮
					$('button:contains("下一步")').show();
					//不显示保存按钮
					$('button:contains("保存")').hide();
				});
			}

			//创建空白值班表
			function createEmptyTable(){
				//得到周期数
				var count = $('input[name="cycle"]').val();
				//得到当前表格的列数
				var colLength = $('#emptyTable tr:first th').length;
				//插入(周期数-表格列+1)列
				var index = count -(colLength - 1);	
				if(index >=0 ){
					addCols(index);
				}else{
					//如果count小于零，则删除count的绝对值列
					delCols(count);
				}
			}

			//表格添加列
			function addCols(count){				
				var rowLength = $('#emptyTable tr').length;
				var colLength = $('#emptyTable tr th').length;
				var addTh = '';
				var addTd = '';
				var addStart = '';
				var startIndex = parseInt($('input[name="firstDayIndex"]:last').val());
				for(var i=0;i<count;i++){
					//如果是首行，只是插入描述信息“第？天”
					addTh += '<th>第 ' +(colLength+i)+ ' 天</th>';
					//正常的班组选择插入checkbox	
					addTd += '<td height=18 name="clickTd"><input type="checkbox" class="radio" value="" name="groupsBeSelected"/>&nbsp;<input type="hidden" value="" name="groupIds" /></td>';
					//如果是周期选择行，则插入radio	
					addStart += '<td><input class="radio" type="radio" value="'+(startIndex + i + 1)+'" onclick="" name="firstDayIndex" ';
					if (i == 0){
						addStart += 'checked=checked';
					}
					addStart += '/></td>'
				}
				for(var i=0;i<rowLength;i++){
					if (i == 0){
						$('#emptyTable tr:eq('+i+')').append(addTh);
					}else if (i == rowLength-1){
						$('#emptyTable tr:eq('+i+')').append(addStart);
					}else{
						$('#emptyTable tr:eq('+i+')').append(addTd);
					}
				}
			}

			//表格删除列
			function delCols(count){
				var rowLength = $('#emptyTable tr').length;
				for(var i = 0;i<rowLength;i++){
					if (i == 0){
						$('#emptyTable tr:eq('+i+') th:gt('+count+')').remove();
					}else {
						$('#emptyTable tr:eq('+i+') td:gt('+count+')').remove();
					}
				}
			}

			//本页面元素显示（日期与周期显示时，班组与排班表不显示；反之亦然）
			function optionDisplay(bool){
				if (bool){
					//日期与周期显示
					$('#arrOptionSelect').css('display','');
					//班组与排班表显示
					$('#groupsList').css('display','none');
					$('#emptyDiv').css('display','none');
				}else {
					$('#arrOptionSelect').css('display','none');
					$('#groupsList').css('display','');
					$('#emptyDiv').css('display','');
				}
			}	
		</script>
	</head>
	<body>
		<div>
			<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<form name="arrangeClassForm" action="${request.contextPath}/duty/duty!addDutyAjax.action" id="arrangeClassForm">
							<input type="hidden" id="hiddenIndex" name="firstDay" />
							<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0" id="arrOptionSelect">
								<th colspan="6">
									${dutyRule.dept.name}
									${dutyRule.ruleName} 排班参数设置
									<input type="hidden" name="ruleId" value="${dutyRule.id}"/>
									<input type="hidden" name="ruleName" value="${dutyRule.ruleName}" />
									<input type="hidden" name="dpId" value="${dutyRule.dept.id}"/>
									<input type="hidden" id="dpName" value="${dutyRule.dept.name}"/>
								</th>
								<tr>
									<td align="right">
										<strong>周期(天)：</strong>
									</td>
									<td>
										<input type="text" ztype="text" name="cycle" value="<#if dutyRule.lastCycle !=0>${dutyRule.lastCycle}</#if>" verify="周期|NotNull&&Int&&Value>=1"/>
									</td>
									<td align="right">
										<strong>开始日期：</strong>
									</td>
									<td>
										<input name="startDate" type="text" value="" id="startDate" znow="startnow" ztype="startdate" verify="开始日期|NotNull"/>
									</td>
									<td align="right">
										<strong>结束日期：</strong>
									</td>
									<td>
										<input name="endDate" type="text" value="" id="endDate" ztype="enddate" verify="结束日期|NotNull"/>
									</td>
								</tr>
							</table>
							<hr />
							<div style="width: auto; text-align: center;">
								<div id="emptyDiv" style="display:none;width: 980px;overflow-x: auto;padding: 0px 0px 8px 0px;">
									<div id="arrMessage" style="margin-bottom:6px;"></div>
									<table border="0" cellpadding="0" cellspacing="0" class="work_info_table" align="center" id="emptyTable">
										<tr id="dayDesc">
											<th>
												&nbsp;
											</th>
											<#assign x=dutyRule.lastCycle>
											<#if x != 0>
												<#list 1..x as i>
												<th>
													第 ${i} 天
												</th>
												</#list>
											</#if>
										</tr>
										<#list dutyRule.dutySchedules as dutySch>
											<tr id="${dutySch_index }">
												<td style="background: #fff;">
													${dutySch.schName }
													<input type="hidden" name="dutyScheduleIds"
														value="${dutySch.id}" />
												</td>
												<#if x != 0>
													<#list dutySch.ruleItems as ruleItem>
														<td name="clickTd">
															<input type="checkbox" style="display: none;" value="${ruleItem.group.id }"
																name="groupsBeSelected" />
															<input type="hidden" value="" name="groupIds" />
															<span style="color: black" name="groupSpan">${ruleItem.group.name}</span>
														</td>
													</#list>
												</#if>
											</tr>
										</#list>
										<tr id="dateSet">
											<td style="background: #fff;">
												选择开始日期
											</td>
											<#if x != 0>
												<#list 1..x as i>
													<td>
														<input type="radio" value="${i}" name="firstDayIndex" />
													</td>
												</#list>
											</#if>
										</tr>
									</table>
								</div>
							</div>
							<div id="groupsList" style="display:none;text-align:center;margin-top:10px;">
								<#list groups as group>
									<input type="radio" name="groupRadio"
										id="${group.id}" value="${group.name}">
										${group.name }</input>&nbsp;
								</#list>
								<br />
								<button ztype="button">设置班组</button>
								<hr />
							</div>
							<div align="right">
								<button ztype="button" type="button">上一步</button>
								<button ztype="button" type="button">下一步</button>&nbsp;&nbsp;&nbsp;&nbsp;
								<button ztype="button" style="display: none;" type="button">保存</button>
								<button ztype="button" type="button">取消</button>
							</div>
						</form>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
