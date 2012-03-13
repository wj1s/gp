<html>
<head>
	<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){								
				//由异态引起的故障
				var colModel=[{name:'id',header:"id",sortable:true,hidden:true,editable:false},	
				               {name:'accdCode',header:"事故编号",sortable:false,editable:true,verify:"NotNull"},	
							   {name:'startTime',header:"发生时间",sortable:false,editable:true,verify:"NotNull",dateFmt:'date',now:'endnow'},
							   {name:'endDate',header:"结束时间",sortable:false,editable:true,verify:"NotNull",dateFmt:'date',now:'endnow'},				               				               			               							  
							   {name:'accdReason',header:"事故简况 ",sortable:false,editable:true,verify:"NotNull"},
							   {name:'dutyPerson',header:"责任人",sortable:false,editable:true,verify:"NotNull"}
							   ]										          										   						          						
			var	loadUrl = '${request.contextPath}/abnormal/tech-accident!load.action';		
			$('#tech-show').grid({name:'技术安全事故列表',colModel:colModel,gridType:'onlyCheck',
				viewUrl:{content:'${request.contextPath}/abnormal/tech-accident!viewTechAccident.ajax',width:'950px'},
				loadUrl:{content:loadUrl},operation:{add:false,edit:false,del:false},viewDetail:{view:true}});						          			   
			});
			function searchTrans(){
				if ($("#techAccidentForm").data('tjForm').checkSubmit()) {
					var startTime = $('#startTime').val();
					var endTime = $('#endTime').val();
					var endFlag = $('#endFlag').tjVal();
					var accdKind = $('#accdKind').tjVal();					
					var url = '${request.contextPath}/abnormal/tech-accident!load.action?formMap.startTime='+startTime+'&formMap.endDate='+endTime+'&formMap.endFlag='+endFlag+'&formMap.accdKind='+accdKind;					
					$('#tech-show').tjSearch(url);	
				}
			}
	</script>
</head>
<body>
    <div>
	    <form action="" name=techAccidentForm id="techAccidentForm" ztype="form">
	    <table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
		    <tr>
				<th colspan="4">
					&lt;&lt;查询条件&gt;&gt;
				</th>
			</tr>
			<tr>
			<td align="right"><strong>开始时间：</strong></td>
			<#if formMap.startTime?exists>
			<td><input name="formMap.startTime" id="startTime" type="text" value="${formMap.techAccident.startTime?string("yyyy-MM-dd hh:mm:ss")}" ztype="startdatetime" verify="开始时间|NotNull&&Datetime"/></td>
			<#else>
			<td><input name="formMap.startTime" id="startTime" type="text" value="" ztype="datetime" verify="开始时间|Datetime"/></td>
			</#if>		
			<td align="right"><strong>结束时间：</strong></td>						
			<#if formMap.endDate?exists>
			<td><input name="formMap.endDate" id="endTime" type="text" value="${formMap.techAccident.endDate?string("yyyy-MM-dd hh:mm:ss")}" ztype="datetime" verify="结束时间|Datetime"/></td>
			<#else>
			<td><input name="formMap.endDate" id="endTime" type="text" value="" ztype="datetime" verify="结束时间|Datetime"/></td>
			</#if>										
			</tr>
			<tr>
				<td align="right">
					<strong>是否结束：</strong>
				</td>
				<td>
				<div id="endFlag" name="formMap.endFlag" ztype="select">
					<ul>
						<#if formMap.endFlag?exists && formMap.endFlag=='-1'>
							<li val="-1" selected="selected">全部</li>								
							<li val="Y" >是</li>
							<li val="N" >否</li>	
						<#elseif formMap.endFlag?exists && formMap.endFlag=='Y'>
							<li val="-1" >全部</li>								
							<li val="Y" selected="selected">是</li>
							<li val="N" >否</li>	
						<#else>
							<li val="-1" >全部</li>								
							<li val="Y" >是</li>
							<li val="N" selected="selected">否</li>														
						</#if>				
					</ul>
				</div>																
				</td>
				<td align="right">
					<strong>事故性质：</strong>
				</td>
				<td>
					<div id="accdKind" name="formMap.accdKind" ztype="select">
						<ul>
							<#if formMap.kindList?exists>
								<#if formMap.accdKind?exists &&  '-1'==formMap.accdKind>
									<li val="-1" selected="selected">全部</li>
								<#else>
									<li val="-1" >全部</li>
								</#if>						
								<#list formMap.kindList as accdKind>
									<#if formMap.accdKind?exists &&  accdKind.paraCode==formMap.accdKind>
										<li val="${accdKind.paraCode}" selected="selected">${accdKind.codeDesc}</li>
									<#else>
										<li val="${accdKind.paraCode}" >${accdKind.codeDesc}</li>
									</#if>							
								</#list>	
							<#else>
									<li val="-1">无</li>
							</#if>				
						</ul>
					</div>																			
				</td>
			</tr>			
			<tr>
				<td colspan="4" align="right">
					<button ztype="button" type="button" onclick="searchTrans();">查询</button>
				</td>
			</tr>
	    </table>
	</form>
    </div>
    <div id="tech-show"></div>
</body>
</html>
