<html>
<head>	
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title></title>
<script language="JavaScript" type="text/javascript">
var locationUrl = "${request.contextPath}/Interface/";
var ctp = "${request.contextPath}";
</script>
<style>
	.title1{
		
	}
</style>
<link type="text/css" rel="stylesheet" href="${request.contextPath}/css/style.css" />
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/ddsmoothmenu/ddsmoothmenu.css" />
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/jquery-ui-1.8.4.custom/css/redmond/jquery-ui-1.8.4.custom.css"/>
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/tj/tjStyle/jquery.tj.style.css"/>
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/jquery-cluetip/jquery.cluetip.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${request.contextPath}/plugin/jquery-ui-1.8.4.custom/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/jquery-ui-1.8.4.custom/js/jquery-ui-1.8.4.custom.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/tj/tjStyle/jquery.tj.style.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/jquery-cluetip/jquery.hoverIntent.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/jquery-cluetip/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/jquery-cluetip/jquery.cluetip.js"></script>
	<script language="JavaScript" type="text/javascript">
	$(document).ready(function(){		
	  	$('a.title1').cluetip({positionBy:'mouse',arrows:true});
 	});				
	function popUpOnParent(id){
		var url="${request.contextPath}/repair/record!scanRecordDetail.ajax?id="+id;
		var parentA=window.parent.document.getElementById('sysA');
		parentA.url=url;
		parentA.click();
	}
	</script>
</head>
<body>               	    	        
       <#if formMap.deptSys.size() != 0>
       	<#list formMap.deptSys as deptCycList>
            <div style="padding:6px;">	                    				
        		<h2 id="sysTitle" align="center">${formMap.year[0]}年《${deptCycList.sysName}》执行情况</h2>
				<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th>项目</th>
						<th>周期</th>
						<th>状态</th>
						<th>一月</th>
						<th>二月</th>
						<th>三月</th>
						<th>四月</th>
						<th>五月</th>
						<th>六月</th>
						<th>七月</th>
						<th>八月</th>
						<th>九月</th>
						<th>十月</th>
						<th>十一月</th>
						<th>十二月</th>														
					</tr>
					<#if (deptCycList.cellList?exists)&&(deptCycList.cellList.size()!=0)>											
							<#list deptCycList.cellList as cycleCell>
							<tr>
								<td style="position: static" colspan="15"><span style="font-size: 13px;color:#B200C2;">【${cycleCell.cellName}】</span></td>				
							</tr>
							<#if (cycleCell.cardList?exists)&&(cycleCell.cardList.size()!=0) >
								<#list cycleCell.cardList as card>
								<tr>
									<td style="position: static; width: 100px" >
									<div style="width: 200px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; cursor: pointer;text-align: left;">									
									<a class="title1" title="${card.cardName}" rel="${request.contextPath}/repair/cycle!showItemContext.ajax?formMap.cardId=${card.cardId}">									
									 ${card.cardName} 
									</a>	
									</div>								
									</td>
									<td style="position: static" >${card.periodName}</td>
									<td style="position: static" width="27" >
									<a class="title1" title="${card.cardName}" rel="${request.contextPath}/repair/cycle!showItemContext.ajax?formMap.cardId=${card.cardId}">
									<#if card.state=="01">
									<img src="${request.contextPath}/img/green.jpg" />
									<#elseif card.state=="02">
									<img src="${request.contextPath}/img/yello.jpg" />
									<#elseif card.state=="03">
									<img src="${request.contextPath}/img/blue.jpg" />
									<#elseif card.state=="04">
									<img src="${request.contextPath}/img/red.jpg" />
									<#else>
									<img src="${request.contextPath}/img/weizhi.jpg"/>
									</#if>
									</a>
									</td>
									<#if (card.repairList?exists)&&(card.repairList.size()!=0) >																
										<#list card.repairList as repairList>																				
											<td style="position: static">											
											<#if (repairList.size()!=0) >											
												<#list repairList as repairListA>												
													<a  zwidth="600px"  href="javascript:popUpOnParent('${repairListA.id}');">${repairListA.ddate.month+1}.${repairListA.ddate.date}</a>												
												</#list>
											<#else>											
												&nbsp;
											</#if>
											</td>																						
										</#list>
									<#else>
										<td style="position: static" colspan="12">&nbsp;</td>
									</#if>																						
								</tr>
								</#list>
							</#if>
							</#list>						
					</#if>																							      		
			    </table>			
            </div>
           </#list>			            
       <#else>
       <div id="content">
           <p>
               >> 欢迎使用检修周期表查看，您可以通过检修周期表查看功能对部门内各周期表及项目的执行情况进行分析： 
           </p>
           <p>
               <img src="<%=request.getContextPath()%>/img/info2.png" />如果您看到此页面，表明系统判断您所具有权限的部门都还没有定义检修周期表，请在检修周期表管理中创建周期表。
               如果您没有权限创建周期表，请联系机房主任或技办。
           </p>
       </div>
       </#if>             	
</body>
</html>
