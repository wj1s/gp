<html>
<head>
<script language="JavaScript" type="text/javascript">
	$(document).ready(function(){	
		$('button:contains("查询")').click(function(){
			var dpId = $('#dpId').tjVal();
			var cellId = $('#cellId').tjVal();
			var cycleId = $('#cycleId').val();									
			var cardsId = $('#cardsId').tjVal();
			var url = '${request.contextPath}/repair/card!loadCard.action?'+ 
				'dpId='+dpId+'&cellId='+cellId+'&cardsId='+cardsId+'&cycleId='+cycleId;
			$('#card-show').tjSearch(url);
		});
        //部门系统联动
		$('#dpId').tjChange(function(){
			var id = $('#dpId').tjVal();
			$.getJSON('${request.contextPath}/baseinfo/dept!getCyclesAjax.action?rd='+Math.random(),
					{'id':id},					
					function(data){
						$('#cycleId').tjEmpty();
					if (data.result){
						var array = eval(data['data']);
						var html = '<li val=""></li>';
						for(var i in array){
							html += '<li val="'+array[i].value+'">'+array[i].name+'</li>';
						}
						$('#cycleId ul').append(html);
						 $('#cardsId').tjEmpty();
						 $('#cellId').tjEmpty();
						$('#cycleId').tjAssemble();
					}
				});
		});
		//系统分区联动	
		$('#cycleId').tjChange(function(){
		    var id = $('#cycleId').tjVal();
		    $.getJSON('${request.contextPath}/repair/cycle!getCycleCellsAjax.action?rd='+Math.random(),
					{'id':id},					
					function(data){
					  $('#cellId').tjEmpty();
						if (data.result){
							var array = eval(data['data']);
							var html = '<li val=""></li>';
							for(var i in array){
								html += '<li val="'+array[i].value+'">'+array[i].name+'</li>';
							}
							$('#cellId ul').append(html);
							$('#cellId').tjAssemble();
						}
					});
		});

       //分区项目联动
	 $('#cellId').tjChange(function(){
	     var id = $('#cellId').tjVal();
	     $.getJSON('${request.contextPath}/repair/cycle-cell!getCardsAjax.action?rd='+Math.random(),
				  {'id':id},					
				  function(data){
					 $('#cardsId').tjEmpty();
						if (data.result){
							var array = eval(data['data']);
							var html = '<li val=""></li>';
							for(var i in array){
								html += '<li val="'+array[i].value+'">'+array[i].name+'</li>';
							}
							$('#cardsId ul').append(html);
							$('#cardsId').tjAssemble();
							}
						});
	 }); 

	  	var colModel=[{name:'id',header:'id',sortable:true,hidden:true,editable:false},
	  	            {name:'cycleCell.name',header:'检修分区',verify:'NotNull'},
	                {name:'name',header:'检修项目',verify:'NotNull'},
					{name:'shutdownTime',header:'停机时间',hidden:true,dateFmt:'timeHM',verify:'NotNull'},
					{name:'processTime',header:'需用时间',hidden:true,dateFmt:'timeHM',verify:'NotNull'},
					{name:'tools',header:'仪器工具',hidden:true,inputType:'textArea',verify:'NotNull'},
					{name:'measure',header:'安全措施',hidden:true,inputType:'textArea',verify:'NotNull'},
					{name:'precaution',header:'注意事项',hidden:true,inputType:'textArea',verify:'NotNull'},
					{name:'others',header:'其他措施',hidden:true,inputType:'textArea',verify:'NotNull'},
					{name:'method',header:'检修方法',hidden:true,inputType:'textArea',verify:'NotNull'},
					{name:'techStd',header:'技术标准',hidden:true,inputType:'textArea',verify:'NotNull'},
					{name:'rmks',header:'备注',hidden:true,inputType:'textArea',verify:'NotNull'}]
		$('#card-show').grid({name:'检修卡片管理',colModel:colModel,
	  	loadUrl:{content:'${request.contextPath}/repair/card!loadCard.action?dpId='+dpId},
		viewUrl:{content:'${request.contextPath}/repair/card!scanCardDetail.ajax',width:'600px'},
		operation:{add:false,edit:false,del:false},viewDetail:{view:true},gridType:'noSearch'});
		});
		
</script>
</head>
<body>
	  <div>
    <form action="" name="viewForm" id="viewForm">
    <table class="work_info_table" width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr>
					<th colspan="4">
						&lt;&lt;查询条件&gt;&gt;
					</th>
				</tr>
				<tr>
					<td align="right" width="15%">
						<strong>部门：</strong>
					</td>
					<td width="34%">
						<div id="dpId" name="dpId" ztype="select">
   							<ul>
								<#list depts as dept>
									<li val="${dept.id}">${dept.name}</li>
								</#list>
							</ul>
						</div>
					</td>
					<td align="right" width="15%">
						<strong>分区：</strong>
					</td>
					<td>
						<div id="cellId" name="cellId" ztype="select">
							<ul>
								<li val=""></li>
								<#if depts?exists>
   							    <#list depts as dept>
   							    <#if dept.cycles?exists>
								<#list dept.cycles as cycle>
								<#if cycle.cycleCells?exists>
								    <#list cycle.cycleCells as cycleCells>																		
										<li val="${cycleCells.id}">${cycleCells.name}</li>
									</#list>
								</#if>
							    </#list>
							    </#if>
							    </#list>
							    </#if>
							</ul>
						</div>
					</td>
				</tr>
                 <tr>
					<td align="right" >
						<strong>系统：</strong>
					</td>
					<td>
						<div id="cycleId" name="cycleId" ztype="select">
   							<ul>
   							<li val=""></li>
   							<#if depts?exists>
   							<#list depts as dept>
   							<#if dept.cycles?exists>
								<#list dept.cycles as cycle>
									<li val="${cycle.id}">${cycle.name}</li>
								</#list>
							</#if>
							</#list>
							</#if>
							</ul>
						</div>
					</td>
					<td align="right" >
						<strong>检修项目：</strong>
					</td>
					<td>
						<div id="cardsId" name="cardsId" ztype="select">
   							<ul>
   							<li val=""></li>
   							<#if depts?exists>
   							<#list depts as dept>
   							<#if dept.cycles?exists>
						    <#list dept.cycles as cycle>
						    <#if cycle.cycleCells?exists>
							<#list cycle.cycleCells as cycleCells>
							<#if cycleCells.cards?exists>
								<#list cycleCells.cards as cards>	
									<li val="${cards.id}">${cards.name}</li>
								</#list>
							</#if>
							</#list>
							</#if>
							</#list>
							</#if>
							</#list>
							</#if>
							</ul>
						</div>
					</td>					
				</tr>
	            <tr>
					<td colspan="4" align="right">
						<button ztype="button" type="button">查询</button>
					</td>
				</tr>
	</table>
</form>
    </div>
	<div id="card-show"></div>
</body>
</html>
