<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){	
				$("#tab1").tabInit();
				var url = '${request.contextPath}/report/rept!query.ajax?reptId=';

				<#if reptDefs?exists && (reptDefs?size>0)>
		       		<#list reptDefs as def>
		       			$("#tab1").tabAjax(${def_index}, url + $('#hdReptId_'+${def_index}).val() + '&index=' + ${def_index});
		       		</#list>
	       		</#if>

				$("#tab1").showTab(0);
			});
		</script>
	</head>
	<body>
		<div class="tab" id="tab1">
	        <ul>
	        	<#if reptDefs?exists && (reptDefs?size>0)>
	        		<#list reptDefs as def>
	        			<li>${def.reptName}</li>
	        		</#list>
	        	</#if>
	        </ul>
	        <#if reptDefs?exists && (reptDefs?size>0)>
        		<#list reptDefs as def>
        			<div></div>
        		</#list>
        	</#if>
    	</div>
    	<#if reptDefs?exists && (reptDefs?size>0)>
       		<#list reptDefs as def>
       			<input type="hidden" id="hdReptId_${def_index}" value="${def.reptId}"/>
       		</#list>
       	</#if>
	</body>
</html>
