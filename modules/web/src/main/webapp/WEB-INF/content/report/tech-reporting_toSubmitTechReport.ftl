<html>
	<head>
		<style  type="text/css" >
			.uploadImg{ overflow:hidden; position:absolute}
			.delLoadImg{ vertical-align:middle; cursor:hand}
			.file{position:absolute; z-index:100; margin-left:-180px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;height:26px;}
		</style>	
		<script language="JavaScript" type="text/javascript">
			var background = 'true'; //true：显示背景；false：不显示背景。
			var station = 'true'; //true：窗口居中；false：窗口跟随。
			var tip_img = 'true'; //true：启用提示关闭功能；false：不启用提示关闭功能。	
		    $(document).ready(function(){
		  		$('.work_data_table tr:odd').addClass('td01');
		  		$('.work_data_table tr:even').addClass('td02');	
				$('#fileAjaxForm').form();
				$('#fileAjaxForm').init_style();
				$('#fileAjaxForm').ajaxForm({
					type: "post",
					dataType: 'json',
					beforeSubmit: validateForm,
					success: function(data){
						if (data['result'] == true){
							if((document.getElementById("addEditFlag").value)=="edit"){
								afterUploading();
							}else{
								afterDeleteLoading();
							}	
						}else {						
							if((document.getElementById("addEditFlag").value)=="edit"){
								tjAlert("文件上传失败!");
							}else{
								tjAlert("文件删除失败!");
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
			function auditing(){
		    	//校验全部上传完毕
		    	var isUpArr=document.getElementsByName("isUpLoad");
		    	if(isUpArr!=null){
		    		for(var i=0;i<isUpArr.length;i++){		    			
		    			if((isUpArr[i]).value=='0'){//有一个是0说明没有添完整
		    				tjAlert('请将附件全部上传完毕，再提交审核！');
		    				return false;
		    			}
		    		}
		    	}
				msg = '确认提交领导审核吗？';
				tjConfirm(msg,function(flag){
					if (!flag){					
						return;
					}else{ 
						document.getElementById("sbButton").disabled=true;
						$('#techReptForm').submit();					
					}
				});				
			}
		    function uploading(id){
		    	document.getElementById("fileUp_"+id).name="formFile";		    	
		    	var uploadName=document.getElementById("fileUp_"+id).value;//	
		    	var uploadNameArr=uploadName.split("\\");
		    	document.getElementById("uploadName").value=uploadNameArr[uploadNameArr.length-1];//	
		    	document.getElementById("fileId").value=id;//	
		    	document.getElementById("fileAjaxForm").action='${request.contextPath}/report/tech-reporting!upLoadOne.action';		    	
		    	document.getElementById("addEditFlag").value="edit";
		    	var fileNameFull=document.getElementById("fileUp_"+id).value;
		    	var fileNameArr=fileNameFull.split('\\');
		    	document.getElementById("fileName").value=fileNameArr[fileNameArr.length-1];
		    	$('#fileAjaxForm').submit();
		    }
		    function afterUploading(){
		    	var id=document.getElementById("fileId").value;
		    	var fileName=document.getElementById("fileName").value;
		    	document.getElementById("fileUp_"+id).name="t000";
		    	document.getElementById("isUp_"+id).value="1";
		    	//将td中内容变成那个文件名连接加删除按钮
		    	document.getElementById("td_"+id).innerHTML=null;
		    	var temHtml='<a href="${request.contextPath}/report/tech-reporting!downLoadOne.action?formMap.ifBackFlag=TECH&formMap.id='+id+'">'+fileName+'</a>';
		    	temHtml=temHtml+'<img src="${request.contextPath}/img/del_f.gif" class="delLoadImg" onclick="deleteLoading('+id+');">';
		    	document.getElementById("td_"+id).innerHTML=temHtml;
		    	//先清理
		    }
		    //执行删除操作
		    function deleteLoading(id){		    	
		    	document.getElementById("fileId").value=id;
		    	document.getElementById("fileAjaxForm").action='${request.contextPath}/report/tech-reporting!delLoadOne.action';				    	
		    	document.getElementById("addEditFlag").value="del";
		    	$('#fileAjaxForm').submit();
		    }
		    function afterDeleteLoading(){
		    	var id=document.getElementById("fileId").value;
		    	//将td内容变成那个input+<a
		    	document.getElementById("td_"+id).innerHTML=null;
		    	document.getElementById("isUp_"+id).value="0";
		    	var temHtml='<span class="uploadImg" id="span_'+id+'">';
		    	temHtml=temHtml+'<input type="file" class="file"  id="fileUp_'+id+'"  onchange="uploading('+id+');" size="1"/>';
		    	temHtml=temHtml+'<a style="font-size: 11px" href="#" id="a_'+id+'">上传附件</a>';
		    	temHtml=temHtml+'</span>&nbsp;';
		    	document.getElementById("td_"+id).innerHTML=temHtml;		    	
		    }			
		</script>
	</head>
	<body>
	<form id='techReptForm' name="techReptForm"  action="${request.contextPath}/report/tech-reporting!submitTechReport.action"  method='post'>		
		<input type='hidden' name='formMap.id' value='${formMap.tech.id}'/>
	</form>		
		<div id="body_div">
		<div class="formTitle2"><span>1、基本信息 </span></div> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="work_info_table" >
				<tr>
					<th colspan="4"><strong>&lt;&lt;${formMap.tech.name}&gt;&gt;</strong></th>
				</tr>
				<tr>
					<td style="width:30%;" align="center">开始时间：</td>
					<td>${formMap.tech.startDate}</td>				
					<td style="width:30%;" align="center">结束时间：</td>
					<td>${formMap.tech.endDate}</td>
				</tr>
			</table>
			<div>
				<div>
					<#if formMap.tech.reptTechDtl?exists>
					<form id="fileAjaxForm" action="${request.contextPath}/report/tech-reporting!submitTechReport.action" method="post">					
					<input type='hidden' id="fileId" name="formMap.id" value=''/>
					<input type='hidden' id="uploadName" name="formMap.uploadName" value=''/>
					<input type='hidden' id="fileName"  value=''/>	
					<input type='hidden' id="addEditFlag" name="formMap.addEditFlag" value=''/>						
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
					<tr>
						<th nowrap="nowrap">
							模版名称
						</th>
						<th nowrap="nowrap" width="100">
							下载模版
						</th>
						<th nowrap="nowrap" width="30%">
							下载附件
						</th>
					</tr>							
						<#list formMap.tech.reptTechDtl as reptTechDtls>
						<tr>
			                <td nowrap="nowrap">
			                    ${reptTechDtls.attachName} &nbsp;
			                </td>	
			                <td nowrap="nowrap"><a href="${request.contextPath}/report/tech-reporting!downLoadOne.action?formMap.ifBackFlag=PATT&formMap.id=${reptTechDtls.id}">下载模版</a></td>	
			                <td nowrap="nowrap" id="td_${reptTechDtls.id}">
			                <#if reptTechDtls.saveName?exists>
		                		<a href="${request.contextPath}/report/tech-reporting!downLoadOne.action?formMap.ifBackFlag=TECH&formMap.id=${reptTechDtls.id}">${reptTechDtls.uploadName}</a>
		                		<img src="${request.contextPath}/img/del_f.gif" class="delLoadImg" onclick="deleteLoading('${reptTechDtls.id}');"> 
			                <#else>
		                	<span class="uploadImg" id="span_${reptTechDtls.id}">
			                	<input type="file" type="file"  class="file"  id="fileUp_${reptTechDtls.id}"  onchange="uploading('${reptTechDtls.id}');" size="1"/>
			                	<a style="font-size: 11px" href="#" id="a_${reptTechDtls.id}">上传附件</a>  
		                	</span>&nbsp;				                			                
			                </#if>			                  
			                </td>	                                
			            </tr> 						
						</#list>							
					</table>
					</form>
					</#if>
					<div class="formTitle2"><span>2、提交审核 </span></div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
			            <tr>
			                <td colspan="4" align="center">
			                    <input id="sbButton" type="button" ztype="button" onclick="auditing();" value="提交领导审核"/>
			                </td>
			            </tr>
				    </table>					
				</div>
			</div>
		</div>
		<#list formMap.tech.reptTechDtl as reptTechDtls>
             <#if reptTechDtls.saveName?exists>
            		<input type='hidden' id="isUp_${reptTechDtls.id}" name="isUpLoad" value='1'/>
             <#else>	
             		<input type='hidden' id="isUp_${reptTechDtls.id}" name="isUpLoad" value='0'/>  	                			                
             </#if>					
		</#list>			
	</body>
</html>
