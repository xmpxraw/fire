<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="/pages/include/base-head.jsp"%>
</head>
<body>
	<div style="color:red;padding:20px;font-size: 12pt;">发生错误啦!</div>
	<div style="padding:0px 20px;font-size: 10pt;">
		原因:
		<%
			Throwable e=(Exception)request.getAttribute("EXCEPTION");
			if(e==null) e=exception;
			out.println(e.getMessage());
		%>
	</div>
</body>
</html>