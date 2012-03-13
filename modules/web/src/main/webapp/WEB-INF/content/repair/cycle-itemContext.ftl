<html>
<head>
	<script language="JavaScript" type="text/javascript">
	$(document).ready(function(){		
	  
 	});				
	</script>
</head>
<body>
	<#if formMap.itemContext.state=="01">
	<img src="${request.contextPath}/img/green.jpg" />正常
	<#elseif formMap.itemContext.state=="02">
	<img src="${request.contextPath}/img/yello.jpg" />提醒到期
	<#elseif formMap.itemContext.state=="03">
	<img src="${request.contextPath}/img/blue.jpg" />到期
	<#elseif formMap.itemContext.state=="04">
	<img src="${request.contextPath}/img/red.jpg" />过期
	<#else>
	<img src="${request.contextPath}/img/weizhi.jpg"/>条件不足，无法预计检修日期
	</#if>
	<hr/>
	<#if formMap.itemContext.state!="05">
	上次检修日期：${formMap.itemContext.lastDate}<br/>
	${formMap.itemContext.peoples}
	<br/>
	距离上次检修已经过了${formMap.itemContext.lastTime}天
	<#else>
	本项目还没有检修过。
	</#if>
	<hr/>
	<#if formMap.itemContext.state=="01"||formMap.itemContext.state=="02"||formMap.itemContext.state=="03">
	预计下次检修日期：${formMap.itemContext.nextDate}<br/>
	距离下次检修还有：${formMap.itemContext.nextTime}天<br/>
	<#elseif formMap.itemContext.state=="04">
	预计检修日期应为：${formMap.itemContext.nextDate}<br/>
	目前已过期：${formMap.itemContext.nextTime}天<br/>
	<#else>
	条件不全无法预计下次检修日期。
	</#if>	
</body>
</html>
