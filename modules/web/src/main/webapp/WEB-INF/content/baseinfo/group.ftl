<html>
<head>
<script language="JavaScript" type="text/javascript">
	$(document).ready(function(){	
	  var colModel=[{name:'id',header:'id',sortable:true,hidden:true,editable:false},
	                {name:'groupType.codeDesc',header:"班组类型",sortable:true,editable:true,verify:"NotNull",inputType:'select', items: ${groupTypeList}},
					{name:'name',header:'班组名称',verify:'NotNull'},
					{name:'dept.name',header:'部门',sortable:true,editable:true,verify:'NotNull',inputType:'select',items:${deptList}}]
					
		$('#group-show').grid({name:'班组信息',colModel:colModel,crudType:'${crudType?if_exists}'});
	});
</script>
</head>
<body>
	<div id="group-show"></div>
</body>
</html>
