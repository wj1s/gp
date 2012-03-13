<html>
<head>
<script language="JavaScript" type="text/javascript">
	var array = [];
	var item = {};
	var repairName='<#if curUser.group?exists><#list curUser.group.people as person>${person.name} </#list><#else>${curUser.name}</#if>';
	var repairNameId='<#if curUser.group?exists><#list curUser.group.people as person>${person.id},</#list><#else>${curUser.id},</#if>';
	function refresh(){
		var examineRecord =$('#examineRecord').val();
		var measureText =$('#measure').val();
		$('#items tr:gt(0)').remove();
		for (var i in array){
			if(examineRecord.indexOf(array[i].examineRecord)<0)
			{
			examineRecord += array[i].examineRecord;
			measureText += array[i].measure;
			}
			var html = '<tr  align="center"><td><input type="checkbox" id="cardIds" name="cardIds" class="checkboxOut" value="'+array[i].id+'"/></td>';
			html += '<td>'+array[i].cycleName+'</td>';
			html += '<td>'+array[i].name+'</td>';
			html += '<td class="empClass">'+array[i].repairName+'<input id="empIds" type="hidden" name="empIds" value="'+array[i].type+'!heng-gang!'+array[i].name+'!dot!'+array[i].cycleName+'!heng-gang!'+array[i].repairNameId+'"/></td></tr>';
			$('#items').append(html).init_style();
		 }
		   
		    $('[name="measure"]').text(measureText);
		    $('[name="examineRecord"]').text(examineRecord);
		    updateRepairPerson();
	}
		
	$(document).ready(function(){
		$('#recordForm').form();
		$('#recordForm').init_style();
		$('#recordForm').ajaxForm({
			type: "post",
			dataType: 'json',
			beforeSubmit: validateForm,
			success: function(data){
				if (data['result'] == true){
					tjAlert('操作成功!');
				var url = '${request.contextPath}/repair/record!add.action';
				window.location.href = url;
				}else {
					tjAlert("操作失败!");
				}
			}
		});
		$('button:contains("删除检修项目")').click(function(){
			if($('.checkboxOut:checked').length>0)
			{
			     i=0;
			 $('.checkboxOut:checked').each(function(){
				
				var removeId=$(this).attr('value');
				if(removeId!=undefined)
				{
				i+=1;
				array=$.grep(array,function(item,i){
				return item.id != removeId;
				});
				$(this).parents('tr').remove();
				}
			 });
			 tjAlert('共删除'+i+'条检修项目！');
			 refresh();
			} else tjAlert('请选择要删除的检修项目！');
			
			
		});
		$('button:contains("全部保存")').click(function(){
			
			if($('.checkboxOut').size()>0)
			{
			$('.checkboxOut:visible').attr('checked',true);
			$('#recordForm').submit();
			}else   tjAlert("请添加检修项目！"); 			
		});
		 var $this=$("a[title='变更检修人员']");
		 var urlT=$this.attr("url")+"?ids=";
		 $('button:contains("变更检修人员")').click(function(){
			if($('.checkboxOut:checked').length==1)
				{
				urlTemp='';
				var $changeNameIds=$('.checkboxOut:checked').parents('tr').find("#empIds").val().split('!heng-gang!')[2];
			    $changeNameIds=$changeNameIds.substring(0,$changeNameIds.lastIndexOf(','));
			    urlTemp= urlT+$changeNameIds;
			    $this.attr("url",urlTemp);
			    $('#changePersonA').trigger('click');
				}else if($('.checkboxOut:checked').length>1)
			    {
				var $changeNameTemp="";
				tagChange=true;
				urlTemp='';
					 $('.checkboxOut:checked').each(function(){
						var $changeNameIds=$(this).parents('tr').find("#empIds").val().split('!heng-gang!')[2];
						$changeNameIds=$changeNameIds.substring(0,$changeNameIds.lastIndexOf(','));
						    if($changeNameTemp!="")
							{
						    if($changeNameTemp!=$changeNameIds)
							{
						    	tagChange=false;
						    	tjAlert('请选择要拥有相同人员的变更检修项目！');
						    	return false; 
							  
							 }else
								{
								urlTemp=urlT+$changeNameIds;
								}
							} else  $changeNameTemp=$changeNameIds;
					 }
					);
					 if(tagChange==true)
						 {
					 $this.attr("url",urlTemp);
					 $('#changePersonA').trigger('click');
						 }
	
			} else tjAlert('请选择要变更检修人员的检修项目！');
		});
		$('#allCheckId').click(function(){
			if($(this).attr('checked')==false){
				$('.checkboxOut:visible').attr('checked','');
			}else if($(this).attr('checked')==true){
				$('.checkboxOut:visible').attr('checked',true);
			}	
		});
		});
	 //点击保存前的验证
	 function validateForm(arr, thisForm){ 
		if (thisForm.data('tjForm').checkSubmit()) {
			return true
		}else{
			return false;					
		}
	}
	 /*项目变更时，改变检修人员*/
	 function updatePersons(ids,names)
	 {
		  //var ids= ["1","3","7"];
		  //var names=["管理员","测试2","测试3"];
		    $('.checkboxOut:checked').each(function(){   	
		    	 var upId=$(this).parents('tr').find('td:eq(0)').find('input').attr('value');
		    	 array=$.grep(array,function(item,i){
		    		if(item.id==upId)
		    		{
		    			item.repairName=names.join(' ');
						item.repairNameId=ids.join(',')+',';
		    		}
					return true;
					});
		    	 refresh();
	        });   
		    $('.checkboxOut:checked').removeAttr('checked');  
	 }
	 function updateRepairPerson()
		{
			  var empNames_temp = $($($($(".checkboxOut:eq(0)")).parent()).siblings(".empClass")).text();
			     $(".checkboxOut:gt(0)").each(function(){
	            var text = $($($(this).parent()).siblings(".empClass")).text();
	            var splitEmpName = text.split(" ");
	            for (var i = 0; i < splitEmpName.length; i++) {
	                if (empNames_temp.indexOf(splitEmpName[i]) == -1) {
	                    empNames_temp +=" " +splitEmpName[i];
	                }
	            }
	        });  	  
	     $('#examinePersons').val(empNames_temp);
		}	 
</script>
</head>
<body>
    	<div>
    		<form action="${request.contextPath}/repair/record!save.action" id="recordForm" name="recordForm" >
	    		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
	                <tr>
	                    <th colspan="4">
	                        <strong><span>填写检修记录 </span></strong>
	                    </th>
	                </tr>
	                <tr>
	                    <td align="right">
	                        <strong>部门：</strong>
	                    </td>
	                    <td>
	                     	<#if curUser.dept?exists>
	                       	 ${curUser.dept.name}
	                       	 </#if>
	                    </td>
	                    <td align="right">
	                        <strong>班组：</strong>
	                    </td>
	                    <td><input readonly="readonly" type="text" ztype="text" value="<#if curUser.group?exists>${curUser.group.name}</#if>"  id="groupId" />
	                    </td>
	                </tr>
	                <tr>
	                    <td align="right">
	                        <strong>检修人员：</strong>
	                    </td>
	                    <td colspan="3">
	                     <input type="text" ztype="text" id="examinePersons" readonly="readonly" name="examinePersons" size="40"  maxlength="100" verify="检修人员|NotNull&&Length<50" value="<#if curUser.group?exists><#list curUser.group.people as person>${person.name} </#list><#else>${curUser.name}</#if>"/>
	                     <input type="hidden" value="<#if curUser.group?exists><#list curUser.group.people as person>${person.id},</#list><#else>${curUser.id},</#if>" id="examinePerpleIds"/> 
	                    </td>
	                </tr>
	                <tr>
	                    <td align="right">
	                        <strong>检修日期：</strong>
	                    </td>
	                    <td>
	                        <input id="ddate" name="ddate" type="text" ztype="date" verify="检修日期|NotNull" value="${curDate?string("yyyy-MM-dd")}"/>
	                    </td>
	                    <td align="right">
	                        <strong>检修时长：</strong>
	                    </td>
	                    <td>
	                        <input  id="timeLength" name="timeLength"  type="text" ztype="text" verify="检修时长|NotNull&&Int"/>分钟
	                    </td>
	                </tr>
	                <tr>
	                    <td align="right">
	                        <strong>安全员：</strong>
	                    </td>
	                    <td>
	                        <input  id="security" name="security" type="text" ztype="text" verify="安全员|NotNull&&Length<20" value="${curUser.name}"/>
	                    </td>
	                    <td align="right">
	                        <strong>负责人：</strong>
	                    </td>
	                    <td>
	                        <input  id="principal" name="principal" type="text" ztype="text" verify="负责人|NotNull&&Length<20" value="${curUser.name}"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td align="right">
	                        <strong>安全措施：</strong>
	                    </td>
	                    <td colspan="7">
	                        <textarea  ztype="textarea"  id="measure"  rows="2" cols="80" name="measure" verify="安全措施|NotNull&&Length<256"></textarea>
	                    </td>
	                </tr>
	                <tr>
	                    <td align="right">
	                        <strong>检修记录：</strong>
	                    </td>
	                    <td colspan="7">
	                        <textarea id="examineRecord"  ztype="textarea"  rows="2" cols="80" name="examineRecord" verify="检修记录|NotNull&&Length<512"></textarea>
	                    </td>
	                </tr>
	                <tr>
	                    <td align="right">
	                        <strong>试机情况：</strong>
	                    </td>
	                    <td colspan="7">
	                        <textarea id="test"  ztype="textarea"  rows="2" cols="80"   name="test" verify="试机情况|NotNull&&Length<256"></textarea>
	                    </td>
	                </tr>
	            </table>
	    	</div>
	    	<div align="right">
	    		<a title="添加检修项目" url="${request.contextPath}/repair/record!itemInputAjax.ajax" ztype="popUp" zwidth="600"><button ztype="button" type="button">添加检修项目</button></a><a title="添加临时检修项目" url="${request.contextPath}/repair/record!itemTemp.ajax" ztype="popUp" zwidth="600"><button ztype="button" type="button">添加临时检修项目</button></a><button ztype="button"  type="button">删除检修项目</button><a title="变更检修人员" url="${request.contextPath}/common/person-ex!getUserUsedAjax.ajax" ztype="popUp" zwidth="600" id="changePersonA"></a><button ztype="button" type="button">变更检修人员</button>
	    	</div>
	    	
	        <div>
	       <table id="items" width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
	                <tr>
	                    <th align="center" nowrap="nowrap" width="30">
	                        <input id="allCheckId" name="all" type="checkbox" />
	                    </th>
	                    <th align="center" nowrap="nowrap" width="150">
	                       	系统名
	                    </th>
	                    <th align="center" nowrap="nowrap">
	                       	 检修项目
	                    </th>
	                    <th align="center" nowrap="nowrap">
	                       	 检修人员
	                    </th>
	                </tr>
	            </table>
	        </div>
	        <div align="right" style="width: 100%">
	        	<button ztype="button" type="button">全部保存</button>
	        </div>
        </form>
	</body>
</html>