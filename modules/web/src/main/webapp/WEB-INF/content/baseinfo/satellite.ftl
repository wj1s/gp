<html>
<head>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){	
		  var colModel=[{name:'id',header:'id',sortable:true,hidden:true,editable:false},
		                {name:'name',header:'卫星名称',verify:'NotNull&&Length<20&&NotRepeat={val:\'baseinfo.satellite.name\',delay:true}'},
						{name:'stlOrbit',header:'轨道位置',verify:'Length<64'}]
						
			$('#satellite-show').grid({name:'卫星信息',colModel:colModel,crudType:'${crudType?if_exists}'});
		});
	</script>
</head>
<body>
	<div id="satellite-show"></div>
</body>
</html>
