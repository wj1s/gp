/*
 *全选全不选
*/
function allSelect(e, itemName){//e--全选框的对象  itemName--多选名字
	 var aa = document.getElementsByName(itemName);//复选框的数组对象 
	 if(aa==null){//判断一下复选框是否存在
		return ;//返回
	 }//判断结束
	 for (var i=0; i<aa.length; i++){//循环并且将 全选框的状态加载到每个复选框上
	 	if(aa[i].disabled)continue;	 		
	 	aa[i].checked = e.checked; //得到那个总控的复选框的选中状态
	 }//循环结束
}
/*
 *每个单选click事件
*/
function itemSelect(e, AllId){//e--每个选框的对象  AllId--全选框的id
  var all = document.getElementById(AllId);//通过id获取全选按钮对象
  if(!e.checked){//如果当前多选框没有选中
	  all.checked = false;//全选框也不选中
  }else{//当前多选框处于选中状态
    var aa = document.getElementsByName(e.name);//获取复选框的数组对象 
	for (var i=0; i<aa.length; i++){//循环并且判断 
	  if(aa[i].disabled)continue;	 
	  if(!aa[i].checked){//如果存在没有选中的多选框 
	      return;//返回
	   }//判断结束
	}//循环结束
    all.checked = true;//如果所有的都选中，则将全选框设置为选中
  }
}
/*
 *判断是否是一个也没选中
*/
function checkSelectNone(itemName){//itemName--多选名字
	 var aa = document.getElementsByName(itemName);//复选框的数组对象 
	 if(aa==null){//判断一下复选框是否存在
		return false;//一个也没选返回false
	 }//判断结束
	 for (var i=0; i<aa.length; i++){//循环每个复选框
	 	if(aa[i].checked){//如果某个选中了
			return true;//返回true
		} //判断结束
	 }//循环结束	
	 return false;//返回一个也没选 
}