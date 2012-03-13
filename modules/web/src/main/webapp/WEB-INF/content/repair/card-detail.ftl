<html>
	<head>
		<title>检修卡片展现</title>		
<style type="text/css">
@media print {
	.notprint {
		display: none;
	}
	.pageNext {
		page-break-after: always;
	}
}

@media screen {
	.notprint {
		display: inline;
		cursor: hand;
	}
}
</style>
</head>
	<body>
		<div>
			<table border="0" cellpadding="0" cellspacing="0" align="center"  width="90%">
				<tr>
					<td>
						<h3 align="center">
							检修卡片
						</h3>
							<table border="0" cellpadding="0" cellspacing="0"
								class="work_info_table" align="center" width="80%">
								<tr>
									<td rowspan="3" align="right" width="10">
										<strong>检修内容</strong>
									</td>
									<td align="right" width="80">
										<strong>分区</strong>：
									</td>
									<td>
										${cardDetail.cycleCell.name}&nbsp;
									</td>
									<td align="right">
										<strong>需用时间:</strong>
									</td>									
									<td align="left" colspan="3">									
									${processTimeShow}
									&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right">
										<strong>项目</strong>：
									</td>
									<td colspan="5">
										${cardDetail.name}&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right">
										<strong>停机时间</strong>：
									</td>
									<td align="left" colspan="5">										
									${shutdownTimeShow}
									&nbsp;
									</td>
								</tr>
								<tr>
									<td rowspan="3" align="right" width="10">
										<strong>保证措施</strong>
									</td>
									<td align="right">
										<strong>仪器工具</strong>：
									</td>
									<td align="left" colspan="5">										
										${cardDetail.tools}&nbsp;	
									</td>
								</tr>
								<tr>
									<td align="right">
										<strong>安全措施</strong>：
									</td>
									<td colspan="5">																		
									     ${cardDetail.measure}&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right">
										<strong>注意事项</strong>：
									</td>
									<td colspan="5">
										${cardDetail.attention}							
									    &nbsp;
									</td>
								</tr>
								<tr>
									<td align="right" width="10">
											<strong>其他措施</strong>
									</td>
									<td align="left" colspan="6">
										${cardDetail.other}
									   &nbsp;
									</td>
								</tr>
								<tr>
									<td align="right" width="10">
										<strong>检修方法</strong>
									</td>
									<td colspan="6">																
									    ${cardDetail.methods}&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right" width="10">
										<strong>技术标准</strong>
									</td>
									<td colspan="6">																		
									    ${cardDetail.technicalStandards}&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right" width="10">
										<strong>备注</strong>
									</td>
									<td align="left" colspan="6">																		
									    ${cardDetail.remark}&nbsp;
									</td>
								</tr>
							</table>
						<div class="notprint">
							<div align="right">
								<input id="button" name="button" type="button"  ztype="button" value="打印"
									onclick="javascript:window.print();" class="input_submit_2" />
								<input id="button" name="button" ztype="button" type="button" value="返回"
									onclick="$('#dialogDiv').dialog('close');" class="input_submit_2" />
							</div>
						</div>

					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
