<html>
<head>
<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){	
				var colModel=[{name:'id',header:"id",hidden:true,editable:false},
				              {name:'name',header:"转发器名称",sortable:true,editable:true,verify:"NotNull"},
				              {name:'satellite.name',header:"卫星",sortable:true,editable:true,verify:"NotNull",inputType:'select', items: ${satelliteList}}]
							    
				$('#transfer-show').grid({name:'转发器信息',colModel:colModel,crudType:'${crudType?if_exists}'});
			});
		</script>
</head>
<body>
    <div id="transfer-show"></div>
</body>
</html>