<html>
<head>
	<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){	
				$("#tab1").tab(1,0,320,0,0);
				
				//集成平台
				var colModel1=[{name:'id',header:"id",hidden:true,editable:false},
				               {name:'name',header:"业务名称",sortable:true,verify:"NotNull&&Length<=64"},
							   //{name:'transmitDef.codeDesc',header:"业务传输方式",sortable:true,inputType:'select',items:${transmitDefList},verify:'NotNull'},
							  // {name:'cawaveFrq',header:"载波频率",sortable:true,verify:"NotNull&&Decimal=4,1"},
							  // {name:'simbleRate',header:"符号率",sortable:true,verify:"NotNull&&Decimal=4,1"},
							   {name:'startDate',header:"开始时间",sortable:true,verify:"NotNull",dateFmt:'date'},
							   {name:'endDate',header:"结束时间",sortable:true,dateFmt:'date'},
							   //{name:'channalRate',header:"信道编码率",sortable:true},
							   {name:'cawaveP.name',header:"节目流",sortable:true,inputType:'select', items: ${cawaveSList},verify:'NotNull'},
							   {name:'program.name',header:"节目",sortable:true,inputType:'select', items: ${programList},verify:'NotNull'},
							   {name:'rmks',header:"备注",sortable:true}
							  ];
				
				//节目源传输
			  	var colModel2= [{name:'id',header:"id",hidden:true,editable:false},
				               {name:'name',header:"业务名称",sortable:true,verify:"NotNull&&Length<64"},
							{name:'transmitDef.codeDesc',header:"业务传输方式",sortable:true,inputType:'select',items:${transmitDefList},verify:'NotNull'},
							   //{name:'cawaveFrq',header:"载波频率",sortable:true,verify:"NotNull&&Decimal=4,1"},
							  // {name:'simbleRate',header:"符号率",sortable:true,verify:"NotNull&&Decimal=4,1"},
							   {name:'startDate',header:"开始时间",sortable:true,verify:"NotNull",dateFmt:'date'},
							   {name:'endDate',header:"结束时间",sortable:true,dateFmt:'date'},
							  // {name:'channalRate',header:"信道编码率",sortable:true,verify:"NotNull&&Decimal=4,1"},
							   {name:'cawaveT.name',header:"节目流",sortable:true,inputType:'select', items: ${cawaveTList},verify:'NotNull'},
							   {name:'route.pl',header:"路由起止点",sortable:true,inputType:'select', items: ${routeList},verify:'NotNull'},
							   {name:'rmks',header:"备注",sortable:true}];
	
				//卫星上星
		   		var colModel3=[{name:'id',header:"id",hidden:true,editable:false},
						  {name:'name',header:"业务名称",sortable:true,verify:"NotNull&&Length<64"},
	                     //{name:'transmitDef.codeDesc',header:"业务传输方式",sortable:true,inputType:'select',items:${transmitDefList},verify:'NotNull'},
			              {name:'cawaveFrq',header:"载波频率",sortable:true,verify:"NotNull&&Decimal=4,1"},
					      {name:'simbleRate',header:"符号率",sortable:true,verify:"NotNull&&Decimal=4,1"},
		                  {name:'startDate',header:"开始时间",sortable:true,verify:"NotNull",dateFmt:'date'},
		                  {name:'endDate',header:"结束时间",sortable:true,dateFmt:'date'},
					      {name:'channalRate',header:"信道编码率",sortable:true,verify:"NotNull&&Decimal=4,1"},
					      {name:'rmks',header:"备注",sortable:true},
					      {name:'cawaveS.name',header:"节目流",sortable:true,inputType:'select', items: ${cawaveSList},verify:'NotNull'},
					      {name:'satellite.name',header:"卫星",sortable:true,verify:'NotNull'},
					      {name:'transfer.name',header:"转发器名称",sortable:true,inputType:'select', items: ${transferList},verify:'NotNull'}];
				
			var	loadUrlP = '${request.contextPath}/baseinfo/operation-p!load.action';
			var	saveUrlP = '${request.contextPath}/baseinfo/operation-p!save.action';
				$('#operationP-show').grid({name:'集成平台',colModel:colModel1,inputUrl:{content: '${request.contextPath}/baseinfo/operation-p!input.ajax'}, loadUrl:{content:loadUrlP},crudType:'${crudType?if_exists}'});						          

				
			var	loadUrlT = '${request.contextPath}/baseinfo/operation-t!load.action';
			var	saveUrlT = '${request.contextPath}/baseinfo/operation-t!save.action';
				$('#operationT-show').grid({name:'节目源传输',colModel:colModel2, loadUrl:{content:loadUrlT},saveUrl:{content:saveUrlT},crudType:'${crudType?if_exists}'});					          					

				
			var	loadUrlS = '${request.contextPath}/baseinfo/operation-s!load.action';
			var	saveUrlS = '${request.contextPath}/baseinfo/operation-s!save.action';
				$('#operationS-show').grid({name:'卫星上行',colModel:colModel3,inputUrl:{content: '${request.contextPath}/baseinfo/operation-s!input.ajax'}, loadUrl:{content:loadUrlS},crudType:'${crudType?if_exists}'});					          			   
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
	        	<li>业务</li>
	    </#if>	        	
        </ul>
        <#if (transTypesPer?size gt 0)>
        <#list transTypesPer as transType>
       		<div id="operation${transType.paraCode}-show"></div>
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
