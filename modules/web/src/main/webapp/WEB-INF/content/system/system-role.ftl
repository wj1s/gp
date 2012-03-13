<html>
	<head>
		<link type="text/css" rel="stylesheet" href="${request.contextPath}/plugin/jsTreeV1.0rc2/css/apple/style.css" />
		<script type="text/javascript" src="${request.contextPath}/plugin/jsTreeV1.0rc2/jquery.jstree.js"></script>
		<script type="text/javascript" src="${request.contextPath}/plugin/jsTreeV1.0rc2/jquery.cookie.js"></script>
		<script type="text/javascript" src="${request.contextPath}/plugin/jsTreeV1.0rc2/jquery.hotkeys.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				var url = '${request.contextPath}/system/system!getRoleTree.action?timestamp='+new Date().getTime();				
				$("#theTree").jstree({//创建树开始 
			        "json_data" : {        
			            "ajax" : {
			                "url" : url
			            },            
			            "xsl" : "nest"
			        },
			        "themes" : {
			            "theme" : "apple",
			            "dots" : false,
			            "icons" : true           
			        },
			        "plugins" : [ "themes", "json_data" ]
			    });
				$("#theTree").delegate("li","click", function (e) {
					if ($(this).attr('roleId') != undefined){
						var roleId = $(this).attr('roleId');
						var url = '${request.contextPath}/system/system!getRolePopedom.blank?id=' + roleId;
						$('#content').load(url);
					}
				});
				$("#theTree").delegate("a","click", function (e){//设置点击标签链接打开、关闭node
					$("#theTree").jstree("toggle_node", this);
					return true;//当return false时，node链接不起作用。
				});
			});
		</script>
	</head>
	<body>
	    <div>
	    	<div style="float:left;width:30%;height:400px;overflow:auto;">
	    		<div id="theTree" style="padding: 10px 0px 0px 0px; "></div>
	    	</div>
	    	<div style="width:65%;margin-left:32%;" id="content">
	    		<strong>请选择角色!</strong>
	    	</div>
	    </div>
	</body>
</html>
