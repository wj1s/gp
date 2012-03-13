<html>
<head>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){	
			$("#tab1").tab(1,0,320,0,0);
			
			var colModel=[{name:'id',header:"id",sortable:true,hidden:true,editable:false},
			               {name:'code',header:"技术系统代码",sortable:true,editable:true,verify:'NotNull&&Length<=4&&NotRepeat={val:\'baseinfo.tech-system.code\',delay:true}'},
							{name:'name',header:"名称",sortable:true,editable:true,verify:'NotNull&&Length<=60&&NotRepeat={val:\'baseinfo.tech-system.name\',delay:true}'}]
	
			<#list transTypesPer as transType>
				$('#techSystem-show'+${transType.id}+'').grid({name:'系统',colModel:colModel,
					loadUrl:{content:'${request.contextPath}/baseinfo/tech-system!load.action?transTypeId=${transType.id}'},
					saveUrl:{content:'${request.contextPath}/baseinfo/tech-system!save.action?transTypeId=${transType.id}'},crudType:'${crudType?if_exists}'});
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
	        	<li>系统</li>
	        </#if>	
	        </ul>
	        <#if (transTypesPer?size gt 0)>
	        <#list transTypesPer as transType>
        		<div id="techSystem-show${transType.id}"></div>
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
