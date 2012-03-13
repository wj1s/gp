<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$("#tab1").tabInit();
				//排班页面
				var arrangeUrl = '${request.contextPath}/duty/duty-rule!arrange.ajax';
				$("#tab1").tabAjax(0, arrangeUrl);

				//快速调班页面
				var quickchangeUrl = '${request.contextPath}/duty/duty!quickChange.ajax';
				$("#tab1").tabAjax(1, quickchangeUrl);
				
				//替换班页面
				var replaceUrl = '${request.contextPath}/duty/duty!toReplace.ajax';
				$("#tab1").tabAjax(2, replaceUrl);

				//删除人员页面
				var addUrl = '${request.contextPath}/duty/duty!toAddStaff.ajax';
				$("#tab1").tabAjax(3, addUrl);

				//删除人员页面
				var delUrl = '${request.contextPath}/duty/duty!toDeleteStaff.ajax';
				$("#tab1").tabAjax(4, delUrl);

				$("#tab1").showTab(0);
			});
		</script>
	</head>
	<body>
		<div class="tab" id="tab1">
	        <ul> 
	            <li>排班</li>
	            <li>快速调班</li>
	            <li>替换班</li>
	            <li>新增排班人员</li>
	            <li>删除排班人员</li>
	        </ul>
	        <div id="arrange"></div>
	        <div></div>
	        <div></div>
	        <div></div>
	        <div></div>
    	</div>
	</body>
</html>
