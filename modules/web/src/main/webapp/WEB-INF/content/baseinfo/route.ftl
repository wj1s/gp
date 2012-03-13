<html>
<head>
	<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){	
				var colModel=[{name:'id',header:"id",hidden:true,editable:false},
				              {name:'fromPl',header:"起点",sortable:true,editable:true,verify:"NotNull&&Length<20"},
				              {name:'toPl',header:"终点",sortable:true,editable:true,verify:"NotNull&&Length<20"}]
							    
				$('#route-show').grid({name:'路由信息',colModel:colModel,crudType:'${crudType?if_exists}'});
			});
	</script>
</head>
<body>
    <div id="route-show"></div>
</body>
</html>