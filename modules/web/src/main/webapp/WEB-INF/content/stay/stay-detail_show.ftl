<html>
	<head>
		<script type="text/javascript">
			$(document).ready(function(){
				var url = '';
				if ($('#deptId').length > 0){
					url = '${request.contextPath}/stay/stay-detail!load.action?deptId='+$('#deptId').tjVal()+'&startDate=${curDate}';
				}else {
					$('button:contains("查询")').attr('disable', true);
					url = '${request.contextPath}/stay/stay-detail!load.action';
				}
				var colModel=[{name:'id',header:'id',hidden:true,editable:false},
		 	  	           	  {name:'dept.name',header:'部门'},
		 	  	              {name:'startTime',header:'开始时间'},
		 					  {name:'endTime',header:'结束时间'},
		 					  {name:'empNames',header:'人员'}];
		 		$('#stay-detail-show').grid({name:'留守信息',colModel:colModel,
			 		loadUrl:{content:url}, inputUrl:{content:'${request.contextPath}/stay/stay-detail!change.ajax', width:"570"},crudType:'${crudType?if_exists}',setting:{edit:true,search:false,multiselect:false,toolbar:[true,"both"]}});

		 		$('button:contains("查询")').click(function(){
			 		var deptId = '';
			 		var options = {};
		 			if ($('#deptId').length > 0){
						options.deptId = $('#deptId').tjVal();
		 			}
					if ($('#startTime').val() != ''){
						options.startTime = $('#startTime').val();
					}
					if ($('#endTime').val() != ''){
						options.endTime = $('#endTime').val();
					}
					if ($('#empName').val()){
						options.empName = $('#empName').val();
					}
					var url = '${request.contextPath}/stay/stay-detail!load.action';
					$('#stay-detail-show').tjSearchPost(url, options);
				});
			});
		</script>
	</head>
	<body>
		<div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
				<tr>
					<th align="center" colspan="4">
						<strong>留守信息查询</strong>
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
										<li val="${dept.id }">${dept.name }</li>
									</#list>
								</ul>
							</div>
						<#else>
							当前人员没有任何权限部门
						</#if>
					</td>
					<td align="right" width="20%">
						留守人员：
					</td>
					<td align="left" width="30%">
						<input type="text" ztype="text" name="empName" id="empName">
					</td>
				</tr>
				<tr>
					<td align="right" width="20%">
						排班开始日期：
					</td>
					<td align="left" width="30%">
						<input type="text" id="startTime" name="startTime" ztype="date" value="${curDate?string('yyyy-MM-dd')}"/>
					</td>
					<td align="right" width="20%">
						排班结束日期：
					</td>
					<td align="left" width="30%">
						<input type="text" id="endTime" name="endTime" ztype="date"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<span class="work_clew_td" style="color:black;"><img src="${request.contextPath}/img/info.png"
							style="margin-bottom: -4px;" /> 如果需要查询历史留守记录，请重新输入开始日期。</span>
					</td>
					<td colspan="2" align="right">
						<!--
						<button ztype="button" type="button">打印</button>
						-->
						<button ztype="button" type="button">查询</button>
					</td>
				</tr>
			</table>
		</div>
		<div id="stay-detail-show"></div>
	</body>
</html>
