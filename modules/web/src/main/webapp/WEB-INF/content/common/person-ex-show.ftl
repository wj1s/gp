<html>
	<head>
		<script type="text/javascript">
		    var ids='${ids}'.split(',');
		    var names='${names}'.split(',');
			$(document).ready(function(){
				$('#formMap').form();
				$('#savePersonChange').click(function(){	
					var check=$('#formMap').data('tjForm').checkSubmit();
					if(!check)return;
					var namesT=$('#empNames').val();
					$.ajax({
						  type: 'POST',
						  url: '${request.contextPath}/common/person-ex!getUserIdsAjax.action',
						  dataType: "json",
						  data: {names:namesT},
						  async: false,
						  success: function(data){
							  updatePersons(data['ids'].split(','),data['names'].split(','));
					          $('#dialogDiv').dialog('close');	
						  }
					});							           
				});				
				$('button:contains("关闭")').click(function(){
					$('#dialogDiv').dialog('close');					
				});
			});			
		</script>
	</head>
	<body>	    
	<form id="formMap" action="#" method="post" style="border:0;margin:0;padding:0;">
	    <div style="margin-top:10px">		    	    	
	    	<table width="100%" border="0" cellpadding="0" cellspacing="5">
	    	<tr>
		    	<td style="text-align:right;">
		    	检修人员：
		    	</td>		    	
		    	<td>
		    	<input type="text" id="empNames"  name="empNames" value="${names}," style="width:320px" ztype="auto" zmode="multi" verify="检修人员|NotNull" />
		    	</td>
	    	</tr>
	    	</table>
			<div class="form_submitDiv">
				<button ztype="button" type="button" id ="savePersonChange">保存</button><button ztype="button" type="button">关闭</button>
			</div>										
		</div>	
		</form>    	    
	</body>
</html>
