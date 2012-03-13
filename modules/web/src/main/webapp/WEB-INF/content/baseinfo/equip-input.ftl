<html>
<head>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
			$('button:contains("关闭")').click(function(){
				$('#dialogDiv').dialog('close');
			});

			$('#equipTypes').tjChange(function(){
				var equipTypeId = $('#equipTypes').tjVal();
				$.ajax({
					type: 'POST',
					url: '${request.contextPath}/baseinfo/para-dtl!getEquipModelsAjax.action?rd='+Math.random(),
					dataType: "json",
					data: {id:equipTypeId},
					async: false,
					success: function(data){
						$('#equipModelList').tjEmpty();
						if (data.result == true){
							var array = eval(data['data']);
							var html = '';
							if (array.length == 0){
								html += '<li val="-1">无</li>';
							}
							for(var i in array){
								html += '<li val="'+array[i].id+'">'+array[i].name+'</li>';
							}
							$('#equipModelList').find('ul').append(html);
							$('#equipModelList').tjAssemble();
						}
					}
				});
			});

			$('#equipTypes').trigger('tjChange');
			<#if equip?exists>				
				var html ='';
				$('#channelsDiv').empty();
				<#list equip.channels as channel>
					html =html+ '<font id="lab_${channel.id}" style="display:inline"><input type="checkbox" id="sub_${channel.id}" value="${channel.id}"  name="channelIds" onclick="clickchannelOnSub(${channel.id})" checked/>${channel.name}</font>';										
				</#list>
				$('#channelsDiv').append(html);	
			</#if>
		});
			
		function onSuccess(data){
			if (data.result == true){
				var id = $('#myForm').find('input[name="id"]').val();
				var name = $('#myForm').find('input[name="name"]').val();
				$('#dialogDiv').dialog('close');
				$('#grid-table-equip-show').jqGrid().trigger("reloadGrid");
				var message = '';
				if (id == ''){
					message = '设备'+name+'保存成功!';
				}else {
					message = '设备'+name+'更新成功!';
				}
				$('#tb_grid-table-equip-show').html('<font color="#ffb400">'+message+'</font>');
			}else {
				$('#tb_grid-table-equip-show').html('<font color="#ffb400">设备'+name+'保存失败</font>');
			}
		}
		
		 //系统通路联动
		function getChannels(target){
			var systemName = $(target).val();
			$.ajax({
				type: 'POST',
				url: '${request.contextPath}/baseinfo/tech-system!getChannelsAjax.action?rd='+Math.random(),
				dataType: "json",
				data: {systemName:systemName},
				async: false,
				success: function(data){
					$('#channelsId').empty();
					if (data.result == true){
						var array = eval(data['data']);
						var html = '';
						if (array.length == 0){
							html += '本系统下没有任何通路';
						}
						for(var i in array){
							html += '<input type="checkbox" id="sel_'+array[i].id+'" value="'+array[i].id+'" name="channelOnSel" onclick="clickchannelOnSel(\''+array[i].id+'\',\''+array[i].name+'\')"/>'+array[i].name;
						}
						$('#channelsId').append(html);
					}
				}
			});
		}
		function clickchannelOnSel(id,name){			
			var obj=document.getElementById("sel_"+id);	
			var objSub=document.getElementById("sub_"+id);	
			var objLab=document.getElementById("lab_"+id);	
			var objChannelIds=document.getElementsByName("channelIds");
			if(obj.checked){//判断一下是否存在、不存在则添加 、存在则不添加				
				if(!objSub){
					if(objChannelIds.length==0){
						$('#cha_blank').remove();
					}
					var html = '<font  id="lab_'+id+'" style="display:inline"><input type="checkbox" id="sub_'+id+'" value="'+id+'"  name="channelIds" onclick="clickchannelOnSub('+id+')" checked/>'+name+"</font>";
					$('#channelsDiv').append(html);	
				}
			}else{//判断一下是否存在、存在则删掉、不存在则不删除
				if(objSub){
					$(objSub).remove();//取消勾选则去掉该多选	
					$(objLab).remove();
					//如果是最后一条则添加cha_blank
					objChannelIds=document.getElementsByName("channelIds");
					if(objChannelIds.length==0){
						var html = '<font  id="cha_blank">&nbsp;</font>';
						$('#channelsDiv').append(html);	
					}
				}				
			}					
		}
		function clickchannelOnSub(id){
			var obj=document.getElementById("sub_"+id);
			var objLab=document.getElementById("lab_"+id);				
			if(obj){
				$(obj).remove();//取消勾选则去掉该多选	
				$(objLab).remove();
				var objChannelIds=document.getElementsByName("channelIds");				
				if(objChannelIds.length==0){
					var html = '<font  id="cha_blank">&nbsp;</font>';
					$('#channelsDiv').append(html);	
				}
			}
		}
		function validateForm(arr, thisForm){
			if (thisForm.data('tjForm').checkSubmit()) {
				if ($('#equipModelList').tjVal() == -1){
					tjAlert('没有设备型号，不能添加!');
					return false;
				}
				var objChannelIds=document.getElementsByName("channelIds");	
				if(objChannelIds.length==0){
					tjAlert('所属通路不能为空!');
					return false;
				}				
				return true;
			}else{
				return false;					
			}
		}
	</script>
</head>
<body>

	<div style="width:500px;">
		<form action="${request.contextPath}/baseinfo/equip!save.action" ztype="form" id="myForm">
			<input type="hidden" name="id" value="<#if equip?exists>${equip.id?if_exists}</#if>" />
			<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">名称：</td>
					<td><input type="text" name="name" ztype="text" verify="设备名称|Length<50&&NotNull&&NotRepeat={val:'baseinfo.equip.name',delay:true}" value="<#if equip?exists>${equip.name?if_exists}</#if>"></td>
				</tr>
				<tr>
					<td align="right">选择系统：</td>
					<td align="left">
						<input type="text" id="techSystem" ztype="auto"  zurl="${request.contextPath}/baseinfo/tech-system!autocompleteAjax.action?funModule=baseinfo"  onblur="getChannels(this);" value="" />
					</td>				
				</tr>
				<tr>
				    <td align="right">选择通路：</td>
				    <td align="left">
				    	<div id="channelsId">
				    		请先输入系统名称
				    	</div>
				    </td>				    
				</tr>
				<tr>
				    <td align="right">所属通路：</td>
				    <td align="left">
				    	<div id="channelsDiv">
				    		<font  id="cha_blank">&nbsp;</font>
				    	</div>	
				    </td>				    
				</tr>				
				<tr>
					<td align="right">所属环节：</td>
					<td align="left">
						<input type="text" ztype="auto" name="tache.name" zurl="${request.contextPath}/baseinfo/tache!autocompleteAjax.action?funModule=baseinfo" verify="所属环节|NotNull" value="<#if equip?exists>${equip.tache.name?if_exists}</#if>" />
					</td>				
				</tr>
				<tr>
					<td align="right">设备类型：</td>
					<td align="left">
						<#if equipTypes?exists && (equipTypes?size>0)>
							<div id="equipTypes" name="equipType.id" ztype="select">
								<ul>
									<#list equipTypes as equipType>
										<#if equip?exists && equip.equipModel.equipType.id == equipType.id>
											<li val="${equipType.id}" selected="selected">${equipType.codeDesc}</li>
										<#else>
											<li val="${equipType.id}">${equipType.codeDesc}</li>
										</#if>
									</#list>
								</ul>
							</div>
						</#if>
					</td>				
				</tr>
				<tr>
					<td align="right">设备型号：</td>
					<td align="left">
						<div id="equipModelList" name="equipModel.id" ztype="select">
							<ul>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<td style="width:50%;text-align: right;border: 0px;background: #fff;padding-right: 5px;" colspan="2">
						<div align="right">
							<button ztype="button" type="submit">保存</button>
							<button ztype="button" type="button">关闭</button>
					    </div>
					</td>
				</tr>
		     </table>
	     </form>
	</div>
</body>
</html>
