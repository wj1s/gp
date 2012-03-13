<html>
	<head>	
		<script language="JavaScript" type="text/javascript">
	    $(document).ready(function(){	  		
	  		$('.work_data_table tr:odd').addClass('td01');
	  		$('.work_data_table tr:even').addClass('td02');			  	
	  	})
		function auditing(){		    	
			document.getElementById("ifBackFlag").value="1";
			submForm('确定审核通过?');
		}
        function back(){
        	document.getElementById("ifBackFlag").value="0";
        	submForm('确定驳回?');
        }	
        function submForm(msg){
			tjConfirm(msg,function(flag){
				if (!flag){					
					return;
				}else{ 
		            document.getElementById("sbButton").disabled=true;		           
		            document.getElementById("sbButtonB").disabled=true;		      
		        	document.getElementById("techReptForm").submit();					
				}
			});
        }      	        
        function checkMaxInput(obj, maxLen){//textarea最大输入限制
            var m = obj.value.length;
            var n = m;
            var j = 0;
            for (var i = 0; i < m; i++) {
                if (obj.value.charCodeAt(i) < 0 || obj.value.charCodeAt(i) > 161) {
                    n = n + 1;
                    if (i < maxLen) {
                        j = j + 1;
                    }
                }
            }
            if (n > maxLen) {
                obj.value = obj.value.substring(0, maxLen - j);
                tjAlert("最大可输入" + maxLen + "字符,您已达到输入上限！");
            }
        }        		
		</script>
	</head>
	<body>		
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
			<form id='techReptForm' name="techReptForm"  action="${request.contextPath}/report/tech-reporting!auditTechReport.action"  method='post'>		
				<input type='hidden' id="id" name='formMap.id' value='${formMap.tech.id}'/>		
				<input type='hidden' id="workflowId" name='formMap.workflowId' value='${formMap.workflowId}'/>
				<input type='hidden' id="ifBackFlag" name='formMap.ifBackFlag' value='0'/>					
					<#if formMap.tech.reptTechDtl?exists>														
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
			                <#else>
		                	&nbsp;				                			                
			                </#if>			                  
			                </td>	                                
			            </tr> 						
						</#list>							
					</table>					
					</#if>
					<div class="formTitle2"><span>2、审核意见 </span></div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
								<tr>
									<td>
									<textarea id="checkContent" name="formMap.checkContent"  onkeyup="checkMaxInput(this,512);"
										onkeydown="checkMaxInput(this,512);" onblur="checkMaxInput(this,512)"
										cols='80' rows='10' class="selected">审核通过</textarea>
									</td>
								</tr>						
				            <tr>
				                <td align="center">
				                    <input id="sbButton" type="button"  ztype="button" onclick="auditing();" value="审核通过"/>&nbsp;&nbsp;
							        <input id="sbButtonB" type="button" ztype="button" onclick="back();" value="驳回"/>		                    
				                </td>
				            </tr>
				    </table>				
				</form>
			</div>
			<div class="formTitle2"><span>3、历史流转信息 </span></div>
			<#list formMap.comments as com>
				<div class="killwangjian">${com}</div>
			</#list>			
		</div>		
	</body>
</html>
