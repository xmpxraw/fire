<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<style type="text/css">
	body {
		font-size: 13px;
		filter: progid:dximagetransform.microsoft.gradient(gradienttype=0,startcolorstr=#dbebfa, endcolorstr=#f9fcfd);
		margin: 0px;
	}
	</style>
</head>
<body>
	<%
		Logger logger = LogManager.getRootLogger();
		logger.error("服务器异常 URL:"+request.getRequestURL()+"  QueryString:"+request.getQueryString(), exception);
	%>
	<table border=0 cellpadding=0 cellspacing=0 width="100%" height="100%">
		<tr>
			<td align="center" style="padding-top: 60px;"><img
				src="${skin}/image/system/error/500/500.jpg" /></td>
		</tr>
	</table>
</body>
</html>