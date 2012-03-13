<html>
	<head>
		<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/jquery-zTree-v3.0-beta/css/zTreeStyle/zTreeStyle.css" />	
		<script type="text/javascript" src="${request.contextPath}/plugin/jquery-zTree-v3.0-beta/js/jquery.ztree.core-3.0.js"></script>
		<script type="text/javascript" src="${request.contextPath}/plugin/jquery-zTree-v3.0-beta/js/jquery.ztree.excheck-3.0.js"></script>		
		<script type="text/javascript">
			var $depts;
			var $emps;
			var $role_Persons;
			var zNodes =[
			 			{ id:-1, pId:-2, name:"选择菜单", open:true,nocheck:true}			 			
			 		];
			var setting = {
				check: {
					enable: true,
					chkboxType : { "Y":'ps', 
						"N":'ps'
					}
				},
				data: {
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "pId"
					}
				},
				callback: {
					onClick: zTreeOnClick
				}				
			};	
			function zTreeOnClick(event, treeId, treeNode){
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				if(!treeNode.nocheck){
					if(treeNode.checked){
						treeObj.checkNode(treeNode, false, true);
					}else{
						treeObj.checkNode(treeNode, true, true);
					}
				}
			}
			function getSelectCheckBox(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getCheckedNodes(true);
				var popedomInRole = "";
				for(var itemId in nodes){
					popedomInRole+=nodes[itemId].id+",";
				}
				document.getElementById('popedomInRole').value = popedomInRole.substring(0,popedomInRole.length-1);	
				//alert(document.getElementById('popedomInRole').value );
			}			
			$(document).ready(function(){				
				load_popeDomView();
				//$.fn.zTree.init($("#treeDemo"), setting);
				$depts=$("#dpId");
				$emps=$("#empId");
				$role_Persons=$("#rolePersons");
				$('#tab1').tab(1,0,410,0,0);
				$("#tab1").showTab(0);	
				$('#rolePersonForm').form();
				$('#rolePersonForm').init_style();
				$('#rolePersonForm').ajaxForm({
					type: "post",
					dataType: 'json',
					beforeSubmit: validateForm,
					success: function(data){
						if (data['result'] == true){
							tjAlert("保存成功!");
						}else {						
							tjAlert("保存失败!"); 						
						}
					}
				});		
				$('#rolePopForm').form();
				$('#rolePopForm').init_style();
				$('#rolePopForm').ajaxForm({
					type: "post",
					dataType: 'json',
					beforeSubmit: validateRoleForm,
					success: function(data){
						document.getElementById("roleButton").disabled=false;
						if (data['result'] == true){
							tjAlert("保存成功!");
						}else {						
							tjAlert("保存失败!"); 						
						}
					}
				});
			});
			//保存		
			function validateRoleForm(){
				document.getElementById("roleButton").disabled=true;
				return true;
			}	
		       //加载权限
            function load_popeDomView(){
				var roleId='${modleRole.id}';				
				$.getJSON('${request.contextPath}/system/system!popCheckBox.action?rd='+Math.random(),
					{'roleId':roleId},	
					function(data){
						$.fn.zTree.init($("#treeDemo"), setting, data);						
					});		
            }			
	       //实现部门与部门人员之间的联动；
            function reload_emps(){
				var dpId=$depts.val();		
				$emps.empty();
				$.getJSON('${request.contextPath}/baseinfo/dept!getPersonAjax.action?rd='+Math.random(),
					{'id':dpId},	
					function(data){						
						if (data.result){
							var array = eval(data['data']);
							for(var i in array){
								var $person=$(array[i]);
								var person_label=$(array[i]).attr("name");
								var person_value=$(array[i]).attr("value");
								//判断这个人员是否已经在右侧列表中存在
								var $rp_options=$role_Persons.find("option");
								var already_in_rp_options=false;
								$rp_options.each(function(){
									if($(this).val()==person_value){
										already_in_rp_options=true;
									}
								})
								//如果这个人还没有此角色的话，添加选项
								if(!already_in_rp_options){
									$emps.append($("<option value="+person_value+">"+person_label+"</option>"));
								}
							}
						}						
					});		
            }
            //增加人员；
            function addItem(){
				 //将左侧被选人员添加到右侧
	             $role_Persons.append($emps.find("option:selected"));
				 //设置数据
				 setPsersInRole();
            }
            
            //删除人员选择筐中的人员；
            function deleteItem(){
                $role_Persons.find("option:selected").each(function(){
					//分别判断右侧被选的每个人员
					var $rp_selected=$(this);
					//根据当前所选的机房，查询本机房的人中是否包含当前选择的这个右侧被选人员
					var in_the_dept=false;
					var dpId=$depts.val();
					$.getJSON('${request.contextPath}/baseinfo/dept!getPersonAjax.action?rd='+Math.random(),
							{'id':dpId},	
							function(data){
								if (data.result){
									var array = eval(data['data']);
									for(var i in array){
										var person_value=$(array[i]).attr("value");
										if(person_value==$rp_selected.val()){
											in_the_dept=true;
											break;
										}										
									}								
								}
								//如果包含的话，将右侧本被选人员添加到左侧，如果不包含，直接在右侧移除本选项					
								if(in_the_dept){
									$emps.append($rp_selected);
								}else{
									$rp_selected.remove();
								}
								//设置数据
				                setPsersInRole();
							});						
				});
            }
            //取得人员的Id字符串，中间用“,”间隔
            function setPsersInRole(){
                var personsInRole = new Array();
                var obj = document.rolePersonForm;
                for (var i = 0; i < obj.rolePersons.length; i++) {
                    personsInRole.push(obj.rolePersons[i].value);
                }
                obj.personsInRole.value = personsInRole.toString();
            }
			//保存		
			function validateForm(){
				return true;
			}					
		</script>
	</head>
	<body>
		<fieldset>
			<legend>角色信息:${modleRole.desc}</legend>
			<div>
				<div class="tab" id="tab1">
			        <ul> 
			            <li>配置人员</li>
			            <li>配置权限</li>
			        </ul>
			        <div>
                    <form id="rolePersonForm" name="rolePersonForm" action="${request.contextPath}/system/system!roleSavePerson.ajax" method="post">
                        <input type="hidden" name="roleId" value="${modleRole.id}"/>
						<input type="hidden" name="personsInRole"/>
                        <table border="0" cellpadding="0" cellspacing="0" class="resultTable" align="center">
                            <tr>
                                <td align="center" valign="bottom">
                                    <strong>选择部门</strong>
                                </td>
                                <td align="center">
                                    <strong>选择人员</strong>
                                </td>
                                <td rowspan="2">
                                	<input type="button" ztype="button"  value="添加 &gt;&gt;" onclick="JavaScript:addItem();" />
                                    <br/>
                                    <br/>
									<input type="button" ztype="button" value="删除&nbsp;&lt;&lt; " onclick="JavaScript:deleteItem();" />
                                </td>
                                <td align="center">
                                    <strong>本角色人员</strong>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" valign="bottom">
                                	<select id="dpId"  style="width:120px" onchange="reload_emps();" size="10">
                                        <option value="" selected="selected">-未选择-</option>
										<#if (deptList?exists)&&(deptList.size()!=0) >
										<#list deptList as dept>
											<option value="${dept.id}">${dept.name}</option>											
										</#list>
										</#if>                                         
                                    </select>
                                </td>
                                <td>
                                <select id="empId" class="input_text" style="width:120px" multiple="multiple" size="10"/>
                                </td>
                                <td>
                                	<select id="rolePersons"  class="input_text" style="width:120px" multiple="multiple" size="10">                                        
										<#if (personList?exists)&&(personList.size()!=0) >
										<#list personList as person>
											<option value="${person.id}">${person.name}</option>											
										</#list>
										</#if>                                         
                                    </select>
                                </td>
                            </tr>
                        </table>
                        <div style="text-align:center;margin-top:10px;">
                            <button ztype="button" type="submit" >保存</button>
                        </div>
                    </form>
			        </div>
			        <div>
                    <form id="rolePopForm" name="rolePopForm" action="${request.contextPath}/system/system!roleSavePopedom.ajax" method="post">
                        <input type="hidden" name="roleId" value="${modleRole.id}"/>
                        <input type="hidden" id="popedomInRole" name="popedomInRole" />
						<div style="height:320px; width: 95%; overflow-y: auto;">
							<ul id="treeDemo" class="ztree"></ul>
                        </div>
                        <div style="text-align:center;margin-top:10px;">
                            <input ztype="button" id="roleButton" type="submit" onclick="getSelectCheckBox();"  value="保存" />
                        </div>
                    </form>
			        </div>
		        </div>
			</div>
		</fieldset>
	</body>
</html>
