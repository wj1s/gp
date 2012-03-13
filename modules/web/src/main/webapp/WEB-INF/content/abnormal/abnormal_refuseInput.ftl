<html>
<head>
<script language="JavaScript" type="text/javascript">
var subFlag=true;
$(document).ready(function(){
	//设置验证form			
	 $('#abnormalRefForm').form();
});
function refuFun(){
	var check=$('#abnormalRefForm').data('tjForm').checkSubmit();
	var obj=document.getElementById('abnormalRefForm');
	if(check){
		if(subFlag){
			subFlag=false;
			tjConfirm("确定将该异态设置为非事故异态？",function(flag){
				if (!flag){	
					subFlag=true;
					return;
				}else{					
					obj.submit();									
				}
			});				
		}		
	}else{
		return ;			
	}
}
</script>
</head>
<body>
<form id="abnormalRefForm" method="post"  action="abnormal!refuse.action">
<input type="hidden" id="refId" name="id" value="${id}"/>
<input type="hidden" id="refTaskId" name="taskId" value="${taskId}"/>
<textarea name="comment" id="descR" rows="7" cols="50"  
			length="300" autosize="true" verify="备注|NotNull&&Length<300" ztype="textarea">审核不通过</textarea>
<br>
<div align="right" style="margin-bottom:0px">
<input type="button" id="saveButtonR" value="保存" ztype="button" onclick="refuFun();"/>
<input type="button"  value="关闭" ztype="button" onclick="$('#dialogDiv').dialog('close');"/> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</form>
</div>
</body>
</html>
