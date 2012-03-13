<html>
<head>
	<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){								
				//由异态引起的故障
				var colModel=[{name:'id',header:"id",sortable:true,hidden:true,editable:false},	
				               {name:'name',header:"报表名称",sortable:false,editable:true,verify:"NotNull"},	
							   {name:'startDate',header:"开始时间",sortable:false,editable:true,verify:"NotNull",dateFmt:'date',now:'endnow'},
							   {name:'endDate',header:"结束时间",sortable:false,editable:true,verify:"NotNull",dateFmt:'date',now:'endnow'}			               				               			               							  							   
							   ]										          										   						          						
			var	loadUrl = '${request.contextPath}/report/tech-reporting!load.action';		
			$('#tech-show').grid({name:'技术安全报告列表',colModel:colModel,gridType:'onlyCheck',
				viewUrl:{content:'${request.contextPath}/report/tech-reporting!toPreviewTechReport.ajax',width:'950px'},
				loadUrl:{content:loadUrl},operation:{add:false,edit:false,del:false},viewDetail:{view:true}});						          			   
			});
			function searchTrans(){
				if ($("#techAccidentForm").data('tjForm').checkSubmit()) {
					var startTime = $('#startTime').val();
					var endTime = $('#endTime').val();				
					var url = '${request.contextPath}/report/tech-reporting!load.action?formMap.startDate='+startTime+'&formMap.endDate='+endTime;					
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
			<td><input name="formMap.startDate" id="startTime" type="text" value="${formMap.techAccident.startDate?string("yyyy-MM-dd hh:mm:ss")}" ztype="startdatetime" verify="开始时间|NotNull&&Datetime"/></td>
			<#else>
			<td><input name="formMap.startDate" id="startTime" type="text" value="" ztype="datetime" verify="开始时间|Datetime"/></td>
			</#if>		
			<td align="right"><strong>结束时间：</strong></td>						
			<#if formMap.endDate?exists>
			<td><input name="formMap.endDate" id="endTime" type="text" value="${formMap.techAccident.endDate?string("yyyy-MM-dd hh:mm:ss")}" ztype="datetime" verify="结束时间|Datetime"/></td>
			<#else>
			<td><input name="formMap.endDate" id="endTime" type="text" value="" ztype="datetime" verify="结束时间|Datetime"/></td>
			</#if>										
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
