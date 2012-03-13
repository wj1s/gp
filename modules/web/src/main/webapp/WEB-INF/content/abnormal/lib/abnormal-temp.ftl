<#macro abnormaltemp editInJb>
<form id="abnormalForm" method="post" ztype="form"
	action="abnormal-f!save.action">
	<input type="hidden" id="abnId" name="id" />
	<input type="hidden" id="editInJb" name="editInJb" value="${editInJb}"/>
	<#if taskId?exists>
	<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
	</#if>
	<div align="right">
		<#if editInJb=="0">
		<input type="button" id="submitButton" value="提交审核" ztype="button" onclick="submintFun();"/>		
		<input id="reset" type="button" value="重置" ztype="button" />
			<#if taskId?exists>
				<a ztype="popUp"  title="备注" url="${request.contextPath}/abnormal/abnormal!refuseInput.ajax?taskId=${taskId}&id=${id}" zwidth="350" zheight="200"><input id="refuse" type="button" value="非事故异态" ztype="button" /></a>
			</#if>
		</#if>
		<#if editInJb=="1">
		<input type="button" id="saveButton" value="保存" ztype="button" onclick="saveFun();"/> 
		</#if>
	</div>
<div class="formTitle2"><span>1、录入异态信息</span> </div>	
<table class="work_info_table" width="100%" border="0" cellpadding="0"
	cellspacing="0">
	<tr class="transType">
		<td>传输类型：</td>
		<td colspan="3">
			<div id="transType" name="transType.id" ztype="select">
				<ul>
					<#if curUser.dept.transTypes?exists>
						<#list curUser.dept.transTypes as transTypes>
							<#if (taskId?exists) &&(transTypes.id==abnormal.transType.id)>
							<li val="${transTypes.id}" selected="selected">${transTypes.codeDesc}</li>
							<#else>
							<li val="${transTypes.id}" >${transTypes.codeDesc}</li>
							</#if>
						</#list>	
					<#else>
							<li val="-1">无</li>
					</#if>				
				</ul>
			</div>
		</td>
	</tr>	
	<tr class="type">
		<td>异态  ：</td>
		<td colspan="3">
			<div id="type" name="type" ztype="select">
				<ul>
					<li val="F">故障</li>
					<li val="O">其他异态</li>
				</ul>
			</div>
		</td>
	</tr>
	<tr>
		<td class="f">故障设备：</td>
		<td colspan="3" class="f">
			<input id='equipF' name='equipName'  ztype="auto" zurl="${request.contextPath}/baseinfo/equip!autocompleteTAjax.action" value=""  maxlength="50" />
		</td>
		<td class="o">异态类型：</td>
		<td colspan="3" class="o">
			<div id="abnType" name="abnType.id" ztype="select">
				<ul>
					<#list abnTypes as abnType>
						<li val="${abnType.id}">${abnType.codeDesc}</li>
					</#list>
				</ul>
			</div>
		</td>
	</tr>
	<tr>
		<td>故障发生时间：</td>
		<td><input name="startTime" id="startTime" type="text" value=""
			ztype="startdatetime" verify="开始时间|NotNull&&Datetime"/></td>
		<td>故障结束时间：</td>
		<td><input name="endTime" id="endTime" type="text" value=""
			ztype="enddatetime" verify="结束时间|NotNull&&Datetime"/></td>
	</tr>
	<tr>
		<td colspan="1">故障现象：</td>
		<td colspan="3"><textarea name="desc" id="desc" rows="3" cols="100"
			length="300" autosize="true" verify="故障现象|NotNull&&Length<300" ztype="textarea"></textarea></td>
	</tr>
	<tr>
		<td colspan="1">故障原因：</td>
		<td colspan="3"><textarea name="reason" id="reason" rows="3" cols="100"
			length="300" autosize="true" verify="故障原因|NotNull&&Length<300" ztype="textarea"></textarea></td>
	</tr>
	<tr>
		<td colspan="1">处理措施：</td>
		<td colspan="3"><textarea name="processStep" id="processStep" cols="100"
			rows="3" length="300" autosize="true" verify="处理措施|NotNull&&Length<300" ztype="textarea"></textarea></td>
	</tr>
</table>
<div id='abnOperations'>	
<div class="formTitle2"><span>2、请您确认影响各业务的开始时间与结束时间</span> </div>
<div id="addAbnOperationButton" align="right" style="width: 100%">
<input type="button" ztype="button" id="addAbnOperation" value="添加明细信息"
	onclick="$.abnormal.abnOperations.addAbnOperation();" /></div>		
<table id='abnOperationDetails' class="work_info_table" width="100%" style="table-layout:fixed"
	border="0" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<th width="100px">是否停传</th>
			<th>业务</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>时长</th>
			<th width="80px">操作</th>
		</tr>
	</thead>
	<tbody id='abnOperationsBody'>
	</tbody>
	<tfoot id='abnOperationsFoot'>
		<tr>
			<td colspan="6" align='center'><span style='font-size: 18px'>请点击“添加明细信息”按钮新增,可新增多条</span>
			</td>
		</tr>
	</tfoot>
</table>
</div>
<div id='abnEquips' class='o'>	
<div class="formTitle2"><span>3、请您确认影响各设备的开始时间与结束时间</span> </div>
<div id="addAbnEquipButton" align="right" style="width: 100%"><input
	type="button" ztype="button" id="AbnEquip" value="添加明细信息"
	onclick="$.abnormal.abnEquips.addAbnEquip();" /></div>
<table id='abnEquipDetails' class="work_info_table" width="100%" style="table-layout:fixed"
	border="0" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<th width="100px">是否引起故障</th>
			<th>设备</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>时长</th>
			<th width="80px">操作</th>
		</tr>
	</thead>
	<tbody id='abnEquipsBody'>
	</tbody>
	<tfoot id='abnEquipsFoot'>
		<tr>
			<td colspan="6" align='center'><span style='font-size: 18px'>请点击“添加明细信息”按钮新增,可新增多条</span>
			</td>
		</tr>
	</tfoot>
</table>
</div>
</form>
<form id="rtnForm" method="post"  action="abnormal!show.action">	
	<input type="hidden" id="tabNum" name="tabNum"/>
</form>
</#macro>
