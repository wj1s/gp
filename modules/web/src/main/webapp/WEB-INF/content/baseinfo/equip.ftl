<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){	
				$("#tab1").tab(1,0,320,0,0);
				
				//平台
				var colModel=[{name:'id',header:"id",hidden:true,editable:false},
				              {name:'name',header:"名称"},
				              {name:'tache.name',header:"所属环节"},				              
				              {name:'channels.name',header:'所属通路'},
				              {name:'equipModel.equipType.codeDesc',header:"设备类型"},
							  {name:'equipModel.name',header:"设备型号"}];
						         
					$('#equip-show').grid({name:'设备信息',colModel:colModel,inputUrl:{content:'${request.contextPath}/baseinfo/equip!input.ajax'},crudType:'${crudType?if_exists}'});
			});
		</script>
	</head>
	<body>
	    <div id="equip-show"></div>
	</body>
</html>