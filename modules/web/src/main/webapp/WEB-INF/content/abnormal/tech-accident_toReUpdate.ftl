<html>
<head>
<style  type="text/css" >
	.uploadImg{ overflow:hidden; position:absolute}
	.delLoadImg{ vertical-align:middle; cursor:hand}
	.file{position:absolute; z-index:100; margin-left:-180px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;height:26px;}
</style>
<script type="text/javascript" language="javasrcipt">  
        var rFlag='${formMap.rFlag}';
        function add(){
        	document.getElementById("ifBackFlag").value="1";           	
        	if(rFlag=='1'){
        		submForm('确定提交领导审核?');
        	}else{
        		submForm('确定审核通过?');
        	}        	
        }
        function back(){
        	document.getElementById("ifBackFlag").value="0";
        	submForm('确定驳回?');
        }
        function submForm(msg){
        	if ($("#techAccidentForm").data('tjForm').checkSubmit()) {
        		if(document.getElementById("isAccd1").checked){
        			document.getElementById("endFlag").value="Y";
        		}else{
        			document.getElementById("endFlag").value="N";
        		}
		        if (confirm(msg)) {
		            //添加数据处理提示信息sbButton
		            document.getElementById("sbButton").disabled=true;
		            if(rFlag!='1'){
		            	document.getElementById("sbButtonB").disabled=true;
		            }
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
							afterUploading(data['saveName'],data['id']);
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
        function afterUploading(saveName,id){
        	var fileName=document.getElementById("fileName").value;
        	var temHtml=document.getElementById("td_0").innerHTML;
        	temHtml=temHtml+'<a style="margin-left:7px;" id="id_a_'+saveName+'" href="${request.contextPath}/abnormal/tech-accident!downLoadOne.action?showName='+fileName+'&saveName='+saveName+'">'+fileName+'</a>';
        	temHtml=temHtml+'<img id="id_i_'+saveName+'" src="${request.contextPath}/img/del_f.gif" class="delLoadImg" onclick="deleteLoading(\''+saveName+'\',\''+id+'\');">';
        	document.getElementById("td_0").innerHTML=null;
        	document.getElementById("td_0").innerHTML=temHtml;
        	$('#fileUp_0').remove();
        	$('#a_0').remove();
        	var temHtml1='<span class="uploadImg" id="uploadImgSpan">';
        	temHtml1=temHtml1+'<input type="file" class="file" id="fileUp_0"  name="formFile" onchange="uploading();" size="1"/>';
        	temHtml1=temHtml1+'<a style="font-size: 11px" href="#" id="a_0">[上传附件]</a>';
        	temHtml1=temHtml1+'</span>&nbsp;';	    	
        	document.getElementById("td_001").innerHTML=temHtml1;
        }
	    //执行删除操作
	    function deleteLoading(id,mid){	  
	    	document.getElementById("fileId").value=mid;
	    	document.getElementById("fileName").value=id;
	    	document.getElementById("formFile").name='12ff';
	    	document.getElementById("fileAjaxForm").action='${request.contextPath}/abnormal/tech-accident!delLoadOne.action';
	    	document.getElementById("addEditFlag1").value="del";
	    	$('#fileAjaxForm').submit();
	    	document.getElementById("fileId").value=document.getElementById("id").value;
	    }        
        //
        function afterDeleteLoading(){	  
        	document.getElementById("formFile").name='formFile';
        	var id=document.getElementById("fileName").value;
        	var obj=document.getElementById("id_a_"+id);
        	var obj1=document.getElementById("id_i_"+id);
        	$(obj).remove();
        	$(obj1).remove();
        }        
</script>
</head>
<body>
<div class="formTitle2"><span>1、事故基本信息</span> </div>
<form id="techAccidentForm" method="post" ztype="form" action="tech-accident!updateTechAccident.action">	
	<input type='hidden' id="endFlag" name='formMap.endFlag' value='N'/>	        
	<input type='hidden' id="id" name='formMap.id' value='${formMap.techAccident.id}'/>	  
	<input type='hidden' id="workflowId" name='formMap.workflowId' value='${formMap.workflowId}'/>
	<input type='hidden' id="ifBackFlag" name='formMap.ifBackFlag' value='0'/>	
	<input type='hidden' id="rFlag" name='formMap.rFlag' value='${formMap.rFlag}'/>			
	<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="transType">
		<td>是否结束：</td>
		<td colspan="3">
			<#if formMap.techAccident.endFlag=='N'>						
				<input id="isAccd1" name="isAccd1" type="checkbox" value="0" onclick="isAccd($(this).is(':checked'));">	
			</#if>
			<#if formMap.techAccident.endFlag=='Y'>					
				<input id="isAccd1" name="isAccd1" type="checkbox" checked="true" value="0" onclick="isAccd($(this).is(':checked'));">
			</#if>	
		</td>
	</tr>
	<tr>
		<td>开始时间：</td>
		<td><input name="formMap.startTime" id="startTime" type="text" value="${formMap.techAccident.startTime?string("yyyy-MM-dd hh:mm:ss")}" ztype="startdatetime" verify="开始时间|NotNull&&Datetime"/></td>
		<td>结束时间：</td>
		<td><input name="formMap.endDate" id="endTime" type="text" value="${formMap.techAccident.endDate?string("yyyy-MM-dd hh:mm:ss")}" ztype="enddatetime" verify="结束时间|NotNull&&Datetime"/></td>
	</tr>
	<tr>
		<td>事故性质：</td>
		<td>
			<div id="accdKind" name="formMap.accdKind" ztype="select">
				<ul>
					<#if formMap.kindList?exists>
						<#list formMap.kindList as accdKind>
							<#if accdKind.paraCode==formMap.techAccident.accdKind>
								<li val="${accdKind.paraCode}" selected="selected">${accdKind.codeDesc}</li>
							<#else>
								<li val="${accdKind.paraCode}" >${accdKind.codeDesc}</li>
							</#if>
							
						</#list>	
					<#else>
							<li val="-1">无</li>
					</#if>				
				</ul>
			</div>		
		</td>
		<td>事故位置：</td>
		<td><input type="text"  ztype="text"  name="formMap.happenLocation" value="${formMap.techAccident.happenLocation}" id="happenLocation" verify="事故位置|NotNull&&Length<30"/></td>
	</tr>
	<tr>
		<td colspan="1">责任人：</td>
		<td colspan="3"><input type="text"  ztype="text"  name="formMap.dutyPerson" value="${formMap.techAccident.dutyPerson}" id="dutyPerson" verify="责任人|NotNull&&Length<30"/></td>
	</tr>
	<tr>
		<td colspan="1">预防措施：</td>
		<td colspan="3"><textarea name="formMap.accdPrev" id="accdPrev" rows="3" cols="100"
			length="1024" autosize="true" verify="预防措施|NotNull&&Length<1024" ztype="textarea">${formMap.techAccident.accdPrev}</textarea></td>
	</tr>
	<tr>
		<td colspan="1">事故原因：</td>
		<td colspan="3"><textarea name="formMap.accdReason" id="accdReason" rows="3" cols="100"
			length="1024" autosize="true" verify="事故原因|NotNull&&Length<1024" ztype="textarea">${formMap.techAccident.accdReason}</textarea></td>
	</tr>
	<tr>
		<td colspan="1">事故经过：</td>
		<td colspan="3"><textarea name="formMap.accdCourse" id="accdCourse" rows="3" cols="100"
			length="1024" autosize="true" verify="事故经过|NotNull&&Length<1024" ztype="textarea">${formMap.techAccident.accdCourse}</textarea></td>
	</tr>
	<tr>
		<td colspan="1">事故处理情况：</td>
		<td colspan="3"><textarea name="formMap.accdManage" id="accdManage" rows="3" cols="100"
			length="1024" autosize="true" verify="事故处理情况|NotNull&&Length<1024" ztype="textarea">${formMap.techAccident.accdManage}</textarea></td>
	</tr>
	<tr>
		<td colspan="1">事故结果：</td>
		<td colspan="3"><textarea name="formMap.accdResult" id="accdResult" rows="6" cols="100"
			length="1024" autosize="true" verify="事故结果|Length<1024" ztype="textarea">${formMap.techAccident.accdResult}</textarea></td>
	</tr>
</form>		
<form id='fileAjaxForm' name="fileAjaxForm" method="post" ztype="form" action="tech-accident!upLoadOne.action">
	<input type='hidden' id="fileId" name="id" value='${formMap.techAccident.id}'/>
	<input type='hidden' id="fileName" name="formMap.fileName"   value=''/>	
	<input type='hidden' id="addEditFlag" name="addEditFlag" value='edit'/>	
	<input type='hidden' id="addEditFlag1" name="addEditFlag1" value=''/>
    <tr class="transType">
        <td  align="left"  id='td_001' colspan="1">
        	<span class="uploadImg" id="uploadImgSpan">
         	<input type="file" class="file" id='fileUp_0'  name="formFile" onchange="uploading();" size="1"/>
         	<a style="font-size: 11px" href="#" id="a_0" >[上传附件]</a>  
        	</span>&nbsp;
        </td>
		<td  style="text-align: left" id="td_0" colspan="3">&nbsp;
		<#list formMap.techAccident.techAccidentMedias as techAccidentMedias>
		<a   id="id_a_${techAccidentMedias.saveName}" style="margin-left:7px;"   href="${request.contextPath}/abnormal/tech-accident!downLoadOne.action?showName=${techAccidentMedias.fileName}&saveName=${techAccidentMedias.saveName}">${techAccidentMedias.fileName}</a>
		<img id="id_i_${techAccidentMedias.saveName}" src="${request.contextPath}/img/del_f.gif" class="delLoadImg" onclick="deleteLoading('${techAccidentMedias.saveName}','${techAccidentMedias.id}');">
		</#list>		
		</td>
    </tr>	
</form>				
	</table>
<div class="formTitle2">
<span>2、
<#if formMap.rFlag=='1'>
以上信息确认无误后点击【提交领导审核】上报事故。
<#else>
以上信息确认无误后点击【审核通过】上报事故。
</#if>
</span> 
</div>	
<div align="center">
<#if formMap.rFlag=='1'>
<input type="button" id="sbButton" value="提交领导审核" ztype="button" onclick="add();"/>
<#else>
<input id="sbButton" type="button" onclick="add();" value="审核通过" ztype="button"/>&nbsp;&nbsp;
<input id="sbButtonB" type="button" onclick="back();" value="驳回" ztype="button"/>
</#if>
</div>
<div class="formTitle2"><span>3、历史流转信息 </span></div>
<#list formMap.comments as com>
	<div class="killwangjian">${com}</div>
</#list>	
</body>
</html>
