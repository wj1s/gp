<html>
    <head>
        <title>检修记录展现</title>
        <script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('#closeButton').click(function(){
					$('#dialogDiv').dialog('close');
				});
			});
			</script>
    </head>
    <body>
 <#if recordDetail?exists>
  <div  style="text-align: center;">
            <table align="center" border="0" cellpadding="0" cellspacing="0"  class="work_info_table" width="600px">
                <tr>
                    <td>
                        <h3 align="center"><#if recordDetail.group?exists>${recordDetail.group.name}</#if><#if recordDetail.ddate?exists>${recordDetail.ddate?string("yyyy-MM-dd")} </#if>检修记录</h3>
                        <table align="center" border="0" cellpadding="0" cellspacing="0" class="work_info_table" width="100%">
                            <tr>
                                <td align="right" width="80">
                                    <strong>部门</strong>：
                                </td>
                                <td style="text-align: left">
                                    ${recordDetail.dept.name}&nbsp;
                                </td>
                                <td align="right" width="80">
                                    <strong>安全员</strong>：
                                </td>
                                <td style="text-align: left">
                                <#if recordDetail.security?exists>
                                    ${recordDetail.security}&nbsp;
                                    </#if>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <strong>检修人员</strong>：
                                </td>
                                <td colspan="3" style="text-align: left">
                                <#if recordDetail.examinePersons?exists>
                                   ${recordDetail.examinePersons}</#if> &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <strong>检修日期</strong>：
                                </td>
                                <td style="text-align: left">
                                <#if recordDetail.ddate?exists>
                                ${recordDetail.ddate?string("yyyy-MM-dd")}
                                </#if>
                                  &nbsp;
                                </td>
                                <td align="right">
                                    <strong>检修时长</strong>：
                                </td>
                                <td style="text-align: left">
                                  <#if recordDetail.timeLength?exists>
                                    ${recordDetail.timeLength}</#if>分钟 &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <strong>安全措施</strong>：
                                </td>
                                <td colspan="5" style="text-align: left">
                                    <textarea style="width: 90%; background: #fff; overflow-y: hidden; overflow: hidden; border-width: 0;" id="measure" name="measure" rows="3" readonly="readonly"><#if recordDetail.measure?exists>${recordDetail.measure}</#if></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <strong>检修记录</strong>：
                                </td>
                                <td colspan="3" style="text-align: left">
                                    <textarea style="width: 90%; background: #fff; overflow-y: hidden; overflow: hidden; border-width: 0;" id="examineRecord" name="examineRecord" rows="5" readonly="readonly"><#if recordDetail.examineRecord?exists>${recordDetail.examineRecord}</#if></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <strong>试机情况</strong>：
                                </td>
                                <td colspan="3" style="text-align: left">
                                    <textarea style="width: 90%; background: #fff; overflow-y: hidden; overflow: hidden; border-width: 0;" id="test" name="test" readonly="readonly"><#if recordDetail.test?exists>${recordDetail.test}</#if></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" align="right">
                                    <strong>负责人</strong>：
                                </td>
                                <td style="text-align: left">
                                <#if recordDetail.principal?exists>
                                    ${recordDetail.principal}</#if>&nbsp;
                                </td>
                            </tr>
                        </table>
                        <#if recordDetail.recordItems?exists>
                        <div style="text-align: left;">
                             <strong>已完成检修项目</strong>：
                        </div>
                        <table border="0" cellpadding="0" cellspacing="0" class="work_data_table" width="100%">
                            <#list recordDetail.recordItems as itemRecord>
                           <tr>
                                <td style="text-align: left; word-wrap: normal; white-space: normal;background: #fff;">
                                      <strong>系统</strong>：${itemRecord.cycleName }&nbsp;&nbsp;&nbsp;<strong>项目<#if itemRecord.type=='1'>(临时)</#if></strong>：${itemRecord.content}
                                         <#if itemRecord.persons?exists>
                                             &nbsp;&nbsp;&nbsp;<strong>检修人员</strong>：
                                             <#list itemRecord.persons as recordPerson>
                                                 <font color="blue">
                                                   ${recordPerson.name}
                                                 </font>
                                              </#list>
                                            </#if>
                                        </td>
                                  </tr>
                             </#list>
                            </table>
                        </#if>
                         <div align="right">
                            <button  ztype="button" type="button" onclick="javascript:window.print();">打印</button><button ztype="button" type="button" id="closeButton" name="closeButton">关闭</button>
                        </div>
                   </td>
                </tr>
            </table>
    </div>
 </#if>               
    </body>
</html>