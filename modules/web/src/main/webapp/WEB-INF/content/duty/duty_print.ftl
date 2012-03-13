<html>
<head>
	<style type="text/css">
		body,td,th {
			font-family: Verdana, Geneva, sans-serif;
			font-size: 12px;
		}
		h2{font-size:24px; font-weight:bold; font-family:simsun}
		.test2012 .textInput{ background:#fff; border:0; border-bottom:1px #000 solid; text-align:right; padding-right:2px; font-size:18px;}
		.test2013 .textInput{ background:#fff; border:0; border-bottom:1px #000 solid; text-align:right; padding-right:2px; font-size:18px;}
		.testTbale2012{ background:#000;}	
		.testTbale2012 td{background:#fff;}
		.odd{background:#fff;}
	</style>	
<script language="JavaScript" type="text/javascript">
	$(document).ready(function(){	

 	});	
</script>
</head>
<body>
    <div id="duty-show1"></div>
	<table  border="0" cellspacing="0" cellpadding="4" class="test2012" width="100%" >
	  <tr>
	    <td align="center"><h2>排班信息</h2></td>
	  </tr>
	</table>    
    <table width="100%" border="0" cellspacing="1" cellpadding="4" class="testTbale2012">
    <tr><td align="center">日期</td><td align="center">班组</td><td align="center">班次</td><td align="center">上班时间</td><td align="center">下班时间</td><td align="center">人员</td></tr>
	<#if (carrier?exists)&&(carrier.result.size()!=0) >
	<#list carrier.result as result>	
	
	</#list>							
	</#if>	   
    </table>
</body>
</html>
