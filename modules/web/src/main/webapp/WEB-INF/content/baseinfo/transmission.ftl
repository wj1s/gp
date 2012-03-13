<html>
<head>
<script language="JavaScript" type="text/javascript">
	//对于某一个地球站，传输方式都是固定的，就暂时不开放增删改查了
			$(document).ready(function(){	
				$("#tab1").tab(1,0,320,0,0);
		
				var colModel=[{name:'id',header:"id",sortable:true,editable:false,hidden:true},
					{name:'transMode',header:"名称",sortable:true,editable:true,verify:"NotNull"}]
			
				
			  	$('#transmission-show').grid({name:'业务传输方式',colModel:colModel,data:data2,crudType:'${crudType?if_exists}'});
			});
		</script>
</head>
<body>
    <!-- 导航结束 -->
    <!-- 标签开始 -->
    <div class="tab" id="tab1">
        <ul> 
            <li num=3>节目源传输</li>
        </ul>
       	<div>
			<div id="transmission-show"></div>
        </div>
    </div>
    <!-- 标签结束 -->
</body>
</html>
