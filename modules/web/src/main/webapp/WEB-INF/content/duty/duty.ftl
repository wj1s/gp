<html>
<head>
<script type="text/javascript" src="${request.contextPath}/js/print.js"></script>
<script language="JavaScript" type="text/javascript">
	var dpId_g = $('#dpId').tjVal();
	var groupId_g = '';
	var startDate_g = '${currentDate}';
	var endDate_g = '';
	var downLoadFlag=false;
	$(document).ready(function(){
		var dpId_g = '';
		$('button:contains("查询")').click(function(){
			var dpId = $('#dpId').tjVal();
			dpId_g=dpId;
			var groupId = $('#groupId').tjVal();
			groupId_g = groupId;
			var startDate = $('#startDate').val();
			startDate_g=startDate;
			var endDate = $('#endDate').val();
			endDate_g=endDate;
			var url = '${request.contextPath}/duty/duty!loadArrange.action?dpId='+dpId+'&groupId='+groupId+'&startDate='+startDate+'&endDate='+endDate;
			$('#duty-show').tjSearch(url);
			if(downLoadFlag){
				downLoadFlag=false;
				$('#duty-show').css('display','block');
				$('#duty-down').css('display','none');
			}
		});
		$('button:contains("排班表")').click(function(){
			var dpId = $('#dpId').tjVal();
			dpId_g=dpId;
			var groupId = $('#groupId').tjVal();
			groupId_g = groupId;
			var startDate = $('#startDate').val();
			startDate_g=startDate;
			var endDate = $('#endDate').val();
			endDate_g=endDate;			
			if(dpId==''){
				tjAlert('部门不能为空!')
				return;
			}
			if(startDate==''){
				tjAlert('开始日期不能为空!')
				return;
			}	
			if(endDate==''){
				tjAlert('结束日期不能为空!')
				return;
			}		
			downLoadFlag=true;
			$('#duty-show').css('display','none');
			$('#duty-down').css('display','block');
			var url = '${request.contextPath}/duty/duty!toShowReport.blank?dpId='+dpId+'&groupId='+groupId+'&startDate='+startDate+'&endDate='+endDate;			
			$('#iframe1').attr('src', url);
		});		
		$('button:contains("打印")').click(function(){
            if(!checkLodop('${request.contextPath}')){
          	  return;
            }
			var url = 'duty!loadArrange.blank?sidx=&print=print&dpId='+dpId_g+'&groupId='+groupId_g+'&startDate='+startDate_g+'&endDate='+endDate_g;
        	  LODOP.ADD_PRINT_URL(30,20,746,509,url);
        	  LODOP.SET_PRINT_STYLEA(1,"HOrient",3);
        	  LODOP.SET_PRINT_STYLEA(1,"VOrient",3);
          	  LODOP.PREVIEW();			
		});		
		//部门班组联动
		$('#dpId').tjChange(function(){
			var id = $('#dpId').tjVal();
			$.getJSON('${request.contextPath}/baseinfo/dept!getGroupsAjax.action?rd='+Math.random(),
				{'id':id},
				function(data){
					$('#groupId').tjEmpty();
					if (data.result){
						var array = eval(data['data']);
						var html = '<li val="">全部</li>';
						for(var i in array){
							html += '<li val="'+array[i].value+'">'+array[i].name+'</li>';
						}
						$('#groupId ul').append(html);
						$('#groupId').tjAssemble();
					}
				}
			);
		});
		changeDateToweak(document.getElementById('startDate'));
		<#if dpId?exists>
			var dpId = ${dpId};
		<#else>
			var dpId = 0;
			$('button').attr('disabled',true);
		</#if>
 	  	var colModel=[{name:'id',header:'id',hidden:true,editable:false},
 	  	           	  {name:'date',header:'日期'},
 	  	       		  {name:'group_name',header:'班组'},
 	  	              {name:'schName',header:'班次'},
 					  {name:'startTime',header:'上班时间'},
 					  {name:'endTime',header:'下班时间'},
 					  {name:'staffOnDuty',header:'人员'}];
 		$('#duty-show').grid({name:'排班信息',colModel:colModel,gridType:'easy',
 		loadUrl:{content:'${request.contextPath}/duty/duty!loadArrange.action?dpId='+dpId+'&startDate=${currentDate}'}});
	});
	function changeDateToweak(obj){
		var idDiv='div_'+obj.id;
		var date= obj.value;
		var d1 = date.split("-"); 
		var now = new Date(d1[0], d1[1]-1, d1[2]);
		var n=now.getDay();
		var showText='(星期';
		if((n+'')=='NaN'){
			showText='';
		}else if(n==1){
			showText=showText+'一)';
		}else if(n==2){
			showText=showText+'二)';
		}else if(n==3){
			showText=showText+'三)';
		}else if(n==4){
			showText=showText+'四)';
		}else if(n==5){
			showText=showText+'五)';
		}else if(n==6){
			showText=showText+'六)';
		}else if(n==0){
			showText=showText+'日)';
		}
		$('#'+idDiv).html(showText);
	}
</script>
</head>
<body>
	<!--
	<div id="printMsg"></div>
	<object  id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0></object>
	-->
    <div>
		<form action="" name="viewForm" id="viewForm">
			<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
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
						<strong>班组：</strong>
					</td>
					<td>
						<div id="groupId" name="groupId" ztype="select">
							<ul>
								<li val="">全部</li>
								<#if groups?exists>
									<#list groups as group>
										<li val="${group.id}">${group.name}</li>
									</#list>
								</#if>
							</ul>
						</div>
					</td>
				</tr>

				<tr>
					<td align="right">
						<strong>开始日期：</strong>
					</td>
					<td>
						<input type="hidden" name="currentDate" value="${currentDate}" />
						<input id="startDate" name="startDate" type="text" value="${currentDate}" ztype="startdate" onchange="changeDateToweak(this);"/>
						<div id="div_startDate" style="display:inline"></div>
					</td>
					<td align="right">
						<strong>结束日期：</strong>
					</td>
					<td>
						<input id="endDate" name="endDate" type="text" value="" ztype="enddate"  onchange="changeDateToweak(this);"/>
						<div id="div_endDate" style="display:inline"></div>
					</td>

				</tr>
				<tr>
					<td colspan="2" align="center">
						<span class="work_clew_td" style="color:black;"><img src="${request.contextPath}/img/info.png"
							style="margin-bottom: -4px;" /> 如果需要查询历史排班记录，请重新输入开始日期。</span>
					</td>
					<td colspan="2" align="right">
						<!--
						<button ztype="button" type="button">打印</button>
						-->
						<button ztype="button" type="button">查询</button>
						<button ztype="button" type="button">排班表</button>
					</td>
				</tr>
			</table>
		</form>
    </div>
    <div id="duty-show"></div>
    <div id="duty-down">
	<iframe id="iframe1" name="iframe1" frameborder="no"  border="0" style="width:100%;border-width:0;" height="400" src=""></iframe>    
    </div>
</body>
</html>
