<html>
<head>
<script language="JavaScript" type="text/javascript">
	
	$(document).ready(function(){	
 	  	var colModel=[{name:'id',header:'id',hidden:true,editable:false},
 	  	         {name:'name',header:'节目流名称',verify:'NotNull&&Length<64'},
 	  	         {name:'programInCawaves',sortable:false,header:'节目流对应的节目'}]
 		$('#cawave-show').grid({name:'节目流信息',colModel:colModel, inputUrl:{content: '${request.contextPath}/baseinfo/cawave!input.ajax'},crudType:'${crudType?if_exists}'});
	});
</script>
</head>
<body>
    <div id="cawave-show"></div>
</body>
</html>