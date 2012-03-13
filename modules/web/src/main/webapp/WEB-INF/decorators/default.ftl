<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>ABRS TMS 北京地球站</title>
<script language="JavaScript" type="text/javascript">
var locationUrl = "${request.contextPath}/Interface/";
var ctp = "${request.contextPath}";
</script>
<link type="text/css" rel="stylesheet" href="${request.contextPath}/css/style.css" />
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/tab/skin/office2010/jquery.tab.css" />
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/ddsmoothmenu/ddsmoothmenu.css" />
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/jqGrid-3.8/themes/ui.jqgrid.css" />
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/jquery-ui-1.8.4.custom/css/redmond/jquery-ui-1.8.4.custom.css"/>
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/tj/tjValidate/jquery.tj.validate.css"/>
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/tj/tjStyle/jquery.tj.style.css"/>
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/tj/tjGrid/grid.tjGrid.css"/>
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/select/skin/blue/jquery.select.css"/>
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/jquery-checkbox/jquery.checkbox.css"/>
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/autoComplete/jquery.autocomplete.css"/>
<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/qtip/jquery.qtip.css"/>
<script type="text/javascript" src="${request.contextPath}/plugin/jquery-ui-1.8.4.custom/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/jquery-ui-1.8.4.custom/js/jquery-ui-1.8.4.custom.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/jqGrid-3.8/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/jqGrid-3.8/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/tab/jquery.tab.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/ddsmoothmenu/ddsmoothmenu.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/tj/tjGrid/grid.tjGrid.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/tj/tjStyle/jquery.tj.style.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/tj/tjValidate/jquery.tj.validate.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/select/jquery.tj.select.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugin/Form/jquery.form.js"></script>
<script type='text/javascript' src="${request.contextPath}/plugin/jquery-checkbox/jquery.checkbox.js"></script>
<script type='text/javascript' src="${request.contextPath}/plugin/autoComplete/jquery.autocomplete.js"></script>
<script type='text/javascript' src="${request.contextPath}/plugin/qtip/jquery.qtip.js"></script>
<style type="text/css">
	stay-index {
		width:500px;
	}
</style>
<script language="JavaScript" type="text/javascript">
	$(document).ready(function(){
		var crudFlag = 1;
		//调用菜单方法
		ddsmoothmenu.init({
			mainmenuid: "smoothmenu1", //menu DIV id
			orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
			classname: 'ddsmoothmenu', //class added to menu's outer DIV
			//customtheme: ["#1c5a80", "#18374a"],
			contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
		});
		$('a,input[type="button"],input[type="submit"]').bind('focus',function(){
	        if(this.blur){ //如果支持 this.blur
	                this.blur();
	        };
		});
		$('li a[href!="#"]').click(function(){
			var arrayNavigation = [];
			var $target = $(this);
			var navMessage = '你现在所处的位置：<a href="#">首页</a> >> ';
			for(var i=0;i<3;i++){
				if ($target != undefined && $target.text() != ''){
					arrayNavigation.push($target.text());
					$target = $target.parents('li:eq(1)').find('a[href="#"]:first');
				}
			}
			for(var i=arrayNavigation.length-1;i>=0;i--){
				navMessage += arrayNavigation[i] + ' >> ';
			}
			navMessage = navMessage.substring(0, navMessage.length - 4);
			var hrefStr = $(this).attr('href');
			$('#curUrl').val(hrefStr);
			$('#crudFlag').val(crudFlag);
			$(this).attr('href','#');
			$('#navForm').attr('action',hrefStr);
			$('#navMessage').val(navMessage);
			$('#navForm').submit();
		});

		$('.home_index').click(function(){
			var navMessage = '你现在所处的位置：<a href="#">首页</a>';
			var hrefStr = $(this).attr('href');
			$(this).attr('href','#');
			$('#navForm').attr('action',hrefStr);
			$('#navMessage').val(navMessage);
			$('#navForm').submit();
		});

		$('#stayImg').qtip({
			position: {
			   my: 'top right',  // Position my top left...
			   at: 'bottom left', // at the bottom right of...
			   target: $('#innerImg') // my target
			},
			style:{
				classes: 'ui-tooltip-shadow ui-tooltip-blue',
				width:350
			},
            content: {
	            text:function(){
	            	var str = '';
					$.ajax({
						url: '${request.contextPath}/stay/stay-detail!showCurrent.action',
						type: "POST",
						async: false,
						dataType: "json",
						success: function(data){
							var list = eval(data['data']);
							var content = '';
							for(var i = 0 ; i < list.length ; i++){
								content += ' <fieldset style="margin-top:20px">';
								content += ' <legend>'+list[i]['deptName']+'</legend>';
								content += '<table width="100%" border="0" cellpadding="0" cellspacing="0" >';
								var personList = eval(list[i]['personList']);
								for(var j = 0; j < personList.length; j ++){
									content += '<tr>';
									content += '<td width="40%" align="center">'+list[i]['startTime']+'---'+list[i]['endTime']+'</td>';
									content += '<td width="60%" style="font-size:12px" align="left">'+personList[j]['person']+'</td>';
									content += '</tr>'
								}
								content += '</table>';
								content += ' </fieldset>';
							}
							if(list.length == 0){
								content += '<div style="font-weight:bold;font-size:14px">';
								content += '当前没有留守人员';
								content += '</div>';
							}
							str = content;
						}
					});
					return str;
	            },
                title: {
                   text: '当前留守人员',
                   button: '关闭'
                }
             },
             show: {
                 event: 'click',
                 effect: function(offset) {
                    $(this).fadeIn("normal");
                 }
             },
             hide: {
                event:false,
            	inactive: 3000
             }
		});


	})
</script>
${head}
</head>
<body>
<!-- 模板开始 -->
<div class="template"><!-- 上部开始 -->
	<div class="template_top">
		<div class="user_info">欢迎<span>${curUser.name}</span>登录本系统
			<a href="#" id="stayImg"><img src="${request.contextPath}/img/stay.png" style="margin-bottom: -10px;" id="innerImg"/></a>
			<a href="${request.contextPath}${loginType?if_exists}" >
				<img src="${request.contextPath}/img/logout.png" style="margin-bottom: -10px;" />
			</a>
			
		</div>
		<div class="template_top_menu">
			<!-- 菜单开始 --> 			
			<div id="smoothmenu1" class="ddsmoothmenu">
			<a href="${request.contextPath}/index/index!toIndex.action" class="home_index">首页</a>
			<ul>
			<#if (curMenu?exists)&&(curMenu.popedomViews.size()!=0)>
				<#list curMenu.popedomViews as pop><!--循环第一层 -->
					<#if pop.enable&&pop.personHas>
						<#if pop.url?exists>
						<li><a href="${request.contextPath}/${pop.url}">${pop.popeName}</a>
						<#else>
						<li><a href="#">${pop.popeName}</a>
						</#if>
						<#if (pop.popedomViews?exists)&&(pop.popedomViews.size()!=0)>
							<ul class="Second">
							<#list pop.popedomViews as pop2><!--循环第二层 -->
								<#if pop2.enable&&pop2.personHas>
									<#if pop2.url?exists>
									<li><a href="${request.contextPath}/${pop2.url}">${pop2.popeName}</a>
									<#else>
									<li><a href="#">${pop2.popeName}</a>
									</#if>
									<#if (pop2.popedomViews?exists)&&(pop2.popedomViews.size()!=0)&&(!pop2.position?exists||pop2.position?exists&&pop2.position!=4)>
										<ul class="Third">
										<#list pop2.popedomViews as pop3><!--循环第三层 -->
											<#if pop3.enable&&pop3.personHas>
												<#if pop3.url?exists>
												<li><a href="${request.contextPath}/${pop3.url}">${pop3.popeName}</a>
												<#else>
												<li><a href="#">${pop3.popeName}</a>
												</#if>
												</li>							
											</#if>										
										</#list>	
										</ul>										
									</#if>
									</li>							
								</#if>										
							</#list>	
							</ul>					
						</#if>	
						</li>						
					</#if>										
				</#list>
			</#if> 							
			</ul>
			<br style="clear: left" />
			</div>
		<!-- 菜单结束 -->
		</div>
	</div>
	<!-- 上部结束 -->
	<!-- 下部开始 -->
	<div class="template_middle">
		<div class="template_middle_content">
			<div class="nav"><#if navMessage?exists>${navMessage}<#else>当前页</#if></div>
		${body}
		</div>
		<form action="" id="navForm" method="post">
			<input type="hidden" id="navMessage" name="navMessage" value=""/>
			<input type="hidden" id="curUrl" name="curUrl" value=""/>
			<input type="hidden" id="crudFlag" name="crudFlag" value=""/>
		</form>
	</div>
<!-- 下部结束 -->
</div>
<!-- 模板结束 -->
</body>
</html>
