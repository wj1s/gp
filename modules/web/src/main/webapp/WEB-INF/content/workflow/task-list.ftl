<html>
<head>
	<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){	
				$("#tab1").tab(1,0,500,0,0);
				    var colModel=[{name:'id',header:"流程编号",sortable:true,editable:false,hidden:true},
								{name:'name',header:"流程描述",sortable:false,editable:false,width:400},
								{name:'start',header:"流程开始时间",sortable:false,editable:false},
								{name:'end',header:"流程结束时间",sortable:false,editable:false},
								{name:'state',header:"当前状态",sortable:false,editable:false,width:100}];
					$('#task-list-show0').grid({name:'待办业务',colModel:colModel,
						loadUrl:{content:'${request.contextPath}/workflow/task-list!taskList.action?taskType=0'},
						inputUrl:{content:'${request.contextPath}/workflow/task-execute.action',type:'redirect',buttonName:'执行任务'},
						viewUrl:{content:'${request.contextPath}/workflow/task-detail.action',type:'redirect',buttonName:'任务详情'},
						operation:{add:false,edit:true,del:false},gridType:'noSearch'
				    });
					$('#task-list-show1').grid({name:'已办业务',colModel:colModel,
						loadUrl:{content:'${request.contextPath}/workflow/task-list!taskList.action?taskType=1'},
						viewUrl:{content:'${request.contextPath}/workflow/task-detail.action',type:'redirect',buttonName:'任务详情'},
						operation:{add:false,edit:false,del:false},gridType:'noSearch'
				   });
			});
	</script>
</head>
<body>
 <!-- 标签开始 -->
     <div class="tab" id="tab1">
	        <ul>
	        		<li>待办任务</li>
	        		<li>已办任务</li>
	        </ul>
	        <div id="task-list-show0"></div>
            <div id="task-list-show1"></div>
	</div>
    <!-- 标签结束 -->
</body>
</html>