<html>
	<head>
	</head>
	<body>
		<div style="padding-left:3px;width:98%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
				<tr>
					<th width="20%" nowrap="nowrap">
						天数
					</th>
					<th width="20%" nowrap="nowrap">
						分段数
					</th>
					<th width="60%" nowrap="nowrap">
						排班规则
					</th>
				</tr>
				<#if model?exists && model.stayPeriods?exists && (model.stayPeriods?size>0)>
					<#list model.stayPeriods as stayPeriod>
						<tr class="sortTr">
							<td align="center" class="sortTd">
								${stayPeriod.periodIndex}
							</td>
							<td align="center">
								分${stayPeriod.staySections?size}段
							</td>
							<td align="center">
								<#list stayPeriod.staySections as staySection>
									<div>
										${staySection.startTime?string("HH:mm")} -- ${staySection.endTime?string("HH:mm")}
									</div>
								</#list>
							</td>
						</tr>
					</#list>
				<#else>
					本留守规则没有相关信息!
				</#if>
			</table>
		</div>
	</body>
</html>
