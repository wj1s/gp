<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){	
				$("#tab1").tabInit();
			});

			function submitReport(id, url){
				 var thUrl = '${request.contextPath}/' + url + '?reptTime=' + id;
		         window.location.href = thUrl;
			}
		</script>
	</head>
	<body>
	    <div align="center"><font style="font-size:30px">《近期报表情况一览》</font></div>
	    <div class="tab" id="tab1">
	        <ul> 
	            <li>${lastMonth}</li>
	            <li>${curMonth}</li>
	            <li>${nextMonth}</li>
	        </ul>
	        <div>
		        <div style="height:300px">
		            <table width="100%" border="0" cellspacing="0" cellpadding="0">
		                <tr>
		                    &nbsp;
		                </tr>
		                <tr>
		                    <td align="center">
		                        <font size="4">
		                           		${lastMonth}&nbsp;报表上报情况
		                        </font>
		                    </td>
		                </tr>
		            </table>
		            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
		                <tr>
		                    <th nowrap="nowrap">
		                        	报表名称
		                    </th>
		                    <th nowrap="nowrap">
		                        	状态
		                    </th>
		                    <th nowrap="nowrap">
		                       	 开始上报时间
		                    </th>
		                    <th nowrap="nowrap" width="150">
		                        	操作
		                    </th>
		                </tr>
		                <#if !lastReptGroupList?exists || lastReptGroupList?size==0>
		                    <tr>
		                        <td colspan="11">
		                            <strong><span style="COLOR: red">没有符合条件的数据！</span></strong>
		                        </td>
		                    </tr>
		                <#else>
		                	<#list lastReptGroupList as lastReptGroup>
		                		<tr>
		                            <td nowrap="nowrap">
		                                ${lastReptGroup.reptGroupName} &nbsp;
		                            </td>
		                            <td nowrap="nowrap" style="word-wrap:normal;white-space:normal;word-break:break-all;" align="center">
		                                ${lastReptGroup.canNotReportReason}
		                                &nbsp;
		                            </td>
		                            <td nowrap="nowrap" align="center">
		                            	${lastReptGroup.reptSubmitDate?string("yyyy-MM-dd HH:mm")}
		                            </td>
		                            <td nowrap="nowrap" align="center">&nbsp;
		                            	<#if lastReptGroup.canReport>
		                            		 <button ztype="button" type="button" onclick="submitReport('${lastReptGroup.reptTime}', '${lastReptGroup.toSubmitUrl}');">上报</button>
		                            	</#if>
		                            </td>
		                        </tr>
		                	</#list>
		                </#if>
		            </table>
	            </div>
        	</div>
        	<div>
        		<div style="height:300px">
		            <table width="100%" border="0" cellspacing="0" cellpadding="0">
		                <tr>
		                    &nbsp;
		                </tr>
		                <tr>
		                    <td align="center">
		                        <font size="4">
		                           		${curMonth}&nbsp;报表上报情况
		                        </font>
		                    </td>
		                </tr>
		            </table>
		            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
		                <tr>
		                    <th nowrap="nowrap">
		                        	报表名称
		                    </th>
		                    <th nowrap="nowrap">
		                        	状态
		                    </th>
		                    <th nowrap="nowrap">
		                       	 开始上报时间
		                    </th>
		                    <th nowrap="nowrap" width="150">
		                        	操作
		                    </th>
		                </tr>
		                <#if !curReptGroupList?exists || curReptGroupList?size==0>
		                    <tr>
		                        <td colspan="11">
		                            <strong><span style="COLOR: red">没有符合条件的数据！</span></strong>
		                        </td>
		                    </tr>
		                <#else>
		                	<#list curReptGroupList as curReptGroup>
		                		<tr>
		                            <td nowrap="nowrap">
		                                ${curReptGroup.reptGroupName} &nbsp;
		                            </td>
		                            <td nowrap="nowrap" style="word-wrap:normal;white-space:normal;word-break:break-all;" align="center">
		                                ${curReptGroup.canNotReportReason}
		                                &nbsp;
		                            </td>
		                            <td nowrap="nowrap" align="center">
		                            	${curReptGroup.reptSubmitDate?string("yyyy-MM-dd HH:mm")}
		                            </td>
		                            <td nowrap="nowrap" align="center">&nbsp;
		                            	<#if curReptGroup.canReport>
		                            		 <button ztype="button" type="button" onclick="submitReport('${curReptGroup.reptTime}', '${curReptGroup.toSubmitUrl}');">上报</button>
		                            	</#if>
		                            </td>
		                        </tr>
		                	</#list>
		                </#if>
		            </table>
	            </div>
        	</div>
        	<div>
        		<div style="height:300px">
		            <table width="100%" border="0" cellspacing="0" cellpadding="0">
		                <tr>
		                    &nbsp;
		                </tr>
		                <tr>
		                    <td align="center">
		                        <font size="4">
		                           		${nextMonth}&nbsp;报表上报情况
		                        </font>
		                    </td>
		                </tr>
		            </table>
		            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
		                <tr>
		                    <th nowrap="nowrap">
		                        	报表名称
		                    </th>
		                    <th nowrap="nowrap">
		                        	状态
		                    </th>
		                    <th nowrap="nowrap">
		                       	 开始上报时间
		                    </th>
		                    <th nowrap="nowrap" width="150">
		                        	操作
		                    </th>
		                </tr>
		                <#if !nextReptGroupList?exists || nextReptGroupList?size==0>
		                    <tr>
		                        <td colspan="11">
		                            <strong><span style="COLOR: red">没有符合条件的数据！</span></strong>
		                        </td>
		                    </tr>
		                <#else>
		                	<#list nextReptGroupList as nextReptGroup>
		                		<tr>
		                            <td nowrap="nowrap">
		                                ${nextReptGroup.reptGroupName} &nbsp;
		                            </td>
		                            <td nowrap="nowrap" style="word-wrap:normal;white-space:normal;word-break:break-all;" align="center">
		                                ${nextReptGroup.canNotReportReason}
		                                &nbsp;
		                            </td>
		                            <td nowrap="nowrap" align="center">
		                            	${nextReptGroup.reptSubmitDate?string("yyyy-MM-dd HH:mm")}
		                            </td>
		                            <td nowrap="nowrap" align="center">&nbsp;
		                            	<#if nextReptGroup.canReport>
		                            		 <button ztype="button" type="button" onclick="submitReport('${nextReptGroup.reptTime}', '${nextReptGroup.toSubmitUrl}');">上报</button>
		                            	</#if>
		                            </td>
		                        </tr>
		                	</#list>
		                </#if>
		            </table>
	            </div>
        	</div>
    	</div>
	</body>
</html>
