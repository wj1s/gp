<html>
	<head>
		<title>卡片模版展现</title>		
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
							检修卡片模版
						</h3>
							<table border="0" cellpadding="0" cellspacing="0"
								class="work_info_table" align="center" width="80%">
								<tr>
									<td rowspan="3" align="right" width="10">
										<strong>检修内容</strong>
									</td>
									<td align="right" width="80">
										<strong>模版名称</strong>：
									</td>
									<td align="left" colspan="3">
										${cardModel.name}&nbsp;
									</td>
									<td align="right">
										<strong>设备类型</strong>：
									</td>
									<td>
										${cardModel.equipType.codeDesc}&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right">
										<strong>需用时间</strong>：
									</td>									
									<td  colspan="5">${processTimeShow}</td>
								</tr>
								<tr>
									<td align="right">
										<strong>停机时间</strong>：
									</td>
									<td align="left" colspan="5">										
									${shutdownTimeShow}									
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
										${cardModel.tools}&nbsp;	
									</td>
								</tr>
								<tr>
									<td align="right">
										<strong>安全措施</strong>：
									</td>
									<td colspan="5">																		
									     ${cardModel.measure}&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right">
										<strong>注意事项</strong>：
									</td>
									<td colspan="5">
										${cardModel.attention}							
									    &nbsp;
									</td>
								</tr>
								<tr>
									<td align="right" width="10">
											<strong>其他措施</strong>
									</td>
									<td align="left" colspan="6">
										${cardModel.other}
									   &nbsp;
									</td>
								</tr>
								<tr>
									<td align="right" width="10">
										<strong>检修方法</strong>
									</td>
									<td colspan="6">																
									    ${cardModel.methods}&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right" width="10">
										<strong>技术标准</strong>
									</td>
									<td colspan="6">																		
									    ${cardModel.technicalStandards}&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right" width="10">
										<strong>备注</strong>
									</td>
									<td align="left" colspan="6">																		
									    ${cardModel.remark}&nbsp;
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
