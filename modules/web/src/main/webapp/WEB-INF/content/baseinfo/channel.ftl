<html>
<head>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){	
			$("#tab1").tab(1,0,320,0,0);
			
			var colModel=[{name:'id',header:"id",sortable:true,hidden:true,editable:false},
			              {name:'techSystem.name',header:"所属技术系统",sortable:true,editable:true,verify:"NotNull",inputType:'select', items: ${techSystemList}},
						  {name:'name',header:"通道名称",sortable:true,editable:true,verify:"NotNull&&Length<20"}]
             $('#channel-show').grid({name:'通路信息',colModel:colModel,crudType:'${crudType?if_exists}'});
		});
	</script>
</head>

    <body>
    <div id="channel-show"></div>
</body>
</html>