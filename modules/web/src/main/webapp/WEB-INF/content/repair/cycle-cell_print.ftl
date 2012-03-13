<html>
<head>	
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title></title>
<script language="JavaScript" type="text/javascript">
var locationUrl = "${request.contextPath}/Interface/";
var ctp = "${request.contextPath}";
</script>
<style>
fieldset{
	padding:6px;
	margin-bottom:6px;
	text-align:center;
	}
fieldset legend{
	font-weight:bold;	
	font-size: 1.2em;
}
body {
	font-family: Tahoma;font-size: 12px;
	margin: 0;
	margin-left: 7px;
	width:97%;
	color:#333;
	padding-top:4px;
}	
</style>
<script type="text/javascript" src="${request.contextPath}/plugin/jquery-ui-1.8.4.custom/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/jquery-ui-1.8.4.custom/js/jquery-ui-1.8.4.custom.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/tj/tjStyle/jquery.tj.style.js"></script>
	<script language="JavaScript" type="text/javascript">
	$(document).ready(function(){		
		
 	});				
	</script>
</head>
	<body>
	<div>
		<div style="font-size: 16px;margin-bottom:2px;text-align:center;font-weight:bold">
			${formMap.cycle.name}
		</div>
		<#if formMap.cycle.cycleCells.size() != 0>
		<#list formMap.cycle.cycleCells as cycleCells>	
	    <fieldset class="fieldset">
	      	<legend>
	      	${cycleCells.name}
	      	</legend>				
	      	<div>		      				      					      		
				<#if (cycleCells.cards?exists)&&(cycleCells.cards.size()!=0) >
				<#list cycleCells.cards as cards>	
				<table width="100%">										      	
	      			<tr>
	      			<td width="80%" align="left">${cards.name}</td>
	      			<td width="10%" align="center">${cards.period.name}</td>		      						      			
	      				<td width="10%">					      				
							<#if cards.active=="1">											
								启用
							<#else>
								停用
							</#if> 				      								      								      																									
	      				</td>				      				
	      			</tr>
	      		</table>	
				</#list>							
				</#if>				      					      				      		
			</div>			
			<div style="text-align:left;;margin-left: 15px;margin-top:10px;">共有 <span style="color:red;font-weight: bold;">${cycleCells.cards.size()}</span> 条</div>
		</fieldset>		
		</#list>
		</#if>  
	</div>        		
	</body>
</html>