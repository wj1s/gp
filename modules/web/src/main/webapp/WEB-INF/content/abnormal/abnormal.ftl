<html>
<head>
	<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){								
				//由异态引起的故障
				var colModel=[{name:'id',header:"id",sortable:true,hidden:true,editable:false},				               
							   {name:'startTime',header:"开始时间",sortable:false,editable:true,verify:"NotNull",dateFmt:'date',now:'endnow'},
							   {name:'endTime',header:"结束时间",sortable:false,editable:true,verify:"NotNull",dateFmt:'date',now:'endnow'},				               
				               {name:'transType',header:"传输类型",sortable:false,editable:true,verify:"NotNull"},				               
							   {name:'desc',header:"异态现象",sortable:false,editable:true,verify:"NotNull"},
							   {name:'reason',header:"异态原因",sortable:false,editable:true,verify:"NotNull"},
							   {name:'processStep',header:"处理措施",sortable:false,editable:true,verify:"NotNull"},
							   {name:'accdCode',header:"事故编号",sortable:false,editable:true,verify:"NotNull"},
							   {name:'accdPrevWay',header:"事故预防措施",sortable:false,editable:true,verify:"NotNull"}	
							   ]										          										   						          						
			var	loadUrl = '${request.contextPath}/abnormal/abnormal!load.action';		
			$('#abnormal-show').grid({name:'故障列表',colModel:colModel,gridType:'onlyCheck',
				viewUrl:{content:'${request.contextPath}/abnormal/abnormal!detail.ajax',width:'950px'},
				loadUrl:{content:loadUrl},operation:{add:false,edit:false,del:false},viewDetail:{view:true}});						          			   
			});
			function searchTrans(){
				var transType = $('#transType').tjVal();
				var url = '${request.contextPath}/abnormal/abnormal!load.action?transType='+transType;
				$('#abnormal-show').tjSearch(url);				
			}
	</script>
</head>
<body>
    <div>
	    <form action="" name="viewForm" id="viewForm">
	    <table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
		    <tr>
				<th colspan="2">
					&lt;&lt;查询条件&gt;&gt;
				</th>
			</tr>
			<tr>
				<td align="right" width="10%">
					<strong>传输类型：</strong>
				</td>
				<td width="90%">
					<div id="transType" name="transType" ztype="select">
						<ul>
						<li val="-1" ></li>
							<#list transTypes as transTypes>
								<li val="${transTypes.id}" >${transTypes.codeDesc}</li>
							</#list>				
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<button ztype="button" type="button" onclick="searchTrans();">查询</button>
				</td>
			</tr>
	    </table>
	</form>
    </div>
    <div id="abnormal-show"></div>
</body>
</html>
