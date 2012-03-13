<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page session="false" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.web.authentication.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.web.authentication.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ page import="org.springframework.security.web.authentication.AbstractProcessingFilter" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户登录</title>
<style type="text/css">
body input{
	font-family: "微软雅黑", Verdana, Geneva, sans-serif;
	font-size: 12px;
	color: #333;
}
body {
	background: #000;
	margin:0;
	text-align:center;
}
.errors
{
font-family: "微软雅黑", Verdana, Geneva, sans-serif;
color:#fc0;
padding:6px; 
border-bottom:1px #8990a2 dashed; 
background:#444b5f;
font-size: 11px;
}
img{border:0;}
div {margin:auto; padding:0;}
.login{
	background:url(img/login.jpg) no-repeat;
	width:662px;
	height:511px;
	margin-top:0px;
}
.login_input{
	padding-top:230px;
	padding-left:270px;
	margin-left:auto;
	text-align:left;
}
.login_input input{
	border:0; 
	background:transparent;
	height:30px; line-height:30px;
	width:156px;
}
.login_button button {
	border:0; 
	background:transparent;
	cursor:pointer;
}
.login_button img{height:33px; width:85px; cursor:pointer;background:url(img/login_bt_01.jpg) no-repeat;}
.login_button .login{background:url(img/login_bt_01.jpg) no-repeat;}
.login_button .rest{background: url(img/login_bt_02.jpg) no-repeat;}
</style>
<script type="text/javascript" src="plugin/jquery-ui-1.8.4.custom/js/jquery-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript">

	$(document).ready(function(){
		$('input[name="j_username"]').focus();
		
		$("#login").hover(function(){
			$(this).attr('src','img/bt_login_02.jpg');
		},function(){
			$(this).attr('src','img/bt_login_01.jpg');
		});
		$("#rest").hover(function(){
			$(this).attr('src','img/bt_rest_02.jpg');
		},function(){
			$(this).attr('src','img/bt_rest_01.jpg');
		});

		$('a').click(function(){
			if($('input[name="j_username"]').val()== ""){
		    	window.alert("请填写用户名！");
	    	}else if($('input[name="j_password"]').val()== ""){
		    	window.alert("请填写密码！");
	    	}else {
			$('#thisForm').submit();
	    }
		});
		$('#reset').click(function(){
			$('input[name="j_username"]').val("");
			$('input[name="j_password"]').val("");
			$('input[name="j_username"]').focus();
		});
		$('a,input[type="button"],input[type="submit"]').bind('focus',function(){
	        if(this.blur){ //如果支持 this.blur
	                this.blur();
	        };
		});
		$(document).keydown(function(event){
			if (event.keyCode == 13){
				if($('input[name="j_username"]').val()== ""){
			    	window.alert("请填写用户名！");
		    	}else if($('input[name="j_password"]').val()== ""){
			    	window.alert("请填写密码！");
		    	}else {
				$('#thisForm').submit();
		    }
			}
		});
	});
	
</script>
</head>

<body>
<%if (request.getParameter("type")!=null&&request.getParameter("type").equals("noLogin")) {	%>
		<div id="errors" class="errors">
			<img src="${request.contextPath}/img/alert.jpg" style="margin-bottom:-6px; margin-right:6px;" />
			警告：您还没有登录！
		</div>
		<%} else if (request.getParameter("type")!=null&&request.getParameter("type").equals("passError")) {%>
		<% String message=((AuthenticationException)request.getSession().getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage();%>
		<div id="errors" class="errors">
			<img src="${request.contextPath}/img/alert.jpg"
				style="margin-bottom:-6px; margin-right:6px;" />
				警告：<% 
				     if(message.equals("Bad credentials"))
				     {
				       out.println("用户名密码错误！");
				    }else { 
				    	 out.println(message);
				    }%>
		 </div>
		<%} else if (request.getParameter("type")!=null&&request.getParameter("type").equals("hasSession")) {%>
		<div id="errors"  class="errors">
			<img src="${request.contextPath}/img/alert.jpg"
				style="margin-bottom:-6px; margin-right:6px;" />
			警告：另一相同用户登录，被踢出，请重新登录，谢谢！

		</div>
		<%}%>
		
<div class="login">
	<form id="thisForm" action="./j_spring_security_check" method="post">
		<div class="login_input">
	    	<input name="j_username" type="text" style="margin-bottom:30px;"/><br/>
	        <input name="j_password" type="password" style="margin-bottom:24px;"/>
	    </div>
		<div class="login_button">
		  <a href="#"><img src="img/bt_login_01.jpg" id="login"/></a>
	      <img src="img/bt_rest_01.jpg" id="rest"/>
	    </div>
	</form>
</div>

</body>
</html>
