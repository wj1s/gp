<html>
	<head>
		<script type="text/javascript" src="${request.contextPath}/js/print.js"></script>
		<script language="JavaScript" type="text/javascript">			
			$(document).ready(function(){				
				//设置验证form					
				$('*[type="text"]').each(function(index){
					var a=this.id;
					if(a!=null&&a!=""){
						var b=a.split('input_');
						var c=a.split('_cid');
						if((b.length>1)||(c.length>1)){
							$(this).css('width','400px');
							$(this).parents('.inputLeft').css('width','418px');
							$(this).parents('.inputRightNo').css('width','418px');
						}
					}													
				})
				$('#addEditForm1').form();
				<#if formMap.cycle.cycleCells.size() != 0>
				<#list formMap.cycle.cycleCells as cycleCells>									
						<#if (cycleCells.cards?exists)&&(cycleCells.cards.size()!=0) >
						<#list cycleCells.cards as cards>	
							$('#editForm_${cards.id}').form();
						</#list>
						</#if>	
						$('#addForm_${cycleCells.id}').form();							
				</#list>
				</#if>				
				$('.a_add').click(function(){
					var periodDiv = $(this).parents('fieldset').find('.period_div');					
					periodDiv.show();					
				});
				//定义弹出框
				$('#dialogDiv1').dialog({
					resizable: false,
					autoOpen: false,
					height: 161,
					width: 400,
					modal: true,
					title:'新增分区'						
				});	
				//新增区间的点击事件											
				$('#btn_addCell').click(function(){	
					var obj2=document.getElementById('formMap.cycleCellAddEdit');
					obj2.value='add';		
					(document.getElementById('name1')).value='';					
					$('#dialogDiv1').dialog('open');
				});
				
			});
			//显示div
			function showDiv(id){
				var obj=document.getElementById(id);
				$(obj).parents('fieldset').find('.period_div').show();
				$(obj).css('display','none').siblings('.a_hide').show();				
			}
			//隐藏div
			function hideDiv(id){
				var obj=document.getElementById(id);
				$(obj).parents('fieldset').find('.period_div').hide();
				$(obj).css('display','none').siblings('.a_show').show();			
			}
			//编辑分区的点击事件
			function editCells(id,name){				
				$('#dialogDiv1').dialog('option', 'title', '编辑分区');
				var obj2=document.getElementById('formMap.cycleCellAddEdit');
				var obj3=document.getElementById('formMap.cycleCellId');
				var obj4=document.getElementById('name1');
				obj2.value='edit';
				obj3.value=id;
				obj4.value=name;
				$('#dialogDiv1').dialog('open');
			}
			//保存分区
			function saveCells(){
				var check=$('#addEditForm1').data('tjForm').checkSubmit();
				if(!check)return;				
				var obj2=document.getElementById('addEditForm1');
				var obj3=document.getElementById('formMap.cycleCellAddEdit');
				var obj4=document.getElementById('name1');
				var obj5=document.getElementById('formMap.name');
				obj5.value=obj4.value;
				if(obj3.value=='add'){
					obj2.action="${request.contextPath}/repair/cycle-cell!add.action";
				}else{
					obj2.action="${request.contextPath}/repair/cycle-cell!edit.action";
				}												
				obj2.submit();
			}
			//删除分区
			function deleteCells(id){
				var obj2=document.getElementById('addEditForm1');
				var obj3=document.getElementById('formMap.cycleCellId');
				tjConfirm("确定删除选中的分区？",function(flag){
					if (!flag){
						return;
					}else{
						//ajax判断是否可以删除				
						$.ajax({
							  type: 'POST',
							  url: '${request.contextPath}/repair/cycle-cell!checkDeleteCell.action',
							  dataType: "json",
							  data: {ids:id},
							  async: false,
							  success: function(data){								 
								  if (data.result == false){
									  	tjAlert("存在检修记录不能删除！");
								  		return ;
								  }else{
										obj3.value=id;
										obj2.action="${request.contextPath}/repair/cycle-cell!delete.action";
										obj2.submit();							  	
								  }
							  }
						});	
					}
				});									
			}
			//添加卡片
			function addCard(id){
				restartAll();
				showDiv('a_show'+id);
				$('#form_'+id).show();	
				var obj=document.getElementById('input_'+id);
				obj.value='';
				window.setTimeout("(document.getElementById('input_"+id+"')).focus();",50);						
			}
			//保存卡片
			function saveCard(cardName,cardPeriod,cell,flag,cardId,formId){			
				var check=$('#'+formId).data('tjForm').checkSubmit();							
				var obj0=document.getElementById(cardName);
				var obj1=$("#"+cardPeriod);				
				var obj2=document.getElementById('addEditForm1');
				var obj3=document.getElementById('formMap.periodId');
				var obj4=document.getElementById('formMap.cycleCellId')
				var obj5=document.getElementById('formMap.name');
				var obj6=document.getElementById('formMap.cardId');
				//alert(formId+':'+check+':'+obj5.value.length);							
				if(!check)return;	
				obj5.value=obj0.value;
				obj4.value=cell;
				obj3.value=obj1.tjVal();
				obj6.value=cardId;
				if(flag=='add'){
					obj2.action="${request.contextPath}/repair/cycle-cell!addCard.action";	
				}else{
					obj2.action="${request.contextPath}/repair/cycle-cell!editCard.action";	
				}							
				obj2.submit();
			}
			//启用停用卡片
			function changeAbleCard(id,ableNum){				
				var url="";
				if(ableNum==0){
					url="${request.contextPath}/repair/cycle-cell!disableCard.action";
				}else{
					url="${request.contextPath}/repair/cycle-cell!enableCard.action";						
				}										
				$.ajax({
					  type: 'POST',
					  url: url,
					  dataType: "json",
					  data: {ids:id},
					  async: false,
					  success: function(data){								  
						  if (data.result == false){
							  	tjAlert("选中的卡片不存在，请刷新后再试！");
						  		return ;
						  }else{//执行变更操作									
						  		var obj1=document.getElementById('imgAble_'+id);
						  		var obj2=document.getElementById('imgDisable_'+id);
								var innerHtmlStr='';								
								if(ableNum==1){//src alt click
									obj1.src="${request.contextPath}/img/icon_enable.gif";
									obj1.alt="停用检修项目";
									obj1.onclick=function (){
										changeAbleCard(id,0);
									}																									
									obj2.src="${request.contextPath}/img/icon_edit.gif";
									obj2.alt="编辑检修项目";
									obj2.onclick=function (){
										editItem(id);
									}									
								}else{
									obj1.src="${request.contextPath}/img/icon_disable.gif";
									obj1.alt="启用检修项目";									
									obj1.onclick=function (){
										changeAbleCard(id,1);
									}																
									obj2.src="${request.contextPath}/img/icon_del.gif";
									obj2.alt="删除检修项目";
									obj2.onclick=function (){
										deleteCard(id);
									}																	
								}								
						  }
					  }
				});				
			}
			//删除卡片
			function deleteCard(id){
				var obj2=document.getElementById('addEditForm1');
				var obj3=document.getElementById('formMap.cardId');
				tjConfirm("确定删除选中的卡片？",function(flag){
					if (!flag){					
						return;
					}else{
						//ajax判断是否可以删除				
						$.ajax({
							  type: 'POST',
							  url: '${request.contextPath}/repair/cycle-cell!checkDeleteCard.action',
							  dataType: "json",
							  data: {ids:id},
							  async: false,
							  success: function(data){								  
								  if (data.result == false){
									  	tjAlert("存在检修记录不能删除！");
								  		return ;
								  }else{
										obj3.value=id;
										obj2.action="${request.contextPath}/repair/cycle-cell!deleteCard.action";
										obj2.submit();						  	
								  }
							  }
						});																					
					}
				});				
			}
			//完成按钮的点击事件
			function cellFinish(){
				var obj2=document.getElementById('addEditForm');
				obj2.action="${request.contextPath}/repair/cycle!show.action";			
				obj2.submit();
			}
			//显示编辑项目
			function editItem(id){
		    	restartAll();
		    	$('#span_item_'+id).hide();
				$('#form_u_'+id).show();
				var obj=document.getElementById(id+'_cid');
				obj.focus();
			}
			//恢复
			function restartAll(){
				$('.form_u_').hide();
				$('.span_item_').show();	
				$('.form_').hide();					
			}
            function Preview(){
              if(!checkLodop('${request.contextPath}')){
            	  return;
              }
          	  LODOP.ADD_PRINT_URL(30,20,746,509,'cycle-cell!show.blank?formMap.print=print&formMap.id=${formMap.cycle.id}');
        	  LODOP.SET_PRINT_STYLEA(1,"HOrient",3);
        	  LODOP.SET_PRINT_STYLEA(1,"VOrient",3);
          	  LODOP.PREVIEW();
             }	       
		</script>
		<style>
			.form_ {
				float:left;	
				padding:5px;
				text-align:left; 
				border:1px #ccc solid;
				background:#efefef;
				display:none;
			}
			.span_item_ {
				float:left;
				width:80%;
			}	
			.span_item_ .span_item_name_{
				float:left;
				width:70%;
			}
			.span_item_ .span_item_period_{
				float:right;
				width:20%;
			}	
			.form_u_{
				float:left;					
				padding:5px;
				text-align:left; 
				border:1px #ccc solid;
				background:#efefef;
				display:none;
			}													
		</style>		
	</head>
	<body>
	<div id="printMsg"></div>
	<object  id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0></object> 
	<div>
		<div align="center">
			<h2>&lt;&lt;${formMap.cycle.name}&gt;&gt;</h2>
		</div>
		<form id="addEditForm"  action="" method="post" style="border:0;margin:0;padding:0;">
		<div align="right">
			<button ztype="button" id="btn_addCell" type="button" >新增分区</button>
			<button ztype="button" onclick="cellFinish();" type="button" >完成</button>
			<button ztype="button" type="button" onclick="Preview();">打印</button>
		</div>
		</form>	
		<#if formMap.cycle.cycleCells.size() != 0>
		<#list formMap.cycle.cycleCells as cycleCells>
			<fieldset class="fieldset">
		    	<legend>
		    		<a class="a_show" id="a_show${cycleCells.id}" onclick="showDiv('a_show${cycleCells.id}');"><img src="${request.contextPath}/img/icon_close.jpg" alt="显示" style="margin-right:4px;"/>${cycleCells.name}</a>
		    		<a class="a_hide" id="a_hide${cycleCells.id}" onclick="hideDiv('a_hide${cycleCells.id}');" style="display:none;"><img src="${request.contextPath}/img/icon_open.jpg" alt="隐藏" style="margin-right:4px;"/>${cycleCells.name}</a>
			      	<a class="a_add"><img src="${request.contextPath}/img/icon_add.gif" alt="新增" onclick="addCard('${cycleCells.id}');"  style="margin-bottom:-2px;"/></a>
			      	<a><img src="${request.contextPath}/img/icon_edit.gif" alt="编辑" onclick="editCells('${cycleCells.id}','${cycleCells.name}');" style="margin-bottom:-2px;"/></a>
			      	<a href="#"><img src="${request.contextPath}/img/icon_del.gif" alt="删除"  onclick="deleteCells('${cycleCells.id}');" style="margin-bottom:-2px;margin-right:2px;"/></a>
		    	</legend>
		      	<div class="period_div">
		      		<div>
			      		<table id="cellTable${cycleCells.id}">
			      		<tr>
			      			<td>
		      					<div class='form_' id='form_${cycleCells.id}'>
		      						<form id="addForm_${cycleCells.id}"  action="" method="post" style="border:0;margin:0;padding:0;">
				      				<table>
				      					<tr>
					      				<td>
					      					<input  type="text"  ztype="text" id="input_${cycleCells.id}"   verify="项目名称|NotNull&&Length<50" />
					      				</td>
					      				<td>
					      					&nbsp;
					      				</td>
					      				<td>
					      					<div id="select_${cycleCells.id}"  ztype="select"   verify="NotNull">
					      					<ul>
											<#if (formMap.period?exists)&&(formMap.period.size()!=0) >
											<#list formMap.period as period>												
												<li val="${period.id}">${period.name}</li>												
											</#list>
											</#if> 	
											</ul>								        
										    </div>
					      				</td>
					      				<td style="text-align:right;">
											<input type="button" value="保存" ztype="button" onclick="saveCard('input_${cycleCells.id}','select_${cycleCells.id}','${cycleCells.id}','add','','addForm_${cycleCells.id}');"/>
										</td>
						      			<td style="text-align:left;">	
											<input type="button" value="取消" ztype="button" onclick="restartAll();" />				      				
					      				</td>
					      				</tr>
				      				</table>
				      				</form>
			      				</div>
		      				</td>
		      				<td>&nbsp;</td>
		      				<td>&nbsp;</td>
		      				<td>&nbsp;</td>		      						      		
			      		</tr>
						<#if (cycleCells.cards?exists)&&(cycleCells.cards.size()!=0) >
						<#list cycleCells.cards as cards>											      	
			      			<tr>
			      			<td>				      			
			      				<div class='span_item_' id='span_item_${cards.id}' onclick="editItem(${cards.id})">
			      					<table>
			      						<tr>
						      				<td style="width:450px;">
						      					<div class="span_item_name_">
								      				${cards.name}
												</div>
											</td>
							      			<td style="width: 300px;">
							      				<div class="span_item_period_">${cards.period.name}</div>
							      			</td>
						      			</tr>
						      		</table>						      																		      					
			      				</div>
			      					<div class='form_u_' id='form_u_${cards.id}'>
			      					<form id="editForm_${cards.id}"  action="" method="post" style="border:0;margin:0;padding:0;">
					      				<table>
					      					<tr>
						      				<td>
						      					<input type="text"  ztype="text"  id="${cards.id}_cid" value="${cards.name}"  verify="项目名称|NotNull&&Length<50" />
						      				</td>
							      			<td>
						      					&nbsp;
						      				</td>	
						      				<td>
						      					<div id="cpid_${cards.id}"  ztype="select"   verify="NotNull" >
						      					<ul>
												<#if (formMap.period?exists)&&(formMap.period.size()!=0) >
												<#list formMap.period as period>
													<#if period.id==cards.period.id>
														<li val="${period.id}" selected="selected">${period.name}</li>
													<#else>
														<li val="${period.id}">${period.name}</li>
													</#if>
												</#list>
												</#if> 
												</ul>									        
											    </div>
						      				</td>
						      				<td style="text-align:right;">
												<input type="button" value="保存" ztype="button" onclick="saveCard('${cards.id}_cid','cpid_${cards.id}','','edit','${cards.id}','editForm_${cards.id}');"/>
											</td>
						      				<td style="text-align:left;">	
												<input type="button" value="取消" ztype="button" onclick="restartAll();" />				      				
						      				</td>
						      				</tr>
					      				</table>
					      			</form>	
				      				</div>
			      				</td>
			      				<td>
			      				<#if !(cards.tools?exists)>	
			      					<a title="编辑详细信息" url="cycle-cell!showCard.ajax?formMap.id=${formMap.cycle.id}&formMap.cardId=${cards.id}" ztype="popUp"><img src="${request.contextPath}/img/card_dis.gif" class="pointImg" alt="编辑详细信息" style="margin-right:4px;"/></a>
			      				<#else>	
			      					<a title="编辑详细信息" url="cycle-cell!showCard.ajax?formMap.id=${formMap.cycle.id}&formMap.cardId=${cards.id}" ztype="popUp"><img src="${request.contextPath}/img/card.gif" class="pointImg" alt="编辑详细信息" style="margin-right:4px;"/></a>
			      				</#if>																						      																								
			      				</td>
			      				<td>					      				
									<#if cards.active=="1">											
										<img id="imgAble_${cards.id}" src="${request.contextPath}/img/icon_enable.gif" class="pointImg" alt="停用检修项目" onclick="changeAbleCard('${cards.id}',0);" style="margin-right:4px;"/>									
									<#else>
										<img id="imgAble_${cards.id}" src="${request.contextPath}/img/icon_disable.gif" class="pointImg" alt="启用检修项目" onclick="changeAbleCard('${cards.id}',1);" style="margin-right:4px;"/>										
									</#if> 				      								      								      																									
			      				</td>
			      				<td>
									<#if cards.active=="1">																					
										<img id="imgDisable_${cards.id}" src="${request.contextPath}/img/icon_edit.gif" class="pointImg" alt="编辑检修项目"  onclick="editItem(${cards.id});"/>
									<#else>										
										<img id="imgDisable_${cards.id}" src="${request.contextPath}/img/icon_del.gif" class="pointImg" alt="删除检修项目" onclick="deleteCard('${cards.id}');" />
									</#if> 				      				
			      				</td>				      				
			      			</tr>
						</#list>							
						</#if>				      			
			      		</table>
		      		</div>
				</div>
				<div class="preiod_div_num">共有 <span>${cycleCells.cards.size()}</span> 条</div>
			</fieldset>				
		</#list>
		</#if>  
	</div>
    <form name="addEditForm1" id="addEditForm1" action="" method="post" style="border:0;margin:0;padding:0;">
	    <div id="dialogDiv1" style="display:none">	
		    <div style="margin-top:10px;">		    	    	
		    	<table width="100%" style="height:30%;" border="0" cellpadding="0" cellspacing="5">
			    	<tr>
				    	<td style="text-align:right;">分区名称：</td>		    	
				    	<td><input type="text"  ztype="text"  id="name1" name="formMap.name1"  verify="分区名称|NotNull&&Length<50"/></td>
			    	</tr>
		    	</table>		    	
				<div class="form_submitDiv">
					<input type="button" value="保存" ztype="button" onclick="saveCells();"/>
					<input type="button" value="关闭" ztype="button" onclick="$('#dialogDiv1').dialog('close');" />
				</div>										
			</div>   
	    </div>
	    <input type="hidden" id="formMap.id" name="formMap.id" value="${formMap.cycle.id}"/>
	    <input type="hidden" id="formMap.cycleCellId" name="formMap.cycleCellId" value=""/>
	    <input type="hidden" id="formMap.periodId" name="formMap.periodId" value=""/>
	    <input type="hidden" id="formMap.cardId" name="formMap.cardId" value=""/>
	    <input type="hidden" id="formMap.cycleCellAddEdit" name="formMap.cycleCellAddEdit" value=""/>
	    <input type="hidden" id="formMap.name" name="formMap.name" value=""/>
    </form>	        		
	</body>
</html>