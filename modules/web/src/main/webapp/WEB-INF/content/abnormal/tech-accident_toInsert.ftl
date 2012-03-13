<html>
<head>
<style  type="text/css" >
	.uploadImg{ overflow:hidden; position:absolute}
	.delLoadImg{ vertical-align:middle; cursor:hand}
	.file{position:absolute; z-index:100; margin-left:-180px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;height:26px;}
</style>
<script type="text/javascript" language="javasrcipt">    
     function add(){
     	if ($("#techAccidentForm").data('tjForm').checkSubmit()) {
     		if(document.getElementById("isAccd1").checked){
    			document.getElementById("endFlag").value="Y";
	  		}else{
     			document.getElementById("endFlag").value="N";
     		}
	       if (confirm('确定提交领导审核?')) {	          
	           document.getElementById("sbButton").disabled=true; //添加数据处理提示信息sbButton
	       	   document.getElementById("techAccidentForm").submit();
	       }        		
     	}
     }
     $().ready(function(){
    		$('#isAccd1').checkbox();	
			$('#fileAjaxForm').form();
			$('#fileAjaxForm').init_style();
			$('#fileAjaxForm').ajaxForm({
				type: "post",
				dataType: 'json',
				beforeSubmit: validateForm,
				success: function(data){
					if (data['result'] == true){
						if((document.getElementById("addEditFlag1").value)=="edit"){
							afterUploading(data['saveName']);
						}else{
							afterDeleteLoading();
						}	
					}else {						
						if((document.getElementById("addEditFlag1").value)=="edit"){
							tjAlert("文件上传失败!");
						}else{
							tjAlert("文件删除失败!");
							document.getElementById("formFile").name='formFile';
						}  						
					}
				}
			});    		
    });   
	function validateForm(){
		return true;
	}
  	function submitAll(){
    	return true;
    }     
    function uploading(){
    	document.getElementById("addEditFlag1").value="edit";    	
    	document.getElementById("fileAjaxForm").action='${request.contextPath}/abnormal/tech-accident!upLoadOne.action';
    	var fileNameFull=document.getElementById("fileUp_0").value;
    	var fileNameArr=fileNameFull.split('\\');
    	document.getElementById("fileName").value=fileNameArr[fileNameArr.length-1];
    	$('#fileAjaxForm').submit();
    }
    function afterUploading(saveName){
    	var fileName=document.getElementById("fileName").value;
    	var temHtml=document.getElementById("td_0").innerHTML;
    	temHtml=temHtml+'<a style="margin-left:7px;" id="id_a_'+saveName+'" href="${request.contextPath}/abnormal/tech-accident!downLoadOne.action?showName='+fileName+'&saveName='+saveName+'">'+fileName+'</a>';
    	temHtml=temHtml+'<img id="id_i_'+saveName+'" src="${request.contextPath}/img/del_f.gif" class="delLoadImg" onclick="deleteLoading(\''+saveName+'\');">';
    	document.getElementById("td_0").innerHTML=null;
    	document.getElementById("td_0").innerHTML=temHtml;
    	//先清理-savename和showname放在form中
    	var saveNameV=document.getElementById("saveName").value;
    	var showNameV=document.getElementById("showName").value;
    	if(saveNameV==''){
    		document.getElementById("saveName").value=saveName;
    		document.getElementById("showName").value=fileName;
    	}else{
    		document.getElementById("saveName").value=saveNameV+'!1103!'+saveName;
    		document.getElementById("showName").value=showNameV+'!1103!'+fileName;
    	}
    	$('#fileUp_0').remove();
    	$('#a_0').remove();
    	var temHtml1='<span class="uploadImg" id="uploadImgSpan">';
    	temHtml1=temHtml1+'<input type="file" class="file" id="fileUp_0"  name="formFile" onchange="uploading();" size="1"/>';
    	temHtml1=temHtml1+'<a style="font-size: 11px" href="#" id="a_0">[上传附件]</a>';
    	temHtml1=temHtml1+'</span>&nbsp;';	    	
    	document.getElementById("td_001").innerHTML=temHtml1;
    }
    //执行删除操作
    function deleteLoading(id){
    	document.getElementById("fileName").value=id;
    	document.getElementById("formFile").name='12ff';
    	document.getElementById("fileAjaxForm").action='${request.contextPath}/abnormal/tech-accident!delLoadOne.action';
    	document.getElementById("addEditFlag1").value="del";
    	$('#fileAjaxForm').submit();
    }
    //
    function afterDeleteLoading(){	  
    	document.getElementById("formFile").name='formFile';
    	var id=document.getElementById("fileName").value;
    	var obj=document.getElementById("id_a_"+id);
    	var obj1=document.getElementById("id_i_"+id);
    	$(obj).remove();
    	$(obj1).remove();
    	var saveNameV=document.getElementById("saveName").value;
    	var showNameV=document.getElementById("showName").value;
    	saveNameVArr=saveNameV.split('!1103!');
    	showNameVArr=showNameV.split('!1103!');
    	saveNameV='';
    	showNameV='';
    	for(var i in saveNameVArr){
    		if(saveNameVArr[i]==id){	    			
    			continue;
    		}
    		if(i==0){
    			saveNameV=saveNameVArr[i];
    			showNameV=showNameVArr[i];
    		}else{
    			saveNameV=saveNameV+'!1103!'+saveNameVArr[i];
    			showNameV=showNameV+'!1103!'+showNameVArr[i];
    		}
    	}
		document.getElementById("saveName").value=saveNameV;
		document.getElementById("showName").value=showNameV;
    }  	
</script>
</head>
<body>
<div class="formTitle2"><span>1、事故基本信息</span> </div>
<form id="techAccidentForm" method="post" ztype="form" action="tech-accident!insertTechAccident.action">	
	<input type='hidden' id="endFlag" name='formMap.endFlag' value='N'/>
    <input type='hidden' id="saveName" name='saveName' value=''/>
    <input type='hidden' id="showName" name='showName' value=''/>	
	<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="transType">
		<td>是否结束：</td>
		<td colspan="3">
			<input id="isAccd1" name="isAccd1" type="checkbox" value="0" onclick="isAccd($(this).is(':checked'));">	
		</td>
	</tr>
	<tr>
		<td>开始时间：</td>
		<td><input name="formMap.startTime" id="startTime" type="text" value="" ztype="startdatetime" verify="开始时间|NotNull&&Datetime"/></td>
		<td>结束时间：</td>
		<td><input name="formMap.endDate" id="endTime" type="text" value="" ztype="enddatetime" verify="结束时间|NotNull&&Datetime"/></td>
	</tr>
	<tr>
		<td>事故性质：</td>
		<td>
			<div id="accdKind" name="formMap.accdKind" ztype="select">
				<ul>
					<#if formMap.kindList?exists>
						<#list formMap.kindList as accdKind>
							<li val="${accdKind.paraCode}" >${accdKind.codeDesc}</li>
						</#list>	
					<#else>
							<li val="-1">无</li>
					</#if>				
				</ul>
			</div>		
		</td>
		<td>事故位置：</td>
		<td><input type="text"  ztype="text"  name="formMap.happenLocation" id="happenLocation" verify="事故位置|NotNull&&Length<30"/></td>
	</tr>
	<tr>
		<td colspan="1">责任人：</td>
		<td colspan="3"><input type="text"  ztype="text"  name="formMap.dutyPerson" id="dutyPerson" verify="责任人|NotNull&&Length<30"/></td>
	</tr>
	<tr>
		<td colspan="1">预防措施：</td>
		<td colspan="3"><textarea name="formMap.accdPrev" id="accdPrev" rows="3" cols="100"
			length="1024" autosize="true" verify="预防措施|NotNull&&Length<1024" ztype="textarea"></textarea></td>
	</tr>
	<tr>
		<td colspan="1">事故原因：</td>
		<td colspan="3"><textarea name="formMap.accdReason" id="accdReason" rows="3" cols="100"
			length="1024" autosize="true" verify="事故原因|NotNull&&Length<1024" ztype="textarea"></textarea></td>
	</tr>
	<tr>
		<td colspan="1">事故经过：</td>
		<td colspan="3"><textarea name="formMap.accdCourse" id="accdCourse" rows="3" cols="100"
			length="1024" autosize="true" verify="事故经过|NotNull&&Length<1024" ztype="textarea"></textarea></td>
	</tr>
	<tr>
		<td colspan="1">事故处理情况：</td>
		<td colspan="3"><textarea name="formMap.accdManage" id="accdManage" rows="3" cols="100"
			length="1024" autosize="true" verify="事故处理情况|NotNull&&Length<1024" ztype="textarea"></textarea></td>
	</tr>
	<tr>
		<td colspan="1">事故结果：</td>
		<td colspan="3"><textarea name="formMap.accdResult" id="accdResult" rows="6" cols="100"
			length="1024" autosize="true" verify="事故结果|Length<1024" ztype="textarea"></textarea></td>
	</tr>	
</form>		
<form id='fileAjaxForm' name="fileAjaxForm" method="post" ztype="form" action="tech-accident!upLoadOne.action">
	<input type='hidden' id="fileId" name="id" value=''/>
	<input type='hidden' id="fileName" name="formMap.fileName"   value=''/>	
	<input type='hidden' id="addEditFlag" name="addEditFlag" value='add'/>	
	<input type='hidden' id="addEditFlag1" name="addEditFlag1" value=''/>
    <tr class="transType">
        <td  align="left"  id='td_001' colspan="1">
        	<span class="uploadImg" id="uploadImgSpan">
         	<input type="file" class="file" id='fileUp_0'  name="formFile" onchange="uploading();" size="1"/>
         	<a style="font-size: 11px" href="#" id="a_0" >[上传附件]</a>  
        	</span>&nbsp;
        </td>
        <td  style="text-align: left" id="td_0" colspan="3">
        &nbsp;
        </td>
    </tr>	
</form>				
	</table>
<div class="formTitle2"><span>2、 以上信息确认无误后点击【提交领导审核】上报事故</span> </div>	
<div align="center">
<input type="button" id="sbButton" value="提交领导审核" ztype="button" onclick="add();"/>
</div>		
</body>
</html>
