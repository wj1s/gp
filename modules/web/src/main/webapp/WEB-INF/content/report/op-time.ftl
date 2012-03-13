<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				$('button:contains("查询")').click(function(){
					var reptTime = $('#reptTime').tjVal();
					var url = '${request.contextPath}/report/op-time!input.ajax?reptTime='+reptTime;
					$('#editDiv').html('<div style="text-align:center;"><img id="imgLoading" alt="正在加载,请稍后" src="${request.contextPath}/img/loading04.gif"></div>');
					$('#editDiv').load(url);
				});

				$('#reptTime').tjChange(function(){
					$('#editDiv').empty();
				});
				$('button:contains("查询")').click();
			});
		</script>
	</head>
	<body>
		<div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
				<tr>
					<th colspan="2">编辑传输时间</th>
				</tr>
				<tr>
					<td align="right" width="50%">
						选择年月:
					</td>
					<td align="left" width="50%">
						<div ztype="select" id="reptTime">
							<ul>
								<#if (reptTimes?size>0)>
									<#list reptTimes as reptTime>
										<li val="${reptTime}">${reptTime}</li>
									</#list>
								</#if>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<button ztype="button" type="button">查询</button>
					</td>
				</tr>
			</table>
		</div>
		<div id="editDiv"></div>
	</body>
</html>
