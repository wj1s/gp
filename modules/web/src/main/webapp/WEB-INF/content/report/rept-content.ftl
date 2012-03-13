<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){	
				$('#queryForm').form();

				//记录多选框的查询条件
				<#if reptCarrier.transType?exists>
					$('#transType').tjVal($('#hiddenTransType').val());
				</#if>
				
				//计算确定按钮的位置
				var arrayInput = $('#hiddenInput').val().split(',');				
				var cols = parseInt(arrayInput.length) * 2 + 1;
				$('#buttonTd').attr('colspan', cols);

				$('button:contains("查询")').click(function(){
					if ($('#queryForm').data('tjForm').checkSubmit()) {
						var url = '${request.contextPath}/report/rept!rqExamRept.blank?reptId=' + $('#hiddenReptId').val();
						if ($('#startTime').val() != '' && $('#startTime').val() != undefined){
							url += '&reptCarrier.startTime=' + $('#startTime').val();
						}
						if ($('#endTime').val() != '' && $('#endTime').val() != undefined){
							url += '&reptCarrier.endTime=' + $('#endTime').val();
						}
						if ($('#calMonth').val() != '' && $('#calMonth').val() != undefined){
							url += '&reptCarrier.calMonth=' + $('#calMonth').val();
						}
						if ($('#transType').tjVal() != undefined){
							url += '&reptCarrier.transType=' + $('#transType').tjVal();
						}
						if ($('#transTypeDB').tjVal() != undefined){
							url += '&reptCarrier.transTypeDB=' + $('#transTypeDB').tjVal();
						}
						if ($('#imptPeriod').tjVal() != undefined){
							url += '&reptCarrier.imptPeriod=' + $('#imptPeriod').tjVal();
						}
						if ($('#broadByFlag').tjVal() != undefined){
							url += '&reptCarrier.broadByFlag=' + $('#broadByFlag').tjVal();
						}
					}
					$('#iframe1').attr('src', url);
				});
			});
		</script>
	</head>
	<body>
		<input type="hidden" id="hiddenInput" value="${reptDefExam.modelInput}"/>
		<input type="hidden" id="hiddenIndex" value="${index}"/>
		<input type="hidden" id="hiddenReptId" value="${reptId}"/>
		<input type="hidden" id="hiddenTransType" value="${reptCarrier.transType?if_exists}"/>
		<div id="queryDiv" style="float:left;width:100%;">
			<form action="" id="queryForm">
				<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<#list inputs as input>
							<#if input=="start_time">
								<td align="right" width="100">
									<strong>开始时间：</strong>
								</td>
								<td align="left" width="100">
									<input type="text" ztype="startdate" style="width:120px;" id="startTime" verify="开始时间|NotNull"
									 value="${reptCarrier.startTime?if_exists}"/>
								</td>
							</#if>
							<#if input=="end_time">
								<td align="right" width="100">
									<strong>结束时间：</strong>
								</td>
								<td align="left" width="100">
									<input type="text" ztype="enddate" style="width:120px;" id="endTime" verify="结束时间|NotNull" value="${reptCarrier.endTime?if_exists}"/>
								</td>
							</#if>
							<#if input=="cal_month">
								<td align="right" width="100">
									<strong>选择年月：</strong>
								</td>
								<td align="left" width="100">
									<input type="text" ztype="dateYm" style="width:80px;" id="calMonth" verify="年月|NotNull"/>
								</td>
							</#if>
							<#if input=="trans_type">
								<td align="right" width="100">
									<strong>传输方式：</strong>
								</td>
								<td align="left" width="120">
									<div ztype="select" id="transType" style="width:80px;" verify="NotNull">
										<ul>
											<#if transTypes?exists && (transTypes?size>0)>
												<#list transTypes as transType>
													<li val="${transType.paraCode}">${transType.codeDesc}</li>
												</#list>
											</#if>
										</ul>
									</div>
								</td>
							</#if>
							<#if input=="trans_type_db">
								<td align="right" width="100">
									<strong>传输方式：</strong>
								</td>
								<td align="left" width="120">
									<div ztype="select" id="transTypeDB" style="width:80px;" verify="NotNull">
										<ul>
											<#if transTypes?exists && (transTypes?size>0)>
												<#list transTypes as transType>
													<#if transType.paraCode != 'T'>
														<li val="${transType.paraCode}">${transType.codeDesc}</li>
													</#if>
												</#list>
											</#if>
										</ul>
									</div>
								</td>
							</#if>
							<#if input=="impt_period_seq">
								<td align="right" width="100">
									<strong>重要保证期：</strong>
								</td>
								<td align="left" width="120">
									<div ztype="select" id="imptPeriod" style="width:80px;" verify="NotNull">
										<ul>
											<#if imptPeriods?exists && (imptPeriods?size>0)>
												<#list imptPeriods as imptPeriod>
													<li val="${imptPeriod.id}">${imptPeriod.imptPeriodName}</li>
												</#list>
											</#if>
										</ul>
									</div>
								</td>
							</#if>
							<#if input=="broad_by_flag">
								<td align="right" width="100">
									<strong>代播标志：</strong>
								</td>
								<td align="left" width="120">
									<div ztype="select" id="broadByFlag" style="width:80px;" verify="NotNull">
										<ul>
											<li val="D">代播</li>
											<li val="B">被代</li>
										</ul>
									</div>
								</td>
							</#if>
						</#list>
						<td >&nbsp;</td>
					</tr>
					<tr>
						<td align="right" id="buttonTd">
							<button ztype="button" type="button">查询</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div align="center" id="reportDiv">
			<iframe id="iframe1" name="iframe1" frameborder="no" scrolling="no" border="0" style="width:100%;border-width:0;" height="400" src=""></iframe>
		</div>
	</body>
</html>
