<html>
<head>
<script language="JavaScript" type="text/javascript">
	
	$(document).ready(function(){	
 	  	var colModel=[{name:'id',header:'id',hidden:true,editable:false},
 	  	            {name:'name',header:'部门名称',sortable:true,verify:'NotNull&&NotRepeat={val:\'baseinfo.dept.name\',delay:true}'},
 	  	            {name:'station.name',header:'所属台站',sortable:true,editable:false,defaultVal:'北京地球站',canSubmit:false},
 	                {name:'code',header:'部门编号',sortable:true,verify:'NotNull&&Int&&Length=4&&NotRepeat={val:\'baseinfo.dept.code\',delay:true}'},
 					{name:'deptType.codeDesc',header:'部门类型',sortable:true, verify:'NotNull', inputType:'select', items: ${deptTypeList}},
 					{name:'transTypes.codeDesc',header:'传输类型',sortable:false, inputType:'checkbox',items:${transTypeList}}];
 		$('#dept-show').grid({name:'部门信息',colModel:colModel,crudType:'${crudType?if_exists}'});
	});
</script>
</head>
<body>
    <div id="dept-show"></div>
</body>
</html>
