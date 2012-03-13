<html>
<head>
<script language="JavaScript" type="text/javascript">
	$(document).ready(function(){
		$('button:contains("查询")').click(function(){
			var dpId = $('#dpId').tjVal();
			var groupId = $('#groupId').tjVal();
			var startDate = $('#startDate').val();
			var endDate = $('#endDate').val();
			var empName = encodeURIComponent($('#empName').val());
			var hasCheckes = $('#hasCheckes').tjVal();
			var url = '${request.contextPath}/duty/duty!loadDuty.action?'+ 
				'dpId='+dpId+'&groupId='+groupId+'&startDate='+startDate+'&endDate='+endDate+'&empName='+empName+'&hasCheckes='+hasCheckes;
			$('#duty-show').tjSearch(url);
		});
		//部门班组联动
		$('#dpId').tjChange(function(){
			var id = $('#dpId').tjVal();
			$.getJSON('${request.contextPath}/baseinfo/dept!getGroupsAjax.action?rd='+Math.random(),
				{'id':id},
				function(data){
					$('#groupId').tjEmpty();
					if (data.result){
						var array = eval(data['data']);
						var html = '<li val="">无</li>';
						for(var i in array){
							html += '<li val="'+array[i].value+'">'+array[i].name+'</li>';
						}
						$('#groupId ul').append(html);
						$('#groupId').tjAssemble();
					}
				}
			);
		});
		<#if dpId?exists>
			var dpId = ${dpId};
		<#else>
			var dpId = 0;
			$('button').attr('disabled',true);
		</#if>
 	  	var colModel=[{name:'id',header:'id',hidden:true,editable:false},
 	  	           	  {name:'dept_name',header:'部门'},
 	  	              {name:'group_name',header:'班组'},
 	  	          	  {name:'schName',header:'班次'},
 					  {name:'staffOnDuty',header:'人员'},
 					  {name:'updDate',header:'交班日期'}];
 		$('#duty-show').grid({name:'值班日志',colModel:colModel,gridType:'onlyCheck',
 			loadUrl:{content:'${request.contextPath}/duty/duty!loadDuty.action?dpId='+dpId+'&startDate=${currentDate}'},
 			viewUrl:{content:'${request.contextPath}/duty/duty!scanDuty.blank', width:500, height:450, mode:'iframe',title:"当前值班提醒"}});

	});
</script>
</head>
<body>
    <div>
		<form action="" name="viewForm" id="viewForm">
			<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<th colspan="4">
						&lt;&lt;查询条件&gt;&gt;
					</th>
				</tr>
				<tr>
					<td align="right" width="15%">
						<strong>部门：</strong>
					</td>
					<td width="34%">
						<div id="dpId" name="dpId" ztype="select">
   							<ul>
								<#list depts as dept>
									<li val="${dept.id}">${dept.name}</li>
								</#list>
							</ul>
						</div>
					</td>
					<td align="right" width="15%">
						<strong>班组：</strong>
					</td>
					<td>
						<div id="groupId" name="groupId" ztype="select">
							<ul>
								<li val="">无</li>
								<#if groups?exists>
									<#list groups as group>
										<li val="${group.id}">${group.name}</li>
									</#list>
								</#if>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<td align="right">
						<strong>人员：</strong>
					</td>
					<td>
						<input type="text" ztype="text" id="empName" name="empName"/>
					</td>
					<td align="right">
						<strong>领导批示：</strong>
					</td>
					<td>
						<div id="hasCheckes" name="hasCheckes" ztype="select">
							<ul>
								<li val="-1"></li>
								<li val="1">有</li>
								<li val="0">无</li>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<td align="right">
						<strong>开始日期：</strong>
					</td>
					<td>
						<input type="hidden" name="currentDate" value="${currentDate}" />
						<input id="startDate" name="startDate" type="text" value="${currentDate}" ztype="startdate" />
					</td>
					<td align="right">
						<strong>结束日期：</strong>
					</td>
					<td>
						<input id="endDate" name="endDate" type="text" value="" ztype="enddate" />
					</td>

				</tr>
				<tr>
					<td colspan="2" align="center">
						<span class="work_clew_td" style="color:black;"><img src="${request.contextPath}/img/info.png"
							style="margin-bottom: -4px;" /> 如果需要查询历史值班记录，请重新输入开始日期。</span>
					</td>
					<td colspan="2" align="right">
						<button ztype="button" type="button">查询</button>
						<button ztype="button" type="button">下载</button>
					</td>
				</tr>
			</table>
		</form>
    </div>
    <div id="duty-show"></div>
</body>
</html>
