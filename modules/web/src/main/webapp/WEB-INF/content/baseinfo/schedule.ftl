<html>
<head>

	<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){	
				//平台
				var colModel=[{name:'id',header:"id",sortable:true,hidden:true,editable:false},
				             {name:'operation.name',header:"所属业务",sortable:true,verify:"NotNull",inputType:'select', items: ${operationList}},
				              {name:'channel.name',header:"所属通路",sortable:true,verify:"NotNull",inputType:'select', items: ${channelList}},
							  {name:'startDate',header:"开始时间",sortable:true,verify:"NotNull",dateFmt:'date'},
							  {name:'endDate',header:"结束时间",sortable:true,verify:"NotNull",dateFmt:'date'}]
				$('#schedule-show').grid({name:'运行图',colModel:colModel,crudType:'${crudType?if_exists}'});
			});
		</script>
		</head>
		<body>
			<div id="schedule-show"></div>
		</body>
		</html>
