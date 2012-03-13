<html>
<head>
	<script type='text/javascript' src="${request.contextPath}/js/repair/selAll.js"></script>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
			//设置验证form
			$('#addEditForm2').form();
			//设置现设tab页签
			$("#tab1").tab(1,0,340,${cycShowNum},0);	
			//点击添加周期表事件	
			$("#addPeriod").click(function(){
				$('#toAddCycle').trigger('click');
			});	
			//定义对话框		
			$('#dialogDiv1').dialog({
				resizable: false,//是否可以重新定义大小
				autoOpen: false,//是否自动打开
				height: 'auto',//是否自动高度
				width: 500,//宽度500
				modal: true,//模态阴影
				title:'更改别名'	//标题					
			});							
		});
		//更改周期表名称
		function changeNames(num){
			cleanRows();//清理
			var obj0=document.getElementById('changeNameTable');//获取table对象
			var obj5=document.getElementById('cycShowNum1');
			obj5.value=num;
			var obsStr="formMap.autoIDActive"+num;//字符串
			if(!checkSelectNone(obsStr)){//判断是否一个都没选		
				tjAlert('请选择周期表!');//提示信息								
				return;
			}
			var aa = document.getElementsByName(obsStr);//获取勾选值
			for (var i=0; i<aa.length; i++){
				if(aa[i].checked){				
					var id_a=document.getElementById(aa[i].value+'_a');
					var length=obj0.rows.length;
					var tr=obj0.insertRow(length);
					var td=document.createElement("td"); 
					var td1=document.createElement("td"); 
					td.innerText=id_a.innerText;
					td1.innerHTML='<input type="text" ztype="text" value="'+id_a.innerText+'" name="formMap.newName"  id="'+aa[i].value+'" verify="更改后的名称|NotNull&&Length<50"/>';					
					tr.appendChild(td);
					tr.appendChild(td1);						
				}
			}
			var obj1=$('#dialogDiv1');	//不能用document.getElementById						
			obj1.init_style();		
			$('#addEditForm2').form();									
			$('#dialogDiv1').dialog('open');//弹出编辑框
		}
		//保存周期表名称的编辑 
		function saveChangeNames(){
			var check=$('#addEditForm2').data('tjForm').checkSubmit();
			if(!check)return;
			var obj0=document.getElementById('changeNameTable');			
			var obj1=document.getElementById('formMap.newNameAndIds');
			var obj2=document.getElementById('addEditForm2');			
			var aa = document.getElementsByName("formMap.newName");
			var rtnStr="";
			for (var i=0; i<aa.length; i++){				
				if(i==0){
					rtnStr=rtnStr+aa[i].id+"!1642in!"+aa[i].value;
				}else{
					rtnStr=rtnStr+"!1643in!"+aa[i].id+"!1642in!"+aa[i].value;
				}				
			}
			obj1.value=rtnStr;			
			obj2.action="${request.contextPath}/repair/cycle!edit.action";				
			obj2.submit();	
		}
		//清理周期表编辑界面
		function cleanRows(){
			var obj=document.getElementById("changeNameTable");
			var rowNum=obj.rows.length;
			for(var i=rowNum-1;i>0;i--){
				obj.deleteRow(i);
			}
		}
		//设置启用停用周期表
		function changeAble(num,ableNum,cycShowNum){
			var obj2=document.getElementById('addEditForm');	
			var obj3=document.getElementById('formMap.tabNum');
			var obj4=document.getElementById('formMap.ableNum');	
			var obj5=document.getElementById('cycShowNum0');
			var obsStr="";
			if(ableNum==0){
				obsStr="formMap.autoIDActive";
			}else{
				obsStr="formMap.autoIDUnActive";
			}		
			if(!checkSelectNone(obsStr+num)){//判断是否一个都没选		
				tjAlert('请选择周期表!');								
				return;
			}
			obj3.value=num;
			obj4.value=ableNum;
			obj5.value=cycShowNum;
			obj2.action="${request.contextPath}/repair/cycle!setAble.action";				
			$('#addEditForm').submit();		
		}
		//删除周期表
		function deleteCycle(num){
			var obsStr="formMap.autoIDUnActive"+num;
			var obj2=document.getElementById('addEditForm');
			var obj3=document.getElementById('formMap.tabNum');
			var obj5=document.getElementById('cycShowNum0');
			obj5.value=num;
			if(!checkSelectNone(obsStr)){//判断是否一个都没选		
				tjAlert('请选择周期表!');								
				return;
			}			
			tjConfirm("确定删除选中的周期表？",function(flag){
				if (!flag){					
					return;
				}else{
					var ids=getDeleteIds(num);
					
					//ajax判断是否可以删除				
					$.ajax({
						  type: 'POST',
						  url: '${request.contextPath}/repair/cycle!checkDelete.action',
						  dataType: "json",
						  data: {ids:ids},
						  async: false,
						  success: function(data){						  	 
							  if (data.result == false){
							  		tjAlert("存在检修记录不能删除！");	
							  		return ;
							  }else{
								obj3.value=num;
								obj2.action="${request.contextPath}/repair/cycle!delete.action";				
								$('#addEditForm').submit();								  	
							  }						  
						  }
					});													
				}
			});							
		}
		//搜集需要删除的id
		function getDeleteIds(num){
			var obsStr="formMap.autoIDUnActive"+num;//字符串			
			var rtnStr="";
			var aa = document.getElementsByName(obsStr);//获取勾选值
			for (var i=0; i<aa.length; i++){				
				if(aa[i].checked){					
					if(rtnStr==""){
						rtnStr=aa[i].value;
					}else{
						rtnStr=rtnStr+"!1538ck!"+aa[i].value;
					}
				}				
			}			
			return rtnStr;			
		}
		//查看周期表明细
		function seeDetailes(num,id){
			var obj2=document.getElementById('addEditForm');	
			var obj3=document.getElementById('formMap.tabNum');
			var obj4=document.getElementById('formMap.id');
			obj3.value=num;
			obj4.value=id;
			obj2.action="${request.contextPath}/repair/cycle-cell!show.action";
			obj2.submit();	
		}
	</script>
</head>
<body>
    <div align="right">
		<a id="toAddCycle" ztype="popUp" title="新增检修周期表" url="${request.contextPath}/repair/cycle!getDepartment.ajax"></a><button  type="button"  ztype="button" id="addPeriod">新增检修周期表</button>
	</div>
	<form id="addEditForm" name="addEditForm" action=""  method="post"  style="border:0;margin:0;padding:0;">
    <div class="tab" id="tab1">    
        <ul> 
	        <#if formMap.cycleList.size() != 0>
	        	<#list formMap.cycleList as cycleList >
		            <li>${cycleList.cycleDepartment.name}</li>			            	           
	            </#list>
	        <#else>
	       	 	<li>检修周期表</li>
	        </#if>   
        </ul>        
        <#if formMap.cycleList.size() != 0>
        	<#list formMap.cycleList as cycleList >
	        	<div >
				  <fieldset class="fieldset">
					    <legend>
					      	<img src="${request.contextPath}/img/icon_run.gif" style="margin-bottom:-10px;margin-right:3px;"/>在用
					    </legend>
					    <div>
						    <#if cycleList.cycleListActive.size() != 0>
						    	<#list cycleList.cycleListActive as cycleListActive>
						    		<div class="li_result">
						    			<input type="checkbox"  name="formMap.autoIDActive${cycleList_index}" onclick="itemSelect(this,'selectAllActive${cycleList_index}');" value="${cycleListActive.id}" />
						    			<a id="${cycleListActive.id}_a" href="javaScript:seeDetailes(${cycleList_index},'${cycleListActive.id}');">${cycleListActive.name}</a>
						    		</div>
						    	</#list>									
							</#if>
					    	<div class="li_button">
					    		<input type="checkbox" onclick="allSelect(this,'formMap.autoIDActive${cycleList_index}')" id="selectAllActive${cycleList_index}"/> 全选
					    		<button  type="button"  ztype="button" onclick="changeAble(${cycleList_index},0,${cycleList_index});">停用</button>
					    		<button  type="button"  ztype="button" onclick="changeNames(${cycleList_index});">更改别名</button>					    						    		
					    	</div>
					    </div>
				    </fieldset>
				    <fieldset class="fieldset">
					    <legend>
						      	<img src="${request.contextPath}/img/icon_stop.gif" style="margin-bottom:-10px;margin-right:3px;"/>停用
						</legend>
					    <div>
						    <#if cycleList.cycleListUnActive.size() != 0>
						    	<#list cycleList.cycleListUnActive as cycleListUnActive>
						    		<div class="li_result">							    			
						    			<input type="checkbox"  name="formMap.autoIDUnActive${cycleList_index}" onclick="itemSelect(this,'selectAllUnActive${cycleList_index}');" value="${cycleListUnActive.id}"  />
						    			<a href="javaScript:seeDetailes(${cycleList_index},'${cycleListUnActive.id}');">${cycleListUnActive.name}</a>
						    		</div>
						    	</#list>									
							</#if> 
					    	<div class="li_button">
					    		<input type="checkbox" onclick="allSelect(this,'formMap.autoIDUnActive${cycleList_index}')" id="selectAllUnActive${cycleList_index}"/> 全选						    		
					    		<button  type="button"  ztype="button" onclick="changeAble(${cycleList_index},1,${cycleList_index});">启用</button>
					    		<button  type="button"  ztype="button" onclick="deleteCycle(${cycleList_index});">删除</button>
					    	</div>
					    </div>
				    </fieldset>		        		
	        	</div>          
            </#list>
        <#else>
	        <div>
	        	<div id="code">
	        		<div id="content">
				  		<p>>> 欢迎使用检修周期表管理功能，您可以通过检修周期表管理功能对部门内各系统及系统中的分区项目进行管理： </p>
				  		<p>
				  			<span style="color: blue">检修周期表管理</span><br/>
				  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您可以通过检修系统管理功能为机房添加检修周期表。<br/>
				  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;周期表添加后，可以批量进行修改系统名称，停用，启用，删除操作。<br/>
				  			<span style="color: blue">分区项目管理</span><br/>
				  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系统创建后，可以通过单击周期表名称查看编辑系统的分区项目信息,完成分区项目的新增，编辑，停用，删除功能
				  		</p>
				  		<p>
		                特别提示:<br/>
		                1.如果建立分空区，只需在名称中输入一个空格即可，一个周期表只允许建立一个空分区<br/>
		                2.如果不想值班员在检修记录中添加某一周期表的项目，可停用此系统，再需要使用时启用即可；删除系统则将
		                数据完全从数据库中移除，所有涉及此周期表的历史记录将只以文本方式保存，无法进行某一项目的统计。<br/>
		                3.在检修周期表中，分区和项目是根据设备机型维护的，同一类机型在全站共用一套分区和项目，对某一种设备的分区项目调整都会同步到其他同类型的设备检修周期表中
		                </p>
					</div>
	        	</div>
	        </div>
        </#if>                    
    </div>
	    <input type="hidden" id="formMap.tabNum" name="formMap.tabNum"/>
	    <input type="hidden" id="formMap.ableNum" name="formMap.ableNum"/>
	    <input type="hidden" id="formMap.id" name="formMap.id"/>
	    <input type="hidden" id="formMap.showTabNum" name="formMap.showTabNum"/>
	    <input type="hidden" id="cycShowNum0" name="cycShowNum"/>	    
    </form>
    
	    <div id="dialogDiv1" style="display:none;">	
		    <div style="margin-top:10px;">		
		        <form name="addEditForm21" id="addEditForm2"   method="post"   action="" style="border:0;margin:0;padding:0;">	    	
			    	<table id="changeNameTable" style="height:60%;" class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">
				    	<tr>		    		
				    		<td>原名称：</td>	  
				    		<td>更改后的名称</td>
				    	</tr>			    				    	
			    	</table>
					<div class="form_submitDiv">
						<input type="button" value="保存" ztype="button" onclick="saveChangeNames();"/>
						<input type="button" value="关闭" ztype="button" onclick="$('#dialogDiv1').dialog('close');" />
					</div>
				    <input type="hidden" id="formMap.newNameAndIds" name="formMap.newNameAndIds"/>
				    <input type="hidden" id="formMap.showTabNum2" name="formMap.showTabNum"/>	
				    <input type="hidden" id="cycShowNum1" name="cycShowNum"/>			
				</form>										
			</div>
	    </div>    	
</body>
</html>
