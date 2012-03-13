<html>
<head>
	<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){	
				$("#tab1").tab(1,0,500,0,0);
				var colModel=[{name:'id',header:"id",sortable:true,hidden:true,editable:false},
								{name:'name',header:'名称',sortable:true,editable:true,verify:'NotNull&&Length<20&&NotRepeat={val:\'baseinfo.tache.name\',delay:true}'}];
				<#list transTypesPer as transType>
					$('#tache-show'+${transType.id}+'').grid({name:'环节',colModel:colModel,
						loadUrl:{content:'${request.contextPath}/baseinfo/tache!load.action?transTypeId=${transType.id}'},
						saveUrl:{content:'${request.contextPath}/baseinfo/tache!save.action?transTypeId=${transType.id}'},crudType:'${crudType?if_exists}'});
				</#list>
			});
	</script>
</head>
<body>
    <!-- 导航结束 -->
    <!-- 标签开始 -->
    <div class="tab" id="tab1">
	        <ul>	      
	        	<#if (transTypesPer?size gt 0)>
		        	<#list transTypesPer as transType>
		        		<li>${transType.codeDesc}</li>
		        	</#list>
	        	<#else>
	        	<li>环节</li>
	        	</#if>
	        </ul>
	        <#if (transTypesPer?size gt 0)>
	        <#list transTypesPer as transType>
        		<div id="tache-show${transType.id}"></div>
            </#list>
            <#else>
	        	<div>
	        	您没有对应的权限，请向管理员申请！
	        	</div>
	        </#if>
	 </div>
    <!-- 标签结束 -->
</body>
</html>
