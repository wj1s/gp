<html>
<head>
<script language="JavaScript" type="text/javascript">
	$(document).ready(function(){	
	  var colModel=[{name:'id',header:'id',sortable:true,hidden:true,editable:false},
	                {name:'name',header:'姓名',verify:'NotNull&&Length<20&&NotRepeat={val:\'baseinfo.person.name\',delay:true}'},
	                {name:'loginName',header:'登录名',hidden:true,editable:true,verify:'NotNull&&Length<60&&NotRepeat={val:\'baseinfo.person.loginName\',delay:true}'},
	                {name:'loginPassword',header:'密码',hidden:true,editable:true,verify:'NotNull&&Length<60'},
					{name:'code',header:'人员编号',verify:'NotNull&&Length<200&&NotRepeat={val:\'baseinfo.person.code\',delay:true}'},
					{name:'dept.name',header:"所属部门",sortable:true,editable:true,inputType:'select',relate:'group.name', relateUrl:'${request.contextPath}/baseinfo/dept!getGroupsAjax.action', items: ${deptList},verify:'NotNull'},
					{name:'group.name',header:"班组名称",sortable:true,editable:true,inputType:'select',related:'dept.name'},
					{name:'post.name',header:'职务',inputType:'select', items: ${postList},verify:'NotNull'},
					{name:'sex',header:'性别',inputType:'select', items: ${sexList}},
					{name:'officeTel',header:'办公电话',verify:'Length<40'},
					{name:'mobile',header:'手机号码',verify:'Length<20&&Int'},
					{name:'officePassword',header:'短号码',verify:'Length<40'},
					{name:'email',header:'电子邮箱',verify:'Length<100&&Email'},
					{name:'dorm',header:'宿舍号',verify:'Length<100'},
					{name:'idtyCard',header:'身份证号码',verify:'Length<19'}]
		$('#person-show').grid({name:'人员信息',colModel:colModel,crudType:'${crudType?if_exists}'});
	});
</script>
</head>
<body>
	<div id="person-show"></div>
</body>
</html>
