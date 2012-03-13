<html>
<head>
	<script type='text/javascript' src="${request.contextPath}/js/repair/selAll.js"></script>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
	        <#if formMap.deptCycList.size() != 0>
        	<#list formMap.deptCycList as deptCycList>
				$('#form_${deptCycList.dept.id}').form();
			</#list>
			</#if> 
			$("#tab1").tab(1,0,100,0,0);		//设置				
            var dpId=$('#compareDept').val();
            getCycle(dpId);                
		});			
		function getCycle(dpId){//点击后查询执行情况			
            var id = $('#select_'+dpId).tjVal();//周期表id
            var year = $('#year_'+dpId).val();//年份            
            if (id != null && id != '') {//  
            	var check=$('#form_'+dpId).data('tjForm').checkSubmit();
				if(!check)return;            	
                if (year != "") {
                    $('#sysFrame').attr('src', '${request.contextPath}/repair/cycle!searchExecute.blank?formMap.cycleId=' + id  + '&formMap.year=' + year+'&formMap.isPrintPage=false');
                    $('#sysFrame').submit();
                }else{                	
                	tjAlert("请选择年份！");
                }                     
            }            
		}
		//打印方法
		function printSysFrame(){
			 document.frames("sysFrame").window.focus();        
			 window.print();	
		}		
	</script>
</head>
<body>
    <div class="tab" id="tab1">	    	    	   
        <ul> 
	        <#if formMap.deptCycList.size() != 0>
	        	<#list formMap.deptCycList as deptCycList>
		            <li>${deptCycList.dept.name}</li>			            	           
	            </#list>
	        <#else>
	       	 	<li>检修工作情况</li>
	        </#if> 
			<input id="compareDept" name="compareDept" value="${formMap.dept.id}" type="hidden"/>            	          	        
        </ul>     
        <#if formMap.deptCycList.size() != 0>
        	<#list formMap.deptCycList as deptCycList>
	            <div id="tabcontend_${deptCycList.dept.id}">
	            <form id="form_${deptCycList.dept.id}" name="form_${deptCycList.dept.id}" action=""  method="post"  style="border:0;margin:0;padding:0;">
        		<div >
        		<table>
	        		<tr>
	        			<td><strong>选择年份</strong>：</td>
	        			<td>	        			
	        				<input type="text" ztype="dateY" value="${formMap.sysYear}" name="year_${deptCycList.dept.id}" id="year_${deptCycList.dept.id}"  verify="年份|NotNull&&Length<5"/>
	        			</td>
	        			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	        			<td><strong>选择检修周期表</strong>:</td>
	        			<td>
		      			<div id="select_${deptCycList.dept.id}"  ztype="select"   verify="NotNull">
		     				<ul>
							<#if (deptCycList.deptSysList?exists)&&(deptCycList.deptSysList.size()!=0) >
								<#list deptCycList.deptSysList as cycle>												
									<li val="${cycle.id}">${cycle.name}</li>												
								</#list>
							</#if> 	
							</ul>								        
						</div>	        				        			
	        			</td>
	        			<td>
	        			&nbsp;&nbsp;&nbsp;     			
	        			</td>
	        			<td>
	        				<button ztype="button" type="button"  onclick="getCycle('${deptCycList.dept.id}');">
	        				查询
	        				</button>	        				
	        			</td>
	        			
	        			<td>
	        				<button ztype="button" type="button" onclick="printSysFrame();">
	        				打印
	        				</button>	        				
	        			</td>	        				        				        			
	        		</tr>
        		</table>
        		</div> 
        		</form> 	            	            	           	           	            								
	            </div>			            	           
            </#list>			            
        <#else>
         <div>
        <div id="code">
                <div id="content">
                    <p>
                        >> 欢迎使用检修周期表查看，您可以通过检修周期表查看功能对部门内各周期表及项目的执行情况进行分析： 
                    </p>
                    <p>
                              如果您看到此页面，表明系统判断您所具有权限的部门都还没有定义检修周期表，请在检修周期表管理中创建周期表。
                              如果您没有权限创建周期表，请联系机房主任或技办。
                    </p>
                </div>
	       	 </div>
	       </div>
        </#if>               
    </div> 
    <a id='sysA' ztype="popUp" zwidth="600px"  url="" style="visibility:hidden"></a>
    <iframe id="sysFrame" src="" scrolling="auto" frameborder="0" width="100%" height="400px" marginwidth="0" marginheight="0">
    </iframe>    	
</body>
</html>
