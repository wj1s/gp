<#assign wit=JspTaglibs["/WEB-INF/precessimg.tld"] />  
<html>
    <head>
        <script type="text/javascript">
        	$(document).ready(function(){
            	$('button:contains("执行")').click(function(){
            		var piId = $('#piId').val();
            		window.location.href = '${request.contextPath}/workflow/task-execute.action?id=' + piId;
                });
            }); 
        	
        </script>
    </head>
    <body>
        <div id="body_div">
            <table>
                <tr>
                    <td align="left">
                        <@wit.processimage task="${pi.id}"/> 
                    </td>
                    <td valign="top">
                        <!-- 任务详情 -->
                        <table>
                        	<tr>
                                <td valign="top">
                                    <table>
                                        <tr>
                                            <td style="font-size:12px; line-height:2em;">
                                               		业务描述:<div>${desc}</div>
                                              		流程编号:${pi.id}
                                              	<br/>
                                               	 	流程名称:${pi.processDefinition.name}
                                                <br/>
													业务ID:${pi.key}
                                                <br/>
                                                	流程创建时间:${pi.start}
                                                <br/>
                                                	<#if pi.getEnd()?exists>
														流程结束时间:${pi.getEnd()}
													<#else>
														进入当前节点时间:${pi.rootToken.nodeEnter}
													</#if>
                                                		
                                                <br/>
													当前节点:“${pi.rootToken.node.name}”
												<br/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
							<tr>
								<td valign="top">
                                  	流转历史信息
                                </td>
                            </tr>
                            <tr>
                                <td valign="top">
                                	<#list comments as com>
								  		<div align="left" style="margin-left:5px">${com}</div>
								  	</#list>
                                </td>
                            </tr>
                            <tr>
			                	<td style="padding-top:50px">
			                		<form action="${request.contextPath}/taskDetail.do?">
				                		<input type="hidden" id="piId" value="${pi.id }"/>
				                		<#if canExecute>
						                	<button ztype="button" type="button">执行</button>
					                	</#if>
										<#if pi.processDefinition.name=='事故上报流程'>
											<a ztype="popUp" url="${request.contextPath}/abnormal/abnormal!detail.ajax?id=${pi.key}" zwidth="950" zheight="500" title="详细信息"><input type="button" ztype="button" value="浏览任务内容" class="input_submit_4" /></a>
										</#if>
										<#if pi.processDefinition.name=='技术安全事故上报流程'>
											<a ztype="popUp" url="${request.contextPath}/abnormal/tech-accident!viewTechAccident.ajax?formMap.id=${pi.key}" zwidth="950" zheight="500" title="详细信息"><input type="button" ztype="button" value="浏览任务内容" class="input_submit_4" /></a>
										</#if>	
										<#if pi.processDefinition.name=='技术安全报告上报'>
											<a ztype="popUp" url="${request.contextPath}/report/tech-reporting!toPreviewTechReport.ajax?formMap.id=${pi.key}" zwidth="950" zheight="500" title="详细信息"><input type="button" ztype="button" value="浏览任务内容" class="input_submit_4" /></a>
										</#if>												                		
				                	</form>
			                	</td>
			                </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
