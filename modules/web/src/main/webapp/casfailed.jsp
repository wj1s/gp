<%@ page session="false" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.web.authentication.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.web.authentication.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ page import="org.springframework.security.web.authentication.AbstractProcessingFilter" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>无线电台管理局 - 门户平台登录入口</title>
		<style type="text/css">
		<!--
		body,td,th {
			font-family: Tahoma;
			font-size: 12px;
			color: #fff;
			margin:0px;
			text-align:center;
		}
		body {
			background-color: #62697c;
		}
			
		-->
		</style>
	</head>
	<body>
		<% String message=((AuthenticationException)request.getSession().getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage();
		%>
		<div id="errors"
			style="color:#fc0;padding:6px; border-bottom:1px #8990a2 dashed; background:#444b5f;">

			<img src="${request.contextPath}/img/alert.jpg"
				style="margin-bottom:-6px; margin-right:6px;" />
			警告：<%=message%>	<a href="${request.contextPath}/j_spring_cas_security_logout" style="color: red;" > 返回</a>登录页面
			
			
		</div>
	</body>
</html>