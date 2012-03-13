<html>
<head>
<script language="JavaScript" type="text/javascript">
	
	$(document).ready(function(){	
 	  	var colModel=[{name:'id',header:'id',hidden:true,editable:false},
 	  	              {name:'name',header:'周期名称',verify:'NotNull'},
 	  	              {name:'rule',header:'规则',verify:'NotNull'},
 	  	              {name:'previousValue',header:'提醒',verify:'NotNull'}]
 		$('#period-show').grid({name:'检修周期',colModel:colModel, inputUrl:{content: '${request.contextPath}/repair/period!input.ajax'}});
	});
</script>
</head>
<body>
    <div id="period-show"></div>
</body>
</html>
