<html>
	<head>
		<style type="text/css">
			.sortable { list-style-type: none; margin: 0; padding: 0; width: 80px;}
			.duty-h4{margin:3px 0 0 0;height:20px; line-height:20px;font-size:12px;}
			.duty-ul{margin:0 0 3px 0;padding:0;}
			.duty-ul li{height:20px; line-height:20px;margin-right:0;margin-left:0;margin-top:1px;}
			.duty-change{background:#d0ebcb;border:1px #a5c49f solid;}
			#replaceDiv form,#addDelDiv form{margin:0;padding:0;}
		</style>
		<script language="JavaScript" type="text/javascript">
			//缓存
			var cache = {};
			//辨别单击双击事件的定时器
			var timerClick=null;
			//操作锁
			var lock = 'unlock';
			$(document).ready(function(){
				//给缓存初始化
				cache.missile = ''; //被换人员
				cache.target = ''; //换班人员

				//初始化替班选框
				$('#replaceDiv').dialog({
					resizable: false,
					autoOpen: false,
					height: '98',
					width: '220px',
					modal: false,
					close: function(event, ui) {
						lock = 'unlock';
					}
				});	

				//初始化增加删除选框
				$('#addDelDiv').dialog({
					resizable: false,
					autoOpen: false,
					height: 'auto',
					width: '220px',
					modal: false,
					close: function(event, ui) {
						lock = 'unlock';
					}
				});	

				//输入人员时的验证
				$('#addDelForm').form();
				$('#replaceForm').form();
				
			});

			//按部门时间查询之后的回调方法
			function onSuccess(data){
				$('#detail table tr').remove();
				if (data.result == true){
					var list = eval(data['data']);
					$('#noArrange').hide();
					$('#detail').show();
					
					//这段时间内有排班表
					if (list.length > 0){
						$('#saveDiv').show();
						$('#helpId').show();
						//运转数
						var dayPartCount = parseInt(list[0].dayPartCount);
						//生成排班表的时间
						var arrayTh = [];
						for(var i=0;i<list.length;i++){
							var hasIt = false;
							for(var j=0;j<arrayTh.length;j++){
								if (arrayTh[j] == list[i].date){
									hasIt = true;
									break;
								}
							}
							if (!hasIt){
								arrayTh.push(list[i].date);
							}
						}
						var html = '<tr><th>&nbsp;</th>';
						for(var i=0;i<arrayTh.length;i++){
							html += '<th>'+arrayTh[i]+'</th>';
						}
						html += '</tr>';
						$('#detail table').append(html);

						//生成每行
						var html = '';
						for(var k=0;k<dayPartCount;k++){
							html += '<tr><td>'+list[k].schName+'</td>';
							for(var i=0;i<arrayTh.length;i++){
								//下面是关键了，生成可拖动的人员grid
								var staffs = list[k + i * dayPartCount].staffOnDuty.split(',');
								html += '<td valign="top" ><div ><h4 class="ui-widget-header duty-h4" onclick="showAddDel(this)">';
								html += list[k + i * dayPartCount].group_name+'</h4><ul class="sortable duty-ul"  id="duty_'+list[k + i * dayPartCount].id+'">';
								for(var j=0;j<staffs.length;j++){
									html+= '<li class="ui-state-default" onclick="toChange(this);" ondblclick="showReplace(this)">'+staffs[j]+'</li>';
								}
								html += '</ul><div></td>';
							}
							html += '</tr>';
						}
						$('#detail table').append(html);
						$('#detail table').append('');
						//初始化默认排序框
						initSortable();
					}
				}else if (data.result != true){
					$('#saveDiv').hide();
					$('#helpId').hide();
					$('#noArrange').show();
				}
			}

			//初始化默认排序框
			function initSortable() {
				$('.sortable').sortable('destroy');
				$('.sortable').sortable({
					cursor:'pointer',
					containment:'parent',
					change: function(event, ui) {
						addMarker($(ui.item).parents('ul'),$(ui.item));
					}
				});
				$('.sortable').disableSelection();

				$('.sortable').bind('sortupdate', function(event, ui) {
					refreshUl($(ui.item).parents('ul:first'));
				});
			}

			//刷新班长和非班长的区别
			function refreshUl(ul) {
				var moniterName = $(ul).find('li:visible:first').html();
				if (moniterName.indexOf('*') == -1){
					$(ul).find('li:visible:first').html(moniterName + '*');
				}
				$(ul).find('li:visible:gt(0)').each(function(){
					var watcherName = $(this).html();
					if (watcherName.indexOf('*') != -1){
						$(this).html(watcherName.substring(0, watcherName.length - 1));
					}
				});
			}

			//代理换班切换方法，用于分离单击双击事件
			function toChange(li){
				timerClick = window.setTimeout(function(){toChangeReal(li);},220)
			}
            temp=false;
			//换班功能开启/关闭
			function toChangeReal(li){
				if ($(li).hasClass('ui-state-default')){
					changeOn($(li));
				}else if ($(li).hasClass('ui-state-replace')){
					changeOff($(li));
				}
			}

			//开启换班功能
			function changeOn(li){
				temp=false;
				if (lock == 'unlock'){
					lock = 'lock';
					if (!$('.sortable li').hasClass('ui-state-replace')){
						$(li).removeClass('ui-state-default').addClass('ui-state-replace');
						   if($(li).hasClass('duty-change'))
							{
							temp=true;
							$(li).removeClass('duty-change');
							}
						$('.sortable').sortable('option', 'disabled', true );
						//关闭替班功能
						$('.sortable li').unbind('dblclick');
						
						$(li).draggable({
							helper:'clone',
							cursor:'pointer'
						});
	
						//向缓存中记录被换人员
						var missile = $(li).html();
						cache.missile = missile.indexOf('*')==-1?missile:missile.substring(0, missile.length - 1);
						$('.sortable li').not($(li).siblings('li').andSelf()).droppable({
							drop:function(event, ui){
								if (cache.missile != ''){
									//向缓存中记录换班人员
									cache.target = $(this).html();
								}
								//更改换班人员的姓名
								$(this).html(cache.missile);
								$(ui.draggable).html(cache.target);
	
								//更新两个UL中的班长
								refreshUl($(this).parents('ul'));
								refreshUl($(ui.draggable).parents('ul'));
								//关闭换班功能
								changeOff($(ui.draggable));
								addMarker($(ui.draggable).parents('ul'),$(ui.draggable));
								addMarker($(this).parents('ul'),$(this));
							}
						});
	
						//关闭带被换人员的班次
						$('.sortable li:contains("'+cache.missile+'")').not($(li).siblings('li').andSelf()).each(function(){
							$(this).parents('ul').find('li').droppable('disable');
						});
						//关闭和被换人员同班的人员
						$(li).siblings('li').each(function(){
							var theOther = $(this).html();
							$('.sortable li:contains("'+theOther+'")').not($(li).siblings('li').andSelf()).each(function(){
								$(this).droppable('disable');
							});
						});
					}
				}
			}

			//关闭换班功能
			function changeOff(li){
				lock = 'unlock';
				$(li).removeClass('ui-state-replace').addClass('ui-state-default');
				if(temp==true)
				{
					$(li).addClass('duty-change');
				}
				//初始化默认排序框
				$('.sortable').sortable('option', 'disabled', false );
				//关闭接受类
				$('.sortable li').not($(li).siblings('li').andSelf()).droppable('enable');
				$('.sortable li').not($(li).siblings('li').andSelf()).droppable('destroy');
				//关闭拖动类
				$(li).draggable('option', 'helper', 'original');
				$(li).draggable('destory');
				$('.sortable li').not($(li).siblings('li').andSelf()).removeClass('ui-droppable');
			}

			//双击事件触发替班选框
			function showReplace(li) {
				if (lock == 'unlock'){
					lock = 'lock';
					//分离单击双击事件，这是精髓啊
					if(timerClick){ 
						window.clearTimeout(timerClick); 
					}
					var top = $(li).offset().top - $(document).scrollTop();
					var left = parseInt($(li).offset().left) + parseInt($(li).width()) + 5;
					$('#replaceDiv').dialog('option', 'position', [left,top] );
					$('#replaceDiv').dialog('open');
					$('#replaceDiv').data('theLi',li);
					$('#replaceDiv').data('theUl',$(li).parents('ul:first'));
				}
			}

			//替班操作
			function replace(){
				if ($('#replaceDiv').data('theLi') != undefined && $('#replaceForm').data('tjForm').checkSubmit()){
					var li = $('#replaceDiv').data('theLi');
					$(li).html($('#replaceName').val());
					refreshUl($(li).parents('ul'));
					$('#replaceDiv').dialog('close');
					$('#replaceName').val('');
					addMarker($(li).parents('ul'),$(li));
				}
			} 

			//单击班组条弹出框的方法
			function showAddDel(h){
				if (lock == 'unlock'){
					lock = 'lock';
					var theUl = $(h).siblings('ul:first');
					var top = $(h).offset().top - $(document).scrollTop();
					var left = parseInt($(h).offset().left) + parseInt($(h).width()) + 5;
					$('#addDelDiv').unbind('dialogclose');
					$('#addDelDiv').bind('dialogclose',function(event, ui) {
						$('#addName').val('');
						$('#delStaffDiv').droppable('destroy');
						$('.sortable').sortable('option', 'disabled', false );
						$(theUl).find('li').draggable('destroy');
						$(theUl).find('li:not(:visible)').sortable('destroy');
						$(theUl).find('li:not(:visible)').remove();
						lock = 'unlock';
					});
					$('#addDelDiv').dialog('option', 'position', [left,top] );
					$('#addDelDiv').dialog('open');
					//用来保存新增删除时哪个班的 Ul
					$('#addDelDiv').data('theUl',theUl);
					//用来保存新增删除的这个班有多少个值班人员
					$('#addDelDiv').data('staffsCount',$(theUl).find('li').length);

					//给用来接收删除的DIV注册接收功能
					$('#delStaffDiv').droppable({
						drop: function(event,ui) {
							var theUiId = $('#addDelDiv').data('theUl').attr('id');
							$(ui.draggable).attr('alreadyDel','alreadyDel').hide();
							refreshUl($('#'+theUiId));
							var staffsCount = $('#addDelDiv').data('staffsCount');
							if($('#'+theUiId).find('li:hidden').length == parseInt(staffsCount,10) - 1){
								$(ui.draggable).parents('ul').find('li:visible').draggable('destroy');
							}

							addMarker($('#'+theUiId));
						}
					});

					//关闭其他UL的sort功能
					$('.sortable').sortable('option', 'disabled', true );
					//开启本UL的drag功能，只剩一个值班员的时候不能删除
					if ($(theUl).find('li').length > 1){
						$(theUl).find('li').draggable({
							helper:'clone',
							cursor:'pointer',
							zIndex: 10
						});
					}
				}
			}

			//添加操作
			function addStaff(){
				if ($('#addDelDiv').data('theUl') != undefined && $('#addDelForm').data('tjForm').checkSubmit()){
					var ul = $('#addDelDiv').data('theUl');
					var html = '<li class="ui-state-default duty-change"  onclick="toChange(this);" ondblclick="showReplace(this)">'+$('#addName').val()+'</li>';
					$(ul).append(html);
					$(ul).find('li:last').draggable({
						helper:'clone',
						cursor:'pointer',
						zIndex: 10
					});
					$('#addName').val('');
					addMarker($(ul));
				}
			}

			//添加改动过的UL标记
			function addMarker(ul,li) {
				$(ul).attr('hasChange','hasChange');
				$(li).addClass('duty-change'); 
			}

			//提交快速排班信息
			function submitAllUl() {
				var changeDetails = [];
				$('#detail table ul[hasChange="hasChange"]').each(function(i){
					var detail = {};
					detail.id =  $(this).attr('id').substring(5);
					detail.watchers = [];
					$(this).find('li:visible').each(function(){
						if ($(this).html().indexOf('*') != -1){
							detail.moniter = $(this).html().substring(0, $(this).html().length - 1);
						}else {
							var watcher = {};
							watcher.watcher = $(this).html();
							detail.watchers.push(watcher);
						}
					});
					changeDetails.push(detail);
				});
				$.ajax({
					type: 'POST',
					url: '${request.contextPath}/duty/duty!saveQuickChangeDetail.action',
					data: {changeDetails:$.toJSON(changeDetails)},
					success: function(data){
						if(data.result == true){
							tjAlert("保存成功");
							window.location.href = '${request.contextPath}/duty/duty!show.action';
						}
					}
				});
			}

			//验证是否不能添加已经存在的值班员
			function validatePerson1(target) {
				var name = $(target).val();
				var flag = true;
				$('#addDelDiv').data('theUl').find('li:visible').each(function(){
					var existName = $(this).text();
					if (existName.indexOf('*') != -1){
						existName = existName.substring(0, existName.length - 1);
					}
					if (name == existName){
						flag = false;
					}
				});
				return flag;
			}

			//验证是否不能添加已经存在的值班员
			function validatePerson2(target) {
				var name = $(target).val();
				var flag = true;
				$('#replaceDiv').data('theUl').find('li:visible').each(function(){
					var existName = $(this).text();
					if (existName.indexOf('*') != -1){
						existName = existName.substring(0, existName.length - 1);
					}
					if (name == existName){
						flag = false;
					}
				});
				return flag;
			}
		</script>
	</head>
	<body>
         <div>
         	<form ztype="form" action="${request.contextPath}/duty/duty!quickChangeDetail.action" id="myForm">
         	<span class="work_clew_td" style="color:black;"><img src="${request.contextPath}/img/info.png"
								style="margin-bottom: -4px;" />只提供从某天开始7天内的调班功能</span>
	            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="work_info_table">
	                <tr>
	                    <th colspan="4">
	                        &lt;&lt;部门/日期选择&gt;&gt;
	                    </th>
	                </tr>
                  	<#if depts?exists && (depts?size>0)>
						<tr>
						    <td align="right">
						        <strong>请选择您要替/换班的部门：</strong>
						    </td>
						    <td align="left">
						    	<div id="dpId" name="dpId" ztype="select" verify="NotNull">
						    		<ul>
						    			<#list depts as dept>
						    				<li val="${dept.id}">${dept.name}</li>	
						    			</#list>
						    		</ul>
						    	</div>
						    </td>
						    <td align="right">
						    	<strong>请选择开始时间：</strong>
						    </td>
						    <td>
						    	<input type="text" ztype="date" name="startDate" id="startDate" value="${curDate?string('yyyy-MM-dd')}" znow="startnow" verify="开始时间|NotNull"/>
						    </td>
						</tr>
		           	<#else>
		           		<tr>
	                       <td align="right">
	                           <strong>请选择您要替/换班的部门：</strong>
	                       </td>
	                       <td align="left" colspan="3">
	                       	登录用户没有任何值班的部门权限！
	                       </td>
	                   </tr>
		           	</#if>
		           	<tr>
						<td colspan="4" align="right">
							<button ztype="button" type="submit">查询</button>
						</td>
					</tr>
					<tr id="noArrange" style="display: none">
						<td colspan="8">
							<strong>一周内没有任何排班</strong>
						</td>
					</tr>
	            </table>
	      	</form>
        </div>
		<div style="text-align: center; width: auto;">
			<div style="display:none;float:left;" id="detail">
				<table border="0" cellpadding="0" cellspacing="0" class="work_info_table" align="center">
					<tr></tr>
				</table>
				<div id="saveDiv" style="display:none;clear:both;text-align: right">
				<button ztype="button" type="button" onclick="submitAllUl();">保存</button>
				</div>
			</div>
			<div style="display:none;float:left;width:240px;line-height:1.8em;padding-left:8px;font-size:12px;text-align: left;" id="helpId">

<span style="font-weight:bold;color:#f60;"><img src="${request.contextPath}/img/info.png"
								style="margin-bottom: -4px;" />调整当班值班长：</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;鼠标选中如“张三”移动到班组首位置，变为“张三*”即可;<br/>
                               <span style="font-weight:bold;color:#f60;"><img src="${request.contextPath}/img/info.png"
								style="margin-bottom: -4px;" />班组增加或删除人员：</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;单击班组，输入本部门的人员增加到班组中；将当前班组已有的人员移到删除区中，即可删除;<br/>
                               <span style="font-weight:bold;color:#f60;"><img src="${request.contextPath}/img/info.png"
								style="margin-bottom: -4px;" />换班：</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;单击选中人员“张三”，可左右挪到非本身班组的任何班组中。<br/>
								<span style="font-weight:bold;color:#f60;"><img src="${request.contextPath}/img/info.png"
								style="margin-bottom: -4px;" />替班：</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;双击选中人员“张三”，再输入替班人员。

			</div>
			
		</div>
		
		
		<!-- 替班和添加删除的DIV -->
		<div id="replaceDiv" style="display:none;" title="选择替班人员">
			<div>
				<form id="replaceForm">
					<input type="text" ztype="auto" zurl="${request.contextPath}/baseinfo/person!autocompleteAjax.action?funModule=duty" id="replaceName" name="empName" verify="人员|NotNull&&Fun={val:'validatePerson2', msg:'本班组已经存在该值班员!',delay:true}" />
					<div style="left:0;bottom:0; text-align: right;">
			    		<button ztype="button" type="button" onclick="replace();">替换</button>
					</div>
				</form>
			</div>
		</div>
		<div id="addDelDiv" style="display:none;z-index:100;" title="添加/删除人员">
			<div>
				<form id="addDelForm">
					<input type="text" ztype="auto" zurl="${request.contextPath}/baseinfo/person!autocompleteAjax.action?funModule=duty" id="addName" name="empName" verify="人员|NotNull&&Fun={val:'validatePerson1', msg:'本班组已经存在该值班员!',delay:true}"/>
					<div style="left:0;bottom:0; text-align: right;">
			    		<button ztype="button" type="button" onclick="addStaff()">添加</button>
					</div>
				</form>
			</div>
			<hr>
			<div id="delStaffDiv" style="width: 200px;height: 62px;border:1px #999 solid;">
				<div style="border:1px #ccc solid; 194px;height: 56px;padding:2px;">拖到此处即可删除人员</div>
			</div>
		</div>
	</body>
</html>
