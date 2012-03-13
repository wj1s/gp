<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('button:contains("查询")').click(function(){
					var opName = encodeURIComponent($('#opName').val());
					var broadByReasonId = $('#broadByReason').tjVal();
					var startDate = $('#startDate').val();
					var endDate = $('#endDate').val();
					var broadByStationId = $('#broadByStation').tjVal();
					var broadByFlag=$('#broadByFlag').tjVal();
					var url = '${request.contextPath}/report/broad-by-time!load.action?opName='+opName+
						'&broadByReasonId='+broadByReasonId+'&broadByStationId='+broadByStationId+
						'&startDate='+startDate+'&endDate='+endDate+'&broadByFlag='+broadByFlag;
					$('#broadByTime-show').tjSearch(url);
				});

				var colModel=[{name:'id',header:'id',hidden:true,editable:false},
		 	  	           	  {name:'operation.name',header:'业务'},
		 	  	           	  {name:'startTime',header:'开始时间'},
		 	  	           	  {name:'endTime',header:'结束时间'},
		 	  	           	  {name:'broadByReason.codeDesc',header:'代播原因'},
		 	  	           	  {name:'braodByFlagStr',header:'代播|被代'},
		 	  	           	  {name:'autoFlagStr',header:'是否自动'},
		 	  	           	  {name:'broadByStation.codeDesc',header:'对方台站'}];
		 		$('#broadByTime-show').grid({name:'代播信息',colModel:colModel,gridType:'noSearch',
		 		loadUrl:{content:'${request.contextPath}/report/broad-by-time!load.action?&startDate=${startDate}'},
		 		inputUrl:{content:'${request.contextPath}/report/broad-by-time!input.ajax'},crudType:'${crudType?if_exists}',setting:{edit:true,search:false,multiselect:false,toolbar:[true,"both"]}});
			});
		</script>
	</head>
	<body>
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
							<strong>业务：</strong>
						</td>
						<td width="34%">
							<input name="operation.name" id="opName" ztype="auto" zurl="${request.contextPath}/baseinfo/operation!autocompleteAjax.action"/>
						</td>
						<td align="right" width="15%">
							<strong>代播原因：</strong>
						</td>
						<td>
							<div id="broadByReason" name="broadByReason.id" ztype="select">
								<ul>
									<li val="">无</li>
									<#if broadByReasons?exists>
										<#list broadByReasons as broadByReason>
											<li val="${broadByReason.id}">${broadByReason.codeDesc}</li>
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
							<input id="startDate" name="startDate" type="text" value="${startDate}" ztype="startdate" />
						</td>
						<td align="right">
							<strong>结束日期：</strong>
						</td>
						<td>
							<input id="endDate" name="endDate" type="text" value="" ztype="enddate" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<strong>代播/被代：</strong>
						</td>
						<td>
							<div id="broadByFlag" name="broadByFlag.id" ztype="select">
								<ul>
									<li val="">无</li>
									<li val="D">代播</li>
									<li val="B">被代</li>
								</ul>
							</div>
						</td>
						<td align="right">
							<strong>代播台站：</strong>
						</td>
						<td>
							<div id="broadByStation" name="broadByStation.id" ztype="select">
								<ul>
									<li val="">无</li>
									<#if broadByStations?exists>
										<#list broadByStations as broadByStation>
											<li val="${broadByStation.id}">${broadByStation.codeDesc}</li>
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
	    <div id="broadByTime-show"></div>
	</body>
</html>
