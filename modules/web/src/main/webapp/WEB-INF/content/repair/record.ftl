<html>
<head>
<script language="javascript" type="text/javascript">
	$(document).ready(function(){	
		$('button:contains("查询")').click(function(){
			var dpId = $('#dpId').tjVal();
			var groupId = $('#groupId').tjVal();
			var cycleId = $('#cycleId').val();
			var startDate = $('#startDate').val();
			var endDate = $('#endDate').val();
			var cardName = encodeURIComponent($('#cardName').val());
			var equipName= $('#equipF').val();
			var url = '${request.contextPath}/repair/record!loadRecord.action?'+ 
				'dpId='+dpId+'&groupId='+groupId+'&startDate='+startDate+'&endDate='+endDate+'&cycleId='+cycleId+'&cardName='+cardName+'&equipName='+equipName;
			$('#record-show').tjSearch(url);
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

				});
			$.getJSON('${request.contextPath}/baseinfo/dept!getCyclesAjax.action?rd='+Math.random(),
					{'id':id},
					/*周期表*/
					function(data){
						$('#cycleId').tjEmpty();
					if (data.result){
						var array = eval(data['data']);
						var html = '<li val="">无</li>';
						for(var i in array){
							html += '<li val="'+array[i].value+'">'+array[i].name+'</li>';
						}
						$('#cycleId ul').append(html);
						$('#cycleId').tjAssemble();
					}
				});
		});
		 <#if dpId?exists>
			var dpId = ${dpId};
		<#else>
			var dpId = 0;
			$('button').attr('disabled',true);
		</#if>
		var colModel=[{name:'id',header:'id',sortable:false,hidden:true,editable:false},
		  	            {name:'group.dept.name',header:'部门',verify:'NotNull'},
		                {name:'group.name',header:'班组'},
						{name:'examinePersons',header:'检修人员',verify:'NotNull'},
						{name:'ddate',header:'检修日期',dateFmt:'date',verify:'NotNull'},
						{name:'timeLength',header:'检修时长',dateFmt:'timeHM',verify:'NotNull'},
						{name:'principal',header:'负责人',verify:'NotNull'},
						{name:'security',header:'安全员',hidden:true,verify:'NotNull'},
						{name:'measure',header:'安全措施',hidden:true,inputType:'textArea',verify:'NotNull'},
						{name:'examineRecord',header:'检修记录',hidden:true,inputType:'textArea',verify:'NotNull'},
						{name:'test',header:'试机情况',hidden:true,inputType:'textArea',verify:'NotNull'}];
			 $('#record-show').grid({name:'检修记录管理',colModel:colModel,
				loadUrl:{content:'${request.contextPath}/repair/record!loadRecord.action?dpId='+dpId},
				viewUrl:{content:'${request.contextPath}/repair/record!scanRecordDetail.ajax',width:'600px'},
				inputUrl:{content:'${request.contextPath}/repair/record!inputRecordDetail.action',type:'redirect'},
				delUrl:{content:'${request.contextPath}/repair/record!delRecord.ajax'},
				operation:{add:false,edit:true,del:true},viewDetail:{view:true},gridType:'noSearch',crudType:'${crudType?if_exists}'});
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
								<li val=""></li>
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
					<td align="right" >
						<strong>系统：</strong>
					</td>
					<td>
						<div id="cycleId" name="cycleId" ztype="select">
   							<ul>
   							<li val=""></li>
   							<#if depts?exists>
   							<#list depts as dept>
   							<#if dept.cycles?exists>
								<#list dept.cycles as cycle>
									<li val="${cycle.id}">${cycle.name}</li>
								</#list>
							</#if>
							</#list>
							</#if>
							</ul>
						</div>
					</td>
					<td align="right" width="15%">
						<strong>项目查询：</strong>
					</td><td><input type="text"  id="cardName" ztype="text" name="cardName"/></td>
					
				</tr>
					<tr>
					<td align="right">
						<strong>相关设备：</strong>
					</td>
					<td>
						<input id='equipF' name='equipName'  ztype="auto" zurl="${request.contextPath}/baseinfo/equip!autocompleteTAjax.action" value=""   maxlength="50" />
					</td>
					<td align="right">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>

				</tr>               
					<tr>
					<td align="right">
						<strong>开始日期：</strong>
					</td>
					<td>
						<input id="startDate" name="startDate" type="text"  ztype="startdate" />
					</td>
					<td align="right">
						<strong>结束日期：</strong>
					</td>
					<td>
						<input id="endDate" name="endDate" type="text" value=""   ztype="enddate" />
					</td>

				</tr>
					<tr>
					<td colspan="4" align="right">
						<button ztype="button" type="button">查询</button>
					</td>
					</tr>
    </table>
</form>
    </div>
	<div id="record-show"></div>
</body>
</html>
