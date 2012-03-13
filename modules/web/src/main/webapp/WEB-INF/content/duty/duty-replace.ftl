<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('#dpId').tjChange(function(){
					var dpId = $('#dpId').tjVal();
					$('div[id^="div_data_"]').hide();
	        		$('#div_data_'+dpId).show();
				});
			});

			//添加一条
			function toAdd(dpId){
        		var table  = $('#div_data_'+dpId).find('.work_info_table');
        		if(table.find('tr').length == 1){
        			$('button:contains("预览")').button('enable');
        			$('button:contains("保存")').button('enable');
        		}
        		var groupOption = $.trim($('#div_group_'+dpId).html());
        		
        		var randomNum = dpId+""+Math.random();
        		randomNum = randomNum.replace('.','');
        		var startDateName = "startDate"+randomNum;
        		var startDate_limit = '#F{$dp.$D(\\\''+startDateName+'\\\')||\\\'%y-%M-{%d}\\\'}';
        		var endDateName = "endDate"+randomNum;
        		var endDate_limit = '#F{$dp.$D(\\\''+endDateName+'\\\')}';
        		
        		var tr_content = '';
        		tr_content += '<tr><form id="form_'+randomNum+'">';
        		tr_content += '<td><div id="schedule_'+randomNum+'" ztype="select" style="text-align:center;width:80%;" verify="NotNull" name="dutyScheduleNames"><ul>'+groupOption+'</ul></div></td>';
        		tr_content += '<td><input id="'+startDateName+'" ztype="text" type="text" verify="开始时间|NotNull" style="text-align:center;width:75%;" name="startDates" onBlur="countNum(this);" onfocus="WdatePicker({maxDate:\''+endDate_limit+'\',minDate:\'%y-%M-{%d}\'})"/></td>';
        		tr_content += '<td><input id="'+endDateName+'" ztype="text" type="text" verify="结束时间|NotNull" style="text-align:center;width:75%;" name="endDates" onBlur="countNum(this);" onfocus="WdatePicker({minDate:\''+startDate_limit+'\'})"/></td>';
        		tr_content += '<td><input ztype="auto" zurl="${request.contextPath}/baseinfo/person!autocompleteAjax.action?funModule=duty" verify="被替换人员|NotNull" type="text" style="text-align:center;width:75%;" onBlur="countNum(this);" id="isReplacedEmpName'+randomNum+'"/></td>';
        		tr_content += '<td><div id="type'+randomNum+'" ztype="select" style="text-align:center;width:80%;" verify="NotNull" name="flags"><ul><li val="0">替班</li><li val="1">换班</li></ul></div></td>';
        		tr_content += '<td><input ztype="auto" zurl="${request.contextPath}/baseinfo/person!autocompleteAjax.action?funModule=duty" verify="替班人员|NotNull" type="text" style="text-align:center;width:80%;" id="replacedEmpName'+randomNum+' name="dutyScheduleRpIds" onBlur="countNum(this);"/></td>';
        		tr_content += '<td style="text-align:center;">&nbsp;</td>';
        		tr_content += '<td style="text-align:center;"><a href="javascript:void(0)" onClick="deleteRow(this);">删除</a></td>';
        		tr_content += '</form></tr>';
        		table.find('tr:last').after(tr_content);
        		table.find('tr:last').init_style();
        		$('#form_'+randomNum).form();

        		//当更改班次时，替换人员和状态自动清空
        		$('#schedule_'+randomNum).tjChange(function(){
        			var dpId = $('#dpId').tjVal();
        			var randomNumNew = dpId+""+Math.random();
        			var tr = $('#schedule_'+randomNum).parents('tr');
        			tr.find('td:eq(3)').html('<input type="text" ztype="auto" zurl="${request.contextPath}/baseinfo/person!autocompleteAjax.action?funModule=duty" verify="替班人员|NotNull" style="text-align:center;" onBlur="countNum(this);" id="isReplacedEmpName'+randomNumNew+'"/>');
        			tr.find('td:eq(6)').html("&nbsp;");	
        			tr.init_style();
        			tr.find('form').form();
            	});

            	//更改替换班状态
				$('#type'+randomNum).tjChange(function(){
					var dpId = $('#dpId').tjVal();
	        		var groupOption = $.trim($('#div_group_'+dpId).html());
	        		var randomNumNew = dpId+""+Math.random();
	        		randomNum = randomNum.replace('.','');
	        		if($('#type'+randomNum).tjVal() == 0 ){ //替班
	        			$('#type'+randomNum).parents('tr').find("td:eq(5)").html('<input type="text" ztype="auto" zurl="${request.contextPath}/baseinfo/person!autocompleteAjax.action?funModule=duty" verify="换班人员|NotNull" style="text-align:center;" id="replacedEmpName'+randomNumNew+'"/>');
	        		}else{ //换班
	        			$('#type'+randomNum).parents('tr').find("td:eq(5)").html('<div style="text-align:left;width:40%;float:left;" ztype="select" verify="NotNull" id="replaceSchedule_'+randomNumNew+'" name="dutyScheduleRpNames"><ul>'+groupOption+'</ul></div>&nbsp;&nbsp;<input type="text" ztype="auto" zurl="${request.contextPath}/baseinfo/person!autocompleteAjax.action?funModule=duty" verify="换班人员|NotNull" onBlur="countNum(this);" style="width:80" style="text-align:center;" id="replacedEmpName'+randomNumNew+'"/>');
	        			$('#type'+randomNum).parents('tr').find("td:eq(5)").init_style();
	        			$('#type'+randomNum).parents('tr').find("td:eq(5)").form();
	        		}
				});            	
        	}

			//计算开始时间 结束时间 班次 人 影响的班次数量
			function countNum(obj){
				var tr = $(obj).parents('tr');
				if (tr.find('form').data('tjForm').checkSubmit()){
					var empName = $.trim(tr.find('input[id^="isReplacedEmpName"]').val());
					var startDate = tr.find('input[id^="startDate"]').val();
					var endDate = tr.find('input[id^="endDate"]').val();
					if(empName == ''){
						tr.find('td:eq(6)').html('&nbsp;');
						return ;
					}
					var scheduleId = (tr.find('div[id^="schedule_"]').tjVal().split('_'))[0];
					var url = '${request.contextPath}/duty/duty!getEffectedGroupNumAjax.ajax?rd='+Math.random();
					$.ajax({
						url : url,
						dataType: 'json',
						type:"post",
						data:{startDate:startDate,endDate:endDate,scheduleId:scheduleId,empName:empName},
						success: function(data){
							if (data.result == true){
								tr.find('td:eq(6)').html("<strong><font color='green'>影响"+data['num']+"个班次</font></strong>");
							}
						}
					});
				}
			}

			//删除一行
			function deleteRow(obj){
        		var table = $(obj).parents('table');
        		var dpId = $('#dpId').val();
        		if(table.find('tr').length == 2){
        		    $('button:contains("预览")').button('disable');
        			$('button:contains("保存")').button('disable');
        		}
        		$(obj).parents('tr').remove();
        	}

			function preView(dpId){
				var dutyScheduleNames_array = new Array();
				var startDate_array = new Array();
				var endDate_array = new Array();
				var empNames_array = new Array();
				var flags_array = new Array();
				var empRpNames_array = new Array();
				var dutyScheduleRpNames_array = new Array();
				var startDate_min ='';
				var endDate_max = '';
				var isSuccess = true;
				var pass = true;
				$('div[id^="div_data_"]:visible').find('form').each(function(){
					if(!$(this).data('tjForm').checkSubmit()){
						pass = false;
					}
				});
				if (pass){
					$('#div_data_'+dpId).find('.work_info_table').find('tr:gt(0)').each(function(index){
						var startDate = $(this).find('input[id^="startDate"]').val();
						var endDate = $(this).find('input[id^="endDate"]').val();
						var isReplacedName = encodeURIComponent($(this).find('input[id^="isReplacedEmpName"]').val());
						var replaceName = encodeURIComponent($(this).find('input[id^="replacedEmpName"]').val());
						if(index == 0){
							startDate_min = startDate;
							endDate_max = endDate;
						}
						if(startDate_min > startDate){
							startDate_min = startDate ;
						}
						if(endDate_max < endDate){
							endDate_max = endDate;
						}
						var scheduleTemp = $(this).find('div[name^="dutyScheduleNames"]').tjVal();
						var scheduleNeed = scheduleTemp.substring(scheduleTemp.indexOf('_')+1);
						dutyScheduleNames_array.push(encodeURIComponent(scheduleNeed));
						startDate_array.push(startDate);
						endDate_array.push(endDate);
						empNames_array.push(isReplacedName);
						flags_array.push($(this).find('div[name^="flags"]').tjVal());
						empRpNames_array.push(replaceName);
						var scheduleRpTemp = $(this).find('div[name^="dutyScheduleRpNames"]').tjVal();
						if (scheduleRpTemp != undefined){
							var scheduleRpNeed = scheduleRpTemp.substring(scheduleRpTemp.indexOf('_')+1);
							dutyScheduleRpNames_array.push(encodeURIComponent(scheduleRpNeed));
						}else {
							dutyScheduleRpNames_array.push(scheduleRpTemp);
						}
					});
				}else {
					return ;
				}
				var url = '${request.contextPath}/duty/duty!toPreviewReplaceDuties.ajax?startDate='+startDate_min+'&endDate='+endDate_max+'&dpId='+dpId
				          +'&startDateArray='+startDate_array.toString()+'&endDateArray='+endDate_array.toString()+'&dutyScheduleNameArray='+dutyScheduleNames_array.toString()+
				          '&empNameArray='+empNames_array.toString()+'&flagsArray='+flags_array.toString()+'&empRpNameArray='+empRpNames_array.toString()+'&dutyScheduleRpNameArray='
				          +dutyScheduleRpNames_array.toString();
				$('button:contains("预览")').prev().attr('url',url).trigger('click');
			}

			function replaceSubmit(dpId){
				var flag = true;
				var theDiv = $('div[id^="div_data_"]:visible:first');
				$(theDiv).find('form').each(function(){
					if (!$(this).data('tjForm').checkSubmit()){
						flag = false;
					}
				});
				if (flag){
					var startDate_min ='';
					var endDate_max = '';
					var updateRp = {};
					updateRp.list = [];
					$(theDiv).find('.work_info_table').find('tr:gt(0)').each(function(index){
						var rp = {};
						rp.startDate = $(this).find('input[id^="startDate"]').val();
						rp.endDate = $(this).find('input[id^="endDate"]').val();
						rp.isReplacedName = $(this).find('input[id^="isReplacedEmpName"]').val();
						rp.replaceName = $(this).find('input[id^="replacedEmpName"]').val();
						var scheduleTemp = $(this).find('div[name^="dutyScheduleNames"]').tjVal();
						rp.schName = scheduleTemp.split('_')[1];
						rp.dayPartCount = scheduleTemp.split('_')[2];
						rp.flag = $(this).find('div[name^="flags"]').tjVal();
						var scheduleRpTemp = $(this).find('div[name^="dutyScheduleRpNames"]').tjVal();
						if (scheduleRpTemp != undefined){
							rp.schNameRp = scheduleRpTemp.split('_')[1];
							rp.dayPartCountRp = scheduleRpTemp.split('_')[2];
						}
						updateRp.list.push(rp);
						if(index == 0){
							startDate_min = rp.startDate;
							endDate_max = rp.endDate;
						}
						if(startDate_min > rp.startDate){
							startDate_min = rp.startDate ;
						}
						if(endDate_max < rp.endDate){
							endDate_max = rp.endDate;
						}
					});
					updateRp.startDateMin = startDate_min;
					updateRp.endDateMax = endDate_max;
					updateRp.dpId = dpId;
					$.ajax({
						type: 'POST',
						url: '${request.contextPath}/duty/duty!updateReplaceResult.action',
						data: {updateRp:$.toJSON(updateRp)},
						success: function(data){
							if(data.result == true){
								tjAlert("保存成功");
								window.location.href = '${request.contextPath}/duty/duty!show.action';
							}
						}
					});
				}else {
					return ;
				}
			}	

		</script>
	</head>
	<body>
		<div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
                <tr>
                    <th colspan="4">
                        &lt;&lt;班组/日期选择&gt;&gt;
                    </th>
                </tr>
                <#if depts?exists && (depts?size>0)>
                 <tr>
                     <td align="right" style="width:50%">
                         <strong>请选择您要替/换班的部门：</strong>
                     </td>
                     <td align="left" style="width:50%">
                     	<div id="dpId" ztype="select" verify="NotNull">
                     		<ul>
                     			<#list depts as dept>
                     				<li val="${dept.id}">${dept.name}</li>	
                     			</#list>
                     		</ul>
                     	</div>
                     </td>
                 </tr>
         	<#else>
         		<tr>
                     <td align="right" style="width:50%">
                         <strong>请选择您要替/换班的部门：</strong>
                     </td>
                     <td align="left" style="width:50%">
                     	登录用户没有任何值班的部门权限！
                     </td>
                 </tr>
         	</#if>
            </table>
        </div>
        <div>
        	<#list depts as dept>
                <input type="hidden" name="dpId" value="${dept.id}"/>
                <div id="div_data_${dept.id}" <#if dept_index!=0>style="display:none"</#if>>
                	<div>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
							<tbody id="replaceTable">
								<tr>
									<th width="15%" nowrap="nowrap">
									   	 班次
									</th>
									<th width="10%" nowrap="nowrap">
										 开始时间
									</th>
									<th width="10%" nowrap="nowrap">
									   	结束时间
									</th>
									<th width="10%" nowrap="nowrap">
									   	被替换人员
									</th>
									<th width="8%" nowrap="nowrap">
									   	替/换班
									</th>
									<th width="27%" nowrap="nowrap">
									  	 替换人员
									</th>
									<th width="10%" nowrap="nowrap">
									   	状态
									</th>
									<th width="5%" nowrap="nowrap">
									   	操作
									</th>
								</tr>
							</tbody>
	                   	</table>
                   	</div>
                   	<div style="width: 100%; text-align: right">
	                    <button ztype="button" type="button" onclick="toAdd(${dept.id});">添加</button>
	                    <a ztype="popUp" title="浏览替换班结果" zwidth="800"></a><button ztype="button" onclick="preView(${dept.id});" disabled="disabled">预览</button>
	                    <button ztype="button" type="button" onclick="replaceSubmit(${dept.id});" disabled="disabled">保存</button>
                  	</div>
                  	<div style="display:none;" id="div_group_${dept.id}">
		                <#if dutySchedules?exists> 
		     				<#list dutySchedules?keys as dpId> 
		     					<#if dpId == dept.id>
			     					<#assign values=dutySchedules.get(dpId)>
		     						<#list values as schedule>
		     							<li val="${schedule.id}_${schedule.schName}_${schedule.dutyRule.dayPartCount}">(${schedule.dutyRule.ruleName})${schedule.schName}</li>
		     						</#list>
	   							</#if>
		     				</#list>
		     			<#else>
		     				<li val="0">该机房无班次</li>
		     			</#if>
                   </div>
				</div>         
			</#list>            
        </div>
	</body>
</html>
