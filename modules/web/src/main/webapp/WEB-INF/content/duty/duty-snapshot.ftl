<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
				var table = $('#historyData');
        		var count = 0 ;
        		var init_date = '' ;
        		table.find('tr:gt(0)').each(function(index){
        			var date = $.trim($(this).find('td:eq(0)').html());
        			if(index == 0){
        				init_date = date;
        			}else if(index == (table.find('tr').length-2)){
        				$(this).find('td:eq(0)').remove();
        				table.find('tr:eq('+(index-count)+')').find('td:eq(0)').attr('rowSpan',count+2);
        			}
        			else{
						if(date == init_date){
							count ++;
							$(this).find('td:eq(0)').remove();
						}else{
							init_date = date;
							table.find('tr:eq('+(index-count)+')').find('td:eq(0)').attr('rowSpan',count+1);
							count = 0;
						}
					}        			
        		});
        		count = 0 ;
        		init_date = '' ;
        		table = $('#newData');
        		table.find('tr:gt(0)').each(function(index){
        			var date = $.trim($(this).find('td:eq(0)').html());
        			if(index == 0){
        				init_date = date;
        			}else if(index == (table.find('tr').length-2)){
        				$(this).find('td:eq(0)').remove();
        				table.find('tr:eq('+(index-count)+')').find('td:eq(0)').attr('rowSpan',count+2);
        			}
        			else{
						if(date == init_date){
							count ++;
							$(this).find('td:eq(0)').remove();
						}else{
							init_date = date;
							table.find('tr:eq('+(index-count)+')').find('td:eq(0)').attr('rowSpan',count+1);
							count = 0;
						}
					}        			
        		});
			});
		</script>
	</head>
	<body>
		<div  class="tips" style="padding-left:30px; align="left">
	                提示：最新排班中<b style="color:blue">(蓝色)</b>字体表示<b style="color: green">(换班)</b>操作中涉及的人员，<b style="color:purple;">(紫色)</b>字体表示<b style="color: green">(替班)</b>操作涉及的人员。
		</div>
		<div style="padding-left:30px;">
	    	<fieldset style="width:46%;vertical-align: top;">
	            <legend><strong>历史排班</strong></legend>
	            <br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table" id="historyData">
					<tr>
						<th width="25%" nowrap="nowrap"> 
							日期
						</th>
						<th width="25%" nowrap="nowrap">
							班次
						</th>
						<th width="50%" nowrap="nowrap">
							人员
						</th>
					</tr>
					<#list oldList as oldDuty>
						<tr height="25">
							<td>
								${oldDuty.startTime?string("yyyy-MM-dd")}
								<input type="hidden" name="historyDate" value='${oldDuty.startTime?string("yyyy-MM-dd")}'/>
							</td>
							<td>
								${oldDuty.schName}
							</td>
							<td>
								${oldDuty.staffOnDuty}
							</td>
						</tr>
					</#list>
				</table>
			</fieldset>
			<fieldset style="width:46%;vertical-align: top">
	            <legend><strong>最新排班</strong></legend>
	            <br>
				<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="work_info_table" id="newData">
					<tr>
						<th width="25%" nowrap="nowrap"> 
							日期
						</th>
						<th width="25%" nowrap="nowrap">
							班次
						</th>
						<th width="50%" nowrap="nowrap">
							人员
						</th>
					</tr>
					<#list newList as newDuty>
						<tr height="25">
							<td>
								${newDuty.startTime?string("yyyy-MM-dd")}
								<input type="hidden" name="historyDate" value='${newDuty.startTime?string("yyyy-MM-dd")}'/>
							</td>
							<td>
								${newDuty.schName}
							</td>
							<td>
								${newDuty.staffOnDuty}
							</td>
						</tr>
					</#list>
				</table>
			</fieldset>
		</div>	
	</body>
</html>
