<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			var background = 'true'; //true：显示背景；false：不显示背景。
			var station = 'true'; //true：窗口居中；false：窗口跟随。
			var tip_img = 'true'; //true：启用提示关闭功能；false：不启用提示关闭功能。	
		    $(document).ready(function(){
		  		$('.work_data_table tr:odd').addClass('td01');
		  		$('.work_data_table tr:even').addClass('td02');	
		  	});			
		</script>
	</head>
	<body>
		<div id="body_div"> 
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
			                <#else>&nbsp;				                			                
			                </#if>			                  
			                </td>	                                
			            </tr> 						
						</#list>							
					</table>					
					</#if>				
				</div>
			</div>
			<div align="right">
				<input id="changeAt" type='button' value='关闭' ztype="button" onclick="$('#dialogDiv').dialog('close');" />&nbsp;&nbsp;&nbsp;
			</div>				
		</div>			
	</body>
</html>
