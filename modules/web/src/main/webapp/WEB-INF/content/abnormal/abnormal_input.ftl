<#include "lib/abnormal-temp.ftl">
<html>
<head>
<style type="text/css">
	.o {display: none;}
</style>
<script type="text/javascript" src="${request.contextPath}/js/abnormal/abnormal_input.js"></script>
<script language="JavaScript" type="text/javascript">
$(function() {
	var data=null;
	<#if abnormal?exists>
		data=${abnormal.jsonObject};
	</#if>
	$('#abnormalForm').abnormal(data);
});
</script>
</head>
<body>
<@abnormaltemp editInJb="0"/>
</body>
</html>
