<html>
<head>
<script language="JavaScript" type="text/javascript">
	$(document).ready(function(){	
		$('button:contains("查询")').click(function(){
			var equipTypeId = $('#equipTypeId').tjVal();
			var url = '${request.contextPath}/repair/card-model!loadCardModel.action?equipTypeId='+equipTypeId;			
			$('#cardModel-show').tjSearch(url);
		});
	  	var colModel=[{name:'id',header:'id',sortable:true,hidden:true,editable:false},
	  	            {name:'equipType.codeDesc',header:'设备类型',verify:'NotNull'},
	                {name:'name',header:'卡片模版',verify:'NotNull'},
					{name:'remark',header:'备注',verify:'NotNull'}
					]
		$('#cardModel-show').grid({name:'检修卡片管理',colModel:colModel,
	  	loadUrl:{content:'${request.contextPath}/repair/card-model!loadCardModel.action'},
		viewUrl:{content:'${request.contextPath}/repair/card-model!scanCardModelDetail.ajax',width:'600px'},
		inputUrl:{content:'${request.contextPath}/repair/card-model!toAddEdit.ajax',width:'600px'},
		operation:{add:true,edit:true,del:true},viewDetail:{view:true},gridType:'noSearch'});
		});
		
</script>
</head>
<body>
	<div>
	    <form action="" name="viewForm" id="viewForm">
	    <table class="work_info_table" width="100%"  border="0" cellpadding="0" cellspacing="0">
	    <tr>
			<th colspan="2">
					&lt;&lt;查询条件&gt;&gt;
			</th>
			</tr>
			<tr>
				<td align="right" width="15%">
					<strong>设备类型：</strong>
				</td>
				<td>
					<div id="equipTypeId" name="equipTypeId" ztype="select">
	 					<ul>
	 							<li val="-1">全部</li>
							<#list equipTypes as equipType>
								<li val="${equipType.id}">${equipType.codeDesc}</li>
							</#list>
						</ul>
					</div>
				</td>					
			</tr>
	        <tr>
				<td colspan="2" align="right">
					<button ztype="button" type="button">查询</button>
				</td>
			</tr>
		</table>
		</form>
    </div>
	<div id="cardModel-show"></div>
</body>
</html>
